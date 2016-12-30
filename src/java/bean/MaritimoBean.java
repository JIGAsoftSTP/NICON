
package bean;

import modelo.Funcionario;
import dao.ContratoDao;
import dao.SeguroDao;
import dao.Seguro_MaritimoDao;
import dao.VeiculoDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import static mensagem.Mensagem.addInfoMsg;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Maritimo;
import modelo.MaritimoCobertura;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

//Hélcio
@ManagedBean
@ViewScoped
public class MaritimoBean implements Serializable
{
     private static final long serialVersionUID = 1L;
     private Maritimo maritimo = new Maritimo();
     private boolean ativar = true;
     private boolean obrigatorio = false;
     private ArrayList<ComoBox> listaCombustiveis =new ArrayList<>();
     private MaritimoCobertura maritimoCobertura = new MaritimoCobertura();
     private ArrayList<String> coberturas = new ArrayList<>();
     private double premioBruto, premioLiquido, totalSegurado = 0;
     @SuppressWarnings("FieldMayBeFinal")
     private SeguroDao seguroDao;
     private String info;
     private HashMap<String,MaritimoCobertura> listaCobertura = new HashMap<>();
     private List<ComoBox> moedas = new ArrayList<>();
     private VeiculoDao veiculoDao;
     private Seguro_MaritimoDao seguro_MaritimoDao;
     private List<MaritimoCobertura> maritimoCoberturas = new ArrayList<>();
     
     
     private MaritimoCobertura incluRiscoGuerra = new MaritimoCobertura(29);
     private MaritimoCobertura exculRiscoGuerra = new MaritimoCobertura(28);
     private MaritimoCobertura incluPassgeiro = new MaritimoCobertura(31);
     private MaritimoCobertura  excluPassageiro = new MaritimoCobertura(30) ;
     

     
     @SuppressWarnings("LeakingThisInConstructor")
     public MaritimoBean()
     {
         veiculoDao = new VeiculoDao();
         moedas = veiculoDao.moedas();
         seguro_MaritimoDao = new Seguro_MaritimoDao();
        listaCombustiveis = seguro_MaritimoDao.tipoCombustivel();
         seguroDao = new SeguroDao();
         if(SessionUtil.obterValor("NHI") != null)
         {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
             MaritimoBean mb = ((MaritimoBean) SessionUtil.obterValor("NHI"));
             for (Field f : this.getClass().getDeclaredFields()) {
                 try {
                     f.setAccessible(true);
                     f.set(this, f.get(mb));
                 } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                 }
             }
             RequestContext.getCurrentInstance().execute("backAndcontine()");
         }
     }
     
     
    public Maritimo getMaritimo() 
    {
        return (maritimo == null) ? maritimo = new Maritimo() : maritimo;
    }

    public void setMaritimo(Maritimo maritimo)
    {    
        this.maritimo = maritimo;
    }

    public HashMap<String, MaritimoCobertura> getListaCobertura() {
        return listaCobertura;
    }

    public void setMaritimoCobertura(MaritimoCobertura maritimoCobertura)
    {
        this.maritimoCobertura = maritimoCobertura;
    }

    public String getInfo() {
        return info;
    }

    public ArrayList<ComoBox> getListaCombustiveis() {
        return listaCombustiveis;
    }

    public void setListaCombustiveis(ArrayList<ComoBox> listaCombustiveis) {
        this.listaCombustiveis = listaCombustiveis;
    }
    

    public boolean isObrigatorio() {
        return obrigatorio;
    }
    
    public void ativarDesativar()
    {
        if(maritimo.getApoioNavegacao().equals("N"))
        {
            ativar = true;
            obrigatorio = false;
        }
        else
        {
            ativar = false;
            obrigatorio = true;
        }
    }

    public boolean isAtivar() {
        return ativar;
    }
            
  

    /**
     * Corpo e o Motor(Excluindo Risco de Guerra)
     */
    public void respExcludRiscoGera()
    {
        Float taxa = 0f, valor = 0f;
          this.gericCobertura(this.exculRiscoGuerra, this.incluRiscoGuerra);
        if(getExculRiscoGuerra().getCobreValor() != null && !getExculRiscoGuerra().getCobreValor().equals(""))
        {
             if(this.getExculRiscoGuerra().getCobreTaxa() != null && !getExculRiscoGuerra().getCobreTaxa().equals("") &&
                     Validacao.isNumeric(getExculRiscoGuerra().getCobreTaxa())== true)
            {
                if(Float.valueOf(this.getExculRiscoGuerra().getCobreTaxa()) <= 100)
                {
                    taxa = Float.valueOf(this.getExculRiscoGuerra().getCobreTaxa())/100;
                    valor = Float.valueOf(this.getExculRiscoGuerra().getCobreValor()) * taxa;
                    getExculRiscoGuerra().setCobrePremioFormatado(Moeda.format(valor));
                    getExculRiscoGuerra().setCobrePremio(String.valueOf(valor));
                    totalSegurado = Float.valueOf(getExculRiscoGuerra().getCobreValor());
                    Validacao.AtualizarCompoente("maritimoFormulario", "item3");
                }
                else
                {
                     getExculRiscoGuerra().setCobrePremio("");
                     getExculRiscoGuerra().setCobreTaxa("");
                     totalSegurado = 0;
                     getExculRiscoGuerra().setCobrePremioFormatado("");
                     Validacao.AtualizarCompoente("maritimoFormulario", "item2");
                     Validacao.AtualizarCompoente("maritimoFormulario", "item3");
                }
            }
            else
             {
                getExculRiscoGuerra().setCobrePremio("");
                getExculRiscoGuerra().setCobreTaxa("");
                totalSegurado = 0;
                getExculRiscoGuerra().setCobrePremioFormatado("");
                Validacao.AtualizarCompoente("maritimoFormulario", "item2");
                Validacao.AtualizarCompoente("maritimoFormulario", "item3");
             }   
        }
  
    }
    
    /**
     * Corpo e o Motor(Incluindo Risco de Guerra)
     */
    public void respIncludRiscogera()
    {
        Float taxa = 0f, valor = 0f;
         this.gericCobertura(this.incluRiscoGuerra, this.exculRiscoGuerra);
        if(getIncluRiscoGuerra().getCobreValor() != null && !getIncluRiscoGuerra().getCobreValor().equals(""))
        {
             if(this.getIncluRiscoGuerra().getCobreTaxa() != null && !getIncluRiscoGuerra().getCobreTaxa().equals("") &&
                     Validacao.isNumeric(getIncluRiscoGuerra().getCobreTaxa())== true)
            {
                if(Float.valueOf(this.getIncluRiscoGuerra().getCobreTaxa()) <= 100)
                {
                    taxa = Float.valueOf(this.getIncluRiscoGuerra().getCobreTaxa())/100;
                    valor = Float.valueOf(this.getIncluRiscoGuerra().getCobreValor()) * taxa;
                    getIncluRiscoGuerra().setCobrePremioFormatado(Moeda.format(valor));
                    getIncluRiscoGuerra().setCobrePremio(String.valueOf(valor));
           
                    Validacao.AtualizarCompoente("maritimoFormulario", "item6");
                }
                else
                {
                     getIncluRiscoGuerra().setCobrePremio("");
                     getIncluRiscoGuerra().setCobrePremioFormatado("");
                     getIncluRiscoGuerra().setCobreTaxa("");
                     Validacao.AtualizarCompoente("maritimoFormulario", "item6");
                     Validacao.AtualizarCompoente("maritimoFormulario", "item5");
                }
            }
            else
            {
                getIncluRiscoGuerra().setCobrePremio("");
                getIncluRiscoGuerra().setCobreTaxa("");
                getIncluRiscoGuerra().setCobrePremioFormatado("");
                Validacao.AtualizarCompoente("maritimoFormulario", "item6");
                Validacao.AtualizarCompoente("maritimoFormulario", "item5");
            }   
        }       
    }
    public void respExcluidPassa()
    {
        Float taxa = 0f, valor = 0f;
        this.gericCobertura(this.excluPassageiro, this.incluPassgeiro);
        if(getExcluPassageiro().getCobreValor() != null && !getExcluPassageiro().getCobreValor().equals(""))
        {
             if(this.getExcluPassageiro().getCobreTaxa() != null && !getExcluPassageiro().getCobreTaxa().equals("") &&
                     Validacao.isNumeric(getExcluPassageiro().getCobreTaxa())== true)
            {
                if(Float.valueOf(this.getExcluPassageiro().getCobreTaxa()) <= 100)
                {
                    taxa = Float.valueOf(this.getExcluPassageiro().getCobreTaxa())/100;
                    valor = Float.valueOf(this.getExcluPassageiro().getCobreValor()) * taxa;
                    getExcluPassageiro().setCobrePremioFormatado(Moeda.format(valor));
                    getExcluPassageiro().setCobrePremio(String.valueOf(valor));
                    Validacao.AtualizarCompoente("maritimoFormulario", "item9");
                }
                else
                {
                     getExcluPassageiro().setCobrePremio("");
                     getExcluPassageiro().setCobreTaxa("");
                     getExcluPassageiro().setCobrePremioFormatado("");
                     Validacao.AtualizarCompoente("maritimoFormulario", "item8");
                     Validacao.AtualizarCompoente("maritimoFormulario", "item9");
                }
            }
            else
             {
                getExcluPassageiro().setCobrePremio("");
                getExcluPassageiro().setCobreTaxa("");
                getExcluPassageiro().setCobrePremioFormatado("");
                Validacao.AtualizarCompoente("maritimoFormulario", "item8");
                Validacao.AtualizarCompoente("maritimoFormulario", "item9");
             }
        }
    }
    
    /**
     * Responsabilidade Civil para passageiros e Tripulantes
     */
    public void respComPassageiro()
    {
          Float taxa = 0f, valor = 0f;
           this.gericCobertura(this.incluPassgeiro, this.excluPassageiro);
        if(getIncluPassgeiro().getCobreValor() != null && !getIncluPassgeiro().getCobreValor().equals(""))
        {
            if(getIncluPassgeiro().getCobreTaxa() != null && !getIncluPassgeiro().getCobreTaxa().equals("") && 
                    Validacao.isNumeric(getIncluPassgeiro().getCobreTaxa())== true)
            {
               if(Float.valueOf(this.getIncluPassgeiro().getCobreTaxa()) <= 100)
                {
                    taxa = Float.valueOf(this.getIncluPassgeiro().getCobreTaxa())/100;
                    valor = Float.valueOf(this.getIncluPassgeiro().getCobreValor()) * taxa;
                    getIncluPassgeiro().setCobrePremioFormatado(Moeda.format(valor));
                    getIncluPassgeiro().setCobrePremio(String.valueOf(valor));

                    Validacao.AtualizarCompoente("maritimoFormulario", "item12");
                }
                else
                {
                     getIncluPassgeiro().setCobrePremio("");
                      getIncluPassgeiro().setCobreTaxa("");
                     getIncluPassgeiro().setCobrePremioFormatado("");
                      Validacao.AtualizarCompoente("maritimoFormulario", "item11");
                     Validacao.AtualizarCompoente("maritimoFormulario", "item12");
                }   
            }
             else
            {
                getIncluPassgeiro().setCobrePremio("");
                getIncluPassgeiro().setCobreTaxa("");
                getIncluPassgeiro().setCobrePremioFormatado("");
                Validacao.AtualizarCompoente("maritimoFormulario", "item11");
                Validacao.AtualizarCompoente("maritimoFormulario", "item12");
            }

        }
       
    }
    
     @SuppressWarnings("element-type-mismatch")
    private void gericCobertura(MaritimoCobertura add, MaritimoCobertura rem)
    {
      
        if(listaCobertura.containsKey(rem.getCobreId()+""))
             this.listaCobertura.remove(rem.getCobreId()+"");
             
            rem.setCobrePremio("");
            rem.setCobreTaxa("");
            rem.setCobreValor("");
            rem.setSelcao(false);
        
            
            
        
        if(listaCobertura.containsKey(add.getCobreId()) && add.isSelcao())
            listaCobertura.replace(add.getCobreId()+"", add);
        else
            if(add.isSelcao())
                this.listaCobertura.put(add.getCobreId()+"", add);
            else if(listaCobertura.containsKey(add.getCobreId()))
                this.listaCobertura.remove(add.getCobreId()+"");
                     
        for(Map.Entry e:  listaCobertura.entrySet())
            System.out.println(e.getValue());


    }
  
    public boolean existNumeroRegistro()
    {
        boolean existe = true;
        if(maritimo.getNumeroRegistro() != null && maritimo.getNumeroRegistro().length()>0)
        {
            existe = ContratoDao.isNumRegistroVago(maritimo.getNumeroRegistro());
            if(existe == false)
            {
                Message.addErrorMsg("Número de registro já existe!", "maritimoFormulario", "maritimoGrowl");
                RequestContext.getCurrentInstance().execute("moveTop()");
            }
        }
        return existe;
    }

    public ArrayList<String> getCoberturas() {
        return coberturas;
    }

    public void setCoberturas(ArrayList<String> coberturas) {
        this.coberturas = coberturas;
    }
    
  
    public void avancar() 
    {
       if(validarApolice() == true)
       {
           calculoPremio();
            maritimo.setSigla(Validacao.Sigla(maritimo.getMoeda()));
            if(Double.valueOf(maritimo.getPremioLiquido()) == 0)
            {
                RequestContext.getCurrentInstance().execute("moveTop()");
                Message.addInfoMsg("Selecione pelo menos uma cobertura informando o valor e a taxa!", "maritimoFormulario", "maritimoGrowl");
            } 
            else
            {
                 SessionUtil.definirParametro("NHI", this);
                 if(existNumeroRegistro() == true)
                     Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");   
                 else
                     RequestContext.getCurrentInstance().execute("existe()");
            }   
       }        
    }
   
    public List<ComoBox> getMoedas() {
        return moedas;
    }

    public void setMoedas(List<ComoBox> moedas) {
        this.moedas = moedas;
    }
    

    

    public void calculoPremio()
    {
        float percentagemRetirar = 100-(5+0.6f);
        String valorPremio =null;
        totalSegurado = 0;
        premioBruto = 0;
        premioLiquido = 0;
        
        String valor;
        String [] info;
        coberturas.clear();
      
        if(getExculRiscoGuerra().isSelcao() == true)
        {
            coberturas.add(listaCobertura.get("28").getCobreId()+";"+listaCobertura.get("28").getCobreValor()+";"+
                    listaCobertura.get("28").getCobreTaxa()+";"+listaCobertura.get("28").getCobrePremio());
            valorPremio = (listaCobertura.get("28").getCobrePremio()==null || listaCobertura.get("28").getCobrePremio().equals(""))? null :listaCobertura.get("28").getCobrePremio();
        }
        if(getIncluRiscoGuerra().isSelcao() == true)
        {
            coberturas.add(listaCobertura.get("29").getCobreId()+";"+listaCobertura.get("29").getCobreValor()+";"+
                    listaCobertura.get("29").getCobreTaxa()+";"+listaCobertura.get("29").getCobrePremio());
                valorPremio = (listaCobertura.get("29").getCobrePremio()==null || listaCobertura.get("29").getCobrePremio().equals(""))? null :listaCobertura.get("29").getCobrePremio();
        }
        if(getExcluPassageiro().isSelcao() == true)
        {
            coberturas.add(listaCobertura.get("30").getCobreId()+";"+listaCobertura.get("30").getCobreValor()+";"+
                    listaCobertura.get("30").getCobreTaxa()+";"+listaCobertura.get("30").getCobrePremio());
            valorPremio = (listaCobertura.get("30").getCobrePremio()==null || listaCobertura.get("30").getCobrePremio().equals(""))? null :listaCobertura.get("30").getCobrePremio();
        }
        if(getIncluPassgeiro().isSelcao() == true)
        {
            coberturas.add(listaCobertura.get("31").getCobreId()+";"+listaCobertura.get("31").getCobreValor()+";"+
                    listaCobertura.get("31").getCobreTaxa()+";"+listaCobertura.get("31").getCobrePremio());
            valorPremio = (listaCobertura.get("31").getCobrePremio()==null || listaCobertura.get("31").getCobrePremio().equals(""))? null :listaCobertura.get("31").getCobrePremio();
        }
        if(valorPremio != null)
        { 
            for(int i = 0;i<coberturas.size();i++)
            {
                valor = coberturas.get(i);
                info = valor.split(";");
                if(info.length == 4)
                {
                    totalSegurado += Double.valueOf(info[1]);
                    premioLiquido += Double.valueOf(info[3]); // determina o premio liquido que é a soma de todos os prémios
                }
            }
        }
        if(premioLiquido >0)
        {
           premioBruto = (percentagemRetirar * premioLiquido)/100;
           maritimo.setTotalSegurado(String.valueOf(totalSegurado));
           maritimo.setTotalSeguradoMoeda(Moeda.format(totalSegurado));
           maritimo.setPremioBruto(String.valueOf(premioBruto));
           maritimo.setPremioBrutoMoeda(Moeda.format(premioBruto));
           maritimo.setPremioLiquido(String.valueOf(premioLiquido));
           maritimo.setPremioLiquidoMoeda(Moeda.format(premioLiquido)); 
        }
        else
            maritimo.setPremioLiquido("0");
    }

   
    public void getNome()
    {
        String info = null;
        System.out.println(maritimo.getTipoCombustivel());
        for(int i = 0;i<moedas.size(); i++)
        {
            if(maritimo.getMoeda().equals(moedas.get(i).getId()))
            {
                maritimo.setSigla(moedas.get(i).getValue());
                break;
            }
        }
    }

    public MaritimoCobertura getIncluRiscoGuerra() {
        return incluRiscoGuerra;
    }

    public void setIncluRiscoGuerra(MaritimoCobertura incluRiscoGuerra) {
        this.incluRiscoGuerra = incluRiscoGuerra;
    }

    public MaritimoCobertura getExculRiscoGuerra() {
        return exculRiscoGuerra;
    }

    public void setExculRiscoGuerra(MaritimoCobertura exculRiscoGuerra) {
        this.exculRiscoGuerra = exculRiscoGuerra;
    }

   
    

    public MaritimoCobertura getExcluPassageiro() {
        return excluPassageiro;
    }

    public void setExcluPassageiro(MaritimoCobertura excluPassageiro) {
        this.excluPassageiro = excluPassageiro;
    }

    public MaritimoCobertura getIncluPassgeiro() {
        return incluPassgeiro;
    }

    public void setIncluPassgeiro(MaritimoCobertura incluPassgeiro) {
        this.incluPassgeiro = incluPassgeiro;
    }

    public class Cobrtura implements Serializable
    {
        
        private int id;
        private String valor="";
        private String taxa="";
        private String premio="";
        private String detalhes = "";
        
        
        public Cobrtura(int id, String valor, String taxa, String premio)
        {
            this.id = id;
            this.valor = valor;
            this.taxa =taxa;
            this.premio = premio;
           
        }
        
        
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        public String getTaxa() {
            return taxa;
        }

        public void setTaxa(String taxa) {
            this.taxa = taxa;
        }

        public String getPremio() {
            return premio;
        }

        public void setPremio(String premio) {
            this.premio = premio;
        }

        public String getDetalhes() {
            return detalhes;
        }

        public void setDetalhes(String detalhes) {
            this.detalhes = detalhes;
        }

        public Cobrtura() {
        }

        @Override
        public String toString()
        {
            return this.id+";"
                    +this.valor+";"
                    +this.taxa+";"
                    +this.premio+";"
                    +this.detalhes+"";
        }
        
        
        
    }
   public boolean validarApolice()
   {
        if(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null && !SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).equals("N"))
        {
            maritimo.setNumeroApolice(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).toString());
            return true;
        }
        else
        {
            if(maritimo.getNumeroApolice() != null && !maritimo.getNumeroApolice().equals(""))
                return true;
            else
            {
                Message.addWarningMsg("Informe o número de apolice", "maritimoFormulario", "maritimoGrowl");
                RequestContext.getCurrentInstance().execute("apoliceMaritimo()");
                return false;
            }
        }
   }
 
}
