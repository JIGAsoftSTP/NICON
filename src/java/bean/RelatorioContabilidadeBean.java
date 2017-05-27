package bean;

import Export.ExporOnlyViagemExcel;
import Export.ExporOnlyViagemPdf;
import Export.GenericExcel;
import Export.GenericPDFs;
import Export.ReciboPagamento;
import dao.ContabilidadeDao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import modelo.ComoBox;
import modelo.Relatorio;
import modelo.Taxa;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean
@ViewScoped
public class RelatorioContabilidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private DataTableControl resultado, taxaProducao, taxaSalario, securityTax, mpaResumoTaxa;
    private final ContabilidadeDao cd = new ContabilidadeDao();
    private Taxa taxa = new Taxa();
    private Relatorio relatorio = new Relatorio();
    private String tipoRelatorio = "Pagamentos";
    private final List<Taxa> listaResumoTaxa = new ArrayList<>();
    private ResultSet rs;
    private List<ComoBox> list = new ArrayList<>();
    private List<Relatorio> listaTaxaSalario = new ArrayList<>();
    private final List<Relatorio> listaTaxaSegurancaSocial = new ArrayList<>();
    private String valorTotal;

    public RelatorioContabilidadeBean() {
        resultado = new DataTableControl("relatorioContabilidadeTabela", "relatorioContabilidadeBean.resultado");
        taxaProducao = new DataTableControl("contabilidadeTabelaTaxaProducao", "RelatorioContabilidadeBean.taxaProducao");
        taxaSalario = new DataTableControl("contabilidadeTabelaTaxaSalario", "RelatorioContabilidadeBean.taxaSalario");
        securityTax = new DataTableControl("contabilidadeTabelaSecurityTax", "RelatorioContabilidadeBean.securityTax");
        mpaResumoTaxa = new DataTableControl("contabilidadeTabelaMapaResumoTaxa", "RelatorioContabilidadeBean.mpaResumoTaxa");
    }

    @PostConstruct
    public void init() {
        
        list.add(new ComoBox("BENEFICIARIO", "Beneficiário"));
        list.add(new ComoBox("BANCO", "Conta"));
        list.add(new ComoBox("PAGAMENTO", "Conta de Pagamento"));
        list.add(new ComoBox("BANCO", "Banco"));
    }

    public Relatorio getRelatorio() {
        return (relatorio == null) ? relatorio = new Relatorio() : relatorio;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public void relatorioSelecionado() {
        tipoRelatorio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("relatorio");
        relatorio.setTipoRelatorio(tipoRelatorio);

    }

    public DataTableControl getResultado() {
        return resultado;
    }

    public List<ComoBox> getList() {
        return list;
    }

    public void searchReport() {
        
        list.clear();// limpa tudo na lista 
        switch (relatorio.getTipoRelatorio()) {
            case "Pagamentos":
                if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                {
                    rs = cd.relatorioPagamento(relatorio);
                    this.resultado.updFaces(FacesContext.getCurrentInstance());
                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE,"CODIGO","DESCRICAO");
                    this.resultado.renameColumn("DESCRICAO", "DESCRIÇÃO");
                    this.resultado.renameColumn("PAGAMENTO", "CONTA PAGAMENTO");
                    this.accountingReportTotalValues(relatorio.getTipoRelatorio());
                }
                list.add(new ComoBox("BENEFICIARIO", "Beneficiário"));
                list.add(new ComoBox("BANCO", "Banco"));
                list.add(new ComoBox("PAGAMENTO", "Conta de Pagamento"));
                break;
            case "Recebimentos":
                list.add(new ComoBox("FORMA PAGAMENTO", "Forma de pagamento"));
                list.add(new ComoBox("BANCO", "Banco"));
                if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                {
                    rs = cd.relatorioRecebimento(relatorio);
                    this.resultado.updFaces(FacesContext.getCurrentInstance());
                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "CODIGO", "DESCRICAO");
                    resultado.renameColumn("FORMA PAGAMENTO", "FORMA PAG.");
                    resultado.renameColumn("BENEFICIARIO", "BENEFICIÁRIO");
                    this.accountingReportTotalValues(relatorio.getTipoRelatorio());
                }    
                break;
            case "Mapa de Produção":
                list = ComoBox.loadCombo("T_CARACTERISTICA", "CARACT_ID", "CARACT_DESC");
                if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                {
                     rs = cd.relatorioMapaproducao(relatorio);
                    this.resultado.updFaces(FacesContext.getCurrentInstance());
                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE,
                            "DATA", "DIAS", "LEN", "RECIBO", "COMISAO", "IMP_CONSUMO", "IMP_SELO", "CONSUMO", "SELO", "TOTAL", "NET OUT", "DEBITO");
                    this.accountingReportTotalValues(relatorio.getTipoRelatorio()); 
                    this.resultado.renameColumn("PREMIO", "PRÉMIO");
                    this.resultado.reloadModel();  
                }
                break;
            case "Taxas":
                relatorio.setCampoPesquisa("");
                rs = cd.relatoriotaxaProducao(relatorio);
                this.taxaProducao.updFaces(FacesContext.getCurrentInstance());
                this.taxaProducao.prepareModel(rs, DataTableControl.ShowMode.SHOWALL);
                cd.relatoriotaxa(relatorio, listaTaxaSalario, listaTaxaSegurancaSocial);
                mapaResumoTaxa();
                break;
            case "Balancete":
                list.add(new ComoBox("CONTA", "Conta"));
                list.add(new ComoBox("DESIGNACAO", "Designação"));
                if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                {
                    rs = cd.relatorioBalancete(relatorio);
                    this.resultado.updFaces(FacesContext.getCurrentInstance());
                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.SHOWALL);
                    if(relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals(""))
                    {

                        RequestContext.getCurrentInstance().execute("balanceteTotaisBalanco('"+0+"','"+0+"','"+0+"','"+0+"')");
                        RequestContext.getCurrentInstance().execute("balanceteTotaisResultado('"+0+"','"+0+"','"+0+"','"+0+"')");
                        RequestContext.getCurrentInstance().execute("balanceteTotaisBalancete('"+0+"','"+0+"','"+0+"','"+0+"')");
                        this.resultado.renameColumn("CEDITO", "CRÉDITO");
                    }
                    else
                    {
                        RequestContext.getCurrentInstance().execute("balanceteTotaisBalanco('"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 3).get("DEBITO") : 0)+"','"+
                            ((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 3).get("CEDITO") : 0)+"','"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 3).get("DEVEDOR") : 0)+"','"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 3).get("CREDOR") : 0)+"')");

                        RequestContext.getCurrentInstance().execute("balanceteTotaisResultado('"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 2).get("DEBITO") : 0)+"','"+
                                ((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 2).get("CEDITO") : 0)+"','"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 2).get("DEVEDOR") : 0)+"','"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 2).get("CREDOR") : 0)+"')");

                        RequestContext.getCurrentInstance().execute("balanceteTotaisBalancete('"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 1).get("DEBITO") : 0)+"','"+
                                ((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 1).get("CEDITO") : 0)+"','"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 1).get("DEVEDOR") : 0)+"','"+((resultado.getListaValue().size()>0)? resultado.getRowMap(resultado.getListaValue().size() - 1).get("CREDOR") : 0)+"')");
                        this.resultado.renameColumn("CEDITO", "CRÉDITO");
                        
                    }
                }
                break;
            case "Jornal":
                list.add(new ComoBox("CONTA", "Conta"));
                if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                {
                    rs = cd.relatorioContas(relatorio);
                    this.resultado.updFaces(FacesContext.getCurrentInstance());
                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "ID","VALOR","MOVIMENTACAO", "REGISTRO",
                            "OPERACAO", "OP", "TP MOV", "ID CONTA", "DATA_SF", "REGISTRO_SF", "VALOR_SF", "VALOR_STD_SF", "VALOR STD");
