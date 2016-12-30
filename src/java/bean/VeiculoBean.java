
package bean;

import Export.ListaVeiculo;
import dao.ContratoDao;
import dao.SeguroDao;
import dao.VeiculoDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Veiculo;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

@ManagedBean
@ViewScoped
public class VeiculoBean implements Serializable
{
   
    private static final long serialVersionUID = 1L;
    private List<Veiculo> info = new ArrayList<>();
    private List<String> ListaMarca = new ArrayList<>();
    private List<String> ListaModelo = new ArrayList<>();
    private List<String> listaMatriculas = new ArrayList<>();
    private Veiculo veiculo = new  Veiculo();
    private Veiculo veiculoSelected = new  Veiculo();
    private List<String> marcas;
    private List<Veiculo> listagemVeiculos;
    private List<String> modelos;
    private List<ComoBox> moeda;
    private boolean adicionado = false;
    private String tipoC;
    private String mensagem;
    private VeiculoDao veiculoDao;
    @ManagedProperty(value = "#{listaRespostas}")
    private ListaRespostas listaRespostas;
    private SeguroDao sd;
    private String matricula = null;
    private String pesquisaVeiculo;
    
    @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
    public VeiculoBean()
    {
        veiculoDao = new VeiculoDao();
        marcas = new ArrayList<>();
        modelos = new ArrayList<>();
        sd = new SeguroDao();
        moeda = new ArrayList<>();
        marcas.add(0,"");
        marcas = veiculoDao.marcas();
        moeda = veiculoDao.moedas();
        listaMatriculas = veiculoDao.listaMatriculas();
        marcaSelecionada();
        if(SessionUtil.obterValor("MV") != null)
        {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
            VeiculoBean vb = ((VeiculoBean) SessionUtil.obterValor("MV"));
            for (Field f : this.getClass().getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    f.set(this, f.get(vb));
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                }
            }
            RequestContext.getCurrentInstance().execute("backAndcontine()");
        }
        if(Validacao.paginaAtual().contains("GestSeg_Veiculos"))
             listagemVeiculos = veiculoDao.listagemVeiculos(null);
    }

    public VeiculoBean(String tipoC) {
        
    }

    public boolean isAdicionado() {
        return adicionado;
    }

    public String getMensagem() {
        return mensagem;
    }
   
    public Veiculo getVeiculo() 
    {
        return (veiculo == null) ? veiculo = new Veiculo() : veiculo;
    }

    public List<ComoBox> getMoeda() {
        return moeda;
    }

    public void setMoeda(List<ComoBox> moeda) {
        this.moeda = moeda;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public ListaRespostas getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(ListaRespostas listaRespostas) {
        this.listaRespostas = listaRespostas;
    }

    public List<String> getListaMarca() {
        return ((ListaMarca==null)? ListaMarca = new ArrayList<>():ListaMarca);
    }

    public void setListaMarca(List<String> ListaMarca) {
        this.ListaMarca = ListaMarca;
    }

    public List<String> getListaModelo() {
        return ((ListaModelo==null)? ListaModelo = new ArrayList<>():ListaModelo);
    }

    public void setListaModelo(List<String> ListaModelo) {
        this.ListaModelo = ListaModelo;
    }
    
    public void carregarModelo()
    {
        getVeiculo().getMarca();
    }

    public Veiculo getVeiculoSelected() {
        return ((veiculoSelected==null)? veiculoSelected= new Veiculo():veiculoSelected);
    }

    public void setVeiculoSelected(Veiculo veiculoSelected) {
        this.veiculoSelected = veiculoSelected;
    }
    
    public void coberturaSelecionada()
    {
        switch(veiculo.getTipoCobertura())
        {
            case "41":
            {
                RequestContext.getCurrentInstance().addCallbackParam("cobertura", "contra terceiros");
                tipoC = "terceiros";
                info.clear();
            }
            break;
            case "42":
            {
                RequestContext.getCurrentInstance().addCallbackParam("cobertura", "Todos os riscos");
                tipoC = "todos riscos";
                info.clear();
            }
            break;
            case "43":
            {
                RequestContext.getCurrentInstance().addCallbackParam("cobertura", "Compreensivo ilimitado");
                tipoC = "compreensivo";
                info.clear();
            }
            break;
        }
    }

    public String getTipoC() {
        return tipoC;
    }

    public List<Veiculo> getInfo() {
        return info;
    }

    public void setInfo(List<Veiculo> info) {
        this.info = info;
    }
     
   public void calcularValorPremio()
   {
       double valor = 0, taxa = 0;
       switch(veiculo.getTipoCobertura())
       {
           case "42":
           {
               if(veiculo.getLimiteResp() != null && veiculo.getLimiteResp().length()>0 && (veiculo.getValorAtual() != null && veiculo.getValorAtual().length()>0))
               {
                    taxa = Double.valueOf(veiculo.getLimiteResp())/100;
                   if(Double.valueOf(veiculo.getLimiteResp()) <= 100)
                   {
                       taxa = Double.valueOf(veiculo.getLimiteResp())/100;
                        valor = taxa * Double.valueOf(veiculo.getValorAtual());
                        veiculo.setValorPremio(String.valueOf(valor));
                        RequestContext.getCurrentInstance().addCallbackParam("valorP", valor);
                   }
                   else
                   {
                       veiculo.setValorPremio("");
                       RequestContext.getCurrentInstance().addCallbackParam("valorP", "nulo");
                   }
               }
           }
       }
   }
   
   public void moreInfo(Veiculo v)
   {
       veiculoSelected = v;
       RequestContext.getCurrentInstance().update("veiculoFormulario:dataInfo");    
       RequestContext.getCurrentInstance().execute("mostrarModalInfo()");
   }
   
   public void addTable()
   {
       System.out.println("sigla "+veiculo.getMoeda());
       FacesContext facesContext = FacesContext.getCurrentInstance();
        if(veiculo.getTipoCobertura().equals("42"))
           {
               if(validar() == true && verificarNumeroRegistro() == true &&
                       verificarNumeroMatricula()== false && 
                       verificarNumeroMotor() == false &&
                       verificarNumeroChassi() == false &&
                       ValidarAnoFabrico(null) == true && 
                       validaAnoCompra() == true)
               {
                   System.err.println("entrou");
                    info.add(new Veiculo(veiculo.getNumeroMatricula(),veiculo.getModelo(),veiculo.getMarca(),veiculo.getNumMotor(),
                    veiculo.getChassi(), veiculo.getAnoFabrico(), veiculo.getAnoCompra(),veiculo.getCapacidade(), veiculo.getValorAtual(), veiculo.getValorPremio(),veiculo.getValorAtual(),veiculo.getValorCompra(), veiculo.getCertificado()));
                    calcular();
                    veiculo.setSigla(Validacao.Sigla(veiculo.getMoeda()));
                     SessionUtil.definirParametro("MV", this);
                    mensagem = "adicionado";
                     Validacao.AtualizarCompoente("veiculoFormulario", "adicionado");
                    Validacao.AtualizarCompoente("veiculoFormulario", "tabelaVeiculo");
                    RequestContext.getCurrentInstance().execute("limparCampos('TR')");  
                    RequestContext.getCurrentInstance().addCallbackParam("veiculo", "adicionadoTR");
                    
               }
              
           }
           else
               if(veiculo.getTipoCobertura().equals("41"))
                {
                    
                    if(validar() == true && verificarNumeroRegistro() == true &&
                       verificarNumeroMatricula()== false && 
                       verificarNumeroMotor() == false &&
                       verificarNumeroChassi() == false &&
                       ValidarAnoFabrico(null) == true && 
                       validaAnoCompra() == true)
                    {
                        info.add(new Veiculo(veiculo.getNumeroMatricula(), veiculo.getModelo(),veiculo.getMarca(),veiculo.getNumMotor(),
                        veiculo.getChassi(), veiculo.getAnoFabrico(), veiculo.getAnoCompra(),veiculo.getCapacidade(), veiculo.getValorAtual(),
                                veiculo.getValorPremio(),veiculo.getLimiteResp(),veiculo.getValorCompra(),veiculo.getCertificado()));
                        calcular();
                        veiculo.setSigla(Validacao.Sigla(veiculo.getMoeda()));
                        SessionUtil.definirParametro("MV", this);
                        mensagem = "adicionado";
                         Validacao.AtualizarCompoente("veiculoFormulario", "adicionado");
                        Validacao.AtualizarCompoente("veiculoFormulario", "tabelaVeiculo");
                        RequestContext.getCurrentInstance().execute("limparCampos('terceiros')");   
                    }
              
                  
                }
           else
               {
                   if(validar() == true && verificarNumeroRegistro() == true &&
                       verificarNumeroMatricula()== false && 
                       verificarNumeroMotor() == false &&
                       verificarNumeroChassi() == false &&
                       ValidarAnoFabrico(null) == true && 
                       validaAnoCompra() == true)
                   {
                        info.add(new Veiculo(veiculo.getNumeroMatricula(), veiculo.getModelo(),veiculo.getMarca(),veiculo.getNumMotor(),
                        veiculo.getChassi(), veiculo.getAnoFabrico(), veiculo.getAnoCompra(),veiculo.getCapacidade(), veiculo.getValorAtual(), veiculo.getValorPremio(),veiculo.getLimiteResp(),veiculo.getValorCompra(),veiculo.getCertificado()));
                        calcular();
                        veiculo.setSigla(Validacao.Sigla(veiculo.getMoeda()));
                        SessionUtil.definirParametro("MV", this);
                        mensagem = "adicionado";
                         Validacao.AtualizarCompoente("veiculoFormulario", "adicionado");
                        Validacao.AtualizarCompoente("veiculoFormulario", "tabelaVeiculo");
                        RequestContext.getCurrentInstance().execute("limparCampos('c')");     
                   }   
               }
   }

    @SuppressWarnings("ConvertToStringSwitch")
   public void calcular()
   {
       System.out.println(veiculo.toString());
       float percentagemRetirar = 100-(5+0.6f+2.5f);
       @SuppressWarnings("UnusedAssignment")
       double premioBruto = 0, premioLiquido = 0, limiteResponsabilidade = 0;
       if(veiculo.getTipoCobertura().equals("41"))
       {
           for(Veiculo veiculo1 : info)
           {
               premioLiquido += Double.valueOf(veiculo1.getValorPremio());
           }
             premioBruto = (percentagemRetirar * premioLiquido)/100;
           veiculo.setPremioBruto(String.valueOf(premioBruto));
           veiculo.setPremioBrutoMoeda(Moeda.format(premioBruto));
           veiculo.setPremioLiquido(String.valueOf(premioLiquido));
           veiculo.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
           veiculo.setTotalSeguradoFormatado("Ilimitado");
           
       }
       else
           if(veiculo.getTipoCobertura().equals("42"))
           {
                for(Veiculo veiculo1 : info)
                {
                     premioLiquido += Double.valueOf(veiculo1.getValorPremio());
                    limiteResponsabilidade += Double.valueOf(veiculo.getValorAtual());
                }
                premioBruto = (percentagemRetirar * premioLiquido)/100;
                veiculo.setPremioBruto(String.valueOf(premioBruto));
                veiculo.setPremioBrutoMoeda(Moeda.format(premioBruto));
                veiculo.setPremioLiquido(String.valueOf(premioLiquido));
                veiculo.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
                veiculo.setLimiteResp(String.valueOf(limiteResponsabilidade));
                veiculo.setTotalSeguradoFormatado(Moeda.format(limiteResponsabilidade));
           }
       else
            {
                for(Veiculo veiculo1 : info)
                {
                        premioLiquido += Double.valueOf(veiculo1.getValorPremio());
                    limiteResponsabilidade += Double.valueOf(veiculo.getLimiteResp());
                }
               premioBruto = (percentagemRetirar * premioLiquido)/100;
                veiculo.setPremioBruto(String.valueOf(premioBruto));
                veiculo.setPremioBrutoMoeda(Moeda.format(premioBruto));
                veiculo.setPremioLiquido(String.valueOf(premioLiquido));
                veiculo.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
                veiculo.setLimiteResp(String.valueOf(limiteResponsabilidade));
                veiculo.setTotalSeguradoFormatado(Moeda.format(limiteResponsabilidade));
           }
       
       System.out.println(veiculo.toString());
   }

    public List<String> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<String> marcas) {
        this.marcas = marcas;
    }

    public List<String> getModelos() {
        return modelos;
    }

    public void setModelos(List<String> modelos) {
        this.modelos = modelos;
    }
   
   public void remover(Veiculo veiculo)
   {
       veiculoSelected = veiculo;
       info.remove(getVeiculoSelected());
       if(info.size()==0)
       {
           mensagem ="vazio";
          Validacao.AtualizarCompoente("veiculoFormulario", "adicionado");
       }
       Validacao.AtualizarCompoente("veiculoFormulario", "tabelaVeiculo");
   }
   
   public void removeAll()
   {
       info.clear();
       mensagem ="vazio";
       Validacao.AtualizarCompoente("veiculoFormulario", "adicionado");
       Validacao.AtualizarCompoente("veiculoFormulario", "tabelaVeiculo");
   }
   
   public boolean verificarNumeroRegistro()
   {
        boolean existe = true;
        FacesContext facesContext = FacesContext.getCurrentInstance();
       if(veiculo.getNumeroRegistro() != null && veiculo.getNumeroRegistro().length() >0)
       {
           existe = ContratoDao.isNumRegistroVago(veiculo.getNumeroRegistro());
           if(existe == false)
           {
               Mensagem.addErrorMsg("Número de registro já existe");
               RequestContext.getCurrentInstance().update("veiculoFormulario:info");
               RequestContext.getCurrentInstance().addCallbackParam("numeroR", "já existe");
           }
       }
       return existe;
   }
   
   public boolean verificarNumeroMatricula()
   {
       boolean existe = false;
       if(veiculo.getNumeroMatricula() != null && veiculo.getNumeroMatricula().length()>0)
       {
           for(Veiculo veiculo1: info)
            {
                if(veiculo.getNumeroMatricula().equals(veiculo1.getNumeroMatricula()))
                {
                    Mensagem.addErrorMsg("Número de registro automóvel já existe");
                    RequestContext.getCurrentInstance().update("veiculoFormulario:info");
                    RequestContext.getCurrentInstance().addCallbackParam("matricula","já existe");
                    existe = true;
                }
            }
       }
       return existe; 
   }
   
   public boolean verificarNumeroMotor()
   {
       boolean existe = false;
       if(veiculo.getNumMotor() != null && veiculo.getNumMotor().length()>0)
       {
           for(Veiculo veiculo1: info)
            {
                if(veiculo.getNumMotor().equals(veiculo1.getNumMotor()))
                {
                    Mensagem.addErrorMsg("Número de motor já existe");
                    RequestContext.getCurrentInstance().update("veiculoFormulario:info");
                    RequestContext.getCurrentInstance().addCallbackParam("motor","já existe");
                    existe = true;
                }
            }
       }
       return existe; 
   }
   
   public boolean verificarNumeroChassi()
   {
       boolean existe = false;
       if(veiculo.getChassi() != null && veiculo.getChassi().length()>0)
       {
           for(Veiculo veiculo1: info)
            {
                if(veiculo.getChassi().equals(veiculo1.getChassi()))
                {
                    Mensagem.addErrorMsg("Número de chassi já existe");
                    RequestContext.getCurrentInstance().update("veiculoFormulario:info");
                    RequestContext.getCurrentInstance().addCallbackParam("chassi","já existe");
                    existe = true;
                }
            }
       }
       return existe; 
   }

   public boolean validaAnoCompra()
   {
       boolean valido = true;
       Date d = new Date();
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       String ano = dateFormat.format(d).substring(6, 10);
       @SuppressWarnings("UnusedAssignment")
       double valorAnoCompra = 0, valorAnoF = 0;
       if(((veiculo.getAnoCompra() != null && veiculo.getAnoCompra().length()>0)) &&
          (veiculo.getAnoFabrico() != null && veiculo.getAnoFabrico().length()>0))
       {
           valorAnoCompra = Integer.valueOf(veiculo.getAnoCompra());
           valorAnoF = Integer.valueOf(veiculo.getAnoFabrico());
           if(valorAnoCompra<valorAnoF || valorAnoCompra>Integer.valueOf(ano))
           {
               valido = false;
               System.out.println("invalido");
               RequestContext.getCurrentInstance().execute("validarAnoCompraInvalido()");
           }
       }
       return valido;
   }
   
   public void avancar()
   {
        RequestContext.getCurrentInstance().execute("veiculoSeguinte()");
       if(info.size()>0)
           SessionUtil.definirParametro("respostas", listaRespostas);
   }
   
    public void marcaSelecionada()
    {
     if(veiculo.getMarca() != null && veiculo.getMarca().length()>0)
     {
         modelos = veiculoDao.MarcaModelo(veiculo.getMarca());
         RequestContext.getCurrentInstance().update("veiculoFormulario:veiculoModelo");
     }
     else{
         modelos = veiculoDao.MarcaModelo("");
         RequestContext.getCurrentInstance().update("veiculoFormulario:veiculoModelo");
     }
     modelos.add(0, "");
 }
 
    public String anterior()
    {
        System.out.println("entrou");
        return "GestSeg_NovoSeguroApolice.xhtml?faces-redirect=true";
    }
    
    public boolean ValidarAnoFabrico(String param)
    {
        Date d = new Date();
        boolean valido = false;
        int diferencaAno = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ano = dateFormat.format(d);
        String anoAtual = ano.substring(6, 10);
        diferencaAno = Integer.valueOf(anoAtual)-100;
        if(veiculo.getAnoFabrico() != null && veiculo.getAnoFabrico().length()>0)
        {
            if(Integer.valueOf(veiculo.getAnoFabrico())>=diferencaAno && 
                    (Integer.valueOf(veiculo.getAnoFabrico()) <=Integer.valueOf(anoAtual)))
            {
                valido = true;
            }
            else
            {
                RequestContext.getCurrentInstance().execute("validarAnofabrico()");
            }
        }
        else
        {
            valido = true;
        }
        return valido;
    }
    
    public boolean verificarAnoCompra(String param)
    {
        boolean valido = true;
        if(veiculo.getAnoCompra() != null && veiculo.getAnoCompra().length()>0 && (veiculo.getAnoFabrico() != null && veiculo.getAnoFabrico().length()>0))
        {
            if(Integer.valueOf(veiculo.getAnoCompra())<Integer.valueOf(veiculo.getAnoFabrico()))
            {
                valido = false;
                RequestContext.getCurrentInstance().addCallbackParam("anoCompra", "invalido");
            }       
        }
        return valido;
    }
    
    public void validarCapacidade(String param)
    {
        if(veiculo.getCapacidade() != null && veiculo.getCapacidade().length()>0)
        {
            if(Integer.valueOf(veiculo.getCapacidade())>253)
            {
                RequestContext.getCurrentInstance().execute("CapacidadeInValido()");
            }
        }
    }
    
    public boolean validar()
    {
        boolean valido = false;
        System.out.println("entrou veiculo");
        switch (veiculo.getTipoCobertura()) {
            case "41":
            {
                if((veiculo.getNumeroRegistro() !=null && veiculo.getNumeroRegistro().length()>0)&&
                  (veiculo.getNumeroMatricula() != null && veiculo.getNumeroMatricula().length()>0)&&
                   (veiculo.getChassi() != null && veiculo.getChassi().length()>0) &&
                   (veiculo.getCertificado() != null && veiculo.getCertificado().length()>0) &&
                    (veiculo.getCapacidade() != null && veiculo.getCapacidade().length()>0)&&
                     (veiculo.getValorPremio() != null && veiculo.getValorPremio().length()>0))
                {
                    valido = true;
                }
            }
            break;
            default:
                 if((veiculo.getNumeroRegistro() !=null && veiculo.getNumeroRegistro().length()>0)&&
                  (veiculo.getNumeroMatricula() != null && veiculo.getNumeroMatricula().length()>0)&&
                   (veiculo.getModelo() != null && veiculo.getModelo().length()>0)&&
                    (veiculo.getMarca() != null && veiculo.getMarca().length()>0)&&
                   (veiculo.getChassi() != null && veiculo.getChassi().length()>0) &&
                   (veiculo.getCertificado() != null && veiculo.getCertificado().length()>0) &&
                    (veiculo.getCapacidade() != null && veiculo.getCapacidade().length()>0)&&
                     (veiculo.getValorPremio() != null && veiculo.getValorPremio().length()>0)&&
                     (veiculo.getLimiteResp() != null && veiculo.getLimiteResp().length()>0)&&
//                      (veiculo.getAnoFabrico() != null && veiculo.getAnoFabrico().length()>0)&&
//                     (veiculo.getAnoCompra() != null && veiculo.getAnoCompra().length()>0)&&
                     (veiculo.getValorCompra() != null && veiculo.getValorCompra().length()>0)&&
                     (veiculo.getValorAtual() != null && veiculo.getValorAtual().length()>0))
                {
                    valido = true;
                }
                break;
        }
        return valido;
    }
    
   public List<String>completarListaMarca(String info)
   {    
       marcas.set(0, info);
       return likeStart(this.marcas, info);      
   }
   
   public List<String>completarListaMatricula(String info)
   {    
       listaMatriculas.set(0, info);
       return likeStart(this.listaMatriculas, info);       
   }
   
   public void carregarVeiculo()
   {
      veiculoDao.loadInfoVeiculo(veiculo.getNumeroMatricula());
      
   }
   public List<String>completarListaModelo(String info)
   { 
       modelos.set(0, info);
       return likeStart(this.modelos, info);
        
   }
   
   public static ArrayList<String> likeStart(List<String> list, String like)
    {
        if(like == null || list == null) return null;
        ArrayList<String> likeList = new ArrayList<>();
        if(like.length() == 0)
        {
            likeList.addAll(list);
            return likeList;
        }
        String aux;
        for(String s: list)
        {
            aux = s;
            if(s.length() >= like.length())
            {
                s=s.substring(0, like.length());
                if(s.toUpperCase().contains(like.toUpperCase()))
                {
                    likeList.add(aux);
                }
            }
            
        }
        
        return likeList;
    }
   
   public void dadosEnviados()
   {
       FacesContext context = FacesContext.getCurrentInstance();
       String apolice = context.getExternalContext().getRequestParameterMap().get("apolice");
       String numeroR = context.getExternalContext().getRequestParameterMap().get("numero registro");
       String moeda = context.getExternalContext().getRequestParameterMap().get("moeda");
       String numeroRegistroAutomovel = context.getExternalContext().getRequestParameterMap().get("numeroAutomovel");
       String marca = context.getExternalContext().getRequestParameterMap().get("marca");
       String modelo = context.getExternalContext().getRequestParameterMap().get("modelo");
       String numeroMotor = context.getExternalContext().getRequestParameterMap().get("numMotor");
       String chassi = context.getExternalContext().getRequestParameterMap().get("chassi");
       String anoFabrico = context.getExternalContext().getRequestParameterMap().get("anoFabrico");
       String anoCompra = context.getExternalContext().getRequestParameterMap().get("anoCompra");
       String capacidade = context.getExternalContext().getRequestParameterMap().get("capacidade");
       String valorCompra = context.getExternalContext().getRequestParameterMap().get("valorCompra");
       String valorAtual = context.getExternalContext().getRequestParameterMap().get("valorAtual");
       String limite = context.getExternalContext().getRequestParameterMap().get("limiteResp");
       String valorPremio = context.getExternalContext().getRequestParameterMap().get("valorPremio");
       String certificado = context.getExternalContext().getRequestParameterMap().get("certificado");
       getVeiculo().setNumeroApolice(apolice);
       getVeiculo().setNumeroRegistro(numeroR);
       getVeiculo().setMoeda(moeda);
       getVeiculo().setNumeroMatricula(numeroRegistroAutomovel);
       getVeiculo().setMarca(marca);
       getVeiculo().setModelo(modelo);
       getVeiculo().setNumMotor(numeroMotor);
       getVeiculo().setChassi(chassi);
       getVeiculo().setAnoFabrico(anoFabrico);
       getVeiculo().setAnoCompra(anoCompra);
       getVeiculo().setCapacidade(capacidade);
       getVeiculo().setValorCompra(valorCompra);
       getVeiculo().setValorAtual(valorAtual);
       getVeiculo().setLimiteResp(limite);
       getVeiculo().setValorPremio(valorPremio);
       getVeiculo().setCertificado(certificado);
       
        if(!veiculo.getNumeroApolice().equals("") && veiculo.getNumeroApolice() != null)
            this.addTable();
   }
      
   public void editar()
   {
       RequestContext.getCurrentInstance().execute("editar('"+veiculoSelected.getNumeroMatricula()+"','"+veiculoSelected.getChassi()+"','"+veiculoSelected.getValorCompra()+"','"+veiculoSelected.getMarca()+"','"+veiculoSelected.getAnoFabrico()+"','"+veiculoSelected.getValorAtual()+"','"+veiculoSelected.getModelo()+"','"+veiculoSelected.getAnoCompra()+"','"+veiculoSelected.getLimiteResp()+"','"+veiculoSelected.getNumMotor()+"','"+veiculoSelected.getCapacidade()+"','"+veiculoSelected.getValorPremio()+"')");
       this.info.remove(veiculoSelected);
       Validacao.AtualizarCompoente("veiculoFormulario", "tabelaVeiculo");
   }

    public List<Veiculo> getListagemVeiculos() {
        return listagemVeiculos;
    }

    public String getPesquisaVeiculo() {
        return pesquisaVeiculo;
    }

    public void setPesquisaVeiculo(String pesquisaVeiculo) {
        this.pesquisaVeiculo = pesquisaVeiculo;
    }
    
    private boolean validarAnos()
    {
        if((veiculo.getAnoFabrico() != null && !veiculo.getAnoFabrico().equals("")) && (veiculo.getAnoCompra() != null && !veiculo.getAnoCompra().equals("")))
        {
            if(Integer.valueOf(veiculo.getAnoCompra())<Integer.valueOf(veiculo.getAnoFabrico()))
            {
                Message.addErrorMsg("Ano de Compra não pode ser inferior ao Ano de Fabrico", "regVeiculoForm", "regVeiculoGrowl");
                return false;
            }
            else return true;
        }
        else return true;
    }
    
    public void dadosRegistroVeiculo()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        getVeiculo().setNumeroMatricula(facesContext.getExternalContext().getRequestParameterMap().get("matricula"));
        getVeiculo().setChassi(facesContext.getExternalContext().getRequestParameterMap().get("chassi"));
        getVeiculo().setMarca(facesContext.getExternalContext().getRequestParameterMap().get("marca"));
        getVeiculo().setModelo(facesContext.getExternalContext().getRequestParameterMap().get("modelo"));
        getVeiculo().setNumMotor(facesContext.getExternalContext().getRequestParameterMap().get("motor"));
        getVeiculo().setAnoFabrico(facesContext.getExternalContext().getRequestParameterMap().get("anoFabrico"));
        getVeiculo().setAnoCompra(facesContext.getExternalContext().getRequestParameterMap().get("anoCompra"));
        getVeiculo().setCapacidade(facesContext.getExternalContext().getRequestParameterMap().get("capacidade"));
        registrarVeiculo();
    }
   private void registrarVeiculo()
   {
        if(matricula == null)
        {
            if(veiculoDao.matriculaIsValid(veiculo.getNumeroMatricula()) == true)
            {
                if(validarAnos() == true)
                {
                    String result = veiculoDao.registrarVeiculo(veiculo, null);
                    if(result.equals("true"))
                    {
                        Message.addInfoMsg("Veiculo registrado com sucesso", "regVeiculoForm", "regVeiculoGrowl");
                        RequestContext.getCurrentInstance().execute("$('.regVeiculoLimpar').css('border','');$('.regVeiculoLimpar').val('');");
                        RequestContext.getCurrentInstance().execute("veiculoRegistrado()");
                        RequestContext.getCurrentInstance().execute("$('.pageModal').addClass('fecharModalV');");
                        listagemVeiculos = veiculoDao.listagemVeiculos(null);
                        Validacao.atualizar("regVeiculoTableForm", "regVeiculoTable");
                    } 
                }
            }
            else
            {
                Message.addErrorMsg("Matricula "+veiculo.getNumeroMatricula()+" já existe!", "regVeiculoForm", "regVeiculoGrowl");
                RequestContext.getCurrentInstance().execute("$('.regVeiculoNumMatricula').focus();");
            }
        }
        else
        {
       
            if(validarAnos() == true)
            {
                String result = veiculoDao.registrarVeiculo(veiculo, null);
                if(result.equals("true"))
                {
                    Message.addInfoMsg("Veiculo atualizado com sucesso", "regVeiculoForm", "regVeiculoGrowl");
                    RequestContext.getCurrentInstance().execute("$('.regVeiculoLimpar').css('border','');$('.regVeiculoLimpar').val('');");
                    RequestContext.getCurrentInstance().execute("veiculoRegistrado()");
                    RequestContext.getCurrentInstance().execute("$('.pageModal').addClass('fecharModalV');");
                    listagemVeiculos = veiculoDao.listagemVeiculos(null);
                    matricula = null;
                    Validacao.atualizar("regVeiculoTableForm", "regVeiculoTable");
                } 
            }
        }
   }
   
   
   public void pesquisarVeiculo()
   {
       if(pesquisaVeiculo == null || pesquisaVeiculo.equals(""))
            listagemVeiculos = veiculoDao.listagemVeiculos(null);  
       else
            listagemVeiculos = veiculoDao.listagemVeiculos(pesquisaVeiculo);
       
        Validacao.atualizar("regVeiculoTableForm", "regVeiculoTable");
   }
   
   public void editarVeiculo(Veiculo veiculo)
   {
       matricula =veiculo.getNumeroMatricula();
        RequestContext.getCurrentInstance().execute("editarVeiculo('"+veiculo.getNumeroMatricula()+"','"+veiculo.getChassi()+"','"+veiculo.getMarca()+"',"
                    + "'"+veiculo.getModelo()+"','"+veiculo.getNumMotor()+"','"+veiculo.getAnoFabrico()+"','"+veiculo.getAnoCompra()+"','"+veiculo.getCapacidade()+"')");
   }
   
   public void reportVeiculo(int i)
   {
       if (i == 1) {
           ListaVeiculo.criarDocPdf(listagemVeiculos, SessionUtil.getUserlogado().getNomeAcesso());
       } else {
           ListaVeiculo.criarDocExcel(listagemVeiculos, SessionUtil.getUserlogado().getNomeAcesso());
       }
   }
}