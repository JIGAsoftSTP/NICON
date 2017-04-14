/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Fontes;
import bean.ViagemBean;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ClienteDao;
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
public class SeguroViagem implements Serializable {

    private String reString;
    OutputStream outputStraem = null;

    public String criarDoc(ViagemBean vb, String numCriente, String user, String arquivo, Contrato c) {
        try {
            PdfPTable pTablePrincaipal;
//            PdfPTable pTableSegu1;
            PdfPTable pTableSegu2;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            SimpleDateFormat sdfPot = new SimpleDateFormat("dd/MM/yyyy");

            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 7.7f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7.7f);

//            PdfPTable pTableLinha = new PdfPTable(1);
//            pTableLinha.setWidthPercentage(95);
//            PdfPCell linha= new PdfPCell(new Phrase(" "));
//            
//            linha.setBorderWidthTop(0.5f);
//            linha.setBorderWidthBottom(0);
//            linha.setBorderWidthLeft(0);
//            linha.setBorderWidthRight(0);
//            pTableLinha.addCell(linha);
            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 128f, 5f);
            File ff = new File(arquivo + "/" + user + "/Seguro Viagem/");
            ff.mkdirs();
            String Ddata = sdf.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Doc Seguro Viagem " + Ddata + ".pdf");
            reString = "../Documentos/" + user + "/Seguro Viagem/" + "Doc Seguro Viagem " + Ddata + ".pdf";
            outputStraem = new FileOutputStream(ff);
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

            int total = vb.getInfoPessoaSegurada().size();
            for (int i = 0; i < total; i++) {

                pTablePrincaipal = new PdfPTable(1);
                pTablePrincaipal.setWidthPercentage(98f);
//                pTableSegu1 = new PdfPTable(1);
//                pTableSegu1.setWidthPercentage(95);
                pTableSegu2 = new PdfPTable(new float[]{55f,45f});
                pTableSegu2.setWidthPercentage(100f);

//                PdfPCell cellTitulo= new PdfPCell(new Phrase("Lista Pessoas Seguradas",fontCabecalhoN));
//                cellTitulo.setBorder(0);
                PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontCorpo));
                cellNull.setBorder(0);

