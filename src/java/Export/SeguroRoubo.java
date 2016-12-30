/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import bean.RouboBean;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Contrato;
import modelo.Roubo;

/**
 *
 * @author AhmedJorge
 */
public class SeguroRoubo implements Serializable {

    private String reString;

    public String criarDoc(
            String numApolice,
            String numCliente,
            RouboBean rb,
            Contrato c, String user, String arquivo
    ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd 'DE' MMMM 'DE' yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoP = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f);
            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8.5f);
            Font fontCorpoNGT = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f);
            Font fontCabecalhoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{25f, 75f});
            PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres2 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);

            PdfPTable pTableNull = new PdfPTable(1);
            PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontCorpo));
            cellNull.setBorder(0);
            pTableNull.addCell(cellNull);

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

            PdfPCell pCellPolice = new PdfPCell(new Phrase(Empresa.APOLICE + numApolice, fontCabecalhoN));
            pCellPolice.setBorder(0);

            Image imageEmpresa = Image.getInstance("logo.png");
            imageEmpresa.scaleToFit(120f, 85f);

            pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
            pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
            pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
            pTableEmpresaInforImpres1.addCell(pCellTeleFax);
            pTableEmpresaInforImpres1.addCell(pCellSociedade);

            pTableEmpresaInforImpres1.addCell(pCellPolice);

            PdfPCell cellTabela3 = new PdfPCell(pTableEmpresaInforImpres1);
            cellTabela3.setBorder(0);

            pTableEmpresaInforImpres5.addCell(cellTabela3);

            PdfPCell cellTabela5 = new PdfPCell(pTableEmpresaInforImpres5);
            cellTabela5.setBorder(0);

            PdfPCell cellTabela6 = new PdfPCell(imageEmpresa);
            cellTabela6.setBorder(0);

            pTableEmpresaPricipal.addCell(cellTabela6);
            pTableEmpresaPricipal.addCell(cellTabela5);

            PdfPTable pTableSeguro = new PdfPTable(1);
            PdfPTable pTableCliente = new PdfPTable(1);

            PdfPTable pTableTitulo = new PdfPTable(1);
            Phrase pTitulo = new Phrase("Formulario de SEguro Roubo".toUpperCase(), fontCorpoNGT);
            PdfPCell cellTitulo = new PdfPCell(pTitulo);
            cellTitulo.setBorder(0);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableTitulo.addCell(cellTitulo);

            ClienteI ci = new ClienteI(numCliente);
            
            PdfPCell cellTituloTsbleSegurado = new PdfPCell(new Phrase("1 - Informações Cliente".toUpperCase(), fontCorpoNG));
            cellTituloTsbleSegurado.setBorder(0);
            
            Paragraph pCl = new Paragraph();
            pCl.add(new Phrase("   " +ci.getNOMEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNOME_(),fontCorpoN));
            PdfPCell cellNome = new PdfPCell(new Phrase(pCl));
            cellNome.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("   " +ci.getENDERECOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getENDERECO_(),fontCorpoN));
            PdfPCell cellEndereco = new PdfPCell(pCl);
            cellEndereco.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("   " +ci.getNUNCLIENTEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNUNCLIENTE_(),fontCorpoN));
            PdfPCell cellNCliente = new PdfPCell( pCl );
            cellNCliente.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("   " +ci.getPROFISSAOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getPROFISSAO_(),fontCorpoN));
            PdfPCell cellProfissao = new PdfPCell(pCl);
            cellProfissao.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("   " +ci.getLOCALTRABALHOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getLOCALTRABALHO_(),fontCorpoN));
            PdfPCell cellLocalTrabalho = new PdfPCell(pCl);
            cellLocalTrabalho.setBorder(0);

            pTableCliente.addCell(cellTituloTsbleSegurado);
            pTableCliente.addCell(cellNome);
            pTableCliente.addCell(cellEndereco);
            pTableCliente.addCell(cellNCliente);
            pTableCliente.addCell(cellProfissao);
            pTableCliente.addCell(cellLocalTrabalho);

            PdfPTable pTableDadosTitulo = new PdfPTable(1);
            PdfPCell cellDadosTitulo = new PdfPCell(new Phrase());
            cellDadosTitulo.setBorder(0);
            Paragraph pInfoTitulo = new Paragraph("2 - Informações do(s) Objecto (s) Segurado(s)".toUpperCase(), fontCorpoN);
            Paragraph pInfo1 = new Paragraph("   O Segurado: ".toUpperCase() + rb.getRoubo().getTipoEdificio(), fontCorpo);
            Paragraph pInfo2 = new Paragraph("   Endereço: ".toUpperCase() + rb.getRoubo().getEnderecoEdificio(), fontCorpo);

            cellDadosTitulo.addElement(pInfoTitulo);
            cellDadosTitulo.addElement(pInfo1);
            cellDadosTitulo.addElement(pInfo2);
            pTableDadosTitulo.addCell(cellDadosTitulo);

            PdfPCell cellSeguro = new PdfPCell(new Phrase());
            cellSeguro.setBorder(0);
            Paragraph pSegTitulo = new Paragraph("3 - Informações Montante Segurado".toUpperCase(), fontCorpoN);
            Paragraph pSeg1 = new Paragraph("   A Propriendade SEgurada: ......................................................... veja a especificação I".toUpperCase(), fontCorpo);
            Paragraph pSeg2 = new Paragraph("   Periodo Do Seguro ".toUpperCase(), fontCorpo);
            Paragraph pSeg3 = new Paragraph("   - DE " + sdf.format(c.getDataInicio()) + " Até " + sdf.format(c.getDataFim()) + " (16:00 Da DATA DE Epiração): ".toUpperCase(), fontCorpo);

            Paragraph pSeg4 = new Paragraph("   Data de Renovação: " + ((c.getDataRenovacao() == null) ? "" : sdf.format(c.getDataRenovacao()).toUpperCase()), fontCorpo);
            Paragraph pSeg5 = new Paragraph("   Primeiro premio: ".toUpperCase() + c.getPrimeiroPremio(), fontCorpo);
            Paragraph pSeg6 = new Paragraph("   Prémeio Anual: ".toUpperCase() + c.getPremioAnual(), fontCorpo);
            Paragraph pSeg7 = new Paragraph("Ojectos/Limite de responsabilidade dedutível/prémio/observação/natureza/Do Risco/valor segurado/preço excesso".toUpperCase(), fontCorpo);
            Paragraph pSeg8 = new Paragraph("   ".toUpperCase(), fontCorpo);
            Paragraph pSeg9 = new Paragraph("   Prémio grosso:".toUpperCase(), fontCorpo);
            Paragraph pSeg10 = new Paragraph("   MeNor: ".toUpperCase(), fontCorpo);
            Paragraph pSeg11 = new Paragraph("   Adiciona: ".toUpperCase(), fontCorpo);
            Paragraph pSeg12 = new Paragraph("   Prémio Liquido Pagável: ".toUpperCase(), fontCorpo);
            Paragraph pSeg13 = new Paragraph("   outras condições/memorando .......................................................... queira por favor vira a página ".toUpperCase(), fontCorpo);

            Paragraph pSegData = new Paragraph("   Data Em ".toUpperCase() + sdf.format(new Date()).toUpperCase(), fontCorpo);

            cellSeguro.addElement(pSegTitulo);
            cellSeguro.addElement(pSeg1);
            cellSeguro.addElement(pSeg2);
            cellSeguro.addElement(pSeg3);
            cellSeguro.addElement(pSeg4);
            cellSeguro.addElement(pSeg5);
            cellSeguro.addElement(pSeg6);
            cellSeguro.addElement(pSeg8);
            cellSeguro.addElement(pSeg7);
            cellSeguro.addElement(pSeg8);
            cellSeguro.addElement(pSeg9);
            cellSeguro.addElement(pSeg10);
            cellSeguro.addElement(pSeg11);
            cellSeguro.addElement(pSeg12);
            cellSeguro.addElement(pSeg13);
            cellSeguro.addElement(pSeg8);
            cellSeguro.addElement(pSeg8);
            cellSeguro.addElement(pSegData);

            pTableSeguro.addCell(cellSeguro);

            PdfPTable pTableObs = new PdfPTable(1);
            PdfPCell cellObs = new PdfPCell();
            cellObs.setBorder(0);
            Paragraph pObs = new Paragraph("2.2 Especificação lidada e que faz parte de apólice de seguro contra Roubo Nº. 1".toUpperCase(), fontCorpo);
            cellObs.addElement(pSeg8);
            cellObs.addElement(pSeg8);
            cellObs.setBorderWidthBottom(0.6f);
            cellObs.addElement(pObs);

            PdfPCell cellObs1 = new PdfPCell();
            cellObs1.setBorder(0);
            Paragraph pObs1 = new Paragraph("Sobre artigos domésticos e afetos pessoais incluindo mobilias e instalações, a proprriedade do Seguro ou"
                    + "ou em fiança ou sobre comissão do qual o Segurado é responsavel enqunto estiver num edificio de contrução padrão.", fontCorpo);
            cellObs1.addElement(pObs1);
            cellObs1.addElement(pSeg8);
            cellObs1.addElement(pSeg8);

            pTableObs.addCell(cellObs);
            pTableObs.addCell(cellObs1);

            PdfPTable pTableOjsSegurado = new PdfPTable(new float[]{8, 40, 26, 26});
            pTableOjsSegurado.setWidthPercentage(90);
            PdfPTable pTableOjsSegurado1 = new PdfPTable(new float[]{74, 26});
            pTableOjsSegurado1.setWidthPercentage(90);
            PdfPCell cellOjsSegurado1 = new PdfPCell(new Phrase("Quant.", fontCorpoNG));
            PdfPCell cellOjsSegurado2 = new PdfPCell(new Phrase("Descrição de Propriedade".toUpperCase(), fontCorpoNG));
            PdfPCell cellOjsSegurado3 = new PdfPCell(new Phrase("Modelo No/nº DE Serie".toUpperCase(), fontCorpoNG));
            PdfPCell cellOjsSegurado4 = new PdfPCell(new Phrase("Valor Segurado".toUpperCase(), fontCorpoNG));

            cellOjsSegurado1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellOjsSegurado2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellOjsSegurado3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellOjsSegurado4.setHorizontalAlignment(Element.ALIGN_CENTER);

            pTableOjsSegurado.addCell(cellOjsSegurado1);
            pTableOjsSegurado.addCell(cellOjsSegurado2);
            pTableOjsSegurado.addCell(cellOjsSegurado3);
            pTableOjsSegurado.addCell(cellOjsSegurado4);

            Double valortotal = 0.0;
            for (Roubo r : rb.getInfo()) {
                cellOjsSegurado1 = new PdfPCell(new Phrase(r.getQuantidade(), fontCorpo));
                cellOjsSegurado2 = new PdfPCell(new Phrase(r.getDescricao(), fontCorpo));
                cellOjsSegurado3 = new PdfPCell(new Phrase(r.getModelo(), fontCorpo));
                cellOjsSegurado4 = new PdfPCell(new Phrase(r.getValor(), fontCorpo));

                valortotal += Double.valueOf(r.getValor());

                cellOjsSegurado1.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellOjsSegurado2.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellOjsSegurado3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellOjsSegurado4.setHorizontalAlignment(Element.ALIGN_LEFT);

                pTableOjsSegurado.addCell(cellOjsSegurado1);
                pTableOjsSegurado.addCell(cellOjsSegurado2);
                pTableOjsSegurado.addCell(cellOjsSegurado3);
                pTableOjsSegurado.addCell(cellOjsSegurado4);
            }

            PdfPCell cellOjs1Segurado1 = new PdfPCell(new Phrase("Somatório ----------------------------------------------------------------------------------------------".toUpperCase(), fontCorpo));
            cellOjs1Segurado1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cellOjs1Segurado2 = new PdfPCell(new Phrase(valortotal + "", fontCorpo));
            cellOjs1Segurado2.setHorizontalAlignment(Element.ALIGN_LEFT);

            pTableOjsSegurado1.addCell(cellOjs1Segurado1);
            pTableOjsSegurado1.addCell(cellOjs1Segurado2);

            PdfPTable pTableAssinaturaTitulo = new PdfPTable(1);
            PdfPTable pTableAssinatura = new PdfPTable(new float[]{50f, 50f});
            PdfPCell cellAssinatora = new PdfPCell(new Phrase("Assinaturas".toUpperCase(), fontCorpoN));
            cellAssinatora.setBorder(0);
            cellAssinatora.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha1 = new PdfPCell(new Phrase("____________________________________________".toUpperCase(), fontCorpo));
            celllinha1.setBorder(0);
            celllinha1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha2 = new PdfPCell(new Phrase("____________________________________________".toUpperCase(), fontCorpo));
            celllinha2.setBorder(0);
            celllinha2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell celllinha11 = new PdfPCell(new Phrase("para nicon Seguro sa stp".toUpperCase(), fontCorpoP));
            celllinha11.setBorder(0);
            celllinha11.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha21 = new PdfPCell(new Phrase("o segurado ".toUpperCase(), fontCorpoP));
            celllinha21.setBorder(0);
            celllinha21.setHorizontalAlignment(Element.ALIGN_CENTER);

            pTableAssinaturaTitulo.addCell(cellAssinatora);
            pTableAssinatura.addCell(celllinha1);
            pTableAssinatura.addCell(celllinha2);
            pTableAssinatura.addCell(celllinha11);
            pTableAssinatura.addCell(celllinha21);

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 35f, 5f);

