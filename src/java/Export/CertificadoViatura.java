/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import bean.VeiculoBean;
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
import modelo.Veiculo;

/**
 *
 * @author AhmedJorge
 */
public class CertificadoViatura implements Serializable {

    private String reString;

    public String criarDoc(
            String numApolice,
            String numCliente,
            Contrato c, VeiculoBean vf, String user, String moeda, String arquivo
    ) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH'.'mm'.'ss");
        SimpleDateFormat sdfPT = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(2f, 2f, 35f, 5f);

//            File ff= new File("Documentos\\"+user+"\\Seguro Automovel\\");
//            ff.mkdirs();
//            ff =new File(ff.getAbsoluteFile()+"\\"+"Certificado Seguro Automovel "+sdf.format(new Date())+".pdf");
            File ff = new File(arquivo + "/" + user + "/Seguro Automovel/");
            ff.mkdirs();
            String Ddata = sdf.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Certificado Seguro Automovel " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro Automovel/" + "Certificado Seguro Automovel " + Ddata + ".pdf";

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
            
//            MyFooter event = new MyFooter();
//            writer.setPageEvent(event);

            Font fontCabecalhoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10.5f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED , 8f);
            Font fontCorpoP = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f);
            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 7.5f);
            Font fontCorpoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 11f);
            Font fontCabecalhoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);
            Font fontUK = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 5f, Font.ITALIC);
            
            documento.open();
            int total = vf.getInfo().size();
            int i = 0;
            for (Veiculo v : vf.getInfo()) {
                i++;
                PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{25f, 75f});
                pTableEmpresaPricipal.setWidthPercentage(93f);
                PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
//                PdfPTable pTableEmpresaInforImpres2 = new PdfPTable(1);
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

                PdfPCell pCellTeleFax = new PdfPCell(new Phrase(Empresa.TELEFAX+" "+ConfigDoc.Empresa.EMAIL, fontCabecalhoN));
                pCellTeleFax.setBorder(0);

                PdfPCell pCellSociedade = new PdfPCell(new Phrase(Empresa.SOCIEDADE, fontCabecalhoN));
                pCellSociedade.setBorder(0);

                PdfPCell pCellPolice = new PdfPCell(new Phrase(Empresa.APOLICE + numApolice, fontCabecalhoN));
                pCellPolice.setBorder(0);

                Image imageEmpresa = Image.getInstance("logo.png");
                imageEmpresa.scaleToFit(190f, 100f);

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

                PdfPTable pTableTitulo = new PdfPTable(1);
                
                Paragraph PTitulo = new Paragraph(new Phrase("", fontCorpo));
                Phrase pTitulo = new Phrase("Certificado Seguro de automóvel".toUpperCase(),fontCorpoNG);
                Phrase pTituloUK = new Phrase("\nCertificate of motor insurance".toUpperCase(),fontUK);
                PTitulo.add(pTitulo);
                PTitulo.add(pTituloUK);
                
                PdfPCell cellTitulo = new PdfPCell(PTitulo);
                cellTitulo.setBorder(0);
                cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                pTableTitulo.addCell(cellTitulo);

                PdfPTable pTableDetalhes = new PdfPTable(new float[]{55, 45});
                pTableDetalhes.setWidthPercentage(93f);

                Paragraph pNunCetificado = new Paragraph(new Phrase("", fontCorpo));
                pNunCetificado.add(new Phrase("1. Certificado: ", fontCorpo));
                pNunCetificado.add(new Phrase(v.getCertificado()==null || v.getCertificado().equals("")? "":v.getCertificado().toUpperCase(), fontCorpoN));
                pNunCetificado.add(new Phrase("\n1. Certificate NO".toUpperCase(), fontUK));
              pNunCetificado.setPaddingTop(0f);
                
                ClienteI ci = new ClienteI(numCliente);
                Paragraph pNomeSegurado = new Paragraph(new Phrase("", fontCorpo));
                pNomeSegurado.add(new Phrase("3. Segurado: ", fontCorpo));
                pNomeSegurado.add(new Phrase(ci.getNOME_().toUpperCase(), fontCorpoN));
                pNomeSegurado.add(new Phrase("\n3. Insured".toUpperCase(), fontUK));
                pNomeSegurado.setPaddingTop(0f);
                
                Paragraph pMarca = new Paragraph(new Phrase("", fontCorpo));
                pMarca.add(new Phrase("5. Marca do Veiculo: ", fontCorpo));
                pMarca.add(new Phrase((v.getMarca()==null) ? " " : v.getMarca().toUpperCase(), fontCorpoN));
                pMarca.add(new Phrase("\n5. vehicle mark".toUpperCase(), fontUK));
