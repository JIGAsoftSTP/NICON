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
import lib.Moeda;
import modelo.Contrato;

/**
 *
 * @author AhmedJorge
 */
public class SeguroViatura implements Serializable {

    private String reString;

    public String criarDoc(
            String numApolice,
            String numCliente,
            Contrato c, VeiculoBean vb, String user, String arquivo
    ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");

            Font fontCabecalhoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10.5f);
            Font fontCorpo = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoTabT = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 6.5f);
            Font fontCorpoTab = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 6.5f);
            Font fontCorpoP = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 8f);
            Font fontCorpoN = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9f);
            Font fontCorpoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 9.5f);
            Font fontCorpoNGT = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 10f);
            Font fontCabecalhoNG = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 16f, Font.UNDERLINE);
            Font fontUK = FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f, Font.ITALIC);
            Font fontUKb = FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED, 6f, Font.ITALIC);

            PdfPTable pTableEmpresaPricipal = new PdfPTable(new float[]{25f, 75f});
            pTableEmpresaPricipal.setWidthPercentage(88f);
            PdfPTable pTableEmpresaInforImpres1 = new PdfPTable(1);
//            PdfPTable pTableEmpresaInforImpres2 = new PdfPTable(1);
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

            PdfPTable pTableSeguro = new PdfPTable(1);
            pTableSeguro.setWidthPercentage(88f);
            PdfPTable pTableCliente = new PdfPTable(1);
            pTableCliente.setWidthPercentage(88f);

            PdfPTable pTableTitulo = new PdfPTable(1);
            pTableTitulo.setWidthPercentage(88f);
            
            Paragraph paTitulo = new Paragraph();
            Paragraph pTitulo = new Paragraph("resumo de apólice para Automóvel".toUpperCase(), fontCorpoNGT);
            Paragraph pTituloUk = new Paragraph("\nthe motor policy sumay".toUpperCase(), fontUKb);
            paTitulo.add(pTitulo);
            paTitulo.add(pTituloUk);
            
            PdfPCell cellTitulo = new PdfPCell(paTitulo);
            cellTitulo.setBorder(0);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableTitulo.addCell(cellTitulo);

            ClienteI ci = new ClienteI(numCliente);
           Paragraph para = new  Paragraph();
           Paragraph phrase =  new Paragraph("1 - Informações Do Cliente".toUpperCase(), fontCorpoNG);
           Paragraph paraUK = new Paragraph("\n1 - Customer information".toUpperCase(), fontUKb);
            
            para.add(phrase);
            para.add(paraUK);
            
            PdfPCell cellTituloTsbleSegurado = new PdfPCell(para);
            cellTituloTsbleSegurado.setBorder(0);
            
            Paragraph pCl = new Paragraph();
            pCl.add(new Phrase(ci.getNOMEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNOME_(),fontCorpoN));
            pCl.add(new Phrase("\nNAME:",fontUK));
            PdfPCell cellNome = new PdfPCell(new Phrase(pCl));
            cellNome.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getENDERECOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getENDERECO_(),fontCorpoN));
            pCl.add(new Phrase("\nADDRESS:",fontUK));
            PdfPCell cellEndereco = new PdfPCell(pCl);
            cellEndereco.setBorder(0);
            
            /**
            pCl = new Paragraph();
            pCl.add(new Phrase("   " +ci.getNUNCLIENTEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNUNCLIENTE_(),fontCorpoN));
            PdfPCell cellNCliente = new PdfPCell( pCl );
            cellNCliente.setBorder(0);
            * */
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getPROFISSAOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getPROFISSAO_(),fontCorpoN));
            pCl.add(new Phrase ("\nPROFISSION:",fontUK));
            PdfPCell cellProfissao = new PdfPCell(pCl);
            cellProfissao.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getLOCALTRABALHOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getLOCALTRABALHO_(),fontCorpoN));
            pCl.add(new Phrase("\nPLACE OF WORK",fontUK));
            PdfPCell cellLocalTrabalho = new PdfPCell(pCl);
            cellLocalTrabalho.setBorder(0);
            
            cellNome.setPaddingTop(7f);
            cellNome.setPaddingBottom(7f);
            cellNome.setBorder(0);

            cellEndereco.setPaddingTop(7f);
            cellEndereco.setPaddingBottom(7f);
            cellEndereco.setBorder(0);

