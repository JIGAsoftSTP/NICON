/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.ModeloPagamento;

/**
 *
 * @author AhmedJorge
 */
public class ModalidadePagamentoPremio {

    private String reString;

    public String criarDoc(
            String numApolice,
            String numCliente, String user, String arquivo,
            String seguro, ArrayList<ModeloPagamento> list, String moeda
    ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy - dd", new Locale("pt", "BR"));
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoP = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f);
            Font fontCorpoPe = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 2f);
            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8.5f);
            Font fontCorpoNGT = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f, Font.UNDERLINE);
            Font fontCabecalhoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{25f, 75f});
            PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);

            PdfPTable pTableNull = new PdfPTable(1);
            PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontCorpoP));
            cellNull.setBorder(0);
            pTableNull.addCell(cellNull);

            PdfPCell pCellNomeEmpresa = new PdfPCell(new Phrase(Empresa.NOME, fontCabecalhoNG));
            pCellNomeEmpresa.setBorder(0);

            PdfPCell pCellNomeEndereco = new PdfPCell(new Phrase(Empresa.ENDERECO, fontCabecalhoN));
            pCellNomeEndereco.setBorder(0);

            PdfPCell pCellCaixaPostal = new PdfPCell(new Phrase(Empresa.CAIXAPOSTAL, fontCabecalhoN));
            pCellCaixaPostal.setBorder(0);

            PdfPCell pCellTeleFax = new PdfPCell(new Phrase(Empresa.TELEFAX + " " + ConfigDoc.Empresa.EMAIL, fontCabecalhoN));
            pCellTeleFax.setBorder(0);

            PdfPCell pCellSociedade = new PdfPCell(new Phrase(Empresa.SOCIEDADE, fontCabecalhoN));
            pCellSociedade.setBorder(0);

            PdfPCell pCellPolice = new PdfPCell(new Phrase(Empresa.APOLICE + numApolice, fontCabecalhoN));
            pCellPolice.setBorder(0);

            Image imageEmpresa = Image.getInstance("logo.png");
            imageEmpresa.scaleToFit(120f, 85f);

            pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
            pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
            pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
            pTableEmpresaInforImpres1.addCell(pCellTeleFax);
            pTableEmpresaInforImpres1.addCell(pCellSociedade);

            pTableEmpresaInforImpres1.addCell(pCellPolice);

            PdfPCell cellTabela3 = new PdfPCell(pTableEmpresaInforImpres1);
            cellTabela3.setBorder(0);

            pTableEmpresaInforImpres5.addCell(cellTabela3);

            PdfPCell cellTabela5 = new PdfPCell(pTableEmpresaInforImpres5);
            cellTabela5.setBorder(0);

            PdfPCell cellTabela6 = new PdfPCell(imageEmpresa);
            cellTabela6.setBorder(0);

            pTableEmpresaPricipal.addCell(cellTabela6);
            pTableEmpresaPricipal.addCell(cellTabela5);

            PdfPTable pTableTitulo = new PdfPTable(1);
            PdfPCell cellTitulo = new PdfPCell(new Phrase("Acordo de pagamento do prémio".toUpperCase(), fontCorpoNGT));
            cellTitulo.setBorder(0);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableTitulo.addCell(cellTitulo);

            PdfPTable pTableCliente = new PdfPTable(new float[]{100});
            pTableCliente.setWidthPercentage(60f);
            ClienteI ci = new ClienteI(numCliente);
            PdfPCell cellTituloTsbleSegurado = new PdfPCell();
            Paragraph pCliente = new Paragraph("Informações do Cliente".toUpperCase(), fontCorpoNG);
            pCliente.setAlignment(Element.ALIGN_CENTER);
            Paragraph pNull = new Paragraph(" ", fontCorpoPe);
            cellTituloTsbleSegurado.addElement(pCliente);
            cellTituloTsbleSegurado.addElement(pNull);
            cellTituloTsbleSegurado.setBorder(0);
            cellTituloTsbleSegurado.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            Paragraph pCl = new Paragraph();
            pCl.add(new Phrase(ci.getNOMEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNOME_(),fontCorpoN));
            PdfPCell cellNome = new PdfPCell(pCl);
            cellNome.setBorder(0);
            cellNome.setBorderWidthRight(0.5f);
            cellNome.setBorderWidthLeft(0.5f);
            cellNome.setBorderWidthTop(0.5f);
            cellNome.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getENDERECOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getENDERECO_(),fontCorpoN));
            PdfPCell cellEndereco = new PdfPCell(new Phrase(pCl));
            cellEndereco.setBorder(0);
            cellEndereco.setBorderWidthRight(0.5f);
            cellEndereco.setBorderWidthLeft(0.5f);
            cellEndereco.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getNUNCLIENTEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNUNCLIENTE_(),fontCorpoN));
            PdfPCell cellNCliente = new PdfPCell(pCl);
            cellNCliente.setBorder(0);
            cellNCliente.setBorderWidthRight(0.5f);
            cellNCliente.setBorderWidthLeft(0.5f);
            cellNCliente.setBorderWidthLeft(0.5f);
            cellNCliente.setBorderWidthBottom(0.5f);
            cellNCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
