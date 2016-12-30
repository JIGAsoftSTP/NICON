/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import static Export.GenericExcel.CreateCell;
import conexao.Call;
import dao.ViagemDao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
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
 * @author AhmedJorge
 */
public class ExporOnlyViagemExcel implements Serializable
{

//   String lastData = "";
   String DATA="DATA", APOLICE = "APOLICE", INICIO = "INICIO", FIM ="FIM", DIAS = "DIAS", CLIENTE = "CLIENTE", RECIBO= "RECIBO", PREMIO = "PREMIO",
   CONSUMO = "CONSUMO", SELO = "SELO", NETOUT ="NET OUT", TOTAL ="TOTAL", COMISSAO = "COMISAO", IMP_CONSUMO="IMP_CONSUMO", IMP_SELO ="IMP_SELO",
           NUMEROAPOLICE = "NUMERO APOLICE", PAISDESTINO ="PAIS DESTINO", CIDADEDESTINO ="CIDADE DESTINO", ZONADESTINO="ZONA DESTINO";
   
   int linha = 0;
   
   Float totalSelo = 0f, totalConsumo = 0f, totalTotal = 0f, totalPremio = 0f, totalComissao= 0f;
   public void criarDoc  (  String user,   Date dataInicio,Date dataFim   ) {
       
            try {
                
                SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
                
                File ff= new File(ConfigDoc.Fontes.getDiretorio()+"/"+user+"/Seguro Viagem/");
                ff.mkdirs();
                
                String Ddata = sdf.format(new Date());
                
                ff =new File(ff.getAbsoluteFile()+"/"+"Export Mapa Viagem "+Ddata+".xls");
                
                String reString = "../Documentos/" + user + "/Seguro Viagem/" + "Export Mapa Viagem "+ Ddata + ".xls";
                
                Workbook wb = new HSSFWorkbook();
                
                dataTableDados(wb, dataInicio, dataFim);
                
                try (FileOutputStream out = new FileOutputStream(ff))
                {  wb.write(out); }
                catch (IOException ex) {   Logger.getLogger(GenericExcel.class.getName()).log(Level.SEVERE, null, ex); }
                
                RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
                
            } 
            catch (ParseException ex) {   Logger.getLogger(ExporOnlyViagemExcel.class.getName()).log(Level.SEVERE, null, ex); }
            
    }

    private void dataTableTitile(Sheet s, String titile, CellStyle csTitulo, CellStyle csTituloP,CellStyle csTituloTabelaNBorder) {
        
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
        CreateCell(c, r, s, csTituloTabelaNBorder, linha, linha, "TOTAL PREMIUM COLLECTED ON TRAVEL INSURANCE AND TAXES FOR "+titile, 1, 10);
        linha += 2;
    }

    private void dataTableDados(Workbook wb, Date dataInicio, Date dataFim) throws ParseException  {
        
        Font fTitulo = wb.createFont();
        fTitulo.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        fTitulo.setFontHeightInPoints((short) 14);

        Font fTituloP = wb.createFont();
        fTituloP.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        fTituloP.setFontHeightInPoints((short) 12);
//            fTituloP.setStrikeout(true);
        fTituloP.setUnderline(org.apache.poi.ss.usermodel.Font.U_SINGLE);

        Font fTituloTabela = wb.createFont();
        fTituloTabela.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        fTituloTabela.setFontHeightInPoints((short) 8);

        Font fCorpoTabela = wb.createFont();
        fCorpoTabela.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_NORMAL);
        fCorpoTabela.setFontHeightInPoints((short) 8.5);

        Font fRodapeTabela = wb.createFont();
        fRodapeTabela.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        fRodapeTabela.setFontHeightInPoints((short) 8.5);

       Font fNormal = wb.createFont();
        fNormal.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
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

