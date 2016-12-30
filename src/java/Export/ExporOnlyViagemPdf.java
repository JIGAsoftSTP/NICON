/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import conexao.Call;
import dao.ViagemDao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author AhmedJorge
 */
public class ExporOnlyViagemPdf implements Serializable
{
   PdfPTable pTableEmpresaPricipal;
   PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
   PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);
   Font fontCabecalhoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f);
   Font fontCorpo= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f);
   Font fontCorpoTable= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,7.5f);
   Font fontCorpoP= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f);
   Font fontCorpoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f);
   Font fontCorpoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f);
   Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f ,Font.UNDERLINE);
   PdfPTable pTableNull = new PdfPTable(1);
   
//   String lastData = "";
   String DATA="DATA", APOLICE = "APOLICE", INICIO = "INICIO", FIM ="FIM", DIAS = "DIAS", CLIENTE = "CLIENTE", RECIBO= "RECIBO", PREMIO = "PREMIO",
   CONSUMO = "CONSUMO", SELO = "SELO", NETOUT ="NET OUT", TOTAL ="TOTAL", COMISSAO = "COMISAO", IMP_CONSUMO="IMP_CONSUMO", IMP_SELO ="IMP_SELO",
           NUMEROAPOLICE = "NUMERO APOLICE", PAISDESTINO ="PAIS DESTINO", CIDADEDESTINO ="CIDADE DESTINO", ZONADESTINO="ZONA DESTINO";
   
   Float totalSelo = 0f, totalConsumo = 0f, totalTotal = 0f, totalPremio = 0f, totalComissao= 0f;
   public void criarDoc
        (
                String user,
                Date dataInicio,Date dataFim
        )
    {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
                    
            PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontCorpo));
            cellNull.setBorder(0);
            pTableNull.addCell(cellNull);
     
            Document documento= new Document();
            documento.setPageSize(PageSize.A4.rotate());
            documento.setMargins(10f, 10f, 35f, 5f);
            
            File ff= new File(ConfigDoc.Fontes.getDiretorio()+"/"+user+"/Seguro Viagem/");
            ff.mkdirs();
            
            String Ddata = sdf.format(new Date());
            
            ff =new File(ff.getAbsoluteFile()+"/"+"Export Mapa Viagem "+Ddata+".pdf");
            
            String reString = "../Documentos/" + user + "/Seguro Viagem/" + "Export Mapa Viagem "+ Ddata + ".pdf";
            
            OutputStream outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);
            
//            MyFooter event = new MyFooter();
//            writer.setPageEvent(event);
            
            documento.open();
            dataTableDados(documento,dataInicio, dataFim);
            documento.close();
            
            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
            
//           PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(),ff.getAbsolutePath(), 0, 595f,842f,"Enviar Para o OneNote 2013",0); 
           //PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",0); 
             