//            PdfPCell cellNCliente= new PdfPCell( new Phrase("   "+ci.getNUNCLIENTE(),fontCorpo));cellNome.setPaddingTop(5f);
//            cellNCliente.setPaddingTop(7f);
//            cellNCliente.setPaddingBottom(7f);
//            cellNCliente.setBorder(0);
            
            cellProfissao.setPaddingTop(7f);
            cellProfissao.setPaddingBottom(7f);
            cellProfissao.setBorder(0);

            cellLocalTrabalho.setPaddingTop(7f);
            cellLocalTrabalho.setPaddingBottom(7f);
            cellLocalTrabalho.setBorder(0);

            pTableCliente.addCell(cellTituloTsbleSegurado);
            pTableCliente.addCell(cellNome);
            pTableCliente.addCell(cellEndereco);
//            pTableCliente.addCell(cellNCliente);
            pTableCliente.addCell(cellProfissao);
            pTableCliente.addCell(cellLocalTrabalho);
            
            para = new Paragraph();
            Paragraph p= new Paragraph("3 - Premio E Cobertura".toUpperCase(), fontCorpoNG);
            Paragraph p1= new Paragraph("\n3 - PREMIUM AND COVER".toUpperCase(), fontUKb);
            para.add(p);
            para.add(p1);
                    
            PdfPCell cellTiltuloSegro = new PdfPCell(para);
            cellTiltuloSegro.setPaddingTop(7f);
            cellTiltuloSegro.setPaddingBottom(7f);
            cellTiltuloSegro.setBorder(0);

//            PdfPCell cellApolice= new PdfPCell( new Phrase("   Nº Apólice: ".toUpperCase()+numApolice,fontCorpo));
//            cellApolice.setPaddingTop(7f);
//            cellApolice.setPaddingBottom(7f);
//            cellApolice.setBorder(0);
            Paragraph pTotalSegurado = new Paragraph();
            pTotalSegurado.add(new Phrase("Prémio: ".toUpperCase().toUpperCase(), fontCorpo));
            /**
             * *
             * ildo - premio liquido ->
             */
            pTotalSegurado.add(new Phrase(((c.getPremioLiquidoMoeda() == null /*&& vb.getVeiculo().getTipoCobertura().equals("41") */ ) ? " " : c.getPremioLiquidoMoeda() + " " + c.getSigla()), fontCorpoN));
            pTotalSegurado.add(new Phrase("\nPREMIUM", fontUK));
            PdfPCell cellTotalSegurado = new PdfPCell(pTotalSegurado);
            cellTotalSegurado.setPaddingTop(7f);
            cellTotalSegurado.setPaddingBottom(7f);
            cellTotalSegurado.setBorder(0);

            Paragraph pExcesso = new Paragraph();
            pExcesso.add(new Phrase("Excesso: ".toUpperCase(), fontCorpo));
            pExcesso.add(new Phrase((c.getFranquia() == null) ? "Não aplicável".toUpperCase() : c.getFranquia(), fontCorpoN));
            pExcesso.add(new Phrase("\nEXCESS: ", fontUK));
            PdfPCell cellExcesso = new PdfPCell(pExcesso);
            cellExcesso.setPaddingTop(7f);
            cellExcesso.setPaddingBottom(7f);
            cellExcesso.setBorder(0);

