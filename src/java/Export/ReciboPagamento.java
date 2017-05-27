/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
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
import com.itextpdf.text.pdf.PdfPageEventHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import conexao.Call;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.JTextPane;
import lib.Moeda;
import validacao.OperacaoData;

/**
 *
 * @author AhmedJorge
 */
public class ReciboPagamento {

    private String reString;
    String IDAMORTIZACAO = "ID AMORTIZACAO";
    String IDCONTRATO = "ID CONTRATO";
    String APOLICA = "APOLICA";
    String DATAREGAPOLICE = "DATA REG APOLICE";
    String NUMREGISTRO = "NUM REGISTRO";
    String IDPRESTACAO = "ID PRESTACAO";
    String ACCESSORIO = "ACCESSORIO";
    String ASEGURADO = "ASEGURADO";
    String IMPOSTOCONSUMO = "IMPOSTO CONSUMO";
    String IMPOSTOSELO = "IMPOSTO SELO";
    String MOTORFOUND = "MOTOR FOUND";
    String TOTAl = "TOTAL";
    String TOTALBRUTO = "TOTAL BRUTO";
    String EFEITO = "EFEITO";
    String VENCIMENTO = "VENCIMENTO";
    String CLIENTE = "CLIENTE";
    String TIPOPAGAMENTO = "TIPO PAGAMENTO";
    String DOCUMENTOAMORTIZACAO = "DOCUMENTO AMORTIZACAO";
    String BANCO = "BANCO";
    String COATA = "COATA";
    String NOMEMOEDA = "NOME MOEDA";
    String SIGLAMOEDA = "SIGLA MOEDA";
    String TAXACOMPRA = "TAXA COMPRA";
    String TAXAVENDA = "TAXA VENDA";
    String VALORPRESTACAO = "VALOR PRESTACAO";
    String VALORAMORTIZADO = "VALOR AMORTIZADO";
    String REGISTROAMORTIZACAO = "REGISTRO AMORTIZACAO";

    Integer numPrestacao;
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

    class MyFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas1 = writer.getDirectContentUnder();
                Image image1 = Image.getInstance("rodape.png");
                image1.scaleAbsolute(PageSize.A4);
                image1.scaleToFit(545f, 300f);
                image1.setAbsolutePosition(document.getPageSize().getWidth() - 320, 480);
                canvas1.saveState();
                PdfGState state1 = new PdfGState();
                state1.setFillOpacity(0.2f);
                canvas1.setGState(state1);
                canvas1.addImage(image1);
                canvas1.restoreState();

                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance("rodape.png");
                image.scaleAbsolute(PageSize.A4);
                image.scaleToFit(545f, 300f);
                image.setAbsolutePosition(document.getPageSize().getWidth() - 320, 60);
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

    public class daoRecibo {

        public HashMap<String, Object> getDados() {
            HashMap<String, Object> mapLista = new HashMap<>();
            ResultSet rs = Call.selectFrom("VER_AMORTIZACAO where \"ID PRESTACAO\" = ?", "*", numPrestacao);
            Consumer<HashMap<String, Object>> act = (map) -> {
                mapLista.putAll(map);
            };
            Call.forEchaResultSet(act, rs);
            return mapLista;
        }
    }

    public String converterData(Object o, int i) {
        SimpleDateFormat sdfout = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfD = new SimpleDateFormat("dd-MM-yyyy");
        if (o != null) {
            try {
                if (i == 1) {
                    System.err.println(o);
                    return sdf2.format(sdfout.parse(o.toString()));
                } else {
                    System.err.println(o);
                    System.err.println( sdfD.format(sdfout.parse(o.toString())) );
                    return sdfD.format(sdfout.parse(o.toString()));
                }
            } catch (ParseException ex) {
                return " ";
            }
        } else {
            return " ";
        }
    }

    public String converterMoeda(Object o, String moeda) {
        if (o != null) {
            return Moeda.format(Moeda.arrendodamento(o.toString())) + " " + moeda;
        } else {
            return " ";
        }
    }

