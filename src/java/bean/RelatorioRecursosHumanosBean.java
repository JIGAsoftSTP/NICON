
package bean;

import Export.GenericExcel;
import Export.GenericPDFs;
import dao.RecursosHumanosDao;
import java.io.Serializable;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Relatorio;
import sessao.SessionUtil;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean(name = "recursosHumanos")
@ViewScoped
public class RelatorioRecursosHumanosBean implements Serializable
{

    private Relatorio relatorio = new Relatorio();
    private DataTableControl recursosHumanos;
    private final RecursosHumanosDao rhd = new RecursosHumanosDao();
    private ResultSet rs;
    private String tipoRelatorio="Consumíveis";
    
       public RelatorioRecursosHumanosBean()
       {
            this.recursosHumanos = new DataTableControl("humanResourcesReportTable", "recursosHumanos.recursosHumanos"); 
            
            rs = this.rhd.relatorioConsumivel(relatorio);
            this.recursosHumanos.updFaces(FacesContext.getCurrentInstance());
            this.recursosHumanos.prepareModel(rs, DataTableControl.ShowMode.HIDE,"ID", "OBSERVACAO");
            this.recursosHumanos.renameColumn("TIPO MOVIMENTACAO", "TIPO MOVIMENTAÇÃO");
            this.recursosHumanos.renameColumn("FUNCIONARIO", "FUNCIONÁRIO");
       }
        
    public void pesquisaRecursosHumanos()
    {
        System.out.println("tipo de relatorio "+tipoRelatorio);
        switch(tipoRelatorio)
        {
               case "Consumíveis":
                rs = this.rhd.relatorioConsumivel(relatorio);
                this.recursosHumanos.updFaces(FacesContext.getCurrentInstance());
                this.recursosHumanos.prepareModel(rs, DataTableControl.ShowMode.HIDE,"ID", "OBSERVACAO");
                this.recursosHumanos.renameColumn("TIPO MOVIMENTACAO", "TIPO MOVIMENTAÇÃO");
                this.recursosHumanos.renameColumn("FUNCIONARIO", "FUNCIONÁRIO");
                 validacao.Validacao.atualizar("humanResourcesReportForm", "humanResourcesReportTable");
                break;
            case "Pessoal":
                rs = this.rhd.relatorioFuncionario(relatorio);
                this.recursosHumanos.updFaces(FacesContext.getCurrentInstance());
                this.recursosHumanos.prepareModel(rs, DataTableControl.ShowMode.HIDE,"ID");
                this.recursosHumanos.renameColumn("FUNCAO", "FUNÇÃO");
                this.recursosHumanos.renameColumn("CODIGO", "CÓDIGO");
                 validacao.Validacao.atualizar("humanResourcesReportForm", "humanResourcesReportTable");
                break;
        }
    }

    public Relatorio getRelatorio() {
        return (relatorio == null)? relatorio = new Relatorio() : relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public DataTableControl getRecursosHumanos() {
        return (recursosHumanos == null ) ? this.recursosHumanos = new DataTableControl("humanResourcesReportTable", "recursosHumanos.recursosHumanos"): recursosHumanos;
    }
    
    
    public void humanResourcesTypeReport()
    {
        tipoRelatorio= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("relatorio");
        System.out.println("relatorio "+tipoRelatorio);
    }
    
    public void reportExport(int i)
    {
        if(i == 1)
        { GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de "+tipoRelatorio, "Relatório de "+tipoRelatorio, recursosHumanos, GenericPDFs.OrientacaoPagina.HORIZONTAL,-1); }
        else
        { GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de "+tipoRelatorio, "Relatório de "+tipoRelatorio, recursosHumanos,-1); }
    }
}
