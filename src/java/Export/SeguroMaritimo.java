/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import bean.MaritimoBean;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Contrato;

/**
 *
 * @author AhmedJorge
 */
public class SeguroMaritimo implements Serializable {

    private String reString;

    public String criarDoc(
            String numApolice,
            String numCliente,
            MaritimoBean mb,
            Contrato c, String user, String moeda, String arquivo
    ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoP = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f);
            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8.5f);
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
            Phrase pTitulo = new Phrase("Formulario de SEguro Maritimo".toUpperCase());
            pTitulo.getFont().setStyle(Font.BOLD);
            pTitulo.getFont().setFamily(Font.FontFamily.COURIER.name());
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

            PdfPCell cellTiltuloSegro = new PdfPCell(new Phrase("3 - Montante Segurado/Limete de Responsabilidade".toUpperCase(), fontCorpoNG));
            cellTiltuloSegro.setBorder(0);
            PdfPCell cellApolice = new PdfPCell(new Phrase("   Nº Apólice: ".toUpperCase() + numApolice, fontCorpo));
            cellApolice.setBorder(0);
            PdfPCell cellPeriodo = new PdfPCell(new Phrase("   Periodo Do Seguro: ".toUpperCase() + ((c.getDataInicio() != null) ? sdf.format(c.getDataInicio()) : "") + " à " + ((c.getDataFim() != null) ? sdf.format(c.getDataFim()) : ""), fontCorpo));
            cellPeriodo.setBorder(0);
            PdfPCell cellDataNota = new PdfPCell(new Phrase("   Ambas as datas incluidas, para os periodos adicionados que possam ser mutuamente acordadas".toUpperCase(), fontCorpo));
            cellDataNota.setBorder(0);
            PdfPCell cellCorpoMotor = new PdfPCell(new Phrase("   Para Corpo e o motor (Excluindo risco de guerra): ".toUpperCase() + c.getPrimeiroPremio(), fontCorpo));
            cellCorpoMotor.setBorder(0);
            PdfPCell cellParaTerceiro = new PdfPCell(new Phrase("   Para terceiros/responsabilidade civil: ", fontCorpo));
            cellParaTerceiro.setBorder(0);
            PdfPCell cellAcidentePessoal = new PdfPCell(new Phrase("   Para acidente pessoal ao condutor: ", fontCorpo));
            cellAcidentePessoal.setBorder(0);
            PdfPCell cellTotalPremioAnual = new PdfPCell(new Phrase("   Valor Premio Anual: ".toUpperCase() + c.getPremioAnual(), fontCorpo));
            cellTotalPremioAnual.setBorder(0);

            pTableSeguro.addCell(cellTiltuloSegro);
            pTableSeguro.addCell(cellApolice);
            pTableSeguro.addCell(cellPeriodo);
            pTableSeguro.addCell(cellDataNota);
            pTableSeguro.addCell(cellCorpoMotor);
            pTableSeguro.addCell(cellParaTerceiro);
            pTableSeguro.addCell(cellAcidentePessoal);
            pTableSeguro.addCell(cellTotalPremioAnual);

            PdfPTable pTableDadosTitulo = new PdfPTable(1);
            PdfPCell cellDadosTitulo = new PdfPCell(new Phrase());
            cellDadosTitulo.setBorder(0);
            Paragraph pInfoTitulo = new Paragraph("2 - Informações do(s) Navio(s)".toUpperCase(), fontCorpoN);
            Paragraph pInfoShip = new Paragraph("   1. Navio/Embarcação: ".toUpperCase() + mb.getMaritimo().getNomeNavio(), fontCorpo);
            Paragraph pInfo1 = new Paragraph("      A)MARCA/MODELO (CASSI: ) " + mb.getMaritimo().getMarcaModelo() + "/" + mb.getMaritimo().getMarcaMotor(), fontCorpo);
            Paragraph pInfo2 = new Paragraph("   2. TIPO DE CONSTRUÇÃO DO NAVIO (i.e Material usado) " + mb.getMaritimo().getTipoConstrucao(), fontCorpo);
            Paragraph pInfo3 = new Paragraph("   3. TIPO DE NAVIO: " + mb.getMaritimo().getTipoNavio(), fontCorpo);
            Paragraph pInfo4 = new Paragraph("   4. IDADE DO NAVIO (ano de construção): " + mb.getMaritimo().getIdadeNavio(), fontCorpo);
            Paragraph pInfo5 = new Paragraph("   5. CONDIÇÃO ACTUAL DO NAVIO: " + mb.getMaritimo().getCondicaoAtual(), fontCorpo);
            Paragraph pInfo6 = new Paragraph("   6. RELATÓRIO DE MANUTEMÇÃO DOS PROPRIETÁRIOS DO NAVIO: ", fontCorpo);
            Paragraph pInfo7 = new Paragraph("   7. CLASSE DE ESTATUTO DE RENOVAÇÃO: " + mb.getMaritimo().getClasseRenovacao(), fontCorpo);
            Paragraph pInfo8 = new Paragraph("   8. BANDEIRA DO NAVIO: " + mb.getMaritimo().getBandeiraNavio(), fontCorpo);
            Paragraph pInfo9 = new Paragraph("   9. USO A QUE SE DISTINA O NAVIO OU COMÉRCIO: " + mb.getMaritimo().getUsoNavio(), fontCorpo);
            Paragraph pInfo10 = new Paragraph("   10. QUANTIDADE/PESO DO " + mb.getMaritimo().getNomeNavio() + " : " + mb.getMaritimo().getPeso() + " (LIQUIDO E BRUTO):VER NO RELATÓRIO", fontCorpo);
            Paragraph pInfo11 = new Paragraph("   11. POTÊNCIA DO MOTOR", fontCorpo);
            Paragraph pInfo12 = new Paragraph("   12. TIPO DE COMBUSTÍVEL DO MOTOR DE PROPULSAO: " + mb.getMaritimo().getTipoCombustivel(), fontCorpo);
            Paragraph pInfo13 = new Paragraph("   13. NÚMERO DO MOTOR: " + mb.getMaritimo().getNumMotor(), fontCorpo);
            Paragraph pInfo14 = new Paragraph("   14. MARCA DO MOTOR: " + mb.getMaritimo().getMarcaMotor(), fontCorpo);
            Paragraph pInfo15 = new Paragraph("   15. NÚMERO MÁXIMO DE TRIPULANTES: " + mb.getMaritimo().getNumMaximoTripulante(), fontCorpo);
            Paragraph pInfo16 = new Paragraph("   16. DISPOSITIVOS DE SALVAMENTO SUFICIENTE PARA UM MÁXIMO: ", fontCorpo);
            Paragraph pInfo17 = new Paragraph("   17. APOIOS PARA NAVEGAÇÃO INSTALADOS: " + mb.getMaritimo().getEspecificacaoApoioNavegacao(), fontCorpo);
            Paragraph pInfo18 = new Paragraph("   18. EXPERIÊNCIA ANTERIOR DE RECLAMAÇÃO: " + mb.getMaritimo().getExperienciaRecalmacao(), fontCorpo);
            Paragraph pInfo19 = new Paragraph("   19. ÂMBITO DA COBERTURA: ", fontCorpo);
            Paragraph pInfo20 = new Paragraph("   20. LIMITAÇÃO TERRITORIAL/ÁREA DE OERAÇÃO: " + mb.getMaritimo().getAreaOperacao(), fontCorpo);
            Paragraph pInfo21 = new Paragraph("          DATA: " + sdf.format(new Date()), fontCorpo);
            cellDadosTitulo.addElement(pInfoTitulo);
            cellDadosTitulo.addElement(pInfoShip);
            cellDadosTitulo.addElement(pInfo1);
            cellDadosTitulo.addElement(pInfo2);
            cellDadosTitulo.addElement(pInfo3);
            cellDadosTitulo.addElement(pInfo4);
            cellDadosTitulo.addElement(pInfo5);
            cellDadosTitulo.addElement(pInfo6);
            cellDadosTitulo.addElement(pInfo7);
            cellDadosTitulo.addElement(pInfo8);
            cellDadosTitulo.addElement(pInfo9);
            cellDadosTitulo.addElement(pInfo10);
            cellDadosTitulo.addElement(pInfo11);
            cellDadosTitulo.addElement(pInfo12);
            cellDadosTitulo.addElement(pInfo13);
            cellDadosTitulo.addElement(pInfo14);
            cellDadosTitulo.addElement(pInfo15);
            cellDadosTitulo.addElement(pInfo16);
            cellDadosTitulo.addElement(pInfo17);
            cellDadosTitulo.addElement(pInfo18);
            cellDadosTitulo.addElement(pInfo19);
            cellDadosTitulo.addElement(pInfo20);
            cellDadosTitulo.addElement(pInfo21);
            pTableDadosTitulo.addCell(cellDadosTitulo);

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

//            File ff= new File("Documentos\\"+user+"\\Seguro Maritimo\\");
//            ff.mkdirs();
//            ff =new File(ff.getAbsoluteFile()+"\\"+"Formulario Seguro Maritimo "+sdf1.format(new Date())+".pdf");
            File ff = new File(arquivo + "/" + user + "/Seguro Maritimo/");

            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Formulario Seguro Maritimo " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro Maritimo/" + "Formulario Seguro Maritimo " + Ddata + ".pdf";

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

    public static void main(String[] args) {
        SeguroMaritimo aPG = new SeguroMaritimo();
        aPG.criarDoc("ddhd", "223", new MaritimoBean(), new Contrato(), "ah", "STD", "");
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
