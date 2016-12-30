/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import static Export.ConfigDoc.Fontes.getDiretorio;
import com.itextpdf.text.BadElementException;
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
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import conexao.Call;
import dao.UtilitarioDao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AhmedJorge
 */
public class ExportMapaProducao__ implements Serializable
{
    public static String DATA="DATA", NUMAPOLICE ="NUM APOLICE" , CLIENTESEGURO= "CLIENTE",  MOEDA="MOEDA",
            PREMIO="PREMIO", IMPOSTOCONSUMO="IMPOSTO CONSUMO", IMPOSTOSELO="IMPOSTO SELO",
               SEGURO="SEGURO", VALORTOTAL="VALOR TOTAL", FGA="FGA";
    
    ArrayList<Producao> list;
    UtilitarioDao ud = new UtilitarioDao();
//    String lastSeguro = "";
    HashMap<String,ArrayList<Producao>> hasList= new  HashMap<>();
    private int a;
    
   public String criarDoc(String user,Date dataInicio, Date dataFim)
    {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            SimpleDateFormat sdfPT= new SimpleDateFormat("dd-MM-yyyy");
            
            Font fontCabecalhoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,10f );
            Font fontCorpo= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f );
            Font fontCorpoTable= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoBP= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,8.5f );
            Font fontCorpoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f );
            Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f ,Font.UNDERLINE);
            
            Document documento= new Document();
            documento.setPageSize(PageSize.A4.rotate());
            documento.setMargins(10f, 10f, 35f, 80f);
            
            File ff= new File(getDiretorio()+"/"+user+"/Relatorio/");
            ff.mkdirs();
            
            String stringData = sdf.format(new Date()); 
            
            ff =new File(ff.getAbsoluteFile()+"/"+"Export Mapa Producao "+stringData+".pdf");
            
            String reString ="../Documentos/"+user+"/Relatorio/"+"Export Mapa Producao "+stringData+".pdf";
            
            OutputStream outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);
            
            MyFooter event = new MyFooter();
            writer.setPageEvent(event);
            documento.open();
            
            PdfPTable pTableEmpresaPricipal= new PdfPTable(new float[]{10f,90f});
            PdfPTable pTableEmpresaInforImpres1= new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres5= new PdfPTable(1);
            
            PdfPTable pTableNull= new PdfPTable(1);
            PdfPCell cellNull= new PdfPCell(new Phrase(" ",fontCorpo));
            cellNull.setBorder(0);
            pTableNull.addCell(cellNull);
            
            PdfPCell pCellNomeEmpresa= new PdfPCell(new Phrase(Empresa.NOME,fontCabecalhoNG));
            pCellNomeEmpresa.setBorder(0);
            
            PdfPCell pCellNomeEndereco= new PdfPCell(new Phrase(Empresa.ENDERECO,fontCabecalhoN));
            pCellNomeEndereco.setBorder(0);
            
            PdfPCell pCellCaixaPostal= new PdfPCell(new Phrase(Empresa.CAIXAPOSTAL,fontCabecalhoN));
            pCellCaixaPostal.setBorder(0);
            
            PdfPCell pCellTeleFax= new PdfPCell(new Phrase(Empresa.TELEFAX+" "+ConfigDoc.Empresa.EMAIL,fontCabecalhoN));
            pCellTeleFax.setBorder(0);
            
            PdfPCell pCellSociedade= new PdfPCell(new Phrase(Empresa.SOCIEDADE,fontCabecalhoN));
            pCellSociedade.setBorder(0);            
            
            Image imageEmpresa= Image.getInstance("logo.png");
            imageEmpresa.scaleToFit(120f, 85f);
            
            pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
            pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
            pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
            pTableEmpresaInforImpres1.addCell(pCellTeleFax);
            pTableEmpresaInforImpres1.addCell(pCellSociedade);
            
            PdfPCell cellTabela3= new PdfPCell(pTableEmpresaInforImpres1);
            cellTabela3.setBorder(0);
            
            pTableEmpresaInforImpres5.addCell(cellTabela3);
                     
            PdfPCell cellTabela5= new PdfPCell(pTableEmpresaInforImpres5);
            cellTabela5.setBorder(0);
             
            PdfPCell cellTabela6= new PdfPCell(imageEmpresa);
            cellTabela6.setBorder(0);
            pTableEmpresaPricipal.setWidthPercentage(95);
            pTableEmpresaPricipal.addCell(cellTabela6);
            pTableEmpresaPricipal.addCell(cellTabela5);
            
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);
            
            PdfPTable pptTitileMapa = new PdfPTable(new float[]{100});
            pptTitileMapa.setWidthPercentage(95);
            PdfPCell cellTitileMapa = new PdfPCell( new Phrase("Mapa de produção de ".toUpperCase()+
                    ((dataInicio!=null)?sdfPT.format(dataInicio)+" à ":" dos Ultimos anos àte hoje".toUpperCase())
                    +((dataFim==null)?"":sdfPT.format(dataFim)),fontCorpoNG));
            cellTitileMapa.setBorder(0);
            cellTitileMapa.setHorizontalAlignment(Element.ALIGN_CENTER);
            pptTitileMapa.addCell(cellTitileMapa);
            documento.add(pptTitileMapa);
            documento.add(pTableNull);
            
            ResultSet rs = ud.relatorioSeguroForImpresao(dataInicio, dataFim);
            Consumer <HashMap<String, Object>> act  = (map)->
            {
                list = new ArrayList<>();
                putNewDado(map,dataInicio, dataFim); 
            };
            Call.forEchaResultSet(act, rs);

            int f=0;
            for (Map.Entry<String, ArrayList<Producao>> al : hasList.entrySet()) 
            {
                if(f>0)
                {
                    documento.add(pTableNull);
                    documento.add(pTableNull);
                }
                f++;
                PdfPTable pptTitulo = new PdfPTable(new float[]{100});
                pptTitulo.setWidthPercentage(95);

                PdfPCell cellTitulo = new PdfPCell( new Phrase(al.getKey().toUpperCase(), fontCorpoNG));
                cellTitulo.setBorder(0);
                pptTitulo.addCell(cellTitulo);
               
                documento.add(pptTitulo);
                documento.add(pTableNull);
                
                PdfPTable pTableDate= HeadTablePrincipal();
                documento.add(pTableDate);

                for (Producao pro:al.getValue()) {
                    pTableDate = new PdfPTable( new float[]{9.7f,28.8f,14.7f,10.7f,10.7f,10.7f,14.7f});
                    pTableDate.setWidthPercentage(95);
                    if (!pro.DATA.equals("SOMATORIO"))
                    {
                        newDado(pro.NUMAPOLICE, fontCorpoTable, pTableDate, documento, Element.ALIGN_LEFT,0.5f);
                        newDado(pro.CLIENTESEGURO, fontCorpoTable, pTableDate, documento, Element.ALIGN_LEFT,0.5f);
                        priencherTable(pro, fontCorpoTable, pTableDate, documento,0.5f);
                    } else 
                    {
                        PdfPTable pTableDate2= rodapeTabelaPrincipal();
                        newDado(("TOTAL "+al.getKey()).toUpperCase(), fontCorpoN, pTableDate2, documento, Element.ALIGN_LEFT,1.5f);
                        priencherTable(pro, fontCorpoBP, pTableDate2, documento,1.5f);
                    }
                }
            }
            
            PdfPTable pTableAssinatura = new PdfPTable(new float[]{50f,50f});
            pTableAssinatura.setTotalWidth(700f);
            
            PdfPCell cellAssinatura = new PdfPCell();
            cellAssinatura.setBorder(0);
            Paragraph assinatora = new Paragraph("DIRETOR TECNICO", fontCorpoN);
            assinatora.setAlignment(Element.ALIGN_CENTER);
            Paragraph espaco = new Paragraph(" ", fontCorpoN);
            Paragraph linha = new Paragraph("______________________________________", fontCorpoN);
            linha.setAlignment(Element.ALIGN_CENTER);
            
            cellAssinatura.addElement(assinatora);
            cellAssinatura.addElement(espaco);
            cellAssinatura.addElement(linha);
            
            pTableAssinatura.addCell(cellAssinatura);
            
            cellAssinatura = new PdfPCell();
            cellAssinatura.setBorder(0);
            assinatora = new Paragraph("DIRETORA GERAL", fontCorpoN);
            assinatora.setAlignment(Element.ALIGN_CENTER);
            linha.setAlignment(Element.ALIGN_CENTER);
            
            cellAssinatura.addElement(assinatora);
            cellAssinatura.addElement(espaco);
            cellAssinatura.addElement(linha);
            
            pTableAssinatura.addCell(cellAssinatura);
            
            
            pTableAssinatura.writeSelectedRows(-1, 2, 70, 80, writer.getDirectContent());
            documento.close();
            

