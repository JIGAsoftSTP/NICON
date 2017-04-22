/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.Contrato;
import validacao.OperacaoData;

/**
 *
 * @author AhmedJorge
 */
public class DocNotaDebito implements Serializable {
    
    public static boolean isReseguro = false;
    public static String segurado;
    
    @SuppressWarnings({"CallToPrintStackTrace", "null", "UnusedAssignment"})
    public String docSeguros(
            String nomeSeguro,
            String numApolice,
            String interCodVendedor,
            String idCliente,
            String fundoContrato,
            ArrayList<String[]> listaValores,
            Contrato contrato,
            String user,
            String moeda, String arquivo, String numeroRegistro
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

            PdfPTable pTableFatura = new PdfPTable(new float[]{80, 20});
            pTableFatura.setWidthPercentage(95);

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

            PdfPCell pCellPolice = new PdfPCell(new Phrase(Empresa.APOLICE + numApolice, fontCabecalhoN));
            pCellPolice.setBorder(0);

            PdfPCell pCellDebNF = new PdfPCell(new Phrase("Deb. Nº", fontCabecalhoS));
            pCellDebNF.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pCellDebNF.setBorder(0);

            PdfPCell pCellDebN = new PdfPCell(new Phrase(numeroRegistro, fontCabecalhoS));
            pCellDebN.setHorizontalAlignment(Element.ALIGN_CENTER);
            pCellDebN.setBorder(0);

            PdfPCell pCellInterCoF = new PdfPCell(new Phrase("Inter COD:", fontCabecalhoS));
            pCellInterCoF.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pCellInterCoF.setBorder(0);

            PdfPCell pCellInterCo = new PdfPCell(new Phrase(interCodVendedor, fontCabecalhoS));
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

            PdfPCell cellFaturaTitulo = new PdfPCell(new Phrase("FACTURA", fontCabecalhoN));
            cellFaturaTitulo.setBorder(0);
            cellFaturaTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell cellFaturaTipo = new PdfPCell(new Phrase(nomeSeguro, fontCabecalhoN));
            cellFaturaTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellFaturaTipo.setBorder(0);

            PdfPTable pTableLinha = new PdfPTable(1);
            pTableLinha.setWidthPercentage(95);
            PdfPCell linha = new PdfPCell(new Phrase(" ", fontLinha));
            linha.setBorderWidthTop(0.5f);
            linha.setBorderWidthBottom(0);
            linha.setBorderWidthLeft(0);
            linha.setBorderWidthRight(0);
            pTableLinha.addCell(linha);

            pTableFatura.addCell(cellFaturaTitulo);
            pTableFatura.addCell(cellFaturaTipo);

            PdfPTable pTableClientePrincipal = new PdfPTable(new float[]{60, 10, 30});
            pTableClientePrincipal.setWidthPercentage(95);
            PdfPTable pTableClienteDescisao = new PdfPTable(1);
            PdfPTable pTableDataEVenda = new PdfPTable(1);
            PdfPTable pTableTitulo = new PdfPTable(1);
            pTableTitulo.setWidthPercentage(95);

            ClienteI ci = null;
            ArrayList<DataReseguro.DataEmpresa> listaDataEmpresas = new ArrayList<>();
            if(!isReseguro) {  ci = new ClienteI(idCliente); }
            else { listaDataEmpresas = DataReseguro.getDadosEmpresa(Integer.valueOf(idCliente)); }
            
            Paragraph pCl = new Paragraph();
            pCl.add(new Phrase((isReseguro) ? "NOME: " : ci.getNOMEL_(), fontCorpo));
            pCl.add(new Phrase( (isReseguro)  ? listaDataEmpresas.get(0).getEMPRESA().toUpperCase()+" ("+ segurado+")" : ci.getNOME_(), fontCorpoN));
            PdfPCell cellNomeCliente = new PdfPCell(pCl);
            cellNomeCliente.setBorder(0);

            pCl = new Paragraph();
            pCl.add(new Phrase((isReseguro)  ? "ENDEREÇO: " : ci.getENDERECOL_(), fontCorpo));
            pCl.add(new Phrase((isReseguro) ? listaDataEmpresas.get(0).getENDERECO() : ci.getENDERECO_(), fontCorpoN));
            if (!isReseguro) {
                pCl.add(new Phrase("       ", fontCorpoN));
                pCl.add(new Phrase((isReseguro) ? "" : ci.getCODPOSTALL_(), fontCorpo));
                pCl.add(new Phrase((isReseguro) ? "" : ci.getCODPOSTAL_(), fontCorpoN));
            }
            PdfPCell cellClienteMorada = new PdfPCell(pCl);
            cellClienteMorada.setBorder(0);
            
            PdfPCell cellNomeLocalidade = null;
            PdfPCell cellNomeTelefone = null;
            if(!isReseguro)
            {
                pCl = new Paragraph();
                pCl.add(new Phrase(ci.getLOCALTRABALHOL_(), fontCorpo));
                pCl.add(new Phrase(ci.getLOCALTRABALHO_(), fontCorpoN));
                cellNomeLocalidade = new PdfPCell(pCl);
                cellNomeLocalidade.setBorder(0);

                pCl = new Paragraph();
                pCl.add(new Phrase(ci.getTELEFONEL_(), fontCorpo));
                pCl.add(new Phrase(ci.getTELEFONE_(), fontCorpoN));
                cellNomeTelefone = new PdfPCell(pCl);
                cellNomeTelefone.setBorder(0);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            SimpleDateFormat sdfContr = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh'.'mm'.'ss");
            PdfPCell cellDataContrato = new PdfPCell(new Phrase("Data: " + (contrato.getDataInicio() != null ? sdfContr.format(contrato.getDataInicio()) : "") + " - " + (contrato.getDataFim() != null ? sdfContr.format(contrato.getDataFim()) : ""), fontCorpo));
            cellDataContrato.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDataContrato.setVerticalAlignment(Element.ALIGN_BOTTOM);
            cellDataContrato.setBorderWidthTop(1f);
            cellDataContrato.setBorderWidthBottom(0f);
            cellDataContrato.setBorderWidthLeft(1f);
            cellDataContrato.setBorderWidthRight(1f);

            PdfPCell cellVendetor = new PdfPCell(new Phrase("Vendedor: " + ((!isReseguro) ? "NICON SEGUROS" : listaDataEmpresas.get(0).getEMPRESA().toUpperCase()), fontCorpo));
            cellVendetor.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellVendetor.setVerticalAlignment(Element.ALIGN_TOP);
            cellVendetor.setBorderWidthTop(0f);
            cellVendetor.setBorderWidthBottom(1f);
            cellVendetor.setBorderWidthLeft(1f);
            cellVendetor.setBorderWidthRight(1f);

            pTableClienteDescisao.addCell(cellNomeCliente);
            pTableClienteDescisao.addCell(cellClienteMorada);
            if (!isReseguro) {
                pTableClienteDescisao.addCell(cellNomeLocalidade);
                pTableClienteDescisao.addCell(cellNomeTelefone);
            }

            PdfPCell cellZerro = new PdfPCell(new Phrase(" ", fontNull));
            cellZerro.setBorder(0);
            PdfPCell cellZerro2 = new PdfPCell(new Phrase(" "));
            cellZerro2.setBorderWidthTop(0f);
            cellZerro2.setBorderWidthBottom(0f);
            cellZerro2.setBorderWidthLeft(1f);
            cellZerro2.setBorderWidthRight(1f);

            PdfPTable pTableNull = new PdfPTable(1);
            pTableNull.setWidthPercentage(95);
            pTableNull.addCell(cellZerro);

            pTableDataEVenda.addCell(cellDataContrato);
            pTableDataEVenda.addCell(cellZerro2);
            pTableDataEVenda.addCell(cellVendetor);

            PdfPCell cellTitulo = new PdfPCell(new Phrase("Cliente", fontCabecalhoN));
            cellTitulo.setBorder(0);

            pTableTitulo.addCell(cellZerro);
            pTableTitulo.addCell(cellTitulo);

            PdfPCell cellCliente = new PdfPCell(pTableClienteDescisao);
            cellCliente.setBorder(0);

            PdfPCell cellCliente1 = new PdfPCell(pTableDataEVenda);
            cellCliente1.setBorder(0);

            pTableClientePrincipal.addCell(cellCliente);
            pTableClientePrincipal.addCell(cellZerro);
            pTableClientePrincipal.addCell(cellCliente1);

            PdfPCell cellQuan = new PdfPCell(new Phrase("QTD", fontCorpo));
            PdfPCell cellDescricao = new PdfPCell(new Phrase("Descrição", fontCorpo));
            PdfPCell cellValorUnitatio = new PdfPCell(new Phrase("Valor Unitário", fontCorpo));
            PdfPCell cellTotal = new PdfPCell(new Phrase("Total", fontCorpo));
            PdfPTable TableRegistro = new PdfPTable(new float[]{10, 45, 28, 17});
            TableRegistro.setWidthPercentage(95);

            cellQuan.setBorderWidthBottom(1f);
            cellQuan.setBorderWidthTop(1f);
            cellQuan.setBorderWidthLeft(1f);
            cellQuan.setBorderWidthRight(1f);
            cellQuan.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellQuan.setVerticalAlignment(Element.ALIGN_CENTER);

            cellDescricao.setBorderWidthBottom(1f);
            cellDescricao.setBorderWidthTop(1f);
            cellDescricao.setBorderWidthLeft(0.5f);
            cellDescricao.setBorderWidthRight(1f);
            cellDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDescricao.setVerticalAlignment(Element.ALIGN_CENTER);

            cellValorUnitatio.setBorderWidthBottom(1f);
            cellValorUnitatio.setBorderWidthTop(1f);
            cellValorUnitatio.setBorderWidthLeft(0.5f);
            cellValorUnitatio.setBorderWidthRight(1f);
            cellValorUnitatio.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellValorUnitatio.setVerticalAlignment(Element.ALIGN_CENTER);

            cellTotal.setBorderWidthBottom(1f);
            cellTotal.setBorderWidthTop(1f);
            cellTotal.setBorderWidthLeft(0.5f);
            cellTotal.setBorderWidthRight(1f);
            cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTotal.setVerticalAlignment(Element.ALIGN_CENTER);

            TableRegistro.addCell(cellQuan);
            TableRegistro.addCell(cellDescricao);
            TableRegistro.addCell(cellValorUnitatio);
            TableRegistro.addCell(cellTotal);

            int total = (listaValores.size() > 21) ? listaValores.size() : 18;
            
            boolean rulpas = false;
            for (int i = 0; i < total + 1; i++) {
                /**
                 * Serve para add linha a Table <TableRegistro> e testa caso o ArrayList tenha
                 * Menos linha que o <total> a Table <TableRegistro> continua recebendo dados Com String Vazios
                 */
                if (listaValores.size() - 1 >= i || (listaValores.size() - 1 ==  i )) {
//                    System.err.println(i);
                    cellQuan = new PdfPCell(new Phrase(listaValores.get(i)[0], fontCorpo));
                    cellDescricao = new PdfPCell(new Phrase(listaValores.get(i)[1], fontCorpo));
                    cellValorUnitatio = new PdfPCell(new Phrase(listaValores.get(i)[2] + " " + ( (!nomeSeguro.equals("Viagem") ) ? contrato.getSigla() : ""), fontCorpo));
                    cellTotal = new PdfPCell(new Phrase(listaValores.get(i)[3] + " " + ( (!nomeSeguro.equals("Viagem") || listaValores.size()-1 == i ) ? contrato.getSigla() : ""), fontCorpo));
                    
                    cellQuan.setPaddingTop(2.5f);
                    cellDescricao.setPaddingTop(2.5f);
                    cellValorUnitatio.setPaddingTop(2.5f);
                    cellTotal.setPaddingTop(2.5f);
                    
                    cellQuan.setPaddingBottom(2.5f);
                    cellDescricao.setPaddingBottom(2.5f);
                    cellValorUnitatio.setPaddingBottom(2.5f);
                    cellTotal.setPaddingBottom(2.5f);
                    
                    cellQuan.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cellDescricao.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cellValorUnitatio.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cellTotal.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    
                } else {
                    cellQuan = new PdfPCell(new Phrase(" ", fontCorpo));
                    cellDescricao = new PdfPCell(new Phrase(" ", fontCorpo));
                    cellValorUnitatio = new PdfPCell(new Phrase(" ", fontCorpo));
                    cellTotal = new PdfPCell(new Phrase(" ", fontCorpo));
                }
                
                if ( listaValores.size()-1 == i && !rulpas )
                {
                    cellDescricao.setRowspan(total-i);
                    rulpas = true;
                }

                cellQuan.setBorderWidthBottom(0f);
                cellQuan.setBorderWidthTop((listaValores.size()-1 == i) ? 1f : 0f);
                cellQuan.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellDescricao.setBorderWidthBottom( (listaValores.size()-1 == i) ? 1f : 0f );
                cellDescricao.setBorderWidthTop((listaValores.size()-1 == i) ? 1f : 0f);
                cellDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellValorUnitatio.setBorderWidthBottom(0f);
                cellValorUnitatio.setBorderWidthTop((listaValores.size()-1 == i) ? 1f : 0f);
                cellValorUnitatio.setHorizontalAlignment(Element.ALIGN_CENTER);

                cellTotal.setBorderWidthBottom(0f);
                cellTotal.setBorderWidthTop((listaValores.size()-1 == i) ? 1f : 0f);
                cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);

                if (i+1 == total) {
                    cellQuan.setBorderWidthBottom(1f);
                    cellDescricao.setBorderWidthBottom(1f);
                    cellValorUnitatio.setBorderWidthBottom(1f);
                    cellTotal.setBorderWidthBottom(1f);
                }

                TableRegistro.addCell(cellQuan);
                if (listaValores.size() - 1 >= i || listaValores.size()-1 == i) {
                    TableRegistro.addCell(cellDescricao);
                }
                
                TableRegistro.addCell(cellValorUnitatio);
                TableRegistro.addCell(cellTotal);
            }
            PdfPTable pTablePrincipalPagamento = new PdfPTable(new float[]{55, 45});
            pTablePrincipalPagamento.setWidthPercentage(95);
            PdfPTable pTableFomaPagamentoPricipal = new PdfPTable(1);
            PdfPTable pTableFomaPagamento = new PdfPTable(new float[]{15, 50, 10});
            PdfPTable pTableFomaValoresPricipal = new PdfPTable(1);
            PdfPTable pTableFomaValores = new PdfPTable(new float[]{15, 47, 38});

            PdfPCell cellvazio = new PdfPCell(new Phrase(" ", fontCorpo));
            PdfPCell cellTituloFormaPagamento = new PdfPCell(new Phrase("Forma de Pagamento", fontCorpoN));
            cellTituloFormaPagamento.setBorderWidthBottom(0f);
            cellTituloFormaPagamento.setBorderWidthTop(1f);
            cellTituloFormaPagamento.setBorderWidthRight(0f);
            cellTituloFormaPagamento.setBorderWidthLeft(0f);

            PdfPCell cellTituloForma1 = new PdfPCell(new Phrase("© Cheque", fontCorpo));
            cellTituloForma1.setBorderWidthBottom(0f);
            cellTituloForma1.setBorderWidthTop(0f);
            cellTituloForma1.setBorderWidthRight(0f);
            cellTituloForma1.setBorderWidthLeft(0f);

            PdfPCell cellTituloForma2 = new PdfPCell(new Phrase("© Dinheiro", fontCorpo));
            cellTituloForma2.setBorderWidthBottom(0f);
            cellTituloForma2.setBorderWidthTop(0f);
            cellTituloForma2.setBorderWidthRight(0f);
            cellTituloForma2.setBorderWidthLeft(0f);

            PdfPCell cellTituloForma3 = new PdfPCell(new Phrase("© Outros", fontCorpo));
            cellTituloForma3.setBorderWidthBottom(1f);
            cellTituloForma3.setBorderWidthTop(0f);
            cellTituloForma3.setBorderWidthRight(0f);
            cellTituloForma3.setBorderWidthLeft(0f);

            cellvazio.setBorderWidthBottom(0f);
            cellvazio.setBorderWidthTop(1f);
            cellvazio.setBorderWidthRight(0f);
            cellvazio.setBorderWidthLeft(1f);
            pTableFomaPagamento.addCell(cellvazio);
            pTableFomaPagamento.addCell(cellTituloFormaPagamento);
            cellvazio.setBorderWidthBottom(0f);
            cellvazio.setBorderWidthTop(1f);
            cellvazio.setBorderWidthRight(1f);
            cellvazio.setBorderWidthLeft(0f);
            pTableFomaPagamento.addCell(cellvazio);

            cellvazio.setBorderWidthBottom(0f);
            cellvazio.setBorderWidthTop(0f);
            cellvazio.setBorderWidthRight(0f);
            cellvazio.setBorderWidthLeft(1f);
            pTableFomaPagamento.addCell(cellvazio);
            pTableFomaPagamento.addCell(cellTituloForma1);
            cellvazio.setBorderWidthBottom(0f);
            cellvazio.setBorderWidthTop(0f);
            cellvazio.setBorderWidthRight(1f);
            cellvazio.setBorderWidthLeft(0f);
            pTableFomaPagamento.addCell(cellvazio);

            cellvazio.setBorderWidthBottom(0f);
            cellvazio.setBorderWidthTop(0f);
            cellvazio.setBorderWidthRight(0f);
            cellvazio.setBorderWidthLeft(1f);
            pTableFomaPagamento.addCell(cellvazio);
            pTableFomaPagamento.addCell(cellTituloForma2);
            cellvazio.setBorderWidthBottom(0f);
            cellvazio.setBorderWidthTop(0f);
            cellvazio.setBorderWidthRight(1f);
            cellvazio.setBorderWidthLeft(0f);
            pTableFomaPagamento.addCell(cellvazio);

            cellvazio.setBorderWidthBottom(1f);
            cellvazio.setBorderWidthTop(0f);
            cellvazio.setBorderWidthRight(0f);
            cellvazio.setBorderWidthLeft(1f);
            pTableFomaPagamento.addCell(cellvazio);
            pTableFomaPagamento.addCell(cellTituloForma3);
            cellvazio.setBorderWidthBottom(1f);
            cellvazio.setBorderWidthTop(0f);
            cellvazio.setBorderWidthRight(1f);
            cellvazio.setBorderWidthLeft(0f);
            pTableFomaPagamento.addCell(cellvazio);

            PdfPCell cellNull = new PdfPCell(new Phrase(" ", fontMenor));
            cellNull.setBorderWidthBottom(0f);
            cellNull.setBorderWidthTop(0f);
            cellNull.setBorderWidthRight(0f);
            cellNull.setBorderWidthLeft(0f);

            pTableFomaPagamentoPricipal.addCell(cellNull);
            PdfPCell cellPagamento = new PdfPCell(pTableFomaPagamento);
            cellPagamento.setBorder(0);
            pTableFomaPagamentoPricipal.addCell(cellPagamento);

            PdfPCell cellPecentage1 = new PdfPCell(new Phrase(((nomeSeguro.equals("Viagem") || isReseguro) ? " " : ((nomeSeguro.equals("Acidente Para Grupo")) ? " " : "2.50%")), fontCorpo));
            cellPecentage1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPecentage1.setBorderWidthBottom(0f);
            cellPecentage1.setBorderWidthTop(1f);
            cellPecentage1.setBorderWidthRight(0f);
            cellPecentage1.setBorderWidthLeft(1f);

            PdfPCell cellPecentage2 = new PdfPCell(new Phrase( (isReseguro) ? " " : "5%", fontCorpo));
            cellPecentage2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPecentage2.setBorderWidthBottom(0f);
            cellPecentage2.setBorderWidthTop(0f);
            cellPecentage2.setBorderWidthRight(0f);
            cellPecentage2.setBorderWidthLeft(1f);

            PdfPCell cellPecentage3 = new PdfPCell(new Phrase(" ", fontCorpo));
            cellPecentage3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPecentage3.setBorderWidthBottom(0f);
            cellPecentage3.setBorderWidthTop(0f);
            cellPecentage3.setBorderWidthRight(0f);
            cellPecentage3.setBorderWidthLeft(1f);

            PdfPCell cellFundo = new PdfPCell(new Phrase(fundoContrato, fontCorpo));
            cellFundo.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellFundo.setBorderWidthBottom(0f);
            cellFundo.setBorderWidthTop(1f);
            cellFundo.setBorderWidthRight(0f);
            cellFundo.setBorderWidthLeft(0f);

            PdfPCell cellImposto = new PdfPCell(new Phrase((isReseguro) ? " " : "IMPOSTO", fontCorpo));
            cellImposto.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellImposto.setBorderWidthBottom(0f);
            cellImposto.setBorderWidthTop(0f);
            cellImposto.setBorderWidthRight(0f);
            cellImposto.setBorderWidthLeft(0f);

            PdfPCell cellSelo = new PdfPCell(new Phrase( (isReseguro) ? " " : "ACESSORIOS", fontCorpo));
            cellSelo.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellSelo.setBorderWidthBottom(0f);
            cellSelo.setBorderWidthTop(0f);
            cellSelo.setBorderWidthRight(0f);
            cellSelo.setBorderWidthLeft(0f);
            
            PdfPCell cellvazio1;
            if (!isReseguro) {
                cellvazio1 = new PdfPCell(new Phrase(((nomeSeguro.equals("Viagem")) ? 
                            ((contrato.getValorNC() == null || contrato.getValorNC().isEmpty()) ? " " : Moeda.format(Double.valueOf(contrato.getValorNC().replace(',', '.'))) + " EUR") 
                                    : ((nomeSeguro.equals("Acidente Para Grupo")) ? " " : ((nomeSeguro.equals("Automovel"))
                                            ? Moeda.format((Double.valueOf(contrato.getPremioLiquido()) * 0.025)) + " " + contrato.getSigla() : "2.50%"))), fontCorpo));
            } else {
                cellvazio1 = new PdfPCell(new Phrase(" ", fontCorpo));
            }
            pTableFomaValores.addCell(cellPecentage1);
            pTableFomaValores.addCell(cellFundo);
            cellvazio1.setBorderWidthTop(1);
            cellvazio1.setBorderWidthRight(1);
            cellvazio1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pTableFomaValores.addCell(cellvazio1);

            double i05 = 0;//imposto 0.05%
            double i25 = 0;//imposto 2.5%
            try {
                if (nomeSeguro.equals("Viagem")) {
                    i05 = 0.05 * Moeda.arrendodamento(contrato.getValorNC().replace(',', '.'));
                    i25 = 0.006 * Moeda.arrendodamento(contrato.getValorNC().replace(',', '.'));
                } else if(!isReseguro){
                    i05 =  0.05 * Moeda.arrendodamento(contrato.getPremioLiquido());
                    i25 =  0.006 * Moeda.arrendodamento(contrato.getPremioLiquido());
                }
            } catch (Exception e) {
            }
            PdfPCell cellvazio2 = new PdfPCell(new Phrase( (isReseguro) ? " " : (Moeda.format( i05 ) + " " + contrato.getSigla()), fontCorpo));
            pTableFomaValores.addCell(cellPecentage2);
            pTableFomaValores.addCell(cellImposto);
            cellvazio2.setBorderWidthRight(1);
            cellvazio2.setBottom(10000);
            cellvazio2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pTableFomaValores.addCell(cellvazio2);

            PdfPCell cellvazio3 = new PdfPCell(new Phrase( (isReseguro) ? " " : (Moeda.format(i25) + " " + contrato.getSigla()), fontCorpo));
            pTableFomaValores.addCell(cellPecentage3);
            pTableFomaValores.addCell(cellSelo);
            cellvazio3.setBorderWidthRight(1);
            cellvazio3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pTableFomaValores.addCell(cellvazio3);

            PdfPTable pTableTotalPagar = new PdfPTable(2);
            PdfPCell cellTituloTotalPagar = new PdfPCell(new Phrase("TOTAL A PAGAR " + contrato.getSigla(), fontCorpoN));
            cellTituloTotalPagar.setBorderWidthBottom(1f);
            cellTituloTotalPagar.setBorderWidthTop(0f);
            cellTituloTotalPagar.setBorderWidthRight(0f);
            cellTituloTotalPagar.setBorderWidthLeft(1f);

            PdfPCell cellValorPagar = new PdfPCell(new Phrase(contrato.getPremioLiquidoMoeda() + " " + contrato.getSigla(), fontCorpo));
            cellValorPagar.setBorderWidthBottom(1f);
            cellValorPagar.setBorderWidthTop(0f);
            cellValorPagar.setBorderWidthRight(1f);
            cellValorPagar.setBorderWidthLeft(0f);
            cellValorPagar.setHorizontalAlignment(Element.ALIGN_RIGHT);

            pTableTotalPagar.addCell(cellTituloTotalPagar);
            pTableTotalPagar.addCell(cellValorPagar);

            PdfPTable pTableSubTotal = new PdfPTable(new float[]{62, 38});
            PdfPCell cellTituloSubTotal = new PdfPCell(new Phrase("SubTotal", fontCorpoN));
            cellTituloSubTotal.setBorder(0);
            cellTituloSubTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell cellValorSubTotal = new PdfPCell(new Phrase(contrato.getPremioBrutoMoeda() + " " + contrato.getSigla(), fontCorpo));
            cellValorSubTotal.setBorderWidthBottom(0.5f);
            cellValorSubTotal.setBorderWidthTop(1f);
            cellValorSubTotal.setBorderWidthRight(0.5f);
            cellValorSubTotal.setBorderWidthLeft(0.5f);
            cellValorSubTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);

