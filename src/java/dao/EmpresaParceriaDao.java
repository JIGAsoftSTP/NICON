
package dao;

import bean.EmpresaPardeiraBean;
import conexao.Call;
import conexao.Conexao;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ComoBox;
import modelo.EmpresaParceiramodelo;
import sessao.SessionUtil;

public class EmpresaParceriaDao implements Serializable
{
       private CallableStatement cs;
    private String sql;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private Object resultado;
    
     
   public Object registarEmpresaParceira(EmpresaParceiramodelo empresaParceiramodelo)
    {
        
         resultado = Call.callSampleFunction("FUNC_REG_EMPRESA", Types.VARCHAR, 
         SessionUtil.getUserlogado().getId(),
         empresaParceiramodelo.getNome(),
         empresaParceiramodelo.getEndereco(),
         empresaParceiramodelo.getPais(),
         Float.valueOf(empresaParceiramodelo.getCapitalSocial())//convertendo float para String
         ); 
        
        return resultado;
        
    }
    
   public List<ComoBox> todosPaises()
      {
    List<ComoBox> textosdocumentopaises = new ArrayList<>();
    ComoBox comoBox = null;
          sql="SELECT * FROM T_PAIS";
          
        Conexao conexao = new Conexao();
        
        try 
        {
        if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
        if (rs != null) 
            {
            while(rs.next())
                {
                    comoBox = new ComoBox(rs.getString("PAIS_ID"), rs.getString("PAIS_NOME"));
                    textosdocumentopaises.add(comoBox);
                }
                rs.close();
                conexao.destruir();
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textosdocumentopaises;
    }

    public EmpresaParceriaDao() 
    {
    }
   

  public List<ComoBox> mostrarNomeEmpresa() // para listar apenas os id e nome dos cliente na tabela T_EMPRESA
      {
    List<ComoBox> testessss = new ArrayList<>();
    ComoBox comoBox = null;
          sql="SELECT EMP_ID, EMP_NOME FROM T_EMPRESA";
          
        Conexao conexao = new Conexao();
        
        try 
        {
        if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
        if (rs != null) 
            {
            while(rs.next())
                {
                    comoBox = new ComoBox(rs.getString("EMP_ID"), rs.getString("EMP_NOME"));
                    testessss.add(comoBox);
                }
                rs.close();
                conexao.destruir();
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return testessss;
    }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  
   public List<EmpresaParceiramodelo> listarEmpresaParceira()
      {
    List<EmpresaParceiramodelo> lista = new ArrayList<>();
    
    EmpresaParceiramodelo empresaParceiraclasseModelo = null;
 
          sql="SELECT * FROM VER_EMPRESAPARCEIRAS";
          
        Conexao conexao = new Conexao();
        
        try 
        {
        if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                
        if (rs != null) 
            {
            while(rs.next())
                {
                    empresaParceiraclasseModelo = new EmpresaParceiramodelo(
                                          rs.getString("EMPRESA"), 
                                          rs.getString("ENDERECO"),
                                          rs.getString("PAIS"),
                                          rs.getString("CAPITAL"),
                                          rs.getInt("ID"));
                                          lista.add(empresaParceiraclasseModelo);
                }
                rs.close();
                conexao.destruir();
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
   
   
   /////////////////////////////////////////////////
   
    public  void removerTabelaEmpresaParceira(int idUser, int idEmpresa)
        {
            SessionUtil.getUserlogado().getId();
            String procedure_prc_disableEnpresa= "PACK_UTIL.prc_disableEnpresa";
            Call.callProcedure(procedure_prc_disableEnpresa, idUser, idEmpresa);
            
            System.out.println("todos os utilizador " +procedure_prc_disableEnpresa+ " Id impresa " +idEmpresa+ "Utilizador "+idUser + " utilizador " + SessionUtil.getUserlogado().getId());
        }
  
  /////////////////////////////////////////////////////////////////
    
  
//   public static void main(String[] args) 
//    {
//        EmpresaParceriaDao fdao =new EmpresaParceriaDao();
//        
//        try {
//            List<ComoBox> dadosT_Empresa =fdao.liistarTodosDadosTEmpresa();
//            
//        for(ComoBox u :dadosT_Empresa)
//            {
//                System.out.println("Resultado " +u.getValue()+" "+u.getId());
//            }
//        } 
//         catch (Exception ex) {
//             System.out.println("Errado  ocorreu erro");
//            Logger.getLogger(EmpresaParceriaDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//   
   
//   public static void main(String[] args) 
//    {
//        EmpresaParceriaDao fdao =new EmpresaParceriaDao();
//        
//        try {
//            List<EmpresaParceiramodelo> lista =fdao.listarEmpresaParceira();
//            
//        for(EmpresaParceiramodelo u :lista)
//            {
//                System.out.println("Resultado"+u);
//            }
//        } 
//         catch (Exception ex) {
//             System.out.println("Errrado  ocorreu erro");
//            Logger.getLogger(EmpresaParceriaDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}

