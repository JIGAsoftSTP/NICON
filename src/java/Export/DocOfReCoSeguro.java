/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.DataReseguro.DataEmpresa;
import Export.DataReseguro.DataResseguro;
import static Export.GenericPDFs.reString;
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
import conexao.Call;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.Contrato;
import org.primefaces.context.RequestContext;
import validacao.OperacaoData;

/**
 *
 * @author ahmedjorge
 */
public class DocOfReCoSeguro {

    public static void docSeguros(
            String nomeSeguro,
            String user,
            String arquivo,
            String numDebito,
            String tileDoc, int idReseguro,
            boolean isReseguroOfNICON
    ) {
        String reString;
        try {

            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.2f);
            Font fontLinha = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 0.000000358f);
            Font fontCabecalhoS = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.2f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoS = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f, Font.UNDERLINE);
            Font fontCorpoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontNull = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 4f);
            Font fontMenor = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 3f);

            DataResseguro reS = DataReseguro.getDadosReseguro(idReseguro);

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{80, 20});
            pTableEmpresaPricipal.setWidthPercentage(90);
            PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres2 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres4 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);

            PdfPTable pTableFatura = new PdfPTable(new float[]{80, 20});

            PdfPCell pCellNomeEmpresa = new PdfPCell(new Phrase(ConfigDoc.Empresa.NOME, fontCabecalhoN));
            pCellNomeEmpresa.setBorder(0);

            PdfPCell pCellNomeEndereco = new PdfPCell(new Phrase(ConfigDoc.Empresa.ENDERECO, fontCabecalhoN));
            pCellNomeEndereco.setBorder(0);

            PdfPCell pCellCaixaPostal = new PdfPCell(new Phrase(ConfigDoc.Empresa.CAIXAPOSTAL, fontCabecalhoN));
            pCellCaixaPostal.setBorder(0);

            PdfPCell pCellTeleFax = new PdfPCell(new Phrase(ConfigDoc.Empresa.TELEFAX + " " + ConfigDoc.Empresa.EMAIL, fontCabecalhoN));
            pCellTeleFax.setBorder(0);

            PdfPCell pCellSociedade = new PdfPCell(new Phrase(ConfigDoc.Empresa.SOCIEDADE, fontCabecalhoN));
            pCellSociedade.setBorder(0);

            PdfPCell pCellCapital = new PdfPCell(new Phrase(ConfigDoc.Empresa.CAPITALSOCIAL, fontCabecalhoN));
            pCellCapital.setBorder(0);

            PdfPCell pCellPolice = new PdfPCell(new Phrase(ConfigDoc.Empresa.APOLICE + reS.getAPOLICE(), fontCabecalhoN));
            pCellPolice.setBorder(0);

            Image imageEmpresa = Image.getInstance("logo.png");
            imageEmpresa.scaleToFit(120f, 100f);

            pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
            pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
            pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
            pTableEmpresaInforImpres1.addCell(pCellTeleFax);
            pTableEmpresaInforImpres1.addCell(pCellSociedade);

            pTableEmpresaInforImpres2.addCell(pCellCapital);
            pTableEmpresaInforImpres2.addCell(pCellPolice);

            PdfPCell cellTabela1 = new PdfPCell(pTableEmpresaInforImpres2);
            cellTabela1.setBorder(0);

            pTableEmpresaInforImpres4.addCell(cellTabela1);

            PdfPCell cellTabela3 = new PdfPCell(pTableEmpresaInforImpres1);
            cellTabela3.setBorder(0);

            pTableEmpresaInforImpres5.addCell(cellTabela3);

            PdfPCell cellTabela4 = new PdfPCell(pTableEmpresaInforImpres4);
            cellTabela4.setBorder(0);

            pTableEmpresaInforImpres5.addCell(cellTabela4);

            PdfPCell cellTabela5 = new PdfPCell(pTableEmpresaInforImpres5);
            cellTabela5.setBorder(0);

            pTableEmpresaPricipal.addCell(cellTabela5);

            PdfPCell cellTabela6 = new PdfPCell(imageEmpresa);
            cellTabela6.setBorder(0);
            cellTabela6.setHorizontalAlignment(Element.ALIGN_RIGHT);

            pTableEmpresaPricipal.addCell(cellTabela6);

            PdfPTable pTableLinha = new PdfPTable(1);
            pTableLinha.setWidthPercentage(90);
            PdfPCell linha = new PdfPCell(new Phrase(" ", fontLinha));
            linha.setBorderWidthTop(0.5f);
            linha.setBorderWidthBottom(0);
            linha.setBorderWidthLeft(0);
            linha.setBorderWidthRight(0);
            pTableLinha.addCell(linha);
            
            /**
             * Tile Doc start
             */
            /*
            PdfPTable pTableTileDoc = new PdfPTable(new float[]{50, 50});

            PdfPCell cellTileDoc = new PdfPCell(new Phrase(tileDoc, fontCabecalhoS));
            cellTileDoc.setColspan(2);
            cellTileDoc.setPaddingTop(10f);
            cellTileDoc.setPaddingBottom(20f);
            cellTileDoc.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellTileDoc.setBorder(PdfPCell.NO_BORDER);
            pTableTileDoc.addCell(cellTileDoc);

            Paragraph pTile = new Paragraph();
            pTile.add(new Phrase("DEBIT:  ", fontCorpoN));
            pTile.add(new Phrase("NiCON Seguros STP", fontCorpoN));
            PdfPCell cellDebit = new PdfPCell(pTile);
            cellDebit.setBorder(PdfPCell.NO_BORDER);
            pTableTileDoc.addCell(cellDebit);

            Paragraph pNotaNum = new Paragraph();
            pNotaNum.add(new Phrase("DEBIT NOTE NO: ", fontCorpoN));
            pNotaNum.add(new Phrase("05525", fontCorpo));
            cellDebit = new PdfPCell(pNotaNum);
            cellDebit.setPaddingLeft(20f);
            cellDebit.setBorder(PdfPCell.NO_BORDER);
            pTableTileDoc.addCell(cellDebit);

            cellDebit = new PdfPCell(new Phrase("             Avenida Marginal 12 de Junlho, 977", fontCorpo));
            cellDebit.setBorder(PdfPCell.NO_BORDER);
            pTableTileDoc.addCell(cellDebit);

            Paragraph pDate = new Paragraph();
            pDate.add(new Phrase("Date: ", fontCorpoN));
            SimpleDateFormat format = new SimpleDateFormat("dd MMMM',' yyyy",new Locale("pt", "BR"));
            pDate.add(new Phrase(format.format(new Date()), fontCorpo));
            cellDebit = new PdfPCell(pDate);
            cellDebit.setBorder(PdfPCell.NO_BORDER);
            cellDebit.setPaddingLeft(20f);
            pTableTileDoc.addCell(cellDebit);

            cellDebit = new PdfPCell(new Phrase("             Cx Postal 556-São Tomé", fontCorpo));
            cellDebit.setBorder(PdfPCell.NO_BORDER);
            pTableTileDoc.addCell(cellDebit);
            cellDebit = new PdfPCell(new Phrase(" "));
            cellDebit.setBorder(PdfPCell.NO_BORDER);
            pTableTileDoc.addCell(cellDebit);
            */
            /**
             * Tile Doc end
             */
            
            ArrayList<DataEmpresa> listaDataEmpresas = DataReseguro.getDadosEmpresa(idReseguro);
            
            /**
             * Data Parte 1 Start
             */
            PdfPTable pTableDataPart1 = new PdfPTable(new float[]{18f, 82f});
            pTableDataPart1.setWidthPercentage(90);

            PdfPCell cellDataPart1 = new PdfPCell(new Phrase("EMPRESA LIDER:", fontCorpoN));
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            cellDataPart1.setPaddingTop(10f);
            cellDataPart1.setPaddingBottom(5f);
            pTableDataPart1.addCell(cellDataPart1);
            cellDataPart1 = new PdfPCell(new Phrase(( (!isReseguroOfNICON) ? listaDataEmpresas.get(0).getEMPRESA().toUpperCase() :  ConfigDoc.Empresa.NOME), fontCorpo));
            cellDataPart1.setPaddingTop(10f);
            cellDataPart1.setPaddingBottom(5f);
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart1.addCell(cellDataPart1);

            cellDataPart1 = new PdfPCell(new Phrase("CONTRATO Nº:", fontCorpoN));
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            pTableDataPart1.addCell(cellDataPart1);
            cellDataPart1 = new PdfPCell(new Phrase(reS.getAPOLICE(), fontCorpo));
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart1.addCell(cellDataPart1);

            cellDataPart1 = new PdfPCell(new Phrase("DESCRIÇÃO:", fontCorpoN));
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart1.addCell(cellDataPart1);
            cellDataPart1 = new PdfPCell(new Phrase(reS.getDESCRICAO(), fontCorpo));
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart1.addCell(cellDataPart1);

            cellDataPart1 = new PdfPCell(new Phrase("PERIODO:", fontCorpoN));
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            pTableDataPart1.addCell(cellDataPart1);
            cellDataPart1 = new PdfPCell(new Phrase(reS.getINICIO() + " - " + reS.getFIM(), fontCorpo));
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            pTableDataPart1.addCell(cellDataPart1);

            cellDataPart1 = new PdfPCell(new Phrase("LIMITE:", fontCorpoN));
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            pTableDataPart1.addCell(cellDataPart1);
            cellDataPart1 = new PdfPCell(new Phrase( (covertDouble(reS.getLIMITE()) == null) ? reS.getLIMITE() :  Moeda.format(covertDouble(reS.getLIMITE())), fontCorpo));
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(5f);
            pTableDataPart1.addCell(cellDataPart1);

            cellDataPart1 = new PdfPCell(new Phrase("MOEDA:", fontCorpoN));
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(10f);
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart1.addCell(cellDataPart1);

            cellDataPart1 = new PdfPCell(new Phrase(reS.getMOEDA(), fontCorpo));
            cellDataPart1.setPaddingTop(5f);
            cellDataPart1.setPaddingBottom(10f);
            cellDataPart1.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart1.addCell(cellDataPart1);
            /**
             * Data Part 1 end
             */

            /**
             * Data Part 2 start
             */
            PdfPTable pTableDataPart2 = new PdfPTable(new float[]{33.333333333f, 33.333333333f, 33.333333333f});
            pTableDataPart2.setWidthPercentage(90);

            PdfPCell cellDataPart2 = new PdfPCell(new Phrase("PREMIO GROSSO:", fontCorpoN));
            cellDataPart2.setColspan(2);
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            cellDataPart2.setPaddingTop(10f);
            cellDataPart2.setPaddingBottom(5f);
            pTableDataPart2.addCell(cellDataPart2);
            cellDataPart2 = new PdfPCell(new Phrase(Moeda.format(Double.valueOf(reS.getPREMIOGROSSO().replace(',', '.'))) + " " + reS.getMOEDA(), fontCorpo));
            cellDataPart2.setPaddingTop(10f);
            cellDataPart2.setPaddingBottom(5f);
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart2.addCell(cellDataPart2);

            cellDataPart2 = new PdfPCell(new Phrase("DEDUÇÃO:", fontCorpoN));
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            cellDataPart2.setColspan(2);
            cellDataPart2.setPaddingTop(5f);
            cellDataPart2.setPaddingBottom(5f);
            pTableDataPart2.addCell(cellDataPart2);
            cellDataPart2 = new PdfPCell(new Phrase(reS.getDEDUCAO() + "%", fontCorpo));
            cellDataPart2.setPaddingTop(5f);
            cellDataPart2.setPaddingBottom(5f);
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart2.addCell(cellDataPart2);

            cellDataPart2 = new PdfPCell(new Phrase("LIQUIDO:", fontCorpoN));
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            cellDataPart2.setColspan(2);
            cellDataPart2.setPaddingTop(5f);
            cellDataPart2.setPaddingBottom(5f);
            pTableDataPart2.addCell(cellDataPart2);
            cellDataPart2 = new PdfPCell(new Phrase(Moeda.format(Double.valueOf(reS.getPREMIOGROSSO().replace(',', '.'))) + " " + reS.getMOEDA(), fontCorpo));
            cellDataPart2.setPaddingTop(5f);
            cellDataPart2.setPaddingBottom(5f);
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart2.addCell(cellDataPart2);

            cellDataPart2 = new PdfPCell(new Phrase("TOTAL:", fontCorpoN));
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            cellDataPart2.setPaddingTop(5f);
            cellDataPart2.setPaddingBottom(10f);
            pTableDataPart2.addCell(cellDataPart2);
            cellDataPart2 = new PdfPCell(new Phrase("100%", fontCorpo));
            cellDataPart2.setPaddingTop(5f);
            cellDataPart2.setPaddingBottom(10f);
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart2.addCell(cellDataPart2);
            cellDataPart2 = new PdfPCell(new Phrase(Moeda.format(Double.valueOf(reS.getTOTAL().replace(',', '.'))) + " " + reS.getMOEDA(), fontCorpo));
            cellDataPart2.setPaddingTop(5f);
            cellDataPart2.setPaddingBottom(10f);
            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
            pTableDataPart2.addCell(cellDataPart2);

