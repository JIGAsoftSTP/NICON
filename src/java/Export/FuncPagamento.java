/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import conexao.Call;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import lib.Moeda;
import modelo.CreditoDebito;
import modelo.Movimento;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AhmedJorge
 */
public class FuncPagamento {

    private String reString;
    static String PAGAMENTO = "PAGAMENTO", CODPAGAMENTO = "COD PAGAMENTO", FORMAPAGAMENTO = "FORMA PAGAMENTO",
            DOCFORMAPAGAMENTO = "DOC FORMA PAGAMENTO", BENEFICIARIO = "BENEFICIARIO",
            DESCRICAOPAGAMENTO = "DESCRICAO PAGAMENTO", VALORPAGAMENTO = "VALOR PAGAMENTO",
            NUMERODACONTABANCO = "NUMERO DA CONTA BANCO", TITULOCONTAPAGAMENTO = "TITULO CONTA PAGAMENTO",
            DESCRICAOCONTABANCO = "DESCRICAO CONTA BANCO", MOEDA = "MOEDA", SIGLA = "SIGLA",
            REGISTRO = "REGISTRO", QUANTIDADE = "QUANTIDADE", CODIGOCONTAPAGAMENTO = "CODIGO CONTA PAGAMENTO", RETENCAOFONTE = "RETENCAO_FONTE";

    public String criarDoc(String user, String numPagamento, String nomeUser, int i) {
        String re = ((i == 2) ? folhaPagamento(numPagamento, user, nomeUser, i) : pequenoPagamento(numPagamento, user, nomeUser, i));
        return re;
    }