    public static void main(String[] args) {
        new ReciboPagamento().criarDoc(291, "Ah");
    }

    public Double impostosCalculo(Object valor, Object taxa) {
        if (taxa != null && taxa != "") {
            try {
                return Moeda.arrendodamento(valor+"") * ((Double.valueOf(taxa.toString()) / 100));
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }
    double taxa = 0;

    public double valorCompra(String nomeMoeda, Object date) {
        try {
            String idMoe = "0";

            try (ResultSet rss = Call.selectFrom("VER_MOEDA where SIGLA = ? ", "*", nomeMoeda)) { while (rss.next()) {    idMoe = rss.getString("ID"); } }

            ResultSet rs; {  rs = Call.callTableFunction("PACK_CONTA.getTaxaDay", "*", date, idMoe); }

            Consumer<HashMap<String, Object>> act = (map) -> {  taxa = Double.valueOf(toString(map.get("TX_VENDA"))); };

            Call.forEchaResultSet(act, rs);
            return taxa;

        } catch (SQLException ex) {
            Logger.getLogger(DocNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taxa;
    }

    public static String toString(Object o) {
        return (o == null) ? "" : o.toString();
    }

//    public static void main(String[] args) {
//        System.err.println(new ReciboPagamento().valorCompra("STD"));
//    }
    /**
     * new Documento Pagamento
     *
     * @param Prestacao
     * @param user
     * @return String
     */
    public String criarDoc(Integer Prestacao, String user) {
        try {

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            numPrestacao = Prestacao;
            File ff = new File(ConfigDoc.Fontes.getDiretorio() + "/" + user + "/Pagamentos/");

            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Recebimento " + Ddata + ".pdf");
            OutputStream outputStraem = new FileOutputStream(ff);

            reString = "../Documentos/" + user + "/Pagamentos/" + "Recebimento " + Ddata + ".pdf";
            Document document = new Document(PageSize.A4);
            document.setMargins(20f, 20f, 0f, 0f);
            PdfWriter writer = PdfWriter.getInstance(document, outputStraem);
            Font fontTitile = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 17f, Font.NORMAL, BaseColor.BLUE.darker().darker());
            Font fontTitileShort = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f, Font.NORMAL, BaseColor.BLUE.darker().darker());
            Font fontRecibo = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f, Font.NORMAL, BaseColor.BLUE.darker().darker());
            Font fontReciboTxt = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f, Font.NORMAL, BaseColor.BLACK.darker().darker().darker());
            Font fontConteudo = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f, Font.NORMAL, BaseColor.BLUE.darker().darker());
            Font fontConteudoTxt = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f, Font.ITALIC, BaseColor.BLACK.darker().darker().darker());
            Font fontConteudoTxtUl = FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 7f, Font.ITALIC + Font.UNDERLINE, BaseColor.BLACK.darker().darker().darker());

            MyFooter event = new MyFooter();
            writer.setPageEvent(event);
            daoRecibo r = new daoRecibo();
            HashMap<String, Object> map = r.getDados();
            document.open();
            PdfPTable table = detaDoc(map, fontTitile, fontRecibo, fontTitileShort, fontReciboTxt, fontConteudo, fontConteudoTxt, fontConteudoTxtUl);
//            document.add(table);
//            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n", fontTitileShort));
//            document.add(table);
//            document.newPage();
            table.setTotalWidth(555);
            table.writeSelectedRows(-1, 100, 22.5f, 820f, writer.getDirectContent());
            table.writeSelectedRows(-1, 100, 22.5f, 400f, writer.getDirectContent());
