/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import bean.DataTableControl;
import com.itextpdf.text.BadElementException;
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
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.ComoBox;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AhmedJorge
 */
public class GenericPDFs {

    static String reString;
    public static ArrayList<Object[]> list;
    public static HashMap<String, ArrayList<Object[]>> map;
    public static HashMap<String, ArrayList<Object[]>> mapTotal;
    static int i = 0;
    static float[] ar = new float[0];
    private static boolean cabe = true;
    static Font fontNump = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
    static String nomeDocR;
    public static boolean no = false;
    public static String nomeNo;
    public static Date dI;
    public static Date dF;
    public static int[] removeItem = new int[]{};
    public static HashMap<Integer, String> renameItem = new HashMap<>();
    public static int paramFilterOculta = -1;
    public static HashMap<Integer, Alignment> alignment = new HashMap<>();
    private static String[] valoresTotal = new String[]{};
    public static int[] arrValoresTotal = new int[]{};
    public static Object[] lista_titulo_table = new String[]{};

    /**
     *
     * @param user
     * @param nomeDoc
     * @param titleDoc
     * @param rs
     * @param op
     * @param paramFilter
     * @return
     */
    public static String createDoc(String user, String nomeDoc, String titleDoc, DataTableControl rs, OrientacaoPagina op, int paramFilter) {
        nomeDocR = nomeDoc;
        try {

            Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f);
            Font fontCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoTable = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7.5f);
            Font fontCorpoTableN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 7.8f);
            Font fontCorpoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoTitile = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 11.5f);
            Font fontCabecalhoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            getMap(rs, paramFilter);
            ar = createPerncetage(list, paramFilter);
            PdfPTable tableDados = new PdfPTable(ar);

            Document documento = new Document();

            i = 0;
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Relatorio");

            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + nomeDoc + " " + Ddata + ".pdf");
            OutputStream outputStraem = new FileOutputStream(ff);

            reString = "../Documentos/" + user + "/Relatorio/" + nomeDoc + " " + Ddata + ".pdf";

            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            MyFooter event = new MyFooter();
            writer.setPageEvent(event);

            if (op == OrientacaoPagina.HORIZONTAL) {
                documento.setPageSize(PageSize.A4.rotate());
            } else {
                documento.setPageSize(PageSize.A4);
            }

            documento.setMargins(10f, 10f, 35f, 35f);

            PdfPTable pTableEmpresaPricipal = new PdfPTable((OrientacaoPagina.HORIZONTAL == op) ? new float[]{14f, 86f} : new float[]{18.5f, 82.5f});
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

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);

            PdfPTable pTableTitile = new PdfPTable(1);
            SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            PdfPCell cellTitile = new PdfPCell(new Phrase(titleDoc.toUpperCase() + ((dF != null && dI != null) ? (" de " + format.format(dI) + " à " + format.format(dF)).toUpperCase() : ""), fontCorpoTitile));
            cellTitile.setPaddingBottom(20f);
            cellTitile.setBorder(0);
            cellTitile.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableTitile.addCell(cellTitile);
            documento.add(pTableTitile);

            tableDados.setWidthPercentage(97f);
            PdfPTable tableTitile = new PdfPTable(ar);
            tableTitile.setWidthPercentage(97f);

            if (paramFilter < 0) {
                for (Object[] emap : list) {
                    tableDados = new PdfPTable(ar);
                    tableDados.setWidthPercentage(97f);
                    if (emap[0] == null || !emap[0].equals("TOTAL")) {
                        for (int j = 0; j < emap.length; j++) {
                            if (cabe && i == 0) {
                                lista_titulo_table = emap;
                                PdfPCell cellTitileTable = new PdfPCell(new Phrase(toString(emap[j]).toUpperCase(), fontCorpoNG));
                                cellTitileTable.setBorderWidth(1f);
                                cellTitileTable.setPaddingTop(8f);
                                cellTitileTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                                cellTitileTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                                BaseColor colorAzul = new BaseColor(255, 255, 255);
                                cellTitileTable.setBackgroundColor(colorAzul);
                                cellTitileTable.setPaddingBottom(8f);
                                tableTitile.addCell(cellTitileTable);
                            } else {
                                PdfPCell dados = new PdfPCell(new Phrase(toString(emap[j]), fontCorpoTable));
                                dados.setHorizontalAlignment((alignment.containsKey(j)) ? alignment.get(j).i : PdfPCell.ALIGN_LEFT);
                                dados.setPaddingTop(5f);
                                dados.setPaddingBottom(5f);
                                BaseColor colorCinza = new BaseColor(255, 255, 255);
                                dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                                tableDados.addCell(dados);
                            }
                        }
                        i++;
                        cabe = false;
                    }

                    if (i == 1) {
                        documento.add(tableTitile);
                    }

                    documento.add(tableDados);

                }

                PdfPTable tableTotal = new PdfPTable(ar);
                tableTotal.setWidthPercentage(97f);

                mapTotal.entrySet().stream().forEach((entrySet) -> {
                    mapTotal.get(entrySet.getKey()).stream().forEach((get) -> {
                        for (int g = 0; g < get.length; g++) {
                            if (g != paramFilter) {
                                PdfPCell dados = new PdfPCell(new Phrase(((g == 0) ? "TOTAL" : toString(get[g])), fontCorpoTableN));
                                dados.setHorizontalAlignment((alignment.containsKey(g)) ? alignment.get(g).i : PdfPCell.ALIGN_LEFT);
                                dados.setPaddingTop(5f);
                                dados.setPaddingBottom(5f);
                                BaseColor colorCinza = new BaseColor(255, 255, 255);
                                dados.setBackgroundColor(colorCinza);
                                tableTotal.addCell(dados);
                            }
                        }
                    });
                });
                documento.add(tableTotal);
            } else {
                int t = 0;
                for (Map.Entry<String, ArrayList<Object[]>> lista : map.entrySet()) {
                    i = 0;
                    cabe = true;

                    PdfPTable pTableSubTitile = new PdfPTable(new float[]{100});
                    pTableSubTitile.setWidthPercentage(97f);

                    PdfPCell cellSubTitile = new PdfPCell(new Paragraph(lista.getKey().toUpperCase(), fontCorpoTitile));
                    cellSubTitile.setBorder(PdfPCell.NO_BORDER);
                    cellSubTitile.setPaddingTop(0f);
                    cellSubTitile.setPaddingBottom(0f);
                    pTableSubTitile.addCell(cellSubTitile);
                    documento.add(pTableSubTitile);

                    documento.add(new Paragraph(" "));

                    for (Object[] emap : lista.getValue()) {
                        tableDados = new PdfPTable(ar);
                        tableTitile = new PdfPTable(ar);
                        tableDados.setWidthPercentage(97f);
                        tableTitile.setWidthPercentage(97f);
                        int k = 0;
                        for (int j = 0; j < emap.length; j++) {
                            if (k != paramFilterOculta) {
                                if (cabe && i == 0) {
                                    lista_titulo_table = emap;
                                    PdfPCell cellTitileTable = new PdfPCell(new Phrase(toString(emap[j]).toUpperCase(), fontCorpoNG));
                                    cellTitileTable.setBorderWidth(1f);
                                    cellTitileTable.setPaddingTop(8f);
                                    cellTitileTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                                    cellTitileTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                                    BaseColor colorAzul = new BaseColor(255, 255, 255);
                                    cellTitileTable.setBackgroundColor(colorAzul);
                                    cellTitileTable.setPaddingBottom(8f);
                                    tableTitile.addCell(cellTitileTable);
                                } else {
                                    PdfPCell dados = new PdfPCell(new Phrase(toString(emap[j]), fontCorpoTable));
                                    dados.setHorizontalAlignment((alignment.containsKey(j)) ? alignment.get(j).i : PdfPCell.ALIGN_LEFT);
                                    dados.setPaddingTop(5f);
                                    dados.setPaddingBottom(5f);
                                    BaseColor colorCinza = new BaseColor(255, 255, 255);
                                    dados.setBackgroundColor(((i % 2 != 0) ? colorCinza : null));
                                    tableDados.addCell(dados);
                                }
                            }
                            k++;
                        }
                        i++;

                        cabe = false;
                        if (i == 1) {
                            documento.add(tableTitile);
                        }

                        documento.add(tableDados);

                    }
                    PdfPTable tableTotal = new PdfPTable(ar);
                    tableTotal.setWidthPercentage(97f);
                    if (mapTotal.containsKey(lista.getKey())) {
                        mapTotal.get(lista.getKey()).stream().forEach((get) -> {
                            for (int g = 0; g < get.length; g++) {
                                if (g != paramFilterOculta) {
                                    PdfPCell dados = new PdfPCell(new Phrase(((g == 0) ? "TOTAL" : toString(get[g])), fontCorpoTableN));
                                    dados.setHorizontalAlignment((alignment.containsKey(g)) ? alignment.get(g).i : PdfPCell.ALIGN_LEFT);
                                    dados.setPaddingTop(5f);
                                    dados.setPaddingBottom(5f);
                                    BaseColor colorCinza = new BaseColor(255, 255, 255);
                                    dados.setBackgroundColor(colorCinza);
                                    tableTotal.addCell(dados);
                                }
                            }
                        });
                    }
                    documento.add(tableTotal);
                    t++;

                    documento.add(new Paragraph(" "));
                    documento.add(new Paragraph(" "));
                    documento.add(new Paragraph(" "));

                    if (t == map.size() && paramFilter > -1) {
                        PdfPTable tableTotal_t = new PdfPTable(ar);
                        tableTotal_t.setWidthPercentage(97f);
                        for (int j = 0; j < lista_titulo_table.length; j++) {
                            if (j != paramFilterOculta) {
                                PdfPCell cellTitileTable = new PdfPCell(new Phrase(toString(lista_titulo_table[j]).toUpperCase(), fontCorpoNG));
                                cellTitileTable.setBorderWidth(1f);
                                cellTitileTable.setPaddingTop(8f);
                                cellTitileTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                                cellTitileTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                                BaseColor colorAzul = new BaseColor(255, 255, 255);
                                cellTitileTable.setBackgroundColor(colorAzul);
                                cellTitileTable.setPaddingBottom(8f);
                                tableTotal_t.addCell(cellTitileTable);
                            }
                        }
                        
                        Double[] total_total = new Double[lista_titulo_table.length];
                        mapTotal.entrySet().stream().forEach((entrySet) -> {
                            mapTotal.get(entrySet.getKey()).stream().forEach((value) -> {
                                for (int g = 0; g < value.length; g++) {
                                    if (!entrySet.getKey().equals("TOTAL")) {
                                        if (g != paramFilterOculta) {
                                            if(Moeda.unFormat(toString(value[g]).replaceAll(" ", "").replaceAll(",", ".").replaceAll("STD", "")) != -1){
                                                double v = ((total_total[g] == null) ? 0.0 : total_total[g]);
                                                total_total[g] =  (Moeda.unFormat(toString(value[g]).replaceAll(" ", "").replaceAll(",", ".").replaceAll("STD", ""))  + v);
                                            }
                                            PdfPCell dados = new PdfPCell(new Phrase(((g == 0) ? "TOTAL " + entrySet.getKey().toUpperCase() : toString(value[g])), fontCorpoTableN));
                                            dados.setHorizontalAlignment((alignment.containsKey(g)) ? alignment.get(g).i : PdfPCell.ALIGN_LEFT);
                                            dados.setPaddingTop(5f);
                                            dados.setPaddingBottom(5f);
                                            BaseColor colorCinza = new BaseColor(255, 255, 255);
                                            dados.setBackgroundColor(colorCinza);
                                            tableTotal_t.addCell(dados);
                                        }
                                    }
                                }
                            });
                        });
                        for (int j = 0; j < total_total.length; j++) {
                            if (j != paramFilterOculta) {
                                PdfPCell cellTitileTable = new PdfPCell(new Phrase((total_total[j] != null) ? Moeda.format(total_total[j]) : " ", fontCorpoNG));
                                cellTitileTable.setPaddingTop(5f);
                                cellTitileTable.setHorizontalAlignment((alignment.containsKey(j)) ? alignment.get(j).i : PdfPCell.ALIGN_LEFT);
                                BaseColor colorAzul = new BaseColor(255, 255, 255);
                                cellTitileTable.setBackgroundColor(colorAzul);
                                cellTitileTable.setPaddingBottom(5f);
                                tableTotal_t.addCell(cellTitileTable);
                            }
                        }
                        documento.add(tableTotal_t);
                    }
                }
            }
            documento.close();
            no = false;
            nomeNo = "";
            dI = null;
            dF = null;
            paramFilterOculta = -1;
            removeItem = new int[]{};
            renameItem = new HashMap<>();
            alignment = new HashMap<>();
            arrValoresTotal = new int[]{};
            valoresTotal = new String[]{};
            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
            return reString;
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(GenericPDFs.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (DocumentException ex) {
            Logger.getLogger(GenericPDFs.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public static void getMap(DataTableControl rs, int paramFilter) {
        list = new ArrayList<>();
        ArrayList<String> titile = new ArrayList<>();
        rs.onReciveHeader((ArrayList<ComoBox> values) -> {
            values.stream().forEach((c) -> {
                titile.add(c.getValue());
            });
        });

        for (int j = 0; j < titile.size(); j++) {
            titile.set(j, ((renameItem.containsKey(j)) ? renameItem.get(j) : titile.get(j)));
        }

        list.add(titile.toArray());

        rs.onReciveDatas((values) -> {
            list.add(values);
        });

        if (arrValoresTotal.length > 0) {
            calTotalValores(list);
        }

        if (paramFilter > -1) {
            map = new LinkedHashMap<>();
            for (int j = 1; j < list.size(); j++) {
                if ((!no && !(list.get(j)[paramFilter] + "").equals(nomeNo) && !(list.get(j)[0] + "").trim().toUpperCase().equals("TOTAL"))
                        || (no && !(list.get(j)[paramFilter] + "").equals(nomeNo) && !(list.get(j)[0] + "").trim().toUpperCase().equals("TOTAL"))) {
                    if (map.containsKey(list.get(j)[paramFilter] + "")) {
                        map.get((list.get(j)[paramFilter]) + "").add(list.get(j));
                    } else if (list.get(j)[paramFilter] != null) {
                        ArrayList<Object[]> al = new ArrayList<>();
                        al.add(list.get(0));
                        al.add(list.get(j));
                        map.put(list.get(j)[paramFilter] + "", al);
                    }
                }
            }
        }

        mapTotal = new LinkedHashMap<>();
        for (int j = 1; j < list.size(); j++) {
            if (((list.get(j)[0] + "").toUpperCase().equals("TOTAL") || (list.get(j)[0] + "").toUpperCase().contains("TOTAL")) && list.get(0).length > 1) {
                ArrayList<Object[]> al = new ArrayList<>();
                al.add(list.get(j));
                mapTotal.put((list.get(j)[1] + ""), al);
            }
        }
        

        if (paramFilter > -1) {
            map.entrySet().stream().forEach((entrySet) -> {
                String key = entrySet.getKey();
                ArrayList<Object[]> value = entrySet.getValue();
                map.replace(key, removeParamm(value));
            });
        }

        mapTotal.entrySet().stream().forEach((entrySet) -> {
            String key = entrySet.getKey();
            ArrayList<Object[]> value = entrySet.getValue();
            mapTotal.replace(key, removeParamm(value));
        });

        list = removeParamm(list);
    }

    public static enum OrientacaoPagina {
        VERTICAL, HORIZONTAL;
    }

    public static enum Alignment {
        LEFT(PdfPCell.ALIGN_LEFT), CENTER(PdfPCell.ALIGN_CENTER), RIGHT(PdfPCell.ALIGN_RIGHT);
        public final int i;

        Alignment(int i) {
            this.i = i;
        }
    }

    public static enum TypeParam {

        OCULTAR, MOSTRAL
    }
    static int somaTotal = 0;

    public static float[] createPerncetage(ArrayList<Object[]> lista, int paramFilter) {

        if (lista == null || lista.isEmpty()) {
            return null;
        }
        float[] perncetages = new float[((paramFilter == -1) ? lista.get(0).length : lista.get(0).length - 1)];
        int[] lenthMax = new int[((paramFilter == -1) ? lista.get(0).length : lista.get(0).length - 1)];

        lista.stream().forEach((linha) -> {
            int soma = 0, j = 0, ii = 0;
            for (Object emap : linha) {
                if (paramFilter != ii) {
                    if (lenthMax[j] < toString(emap).length()) {
                        soma = soma - lenthMax[j];
                        lenthMax[j] = toString(emap).length();
                        somaTotal = somaTotal + lenthMax[j];
                    }
                    j++;
                }
                ii++;
            }

        });
        for (int g = 0; g < perncetages.length; g++) {
            perncetages[g] = (((((float) ((float) lenthMax[g] * 100) / somaTotal) > 25) ? (((float) ((float) lenthMax[g] * 100) / somaTotal) - 24)
                    : (((float) ((float) lenthMax[g] * 100) / somaTotal) > 15)
                            ? (((float) ((float) lenthMax[g] * 100) / somaTotal) - 9)
                            : ((((float) ((float) lenthMax[g] * 100) / somaTotal) > 7)
                                    ? (((float) ((float) lenthMax[g] * 100) / somaTotal) - 1.5f)
                                    : ((((float) ((float) lenthMax[g] * 100) / somaTotal) < 4)
                                            ? (((float) ((float) lenthMax[g] * 100) / somaTotal) + 1)
                                            : ((float) ((float) lenthMax[g] * 100) / somaTotal)))));
        }
        somaTotal = 0;

        return perncetages;
    }

    public static String toString(Object o) {
        return (o == null) ? "" : o.toString();
    }

    public static void main(String[] args) {
    }

    public static boolean sheachInParam(String key, int i, String... param) {
        for (String string : param) {
            if (string.equals(key)) {
                return (i == 0) ? (true) : (!true);
            }
        }
        return (i == 0) ? (!true) : true;
    }

    static class MyFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            cabe = true;
            PdfPTable pTableNumPage = new PdfPTable(1);
            pTableNumPage.setTotalWidth(80f);
            PdfPCell pCellImagem = new PdfPCell(new Phrase("Pag Nº: ".toUpperCase() + document.getPageNumber(), fontNump));
            pCellImagem.setRotation(270);
            pCellImagem.setBorder(0);
            pTableNumPage.addCell(pCellImagem);

            pTableNumPage.writeSelectedRows(-1, 2, 761f, 80.5f, writer.getDirectContent());
        }
    }

    private static ArrayList<Object[]> removeParamm(ArrayList<Object[]> al) {
        boolean b;
        Object[] newList;
        ArrayList<Object[]> newArrayList = new ArrayList<>();
        for (Object[] listActal : al) {
            int h = 0;
            newList = new Object[al.get(0).length - removeItem.length];
            for (int ii = 0; ii < listActal.length; ii++) {
                b = true;
                for (int j = 0; j < removeItem.length; j++) {
                    if (ii == removeItem[j]) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    newList[h] = listActal[ii];
                    h++;
                }
            }
            newArrayList.add(newList);
        }
        return newArrayList;
    }

    private static void calTotalValores(ArrayList<Object[]> list) {
        Double valor;
        valoresTotal = new String[list.get(0).length];
        for (Object[] value : list) {
            for (int j = 0; j < value.length; j++) {
                for (int k = 0; k < arrValoresTotal.length; k++) {
                    if (j == arrValoresTotal[k]) {
                        System.err.println(value[j]);
                        valor = ((valoresTotal[j] == null) ? 0.0 : Moeda.unFormat(valoresTotal[j].replaceAll(" ", "").replaceAll(",", ".").replaceAll("STD", "")));
                        valor += Moeda.unFormat(toString(value[j]).replaceAll(" ", "").replaceAll(",", ".").replaceAll("STD", ""));
                        valoresTotal[j] = Moeda.format(valor);
                    }
                }
            }
        }

        if (!Arrays.toString(arrValoresTotal).contains("0")) {
            valoresTotal[0] = "TOTAL";
        }

        list.add(valoresTotal);
    }
}