//            PdfPCell cellProfissao= new PdfPCell( new Phrase(Cliente.PROFISSAO,fontCorpo));
//            cellProfissao.setBorder(0);
//            cellProfissao.setBorderWidthRight(0.5f);
//            cellProfissao.setBorderWidthLeft(0.5f);
//            cellProfissao.setHorizontalAlignment(Element.ALIGN_CENTER);
//            PdfPCell cellLocalTrabalho= new PdfPCell( new Phrase(Cliente.LOCALTRABALHO,fontCorpo));
//            cellLocalTrabalho.setBorder(0);
//            cellLocalTrabalho.setBorderWidthRight(0.5f);
//            cellLocalTrabalho.setBorderWidthLeft(0.5f);
//            cellLocalTrabalho.setBorderWidthBottom(0.5f);
//            cellLocalTrabalho.setHorizontalAlignment(Element.ALIGN_CENTER);

            pTableCliente.addCell(cellTituloTsbleSegurado);
            pTableCliente.addCell(cellNome);
            pTableCliente.addCell(cellEndereco);
            pTableCliente.addCell(cellNCliente);
//            pTableCliente.addCell(cellProfissao);
//            pTableCliente.addCell(cellLocalTrabalho);

//            PdfPTable pTableOjbSegurado = new PdfPTable( new float[] {50,50});
//            pTableOjbSegurado.setWidthPercentage(75);
//            PdfPCell cellNomeObj, cellDescreObj; 
//            for (int i = 0; i < 4; i++) {
//                cellNomeObj = new PdfPCell( new Phrase("OBJ "+i, fontCorpo));
//                cellNomeObj.setPaddingBottom(5f);
//                cellNomeObj.setPaddingTop(5f);
//                cellNomeObj.setPaddingRight(20f);
//                cellNomeObj.setBorder(0);
////                cellNomeObj.setBorderWidthBottom(0.5f);
//                cellNomeObj.setBorderWidthLeft(0.5f);
//                cellNomeObj.setBorderWidthRight(0.5f);
//                cellNomeObj.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellDescreObj = new PdfPCell( new Phrase("DescreObj "+i, fontCorpo));
//                cellDescreObj.setPaddingBottom(5f);
//                cellDescreObj.setPaddingTop(5f);
//                cellDescreObj.setPaddingLeft(20f);
//                cellDescreObj.setBorder(0);
////                cellDescreObj.setBorderWidthBottom(0.5f);
//                cellDescreObj.setBorderWidthRight(0.5f);
//                cellDescreObj.setBorderWidthLeft(0.5f);
//                cellDescreObj.setHorizontalAlignment(Element.ALIGN_LEFT);
//                pTableOjbSegurado.addCell(cellNomeObj);
//                pTableOjbSegurado.addCell(cellDescreObj);
//                
//            }
            PdfPTable pTableTextoPagamento = new PdfPTable(new float[]{100});
            PdfPCell cellTextoPagamento = new PdfPCell(new Phrase("O Pagamento de Premio dessa apólice será efectuada com base no seguinte plano:", fontCorpo));
            cellTextoPagamento.setBorder(0);
            pTableTextoPagamento.addCell(cellTextoPagamento);

            PdfPTable pTablePestacao = new PdfPTable(new float[]{75f, 35f});
            PdfPCell cellDataPrestacao, cellValorPrestacao;
            Double total = 0.0;
            for (int i = 0; i < list.size(); i++) {
                cellDataPrestacao = new PdfPCell(new Phrase(ConfigDoc.getPestação((i + 1)) + "Pestacão " + sdf.format(list.get(i).getDataPagamentoLimite()), fontCorpo));
                cellDataPrestacao.setPaddingBottom(5f);
                cellDataPrestacao.setPaddingTop(5f);
                cellDataPrestacao.setPaddingLeft(20f);
                cellDataPrestacao.setBorder(0);
                cellDataPrestacao.setBorderWidthBottom(0.5f);

                total += list.get(i).getValorPagameto();

                cellValorPrestacao = new PdfPCell(new Phrase(moeda + " " + list.get(i).getValorPagametoVeiw(), fontCorpo));
                cellValorPrestacao.setPaddingBottom(7f);
                cellValorPrestacao.setPaddingTop(7f);
                cellValorPrestacao.setPaddingRight(20f);
                cellValorPrestacao.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellValorPrestacao.setBorder(0);
                cellValorPrestacao.setBorderWidthBottom(0.5f);

                pTablePestacao.addCell(cellDataPrestacao);
                pTablePestacao.addCell(cellValorPrestacao);
            }
            cellDataPrestacao = new PdfPCell(new Phrase("Total", fontCorpoN));
            cellDataPrestacao.setPaddingBottom(7f);
            cellDataPrestacao.setPaddingTop(7f);
            cellDataPrestacao.setPaddingLeft(20f);
            cellDataPrestacao.setBorder(0);
            cellDataPrestacao.setBorderWidthBottom(1f);

            cellValorPrestacao = new PdfPCell(new Phrase(moeda + " " + Moeda.format(total), fontCorpoN));
            cellValorPrestacao.setPaddingBottom(7f);
            cellValorPrestacao.setPaddingTop(7f);
            cellValorPrestacao.setPaddingRight(20f);
            cellValorPrestacao.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellValorPrestacao.setBorder(0);
            cellValorPrestacao.setBorderWidthBottom(1f);

            pTablePestacao.addCell(cellDataPrestacao);
            pTablePestacao.addCell(cellValorPrestacao);

            PdfPTable pTableTextoNota = new PdfPTable(new float[]{100});
            PdfPCell cellTextoNota = new PdfPCell();
            Paragraph pNota = new Paragraph("Foi entendido e acordado de que se o premio não for pago seguindo o método acima indicado e "
                    + "houver reclamações, o pagamento da reclamação será baseado na precentagem do prémio antes da ocorrência do acidente.", fontCorpo);
            Paragraph pData = new Paragraph("S.Tomé, " + sdf2.format(new Date()), fontCorpo);
            pNota.setAlignment(Element.ALIGN_JUSTIFIED);
            cellTextoNota.addElement(pNota);
            cellTextoNota.addElement(pNull);
            cellTextoNota.addElement(pData);
            cellTextoNota.setPaddingBottom(10f);
            pTableTextoNota.addCell(cellTextoNota);

            PdfPTable pTableAssinaturaTitulo = new PdfPTable(1);
            pTableAssinaturaTitulo.setTotalWidth(523);
            PdfPTable pTableAssinatura = new PdfPTable(new float[]{50f, 50f});
            pTableAssinatura.setTotalWidth(523);
            PdfPCell cellAssinatora = new PdfPCell(new Phrase("Assinaturas".toUpperCase(), fontCorpoN));
            cellAssinatora.setBorder(0);
            cellAssinatora.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha1 = new PdfPCell(new Phrase("___________________________________".toUpperCase(), fontCorpo));
            celllinha1.setBorder(0);
            celllinha1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha2 = new PdfPCell(new Phrase("___________________________________".toUpperCase(), fontCorpo));
            celllinha2.setBorder(0);
            celllinha2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell celllinha11 = new PdfPCell(new Phrase("nicon Seguros stp".toUpperCase(), fontCorpoP));
            celllinha11.setBorder(0);
            celllinha11.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha21 = new PdfPCell(new Phrase("o segurado ".toUpperCase(), fontCorpoP));
            celllinha21.setBorder(0);
            celllinha21.setHorizontalAlignment(Element.ALIGN_CENTER);

            pTableAssinaturaTitulo.addCell(cellAssinatora);
            pTableAssinatura.addCell(celllinha1);
            pTableAssinatura.addCell(celllinha2);
            pTableAssinatura.addCell(celllinha11);
            pTableAssinatura.addCell(celllinha21);

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 35f, 105f);

            File ff = new File(arquivo + "/" + user + "/Pagamentos/");

            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Pagamento-Contrato " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Pagamentos/" + "Pagamento-Contrato " + Ddata + ".pdf";

            OutputStream outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            if(MarcaDAgua.isSimulation) {
                MarcaDAgua.SimulacaoVertical  v = new MarcaDAgua.SimulacaoVertical();
                writer.setPageEvent(v);
            }
            
            if(MarcaDAgua.isCanceled) {
                MarcaDAgua.AnulacaoVertical  v = new MarcaDAgua.AnulacaoVertical();
                writer.setPageEvent(v);
            }
            
            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableTitulo);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableCliente);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableTextoPagamento);
            documento.add(pTableNull);
            documento.add(pTablePestacao);
            documento.add(pTableNull);
            documento.add(pTableTextoNota);
