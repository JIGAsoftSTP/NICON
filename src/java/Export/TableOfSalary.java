/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import com.itextpdf.text.BadElementException;
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
import conexao.Call;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ahmedjorge
 */
public class TableOfSalary {

    private static ArrayList<Processo> psList = new ArrayList<>();
    private static HashMap<String, TotalBank> tbs = new LinkedHashMap<>();

    public static void ciarDoc(String user, String idProcesso, String data) {
        FileOutputStream outputStraem;
        try {
            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f);
            Font fontCorpoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f);
            Font fontCabecalhoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Relatorio");
            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Tabela de Salário " + Ddata + ".pdf");
            outputStraem = new FileOutputStream(ff);
            String reString = "../Documentos/" + user + "/Relatorio/Tabela de Salário " + Ddata + ".pdf";

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{7f, 93f});
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
            Document documento = new Document(PageSize.A3.rotate());

            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            PdfPTable pTableDados = new PdfPTable(new float[]{2.5f, 7.5f, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4});
            pTableDados.setWidthPercentage(105);

            /**
             * primeira linha de titulo inicio
             */
            PdfPCell cellDados;
            Paragraph pTitile = new Paragraph();

//            SimpleDateFormat sdfPT = new SimpleDateFormat("MMMM yyyy",new Locale("pt", "BR"));
            pTitile.add(new Paragraph(ConfigDoc.Empresa.NOME + "\n" + "TABELA SALÁRIO REFERENTE A " + data, fontCorpoN));

            cellDados = new PdfPCell(pTitile);
            cellDados.setColspan(10);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("F\nSOC. SEGU. 4%", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("G", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("H", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("I", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("IMPOSTO\n18% : 20%", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            for (int j = 0; j < 8; j++) {
                cellDados = new PdfPCell(new Phrase(" ", fontCorpo));
                pTableDados.addCell(cellDados);
            }

            cellDados = new PdfPCell(new Phrase("6\nSOC. SEGU.", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);
            /**
             * primeira linha de titulo Fim
             */

            /**
             * Segunda linha de titulo Inicio
             */
            for (int j = 0; j < 3; j++) {
                cellDados = new PdfPCell(new Phrase(" ", fontCorpo));
                pTableDados.addCell(cellDados);
            }

            cellDados = new PdfPCell(new Phrase("A", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("B", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("C", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("D", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("E", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("SUBSIDIO DE", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("VALOR A SER", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("F=(E*4%)", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("G=(E-F)", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("I=G+", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("J=", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableDados.addCell(cellDados);

            for (int j = 0; j < 4; j++) {
                cellDados = new PdfPCell(new Phrase(" ", fontCorpo));
                pTableDados.addCell(cellDados);
            }

            cellDados = new PdfPCell(new Phrase("SUBSIDIO DE", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("k=E-(F+G)", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("PYT", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("I", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("J", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableDados.addCell(cellDados);

            /**
             * Segunda linha de titulo Fim
             */
            /**
             * Terceira linha de titulo Inicio
             */
            for (int i = 0; i < 25; i++) {
                cellDados = new PdfPCell(new Phrase(getTitile(i), fontCorpoN));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cellDados.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                pTableDados.addCell(cellDados);
            }
            /**
             * Terceira linha de titulo FIM
             */
            ArrayList<Processo> ps = getListData(idProcesso);

            for (Processo p : ps) {
                cellDados = new PdfPCell(new Phrase(p.CODIGO1, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.NOME2, fontCorpo));
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.DIAS3, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.S_BASE4, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.S_ALOJAMENTO5, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.S_TRANSPORTE6, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.S_ALMOCO7, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.TT_SEM8, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.ALMOCOLIVREIMPOSTO9, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.TRIBUTADO10, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.SSFUNCIONARIO11, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.MENOS_SS_FUNCIONARIO12, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.COMISOES13, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.TOTAL_E_COMISAO14, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.IRS15, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.PARCELABATER16, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.IRSAPURADO17, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.SITUAFAMILIAR18, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.IRSLIQUIDO19, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.ALMOCO20, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.SALARIOLIQUIDO21, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.AVANCO22, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.NETOUT23, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.SSEMPRESA24, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(p.TOTAL25, fontCorpo));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableDados.addCell(cellDados);
            }

            PdfPTable pTableRodape = new PdfPTable(new float[]{33.333333333f, 33.333333333f, 33.333333333f});
            PdfPTable pTableSegurancaSocial = new PdfPTable(new float[]{35f, 21.666666667f, 21.666666667f, 21.666666667f});
            pTableSegurancaSocial.setWidthPercentage(75f);

            PdfPTable pTableImpostoSalario = new PdfPTable(new float[]{25f, 25f, 25f, 25f});
            pTableImpostoSalario.setWidthPercentage(75f);

            PdfPTable pTableNetSalaryPayble = new PdfPTable(new float[]{70f, 30f});
            pTableNetSalaryPayble.setWidthPercentage(75f);

            PdfPTable pTableAssinatura = new PdfPTable(new float[]{25f, 25f, 25f, 25f});

            /**
             * seguranção social inicio
             */
            cellDados = new PdfPCell(new Phrase("SEGURANÇA SOCIAL", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setPaddingTop(8.5f);
            pTableRodape.addCell(cellDados);
            /**
             * seguranção social Fim
             */

            /**
             * IMPOSTO DE SALÁRIO inicio
             */
            cellDados = new PdfPCell(new Phrase("IMPOSTO DE SALÁRIO", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setPaddingTop(8.5f);
            pTableRodape.addCell(cellDados);
            /**
             * IMPOSTO DE SALÁRIO Fim
             */

            /**
             * NET SALARY PAYABLE incial
             */
            cellDados = new PdfPCell(new Phrase("NET SALARY PAYABLE", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setPaddingTop(8.5f);
            pTableRodape.addCell(cellDados);
            /**
             * NET SALARY PAYABLE Fim
             */

            /**
             * seguranção social inicio
             */
            cellDados = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setBorder(PdfPCell.NO_BORDER);
            pTableSegurancaSocial.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("4%", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableSegurancaSocial.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("6%", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableSegurancaSocial.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("TOTAL", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableSegurancaSocial.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("TOTAL", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableSegurancaSocial.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(Moeda.format(Double.valueOf(psList.get(0).SSFUNCIONARIO11.replace(',', '.'))), fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pTableSegurancaSocial.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(Moeda.format(Double.valueOf(psList.get(0).SSEMPRESA24.replace(',', '.'))), fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableSegurancaSocial.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(Moeda.format((Double.valueOf(psList.get(0).SSFUNCIONARIO11.replace(',', '.')) + Double.valueOf(psList.get(0).SSEMPRESA24.replace(',', '.')))), fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableSegurancaSocial.addCell(cellDados);
            pTableSegurancaSocial.setComplete(true);

            cellDados = new PdfPCell(pTableSegurancaSocial);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setPadding(8.5f);
//            cellDados.setCalculatedHeight(100);
            pTableRodape.addCell(cellDados);
            /**
             * seguranção social Fim
             */

            /**
             * IMPOSTO DE SALÁRIO inicio
             */
            cellDados = new PdfPCell(new Phrase("TOTAL", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableImpostoSalario.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableImpostoSalario.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(" ", fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableImpostoSalario.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase(Moeda.format(Double.valueOf(psList.get(0).IRSLIQUIDO19.replace(',', '.'))), fontCorpoN));
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableImpostoSalario.addCell(cellDados);
            pTableImpostoSalario.setComplete(true);

            cellDados = new PdfPCell(pTableImpostoSalario);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//            cellDados.setCalculatedHeight(100);
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setPadding(8.5f);
            pTableRodape.addCell(cellDados);
            /**
             * IMPOSTO DE SALÁRIO Fim
             */

            /**
             * NET SALARY PAYABLE incial
             */
            for (Map.Entry<String, TotalBank> entrySet : tbs.entrySet()) {
                
                cellDados = new PdfPCell(new Phrase(((entrySet.getValue().nome.isEmpty()) ? "INDEFINIDO" : entrySet.getValue().nome), fontCorpoN));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                pTableNetSalaryPayble.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(Moeda.format(entrySet.getValue().value), fontCorpoN));
                cellDados.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableNetSalaryPayble.addCell(cellDados);

            }

            cellDados = new PdfPCell(pTableNetSalaryPayble);
            cellDados.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//            cellDados.setCalculatedHeight(100);
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setPadding(8.5f);
            pTableRodape.addCell(cellDados);
            /**
             * NET SALARY PAYABLE Fim
             */

            /**
             * Asssinatura inicio
             */
            cellDados = new PdfPCell(new Phrase("VISTO\nRESPONSÁVEL CONTABILIDADE", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            cellDados.setPaddingBottom(20.f);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("VISTO\nDIRETOR ADMINISTRATIVO & FINANCEIRO", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            cellDados.setPaddingBottom(20.f);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("VISTO\nDIRETOR GERAL", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            cellDados.setPaddingBottom(20.f);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("VISTO\nAUDITOR INTERNO", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            cellDados.setPaddingBottom(20.f);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("______________________________________________________", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            cellDados.setPaddingBottom(5.f);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("______________________________________________________", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("______________________________________________________", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(new Phrase("______________________________________________________", fontCorpoN));
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            pTableAssinatura.addCell(cellDados);

            cellDados = new PdfPCell(pTableAssinatura);
            cellDados.setBorder(PdfPCell.NO_BORDER);
            cellDados.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            cellDados.setColspan(3);
            pTableRodape.addCell(cellDados);
            /**
             * Asssinatura fim
             */

            cellDados = new PdfPCell(pTableRodape);
            cellDados.setColspan(25);
            pTableDados.addCell(cellDados);

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableDados);
            documento.close();
            
            psList = new ArrayList<>();
            tbs = new LinkedHashMap<>();
            
            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableOfSalary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException ex) {
            Logger.getLogger(TableOfSalary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(TableOfSalary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        TableOfSalary.ciarDoc("Ah", "1", "Agosto-2016");
    }

    public static String getTitile(int i) {
        if (i == 0) {
            return "STAFF\nNUM.";
        } else if (i == 1) {
            return "NOME";
        } else if (i == 2) {
            return "DIAS";
        } else if (i == 3) {
            return "SALÁRIO\nBASE";
        } else if (i == 4) {
            return "SUBSIDIO\nALOJA.";
        } else if (i == 5) {
            return "SUBSIDIO\nTRANS.";
        } else if (i == 6) {
            return "SUBSIDIO\nALMOCO";
        } else if (i == 7) {
            return "TOTAL\nSEM IMPOSTO";
        } else if (i == 8) {
            return "ALMOCO LIVRE DE IMP.";
        } else if (i == 9) {
            return "TRIBUTADO";
        } else if (i == 10) {
            return "4%\nSOC. SEGU.";
        } else if (i == 11) {
            return "TOTAL\nMENOS 4%";
        } else if (i == 12) {
            return "CONSUMO";
        } else if (i == 13) {
            return "TOTAL SAL. & COMM.";
        } else if (i == 14) {
            return "15%\n11% : 8%";
        } else if (i == 15) {
            return "PARCELA A ABATER";
        } else if (i == 16) {
            return "IRS APURADO";
        } else if (i == 17) {
            return "SITUAÇÃO\nFAMILIAR";
        } else if (i == 18) {
            return "IRS LIQUIDO\nA PAGAR.";
        } else if (i == 19) {
            return "ALMOÇO LIVRE DE IMP.";
        } else if (i == 20) {
            return "SALÁRIO\n LIQUIDO";
        } else if (i == 21) {
            return "AVANÇO AL/OUTRAS DE";
        } else if (i == 22) {
            return "NET OF I.O.U";
        } else if (i == 23) {
            return "6%\nSOCIAL SEG.";
        } else {
            return "TOTAL";
        }
    }

    private static ArrayList<Processo> getListData(String idProcesso) {
        psList = new ArrayList<>();
        ResultSet rs = Call.callTableFunction("PACK_REPORT2.reportSalario", "*", idProcesso);
        ArrayList<Processo> ps = new ArrayList<>();
        Consumer<HashMap<String, Object>> act = (map) -> {
            if (toString(map.get("STATUS")).equals("FORMAT")) {
                createAprocesso(map, ps);
            }
            if (toString(map.get("CODIGO")).equals("TOTAL") && !toString(map.get("STATUS")).equals("FORMAT")) {
                createAprocesso(map, psList);
            }
            if (!tbs.containsKey(toString(map.get("BANCO"))) && !toString(map.get("STATUS")).equals("FORMAT") && !toString(map.get("CODIGO")).equals("TOTAL")) {
                TotalBank tb = new TotalBank();
                tb.nome = toString(map.get("BANCO"));
                tb.value = Double.valueOf(toString(map.get("NET OUT")).replace(',', '.'));
                tbs.put(toString(map.get("BANCO")), tb);
            } else if (!toString(map.get("STATUS")).equals("FORMAT")) {
                tbs.get(toString(map.get("BANCO"))).value += Double.valueOf(toString(map.get("NET OUT")).replace(',', '.'));
            }
        };
        Call.forEchaResultSet(act, rs);

        TotalBank tb = new TotalBank();
        tb.nome = "TOTAL";
        tbs.entrySet().stream().forEach((entrySet) -> {
            tb.value += entrySet.getValue().value;
        });
        tbs.put(tb.nome, tb);
        return ps;
    }

    private static void createAprocesso(HashMap<String, Object> map, ArrayList<Processo> ps) {
        Processo p = new Processo();
        p.CODIGO1 = toString(map.get(p.CODIGO));
        p.NOME2 = toString(map.get(p.NOME));
        p.DIAS3 = toString(map.get(p.DIAS));
        p.S_BASE4 = toString(map.get(p.S_BASE));
        p.S_ALOJAMENTO5 = toString(map.get(p.S_ALOJAMENTO));
        p.S_TRANSPORTE6 = toString(map.get(p.S_TRANSPORTE));
        p.S_ALMOCO7 = toString(map.get(p.S_ALMOCO));
        p.TT_SEM8 = toString(map.get(p.TT_SEM));
        p.ALMOCOLIVREIMPOSTO9 = toString(map.get(p.ALMOCOLIVREIMPOSTO));
        p.TRIBUTADO10 = toString(map.get(p.TRIBUTADO));
        p.SSFUNCIONARIO11 = toString(map.get(p.SSFUNCIONARIO));
        p.MENOS_SS_FUNCIONARIO12 = toString(map.get(p.MENOS_SS_FUNCIONARIO));
        p.COMISOES13 = toString(map.get(p.COMISOES));
        p.TOTAL_E_COMISAO14 = toString(map.get(p.TOTAL_E_COMISAO));
        p.IRS15 = toString(map.get(p.IRS));
        p.PARCELABATER16 = toString(map.get(p.PARCELABATER));
        p.IRSAPURADO17 = toString(map.get(p.IRSAPURADO));
        p.SITUAFAMILIAR18 = toString(map.get(p.SITUAFAMILIAR));
        p.IRSLIQUIDO19 = toString(map.get(p.IRSLIQUIDO));
        p.ALMOCO20 = toString(map.get(p.ALMOCO));
        p.SALARIOLIQUIDO21 = toString(map.get(p.SALARIOLIQUIDO));
        p.AVANCO22 = toString(map.get(p.AVANCO));
        p.NETOUT23 = toString(map.get(p.NETOUT));
        p.TOTAL25 = toString(map.get(p.TOTAL));
        p.SSEMPRESA24 = toString(map.get(p.SSEMPRESA));
        ps.add(p);
    }

    private static class Processo {

        String ID = "ID",
                CODIGO = "CODIGO",
                NOME = "NOME",
                DIAS = "DIAS",
                S_BASE = "S.BASE",
                S_ALOJAMENTO = "S.ALOJAMENTO",
                S_TRANSPORTE = "S.TRANSPORTE",
                S_ALMOCO = "S.ALMOCO",
                TT_SEM = "TT.SEM IMPOSTO",
                ALMOCOLIVREIMPOSTO = "ALMOCO LIVRE IMPOSTO",
                TRIBUTADO = "TRIBUTADO",
                SSFUNCIONARIO = "SS FUNCIONARIO",
                MENOS_SS_FUNCIONARIO = "MENOS SS FUNCIONARIO",
                COMISOES = "COMISOES",
                TOTAL_E_COMISAO = "TOTAL E COMISAO",
                IRS = "IRS",
                PARCELABATER = "PARCELA BATER",
                IRSAPURADO = "IRS APURADO",
                SITUAFAMILIAR = "SITUA FAMILIAR",
                IRSLIQUIDO = "IRS LIQUIDO",
                ALMOCO = "ALMOCO",
                SALARIOLIQUIDO = "SALARIO LIQUIDO",
                AVANCO = "AVANCO",
                NETOUT = "NET OUT",
                SSEMPRESA = "SS EMPRESA",
                TOTAL = "TOTAL",
                STATUS = "STATUS";
        String ID0;
        String CODIGO1;
        String NOME2;
        String DIAS3;
        String S_BASE4;
        String S_ALOJAMENTO5;
        String S_TRANSPORTE6;
        String S_ALMOCO7;
        String TT_SEM8;
        String ALMOCOLIVREIMPOSTO9;
        String TRIBUTADO10;
        String SSFUNCIONARIO11;
        String MENOS_SS_FUNCIONARIO12;
        String COMISOES13;
        String TOTAL_E_COMISAO14;
        String IRS15;
        String PARCELABATER16;
        String IRSAPURADO17;
        String SITUAFAMILIAR18;
        String IRSLIQUIDO19;
        String ALMOCO20;
        String SALARIOLIQUIDO21;
        String AVANCO22;
        String NETOUT23;
        String SSEMPRESA24;
        String TOTAL25;
    }

    public static String toString(Object o) {
//        System.err.println(o);
        return ((o != null) ? o.toString() : "");
    }

    private static class TotalBank {

        private String nome;
        private Double value = 0.0;

        @Override
        public String toString() {
            return "TotalBank{" + "nome=" + nome + ", value=" + value + '}';
        }

    }

}