//            PdfPCell cellValor1Preminio= new PdfPCell( new Phrase("   Valor Primeiro Premio: ".toUpperCase()+((c.getPrimeiroPremio()==null)?" ":c.getPrimeiroPremio()),fontCorpo));
//            cellValor1Preminio.setPaddingTop(7f);
//            cellValor1Preminio.setPaddingBottom(7f);
//            cellValor1Preminio.setBorder(0);
//            
//            PdfPCell cellTotalPremioAnual= new PdfPCell( new Phrase("   Valor Premio Anual: ".toUpperCase()+((c.getPremioAnual()==null)?" ":c.getPremioAnual()),fontCorpo));
//            cellTotalPremioAnual.setPaddingTop(7f);
//            cellTotalPremioAnual.setPaddingBottom(7f);
//            cellTotalPremioAnual.setBorder(0);
//            PdfPCell cellPeriodo= new PdfPCell( new Phrase("   Periodo Do Seguro: ".toUpperCase()+((c.getDataInicio()!= null) ? sdf.format(c.getDataInicio()): " " )+" à "+((c.getDataFim()!= null) ? sdf.format(c.getDataFim()): " " ),fontCorpo));
//            cellPeriodo.setPaddingTop(7f);
//            cellPeriodo.setPaddingBottom(7f);
//            cellPeriodo.setBorder(0)
            Paragraph pDataRenovacao = new Paragraph();
            pDataRenovacao.add(new Phrase("Data Renovação de Seguro: ".toUpperCase(), fontCorpo));
            pDataRenovacao.add(new Phrase(((c.getDataRenovacao() != null) ? sdf.format(c.getDataRenovacao()) : " "), fontCorpoN));
            pDataRenovacao.add(new Phrase("\nRENEWAL DATE OF COVER", fontUK));
            PdfPCell cellDataRenovacao = new PdfPCell(pDataRenovacao);
            cellDataRenovacao.setPaddingTop(7f);
            cellDataRenovacao.setPaddingBottom(7f);
            cellDataRenovacao.setBorder(0);

            Paragraph pTipoCobert = new Paragraph();
            pTipoCobert.add(new Phrase("Tipo De Cobertura: ".toUpperCase(), fontCorpo));
            pTipoCobert.add(new Phrase(((vb.getVeiculo().getTipoCobertura() != null) ? (vb.getVeiculo().getTipoCobertura().equals("41") ? "Contra Terceiros".toUpperCase() : (vb.getVeiculo().getTipoCobertura().equals("42") ? "CONTRA Todos os riscos".toUpperCase() : (vb.getVeiculo().getTipoCobertura().equals("43") ? "Compreensivo limitado".toUpperCase() : vb.getVeiculo().getTipoCobertura()))) : " "), fontCorpoN));
            pTipoCobert.add(new Phrase("\nTYPE OF CAVER", fontUK));
            PdfPCell cellTipoCobert = new PdfPCell(pTipoCobert);
            cellTipoCobert.setPaddingTop(7f);
            cellTipoCobert.setPaddingBottom(7f);
            cellTipoCobert.setBorder(0);

            Paragraph pLimiteArea = new Paragraph();
            pLimiteArea.add(new Phrase("Limite de área geográfica: ".toUpperCase(), fontCorpo));
            pLimiteArea.add(new Phrase("são Tomé e principe".toUpperCase(), fontCorpoN));
            pLimiteArea.add(new Phrase("\nLIMIT OF GEOGRAPHICAL AREA".toUpperCase(), fontUK));
            PdfPCell cellLimiteArea = new PdfPCell(pLimiteArea);
            cellLimiteArea.setPaddingTop(7f);
            cellLimiteArea.setPaddingBottom(7f);
            cellLimiteArea.setBorder(0);

            Paragraph pLeis = new Paragraph();
            pLeis.add(new Phrase("legislação: ".toUpperCase(), fontCorpo));
            pLeis.add(new Phrase("artigos 1 à 36, lei nº 30/2000 da república democrática de stp".toUpperCase(), fontCorpoN));
            pLeis.add(new Phrase("\nLEGISLATION: ", fontUK));
            pLeis.add(new Phrase("ARTICLES 1 TO 36, LAW NO 30/2000 OF THE DEMOCRATIC REPUBLIC OF STP", fontUKb));
            PdfPCell cellLeis = new PdfPCell(pLeis);
            cellLeis.setPaddingTop(7f);
            cellLeis.setPaddingBottom(7f);
            cellLeis.setBorder(0);

            pTableSeguro.addCell(cellTiltuloSegro);
//            pTableSeguro.addCell(cellApolice);
            pTableSeguro.addCell(cellTotalSegurado);
