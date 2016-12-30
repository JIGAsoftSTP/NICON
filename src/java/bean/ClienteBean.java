package bean;

import conexao.Call;
import dao.ClienteDao;
import dao.ContratoDao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.Cliente;
import modelo.ComoBox;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable 
{
    private static final long serialVersionUID = 1L;
    private Cliente cliente = new Cliente();
    private String keyQuere;
    private String seletedColuna;
    private String selectedeItemComb;
    
    private ClienteDao clienteDao = new ClienteDao();
    private List<ComoBox> paises;
    private List<ComoBox> documentos;
    private List<ComoBox> estadoCivis;
    private List<ComoBox> sexos;
    private String info;
    private List<Cliente> clientes;
    //Copiado /**********
    
    private DataTableControl cttTable;
    private List<Cliente> listaClientes;
    private String pesquisa;
    private String campo;
    private int totalRegistro;
    private String tipo;
    private String nomeCliente;
    private DataTableControl mostrarDados;
    private String seguroAdicionar;
    private String pagina;
    private String ordenar = "DATA REGISTRO";


    
    public ClienteBean()
    {
        this.cttTable = new DataTableControl(":info:addSegTable_cttClient", "clienteBean.cttTable"); 
        this.mostrarDados = new DataTableControl(":info:mostrarDadosCliente","clienteBean.mostrarDados");
        listaClientes = new ArrayList();
        listaClientes = clienteDao.filtrarPesquisa(pesquisa, campo,"total", false, 0);
        totalRegistro = listaClientes.size();
        paises = new ArrayList<>();
        sexos = new ArrayList<>();
        paises = clienteDao.listaPaises();
        documentos = new ArrayList<>();
        documentos = clienteDao.listaDocumentos();
        estadoCivis = new ArrayList<>();  
        estadoCivis = clienteDao.listaEstados();
        sexos = clienteDao.listaSexos();

    }
    
    @PostConstruct
    public void init()
    {     
        if(!Validacao.paginaAtual().contains("GestSeg_Clientes.xhtml"))
        {
            cliente.setNumero(String.valueOf(clienteDao.lastNumeroCliente())); 
        }    
    }

    public String getSeletedColuna() {
        return seletedColuna;
    }

    public String getKeyQuere() {
        return keyQuere;
    }

    public void setKeyQuere(String keyQuere) {
        this.keyQuere = keyQuere;
    }

    public void setSeletedColuna(String seletedColuna) {
        this.seletedColuna = seletedColuna;
    }

    public List<ComoBox> getPaises() {
        return paises;
    }

    public void setPaises(List<ComoBox> paises) {
        this.paises = paises;
    }

    public List<ComoBox> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<ComoBox> documentos) {
        this.documentos = documentos;
    }

    public List<ComoBox> getEstadoCivis() {
        return estadoCivis;
    }

    public void setEstadoCivis(List<ComoBox> estadoCivis) {
        this.estadoCivis = estadoCivis;
    }

    public List<ComoBox> getSexos() {
        return sexos;
    }

    public void setSexos(List<ComoBox> sexos) {
        this.sexos = sexos;
    }
    
 
    public Cliente getCliente()
    {
        return (cliente == null) ? cliente = new Cliente() : cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public String getSelectedeItemComb() {
        return selectedeItemComb;
    }

    public void setSelectedeItemComb(String selectedeItemComb) {
        this.selectedeItemComb = selectedeItemComb;
    }
    
    public void entrar()
    {
        System.out.println("entrou no método");
    }

    public void adicionarCliente()
    {
        @SuppressWarnings("UnusedAssignment")
        String resultado = null;
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RecuperarUltimoNumero();
        if(cliente.getTipoCliente().equals("Singular"))
        {
            if(CamposSingularVazio() == false)
            {
                if(verificarNif() == false && validarEmail() == true && verificarEmail() == false)
                {

                    cliente.setTipoCliente("1");
                    resultado = clienteDao.registrarClienteSingular(cliente);
                    if(resultado.equals("true"))
                    {
                        listaClientes = clienteDao.filtrarPesquisa(null, null,"total", false, 0);
                        Validacao.AtualizarCompoente("s", "tabelaClienteS");
                        RequestContext.getCurrentInstance().execute("clienteRegistrado()");   
                        session.setAttribute("CodigoCliente", cliente.getNumero());
                        session.setMaxInactiveInterval(-1);
                    }
                }
            }
        }
        else
        {
            if(CamposColetivoVazio() == false)
            {
                if(validarEmail() == true && verificarEmail() == false && verificarNif() == false )
                {
                    cliente.setTipoCliente("2");
                    resultado = clienteDao.registrarClienteColetivo(cliente);
                    if(resultado.equals("true"))
                    {
                        listaClientes = clienteDao.filtrarPesquisa(null, null,"total", false, 0);
                        Validacao.AtualizarCompoente("s", "tabelaClienteS");
                        RequestContext.getCurrentInstance().execute("clienteRegistrado()");  
                        session.setAttribute("CodigoCliente", cliente.getNumero());
                        session.setMaxInactiveInterval(-1);
       
                    }
               }
            }
        }       
    }
    
    /**
     * função que verifica- se nif já existe para o tipo de cliente singular(pessoa)
     * @return true se existe e fase caso contrário
     */
    
    public boolean verificarNif()
    {
        boolean resultado = false;

        if(cliente.getNifS() != null && !cliente.getNifS().equals(""))
        {           
            resultado = clienteDao.verificarNif(cliente.getNifS());
            if(resultado== true)
            {
                Mensagem.addErrorMsg("NIF já existe.");
                Validacao.AtualizarCompoente("addClienteformulario", "cliGrowl");
                RequestContext.getCurrentInstance().execute("bordaVermelhaNIF1()");
            }
            else
            {
                if(cliente.getNifS().length() != 9)
                {
                    Message.addErrorMsg("NIF deve ter nove(9) dígitos.", "addClienteformulario", "cliGrowl");
                    return true;
                }
            }
        }
        if(cliente.getNifC() != null && !cliente.getNifC().equals(""))
        {
            resultado = clienteDao.verificarNif(cliente.getNifC());
            if(resultado== true)
            {
                Mensagem.addErrorMsg("NIF já existe");
                Validacao.AtualizarCompoente("addClienteformulario", "cliGrowl");
                RequestContext.getCurrentInstance().execute("bordaVermelhaNIF2()");
            }
            else
            {
                if(cliente.getNifC().length() != 9)
                {
                    Message.addErrorMsg("NIF deve ter nove(9) dígitos.", "addClienteformulario", "cliGrowl");
                    return true;
                }
            }
        }
        return resultado;
    }
 
    public boolean verificarEmail()
    {
       boolean resultado = false;
        if(cliente.getEmailS() != null && !cliente.getEmailS().equals(""))
        {           
            resultado = clienteDao.verificarEmail(cliente.getEmailS());
            if(resultado== true)
            {
                 Mensagem.addErrorMsg("Email já existe");
                Validacao.AtualizarCompoente("addClienteformulario", "cliGrowl");
                RequestContext.getCurrentInstance().execute("bordaVermelhaEmail1()");
            }
        }
        else if(cliente.getEmailC() != null && !cliente.getEmailC().equals(""))
        {
            resultado = clienteDao.verificarEmail(cliente.getEmailC());
            if(resultado== true)
            {
                Mensagem.addErrorMsg("Email já existe");
                Validacao.AtualizarCompoente("addClienteformulario", "cliGrowl");
                RequestContext.getCurrentInstance().execute("bordaVermelhaEmail2()");
            }
        }
        return resultado;
    }
    
      /**
     * função que valida o email para o tipo de cliente singular(pessoa)
     * @return true se válido e fase caso contrário
     */
    public boolean validarEmail()
    {
        boolean valido = true;
         Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
        if(cliente.getEmailS() != null && cliente.getEmailS().length()>0)
        {
             Matcher m = p.matcher(cliente.getEmailS()); 
            if (!m.find())
            {
                valido = false;
                Mensagem.addErrorMsg("Email inválido");
                Validacao.AtualizarCompoente("addClienteformulario", "cliGrowl");
                RequestContext.getCurrentInstance().execute("bordaVermelhaEmail1()");
            }   
        }
        if(cliente.getEmailC() != null && !cliente.getEmailC().equals(""))
        {
             Matcher m = p.matcher(cliente.getEmailC()); 
            if (!m.find())
            {
                valido = false;
                Mensagem.addErrorMsg("Email inválido");
                Validacao.AtualizarCompoente("addClienteformulario", "cliGrowl");
                RequestContext.getCurrentInstance().execute("bordaVermelhaEmail2()");
            }   
        }
        return valido;
    }

    public boolean CamposSingularVazio()
    {
        boolean vazio = true;
        if((cliente.getTelefoneS() != null && cliente.getTelefoneS().length()>0) &&
           (cliente.getNomeS()!=null && cliente.getNomeS().length()>0)&&
           (cliente.getApelido() != null && cliente.getApelido().length()>0)&&
           (cliente.getSexo() != null && cliente.getSexo().length()>0) &&
           (cliente.getNifS() != null && cliente.getNifS().length()>0) &&
           (cliente.getEstadoCivil() != null && cliente.getEstadoCivil().length()>0) &&
           (cliente.getDocumento() != null && cliente.getDocumento().length()>0) &&
            (cliente.getNumeroDocumento() != null && cliente.getNumeroDocumento().length()>0) &&
            (cliente.getDataNascimento() != null))
        {
                vazio = false;  
        }
       
        return vazio;
    }
   
    public int verificarIdadeCliente()
    {
         Calendar dataNascimento = Calendar.getInstance();  
        dataNascimento.setTime(cliente.getDataNascimento()); 
        Calendar hoje = Calendar.getInstance();  
        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);
        
        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) 
          idade--;  
        else 
        { 
            if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH))
                idade--;      
        }
        if(idade>=18)
            
            return 1;
        else
        {
               RequestContext.getCurrentInstance().execute("idadeCliente()");
               return 0;
        }      
    }
     
    public boolean CamposColetivoVazio()
    {
        boolean vazio = true;
        if((cliente.getNomeC() != null && cliente.getNomeC().length()>0)&&
          (cliente.getPais() != null && cliente.getPais().length()>0)&&
          (cliente.getLocalizacao() != null && cliente.getLocalizacao().length()>0)&&
          (cliente.getNifC() != null && cliente.getNifC().length()>0)&&
           (cliente.getTelefoneC() != null && cliente.getTelefoneC().length()>0))
        {
            vazio = false;
        }
        return vazio;
    }
    
    public void fecharModal()
    {
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true ); 
        RequestContext.getCurrentInstance().execute("PF('dlg').hide()"); 
        RequestContext.getCurrentInstance().execute("fechar()");  
        Validacao.AtualizarCompoente("s", "tabelaClienteS");
        session.setAttribute("CodigoCliente", cliente.getNumero());
        session.setMaxInactiveInterval(-1);
        RequestContext.getCurrentInstance().execute("prosseguir()");  
    }
    
    public void RecuperarUltimoNumero()
    {
         cliente.setNumero(String.valueOf(clienteDao.lastNumeroCliente()));  
         Validacao.AtualizarCompoente("addClienteformulario", "numeroCliente");
    }
    
    public void DadosCliente()
    {
          System.err.println("entrou");
        if(SessionUtil.obterValor("objCliente") != null)
        {
            cliente = ((Cliente) SessionUtil.obterValor("objCliente"));
          
        }
    }
    
    ////Copiado /***********
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

    public String getPagina() {
        return pagina;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getOrdenar() {
        return ordenar;
    }

    public void setOrdenar(String ordenar) {
        this.ordenar = ordenar;
    }

     public void setListaClientes(List<Cliente> listaClientes) {
         this.listaClientes = listaClientes;
     }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
        listaClientes = clienteDao.filtrarPesquisa(pesquisa, campo,"total", false , 0);
        totalRegistro = listaClientes.size();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void search()
    {
        listaClientes = clienteDao.filtrarPesquisa(pesquisa, campo,"total",false, 0);
        totalRegistro = listaClientes.size();
        Validacao.atualizar("info", "linhas");
        Validacao.atualizar("infoCliente", "totalCliente");
    }
   
    // cliente selecionado
    public void clienteSelecionado()
    {
       ContratoDao contratoDao = new ContratoDao();
       String contrato = null;
       HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
       tipo = cliente.getTipoCliente(); // tipo recebe o tipo de cliente selecionado
       nomeCliente = cliente.getNome(); // nome do cliente selecionado recebe o 
       session.setAttribute("CodigoCliente", cliente.getNumero());
       seguroAdicionar = seguroSelecionado()[0];
       session.setMaxInactiveInterval(-1);
       contrato = contratoDao.totalContrato(cliente.getNumero());
       if(contrato == null)
            RequestContext.getCurrentInstance().addCallbackParam("contrato", "Nenhum contrato em curso");
       else if(Integer.valueOf(contrato)==1)
            RequestContext.getCurrentInstance().addCallbackParam("contrato", "1 contrato");
       else
            RequestContext.getCurrentInstance().addCallbackParam("contrato", contrato+" contratos");
       
       RequestContext.getCurrentInstance().addCallbackParam("selecionado", "sim");
       RequestContext.getCurrentInstance().addCallbackParam("nome", testeStringVazio(cliente.getNome()));
       RequestContext.getCurrentInstance().addCallbackParam("tipoC", testeStringVazio(cliente.getTipoCliente()));
       RequestContext.getCurrentInstance().addCallbackParam("emailClienteT", testeStringVazio(cliente.getEmail()));
        Validacao.callBackParam("nif", testeStringVazio(cliente.getNif()));
        Validacao.callBackParam("telefone", testeStringVazio(cliente.getTelefoneS()));
        Validacao.callBackParam("telemovel", testeStringVazio(cliente.getTelemovelS()));
       try
       {
            InfoCliente();
       }
       catch (SQLException ex)
       {
            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
      if(cliente != null)
      {        
          ResultSet rs = Call.callTableFunction("PACK_PESQUISA.pesqContratoCliente", "*", this.cliente.getNumero());
          //this.controlTabelaContrato.prepareModel(rs, DataTableControl.ShowMode.HIDE, "CLIENTE");
          this.cttTable.updFaces(FacesContext.getCurrentInstance());
          this.cttTable.prepareModel(rs, DataTableControl.ShowMode.HIDE, "CLIENTE", "ID_CONTRATO", "ID_SEGURO");
          
          Object result = ContratoDao.loadApolice(Integer.valueOf(cliente.getNumero()), Integer.valueOf(seguroSelecionado()[1]));
          if(seguroSelecionado()[1].equals("3"))
                result = "";
          
          RequestContext.getCurrentInstance().execute("apoliceContrato('"+((result == null) ? "": result)+"')");
          if(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null)
          {
              SessionUtil.removerParametro(SessionUtil.APOLICE_CONTRATO_SEGURO);
              SessionUtil.definirParametro(SessionUtil.APOLICE_CONTRATO_SEGURO, ((result == null) ? "N" : result));
          }
          else SessionUtil.definirParametro(SessionUtil.APOLICE_CONTRATO_SEGURO, ((result == null) ? "N" : result));
      }
     
    }
    
    public String novoContrato()
    {
        return "GestaoContratos.xhtml?faces-redirect=true";
    }

    public DataTableControl getCttTable() {
        return cttTable;
    }

    public void setCttTable(DataTableControl cttTable) {
        this.cttTable = cttTable;
    }

    public DataTableControl getMostrarDados() {
        return mostrarDados;
    }

    public void setMostrarDados(DataTableControl mostrarDados) {
        this.mostrarDados = mostrarDados;
    }
    
    
    
    public String[] seguroSelecionado()// pega o nome do seguro de acordo com o código
    {
        String[] seguro = new String[2]; // pega o nome do seguro
        String numeroSeguro;
        
         HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
         if(session.getAttribute("S")==null)//Se a sessão com o codigo S for nulo, siginfica que nehum seguro foi selecionad
             seguro[0] ="Nenhum seguro";
         else
         {
             seguro[1] = session.getAttribute("S").toString().split(";")[1];
             numeroSeguro = session.getAttribute("S").toString().split(";")[0];//se a sessão não for nula pega o codigo do seguro selecionado
            switch(numeroSeguro)
            {
                case "MV":// caso seja MV então o nome do seguro é veiculo
                   seguro[0] ="Seguro de Veiculo";
                   pagina = "Seguro_Automovel.xhtml?faces-redirect=true";
                    break;
                case "TIN":// caso seja TIN então, o nome do seguro é viagem
                    seguro[0] ="Seguro de Viagem";
                     pagina = "includes/Viagem.xhtml?faces-redirect=true";
                    break;
                case "DI":// caso seja DI então, o nome do seguro é roubo
                    seguro[0] ="Seguro de Roubo";
                    pagina = "includes/Viagem.xhtml?faces-redirect=true";
                    break;
                case "GPA":
                    seguro[0] ="Seguro de Acidente para Grupo";
                    pagina = "includes/AcdentePG.xhtml?faces-redirect=true";
                    break;
                case "MAC":
                    seguro[0] = "Seguro de Carga Maritima";
                    pagina = "includes/Seg_Carga_Maritima.xhtml?faces-redirect=true";
                    break;
                 case "NHI":
                    seguro[0] = "Seguro Maritimo";
                    pagina = "Seguro_Maritimo.xhtml?faces-redirect=true";
                    break;
                 case "FR":
                    seguro [0]= "Seguro de Incendio";
                    pagina = "includes/Seg_Incendio.xhtml?faces-redirect=true";
                    break;
                 case "RP":
                    seguro[0] = "Seguro de Resposabildade Publica";
                    pagina = "includes/seguroResPublica.xhtml?faces-redirect=true";
                    break;
            }
         }
                
         return seguro;
    }
    // redireciona para a pagina do seguro selecionado
    public String redirecionar()
    {         
//        seguroSelecionado()[0];//devolve o nome do seguro selecionado
        return pagina;// retorna a pagina do seguro selecionado
    }
    
    public void ContratoCliente()
    {
        ContratoDao contratoDao = new ContratoDao();
        
    }
    
    /**
     * Método que recupera as informações do cliente selecionado e coloca como parametro de resposta
     * para ser usado em jquery
     * @throws java.sql.SQLException
     */
    public void InfoCliente() throws SQLException
    {
        String[] nome;        
           if(SessionUtil.obterValor("typeCustomer")!= null)
                      SessionUtil.removerParametro("typeCustomer");
       if(SessionUtil.obterValor("documento") != null)
            SessionUtil.removerParametro("documento");
       if(SessionUtil.obterValor("numDocumento") != null)
             SessionUtil.removerParametro("numDocumento");
       if(SessionUtil.obterValor("numCliente")!= null)
       {
           SessionUtil.removerParametro("numCliente");
           SessionUtil.definirParametro("numCliente",  cliente.getNumero());
       }
       if(SessionUtil.obterValor("apelidoCliente") != null)
           SessionUtil.removerParametro("apelidoCliente");
        if(SessionUtil.obterValor("paisColetivoCliente")!=null)
             SessionUtil.removerParametro("paisColetivoCliente");
        if(SessionUtil.obterValor("nifColetivoCliente")!=null)
            SessionUtil.removerParametro("nifColetivoCliente");
  
        switch(cliente.getTipoCliente())
        {
            case "Pessoa":
            {
               nome = cliente.getNome().split(" ");
                getCliente().setTipoCliente("Singular");
                SessionUtil.definirParametro("typeCustomer", "Singular");
                getCliente().setNomeS(nome[0]);
                getCliente().setApelido(cliente.getApelido());
                SessionUtil.definirParametro("apelidoCliente",  cliente.getApelido());
                getCliente().setLocalTrabalho(cliente.getLocalTrabalho());
                getCliente().setProfissao(cliente.getProfissao());
                getCliente().setEmailS((cliente.getEmail() == null || cliente.getEmail().equals("")) ? "" : cliente.getEmail());
                getCliente().setTelefoneS(cliente.getTelefoneS());
                getCliente().setTelemovelS(cliente.getTelemovelS()); 
                getCliente().setNifS(cliente.getNif());
               SessionUtil.definirParametro("numCliente",  cliente.getNumero());
                getCliente().setSexo(clienteDao.DevolverValor("GEN_ID", "GEN_DESC", "T_GENDER",cliente.getSexo()));
                getCliente().setMorada(cliente.getMorada());
                getCliente().setEstadoCivil(clienteDao.DevolverValor("SC_ID", "SC_DESC", "T_STATECIVIL",
                         clienteDao.InfoCliente(cliente.getNumero()).getEstadoCivil()));
                getCliente().setNacionalidade(clienteDao.DevolverValor("PAIS_ID", "PAIS_NOME", "T_PAIS", cliente.getPais()));
                getCliente().setTipoDocumento(clienteDao.InfoCliente(cliente.getNumero()).getDocumento());
               
                 getCliente().setDocumento(clienteDao.DevolverValor("REALID", "DOCUMENTO", "VER_TIPODOCUMENTO",
                         clienteDao.InfoCliente(cliente.getNumero()).getDocumento()));
                 SessionUtil.definirParametro("documento",  cliente.getDocumento());
                 getCliente().setNumeroDocumento(clienteDao.InfoCliente(cliente.getNumero()).getNumeroDocumento());
                    SessionUtil.definirParametro("numDocumento",  cliente.getNumeroDocumento());
                 getCliente().setDataNascimento(cliente.getDataNascimento());
                 atualizarComponentes();
            }
            break;
            case "Empresa":
            {
              getCliente().setTipoCliente("Coletivo");
                 SessionUtil.definirParametro("typeCustomer", "Empresa");
                getCliente().setNomeC(cliente.getNome());
                getCliente().setEmailC((cliente.getEmail() == null) ? "" : cliente.getEmail());
                getCliente().setTelefoneC(cliente.getTelefoneS());
                getCliente().setTelemovelC(cliente.getTelemovelS()); 
                getCliente().setNifC(cliente.getNif());
                  SessionUtil.definirParametro("nifColetivoCliente", cliente.getNif());
                getCliente().setFunção(clienteDao.InfoCliente(cliente.getNumero()).getFunção());
                getCliente().setResponsavel(clienteDao.InfoCliente(cliente.getNumero()).getResponsavel());
                getCliente().setPais(clienteDao.DevolverValor("PAIS_ID", "PAIS_NOME", "T_PAIS", cliente.getPais()));
                SessionUtil.definirParametro("paisColetivoCliente", cliente.getPais());
                getCliente().setRazaoSocial(cliente.getRazaoSocial());
                getCliente().setLocalizacao(clienteDao.InfoCliente(cliente.getNumero()).getLocalizacao());
                atualizarComponentes();
            }
            break;
        } 
    }
    
    public void GestaoClientesInfo(int p)
    {
        ContratoDao contratoDao = new ContratoDao();
        String contrato = null;
    
        RequestContext.getCurrentInstance().addCallbackParam("selecionado", "true");
        RequestContext.getCurrentInstance().addCallbackParam("tipoClienteSelecionado", testeStringVazio(cliente.getTipoCliente()));
        RequestContext.getCurrentInstance().addCallbackParam("nomeCliente", testeStringVazio(cliente.getNome()));
        System.out.println("entrou");
        contrato = contratoDao.totalContrato(cliente.getNumero());
       if(contrato == null)
            RequestContext.getCurrentInstance().addCallbackParam("contratoCliente", "Nenhum contrato em curso");
       else if(Integer.valueOf(contrato)==1)
               RequestContext.getCurrentInstance().addCallbackParam("contrato", "1 contrato");
       else
              RequestContext.getCurrentInstance().addCallbackParam("contrato", contrato+" contratos"); 
    }
    
    public void info()
    {
  
        Validacao.callBackParam("nomeClienteSelecionado", testeStringVazio(cliente.getNome()));
        Validacao.callBackParam("tipoClienteS", testeStringVazio(cliente.getTipoCliente())) ;    
        Validacao.callBackParam("nifCliente", testeStringVazio(cliente.getNif()));
        Validacao.callBackParam("telefoneCliente", testeStringVazio(cliente.getTelefoneS()));
        Validacao.callBackParam("telemovelCliente", testeStringVazio(cliente.getTelemovelS()));
        try
        {
            InfoCliente();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void dadosEnviados()
    {
      if(Validacao.paginaAtual().contains("GestSeg_Clientes.xhtml"))     
          editar();
      else
           adicionarCliente();    
    }
    public void editar()
    {
         if(SessionUtil.obterValor("typeCustomer").toString().equals("Singular"))
         {
              if((cliente.getSexo() != null && !cliente.getSexo().equals("")) &&(cliente.getTelefoneS() != null && !cliente.getTelefoneS().equals("")) && validarEmail() == true)
              {
                     clienteDao.atualizarCliente(cliente);   
                     cliente.setEmailS("");
                     listaClientes = clienteDao.filtrarPesquisa(null, null,"total", false, 0);
                     Mensagem.addInfoMsg("Informações atualizadas com sucesso!");
                     Validacao.AtualizarCompoente("geriCliente", "tabelaGestaoCliente");
                     Validacao.AtualizarCompoente("geriCliente", "atualizarClienteGrowl");
                     RequestContext.getCurrentInstance().execute("clienteAtualizado()");
              }
         }
         else
         {
             if((cliente.getLocalizacao() != null && !cliente.getLocalizacao().equals(""))
                     && (cliente.getRazaoSocial() != null && !cliente.getRazaoSocial().equals(""))
                     && (cliente.getTelefoneC() != null && !cliente.getTelefoneC().equals("")) && validarEmail() == true)
             {
                 clienteDao.atualizarCliente(cliente);   
                 listaClientes = clienteDao.filtrarPesquisa(null, null, "total", false , 0);
                 Mensagem.addInfoMsg("Informações atualizadas com sucesso!");
                 Validacao.AtualizarCompoente("geriCliente", "tabelaGestaoCliente");
                 Validacao.AtualizarCompoente("geriCliente", "atualizarClienteGrowl");
                 RequestContext.getCurrentInstance().execute("clienteAtualizado()");;
             }
         }
    }
   
    public void atualizarComponentes()
   {

       switch(cliente.getTipoCliente())
       {
           case "Singular":   
                Validacao.AtualizarCompoente("addClienteformulario", "clienteNomeS");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteApelido");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteLocalT");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteProfissao");
                Validacao.AtualizarCompoente("addClienteformulario", "emailCliS");
                Validacao.AtualizarCompoente("addClienteformulario", "numeroCliente");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteSexo");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteMorada");
                Validacao.AtualizarCompoente("addClienteformulario", "nacionalidadeCliente");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteEstadoCivil");
                Validacao.AtualizarCompoente("addClienteformulario", "documentoCliente");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteDataNasc");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteS");
            break;
           case "Coletivo":
                Validacao.AtualizarCompoente("addClienteformulario", "clienteNomeC");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteEmailC");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteRazao");
                Validacao.AtualizarCompoente("addClienteformulario", "clienteResponsavel");
                Validacao.AtualizarCompoente("addClienteformulario", "funcaoCliente");
                Validacao.AtualizarCompoente("addClienteformulario", "numeroCliente");
                Validacao.AtualizarCompoente("addClienteformulario", "paisCliente");
                Validacao.AtualizarCompoente("addClienteformulario", "localizacaoCliente");
           break;
       }
   }
    
    public String testeStringVazio(String test)
    {
        return ((test==null||test.equals(""))?"":test);
    }
    
    public void reportCliente(int i)
    {
         clienteDao.filtrarPesquisa(null, null, "total", true , i);
    }
}
