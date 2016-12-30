/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import Export.ConfigDoc.Empresa;
import Export.ConfigDoc.Fontes;
import bean.IncendioBean;
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
public class SeguroIncendio implements Serializable
{
    private String reString;
    public String criarDoc
        (
            String numApolice,
            String numCliente,
            Contrato c,
            IncendioBean ib,
            String user,String sigla, String arquivo
        )
    {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("dd 'DE' MMMM 'DE' yyyy",new Locale("pt", "BR"));
            SimpleDateFormat sdfS= new SimpleDateFormat("dd/MM/yyyy",new Locale("pt", "BR"));
            SimpleDateFormat sdf1= new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
                    
            Font fontCabecalhoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9.5f );
            Font fontCorpo= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f );
//            Font fontTableCo= new Font(Font.FontFamily.HELVETICA,9f , Font.NORMAL);
//            Font fontTableCa= new Font(Font.FontFamily.HELVETICA,9.5f , Font.BOLD);
            Font fontCorpoP= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,8f );
            Font fontCorpoN= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,9f );
//            Font fontCorpoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,8.5f );
            Font fontCorpoNGT= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,10f );
            Font fontCabecalhoNG= FontFactory.getFont(Fontes.FONTB, BaseFont.WINANSI, BaseFont.EMBEDDED ,16f ,Font.UNDERLINE);
            Font fontUK= FontFactory.getFont(Fontes.FONT, BaseFont.WINANSI, BaseFont.EMBEDDED ,5.5f, Font.ITALIC);
            
            PdfPTable pTableEmpresaPricipal= new PdfPTable(new float[]{25f,75f});
            pTableEmpresaPricipal.setWidthPercentage(90f);
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
            
            PdfPCell pCellCaixaPostal= new PdfPCell(new Phrase(Empresa.CAIXAPOSTAL,fontCabecalhoN));
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
            
//            PdfPTable pTableSeguro =new PdfPTable(1);
            PdfPTable pTableCliente =new PdfPTable(1);
            pTableCliente.setWidthPercentage(90f);
            
            Phrase phraseUK;
            
            PdfPTable pTableTitulo= new PdfPTable(1);
            Paragraph pTitulo= new Paragraph("",fontCorpo);
            phraseUK = new Phrase("Formulario de SEguro Incêndio".toUpperCase(), fontCorpoNGT);
            pTitulo.add(phraseUK);
            phraseUK = new Phrase("\nfire insurance schedule".toUpperCase(), fontUK);
            pTitulo.add(phraseUK);
            PdfPCell cellTitulo= new PdfPCell(pTitulo);
            cellTitulo.setBorder(0);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            pTableTitulo.addCell(cellTitulo);
            
            ClienteI ci = new ClienteI(numCliente);
            
            Paragraph p = new Paragraph("", fontCorpo);
            phraseUK = new Phrase("1 - Informações Cliente".toUpperCase(), fontCorpoNGT);
            p.add(phraseUK);
            phraseUK = new Phrase("\n(1 - Customer information)".toUpperCase(), fontUK);
            p.add(phraseUK);
            PdfPCell cellTituloTsbleSegurado= new PdfPCell(p);
            cellTituloTsbleSegurado.setBorder(0);
           
            Paragraph pCl = new Paragraph();
            pCl.add(new Phrase(ci.getNOMEL_(),fontCorpo));
            pCl.add(new Phrase(ci.getNOME_(),fontCorpoN));
            pCl.add(new Phrase("\n(THE INSURED)",fontUK));
            PdfPCell cellNome = new PdfPCell(new Phrase(pCl));
            cellNome.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getENDERECOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getENDERECO_(),fontCorpoN));
            pCl.add(new Phrase("\nAddress".toUpperCase(),fontUK));
            PdfPCell cellEndereco = new PdfPCell(pCl);
            cellEndereco.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase("A SEGURADORA: ",fontCorpo));
            pCl.add(new Phrase("NICON SEGUROS S.A",fontCorpoN));
            pCl.add(new Phrase("\n(THE COMPANY)",fontUK));
            PdfPCell cellNCliente = new PdfPCell( pCl );
            cellNCliente.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getPROFISSAOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getPROFISSAO_(),fontCorpoN));
            pCl.add(new Phrase("\n(occupation)".toUpperCase(),fontUK));
            PdfPCell cellProfissao = new PdfPCell(pCl);
            cellProfissao.setBorder(0);
            
            pCl = new Paragraph();
            pCl.add(new Phrase(ci.getLOCALTRABALHOL_(),fontCorpo));
            pCl.add(new Phrase(ci.getLOCALTRABALHO_(),fontCorpoN));
            pCl.add(new Phrase("\n(workplace)",fontUK));
            PdfPCell cellLocalTrabalho = new PdfPCell(pCl);
            cellLocalTrabalho.setBorder(0);
            
            cellTituloTsbleSegurado.setPaddingBottom(8f);
            pTableCliente.addCell(cellTituloTsbleSegurado);
            cellNome.setPaddingBottom(8f);
            pTableCliente.addCell(cellNome);
            cellEndereco.setPaddingBottom(8f);
            pTableCliente.addCell(cellEndereco);
            cellProfissao.setPaddingBottom(8f);
            pTableCliente.addCell(cellProfissao);
            cellLocalTrabalho.setPaddingBottom(8f);
            pTableCliente.addCell(cellLocalTrabalho);
            cellNCliente.setPaddingBottom(8f);
            pTableCliente.addCell(cellNCliente);