//            pTableSeguro.addCell(cellValor1Preminio);
//            pTableSeguro.addCell(cellTotalPremioAnual);
            pTableSeguro.addCell(cellExcesso);
            pTableSeguro.addCell(cellDataRenovacao);
            pTableSeguro.addCell(cellTipoCobert);
            pTableSeguro.addCell(cellLimiteArea);
            pTableSeguro.addCell(cellLeis);

            PdfPTable pTableDadosTitulo = new PdfPTable(1);
            pTableDadosTitulo.setWidthPercentage(88f);
            
            para = new Paragraph();
            Phrase PDadosTitulo = new Phrase("2 - Informações do(s) Veículo(s)".toUpperCase(), fontCorpoN);
            Phrase PDadosTitulos = new Phrase("\n2 - VEHICLE (S) INFORMATIONS".toUpperCase(), fontUKb);
            para.add(PDadosTitulo);
            para.add(PDadosTitulos);
            PdfPCell cellDadosTitulo = new PdfPCell(para);
            
            cellDadosTitulo.setBorder(0);
            pTableDadosTitulo.addCell(cellDadosTitulo);
            pTableDadosTitulo.addCell(cellNull);

            PdfPTable pTableDados = new PdfPTable(new float[]{13.5f,13.5f,14,17,16,12,14});
            pTableDados.setWidthPercentage(88f);
            
            para = new Paragraph();
            Phrase PMarca = new Phrase("Marca".toUpperCase(), fontCorpoTabT);
            Phrase PMarcaUK = new Phrase("\nMARK".toUpperCase(), fontUK);
            para.add(PMarca);
            para.add(PMarcaUK);
            
            PdfPCell cellMarca = new PdfPCell(para);
            cellMarca.setPaddingTop(5f);
            cellMarca.setPaddingBottom(5f);
            cellMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            para = new Paragraph();
            Paragraph PModelo = new Paragraph("Modelo".toUpperCase(), fontCorpoTabT);
            Paragraph PModeloUK = new Paragraph("\nMODEL".toUpperCase(), fontUK);
            para.add(PModelo);
            para.add(PModeloUK);
            
            PdfPCell cellModelo = new PdfPCell(para);
            cellModelo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellModelo.setPaddingTop(5f);
            cellModelo.setPaddingBottom(5f);
            
            para = new Paragraph();
            Phrase PMatricula = new Phrase("NO DE MATRÍCULA".toUpperCase(), fontCorpoTabT);
            Phrase PMatriculaUK = new Phrase("\nREG. NUMBER".toUpperCase(), fontUK);
            para.add(PMatricula);
            para.add(PMatriculaUK);
            
            PdfPCell cellMatricula = new PdfPCell(para);
            cellMatricula.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellMatricula.setPaddingTop(5f);
            cellMatricula.setPaddingBottom(5f);
            
            para = new Paragraph();
            Phrase PChasii = new Phrase("No. de chissi/Motor".toUpperCase(), fontCorpoTabT);
            Phrase PChasiiUK = new Phrase("\nchissiS/ENGINE NO.".toUpperCase(), fontUK);
            para.add(PChasii);
            para.add(PChasiiUK);
            
            PdfPCell cellChasii = new PdfPCell(para);
            cellChasii.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellChasii.setPaddingTop(5f);
            cellChasii.setPaddingBottom(5f);
            
            para = new Paragraph();
            Phrase PAnoFabricoCompra = new Phrase("ano de Fabrico/Compra".toUpperCase(), fontCorpoTabT);
            Phrase PAnoFabricoCompraUK = new Phrase("\nYear of Manufacture/PURCHASE".toUpperCase(), fontUK);
            para.add(PAnoFabricoCompra);
            para.add(PAnoFabricoCompraUK);
            
            PdfPCell cellAnoFabricoCompra = new PdfPCell(para);
            cellAnoFabricoCompra.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellAnoFabricoCompra.setPaddingTop(5f);
            cellAnoFabricoCompra.setPaddingBottom(5f);
            
            para = new Paragraph();
            Phrase PTotalLuagares = new Phrase("lotação".toUpperCase(), fontCorpoTabT);
            Phrase PTotalLuagaresUK = new Phrase("\nSTOCKING",fontUK);
            para.add(PTotalLuagares);
            para.add(PTotalLuagaresUK);
            
            PdfPCell cellTotalLuagares = new PdfPCell( para);
            cellTotalLuagares.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTotalLuagares.setPaddingTop(5f);
            cellTotalLuagares.setPaddingBottom(5f);
            
            para = new Paragraph();
            Phrase PDadosValorSeguro = new Phrase("Estimativa / Valor Segurado".toUpperCase(), fontCorpoTabT);
            Phrase PDadosValorSeguroUK = new Phrase("\nEstimation / Amount Insured".toUpperCase(), fontUK);
            para.add(PDadosValorSeguro);
            para.add(PDadosValorSeguroUK);
            
            PdfPCell cellDadosValorSeguro = new PdfPCell(para);
            cellDadosValorSeguro.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDadosValorSeguro.setPaddingTop(5f);
            cellDadosValorSeguro.setPaddingBottom(5f);

            pTableDados.addCell(cellMarca);
            pTableDados.addCell(cellModelo);
            pTableDados.addCell(cellMatricula);
            pTableDados.addCell(cellChasii);
            pTableDados.addCell(cellAnoFabricoCompra);
            pTableDados.addCell(cellTotalLuagares);
            pTableDados.addCell(cellDadosValorSeguro);

            int total = vb.getInfo().size();
            for (int i = 0; i < total; i++) {
                cellMarca = new PdfPCell(new Phrase((vb.getInfo().get(i).getMarca() == null) ? " " : vb.getInfo().get(i).getMarca(), fontCorpoTab));
                cellMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellModelo = new PdfPCell(new Phrase((vb.getInfo().get(i).getModelo() == null) ? " " : vb.getInfo().get(i).getModelo(), fontCorpoTab));
                cellModelo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellMatricula = new PdfPCell(new Phrase(((vb.getInfo().get(i).getNumeroMatricula() == null) ? ".........." : vb.getInfo().get(i).getNumeroMatricula()), fontCorpoTab));
                cellMatricula.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDadosValorSeguro = new PdfPCell(new Phrase(((vb.getInfo().get(i).getValorAtual() == null || vb.getInfo().get(i).getValorAtual().isEmpty() || vb.getVeiculo().getTipoCobertura().equals("41")) ? " NA " : Moeda.format(Double.valueOf(vb.getInfo().get(i).getValorAtual()))), fontCorpoTab));
                cellDadosValorSeguro.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellAnoFabricoCompra = new PdfPCell(new Phrase(((vb.getInfo().get(i).getAnoFabrico() == null || (vb.getInfo().get(i).getAnoFabrico().isEmpty())) ? "......." : vb.getInfo().get(i).getAnoFabrico()) + "/" + ((vb.getInfo().get(i).getAnoCompra() == null || (vb.getInfo().get(i).getAnoCompra().isEmpty())) ? "......." : vb.getInfo().get(i).getAnoCompra()), fontCorpoTab));
                cellAnoFabricoCompra.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTotalLuagares = new PdfPCell(new Phrase(vb.getInfo().get(i).getCapacidade() + " LUGARES", fontCorpoTab));
                cellTotalLuagares.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellChasii = new PdfPCell(new Phrase(vb.getInfo().get(i).getChassi() + " ", fontCorpoTab));
                cellChasii.setHorizontalAlignment(Element.ALIGN_CENTER);

                float pad = getPadding(total);

                cellMarca.setPaddingTop(pad);
                cellMarca.setPaddingBottom(pad);
                pTableDados.addCell(cellMarca);

                cellModelo.setPaddingTop(pad);
                cellModelo.setPaddingBottom(pad);
                pTableDados.addCell(cellModelo);

                cellMatricula.setPaddingTop(pad);
                cellMatricula.setPaddingBottom(pad);
                pTableDados.addCell(cellMatricula);

                cellChasii.setPaddingTop(pad);
                cellChasii.setPaddingBottom(pad);
                pTableDados.addCell(cellChasii);

                cellAnoFabricoCompra.setPaddingTop(pad);
                cellAnoFabricoCompra.setPaddingBottom(pad);
                pTableDados.addCell(cellAnoFabricoCompra);

                cellTotalLuagares.setPaddingTop(pad);
                cellTotalLuagares.setPaddingBottom(pad);
                pTableDados.addCell(cellTotalLuagares);

                cellDadosValorSeguro.setPaddingTop(pad);
                cellDadosValorSeguro.setPaddingBottom(pad);
                pTableDados.addCell(cellDadosValorSeguro);
            }