//                if(((i)%6)==0)
//                {
//                    pTableSegu1.addCell(cellTitulo);
//                    pTableSegu1.addCell(cellNull);
//                }
//                
                Paragraph pCl = new Paragraph();
                pCl.add(new Phrase("Nome do Segurado: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNomePessoaSegurada().toUpperCase(), fontCorpoN));
                PdfPCell cellNome = new PdfPCell(pCl);
                cellNome.setColspan(2);
                cellNome.setBorder(0);
                pTableSegu2.addCell(cellNome);

//                PdfPCell cellNum= new PdfPCell(new Phrase(""+(i+1),fontCorpo));
//                cellNum.setBorder(0);
//                cellNum.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                pTableSegu2.addCell(cellNum);
                pCl = new Paragraph();
                pCl.add(new Phrase("Endereço: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getEndereco().toUpperCase(), fontCorpoN));
                PdfPCell cellEndereco = new PdfPCell(pCl);
                cellEndereco.setBorder(0);
                pTableSegu2.addCell(cellEndereco);
                pTableSegu2.addCell(cellNull);

                pCl = new Paragraph();
                pCl.add(new Phrase("Data e Local Nascimento: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getDataNascFormatada(), fontCorpoN));
                pCl.add(new Phrase(" em ", fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getLocalNascimento(), fontCorpoN));
                PdfPCell cellNascDaNascloc = new PdfPCell(pCl);
                cellNascDaNascloc.setBorder(0);

                pCl = new Paragraph();
                pCl.add(new Phrase("Sexo: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getSexo(), fontCorpoN));
                pCl.add(new Phrase("   Tel.Nº ", fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getTelefone(), fontCorpoN));
                PdfPCell cellSexo = new PdfPCell(pCl);
                cellSexo.setBorder(0);
                pTableSegu2.addCell(cellNascDaNascloc);
                pTableSegu2.addCell(cellSexo);

                pCl = new Paragraph();
                pCl.add(new Phrase("Apolice: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNumApolice(), fontCorpoN));
                PdfPCell cellApolice = new PdfPCell(pCl);
                cellApolice.setBorder(0);

                pCl = new Paragraph();
                pCl.add(new Phrase("Cliente: ".toUpperCase(), fontCorpo));

                String[] codCliente = vb.getInfoPessoaSegurada().get(i).getNumApolice().split("/");

                pCl.add(new Phrase(((codCliente.length == 2) ? "TIN " + codCliente[1] : "TIN "), fontCorpoN));
                PdfPCell cellCliente = new PdfPCell(pCl);
                cellCliente.setBorder(0);
                pTableSegu2.addCell(cellApolice);
                pTableSegu2.addCell(cellCliente);

                pCl = new Paragraph();
                pCl.add(new Phrase("Duração: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNumDias()+" DIAS", fontCorpoN));
                PdfPCell cellDuracao = new PdfPCell(pCl);
                cellDuracao.setBorder(0);

                pCl = new Paragraph();
                pCl.add(new Phrase("Período: de ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(sdfPot.format(vb.getInfoPessoaSegurada().get(i).getDataInicio()), fontCorpoN));
                pCl.add(new Phrase(" À ", fontCorpo));
                pCl.add(new Phrase(((vb.getInfoPessoaSegurada().get(i).getDataFim() != null) ? sdfPot.format(vb.getInfoPessoaSegurada().get(i).getDataFim()) : " "), fontCorpoN));
                PdfPCell cellPeriodo = new PdfPCell(pCl);
                cellPeriodo.setBorder(0);
                pTableSegu2.addCell(cellDuracao);
                pTableSegu2.addCell(cellPeriodo);

                pCl = new Paragraph();
                pCl.add(new Phrase("Data de Emissão: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(sdfPot.format(c.getDataContrato()), fontCorpoN));
                PdfPCell cellDataEmissao = new PdfPCell(pCl);
                cellDataEmissao.setBorder(0);

                pCl = new Paragraph();
                pCl.add(new Phrase("Destino: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(ClienteDao.paisesSelected(vb.getInfoPessoaSegurada().get(i).getPaisDestino().toUpperCase()) + "    ", fontCorpoN));
                pCl.add(new Phrase("Nº de Pessoas: ", fontCorpo));
                pCl.add(new Phrase((i + 1) + "", fontCorpoN));
                PdfPCell cellDestino = new PdfPCell(pCl);
                cellDestino.setBorder(0);
                pTableSegu2.addCell(cellDataEmissao);
                pTableSegu2.addCell(cellDestino);

                pCl = new Paragraph();
                pCl.add(new Phrase("Meio de Identificação: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase( ( ( vb.getInfoPessoaSegurada().get(i).getTipoDoc().toUpperCase().equals("PASSAPORTE")) ? "PASS" : (vb.getInfoPessoaSegurada().get(i).getTipoDoc().toUpperCase().equals("BILHETE IDENTIDADE") ? "BI" : vb.getInfoPessoaSegurada().get(i).getTipoDoc().toUpperCase() ) ), fontCorpoN));
                pCl.add(new Phrase("   Nº ", fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getNumDoc(), fontCorpoN));
                PdfPCell cellEmiDocNum = new PdfPCell(pCl);
                cellEmiDocNum.setBorder(0);

                pCl = new Paragraph();
                pCl.add(new Phrase("Emitido em: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getLocalEmissao().toUpperCase() + " ", fontCorpoN));
                pCl.add(new Phrase("em ", fontCorpo));
                pCl.add(new Phrase(vb.getInfoPessoaSegurada().get(i).getDataEmissaoFormatada(), fontCorpoN));
                PdfPCell cellEmiDoc = new PdfPCell(pCl);
                cellEmiDoc.setBorder(0);
                pTableSegu2.addCell(cellEmiDocNum);
                pTableSegu2.addCell(cellEmiDoc);

                pCl = new Paragraph();
                pCl.add(new Phrase("Outras Informações: ".toUpperCase(), fontCorpo));
                pCl.add(new Phrase(((vb.getInfoPessoaSegurada().get(i).getOutrasInformacoes() == null) ? " " : vb.getInfoPessoaSegurada().get(i).getOutrasInformacoes().toUpperCase()), fontCorpoN));
                PdfPCell cellOutraInf = new PdfPCell(pCl);
                cellOutraInf.setColspan(2);
                cellOutraInf.setBorder(0);
                pTableSegu2.addCell(cellOutraInf);

//                PdfPCell cellSegui1 = new PdfPCell();
//                cellSegui1.addElement(pTableSegu1);
//                cellSegui1.setBorder(0);
                PdfPCell cellSegui2 = new PdfPCell();
                cellSegui2.addElement(pTableSegu2);
                cellSegui2.setBorder(0);

//                PdfPCell cellSeguiT = new PdfPCell();
//                cellSeguiT.addElement(pTableLinha);
//                cellSeguiT.setBorder(0);
//                pTablePrincaipal.addCell(cellSegui1);
                pTablePrincaipal.addCell(cellSegui2);

//                if((i+1)<total&&((i+1)%6)!=0)
//                    pTablePrincaipal.addCell(cellSeguiT);
//                
                documento.add(pTablePrincaipal);
//                
//                System.err.println((((i+1)%6)==0)+" "+(i+1));
                if ((i + 1) != total) {
                    documento.newPage();
                }

            }

            documento.close();
            return reString;

        } catch (DocumentException | IOException ex) {
            Logger.getLogger(SeguroViagem.class.getName()).log(Level.SEVERE, null, ex);
            return reString;
        }
    }

    public static void main(String[] args) {
        SeguroViagem dv = new SeguroViagem();
        dv.criarDoc(new ViagemBean(), "djdd", "ah", "d", new Contrato());
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
