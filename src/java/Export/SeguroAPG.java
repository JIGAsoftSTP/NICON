/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import bean.AcidentePGBean;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Contrato;

/**
 *
 * @author AhmedJorge
 */
public class SeguroAPG implements Serializable {

    private String reString;

    public String criarDoc(
            String numApolice,
            String numCliente,
            Contrato c,
            AcidentePGBean apg, String user, String moeda, String arquivo
    ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10.5f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoTab = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7.5f);
            Font fontCorpoP = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7.5f);
            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoNTable = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10.5f);
            Font fontCorpoNGT = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 11f);
            Font fontCabecalhoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);
//            Font fontCorpoNGTi= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,10f );

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
            Phrase pTitulo = new Phrase("Formulario de SEguro Acidente Para grupo".toUpperCase(), fontCorpoNGT);
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

            PdfPCell cellTiltuloSegro = new PdfPCell(new Phrase("2 - Informações Premio".toUpperCase(), fontCorpoNG));
            cellTiltuloSegro.setBorder(0);
            PdfPCell cellApolice = new PdfPCell(new Phrase("   Nº Apólice: ".toUpperCase() + numApolice, fontCorpo));
            cellApolice.setBorder(0);
            PdfPCell cellPropriedadeSegurada = new PdfPCell(new Phrase("   Propriedade segurada: ".toUpperCase() + apg.getInfo().size() + " " + "Trabalhodor".toUpperCase() + ((apg.getInfo().size() > 1) ? "ES" : ""), fontCorpo));
            cellPropriedadeSegurada.setBorder(0);
            PdfPCell cellTotalSegurado = new PdfPCell(new Phrase("   Valor Total segrurado: ".toUpperCase() + c.getTotalSeguradoMoeda() + " " + moeda, fontCorpo));
            cellTotalSegurado.setBorder(0);
            PdfPCell cellValor1Preminio = new PdfPCell(new Phrase("   Valor Primeiro Premio: ".toUpperCase() + ((c.getPrimeiroPremio() == null) ? "" : c.getPrimeiroPremio() + " " + moeda), fontCorpo));
            cellValor1Preminio.setBorder(0);
            PdfPCell cellTotalPremioAnual = new PdfPCell(new Phrase("   Valor Premio Anual: ".toUpperCase() + ((c.getPremioAnual() == null) ? " " : c.getPremioAnual() + " " + moeda), fontCorpo));
            cellTotalPremioAnual.setBorder(0);
            PdfPCell cellPeriodo = new PdfPCell(new Phrase("   Periodo Do Seguro: ".toUpperCase() + ((c.getDataInicio() != null) ? sdf.format(c.getDataInicio()) : "") + " à " + ((c.getDataFim() != null) ? sdf.format(c.getDataFim()) : ""), fontCorpo));
            cellPeriodo.setBorder(0);
            PdfPCell cellDataRenovacao = new PdfPCell(new Phrase("   Data Renovação: ".toUpperCase() + ((c.getDataRenovacao() == null) ? " " : c.getDataRenovacao() + " " + moeda), fontCorpo));
            cellDataRenovacao.setBorder(0);
            Paragraph p = new Paragraph();
            Phrase p1 = new Phrase("BEnefício (s) De Peessoa e Plano: ".toUpperCase(), fontCorpoN);
            Phrase p2 = new Phrase("O Seguro Sob esta secção se aplique apenas as pessoas ou grupo das pessoas seguradas indicadas".toUpperCase()
                    + " e apenas de acordo com às coberturas para qual o valor foi especificado. o Valor especificado será aplicado para ".toUpperCase()
                    + "cada pessoa segurada por sinistro subjeito a todos os termos da apolice.".toUpperCase(), fontCorpo);

            p.add(p1);
            p.add(p2);

            PdfPCell cellNotaBenefi = new PdfPCell(p);
            cellNotaBenefi.setBorder(0);
            cellNotaBenefi.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

            PdfPCell cellCobertura = new PdfPCell(new Phrase("Cobertuta Diária: ".toUpperCase() + ((apg.getAcidentePG().getTipoCobertura().equals("1")) ? "Apenas em situação de acidente de trabalho" : ((apg.getAcidentePG().getTipoCobertura().equals("2") ? "24 horas" : apg.getAcidentePG().getTipoCobertura()))), fontCorpo));
            cellCobertura.setBorder(0);

            PdfPCell cellLimiteBeneficio = new PdfPCell(new Phrase("3 - Informações de Limites de Benefício:".toUpperCase(), fontCorpoNG));
            cellLimiteBeneficio.setBorder(0);

            PdfPCell cellLimiteA = new PdfPCell(new Phrase("   Benefício A: Morte: ".toUpperCase() + apg.getAcidentePG().getPremioMorteMoeda() + " " + moeda, fontCorpo));
            cellLimiteA.setBorder(0);
            PdfPCell cellLimiteB = new PdfPCell(new Phrase("   Benefício B: Invalidez permanete: ".toUpperCase() + apg.getAcidentePG().getPremioIncapacidadePermanenteMoeda() + " " + moeda, fontCorpo));
            cellLimiteB.setBorder(0);
            PdfPCell cellLimiteC = new PdfPCell(new Phrase("   Benefício C: Reembolso das despesas Médica Acidental: ".toUpperCase() + apg.getAcidentePG().getPremioDespesaMedicaMoeda() + " " + moeda, fontCorpo));
            cellLimiteC.setBorder(0);
            PdfPCell cellLimiteGlobal = new PdfPCell(new Phrase("   Limite Global de Responsabilidade: ".toUpperCase() + c.getTotalSeguradoMoeda() + " " + moeda, fontCorpo));
            cellLimiteGlobal.setBorder(0);

            pTableSeguro.addCell(cellTiltuloSegro);
            pTableSeguro.addCell(cellApolice);
            pTableSeguro.addCell(cellPropriedadeSegurada);
            pTableSeguro.addCell(cellTotalSegurado);
            pTableSeguro.addCell(cellValor1Preminio);
            pTableSeguro.addCell(cellTotalPremioAnual);
            pTableSeguro.addCell(cellPeriodo);
            pTableSeguro.addCell(cellDataRenovacao);
            pTableSeguro.addCell(cellNull);
            pTableSeguro.addCell(cellNotaBenefi);
            pTableSeguro.addCell(cellNull);
            pTableSeguro.addCell(cellCobertura);
            pTableSeguro.addCell(cellNull);
            pTableSeguro.addCell(cellLimiteBeneficio);
            pTableSeguro.addCell(cellLimiteA);
            pTableSeguro.addCell(cellLimiteB);
            pTableSeguro.addCell(cellLimiteC);
            pTableSeguro.addCell(cellLimiteGlobal);

            PdfPTable pTableDados = new PdfPTable(new float[]{7, 15.5f, 15.5f, 15.5f, 15.f, 15.5f, 15.5f});
            PdfPCell cellDadosNum = new PdfPCell(new Phrase("No.".toUpperCase(), fontCorpoNTable));
            cellDadosNum.setBorderWidth(1);

            PdfPCell cellDadosSegurado = new PdfPCell(new Phrase("Nome Segurado".toUpperCase(), fontCorpoNTable));
            cellDadosSegurado.setBorderWidth(1);

            PdfPCell cellDadosCategoria = new PdfPCell(new Phrase("Categoria do Segurado".toUpperCase(), fontCorpoNTable));
            cellDadosCategoria.setBorderWidth(1);

            PdfPCell cellDadosBeneficioMorte = new PdfPCell(new Phrase("Beneficio Em caso de morte por pessoa".toUpperCase(), fontCorpoNTable));
            cellDadosBeneficioMorte.setBorderWidth(1);

            PdfPCell cellDadosInvalidez = new PdfPCell(new Phrase("Beneficio Em caso de Invalidez permanente por pessoa".toUpperCase(), fontCorpoNTable));
            cellDadosInvalidez.setBorderWidth(1);

            PdfPCell cellDadosTemporaria = new PdfPCell(new Phrase("Beneficio Em caso de Invalidez temporaria por pessoa".toUpperCase(), fontCorpoNTable));
            cellDadosTemporaria.setBorderWidth(1);

            PdfPCell cellDadosDepesasMedica = new PdfPCell(new Phrase("Despesa Medica Por Pessoa".toUpperCase(), fontCorpoNTable));
            cellDadosDepesasMedica.setBorderWidth(1);

            pTableDados.addCell(cellDadosNum);
            pTableDados.addCell(cellDadosSegurado);
            pTableDados.addCell(cellDadosCategoria);
            pTableDados.addCell(cellDadosBeneficioMorte);
            pTableDados.addCell(cellDadosInvalidez);
            pTableDados.addCell(cellDadosTemporaria);
            pTableDados.addCell(cellDadosDepesasMedica);

            int total = (apg.getInfo() == null) ? 0 : apg.getInfo().size();
            for (int i = 0; i < total; i++) {
                cellDadosNum = new PdfPCell(new Phrase((i + 1) + " ", fontCorpoTab));
                cellDadosNum.setBorderWidth(1);
                cellDadosNum.setPaddingTop(5f);
                cellDadosNum.setPaddingBottom(5f);

                cellDadosSegurado = new PdfPCell(new Phrase(apg.getInfo().get(i).getNome(), fontCorpoTab));
                cellDadosSegurado.setBorderWidth(1);
                cellDadosSegurado.setPaddingTop(5f);
                cellDadosSegurado.setPaddingBottom(5f);

                cellDadosCategoria = new PdfPCell(new Phrase(apg.getInfo().get(i).getCategoria(), fontCorpoTab));
                cellDadosCategoria.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDadosCategoria.setBorderWidth(1);
                cellDadosCategoria.setPaddingTop(5f);
                cellDadosCategoria.setPaddingBottom(5f);

                cellDadosBeneficioMorte = new PdfPCell(new Phrase(apg.getInfo().get(i).getValorMorte() + " " + moeda, fontCorpoTab));
                cellDadosBeneficioMorte.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDadosBeneficioMorte.setBorderWidth(1);
                cellDadosBeneficioMorte.setPaddingTop(5f);
                cellDadosBeneficioMorte.setPaddingBottom(5f);

                cellDadosInvalidez = new PdfPCell(new Phrase(apg.getInfo().get(i).getIncapacidadeTotal() + " " + moeda, fontCorpoTab));
                cellDadosInvalidez.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDadosInvalidez.setBorderWidth(1);
                cellDadosInvalidez.setPaddingTop(5f);
                cellDadosInvalidez.setPaddingBottom(5f);

                cellDadosTemporaria = new PdfPCell(new Phrase(apg.getInfo().get(i).getIncapacidadeTotalTemporaria() + " " + moeda, fontCorpoTab));
                cellDadosTemporaria.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDadosTemporaria.setBorderWidth(1);
                cellDadosTemporaria.setPaddingTop(5f);
                cellDadosTemporaria.setPaddingBottom(5f);

                cellDadosDepesasMedica = new PdfPCell(new Phrase(apg.getInfo().get(i).getDespesaMedica() + " " + moeda, fontCorpoTab));
                cellDadosDepesasMedica.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDadosDepesasMedica.setBorderWidth(1);
                cellDadosDepesasMedica.setPaddingTop(5f);
                cellDadosDepesasMedica.setPaddingBottom(5f);

                pTableDados.addCell(cellDadosNum);
                pTableDados.addCell(cellDadosSegurado);
                pTableDados.addCell(cellDadosCategoria);
                pTableDados.addCell(cellDadosBeneficioMorte);
                pTableDados.addCell(cellDadosInvalidez);
                pTableDados.addCell(cellDadosTemporaria);
                pTableDados.addCell(cellDadosDepesasMedica);
            }

            PdfPTable pTableRenovacaoDataHoje = new PdfPTable(1);
            PdfPCell cellRenovacao = new PdfPCell(new Phrase("Prémio de renovação: ".toUpperCase() + ((c.getPremioAnual() == null) ? " " : c.getPremioAnual() + " " + moeda), fontCorpo));
            cellRenovacao.setBorder(0);
            PdfPCell cellData = new PdfPCell(new Phrase("Data: ".toUpperCase() + sdf.format(new Date()), fontCorpo));
            cellData.setBorder(0);
            pTableRenovacaoDataHoje.addCell(cellRenovacao);
            pTableRenovacaoDataHoje.addCell(cellData);

            PdfPTable pTableAssinaturaTitulo = new PdfPTable(1);
            PdfPTable pTableAssinatura = new PdfPTable(new float[]{50f, 50f});
            PdfPCell cellAssinatora = new PdfPCell(new Phrase("Assinaturas".toUpperCase(), fontCorpoN));
            cellAssinatora.setBorder(0);
            cellAssinatora.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha1 = new PdfPCell(new Phrase("___________________________".toUpperCase(), fontCorpo));
            celllinha1.setBorder(0);
            celllinha1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha2 = new PdfPCell(new Phrase("___________________________".toUpperCase(), fontCorpo));
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

//            File ff= new File("Documentos\\"+user+"\\Seguro Acidente para Grupo\\");
//            ff.mkdirs();
//            ff =new File(ff.getAbsoluteFile()+"\\"+"Formulario Seguro Acidente Para Grupo "+sdf1.format(new Date())+".pdf");
            File ff = new File(arquivo + "/" + user + "/Seguro Acidente Para Grupo/");
            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            System.err.println(ff.getAbsoluteFile());
            ff = new File(ff.getAbsoluteFile() + "/" + "Formulario Seguro Acidente Para Grupo " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro Acidente Para Grupo/" + "Formulario Seguro Acidente Para Grupo " + Ddata + ".pdf";

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
            documento.add(pTableNull);
            documento.add(pTableTitulo);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableCliente);
            documento.add(pTableNull);
            documento.add(pTableSeguro);
            documento.newPage();
            documento.add(pTableDados);
            documento.add(pTableNull);
            documento.add(pTableRenovacaoDataHoje);
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
        SeguroAPG aPG = new SeguroAPG();
        aPG.criarDoc("ddhd", "223", new Contrato(), new AcidentePGBean(), "ah", "STD", "");
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
