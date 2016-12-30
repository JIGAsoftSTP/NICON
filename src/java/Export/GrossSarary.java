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
public class GrossSarary {

    static private ArrayList<DadosEstrutura> de;

    public static void criaDoc(String user) {
        OutputStream outputStraem;
        try {
            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoBP = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
            Font fontCorpoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 13f);
            Font fontCorpoTitile = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 11f);
            Font fontCabecalhoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Relatorio");
            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Gross Salary " + Ddata + ".pdf");
            outputStraem = new FileOutputStream(ff);

            String reString = "../Documentos/" + user + "/Relatorio/Gross Salary " + Ddata + ".pdf";

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{15f, 85f});
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
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            //Table Principal
            PdfPTable pTableTitile = new PdfPTable(new float[]{100});
            PdfPTable pTableTablePrincipal = new PdfPTable(new float[]{16.875f, 11.875f, 11.875f, 11.875f, 11.875f, 11.875f, 11.875f, 11.875f});
            pTableTablePrincipal.setWidthPercentage(100f);

            PdfPCell pCellTable = new PdfPCell(new Phrase("Categoria".toUpperCase(), fontCorpoTitile));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableTablePrincipal.addCell(pCellTable);

            //Table principal
            PdfPTable tableCell;
            for (int i = 1; i <= 7; i++) {
                tableCell = new PdfPTable(new float[]{100});

                pCellTable = new PdfPCell(new Phrase("NIVEL", fontCorpoTitile));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableCell.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(i + "", fontCorpoTitile));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tableCell.addCell(pCellTable);

                pTableTablePrincipal.addCell(tableCell);
            }

            de = getList();

            for (DadosEstrutura de1 : de) {
                pCellTable = new PdfPCell(new Phrase(de1.categoria, fontCorpoN));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                pTableTablePrincipal.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(de1.set1, fontCorpo));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableTablePrincipal.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(de1.set2, fontCorpo));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableTablePrincipal.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(de1.set3, fontCorpo));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableTablePrincipal.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(de1.set4, fontCorpo));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableTablePrincipal.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(de1.set5, fontCorpo));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableTablePrincipal.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(de1.set6, fontCorpo));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableTablePrincipal.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(de1.set7, fontCorpo));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                pTableTablePrincipal.addCell(pCellTable);
            }

            //Titulo de Table
            pCellTable = new PdfPCell(new Phrase("Estrutura de salário Grosso".toUpperCase(), fontCorpoNG));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setBorder(PdfPCell.NO_BORDER);
            pCellTable.setPadding(15f);
            pTableTitile.addCell(pCellTable);

            pCellTable = new PdfPCell(new Phrase("Mensal".toUpperCase(), fontCorpoNG));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setBorder(PdfPCell.NO_BORDER);
            pTableTitile.addCell(pCellTable);

            @SuppressWarnings("UnusedAssignment")
            PdfPTable pTableInf = null;
            PdfPTable pTableInfTile = new PdfPTable(new float[]{6, 18.5f, 18.5f, 18.5f, 18f, 20f});
            PdfPTable pTableInfReal = new PdfPTable(new float[]{100f});
            pTableInfTile.setWidthPercentage(95f);
            pTableInfReal.setWidthPercentage(95f);

            pTableInfTile.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            pTableInfReal.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);

            pCellTable = new PdfPCell(new Phrase("NIVEL", fontCorpoN));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableInfTile.addCell(pCellTable);

            pCellTable = new PdfPCell(new Phrase("SALÁRIO BASE", fontCorpoN));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableInfTile.addCell(pCellTable);

            pCellTable = new PdfPCell(new Phrase("SUBSIDIO ALMOÇO", fontCorpoN));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableInfTile.addCell(pCellTable);

            pCellTable = new PdfPCell(new Phrase("SUBSIDIO ALOGAMENTO", fontCorpoN));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableInfTile.addCell(pCellTable);

            pCellTable = new PdfPCell(new Phrase("SUBSIDIO TRANSPORTE", fontCorpoN));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableInfTile.addCell(pCellTable);

            pCellTable = new PdfPCell(new Phrase("TOTAL", fontCorpoN));
            pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableInfTile.addCell(pCellTable);

            HashMap<String, ArrayList<Categoria>> outherAr = GrossSarary.getCatList();
            int si = outherAr.size(), i = 0;
            for (Map.Entry<String, ArrayList<Categoria>> eSet : outherAr.entrySet()) {

                pTableInf = new PdfPTable(new float[]{6, 18.5f, 18.5f, 18.5f, 18f, 20f});
                pTableInf.setWidthPercentage(95f);
                pTableInf.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);

                pCellTable = new PdfPCell(new Phrase(eSet.getKey(), fontCorpoNG));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                pCellTable.setBorder(PdfPCell.NO_BORDER);
                pCellTable.setColspan(6);
                pTableInf.addCell(pCellTable);

                pCellTable = new PdfPCell(new Phrase(" ", fontCorpoN));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                pCellTable.setColspan(6);
                pCellTable.setBorder(PdfPCell.NO_BORDER);
                pTableInf.addCell(pCellTable);

                pCellTable = new PdfPCell(pTableInfTile);
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                pCellTable.setColspan(6);
                pCellTable.setBorder(PdfPCell.NO_BORDER);
                pTableInf.addCell(pCellTable);

                for (Categoria ca : eSet.getValue()) {
                    pCellTable = new PdfPCell(new Phrase(ca.nivel, fontCorpo));
                    pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    pTableInf.addCell(pCellTable);

                    pCellTable = new PdfPCell(new Phrase(toConverterToMoeda(ca.salarioBase), fontCorpo));
                    pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    pCellTable.setPaddingRight(10f);
                    pTableInf.addCell(pCellTable);

                    pCellTable = new PdfPCell(new Phrase(toConverterToMoeda(ca.subsidioAlmoco), fontCorpo));
                    pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    pCellTable.setPaddingRight(10f);
                    pTableInf.addCell(pCellTable);

                    pCellTable = new PdfPCell(new Phrase(toConverterToMoeda(ca.subsidioAlogamento), fontCorpo));
                    pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    pCellTable.setPaddingRight(10f);
                    pTableInf.addCell(pCellTable);

                    pCellTable = new PdfPCell(new Phrase(toConverterToMoeda(ca.subsidioTransporte), fontCorpo));
                    pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    pCellTable.setPaddingRight(10f);
                    pTableInf.addCell(pCellTable);

                    pCellTable = new PdfPCell(new Phrase(toConverterToMoeda(ca.total), fontCorpo));
                    pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    pCellTable.setPaddingRight(10f);
                    pTableInf.addCell(pCellTable);
                }

                pCellTable = new PdfPCell(new Phrase(" ", fontCorpoN));
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                pCellTable.setBorder(PdfPCell.NO_BORDER);
                pCellTable.setColspan(6);

                i++;
                if (si == i) {
                    pTableInf.addCell(pCellTable);
                    pTableInf.addCell(pCellTable);
                    pTableInf.addCell(pCellTable);
                }

                pCellTable = new PdfPCell(pTableInf);
                pCellTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pCellTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                pCellTable.setBorder(PdfPCell.NO_BORDER);
                pTableInfReal.addCell(pCellTable);

            }

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);
            documento.add(pTableTitile);
            documento.add(pTableNull);
            documento.add(pTableTablePrincipal);