//                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "DATA","DOC","CONTA", "DEBITO", "CREDITO"
                    this.resultado.renameColumn("DEBITO", "DÉBITO");
                    this.resultado.renameColumn("CREDITO", "CRÉDITO");
//                    this.accountingReportTotalValues(relatorio.getTipoRelatorio());
                }
        }
        Validacao.atualizar("contabilidadeRelatorioForm", "relatorioContabilidadeTabela",
                "relatorioContabilidadeCampoPesquisa", "contabilidadeTabelaTaxaProducao", 
            "contabilidadeTabelaTaxaSalario", "contabilidadeTabelaSecurityTax", "contabilidadeTabelaMapaResumoTaxa");
    }

    private void mapaResumoTaxa() {
        this.listaResumoTaxa.clear();
        if (this.taxaProducao.getListaValue().size() > 0) {
            taxa.setFga(this.taxaProducao.getRowMap(this.taxaProducao.getListaValue().size() - 1).get("FGA"));
            taxa.setConsumo(this.taxaProducao.getRowMap(this.taxaProducao.getListaValue().size() - 1).get("CONSUMO"));
            taxa.setSelo(this.taxaProducao.getRowMap(this.taxaProducao.getListaValue().size() - 1).get("SELO"));
            if(listaTaxaSalario.isEmpty() )
                 taxa.setIrs("");
            else 
                taxa.setIrs(listaTaxaSalario.get(listaTaxaSalario.size()-1).getValor()); 
            if(listaTaxaSegurancaSocial.isEmpty())
                 taxa.setSegSocial("");
            else 
                 taxa.setSegSocial(listaTaxaSegurancaSocial.get(listaTaxaSegurancaSocial.size()-1).getValor());

            this.listaResumoTaxa.add(taxa);
        }
    }

    public List<Relatorio> getListaTaxaSalario() {
        return listaTaxaSalario;
    }

    public List<Relatorio> getListaTaxaSegurancaSocial() {
        return listaTaxaSegurancaSocial;
    }

    public List<Taxa> getListaResumoTaxa() {
        return listaResumoTaxa;
    }

    public void exportRepor(int i) {
        if (null != relatorio.getTipoRelatorio()) switch (relatorio.getTipoRelatorio()) {
            case "Mapa de Produção":
                if (i == 1) {
                    if (!relatorio.getCampoPesquisa().equals("3")) {
                        GenericPDFs.dI = relatorio.getDataInicio();
                        GenericPDFs.dF = relatorio.getDataFim();
                        GenericPDFs.paramFilterOculta = 1;
                        GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), relatorio.getTipoRelatorio(), relatorio.getTipoRelatorio(), resultado, GenericPDFs.OrientacaoPagina.HORIZONTAL, 1);
                    } else {
                        ExporOnlyViagemPdf eovp = new ExporOnlyViagemPdf();
                        eovp.criarDoc(SessionUtil.getUserlogado().getNomeAcesso(), relatorio.getDataInicio(), relatorio.getDataFim());
                    }
                } else {
                   if (!relatorio.getCampoPesquisa().equals("3")) {
                    GenericExcel.dI = relatorio.getDataInicio();
                    GenericExcel.dF = relatorio.getDataFim();
                    GenericExcel.paramFilterOculta = 1;
                    GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), relatorio.getTipoRelatorio(), relatorio.getTipoRelatorio(), resultado, 1);
                   }
                   else {
                       ExporOnlyViagemExcel eove = new ExporOnlyViagemExcel();
                       eove.criarDoc(SessionUtil.getUserlogado().getNomeAcesso(), relatorio.getDataInicio(), relatorio.getDataFim());
                   }
                }   break;
            case "Taxas":
                GenericPDFs.dI = relatorio.getDataInicio();
                GenericPDFs.dF = relatorio.getDataFim();
                GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), relatorio.getTipoRelatorio(), relatorio.getTipoRelatorio(), taxaProducao, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1);
                break;
            default:
                if (i == 1) {
                    if(relatorio.getTipoRelatorio().equals("Pagamentos"))
                    { 
                        rs = cd.relatorioPagamento(relatorio);
                        this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "CODIGO","PAGAMENTO"); 
                        this.resultado.renameColumn("DESCRICAO", "DESCRIÇÃO");
                    }
                    GenericPDFs.dI = relatorio.getDataInicio();
                    GenericPDFs.dF = relatorio.getDataFim();
                    
                    if( relatorio.getTipoRelatorio().equals("Balancete") )
                    {
                        GenericPDFs.alignment.put(0, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.alignment.put(2, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.alignment.put(3, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.alignment.put(4, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.alignment.put(5, GenericPDFs.Alignment.RIGHT);
                    }
                    
                    if( relatorio.getTipoRelatorio().equals("Jornal") )
                    {
                        this.resultado.selectShow(DataTableControl.ShowMode.SHOW, "OBSERVACAO");
                        this.resultado.renameColumn("OBSERVACAO", "OBSERVAÇÃO");
                        this.resultado.reloadModel();
                        
                        GenericPDFs.arrValoresTotal = new int[]{3,4};
                        GenericPDFs.alignment.put(3, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.alignment.put(4, GenericPDFs.Alignment.RIGHT);
                    }
                    GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, (relatorio.getTipoRelatorio().equals("Balancete") ? GenericPDFs.OrientacaoPagina.VERTICAL : GenericPDFs.OrientacaoPagina.HORIZONTAL), -1);
                } else {
                    
                    if( relatorio.getTipoRelatorio().equals("Jornal") )
                    {
                        this.resultado.selectShow(DataTableControl.ShowMode.SHOW, "OBSERVACAO");
                        this.resultado.renameColumn("OBSERVACAO", "OBSERVAÇÃO");
                        this.resultado.reloadModel();
                        GenericExcel.arrValoresTotal = new int[]{3,4};
                        GenericExcel.alignment.put(3, GenericExcel.Alignment.RIGHT);
                        GenericExcel.alignment.put(4, GenericExcel.Alignment.RIGHT);
                    }
                    
                    if( relatorio.getTipoRelatorio().equals("Balancete") )
                    {
                        GenericExcel.alignment.put(0, GenericExcel.Alignment.RIGHT);
                        GenericExcel.alignment.put(2, GenericExcel.Alignment.RIGHT);
                        GenericExcel.alignment.put(3, GenericExcel.Alignment.RIGHT);
                        GenericExcel.alignment.put(4, GenericExcel.Alignment.RIGHT);
                        GenericExcel.alignment.put(5, GenericExcel.Alignment.RIGHT);
                    }
                    GenericExcel.dI = relatorio.getDataInicio();
                    GenericExcel.dF = relatorio.getDataFim();
                    GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, -1);
            }   break;
        }
    }
