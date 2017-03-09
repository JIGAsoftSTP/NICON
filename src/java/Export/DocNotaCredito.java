/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.DataReseguro.DataResseguro;
import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
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
import conexao.Call;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import lib.Moeda;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AhmedJorge
 */
public class DocNotaCredito implements Serializable {

    private String nomeSeguro;
    private String interCod;
    private String idCliente;
    private String user;
    private String arquivo;
    private int idResseguro;
    private Integer idNoTaCretido;
    private TypeNotaCredito typeNotaCredito;
    
    final String ID ="ID",
            DATAINICIO = "DATA INICIO",
            DATAFIM = "DATA FIM",
            IDCLIENTE = "ID CLIENTE",
            IDMOEDA = "ID MOEDA",
            NOMEDAMOEDA = "NOME DA MOEDA",
            SIGLADAMOEDA = "SIGLA DA MOEDA",
            APOLICE = "APOLICE",
            NOTADEBITO = "NOTA DEBITO",
            CODIGO = "CODIGO",
            BENEFICIARIO = "BENEFICIARIO",
            ENDERECODOENVIO = "ENDERECO DO ENVIO",
            DESCRICAO = "DESCRICAO",
            PREMIOGROSSO = "PREMIO GROSSO",
            DECUCAO = "DECUCAO",
            TOTAL = "TOTAL",
            DTREG = "DTREG",
            DATE = "DATE",
            CTT_DTREG = "CTT_DTREG",
            COLABORADOR = "COLABORADOR";