//            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(cellNull);
            
//            PdfPTable pTableDadosTitulo= new PdfPTable(1);
//            PdfPCell cellDadosTitulo= new PdfPCell(new Phrase());
//            cellDadosTitulo.setBorder(0);
            
            Paragraph pInfoTitulo= new Paragraph("".toUpperCase(),fontCorpo);
            phraseUK = new Phrase("2 - Informações do seguro".toUpperCase(), fontCorpoN);
            pInfoTitulo.add(phraseUK);
            phraseUK = new Phrase("\n(2 - insurance information)".toUpperCase(), fontUK);
            pInfoTitulo.add(phraseUK);
            
            
            Paragraph pInfoPro1= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("Valor Da propriedade segurada: ".toUpperCase(), fontCorpo);
            pInfoPro1.add(phraseUK);
            phraseUK = new Phrase(c.getPremioLiquidoMoeda().toUpperCase(), fontCorpo);
            pInfoPro1.add(phraseUK);
            phraseUK = new Phrase("\n(insured property value)".toUpperCase(), fontUK);
            pInfoPro1.add(phraseUK);
            
            Paragraph pInfoPro= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("Endereço da propriedade segurada: ".toUpperCase(), fontCorpo);
            pInfoPro.add(phraseUK);
            phraseUK = new Phrase(ib.getIncendio().getEndereco(), fontCorpo);
            pInfoPro.add(phraseUK);
            phraseUK = new Phrase("\n(property address insured)".toUpperCase(), fontUK);
            pInfoPro.add(phraseUK);
            
            Paragraph pInfo1= new Paragraph("".toUpperCase(), fontCorpoN);
            phraseUK = new Phrase("Riscos Basícos:".toUpperCase(), fontCorpoN);
            pInfo1.add(phraseUK);
            phraseUK = new Phrase("\n(basic risks)".toUpperCase(), fontUK);
            pInfo1.add(phraseUK);
            
            Paragraph pInfo2= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-Edifício".toUpperCase(), fontCorpo);
            pInfo2.add(phraseUK);
            phraseUK = new Phrase("\n(Building)".toUpperCase(), fontUK);
            pInfo2.add(phraseUK);
            
            Paragraph pInfo3= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-Maquinaria: ".toUpperCase()+testeUsado(""), fontCorpo);
            pInfo3.add(phraseUK);
            phraseUK = new Phrase("\n(Machinery)".toUpperCase(), fontUK);
            pInfo3.add(phraseUK);
            
            Paragraph pInfo4= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-Conteúdos".toUpperCase(), fontCorpo);
            pInfo4.add(phraseUK);
            phraseUK = new Phrase("\n(contents)".toUpperCase(), fontUK);
            pInfo4.add(phraseUK);
            
            Paragraph pInfo5= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-Reserva: ".toUpperCase()+testeUsado(""), fontCorpo);
            pInfo5.add(phraseUK);
            phraseUK = new Phrase("\n(STOKS) NA".toUpperCase(), fontUK);
            pInfo5.add(phraseUK);
            
            Paragraph pInfo6= new Paragraph("", fontCorpo);
            phraseUK = new Phrase("-Risco Para com Terceiro/Vizinhos: ".toUpperCase()+testeUsado(""), fontCorpo);
            pInfo6.add(phraseUK);
            phraseUK = new Phrase("\n(third party/neighbour's risk )".toUpperCase(), fontUK);
            pInfo6.add(phraseUK);
            
            Paragraph pInfo7= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-Renda: ".toUpperCase()+testeUsado(""), fontCorpo);
            pInfo7.add(phraseUK);
            phraseUK = new Phrase("\n(RENT)".toUpperCase(), fontUK);
            pInfo7.add(phraseUK);
            
            Paragraph pInfo8= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-Despesas com perito: ".toUpperCase()+testeUsado(""), fontCorpo);
            pInfo8.add(phraseUK);
            phraseUK = new Phrase("\n(expert fees)".toUpperCase(), fontUK);
            pInfo8.add(phraseUK);
            
            Paragraph pInfo9= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-risco especiais: ".toUpperCase()+testeUsado(""), fontCorpo);
            pInfo9.add(phraseUK);
            phraseUK = new Phrase("\n(Special risk)".toUpperCase(), fontUK);
            pInfo9.add(phraseUK);
            
            Paragraph pInfo10= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-impactos: ".toUpperCase()+testeUsado(""), fontCorpo);
            pInfo10.add(phraseUK);
            phraseUK = new Phrase("\n(impacts)".toUpperCase(), fontUK);
            pInfo10.add(phraseUK);
            
            Paragraph pInfo11= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-motim & greve".toUpperCase(), fontCorpo);
            pInfo11.add(phraseUK);
            phraseUK = new Phrase("\n(Riot & Strike)".toUpperCase(), fontUK);
            pInfo11.add(phraseUK);
            
            Paragraph pInfo12= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-incêndio florestal: ".toUpperCase()+testeUsado(ib.getIncendioRisco().getIncendioFId()), fontCorpo);
            pInfo12.add(phraseUK);
            phraseUK = new Phrase("\n(bush fire)".toUpperCase(), fontUK);
            pInfo12.add(phraseUK);
            
            Paragraph pInfo12_1= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-explosão de tubos".toUpperCase(), fontCorpo);
            pInfo12_1.add(phraseUK);
            phraseUK = new Phrase("\n(Burst pipes)".toUpperCase(), fontUK);
            pInfo12_1.add(phraseUK);
            
            Paragraph pInfo12_2= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-danos maliciosos: ".toUpperCase()+testeUsado(ib.getIncendioRisco().getDanoId()), fontCorpo);
            pInfo12_2.add(phraseUK);
            phraseUK = new Phrase("\n(malicious damage)".toUpperCase(), fontUK);
            pInfo12_2.add(phraseUK);
            
            Paragraph pInfo13= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-Explosão: ".toUpperCase()+testeUsado(ib.getIncendioRisco().getExplosaoId()), fontCorpo);
            pInfo13.add(phraseUK);
            phraseUK = new Phrase("\n(Explosion)".toUpperCase(), fontUK);
            pInfo13.add(phraseUK);
            
            Paragraph pInfo14= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("-terramoto: ".toUpperCase()+testeUsado(ib.getIncendioRisco().getTerramotoId()), fontCorpo);
            pInfo14.add(phraseUK);
            phraseUK = new Phrase("\n(earthquake)".toUpperCase(), fontUK);
            pInfo14.add(phraseUK);
            
            Paragraph pInfo14_1= new Paragraph("", fontCorpo);
            phraseUK = new Phrase("-Queda de aeronave: ".toUpperCase()+testeUsado(ib.getIncendioRisco().getRiscoAviaoId()), fontCorpo);
            pInfo14_1.add(phraseUK);
            phraseUK = new Phrase("\n(plane crash)".toUpperCase(), fontUK);
            pInfo14_1.add(phraseUK);
            
            Paragraph pInfo15= new Paragraph("", fontCorpo);
            phraseUK = new Phrase("-trovoada, tempestade e inundação: ".toUpperCase()+testeUsado(ib.getIncendioRisco().getTempestadeId()), fontCorpo);
            pInfo15.add(phraseUK);
            phraseUK = new Phrase("\n(storm, tempest and flood)".toUpperCase(), fontUK);
            pInfo15.add(phraseUK);
            
            PdfPCell CellData= new PdfPCell(pInfoTitulo);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
            
            CellData= new PdfPCell(pInfoPro);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);

            CellData= new PdfPCell(pInfoPro1);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);         
