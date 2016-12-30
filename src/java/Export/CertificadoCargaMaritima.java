/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import bean.CargaMaritimaBean;
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
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Contrato;

/**
 *
 * @author AhmedJorge
 */
public class CertificadoCargaMaritima implements Serializable
{
    private String reString;
//    private final String FONT = "/Font/times.ttf";
     public String criarDoc
        (
            String numApolice,
            String numCliente,
            Contrato c,
            CargaMaritimaBean cm,
            String user,
            String moeda,
            String arquivo
        )
    {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            
            Font fontCabecalhoN=  FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED , 9.5f);
            Font fontCorpo=  FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED , 9f);
            Font fontCorpoP= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED , 8f);
            Font fontCorpoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f);
            Font fontCorpoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,10f);
            Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED , 16f,Font.UNDERLINE);
            Font fontUK= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED , 5.5f,Font.ITALIC);
            
            PdfPTable pTableEmpresaPricipal= new PdfPTable(new float[]{25f,75f});
            PdfPTable pTableEmpresaInforImpres1= new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres2= new PdfPTable(1);
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
            
            PdfPCell pCellPolice= new PdfPCell(new Phrase(Empresa.APOLICE+numApolice,fontCabecalhoN));
            pCellPolice.setBorder(0);
            
            
            Image imageEmpresa= Image.getInstance("logo.png");
            imageEmpresa.scaleToFit(120f, 85f);
            
            pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
            pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
            pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
            pTableEmpresaInforImpres1.addCell(pCellTeleFax);
            pTableEmpresaInforImpres1.addCell(pCellSociedade);
            
            pTableEmpresaInforImpres1.addCell(pCellPolice);
            
            PdfPCell cellTabela3= new PdfPCell(pTableEmpresaInforImpres1);
            cellTabela3.setBorder(0);
            
            pTableEmpresaInforImpres5.addCell(cellTabela3);
                     
            PdfPCell cellTabela5= new PdfPCell(pTableEmpresaInforImpres5);
            cellTabela5.setBorder(0);
             
            PdfPCell cellTabela6= new PdfPCell(imageEmpresa);
            cellTabela6.setBorder(0);
            
            pTableEmpresaPricipal.addCell(cellTabela6);
            pTableEmpresaPricipal.addCell(cellTabela5);
            

            PdfPTable pTableTitulo= new PdfPTable(1);
            Phrase pTitulo= new Phrase("CErtificado Seguro de Carga Maritima".toUpperCase(),fontCorpoNG);
            pTitulo.add(new Phrase("\nMARINE CARGO CERTIFICATE", fontUK));
            PdfPCell cellTitulo= new PdfPCell(pTitulo);
            cellTitulo.setBorder(0);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableTitulo.addCell(cellTitulo);
            
            PdfPTable pTableCorpoUm= new PdfPTable(new float[]{100});
            PdfPTable pTableCorpoDois= new PdfPTable(new float[]{50,50});
            PdfPTable pTableCorpoTres= new PdfPTable(new float[]{100});
            
            PdfPCell cellCorpopUm= new PdfPCell();
            cellCorpopUm.setBorder(0);
            PdfPCell cellCorpopDois1= new PdfPCell();
            cellCorpopDois1.setBorder(0);
            PdfPCell cellCorpopDois2= new PdfPCell();
            cellCorpopDois2.setBorder(0);
            PdfPCell cellCorpopTres= new PdfPCell();
            cellCorpopTres.setBorder(0);
            
            Paragraph pCorpoUm1 = new Paragraph("Certificado de seguro emitido sob contracto aberto nº".toUpperCase(),fontCorpoN);
            pCorpoUm1.add(new Phrase("\ncertificate OF insurance issued under contract open No.".toUpperCase(),fontUK));
            Paragraph pCorpoUm2 = new Paragraph("Âmbito da cobertura: ".toUpperCase(),fontCorpoN);
            pCorpoUm2.add(new Phrase("\nSCOPE OF COVER",fontUK));
            Paragraph pCorpoUm3 = new Paragraph("Este certificado atesta que a Companhia tem as mencionadas, sob seguro para viagem (s) e valore (s) declarados em favor de",fontCorpo);
            pCorpoUm3.add(new Phrase("\nTHIS IS TO CERTIFY THAT THE COMPANY HAS INSURED THE UNDER MENTIONED GOODS FOR THE VOYGE (S) AND VALUE (S) STATED ON BEHALF OF",fontUK));
            
            cellCorpopUm.addElement(pCorpoUm1);
            cellCorpopUm.addElement(pCorpoUm2);
            cellCorpopUm.addElement(pCorpoUm3);
            pTableCorpoUm.addCell(cellCorpopUm);
            
            Paragraph pCorpoDois11 = new Paragraph("Taxa Maritima: ",fontCorpo);
            pCorpoDois11.add(new Phrase("\nMARINE RATE",fontUK));
            Paragraph pCorpoDois12 = new Paragraph("Taxa Guerra: ",fontCorpo);
            pCorpoDois12.add(new Phrase("\nWAR RATE",fontUK));
            
            cellCorpopDois1.addElement(pCorpoDois11);
            cellCorpopDois1.addElement(pCorpoDois12);
            pTableCorpoDois.addCell(cellCorpopDois1);
            
            cellCorpopUm.addElement(pCorpoUm1);
            cellCorpopUm.addElement(pCorpoUm2);
            
            Paragraph pCorpoDois21 = new Paragraph("Nota de Bebito: ",fontCorpo);
            pCorpoDois21.add(new Phrase("\nDEBIT NOTE NO",fontUK));
            Paragraph pCorpoDois22 = new Paragraph("Total de Prémio: ",fontCorpo);
            pCorpoDois22.add(new Phrase("\nGROSS PREMIUM",fontUK));
            
            cellCorpopDois2.addElement(pCorpoDois21);
            cellCorpopDois2.addElement(pCorpoDois22);
            pTableCorpoDois.addCell(cellCorpopDois2);
            
            Paragraph pCorpoTres1 = new Paragraph("Taxa Superintendente: ",fontCorpo);
            pCorpoTres1.add(new Phrase("\nSuperintendent RATE".toUpperCase(),fontUK));
            Paragraph pCorpoTres2 = new Paragraph("Taxa Interna de Trânsito: ",fontCorpo);
            pCorpoTres2.add(new Phrase("\nINLANDTraffic Rate",fontUK));
            Paragraph pCorpoTres3 = new Paragraph("Taxa Total: ",fontCorpo);
            pCorpoTres3.add(new Phrase("\nTOTAL RATE",fontUK));
            Paragraph pCorpoTres4 = new Paragraph("Segurado: ",fontCorpo);
            pCorpoTres4.add(new Phrase("\nInsured".toUpperCase(),fontUK));
            Paragraph pCorpoTres5 = new Paragraph("Meio de Transporte: "+cm.getCargaMaritima().getFormaEnvio(),fontCorpo);
            pCorpoTres5.add(new Phrase("\nMODE OF CONVEYANCE",fontUK));
            
            //Paragraph pCorpoTres6_1 = new Paragraph("Medadoria Assegurada: ",fontCorpo);
            Paragraph pCorpoTres6 = new Paragraph("De "+cm.getCargaMaritima().getPaisOrigem()+" Para: "+cm.getCargaMaritima().getPaisDestino(),fontCorpo);
            pCorpoTres6.add(new Phrase("\nFROM                         TO",fontUK));
            Paragraph pCorpoTres7 = new Paragraph("Juros: ",fontCorpo);
            pCorpoTres7.add(new Phrase("\nINTEREST",fontUK));
            Paragraph pCorpoTres8 = new Paragraph("Valor Segurado: ",fontCorpo);
            pCorpoTres8.add(new Phrase("\nINSURED VALUE",fontUK));
            Paragraph pCorpoTres9 = new Paragraph("CONDIÇÕES: Sujeitas as seguintes clásulas e garantias (Ver anexo A)",fontCorpoN);
            pCorpoTres9.add(new Phrase("\nCONDITIONS : Subject to the following clásulas and warranteis (SEE APPENDICIX A)",fontUK));
            Paragraph pCorpoTres10 = new Paragraph("No caso de perda ou dano que se presume a companhia ser reponsável, deve-se comunicar de imediato à NICOM SEGUROS STP"
                    + " para ser feita a vistoria (Por favor volte).",fontCorpo);
            pCorpoTres10.add(new Phrase("\nIn the event of loss or damage for which company is presumed to be liable, immediate resquest for survey must be made to  NICON SEGUROS STP (PLEASE TURN OVER).",fontUK));
            
            Paragraph pCorpoTres11 = new Paragraph("No caso de perda ou dano este certificado, depois de autenticado, deve ser anexo a reclamação acompanhado"
                    + " de relatório de auditoria e da factura original do desembarque, a cópia autenticada ou original da factura e uaisquer outros documentos relativo.",fontCorpo);
            pCorpoTres10.add(new Phrase("\nIn case of loss or damage this certificate, must be annexed to the claim note accompaneid by the repost, original bill of landing, true copy"
                    + " or original invoice and any other relevant documenent.",fontUK));
            
            Paragraph pCorpoTres12 = new Paragraph();
            Phrase p1= new Phrase("Nota: ",fontCorpoN);
            Phrase p2= new Phrase("A Empresa compromete-se a emitir uma apólice que cobre as mercadorias descritas no pedido",fontCorpo);
            Phrase p3= new Phrase("\nThe Company undertakes to issue a policy covering the good described herein on request.",fontUK);
            pCorpoTres12.add(p1);
            pCorpoTres12.add(p2);
            pCorpoTres12.add(p3);
            
            cellCorpopTres.addElement(pCorpoTres1);
            cellCorpopTres.addElement(pCorpoTres2);
            cellCorpopTres.addElement(pCorpoTres3);
            cellCorpopTres.addElement(pCorpoTres4);
            cellCorpopTres.addElement(pCorpoTres5);
            //cellCorpopTres.addElement(pCorpoTres6_1);
            cellCorpopTres.addElement(pCorpoTres6);
            cellCorpopTres.addElement(pCorpoTres7);
            cellCorpopTres.addElement(pCorpoTres8);
            cellCorpopTres.addElement(cellNull.getPhrase());
            cellCorpopTres.addElement(pCorpoTres9);
            cellCorpopTres.addElement(pCorpoTres10);
            cellCorpopTres.addElement(cellNull.getPhrase());
            cellCorpopTres.addElement(pCorpoTres11);
            cellCorpopTres.addElement(cellNull.getPhrase());
            cellCorpopTres.addElement(pCorpoTres12);
            pTableCorpoTres.addCell(cellCorpopTres);
            
            PdfPTable pTableAssinaturaTitulo= new PdfPTable(1);
            PdfPTable pTableAssinatura= new PdfPTable( new float[]{50f,50f});
            
            Paragraph pUK = new Paragraph("", fontCorpo);
            pUK.add(new Phrase("Assinaturas",fontCorpoN));
            pUK.add(new Phrase("Signature",fontUK));
            PdfPCell cellAssinatora= new PdfPCell(pUK);
            cellAssinatora.setBorder(0);
            cellAssinatora.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha1= new PdfPCell(new Phrase("___________________________________".toUpperCase(),fontCorpo));
            celllinha1.setBorder(0);
            celllinha1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha2= new PdfPCell(new Phrase("___________________________________".toUpperCase(),fontCorpo));
            celllinha2.setBorder(0);
            celllinha2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            pUK = new Paragraph("", fontCorpo);
            pUK.add(new Phrase("Pela NICON SEGUROS STP",fontCorpoN));
            pUK.add(new Phrase("For NICON SEGUROS STP",fontUK));
            PdfPCell celllinha11= new PdfPCell(pUK);
            celllinha11.setBorder(0);
            celllinha11.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            pUK = new Paragraph("", fontCorpo);
            pUK.add(new Phrase("O Segurado",fontCorpoN));
            pUK.add(new Phrase("Insured",fontUK));
            PdfPCell celllinha21= new PdfPCell(pUK);
            celllinha21.setBorder(0);
            celllinha21.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            pTableAssinaturaTitulo.addCell(cellAssinatora);
            pTableAssinatura.addCell(celllinha1);
            pTableAssinatura.addCell(celllinha2);
            pTableAssinatura.addCell(celllinha11);
            pTableAssinatura.addCell(celllinha21);
                    
            Document documento= new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 35f, 5f);
            
            File f= new File(arquivo+"/"+user+"/Seguro Carga Maritima/");
            f.mkdirs();
            String Ddata=sdf.format(new Date());
            f =new File(f.getAbsoluteFile()+"/"+"Certificado Carga Maritima "+Ddata+".pdf");
            
            reString ="../Documentos/"+user+"/Seguro Carga Maritima/"+"Certificado Carga Maritima "+Ddata+".pdf";
            
            OutputStream outputStraem = new FileOutputStream(f);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);
            
            if(MarcaDAgua.isSimulation) {
                MarcaDAgua.SimulacaoVertical  v = new MarcaDAgua.SimulacaoVertical();
                writer.setPageEvent(v);
            }
            
            if(MarcaDAgua.isCanceled) {
                MarcaDAgua.AnulacaoVertical  v = new MarcaDAgua.AnulacaoVertical();
                writer.setPageEvent(v);
            }
            
            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);
            documento.add(pTableTitulo);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableCorpoUm);
            documento.add(pTableNull);
            documento.add(pTableCorpoDois);
            documento.add(pTableCorpoTres);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableAssinaturaTitulo);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableAssinatura);
            documento.close();
            