    public DocNotaCredito() {
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private void docSeguros(
            String nomeSeguro,
            String interCod,
            String user,
            String arquivo,
            int idResseguro,
            TypeNotaCredito tnc
    ) {
        String reString;
        try {

            Font fontCabecalhoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.2f);
            Font fontLinha = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 0.000000358f);
            Font fontCabecalhoS = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.2f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontNull = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 4f);
            Font fontMenor = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 3f);

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{80, 20});
            pTableEmpresaPricipal.setWidthPercentage(95); 
            PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres2 = new PdfPTable(1);
            PdfPTable pTableEmpresaInforImpres3 = new PdfPTable(2);
            PdfPTable pTableEmpresaInforImpres4 = new PdfPTable(2);
            PdfPTable pTableEmpresaInforImpres5 = new PdfPTable(1);
            
            DataResseguro resS = new DataResseguro();
            HashMap<String, Object> map = new LinkedHashMap<>();
            if(tnc == TypeNotaCredito.RESEGURO) { resS = DataReseguro.getDadosReseguro(idResseguro); }
            else { map = loadNotaConta(); }

            PdfPTable pTableFatura = new PdfPTable(new float[]{80, 20});

            PdfPCell pCellNomeEmpresa = new PdfPCell(new Phrase(Empresa.NOME, fontCabecalhoN));
            pCellNomeEmpresa.setBorder(0);

            PdfPCell pCellNomeEndereco = new PdfPCell(new Phrase(Empresa.ENDERECO, fontCabecalhoN));
            pCellNomeEndereco.setBorder(0);

            PdfPCell pCellCaixaPostal = new PdfPCell(new Phrase(Empresa.CAIXAPOSTAL, fontCabecalhoN));
            pCellCaixaPostal.setBorder(0);

            PdfPCell pCellTeleFax = new PdfPCell(new Phrase(Empresa.TELEFAX + " " + ConfigDoc.Empresa.EMAIL, fontCabecalhoN));
            pCellTeleFax.setBorder(0);

            PdfPCell pCellSociedade = new PdfPCell(new Phrase(Empresa.SOCIEDADE, fontCabecalhoN));
            pCellSociedade.setBorder(0);

            PdfPCell pCellCapital = new PdfPCell(new Phrase(Empresa.CAPITALSOCIAL, fontCabecalhoN));
            pCellCapital.setBorder(0);

            PdfPCell pCellPolice = new PdfPCell(new Phrase(Empresa.APOLICE + ( (TypeNotaCredito.RESEGURO == tnc) ? resS.getAPOLICE() : map.get(APOLICE) ), fontCabecalhoN));
            pCellPolice.setBorder(0);

            PdfPCell pCellDebNF = new PdfPCell(new Phrase("Cre. Nº", fontCabecalhoS));
            pCellDebNF.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pCellDebNF.setBorder(0);

            PdfPCell pCellDebN = new PdfPCell(new Phrase(( (TypeNotaCredito.RESEGURO == tnc) ? resS.getIDSEGURO() : map.get(ID)+"" ), fontCabecalhoS));
            pCellDebN.setHorizontalAlignment(Element.ALIGN_CENTER);
            pCellDebN.setBorder(0);

            PdfPCell pCellInterCoF = new PdfPCell(new Phrase("Inter COD:", fontCabecalhoS));
            pCellInterCoF.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pCellInterCoF.setBorder(0);

            PdfPCell pCellInterCo = new PdfPCell(new Phrase(interCod, fontCabecalhoS));
            pCellInterCo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pCellInterCo.setBorder(0);

            Image imageEmpresa = Image.getInstance("logo.png");
            imageEmpresa.scaleToFit(120f, 100f);

            pTableEmpresaInforImpres1.addCell(pCellNomeEmpresa);
            pTableEmpresaInforImpres1.addCell(pCellNomeEndereco);
            pTableEmpresaInforImpres1.addCell(pCellCaixaPostal);
            pTableEmpresaInforImpres1.addCell(pCellTeleFax);
            pTableEmpresaInforImpres1.addCell(pCellSociedade);

            pTableEmpresaInforImpres2.addCell(pCellCapital);
            pTableEmpresaInforImpres2.addCell(pCellPolice);

            pTableEmpresaInforImpres3.addCell(pCellDebNF);
            pTableEmpresaInforImpres3.addCell(pCellDebN);
            pTableEmpresaInforImpres3.addCell(pCellInterCoF);
            pTableEmpresaInforImpres3.addCell(pCellInterCo);

            PdfPCell cellTabela1 = new PdfPCell(pTableEmpresaInforImpres2);
            cellTabela1.setBorder(0);

            pTableEmpresaInforImpres4.addCell(cellTabela1);

            PdfPCell cellTabela2 = new PdfPCell(pTableEmpresaInforImpres3);
            cellTabela2.setBorder(0);

            pTableEmpresaInforImpres4.addCell(cellTabela2);

            PdfPCell cellTabela3 = new PdfPCell(pTableEmpresaInforImpres1);
            cellTabela3.setBorder(0);

            pTableEmpresaInforImpres5.addCell(cellTabela3);

            PdfPCell cellTabela4 = new PdfPCell(pTableEmpresaInforImpres4);
            cellTabela4.setBorder(0);

            pTableEmpresaInforImpres5.addCell(cellTabela4);

            PdfPCell cellTabela5 = new PdfPCell(pTableEmpresaInforImpres5);
            cellTabela5.setBorder(0);

            pTableEmpresaPricipal.addCell(cellTabela5);

            PdfPCell cellTabela6 = new PdfPCell(imageEmpresa);
            cellTabela6.setBorder(0);
            cellTabela6.setHorizontalAlignment(Element.ALIGN_RIGHT);

            pTableEmpresaPricipal.addCell(cellTabela6);

