/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.showdata;

import bean.DataTableControl;
import conexao.Call;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ildo
 */

@ManagedBean
@ViewScoped 
public class ShowData
{
    private DataTableControl controlTable;
    private String sqlText;
    private ArrayList<String> entyName;
    private String seletedEntity;
            
    
    
    public ShowData()
    {
        ResultSet rs = Call.executeQuere("SELECT * FROM TABLE(FUNC_LOAD_TABLES)").getResultSet();
        this.controlTable = new DataTableControl("testResultSetPage_tabela", "dataTableControl.controlTable");
        this.controlTable.updFaces(FacesContext.getCurrentInstance());
        this.controlTable.prepareModel(rs, DataTableControl.ShowMode.SHOW, "TABELA");

        this.entyName = new ArrayList<>();
        entyName.add("Selecione ...");
        
        for (int i = 0; i<controlTable.countLines(); i++)
            this.entyName.add(controlTable.getRowMap(i).get("TABELA"));
    }
    
    public DataTableControl getControlTable() {
        return controlTable;
    }

    
    

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public ArrayList<String> getEntyName() {
        return entyName;
    }

    public void setEntyName(ArrayList<String> entyName) {
        this.entyName = entyName;
    }

    

    

    public String getSeletedEntity() {
        return seletedEntity;
    }

    public void setSeletedEntity(String seletedEntity) {
        if (!seletedEntity.equals("Selecione ..."))
        {
            ResultSet resultSet =  Call.executeQuere("SELECT * FROM "+seletedEntity).getResultSet();
            this.controlTable.updFaces(FacesContext.getCurrentInstance());
            this.controlTable.prepareModel(resultSet, DataTableControl.ShowMode.SHOWALL);
            this.seletedEntity = seletedEntity;
        }
    }
    
    public void executQuere ()
    {
        try
        {
            ResultSet resultSet = Call.executeQuere(sqlText).getResultSet();
            this.controlTable.updFaces(FacesContext.getCurrentInstance());
            this.controlTable.prepareModel(resultSet, DataTableControl.ShowMode.SHOWALL);
            
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