//            cellDadosTitulo.addElement(pInfoPro1);
            
            CellData= new PdfPCell(pInfo1);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo1);
            
            CellData= new PdfPCell(pInfo2);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo2);
            
            CellData= new PdfPCell(pInfo3);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo3);
            
            CellData= new PdfPCell(pInfo4);
            CellData.setBorder(0);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo4);
            
            CellData= new PdfPCell(pInfo5);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo5);
            
            CellData= new PdfPCell(pInfo6);
            CellData.setBorder(0);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo6);
            
            CellData= new PdfPCell(pInfo7);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo7);
            
            CellData= new PdfPCell(pInfo8);
            CellData.setBorder(0);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo8);
            
            CellData= new PdfPCell(pInfo9);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo9);
            
            CellData= new PdfPCell(pInfo10);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo10);
            
            CellData= new PdfPCell(pInfo11);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo11);
            
            CellData= new PdfPCell(pInfo12);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo12);
            
            CellData= new PdfPCell(pInfo12_1);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo12_1);
            
            CellData= new PdfPCell(pInfo12_2);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo12_2);
            
            CellData= new PdfPCell(pInfo13);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo13);
            
            CellData= new PdfPCell(pInfo14);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo14);
            
            CellData= new PdfPCell(pInfo14_1);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellDadosTitulo.addElement(pInfo14_1);
            
            CellData= new PdfPCell(pInfo15);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
            
            pTableCliente.addCell(cellNull);
