
import conexao.Conexao;
import dao.ViagemDao;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.ComoBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ildo
 */

/**
 *
 * @author Helcio
 */
@ManagedBean
public class ConsultaCliente
{
    private Cliente cliente;
    private List<Cliente> listaClientes;
    private ClienteDao clienteDao;
    private String pesquisa;
    private String campo;
    private int totalRegistro;
    private String tipo;
    private String nomeCliente;
    private String seguroAdicionar;
    
    public ConsultaCliente()
    {
        clienteDao = new ClienteDao();
        listaClientes = new ArrayList();
        listaClientes = clienteDao.filtrarPesquisa(pesquisa, campo);
        totalRegistro = clienteDao.filtrarPesquisa(pesquisa, campo).size();

    }
    public Cliente getCliente() {
         return (cliente == null) ? cliente = new Cliente() : cliente;
     }

     public void setCliente(Cliente cliente) {
         this.cliente = cliente;
     }

    public int getTotalRegistro() {
        return totalRegistro;
    }

    public void setTotalRegistro(int totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

     public List<Cliente> getListaClientes() {
         return listaClientes;
     }

    public String getCampo() {
        return campo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getSeguroAdicionar() {
        return seguroAdicionar;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

     public void setListaClientes(List<Cliente> listaClientes) {
         this.listaClientes = listaClientes;
     }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
        if(clienteDao.filtrarPesquisa(pesquisa, campo).size()>0)
        {
            setListaClientes(clienteDao.filtrarPesquisa(pesquisa, campo));
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
    // cliente selecionado
    public void clienteSelecionado()
    {
       HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
       tipo = cliente.getTipoCliente(); // tipo recebe o tipo de cliente selecionado
       nomeCliente = cliente.getNome(); // nome do cliente selecionado recebe o 
       System.out.println("Nome do cliente "+nomeCliente);
       session.setAttribute("CodigoCliente", cliente.getNumero());
       seguroAdicionar = devolverNomeSeguro();
        System.out.println("Seguro a adicionar "+devolverNomeSeguro());

    }
    
    public String devolverNomeSeguro()// pega o nome do seguro de acordo com o código
    {
        String seguro = null; // pega o nome do seguro
        String numeroSeguro = null; // pega o numero do seguro
         HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
         if(session.getAttribute("S")==null)//Se a sessão com o codigo S for nulo, siginfica que nehum seguro foi selecionado
         {
             seguro ="Nenhum seguro";
         }
         else
         {
             numeroSeguro = session.getAttribute("S").toString().split(";")[0];//se a sessão não for nula pega o codigo do seguro selecionado
            switch(numeroSeguro)
            {
                case "MV":// caso seja MV então o nome do seguro é veiculo
                   seguro ="Seguro de Veiculo";
                    break;
                case "TIN":// caso seja TIN então, o nome do seguro é viagem
                    seguro ="Seguro de Viagem";
                    break;
                case "DI":// caso seja DI então, o nome do seguro é roubo
                    seguro ="Seguro de Roubo";
                    break;
                case "GPA":
                    seguro ="Seguro de Acidente para Grupo";
                    break;
                case "MAC":
                    seguro = "Seguro de Carga Maritima";
                    break;
               
            }
         }
                
         return seguro;
    }
    
    
/**
 * 
 * @author ildo
 */
public class ClienteDao 
    {
    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    private String resultado = null;
    
     public List<ComoBox> listaPaises() {
        List<ComoBox> paises = new ArrayList<>();
        ComoBox comoBox = null;
        sql = "SELECT * FROM T_PAIS";
        Conexao conexao = new Conexao();
        try {
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
        } catch (SQLException ex) {
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
            ps = conexao.getCon().prepareStatement(sql);
            ps.execute();
            rs = ps.getResultSet();
            while(rs.next())
            {
                sexos.add(rs.getString("GEN_DESC"));
            }
            rs.close();
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
            ps = conexao.getCon().prepareStatement(sql);
            ps.execute();
            rs = ps.getResultSet();
            while(rs.next())
            {
                doumentos.add(rs.getString("Documento"));
            }
            rs.close();
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
        sql="SELECT Cli_Alvara FROM T_CLIENTE WHERE CLI_ALVARA=?";
        boolean existe = false;
        Conexao conexao = new Conexao();
        try
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
        catch (SQLException ex) 
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return existe;
    }
    
    public List estadoCivis()
    {
        sql="SELECT SC_DESC FROM T_STATECIVIL";
        List estadosCivis = new ArrayList();
        Conexao conexao = new Conexao();
        try 
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
        catch (SQLException ex) 
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return estadosCivis;
    }
   /**
    * Registra um novo cliente singular
    * @param idUser o id do funcionário logado no sistema
    * @param cliente
    * @return 
    */
//   public String registrarClienteSingular(ClienteBean cliente)
//   {
//       sql="{?=call FUNC_REG_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
//        String telefone= (cliente.getTelefoneS()==null) ? null : cliente.getTelefoneS();
//       String email = (cliente.getEmailS()==null) ? null : cliente.getEmailS();
//       String movel = (cliente.getTelemovelS()==null) ? null : cliente.getTelemovelS();
//       Conexao conexao = new Conexao();
//       System.out.println("Data de nascimento "+cliente.getDataNascimentoFormatada());
//        try 
//        {
//            cs = conexao.getCon().prepareCall(sql);
//            cs.registerOutParameter(1, Types.VARCHAR);
//            cs.setObject(2,1);
//            cs.setObject(3, cliente.getSexo());
//           cs.setObject(4, cliente.getTipoCliente());
//           cs.setObject(5, cliente.getPais());
//           cs.setObject(6, cliente.getDataNascimentoFormatada());
//           cs.setObject(7, cliente.getNomeS());
//           cs.setObject(8, cliente.getNifS());
//           cs.setObject(9, cliente.getNumero());
//           cs.setObject(10, cliente.getApelido());
//           cs.setObject(11, cliente.getLocalTrabalho());
//           cs.setObject(12, cliente.getMorada());
//           cs.setObject(13, cliente.getEstadoCivil());
//           cs.setObject(14,cliente.getDocumento());
//           cs.setObject(15, cliente.getNumeroDocumento());
//           cs.setObject(16, null);
//           cs.setObject(17, movel);
//           cs.setObject(18, telefone);
//           cs.setObject(19, email);
//           cs.setObject(20, cliente.getProfissao());
//           cs.setObject(21, null);
//           cs.setObject(22, null);
//           cs.setObject(23, null);
//           cs.execute();
//            resultado = cs.getString(1);
//            conexao.destruir();
//        }
//        catch (SQLException ex)
//        {
//            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return resultado; 
//   }
   public long numeroCliente()
   {
       long numero =0;
       sql="{?=call FUNC_NEXT_CLIENTE()}";
       Conexao conexao = new Conexao();
        try 
        {
            cs = conexao.getCon().prepareCall(sql);
            cs.registerOutParameter(1,Types.NUMERIC);
            cs.execute();
            numero = cs.getLong(1);
            conexao.destruir();
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
     return numero;
   }
//   public String registrarClienteColetivo( ClienteBean cliente)
//   {
//       String telefone= (cliente.getTelefoneC()==null) ? null : cliente.getTelefoneC();
//       String email = (cliente.getEmailC()==null) ? null : cliente.getEmailC();
//       String movel = (cliente.getTelemovelC()==null) ? null : cliente.getTelemovelC();
//       sql="{?=call FUNC_REG_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
//       Conexao conexao = new Conexao();
//       System.out.println("data de criação "+cliente.getDataCriacaoFormatada());
//        try 
//        {
//            cs = conexao.getCon().prepareCall(sql);
//            cs.registerOutParameter(1,Types.VARCHAR);
//            cs.setObject(2, 1);
//            cs.setObject(3, "Outro");
//            cs.setObject(4, cliente.getTipoCliente());
//            cs.setObject(5, cliente.getLocalizacao());// ´Com dúvida
//            cs.setObject(6, cliente.getDataCriacaoFormatada());
//            cs.setObject(7, cliente.getNomeC());
//            cs.setObject(8, cliente.getNifC());
//            cs.setObject(9, cliente.getNumero());// código do cliente
//            cs.setObject(10, null);//apelido
//            cs.setObject(11, null);//local trabalho
//            cs.setObject(12, null);
//            cs.setObject(13, null);//estado civil
//            cs.setObject(14, null);// tipo de documento
//            cs.setObject(15, null);// número do documento
//            cs.setObject(16, null);// caixa postal
//            cs.setObject(17, movel);
//            cs.setObject(18, telefone);
//            cs.setObject(19, email);
//            cs.setObject(20, null);
//            cs.setObject(21, cliente.getSite()); // site
//            cs.setObject(22, cliente.getAlvara());
//            cs.setObject(23, cliente.getRazaoSocial());
//            cs.execute();
//            resultado = cs.getString(1);
//            conexao.destruir();
//        }
//        catch (SQLException ex)
//        {
//            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return resultado; 
//   }
    public List<Cliente>filtrarPesquisa(String valor,String campo)
    {
         List<Cliente> lista = new ArrayList<>();
         System.out.println("Valor ");
        try {
           
            Conexao conexao = new Conexao();
            Cliente cliente =null;
            sql = (valor==null || valor.isEmpty()) ? "SELECT * FROM VER_CLIENTE" : "SELECT * from VER_CLIENTE WHERE UPPER("+campo + ") like UPPER( '%"+valor + "%')";
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
                    cliente.setNumero(rs.getLong("ID"));
                    cliente.setTipoCliente(rs.getString("TIPO CLIENTE"));
                    lista.add(cliente);
                }
                rs.close();
            }
            conexao.destruir();
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
        catch (SQLException ex)
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public void atualizarCliente()
    {
        
    }
 
 
    public ResultSet getDadosCliente(String valor)
    {
        ResultSet rs= null;
        try
        {
            Conexao conexao = new Conexao();
            String sql ="SELECT *from VER_CLIENTE WHERE UPPER(ID) = UPPER( '"+valor+ "')";
            PreparedStatement ps = conexao.getCon().prepareStatement(sql);
            ps.execute();
            rs = ps.getResultSet();
            rs.next();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs; 
    }
}


/**
 *
 * @author Helio
 */
public class Cliente
{
    private String nomeS;
    private String nome;
    private String nif;
    private String contacto;
    private String endereco;
    private String apelido;
    private String nifS;
    private Date dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String morada;
    private String nacionalidade;
    private String profissao;
    private String localTrabalho;
    private String telefoneS;   
    private String telemovelS;
    private String emailS;
    private String nomeC;
    private String nifC;
    private String email;
    private String codigo;
    private String localizacao;
    private String site;
    private String alvara;
    private Date dataCriacao;
    private String razaoSocial;
    private String telefoneC;
    private String numLinhas;
    private String telemovelC;
    private String emailC;
    private String tipoCliente;
    private String pais;
    private String documento;
    private String numeroDocumento;
    private long numero;

    public String getNomeS() {
        return nomeS;
    }

    public void setNomeS(String nomeS) {
        this.nomeS = nomeS;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNifS() {
        return nifS;
    }

    public void setNifS(String nifS) {
        this.nifS = nifS;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public String getTelefoneS() {
        return telefoneS;
    }

    public void setTelefoneS(String telefoneS) {
        this.telefoneS = telefoneS;
    }

    public String getTelemovelS() {
        return telemovelS;
    }

    public void setTelemovelS(String telemovelS) {
        this.telemovelS = telemovelS;
    }

    public String getEmailS() {
        return emailS;
    }

    public void setEmailS(String emailS) {
        this.emailS = emailS;
    }

    public String getNomeC() {
        return nomeC;
    }

    public void setNomeC(String nomeC) {
        this.nomeC = nomeC;
    }

    public String getNifC() {
        return nifC;
    }

    public void setNifC(String nifC) {
        this.nifC = nifC;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAlvara() {
        return alvara;
    }

    public void setAlvara(String alvara) {
        this.alvara = alvara;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefoneC() {
        return telefoneC;
    }

    public void setTelefoneC(String telefoneC) {
        this.telefoneC = telefoneC;
    }

    public String getNumLinhas() {
        return numLinhas;
    }

    public void setNumLinhas(String numLinhas) {
        this.numLinhas = numLinhas;
    }

    public String getTelemovelC() {
        return telemovelC;
    }

    public void setTelemovelC(String telemovelC) {
        this.telemovelC = telemovelC;
    }

    public String getEmailC() {
        return emailC;
    }

    public void setEmailC(String emailC) {
        this.emailC = emailC;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }
    
}

}