//            cellDataPart2 = new PdfPCell(new Phrase("Your faithfully", fontCorpo));
//            cellDataPart2.setColspan(3);
//            cellDataPart2.setPaddingTop(5f);
//            cellDataPart2.setPaddingBottom(0.7f);
//            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
//            pTableDataPart2.addCell(cellDataPart2);
//            cellDataPart2 = new PdfPCell(new Phrase("THE UNITED AFRICAN INSURANCE BROKERS LTD", fontCorpo));
//            cellDataPart2.setColspan(3);
//            cellDataPart2.setPaddingTop(0.7f);
//            cellDataPart2.setPaddingBottom(10F);
//            cellDataPart2.setBorder(PdfPCell.NO_BORDER);
//            pTableDataPart2.addCell(cellDataPart2);
            /**
             * Data Part 2 end
             */
            PdfPTable pTableSecureter = new PdfPTable(new float[]{30f, 30f, 40f});

            PdfPCell cellSecureterNull = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellSecureterNull.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellSecureterNull.setPaddingTop(2f);
            cellSecureterNull.setPaddingBottom(2f);
            cellSecureterNull.setColspan(3);
            cellSecureterNull.setBorder(PdfPCell.NO_BORDER);
            pTableSecureter.addCell(cellSecureterNull);

            cellSecureterNull.setColspan(0);

            if (isReseguroOfNICON) {
                PdfPCell cellSecureter = new PdfPCell(new Phrase("RESSEGURADORA USADA", fontCorpoN));
                cellSecureter.setColspan(2);
                cellSecureter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cellSecureter.setPaddingTop(8f);
                cellSecureter.setPaddingBottom(8f);
                cellSecureter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                pTableSecureter.addCell(cellSecureter);
                pTableSecureter.addCell(cellSecureterNull);

                cellSecureter = new PdfPCell(new Phrase("RESSEGURA", fontCorpoN));
                cellSecureter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cellSecureter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellSecureter.setPaddingTop(5f);
                cellSecureter.setPaddingBottom(5);
                pTableSecureter.addCell(cellSecureter);
                cellSecureter = new PdfPCell(new Phrase("%PERCENTAGEM", fontCorpoN));
                cellSecureter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cellSecureter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellSecureter.setPaddingTop(5f);
                cellSecureter.setPaddingBottom(5);
                pTableSecureter.addCell(cellSecureter);
                pTableSecureter.addCell(cellSecureterNull);

                int total = listaDataEmpresas.size();
                for (int i = 0; (i < total); i++) {
                    cellSecureter = new PdfPCell(new Phrase(listaDataEmpresas.get(i).getEMPRESA(), fontCorpo));
                    cellSecureter.setPaddingTop(5f);
                    cellSecureter.setPaddingBottom(5);
                    cellSecureter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    pTableSecureter.addCell(cellSecureter);
                    cellSecureter = new PdfPCell(new Phrase(listaDataEmpresas.get(i).getPERCENTAGEM() + "% -- " + Moeda.format(((covertDouble(listaDataEmpresas.get(i).getPERCENTAGEM()) / 100) * covertDouble(reS.getPREMIOGROSSO()))) + " " + reS.getMOEDA(), fontCorpo));
                    cellSecureter.setPaddingTop(5f);
                    cellSecureter.setPaddingBottom(5);
                    cellSecureter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cellSecureter.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    pTableSecureter.addCell(cellSecureter);
                    pTableSecureter.addCell(cellSecureterNull);
                }
            }
            PdfPTable pTablePocessed = new PdfPTable(new float[]{100});
            PdfPCell cellPocessed = new PdfPCell(new Phrase("_____________________________", fontCorpoN));
            cellPocessed.setBorder(PdfPCell.NO_BORDER);
            cellPocessed.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
            cellPocessed.setPaddingTop(50f);
            cellPocessed.setPaddingBottom(1f);
            pTablePocessed.addCell(cellPocessed);
            cellPocessed = new PdfPCell(new Phrase("PROCESSED BY", fontCorpoN));
            cellPocessed.setPaddingTop(1f);
            cellPocessed.setVerticalAlignment(PdfPCell.ALIGN_TOP);
            cellPocessed.setBorder(PdfPCell.NO_BORDER);
            pTablePocessed.addCell(cellPocessed);

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 70f, 5f);

            String f1 = (arquivo + "/" + user + "/Seguro " + nomeSeguro + "/");
            File f = new File(f1);
            String Ddata = sdf1.format(new Date());
            f.mkdirs();
            f = new File(f.getAbsoluteFile() + "/" + "Doc Nota CO-ReSSEGURO " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro " + nomeSeguro + "/" + "Doc Nota CO-ReSSEGURO " + Ddata + ".pdf";
            OutputStream outputStraem = new FileOutputStream(f);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableLinha);
            documento.add(pTableLinha);
            /*
            documento.add(pTableTileDoc);
            documento.add(pTableLinha);
             */
            documento.add(pTableDataPart1);
            documento.add(pTableLinha);
            documento.add(pTableDataPart2);
            documento.add(pTableLinha);
            documento.add(pTableLinha);
            documento.add(pTableSecureter);
