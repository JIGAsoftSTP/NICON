
package dao;

import modelo.Funcionario;
import conexao.Call;
import conexao.Conexao;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.faces.bean.ManagedBean;
import modelo.Seguroincendio;
import sessao.SessionUtil;

@ManagedBean
public class Seguroincendiodao implements Serializable
{
    private CallableStatement cs;
    private String sql;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private String resultado;
    
     public String registarSeguroincendio (Seguroincendio seguroincendio,String idContrato)//usada em tudo no xhtml
      {
      sql="{?=call FUNC_REGOBJ_SEGUROINCENDIO(?,?,?,?,?,?,?,?,?,?,?)}";

      Conexao conexao = new Conexao();
 
      try 
          {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setObject(2, ((Funcionario)SessionUtil.obterValor("utilizador")).getId());
                cs.setObject(3, idContrato);
                cs.setObject(4, seguroincendio.getEndereco_Edificio());
                cs.setObject(5, seguroincendio.getNumero_Andares());
                cs.setObject(6, seguroincendio.getCondicao());
                cs.setObject(7, seguroincendio.getEqui_Combate_Incendio());
                cs.setObject(8, seguroincendio.getFonte_Agua_Disponivel());
                cs.setObject(9, seguroincendio.getDistancia_Com_Bombeiro());
                cs.setObject(10, seguroincendio.getPavimento_Edificio());
                cs.setObject(11, seguroincendio.getTetos());
                cs.setObject(12, seguroincendio.getParede());
                cs.execute();
                resultado = cs.getString(1);
                conexao.destruir();
            }
          }
           catch (SQLException ex) 
           {
                System.out.println("Erro a registrar seguro "+ex.getMessage());
           }
          return resultado;
      }
      public String iincendioregistrarInfo(String idContrato, int tipoCobertura)
    {
       resultado = (String) Call.callSampleFunction("FUNC_REGOBJ_SEGUROINCENDIO",Types.VARCHAR, ((Funcionario)SessionUtil.obterValor("utilizador")).getId(),idContrato,tipoCobertura);
       return resultado;
    }
}
