/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import bean.ViagemBean;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import dao.ClienteDao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Contrato;
import org.primefaces.context.RequestContext;


/**
 *
 * @author AhmedJorge
 */
public class SeguroCoberturaViagem implements Serializable
{
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
    static SimpleDateFormat sdfPot = new SimpleDateFormat("dd/MM/yyyy");
    public static void criarDoc(ViagemBean vb, String numCriente, String user, String arquivo, Contrato c)
    {
        try  { 
            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(10f, 10f, 35f, 5f);
            File ff = new File(arquivo + "/" + user + "/Seguro Viagem/");
            ff.mkdirs();
            String Ddata = sdf.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Doc Seguro Viagem-r " + Ddata + ".pdf");
            String reString = "../Documentos/" + user + "/Seguro Viagem/" + "Doc Seguro Viagem-r " + Ddata + ".pdf";
            FileOutputStream outputStraem = new FileOutputStream(ff);
            
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
            for (int i = 0; i < vb.getInfoPessoaSegurada().size() ; i++) { criarDoc(documento,vb, numCriente, user, c,  i);  }
            documento.close();
            
            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
        }
        catch (BadElementException | IOException ex) { Logger.getLogger(SeguroCoberturaViagem.class.getName()).log(Level.SEVERE, null, ex); }
        catch (DocumentException ex) { Logger.getLogger(SeguroCoberturaViagem.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static void criarDoc(Document documento,ViagemBean vb, String numCriente, String user, Contrato c, int i) throws DocumentException, FileNotFoundException, IOException, BadElementException {
        BaseColor colorVermelho = new BaseColor(193, 51, 51);
        BaseColor coloAzul = new BaseColor(0, 0, 179);
        
        Font fontCabecalhoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,10.5f, Font.NORMAL ,coloAzul);
        Font fontCabecalhoTitile= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,14.5f , Font.UNDERLINE, colorVermelho);
        Font fontTableSimple= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8.5f );
        Font fontTableNegrito= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED , 8.5f );
        Font fontTableSimpleMenor= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
        Font fontTableItalico= FontFactory.getFont(Fontes.FONTBI, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
        Font fontTableNormal= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
        Font fontTableSimpleCausulas= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8.999999f );
        
//        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
        