//            File ff= new File("Documentos\\"+user+"\\Seguro Roubo\\");
//            ff.mkdirs();
//            ff =new File(ff.getAbsoluteFile()+"\\"+"Formulario Seguro Roubo "+sdf1.format(new Date())+".pdf");
            File ff = new File(arquivo + "/" + user + "/Seguro Roubo/");

            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Formulario Seguro Roubo " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro Roubo/" + "Formulario Seguro Roubo " + Ddata + ".pdf";

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
            documento.add(pTableTitulo);
            documento.add(pTableNull);
            documento.add(pTableCliente);
            documento.add(pTableNull);
            documento.add(pTableDadosTitulo);
            documento.add(pTableNull);
            documento.add(pTableSeguro);
            documento.add(pTableNull);
            documento.add(pTableAssinaturaTitulo);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableAssinatura);
            if (rb.getInfo().size() >= 10) {
                documento.newPage();
            }
            documento.add(pTableObs);
            documento.add(pTableOjsSegurado);
            documento.add(pTableOjsSegurado1);
            documento.close();

//           PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Enviar Para o OneNote 2013",1); 
            //PrintPdf printPdf = new PrintPdf(ff.getAbsolutePath(), ff.getAbsolutePath(), 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",1); 
//            printPdf.print();
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SeguroAPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    }

    public static void main(String[] args) {
        SeguroRoubo aPG = new SeguroRoubo();
        aPG.criarDoc("ddhd", "223", new RouboBean(), new Contrato(), "ah", "");
    }
//    class MyFooter extends PdfPageEventHelper {
//
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
}