//            cellDadosTitulo.addElement(pInfo15);
            

//            PdfPCell cellSeguro= new PdfPCell(new Phrase());
//            cellSeguro.setBorder(0);
            Paragraph pSegTitulo= new Paragraph("".toUpperCase(),fontCorpoN);
            phraseUK = new Phrase("3 - Informações Montante e periodo de Segurado".toUpperCase(), fontCorpoN);
            pSegTitulo.add(phraseUK);
            phraseUK = new Phrase("\n(3 - Amount information and period of Insured)".toUpperCase(), fontUK);
            pSegTitulo.add(phraseUK);
            
            Paragraph pSeg1= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("período Seguro de: ".toUpperCase(), fontCorpo);
            pSeg1.add(phraseUK);
            phraseUK = new Phrase(sdfS.format(c.getDataInicio())+" até ".toUpperCase()+sdfS.format(c.getDataFim())+" (16H00 HORAS DA DATA DE EXPIRAÇão)".toUpperCase(), fontCorpoN);
            pSeg1.add(phraseUK);
            phraseUK = new Phrase("\n(preiod of insurance)     (from)     (to)     (4 o'clock in the afternoon of the expiring date)".toUpperCase(), fontUK);
            pSeg1.add(phraseUK);
            
            Paragraph pSeg2= new Paragraph("", fontCorpo);
            phraseUK = new Phrase("Data Da renovação: ".toUpperCase(), fontCorpo);
            pSeg2.add(phraseUK);
            phraseUK = new Phrase(((c.getDataRenovacao()==null)? "":sdfS.format(c.getDataRenovacao())).toUpperCase(), fontCorpo);
            pSeg2.add(phraseUK);
            phraseUK = new Phrase("\n(renewal date)".toUpperCase(), fontUK);
            pSeg2.add(phraseUK);
            