//            documento.add(pTablePocessed);
            pTablePocessed.setTotalWidth(200);
            pTablePocessed.writeSelectedRows(-1, 100, 100f, 175f, writer.getDirectContent());
            documento.close();
            
            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
            
        } catch (FileNotFoundException | DocumentException e) {
        } catch (IOException ex) {
            Logger.getLogger(DocNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        DocOfReCoSeguro.docSeguros(
                "Automavel",
                "Ah",
                "./",
                "numDebito",
                "RESSEGURO", 3, false
        );
    }

    double taxa = 0;

    public double valorCompra(Date data, Contrato c) {
        ResultSet rs;

        if (data == null) {
            rs = Call.callTableFunction("PACK_CONTA.getTaxaDay", "*", OperacaoData.toSQLDate(new Date()), c.getMoeda());
        } else {
            rs = Call.callTableFunction("PACK_CONTA.getTaxaDay", "*", OperacaoData.toSQLDate(new Date()), c.getMoeda());
        }

        Consumer<HashMap<String, Object>> act = (map) -> {
            taxa = Double.valueOf(toString(map.get("TX_VENDA")));
        };
        Call.forEchaResultSet(act, rs);

        return taxa;
    }

    private static String toString(Object o) {
        return (o == null) ? "" : o.toString();
    }

    private static Double covertDouble(String s) {
        return ((s == null || s.isEmpty()) ? null : Double.valueOf(s));
    }
}