            pTableSubTotal.addCell(cellTituloSubTotal);
            pTableSubTotal.addCell(cellValorSubTotal);

            PdfPCell cellPagament1 = new PdfPCell(pTableSubTotal);
            cellPagament1.setBorder(0);
            PdfPCell cellPagament2 = new PdfPCell(pTableFomaValores);
            cellPagament2.setBorder(0);
            PdfPCell cellPagament3 = new PdfPCell(pTableTotalPagar);
            cellPagament3.setBorder(0);

            pTableFomaValoresPricipal.addCell(cellPagament1);
            pTableFomaValoresPricipal.addCell(cellPagament2);
            pTableFomaValoresPricipal.addCell(cellPagament3);

            PdfPCell cellPagamentPricipal1 = new PdfPCell(pTableFomaPagamentoPricipal);
            cellPagamentPricipal1.setBorder(0);
            PdfPCell cellPagamentPricipal2 = new PdfPCell(pTableFomaValoresPricipal);
            cellPagamentPricipal2.setBorder(0);

            pTablePrincipalPagamento.addCell(cellPagamentPricipal1);
            pTablePrincipalPagamento.addCell(cellPagamentPricipal2);

            PdfPTable pTableCambio = new PdfPTable(2);
            pTableCambio.setWidthPercentage(95);
            PdfPCell cellTituloCambio1 = new PdfPCell(new Phrase("Cambio", fontCorpoN));
            cellTituloCambio1.setBorder(0);
            cellTituloCambio1.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cellTituloCambio2 = new PdfPCell(new Phrase("Total Em Dobras", fontCorpoN));
            cellTituloCambio2.setBorder(0);
            cellTituloCambio2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cellValorCambio = new PdfPCell(new Phrase("1 " + contrato.getSigla() + "= " + Moeda.format(valorCompra(contrato.getDataRegistro(), contrato)) + " " + "STD", fontCorpo));
            cellValorCambio.setBorder(0);
            cellValorCambio.setHorizontalAlignment(Element.ALIGN_CENTER);
            
