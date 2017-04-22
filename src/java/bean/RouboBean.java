
package bean;

import dao.ContratoDao;
import dao.SeguroDao;
import dao.VeiculoDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Roubo;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean
@ViewScoped
public class RouboBean implements  Serializable
{
    private static final long serialVersionUID = 1L;
    private Roubo roubo = new Roubo();
    private Roubo rouboInfo = new Roubo();
    private List<Roubo> info;
    private SeguroDao sd = new SeguroDao();
    private String numeroR;
    private List<ComoBox> moeda;
    private VeiculoDao veiculoDao = new VeiculoDao();
    private String info1;
    private boolean ativarDesativar = true, ativar1 = false, ativar2 = false, ativar3 = false, ativar4 = false;
    private double valor;
    private String moeda1;
    @ManagedProperty(value = "#{listaRespostas}")
    private ListaRespostas listaRespostas;
    private HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
    
    @SuppressWarnings("LeakingThisInConstructor")
    public RouboBean()
    {
        info = new ArrayList<>();        
        if(SessionUtil.obterValor("DI") != null)
        {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
            RouboBean rpb = ((RouboBean) SessionUtil.obterValor("DI"));
             for (Field f : this.getClass().getDeclaredFields()) {
                 try {
                     f.setAccessible(true);
                     f.set(this, f.get(rpb));
                 } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                 }
             }
             RequestContext.getCurrentInstance().execute("backAndcontine()");
        } 
    }
    
    public RouboBean(String isnull){
        
    }
    
    @PostConstruct
    public void init()
    {
        moeda = new ArrayList<>();
        moeda = veiculoDao.moedas();
    }
    
    public List<Roubo> getInfo() {
        return info;
    }

    public void setInfo(List<Roubo> info) {
        this.info = info;
    }

    public List<ComoBox> getMoeda() {
        return moeda;
    }

    public void setMoeda(List<ComoBox> moeda) {
        this.moeda = moeda;
    }

    public ListaRespostas getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(ListaRespostas listaRespostas) {
        this.listaRespostas = listaRespostas;
    }

    public boolean isAtivar1() {
        return ativar1;
    }

    public boolean isAtivar2() {
        return ativar2;
    }

    public boolean isAtivar3() {
        return ativar3;
    }

    public boolean isAtivar4() {
        return ativar4;
    }
    

    public Roubo getRoubo()
    {
        return (roubo == null) ? roubo = new Roubo() : roubo;
    }

    public void setRoubo(Roubo roubo) 
    {
        this.roubo = roubo;
    }

    
    public void addTable()
    {
                  System.out.println("quantidade ");
        if((roubo.getQuantidade()!= null && !roubo.getQuantidade().equals("")) &&
           (roubo.getModelo() != null && !roubo.getModelo().equals("")) &&
            (roubo.getValor() != null && !roubo.getValor().equals("")) &&
          (roubo.getDescricao() != null && !roubo.getDescricao().equals("")))
        {
  
            info.add(new Roubo(roubo.getQuantidade(), roubo.getModelo(),roubo.getDescricao(), roubo.getValor()));
            RequestContext.getCurrentInstance().update("forma:tabelaRoubo");
            valor += Double.valueOf(roubo.getValor());
            roubo.setLimiteRsp(String.valueOf(valor));
            roubo.setLimiteRspFormatado(Moeda.format(valor));
            RequestContext.getCurrentInstance().execute("adicionarTabelaRoubo()");
        }
    }
    
    public void test()
    {
        System.out.println("it is here!");
    }
    public void calcular()
    {
        double valor = 0;
        for(int i = 0;i<info.size();i++)
        {
            valor += Double.valueOf(info.get(i).getValor());      
        }
        roubo.setLimiteRsp(String.valueOf(valor));
        roubo.setLimiteRspFormatado(Moeda.format(valor));
        System.out.println("total S "+roubo.getLimiteRsp());
        
    }
    public String getNumeroR() {
        return numeroR;
    }
    
    public void removeAll()
    {
        info.clear();
        Validacao.AtualizarCompoente("forma", "tabelaRoubo");
    }
    
    public void remove()
    {
        if(rouboInfo!= null)
        {
            valor -=Double.valueOf(rouboInfo.getValor());
            info.remove(rouboInfo);
            roubo.setLimiteRsp(String.valueOf(valor));
            roubo.setLimiteRspFormatado(Moeda.format(valor));
            Validacao.AtualizarCompoente("forma", "tabelaRoubo");
        }
    }

    public boolean isAtivarDesativar() {
        return ativarDesativar;
    }
    
    public boolean VerificarNumeroRegistro()
    {
        boolean existe;
        FacesContext facesContext = FacesContext.getCurrentInstance();   
        existe = ContratoDao.isNumRegistroVago(roubo.getNumeroRegistro());
        if(existe == false)
        {
            Message.addErrorMsg("Número de registro já existe", "forma", "growl-error");
            RequestContext.getCurrentInstance().execute("moverTopo()");
            RequestContext.getCurrentInstance().addCallbackParam("numeroRegistro", "já existe");
        }
        return existe;
    }
   
    public void ativarDesativarCampo(String param)
    {
        if(!roubo.getTipoEdificio().equals("66;"))
            ativarDesativar = true;
        else
        {
            ativarDesativar = false;
            ativar1 = true;
        }
    }

    public Roubo getRouboInfo() {
        return rouboInfo;
    }

    public void setRouboInfo(Roubo rouboInfo) {
        this.rouboInfo = rouboInfo;
    }
    
    public void continuar(MultiRiscoBean multiRiscoBean)
    {
        System.out.println("entrou R");
        System.out.println(roubo.toString());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String dataAquisicao, pattern ="dd-MM-yyyy";
        getRoubo().setNumeroRegistro(facesContext.getExternalContext().getRequestParameterMap().get("numeroRegistro"));
        getRoubo().setNumeroApolice(facesContext.getExternalContext().getRequestParameterMap().get("apolice"));
        getRoubo().setMoeda(facesContext.getExternalContext().getRequestParameterMap().get("moeda"));
        getRoubo().setTipoEdificioEspecifique(facesContext.getExternalContext().getRequestParameterMap().get("especificarTipoEdificio"));
        getRoubo().setTempoOcupacao(facesContext.getExternalContext().getRequestParameterMap().get("tempoOcupacao"));
        getRoubo().setDatatInspecao(facesContext.getExternalContext().getRequestParameterMap().get("dataInspecao"));
        getRoubo().setEnderecoEdificio(facesContext.getExternalContext().getRequestParameterMap().get("endereço"));
        getRoubo().setValorCofre(facesContext.getExternalContext().getRequestParameterMap().get("valorCofre"));
        getRoubo().setMarcaCofre(facesContext.getExternalContext().getRequestParameterMap().get("marca"));
        dataAquisicao = facesContext.getExternalContext().getRequestParameterMap().get("dataAquisicao");
        getRoubo().setDataAquisicao(OperacaoData.stringFormatToDate(dataAquisicao, pattern));
        listaRespostas.getResposta(147).setObj(facesContext.getExternalContext().getRequestParameterMap().get("descricaoPerda"));
        listaRespostas.getResposta(147).setObj(facesContext.getExternalContext().getRequestParameterMap().get("medidasTomadasAssalto"));
        
        if(session.getAttribute("S") != null && session.getAttribute("S").toString().split(";")[0].equalsIgnoreCase("mr"))
        {
            if(info.isEmpty())
                Message.addWarningMsg("Adicione informações na tabela","forma","growl-error");
            else
            {
                calcular();
                SessionUtil.removerParametro("DI2");
                SessionUtil.removerParametro("DI");
                SessionUtil.definirParametro("DI", this);
                SessionUtil.definirParametro("DI2", "true");
                SessionUtil.definirParametro("respostas", listaRespostas);
                
                multiRiscoBean.validar();
            }
        }
        else
        {
           
            if(roubo.getNumeroRegistro() != null && !roubo.getNumeroRegistro().equals("") )
            {
               if(VerificarNumeroRegistro()== true && validaNumeroApolice() == true)
               {
                   if(info.isEmpty())
                   {
                        Mensagem.addWarningMsg("Adicione informações na tabela");     
                         RequestContext.getCurrentInstance().update("forma:growl-error");
                   }
                   else
                   {
                       calcular();

                       roubo.setSigla(Validacao.Sigla(roubo.getMoeda()));
                       SessionUtil.removerParametro("DI");
                       SessionUtil.definirParametro("DI", this);
                       SessionUtil.definirParametro("respostas", listaRespostas);
                       RequestContext.getCurrentInstance().execute("redirecionar()");
                   }
               }
           }
           else
               Message.addWarningMsg("Informe o número de registro!", "forma", "growl-error");
        }   
    }
    
   public boolean validaNumeroApolice()
   {
        return roubo.getNumeroApolice() != null && !roubo.getNumeroApolice().equals("");
   }
     
    public void moreInfo()
    {
        Validacao.AtualizarCompoente("forma", "rouboMoreInfo");
        RequestContext.getCurrentInstance().execute("modalInfoRoubo()");
    }
    
}
