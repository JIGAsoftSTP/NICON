/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import static Export.ConfigDoc.Empresa.CAIXAPOSTAL;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

/**
 *
 * @author AhmedJorge
 */
public class SeguroCargaMaritima implements Serializable
{
    private String reString;
    public String criarDoc
        (
            String numApolice,
            String numCliente,
            Contrato c,
            CargaMaritimaBean cm,
            String user, String arquivo
        )
    {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1= new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            Font fontCabecalhoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f );
            Font fontCorpo= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoP= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,7f );
            Font fontCorpoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,8.5f );
            Font fontCorpoNGT= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,10f );
            Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f , Font.UNDERLINE);
            Font fontUK= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,5.5f, Font.ITALIC);
            
            PdfPTable pTableEmpresaPricipal= new PdfPTable(new float[]{25f,75f});
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
            
            PdfPCell pCellCaixaPostal= new PdfPCell(new Phrase(CAIXAPOSTAL,fontCabecalhoN));
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
            
            PdfPTable pTableSeguro =new PdfPTable(1);
            PdfPTable pTableCliente =new PdfPTable(1);
            
            PdfPTable pTableTitulo= new PdfPTable(1);
            Paragraph pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Formulario de SEguro Carga Maritma".toUpperCase(),fontCorpoNGT));
            pUK.add(new Phrase("\nMarine cargo policy schedule".toUpperCase(),fontUK));
            PdfPCell cellTitulo= new PdfPCell(pUK);
            cellTitulo.setBorder(0);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableTitulo.addCell(cellTitulo);
            pTableTitulo.addCell(cellNull);
            pTableTitulo.addCell(cellNull);
            
            ClienteI ci = new ClienteI(numCliente);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("1 - Informações do Segurado".toUpperCase(),fontCorpoNG));
            pUK.add(new Phrase("\n1 - information Insured".toUpperCase(),fontUK));
            PdfPCell cellTituloTsbleSegurado= new PdfPCell(pUK);
            cellTituloTsbleSegurado.setBorder(0);
            
            Paragraph pCl = new Paragraph();
            pCl.add(new Phrase("-"+ci.getNOMEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNOME_(),fontCorpoN));
            pCl.add(new Phrase("\nNAME",fontUK));
            PdfPCell cellNome = new PdfPCell(new Phrase(pCl));
            cellNome.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("-" +ci.getENDERECOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getENDERECO_(),fontCorpoN));
            pCl.add(new Phrase("Address".toUpperCase(),fontUK));
            PdfPCell cellEndereco = new PdfPCell(pCl);
            cellEndereco.setBorder(0);
            
//            pCl = new Paragraph();
//            pCl.add(new Phrase("   " +ci.getNUNCLIENTEL_(),fontCorpo));
//            pCl.add(new Phrase(ci.getNUNCLIENTE_(),fontCorpoN));
//            PdfPCell cellNCliente = new PdfPCell( pCl );
//            cellNCliente.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("-" +ci.getPROFISSAOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getPROFISSAO_(),fontCorpoN));
            pCl.add(new Phrase("occupation".toUpperCase(),fontUK));
            PdfPCell cellProfissao = new PdfPCell(pCl);
            cellProfissao.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("-" +ci.getLOCALTRABALHOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getLOCALTRABALHO_(),fontCorpoN));
            pCl.add(new Phrase("\nworkplace".toUpperCase(),fontUK));
            PdfPCell cellLocalTrabalho = new PdfPCell(pCl);
            cellLocalTrabalho.setBorder(0);
            
            pTableCliente.addCell(cellTituloTsbleSegurado);
            pTableCliente.addCell(cellNome);
            pTableCliente.addCell(cellEndereco);