    private String folhaPagamento(String numPagamento, String user, String nomeUser, int j) throws NumberFormatException {
        try {

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoP = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoNU = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f, Font.UNDERLINE);
            Font fontCorpoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 11.5f);
            Font fontCorpoNGT = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 13f, Font.UNDERLINE);
            Font fontCabecalhoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            ArrayList<HashMap<String, Object>> mapList = getObj(numPagamento);

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 10f, 5f);

            String Ddata = sdf1.format(new Date());

            int size = mapList.size(), i = 0;

            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Pagamentos/");

            ff.mkdirs();
            ff = new File(ff.getAbsoluteFile() + "/" + "Pagamentos Func " + Ddata + ".pdf");

            OutputStream outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            MyFooter event = new MyFooter();
            writer.setPageEvent(event);

            documento.open();

            for (HashMap<String, Object> hashMap : mapList) {
                i++;

                PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{25f, 75f});
                pTableEmpresaPricipal.setWidthPercentage(90);
                PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
                PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);

                PdfPTable pTableNull = new PdfPTable(1);
                PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontCorpoP));
                cellNull.setBorder(0);
                pTableNull.addCell(cellNull);

                PdfPCell pCellNomeEmpresa = new PdfPCell(new Phrase(ConfigDoc.Empresa.NOME, fontCabecalhoNG));
                pCellNomeEmpresa.setBorder(0);

                PdfPCell pCellNomeEndereco = new PdfPCell(new Phrase(ConfigDoc.Empresa.ENDERECO, fontCabecalhoN));
                pCellNomeEndereco.setBorder(0);

                PdfPCell pCellCaixaPostal = new PdfPCell(new Phrase(ConfigDoc.Empresa.CAIXAPOSTAL, fontCabecalhoN));
                pCellCaixaPostal.setBorder(0);

                PdfPCell pCellTeleFax = new PdfPCell(new Phrase(ConfigDoc.Empresa.TELEFAX + " " + ConfigDoc.Empresa.EMAIL, fontCabecalhoN));
                pCellTeleFax.setBorder(0);

                PdfPCell pCellSociedade = new PdfPCell(new Phrase(ConfigDoc.Empresa.SOCIEDADE, fontCabecalhoN));
                pCellSociedade.setBorder(0);

                Image imageEmpresa = Image.getInstance("logo.png");
                imageEmpresa.scaleToFit(120f, 85f);

                pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
                pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
                pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
                pTableEmpresaInforImpres1.addCell(pCellTeleFax);
                pTableEmpresaInforImpres1.addCell(pCellSociedade);

                PdfPCell cellTabela3 = new PdfPCell(pTableEmpresaInforImpres1);
                cellTabela3.setBorder(0);

                pTableEmpresaInforImpres5.addCell(cellTabela3);

                PdfPCell cellTabela5 = new PdfPCell(pTableEmpresaInforImpres5);
                cellTabela5.setBorder(0);

                PdfPCell cellTabela6 = new PdfPCell(imageEmpresa);
                cellTabela6.setBorder(0);

                pTableEmpresaPricipal.addCell(cellTabela6);
                pTableEmpresaPricipal.addCell(cellTabela5);

                documento.add(pTableEmpresaPricipal);

                documento.add(pTableNull);
                documento.add(pTableNull);

                PdfPTable pTableTitulo = new PdfPTable(new float[]{50, 50});
                pTableTitulo.setWidthPercentage(90f);
                PdfPCell cellTitulo = new PdfPCell(new Phrase("Comprovativo de Pagamento", fontCorpoNGT));
                cellTitulo.setBorder(0);
                cellTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTitulo.setPaddingRight(-80f);
                cellTitulo.setPaddingTop(-26f);
                pTableTitulo.addCell(cellTitulo);
                cellTitulo = new PdfPCell(new Phrase("Nº: " + hashMap.get(PAGAMENTO), fontCorpoNGT));
                cellTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTitulo.setBorder(0);
                cellTitulo.setPaddingBottom(5f);
                pTableTitulo.addCell(cellTitulo);

                documento.add(pTableTitulo);

                PdfPTable pTableAno = new PdfPTable(new float[]{100});
                pTableAno.setWidthPercentage(90f);
                PdfPCell cellAno = new PdfPCell(new Phrase(toData(hashMap.get(REGISTRO), j), fontCorpoNGT));
                cellAno.setBorder(0);
                cellAno.setPaddingBottom(20f);
                pTableAno.addCell(cellAno);

                documento.add(pTableAno);

                PdfPTable pTableDetalhesPagamento = new PdfPTable(new float[]{100});
                pTableDetalhesPagamento.setWidthPercentage(90f);
                PdfPCell cellDetalhesPagamento = new PdfPCell();

                Paragraph pDetalhesPagamento = new Paragraph();
                pDetalhesPagamento.add(new Paragraph("\nDetalhes de Pagamento ", fontCorpoNG));

                pDetalhesPagamento.add(new Phrase("Beneficiário: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toString(hashMap.get(BENEFICIARIO)) + "\n", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Descrição do pagamento: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toString(hashMap.get(DESCRICAOPAGAMENTO)), fontCorpo));
                pDetalhesPagamento.add(new Paragraph("CONFORME APROVADO\n\n", fontCorpoN));

                pDetalhesPagamento.add(new Paragraph("Valor Pagamento", fontCorpoNG));
                pDetalhesPagamento.add(new Phrase("Valor Numerico: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toMoeda(hashMap.get(VALORPAGAMENTO), toString(hashMap.get(SIGLA))) + "\n", fontCorpo));
                System.err.println(toString(hashMap.get(RETENCAOFONTE))+" hfhfh retensao");
                if(toString(hashMap.get(RETENCAOFONTE)).trim().equals("1")){
                   double ret = getValorImportRetensao();
                   pDetalhesPagamento.add(new Phrase("Valor Retensao Fonte: ", fontCorpoN));
                   pDetalhesPagamento.add(new Phrase(toMoeda(ret, "%") + "\n", fontCorpo));
                   pDetalhesPagamento.add(new Phrase("Valor Retido: ", fontCorpoN));
                   double valret =toDouble(hashMap.get(VALORPAGAMENTO))*ret;
                   pDetalhesPagamento.add(new Phrase( toMoeda(valret, toString(hashMap.get(SIGLA)))+ "\n", fontCorpo));
                }

                JTextPane jtp = new JTextPane();
                Double valor = Double.valueOf((hashMap.get(VALORPAGAMENTO) + ""));
                Moeda.EscreverEstenso(valor, jtp, toString(hashMap.get(MOEDA)));

                pDetalhesPagamento.add(new Phrase("Valor por Extenso: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(jtp.getText().trim() + " \n\n", fontCorpo));

                pDetalhesPagamento.add(new Paragraph("Descrição da Conta", fontCorpoNG));
                pDetalhesPagamento.add(new Phrase("Cod Conta: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(hashMap.get(CODIGOCONTAPAGAMENTO) + "\n", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Titulo da Conta: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(hashMap.get(TITULOCONTAPAGAMENTO) + "\n", fontCorpo));

                /**
                 * For alter
                 */
                pDetalhesPagamento.add(new Phrase("Forma de Pagamento: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(hashMap.get(FORMAPAGAMENTO) + " \n", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Documento de Pagamento: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toString(hashMap.get(DESCRICAOCONTABANCO)) + " - " + toString(hashMap.get(DOCFORMAPAGAMENTO)) + "\n\n", fontCorpo));
                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));

                cellDetalhesPagamento.addElement(pDetalhesPagamento);
                pTableDetalhesPagamento.addCell(cellDetalhesPagamento);

                cellDetalhesPagamento = new PdfPCell();
                pDetalhesPagamento = new Paragraph();

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Preparado por: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(nomeUser + " ", fontCorpoP));
                pDetalhesPagamento.add(new Phrase("            Examinado por", fontCorpoN));
                pDetalhesPagamento.add(new Phrase("____________________\n", fontCorpo));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));
                pDetalhesPagamento.add(new Paragraph("Pagamento Autorizado por:", fontCorpoNG));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase("Assinatura: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ____________________________________________", fontCorpo));
                pDetalhesPagamento.add(new Phrase("    Data ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ______________________\n\n", fontCorpo));

                pDetalhesPagamento.add(new Phrase("Assinatura: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ____________________________________________", fontCorpo));
                pDetalhesPagamento.add(new Phrase("    Data ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ______________________\n", fontCorpo));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                pDetalhesPagamento.add(new Paragraph("............................................................................."
                        + "...........................................................................", fontCorpoN));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                Paragraph pReceip = new Paragraph("RECIBO", fontCorpoN);
                pReceip.setAlignment(Element.ALIGN_CENTER);
                pDetalhesPagamento.add(pReceip);

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Recebi o valor de: ", fontCorpo));
                pDetalhesPagamento.add(new Phrase(jtp.getText() + " \n", fontCorpoNU));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));

                PdfPTable pTableNumCheque = new PdfPTable(new float[]{33.3333333333f, 33.3333333333f, 33.3333333333f});
                pTableNumCheque.setWidthPercentage(90f);
                pTableNumCheque.setWidthPercentage(100f);

                PdfPCell cellNumCheque = new PdfPCell(new Phrase("_______________________", fontCorpo));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("_______________________", fontCorpo));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("_______________________", fontCorpo));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);

                cellNumCheque = new PdfPCell(new Phrase("Cheque No.", fontCorpoN));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("Data", fontCorpoN));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("Receiver Name & Signature", fontCorpoN));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);

                pDetalhesPagamento.add(pTableNumCheque);

                pDetalhesPagamento.add(new Phrase("\nNOTA: Um recibo oficial pode ser obtido por um pagamento e informado na parte inversa deste comprovativo. \n\n", fontCorpo));

                cellDetalhesPagamento.addElement(pDetalhesPagamento);

                pTableDetalhesPagamento.addCell(cellDetalhesPagamento);

                documento.add(pTableDetalhesPagamento);

                if (i != size) {
                    documento.newPage();
                }
            }

            documento.close();

            reString = "../Documentos/" + user + "/Pagamentos/" + "Pagamentos Func " + Ddata + ".pdf";
            return reString;
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(FuncPagamento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FuncPagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    }

    private String pequenoPagamento(String numPagamento, String user, String nomeUser, int i) {
        OutputStream outputStraem;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy - dd", new Locale("pt", "BR"));
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 14f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8.5f);
            Font fontCorpoU = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f, Font.UNDERLINE);
