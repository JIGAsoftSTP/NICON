
package dao;

import conexao.Conexao;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.ComoBox;

/**
 *
 * @author Helio
 */
public class FuncoesProcedimentos 
{
    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    private String resultado = null;
    
     public List<ComoBox> listaPaises()
     {
        List<ComoBox> paises = new ArrayList<>();
        ComoBox comoBox = null;
        sql = "SELECT * FROM VER_PAIS";
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
                    while (rs.next()) 
                    {
                        comoBox = new ComoBox(rs.getString("ID"), rs.getString("NOME"));
                        paises.add(comoBox);
                    }
                    rs.close();
                    conexao.destruir();
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ViagemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paises;
    }
    public List listaSexos()
    {
        List sexos = new ArrayList();
        sql="SELECT GEN_DESC FROM T_GENDER";
        Conexao conexao = new Conexao();
        try 
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                while(rs.next())
                {
                    sexos.add(rs.getString("GEN_DESC"));
                }
                rs.close();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return sexos;
    }
    public List listaDocumentos()
    {
           List doumentos = new ArrayList();
        sql="SELECT Documento FROM VER_TIPODOCUMENTO";
        Conexao conexao = new Conexao();
        try 
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                while(rs.next())
                {
                    doumentos.add(rs.getString("Documento"));
                }
                rs.close();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doumentos;
    }
    
    
    public List estadoCivis()
    {
        sql="SELECT SC_DESC FROM T_STATECIVIL";
        List estadosCivis = new ArrayList();
        Conexao conexao = new Conexao();
        try 
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                while(rs.next())
                {
                    estadosCivis.add(rs.getObject("SC_DESC").toString());
                }
                rs.close();
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return estadosCivis;
    }
 
    
    public String obterIso3(String pais)
    {
        String iso = null;
        sql="SELECT * FROM VER_PAIS";
        Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                if( rs!= null)
                {
                    while(rs.next())
                    {
                        if(pais.equals(rs.getString("NOME")))
                            resultado = rs.getString("ISO");
                    }
                    rs.close();
                    conexao.destruir();
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    public class ClienteDao
    {
        @SuppressWarnings("unchecked")
        public boolean verificarNif(String nif)
        {
            String result = null;
            boolean existe = false;
            sql="SELECT NIF FROM VER_CLIENTE WHERE NIF=?";
            Conexao conexao = new Conexao();
            try {
                if(conexao.getCon()!=null)
                {
                    ps = conexao.getCon().prepareStatement(sql);
                    ps.setString(1, nif);
                    ps.execute();
                    rs = ps.getResultSet();
                    if(rs != null)
                    {
                        while(rs.next())
                        {
                            result =  rs.getString("NIF");
                        }
                        rs.close();
                    }
                    conexao.destruir();
                    existe = (result == null)? false : true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(dao.ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Erro a verificar nif "+ex.getMessage());
            }

            return existe;
        }
        /**
         * Verifica- se o número de alvara do cliente já existe na base de dados
         * @param alvara
         * @return 
         */
        public boolean verificarAlvara(String alvara)
        {
            sql="SELECT Cli_Alvara FROM T_CLIENTE WHERE CLI_ALVARA=?";
            boolean existe = false;
            Conexao conexao = new Conexao();
            try
            {
                if(conexao.getCon()!=null)
                {
                    ps = conexao.getCon().prepareStatement(sql);
                    ps.setString(1, alvara);
                    ps.execute();
                    rs = ps.getResultSet();
                    if(rs!= null)
                    {
                        while(rs.next())
                        {
                            resultado= rs.getString("CLI_ALVARA");
                        }
                    }
                    conexao.destruir();
                    existe = (resultado == null)? false : true;
                }
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(dao.ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            return existe;
        }
        
        public long lastNumeroCliente()
        {
            long rt=0;
            sql="SELECT MAX(CLI_ID)FROM T_CLIENTE";
            Conexao conexao = new Conexao();

            try 
            {
                if(conexao.getCon()!=null)
                {
                    ps=conexao.getCon().prepareStatement(sql);
                    ps.executeQuery();
                    rs=ps.getResultSet();
                    if(rs!= null)
                    {
                        while(rs.next())
                        {
                            rt = rs.getLong(1);
                        }
                        rs.close();
                    }
                    rt++;
                    conexao.destruir();
                }
            }
            catch (SQLException ex) 
            {

                Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);

            }
            return rt;
        }
        
        public List<Cliente>filtrarPesquisa(String valor,String campo)
        {
             List<Cliente> lista = new ArrayList<>();
             System.out.println("Valor ");
            try {

                Conexao conexao = new Conexao();
                Cliente cliente =null;
                sql = (valor==null || valor.isEmpty()) ? "SELECT * FROM VER_CLIENTE" : "SELECT * from VER_CLIENTE WHERE UPPER("+campo + ") like UPPER( '%"+valor + "%')";
                if(conexao.getCon()!=null)
                {
                    ps = conexao.getCon().prepareStatement(sql);
                    ps.execute();
                    rs = ps.getResultSet();
                    if(rs!=null)
                    {

                        while(rs.next())
                        {
                            cliente = new Cliente();
                            cliente.setNif(rs.getString("NIF"));
                            cliente.setNome(rs.getString("NOME"));
                            cliente.setEmail(rs.getString("EMAIL"));
                            cliente.setMorada( rs.getString("RESIDENCIA"));
                            cliente.setTipoCliente(rs.getString("TIPO CLIENTE"));
                            lista.add(cliente);
                        }
                        rs.close();
                    }
                    conexao.destruir();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
           return lista;


        }
        
    
        public long numeroCliente()
        {
            long numero =0;
            sql="{?=call FUNC_NEXT_CLIENTE()}";
            Conexao conexao = new Conexao();
             try 
             {
                if(conexao.getCon()!=null)
                {
                     cs = conexao.getCon().prepareCall(sql);
                     cs.registerOutParameter(1,Types.NUMERIC);
                     cs.execute();
                     numero = cs.getLong(1);
                     conexao.destruir();
                }
             }
             catch (SQLException ex) 
             {
                 Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
             }
          return numero;
        }
    }
    
   
}