        Font fontCabecalhoNP= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f );
        Font fontCorpo= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8.5f );
        Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f , Font.UNDERLINE, coloAzul);
        
        PdfPTable pTableEmpresaPricipal= new PdfPTable(new float[]{23f,77f});
        pTableEmpresaPricipal.setWidthPercentage(92);
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
        
        
        Image imageEmpresa= Image.getInstance("logo.png");
        imageEmpresa.scaleToFit(120f, 85f);
        
        pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
        pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
        pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
        pTableEmpresaInforImpres1.addCell(pCellTeleFax);
        pTableEmpresaInforImpres1.addCell(pCellSociedade);
        
        PdfPCell cellTabela1= new PdfPCell(pTableEmpresaInforImpres2);
        cellTabela1.setBorder(0);
        
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
        pTableTitulo.setWidthPercentage(92f);
        PdfPCell paragraphTitulo = new PdfPCell(new Phrase("SEGURO DE VIAGEM",fontCabecalhoTitile));
        paragraphTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        paragraphTitulo.setBorder(0);
        pTableTitulo.addCell(paragraphTitulo);
        
        
        PdfPTable pTablePrincipalSegurado = new PdfPTable(new float[]{55,45});
        pTablePrincipalSegurado.setWidthPercentage(92);
        Paragraph pa = new Paragraph(new Phrase("NOME DO SEGURADO: ",fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNomePessoaSegurada().toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado02= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Endereço: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getEndereco().toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado12= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Data e Local de Nascimento: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getDataNascFormatada().toUpperCase(),fontTableNegrito));
        pa.add(new Phrase(" EM",fontTableSimple));
        pa.add(new Phrase(" "+vb.getInfoPessoaSegurada().get(i).getLocalNascimento().toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado21= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Sexo: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getSexo().toUpperCase(),fontTableNegrito));
        pa.add(new Phrase("  Tel: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getTelefone().toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado22= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Apólice: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNumApolice().toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado31= new PdfPCell(pa);
        
        String[] codCliente = vb.getInfoPessoaSegurada().get(i).getNumApolice().split("/");
        
        pa = new Paragraph(new Phrase("Cliente: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(((codCliente.length == 2) ? "TIN " + codCliente[1] : "TIN ").toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado32= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Duração: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNumDias()+" DIAS",fontTableNegrito));
        PdfPCell cellSegurado41= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Período: DE ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(sdfPot.format(vb.getInfoPessoaSegurada().get(i).getDataInicio()).toUpperCase(),fontTableNegrito));
        pa.add(new Phrase(" À ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(((vb.getInfoPessoaSegurada().get(i).getDataFim() != null) ? sdfPot.format(vb.getInfoPessoaSegurada().get(i).getDataFim()) : " ").toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado42= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Data de Emissão: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(sdfPot.format(c.getDataContrato()).toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado51= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Destino: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(ClienteDao.paisesSelected(vb.getInfoPessoaSegurada().get(i).getPaisDestino().toUpperCase()),fontTableNegrito));
        pa.add(new Phrase(" Nº de Pessoas: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase((i+1)+"".toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado52= new PdfPCell(pa);
        
//        PASSAPORTE   BILHETE IDENTIDADE
        pa = new Paragraph(new Phrase("Meio de Identificação: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase((vb.getInfoPessoaSegurada().get(i).getTipoDoc().toUpperCase().equals("PASSAPORTE") ? "PASS" : (vb.getInfoPessoaSegurada().get(i).getTipoDoc().toUpperCase().equals("BILHETE IDENTIDADE") ? "BI" : vb.getInfoPessoaSegurada().get(i).getTipoDoc().toUpperCase() ) ),fontTableNegrito));
        pa.add(new Phrase(" Nº ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNumDoc().toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado61= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Emitido EM: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getLocalEmissao().toUpperCase() + " ".toUpperCase(),fontTableNegrito));
        pa.add(new Phrase(" em ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getDataEmissaoFormatada().toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado62= new PdfPCell(pa);
        
        pa = new Paragraph(new Phrase("Outras Informações: ".toUpperCase(),fontTableSimple));
        pa.add(new Phrase(((vb.getInfoPessoaSegurada().get(i).getOutrasInformacoes() == null) ? " " : vb.getInfoPessoaSegurada().get(i).getOutrasInformacoes().toUpperCase()).toUpperCase(),fontTableNegrito));
        PdfPCell cellSegurado72= new PdfPCell(pa);
        
        cellSegurado02.setBorder(0);
        cellSegurado02.setColspan(2);
        pTablePrincipalSegurado.addCell(cellSegurado02);
        cellSegurado12.setColspan(2);
        cellSegurado12.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado12);
        cellSegurado21.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado21);
        cellSegurado22.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado22);
        cellSegurado31.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado31);
        cellSegurado32.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado32);
        cellSegurado41.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado41);
        cellSegurado42.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado42);
        cellSegurado51.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado51);
        cellSegurado52.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado52);
        cellSegurado61.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado61);
        cellSegurado62.setBorder(0);
        pTablePrincipalSegurado.addCell(cellSegurado62);
        cellSegurado72.setBorder(0);
        cellSegurado72.setColspan(2);
        pTablePrincipalSegurado.addCell(cellSegurado72);
        
        PdfPTable pTableOtros= new PdfPTable(1);
        pTableOtros.setWidthPercentage(92f);
//            PdfPCell paragraphOutros = new PdfPCell(new Phrase("Outros Informações:",fontTableSimple));
        PdfPCell paragraphNotaDebito = new PdfPCell(new Phrase(" ",fontTableNegrito));
        PdfPCell paragraphAmbitoC = new PdfPCell(new Phrase("Âmbito de Cobertura/Condições Particulares:",fontTableSimple));
        
//            paragraphOutros.setBorder(0);
//            pTableOtros.addCell(paragraphOutros);
        paragraphNotaDebito.setBorder(0);
        pTableOtros.addCell(paragraphNotaDebito);
        paragraphAmbitoC.setBorder(0);
        paragraphAmbitoC.setPaddingBottom(5f);
        pTableOtros.addCell(paragraphAmbitoC);
        
        PdfPTable pTableCobertura = new PdfPTable(new float[]{70,30});
        pTableCobertura.setWidthPercentage(92);
        PdfPCell cellTable11= new PdfPCell(new Phrase("COBERTURA",fontTableNegrito));
        PdfPCell cellTable12= new PdfPCell(new Phrase("Limites de Indemnização",fontTableNegrito));
        PdfPCell cellTable21= new PdfPCell(new Phrase("Depesas médicas, Ciúrgias, Farmacêutica e de Hospitalização no Estrangeiro",fontTableSimpleMenor));
        PdfPCell cellTable22= new PdfPCell(new Phrase("Euros 30.000 Franquia: 80 Euros",fontTableSimpleMenor));
        PdfPCell cellTable31= new PdfPCell(new Phrase("Acompanhamento da pessoa segura hospitalizada",fontTableSimpleMenor));
        PdfPCell cellTable32= new PdfPCell(new Phrase("Dia: Euros 70/ Máximo:Euros 700",fontTableSimpleMenor));
        PdfPCell cellTable41= new PdfPCell(new Phrase("Transporte de ida e volta de familiar e estadia",fontTableSimpleMenor));
        PdfPCell cellTable42= new PdfPCell(new Phrase("Dia: Euros 70/ Máximo: Euros 700",fontTableSimpleMenor));
        PdfPCell cellTable51= new PdfPCell(new Phrase("Prolongamento de estadia no Hotel",fontTableSimpleMenor));
        PdfPCell cellTable52= new PdfPCell(new Phrase("Dia: Euros 40/ Máximo: Euros 400",fontTableSimpleMenor));
        PdfPCell cellTable61= new PdfPCell(new Phrase("Repatriamento / Transporte sanitário de feridos e doentes",fontTableSimpleMenor));
        PdfPCell cellTable62= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable71= new PdfPCell(new Phrase("Repatriamento / Transporte após a morte",fontTableSimpleMenor));
        PdfPCell cellTable72= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable81= new PdfPCell(new Phrase("Transporte ou Repatriamento das restantes pessoas seguras",fontTableSimpleMenor));
        PdfPCell cellTable82= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable91= new PdfPCell(new Phrase("Supervisão de crianças no estrangeiro",fontTableSimpleMenor));
        PdfPCell cellTable92= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable101= new PdfPCell(new Phrase("Regresso antecipado por falecimento de familiar",fontTableSimpleMenor));
        PdfPCell cellTable102= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable111= new PdfPCell(new Phrase("Localização e envio de medicamentos de urgência",fontTableSimpleMenor));
        PdfPCell cellTable112= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable121= new PdfPCell(new Phrase("Transporte ou repatriamento de bagagens",fontTableSimpleMenor));
        PdfPCell cellTable122= new PdfPCell(new Phrase("Limite imposto pela Empresa Transportadora",fontTableSimpleMenor));
        PdfPCell cellTable131= new PdfPCell(new Phrase("Adiantamento de fundos no estrangeiro",fontTableSimpleMenor));
        PdfPCell cellTable132= new PdfPCell(new Phrase("Máximo: Euros 700",fontTableSimpleMenor));
        PdfPCell cellTable141= new PdfPCell(new Phrase("Aconselhamento médico",fontTableSimpleMenor));
        PdfPCell cellTable142= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable151= new PdfPCell(new Phrase("Pagamento de despesas de comunicação",fontTableSimpleMenor));
        PdfPCell cellTable152= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        PdfPCell cellTable161= new PdfPCell(new Phrase("Serviços informativos",fontTableSimpleMenor));
        PdfPCell cellTable162= new PdfPCell(new Phrase("Ilimitado",fontTableSimpleMenor));
        
        cellTable11.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable11);
        cellTable12.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable12);
        pTableCobertura.addCell(cellTable21);
        cellTable22.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable22);
        pTableCobertura.addCell(cellTable31);
        cellTable32.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable32);
        pTableCobertura.addCell(cellTable41);
        cellTable42.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable42);
        pTableCobertura.addCell(cellTable51);
        cellTable52.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable52);
        pTableCobertura.addCell(cellTable61);
        cellTable62.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable62);
        pTableCobertura.addCell(cellTable71);
        cellTable72.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable72);
        pTableCobertura.addCell(cellTable81);
        cellTable82.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable82);
        pTableCobertura.addCell(cellTable91);
        cellTable92.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable92);
        pTableCobertura.addCell(cellTable101);
        cellTable102.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable102);
        pTableCobertura.addCell(cellTable111);
        cellTable112.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable112);
        pTableCobertura.addCell(cellTable121);
        cellTable122.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable122);
        pTableCobertura.addCell(cellTable131);
        cellTable132.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable132);
        pTableCobertura.addCell(cellTable141);
        cellTable142.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable142);
        pTableCobertura.addCell(cellTable151);
        cellTable152.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable152);
        pTableCobertura.addCell(cellTable161);
        cellTable162.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTableCobertura.addCell(cellTable162);
        
        PdfPTable pTableCausuala = new PdfPTable(1);
        pTableCausuala.setWidthPercentage(92);
        PdfPCell paragraphParaFamilha = new PdfPCell(new Phrase("1. Para Família e/ou Grupo, fornecer uma lista anexada com outros detalhes necessários.",fontTableSimpleCausulas));
        paragraphParaFamilha.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        paragraphParaFamilha.setPaddingBottom(7f);
        
        PdfPCell paragraphOPresente = new PdfPCell(new Phrase("2. O presente Contrato baseia-se na proposta do Segurador e é regido pelos termos referidos nas "
                + "Condições Gerais e Especiais e de Exclusões anexadas ao presente documento.",fontTableSimpleCausulas));
        paragraphOPresente.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        paragraphOPresente.setPaddingBottom(7f);
        
        PdfPCell paragraphOPremio = new PdfPCell(new Phrase("3. O Prémio pago não é reembolsável excepto a rejeição da apólice pela Embaixada.",fontTableSimpleCausulas));
        paragraphOPremio.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        paragraphOPremio.setPaddingBottom(7f);
        
        PdfPCell paragraphOSeguro = new PdfPCell(new Phrase("4. O Segurado declara que as informações prestadas são verdadeiras e completas e declara ter "
                + "lido as condições Gerais e Especiais e as Exclusões e concordado com as disposições nelas contidas.",fontTableSimpleCausulas));
        paragraphOSeguro.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        paragraphOSeguro.setPaddingBottom(7f);
        
        PdfPCell paragraphSolitacao = new PdfPCell(new Phrase("5. Solicitação de reembolso deve ser feita por carta dirigida a Directora Geral da NICON Seguros STP, "
                + "3 dias antes da data de inicio do seguro, e deve ser acompanhada da apólice original e da fotocópia da carta de rejeição da Embaixada.",fontTableSimpleCausulas));
        paragraphSolitacao.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//            paragraphSolitacao.setPaddingBottom(7f);
        
        paragraphParaFamilha.setBorder(0);
        pTableCausuala.addCell(paragraphParaFamilha);
        paragraphOPresente.setBorder(0);
        pTableCausuala.addCell(paragraphOPresente);
        paragraphOPremio.setBorder(0);
        pTableCausuala.addCell(paragraphOPremio);
        paragraphOSeguro.setBorder(0);
        pTableCausuala.addCell(paragraphOSeguro);
        paragraphSolitacao.setBorder(0);
        pTableCausuala.addCell(paragraphSolitacao);
        
        PdfPTable pTableNota= new PdfPTable(1);
        pTableNota.setWidthPercentage(92);
        PdfPCell paragraphNota = new PdfPCell(new Phrase("É condição indispensável para usufruir das garantias deste contrato que o Subscritor ou Pessoas Seguradas contactem de imediato:",fontTableItalico));
        paragraphNota.setBorder(0);
        pTableNota.addCell(paragraphNota);
        
        PdfPTable pTableRotape= new PdfPTable(1);
        pTableRotape.setWidthPercentage(92);
        Paragraph p= new Paragraph();
        Image imageRotape= Image.getInstance(ConfigDoc.Fontes.getDiretorio()+"/europ-assistance2.jpg");
        imageRotape.scaleToFit(90, 40);
        p.add(new Chunk(imageRotape, 1, 1, true));
        
        Phrase phraseRodape =new Phrase(", COMPAINHA PORTUGUESA DE SEGUROS DE ASSISTÊNCIA, Tel. 351 21 384 80 97 Fax: 351 21 386 03 08 www.europ-assitance.pt",fontTableNormal);
        p.add(phraseRodape);
        
        PdfPCell cellRodape= new PdfPCell(p);
        cellRodape.setPaddingTop(10f);
        cellRodape.setBorder(0);
        pTableRotape.addCell(cellRodape);
        
        PdfPTable pTableAsinatura= new PdfPTable(2);
        pTableAsinatura.setWidthPercentage(92);
        PdfPCell cellSeguradora= new PdfPCell(new Phrase("A Seguradora",fontTableSimpleCausulas));
        cellSeguradora.setBorder(0);
        cellSeguradora.setPaddingTop(25);
        cellSeguradora.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cellSegurado= new PdfPCell(new Phrase("O Segurado",fontTableSimpleCausulas));
        cellSegurado.setPaddingTop(25);
        cellSegurado.setBorder(0);
        cellSegurado.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cellSeguradoValor= new PdfPCell(new Phrase("____________________________",fontTableSimpleCausulas));
        cellSeguradoValor.setBorder(0);
        cellSeguradoValor.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        pTableAsinatura.addCell(cellSeguradora);
        pTableAsinatura.addCell(cellSegurado);
        pTableAsinatura.addCell(cellSeguradoValor);
        pTableAsinatura.addCell(cellSeguradoValor);
        
//        documento.open();
        documento.add(pTableEmpresaPricipal);
        documento.add(pTableTitulo);
        documento.add(pTableNull);
        documento.add(pTablePrincipalSegurado);
        documento.add(pTableOtros);
//            documento.add(pTableNull);
        documento.add(pTableCobertura);
        documento.add(pTableNull);
        documento.add(pTableCausuala);
        documento.add(pTableNull);
        documento.add(pTableNota);
        documento.add(pTableRotape);
        documento.add(pTableNull);
        documento.add(pTableAsinatura);
        
        if((i+1) != vb.getInfoPessoaSegurada().size())
            documento.newPage();
        
        //PrintPdf printPdf = new PrintPdf("Cobertura Viagem.pdf", "Cobertura Viagem.pdf", 0, 595f,842f,"Enviar Para o OneNote 2013",1);
//            PrintPdf printPdf = new PrintPdf("Cobertura Viagem.pdf", "Cobertura Viagem.pdf", 0, 595f,842f,"Hewlett-Packard HP LaserJet P2035",1);
             
//            printPdf.print();
    }
    
    public static void main(String[] args) {
        SeguroCoberturaViagem.criarDoc(null, null, null, null, null);
    }
}