//            Font fontCorpoP= FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8.5f);
//            Font fontCorpoNGT= FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED , 13f ,Font.UNDERLINE);
            Font fontCabecalhoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 10f, 5f);
            String Ddata = sdf1.format(new Date());

            ArrayList<HashMap<String, Object>> mapList = getObj(numPagamento);
            int total = mapList.size();

            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Pagamentos/");
            ff.mkdirs();
            ff = new File(ff.getAbsoluteFile() + "/" + "Pagamentos Func " + Ddata + ".pdf");

            outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);
            MyFooterA5 event = new MyFooterA5();
            writer.setPageEvent(event);

            documento.open();

            PdfPTable pTableTitile = new PdfPTable(new float[]{100});
            pTableTitile.setWidthPercentage(95f);
            PdfPCell cellTitile = new PdfPCell();
            cellTitile.setBorder(0);

            Paragraph pTitile = new Paragraph();

            pTitile.add(new Phrase(ConfigDoc.Empresa.NOME.toUpperCase(), fontCabecalhoNG));
            pTitile.setAlignment(Element.ALIGN_CENTER);

            PdfPTable pTableSubTitile = new PdfPTable(new float[]{80, 20});
            pTableSubTitile.setWidthPercentage(101f);

            PdfPCell cellSubTitile = new PdfPCell(new Phrase("Petty cash voucher no.".toUpperCase(), fontCabecalhoN));
            cellSubTitile.setBorder(0);
            cellSubTitile.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellSubTitile.setPaddingBottom(20);
            cellSubTitile.setPaddingRight(67);
            cellSubTitile.setPaddingTop(10);
            pTableSubTitile.addCell(cellSubTitile);

            cellSubTitile = new PdfPCell(new Phrase(toData((mapList.size() > 0) ? mapList.get(0).get(REGISTRO) : " ", i).toUpperCase(), fontCabecalhoN));
            cellSubTitile.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellSubTitile.setBorder(0);
            cellSubTitile.setPaddingBottom(20);
            cellSubTitile.setPaddingTop(10);
            pTableSubTitile.addCell(cellSubTitile);
//            pTitile.add(cellSubTitile);

            pTitile.add(pTableSubTitile);

            cellTitile.addElement(pTitile);
            pTableTitile.addCell(cellTitile);

            documento.add(pTableTitile);

            PdfPTable pTableDados = new PdfPTable(new float[]{5.6f, 6.05f, 53.5f, 17.95f, 19f});
            pTableDados.setWidthPercentage(95f);

            PdfPCell cellDados = new PdfPCell(new Phrase("S/N", fontCorpoNG));
            cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Qty", fontCorpoNG));
            cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Detail Description", fontCorpoNG));
            cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Unit price", fontCorpoNG));
            cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("AMT (STD)", fontCorpoNG));
            cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            Double toPa = 0.0;
            for (int j = 0; j < total; j++) {
                int tLinha = toString(mapList.get(j).get(DESCRICAOPAGAMENTO)).split("\n").length;

                float pad = setPadding(total, tLinha);

                cellDados = new PdfPCell(new Phrase((j + 1) + "", fontCorpo));
                cellDados.setPaddingBottom(pad);
                cellDados.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellDados.setPaddingTop(pad);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(toInt(mapList.get(j).get(QUANTIDADE)) + "", fontCorpo));
                cellDados.setPaddingBottom(pad);
                cellDados.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellDados.setPaddingTop(pad);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(toString(mapList.get(j).get(DESCRICAOPAGAMENTO)), fontCorpo));
                cellDados.setPaddingBottom(pad);
                cellDados.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellDados.setPaddingTop(pad);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(toMoeda((toDouble(mapList.get(j).get(VALORPAGAMENTO)) / (float) toInt(mapList.get(j).get(QUANTIDADE))),
                        toString(mapList.get(j).get(SIGLA))), fontCorpo));
                cellDados.setPaddingBottom(pad);
                cellDados.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellDados.setPaddingTop(pad);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(toMoeda(toDouble(mapList.get(j).get(VALORPAGAMENTO)), toString(mapList.get(j).get(SIGLA))), fontCorpo));
                cellDados.setPaddingBottom(pad);
                cellDados.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellDados.setPaddingTop(pad);
                pTableDados.addCell(cellDados);

                toPa += toDouble(mapList.get(j).get(VALORPAGAMENTO));
            }