//           PrintPdf printPdf = new PrintPdf(f.getAbsolutePath(), f.getAbsolutePath(), 0, 595f,842f,"Enviar Para o OneNote 2013",1); 
//           //PrintPdf printPdf = new PrintPdf(f.getAbsolutePath(), f.getAbsolutePath(), 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",1); 
//             
//            printPdf.print();
            
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    } 
//    class MyFooter extends PdfPageEventHelper {
//        
//        @Override
//        public void onStartPage(PdfWriter writer, Document document)
//        {
//            try {
//                PdfContentByte canvas = writer.getDirectContentUnder();
//                Image image = Image.getInstance("logo.png");
//                image.scaleAbsolute(PageSize.A4.rotate());
//                image.scaleToFit( 700f, 500f);
//                image.setAbsolutePosition(document.getPageSize().getWidth()-495, 170);
//                canvas.saveState();
//                PdfGState state = new PdfGState();
//                state.setFillOpacity(0.2f);
//                canvas.setGState(state);
//                canvas.addImage(image);
//                canvas.restoreState();
//            } catch (BadElementException | IOException ex) 
//            {Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);} 
//            catch (DocumentException ex)
//            {Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);}
//        }
//    }
    public static void main(String[] args) {
        CertificadoCargaMaritima aPG= new CertificadoCargaMaritima();
        aPG.criarDoc("ddhd", "223",new Contrato(),null,"ah","STD","dhdghd");
    }
}
