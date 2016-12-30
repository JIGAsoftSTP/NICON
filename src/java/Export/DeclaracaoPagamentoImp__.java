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
import com.itextpdf.text.Paragraph;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.ModeloPagamento;

/**
 *
 * @author AhmedJorge
 */
public class DeclaracaoPagamentoImp__ {

    private String reString;
    public String criarDoc
        (
            String numApolice,
            String numCliente,String user, String arquivo,String segruro,
            ArrayList<ModeloPagamento> list, String moeda
        )
    {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("MMMM yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdf3= new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf2= new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdf1= new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            
            Font fontCabecalhoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f );
            Font fontCorpo= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoP= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,7f );
            Font fontCorpoPe= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,2f );
            Font fontCorpoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f );
            Font fontCorpoNGT= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED , 10f ,Font.UNDERLINE);
            Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f ,Font.UNDERLINE);
            
            PdfPTable pTableNull= new PdfPTable(1);
            PdfPCell cellNull= new PdfPCell(new Phrase(" ",fontCorpoP));
            cellNull.setBorder(0);
            pTableNull.addCell(cellNull);
            
            PdfPTable pTableEmpresaPricipal = new PdfPTable( new float[]{100});
            pTableEmpresaPricipal.setTotalWidth(250f);
            
            PdfPCell cellEmpresaPricipal = new PdfPCell();
            Paragraph pSenhor = new Paragraph("Exmo. Senhor", fontCorpoNG);
            Paragraph pDiretor = new Paragraph("Diretor Geral de "+Empresa.NOME, fontCorpoNG);
            Paragraph pLocal = new Paragraph(Empresa.ENDERECO, fontCorpoNG);
            cellEmpresaPricipal.setBorder(0);
            cellEmpresaPricipal.addElement(pSenhor);
            cellEmpresaPricipal.addElement(pDiretor);
            cellEmpresaPricipal.addElement(pLocal);
            cellEmpresaPricipal.setPaddingRight(20);
            pTableEmpresaPricipal.addCell(cellEmpresaPricipal);
            
            PdfPTable pTableTextoPrin = new PdfPTable(new float[]{100});
            PdfPCell cellTextoPrin = new PdfPCell(new Phrase("Devido a falta de disponibilidade imediata, venho a V. Excia, o pagamento do "
                    + "seguro de "+segruro+" com Apolice Nº "+numApolice+", seguindo as seguistes modalidades:", fontCorpo));
            cellTextoPrin.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellTextoPrin.setBorder(0);
            pTableTextoPrin.addCell(cellTextoPrin);
            
            PdfPTable pTablePestacao = new PdfPTable( new float[]{75f,35f});
            PdfPCell cellDataPrestacao,cellValorPrestacao;
            
            Double total = 0.0;
            
            for (int i=0;i<list.size(); i++) 
            {
                cellDataPrestacao = new PdfPCell(new Phrase(ConfigDoc.getPestação((i+1)) +"Pestacão "+sdf.format(list.get(i).getDataPagamentoLimite()), fontCorpo));
                cellDataPrestacao.setPaddingBottom(5f);
                cellDataPrestacao.setPaddingTop(5f);
                cellDataPrestacao.setPaddingLeft(20f);
                cellDataPrestacao.setBorder(0);
                cellDataPrestacao.setBorderWidthBottom(0.5f);
                
                total += list.get(i).getValorPagameto();
                        
                cellValorPrestacao = new PdfPCell(new Phrase(moeda+" "+list.get(i).getDataPagamentoLimiteView(), fontCorpo));
                cellValorPrestacao.setPaddingBottom(7f);
                cellValorPrestacao.setPaddingTop(7f);
                cellValorPrestacao.setPaddingRight(20f);
                cellValorPrestacao.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellValorPrestacao.setBorder(0);
                cellValorPrestacao.setBorderWidthBottom(0.5f);
                
                pTablePestacao.addCell(cellDataPrestacao);
                pTablePestacao.addCell(cellValorPrestacao);
            }
            cellDataPrestacao = new PdfPCell(new Phrase("Total", fontCorpoN));
            cellDataPrestacao.setPaddingBottom(7f);
            cellDataPrestacao.setPaddingTop(7f);
            cellDataPrestacao.setPaddingLeft(20f);
            cellDataPrestacao.setBorder(0);
            cellDataPrestacao.setBorderWidthBottom(1f);

            cellValorPrestacao = new PdfPCell(new Phrase(moeda+" "+Moeda.format(total), fontCorpoN));
            cellValorPrestacao.setPaddingBottom(7f);
            cellValorPrestacao.setPaddingTop(7f);
            cellValorPrestacao.setPaddingRight(20f);
            cellValorPrestacao.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellValorPrestacao.setBorder(0);
            cellValorPrestacao.setBorderWidthBottom(1f);
            
            pTablePestacao.addCell(cellDataPrestacao);
            pTablePestacao.addCell(cellValorPrestacao);
            
            PdfPTable pTableTextoNota = new PdfPTable(new float[]{100});
            PdfPCell cellTextoNota = new PdfPCell();
            Paragraph pNota = new Paragraph("Por ser verdade passei esta declaração que vai por mim assinada.",fontCorpo);
            Paragraph pData = new Paragraph("S.Tomé, "+sdf2.format( new Date()),fontCorpo);
            Paragraph pNull = new Paragraph(" ", fontCorpoPe);
            pNota.setAlignment(Element.ALIGN_JUSTIFIED);
            cellTextoNota.addElement(pNota);
            cellTextoNota.addElement(pNull);
            cellTextoNota.addElement(pData);
            cellTextoNota.setPaddingBottom(10f);
            cellTextoNota.setBorder(0);
            pTableTextoNota.addCell(cellTextoNota);
            
            PdfPTable pTableAssinatura = new PdfPTable(new float[]{100});
            pTableAssinatura.setTotalWidth(200f);
            
            PdfPCell cellAssinatura = new PdfPCell();
            cellAssinatura.setBorder(0);
            Paragraph assinatora = new Paragraph("Assinatura", fontCorpoN);
            assinatora.setAlignment(Element.ALIGN_CENTER);
            Paragraph espaco = new Paragraph(" ", fontCorpoN);
            Paragraph linha = new Paragraph("______________________________________", fontCorpoN);
            linha.setAlignment(Element.ALIGN_CENTER);
            
            cellAssinatura.addElement(assinatora);
            cellAssinatura.addElement(espaco);
            cellAssinatura.addElement(linha);
            
            pTableAssinatura.addCell(cellAssinatura);
                    
            Document documento= new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 160f, 220f);
            
            File ff= new File(arquivo+"/"+user+"/Pagamentos/");
            
            ff.mkdirs();
            String Ddata=sdf1.format(new Date());
            ff =new File(ff.getAbsoluteFile()+"/"+"Declaração "+Ddata+".pdf");
            
            reString ="../Documentos/"+user+"/Pagamentos/"+"Declaração "+Ddata+".pdf";
           
            OutputStream outputStraem = new FileOutputStream(ff);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);
            
            MyFooter event = new MyFooter();
            writer.setPageEvent(event);
            
            documento.open();
            pTableEmpresaPricipal.writeSelectedRows(-1, 2, 330, 770, writer.getDirectContent());
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableTextoPrin);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTablePestacao);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableTextoNota);
            pTableAssinatura.writeSelectedRows(-1, 2, 300, 160, writer.getDirectContent());
            documento.close();
            
//           PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Enviar Para o OneNote 2013",1); 
//           //PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",1); 
//             
//            printPdf.print();
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    }
    class MyFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance("logo.png");
                image.scaleAbsolute(PageSize.A4.rotate());
                image.scaleToFit(700f, 500f);
                image.setAbsolutePosition(document.getPageSize().getWidth() - 495, 170);
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
    public static void main(String[] args) {
        new DeclaracaoPagamentoImp__().criarDoc("fhfhhf", "35", "AH", "ddddd", "Viagem", new ArrayList<>(),"std");
    }
   
}
