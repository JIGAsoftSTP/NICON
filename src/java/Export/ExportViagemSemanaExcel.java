/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import static Export.GenericExcel.CreateCell;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ahmedjorge
 */
public class ExportViagemSemanaExcel {
    
    static final String DATA="DATA", APOLICE = "APOLICE", INICIO = "INICIO", FIM ="FIM", DIAS = "DIAS", CLIENTE = "CLIENTE", RECIBO= "RECIBO", PREMIO = "PREMIO",
   CONSUMO = "CONSUMO", SELO = "SELO", NETOUT ="NET OUT", TOTAL ="TOTAL", COMISSAO = "COMISAO", IMP_CONSUMO="IMP_CONSUMO", IMP_SELO ="IMP_SELO",
           NUMEROAPOLICE = "NUMERO APOLICE", PAISDESTINO="PAIS DESTINO", CIDADEDESTINO ="CIDADE DESTINO", ZONADESTINO="ZONA DESTINO",
            DATANASCIMENTO ="DATA NASCIMENTO", TELEFONE="TELEFONE", ENDERECO="ENDERECO" , LOCALNASCIMENTO ="LOCAL NASCIMENTO",
            NACIONALIDADE ="NACIONALIDADE";
    
    public static void criarDoc(Date dataInicio, Date dateFim, String user, String nomeFuncinario) {
        Workbook wb = new HSSFWorkbook();
        
        Font fTitulo = wb.createFont();
        fTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fTitulo.setFontHeightInPoints((short) 14);

        Font fTituloP = wb.createFont();
        fTituloP.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fTituloP.setFontHeightInPoints((short) 12);
//            fTituloP.setStrikeout(true);
        fTituloP.setUnderline(Font.U_SINGLE);

        Font fTituloTabela = wb.createFont();
        fTituloTabela.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fTituloTabela.setFontHeightInPoints((short) 8);

        Font fCorpoTabela = wb.createFont();
        fCorpoTabela.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        fCorpoTabela.setFontHeightInPoints((short) 8.5);

        Font fRodapeTabela = wb.createFont();
        fRodapeTabela.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fRodapeTabela.setFontHeightInPoints((short) 8.5);

       Font fNormal = wb.createFont();
        fNormal.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fNormal.setFontHeightInPoints((short) 8.5);

        CellStyle csTitulo = wb.createCellStyle();
        csTitulo.setFont(fTitulo);
        csTitulo.setAlignment((short) 1);
        csTitulo.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
        csTitulo.setBorderBottom((short) 0);
        csTitulo.setBorderTop((short) 0);
        csTitulo.setBorderRight((short) 0);
        csTitulo.setBorderLeft((short) 0);
        csTitulo.setWrapText(true);

        CellStyle csTituloP = wb.createCellStyle();
        csTituloP.setFont(fTituloP);
        csTituloP.setAlignment((short) 1);
        csTituloP.setVerticalAlignment((short) 1);
        csTituloP.setBorderBottom((short) 0);
        csTituloP.setBorderTop((short) 0);
        csTituloP.setBorderRight((short) 0);
        csTituloP.setBorderLeft((short) 0);
        csTituloP.setWrapText(true);

        CellStyle csTituloT = wb.createCellStyle();
        csTituloT.setFont(fTituloP);
        csTituloT.setAlignment((short) 1);
        csTituloT.setVerticalAlignment((short) 1);
        csTituloT.setBorderBottom((short) 0);
        csTituloT.setBorderTop((short) 0);
        csTituloT.setBorderRight((short) 0);
        csTituloT.setBorderLeft((short) 0);
        csTituloT.setWrapText(true);

        CellStyle csTituloTabela = wb.createCellStyle();
        csTituloTabela.setFont(fTituloTabela);
        csTituloTabela.setAlignment(CellStyle.ALIGN_CENTER);
        csTituloTabela.setVerticalAlignment((short) 2);
        csTituloTabela.setBorderBottom((short) 2);
        csTituloTabela.setBorderTop((short) 2);
        csTituloTabela.setBorderRight((short) 2);
        csTituloTabela.setBorderLeft((short) 2);
        csTituloTabela.setWrapText(true);

        CellStyle csTituloTabelaNBorder = wb.createCellStyle();
        csTituloTabelaNBorder.setFont(fTituloTabela);
        csTituloTabelaNBorder.setAlignment(CellStyle.ALIGN_CENTER);
        csTituloTabelaNBorder.setVerticalAlignment((short) 2);
        csTituloTabelaNBorder.setBorderBottom((short) 2);
        csTituloTabelaNBorder.setBorderTop((short) 2);
        csTituloTabelaNBorder.setBorderRight((short) 2);
        csTituloTabelaNBorder.setBorderLeft((short) 2);
        csTituloTabelaNBorder.setWrapText(true);

        CellStyle csCorpoTabela = wb.createCellStyle();
        csCorpoTabela.setFont(fCorpoTabela);
        csCorpoTabela.setAlignment((short) 2);
        csCorpoTabela.setVerticalAlignment((short) 1);
        csCorpoTabela.setBorderBottom((short) 1);
        csCorpoTabela.setBorderTop((short) 1);
        csCorpoTabela.setBorderRight((short) 1);
        csCorpoTabela.setBorderLeft((short) 1);
        csCorpoTabela.setWrapText(true);

        CellStyle csCorpoTabelaR = wb.createCellStyle();
        csCorpoTabelaR.setFont(fCorpoTabela);
        csCorpoTabelaR.setAlignment(CellStyle.ALIGN_RIGHT);
        csCorpoTabelaR.setVerticalAlignment((short) 1);
        csCorpoTabelaR.setBorderBottom((short) 1);
        csCorpoTabelaR.setBorderTop((short) 1);
        csCorpoTabelaR.setBorderRight((short) 1);
        csCorpoTabelaR.setBorderLeft((short) 1);
        csCorpoTabelaR.setWrapText(true);

        CellStyle csCorpoTabelaL = wb.createCellStyle();
        csCorpoTabelaL.setFont(fCorpoTabela);
        csCorpoTabelaL.setAlignment(CellStyle.ALIGN_LEFT);
        csCorpoTabelaL.setVerticalAlignment((short) 1);
        csCorpoTabelaL.setBorderBottom((short) 1);
        csCorpoTabelaL.setBorderTop((short) 1);
        csCorpoTabelaL.setBorderRight((short) 1);
        csCorpoTabelaL.setBorderLeft((short) 1);
        csCorpoTabelaL.setWrapText(true);

        CellStyle csRodapeTabela = wb.createCellStyle();
        csRodapeTabela.setFont(fRodapeTabela);
        csRodapeTabela.setAlignment((short) 1);
        csRodapeTabela.setVerticalAlignment((short) 2);
        csRodapeTabela.setBorderBottom((short) 2);
        csRodapeTabela.setBorderTop((short) 2);
        csRodapeTabela.setBorderRight((short) 2);
        csRodapeTabela.setBorderLeft((short) 2);
        csRodapeTabela.setWrapText(true);

        CellStyle csRodapeTabelaR = wb.createCellStyle();
        csRodapeTabelaR.setFont(fRodapeTabela);
        csRodapeTabelaR.setAlignment(CellStyle.ALIGN_RIGHT);
        csRodapeTabelaR.setVerticalAlignment((short) 2);
        csRodapeTabelaR.setBorderBottom((short) 2);
        csRodapeTabelaR.setBorderTop((short) 2);
        csRodapeTabelaR.setBorderRight((short) 2);
        csRodapeTabelaR.setBorderLeft((short) 2);
        csRodapeTabelaR.setWrapText(true);

        CellStyle csNomal = wb.createCellStyle();
        csNomal.setFont(fCorpoTabela);
        csNomal.setAlignment((short) 1);
        csNomal.setVerticalAlignment((short) 1);
        csNomal.setBorderBottom((short) 0);
        csNomal.setBorderTop((short) 0);
        csNomal.setBorderRight((short) 0);
        csNomal.setBorderLeft((short) 0);
        csNomal.setWrapText(true);
   
        OutputStream outputStraem;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
            SimpleDateFormat sdfTitile = new SimpleDateFormat("dd-MM-yyyy");

            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Seguro Viagem/");
            ff.mkdirs();

            String Ddata = sdf.format(new Date());

            ff = new File(ff.getAbsoluteFile() + "/" + "Export Mapa Viagem Semanal " + Ddata + ".xls");
            String reString = "../Documentos/" + user + "/Seguro Viagem/" + "Export Mapa Viagem Semanal " + Ddata + ".xls";
            outputStraem = new FileOutputStream(ff);
            
            int linha = 0;
            
            Sheet s = wb.createSheet("RELATORIO SEMANAL");
            
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
            CreateCell(c, r, s, csTituloTabelaNBorder, linha,  linha+1, "RELATORIO SEMANAL NO. " + "" + "\n" + ((dataInicio != null) ? sdfTitile.format(dataInicio) + " - " : "") + ((dateFim != null) ? sdfTitile.format(dateFim) : ""), 1, 10);
            linha += 3;
            
             r = s.createRow(linha);
             c = r.createCell(2);
             
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(0), 1, 1); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(1), 2, 3); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(2), 4, 4); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(3), 5, 5); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(4), 6, 7); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(5), 8, 10); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(6), 11, 11); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(7), 12, 12); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(8), 13, 14); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(9), 15, 16); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(10), 17, 18); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(11), 19, 20); 
            CreateCell(c, r, s, csTituloTabela, linha,  linha+1, titileTable(12), 21, 22); 

            dataViagem(dataInicio, dateFim);
            
            float premiototal = 0;
            
            linha++;
            for (HashMap<String, Object> data: hasList) {
              
                linha++;
                r = s.createRow(linha);
                c = r.createCell(2);
                
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(DATA)), 1, 1);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(NUMEROAPOLICE)), 2, 3);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ConfigDoc.toFormat(toString(data.get(INICIO)), "dd-MM-yyyy", "yyyy-MM-dd"), 4, 4);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, ConfigDoc.toFormat(toString(data.get(FIM)), "dd-MM-yyyy", "yyyy-MM-dd"), 5, 5);
                
                premiototal += toFloat(data.get(PREMIO));
                CreateCell(c, r, s, csCorpoTabelaR, linha, linha, ConfigDoc.toMoeda(toFloat(data.get(PREMIO)), ""), 6, 7);
                
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(CLIENTE)), 8, 10);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(DATANASCIMENTO)), 11, 11);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(TELEFONE)), 12, 12);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(ENDERECO)), 13, 14);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(LOCALNASCIMENTO)), 15, 16);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(PAISDESTINO)), 17, 18);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(CIDADEDESTINO)), 19, 20);
                CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(data.get(ZONADESTINO)), 21, 22);

            }
            
            linha++;
            r = s.createRow(linha);
            c = r.createCell(2);
                
            CreateCell(c, r, s, csRodapeTabela, linha, linha, "AL AMOUNT..........................................", 1, 5);
            
            CreateCell(c, r, s, csRodapeTabelaR, linha, linha, ConfigDoc.toMoeda(premiototal, ""), 6, 7);
            
            CreateCell(c, r, s, csRodapeTabela, linha, linha, " ", 8, 22);
           
            
           try (FileOutputStream out = new FileOutputStream(ff))   {  wb.write(out); }
           catch (IOException ex) {   Logger.getLogger(GenericExcel.class.getName()).log(Level.SEVERE, null, ex); }
           
            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportViagemSemanaExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ExportViagemSemanaExcel.criarDoc( null, null, "Ah","Ahmed Ferreira");
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