        int i, p = 0;
        dataViagem(dataInicio, dataFim);
        for (Map.Entry<String, ArrayList<HashMap<String, Object>>> data : hasList.entrySet()) {

            totalSelo = 0f;
            totalConsumo = 0f;
            totalTotal = 0f;
            totalPremio = 0f;
            totalComissao = 0f;
            
             linha = 0;
             
           SimpleDateFormat sdfEsp = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
           SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
           
           String titile = sdfEsp.format(sdf.parse((data.getKey()))).toUpperCase();

            Sheet s = wb.createSheet(titile);

            dataTableTitile(s, titile, csTitulo, csTituloP, csTituloTabelaNBorder);

            Row r = s.createRow(linha);
            Cell c = r.createCell(2);
            
            for (int j = 0; j < 13; j++)
            { 
                if(j == 0) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 1, 1); }
                else if(j == 1) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 2, 3); }
                else if(j == 2) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 4, 5 ); }
                else if(j == 3) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 6, 7); }
                else if(j == 4) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 8, 9); }
                else if(j == 5) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 10, 10); }
                else if(j == 6) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 11, 14); }
                else if(j == 7) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 15, 16); }
                else if(j == 8) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 17, 18); }
                else if(j == 9) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 19,20); }
                else if(j == 10) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 21, 22); }
                else if(j == 11) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 23, 24); }
                else if(j == 12) { CreateCell(c, r, s, csTituloTabela, linha, linha, funcaoTitulo(j), 25, 27); }
            }
            i = 1;
            
            for (HashMap<String, Object> hashMap : data.getValue()) {
               
                linha ++;
                r = s.createRow(linha);
                c = r.createCell(2);
                
                CreateCell(c, r, s, csCorpoTabela, linha, linha, (i + ""), 1, 1);
                
                 CreateCell(c, r, s, csCorpoTabela, linha, linha, toString(hashMap.get(DATA)), 2, 3);
                 
                 CreateCell(c, r, s, csCorpoTabela, linha, linha, toString(hashMap.get(NUMEROAPOLICE)), 4, 5);
                 
                 CreateCell(c, r, s, csCorpoTabela, linha, linha, ConfigDoc.toFormat( toString(hashMap.get(INICIO)), "dd-MM-yyyy", "yyyy-MM-dd"), 6, 7);
                 
                 CreateCell(c, r, s, csCorpoTabela, linha, linha,ConfigDoc.toFormat( toString(hashMap.get(FIM)), "dd-MM-yyyy", "yyyy-MM-dd"), 8, 9);
                 
                 CreateCell(c, r, s, csCorpoTabela, linha, linha, toString(hashMap.get(DIAS)), 10, 10);
                 
                 CreateCell(c, r, s, csCorpoTabelaL, linha, linha, toString(hashMap.get(CLIENTE)), 11, 14);
                 
                 CreateCell(c, r, s, csCorpoTabela, linha, linha, toString(hashMap.get(RECIBO)), 15, 16);
                 
                 CreateCell(c, r, s, csCorpoTabelaR, linha, linha, toString(hashMap.get(PREMIO)), 17, 18);
                 
                 totalComissao += toFloat(toString(hashMap.get(COMISSAO)));
                 CreateCell(c, r, s, csCorpoTabelaR, linha, linha, ConfigDoc.toMoeda(toString(hashMap.get(COMISSAO)), ""), 19, 20);
                 
                 totalConsumo += toFloat(toString(hashMap.get(CONSUMO)));
                 CreateCell(c, r, s, csCorpoTabelaR, linha, linha, ConfigDoc.toMoeda(toString(hashMap.get(CONSUMO)), ""), 21, 22);   

                totalSelo += toFloat(toString(hashMap.get(SELO)));
                CreateCell(c, r, s, csCorpoTabelaR, linha, linha, ConfigDoc.toMoeda(toString(hashMap.get(SELO)), ""), 23, 24);

                totalTotal += toFloat(toString(hashMap.get(TOTAL)));
                CreateCell(c, r, s, csCorpoTabelaR, linha, linha, ConfigDoc.toMoeda(toString(hashMap.get(TOTAL)), ""), 25, 27);
                
                i++;
            }
            linha++;
            radapeTable(s, csRodapeTabelaR);
        }
    }

    private void radapeTable(Sheet s,CellStyle cellStyle){
        
         Row r = s.createRow(linha);
         Cell c = r.createCell(2);
        
        CreateCell(c, r, s, cellStyle, linha, linha, " ", 1, 3);  
        
        CreateCell(c, r, s, cellStyle, linha, linha, " ", 4, 5);
        
        CreateCell(c, r, s, cellStyle, linha, linha, " ", 6, 7);
        
        CreateCell(c, r, s, cellStyle, linha, linha, " ", 8, 9);
        
        CreateCell(c, r, s, cellStyle, linha, linha, " ", 10, 10);
        
        CreateCell(c, r, s, cellStyle, linha, linha, " ", 11, 14);
        
        CreateCell(c, r, s, cellStyle, linha, linha, " ", 15, 16);
       
        CreateCell(c, r, s, cellStyle, linha, linha,  ConfigDoc.toMoeda(totalPremio, ""), 17, 18);
        
        CreateCell(c, r, s, cellStyle, linha, linha,  ConfigDoc.toMoeda(totalComissao, ""), 19, 20);
        
        CreateCell(c, r, s, cellStyle, linha, linha,  ConfigDoc.toMoeda(totalConsumo, ""), 21, 22);
        
        CreateCell(c, r, s, cellStyle, linha, linha,  ConfigDoc.toMoeda(totalSelo, ""), 23,24);
        
        CreateCell(c, r, s, cellStyle, linha, linha,  ConfigDoc.toMoeda(totalTotal, ""), 25, 27);
    }
        
    private String funcaoTitulo(int i) {
       switch (i) 
       {
           case 0:return "S/N";
           case 1:return"DATA";
           case 2:return"APOLICE";
           case 3:return "DATA INICIO";
           case 4:return "DATA FIM";
           case 5: return "NO. DIAS";
           case 6:return "NOME";
           case 7:return "RECEIPT NO.";
           case 8:return "EA PREM";
           case 9:return "NICON COMISSÃO";
           case 10:return"5% IMPOSTO";
           case 11:return "0.60% SELO";
//           case 12:txt="NET OUT OF TAX";break;
           default:return "TOTAL"/*"NET OUT OF TAX"*/;
       } 
    }
    public static void main(String[] args) {
        ExporOnlyViagemExcel aPG= new ExporOnlyViagemExcel();
        aPG.criarDoc("ah",null,null);
    }
    
    HashMap <String, ArrayList<HashMap<String,Object>>> hasList= new  LinkedHashMap<>();
    private void dataViagem(Date dataInicio,Date dataFim)
    {
        hasList = new LinkedHashMap<>();
        ResultSet rs = ViagemDao.relatorioTravel(dataInicio, dataFim);
        Consumer <HashMap<String, Object>> act  = (map)->
        {
            map.put(CONSUMO , ((toFloat(map.get(IMP_CONSUMO))/100) * toFloat(map.get(COMISSAO))));
            map.put(SELO , ((toFloat(map.get(IMP_SELO))/100) * toFloat(map.get(COMISSAO))));
            map.put(TOTAL , (toFloat(map.get(IMP_SELO)) + toFloat(map.get(COMISSAO)) + toFloat(map.get(CONSUMO)) + toFloat(map.get(PREMIO))));
            
            if(hasList.containsKey((map.get(DATA) != null) ? map.get(DATA).toString().substring(3, toString(map.get(DATA)).length()) : ""))
            { hasList.get(((map.get(DATA) != null) ? map.get(DATA).toString().substring(3, toString(map.get(DATA)).length()) : "")).add(new LinkedHashMap<>(map)); }
            else 
            {
                ArrayList<HashMap<String,Object>> list = new ArrayList<>();
                list.add(new LinkedHashMap<>(map));
                hasList.put(((map.get(DATA) != null) ? map.get(DATA).toString().substring(3, toString(map.get(DATA)).length()) : ""), list);
            }
        };
        Call.forEchaResultSet(act, rs);
    }
    
//   @SuppressWarnings("null")
    private Float toFloat(Object o)
    {
        return ((o!=null && !o.toString().isEmpty() ) ? Float.valueOf(o.toString().replace(",", ".").replace(" ", "0")): 0f );
    }
    
    private String toString(Object o)
    {
        return ((o == null) ? " " : o.toString());
    }
}