//           PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Enviar Para o OneNote 2013",0); 
           //PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",0); 
//            printPdf.print();
    return reString;
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void priencherTable(Producao pro, Font fontCorpoTable, PdfPTable pTableDate, Document documento, float border) throws DocumentException {
        newDado(pro.PREMIO + " " + pro.MOEDA, fontCorpoTable, pTableDate, documento, Element.ALIGN_RIGHT,border);
        newDado(pro.IMPOSTOCONSUMO + " " + pro.MOEDA, fontCorpoTable, pTableDate, documento, Element.ALIGN_RIGHT,border);
        newDado(pro.IMPOSTOSELO + " " + pro.MOEDA, fontCorpoTable, pTableDate, documento, Element.ALIGN_RIGHT,border);
        newDado(pro.FGA + " " + pro.MOEDA, fontCorpoTable, pTableDate, documento, Element.ALIGN_RIGHT,border);
        newDado(pro.VALORTOTAL + " " + pro.MOEDA, fontCorpoTable, pTableDate, documento, Element.ALIGN_RIGHT,border);
    }

    public void newDado(String dados, Font fontCorpoTable, PdfPTable pTableDate, Document documento,int align, float border) throws DocumentException {
        PdfPCell cellDados;
        cellDados= new PdfPCell(new Phrase(dados,fontCorpoTable));
        cellDados.setHorizontalAlignment(align);
        cellDados.setBorderWidth(border);
        pTableDate.addCell(cellDados);
        documento.add(pTableDate);
    }

    public void putNewDado(HashMap<String, Object> map,Date dataInicio,Date dataFim) {
        
        list = new ArrayList<>();
        ResultSet rs = ud.relatorioPromocaoTipo(dataInicio, dataFim,Integer.valueOf(map.get("ID").toString()),2);
        Consumer <HashMap<String, Object>> act  = (map1)->
        {
            list.add(new Producao( stringValue(map1.get(DATA)),
            stringValue(map1.get(NUMAPOLICE)),stringValue(map1.get(CLIENTESEGURO)),stringValue(map1.get(MOEDA)), stringValue(map1.get(PREMIO)), 
            stringValue(map1.get(IMPOSTOCONSUMO)), stringValue(map1.get(IMPOSTOSELO)), stringValue(map1.get(VALORTOTAL)),stringValue(map1.get(SEGURO))
            ,stringValue(map1.get(FGA))));
        };
        Call.forEchaResultSet(act, rs);
        hasList.put(stringValue(map.get("NOME")), list);
    }

    public PdfPTable rodapeTabelaPrincipal() {
        PdfPTable pTableDate2= new PdfPTable(new float[]{38.5f,14.7f,10.7f,10.7f,10.7f,14.7f});
        pTableDate2.setWidthPercentage(95);
        return pTableDate2;
    }

    public PdfPTable HeadTablePrincipal() {
        PdfPTable pTableDate= new PdfPTable(new float[]{9.7f,28.8f,14.7f,10.7f,10.7f,10.7f,14.7f});
        pTableDate.setWidthPercentage(95);
        for (int i = 0; i < 7; i++) 
        {
            PdfPCell cellDados= new PdfPCell(funcaoTitulo(i));
            cellDados.setHorizontalAlignment(a);
            cellDados.setBorderWidth(0.85f);
            pTableDate.addCell(cellDados); 
        }
        return pTableDate;
    }
        
    private Phrase funcaoTitulo(int i) {
        String txt;
        Font fontcabecatable =  FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,10f );
        switch (i)
        {
            case 0:txt="Nr. Factura";break;
            case 1:txt="Nome do Segurado"; break;
            case 2:txt="Prémio";break;
            case 3:txt="Imposto 6%";break;
            case 4:txt="Imposto 5%";break;
            case 5:txt="FGA 2.6%";break;
            default:txt="TOTAL";break;
        }
        
        a=com.itextpdf.text.Element.ALIGN_CENTER;
        Phrase rt = new Phrase(txt,fontcabecatable);
        return rt; 
    }
    public static void main(String[] args) {
        ExportMapaProducao__ aPG= new ExportMapaProducao__();
        aPG.criarDoc("ah", null,null);
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
    class MyFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance("logo.png");
                image.scaleAbsolute(PageSize.A4.rotate());
                image.scaleToFit(700f, 500f);
                image.setAbsolutePosition(document.getPageSize().getWidth() - 625, 30);
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
}