             DecimalFormat formato = new DecimalFormat("#.##"); 
             String numero = formato.format(Double.valueOf(contrato.getPremioLiquido()));
            double  premioLiquido = Double.valueOf((numero).replace(',', '.'));
            
//            System.err.println(taxa + "Taxa * Premio Liquido" + Double.valueOf(contrato.getPremioLiquido()));
//            System.err.println(taxa + "Taxa * Premio Liquido" + Double.toString(premioLiquido));
            
            PdfPCell cellValorDobras = new PdfPCell(new Phrase(Moeda.format((taxa * premioLiquido)) + " STD", fontCorpo));
            cellValorDobras.setBorder(0);
            cellValorDobras.setHorizontalAlignment(Element.ALIGN_CENTER);

            pTableCambio.addCell(cellTituloCambio1);
            pTableCambio.addCell(cellTituloCambio2);
            pTableCambio.addCell(cellValorCambio);
            pTableCambio.addCell(cellValorDobras);

            PdfPTable pTableData = new PdfPTable(1);
            pTableData.setWidthPercentage(95);
            Paragraph pData = new Paragraph();
            pData.add(new Phrase("Elaborado por ", fontCorpo));
            pData.add(new Phrase(interCodVendedor, fontCorpoN));
            pData.add(new Phrase(" Data ".toUpperCase() + sdf.format(new Date()), fontCorpo));
            PdfPCell cellData = new PdfPCell(pData);
            cellData.setBorder(0);
            cellData.setBorderWidthBottom(0.1f);
            cellData.setHorizontalAlignment(Element.ALIGN_CENTER);