//                documento.add(pTableNull);
            documento.newPage();
            documento.add(pTableInfReal);
            documento.close();

            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GrossSarary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(GrossSarary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GrossSarary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//SELECT * FROM TABLE (PACK_CONTA.functLoadEstrutura());

    private static ArrayList<DadosEstrutura> getList() {
//        CATEGORIA	NIVEL 1	NIVEL 2	NIVEL 3	NIVEL 4	NIVEL 5	NIVEL 6	NIVEL 7	TOTAL
        ResultSet rs = Call.callTableFunction("PACK_CONTA.functLoadEstrutura", "*");
        ArrayList<DadosEstrutura> al = new ArrayList<>();

        Consumer<HashMap<String, Object>> act = (map) -> {
            DadosEstrutura ed = new DadosEstrutura();
            ed.categoria = toString(map.get("CATEGORIA"));
            ed.set1 = (toString(map.get("NIVEL 1")));
            ed.set2 = (toString(map.get("NIVEL 2")));
            ed.set3 = (toString(map.get("NIVEL 3")));
            ed.set4 = (toString(map.get("NIVEL 4")));
            ed.set5 = (toString(map.get("NIVEL 5")));
            ed.set6 = (toString(map.get("NIVEL 6")));
            ed.set7 = (toString(map.get("NIVEL 7")));
            al.add(ed);
        };
        Call.forEchaResultSet(act, rs);
        return al;
    }

    private static HashMap<String, ArrayList<Categoria>> getCatList() {
//    Nome	NIVEL	Dias	salario base	Subsidio Alagamento	Subsidio lanche	subsidio Transporte	registo	total	BONUS ALMOCO	ESTADO
        ResultSet rs = Call.selectFrom("VER_CATEGORIA_FUNC", "*");
        HashMap<String, ArrayList<Categoria>> mapList = new HashMap<>();

        Consumer<HashMap<String, Object>> act = (map) -> {

            Categoria ca = new Categoria();
            ca.nivel = toString(map.get("NIVEL"));
            ca.salarioBase = (toString(map.get("salario base")));
            ca.subsidioAlmoco = (toString(map.get("Subsidio lanche")));
            ca.subsidioAlogamento = (toString(map.get("Subsidio Alagamento")));
            ca.subsidioTransporte = (toString(map.get("subsidio Transporte")));
            ca.total = (toString(map.get("total")));

            if (!mapList.containsKey(toString(map.get("Nome")))) {
                ArrayList<Categoria> al1 = new ArrayList();
                al1.add(ca);
                mapList.put(toString(map.get("Nome")), al1);
            } else {
                mapList.get(toString(map.get("Nome"))).add(ca);
            }
        };
        Call.forEchaResultSet(act, rs);
        return mapList;
    }

    private static String toString(Object o) {
        return ((o == null) ? " " : o.toString());
    }

    private static class Categoria {

        private String nivel;
        private String salarioBase;
        private String subsidioAlmoco;
        private String subsidioAlogamento;
        private String subsidioTransporte;
        private String total;
    }

    private static class DadosEstrutura {

        private String categoria;
        private String set1;
        private String set2;
        private String set3;
        private String set4;
        private String set5;
        private String set6;
        private String set7;

    }

    public static void main(String[] args) {
        GrossSarary.criaDoc("Ah");
    }

    private static String toConverterToMoeda(String s) {
        return ((s.isEmpty() || s.equals(" ")) ? " " : Moeda.format(Double.valueOf(s)) + " STD");
    }
}