//            pTableCliente.addCell(cellNCliente);
            pTableCliente.addCell(cellProfissao);
            pTableCliente.addCell(cellLocalTrabalho);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("3 - Informações do Seguro".toUpperCase(),fontCorpoNG));
            pUK.add(new Phrase("\n3 - Insurance Information".toUpperCase(),fontUK));
            PdfPCell cellTiltuloSegro= new PdfPCell(pUK);
            cellTiltuloSegro.setBorder(0);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Nº Apólice : ".toUpperCase()+numApolice,fontCorpoNG));
            pUK.add(new Phrase("\npolicy no".toUpperCase(),fontUK));
            PdfPCell cellApolice= new PdfPCell(pUK);
            cellApolice.setBorder(0);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("VIAGEM ou Periodo Do Seguro: ".toUpperCase()+((c.getDataInicio()!= null) ? sdf.format(c.getDataInicio()): "" )+" à "+((c.getDataFim()!= null) ? sdf.format(c.getDataFim()): "" )));
            pUK.add(new Phrase("\nvoyage or period of insurance".toUpperCase(),fontUK));
            PdfPCell cellPeriodo= new PdfPCell(pUK);
            cellPeriodo.setBorder(0);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Montante Segurado: ".toUpperCase()+c.getPremioLiquidoMoeda(),fontCorpo));
            pUK.add(new Phrase("\namount insured hereunder".toUpperCase(),fontUK));
            PdfPCell cellMontanteSegurado= new PdfPCell(pUK);
            cellMontanteSegurado.setBorder(0);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Premio: ".toUpperCase()+c.getPrimeiroPremio(),fontCorpo));
            pUK.add(new Phrase("\npremium".toUpperCase(),fontUK));
            PdfPCell cellPremio= new PdfPCell(pUK);
            cellPremio.setBorder(0);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Franquia: ".toUpperCase()+c.getFranquia(),fontCorpo));
            pUK.add(new Phrase("\nfranchise".toUpperCase(),fontUK));
            PdfPCell cellFranquia= new PdfPCell(pUK);
            cellFranquia.setBorder(0);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Condições e Garantia: ".toUpperCase()+numApolice,fontCorpo));
            pUK.add(new Phrase("\nconditions & warrantes".toUpperCase(),fontUK));
            PdfPCell cellCondicaoEGarantia= new PdfPCell(pUK);
            cellCondicaoEGarantia.setBorder(0);
            
            PdfPCell cellNotas = new PdfPCell();
            Paragraph pTituloIntrucaoes = new Paragraph("INTRUDÇÕES PARA INSPECÇÃO", fontCorpoN);
            pTituloIntrucaoes.add(new Phrase("\nINSTRUTOIN FOR SERVEY", fontUK));
            
            Paragraph pIntrucaoes = new Paragraph("No Caso de perda ou dano que possa envolver uma reclamação ao abrigo deste seguro, aviso imediato do dano ser dado á Seguradoura em um relatório de inspeção obtido do inspetor ou por indicado.", fontCorpo);
            pIntrucaoes.add(new Phrase("\nIn the envent of loss or damage which may involve a claim this insurance immediate notice of damage should be given to the assurers and survey report obtained from the survey or named herein.", fontUK));
            
            Paragraph pTituloReclamacoes = new Paragraph("RECLAMAÇÕES", fontCorpoN);
            pTituloReclamacoes.add(new Phrase("\nclaims".toUpperCase(), fontUK));
            
            Paragraph pReclamacoes = new Paragraph("No caso reclamação de qualquer reclamação legal resultante sobre a apólice, fica acordado que as mesma serão resolvidas pela Coporação ou a reclamação estabelecida por agente indicado mediante entrega pela Corporação ou a sujeito às Leis e pratica Inglesas mas na Jurisdição Sontomense.", fontCorpo);
            pReclamacoes.add(new Phrase("\nIn case of any lawful claim arising on the policy it is agreed that the same shall be settled by the claims settling agente named herein upon serrender of the orignal Policy duly signed. This insurance shall be subject to English Law and Practice but São Tomé Jurisdiction.", fontUK));
            
            cellNotas.addElement(pTituloIntrucaoes);
            cellNotas.addElement(pIntrucaoes);
            cellNotas.addElement(cellNull.getPhrase());
            cellNotas.addElement(pTituloReclamacoes);
            cellNotas.addElement(pReclamacoes);
            cellNotas.setBorder(0);
            
            pTableSeguro.addCell(cellTiltuloSegro);
            pTableSeguro.addCell(cellApolice);
            pTableSeguro.addCell(cellPeriodo);
            pTableSeguro.addCell(cellMontanteSegurado);
            pTableSeguro.addCell(cellPremio);
            pTableSeguro.addCell(cellFranquia);
            pTableSeguro.addCell(cellCondicaoEGarantia);
            pTableSeguro.addCell(cellNull);
            pTableSeguro.addCell(cellNotas);
            
            PdfPTable pTableDadosTitulo= new PdfPTable(1);
            PdfPCell cellDadosTitulo= new PdfPCell(new Phrase());
            cellDadosTitulo.setBorder(0);
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("I2 - nformação do Navio: ".toUpperCase()+c.getFranquia(),fontCorpoNG));
            pUK.add(new Phrase("\n2 - Ship Information".toUpperCase(),fontUK));
            Paragraph pInfoTitulo = pUK;
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Navio: ".toUpperCase()+cm.getCargaMaritima().getNomeNavio(),fontCorpo));
            pUK.add(new Phrase("\nShip".toUpperCase(),fontUK));
            Paragraph pInfoShip = pUK;
            
            pUK = new Paragraph(new Phrase("",fontCorpo));
            pUK.add( new Phrase("Mercadoria SeGurada: ".toUpperCase()+cm.getCargaMaritima().getDecricaoMecadoria(),fontCorpo));
            pUK.add(new Phrase("\nSubject - matter insured".toUpperCase(),fontUK));
            Paragraph pInfo1 = pUK;
            
            cellDadosTitulo.addElement(pInfoTitulo);
            cellDadosTitulo.addElement(pInfoShip);
            cellDadosTitulo.addElement(pInfo1);
            cellDadosTitulo.addElement(cellNull.getPhrase());
            pTableDadosTitulo.addCell(cellDadosTitulo);
                    
            Document documento= new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 35f, 5f);
            
