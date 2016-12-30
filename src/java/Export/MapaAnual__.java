
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Contrato;


public class MapaAnual__ implements Serializable
    {
     private int a;
        public void criarDoc
            (
                String numApolice,
                String numCliente,
                Contrato c,
                String user
            )
              {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
            
            Font fontCabecalhoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f );
            Font fontCorpo= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f );
            Font fontCorpoTable= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoP= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f );
            Font fontCorpoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f);
            Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f,Font.UNDERLINE);
            
            PdfPTable pTableEmpresaPricipal= new PdfPTable(new float[]{10f,90f});
            PdfPTable pTableEmpresaInforImpres1= new PdfPTable(1);
//            PdfPTable pTableEmpresaInforImpres2= new PdfPTable(1);
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
            
            PdfPTable pTableDate= new PdfPTable
            (new float[]
            {
                3f,//1
                3.50f,//2
                6.85f,//3
                20.40f,//4
                6.90f,//5
                7.45f,//6
                8f//7

            }
            );
            pTableDate.setWidthPercentage(95);
            for (int i = 0; i < 7; i++) {
                PdfPCell cellDados= new PdfPCell(funcaoTitulo(i));
                cellDados.setHorizontalAlignment(a);
                pTableDate.addCell(cellDados);
                
            }
            for (int i = 0; i < 5 ; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    PdfPCell cellDados= new PdfPCell(new Phrase("LUGARES VISTOS"+j,fontCorpoTable));
                    pTableDate.addCell(cellDados);
                }
            }
            
           
            
            Document documento= new Document();
            documento.setPageSize(PageSize.A4.rotate());
            documento.setMargins(10f, 10f, 35f, 5f);
            
            File ff= new File("Documentos/"+user+"/Relatorio/");
            ff.mkdirs();
            ff =new File(ff.getAbsoluteFile()+"/"+"Export Premio Coletado "+sdf.format(new Date())+".pdf");
            
            OutputStream outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);
            
            MyFooter event = new MyFooter();
            writer.setPageEvent(event);
            
            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);
            documento.add(pTableDate);
            documento.close();
     
//           PrintPdf printPdf = new PrintPdf("ExportPdf.pdf", "ExportPdf.pdf", 0, 595f,842f,"Enviar Para o OneNote 2013",0); 
           //PrintPdf printPdf = new PrintPdf("Certificado Veiculo.pdf", "Certificado Veiculo.pdf", 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",1); 
             
//            printPdf.print();

        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    private Phrase funcaoTitulo(int i) {
        String txt;
        Font fontcabecatable = new Font(Font.FontFamily.COURIER, 9, Font.BOLD);
        if(i==0){txt="S/N";}
        else if(i==1){txt="DATA";}
        else if(i==2){txt="APOLICE";}
        else if(i==3){txt="NOME CLIENTE";}
        else if(i==4){txt="NUMERO DEBITO";}
        else if(i==5){txt="BANCO";}
        else {txt="QUANTIA";}

        a=com.itextpdf.text.Element.ALIGN_CENTER;
        Phrase rt = new Phrase(txt,fontcabecatable);
        return rt; 
    }
    
    public static void main(String[] args) {
        MapaAnual__ aPG= new MapaAnual__();
        aPG.criarDoc("ddhd", "223",new Contrato(),"ah");
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
}