//            table.writeSelectedRows(-1, 2, 52.5f, 402.5f, writer.getDirectContent());
            document.close();
            return reString;
        } catch (DocumentException ex) {
            Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            return reString;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reString;
    }

    public PdfPTable detaDoc(HashMap<String, Object> map, Font fontTitile, Font fontRecibo, Font fontTitileShort, Font fontReciboTxt, Font fontConteudo, Font fontConteudoTxt, Font fontConteudoTxtUl) throws DocumentException {
        PdfPTable pTablePrincipal = new PdfPTable(new float[]{100});
        try {
            pTablePrincipal.setWidthPercentage(100f);

            PdfPTable pTableTitulo = new PdfPTable(new float[]{60, 40});
            pTableTitulo.setWidthPercentage(100);

            PdfPTable pTableTituloImage = new PdfPTable(new float[]{20, 80});
            pTableTituloImage.setWidthPercentage(100);

            PdfPCell cellTitulo = new PdfPCell(new Phrase(ConfigDoc.Empresa.NOME, fontTitile));
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTitulo.setBorder(0);
            cellTitulo.setPaddingTop(20f);
            cellTitulo.setPaddingLeft(70f);
            pTableTitulo.addCell(cellTitulo);
            cellTitulo = new PdfPCell(new Phrase("Recibo Nº ".toUpperCase() + map.get(IDAMORTIZACAO), fontRecibo));
            cellTitulo.setVerticalAlignment(Element.ALIGN_BOTTOM);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTitulo.setBorder(0);
            pTableTitulo.addCell(cellTitulo);

            cellTitulo = new PdfPCell(new Phrase(ConfigDoc.Empresa.ENDERECO + ", " + ConfigDoc.Empresa.CAIXAPOSTAL + ", " + ConfigDoc.Empresa.TELEFAX, fontTitileShort));
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTitulo.setPaddingLeft(70f);
            cellTitulo.setBorder(0);
            pTableTitulo.addCell(cellTitulo);
            cellTitulo = new PdfPCell(new Phrase(" ", fontTitileShort));
            cellTitulo.setPaddingBottom(0f);
            cellTitulo.setPaddingTop(0f);
            cellTitulo.setBorder(0);
            pTableTitulo.addCell(cellTitulo);

            cellTitulo = new PdfPCell(new Phrase(ConfigDoc.Empresa.REPUBLICA, fontTitileShort));
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTitulo.setBorder(0);
            cellTitulo.setPaddingLeft(70f);
            pTableTitulo.addCell(cellTitulo);
            cellTitulo = new PdfPCell(new Phrase(" ", fontTitileShort));
            cellTitulo.setPaddingBottom(0f);
            cellTitulo.setPaddingTop(0f);
            cellTitulo.setBorder(0);
            pTableTitulo.addCell(cellTitulo);

            cellTitulo = new PdfPCell(new Phrase(ConfigDoc.Empresa.EMAIL, fontTitileShort));
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTitulo.setBorder(0);
            cellTitulo.setPaddingLeft(70f);
            pTableTitulo.addCell(cellTitulo);
            cellTitulo = new PdfPCell(new Phrase(" ", fontTitileShort));
            cellTitulo.setPaddingBottom(0f);
            cellTitulo.setPaddingTop(0f);
            cellTitulo.setBorder(0);
            pTableTitulo.addCell(cellTitulo);

            cellTitulo = new PdfPCell(new Phrase("Ordem de receita / recibo".toUpperCase(), fontRecibo));
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTitulo.setBorder(0);
            cellTitulo.setPaddingLeft(70f);
            pTableTitulo.addCell(cellTitulo);
            cellTitulo = new PdfPCell(new Phrase(" ", fontTitileShort));
            cellTitulo.setVerticalAlignment(Element.ALIGN_TOP);
            cellTitulo.setPaddingBottom(30f);
            cellTitulo.setPaddingTop(0f);
            cellTitulo.setBorder(0);
            pTableTitulo.addCell(cellTitulo);

            Image imageEmpresa = Image.getInstance("logo.png");
            imageEmpresa.scaleToFit(120f, 85f);
            imageEmpresa.setAlignment(Image.ALIGN_CENTER);

            PdfPCell cellImagem = new PdfPCell(imageEmpresa);
            cellImagem.setBorder(PdfPCell.NO_BORDER);
            cellImagem.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cellImagem.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

            PdfPCell cellpTableTitulo = new PdfPCell(pTableTitulo);
            cellpTableTitulo.setBorder(PdfPCell.NO_BORDER);
            cellpTableTitulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            pTableTituloImage.addCell(cellImagem);
            pTableTituloImage.addCell(cellpTableTitulo);

            PdfPCell cellpTableTituloPrincipal = new PdfPCell(pTableTituloImage);
            cellpTableTituloPrincipal.setBorder(PdfPCell.NO_BORDER);

            pTablePrincipal.addCell(cellpTableTituloPrincipal);

            PdfPTable pTableDataCaixaMontante = new PdfPTable(new float[]{35f, 5f, 60f});
            pTableDataCaixaMontante.setWidthPercentage(100f);

            PdfPCell cellDataCaixaMontante = new PdfPCell(new Phrase(" ", fontRecibo));
            cellDataCaixaMontante.setBorder(0);
            pTableDataCaixaMontante.addCell(cellDataCaixaMontante);
            pTableDataCaixaMontante.addCell(cellDataCaixaMontante);
            cellDataCaixaMontante = new PdfPCell();
            cellDataCaixaMontante.setBorder(0);
            Paragraph p = new Paragraph();
            p.add(new Phrase("Data: ".toUpperCase(), fontRecibo));

            p.add(new Phrase(converterData(map.get(REGISTROAMORTIZACAO), 1), fontReciboTxt));
            p.setAlignment(Element.ALIGN_RIGHT);
            cellDataCaixaMontante.addElement(p);
            cellDataCaixaMontante.setPaddingBottom(5f);
            pTableDataCaixaMontante.addCell(cellDataCaixaMontante);

            cellDataCaixaMontante = new PdfPCell();
            p = new Paragraph();
            p.add(new Phrase("Caixa de: ".toUpperCase(), fontRecibo));
            p.add(new Phrase("São Tomé", fontReciboTxt));
            p.setAlignment(Element.ALIGN_LEFT);
            cellDataCaixaMontante.addElement(p);
            cellDataCaixaMontante.setPaddingTop(-2f);
            cellDataCaixaMontante.setPaddingBottom(5f);
            cellDataCaixaMontante.setBorderColor(BaseColor.BLUE.darker().darker().darker());
            pTableDataCaixaMontante.addCell(cellDataCaixaMontante);

            cellDataCaixaMontante = new PdfPCell(new Phrase(" ", fontRecibo));
            cellDataCaixaMontante.setBorder(0);
            pTableDataCaixaMontante.addCell(cellDataCaixaMontante);

            cellDataCaixaMontante = new PdfPCell();
            p = new Paragraph();
            p.add(new Phrase("Montante: ".toUpperCase(), fontRecibo));

            Double montade = Moeda.arrendodamento(toString(map.get(VALORAMORTIZADO)));

//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:");
            Object dRegistro = map.get(DATAREGAPOLICE);
            montade *= valorCompra(map.get(SIGLAMOEDA) + "", dRegistro);

            p.add(new Phrase(converterMoeda(montade, "STD") + "", fontReciboTxt));
            p.setAlignment(Element.ALIGN_LEFT);
            cellDataCaixaMontante.addElement(p);
            cellDataCaixaMontante.setPaddingTop(-2f);
            cellDataCaixaMontante.setPaddingBottom(5f);
            cellDataCaixaMontante.setBorderColor(BaseColor.BLUE.darker().darker().darker());
            pTableDataCaixaMontante.addCell(cellDataCaixaMontante);

            PdfPCell cellDataCaixaMontantePrincipal = new PdfPCell(pTableDataCaixaMontante);
            cellDataCaixaMontantePrincipal.setBorder(PdfPCell.NO_BORDER);

            pTablePrincipal.addCell(cellDataCaixaMontantePrincipal);

            PdfPTable pTableConteudo = new PdfPTable(new float[]{40, 60});
            pTableConteudo.setWidthPercentage(100f);
            PdfPCell cellConteudo = new PdfPCell();
            cellConteudo.setBorder(0);

            Paragraph pConteudo = new Paragraph();
            pConteudo.add(new Phrase("Nº Assegurado: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(map.get(NUMREGISTRO) + "\n", fontConteudoTxt));

            /**
             * For alter
             */
            pConteudo.add(new Phrase("Prémio Liquido: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(converterMoeda(map.get(TOTALBRUTO), map.get(SIGLAMOEDA) + "") + "\n", fontConteudoTxt));

            pConteudo.add(new Phrase("Impostos: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(converterMoeda(impostosCalculo(map.get(TOTAl), map.get(IMPOSTOCONSUMO)), map.get(SIGLAMOEDA) + "") + "\n", fontConteudoTxt));

            pConteudo.add(new Phrase("Impostos Selo: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(converterMoeda(impostosCalculo(map.get(TOTAl), map.get(IMPOSTOSELO)), map.get(SIGLAMOEDA) + "") + "\n", fontConteudoTxt));

            pConteudo.add(new Phrase("FGA: ", fontConteudo));
            pConteudo.add(new Phrase(converterMoeda(impostosCalculo(map.get(TOTAl), map.get(MOTORFOUND)), map.get(SIGLAMOEDA) + "") + "\n", fontConteudoTxt));

            pConteudo.add(new Phrase("Total: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(converterMoeda(map.get(TOTAl), map.get(SIGLAMOEDA) + "") + "\n", fontConteudoTxt));

            pConteudo.add(new Phrase("Efeito: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(converterData(map.get(EFEITO), 1) + "\n", fontConteudoTxt));

            pConteudo.add(new Phrase("Vencimento: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(converterData(map.get(VENCIMENTO), 1) + "\n", fontConteudoTxt));

            cellConteudo.addElement(pConteudo);
            pTableConteudo.addCell(cellConteudo);

            cellConteudo = new PdfPCell();
            cellConteudo.setBorder(0);

            pConteudo = new Paragraph();
            pConteudo.add(new Phrase("Recebido do Sr: ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(map.get(CLIENTE) + "\n", fontConteudoTxt));

            PdfPTable pTableNomeCod = new PdfPTable(new float[]{70, 30});
            pTableNomeCod.setWidthPercentage(100);

            Paragraph pNomeCod = new Paragraph();
            pNomeCod.add(new Phrase("Nome do Assegurado/Pagador: ".toUpperCase(), fontConteudo));
            pNomeCod.add(new Phrase(map.get(CLIENTE) + "", fontConteudoTxt));
            PdfPCell cellNomeCod = new PdfPCell();
            cellNomeCod.addElement(pNomeCod);
            cellNomeCod.setBorder(0);
            cellNomeCod.setPaddingTop(-2f);
            cellNomeCod.setPaddingBottom(2f);
            pTableNomeCod.addCell(cellNomeCod);

            cellNomeCod = new PdfPCell();
            pNomeCod = new Paragraph();
            pNomeCod.add(new Phrase("Codigo: ".toUpperCase(), fontConteudo));
            pNomeCod.add(new Phrase(" ", fontConteudoTxt));
            cellNomeCod.addElement(pNomeCod);
            cellNomeCod.setBorder(0);
            cellNomeCod.setPaddingTop(-2f);
            cellNomeCod.setPaddingBottom(2f);
            pTableNomeCod.addCell(cellNomeCod);

            pNomeCod = new Paragraph();
            pNomeCod.add(new Phrase("Nome do Intermediário: ".toUpperCase(), fontConteudo));
            pNomeCod.add(new Phrase(" ", fontConteudoTxt));
            cellNomeCod = new PdfPCell();
            cellNomeCod.addElement(pNomeCod);
            cellNomeCod.setBorder(0);
            cellNomeCod.setPaddingTop(-2f);
            cellNomeCod.setPaddingBottom(2f);
            pTableNomeCod.addCell(cellNomeCod);

            cellNomeCod = new PdfPCell();
            pNomeCod = new Paragraph();
            pNomeCod.add(new Phrase("Codigo: ".toUpperCase(), fontConteudo));
            pNomeCod.add(new Phrase(" ", fontConteudoTxt));
            cellNomeCod.addElement(pNomeCod);
            cellNomeCod.setBorder(0);
            cellNomeCod.setPaddingTop(-2f);
            cellNomeCod.setPaddingBottom(2f);
            pTableNomeCod.addCell(cellNomeCod);

            pConteudo.add(pTableNomeCod);

            JTextPane jtp = new JTextPane();
            Double valor = Moeda.arrendodamento((map.get(VALORPRESTACAO) + ""));
            valor *= valorCompra(map.get(SIGLAMOEDA) + "", dRegistro);
            Moeda.EscreverEstenso(valor, jtp, "Dobras");

            pConteudo.add(new Phrase("A soma (por extenso) ".toUpperCase(), fontConteudo));
            pConteudo.add(new Phrase(jtp.getText().trim() + "\n", fontConteudoTxt));

            PdfPTable pTableApoliceDatasP = new PdfPTable(new float[]{20, 80});
            pTableApoliceDatasP.setWidthPercentage(100);
            PdfPTable pTableApoliceDatas = new PdfPTable(new float[]{60, 40});
            pTableApoliceDatas.setWidthPercentage(100);

            for (int i = 0; i < 1; i++) {
                PdfPCell cellApolice = new PdfPCell();
                Paragraph pApolice = new Paragraph();
                pApolice.add(new Phrase("Nº ", fontConteudo));
                pApolice.add(new Phrase(map.get(APOLICA) + "", fontConteudoTxt));
                cellApolice.addElement(pApolice);
                cellApolice.setPaddingTop(-2f);
                cellApolice.setPaddingBottom(2f);
                cellApolice.setBorder(0);
                pTableApoliceDatas.addCell(cellApolice);

                PdfPCell cellDatas = new PdfPCell();
                Paragraph pDatas = new Paragraph();
                pDatas.add(new Phrase("Data: ".toUpperCase(), fontConteudo));
                pDatas.add(new Phrase(converterData(map.get(DATAREGAPOLICE), 2) + "", fontConteudoTxt));
                cellDatas.addElement(pDatas);
                cellDatas.setBorder(0);
                cellDatas.setPaddingTop(-2f);
                cellDatas.setPaddingBottom(2f);
                pTableApoliceDatas.addCell(cellDatas);
            }
            PdfPCell cellApoliceP = new PdfPCell(new Phrase("Apolices".toUpperCase(), fontConteudo));
            cellApoliceP.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellApoliceP.setBorder(0);
            pTableApoliceDatasP.addCell(cellApoliceP);
            cellApoliceP = new PdfPCell(pTableApoliceDatas);
            cellApoliceP.setBorder(0);
            pTableApoliceDatasP.addCell(cellApoliceP);

            pConteudo.add(pTableApoliceDatasP);
//            ▬↑†▄♠◘
            PdfPTable pTableTipoPagemento = new PdfPTable(new float[]{70f, 30f});
            pTableTipoPagemento.setWidthPercentage(100f);
            
            PdfPCell cellTipoPageme = new PdfPCell();
            Paragraph pDatas = new Paragraph();
            pDatas.add(new Phrase("Pagamento: ".toUpperCase(), fontConteudo));
            pDatas.add(new Phrase(map.get(TIPOPAGAMENTO) + " ", fontConteudoTxt));
            cellTipoPageme.addElement(pDatas);
            cellTipoPageme.setBorder(0);
            cellTipoPageme.setPaddingTop(-2f);
            cellTipoPageme.setPaddingBottom(2f);
//           cellTipoPageme.setBorderColor(BaseColor.BLUE.darker().darker().darker());
//           cellTipoPageme.setBorderWidthTop((i==0)?0.5f:0f);
//           cellTipoPageme.setBorderWidthBottom(0.5f);
            pTableTipoPagemento.addCell(cellTipoPageme);

            cellTipoPageme = new PdfPCell();
            pDatas = new Paragraph();
            pDatas.add(new Phrase("Nº ".toUpperCase(), fontConteudo));
            pDatas.add(new Phrase(toString(map.get(DOCUMENTOAMORTIZACAO)) + " ", fontConteudoTxt));
            cellTipoPageme.addElement(pDatas);
            cellTipoPageme.setBorder(0);
            cellTipoPageme.setPaddingTop(-2f);
            cellTipoPageme.setPaddingBottom(2f);
//                cellTipoPageme.setBorderColor(BaseColor.BLUE.darker().darker().darker());
//                cellTipoPageme.setBorderWidthTop((i==0)?0.5f:0f);
//                cellTipoPageme.setBorderWidthBottom(0.5f);
            pTableTipoPagemento.addCell(cellTipoPageme);

            pConteudo.add(pTableTipoPagemento);

            cellConteudo.addElement(pConteudo);
            pTableConteudo.addCell(cellConteudo);

            PdfPCell cellConteudoPrincipal = new PdfPCell(new PdfPTable(pTableConteudo));
            cellConteudoPrincipal.setBorder(PdfPCell.NO_BORDER);
            pTablePrincipal.addCell(cellConteudoPrincipal);

            PdfPTable pTableAssinatura = new PdfPTable(new float[]{50f, 50f});
            pTableAssinatura.setWidthPercentage(100f);
            
            Font fontConteudoTxt_acess= FontFactory.getFont(ConfigDoc.Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 5f, Font.ITALIC, BaseColor.BLACK.darker().darker().darker());
            Font fontConteudo_acess = FontFactory.getFont(ConfigDoc.Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 5f, Font.NORMAL, BaseColor.BLUE.darker().darker());
            
            PdfPCell cellAcessorio = new PdfPCell();
            Paragraph pAcessorio = new Paragraph();
            pAcessorio.add(new Phrase("Acessórios: ".toUpperCase(), fontConteudo_acess));
            pAcessorio.add(new Phrase((map.get(ACCESSORIO) == null) ? " " : (map.get(ACCESSORIO) + "").replaceAll("\n", " "), fontConteudoTxt_acess));
            cellAcessorio.addElement(pAcessorio);
            cellAcessorio.setColspan(2);
            cellAcessorio.setBorder(0);
            cellAcessorio.setPaddingBottom(4f);
            cellAcessorio.setPaddingTop(-2f);
            pTableAssinatura.addCell(cellAcessorio);

            PdfPCell cellLinhaAssina = new PdfPCell();
            Paragraph pAssinatura = new Paragraph();
            pAssinatura.add(new Paragraph("_________________________________", fontConteudo));
            pAssinatura.add(new Paragraph("                Carimbo e Assinatura", fontConteudo));
            pAssinatura.setAlignment(Element.ALIGN_CENTER);
            cellLinhaAssina.addElement(pAssinatura);
            cellLinhaAssina.setPaddingBottom(2f);
            cellLinhaAssina.setBorder(0);
            cellLinhaAssina.setPaddingTop(25f);
            pTableAssinatura.addCell(cellLinhaAssina);

            PdfPCell cellCambio = new PdfPCell();
            Paragraph pCombio = new Paragraph();
            pCombio.add(new Phrase("Cambio (USD): ".toUpperCase(), fontConteudo));
            pCombio.add(new Phrase(converterMoeda(valorCompra("USD", dRegistro), "") + "\n", fontConteudoTxt));
            pCombio.add(new Phrase("Cambio (EUR): ".toUpperCase(), fontConteudo));
            pCombio.add(new Phrase(converterMoeda(valorCompra("EUR", dRegistro), "") + "\n", fontConteudoTxt));
            cellCambio.addElement(pCombio);
            cellCambio.setBorder(0);
            cellCambio.setPaddingTop(-2f);
            cellCambio.setPaddingBottom(2f);
            pTableAssinatura.addCell(cellCambio);
            
            PdfPCell cellpTableAssinaturaPrincipal = new PdfPCell(pTableAssinatura);
            cellpTableAssinaturaPrincipal.setBorder(PdfPCell.NO_BORDER);
            pTablePrincipal.addCell(new PdfPCell(cellpTableAssinaturaPrincipal));

//            pTableAssinatura.writeSelectedRows(-1, 2, 10, 70, writer.getDirectContent());
//            pTableImagem.writeSelectedRows(-1, 2, 52.5f, 402.5f, writer.getDirectContent());
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pTablePrincipal;
    }
}
