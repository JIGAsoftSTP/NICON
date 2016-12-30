/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import static Export.ConfigDoc.Fontes.getDiretorio;
import conexao.Call;
import dao.UtilitarioDao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author AhmedJorge
 */
public class ExportMapaProducaoExcel__ 
{
    public static String DATA="DATA", NUMAPOLICE ="NUM APOLICE" , CLIENTESEGURO= "CLIENTE",  MOEDA="MOEDA",
            PREMIO="PREMIO", IMPOSTOCONSUMO="IMPOSTO CONSUMO", IMPOSTOSELO="IMPOSTO SELO",
               SEGURO="SEGURO", VALORTOTAL="VALOR TOTAL", FGA="FGA";
    
    ArrayList<Producao> list = new ArrayList<>();
    UtilitarioDao ud = new UtilitarioDao();
//    String lastSeguro = "";
    HashMap<String,ArrayList<Producao>> hasList= new  HashMap<>();
    private int a;
    public String criarDoc(String user, Date incio,Date fim)
    {
        try {
            Workbook wb = new HSSFWorkbook();
            
            Font fTitulo = wb.createFont();
            fTitulo.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fTitulo.setFontHeightInPoints((short) 22);
            
            Font fTituloP = wb.createFont();
            fTituloP.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fTituloP.setFontHeightInPoints((short) 13);
            
            Font fTituloTabela = wb.createFont();
            fTituloTabela.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fTituloTabela.setFontHeightInPoints((short) 12.5);
            
            Font fCorpoTabela = wb.createFont();
            fCorpoTabela.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            fCorpoTabela.setFontHeightInPoints((short) 11.5);
            
            Font fRodapeTabela = wb.createFont();
            fRodapeTabela.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fRodapeTabela.setFontHeightInPoints((short) 11.5);
            
            Font fNormal = wb.createFont();
            fNormal.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fNormal.setFontHeightInPoints((short) 11);
            
            CellStyle csTitulo = wb.createCellStyle();
            csTitulo.setFont(fTitulo);
            csTitulo.setAlignment((short) 1);
            csTitulo.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
            csTitulo.setWrapText(true);
            csTitulo.setBorderBottom((short) 0);
            csTitulo.setBorderTop((short) 0);
            csTitulo.setBorderRight((short) 0);
            csTitulo.setBorderLeft((short) 0);
            csTitulo.setWrapText(true);
            
            CellStyle csTituloP = wb.createCellStyle();
            csTituloP.setFont(fTituloP);
            csTituloP.setAlignment((short) 1);
            csTituloP.setVerticalAlignment((short) 1);
            csTituloP.setWrapText(true);
            csTituloP.setBorderBottom((short) 0);
            csTituloP.setBorderTop((short) 0);
            csTituloP.setBorderRight((short) 0);
            csTituloP.setBorderLeft((short) 0);
            csTituloP.setWrapText(true);
            
            CellStyle csTituloT = wb.createCellStyle();
            csTituloT.setFont(fTituloP);
            csTituloT.setAlignment((short) 1);
            csTituloT.setVerticalAlignment((short) 1);
            csTituloT.setWrapText(true);
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
            
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            SimpleDateFormat sdfPt= new SimpleDateFormat("dd-MM-yyyy");
            
            
            File ff= new File(getDiretorio()+"/"+user+"/Relatorio/");
            ff.mkdirs();
            
            String stringData = sdf.format(new Date()); 
            
            ff =new File(ff.getAbsoluteFile()+"/"+"Export Mapa ProducaoExel "+stringData+".xls");
            
            String reString ="../Documentos/"+user+"/Relatorio/"+"Export Mapa ProducaoExel "+stringData+".xls";
            
            Sheet s = wb.createSheet("Mapa de produção de ".toUpperCase()+
                    ((incio!=null)?sdfPt.format(incio)+" à ":" dos Ultimos anos àte hoje".toUpperCase())
                    +((fim==null)?"":sdfPt.format(fim)));
            int linha=0;
            
//            int pictureIdx;
//            try (InputStream inputStream = new FileInputStream("logo1.png")) {
//                byte[] bytes = IOUtils.toByteArray(inputStream);
//                pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
//            }
//            CreationHelper helper = wb.getCreationHelper();
//            Drawing drawing = s.createDrawingPatriarch();
//            ClientAnchor anchor = helper.createClientAnchor();
//            anchor.setCol1(0);
//            anchor.setCol2(3);
//            anchor.setRow1(0);
//            Picture pict = drawing.createPicture(anchor, pictureIdx);
//            pict.resize();
            
            Row r = s.createRow(linha);
            Cell c = r.createCell(2);
            CreateCell(c, r, s, csTitulo, linha, linha+3, ConfigDoc.Empresa.NOME, 1, 22);
            linha+=4;
            
            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.ENDERECO, 1, 22);
            linha++;
            
            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.CAIXAPOSTAL, 1, 22);
            linha++;
            
            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.TELEFAX+" "+ConfigDoc.Empresa.EMAIL, 1, 22);
            linha++;
            
            r = s.createRow(linha);
            CreateCell(c, r, s, csTituloP, linha, linha, ConfigDoc.Empresa.SOCIEDADE, 1, 22);
            
            ResultSet rs = ud.relatorioSeguroForImpresao(incio, fim);
            Consumer <HashMap<String, Object>> act  = (map)->
            {
                list = new ArrayList<>();
                putNewDado(map,incio, fim); 
            };
            
            Call.forEchaResultSet(act, rs);

            for (Map.Entry<String, ArrayList<Producao>> al : hasList.entrySet()) 
            {               
                linha+=4;
                r = s.createRow(linha);
                CreateCell(c, r, s, csTituloT, linha, linha, al.getKey(), 1, 22);

                linha++;
                linha++;
                r = s.createRow(linha);
                CreateCell(c, r, s, csTituloTabela, linha, linha, "Nr. Factura", 1, 2);
                CreateCell(c, r, s, csTituloTabela, linha, linha, "Nome do Segurado", 3, 7);
                CreateCell(c, r, s, csTituloTabela, linha, linha, "Premio", 8, 10);
                CreateCell(c, r, s, csTituloTabela, linha, linha, "Imposto 6%", 11, 13);
                CreateCell(c, r, s, csTituloTabela, linha, linha, "Imposto 5%", 14, 16);
                CreateCell(c, r, s, csTituloTabela, linha, linha, "FGA 2.6%", 17, 19);
                CreateCell(c, r, s, csTituloTabela, linha, linha, "TOTAL", 20, 22);
                for (Producao pro:al.getValue()) {
                    
                    linha++;
                    r = s.createRow(linha);
                    if (!pro.DATA.equals("SOMATORIO"))
                    {
                       CreateCell(c, r, s, csCorpoTabela, linha, linha, pro.NUMAPOLICE, 1, 2);
                       CreateCell(c, r, s, csCorpoTabelaL, linha, linha, pro.CLIENTESEGURO, 3, 7);
                       CreateCell(c, r, s, csCorpoTabelaR, linha, linha, pro.PREMIO +" "+pro.MOEDA, 8, 10);
                       CreateCell(c, r, s, csCorpoTabelaR, linha, linha, pro.IMPOSTOCONSUMO +" "+pro.MOEDA, 11, 13);
                       CreateCell(c, r, s, csCorpoTabelaR, linha, linha, pro.IMPOSTOSELO +" "+pro.MOEDA, 14, 16);
                       CreateCell(c, r, s, csCorpoTabelaR, linha, linha, pro.FGA +" "+pro.MOEDA, 17, 19);
                       CreateCell(c, r, s, csCorpoTabelaR, linha, linha, pro.VALORTOTAL +" "+pro.MOEDA, 20, 22);
                    } else 
                    {
                       CreateCell(c, r, s, csRodapeTabela, linha, linha, "Total "+al.getKey(), 1, 7);
                       CreateCell(c, r, s, csRodapeTabelaR, linha, linha, pro.PREMIO +" "+pro.MOEDA, 8, 10);
                       CreateCell(c, r, s, csRodapeTabelaR, linha, linha, pro.IMPOSTOCONSUMO +" "+pro.MOEDA, 11, 13);
                       CreateCell(c, r, s, csRodapeTabelaR, linha, linha, pro.IMPOSTOSELO +" "+pro.MOEDA, 14, 16);
                       CreateCell(c, r, s, csRodapeTabelaR, linha, linha, pro.FGA +" "+pro.MOEDA, 17, 19);
                       CreateCell(c, r, s, csRodapeTabelaR, linha, linha, pro.VALORTOTAL +" "+pro.MOEDA, 20, 22);
                    }
                }
            }
            
            
            
            try (FileOutputStream out = new FileOutputStream(ff)) 
            {wb.write(out);}
            
            return reString;
        } catch (IOException ex) 
        {Logger.getLogger(ExportMapaProducaoExcel__.class.getName()).log(Level.SEVERE, null, ex); return null;}
    }
    public void CreateCell(Cell c, Row r, Sheet s, CellStyle cs, int colinaI, int colinaF, String valorS, int linhaI, int linhaF) {
      
        c = r.createCell(linhaI);
        c.setCellStyle(cs);
        c.setCellValue(valorS);
        s.addMergedRegion(new CellRangeAddress(colinaI, colinaF, linhaI, linhaF));
        for (int e = (linhaI + 1); e <= linhaF; e++) {
            c = r.createCell(e);
            c.setCellStyle(cs);
        }
    }
    
    public void putNewDado(HashMap<String, Object> map,Date dataInicio,Date dataFim) {
        
        list = new ArrayList<>();
        ResultSet rs = ud.relatorioPromocaoTipo(dataInicio, dataFim,Integer.valueOf(map.get("ID").toString()),2);
        Consumer <HashMap<String, Object>> act  = (map1)->
        {
            list.add(new Producao(stringValue(map1.get(DATA)),
            stringValue(map1.get(NUMAPOLICE)),stringValue(map1.get(CLIENTESEGURO)),stringValue(map1.get(MOEDA)), stringValue(map1.get(PREMIO)), 
            stringValue(map1.get(IMPOSTOCONSUMO)), stringValue(map1.get(IMPOSTOSELO)), stringValue(map1.get(VALORTOTAL)),stringValue(map1.get(SEGURO))
            ,stringValue(map1.get(FGA))));
        };
        Call.forEchaResultSet(act, rs);
        hasList.put(stringValue(map.get("NOME")), list);
    }
    public class Producao
    {
        private String DATA, NUMAPOLICE , CLIENTESEGURO,  MOEDA,
            PREMIO, IMPOSTOCONSUMO, IMPOSTOSELO,VALORTOTAL, SEGURO,FGA;

        public Producao(String DATA, String NUMAPOLICE, String CLIENTESEGURO, String MOEDA, String PREMIO, String IMPOSTOCONSUMO, String IMPOSTOSELO, String VALORTOTAL,String SEGURO,String FGA) {
            this.DATA = DATA;
            this.NUMAPOLICE = NUMAPOLICE;
            this.CLIENTESEGURO = CLIENTESEGURO;
            this.MOEDA = MOEDA;
            this.PREMIO = PREMIO;
            this.IMPOSTOCONSUMO = IMPOSTOCONSUMO;
            this.IMPOSTOSELO = IMPOSTOSELO;
            this.VALORTOTAL = VALORTOTAL;
            this.SEGURO= SEGURO;
            this.FGA=FGA;
        }
    }
    public String stringValue(Object string)
    {
        return ((string==null)?"":string.toString());
    }
    
    public static void main(String[] args) {
        new ExportMapaProducaoExcel__().criarDoc("ah", null, null);
    }
}