//
            PdfPTable pTableLinha = new PdfPTable(1);
            pTableLinha.setWidthPercentage(95);
            PdfPCell linha = new PdfPCell(new Phrase(" ", fontLinha));
            linha.setBorderWidthTop(0.5f);
            linha.setBorderWidthBottom(0);
            linha.setBorderWidthLeft(0);
            linha.setBorderWidthRight(0);
            pTableLinha.addCell(linha);

            PdfPTable pTableCorpoEndTitile = new PdfPTable(new float[]{100});
            pTableCorpoEndTitile.setWidthPercentage(95);
            PdfPCell cellCorpoEndTitile = new PdfPCell();
            Paragraph paragraphCorpoEndTitile = new Paragraph();

            Paragraph titile = new Paragraph("NOTA DE CREDITO", fontCabecalhoN);
            titile.setAlignment(Paragraph.ALIGN_CENTER);

            PdfPTable pTableNumNota = new PdfPTable(new float[]{100f});

            Paragraph titileSub = new Paragraph(nomeSeguro, fontCabecalhoN);
            titileSub.setAlignment(Paragraph.ALIGN_CENTER);
            paragraphCorpoEndTitile.add(titile);
            
            if(tnc == TypeNotaCredito.ANULACAO)
            {
                PdfPCell titileNum = new PdfPCell(new Paragraph("Deb. Nº " + map.get(NOTADEBITO), fontCabecalhoN));
                titileNum.setBorder(PdfPCell.NO_BORDER);
                titileNum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                
                pTableNumNota.addCell(titileNum);
                paragraphCorpoEndTitile.add(pTableNumNota);
            }
            

            paragraphCorpoEndTitile.add(titileSub);

            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpo));
            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpo));
            
            ArrayList<DataReseguro.DataEmpresa> listaDataEmpresas = new ArrayList<>();
            
            ClienteI ci = null;
            if(tnc == TypeNotaCredito.RESEGURO)  {  listaDataEmpresas = DataReseguro.getDadosEmpresa(idResseguro); }
            else { ci = new ClienteI(map.get(IDCLIENTE)+""); }

            Phrase pCr = new Phrase("BENEFICIÁRIO: ", fontCabecalhoN);
            paragraphCorpoEndTitile.add(pCr);
            @SuppressWarnings("null")
            Phrase pCrTex = new Phrase( ((tnc == TypeNotaCredito.RESEGURO) ? resS.getCLIENTE() : map.get(BENEFICIARIO)+"\n"), fontCabecalhoS);
            paragraphCorpoEndTitile.add(pCrTex);

            Phrase pEndereco = new Phrase("Endereço: ".toUpperCase(), fontCabecalhoN);
            paragraphCorpoEndTitile.add(pEndereco);
            Phrase pEnderecoTex = new Phrase(((tnc == TypeNotaCredito.RESEGURO )  ? ConfigDoc.Empresa.ENDERECO : ci.getENDERECO_()) + "\n", fontCabecalhoS);
            paragraphCorpoEndTitile.add(pEnderecoTex);

            Phrase pPolice = new Phrase("Apolice: ".toUpperCase(), fontCabecalhoN);
            paragraphCorpoEndTitile.add(pPolice);
            Phrase pPoliceTex = new Phrase( ( (tnc == TypeNotaCredito.RESEGURO) ? resS.getAPOLICE() : map.get(APOLICE) )  + "\n", fontCabecalhoS);
            paragraphCorpoEndTitile.add(pPoliceTex);

//                Phrase pInterCod = new Phrase("intermediry Code".toUpperCase(), fontCabecalhoN);
//                paragraphCorpoEndTitile.add(pInterCod);
//                Phrase pInterCodTex = new Phrase(/*SessionUtil.getUserlogado().getResidencia()*/"In ----\n", fontCabecalhoS);
//                paragraphCorpoEndTitile.add(pInterCodTex);

            Phrase pDate = new Phrase("DATA: ".toUpperCase(), fontCabecalhoN);
            paragraphCorpoEndTitile.add(pDate);
            Phrase pDateTex = new Phrase( ( (TypeNotaCredito.ANULACAO != tnc) ? resS.getINICIO() + " " + resS.getFIM() : map.get(DATAINICIO)+" "+map.get(DATAFIM) ) + "\n", fontCabecalhoS);
            paragraphCorpoEndTitile.add(pDateTex);
            
            Phrase pSegurado = new Phrase("Segurado: ".toUpperCase(), fontCabecalhoN);
            paragraphCorpoEndTitile.add(pSegurado);
            Phrase pSeguradoTex = new Phrase( ((tnc == TypeNotaCredito.RESEGURO )  ? ConfigDoc.Empresa.NOME+"("+resS.getCLIENTE()+")" : ci.getNOME_()) , fontCabecalhoS);
            paragraphCorpoEndTitile.add(pSeguradoTex);

            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpo));
            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpo));

            //Tabela Principal
            PdfPTable pTableNotaCredito = new PdfPTable(new float[]{21.5f, 21.5f, 22f, 27f, 7f});
//            PdfPTable pTableNotaCreditos = new PdfPTable(new float[]{70f, 30f});
            pTableNotaCredito.setWidthPercentage(100f);
