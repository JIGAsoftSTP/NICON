
package bean;

import dao.ContratoDao;
import dao.SeguroDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import modelo.Cobertura;
import modelo.ResponsabilidadePublica;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author Helcio
 */
@ManagedBean
@ViewScoped
public class ResponsabilidadePublicaBean implements Serializable
{
    private static final long serialVersionUID =1L;
    private ResponsabilidadePublica rp = new ResponsabilidadePublica();
    private ResponsabilidadePublica rpSelected = new ResponsabilidadePublica();
    private List<ResponsabilidadePublica> info;
    private SeguroDao seguroDao = new SeguroDao();
    @ManagedProperty(value = "#{listaRespostas}")
    private ListaRespostas listaRespostas;
    private String mensagem;
    
    private Cobertura incendioExplosao = new Cobertura(61);
    private Cobertura cobertura = new Cobertura();
    private Cobertura intoxicacaoAlimentar = new Cobertura(62);
    private Cobertura outro = new Cobertura(63);
    private Cobertura acidente = new Cobertura(64);
    private Cobertura periodoSeguro = new Cobertura(66);
    private ArrayList<String> coberturas = new ArrayList<>();
    private  float perecentagemRetirar = 100-(5+0.6f);
    @SuppressWarnings({"LeakingThisInConstructor", "LeakingThisInConstructor"})
    public ResponsabilidadePublicaBean()
    {      
         info = new ArrayList<>(); 
         if(SessionUtil.obterValor("RP") != null)
         {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
             ResponsabilidadePublicaBean rpb = ((ResponsabilidadePublicaBean) SessionUtil.obterValor("RP"));
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

    public List<ResponsabilidadePublica> getInfo() {
        return info;
    }

    public SeguroDao getSeguroDao() {
        return seguroDao;
    }

    public void setSeguroDao(SeguroDao seguroDao) {
        this.seguroDao = seguroDao;
    }

  

    public Cobertura getIncendioExplosao() {
        return incendioExplosao;
    }

    public ListaRespostas getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(ListaRespostas listaRespostas) {
        this.listaRespostas = listaRespostas;
    }

    public ResponsabilidadePublica getRpSelected() {
        return rpSelected;
    }

    public void setRpSelected(ResponsabilidadePublica rpSelected) {
        this.rpSelected = rpSelected;
    }
    
    

    public void setIncendioExplosao(Cobertura incendioExplosao) {
        this.incendioExplosao = incendioExplosao;
    }

    public Cobertura getIntoxicacaoAlimentar() {
        return intoxicacaoAlimentar;
    }

    public void setIntoxicacaoAlimentar(Cobertura intoxicacaoAlimentar) {
        this.intoxicacaoAlimentar = intoxicacaoAlimentar;
    }

    public Cobertura getOutro() {
        return outro;
    }

    public ArrayList<String> getCoberturas() {
        return coberturas;
    }

    public void setCoberturas(ArrayList<String> coberturas) {
        this.coberturas = coberturas;
    }

    public void setOutro(Cobertura outro) {
        this.outro = outro;
    }

    public Cobertura getAcidente() {
        return acidente;
    }

    public void setAcidente(Cobertura acidente) {
        this.acidente = acidente;
    }

    public Cobertura getPeriodoSeguro() {
        return periodoSeguro;
    }

    public void setPeriodoSeguro(Cobertura periodoSeguro) {
        this.periodoSeguro = periodoSeguro;
    }
    
    public void addTable()
    {
        if((rp.getEmprego() != null && !rp.getEmprego().equals("")) &&
           (rp.getProfissao() != null && !rp.getProfissao().equals("")) &&
           (rp.getEndereco() != null && !rp.getEndereco().equals("")))
        {
            info.add(new ResponsabilidadePublica(rp.getEmprego(), rp.getProfissao(), rp.getEndereco()));
            Validacao.atualizar("responsabilidadeForm", "respTable");
            RequestContext.getCurrentInstance().execute("limparCamposTabela()");
        }
    }
    public ResponsabilidadePublica getRp() 
    {
        return (rp == null) ? rp = new ResponsabilidadePublica() : rp;
    }
    
    public void setRp(ResponsabilidadePublica rp) 
    {
        this.rp = rp;
    }
    
    public void removeFromTable(ResponsabilidadePublica rp)
    {
        rpSelected = rp;
        info.remove(rpSelected);
        Validacao.atualizar("responsabilidadeForm", "respTable");
    }
    
    public void limparTudo()
    {
        info.clear();
        Validacao.atualizar("responsabilidadeForm", "respTable");
    }
    
    public void respDadosForm()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String valor;
        String[] primeiraCobertura = new String[1], segundaCobertura = new String[1], terceiraCobertura = new String[1];
        primeiraCobertura[0]=facesContext.getExternalContext().getRequestParameterMap().get("incendioCoberturaCheckBox");
        segundaCobertura[0]=facesContext.getExternalContext().getRequestParameterMap().get("respIntoxicacaoCob");
        terceiraCobertura[0]=facesContext.getExternalContext().getRequestParameterMap().get("respOutroCob");
        rp.setNumeroApolice(facesContext.getExternalContext().getRequestParameterMap().get("apolice"));   
        rp.setNumeroRegistro(facesContext.getExternalContext().getRequestParameterMap().get("numeroRegistro"));   
        rp.setMoeda(facesContext.getExternalContext().getRequestParameterMap().get("moeda"));  
        incendioExplosao.setCoberturas(primeiraCobertura);
        incendioExplosao.setCobreValor(facesContext.getExternalContext().getRequestParameterMap().get("incendioCobValor"));
        intoxicacaoAlimentar.setCoberturas(segundaCobertura);
        intoxicacaoAlimentar.setCobreValor(facesContext.getExternalContext().getRequestParameterMap().get("intoxicacaoCobValor"));
        outro.setCoberturas(terceiraCobertura);
        outro.setCobreDetalhes(facesContext.getExternalContext().getRequestParameterMap().get("outroCobDetalhes"));
        outro.setCobreValor(facesContext.getExternalContext().getRequestParameterMap().get("outroCobValor"));
        acidente.setCobreValor(facesContext.getExternalContext().getRequestParameterMap().get("respAcidenteValor"));
        acidente.setCobreTaxa(facesContext.getExternalContext().getRequestParameterMap().get("respAcidenteTaxa"));
        periodoSeguro.setCobreValor(facesContext.getExternalContext().getRequestParameterMap().get("respPeriodoValor"));
        periodoSeguro.setCobreTaxa(facesContext.getExternalContext().getRequestParameterMap().get("respPeriodoTaxa"));
        rp.setSalarioSubempreiteiros(facesContext.getExternalContext().getRequestParameterMap().get("respSubEmpreiteiros"));
        rp.setSalarioDiretorColaborador(facesContext.getExternalContext().getRequestParameterMap().get("respDiretor"));
        rp.setEstadoEdificio(facesContext.getExternalContext().getRequestParameterMap().get("respEstadoEdificio"));
      
    }
    public boolean validarNumeroRegistro()
    {
        boolean valido = true;
        if(rp.getNumeroRegistro() != null && rp.getNumeroRegistro().length()>0)
        {
            valido = ContratoDao.isNumRegistroVago(rp.getNumeroRegistro());
           if(valido == false)
           {
               Mensagem.addErrorMsg("Número de registro já existe");
               RequestContext.getCurrentInstance().execute("moveTop()");
               RequestContext.getCurrentInstance().execute("respNumeroRegistroInvalido()");
               Validacao.atualizar("responsabilidadeForm", "RespPublicaInfo");
               
           }
        }
        return valido;
    }
    
    public void seguinte()
    {
        this.respDadosForm();
        System.out.println(rp.toString());
        double premioBruto = 0, valor1= 0, valor2= 0, totalSegurado = 0, premioLiquido = 0;
       
        if(validaNumeroApolice() == true)
        {
            if(validarNumeroRegistro() == true && VerificarCobertura() == true && selectLimiteIndenmizacao()== true )
            {
                if(InfoAdicionais() == true)
                {
                    valor1 = ((acidente.getCobreValor() != null && !acidente.getCobreValor().equals("")) ? Double.valueOf(acidente.getCobreValor()) : 0);
                    valor2 = ((periodoSeguro.getCobreValor() != null && !periodoSeguro.getCobreValor().equals("")) ? Double.valueOf(periodoSeguro.getCobreValor()) : 0);
                    totalSegurado = valor1 + valor2;
                    rp.setTotalSegurado(String.valueOf(totalSegurado));
                    rp.setTotalSeguradoFormatado(Moeda.format(totalSegurado));
                    valor1 = ((acidente.getCobrePremio() != null && !acidente.getCobrePremio().equals("")) ? Double.valueOf(acidente.getCobrePremio()) : 0);
                    valor2 = ((periodoSeguro.getCobrePremio() != null && !periodoSeguro.getCobrePremio().equals("")) ? Double.valueOf(periodoSeguro.getCobrePremio()) : 0);
                    premioLiquido = valor1+valor2;
                    premioBruto = (perecentagemRetirar *premioLiquido)/100;
                    rp.setPremioBruto(String.valueOf(premioBruto));
                    rp.setPremioBrutoMoeda(Moeda.format(premioBruto));
                    rp.setPremioLiquido(String.valueOf(premioLiquido));
                    rp.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
                    if(incendioExplosao.getCoberturas()[0].equals("true"))
                            coberturas.add("61"+";"+incendioExplosao.getCobreValor()+";;;;");
                    if(intoxicacaoAlimentar.getCoberturas()[0].equals("true"))
                         coberturas.add("62"+";"+intoxicacaoAlimentar.getCobreValor()+";;;;");
                    if(outro.getCoberturas()[0].equals("true"))
                          coberturas.add("63"+";"+outro.getCobreValor()+";;;"+outro.getCobreDetalhes());
                    if(acidente.getCobrePremio() != null && !acidente.getCobrePremio().equals(""))
                          coberturas.add("64"+";"+acidente.getCobreValor()+";"+acidente.getCobreTaxa()+";"+acidente.getCobrePremio()+";;");
                    if(periodoSeguro.getCobrePremio() != null && !periodoSeguro.getCobrePremio().equals(""))
                        coberturas.add("66"+";"+periodoSeguro.getCobreValor()+";"+periodoSeguro.getCobreTaxa()+";"+periodoSeguro.getCobrePremio()+";;");

                    if(info.isEmpty())
                    {
                        Mensagem.addInfoMsg("Adicione informações na tabela!");
                        Validacao.atualizar("responsabilidadeForm", "RespPublicaInfo");
                    }
                    else
                    {   

                        SessionUtil.definirParametro("respostas", listaRespostas);
                        SessionUtil.definirParametro("RP", this);
                        RequestContext.getCurrentInstance().execute("outrasInfoDown()");
                    }
                }
            }
              
        } 
    }
    
    public boolean selectLimiteIndenmizacao()
    {
        boolean valido = true;
        if((acidente.getCobrePremio() == null || acidente.getCobrePremio().equals("")) && (periodoSeguro.getCobrePremio() == null || periodoSeguro.getCobrePremio().equals("")))
        {
            valido = false;   
            Mensagem.addWarningMsg("Preencha as informações de pelo menos um limite de indenmização!");
            Validacao.atualizar("responsabilidadeForm", "RespPublicaInfo");
            RequestContext.getCurrentInstance().execute("moveTop()");
        }
        else
            valido = true;
      
        return valido;   
    }
   public boolean InfoAdicionais()
   {
       RequestContext.getCurrentInstance().execute("limparIndBorda()");
        return (rp.getSalarioDiretorColaborador() != null && !rp.getSalarioDiretorColaborador().equals(""))
                && (rp.getSalarioSubempreiteiros() != null && !rp.getSalarioSubempreiteiros().equals("")) ;
   }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
   
   public void calcularPremio()
   {
       double valorTaxa, valorPremio = 0;
       String valor1, valor2 = null;
       if(acidente.getCobreTaxa() != null && !acidente.getCobreTaxa().equals("") && Validacao.isNumeric(acidente.getCobreTaxa())== true)
       {
           if(Double.valueOf(acidente.getCobreTaxa())>100)
           {
                acidente.setCobreTaxa("");
               Validacao.atualizar("responsabilidadeForm", "respAcidenteTaxa");
           }
       }
       if(periodoSeguro.getCobreTaxa() != null && !periodoSeguro.getCobreTaxa().equals("") && Validacao.isNumeric(periodoSeguro.getCobreTaxa())== true)
       {
           if(Double.valueOf(periodoSeguro.getCobreTaxa())>100)
           {
                periodoSeguro.setCobreTaxa("");
               Validacao.atualizar("responsabilidadeForm", "respPeriodoTaxa");
           }
       }
       if((acidente.getCobreTaxa() != null && !acidente.getCobreTaxa().equals("")) && 
               (Double.valueOf(acidente.getCobreTaxa())<=100) && (acidente.getCobreValor() != null && !acidente.getCobreValor().equals("")))
       {
           valorTaxa = Double.valueOf(acidente.getCobreTaxa())/100;
           valorPremio = Double.valueOf(acidente.getCobreValor()) * valorTaxa;
           acidente.setCobrePremio(String.valueOf(valorPremio));
           acidente.setCobrePremioFormatado(Moeda.format(valorPremio));
           Validacao.atualizar("responsabilidadeForm", "respAcidentePremio");
       }
       else
       {
          acidente.setCobrePremio("");
          acidente.setCobrePremioFormatado(""); 
          Validacao.atualizar("responsabilidadeForm", "respAcidentePremio");
       }
       if((periodoSeguro.getCobreTaxa() != null && !periodoSeguro.getCobreTaxa().equals("")) && 
               (Double.valueOf(periodoSeguro.getCobreTaxa())<=100) && (periodoSeguro.getCobreValor() != null && !periodoSeguro.getCobreValor().equals("")))
       {
           valorTaxa = 0;
           valorPremio = 0;
           valorTaxa = Double.valueOf(periodoSeguro.getCobreTaxa())/100;
           valorPremio = Double.valueOf(periodoSeguro.getCobreValor()) * valorTaxa;
           periodoSeguro.setCobrePremio(String.valueOf(valorPremio));
           periodoSeguro.setCobrePremioFormatado(Moeda.format(valorPremio));
           Validacao.atualizar("responsabilidadeForm", "respPeriodoPremio");
       }
       else
       {
           periodoSeguro.setCobrePremio("");
           periodoSeguro.setCobrePremioFormatado("");
           Validacao.atualizar("responsabilidadeForm", "respPeriodoPremio");
       }
   }

    /**
     * Verifica-se alguma cobertura foi selecionada
     * @return true se selecionado
     */
    public boolean VerificarCobertura()
    {
        boolean selecionado = true;
        if(incendioExplosao.getCoberturas()[0].equals("0") && intoxicacaoAlimentar.getCoberturas()[0].equals("0") & outro.getCoberturas()[0].equals("0"))
            selecionado = false;
        else
        {
            if(incendioExplosao.getCoberturas()[0].equals("true"))
            {
                 if(incendioExplosao.getCobreValor() == null || incendioExplosao.getCobreValor().equals(""))
                    selecionado = false;
            }
            if(intoxicacaoAlimentar.getCoberturas()[0].equals("true"))
            {
                if(intoxicacaoAlimentar.getCobreValor() == null || intoxicacaoAlimentar.getCobreValor().equals(""))
                 selecionado = false;
            }   
           if(outro.getCoberturas()[0].equals("true"))
           {
               if(outro.getCobreValor() == null || outro.getCobreValor().equals(""))
                   selecionado = false;
                if(outro.getCobreDetalhes() == null || outro.getCobreDetalhes().equals(""))
                   selecionado = false;
           }        
        }
        if(selecionado == false)
        {
            Mensagem.addWarningMsg("Selecione pelo menos uma cobertura informando as respetivas informações!");
            Validacao.atualizar("responsabilidadeForm", "RespPublicaInfo");
            RequestContext.getCurrentInstance().execute("moveTop()");
        }
        return selecionado;
    }

    public Cobertura getCobertura() {
        return cobertura;
    }

    public void setCobertura(Cobertura cobertura) {
        this.cobertura = cobertura;
    }
   public boolean validaNumeroApolice()
   {
        return rp.getNumeroApolice() != null && !rp.getNumeroApolice().equals("");
   }
    
}