//            PdfPTable pTableAssinaturaTitulo= new PdfPTable(1);
//            pTableAssinaturaTitulo.setWidthPercentage(88f);
//            PdfPTable pTableAssinatura= new PdfPTable( new float[]{50f,50f});
//            pTableAssinatura.setWidthPercentage(88f);
//            PdfPCell cellAssinatora= new PdfPCell(new Phrase("Assinaturas".toUpperCase(),fontCorpoN));
//            cellAssinatora.setBorder(0);
//            cellAssinatora.setHorizontalAlignment(Element.ALIGN_CENTER);
//            PdfPCell celllinha1= new PdfPCell(new Phrase("____________________________________________".toUpperCase(),fontCorpo));
//            celllinha1.setBorder(0);
//            celllinha1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            PdfPCell celllinha2= new PdfPCell(new Phrase("____________________________________________".toUpperCase(),fontCorpo));
//            celllinha2.setBorder(0);
//            celllinha2.setHorizontalAlignment(Element.ALIGN_CENTER);
//            PdfPCell celllinha11= new PdfPCell(new Phrase("para nicon Seguro sa stp".toUpperCase(),fontCorpoP));
//            celllinha11.setBorder(0);
//            celllinha11.setHorizontalAlignment(Element.ALIGN_CENTER);
//            PdfPCell celllinha21= new PdfPCell(new Phrase("o segurado ".toUpperCase(),fontCorpoP));
//            celllinha21.setBorder(0);
//            celllinha21.setHorizontalAlignment(Element.ALIGN_CENTER);
//            pTableAssinaturaTitulo.addCell(cellAssinatora);
//            pTableAssinatura.addCell(celllinha1);
//            pTableAssinatura.addCell(celllinha2);
//            pTableAssinatura.addCell(celllinha11);
//            pTableAssinatura.addCell(celllinha21);
            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 35f, 5f);