//                pMarca.setPaddingTop(1f);
                
                Paragraph pModelo = new Paragraph(new Phrase("", fontCorpo));
                pModelo.add(new Phrase("7. Modelo do Veiculo: ",fontCorpo));
                pModelo.add(new Phrase((v.getModelo()==null) ? " " : v.getModelo().toUpperCase(),fontCorpoN));
                pModelo.add(new Phrase("\n7. Model Vehicle".toUpperCase(),fontUK));
                pModelo.setPaddingTop(0f);
                
                Paragraph pPeriodo = new Paragraph(new Phrase("", fontCorpo));
                pPeriodo.add( new Phrase("9. Período de Seguro - De: ", fontCorpo));
                pPeriodo.add( new Phrase((sdfPT.format(c.getDataInicio()) + " à " + sdfPT.format(c.getDataFim())).toUpperCase(), fontCorpoN));
                pPeriodo.add( new Phrase("\n9. period of insurance - from  to".toUpperCase(), fontUK));
                pPeriodo.setPaddingTop(0f);
                
                Paragraph pCategoria = new Paragraph(new Phrase("", fontCorpo));
                pCategoria.add( new Phrase("11. Categoria/Uso do Veículo: ", fontCorpo));
                pCategoria.add( new Phrase(" ", fontCorpoN));
                pCategoria.add( new Phrase("\n11. category/use of vehicle".toUpperCase(), fontUK));
                pCategoria.setPaddingTop(0f);
                
                PdfPCell cellDetalhesRi = new PdfPCell();
                cellDetalhesRi.addElement(pNunCetificado);
                cellDetalhesRi.addElement(pNomeSegurado);
                cellDetalhesRi.addElement(pMarca);
                cellDetalhesRi.addElement(pModelo);
                cellDetalhesRi.addElement(pPeriodo);
                cellDetalhesRi.addElement(pCategoria);

                Paragraph pNumApolice = new Paragraph(new Phrase("", fontCorpo));
                pNumApolice.add( new Phrase("2. Apólice Nº ", fontCorpo));
                pNumApolice.add( new Phrase(numApolice, fontCorpoN));
                pNumApolice.add( new Phrase("\n2. policy no".toUpperCase(), fontUK));
                pNumApolice.setPaddingTop(0f);
                
                Paragraph pOcupacaoSegurado = new Paragraph(new Phrase("", fontCorpo));
                pOcupacaoSegurado.add(new Phrase("4. Ocupação do Seguros: ", fontCorpo));
                pOcupacaoSegurado.add(new Phrase(ci.getPROFISSAO_().toUpperCase(), fontCorpoN));
                pOcupacaoSegurado.add(new Phrase("\n4. insured's occupation".toUpperCase(), fontUK));
                pOcupacaoSegurado.setPaddingTop(0f);
                
                Paragraph pNumChassi = new Paragraph(new Phrase("", fontCorpo));
                pNumChassi.add(new Phrase("6. Nº de Chassi/Motor: ", fontCorpo)); 
                pNumChassi.add(new Phrase(v.getChassi() + ((v.getNumMotor()==null || v.getNumMotor().isEmpty() ) ? " " :"/"+v.getNumMotor()), fontCorpoN)); 
                pNumChassi.add(new Phrase("\n6 EnGINE", fontUK)); 
                pNumChassi.setPaddingTop(0f);
                
                Paragraph pDataFabrico = new Paragraph(new Phrase("", fontCorpo));
                pDataFabrico.add(new Phrase("8. Data de Fabrico: ", fontCorpo));
                pDataFabrico.add(new Phrase(((v.getAnoFabrico()==null) ? " " : v.getAnoFabrico()), fontCorpo));
                pDataFabrico.add(new Phrase("\n8. Date of manofacturing", fontUK));
                pDataFabrico.setPaddingTop(0f);
                
                Paragraph pTipoCobertura = new Paragraph(new Phrase("", fontCorpo));
                pTipoCobertura.add(new Phrase("10. Tipo de Cobertura: ", fontCorpo));
                pTipoCobertura.add(new Phrase(((v.getTipoCobertura() != null) ? (vf.getVeiculo().getTipoCobertura().equals("41") ? "Contra Terceiros".toUpperCase() : (vf.getVeiculo().getTipoCobertura().equals("42") ? "CONTRA Todos os riscos".toUpperCase() : (vf.getVeiculo().getTipoCobertura().equals("43") ? "Compreensivo limitado".toUpperCase() : vf.getVeiculo().getTipoCobertura()))) : " "), fontCorpoN));
                pTipoCobertura.add(new Phrase("\n10. Tipo of Cover".toUpperCase(), fontUK));
                pTipoCobertura.setPaddingTop(0f);
                
                 PdfPCell cellDetalhesLe = new PdfPCell();
                cellDetalhesLe.addElement(pNumApolice);
                cellDetalhesLe.addElement(pOcupacaoSegurado);
                cellDetalhesLe.addElement(pNumChassi);
                cellDetalhesLe.addElement(pDataFabrico);
                cellDetalhesLe.addElement(pTipoCobertura);

                pTableDetalhes.addCell(cellDetalhesRi);
                pTableDetalhes.addCell(cellDetalhesLe);
                String f = "¤";
                
                Paragraph para = new Paragraph(new Phrase("", fontCorpo));
                para.add(new Phrase("12. Pessoas ou Classe de Pessoas Habilitadas Para Conduzir",fontCorpoN));
                para.add(new Phrase("\n12. Persons or Class of Persons Entitled to Drive",fontUK));
                pTableDetalhes.addCell(new PdfPCell(para));
                
                para = new Paragraph(new Phrase("", fontCorpo));
                para.add(new Phrase("14. Categorias/Uso de Veículos",fontCorpoN));
                para.add(new Phrase("\n14. Categories / Use of Vehicles",fontUK));
                pTableDetalhes.addCell(new PdfPCell(para));

                Paragraph p11 = new Paragraph(new Phrase("", fontCorpo));
                p11.add(new Phrase("Qualquer pessoa que conduza sob a ordem do detentor da apólice ou com a sua permissão:",fontCorpo));
                p11.add(new Phrase("\nAny person who is driving on the policy holder's or with his permission",fontUK));
                p11.setPaddingTop(0f);
                
                Paragraph p12 = new Paragraph(new Phrase("", fontCorpo));
                p12.add(new Phrase("Desde que o/a condutor/a esteja habilitado/a para conduzir de acordo com a licença/normas que regulam "
                        + "a condução de veículos a motor ou instruções para condução lhe tenha sido passada e não esteja impedido de conduzir por"
                        + " ordem do tribunal ou por alguma outra razão.",fontCorpo));
                p12.add(new Phrase("\nProvided that the person diving is permitted in accodance with the licensing or outher laws or regulations to dive the"
                        + "motor vehicles or has been premitted and is not disiqualified by order of a court of law or reason of any enactment or regulation in that behalf from diving such motor vehice",fontUK));
                p12.setPaddingTop(0f);
                
                Paragraph p13 = new Paragraph(new Phrase("", fontCorpo));
                p13.add(new Phrase("13. Limite para uso:", fontCorpoN));
                p13.add(new Phrase("\n13. Limitation as to use", fontUK));
                p13.setPaddingTop(0f);
                
                Paragraph p14 = new Paragraph(new Phrase("", fontCorpo));
                p14.add(new Phrase("  - Usado apenas como descrito na categoria aqui indicada.", fontCorpo));
                p14.add(new Phrase("\n  - Used only as prescribed under the applicable category stated there in.", fontUK));
                p14.setPaddingTop(0f);
                
                Paragraph p15 = new Paragraph(new Phrase("", fontCorpo));
                p15.add(new Phrase("  - Em todo caso, a apólice não cobre corridas, prova de segurança teste de velocidade, nem para qualquer outro propósito relacionado com a venda do automóvel.", fontCorpo));
                p15.add(new Phrase("\n  - In all cases, the policy does not cover racing, pace-making, reliability trial, speed testing, nor use for any purpose in connection with the trade", fontUK));
                p15.setPaddingTop(0f);
                
                cellDetalhesRi = new PdfPCell();
                cellDetalhesRi.addElement(p11);
                cellDetalhesRi.addElement(p12);
                cellDetalhesRi.addElement(p13);
                cellDetalhesRi.addElement(p14);
                cellDetalhesRi.addElement(p15);

                Paragraph p21 = new Paragraph(new Phrase("", fontCorpo));
                p21.add( new Phrase("CATEGORIA 1 ", fontCorpoN));
                p21.add( new Paragraph("- Veículos usados para actividade social, doméstica e de lazer e do segurado incluindo uso comercial.", fontCorpo));
                p21.add( new Phrase("Vehicles used for social, domestic and pleasure including business use of the insured.", fontUK));
                p21.setPaddingTop(0f);
                
                Paragraph p22 = new Paragraph(new Phrase("", fontCorpo));
                p22.add( new Phrase("CATEGORIA 2 ", fontCorpoN));
                p22.add( new Paragraph("- Veículo pertence ao segurado e usado no transporte de mercadorias.", fontCorpo));
                p22.add( new Phrase("Vehicle used for transportation of good and belonging to the insured", fontUK));
                p22.setPaddingTop(0f);
                
                Paragraph p23 = new Paragraph(new Phrase("", fontCorpo));
                p23.add( new Phrase("CATEGORIA 3 ",fontCorpoN));
                p23.add( new Paragraph("- Veículos alugados utilizados no transporte Comercial de mercadorias pertencente à terceiros.",fontCorpo));
                p23.add( new Phrase("Vehicles used for commercial transportation of good belonging to third parteis fare paying.",fontUK));
                p23.setPaddingTop(0f);
                
                Paragraph p24 = new Paragraph(new Phrase("", fontCorpo));
                p24.add(new Phrase("CATEGORIA 5 ",fontCorpoN));
                p24.add(new Paragraph("- Veículos a motor com duas ou três rodas para o transporte público de passageiro (mediante pagamento de bilhetes).",fontCorpo));
                p24.add(new Phrase("Motor vehicles with two or three wheels for carrying fare paying pessengers.",fontUK));
                p24.setPaddingTop(0f);
                
                Paragraph p25 = new Paragraph(new Phrase("", fontCorpo));
                p25.add( new Phrase("CATEGORIA 6-10 ",fontCorpoN));
                p25.add( new Paragraph("- Veículos para fins especiais: Garagem (6), Escola de Condução (7), Para Aluguer sem motorista (8), Veículo Pesado (9) e Ambulância, Transporte de distribuição de mercadoria, etc. (10).",fontCorpo));
                p25.add( new Phrase("To special purposes vehicles: Garage (6) Driving School (7) Haring without driver ( 8) , Heavy trucks ( 9) and ambulance, refuse disposal vans etc. ( 10).",fontUK));
                p25.setPaddingTop(0f);
                
                cellDetalhesLe = new PdfPCell();
                cellDetalhesLe.addElement(p21);
                cellDetalhesLe.addElement(p22);
                cellDetalhesLe.addElement(p23);
                cellDetalhesLe.addElement(p24);
                cellDetalhesLe.addElement(p25);

                pTableDetalhes.addCell(cellDetalhesRi);
                pTableDetalhes.addCell(cellDetalhesLe);

                PdfPTable pTableArtigo = new PdfPTable(1);
                pTableArtigo.setWidthPercentage(93f);
                Paragraph pArtigoCetificado = new Paragraph(new Phrase("", fontCorpo));
                pArtigoCetificado.add(new Phrase("O Contrato de seguro cessa, nos termos da legislação em vigor, os efeitos às 24 horas do dia da alienação do veículo.", fontCorpo));
                pArtigoCetificado.add(new Phrase("\nThe insurance contract ceases, according to law in force, from its effects from 24 hours from the date of alienation of the vehicle.", fontUK));
                PdfPCell cellArtigoCetificado = new PdfPCell(pArtigoCetificado);
                cellArtigoCetificado.setBorder(0);

                Paragraph pLeis = new Paragraph(new Phrase("", fontCorpo));
                pLeis.add(new Phrase("Este certificado foi emitido em conformidade com os Artigos 1º a 36º, lei nº 30/2000 da República Democrática de São Tomé e Príncipe e que institui a existência de uma cobertura pelo seguro", fontCorpo));
                pLeis.add(new Phrase("\nThis certificate is issurd in pursuat to the prevision of Articles 1 to 36 , Law No. 30/2000 of the Democratic Republic of Sao Tome and Principe and it constitutes the existence of an insurance cover", fontUK));
                PdfPCell cellLeis = new PdfPCell(pLeis);
                cellLeis.setBorder(0);

                pTableArtigo.addCell(cellArtigoCetificado);
                pTableArtigo.addCell(cellLeis);

                PdfPTable pTableAssinaturaTitulo = new PdfPTable(1);
                PdfPTable pTableAssinatura = new PdfPTable(new float[]{50f, 50f});
                PdfPCell cellAssinatora = new PdfPCell(new Phrase("Assinaturas e Carimbo".toUpperCase(), fontCorpoN));
                cellAssinatora.setBorder(0);
                cellAssinatora.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celllinha1 = new PdfPCell(new Phrase("___________________________________".toUpperCase(), fontCorpo));
                celllinha1.setBorder(0);
                celllinha1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celllinha2 = new PdfPCell(new Phrase("___________________________________".toUpperCase(), fontCorpo));
                celllinha2.setBorder(0);
                celllinha2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell celllinha11 = new PdfPCell(new Phrase("Nicon Seguros sa STP".toUpperCase(), fontCorpoP));
                celllinha11.setBorder(0);
                celllinha11.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celllinha21 = new PdfPCell(new Phrase("o segurado ".toUpperCase(), fontCorpoP));
                celllinha21.setBorder(0);
                celllinha21.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                PdfPCell assinaturaUK = new PdfPCell(new Phrase("Signature of Insurer and stamp".toUpperCase(), fontUK));
                assinaturaUK.setBorder(0);
                assinaturaUK.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                assinaturaUK.setColspan(2);

                pTableAssinaturaTitulo.addCell(cellAssinatora);
                pTableAssinatura.addCell(celllinha1);
                pTableAssinatura.addCell(celllinha2);
                pTableAssinatura.addCell(celllinha11);
                pTableAssinatura.addCell(celllinha21);
                pTableAssinatura.addCell(assinaturaUK);
                
                
                documento.add(pTableEmpresaPricipal);
                documento.add(pTableNull);
                documento.add(pTableTitulo);
                documento.add(pTableNull);
                documento.add(pTableNull);
                documento.add(pTableDetalhes);
                documento.add(pTableNull);
                documento.add(pTableArtigo);
                documento.add(pTableNull);
                documento.add(pTableAssinaturaTitulo);
                documento.add(pTableNull);
                documento.add(pTableNull);
                documento.add(pTableAssinatura);
                if(i!=total)
                documento.newPage();
            }
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
    public static void main(String[] args) {
        CertificadoViatura aPG = new CertificadoViatura();
        aPG.criarDoc("ddhd", "223", new Contrato(), new VeiculoBean(), "ah", "STD", "dgg");
    }
}
