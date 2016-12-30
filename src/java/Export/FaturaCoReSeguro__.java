/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import com.itextpdf.text.BaseColor;
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
import java.util.Date;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Contrato;
import validacao.OperacaoData;

/**
 *
 * @author ahmedjorge
 */
public class FaturaCoReSeguro__ {

    public String docSeguros(
            String nomeSeguro,
            String numApolice,
            String fundoContrato,
            String user,
            String moeda,
            String arquivo,
            String numeroRegistro
    ) {
        String reString;
        try {

            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.2f);
            Font fontLinha = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 0.000000358f);
            Font fontCabecalhoS = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.2f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontNull = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 4f);
            Font fontMenor = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f);
            Font fontMenorN = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f);

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{80, 20});
            PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres2 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres4 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);

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

            PdfPCell pCellPolice = new PdfPCell(new Phrase(ConfigDoc.Empresa.APOLICE + numApolice, fontCabecalhoN));
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
            PdfPCell linha = new PdfPCell(new Phrase(" ", fontLinha));
            linha.setBorderWidthTop(0.5f);
            linha.setBorderWidthBottom(0);
            linha.setBorderWidthLeft(0);
            linha.setBorderWidthRight(0);
            pTableLinha.addCell(linha);

            /**
             * Tile Doc
             */
            PdfPTable pTableTileDoc = new PdfPTable(new float[]{65f, 35f});

            PdfPCell cellTileDoc = new PdfPCell(new Phrase("FACTURA", fontCabecalhoS));
            cellTileDoc.setColspan(2);
            cellTileDoc.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellTileDoc.setBorder(PdfPCell.NO_BORDER);

            pTableTileDoc.addCell(cellTileDoc);

            Paragraph pNumFatura = new Paragraph();
            pNumFatura.add(new Phrase("Factura Nº ", fontMenorN));
            pNumFatura.add(new Phrase("0019528/16 ", fontMenor));
            cellTileDoc = new PdfPCell(pNumFatura);
            cellTileDoc.setColspan(2);
            cellTileDoc.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellTileDoc.setBorder(PdfPCell.NO_BORDER);
            cellTileDoc.setPaddingBottom(10f);

            pTableTileDoc.addCell(cellTileDoc);

            PdfPTable pTableCliente = new PdfPTable(new float[]{100});

            Paragraph pCliente = new Paragraph();
            pCliente.add(new Phrase("Nome: ", fontCorpo));
            pCliente.add(new Phrase(" ", fontCorpoN));
            PdfPCell cellCliente = new PdfPCell(pCliente);
            cellCliente.setBorder(PdfPCell.NO_BORDER);
            pTableCliente.addCell(cellCliente);

            pCliente = new Paragraph();
            pCliente.add(new Phrase("Morada: ", fontCorpo));
            pCliente.add(new Phrase(" ", fontCorpoN));
            cellCliente = new PdfPCell(pCliente);
            cellCliente.setBorder(PdfPCell.NO_BORDER);
            pTableCliente.addCell(cellCliente);

            pCliente = new Paragraph();
            pCliente.add(new Phrase("Localidade: ", fontCorpo));
            pCliente.add(new Phrase(" ", fontCorpoN));
            pCliente.add(new Phrase("  Área: ", fontCorpo));
            pCliente.add(new Phrase(" ", fontCorpoN));
            pCliente.add(new Phrase("  CP: ", fontCorpo));
            pCliente.add(new Phrase(" ", fontCorpoN));
            cellCliente = new PdfPCell(pCliente);
            cellCliente.setBorder(PdfPCell.NO_BORDER);
            pTableCliente.addCell(cellCliente);

            pCliente = new Paragraph();
            pCliente.add(new Phrase("Telefone: ", fontCorpo));
            pCliente.add(new Phrase(" ", fontCorpoN));
            cellCliente = new PdfPCell(pCliente);
            cellCliente.setBorder(PdfPCell.NO_BORDER);
            pTableCliente.addCell(cellCliente);

            cellCliente = new PdfPCell(pTableCliente);
            cellCliente.setBorder(PdfPCell.NO_BORDER);
            pTableTileDoc.addCell(cellCliente);

            PdfPTable pTableContrato = new PdfPTable(new float[]{40, 60});

            PdfPCell cellContrato = new PdfPCell(new Phrase("Data", fontCorpo));
            cellContrato.setBorder(PdfPCell.NO_BORDER);
            pTableContrato.addCell(cellContrato);

            cellContrato = new PdfPCell(new Phrase("09-12-2016", fontCorpo));
            cellContrato.setBorder(PdfPCell.NO_BORDER);
            pTableContrato.addCell(cellContrato);

            cellContrato = new PdfPCell(new Phrase("Vendedor", fontCorpo));
            cellContrato.setBorder(PdfPCell.NO_BORDER);
            pTableContrato.addCell(cellContrato);

            cellContrato = new PdfPCell(new Phrase("SAT ISURANCE", fontCorpoN));
            cellContrato.setBorder(PdfPCell.NO_BORDER);
            pTableContrato.addCell(cellContrato);

            cellContrato = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellContrato.setBorder(PdfPCell.NO_BORDER);
            pTableContrato.addCell(cellContrato);

            Paragraph pNIF = new Paragraph();
            pNIF.add(new Phrase("NIF: ", fontCorpoN));
            pNIF.add(new Phrase(" 517255068", fontCorpoN));
            cellContrato = new PdfPCell(pNIF);
            cellContrato.setBorder(PdfPCell.NO_BORDER);
            pTableContrato.addCell(cellContrato);

            cellContrato = new PdfPCell(pTableContrato);
            cellContrato.setBorder(PdfPCell.BOX);
            cellContrato.setPadding(5f);
            pTableTileDoc.addCell(cellContrato);

            PdfPTable pTableDados = new PdfPTable(new float[]{10, 50, 18, 22});

            PdfPCell cellDados = new PdfPCell(new Phrase("Qtde.", fontCorpoN));
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Descrição.", fontCorpoN));
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Preço unitário", fontCorpoN));
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Total", fontCorpoN));
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Qtde.", fontCorpo));
            cellDados.setRowspan(13);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Descrição.", fontCorpo));
            cellDados.setRowspan(13);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Preço unitário", fontCorpo));
            cellDados.setRowspan(13);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("Total", fontCorpo));
            cellDados.setBorderWidthTop(0);
            cellDados.setBorderWidthBottom(0);
            cellDados.setBorderWidthLeft(0);
            cellDados.setBorderWidthRight(0.5f);
            pTableDados.addCell(cellDados);

            for (int i = 0; i < 12; i++) {
                cellDados = new PdfPCell(new Phrase(" ", fontCorpo));
                cellDados.setBorderWidthTop(0f);
                if ((i != (12 - 1))) {
                    cellDados.setBorderWidthBottom(0f);
                }
                cellDados.setBorderWidthLeft(0f);
                cellDados.setBorderWidthRight(0.5f);
                pTableDados.addCell(cellDados);
            }
            
            cellDados = new PdfPCell(new Phrase("Total", fontCorpo));
            cellDados.setColspan(2);
            cellDados.setRowspan(8);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("SubTotal", fontCorpo));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("46.666.790,01 STD", fontCorpo));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("Acessórios", fontCorpo));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("1.000.000,00 STD", fontCorpo));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            
            cellDados = new PdfPCell(new Phrase("Imposto", fontCorpo));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("2.680.669,75 STD", fontCorpo));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            
            cellDados = new PdfPCell(new Phrase("FGA", fontCorpo));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("1.196.669,75 STD", fontCorpo));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            
            cellDados = new PdfPCell(new Phrase("TOTAL", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("51.744.000,00 STD", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableDados.addCell(cellDados);
            
            
            cellDados = new PdfPCell(new Phrase("Cambio", fontCorpo));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("24.500,00 STD", fontCorpo));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellDados.setBorder(PdfPCell.NO_BORDER);
            pTableDados.addCell(cellDados);
            
            
            cellDados = new PdfPCell(new Phrase("Total a pagar Euros", fontCorpo));
            cellDados.setBorderWidthRight(0);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("2.112,00 EUR", fontCorpo));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellDados.setBorderWidthLeft(0);
            pTableDados.addCell(cellDados);
            
            
            cellDados = new PdfPCell(new Phrase("Total a Pagar USD", fontCorpo));
            cellDados.setBorderWidthRight(0);
            pTableDados.addCell(cellDados);
            
            cellDados = new PdfPCell(new Phrase("2.282.78 USD", fontCorpo));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellDados.setBorderWidthLeft(0);
            pTableDados.addCell(cellDados);
            
            
            
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 70f, 5f);

            String f1 = (arquivo + "/" + user + "/Seguro " + nomeSeguro + "/");
            File f = new File(f1);
            String Ddata = sdf1.format(new Date());
            f.mkdirs();
            f = new File(f.getAbsoluteFile() + "/" + "Fatura CO-Re SEGURO " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro " + nomeSeguro + "/" + "Fatura CO-Re SEGURO " + Ddata + ".pdf";
            OutputStream outputStraem = new FileOutputStream(f);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableLinha);
            documento.add(pTableLinha);
            documento.add(pTableTileDoc);
            documento.add(pTableDados);
            documento.close();

            return reString;
        } catch (FileNotFoundException | DocumentException e) {
        } catch (IOException ex) {
            Logger.getLogger(DocNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public static void main(String[] args) {
        FaturaCoReSeguro__ dr = new FaturaCoReSeguro__();
        dr.docSeguros(
                "Automavel",
                "88",
                null,
                "ah",
                "STD",
                "./",
                "numregistro"
        );
    }

    double taxa = 0;

    private double valorCompra(Date data, Contrato c) {
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

    public String toString(Object o) {
        return (o == null) ? "" : o.toString();
    }
}