//            Paragraph pSeg3= new Paragraph("   Valo".toUpperCase(), fontCorpo);
//            Paragraph pSeg4= new Paragraph("   Data de Renovação: ".toUpperCase()+, fontCorpo);
            
            Paragraph pSeg5= new Paragraph("", fontCorpo);
            phraseUK = new Phrase("valor do primeiro préMio: ".toUpperCase(), fontCorpo);
            pSeg5.add(phraseUK);
            phraseUK = new Phrase(c.getPrimeiroPremio(), fontCorpo);
            pSeg5.add(phraseUK);
            phraseUK = new Phrase("\n(first premiun)".toUpperCase(), fontUK);
            pSeg5.add(phraseUK);
            
            Paragraph pSeg6= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("Valor do Prémio Anual: ".toUpperCase(), fontCorpo);
            pSeg6.add(phraseUK);
            phraseUK = new Phrase(c.getPremioAnual(), fontCorpo);
            pSeg6.add(phraseUK);
            phraseUK = new Phrase("\n(annuals premium)".toUpperCase(), fontUK);
            pSeg6.add(phraseUK);
            
            Paragraph pSeg7= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("valor do prémio bruto: ".toUpperCase(), fontCorpo);
            pSeg7.add(phraseUK);
            phraseUK = new Phrase(c.getPremioBrutoMoeda(), fontCorpo);
            pSeg7.add(phraseUK);
            phraseUK = new Phrase("\n(gross premium)".toUpperCase(), fontUK);
            pSeg7.add(phraseUK);
            
            Paragraph pSeg8= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("menos (SE aplicável) Não".toUpperCase(), fontCorpo);
            pInfo14_1.add(phraseUK);
            phraseUK = new Phrase("\nLESS (if applicable)".toUpperCase(), fontUK);
            pInfo14_1.add(phraseUK);
            
            Paragraph pSeg9= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("a adicionar: impst. @ 5% + IMpost. sobre selo @ 0.6% = ".toUpperCase(), fontCorpo);
            pSeg9.add(phraseUK);
            phraseUK = new Phrase(" ", fontCorpo);
            pSeg9.add(phraseUK);
            phraseUK = new Phrase("\n(add)".toUpperCase(), fontUK);
            pSeg9.add(phraseUK);
            
            Paragraph pSeg10= new Paragraph("".toUpperCase(), fontCorpo);
            phraseUK = new Phrase("Valor do prémio liquido a pagar: ".toUpperCase(), fontCorpo);
            pSeg10.add(phraseUK);
            phraseUK = new Phrase(c.getPremioLiquidoMoeda(), fontCorpo);
            pSeg10.add(phraseUK);
            phraseUK = new Phrase("\n(net premium payable)".toUpperCase(), fontUK);
            pSeg10.add(phraseUK);
            
//            Paragraph pSeg11= new Paragraph("   outras condições/memorado                                                                              por favor, volte a página".toUpperCase(), fontCorpo);
            Paragraph pSeg13= new Paragraph("   ".toUpperCase(), fontCorpo); 
            
            Paragraph pSegData= new Paragraph("", fontCorpo);
            phraseUK = new Phrase("Data Em: ".toUpperCase(), fontCorpo);
            pSegData.add(phraseUK);
            phraseUK = new Phrase(sdf.format(new Date()).toUpperCase(), fontCorpo);
            pSegData.add(phraseUK);
            phraseUK = new Phrase("\n(DATE IN)".toUpperCase(), fontUK);
            pSegData.add(phraseUK);
            
            CellData= new PdfPCell(pSegTitulo);
            CellData.setBorder(0);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSegTitulo);
            
            CellData= new PdfPCell(pSeg1);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg1);
            
            CellData= new PdfPCell(pSeg2);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg2);
            
