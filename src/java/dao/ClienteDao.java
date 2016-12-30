
package dao;

import Export.GenericExcel;
import Export.GenericPDFs;
import bean.DataTableControl;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.ComoBox;
import sessao.SessionUtil;
import validacao.OperacaoData;


/**
 * 
 * @author ildo
 */
public class ClienteDao implements Serializable
{
    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    private String resultado = null;
    private String formato = "dd-MM-yyyy";
    
    
        public boolean verificarEmail(String email)
    {
        String result = null;
        boolean existe = false;
        sql="SELECT EMAIL FROM VER_CLIENTE WHERE EMAIL=?";
        Conexao conexao = new Conexao();
        try {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.setString(1, email);
                ps.execute();
                rs = ps.getResultSet();
                if(rs != null)
                {
                    while(rs.next())
                    {
                        result =  rs.getString("EMAIL");
                    }
                    rs.close();
                }
                conexao.destruir();
            }
            existe = (result == null)? false : true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro a verificar email "+ex.getMessage());
        }
        
        return existe;
    }
     public List<ComoBox> listaPaises() {
        List<ComoBox> paises = new ArrayList<>();
        ComoBox comoBox = null;
        sql = "SELECT * FROM T_PAIS";
        Conexao conexao = new Conexao();
        try {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                if (rs != null) {
                    while (rs.next()) {
                        comoBox = new ComoBox(rs.getString("PAIS_ID"), rs.getString("PAIS_NOME"));
                        paises.add(comoBox);
                    }
                    rs.close();
                    conexao.destruir();
            }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViagemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paises;
    }
  
    public List<ComoBox>listaSexos()
    {
        List<ComoBox> sexos = new ArrayList<>();
        sql="SELECT GEN_ID,GEN_DESC FROM T_GENDER";
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
                    sexos.add(new ComoBox(rs.getString("GEN_ID"), rs.getString("GEN_DESC")));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return sexos;
    }
    
       public List<ComoBox>listaEstados()
    {
        List<ComoBox> sexos = new ArrayList<>();
        sql="SELECT SC_ID, SC_DESC FROM T_STATECIVIL";
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
                    sexos.add(new ComoBox(rs.getString("SC_ID"), rs.getString("SC_DESC")));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return sexos;
    }
   public List<ComoBox> listaDocumentos()
    {
           List<ComoBox> doumentos = new ArrayList<>();
        sql="SELECT REALID,DOCUMENTO FROM VER_TIPODOCUMENTO";
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
                    doumentos.add(new ComoBox(rs.getString("REALID"), rs.getString("DOCUMENTO")));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doumentos;
    }
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
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
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
        sql="SELECT ALVARA FROM VER_CLIENTE WHERE ALVARA=?";
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
                        resultado= rs.getString("ALVARA");
                    }
                }
                conexao.destruir();
                existe = (resultado == null)? false : true;
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return existe;
    }
      public List<ComoBox> paises()
      {
           List<ComoBox> doumentos = new ArrayList<>();
        sql="SELECT REALID,DOCUMENTO FROM VER_TIPODOCUMENTO";
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
                    doumentos.add(new ComoBox(rs.getString("REALID"), rs.getString("DOCUMENTO")));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doumentos;
    }
   /**
    * Registra um novo cliente singular
    * @param idUser o id do funcionário logado no sistema
    * @param cliente
    * @return 
    */
   public String registrarClienteSingular(Cliente cliente)
   {
       System.out.println("função");
       sql="{?=call FUNC_REG_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        String telefone= (cliente.getTelefoneS()==null) ? null : cliente.getTelefoneS();
       String movel = (cliente.getTelemovelS()==null) ? null : cliente.getTelemovelS();
       Object idUser = ((Funcionario) SessionUtil.obterValor("utilizador")).getId();
       System.out.println("Singular "+cliente.getNacionalidade());
       Conexao conexao = new Conexao();
       if(conexao.getCon()!=null)
       {
           Object dd = Call.callSampleFunction("FUNC_REG_CLIENTE", Types.VARCHAR,
                   idUser
                   , cliente.getSexo()
                   , cliente.getTipoCliente()
                   , (cliente.getNacionalidade()!= null && !cliente.getNacionalidade().equals("") ? cliente.getNacionalidade() : null)
                   , OperacaoData.toSQLDate(cliente.getDataNascimento())
                   , cliente.getNomeS()
                   , (cliente.getNifS() != null && !cliente.getNifS().equals("") ? cliente.getNifS() : null)
                   , cliente.getNumero()
                   , cliente.getApelido()
                   , (cliente.getLocalTrabalho() != null && !cliente.getLocalTrabalho().equals("") ? cliente.getLocalTrabalho() : null)
                   , (cliente.getMorada()!= null && !cliente.getMorada().equals("") ? cliente.getMorada() : null)
                   , (cliente.getEstadoCivil()!= null && !cliente.getEstadoCivil().equals("") ? cliente.getEstadoCivil() : null)
                   , (cliente.getDocumento()!= null && !cliente.getDocumento().equals("") ? cliente.getDocumento() : null)
                   , (cliente.getNumeroDocumento() != null && !cliente.getNumeroDocumento().equals("") ? cliente.getNumeroDocumento() : null)
                   , null
                   , movel
                   , telefone
                   , (cliente.getEmailS()!=null && !cliente.getEmailS().equals("") ? cliente.getEmailS() : null)
                   , (cliente.getProfissao() != null && !cliente.getProfissao().equals("") ? cliente.getProfissao() : null)
                   , null
                   , null
                   , null);
           resultado = dd.toString();
           conexao.destruir();
       }
        return resultado; 
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
   public String registrarClienteColetivo( Cliente cliente)
   {
       String telefone= (cliente.getTelefoneC()==null) ? null : cliente.getTelefoneC();
       String email = (cliente.getEmailC()==null || cliente.getEmailC().equals("")) ? null : cliente.getEmailC();
       String movel = (cliente.getTelemovelC()==null || cliente.getTelemovelC().equals("")) ? null : cliente.getTelemovelC();
       Object idUser = ((Funcionario) SessionUtil.obterValor("utilizador")).getId();
       Conexao conexao = new Conexao();
       if(conexao.getCon()!=null)
       {
           Object dd = Call.callSampleFunction("FUNC_REG_CLIENTE",
                   Types.VARCHAR
                   , idUser
                   , 3
                   , cliente.getTipoCliente()
                   , cliente.getPais()// ´Com dúvida
                   , null
                   , cliente.getNomeC()
                   , ((cliente.getNifC() == null || cliente.getNifC().equals("")) ? null : cliente.getNifC())
                   , cliente.getNumero()// código do cliente
                   , ((cliente.getResponsavel() == null || cliente.getResponsavel().equals("")) ? null : cliente.getResponsavel())
                   , null//local trabalho
                   , cliente.getLocalizacao()
                   , null//estado civil
                   , null// tipo de documento
                   , null// número do documento
                   , null// caixa postal
                   , movel
                   , telefone
                   , email
                   , ((cliente.getFunção() == null || cliente.getFunção().equals("")) ? null : cliente.getFunção())
                   , null
                   ,null
                   , (cliente.getRazaoSocial()== null || cliente.getRazaoSocial().equals("")) ? null :cliente.getRazaoSocial());
           resultado = dd.toString();
           conexao.destruir();
       }
        return resultado; 
   }
    public List<Cliente>filtrarPesquisa(String valor,String campo, String quantidadeRegistro, boolean im,int type)
    {
         List<Cliente> lista = new ArrayList<>();
        try {
           
            Conexao conexao = new Conexao();
            Cliente cliente =null;
            if(valor == null || valor.isEmpty())
            {
                if(quantidadeRegistro.equals("metade"))
                    sql = "SELECT * FROM VER_CLIENTE WHERE ROWNUM<=10";
                else
                    sql = "SELECT * FROM VER_CLIENTE"; 
            }
            else 
            {
                System.out.println("entrou  aqui");
                 sql = "SELECT *FROM VER_CLIENTE WHERE UPPER("+campo + ") like UPPER( '%"+valor + "%')"; 
            }
             
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                if(rs!=null && !im)
                {

                    while(rs.next())
                    {
                        cliente = new Cliente();
                        cliente.setNif(rs.getString("NIF"));
                        cliente.setNome(rs.getString("NOME"));
                        cliente.setApelido(rs.getString("APELIDO"));
                        cliente.setEmail(rs.getString("EMAIL"));
                        cliente.setMorada( rs.getString("RESIDENCIA"));
                        cliente.setNumero(rs.getString("ID"));
                        cliente.setAlvara(rs.getString("ALVARA"));
                        cliente.setTipoCliente(rs.getString("TIPO CLIENTE"));
                        cliente.setRazaoSocial(rs.getString("RASAO SOCIAL"));
                        cliente.setLocalTrabalho(rs.getString("LOCAL TRABALHO"));
                        cliente.setProfissao(rs.getString("ACUPACAO"));
                        cliente.setTelefoneS(rs.getString("TELEFONE"));
                        cliente.setTelemovelS(rs.getString("TELEMOVEL"));
                        cliente.setSexo(rs.getString("SEXO"));
                        cliente.setPais(rs.getString("PAIS"));
//                        Código	Tipo	NIF	Nome Completo	Contacto	Residência	Telefone
                        if(rs.getString("DATA NASCIMENTO") != null)
                        {
                            cliente.setDataNascimento(OperacaoData.stringFormatToDate(rs.getString("DATA NASCIMENTO"), formato));
                        }
                      
                        lista.add(cliente);

                    }
                    rs.close();
                }
                
                if(im)
                {   
                    DataTableControl clienteControl = new DataTableControl("id55", "clienteff.fjfjf");
                    clienteControl.prepareModel(rs, DataTableControl.ShowMode.SHOW,"NIF", "NOME", "APELIDO", "TELEFONE", "TELEMOVEL", "RESIDENCIA","EMAIL", "SEXO", "PAIS");
                    clienteControl.renameColumn("PAIS", "NACIONALIDADE");
                    clienteControl.renameColumn("TELEFONE", "CONTACTO");
//                    clienteControl.updFaces(FacesContext.getCurrentInstance());
                    if(type == 1)
                    { GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Cliente", "Relatório de Cliente", clienteControl, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1); }
                    else
                    { GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Cliente", "Relatório de Cliente", clienteControl, -1); }
                }
                conexao.destruir();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return lista;
       
      
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
    

    public String obterIso3(String pais)
    {
        String iso = null;
        sql="SELECT * FROM T_PAIS";
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
                        if(pais.equals(rs.getString("PAIS_NOME")))
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
    
   
    public static ResultSet getDadosCliente(String valor)
    {
        ResultSet rs= null;
        try
        {
            System.err.println(valor +" Cliente Selecionado");
            Conexao conexao = new Conexao();
            String sql ="SELECT *from VER_CLIENTE WHERE UPPER(ID) = UPPER( '"+valor+ "')";
            if(conexao.getCon()!=null)
            {
                PreparedStatement ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                rs.next();
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public static String getDadosCliente(String valor,String param)
    {
        String res= "Não encontrado!";
        ResultSet rs= null;
        try
        {
            rs = Call.selectFrom("VER_CLIENTE WHERE UPPER(ID) = ?", "*", valor);
            if(rs==null)
                return res;
            while(rs.next())
            {     
                res=rs.getString(param);
                return res;
            }    
        } 
        catch (SQLException ex) 
        {Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);}
        return res;
    }
    /**
     * 
     * @param campoRetorno O valor do campo a ser retornado
     * @param campoSql campo sql a comparar
     * @param view view que se efetuará a pesquisa
     * @return valor do campo campoRetorno
     */
    public String DevolverValor(String campoRetorno,String campoSql, String view, String valor)
    {
        sql = "SELECT "+campoRetorno+" FROM "+view+" WHERE "+campoSql+"=?";
           Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.setString(1, valor);
                ps.execute();
                rs = ps.getResultSet();
                if( rs!= null)
                {
                    while(rs.next())
                    {
                        resultado = rs.getString(campoRetorno);
                    }
                    rs.close();
                    conexao.destruir();
                }
            }
            System.out.println("resultado "+resultado);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    /**
     * Lista que retorna toda informação do cliente selecionado
     * @return 
     */
    public Cliente InfoCliente(String codigo)
    {
          Cliente cliente = null;
        sql = "SELECT * FROM VER_CLIENTECOMPLETO WHERE ID=?";
        {
            Conexao conexao = new Conexao();
            if(conexao.getCon() != null)
            {
                try
                {
                    ps = conexao.getCon().prepareStatement(sql);
                    ps.setString(1,codigo);
                    ps.execute();
                    rs = ps.getResultSet();
                    if(rs != null)
                    {
                        while(rs.next())
                        {
                            cliente = new Cliente();
                            cliente.setEstadoCivil(rs.getString("ESTADO CIVIL"));
                            cliente.setFunção(rs.getString("ACUPACAO"));
                            cliente.setLocalizacao(rs.getString("RESIDENCIA"));
                            cliente.setDocumento(rs.getString("DOCUMENTO"));
                            cliente.setNumeroDocumento(rs.getString("NUMERO DOCUMENTO"));
                            cliente.setResponsavel(rs.getString("APELIDO"));
                        }
                        rs.close();
                    }
                    
                }
                catch(SQLException ex)
                {
                    System.err.println("erro a obter informações do cliente "+ex.getMessage());
                }
            }
        }
        return cliente;
    }
    

    public static String paisesSelected(String idPais)
      {
        String sql;
        if(isInteger(idPais))
             sql="SELECT PAIS_ID,PAIS_NOME FROM T_PAIS where PAIS_ID="+idPais;
        else
            return  idPais;
        Conexao conexao = new Conexao();
        try 
        {
            System.err.println("fjfhf entrou!!");
            if(conexao.getCon()!=null)
            {
                PreparedStatement ps = conexao.getCon().prepareStatement(sql);
                ResultSet rs=ps.executeQuery();

                    while(rs.next())
                    {
                        System.err.println("ff");
                        return rs.getString("PAIS_NOME");
                    }
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
    
    public void atualizarCliente(Cliente cliente)
    {
        String id = ((Funcionario)SessionUtil.obterValor("utilizador")).getId().toString();
        String functionName = "PRC_UPD_CLIENTE";
        if(SessionUtil.obterValor("typeCustomer").toString().equals("Singular"))
        {
            Call.callProcedure(functionName, 
             id,
             SessionUtil.obterValor("numCliente").toString(),
              2,
              ((cliente.getLocalTrabalho() == null || cliente.getLocalTrabalho().equals(""))? null :cliente.getLocalTrabalho()),
              ((cliente.getMorada()==null || cliente.getMorada().equals(""))? null :cliente.getMorada()),
              ((SessionUtil.obterValor("documento")== null || SessionUtil.obterValor("documento").equals(""))? null : Integer.valueOf(SessionUtil.obterValor("documento").toString())),
               ((cliente.getEstadoCivil() == null || cliente.getEstadoCivil().equals(""))? null : Integer.valueOf(cliente.getEstadoCivil())),
              ((SessionUtil.obterValor("numDocumento")== null || SessionUtil.obterValor("numDocumento").equals(""))? null : SessionUtil.obterValor("numDocumento").toString()),
              null,
              ((cliente.getTelemovelS()==null || cliente.getTelemovelS().equals(""))? null :cliente.getTelemovelS()),
              cliente.getTelefoneS(),
              ((cliente.getEmailS()==null || cliente.getEmailS().equals(""))? null :cliente.getEmailS()),
              ((cliente.getProfissao()== null || cliente.getProfissao().equals(""))? null :cliente.getProfissao()),
              null,
              (( cliente.getSexo() == null || cliente.getSexo().equals(""))? null :Integer.valueOf(cliente.getSexo())),
              ((cliente.getNifS()== null || cliente.getNifS().equals("") )? null :cliente.getNifS()),
              ((cliente.getNacionalidade()==null || cliente.getNacionalidade().equals(""))? null :Integer.valueOf(cliente.getNacionalidade())) ,
              SessionUtil.obterValor("apelidoCliente").toString(),
             null);
        }
        else
        {
            Call.callProcedure(functionName, 
              id,
             SessionUtil.obterValor("numCliente").toString(),
              2,
              null,
              cliente.getLocalizacao(),
              null,
              null,
              null,
              null,
              ((cliente.getTelemovelC()==null || cliente.getTelemovelC().equals(""))? null :cliente.getTelemovelC()),
              cliente.getTelefoneC(),
              ((cliente.getEmailC()== null || cliente.getEmailC().equals(""))? null :cliente.getEmailC()),
              ((cliente.getFunção()==null || cliente.getFunção().equals(""))? null :cliente.getFunção()),
              null,
             3,
             SessionUtil.obterValor("nifColetivoCliente").toString(),
             Integer.valueOf(SessionUtil.obterValor("paisColetivoCliente").toString()),
             ((cliente.getResponsavel() == null || cliente.getResponsavel().equals(""))? null :cliente.getResponsavel()),
             cliente.getRazaoSocial());
        } 
    }
    
    public Cliente dadosCliente(String codigo)
    {
          Cliente cliente = null;
        sql = "SELECT * FROM VER_CLIENTECOMPLETO WHERE ID=?";
        {
            Conexao conexao = new Conexao();
            if(conexao.getCon() != null)
            {
                try
                {
                    ps = conexao.getCon().prepareStatement(sql);
                    ps.setString(1,codigo);
                    ps.execute();
                    rs = ps.getResultSet();
                    if(rs != null)
                    {
                        while(rs.next())
                        {
                            cliente = new Cliente();
                            cliente.setDocumento(rs.getString("DOCUMENTO"));
                            cliente.setNumeroDocumento(rs.getString("NUMERO DOCUMENTO"));
                        }
                        rs.close();
                    }
                    
                }
                catch(SQLException ex)
                {
                    System.err.println("erro a obter informações do cliente "+ex.getMessage());
                }
            }
        }
        return cliente;
    }

    public static boolean isInteger(String test)
    {
        try{
            Integer.valueOf(test);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
//    public List<Cliente>filtrarPesquisaCliente(String valor,String campo)
//    {
//         List<Cliente> lista = new ArrayList<>();
//        try {
//           
//            Conexao conexao = new Conexao();
//            Cliente cliente =null;
//            if(valor == null || valor.isEmpty())
//            {
//                sql = "SELECT * FROM T_CLIENTE";
//            }
//            else 
//            {
//                System.out.println("entrou  aqui");
//                 sql = "SELECT *FROM VER_CLIENTE WHERE UPPER("+campo + ") like UPPER( '%"+valor + "%')"; 
//            }
//             
//            if(conexao.getCon()!=null)
//            {
//                ps = conexao.getCon().prepareStatement(sql);
//                ps.execute();
//                rs = ps.getResultSet();
//                if(rs!=null)
//                {
//
//                    while(rs.next())
//                    {
//                        cliente = new Cliente();
//                        cliente.setNif(rs.getString("NIF"));
//                        cliente.setNome(rs.getString("NOME"));
//                        cliente.setApelido(rs.getString("APELIDO"));
//                        cliente.setEmail(rs.getString("EMAIL"));
//                        cliente.setMorada( rs.getString("RESIDENCIA"));
//                        cliente.setNumero(rs.getString("ID"));
//                        cliente.setAlvara(rs.getString("ALVARA"));
//                        cliente.setTipoCliente(rs.getString("TIPO CLIENTE"));
//                        cliente.setRazaoSocial(rs.getString("RASAO SOCIAL"));
//                        cliente.setLocalTrabalho(rs.getString("LOCAL TRABALHO"));
//                        cliente.setProfissao(rs.getString("ACUPACAO"));
//                        cliente.setTelefoneS(rs.getString("TELEFONE"));
//                        cliente.setTelemovelS(rs.getString("TELEMOVEL"));
//                        cliente.setSexo(rs.getString("SEXO"));
//                        cliente.setPais(rs.getString("PAIS"));
//                        
//                        if(rs.getString("DATA NASCIMENTO") != null)
//                        {
//                            cliente.setDataNascimento(OperacaoData.stringFormatToDate(rs.getString("DATA NASCIMENTO"), formato));
//                        }
//                      
//                        lista.add(cliente);
//
//                    }
//                    rs.close();
//                }
//                conexao.destruir();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       return lista;
//       
//      
//    }
}
