package bean;

import Export.ExporOnlyViagemPdf;
import Export.ExportViagemSemanaExcel;
import Export.ExportViagemSemanaPdf;
import Export.GenericExcel;
import Export.GenericPDFs;
import dao.RecursosHumanosDao;
import dao.SeguroDao;
import dao.UtilitarioDao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import modelo.ComoBox;
import modelo.Relatorio;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author Helio
 */
@ViewScoped
@ManagedBean(name = "relatorio")
public class RelatorioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private DataTableControl resultado;
    private final UtilitarioDao utilitarioDao = new UtilitarioDao();
    private final RecursosHumanosDao rhd = new RecursosHumanosDao();
    private List<ComoBox> listaSeguros;
    private Relatorio relatorio = new Relatorio();
    private final SeguroDao seguroDao = new SeguroDao();
    private ResultSet rs;
    private String valorTotal;
    private String[] dataRemoved = new String[0];

    public RelatorioBean() {
        this.resultado = new DataTableControl("tabelaRelatorio", "relatorio.resultado");

    }

    @PostConstruct
    public void init() {

        listaSeguros = new ArrayList<>();
        listaSeguros = seguroDao.seguros();
    }

    public DataTableControl getResultado() {
        return resultado;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setResultado(DataTableControl resultado) {
        this.resultado = resultado;
    }

    public void pesquisaGenerica()//String funcao, String tipo, String campo, Object value)
    {
        this.resultado = new DataTableControl("tabelaRelatorio", "relatorio.resultado");
        switch (relatorio.getTipoRelatorio()) {
            case "Clientes":
                System.out.println("Relatório de clientes");
                if (relatorio.getSeguro().equals("0"))
                {
                    if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                    {
                        rs = utilitarioDao.relatorioCliente(null, relatorio.getDataInicio(), relatorio.getDataFim());
                        this.resultado.updFaces(FacesContext.getCurrentInstance());
                        this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "ID", "QUANT. CONTRATO");
                        
                        RequestContext.getCurrentInstance().execute("$('.firstField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("TOTAL PREMIO") +"')");
                        
                        dataRemoved = resultado.getListaValue().get(resultado.getListaValue().size()-1);
                                
                        resultado.getListaValue().remove(resultado.getListaValue().size()-1);
                        Validacao.atualizar("Rel", "relatorioContratoValorTotal");
                    }  
                }
                else 
                {
                    if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                    {
                        rs = utilitarioDao.relatorioCliente(relatorio.getSeguro(), relatorio.getDataInicio(), relatorio.getDataFim());
                        this.resultado.updFaces(FacesContext.getCurrentInstance());
                        this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "SEGURO FREQ", "ID", "QUANT. CONTRATO");
                        
                        RequestContext.getCurrentInstance().execute("$('.firstField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("TOTAL PREMIO") +"')");
                        
                        dataRemoved = resultado.getListaValue().get(resultado.getListaValue().size()-1);
                        
                        resultado.getListaValue().remove(resultado.getListaValue().size()-1);
                        Validacao.atualizar("Rel", "relatorioContratoValorTotal");
                    }
                }
               
                break;
            case "Seguros":
                System.out.println("Relatório de seguros");
                if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                {
                    rs = utilitarioDao.relatorioSeguro(relatorio.getDataInicio(), relatorio.getDataFim());
                    this.resultado.updFaces(FacesContext.getCurrentInstance());
                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.SHOWALL);
                    
                    RequestContext.getCurrentInstance().execute("$('.firstField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("PREMIO") +"')");
                    
                    dataRemoved = resultado.getListaValue().get(resultado.getListaValue().size()-1);
                    
                    resultado.getListaValue().remove(resultado.getListaValue().size()-1);
                    Validacao.atualizar("Rel", "relatorioContratoValorTotal");
                }
                break;
            case "Produção": // relatório de promoção. Se o seguro for selecionado invocar promoção tipo e ocultar a coluna do seguro selecionado
                if (relatorio.getSeguro().equals("0"))
                {
                    if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                    {
                        rs = utilitarioDao.relatorioPromocao(relatorio.getDataInicio(), relatorio.getDataFim());
                        this.resultado.updFaces(FacesContext.getCurrentInstance());
                        this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "MOEDA");
                        
                        RequestContext.getCurrentInstance().execute("$('.firstField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("PREMIO") +"')");
                        RequestContext.getCurrentInstance().execute("$('.secondField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("IMPOSTO CONSUMO") +"')");
                        RequestContext.getCurrentInstance().execute("$('.thirdField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("IMPOSTO SELO") +"')");
                        RequestContext.getCurrentInstance().execute("$('.fourthField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("FGA") +"')");
                        RequestContext.getCurrentInstance().execute("$('.fifthField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("VALOR TOTAL") +"')");
                        
                        dataRemoved = resultado.getListaValue().get(resultado.getListaValue().size()-1);
                        
                        resultado.getListaValue().remove(resultado.getListaValue().size()-1);
                        Validacao.atualizar("Rel", "relatorioContratoValorTotal");
                        this.resultado.setWidthColumn("MOEDA", 450);
                    }
                }
                else 
                {
                    if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                    {
                        rs = utilitarioDao.relatorioPromocaoTipo(relatorio.getDataInicio(), relatorio.getDataFim(), Integer.valueOf(relatorio.getSeguro()), relatorio.getAgrupamento());
                        this.resultado.updFaces(FacesContext.getCurrentInstance());
                        this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "SEGURO");
                        
                        RequestContext.getCurrentInstance().execute("$('.firstField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("PREMIO") +"')");
                        RequestContext.getCurrentInstance().execute("$('.secondField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("IMPOSTO CONSUMO") +"')");
                        RequestContext.getCurrentInstance().execute("$('.thirdField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("IMPOSTO SELO") +"')");
                        RequestContext.getCurrentInstance().execute("$('.fourthField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("FGA") +"')");
                        RequestContext.getCurrentInstance().execute("$('.fifthField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("VALOR TOTAL") +"')");
                        
                        dataRemoved = resultado.getListaValue().get(resultado.getListaValue().size()-1);
                        
                        resultado.getListaValue().remove(resultado.getListaValue().size()-1);
                        Validacao.atualizar("Rel", "relatorioContratoValorTotal");
                        this.resultado.setWidthColumn("MOEDA", 450);
                    } 
                }
                break;
            case "Crescente":
                if (relatorio.getSeguro().equals("0"))
                {
                    if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                    {
                        rs = utilitarioDao.relatorioCrescente(relatorio.getDataInicio(), relatorio.getDataFim(), relatorio.getAgrupamento());
                        this.resultado.updFaces(FacesContext.getCurrentInstance());
                        this.resultado.prepareModel(rs, DataTableControl.ShowMode.SHOWALL);
                        this.renameColumns("Crescente");
                    } 
                } 
                else 
                {
                    if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                    {
                        rs = utilitarioDao.relatorioCrescenteSeguro(relatorio.getDataInicio(), relatorio.getDataFim(),
                        relatorio.getAgrupamento(), Integer.valueOf(relatorio.getSeguro()));
                        this.resultado.updFaces(FacesContext.getCurrentInstance());
                        this.resultado.prepareModel(rs, DataTableControl.ShowMode.SHOWALL);
                        this.renameColumns("Crescente");
                    }
                }
                break;
            case "Mapa de Provisão":
                if(relatorio.getDataInicio() != null && relatorio.getDataFim() != null)
                {
                    rs = utilitarioDao.relatorioMapaProvisao(relatorio);
                    this.resultado.updFaces(FacesContext.getCurrentInstance());
                    this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "ID");
                    RequestContext.getCurrentInstance().execute("$('.firstField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("PREMIO LIQUIDO") +"')");
                    RequestContext.getCurrentInstance().execute("$('.secondField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("PROVISAO 10%") +"')");
                    RequestContext.getCurrentInstance().execute("$('.thirdField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("PROVISAO 30%") +"')");
                    RequestContext.getCurrentInstance().execute("$('.fourthField').html('"+resultado.getRowMap(resultado.getListaValue().size() - 1).get("TOTAL") +"')");
                    
                    dataRemoved = resultado.getListaValue().get(resultado.getListaValue().size()-1);
                    
                    resultado.getListaValue().remove(resultado.getListaValue().size()-1);
                    Validacao.atualizar("Rel", "relatorioContratoValorTotal");
                }
                break;
        }

    }

    private void renameColumns(String tipo) {
        switch (tipo) {
            case "Crescente":
                this.resultado.renameColumn("TOTAL CLIETES", "CLIENTES");
                this.resultado.renameColumn("TOTAL CONTRATOS", "CONTRATOS");
                this.resultado.renameColumn("SEGURO EPOCA", "ÉPOCA DO SEGURO");
                this.resultado.renameColumn("EPOCAS PASSADA", "ÉPOCAS PASSADAS");
                this.resultado.renameColumn("NOVOS ADERENTE", "NOVOS ADERENTES");
                this.resultado.renameColumn("PREMIO EPOCA", "ÉPOCA DO PRÉMIO");
                this.resultado.renameColumn("PREMIO", "PRÉMIO");
                break;
            case "Produção":
                this.resultado.renameColumn("NUM APOLICE", "Apólice");
                break;

        }
    }

    public Relatorio getRelatorio() {
        return (relatorio == null) ? relatorio = new Relatorio() : relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public void teste() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        DataTable dataTable = (DataTable) viewRoot.findComponent("Rel:tabelaRelatorio");
        dataTable.setValue(null);
        dataTable.setColumns(null);
        Validacao.AtualizarCompoente("Rel", "tabelaRelatorio");
        String tipoRelatorio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("relatorio");
        relatorio.setTipoRelatorio(tipoRelatorio);
    }

    public List<ComoBox> getListaSeguros() {
        return listaSeguros;
    }

    public void printRelatorio(int i) {
        if (i == 3) {
            conRelatorio(2);
        } else {
            conRelatorio(1);
        }
    }

    private void conRelatorio(int i) {
        
        if(dataRemoved.length > 0)  { resultado.getListaValue().add(dataRemoved); }
        dataRemoved = new String[0];
        
        if (i == 1) {
            System.err.println(relatorio.getSeguro()/*.equals("3")*/ +" "+ relatorio.getSeguro()/*.equals("0")*/);
            GenericPDFs.dI = relatorio.getDataInicio();
            GenericPDFs.dF = relatorio.getDataFim();
            switch (relatorio.getTipoRelatorio()) {
                case "Produção":
                    if (relatorio.getSeguro().equals("3") || relatorio.getSeguro().equals("0")) {
                        ExportViagemSemanaPdf.criarDoc(relatorio.getDataInicio(), relatorio.getDataFim(), SessionUtil.getUserlogado().getNomeAcesso(), SessionUtil.getUserlogado().getNomeCompleto());
                    }  
                    if (!relatorio.getSeguro().equals("3")) {
                        GenericPDFs.no = true;
                        GenericPDFs.nomeNo = "Seguro de Viagem";
                        GenericPDFs.removeItem = new int[]{0,1};
                        GenericPDFs.paramFilterOculta = 2;
                        
                         GenericPDFs.renameItem.put(5, "prémio (STD)");
                         GenericPDFs.alignment.put(3, GenericPDFs.Alignment.RIGHT);
                         GenericPDFs.renameItem.put(6, "inposto consumo (STD)");
                         GenericPDFs.alignment.put(4, GenericPDFs.Alignment.RIGHT);
                         GenericPDFs.renameItem.put(7, "inposto selo (STD)");
                         GenericPDFs.alignment.put(5, GenericPDFs.Alignment.RIGHT);
                         GenericPDFs.renameItem.put(8, "FGA (STD)");
                         GenericPDFs.alignment.put(6, GenericPDFs.Alignment.RIGHT);
                         GenericPDFs.renameItem.put(9, "Valor Total (STD)");
                         GenericPDFs.alignment.put(7, GenericPDFs.Alignment.RIGHT);
                        
                        GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, GenericPDFs.OrientacaoPagina.HORIZONTAL, 4);
                    }   break;
                case "Mapa de Provisão":
                    if (relatorio.getAgrupamento() != 3) {
                        GenericPDFs.no = true;
                        GenericPDFs.nomeNo = "Seguro de Viagem";
                        GenericPDFs.paramFilterOculta = 8;
                        
                        GenericPDFs.alignment.put(0, GenericPDFs.Alignment.CENTER);
                        GenericPDFs.renameItem.put(2, "Prémio liquido (STD)");
                        GenericPDFs.alignment.put(2, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.renameItem.put(5, "provisão 10% (STD)");
                        GenericPDFs.alignment.put(5, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.renameItem.put(6, "provisão 30% (STD)");
                        GenericPDFs.alignment.put(6, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.renameItem.put(7, "Total (STD)");
                        GenericPDFs.alignment.put(7, GenericPDFs.Alignment.RIGHT);

                        GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, GenericPDFs.OrientacaoPagina.HORIZONTAL, 8);
                }   break;
                default:
                    if (relatorio.getTipoRelatorio().equals("Clientes")) {
                        GenericPDFs.renameItem.put(3, "TOTAL PREMIO (STD)");
                        GenericPDFs.alignment.put(3, GenericPDFs.Alignment.RIGHT);
                    }
                    if (relatorio.getTipoRelatorio().equals("Seguros")) {
                        GenericPDFs.renameItem.put(3, "PREMIO (STD)");
                        GenericPDFs.alignment.put(3, GenericPDFs.Alignment.RIGHT);
                    }
                    if (relatorio.getTipoRelatorio().equals("Crescente")) {
                        GenericPDFs.alignment.put(0, GenericPDFs.Alignment.CENTER);
                        GenericPDFs.renameItem.put(5, "PREMIO (STD)");
                        GenericPDFs.alignment.put(5, GenericPDFs.Alignment.RIGHT);
                        GenericPDFs.renameItem.put(6, "Total (STD)");
                        GenericPDFs.alignment.put(6, GenericPDFs.Alignment.RIGHT);
                    }
                    GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1);
                    break;
            }
        } else {
            GenericExcel.dI = relatorio.getDataInicio();
            GenericExcel.dF = relatorio.getDataFim();
            switch (relatorio.getTipoRelatorio()) {
                case "Produção":                
                    if (relatorio.getSeguro().equals("3") || relatorio.getSeguro().equals("0")) {
                        ExportViagemSemanaExcel.criarDoc(relatorio.getDataInicio(), relatorio.getDataFim(), SessionUtil.getUserlogado().getNomeAcesso(), SessionUtil.getUserlogado().getNomeCompleto());
                    } 
                    if (!relatorio.getSeguro().equals("3")) {
                        GenericExcel.no = true;
                        GenericExcel.nomeNo = "Seguro de Viagem";
                        GenericExcel.removeItem = new int[]{0, 1};
                        GenericExcel.paramFilterOculta = 2;
                        
                        GenericExcel.renameItem.put(5, "prémio (STD)");
                        GenericExcel.alignment.put(3, GenericExcel.Alignment.RIGHT);
                        GenericExcel.renameItem.put(6, "inposto consumo (STD)");
                        GenericExcel.alignment.put(4, GenericExcel.Alignment.RIGHT);
                        GenericExcel.renameItem.put(7, "inposto selo (STD)");
                        GenericExcel.alignment.put(5, GenericExcel.Alignment.RIGHT);
                        GenericExcel.renameItem.put(8, "FGA (STD)");
                        GenericExcel.alignment.put(6, GenericExcel.Alignment.RIGHT);
                        GenericExcel.renameItem.put(9, "Valor Total (STD)");
                        GenericExcel.alignment.put(7, GenericExcel.Alignment.RIGHT);
                         
                        GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, 4);
                    }
                    break;
                case "Mapa de Provisão":
                    GenericExcel.no = true;
                    GenericExcel.nomeNo = "Seguro de Viagem";
                    GenericExcel.paramFilterOculta = 8;
                    
                    GenericExcel.alignment.put(0, GenericExcel.Alignment.CENTER);
                    GenericExcel.renameItem.put(2, "Prémio liquido (STD)");
                    GenericExcel.alignment.put(2, GenericExcel.Alignment.RIGHT);
                    GenericExcel.renameItem.put(5, "provisão 10% (STD)");
                    GenericExcel.alignment.put(5, GenericExcel.Alignment.RIGHT);
                    GenericExcel.renameItem.put(6, "provisão 30% (STD)");
                    GenericExcel.alignment.put(6, GenericExcel.Alignment.RIGHT);
                    GenericExcel.renameItem.put(7, "Total (STD)");
                    GenericExcel.alignment.put(7, GenericExcel.Alignment.RIGHT);

                    GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, 8);
                    break;
                default:
                    if (relatorio.getTipoRelatorio().equals("Clientes")) {
                        GenericExcel.renameItem.put(3, "TOTAL PREMIO (STD)");
                        GenericExcel.alignment.put(3, GenericExcel.Alignment.RIGHT);
                    }
                    if (relatorio.getTipoRelatorio().equals("Seguros")) {
                        GenericExcel.renameItem.put(3, "PREMIO (STD)");
                        GenericExcel.alignment.put(3, GenericExcel.Alignment.RIGHT);
                    }
                    if (relatorio.getTipoRelatorio().equals("Crescente")) {
                        GenericExcel.alignment.put(0, GenericExcel.Alignment.CENTER);
                        GenericExcel.renameItem.put(5, "PREMIO (STD)");
                        GenericExcel.alignment.put(5, GenericExcel.Alignment.RIGHT);
                        GenericExcel.renameItem.put(6, "Total (STD)");
                        GenericExcel.alignment.put(6, GenericExcel.Alignment.RIGHT);
                    }
                    GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de " + relatorio.getTipoRelatorio(), "Relatório de " + relatorio.getTipoRelatorio(), resultado, -1);
                    break;
            }

        }
    }

    private void growingReportTotalValues()
    {
        int[] total = new int[4];
        
        for (int i = 0;i<resultado.getListaValue().size(); i++) 
        {
            total[0] +=Integer.valueOf(resultado.getRowMap(i).get("NOVOS ADERENTE"));
            total[1] +=Integer.valueOf(resultado.getRowMap(i).get("TODOS ADERENTES"));
            total[2] +=Integer.valueOf(resultado.getRowMap(i).get("NOVOS CONTRATOS"));
            total[3] +=Integer.valueOf(resultado.getRowMap(i).get("TODOS CONTRATOS"));
        }
        
        RequestContext.getCurrentInstance().execute("$('.firstField').html('"+total[0] +"')");
        RequestContext.getCurrentInstance().execute("$('.secondField').html('"+total[1] +"')");
        RequestContext.getCurrentInstance().execute("$('.thirdField').html('"+total[2] +"')");
        RequestContext.getCurrentInstance().execute("$('.fourthField').html('"+total[3] +"')");
    }

}
