/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Export.GenericExcel;
import Export.GenericPDFs;
import bean.DataTableControl;
import conexao.Call;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import modelo.ContaBanco;
import sessao.SessionUtil;

/**
 *
 * @author ahmedjorge
 */
public class ContaBancoDao {
    public String[] regAccount(ContaBanco cb){
       Object result =  Call.callSampleFunction("FUNC_REG_ACCOUNTBANK", Types.VARCHAR,
                SessionUtil.getUserlogado().getId(), cb.getIdbanco(),cb.getIdTipoMoeda(), cb.getIdtipoConta(), cb.getNumConta());
              String[] returnBD = (result != null ) ? result.toString().split(";") : null;
              if("true".equals(returnBD[0])){ returnBD = assocciarAccount( Integer.valueOf(returnBD[1]), cb); }
        return returnBD;
    }
    
    public String[] assocciarAccount(int newAccountID, ContaBanco cb){
        Object result =  Call.callSampleFunction("FUNC_ASSOCIAR_ACCOUNTS", Types.VARCHAR,
                 SessionUtil.getUserlogado().getId(), cb.getIdtipoContaContabilistica(), newAccountID);
        String[] returnBD = (result != null ) ? result.toString().split(";") : null;
    return returnBD;
    }
    
    public ArrayList<ContaBanco> getListContaBanco(String pesq){
        ResultSet rs = Call.selectFrom("VER_ACCOUNTBANK", "*");
        ArrayList<ContaBanco> listMap = new ArrayList<>();
         Consumer<HashMap<String, Object>> act = (map) -> {
            ContaBanco cb = new ContaBanco();
            cb.setNumConta(toString(map.get("NUMBER")));
            cb.setNameConta(toString(map.get("NAME")));
            cb.setDescContaContabilistica(toString(map.get("ACCOUNT DESC")));
            cb.setNumContaContabilistica(toString(map.get("ACCOUNT NUMBER")));
            listMap.add(cb);
        };
        Call.forEchaResultSet(act, rs);
        return listMap;
    }
    
    public String toString(Object str){
        return (str != null) ? str.toString() : "" ; 
    }
    
    public ArrayList<ContaBanco> resultPesqu(String pesqu, boolean print, String type){
        
        ArrayList<ContaBanco> listMap = new ArrayList<>();
        
        ResultSet rs = (pesqu == null || pesqu.equals("")) 
                ? Call.selectFrom("VER_ACCOUNTBANK", "*")
                :Call.selectFiltredFrom("VER_ACCOUNTBANK", "*", pesqu, new String[]{"NUMBER","NAME","ACCOUNT DESC","ACCOUNT NUMBER"}) ;
       
        if(!print){
            Consumer<HashMap<String, Object>> act = (map) -> {
                ContaBanco cb = new ContaBanco();
                cb.setNumConta(toString(map.get("NUMBER")));
                cb.setNameConta(toString(map.get("NAME")));
                cb.setDescContaContabilistica(toString(map.get("ACCOUNT DESC")));
                cb.setNumContaContabilistica(toString(map.get("ACCOUNT NUMBER")));
                listMap.add(cb);
            };
            Call.forEchaResultSet(act, rs);
        }
        
        if(print){
            DataTableControl contabancoController = new DataTableControl("idcb.print", "contabanco.print");
            contabancoController.prepareModel(rs, DataTableControl.ShowMode.SHOW, "NUMBER", "NAME", "ACCOUNT NUMBER", "ACCOUNT DESC");
            contabancoController.renameColumn("NUMBER", "Número");
            contabancoController.renameColumn("NAME", "Nome");
            contabancoController.renameColumn("ACCOUNT DESC", "Numero Conta Contabilistica");
            contabancoController.renameColumn("ACCOUNT NUMBER", "Descrição Conta Contabilistica");
            
            if("pdf".equals(type))
            { GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Conta Banco", "Relatório de Conta Banco", contabancoController, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1); }
            else
            { GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Conta Banco", "Relatório de Conta Banco", contabancoController, -1); }
        }
        return listMap;
    }
}