//            printPdf.print();
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dataTableTitile(Document documento, String data) throws IOException, BadElementException, DocumentException {
        
       try {
           pTableEmpresaPricipal = new PdfPTable(new float[]{15f, 85f});
           pTableEmpresaInforImpres1 = new PdfPTable(1);
           pTableEmpresaInforImpres5 = new PdfPTable(1);
           
           PdfPCell pCellNomeEmpresa = new PdfPCell(new Phrase(Empresa.NOME, fontCabecalhoNG));
           pCellNomeEmpresa.setBorder(0);
           PdfPCell pCellNomeEndereco = new PdfPCell(new Phrase(Empresa.ENDERECO, fontCabecalhoN));
           pCellNomeEndereco.setBorder(0);
           PdfPCell pCellCaixaPostal = new PdfPCell(new Phrase(Empresa.CAIXAPOSTAL, fontCabecalhoN));
           pCellCaixaPostal.setBorder(0);
           PdfPCell pCellTeleFax = new PdfPCell(new Phrase(Empresa.TELEFAX + " " + ConfigDoc.Empresa.EMAIL, fontCabecalhoN));
           pCellTeleFax.setBorder(0);
           PdfPCell pCellSociedade = new PdfPCell(new Phrase(Empresa.SOCIEDADE, fontCabecalhoN));
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
           
           SimpleDateFormat sdfEsp = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
           SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
           PdfPCell cellTitulo = new PdfPCell(new Phrase("TOTAL PREMIUM COLLECTED ON TRAVEL INSURANCE AND TAXES FOR "+sdfEsp.format(sdf.parse(data)).toUpperCase(), fontCorpoNG));
           cellTitulo.setBorder(0);
           cellTitulo.setPaddingBottom(10f);
           pTableTitulo.addCell(cellTitulo);
           
           pTableEmpresaPricipal.setHorizontalAlignment(Element.ALIGN_CENTER);
           
           documento.add(pTableEmpresaPricipal);
           documento.add(pTableNull);
           documento.add(pTableTitulo);
       } catch (ParseException ex) {
           Logger.getLogger(ExporOnlyViagemPdf.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    private void dataTableDados(Document documento, Date dataInicio, Date dataFim) throws IOException, BadElementException, DocumentException {

        PdfPTable pTableDate;

        int i = 0, p = 0;
        dataViagem(dataInicio, dataFim);
        for (Map.Entry<String, ArrayList<HashMap<String, Object>>> data : hasList.entrySet()) {

            totalSelo = 0f;
            totalConsumo = 0f;
            totalTotal = 0f;
            totalPremio = 0f;
            totalComissao = 0f;
            
            pTableDate = new PdfPTable(new float[]{3f, 6.85f, 10.46f, 6.60f, 6.60f, 3.90f, 24.09f, 6.75f, 6.85f, 5.80f, 5.80f, 5.80f, 7.55f});
            dataTableTitile(documento, toString(data.getKey()));

            pTableDate.setWidthPercentage(100);
            for (int j = 0; j < 13; j++) {
                PdfPCell cellDados = new PdfPCell(funcaoTitulo(j));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);
            }
            
            for (HashMap<String, Object> hashMap : data.getValue()) {

                PdfPCell cellDados = new PdfPCell(new Phrase(i + "", fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(toString(hashMap.get(DATA)), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);

                //---
                cellDados = new PdfPCell(new Phrase(toString(hashMap.get(NUMEROAPOLICE)), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(ConfigDoc.toFormat(toString(hashMap.get(INICIO)), "dd-MM-yyyy", "yyyy-MM-dd"), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);

                //---
                cellDados = new PdfPCell(new Phrase(ConfigDoc.toFormat(toString(hashMap.get(FIM)), "dd-MM-yyyy", "yyyy-MM-dd"), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(toString(hashMap.get(DIAS)), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);

                //----
                cellDados = new PdfPCell(new Phrase(toString(hashMap.get(CLIENTE)), fontCorpoTable));
                pTableDate.addCell(cellDados);

                cellDados = new PdfPCell(new Phrase(toString(hashMap.get(RECIBO)), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableDate.addCell(cellDados);

                //---
                totalPremio += toFloat(toString(hashMap.get(PREMIO)));
                cellDados = new PdfPCell(new Phrase(ConfigDoc.toMoeda(toString(hashMap.get(PREMIO)), ""), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                pTableDate.addCell(cellDados);

                totalComissao += toFloat(toString(hashMap.get(COMISSAO)));
                cellDados = new PdfPCell(new Phrase(ConfigDoc.toMoeda(toString(hashMap.get(COMISSAO)), ""), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                pTableDate.addCell(cellDados);

                //--
                totalConsumo += toFloat(toString(hashMap.get(CONSUMO)));
                cellDados = new PdfPCell(new Phrase(ConfigDoc.toMoeda(toString(hashMap.get(CONSUMO)), ""), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                pTableDate.addCell(cellDados);

                totalSelo += toFloat(toString(hashMap.get(SELO)));
                cellDados = new PdfPCell(new Phrase(ConfigDoc.toMoeda(toString(hashMap.get(SELO)), ""), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                pTableDate.addCell(cellDados);

                //---
//                cellDados = new PdfPCell(new Phrase(toString(hashMap.get(NETOUT)), fontCorpoTable));
//                pTableDate.addCell(cellDados);

                totalTotal += toFloat(toString(hashMap.get(TOTAL)));
                cellDados = new PdfPCell(new Phrase(ConfigDoc.toMoeda(toString(hashMap.get(TOTAL)), ""), fontCorpoTable));
                cellDados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                pTableDate.addCell(cellDados);
                i++;

            }
            radapeTable(pTableDate, documento);
            documento.newPage();
        }
    }

    private void radapeTable(PdfPTable pTableDate, Document documento) throws DocumentException {
        PdfPCell cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        cellRodape.setColspan(2);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(ConfigDoc.toMoeda(totalPremio, ""), false, false,Element.ALIGN_RIGHT));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(ConfigDoc.toMoeda(totalComissao, ""), false, false,Element.ALIGN_RIGHT));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(ConfigDoc.toMoeda(totalConsumo, ""), false, false,Element.ALIGN_RIGHT));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        cellRodape = new PdfPCell(cellRodape(ConfigDoc.toMoeda(totalSelo, ""), false, false,Element.ALIGN_RIGHT));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
        pTableDate.addCell(cellRodape);
        
//        cellRodape = new PdfPCell(cellRodape(" ", false, false,-1));
//        cellRodape.setBorder(PdfPCell.NO_BORDER);
//        setStyleRodape(cellRodape);
//        pTableDate.addCell(cellRodape);
        
        cellRodape = new PdfPCell(cellRodape(ConfigDoc.toMoeda(totalTotal, ""), false, false,Element.ALIGN_RIGHT));
        cellRodape.setBorder(PdfPCell.NO_BORDER);
        setStyleRodape(cellRodape);
//        cellRodape.setBorderWidthBottom(0.5f);
//        cellRodape.setBorderWidthRight(0.5f);
//        cellRodape.setPadding(2f);
//        cellRodape.setPaddingLeft(0.8f);
        pTableDate.addCell(cellRodape);
        
        documento.add(pTableDate);
    }

    private void setStyleRodape(PdfPCell cellRodape) {
        cellRodape.setBorderWidthBottom(0.5f);
        cellRodape.setPadding(2f);
        cellRodape.setPaddingLeft(0.8f);
        cellRodape.setPaddingRight(0.8f);
    }
        
    private Phrase funcaoTitulo(int i) {
        String txt;
        Font fontcabecatable = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
       switch (i) 
       {
           case 0:txt="S/N";break;
           case 1:txt="DATA";break;
           case 2:txt="APOLICE";break;
           case 3:txt="DATA INICIO";break;
           case 4:txt="DATA FIM";break;
           case 5:txt="NO. DIAS";break;
           case 6:txt="NOME";break;
           case 7:txt="RECEIPT NO.";break;
           case 8:txt="EA PREM";break;
           case 9:txt="NICON COMISSÃO";break;
           case 10:txt="5% IMPOSTO";break;
           case 11:txt="0.60% SELO";break;
//           case 12:txt="NET OUT OF TAX";break;
           default:txt="TOTAL"/*"NET OUT OF TAX"*/;break;
       }
        
        Phrase rt = new Phrase(txt,fontcabecatable);
        return rt; 
    }
    public static void main(String[] args) {
        ExporOnlyViagemPdf aPG= new ExporOnlyViagemPdf();
        aPG.criarDoc("ah",null,null);
    }
    class MyFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance("logo.png");
                image.scaleAbsolute(PageSize.A4.rotate());
                image.scaleToFit(700f, 500f);
                image.setAbsolutePosition(document.getPageSize().getWidth() - 625, 50);
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.2f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private PdfPTable cellRodape(String value, boolean l,boolean r,int align)
    {
        Font fontCorpoTableO= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,7.5f);
        PdfPTable pTable = new PdfPTable(1);
        pTable.setWidthPercentage(100f);
        PdfPCell cellValue = new PdfPCell(new Phrase(value,fontCorpoTableO));
        if(l){cellValue.setBorderWidthLeft(0);}
        if(r){cellValue.setBorderWidthRight(0);}
       switch (align) 
       {
           case Element.ALIGN_RIGHT:cellValue.setHorizontalAlignment(Element.ALIGN_RIGHT);break;
           case Element.ALIGN_LEFT:cellValue.setHorizontalAlignment(Element.ALIGN_LEFT);break;
           case Element.ALIGN_CENTER:cellValue.setHorizontalAlignment(Element.ALIGN_CENTER);break;
           default:break;
       }
        pTable.addCell(cellValue);
        return pTable;
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
