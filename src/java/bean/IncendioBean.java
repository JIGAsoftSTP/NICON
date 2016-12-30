
package bean;

import modelo.Funcionario;
import dao.ContratoDao;
import dao.SeguroDao;
import dao.VeiculoDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Incendio;
import modelo.IncendioRisco;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author Helio
 */
@ManagedBean
@ViewScoped
public class IncendioBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Incendio incendio = new Incendio();
    private IncendioRisco incendioRisco = new IncendioRisco();
    private ArrayList<IncendioRisco> riscos;
    private SeguroDao sd;
    private ArrayList<String> riscosIncendio;
    @ManagedProperty(value = "#{listaRespostas}")
    private ListaRespostas listaRespostas;
    private String info;
    private VeiculoDao veiculoDao;
    private final List<ComoBox> moeda;
    private float percentagemRetirar = 100-(5+0.6f);
    private HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
    @SuppressWarnings("LeakingThisInConstructor")
    public IncendioBean()
    {
        sd = new SeguroDao();
        veiculoDao = new VeiculoDao();
        moeda = veiculoDao.moedas();
        riscos = new ArrayList<>();
        riscosIncendio = new ArrayList<>();
         if(SessionUtil.obterValor("FR")!=null)//respostas
         {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
             IncendioBean ib = ((IncendioBean) SessionUtil.obterValor("FR"));
             for (Field f : this.getClass().getDeclaredFields()) {
                 try {
                     f.setAccessible(true);
                     f.set(this, f.get(ib));
                 } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                 }
             }
             RequestContext.getCurrentInstance().execute("backAndcontine()");
         }
    }
    public Incendio getIncendio() 
    {
        return (incendio == null) ? incendio = new Incendio() : incendio;
    }

    public ArrayList<IncendioRisco> getRiscos() {
        return riscos;
    }

    public void setRiscos(ArrayList<IncendioRisco> riscos) {
        this.riscos = riscos;
    }

    public ArrayList<String> getRiscosIncendio() {
        return riscosIncendio;
    }

    public void setRiscosIncendio(ArrayList<String> riscosIncendio) {
        this.riscosIncendio = riscosIncendio;
    }

    public ListaRespostas getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(ListaRespostas listaRespostas) {
        this.listaRespostas = listaRespostas;
    }

    
    public void setIncendio(Incendio incendio) {
        this.incendio = incendio;
    }

    public IncendioRisco getIncendioRisco() 
    {
        return (incendioRisco == null) ? incendioRisco = new IncendioRisco() : incendioRisco;
    }

    public void setIncendioRisco(IncendioRisco incendioRisco) {
        this.incendioRisco = incendioRisco;
    }
    
    public void RiscoAviao(String param)
    {
        if(!incendioRisco.getAviaoTaxa().isEmpty()&&!incendioRisco.getAviaoValor().isEmpty())
        {
            incendioRisco.setAviaoPremio(caluclar(incendioRisco.getAviaoValor(),incendioRisco.getAviaoTaxa())+"");
            incendioRisco.setAviaoPremioMoeda(Moeda.format(caluclar(incendioRisco.getAviaoValor(),incendioRisco.getAviaoTaxa())));
        }
        else
        {
            incendioRisco.setAviaoPremio("");
            incendioRisco.setAviaoPremioMoeda("");
        }
    }
    
    public void RiscoColisao(String param)
    {
        if(!incendioRisco.getColisaoTaxa().isEmpty()&&!incendioRisco.getColisaoValor().isEmpty())
        {
            incendioRisco.setColisaoPremio(caluclar(incendioRisco.getColisaoValor(),incendioRisco.getColisaoTaxa())+"");
            incendioRisco.setColisaoPremioMoeda(Moeda.format(caluclar(incendioRisco.getColisaoValor(),incendioRisco.getColisaoTaxa())));
        }
        else
        {
            incendioRisco.setColisaoPremio("");
            incendioRisco.setColisaoPremioMoeda("");
        }
    }
    
    public void RiscoExplosao(String param)
    {
       if(!incendioRisco.getExplosaoTaxa().isEmpty()&&!incendioRisco.getExplosaoValor().isEmpty())
        {
            incendioRisco.setExplosaoPremio(""+caluclar(incendioRisco.getExplosaoValor(),incendioRisco.getExplosaoTaxa()));
            incendioRisco.setExplosaoPremioMoeda(Moeda.format(caluclar(incendioRisco.getExplosaoValor(),incendioRisco.getExplosaoTaxa())));
        }
       else
       {
           incendioRisco.setExplosaoPremio("");
           incendioRisco.setExplosaoPremioMoeda("");
       }
    }
    
    public void RiscoTerramoto(String param)
    {
        if(!incendioRisco.getTerramotoValor().isEmpty()&&!incendioRisco.getTerramotoTaxa().isEmpty())
        {
            incendioRisco.setTerramotoPremio(caluclar(incendioRisco.getTerramotoValor(),incendioRisco.getTerramotoTaxa())+"");
            incendioRisco.setTerramotoPremioMoeda(Moeda.format(caluclar(incendioRisco.getTerramotoValor(),incendioRisco.getTerramotoTaxa())));
        }
        else
        {
            incendioRisco.setTerramotoPremio("");
            incendioRisco.setTerramotoPremioMoeda("");
        }
    }
    
    public void RiscoTempestade(String param)
    {
        if(!incendioRisco.getTempestadeValor().isEmpty()&&!incendioRisco.getTempestadeTaxa().isEmpty())
        {
            incendioRisco.setTempestadePremio(""+caluclar(incendioRisco.getTempestadeValor(),incendioRisco.getTempestadeTaxa()));
            incendioRisco.setTempestadePremioMoeda(Moeda.format(caluclar(incendioRisco.getTempestadeValor(),incendioRisco.getTempestadeTaxa())));
        }
        else
        {
            incendioRisco.setTempestadePremio("");
            incendioRisco.setTempestadePremioMoeda("");
        }
    }
    
    public void RiscoAfundamento(String param)
    {
        if(!incendioRisco.getAfundamentoValor().isEmpty()&&!incendioRisco.getAfundamentoTaxa().isEmpty())
        {
            incendioRisco.setAfundamentoPremio(""+caluclar(incendioRisco.getAfundamentoValor(),incendioRisco.getAfundamentoTaxa()));
            incendioRisco.setAfundamentoPremioMoeda(Moeda.format(caluclar(incendioRisco.getAfundamentoValor(),incendioRisco.getAfundamentoTaxa())));
        }
        else
        {
           incendioRisco.setAfundamentoPremio("");
           incendioRisco.setAfundamentoPremioMoeda("");
        }
    }
    
    public void RiscoRiscoPolitico(String param)
    {
        if(!incendioRisco.getRiscoPoliticoValor().isEmpty()&&!incendioRisco.getRiscoPoliticoTaxa().isEmpty())
        {
            incendioRisco.setRiscoPoliticoPremio(""+caluclar(incendioRisco.getRiscoPoliticoValor(),incendioRisco.getRiscoPoliticoTaxa()));
            incendioRisco.setRiscoPoliticoPremioMoeda(Moeda.format(caluclar(incendioRisco.getRiscoPoliticoValor(),incendioRisco.getRiscoPoliticoTaxa())));
        }
        else
        {
            incendioRisco.setRiscoPoliticoPremio("");
            incendioRisco.setRiscoPoliticoPremioMoeda("");
        }
    }
    
    public void RiscoDanoMalicioso(String param)
    {
        if(!incendioRisco.getDanoValor().isEmpty()&&!incendioRisco.getDanoTaxa().isEmpty())
        {
            incendioRisco.setDanoPremio(""+caluclar(incendioRisco.getDanoValor(),incendioRisco.getDanoTaxa()));
            incendioRisco.setDanoPremioMoeda(Moeda.format(caluclar(incendioRisco.getDanoValor(),incendioRisco.getDanoTaxa())));
        }
        else
        {
            incendioRisco.setDanoPremio("");
            incendioRisco.setDanoPremioMoeda("");
        }
    }
    
    public void RiscoTumulto(String param)
    {
        if(!incendioRisco.getTumultoValor().isEmpty()&&!incendioRisco.getTumultoTaxa().isEmpty())
        {
            incendioRisco.setTumultoPremio(""+caluclar(incendioRisco.getTumultoValor(),incendioRisco.getTumultoTaxa()));
            incendioRisco.setTumultoPremioMoeda(Moeda.format(caluclar(incendioRisco.getTumultoValor(),incendioRisco.getTumultoTaxa())));
        }
        else
        {
            incendioRisco.setTumultoPremio("");
            incendioRisco.setTumultoPremioMoeda("");
        }
    }
    
    public void RiscoIncendioFlorestal(String param)
    {
        if(!incendioRisco.getIncendioFValor().isEmpty()&&!incendioRisco.getIncendioFTaxa().isEmpty())
        {
            incendioRisco.setIncendioFPremio(""+caluclar(incendioRisco.getIncendioFValor(),incendioRisco.getIncendioFTaxa()));
            incendioRisco.setIncendioFPremioMoeda(Moeda.format(caluclar(incendioRisco.getIncendioFValor(),incendioRisco.getIncendioFTaxa())));
        }
        else
        {
            incendioRisco.setIncendioFPremio("");
            incendioRisco.setIncendioFPremioMoeda("");
        }
    }
    
    public void RiscoExplosaoEspontanea(String param)
    {
        if(!incendioRisco.getExplosaoExTaxa().isEmpty()&&!incendioRisco.getExplosaoExValor().isEmpty())
        {
            incendioRisco.setExplosaoExPremio(""+caluclar(incendioRisco.getExplosaoExValor(),incendioRisco.getExplosaoExTaxa()));
            incendioRisco.setExplosaoExPremioMoeda(Moeda.format(caluclar(incendioRisco.getExplosaoExValor(),incendioRisco.getExplosaoExTaxa())));
        }
        else
        {
            incendioRisco.setExplosaoExPremio("");
            incendioRisco.setExplosaoExPremioMoeda("");
        }
    }
    
    public void RiscoRotura(String param)
    {
        if(!incendioRisco.getRoturaValor().isEmpty()&&!incendioRisco.getRoturaTaxa().isEmpty())
        {
            incendioRisco.setRoturaPremio(""+caluclar(incendioRisco.getRoturaValor(),incendioRisco.getRoturaTaxa()));
            incendioRisco.setRoturaPremioMoeda(Moeda.format(caluclar(incendioRisco.getRoturaValor(),incendioRisco.getRoturaTaxa())));
        }
        else
        {
            incendioRisco.setRoturaPremio("");
            incendioRisco.setRoturaPremioMoeda("");
        }
    }
    
    public void dadosIncendio(MultiRiscoBean multiRiscoBean)
    {
        if(session.getAttribute("S") != null && session.getAttribute("S").toString().split(";")[0].equalsIgnoreCase("mr"))
            calcularMultiRisco(multiRiscoBean);        
        else
            calcular();
    }
    public void calcular()
    {
        riscos.clear();
        riscosIncendio.clear();
        @SuppressWarnings("UnusedAssignment")
        Float premioBruto = 0f, premioLiquido = 0f, totalSegurado = 0f;
        Float aviao = 0f,terramoto = 0f,colisao = 0f,tempestade = 0f,explosao = 0f,afundamento = 0f,politico = 0f,dano = 0f,
        tumulto = 0f,incendioF = 0f,explosaoF = 0f,rotura = 0f,aviao2 = 0f,terramoto2 = 0f,colisao2 = 0f,tempestade2 = 0f
        ,explosao2 = 0f,afundamento2 = 0f,politico2 = 0f,dano2 = 0f,tumulto2 = 0f,incendioF2 = 0f,explosaoF2 = 0f,rotura2 = 0f;
        if(verificarNumeroRegistro() == true && validaNumeroApolice() == true)
        {
            if (incendioRisco.getRiscoAviaoId().equals("true")) 
            {
                aviao = (incendioRisco.getAviaoValor() != null && incendioRisco.getAviaoValor().length()>0) ? Float.valueOf(incendioRisco.getAviaoValor()) : -1f;
                aviao2 = (incendioRisco.getAviaoPremio() != null && incendioRisco.getAviaoPremio().length()>0) ? Float.valueOf(incendioRisco.getAviaoPremio()) : -1f;
                riscosIncendio.add("103;"+incendioRisco.getAviaoValor() + ";" + incendioRisco.getAviaoTaxa() + ";" + incendioRisco.getAviaoPremio());
            }
            if (incendioRisco.getColisaoId().equals("true")) {
                colisao = (incendioRisco.getColisaoValor() != null && incendioRisco.getColisaoValor().length()>0) ? Float.valueOf(incendioRisco.getColisaoValor()) : -1f;
                colisao2 = (incendioRisco.getColisaoPremio() != null && incendioRisco.getColisaoPremio().length()>0) ? Float.valueOf(incendioRisco.getColisaoPremio()) : -1f;
                riscosIncendio.add("104;"+incendioRisco.getColisaoValor() + ";" + incendioRisco.getColisaoTaxa() + ";" + incendioRisco.getColisaoPremio());
            }
            if (incendioRisco.getExplosaoId().equals("true")) {
                explosao = (incendioRisco.getExplosaoValor() != null && incendioRisco.getExplosaoValor().length()>0) ? Float.valueOf(incendioRisco.getExplosaoValor()) : -1f;
                explosao2 = (incendioRisco.getExplosaoPremio() != null && incendioRisco.getExplosaoPremio().length()>0) ? Float.valueOf(incendioRisco.getExplosaoPremio()) : -1f;
                riscosIncendio.add("105;"+incendioRisco.getExplosaoValor() + ";" + incendioRisco.getExplosaoTaxa() + ";" + incendioRisco.getExplosaoPremio());
            }
            if (incendioRisco.getTerramotoId().equals("true")) {
                terramoto2 = (incendioRisco.getTerramotoPremio() != null && incendioRisco.getTerramotoPremio().length()>0) ? Float.valueOf(incendioRisco.getTerramotoPremio()) : -1f;
                terramoto = (incendioRisco.getTerramotoValor() != null && incendioRisco.getTerramotoValor().length()>0) ? Float.valueOf(incendioRisco.getTerramotoValor()) : -1f;
                riscosIncendio.add("106;"+incendioRisco.getTerramotoValor() + ";" + incendioRisco.getTerramotoTaxa() + ";" + incendioRisco.getTerramotoPremio());
            }
            if (incendioRisco.getTempestadeId().equals("true")) {
                tempestade = (incendioRisco.getTempestadeValor() != null && incendioRisco.getTempestadeValor().length()>0) ? Float.valueOf(incendioRisco.getTempestadeValor()) : -1f;
                tempestade2 = (incendioRisco.getTempestadePremio() != null && incendioRisco.getTempestadePremio().length()>0) ? Float.valueOf(incendioRisco.getTempestadePremio()) : -1f;
                riscosIncendio.add("107;"+incendioRisco.getTempestadeValor() + ";" + incendioRisco.getTempestadeTaxa() + ";" + incendioRisco.getTempestadePremio());
            }
            if (incendioRisco.getAfundamentoId().equals("true")) {
                afundamento = (incendioRisco.getAfundamentoValor() != null && incendioRisco.getAfundamentoValor().length()>0) ? Float.valueOf(incendioRisco.getAfundamentoValor()) : -1f;
                afundamento2 = (incendioRisco.getAfundamentoPremio() != null && incendioRisco.getAfundamentoPremio().length()>0) ? Float.valueOf(incendioRisco.getAfundamentoPremio()) : -1f;
                riscosIncendio.add("108;"+incendioRisco.getAfundamentoValor() + ";" + incendioRisco.getAfundamentoTaxa() + ";" + incendioRisco.getAfundamentoPremio());
            }
            if (incendioRisco.getRiscoPoliticoId().equals("true")) {
                politico = (incendioRisco.getRiscoPoliticoValor() != null && incendioRisco.getRiscoPoliticoValor().length()>0) ? Float.valueOf(incendioRisco.getRiscoPoliticoValor()) : -1f;
                politico2 = (incendioRisco.getRiscoPoliticoPremio() != null && incendioRisco.getRiscoPoliticoPremio().length()>0) ? Float.valueOf(incendioRisco.getRiscoPoliticoPremio()) : -1f;
                riscosIncendio.add("109;"+incendioRisco.getRiscoPoliticoValor() + ";" + incendioRisco.getRiscoPoliticoTaxa() + ";" + incendioRisco.getRiscoPoliticoPremio());
            }
            if (incendioRisco.getDanoId().equals("true")) {
                dano = (incendioRisco.getDanoValor() != null && incendioRisco.getDanoValor().length()>0) ? Float.valueOf(incendioRisco.getDanoValor()) : -1f;
                dano2 = (incendioRisco.getDanoPremio() != null && incendioRisco.getDanoPremio().length()>0) ? Float.valueOf(incendioRisco.getDanoPremio()) : -1f;
                riscosIncendio.add("110;"+incendioRisco.getDanoValor() + ";" + incendioRisco.getDanoTaxa() + ";" + incendioRisco.getDanoPremio());
            }
            if (incendioRisco.getTumultoId().equals("true")) {
                tumulto = (incendioRisco.getTumultoValor() != null && incendioRisco.getTumultoValor().length()>0) ? Float.valueOf(incendioRisco.getTumultoValor()) : -1f;
                tumulto2 = (incendioRisco.getTumultoPremio() != null && incendioRisco.getTumultoPremio().length()>0) ? Float.valueOf(incendioRisco.getTumultoPremio()) : -1f;
                riscosIncendio.add("111;"+incendioRisco.getTumultoValor() + ";" + incendioRisco.getTumultoTaxa() + ";" + incendioRisco.getTumultoPremio());
            }
            if (incendioRisco.getIncendioFId().equals("true")) {
                incendioF = (incendioRisco.getIncendioFValor() != null && incendioRisco.getIncendioFValor().length()>0) ? Float.valueOf(incendioRisco.getIncendioFValor()) : -1f;
                incendioF2 = (incendioRisco.getIncendioFPremio() != null && incendioRisco.getIncendioFPremio().length()>0) ? Float.valueOf(incendioRisco.getIncendioFPremio()) : -1f;
                riscosIncendio.add("112;"+incendioRisco.getIncendioFValor() + ";" + incendioRisco.getIncendioFTaxa() + ";" + incendioRisco.getIncendioFPremio());
            }
            if (incendioRisco.getExplosaoExId().equals("true")) {
                explosaoF = (incendioRisco.getExplosaoExValor() != null && incendioRisco.getExplosaoExValor().length()>0) ? Float.valueOf(incendioRisco.getExplosaoExValor()) : -1f;
                explosaoF2 = (incendioRisco.getExplosaoExPremio() != null && incendioRisco.getExplosaoExPremio().length()>0) ? Float.valueOf(incendioRisco.getExplosaoExPremio()) : -1f;
                riscosIncendio.add("113;"+incendioRisco.getExplosaoExValor() + ";" + incendioRisco.getExplosaoExTaxa() + ";" + incendioRisco.getExplosaoExPremio());
            }
            if (incendioRisco.getRoturaId().equals("true")) {
                rotura = (incendioRisco.getRoturaValor() != null && incendioRisco.getRoturaValor().length()>0) ? Float.valueOf(incendioRisco.getRoturaValor()) : -1f;
                rotura2 = (incendioRisco.getRoturaPremio() != null && incendioRisco.getRoturaPremio().length()>0) ? Float.valueOf(incendioRisco.getRoturaPremio()) : -1f;
                riscosIncendio.add("114;"+incendioRisco.getRoturaValor() + ";" + incendioRisco.getRoturaTaxa() + ";" + incendioRisco.getRoturaPremio());
            }
            
            System.err.println("ffff "+riscosIncendio.size()+"       "+Arrays.toString(riscosIncendio.toArray()));
            
                totalSegurado = aviao + colisao + explosao + terramoto + tempestade + afundamento + politico + dano + tumulto + incendioF + explosaoF + rotura;
                premioLiquido = aviao2 + colisao2 + explosao2 + terramoto2 + tempestade2 + afundamento2 + politico2 + dano2 + tumulto2 + incendioF2 + explosaoF2 + rotura2;
            
            if(totalSegurado ==0/*||aviao  == -1 || colisao  == -1 || explosao  == -1 || terramoto  == -1 || tempestade  == -1 || afundamento  == -1 || politico  == -1 || dano  == -1 || tumulto  == -1 || incendioF  == -1 || explosaoF  == -1 || rotura== -1
                    ||aviao2  == -1 || colisao2  == -1 || explosao2  == -1 || terramoto2  == -1 || tempestade2  == -1 || afundamento2  == -1 || politico2  == -1 || dano2  == -1 || tumulto2  == -1 || incendioF2  == -1 || explosaoF2  == -1 || rotura2 ==-1*/)
            {
                Mensagem.addErrorMsg("Selecione pelo menos um risco adicional");
                RequestContext.getCurrentInstance().update("Incendio:msg");
            }
            else
            {
                premioBruto = (percentagemRetirar * premioLiquido)/100;
                incendio.setTotalSegurado(String.valueOf(totalSegurado));
                incendio.setTotalSeguradoFormatado(Moeda.format(totalSegurado));
                incendio.setPremioBruto(String.valueOf(premioBruto));
                incendio.setPremioBrutoFormatado(Moeda.format(premioBruto));
                incendio.setPremioLiquido(String.valueOf(premioLiquido));
                incendio.setPremioLiquidoFormatado(Moeda.format(premioLiquido));
                
                construirArrays();
                if(incendio.getParede().size()>0&&incendio.getPavimento().size()>0&&incendio.getTecto().size()>0)
                {
                    System.out.println(incendio.toString());   
                    SessionUtil.definirParametro("FR", this);
                    SessionUtil.definirParametro("respostas", listaRespostas);
                    RequestContext.getCurrentInstance().execute("IncendioAvancar()");
                }
                else
                {
                    Mensagem.addWarningMsg("Selecione pelo menos um tipo de cobertura para pavimento, parede e teto.");
                    RequestContext.getCurrentInstance().update("Incendio:msg");
                }
            }
           
        }
        
    }
    
    public boolean verificarNumeroRegistro()
    {
        @SuppressWarnings("UnusedAssignment")
        boolean valido = false;
        FacesContext context = FacesContext.getCurrentInstance();
        if(incendio.getNumeroRegistro() != null && !incendio.getNumeroRegistro().equals(""))
        {
            valido = ContratoDao.isNumRegistroVago(incendio.getNumeroRegistro());
            if(valido == false)
            {
                info = "Número de registro já existe";
                 RequestContext.getCurrentInstance().addCallbackParam("registro", "já existe");
                 Mensagem.addErrorMsg("Número de registro já existe");
                 RequestContext.getCurrentInstance().update("Incendio:msg");
            }
            else
            {
                valido = true;
                 RequestContext.getCurrentInstance().update("Incendio:msg");
            }
        }
        else
        {
            Message.addWarningMsg("Informe o número de registro", "Incendio", "msg");
            RequestContext.getCurrentInstance().execute("topoIncendio()");
            valido = false;
        }
        return valido;
    }

    private void construirArrays() {
        construirPavimentos();
        construirParede();
        construirTecto();
    }

    private void construirParede() {
        if(this.incendio.isParedeBetao())
        {
            this.incendio.getParede().add("60;"+5);
        }
        if(this.incendio.isParedeTijolo())
        {
            this.incendio.getParede().add("60;"+6);
        }
    }

    private void construirTecto() {
        if(this.incendio.isTectoChapaFibrocimento())
        {
            this.incendio.getTecto().add("59;"+58);
        }
        if(this.incendio.isTectoChapaZinco())
        {
            this.incendio.getTecto().add("59;57");
        }
        if(this.incendio.isTectoPlacabetao())
        {
            this.incendio.getTecto().add("59;59");
        }
        if(this.incendio.isTectoTelha())
        {
            this.incendio.getTecto().add("59;56");
        }
    }

    private void construirPavimentos() {
        if(this.incendio.isPavimentoAzuleijo())
        {
            this.incendio.getPavimento().add("58;"+9);
        }
        if(this.incendio.isPavimentoBaro())
        {
            this.incendio.getPavimento().add("58;"+7);
        }
        if(this.incendio.isPavimentoBetao())
        {
            this.incendio.getPavimento().add("58;"+31);
        }
        if(this.incendio.isPavimentoCerramica())
        {
            this.incendio.getPavimento().add("58;"+10);
        }
        if(this.incendio.isPavimentoMarmore())
        {
            this.incendio.getPavimento().add("58;"+8);
        }
    }

    private Float caluclar(String objValor, String objTaxa) {
        Float vPremio= Float.valueOf(objValor) * (Float.valueOf(objTaxa)/100);
        return vPremio;  
    }
    
    
    public void calcularMultiRisco(MultiRiscoBean multiRiscoBean)
    {
        riscos.clear();
        riscosIncendio.clear();
        @SuppressWarnings("UnusedAssignment")
        Float premioBruto = 0f, premioLiquido = 0f, totalSegurado = 0f;
        Float aviao = 0f,terramoto = 0f,colisao = 0f,tempestade = 0f,explosao = 0f,afundamento = 0f,politico = 0f,dano = 0f,
        tumulto = 0f,incendioF = 0f,explosaoF = 0f,rotura = 0f,aviao2 = 0f,terramoto2 = 0f,colisao2 = 0f,tempestade2 = 0f
        ,explosao2 = 0f,afundamento2 = 0f,politico2 = 0f,dano2 = 0f,tumulto2 = 0f,incendioF2 = 0f,explosaoF2 = 0f,rotura2 = 0f;
        if (incendioRisco.getRiscoAviaoId().equals("true")) 
        {
            aviao = (incendioRisco.getAviaoValor() != null && incendioRisco.getAviaoValor().length()>0) ? Float.valueOf(incendioRisco.getAviaoValor()) : -1f;
            aviao2 = (incendioRisco.getAviaoPremio() != null && incendioRisco.getAviaoPremio().length()>0) ? Float.valueOf(incendioRisco.getAviaoPremio()) : -1f;
            riscosIncendio.add("103;"+incendioRisco.getAviaoValor() + ";" + incendioRisco.getAviaoTaxa() + ";" + incendioRisco.getAviaoPremio());
        }
        if (incendioRisco.getColisaoId().equals("true")) {
            colisao = (incendioRisco.getColisaoValor() != null && incendioRisco.getColisaoValor().length()>0) ? Float.valueOf(incendioRisco.getColisaoValor()) : -1f;
            colisao2 = (incendioRisco.getColisaoPremio() != null && incendioRisco.getColisaoPremio().length()>0) ? Float.valueOf(incendioRisco.getColisaoPremio()) : -1f;
            riscosIncendio.add("104;"+incendioRisco.getColisaoValor() + ";" + incendioRisco.getColisaoTaxa() + ";" + incendioRisco.getColisaoPremio());
        }
        if (incendioRisco.getExplosaoId().equals("true")) {
            explosao = (incendioRisco.getExplosaoValor() != null && incendioRisco.getExplosaoValor().length()>0) ? Float.valueOf(incendioRisco.getExplosaoValor()) : -1f;
            explosao2 = (incendioRisco.getExplosaoPremio() != null && incendioRisco.getExplosaoPremio().length()>0) ? Float.valueOf(incendioRisco.getExplosaoPremio()) : -1f;
            riscosIncendio.add("105;"+incendioRisco.getExplosaoValor() + ";" + incendioRisco.getExplosaoTaxa() + ";" + incendioRisco.getExplosaoPremio());
        }
        if (incendioRisco.getTerramotoId().equals("true")) {
            terramoto2 = (incendioRisco.getTerramotoPremio() != null && incendioRisco.getTerramotoPremio().length()>0) ? Float.valueOf(incendioRisco.getTerramotoPremio()) : -1f;
            terramoto = (incendioRisco.getTerramotoValor() != null && incendioRisco.getTerramotoValor().length()>0) ? Float.valueOf(incendioRisco.getTerramotoValor()) : -1f;
            riscosIncendio.add("106;"+incendioRisco.getTerramotoValor() + ";" + incendioRisco.getTerramotoTaxa() + ";" + incendioRisco.getTerramotoPremio());
        }
        if (incendioRisco.getTempestadeId().equals("true")) {
            tempestade = (incendioRisco.getTempestadeValor() != null && incendioRisco.getTempestadeValor().length()>0) ? Float.valueOf(incendioRisco.getTempestadeValor()) : -1f;
            tempestade2 = (incendioRisco.getTempestadePremio() != null && incendioRisco.getTempestadePremio().length()>0) ? Float.valueOf(incendioRisco.getTempestadePremio()) : -1f;
            riscosIncendio.add("107;"+incendioRisco.getTempestadeValor() + ";" + incendioRisco.getTempestadeTaxa() + ";" + incendioRisco.getTempestadePremio());
        }
        if (incendioRisco.getAfundamentoId().equals("true")) {
            afundamento = (incendioRisco.getAfundamentoValor() != null && incendioRisco.getAfundamentoValor().length()>0) ? Float.valueOf(incendioRisco.getAfundamentoValor()) : -1f;
            afundamento2 = (incendioRisco.getAfundamentoPremio() != null && incendioRisco.getAfundamentoPremio().length()>0) ? Float.valueOf(incendioRisco.getAfundamentoPremio()) : -1f;
            riscosIncendio.add("108;"+incendioRisco.getAfundamentoValor() + ";" + incendioRisco.getAfundamentoTaxa() + ";" + incendioRisco.getAfundamentoPremio());
        }
        if (incendioRisco.getRiscoPoliticoId().equals("true")) {
            politico = (incendioRisco.getRiscoPoliticoValor() != null && incendioRisco.getRiscoPoliticoValor().length()>0) ? Float.valueOf(incendioRisco.getRiscoPoliticoValor()) : -1f;
            politico2 = (incendioRisco.getRiscoPoliticoPremio() != null && incendioRisco.getRiscoPoliticoPremio().length()>0) ? Float.valueOf(incendioRisco.getRiscoPoliticoPremio()) : -1f;
            riscosIncendio.add("109;"+incendioRisco.getRiscoPoliticoValor() + ";" + incendioRisco.getRiscoPoliticoTaxa() + ";" + incendioRisco.getRiscoPoliticoPremio());
        }
        if (incendioRisco.getDanoId().equals("true")) {
            dano = (incendioRisco.getDanoValor() != null && incendioRisco.getDanoValor().length()>0) ? Float.valueOf(incendioRisco.getDanoValor()) : -1f;
            dano2 = (incendioRisco.getDanoPremio() != null && incendioRisco.getDanoPremio().length()>0) ? Float.valueOf(incendioRisco.getDanoPremio()) : -1f;
            riscosIncendio.add("110;"+incendioRisco.getDanoValor() + ";" + incendioRisco.getDanoTaxa() + ";" + incendioRisco.getDanoPremio());
        }
        if (incendioRisco.getTumultoId().equals("true")) {
            tumulto = (incendioRisco.getTumultoValor() != null && incendioRisco.getTumultoValor().length()>0) ? Float.valueOf(incendioRisco.getTumultoValor()) : -1f;
            tumulto2 = (incendioRisco.getTumultoPremio() != null && incendioRisco.getTumultoPremio().length()>0) ? Float.valueOf(incendioRisco.getTumultoPremio()) : -1f;
            riscosIncendio.add("111;"+incendioRisco.getTumultoValor() + ";" + incendioRisco.getTumultoTaxa() + ";" + incendioRisco.getTumultoPremio());
        }
        if (incendioRisco.getIncendioFId().equals("true")) {
            incendioF = (incendioRisco.getIncendioFValor() != null && incendioRisco.getIncendioFValor().length()>0) ? Float.valueOf(incendioRisco.getIncendioFValor()) : -1f;
            incendioF2 = (incendioRisco.getIncendioFPremio() != null && incendioRisco.getIncendioFPremio().length()>0) ? Float.valueOf(incendioRisco.getIncendioFPremio()) : -1f;
            riscosIncendio.add("112;"+incendioRisco.getIncendioFValor() + ";" + incendioRisco.getIncendioFTaxa() + ";" + incendioRisco.getIncendioFPremio());
        }
        if (incendioRisco.getExplosaoExId().equals("true")) {
            explosaoF = (incendioRisco.getExplosaoExValor() != null && incendioRisco.getExplosaoExValor().length()>0) ? Float.valueOf(incendioRisco.getExplosaoExValor()) : -1f;
            explosaoF2 = (incendioRisco.getExplosaoExPremio() != null && incendioRisco.getExplosaoExPremio().length()>0) ? Float.valueOf(incendioRisco.getExplosaoExPremio()) : -1f;
            riscosIncendio.add("113;"+incendioRisco.getExplosaoExValor() + ";" + incendioRisco.getExplosaoExTaxa() + ";" + incendioRisco.getExplosaoExPremio());
        }
        if (incendioRisco.getRoturaId().equals("true")) {
            rotura = (incendioRisco.getRoturaValor() != null && incendioRisco.getRoturaValor().length()>0) ? Float.valueOf(incendioRisco.getRoturaValor()) : -1f;
            rotura2 = (incendioRisco.getRoturaPremio() != null && incendioRisco.getRoturaPremio().length()>0) ? Float.valueOf(incendioRisco.getRoturaPremio()) : -1f;
            riscosIncendio.add("114;"+incendioRisco.getRoturaValor() + ";" + incendioRisco.getRoturaTaxa() + ";" + incendioRisco.getRoturaPremio());
        }

        System.err.println("ffff "+riscosIncendio.size()+"       "+Arrays.toString(riscosIncendio.toArray()));

            totalSegurado = aviao + colisao + explosao + terramoto + tempestade + afundamento + politico + dano + tumulto + incendioF + explosaoF + rotura;
            premioLiquido = aviao2 + colisao2 + explosao2 + terramoto2 + tempestade2 + afundamento2 + politico2 + dano2 + tumulto2 + incendioF2 + explosaoF2 + rotura2;

        if(totalSegurado ==0/*||aviao  == -1 || colisao  == -1 || explosao  == -1 || terramoto  == -1 || tempestade  == -1 || afundamento  == -1 || politico  == -1 || dano  == -1 || tumulto  == -1 || incendioF  == -1 || explosaoF  == -1 || rotura== -1
                ||aviao2  == -1 || colisao2  == -1 || explosao2  == -1 || terramoto2  == -1 || tempestade2  == -1 || afundamento2  == -1 || politico2  == -1 || dano2  == -1 || tumulto2  == -1 || incendioF2  == -1 || explosaoF2  == -1 || rotura2 ==-1*/)
        {
            Mensagem.addWarningMsg("Selecione pelo menos um risco adicional");
            RequestContext.getCurrentInstance().update("Incendio:msg");
        }
        else
        {
            premioBruto =(percentagemRetirar *premioLiquido)/100;
            incendio.setTotalSegurado(String.valueOf(totalSegurado));
            incendio.setTotalSeguradoFormatado(Moeda.format(totalSegurado));
            incendio.setPremioBruto(String.valueOf(premioBruto));
            incendio.setPremioBrutoFormatado(Moeda.format(premioBruto));
            incendio.setPremioLiquido(String.valueOf(premioLiquido));
            incendio.setPremioLiquidoFormatado(Moeda.format(premioLiquido));

            construirArrays();
            if(incendio.getParede().size()>0&&incendio.getPavimento().size()>0&&incendio.getTecto().size()>0)
            {
                System.out.println(incendio.toString());   
                SessionUtil.definirParametro("FR", this);
                SessionUtil.definirParametro("FR2", "true2");
                SessionUtil.definirParametro("respostas", listaRespostas);
                        
                multiRiscoBean.validar();
            }
            else
            {
                Mensagem.addWarningMsg("Selecione pelo menos um tipo de cobertura, parede ou teto.");
                RequestContext.getCurrentInstance().update("Incendio:msg");
            }
        } 
    }
    
   public boolean validaNumeroApolice()
   {
        if(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null && !SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).equals("N"))
        {
            incendio.setNumeroApolice(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).toString());
            return true;
        }
        else
           return incendio.getNumeroApolice() != null && !incendio.getNumeroApolice().equals("");
   }
}