//
    public void impressaoRecebimento() {
        String idPrestacao = null;
        ReciboPagamento rp = new ReciboPagamento();
        String ret = rp.criarDoc(Integer.valueOf(idPrestacao), SessionUtil.getUserlogado().getNomeAcesso());
        RequestContext.getCurrentInstance().execute("openAllDocument('" + ret + "')");
    }
    
    private void accountingReportTotalValues(String report)
    {
        double value = 0, value2 = 0;
        int finish = 0; 
        String []formatedValue;
        String []total = new String[5];
        String currency ="";
        String result = "";
    
        switch(report)
        {
            case "Contas":
                for(int i = 0;i<resultado.getListaValue().size();i++)
                {
                    value += Double.valueOf(resultado.getRowMap(i).get("VALOR"));
                }
                RequestContext.getCurrentInstance().execute("$('.firstField').html('"+Moeda.format(value)+"')");
                break;
            case "Pagamentos" : case "Recebimentos":
                if(resultado.getListaValue().size() >0)
                {
                    if(resultado.getRowMap(resultado.getListaValue().size()-1).get("DATA").equalsIgnoreCase("total"))
                       finish = resultado.getListaValue().size()-1;
                    else
                        finish = resultado.getListaValue().size();
                        
                        for(int i=0;i<finish;i++) 
                        {
                            result = resultado.getRowMap(i).get("DEBITO"); 
                            value += Validacao.unformatValue(result);
                            value2 += Validacao.unformatValue(resultado.getRowMap(i).get("CREDITO"));
                        }
//                        resultado.getListaValue().
                        result = Moeda.format(value);
                        RequestContext.getCurrentInstance().execute("$('.firstField').html('Valor Débito '"+result+"' Valor Crédito '"+Moeda.format(value2)+")"); 
                }
                break;
            case "Mapa de Produção":
                    double std = 0, usd = 0, eur = 0, premio = 0;
                    if(relatorio.getCampoPesquisa().equals("3"))
                    {
                        for(int i = 0;i<resultado.getListaValue().size(); i++) 
                        {
                            premio += Double.valueOf(resultado.getRowMap(i).get("PREMIO"));  
                        }
                        RequestContext.getCurrentInstance().execute("mapaProducaoTotais('"+Moeda.format(std)+"','"+Moeda.format(usd)+"','"+Moeda.format(eur)+"','"+relatorio.getCampoPesquisa()+"','"+Moeda.format(premio)+"')");
                    }
                    else
                    {
                        
                        if(resultado.getListaValue().size() > 0)
                        {
                             if(relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals(""))
                                   finish = resultado.getListaValue().size();
                             else
                             {
                                if(relatorio.getCampoPesquisa().equals("1") || !relatorio.getCampoPesquisa().equals("2"))  
                                    finish =  resultado.getListaValue().size()-2;
                                else
                                    finish = resultado.getListaValue().size()-8;
                             }     
                            for (int i = 0;i<finish; i++) 
                            {
                                result = resultado.getRowMap(i).get("STD");  
                                std += Validacao.unformatValue(result);
                                result = resultado.getRowMap(i).get("USD");  
                                usd += Validacao.unformatValue(result);
                                result = resultado.getRowMap(i).get("EUR");  
                                eur += Validacao.unformatValue(result);
                            }
                        }
                        RequestContext.getCurrentInstance().execute("mapaProducaoTotais('"+Moeda.format(std)+"','"+Moeda.format(usd)+"','"+Moeda.format(eur)+"','"+relatorio.getCampoPesquisa()+"','"+null+"')");
                  }
                  break;
        }
    }
       
}
