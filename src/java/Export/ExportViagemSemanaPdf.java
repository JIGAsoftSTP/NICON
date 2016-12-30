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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexao.Call;
import dao.ViagemDao;
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
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ahmedjorge
 */
public class ExportViagemSemanaPdf {
    
    static final String DATA="DATA", APOLICE = "APOLICE", INICIO = "INICIO", FIM ="FIM", DIAS = "DIAS", CLIENTE = "CLIENTE", RECIBO= "RECIBO", PREMIO = "PREMIO",
   CONSUMO = "CONSUMO", SELO = "SELO", NETOUT ="NET OUT", TOTAL ="TOTAL", COMISSAO = "COMISAO", IMP_CONSUMO="IMP_CONSUMO", IMP_SELO ="IMP_SELO",
           NUMEROAPOLICE = "NUMERO APOLICE", PAISDESTINO="PAIS DESTINO", CIDADEDESTINO ="CIDADE DESTINO", ZONADESTINO="ZONA DESTINO",
            DATANASCIMENTO ="DATA NASCIMENTO", TELEFONE="TELEFONE", ENDERECO="ENDERECO" , LOCALNASCIMENTO ="LOCAL NASCIMENTO",
            NACIONALIDADE ="NACIONALIDADE";
    
    public static void criarDoc(Date dataInicio, Date dateFim, String user, String nomeFuncinario) {
        Font fontTableCorpo = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f);
        Font fontTableTitile = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f);
        
        Font fontRoadape = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
        Font fontRoadapeP = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f);
        Font fontRoadapeB = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
        Font fontRoadapeBU = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f, Font.UNDEFINED);

        Font fontCabecalhoN = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
        Font fontCorpoNG = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
        Font fontCabecalhoNG= FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f ,Font.UNDERLINE);
   
        OutputStream outputStraem;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
            SimpleDateFormat sdfTitile = new SimpleDateFormat("dd-MM-yyyy");
            Document documento = new Document();
            documento.setPageSize(PageSize.A4.rotate());
            documento.setMargins(10f, 10f, 35f, 20f);

            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Seguro Viagem/");
            ff.mkdirs();

            String Ddata = sdf.format(new Date());

            ff = new File(ff.getAbsoluteFile() + "/" + "Export Mapa Viagem Semanal " + Ddata + ".pdf");
            String reString = "../Documentos/" + user + "/Seguro Viagem/" + "Export Mapa Viagem Semanal " + Ddata + ".pdf";
            outputStraem = new FileOutputStream(ff);

            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            PdfPTable tableDados = new PdfPTable(new float[]{5f, 10.6f, 5f, 5f, 4.6f, 19f, 5.6f, 6f, 10.6f, 9.6f, 7f, 5.5f, 5.5f});
            tableDados.setWidthPercentage(100f);
            
            BaseColor colorCinza = new BaseColor(129, 138, 145);
            
            for (int j = 0; j < 13; j++) {
                PdfPCell cellTitileTable = new PdfPCell(new Phrase(titileTable(j), fontTableTitile));
                cellTitileTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cellTitileTable.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
               
                cellTitileTable.setBackgroundColor(colorCinza);
                tableDados.addCell(cellTitileTable);
            }
            
            dataViagem(dataInicio, dateFim);
            
            float premiototal = 0;
            
            for (HashMap<String, Object> data: hasList) {
                tableDados.addCell(new Phrase(toString(data.get(DATA)), fontTableCorpo));
                tableDados.addCell(ExportViagemSemanaPdf.cellEspecial(new PdfPCell(new Phrase(toString(data.get(NUMEROAPOLICE)), fontTableCorpo))));
                tableDados.addCell(new Phrase(ConfigDoc.toFormat(toString(data.get(INICIO)), "dd-MM-yyyy", "yyyy-MM-dd"), fontTableCorpo));
                tableDados.addCell(new Phrase(ConfigDoc.toFormat(toString(data.get(FIM)), "dd-MM-yyyy", "yyyy-MM-dd"), fontTableCorpo));
                
                premiototal += toFloat(data.get(PREMIO));
                
                PdfPCell cellRigh =new  PdfPCell(new Phrase(ConfigDoc.toMoeda(toFloat(data.get(PREMIO)), ""), fontTableCorpo));
                cellRigh.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                tableDados.addCell(cellRigh);
                
                tableDados.addCell(new Phrase(toString(data.get(CLIENTE)), fontTableCorpo));
                tableDados.addCell(new Phrase(toString(data.get(DATANASCIMENTO)), fontTableCorpo));
                tableDados.addCell(new Phrase(toString(data.get(TELEFONE)), fontTableCorpo));
                tableDados.addCell(new Phrase(toString(data.get(ENDERECO)), fontTableCorpo));
                tableDados.addCell(new Phrase(toString(data.get(LOCALNASCIMENTO)), fontTableCorpo));
                tableDados.addCell(new Phrase(toString(data.get(PAISDESTINO)), fontTableCorpo));
                tableDados.addCell(new Phrase(toString(data.get(CIDADEDESTINO)), fontTableCorpo));
                tableDados.addCell(new Phrase(toString(data.get(ZONADESTINO)), fontTableCorpo));
            }

            PdfPCell cellTotal = new PdfPCell(ExportViagemSemanaPdf.cellEspecial(new PdfPCell(new Phrase("AL AMOUNT..........................................", fontTableTitile))));
            cellTotal.setColspan(4);
            cellTotal.setPadding(1.5f);
            cellTotal.setBackgroundColor(colorCinza);
            tableDados.addCell(cellTotal);
            
            cellTotal = new PdfPCell(ExportViagemSemanaPdf.cellEspecial(new PdfPCell(new Phrase(ConfigDoc.toMoeda(premiototal, ""), fontTableTitile))));
            cellTotal.setPadding(1.5f);
            cellTotal.setBackgroundColor(colorCinza);
            tableDados.addCell(cellTotal);

            cellTotal = new PdfPCell(ExportViagemSemanaPdf.cellEspecial(new PdfPCell(new Phrase(" ", fontTableTitile))));
            cellTotal.setColspan(8);
            cellTotal.setPadding(1.5f);
            cellTotal.setBackgroundColor(colorCinza);
            tableDados.addCell(cellTotal);
            
           PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{15f, 85f});
           PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
           PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);
           
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
           pTableEmpresaPricipal.setWidthPercentage(95);
           pTableEmpresaPricipal.addCell(cellTabela6);
           pTableEmpresaPricipal.addCell(cellTabela5);
           
           PdfPTable pTableTitulo = new PdfPTable(1);
           pTableTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
           pTableTitulo.setWidthPercentage(100);
           
           PdfPCell cellTitulo = new PdfPCell(new Phrase("RLELATORIO SEMANAL NO. " + "" + "\n" + ((dataInicio != null) ? sdfTitile.format(dataInicio) + " - " : "") + ((dateFim != null) ? sdfTitile.format(dateFim) : ""), fontCorpoNG));
           cellTitulo.setBorder(0);
           cellTitulo.setPaddingBottom(20f);
           cellTitulo.setPaddingTop(10f);
           cellTitulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           pTableTitulo.addCell(cellTitulo);
           
           pTableEmpresaPricipal.setHorizontalAlignment(Element.ALIGN_CENTER);
           
           PdfPTable pTableRodape = new PdfPTable(new float[]{50f,50f});
           pTableRodape.setWidthPercentage(90f);
           
           
           PdfPCell cellRodape = new PdfPCell(new Phrase("DEPARTAMENTO FINANCEIRO", fontRoadapeBU));
           cellRodape.setBorder(0);
           cellRodape.setColspan(2);
           cellRodape.setPaddingTop(20f);
           pTableRodape.addCell(cellRodape);
           
           cellRodape = new PdfPCell(new Phrase("QUEIRA POR FAVOR CONFERIR OS PAGAMENTOS", fontRoadape));
           cellRodape.setColspan(2);
           cellRodape.setBorder(0);
           pTableRodape.addCell(cellRodape);
           
           cellRodape = new PdfPCell(new Phrase("ELABORADO POR", fontRoadapeB));
           cellRodape.setBorder(0);
           pTableRodape.addCell(cellRodape);
           cellRodape = new PdfPCell(new Phrase("VENFICADO POR", fontRoadapeB));
           cellRodape.setBorder(0);
           cellRodape.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           pTableRodape.addCell(cellRodape);
           
           cellRodape = new PdfPCell(new Phrase("................................................", fontRoadape));
           cellRodape.setBorder(0);
           cellRodape.setPaddingTop(30f);
           pTableRodape.addCell(cellRodape);
           cellRodape = new PdfPCell(new Phrase("................................................", fontRoadape));
           cellRodape.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           cellRodape.setBorder(0);
           cellRodape.setPaddingTop(30f);
           pTableRodape.addCell(cellRodape);
           
           cellRodape = new PdfPCell(new Phrase(nomeFuncinario, fontRoadapeP));
           cellRodape.setColspan(2);
           cellRodape.setBorder(0);
           pTableRodape.addCell(cellRodape);

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableTitulo);
            documento.add(tableDados);
            documento.add(pTableRodape);
            documento.close();
            
            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
            
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(ExportViagemSemanaPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportViagemSemanaPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ExportViagemSemanaPdf.criarDoc( null, null, "Ah","Ahmed Ferreira");
//        ExportDocViagemSemanal.criarDoc(new Date(), new Date(), "Ah", "Ahmed Ferreira");
    }

    public static String titileTable(int i) {
        if (i == 0) {
            return "DATA";
        } else if (i == 1) {
            return "APOLICE";
        } else if (i == 2) {
            return "DATA INICIO";
        } else if (i == 3) {
            return "DATA FIM";
        } else if (i == 4) {
            return "PROPOSTA DE EUROP ASSIST.";
        } else if (i == 5) {
            return "NOME";
        } else if (i == 6) {
            return "DATA NASC";
        } else if (i == 7) {
            return "TELEFONE";
        } else if (i == 8) {
            return "MORADA";
        } else if (i == 9) {
            return "NACIONALIDADE";
        } else if(i==10){
            return "DESTINO-PAIS";
        } else if(i==11) {
            return "DESTINO-CIDADE";
        } else {
            return "DESTINO-ZONA";
        }
    }

    private static PdfPTable cellEspecial(PdfPCell cellEspcial) {
        PdfPTable pTable = new PdfPTable(1);
        pTable.addCell(cellEspcial);
        cellEspcial.setPadding(3f);
        return pTable;
    }
    
    static ArrayList<HashMap<String,Object>> hasList= new  ArrayList<>();
    static private void dataViagem(Date dataInicio,Date dataFim)
    {
        hasList = new ArrayList<>();
        ResultSet rs = ViagemDao.relatorioTravel(dataInicio, dataFim);
        Consumer <HashMap<String, Object>> act  = (map)->
        {
                hasList.add(new LinkedHashMap<>(map));
        };
        Call.forEchaResultSet(act, rs);
    }
    
    static private Float toFloat(Object o)
    {
        return ((o!=null && !o.toString().isEmpty() ) ? Float.valueOf(o.toString().replace(",", ".").replace(" ", "0")): 0f );
    }

    
    static private String toString(Object o)
    {
        return ((o == null) ? " " : o.toString());
    }
}