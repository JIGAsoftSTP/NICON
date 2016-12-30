/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import modelo.Funcionario;
import conexao.Call;
import conexao.Conexao;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ComoBox;
import sessao.SessionUtil;

/**
 *
 * @author Helio
 */
public class SeguroDao implements Serializable
{
    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    private String resultado = null;
    public static String error;
    public String getCodigo(String codigoSeguro,Object idUser)
    {
        sql="{?=call FUNC_PREVIEW_NEXTCODIGO(?,?)}";
        Conexao conexao = new Conexao();
        
        try
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setObject(2,idUser);
                cs.setString(3, codigoSeguro);
                cs.execute();
                resultado = cs.getString(1);
                System.out.println("Parametros user"+idUser+"\n Codigo do seguro "+codigoSeguro +"\n apolice "+resultado);
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(SeguroDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    // função que ativa ou desativa um seguro
    public String ativarDesativarSeguro(String codigoSeguro, long userId)
    {
        Conexao conexao = new Conexao();
        sql="{?=call FUNC_ACTIVADASATIVA_SEGURO(?,?)}";
        try 
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setString(2, codigoSeguro);
                cs.setLong(3, userId);
                cs.execute();
                resultado = cs.getString(1);
                conexao.destruir();
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(SeguroDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro a ativar ou deativar seguro "+ex.getMessage());
        }
        return resultado;
    }
    
    public void s(java.util.Date da)
    {
        Conexao conexao = new Conexao ();
        java.sql.Date d = new java.sql.Date(da.getTime());
        sql="{?=call FFF(?)}";
                
        try 
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setDate(2, d);
                cs.execute();
                resultado = cs.getString(1);
                conexao.destruir();
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(SeguroDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro a ativar ou deativar seguro "+ex.getMessage());
        }
   
    }
    
    public String getIDSeguro(String codigoSeguro)
    {
        Conexao conexao = new Conexao();
        sql="SELECT ID from VER_SEGURO where CODIGO = ?";
        try 
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.setString(1, codigoSeguro);
                rs=cs.executeQuery();
                if(rs.next())
                {
                    resultado=rs.getString("ID");
                    System.err.println("ID Seguro do "+codigoSeguro+" é: "+resultado);
                }
                conexao.destruir();
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(SeguroDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro a ativar ou deativar seguro "+ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Anula o contrato do cliente selecionado
     * @param idContrato
     * @param novoEstado novo estado do contrato
     * @param justificativa justificaçao do anulamento
     * @param dataIncio
     * @param dataFim
     * @see anularContrato
     * @return 
     */
    public String AnularContrato(int idContrato,String novoEstado, String justificativa ,Date dataIncio,Date dataFim)
    {
        String idUser = ((Funcionario) SessionUtil.obterValor(Funcionario.SESSION_NAME)).getId().toString();
        String functionName = "FUNC_ALTERAR_ESTADOCONTRATO";
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                Integer.valueOf(idUser),
                idContrato,
                novoEstado,
                justificativa,
                dataIncio,
               (dataFim == null ) ? null : dataFim
        );
        return resp.toString();
    }
    
    /***
     * Lista de todos os seguros existentes
     * @return 
     */
    public List<ComoBox> seguros()
    {
        rs = Call.selectFrom("VER_SEGURO","*");
        List<ComoBox> listaSeguros = new ArrayList<>();
        Consumer <HashMap<String,Object>> act = (map)-> listaSeguros.add(new ComoBox(map.get("ID").toString(), map.get("NOME").toString()));
        Call.forEchaResultSet(act, rs);
        return listaSeguros;
    }
    
    public static Object verificarApolice(String numeroApolice)
    {
        Object resp = Call.callSampleFunction("PACK_VALIDATE.apoliceExist",Types.VARCHAR, numeroApolice);
        return resp;
    }
}