//            pTableNotaCreditos.setWidthPercentage(100f);

            PdfPCell cellParticular = new PdfPCell(new Phrase("Particular".toUpperCase(), fontCorpoN));
            cellParticular.setBorderWidth(1);
            pTableNotaCredito.addCell(cellParticular);

            PdfPCell cellPremioGrosso = new PdfPCell(new Phrase("Prémio Grosso".toUpperCase(), fontCorpoN));
            cellPremioGrosso.setBorderWidth(1);
            pTableNotaCredito.addCell(cellPremioGrosso);

            PdfPCell cellComissaoDedutivel = new PdfPCell(new Phrase("Comissão Dedutível".toUpperCase(), fontCorpoN));
            cellComissaoDedutivel.setBorderWidth(1);
            pTableNotaCredito.addCell(cellComissaoDedutivel);

            PdfPCell cellValorLiquidoD = new PdfPCell(new Phrase("Valor Liquído Devido", fontCorpoN));
            cellValorLiquidoD.setBorderWidth(1);
            cellValorLiquidoD.setBorderWidthRight(0);
            pTableNotaCredito.addCell(cellValorLiquidoD);
            
            PdfPCell cellNull= new PdfPCell(new Phrase(" ", fontCorpoN));
            PdfPCell cellNull1= new PdfPCell(new Phrase("(NET)", fontCorpoN));
            cellNull.setBorder(0);
            
            cellNull1.setBorderWidthTop(1);
            cellNull1.setBorderWidthRight(1);
            cellNull1.setBorderWidthLeft(0);
            cellNull1.setBorderWidthBottom(1);
            cellNull1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            pTableNotaCredito.addCell(cellNull1);
            

            cellParticular = new PdfPCell(new Phrase( ( (TypeNotaCredito.RESEGURO == tnc) ? resS.getDESCRICAO().toUpperCase() : map.get(DESCRICAO)+"" ), fontCorpo));
            cellParticular.setBorderWidth(1);
            pTableNotaCredito.addCell(cellParticular);

            cellPremioGrosso = new PdfPCell(new Phrase( (( TypeNotaCredito.RESEGURO == tnc ) ? Moeda.format(Double.valueOf(resS.getPREMIOGROSSO())) : Moeda.format(Double.valueOf(map.get(PREMIOGROSSO)+"")) ), fontCorpo));
            cellPremioGrosso.setBorderWidth(1);
            cellPremioGrosso.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellPremioGrosso.setPaddingTop(30f);
            pTableNotaCredito.addCell(cellPremioGrosso);

            cellComissaoDedutivel = new PdfPCell(new Phrase( ( (TypeNotaCredito.RESEGURO == tnc)  ? resS.getDEDUCAO()+"%" : map.get(DECUCAO)+"%"), fontCorpo));
            cellComissaoDedutivel.setBorderWidth(1);
            cellComissaoDedutivel.setPaddingTop(30f);
            cellComissaoDedutivel.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableNotaCredito.addCell(cellComissaoDedutivel);

            cellValorLiquidoD = new PdfPCell(new Phrase(Moeda.format(Double.valueOf( ( (TypeNotaCredito.RESEGURO == tnc) ? resS.getTOTAL() : map.get(TOTAL)+""))), fontCorpo));
            cellValorLiquidoD.setBorderWidth(1);
            cellValorLiquidoD.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellValorLiquidoD.setPaddingTop(30f);
            cellValorLiquidoD.setPaddingBottom(75f);
            pTableNotaCredito.addCell(cellValorLiquidoD);
            
            pTableNotaCredito.addCell(cellNull);

            PdfPCell cellTotalDebito = new PdfPCell(new Phrase("TOTAL ", fontCorpoN));
            cellTotalDebito.setBorder(PdfPCell.NO_BORDER);
            cellTotalDebito.setColspan(3);
            pTableNotaCredito.addCell(cellTotalDebito);

            PdfPCell cellTotalDebitoV = new PdfPCell(new Phrase(Moeda.format(Double.valueOf(( (TypeNotaCredito.RESEGURO == tnc) ? resS.getTOTAL() : map.get(TOTAL)+""))), fontCorpo));
            cellTotalDebitoV.setBorderWidth(1);
            cellTotalDebitoV.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pTableNotaCredito.addCell(cellTotalDebitoV);
            
            pTableNotaCredito.addCell(cellNull);
            
            double cambio;
            double totalSTD = 0;
            if(!((TypeNotaCredito.RESEGURO == tnc)  ? resS.getMOEDA() : map.get(SIGLADAMOEDA) ).equals("STD"))
            {
                PdfPCell cellCambio = new PdfPCell(new Phrase("CAMBIO "+ ( (TypeNotaCredito.RESEGURO == tnc) ? resS.getMOEDA() : map.get(SIGLADAMOEDA)), fontCorpoN));
                cellCambio.setBorder(PdfPCell.NO_BORDER);
                cellCambio.setColspan(3);
                pTableNotaCredito.addCell(cellCambio);

                cambio =  valorCompra( ((TypeNotaCredito.RESEGURO == tnc) ? resS.getMOEDA() : map.get(SIGLADAMOEDA)+""), ( (TypeNotaCredito.RESEGURO == tnc) ? new Date() : map.get(CTT_DTREG)) );

                PdfPCell cellCambioV = new PdfPCell(new Phrase(Moeda.format(cambio), fontCorpo));
                cellCambioV.setBorderWidth(1);
                cellCambioV.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pTableNotaCredito.addCell(cellCambioV);

                pTableNotaCredito.addCell(cellNull);
            
                PdfPCell cellValorDobras = new PdfPCell(new Phrase("VALOR EM DOBRAS", fontCorpoN));
                cellValorDobras.setBorder(PdfPCell.NO_BORDER);
                cellValorDobras.setColspan(3);
                pTableNotaCredito.addCell(cellValorDobras);

                totalSTD = cambio * Double.valueOf( (TypeNotaCredito.RESEGURO == tnc) ? resS.getTOTAL() : map.get(TOTAL)+"" );
                PdfPCell cellValorDobrasV = new PdfPCell(new Phrase(Moeda.format(totalSTD), fontCorpo));
                cellValorDobrasV.setBorderWidth(1);
                cellValorDobrasV.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                pTableNotaCredito.addCell(cellValorDobrasV);

                pTableNotaCredito.addCell(cellNull); 
            }

            paragraphCorpoEndTitile.add(pTableNotaCredito);
            JTextPane jtp = new JTextPane();
            Moeda.EscreverEstenso(totalSTD, jtp, "Dobras");
            paragraphCorpoEndTitile.add(new Phrase("POR EXTENSO: ", fontCorpoN));
            paragraphCorpoEndTitile.add(new Phrase(jtp.getText().toUpperCase()+"\n", fontCorpo));

            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpoN));
            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpoN));

            paragraphCorpoEndTitile.add(new Phrase("NOTA: ZERO PRÉMIO, ZERO COBERTURA", fontCorpoN));

            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpoN));
            paragraphCorpoEndTitile.add(new Paragraph(" ", fontCorpoN));

            PdfPTable pTableRodape = new PdfPTable(new float[]{33.333333333f, 33.333333333f, 33.333333333f});
            pTableRodape.setWidthPercentage(100.0f);

            PdfPCell cellRodapeData = new PdfPCell(new Phrase(".......................................................", fontCorpo));
            cellRodapeData.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellRodapeData.setBorder(PdfPCell.NO_BORDER);
            pTableRodape.addCell(cellRodapeData);

            PdfPCell cellRodapeVerificado = new PdfPCell(new Phrase(".......................................................", fontCorpoN));
            cellRodapeVerificado.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellRodapeVerificado.setBorder(PdfPCell.NO_BORDER);
            pTableRodape.addCell(cellRodapeVerificado);

            PdfPCell cellAssinatura = new PdfPCell(new Phrase(".......................................................", fontCorpo));
            cellAssinatura.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellAssinatura.setBorder(PdfPCell.NO_BORDER);
            pTableRodape.addCell(cellAssinatura);

            cellRodapeData = new PdfPCell(new Phrase(" ", fontCorpo));
            cellRodapeData.setBorder(PdfPCell.NO_BORDER);
            pTableRodape.addCell(cellRodapeData);

            cellRodapeVerificado = new PdfPCell(new Phrase("VERIFICADO POR", fontCorpoN));
            cellRodapeVerificado.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellRodapeVerificado.setBorder(PdfPCell.NO_BORDER);
            pTableRodape.addCell(cellRodapeVerificado);

            cellAssinatura = new PdfPCell(new Phrase("ASSINATURA", fontCorpo));
            cellAssinatura.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellAssinatura.setBorder(PdfPCell.NO_BORDER);
            pTableRodape.addCell(cellAssinatura);

            paragraphCorpoEndTitile.add(pTableRodape);

            cellCorpoEndTitile.addElement(paragraphCorpoEndTitile);

            cellCorpoEndTitile.setBorder(PdfPCell.NO_BORDER);

            pTableCorpoEndTitile.addCell(cellCorpoEndTitile);

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 70f, 5f);

            String f1 = (arquivo + "/" + interCod + "/Seguro " + nomeSeguro + "/");
            File f = new File(f1);
            String Ddata = sdf1.format(new Date());
            f.mkdirs();
            f = new File(f.getAbsoluteFile() + "/" + "Nota de Credito " + Ddata + ".pdf");

            reString = "../Documentos/" + interCod + "/Seguro " + nomeSeguro + "/" + "Nota de Credito " + Ddata + ".pdf";
            OutputStream outputStraem = new FileOutputStream(f);
            PdfWriter writer = PdfWriter.getInstance(documento, outputStraem);

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableLinha);
            documento.add(pTableLinha);
            documento.add(pTableCorpoEndTitile);
            documento.close();

            RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DocNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        new DocNotaCredito()
                .IdNoTaCretido(2)
                .Arquivo("./")
                .IdResseguro(454)
                .InterCod("Ah")
                .NomeSeguro("Viagem")
                .User("Ah")
                .TypeNotaCredito(TypeNotaCredito.ANULACAO)
                .createDoc();

    }


    public static String toString(Object o) {
        return (o == null) ? "" : o.toString();
    }
     static double taxa = 0;
    public static double valorCompra(String nomeMoeda, Object date) {
       taxa = 0;
        try {
            String idMoe = "0";

            try (ResultSet rss = Call.selectFrom("VER_MOEDA where SIGLA = ? ", "*", nomeMoeda)) { while (rss.next()) {    idMoe = rss.getString("ID"); } }

            ResultSet rs; {  rs = Call.callTableFunction("PACK_CONTA.getTaxaDay", "*", date, idMoe); }

            Consumer<HashMap<String, Object>> act = (map) -> {  
                taxa = Double.valueOf(toString(map.get("TX_VENDA"))); 
            };

            Call.forEchaResultSet(act, rs);
            return taxa;

        } catch (SQLException ex) {
            Logger.getLogger(DocNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taxa;
    }

    public DocNotaCredito NomeSeguro(String nomeSeguro) {
        this.nomeSeguro = nomeSeguro;
        return this;
    }

    public DocNotaCredito InterCod(String interCod) {
        this.interCod = interCod;
        return this;
    }

    public DocNotaCredito IdCliente(String idCliente) {
        this.idCliente = idCliente;
        return this;
    }

    public DocNotaCredito User(String user) {
        this.user = user;
        return this;
    }

    public DocNotaCredito Arquivo(String arquivo) {
        this.arquivo = arquivo;
        return this;
    }

    public DocNotaCredito IdResseguro(int idResseguro) {
        this.idResseguro = idResseguro;
        return this;
    }

    public DocNotaCredito TypeNotaCredito(TypeNotaCredito tnc) {
        this.typeNotaCredito = tnc;
        return this;
    }

    public void createDoc()
    {
        docSeguros(nomeSeguro,interCod,user,arquivo,idResseguro,typeNotaCredito);
    }

    public DocNotaCredito IdNoTaCretido(Integer idNoTaCretido) {
        this.idNoTaCretido = idNoTaCretido;
        return this;
    }
    
    public enum TypeNotaCredito {  ANULACAO, RESEGURO }
    
    private HashMap<String,Object> loadNotaConta()
    {
        HashMap<String,Object> mapMap = new LinkedHashMap<>();
        ResultSet rs = Call.selectFrom("VER_NOTA_CREDITO where \"ID\" = ?", "*", idNoTaCretido);
        Consumer<HashMap<String, Object>> act = (map) -> {
           mapMap.putAll(map);
        };
        try { Call.forEchaResultSet(act, rs); } catch (Exception e) { }
        
        return mapMap;
    }
}