            pTableData.addCell(cellData);

            Document documento = new Document();
            documento.setPageSize(PageSize.A4);
            documento.setMargins(20f, 20f, 70f, 5f);

            String f1 = (arquivo + "/" + user + "/Seguro " + nomeSeguro + "/");
            File f = new File(f1);
            String Ddata = sdf1.format(new Date());
            f.mkdirs();
            f = new File(f.getAbsoluteFile() + "/" + "Nota de Debito " + Ddata + ".pdf");

            reString = "../Documentos/" + user + "/Seguro " + nomeSeguro + "/" + "Nota de Debito " + Ddata + ".pdf";
            OutputStream outputStraem = new FileOutputStream(f);
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
            documento.add(pTableFatura);
            documento.add(pTableLinha);
            documento.add(pTableLinha);
            documento.add(pTableTitulo);
            documento.add(pTableClientePrincipal);
            documento.add(pTableNull);
            documento.add(TableRegistro);
            documento.add(pTablePrincipalPagamento);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableCambio);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableData);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableNull);
            documento.add(pTableLinha);
            documento.add(pTableLinha);
            documento.close();
            
            isReseguro = false;
//            PrintPdf printPdf = new PrintPdf(f.getAbsolutePath(), f.getAbsolutePath(), 0, 595f,842f,"\\\\JIGASOFTPC\\Hewlett-Packard HP LaserJet P2035",1); 
//            PrintPdf printPdf = new PrintPdf(f.getAbsolutePath(), f.getAbsolutePath(), 1, 595f,842f,"Enviar Para o OneNote 2013",1); 
//            printPdf.print();
            return reString;
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DocNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public DocNotaDebito() {
    }

    public static void main(String[] args) {
//        DocNotaDebito dr = new DocNotaDebito();
//        String[] Dados;
//        ArrayList<String[]> al = new ArrayList<>();
//
//        Dados = ("4;Seguros de Viagem;5000000;" + (5000000 * 4) + "").split(";");
//        al.add(Dados);
//
//        Dados = ("7;Seguros de Viagem;5000000;" + (5000000 * 7) + "").split(";");
//        al.add(Dados);
//
//        Dados = ("15;Seguros de Viagem;5000000;" + (5000000 * 15) + "").split(";");
//        al.add(Dados);
//
//        dr.docSeguros("Viagem", null, null, "88", null, al, new Contrato(), "ah", "STD", "", "numregistro");
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
    double taxa = 0;

    public double valorCompra(Date data, Contrato c) {
        ResultSet rs;

        if (data == null) {
            rs = Call.callTableFunction("PACK_CONTA.getTaxaDay", "*", OperacaoData.toSQLDate(new Date()), c.getMoeda());
        } else {
            rs = Call.callTableFunction("PACK_CONTA.getTaxaDay", "*", OperacaoData.toSQLDate(data), c.getMoeda());
        }

        Consumer<HashMap<String, Object>> act = (map) -> {
            taxa = Double.valueOf(toString(map.get("TX_VENDA")));
        };
        Call.forEchaResultSet(act, rs);

        return taxa;
    }

    public String toString(Object o) {
        return (o == null) ? "" : o.toString();
    }
}