//            File ff= new File("Documentos\\"+user+"\\Seguro Automovel\\");
//            ff.mkdirs();
//            ff =new File(ff.getAbsoluteFile()+"\\"+"Formulario Seguro Automovel "+sdf1.format(new Date())+".pdf");
            File ff = new File(arquivo + "/" + user + "/Seguro Automovel/");

            ff.mkdirs();
            String Ddata = sdf1.format(new Date());
            ff = new File(ff.getAbsoluteFile() + "/" + "Formulario Seguro Automovel " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro Automovel/" + "Formulario Seguro Automovel " + Ddata + ".pdf";

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
            
            PdfPTable pTableLinha = new PdfPTable(1);
            pTableLinha.setWidthPercentage(88f);
            PdfPCell cellLinha = new PdfPCell(new Phrase("----------------------------------------------------------------------------------------------------------------------", fontCorpo));
            cellLinha.setBorder(0);
            pTableLinha.addCell(cellLinha);
            cellLinha.setPaddingTop(-9f);
            pTableLinha.addCell(cellLinha);

            PdfPTable pTableApoliceAndPeriodo = new PdfPTable(new float[]{40, 60});
            pTableApoliceAndPeriodo.setWidthPercentage(88f);
            

            Paragraph pApolice = new Paragraph();
            pApolice.add(new Phrase("Nº Apólice: ".toUpperCase(), fontCorpo));
            pApolice.add(new Phrase(numApolice, fontCorpoN));
            pApolice.add(new Phrase("\nPOLICY NO:", fontUK));
            PdfPCell cellApolice = new PdfPCell(pApolice);
//            cellApolice.setPaddingTop(-2);
//            cellApolice.setPaddingBottom(4.5f);
            cellApolice.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableApoliceAndPeriodo.addCell(cellApolice);

            Paragraph pPeriodo = new Paragraph();
            pPeriodo.add(new Phrase("Periodo Do Seguro: ".toUpperCase(), fontCorpo));
            pPeriodo.add(new Phrase(((c.getDataInicio() != null) ? sdf.format(c.getDataInicio()) : " ") + " à " + ((c.getDataFim() != null) ? sdf.format(c.getDataFim()) : " "), fontCorpoN));
            pPeriodo.add(new Phrase("\nPERIOD OF INSURANCE:", fontUK));
            PdfPCell cellPeriodo = new PdfPCell(pPeriodo);
//            cellPeriodo.setPaddingTop(-2);
//            cellPeriodo.setPaddingBottom(4.5f);
            cellPeriodo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            pTableApoliceAndPeriodo.addCell(cellPeriodo);

            documento.open();
            documento.add(pTableEmpresaPricipal);
            documento.add(pTableNull);
            documento.add(pTableTitulo);
            documento.add(pTableNull);
            documento.add(pTableApoliceAndPeriodo);
            documento.add(pTableNull);
            documento.add(pTableCliente);
            documento.add(pTableNull);
            documento.add(pTableLinha);
            documento.add(pTableNull);
            documento.add(pTableDadosTitulo);
            documento.add(pTableDados);
            documento.add(pTableNull);
            documento.add(pTableLinha);
            documento.add(pTableNull);
            documento.add(pTableSeguro);
//            documento.add(pTableNull);
//            documento.add(pTableAssinaturaTitulo);
//            documento.add(pTableNull);
//            documento.add(pTableNull);
//            documento.add(pTableAssinatura);
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
        SeguroViatura aPG = new SeguroViatura();
        aPG.criarDoc("ddhd", "223", new Contrato(), new VeiculoBean(), "ah", "text");
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

    public float getPadding(int total) {
        return ((40 / total) < 0) ? 0 : (40 / total);
    }

    public static String toMoeda(String valor) {
        try {
            return Moeda.format(Double.valueOf(valor));
        } catch (Exception e) {
            return valor;
        }
    }
}