//            5.6f,5.6f,53.5f,17.95f,19.45f
            PdfPTable pTableRodape = new PdfPTable(new float[]{5.6f, 6.05f, 19.795f, 33.705f, 17.95f, 19f});
            pTableRodape.setWidthPercentage(95f);

            cellDados = new PdfPCell(new Phrase(" ", fontCorpoNG));
            pTableRodape.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("CODE", fontCorpoNG));
            pTableRodape.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(toString(mapList.get(0).get(DOCFORMAPAGAMENTO)), fontCorpoNG));
            pTableRodape.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(toString(mapList.get(0).get(DESCRICAOCONTABANCO)), fontCorpoNG));
            pTableRodape.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("TOTAL", fontCorpoNG));
            pTableRodape.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(toMoeda(toPa, toString(mapList.get(0).get(SIGLA))), fontCorpoNG));
            pTableRodape.addCell(cellDados);

            documento.add(pTableDados);
            documento.add(pTableRodape);

            PdfPTable pTableAssiEstenso = new PdfPTable(new float[]{100f});

            pTableAssiEstenso.setWidthPercentage(95f);

            PdfPCell cellAssiEstenso = new PdfPCell();
            Paragraph pAssiEstenso = new Paragraph();

            PdfPTable pTableResposavel = new PdfPTable(new float[]{70, 30});
            pTableResposavel.setWidthPercentage(100f);

            PdfPCell cellResposavel = new PdfPCell();
            cellResposavel.addElement(new Phrase("Requested by:_________________________________________", fontCorpoN));
            cellResposavel.setPaddingTop(10f);
            cellResposavel.setPaddingBottom(20f);
            cellResposavel.setBorder(0);
            pTableResposavel.addCell(cellResposavel);

            cellResposavel = new PdfPCell();
            cellResposavel.addElement(new Phrase(" HOD:___________________", fontCorpoN));
            cellResposavel.setPaddingTop(10f);
            cellResposavel.setPaddingBottom(20f);
            cellResposavel.setBorder(0);
            pTableResposavel.addCell(cellResposavel);

            cellResposavel = new PdfPCell();
            cellResposavel.addElement(new Phrase("Approveds by:_________________________________________", fontCorpoN));
            cellResposavel.setPaddingTop(-5f);
            cellResposavel.setPaddingBottom(20f);
            cellResposavel.setBorder(0);
            pTableResposavel.addCell(cellResposavel);

            cellResposavel = new PdfPCell();
            cellResposavel.addElement(new Phrase(" Date:___________________", fontCorpoN));
            cellResposavel.setPaddingTop(-5f);
            cellResposavel.setPaddingBottom(20f);
            cellResposavel.setBorder(0);
            pTableResposavel.addCell(cellResposavel);

            pAssiEstenso.add(pTableResposavel);
            cellAssiEstenso.addElement(pAssiEstenso);

            PdfPTable pTableExteso = new PdfPTable(new float[]{25f, 75f});
            pTableExteso.setWidthPercentage(100f);

            JTextPane jtp = new JTextPane();
            Moeda.EscreverEstenso(toPa, jtp, ((mapList.size() > 0) ? toString(mapList.get(0).get(MOEDA)) : ""));

            PdfPCell cellExteso = new PdfPCell(new Phrase("Recived the sum of", fontCorpoN));
            cellExteso.setBorder(0);
            pTableExteso.addCell(cellExteso);
            cellExteso = new PdfPCell(new Phrase(jtp.getText().toUpperCase().trim(), fontCorpoU));
            cellExteso.setBorder(0);
            pTableExteso.addCell(cellExteso);

            pAssiEstenso.add(pTableExteso);

            PdfPTable pTableAss = new PdfPTable(new float[]{45f, 55f});
            pTableAss.setWidthPercentage(100f);

            PdfPCell cellAss = new PdfPCell(new Phrase(" ______________", fontCorpoN));
            cellAss.setPaddingTop(15f);
            cellAss.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellAss.setBorder(0);
            cellAss.setPaddingBottom(0f);
            pTableAss.addCell(cellAss);

            cellAss = new PdfPCell(new Phrase(" ____________________________________________", fontCorpoN));
            cellAss.setPaddingTop(15f);
            cellAss.setPaddingBottom(0f);
            cellAss.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellAss.setBorder(0);
            pTableAss.addCell(cellAss);

            cellAss = new PdfPCell(new Phrase("Date", fontCorpoN));
            cellAss.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellAss.setBorder(0);
            pTableAss.addCell(cellAss);

            cellAss = new PdfPCell(new Phrase("Receiver name & signature", fontCorpoN));
            cellAss.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellAss.setBorder(0);
            pTableAss.addCell(cellAss);
            pTableAss.setHorizontalAlignment(Element.ALIGN_CENTER);

            pAssiEstenso.add(pTableAss);
            pAssiEstenso.setAlignment(Element.ALIGN_CENTER);
            pAssiEstenso.add(new Phrase("NOTA: Um recibo oficial pode ser obtido por um pagamento e informado na parte inversa deste comprovativo.\n\n", fontCorpoN));
            cellAssiEstenso = new PdfPCell();
            cellAssiEstenso.addElement(pAssiEstenso);

            pTableAssiEstenso.addCell(cellAssiEstenso);

            documento.add(pTableAssiEstenso);

            documento.close();
            reString = "../Documentos/" + user + "/Pagamentos/" + "Pagamentos Func " + Ddata + ".pdf";
            return reString;
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(FuncPagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    }

    private float setPadding(float total, int linha) {
        return ((((75 / (2)) / total) - (linha * total)) < 0) ? 0 : (((75 / (2)) / total) - (linha * total));
    }

    static class MyFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance("rodape.png");
                image.scaleAbsolute(PageSize.A4.rotate());
                image.scaleToFit(550f, 400f);
                image.setAbsolutePosition(60, 195);
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.15f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class MyFooterA5 extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance("rodape.png");
                image.scaleAbsolute(PageSize.A4);
                image.scaleToFit(300f, 250f);
                image.setAbsolutePosition(145, 530);
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.15f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
//        new FuncPagamento().criarDoc("ah", "105", "Ahmed Ferreira", 1);

        FuncPagamento.folhaPagamentoOnlyMovCre("ah", "105", "Ahmed Ferreira", 1);
    }

    public ArrayList<HashMap<String, Object>> getObj(String numPagamento) {
        ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
        HashMap<String, Object> mapLista = new HashMap<>();
        ResultSet rs = Call.selectFrom("VER_PAGAMENTO_ITEMS where \"PAGAMENTO\" = ?", "*", numPagamento);
        Consumer<HashMap<String, Object>> act = (map) -> {
            mapLista.putAll(map);
            listMap.add(new HashMap<>(mapLista));
        };
        Call.forEchaResultSet(act, rs);
        return listMap;
    }

    static private ArrayList<HashMap<String, Object>> setObj(String numPagamento, Object ob) {
        ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
        HashMap<String, Object> mapLista;
         SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        if (ob instanceof CreditoDebito) {
            CreditoDebito cd = (CreditoDebito) ob;
            mapLista = new HashMap<>();
            
            ContaBanco cb = new ContaBanco(cd.getConta());
            
            mapLista.put(BENEFICIARIO,cb.NUMEROBANCARIO);
            mapLista.put(DESCRICAOPAGAMENTO, "Novo "+(cd.getTipo().equals("1") ? "Credito" : "Debito")+" efetuado para a conta "+cb.NUMEROBANCARIO+" em "+sdf.format(new Date()));

            mapLista.put(VALORPAGAMENTO, cd.getValor());

            mapLista.put(CODIGOCONTAPAGAMENTO, cb.NUMEROBANCARIO);
            mapLista.put(TITULOCONTAPAGAMENTO, cb.DESCRICAO);

            mapLista.put(DESCRICAOCONTABANCO, cb.BANCO +" "+cb.SIGLA+" "+cb.TIPOMOVIMENTO);
            mapLista.put(DOCFORMAPAGAMENTO, cd.getNumeroDucumento());
            mapLista.put(MOEDA, cb.NOMEMOEDA);
            mapLista.put(SIGLA, cb.SIGLA);
            mapLista.put(PAGAMENTO, numPagamento);
        } else {
            Movimento m = (Movimento) ob;

            mapLista = new HashMap<>();

            ContaBanco cb1 = new ContaBanco(m.getPara());
            ContaBanco cb2 = new ContaBanco(m.getDe());
            
            float valor = Float.valueOf(m.getValor());
            float taxa = 1;
            if(!cb2.SIGLA.equals(cb1.SIGLA))
            {
                    int id1 = Moeda.getIdMoedaBySigla(cb1.SIGLA);
                    int id2 = Moeda.getIdMoedaBySigla(cb2.SIGLA);
                    taxa = Moeda.getTaxaMoeda(id2, id1, new Date());
                    valor *= taxa;
            }
            
            mapLista.put(BENEFICIARIO, cb1.NUMEROBANCARIO);
            mapLista.put(DESCRICAOPAGAMENTO,m.getDescricao()+ "\nTransferência de "+Moeda.format(Double.valueOf(m.getValor()))+" "+cb2.SIGLA +" da Conta "+cb2.NUMEROBANCARIO+" para "+cb1.NUMEROBANCARIO+
                    ((!cb2.SIGLA.equals(cb1.SIGLA)) ? "\nCâmbio do dia: 1 "+cb2.SIGLA +" = "+ Moeda.format(taxa) +" "+cb1.SIGLA +" equivalente a "+Moeda.format(valor)+" "+cb1.SIGLA+" em "+sdf.format(new Date()): " em "+sdf.format(new Date()))
                    );

            mapLista.put(VALORPAGAMENTO, m.getValor());

            mapLista.put(CODIGOCONTAPAGAMENTO, cb1.NUMEROBANCARIO);
            mapLista.put(TITULOCONTAPAGAMENTO, cb1.DESCRICAO);

            mapLista.put(DESCRICAOCONTABANCO, cb2.BANCO +" "+cb2.SIGLA+" "+cb2.TIPOMOVIMENTO);
//            mapLista.put(DOCFORMAPAGAMENTO, "10");
            mapLista.put(MOEDA, cb2.NOMEMOEDA);
            mapLista.put(SIGLA, cb2.SIGLA);
            mapLista.put(PAGAMENTO, numPagamento);
        }
        listMap.add(mapLista);
        return listMap;
    }

    public static String toMoeda(Object o, String moeda) {
        if (o != null) {
            return Moeda.format(Double.valueOf(o.toString())) + " " + moeda;
        } else {
            return " ";
        }
    }

    public static String toString(Object o) {
        return ((o != null) ? o.toString() : "");
    }

    public static Double toDouble(Object o) {
        return ((o != null) ? Double.valueOf(o.toString()) : 0);
    }

    public static int toInt(Object o) {
        return ((o != null) ? Integer.valueOf(o.toString()) : 0);
    }

    public static String toData(Object o, int i) {
        SimpleDateFormat sdfout = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM '('dd/MM/yyyy')'", new Locale("pt", "BR"));
        if (o != null) {
            try {
                return (i == 2) ? sdf1.format(sdfout.parse(o.toString())) : sdf2.format(sdfout.parse(o.toString()));
            } catch (ParseException ex) {
                return " ";
            }
        } else {
            return " ";
        }
    }

    public static String folhaPagamentoOnlyMovCre(String numPagamento, String user, String nomeUser, Object ob) throws NumberFormatException {
        try {

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoP = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoNU = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f, Font.UNDERLINE);
            Font fontCorpoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 11.5f);
            Font fontCorpoNGT = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 13f, Font.UNDERLINE);
            Font fontCabecalhoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            ArrayList<HashMap<String, Object>> mapList = setObj(numPagamento, ob);

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 10f, 5f);

            String Ddata = sdf1.format(new Date());

            int size = mapList.size(), i = 0;

            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Pagamentos/");

            ff.mkdirs();
            ff = new File(ff.getAbsoluteFile() + "/" + "Pagamentos Func " + Ddata + ".pdf");

            OutputStream outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            MyFooter event = new MyFooter();
            writer.setPageEvent(event);

            documento.open();

            for (HashMap<String, Object> hashMap : mapList) {
                i++;

                PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{25f, 75f});
                pTableEmpresaPricipal.setWidthPercentage(90);
                PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
                PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);

                PdfPTable pTableNull = new PdfPTable(1);
                PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontCorpoP));
                cellNull.setBorder(0);
                pTableNull.addCell(cellNull);

                PdfPCell pCellNomeEmpresa = new PdfPCell(new Phrase(ConfigDoc.Empresa.NOME, fontCabecalhoNG));
                pCellNomeEmpresa.setBorder(0);

                PdfPCell pCellNomeEndereco = new PdfPCell(new Phrase(ConfigDoc.Empresa.ENDERECO, fontCabecalhoN));
                pCellNomeEndereco.setBorder(0);

                PdfPCell pCellCaixaPostal = new PdfPCell(new Phrase(ConfigDoc.Empresa.CAIXAPOSTAL, fontCabecalhoN));
                pCellCaixaPostal.setBorder(0);

                PdfPCell pCellTeleFax = new PdfPCell(new Phrase(ConfigDoc.Empresa.TELEFAX + " " + ConfigDoc.Empresa.EMAIL, fontCabecalhoN));
                pCellTeleFax.setBorder(0);

                PdfPCell pCellSociedade = new PdfPCell(new Phrase(ConfigDoc.Empresa.SOCIEDADE, fontCabecalhoN));
                pCellSociedade.setBorder(0);

                Image imageEmpresa = Image.getInstance("logo.png");
                imageEmpresa.scaleToFit(120f, 85f);

                pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
                pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
                pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
                pTableEmpresaInforImpres1.addCell(pCellTeleFax);
                pTableEmpresaInforImpres1.addCell(pCellSociedade);

                PdfPCell cellTabela3 = new PdfPCell(pTableEmpresaInforImpres1);
                cellTabela3.setBorder(0);

                pTableEmpresaInforImpres5.addCell(cellTabela3);

                PdfPCell cellTabela5 = new PdfPCell(pTableEmpresaInforImpres5);
                cellTabela5.setBorder(0);

                PdfPCell cellTabela6 = new PdfPCell(imageEmpresa);
                cellTabela6.setBorder(0);

                pTableEmpresaPricipal.addCell(cellTabela6);
                pTableEmpresaPricipal.addCell(cellTabela5);

                documento.add(pTableEmpresaPricipal);

                documento.add(pTableNull);
                documento.add(pTableNull);

                PdfPTable pTableTitulo = new PdfPTable(new float[]{50, 50});
                pTableTitulo.setWidthPercentage(90f);
                PdfPCell cellTitulo = new PdfPCell(new Phrase("Comprovativo de Pagamento", fontCorpoNGT));
                cellTitulo.setBorder(0);
                cellTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTitulo.setPaddingRight(-80f);
                cellTitulo.setPaddingTop(-26f);
                pTableTitulo.addCell(cellTitulo);
                cellTitulo = new PdfPCell(new Phrase("Nº: " + hashMap.get(PAGAMENTO), fontCorpoNGT));
                cellTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTitulo.setBorder(0);
                cellTitulo.setPaddingBottom(5f);
                pTableTitulo.addCell(cellTitulo);

                documento.add(pTableTitulo);

                PdfPTable pTableAno = new PdfPTable(new float[]{100});
                pTableAno.setWidthPercentage(90f);

                SimpleDateFormat sdfVeiw = new SimpleDateFormat("MMMM '('dd/MM/yyyy')'",new Locale("pt", "BR"));
                PdfPCell cellAno = new PdfPCell(new Phrase(sdfVeiw.format(new Date()), fontCorpoNGT));
                cellAno.setBorder(0);
                cellAno.setPaddingBottom(20f);
                pTableAno.addCell(cellAno);

                documento.add(pTableAno);

                PdfPTable pTableDetalhesPagamento = new PdfPTable(new float[]{100});
                pTableDetalhesPagamento.setWidthPercentage(90f);
                PdfPCell cellDetalhesPagamento = new PdfPCell();

                Paragraph pDetalhesPagamento = new Paragraph();
                pDetalhesPagamento.add(new Paragraph("\nDetalhes de Pagamento ", fontCorpoNG));

                pDetalhesPagamento.add(new Phrase("Beneficiário: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toString(hashMap.get(BENEFICIARIO)) + "\n", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Descrição do pagamento: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toString(hashMap.get(DESCRICAOPAGAMENTO)), fontCorpo));
                pDetalhesPagamento.add(new Paragraph("CONFORME APROVADO\n\n", fontCorpoN));

                pDetalhesPagamento.add(new Paragraph("Valor Pagamento", fontCorpoNG));
                pDetalhesPagamento.add(new Phrase("Valor Numerico: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toMoeda(hashMap.get(VALORPAGAMENTO), toString(hashMap.get(SIGLA))) + "\n", fontCorpo));
                System.err.println(toString(hashMap.get(RETENCAOFONTE))+" hfhfh retensao");
                if(toString(hashMap.get(RETENCAOFONTE)).trim().equals("1")){
                   double ret = getValorImportRetensao();
                   pDetalhesPagamento.add(new Phrase("Valor Retensao Fonte: ", fontCorpoN));
                   pDetalhesPagamento.add(new Phrase(toMoeda(ret, "%") + "\n", fontCorpo));
                   pDetalhesPagamento.add(new Phrase("Valor Retido: ", fontCorpoN));
                   double valret =toDouble(hashMap.get(VALORPAGAMENTO))*ret;
                   pDetalhesPagamento.add(new Phrase( toMoeda(valret, toString(hashMap.get(SIGLA)))+ "\n", fontCorpo));
                }

                JTextPane jtp = new JTextPane();
                Double valor = Double.valueOf((hashMap.get(VALORPAGAMENTO) + ""));
                Moeda.EscreverEstenso(valor, jtp, toString(hashMap.get(MOEDA)));

                pDetalhesPagamento.add(new Phrase("Valor por Extenso: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(jtp.getText().trim() + " \n\n", fontCorpo));

                pDetalhesPagamento.add(new Paragraph("Descrição da Conta", fontCorpoNG));
                pDetalhesPagamento.add(new Phrase("Cod Conta: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(hashMap.get(CODIGOCONTAPAGAMENTO) + "\n", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Titulo da Conta: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(hashMap.get(TITULOCONTAPAGAMENTO) + "\n", fontCorpo));

                /**
                 * For alter
                 */
//                pDetalhesPagamento.add(new Phrase("Forma de Pagamento: ", fontCorpoN));
//                pDetalhesPagamento.add(new Phrase(hashMap.get(FORMAPAGAMENTO) + " \n", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Documento de Pagamento: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(toString(hashMap.get(DESCRICAOCONTABANCO)) + " - " + toString(hashMap.get(DOCFORMAPAGAMENTO)) + "\n\n", fontCorpo));
                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));

                cellDetalhesPagamento.addElement(pDetalhesPagamento);
                pTableDetalhesPagamento.addCell(cellDetalhesPagamento);

                cellDetalhesPagamento = new PdfPCell();
                pDetalhesPagamento = new Paragraph();

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Preparado por: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(nomeUser + " ", fontCorpoP));
                pDetalhesPagamento.add(new Phrase("            Examinado por", fontCorpoN));
                pDetalhesPagamento.add(new Phrase("____________________\n", fontCorpo));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));
                pDetalhesPagamento.add(new Paragraph("Pagamento Autorizado por:", fontCorpoNG));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase("Assinatura: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ____________________________________________", fontCorpo));
                pDetalhesPagamento.add(new Phrase("    Data ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ______________________\n\n", fontCorpo));

                pDetalhesPagamento.add(new Phrase("Assinatura: ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ____________________________________________", fontCorpo));
                pDetalhesPagamento.add(new Phrase("    Data ", fontCorpoN));
                pDetalhesPagamento.add(new Phrase(" ______________________\n", fontCorpo));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                pDetalhesPagamento.add(new Paragraph("............................................................................."
                        + "...........................................................................", fontCorpoN));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                Paragraph pReceip = new Paragraph("RECIBO", fontCorpoN);
                pReceip.setAlignment(Element.ALIGN_CENTER);
                pDetalhesPagamento.add(pReceip);

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpo));
                pDetalhesPagamento.add(new Phrase("Recebi o valor de: ", fontCorpo));
                pDetalhesPagamento.add(new Phrase(jtp.getText() + " \n", fontCorpoNU));

                pDetalhesPagamento.add(new Paragraph(" ", fontCorpoN));

                PdfPTable pTableNumCheque = new PdfPTable(new float[]{33.3333333333f, 33.3333333333f, 33.3333333333f});
                pTableNumCheque.setWidthPercentage(90f);
                pTableNumCheque.setWidthPercentage(100f);

                PdfPCell cellNumCheque = new PdfPCell(new Phrase("_______________________", fontCorpo));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("_______________________", fontCorpo));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("_______________________", fontCorpo));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);

                cellNumCheque = new PdfPCell(new Phrase("Cheque No.", fontCorpoN));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("Data", fontCorpoN));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);
                cellNumCheque = new PdfPCell(new Phrase("Receiver Name & Signature", fontCorpoN));
                cellNumCheque.setBorder(0);
                cellNumCheque.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableNumCheque.addCell(cellNumCheque);

                pDetalhesPagamento.add(pTableNumCheque);

                pDetalhesPagamento.add(new Phrase("\nNOTA: Um recibo oficial pode ser obtido por um pagamento e informado na parte inversa deste comprovativo. \n\n", fontCorpo));

                cellDetalhesPagamento.addElement(pDetalhesPagamento);

                pTableDetalhesPagamento.addCell(cellDetalhesPagamento);

                documento.add(pTableDetalhesPagamento);

                if (i != size) {
                    documento.newPage();
                }
            }

            documento.close();
            String ret = "../Documentos/" + user + "/Pagamentos/" + "Pagamentos Func " + Ddata + ".pdf";

            RequestContext.getCurrentInstance().execute("openAllDocument('" + ret + "')");

            return ret;
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(FuncPagamento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FuncPagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static class ContaBanco {

        String ID;
        String DESCRICAO;
        String SIGLA;
        String BANCO;
        String NUMEROBANCARIO;
        String TIPOCONTA;
        String TIPOMOVIMENTO;
        String SALDO;
        String DEBITO;
        String CREDITO;
        String ESTADO;
        String NOMEMOEDA;
        
        String ID_ = "ID";
        String DESCRICAO_ = "DESCRICAO";
        String SIGLA_ = "MOEDA";
        String BANCO_ = "BANCO";
        String NUMEROBANCARIO_ = "NUMERO BANCARIO";
        String TIPOCONTA_ = "TIPO CONTA";
        String TIPOMOVIMENTO_ = "TIPO MOVIMENTO";
        String SALDO_ = "SALDO";
        String DEBITO_ = "DEBITO";
        String CREDITO_ = "CREDITO";
        String ESTADO_ = "ESTADO";
        String NOMEMOEDA_ = "NOME MOEDA";
        

        public ContaBanco(String ID) {
            this.ID = ID;
            ResultSet rs = Call.selectFrom("VER_CONTA_CONTABIL WHERE \"TIPO CONTA\"='Conta Banco' and ID ='"+ID+"'" , "*");
            Consumer<HashMap<String, Object>> act = (map) -> {
                this.DESCRICAO = FuncPagamento.toString(map.get(this.DESCRICAO_));
                this.SIGLA = FuncPagamento.toString(map.get(this.SIGLA_));
                this.NOMEMOEDA = FuncPagamento.toString(map.get(this.NOMEMOEDA_));
                this.BANCO = FuncPagamento.toString(map.get(this.BANCO_));
                this.NUMEROBANCARIO = FuncPagamento.toString(map.get(this.NUMEROBANCARIO_));
                this.TIPOCONTA = FuncPagamento.toString(map.get(this.TIPOCONTA_));
                this.TIPOMOVIMENTO = FuncPagamento.toString(map.get(this.TIPOMOVIMENTO_));
                this.SALDO = FuncPagamento.toString(map.get(this.SALDO_));
                this.DEBITO = FuncPagamento.toString(map.get(this.DEBITO_));
                this.CREDITO = FuncPagamento.toString(map.get(this.CREDITO_));
                this.ESTADO = FuncPagamento.toString(map.get(this.ESTADO_));
            };
            Call.forEchaResultSet(act, rs);
        }
        
    }
    
    public static Double getValorImportRetensao() {
        Object re = Call.callSampleFunction("FUNC_GET_IMPOSTO_TAXA_VALOR", Types.FLOAT, "RETENCAO");
        Double ret = ((re != null) ? Double.valueOf(re.toString()) : 0);
        return ret;
    }
}