//            cellSeguro.addElement(pSeg3);
//            cellSeguro.addElement(pSeg4);
            
            CellData= new PdfPCell(pSeg5);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg5);
            
            CellData= new PdfPCell(pSeg6);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg6);
            
            CellData= new PdfPCell(pSeg7);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg7);
            
            CellData= new PdfPCell(pSeg8);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg8);
            
            CellData= new PdfPCell(pSeg9);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
            
//            cellSeguro.addElement(pSeg9);
            
            CellData= new PdfPCell(pSeg10);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg10);
            
//            cellSeguro.addElement(pSeg11);
            
            CellData= new PdfPCell(pSeg13);
            CellData.setBorder(0);
            CellData.setPaddingBottom(8f);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSeg13);

            CellData= new PdfPCell(pSegData);
            CellData.setBorder(0);
            pTableCliente.addCell(CellData);
//            cellSeguro.addElement(pSegData);
            
//            pTableCliente.addCell(cellSeguro);
            
            
            PdfPTable pTableAssinaturaTitulo= new PdfPTable(1);
            PdfPTable pTableAssinatura= new PdfPTable( new float[]{50f,50f});
            
            Paragraph p1 = new Paragraph(new Phrase("Assinaturas".toUpperCase(),fontCorpoN));
            p1.add(new Phrase("\nsignature".toUpperCase(),fontUK));
            
            PdfPCell cellAssinatora= new PdfPCell(p1);
            cellAssinatora.setBorder(0);
            cellAssinatora.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha1= new PdfPCell(new Phrase("________________________________".toUpperCase(),fontCorpo));
            celllinha1.setBorder(0);
            celllinha1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celllinha2= new PdfPCell(new Phrase("________________________________".toUpperCase(),fontCorpo));
            celllinha2.setBorder(0);
            celllinha2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            p1 = new Paragraph(new Phrase("para nicon Seguro sa stp".toUpperCase(),fontCorpoP));
            p1.add(new Phrase("\nFor nicon seguros sa stp".toUpperCase(),fontUK));
            
            PdfPCell celllinha11= new PdfPCell(p1);
            celllinha11.setBorder(0);
            celllinha11.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            p1 = new Paragraph(new Phrase("o segurado".toUpperCase(),fontCorpoP));
            p1.add(new Phrase("\nthe insured".toUpperCase(),fontUK));
            PdfPCell celllinha21= new PdfPCell(p1);
            celllinha21.setBorder(0);
            celllinha21.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            pTableAssinaturaTitulo.addCell(cellAssinatora);
            pTableAssinatura.addCell(celllinha1);
            pTableAssinatura.addCell(celllinha2);
            pTableAssinatura.addCell(celllinha11);
            pTableAssinatura.addCell(celllinha21);
                    
            Document documento= new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 35f, 60f);
            
            
            File ff= new File(arquivo+"/"+user+"/Seguro Incendio/");
            
            ff.mkdirs();
            String Ddata=sdf1.format(new Date());
            ff =new File(ff.getAbsoluteFile()+"/"+"Formulario Seguro Incendio "+Ddata+".pdf");
            
            reString ="../Documentos/"+user+"/Seguro Incendio/"+"Formulario Seguro Incendio "+Ddata+".pdf";
            
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
//            documento.add(pTableNull);
//            documento.add(pTableDadosTitulo);
//            documento.add(pTableNull);
//            documento.add(pTableSeguro);
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
        SeguroIncendio aPG= new SeguroIncendio();
        aPG.criarDoc("ddhd", "223",new Contrato(),new IncendioBean(),"ah","std","");
    }
    public String testeUsado(String cobertuta)
    {
        if(!cobertuta.equals("true"))
            return "NÃO APLICAVEL";
        else
            return "SIM";
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
