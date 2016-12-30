
package dao;


import bean.FuncionarioBean;
import conexao.Conexao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Funcionario;
import conexao.Call;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Types;
import java.util.HashMap;
import java.util.function.Consumer;
import modelo.ComoBox;
import modelo.Empresa;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;


public class FuncionarioDao implements Serializable
{
    private static final long SERIAL_VERSION = 1L;
    private CallableStatement cs;
    private String sql;
    private Connection con;
    private ResultSet rs;  
    
    private PreparedStatement ps;
    private String resultado;
    
    public ResultSet carregarFuncionario()
    {
        List<Funcionario> lista;
        Funcionario utilizador;
        Conexao conexao = new Conexao();
        sql="SELECT *FROM TABLE(FUNC_LOAD_USERS)";
        try 
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public Funcionario logar(Funcionario funcionario)
    {
        Funcionario f = null;
         Object dd = Call.callSampleFunction("FUNCT_LOGAR", Types.ARRAY, funcionario.getNomeAcesso(),this.encrypt(funcionario.getSenha()));
            if(dd!=null)
            {
                NomeUtilizadorCompleto(funcionario.getNomeAcesso());
                HashMap<String, String> map = new HashMap<>();
                String campos[];
                if(!((ArrayList<String>)dd).isEmpty())
                {
                    for (String list :((ArrayList<String>)dd))
                    {
                        campos = list.split(";");
                        map.put(campos[0], campos[1]);
                    }
                    f = new Funcionario();
                    f.setNome(map.get("NOME"));
                    f.setApelido(map.get("APELIDO"));
                    f.setNomeCompleto((map.get("NOME")+" "+map.get("APELIDO")));
                    f.setNif(map.get("NIF"));
                    f.setEstado(map.get("ESTADO"));
                    f.setNivelAcesso(map.get("NIVEL"));
                    f.setId(map.get("ID"));
                    f.setDepartamento(map.get("NOME_DEPARTAMENTO"));
                    f.setIdDepartamento(map.get("DEPARTAMENTO"));
                    f.setNomeNivel(map.get("NOME_NIVEL"));
                    f.setLogin(map.get("LOGIN"));
                }
            }
        return f;
    }
    public void NomeUtilizadorCompleto(String nomeAcesso)
    {
       if(SessionUtil.obterValor("nomeUtilizador") != null)
       {
           SessionUtil.removerParametro("nomeUtilizador");
       }
       rs = Call.selectFrom("VER_USER WHERE ACCESSNAME=?", "*", nomeAcesso);
       if(rs!= null)
       {
           try 
           {
               while(rs.next())
               {
                   SessionUtil.definirParametro("nomeUtilizador", (rs.getString("NOME")+" "+rs.getString("APELIDO")));
               }
               rs.close();
           }
           catch (SQLException ex) {
               Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    public Funcionario informations(String nif)
    {
        Funcionario funcionario = null;
        ResultSet rs = Call.selectFrom("VER_DATA_FUNCIONARIO WHERE NIF=?","*", nif);
        String idFuncionario = null;
        String dataFormatada, dataEntrada;
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    funcionario = new Funcionario();
                    dataFormatada = rs.getString("DATA NASCIMENTO").substring(8, 10)+"-"+rs.getString("DATA NASCIMENTO").substring(5, 7)
                            +"-"+rs.getString("DATA NASCIMENTO").substring(0, 4);
                    dataEntrada = rs.getString("DATA ENTRADA").substring(8, 10)+"-"+rs.getString("DATA ENTRADA").substring(5, 7)
                            +"-"+rs.getString("DATA ENTRADA").substring(0, 4);
                    System.out.println("data "+dataFormatada);
                    funcionario.setNome(rs.getString("NOME")+" "+rs.getString("APELIDO"));
                    funcionario.setNomeAcesso(rs.getString("COD SEGURADORA"));
                    funcionario.setApelido(rs.getString("APELIDO"));
                    funcionario.setEmail((rs.getString("MAIL")==null)? "":rs.getString("MAIL"));
                    funcionario.setDataNasc(dataFormatada);
                    funcionario.setNif(rs.getString("NIF"));
                    funcionario.setGenero(rs.getString("SEXO"));
                    funcionario.setEstadoCivil(rs.getString("ESTADO CIVIL"));
                    funcionario.setMovel(rs.getString("MOVEL"));
                    funcionario.setCategoria(rs.getString("CATERGORIA"));
                    funcionario.setResidencia(rs.getString("RESIDENCIA"));
                    funcionario.setDataEnt(dataEntrada);
                    funcionario.setBanco(rs.getString("BANCO"));
                    funcionario.setNumConta(rs.getString("NIB CONTA"));
                    funcionario.setNunFilhos((rs.getString("NUMERO DE FILHOS")== null)? "" : rs.getString("NUMERO DE FILHOS"));
                    funcionario.setCodigoNicon(rs.getString("COD SEGURADORA"));
                    funcionario.setDepartamento(rs.getString("DEPARTAMENTO"));
                    funcionario.setNivel(rs.getString("NIVEL"));
                    funcionario.setNumNivel(rs.getString("NIVLE CATEGORIA"));
                }
                rs.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return funcionario;
    }
    public String registrarFuncionario(Funcionario funcionario, Object idUser, Empresa e)
    {
        Conexao conexao = new Conexao();
        if(conexao.getCon()!=null) 
        {
            Object o = Call.callSampleFunction("FUNC_REG_FUNCTIONARIO", Call.VARCHAR,
                    idUser
                   ,funcionario.getGenero()
                   ,funcionario.getNome()
                   ,funcionario.getNif()
                   ,OperacaoData.toSQLDate(funcionario.getDataNascimento())
                   ,OperacaoData.toSQLDate(e.getDataEntrada())
                   ,e.getCodigoNicon()
                   ,funcionario.getApelido()
                   ,funcionario.getResidencia()
                   ,e.getCategoria()
                   ,funcionario.getEstadoCivil()
                   ,funcionario.getTelefone()
                   ,funcionario.getTelefone() 
                   ,((funcionario.getEmail()== null || funcionario.getEmail().equals(""))? null :funcionario.getEmail())
                    ,e.getDepartamento()
                    ,((funcionario.getNunFilhos()== null || funcionario.getNunFilhos().equals(""))?null :funcionario.getNunFilhos()),
                    Integer.valueOf(funcionario.getNivel())
            );
            
            resultado = ((o==null)?resultado:o.toString());
            conexao.destruir();
        }
        return resultado;
    }
    
    /**
     * Atualiza as informações de um determinado funcionário
     * @param funcionario 
     * @param empresa 
     * @param idFuncionario 
     */
    public void atualizarFuncionario(Funcionario funcionario,Empresa empresa, int idFuncionario)
    {
       Call.callProcedure("PRC_UPD_FUNCIONARIO", 
                SessionUtil.getUserlogado().getId().toString(),
                2,
                idFuncionario,
                funcionario.getResidencia(),
                Integer.valueOf(empresa.getCategoria()),
                Integer.valueOf(funcionario.getEstadoCivil()),
                funcionario.getTelefone(),
                null,
               ((funcionario.getNunFilhos()==null || funcionario.getNunFilhos().equals("")) ? null : Integer.valueOf(funcionario.getNunFilhos())),
               funcionario.getNome(),
               funcionario.getApelido(),
               funcionario.getNif(),
               OperacaoData.toSQLDate(funcionario.getDataNascimento()),
                empresa.getCodigoNicon(),
               OperacaoData.toSQLDate(empresa.getDataEntrada()),
               ((funcionario.getEmail()==null || funcionario.getEmail().equals(""))? null :funcionario.getEmail()),
               Integer.valueOf(funcionario.getNivel())
            );
                      
    }
    
   public Funcionario dadosConjugue(String idFuncionario)
   {
       Funcionario f = null;
       System.out.println("id Funcionario "+idFuncionario);
       ResultSet rs = Call.selectFrom("VER_DEPENDENTE", "*");
       String dataNasc;
       if(rs != null)
       {
           try
           {
               while(rs.next())
               {
                   if(idFuncionario.equals(rs.getString("ID FUNCIONARIO")))
                   {
                        f = new Funcionario();
                        
                        dataNasc = ((rs.getString("DATA NASCIMENTO")==null)?null:rs.getString("DATA NASCIMENTO").substring(8, 10)+"-"+rs.getString("DATA NASCIMENTO").substring(5, 7)
                            +"-"+rs.getString("DATA NASCIMENTO").substring(0, 4));
                        f.setConjugueNome(rs.getString("NOME")+" "+rs.getString("APELIDO"));
                        f.setConjugueEstadoCivil(rs.getString("ESTADO CIVIL"));
                        f.setConjugueDataNasc(dataNasc);
                        break;
                   }
               }
               rs.close();
           }
           catch (SQLException ex) {
               Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return f;
   }
   
   public String codigoNicon(String idUser)
   {
       String codigo = null;
       rs = Call.selectFrom("T_FUNCIONARIO WHERE FUNC_ID=?", "FUNC_ACCESSNAME",idUser);
       if(rs != null)
       {
           try 
           {
               while(rs.next())
               {
                   codigo = rs.getString("FUNC_ACCESSNAME");
               }
                rs.close();
           }
           catch (SQLException ex) {
               Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return codigo;
   }
   
    public List<Funcionario> ListFucionario(String valorPesquisa, boolean estado,boolean acesso)
    {
        List<Funcionario> list= new ArrayList<>();
        Funcionario f = null;
         if((valorPesquisa == null || valorPesquisa.equals(""))
                 && (estado == false))
         {
            rs = Call.selectFrom("VER_FUNCIONARIO WHERE \"ESTADO_NUMBER\" != 0 ", "*");
            if(rs != null)
            {
                try 
                {
                    while(rs.next())
                    {
                        if(!rs.getString("ACCESS").equals("0") || !acesso)
                        {
                            f = new Funcionario(rs.getString("ID"), rs.getString("NOME")+" "+rs.getString("APELIDO"), rs.getString("NIF"));
                            f.setEstado(rs.getString("ESTADO"));
                            f.setNivelAcesso(rs.getString("ACCESS"));
                            list.add(f);
                        }   
                    }
                    rs.close();
                } 
                catch (SQLException ex) {
                    Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         }
         else if((valorPesquisa != null && !valorPesquisa.equals(""))
              && (estado == false))
         {
             rs = Call.selectFrom("VER_FUNCIONARIO WHERE UPPER(NOME) LIKE UPPER('%"+valorPesquisa+"%') AND \"ESTADO_NUMBER\" != 0 ","*");
            if(rs != null)
            {
                 try 
                 {
                     while(rs.next())
                     {
                        if(!rs.getString("ACCESS").equals("0") || !acesso)
                        {
                            f = new Funcionario(rs.getString("ID"), rs.getString("NOME")+" "+rs.getString("APELIDO"), rs.getString("NIF"));
                            f.setEstado(rs.getString("ESTADO"));
                            f.setNivelAcesso(rs.getString("ACCESS"));
                            list.add(f);
                        }  
                     }
                    rs.close();
                 } 
                 catch (SQLException ex) {
                     Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
         }
         else if((valorPesquisa == null || valorPesquisa.equals(""))
              && (estado == true))
         {
             rs = Call.selectFrom("VER_FUNCIONARIO WHERE \"ESTADO_NUMBER\" = 0","*");
            if(rs != null)
            {
                 try 
                 {
                     while(rs.next())
                     {
                        if(!rs.getString("ACCESS").equals("0") || !acesso)
                        {
                            f = new Funcionario(rs.getString("ID"), rs.getString("NOME")+" "+rs.getString("APELIDO"), rs.getString("NIF"));
                            f.setEstado(rs.getString("ESTADO"));
                            f.setNivelAcesso(rs.getString("ACCESS"));
                            list.add(f);
                        }
                     }
                    rs.close();
                 }
                 catch (SQLException ex) {
                     Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
         }
         else
         {
             rs = Call.selectFrom("VER_FUNCIONARIO WHERE UPPER(NOME) LIKE UPPER('%"+valorPesquisa+"%') AND \"ESTADO_NUMBER\" = 0","*");
            if(rs != null)
            {
                 try 
                 {
                     while(rs.next())
                     {
                        if(!rs.getString("ACCESS").equals("0") || !acesso)
                        {
                            f = new Funcionario(rs.getString("ID"), rs.getString("NOME")+" "+rs.getString("APELIDO"), rs.getString("NIF"));
                            f.setEstado(rs.getString("ESTADO"));
                            f.setNivelAcesso(rs.getString("ACCESS"));
                            list.add(f);
                        }  
                     }
                    rs.close();
                 }
                 catch (SQLException ex) {
                     Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
         }
        
         
        return list;
    }
    
    public void redefinirSenha(String idUserRedifinir,String op, String type)
    {
        System.out.println("idUserR "+idUserRedifinir);
        String codigo = codigoNicon(idUserRedifinir);
        Call.callProcedure("PRC_USER_DESATIVE",
                SessionUtil.getUserlogado().getId().toString(),
                idUserRedifinir,
                encrypt(codigo),
                op,
                type);
    }
    
    public List<ComoBox> ListFucionarioNoUser()
    {
         //sql="select ID,NOME,APELIDO from VER_FUNCIONARIO";
         ResultSet rs = Call.selectFrom("VER_FUNCIONARI_NUSER", "*");
         List<ComoBox> list= new ArrayList<>();
         if(rs==null)
             return list;
         Consumer <HashMap<String, Object>> act  = (map)-> list.add(new ComoBox(map.get("ID").toString(), map.get("NOME")+" "+map.get("APELIDO"), map.get("CODIGO").toString()));
         Call.forEchaResultSet(act, rs);
         
        return list;
    }
    
    public List<ComoBox> BancoLista()
    {
         //sql="select ID,NOME,APELIDO from VER_FUNCIONARIO";
         ResultSet rs = Call.selectFrom("VER_BANK", "*");
         List<ComoBox> list= new ArrayList<>();
         if(rs==null)
             return list;
         Consumer <HashMap<String, Object>> act  = (map)-> list.add(new ComoBox(map.get("ID").toString(), map.get("NOME").toString()));
         Call.forEchaResultSet(act, rs);
         
        return list;
    }
    
    public List<ComoBox> ListFucionarioCategoria()
    {
         sql="select REALID,CATEGORIA from VER_CATEGORIAFUNCIONARIO";
        Conexao conexao = new Conexao();
        List<ComoBox> list= new ArrayList<>();
        try 
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                rs = cs.executeQuery();
                while (rs.next()) {                    
                    list.add(new ComoBox(rs.getString("REALID"), rs.getString("CATEGORIA")));
                }
                conexao.destruir();
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro a atualizar funcionário "+ex.getMessage());
        }
        return list;
    }
    
    public String registrarFuncionarioDependente(Funcionario funcionario, Object idUser, String idFuncionario)
    {
        Conexao conexao = new Conexao();
        if(conexao.getCon()!=null) 
        {   
            Object o = Call.callSampleFunction("FUNC_REG_DEPENDENTE", Call.VARCHAR,
                    idUser
                   ,((funcionario.getNome() == null || funcionario.getNome().equals(""))? null : funcionario.getNome())
                   ,((funcionario.getApelido()==null || funcionario.getApelido().equals(""))? null :funcionario.getApelido())
                   ,idFuncionario
                   ,46
                   ,2
                   ,((funcionario.getEstadoCivil()==null || funcionario.getEstadoCivil().equals(""))? null : funcionario.getEstadoCivil())
                   ,((funcionario.getDataNascimento()==null)? null :OperacaoData.toSQLDate(funcionario.getDataNascimento()))
                   ,((funcionario.getResidencia()==null || funcionario.getResidencia().equals(""))? null :funcionario.getResidencia())
            );
            
            resultado = ((o==null)?resultado:o.toString());
            conexao.destruir();
        }
        return resultado;
    }   

    /**
     * encriptar a senha do usuario
     *
     * @param password
     * @return
     */
    public String encrypt(String password) 
        {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest)
                hexString.append(String.format("%02X", 0xFF & b));
            
            String senha = hexString.toString();
            return senha;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            //Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


     public String registrarFuncionarioConta(Empresa empresa, Object idUser, String idFuncionario)
    {
        Conexao conexao = new Conexao();
        if(conexao.getCon()!=null) 
        {
            Object o = Call.callSampleFunction("FUNC_REG_CONTAFUNCIONARIO", Call.VARCHAR,
                    idUser
                   ,idFuncionario
                   ,empresa.getBanco()
                   ,empresa.getNumContaBancario()
            );
            
            resultado = ((o==null)?resultado:o.toString());
            conexao.destruir();
        }
        return resultado;
    }
   
    public String senhaUtilizador()
    {
        String id = ((Funcionario)SessionUtil.obterValor("utilizador")).getId().toString();
        String senha = null;
        rs = Call.selectFrom("T_FUNCIONARIO WHERE FUNC_ID=?","FUNC_PWD", id);
        if(rs != null)
        {
            try {
                while(rs.next())
                {
                    senha = rs.getString("FUNC_PWD");
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return senha;
    }
    
    public String registrarUtilizacao(Funcionario funcionario, Object idUser)
    {
        Conexao conexao = new Conexao();
        if(conexao.getCon()!=null) 
        {
            Object o = Call.callSampleFunction("FUNC_CAST_TOUSER", Call.VARCHAR,
                    idUser
                   ,funcionario.getNome()
                   ,funcionario.getNomeAcesso()
                   ,encrypt(funcionario.getNomeAcesso())
                   ,funcionario.getNivelAcesso()
            );
            resultado = ((o==null)?resultado:o.toString());
            conexao.destruir();
        }
        return resultado;
    }
    
    public boolean exinteNifFucionario(String nifFuncionario)
    {
        try {
            //sql="select ID,NOME,APELIDO from VER_FUNCIONARIO";
            rs = Call.selectFrom("VER_FUNCIONARIO", "*");
            while (rs.next())            
            {
                if(rs.getString("NIF").equals(nifFuncionario))
                    return true;
            }
            return false;
        } catch (SQLException ex) {
           return false;
        }
    }
    
    public boolean exinteNomeAcessoFucionario(String nomeAcesso)
    {
        try {
            //sql="select ID,NOME,APELIDO from VER_FUNCIONARIO";
            rs = Call.selectFrom("VER_USER", "*");
            while (rs.next())            
            {
                if(rs.getString("ACCESSNAME")!=null)
                if(rs.getString("ACCESSNAME").equals(nomeAcesso))
                    return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;       
        }
    }
    
    public List<ComoBox> nivelAcesso()
    {
         //sql="select ID,NOME,APELIDO from VER_FUNCIONARIO";
         ResultSet rs = Call.selectFrom("T_NIVELUSER WHERE NVUSER_ID != 1 ", "*");
         List<ComoBox> list= new ArrayList<>();
         if(rs==null)
             return list;
         Consumer <HashMap<String, Object>> act  = (map)-> list.add(new ComoBox(map.get("NVUSER_ID").toString(), map.get("NVUSER_DESC").toString()));
         Call.forEchaResultSet(act, rs);
         
        return list;
    }
    public List<ComoBox> nivelAcessoAlterar(String nivel)
    {
         //sql="select ID,NOME,APELIDO from VER_FUNCIONARIO";
         ResultSet rs = Call.selectFrom("T_NIVELUSER WHERE NVUSER_ID != 1 AND NVUSER_DESC!=?", "*",nivel);
         List<ComoBox> list= new ArrayList<>();
         if(rs==null)
             return list;
         Consumer <HashMap<String, Object>> act  = (map)-> list.add(new ComoBox(map.get("NVUSER_ID").toString(), map.get("NVUSER_DESC").toString()));
         Call.forEchaResultSet(act, rs);
         
        return list;
    }
    
    public void alterarNivel(String idUserLogado,String idUserAlterar,int idNivel,int idDepartamento )
    {
        Call.callProcedure("PRC_USER_ALTERARLEVEL", idUserLogado,idUserAlterar,idNivel,idDepartamento);
    }
    public Object alterarSenha(Funcionario f)
    {
        String id = ((Funcionario)SessionUtil.obterValor("utilizador")).getId().toString();
        Object resp = Call.callSampleFunction("FUNC_USER_ALTERASENHA", Types.VARCHAR, 
               Integer.valueOf(id),
                encrypt(f.getSenha()),
                encrypt(f.getNovaSenha()));
        return resp;
                           
    }
    
    public void ativarUtilizador(int idUtilizador, String novaSenha)
    {
        String procedureName = "PRC_USER_ACTVIE";
        Call.callProcedure(procedureName, idUtilizador,this.encrypt(novaSenha));
    }
    
    public String getInfo(String tabelaView, String campoPesquisa, String campoRetorno,String valorPesquisa)
    {
        String valor = null;
        ResultSet rs = Call.selectFrom(tabelaView+" WHERE "+campoPesquisa+"=?", campoRetorno, valorPesquisa);
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    valor = rs.getString(campoRetorno);
                }
                rs.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valor;
    }
    
    public void editarFuncionario(Funcionario func, Empresa empresa, Funcionario conjugue)
    {
        String pattern ="dd-MM-yyyy";
        String idFuncionario = func.getId().toString();
        Funcionario funcionario = new Funcionario();
        funcionario = this.informations(func.getNif());
        if(funcionario != null)
        {
            funcionario.setNome(funcionario.getNome().split(" ")[0]);
            funcionario.setGenero(Validacao.DevolverValorCampo("REALID", "SEXO", "VER_SEXO", funcionario.getGenero()));
            funcionario.setEstadoCivil(Validacao.DevolverValorCampo("SC_ID", "SC_DESC", "T_STATECIVIL", funcionario.getEstadoCivil()));
            funcionario.setDepartamento(Validacao.DevolverValorCampo("ID", "DEPARTAMENTO", "VER_DEPARTAMENTO", funcionario.getDepartamento()));
            funcionario.setCategoria(Validacao.DevolverValorCampo("REALID", "CATEGORIA", "VER_CATEGORIAFUNCIONARIO", funcionario.getCategoria()));
            funcionario.setBanco(Validacao.DevolverValorCampo("ID", "SIGLA", "VER_BANK", funcionario.getBanco()));
            empresa.setDataEntrada(OperacaoData.stringFormatToDate(funcionario.getDataEnt(), pattern));

            RequestContext.getCurrentInstance().execute("dadosPessoaisFuncionario('"+funcionario.getNome()+"','"+funcionario.getDataNasc()+"','"+funcionario.getEstadoCivil()+"',"
                    + "'"+funcionario.getNunFilhos()+"','"+funcionario.getMovel()+"','"+funcionario.getApelido()+"','"+funcionario.getNif()+"','"+funcionario.getGenero()+"',"
                    + "'"+funcionario.getEmail()+"','"+funcionario.getResidencia()+"')");
            RequestContext.getCurrentInstance().execute("dadosEmpresa('"+funcionario.getCodigoNicon()+"','"+funcionario.getDataEnt()+"','"+funcionario.getDepartamento()+"',"
                    + "'"+funcionario.getCategoria()+"','"+funcionario.getBanco()+"','"+funcionario.getNumConta()+"','"+funcionario.getNumNivel()+"')");
            conjugue = this.dadosConjugue(idFuncionario);
            if(conjugue != null)
            {
                conjugue.setConjugueEstadoCivil(Validacao.DevolverValorCampo("SC_ID", "SC_DESC", "T_STATECIVIL", conjugue.getConjugueEstadoCivil()));
                RequestContext.getCurrentInstance().execute("dadosConjugue('"+conjugue.getConjugueNome().split(" ")[0]+"','"+conjugue.getConjugueNome().split(" ")[1]+"','"+conjugue.getConjugueDataNasc()+"','"+conjugue.getConjugueEstadoCivil()+"')");
            }   
        }
 
    }
    
    /**
     * 
     * @param codigo
     * @return -1 se o código é inválido e 1 se estiver válido
     */
    public int verificarCodigoNicon(String codigo)
    {
        String code = null;
        ResultSet rs;
        rs = Call.selectFrom("T_FUNCIONARIO WHERE FUNC_ACCESSNAME=?","*", codigo);
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    code = rs.getString("FUNC_ACCESSNAME");
                }
                rs.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (code == null)? 1:-1;
    }
    
    
    public String[] nivelAtual(String idFuncionario)
    {
        ResultSet rs;
        String nivel = null;
        String departamento = null;
        String idDepartamento = null;
        String [] info = new String[2];
        rs = Call.selectFrom("VER_DATA_FUNCIONARIO WHERE ID=?", "*", idFuncionario);
        if(rs != null)
        {
            try {
                while(rs.next())
                {
                    nivel = rs.getString("NIVEL");
                    departamento = rs.getString("DEPARTAMENTO");
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        rs = Call.selectFrom("T_DEPARTAMENTO WHERE DEP_DESC=?","*", departamento);
        if(rs!= null)
        {
            try {
                while(rs.next())
                {
                    idDepartamento = rs.getString("DEP_ID");
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("nivel "+nivel);
        info[0]=nivel;
        info[1]=idDepartamento;
        return info;
    }
    
    public static Object darFerias(Object idFun,FuncionarioBean.Ferias f)
    {
       Object o =Call.callSampleFunction("PACK_RH.funcFeriarFuncionario", Types.VARCHAR, SessionUtil.getUserlogado().getId(), idFun, f.getDataInicio(), f.getDataFim());
       return o; 
    }
    
    public static Object ultimarSuspensao(Object idFun,int i,String just)
    {
        Object o =Call.callSampleFunction("PACK_RH.funcSuspenderFuncionario", Types.VARCHAR, SessionUtil.getUserlogado().getId(), idFun, just, ((i==1) ? 0 : 2 ));
        return o;
    }
    
    public static Object diasRestantes(Object idFun)
    {
        Object o =Call.callSampleFunction("PACK_RH.getDiasRestante", Types.VARCHAR, SessionUtil.getUserlogado().getId(), idFun);
        return o;
    }
    
    public static ArrayList<ComoBox> listFuc()
    {
       ArrayList<ComoBox> list = new ArrayList<>();
       ResultSet rs = Call.selectFrom("VER_FUNCIONARIO", "*");
       Consumer <HashMap<String, Object>> act  = (HashMap<String, Object> map) -> 
       {list.add( new ComoBox(map.get("ID")+"", map.get("NOME")+" "+map.get("APELIDO")));};
       Call.forEchaResultSet(act, rs);
       return list;
    }
}