//            documento.add(pTableOjbSegurado);
//            documento.add(pTableNull);
            pTableAssinaturaTitulo.writeSelectedRows(-1, 1, 30, 90, writer.getDirectContent());
            pTableAssinatura.writeSelectedRows(-1, 2, 30, 60, writer.getDirectContent());
            documento.close();

//           PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Enviar Para o OneNote 2013",1); 
//           //PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",1); 
//             
//            printPdf.print();
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    }

    public static void main(String[] args) {
        new ModalidadePagamentoPremio().criarDoc("fhfhhf", "35", "AH", "ddddd", "Viagem", new ArrayList<>(), "STD");
    }
//   class MyFooter extends PdfPageEventHelper {
//
//        @Override
//        public void onStartPage(PdfWriter writer, Document document) {
//            try {
//                PdfContentByte canvas = writer.getDirectContentUnder();
//                Image image = Image.getInstance("logo.png");
//                image.scaleAbsolute(PageSize.A4.rotate());
//                image.scaleToFit(700f, 500f);
//                image.setAbsolutePosition(document.getPageSize().getWidth() - 495, 170);
//                canvas.saveState();
//                PdfGState state = new PdfGState();
//                state.setFillOpacity(0.2f);
//                canvas.setGState(state);
//                canvas.addImage(image);
//                canvas.restoreState();
//            } catch (BadElementException | IOException ex) {
//                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (DocumentException ex) {
//                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}