//            File ff= new File("Documentos\\"+user+"\\Seguro Carga Maritima\\");
//            ff.mkdirs();
//            ff =new File(ff.getAbsoluteFile()+"\\"+"Formulario Seguro Carga Maritima "+sdf1.format(new Date())+".pdf");
            
            File ff= new File(arquivo+"/"+user+"/Seguro Carga Maritima/");
            
            ff.mkdirs();
            String Ddata=sdf1.format(new Date());
            ff =new File(ff.getAbsoluteFile()+"/"+"Formulario Seguro Carga Maritima "+Ddata+".pdf");
            
            reString ="../Documentos/"+user+"/Seguro Carga Maritima/"+"Formulario Seguro Carga Maritima "+Ddata+".pdf";
            
            OutputStream outputStraem = new FileOutputStream(ff);
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
            documento.add(pTableNull);
            documento.add(pTableTitulo);
            documento.add(pTableNull);
            documento.add(pTableCliente);
            documento.add(pTableNull);
            documento.add(pTableDadosTitulo);
            documento.add(pTableNull);
            documento.add(pTableSeguro);
            documento.close();
            
//          PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Enviar Para o OneNote 2013",1); 
//          //PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",1); 
//             
//            printPdf.print();
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    } 
//        class MyFooter extends PdfPageEventHelper {

//        @Override
//        public void onStartPage(PdfWriter writer, Document document) {
//            try {
//                PdfContentByte canvas = writer.getDirectContentUnder();
//                Image image = Image.getInstance("logo.png");
//                image.scaleAbsolute(PageSize.A4.rotate());
//                image.scaleToFit(700f, 500f);
//                image.setAbsolutePosition(document.getPageSize().getWidth() - 495, 170);
//                canvas.saveState();
//                PdfGState state = new PdfGState();
//                state.setFillOpacity(0.2f);
//                canvas.setGState(state);
//                canvas.addImage(image);
//                canvas.restoreState();
//            } catch (BadElementException | IOException ex) {
//                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (DocumentException ex) {
//                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    public static void main(String[] args) {
        SeguroCargaMaritima aPG= new SeguroCargaMaritima();
        aPG.criarDoc("ddhd", "223",new Contrato(),new CargaMaritimaBean(),"ah","");
    }
}
