/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Veiculo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ahmedjorge
 */
public class ListaVeiculo {

    public static void criarDocPdf(List<Veiculo> ls, String user) {
        @SuppressWarnings("UnusedAssignment")
        OutputStream outputStraem = null;
        try {

            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoTable = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoTableN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoTitile = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 11.5f);
            Font fontCabecalhoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{15f, 85});
            PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);
            PdfPTable pTableNull = new PdfPTable(1);

            PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontCorpo));
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

            pTableEmpresaPricipal.setWidthPercentage(97);
            pTableEmpresaPricipal.addCell(cellTabela6);
            pTableEmpresaPricipal.addCell(cellTabela5);
            Document documento = new Document(PageSize.A4.rotate());

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Relatorio");
            ff.mkdirs();

            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Lista de Veiculos" + " " + Ddata + ".pdf");

            outputStraem = new FileOutputStream(ff);
            String reString = "../Documentos/" + user + "/Relatorio/" + "Lista de Veiculos" + " " + Ddata + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            BaseColor colorAzul = new BaseColor(176, 196, 222);
            BaseColor colorCinza = new BaseColor(240, 240, 240);
//            BaseColor colorAzulEscuro = new BaseColor(100, 149, 237);

            PdfPTable pTableSubTitile = new PdfPTable(new float[]{100});
            pTableSubTitile.setWidthPercentage(97f);

            PdfPCell cellSubTitile = new PdfPCell(new Paragraph("Lista de Veiculos", fontCorpoTitile));
            cellSubTitile.setBorder(PdfPCell.NO_BORDER);
            cellSubTitile.setPaddingTop(0f);
            cellSubTitile.setPaddingBottom(0f);
            pTableSubTitile.addCell(cellSubTitile);

            PdfPTable tableDados = new PdfPTable(new float[]{12.8f, 12.8f, 12.8f, 12.8f, 12.8f, 12.8f, 12.8f, 12.8f});
            tableDados.setWidthPercentage(97f);

            for (int i = 0; i < 8; i++) {
                PdfPCell cellTitileTable = new PdfPCell(new Phrase(getList(i), fontCorpoNG));
                cellTitileTable.setBorderWidth(1f);
                cellTitileTable.setPaddingTop(8f);
                cellTitileTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellTitileTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cellTitileTable.setBackgroundColor(colorAzul);
                cellTitileTable.setPaddingBottom(8f);
                tableDados.addCell(cellTitileTable);
            }

            PdfPCell dados;

            for (int i = 0; i < ls.size(); i++) {

                dados = new PdfPCell(new Phrase(ls.get(i).getNumeroMatricula(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

                dados = new PdfPCell(new Phrase(ls.get(i).getMarca(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

                dados = new PdfPCell(new Phrase(ls.get(i).getModelo(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

                dados = new PdfPCell(new Phrase(ls.get(i).getNumMotor(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

                dados = new PdfPCell(new Phrase(ls.get(i).getChassi(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

                dados = new PdfPCell(new Phrase(ls.get(i).getAnoFabrico(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

                dados = new PdfPCell(new Phrase(ls.get(i).getAnoCompra(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

                dados = new PdfPCell(new Phrase(ls.get(i).getCapacidade(), fontCorpoTable));
                dados.setPaddingTop(5f);
                dados.setPaddingBottom(5f);
                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                tableDados.addCell(dados);

            }

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableSubTitile);
            documento.add(pTableNull);
            documento.add(tableDados);
            documento.close();

            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");

        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(ListaVeiculo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListaVeiculo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getList(int i) {
        if (i == 0) {
            return "MATRICULA";
        } else if (i == 1) {
            return "MARCA";
        } else if (i == 2) {
            return "MODELO";
        } else if (i == 3) {
            return "Nº MOTOR";
        } else if (i == 4) {
            return "Nº CHASSI";
        } else if (i == 5) {
            return "ANO FRABRICO";
        } else if (i == 6) {
            return "ANO CAMPRA";
        } else {
            return "CAPACIDADE";
        }

    }

    public static void criarDocExcel(List<Veiculo> ls, String user) {
        try {

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Relatorio");
            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Lista de Veiculos" + " " + Ddata + ".xls");
            FileOutputStream outputStraem = new FileOutputStream(ff);
            String reString = "../Documentos/" + user + "/Relatorio/" + "Lista de Veiculos" + " " + Ddata + ".xls";

            Workbook wb = new HSSFWorkbook();

            org.apache.poi.ss.usermodel.Font fTitulo = wb.createFont();
            fTitulo.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
            fTitulo.setFontHeightInPoints((short) 22);

            org.apache.poi.ss.usermodel.Font fTituloP = wb.createFont();
            fTituloP.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
            fTituloP.setFontHeightInPoints((short) 15);
//            fTituloP.setStrikeout(true);
            fTituloP.setUnderline(org.apache.poi.ss.usermodel.Font.U_SINGLE);

            org.apache.poi.ss.usermodel.Font fTituloTabela = wb.createFont();
            fTituloTabela.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
            fTituloTabela.setFontHeightInPoints((short) 11);

            org.apache.poi.ss.usermodel.Font fCorpoTabela = wb.createFont();
            fCorpoTabela.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_NORMAL);
            fCorpoTabela.setFontHeightInPoints((short) 11.5);

            org.apache.poi.ss.usermodel.Font fRodapeTabela = wb.createFont();
            fRodapeTabela.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
            fRodapeTabela.setFontHeightInPoints((short) 11.5);

            org.apache.poi.ss.usermodel.Font fNormal = wb.createFont();
            fNormal.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
            fNormal.setFontHeightInPoints((short) 11);

            CellStyle csTitulo = wb.createCellStyle();
            csTitulo.setFont(fTitulo);
            csTitulo.setAlignment((short) 1);
            csTitulo.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
//            csTitulo.setWrapText(true);
            csTitulo.setBorderBottom((short) 0);
            csTitulo.setBorderTop((short) 0);
            csTitulo.setBorderRight((short) 0);
            csTitulo.setBorderLeft((short) 0);
//            csTitulo.setWrapText(true);

            CellStyle csTituloP = wb.createCellStyle();
            csTituloP.setFont(fTituloP);
            csTituloP.setAlignment((short) 1);
            csTituloP.setVerticalAlignment((short) 1);
//            csTituloP.setWrapText(true);
            csTituloP.setBorderBottom((short) 0);
            csTituloP.setBorderTop((short) 0);
            csTituloP.setBorderRight((short) 0);
            csTituloP.setBorderLeft((short) 0);
//            csTituloP.setWrapText(true);

            CellStyle csTituloT = wb.createCellStyle();
            csTituloT.setFont(fTituloP);
            csTituloT.setAlignment((short) 1);
            csTituloT.setVerticalAlignment((short) 1);
//            csTituloT.setWrapText(true);
            csTituloT.setBorderBottom((short) 0);
            csTituloT.setBorderTop((short) 0);
            csTituloT.setBorderRight((short) 0);
            csTituloT.setBorderLeft((short) 0);
//            csTituloT.setWrapText(true);

            CellStyle csTituloTabela = wb.createCellStyle();
            csTituloTabela.setFont(fTituloTabela);
            csTituloTabela.setAlignment(CellStyle.ALIGN_CENTER);
            csTituloTabela.setVerticalAlignment((short) 2);
            csTituloTabela.setBorderBottom((short) 2);
            csTituloTabela.setBorderTop((short) 2);
            csTituloTabela.setBorderRight((short) 2);
            csTituloTabela.setBorderLeft((short) 2);
//            csTituloTabela.setWrapText(true);

            CellStyle csCorpoTabela = wb.createCellStyle();
            csCorpoTabela.setFont(fCorpoTabela);
            csCorpoTabela.setAlignment((short) 2);
            csCorpoTabela.setVerticalAlignment((short) 1);
            csCorpoTabela.setBorderBottom((short) 1);
            csCorpoTabela.setBorderTop((short) 1);
            csCorpoTabela.setBorderRight((short) 1);
            csCorpoTabela.setBorderLeft((short) 1);
//            csCorpoTabela.setWrapText(true);

            CellStyle csCorpoTabelaR = wb.createCellStyle();
            csCorpoTabelaR.setFont(fCorpoTabela);
            csCorpoTabelaR.setAlignment(CellStyle.ALIGN_RIGHT);
            csCorpoTabelaR.setVerticalAlignment((short) 1);
            csCorpoTabelaR.setBorderBottom((short) 1);
            csCorpoTabelaR.setBorderTop((short) 1);
            csCorpoTabelaR.setBorderRight((short) 1);
            csCorpoTabelaR.setBorderLeft((short) 1);
//            csCorpoTabelaR.setWrapText(true);

            CellStyle csCorpoTabelaL = wb.createCellStyle();
            csCorpoTabelaL.setFont(fCorpoTabela);
            csCorpoTabelaL.setAlignment(CellStyle.ALIGN_LEFT);
            csCorpoTabelaL.setVerticalAlignment((short) 1);
            csCorpoTabelaL.setBorderBottom((short) 1);
            csCorpoTabelaL.setBorderTop((short) 1);
            csCorpoTabelaL.setBorderRight((short) 1);
            csCorpoTabelaL.setBorderLeft((short) 1);
//            csCorpoTabelaL.setWrapText(true);

            CellStyle csRodapeTabela = wb.createCellStyle();
            csRodapeTabela.setFont(fRodapeTabela);
            csRodapeTabela.setAlignment((short) 1);
            csRodapeTabela.setVerticalAlignment((short) 2);
            csRodapeTabela.setBorderBottom((short) 2);
            csRodapeTabela.setBorderTop((short) 2);
            csRodapeTabela.setBorderRight((short) 2);
            csRodapeTabela.setBorderLeft((short) 2);
//            csRodapeTabela.setWrapText(true);

            CellStyle csRodapeTabelaR = wb.createCellStyle();
            csRodapeTabelaR.setFont(fRodapeTabela);
            csRodapeTabelaR.setAlignment(CellStyle.ALIGN_RIGHT);
            csRodapeTabelaR.setVerticalAlignment((short) 2);
            csRodapeTabelaR.setBorderBottom((short) 2);
            csRodapeTabelaR.setBorderTop((short) 2);
            csRodapeTabelaR.setBorderRight((short) 2);
            csRodapeTabelaR.setBorderLeft((short) 2);
//            csRodapeTabelaR.setWrapText(true);

            CellStyle csNomal = wb.createCellStyle();
            csNomal.setFont(fCorpoTabela);
            csNomal.setAlignment((short) 1);
            csNomal.setVerticalAlignment((short) 1);
            csNomal.setBorderBottom((short) 0);
            csNomal.setBorderTop((short) 0);
            csNomal.setBorderRight((short) 0);
            csNomal.setBorderLeft((short) 0);
//            csNomal.setWrapText(true);

            Sheet s = wb.createSheet("Lista de Veiculos");

            short linha = 0;

            Row r = s.createRow(linha);
            Cell c = r.createCell(2);
            CreateCell(c, r, s, csTitulo, linha, linha + 3, ConfigDoc.Empresa.NOME, 1, 22);
            linha += 4;

            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.ENDERECO, 1, 22);
            linha++;

            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.CAIXAPOSTAL, 1, 22);
            linha++;

            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.TELEFAX + " " + ConfigDoc.Empresa.EMAIL, 1, 22);
            linha++;

            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.SOCIEDADE, 1, 22);
            linha += 3;

            r = s.createRow(linha);

            CreateCell(c, r, s, csTituloT, linha, linha, "Lista de Veiculos".toUpperCase(), 1, 22);
            linha += 2;

            csCorpoTabela.setFillBackgroundColor(HSSFColor.BLUE.index);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(0), 1, 3);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(1), 4, 6);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(2), 7, 9);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(3), 10, 12);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(4), 13, 15);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(5), 16, 18);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(6), 19, 21);
            CreateCell(c, r, s, csTituloTabela, linha, linha, getList(7), 22, 24);

            for (int i = 0; i < ls.size(); i++) {
                r = s.createRow(linha);
                csCorpoTabelaL.setFillBackgroundColor(((i % 2) == 0) ? HSSFColor.WHITE.index : HSSFColor.GREY_25_PERCENT.index);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getNumeroMatricula(), 1, 3);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getMarca(), 4, 6);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getModelo(), 7, 9);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getNumMotor(), 10, 12);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getChassi(), 13, 15);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getAnoFabrico(), 16, 18);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getAnoCompra(), 19, 21);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ls.get(i).getCapacidade(), 22, 24);

                linha++;
            }

            try (FileOutputStream out = new FileOutputStream(ff)) {
                wb.write(out);
            } catch (IOException ex) {
                Logger.getLogger(GenericExcel.class.getName()).log(Level.SEVERE, null, ex);
            }

            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");

        } catch (IOException ex) {
            Logger.getLogger(GenericExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void CreateCell(Cell c, Row r, Sheet s, CellStyle cs, int colinaI, int colinaF, String valorS, int linhaI, int linhaF) {

        c = r.createCell(linhaI);
        c.setCellStyle(cs);
        c.setCellValue(valorS);
        s.addMergedRegion(new CellRangeAddress(colinaI, colinaF, linhaI, linhaF));
        for (int e = (linhaI + 1); e <= linhaF; e++) {
            c = r.createCell(e);
            c.setCellStyle(cs);
        }
    }
}
