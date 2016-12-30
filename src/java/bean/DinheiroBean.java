
package bean;
import dao.ContratoDao;
import dao.SeguroDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.Dinheiro;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

@ManagedBean
@ViewScoped
public class DinheiroBean implements Serializable
{    
    private static final long serialVersionUID =1L;
    private List<Dinheiro> infoCofre;
    private Dinheiro dinheiro = new Dinheiro();
    private Dinheiro dinheiro2 = new Dinheiro();
    private Dinheiro dinheiro3 = new Dinheiro();
    private SeguroDao sd;
    private String cobertura;
    private String premio;
    private String limite;
    private  HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
        
    @SuppressWarnings({"LeakingThisInConstructor", "LeakingThisInConstructor"})
    public DinheiroBean()
    {
        infoCofre = new ArrayList<>();
        sd = new SeguroDao();
        if(SessionUtil.obterValor("DH") != null)
        {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
            DinheiroBean db = ((DinheiroBean) SessionUtil.obterValor("DH"));
            for (Field f : this.getClass().getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    f.set(this, f.get(db));
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                }
            }
            RequestContext.getCurrentInstance().execute("backAndcontine()");
        }
    }
    
    public void setInfoCofre(List<Dinheiro> infoCofre) {
        this.infoCofre = infoCofre;
    }

    public List<Dinheiro> getInfoCofre() {
        return infoCofre;
    }
    
    public void risco1()
    {
        dinheiro.Limpar(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        dinheiro.setInten2(false);
        dinheiro.setInten3(false);
        dinheiro.setInten4(false);
        dinheiro.setInten5(false);
        infoCofre.clear();
        Validacao.AtualizarCompoente("dinhFormID", "tabela1");
    }
    public void risco2()
    {
        dinheiro.Limpar(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        dinheiro.setInten1(false);
        dinheiro.setInten3(false);
        dinheiro.setInten4(false);
        dinheiro.setInten5(false);
        infoCofre.clear();
        Validacao.AtualizarCompoente("dinhFormID", "tabela1");
    }
    public void risco3()
    {
        dinheiro.Limpar(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        dinheiro.setInten1(false);
        dinheiro.setInten2(false);
        dinheiro.setInten4(false);
        dinheiro.setInten5(false);
        infoCofre.clear();
        Validacao.AtualizarCompoente("dinhFormID", "tabela1");
    }
    public void risco4()
    {
        dinheiro.Limpar(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        dinheiro.setInten1(false);
        dinheiro.setInten2(false);
        dinheiro.setInten3(false);
        dinheiro.setInten5(false);
        infoCofre.clear();
        Validacao.AtualizarCompoente("dinhFormID", "tabela1");
    }
    public void risco5()
    {
        dinheiro.Limpar(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        dinheiro.setInten1(false);
        dinheiro.setInten2(false);
        dinheiro.setInten3(false);
        dinheiro.setInten4(false);
        infoCofre.clear();
        Validacao.AtualizarCompoente("dinhFormID", "tabela1");
    }
    public Dinheiro getDinheiro() {
        return (dinheiro == null) ? dinheiro = new Dinheiro() : dinheiro;
    }

    public void setDinheiro(Dinheiro dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Dinheiro getDinheiro2() {
        return dinheiro2;
    }

    public void setDinheiro2(Dinheiro dinheiro2) {
        this.dinheiro2 = dinheiro2;
    }
    
    public void addTable()
    {
    
        if((dinheiro.getNome_Fabricante() != null && dinheiro.getNome_Fabricante().length()>0) &&
           (dinheiro.getNumero_Fabricante() != null && dinheiro.getNumero_Fabricante().length()>0) &&
           (dinheiro.getTamanho() != null && dinheiro.getTamanho().length()>0) &&
           (dinheiro.getPeso() != null && dinheiro.getPeso().length()>0) &&
           (dinheiro.getDetentor_Chave() != null && dinheiro.getDetentor_Chave().length()>0) && 
           (dinheiro.getEstrutura() != null && dinheiro.getEstrutura().length()>0))
        {
           infoCofre.add(new Dinheiro(dinheiro.getNome_Fabricante(), dinheiro.getNumero_Fabricante(), dinheiro.getTamanho(), dinheiro.getPeso(), 
                   dinheiro.getDetentor_Chave(),((dinheiro.getConstruido_ou_Fixo() == null) ? "Y" : dinheiro.getConstruido_ou_Fixo()), dinheiro.getEstrutura()));
            dinheiro.limparInfo(null, null, null, null, null, null, null);
            RequestContext.getCurrentInstance().addCallbackParam("limparDetalheC", "true");
        }
    }
    
    public boolean validarNumeroRegistro()
    {
        boolean existe = true;
       if(dinheiro.getNumeroRegistro() != null && dinheiro.getNumeroRegistro().length()>0)
       {
           existe = ContratoDao.isNumRegistroVago(dinheiro.getNumeroRegistro());
           if(existe == false)
           {
               Mensagem.addErrorMsg("Número de registro já existe");
               Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
               Validacao.callBackParam("NumeroRegistro", "já existe");
           }
       }
       else
       {
            existe = false;
            Mensagem.addWarningMsg("Informe o número de registro");
            Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
       }
       return existe;
   }
    /**
     * Limpa todos os dados na linha selecionada
     * @param p 
     */
    public void removeFromTable()
    {
        infoCofre.remove(dinheiro3);
        Validacao.AtualizarCompoente("dinhFormID", "tabela1");
    }
  
    public void limpar()
    {
        infoCofre.clear();
        Validacao.AtualizarCompoente("dinhFormID", "tabela1");
    }
    public void dadosDinheiro(MultiRiscoBean multiRiscoBean)
    {
        if(dinheiro.isInten1() == false && dinheiro.isInten2() == false && dinheiro.isInten3() == false && dinheiro.isInten4() == false && dinheiro.isInten5() == false)
        {
            Mensagem.addInfoMsg("Selecione uma proposta a ser segurada");
            Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
        }
        else
        {
            if(VerificarCampos() == true)
            {
                CoberturaSelecionada();
                SessionUtil.removerParametro("DH2");
                SessionUtil.removerParametro("DH");
                SessionUtil.definirParametro("DH", this);
                SessionUtil.definirParametro("DH2", "true2");
                if(!session.getAttribute("S").toString().split(";")[0].equalsIgnoreCase("MR"))
                    Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");
                else
                   multiRiscoBean.validar();
            }
        }  
    }
    public void seguinte(MultiRiscoBean multiRiscoBean)
    {
        if(session.getAttribute("S") != null && session.getAttribute("S").toString().split(";")[0].equalsIgnoreCase("MR"))
            dadosDinheiro(multiRiscoBean);   
        else
        {
            dinheiro.setSigla(Validacao.Sigla(dinheiro.getMoeda()));
            if(validaNumeroApolice() == true && validarNumeroRegistro() == true)
                dadosDinheiro(null);
        }            
    }

    public Dinheiro getDinheiro3() {
        return (dinheiro3 == null)? dinheiro3= new Dinheiro(): dinheiro3;
    }

    public void setDinheiro3(Dinheiro dinheiro3) {
        this.dinheiro3 = dinheiro3;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }
    
    public void CoberturaSelecionada()
    {
        if(cobertura.equals("32"))
        {
              premio = dinheiro.inten1Valor;
              limite = dinheiro.inten1Lresp;
        }
        if(cobertura.equals("33"))
        {
              premio = dinheiro.inten2Valor;
              limite = dinheiro.inten2Lresp;
        }
        if(cobertura.equals("34"))
        {
             premio = dinheiro.inten3Valor;
             limite = dinheiro.inten3Lresp;
        } 
        if(cobertura.equals("35"))
        {
            premio = dinheiro.inten4Valor; 
            limite = dinheiro.inten4Lresp;
        } 
        if(cobertura.equals("36"))
        {
            premio = dinheiro.inten5Valor;
            limite = dinheiro.inten5Lresp;
        }
             
    }

    public String getLimite() {
        return limite;
    }
    
    public boolean VerificarCampos()
    {
        boolean valido = false;

        if(dinheiro.isInten1() == true)
        {
           cobertura = "32";
            if((dinheiro.getInten1Lresp() == null || dinheiro.getInten1Lresp().equals("")) || (dinheiro.getInten1Valor() == null || dinheiro.getInten1Valor().equals("")))
            {
                 Mensagem.addWarningMsg("Informe o valor de limite de responsabilidade e o valor de risco");
                 Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else  if(dinheiro.getBanco() == null || dinheiro.getBanco().equals(""))
            {
                Mensagem.addWarningMsg("Informe a distância entre o edificio e o banco");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getTempoPermanenciaBanco() == null || dinheiro.getTempoPermanenciaBanco().equals(""))
            {
                Mensagem.addWarningMsg("Informe o tempo de permanência no banco");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getPagamento1item() == null || dinheiro.getPagamento1item().equals(""))
            {
                 Mensagem.addWarningMsg("Selecione a forma como o dinheiro será pago");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getTransporteDinheiro() == null || dinheiro.getTransporteDinheiro().equals(""))
            {
                 Mensagem.addWarningMsg("Informe como será o transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getPrecupacao() == null || dinheiro.getPrecupacao().equals(""))
            {
                Mensagem.addWarningMsg("Informe as precauções tomadas no transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else
            {
                dinheiro.setTotalSegurado(dinheiro.getInten1Lresp());
                dinheiro.setTotalSeguradoFormatado(Moeda.format(Double.valueOf(dinheiro.getInten1Lresp())));
                 valido = true;
            }
               
        }
        
         if(dinheiro.isInten2() == true)
        {
            cobertura = "33";
            if((dinheiro.getInten2Lresp() == null || dinheiro.getInten2Lresp().equals("")) || (dinheiro.getInten2Valor() == null || dinheiro.getInten2Valor().equals("")))
            {
                 Mensagem.addWarningMsg("Informe o valor de limite de responsabilidade e o valor de risco");
                 Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else  if(dinheiro.getOutros() == null || dinheiro.getOutros().equals(""))
            {
                Mensagem.addWarningMsg("Informe a distância entre o edificio e outros");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getTempoPermanenciaBanco() == null || dinheiro.getTempoPermanenciaBanco().equals(""))
            {
                Mensagem.addWarningMsg("Informe o tempo de permanência no banco");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getPagamento1item() == null || dinheiro.getPagamento1item().equals(""))
            {
                 Mensagem.addWarningMsg("Selecione a forma como o dinheiro será pago");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getTransporteDinheiro() == null || dinheiro.getTransporteDinheiro().equals(""))
            {
                 Mensagem.addWarningMsg("Informe como será o transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getPrecupacao() == null || dinheiro.getPrecupacao().equals(""))
            {
                Mensagem.addWarningMsg("Informe as precauções tomadas no transporte do dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            } 
            else
            {
                dinheiro.setTotalSegurado(dinheiro.getInten2Lresp());
                dinheiro.setTotalSeguradoFormatado(Moeda.format(Double.valueOf(dinheiro.getInten2Lresp())));
                 valido = true;
            }
               
        }
 
        if(dinheiro.isInten3() == true)
        {
            cobertura = "34";
             System.out.println(dinheiro.getInten1Valor() == null);
            if((dinheiro.getInten3Lresp() == null || dinheiro.getInten3Lresp().equals("")) || (dinheiro.getInten3Valor() == null || dinheiro.getInten3Valor().equals("")))
            {
                 Mensagem.addWarningMsg("Informe o valor de limite de responsabilidade e o valor de risco");
                 Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else  if(dinheiro.getCorreio()== null || dinheiro.getCorreio().equals(""))
            {
                Mensagem.addWarningMsg("Informe a distância entre o edificio e o correio");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getTransporteDinheiro() == null || dinheiro.getTransporteDinheiro().equals(""))
            {
                 Mensagem.addWarningMsg("Informe como será o transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getPrecupacao() == null || dinheiro.getPrecupacao().equals(""))
            {
                Mensagem.addWarningMsg("Informe as precauções tomadas no transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }  
            else
            {
                dinheiro.setTotalSegurado(dinheiro.getInten3Lresp());
                dinheiro.setTotalSeguradoFormatado(Moeda.format(Double.valueOf(dinheiro.getInten3Lresp())));
                valido = true;
            }
                
        }
 
        if(dinheiro.isInten4() == true)
        {
            cobertura = "35";
            if((dinheiro.getInten4Lresp() == null || dinheiro.getInten4Lresp().equals("")) || (dinheiro.getInten4Valor() == null || dinheiro.getInten4Valor().equals("")))
            {
                 Mensagem.addWarningMsg("Informe o valor de limite de responsabilidade e o valor de risco");
                 Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else  if(infoCofre.size() == 0)
            {
                Mensagem.addWarningMsg("Preencha as informações de descrição de cada cofre e adicione-os na tabela");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getTransporteDinheiro() == null || dinheiro.getTransporteDinheiro().equals(""))
            {
                 Mensagem.addWarningMsg("Informe como será o transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getPrecupacao() == null || dinheiro.getPrecupacao().equals(""))
            {
                Mensagem.addWarningMsg("Informe as precauções tomadas no transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            } 
            else
            {
                 dinheiro.setTotalSegurado(dinheiro.getInten4Lresp());
                valido = true;
                dinheiro.setTotalSeguradoFormatado(Moeda.format(Double.valueOf(dinheiro.getInten4Lresp())));
            }
               
        }
 
        if(dinheiro.isInten5() == true)
        {
            cobertura = "36";
             System.out.println(dinheiro.getInten1Valor() == null);
            if((dinheiro.getInten5Lresp() == null || dinheiro.getInten5Lresp().equals("")) || (dinheiro.getInten5Valor() == null || dinheiro.getInten5Valor().equals("")))
            {
                 Mensagem.addWarningMsg("Informe o valor de limite de responsabilidade e o valor de risco");
                 Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else  if(dinheiro.getOutros() == null || dinheiro.getOutros().equals(""))
            {
                Mensagem.addWarningMsg("Informe a distância entre o edificio e o outros");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getTransporteDinheiro() == null || dinheiro.getTransporteDinheiro().equals(""))
            {
                 Mensagem.addWarningMsg("Informe como será o transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            }
            else if(dinheiro.getPrecupacao() == null || dinheiro.getPrecupacao().equals(""))
            {
                Mensagem.addWarningMsg("Informe as precauções tomadas no transporte de dinheiro");
                Validacao.AtualizarCompoente("dinhFormID", "dinhGrowl");
            } 
            else
            {
                dinheiro.setTotalSegurado(dinheiro.getInten5Lresp());
                dinheiro.setTotalSeguradoFormatado(Moeda.format(Double.valueOf(dinheiro.getInten5Lresp())));
                 valido = true;
            }
               
        }
        return valido;
    }
    
    public boolean validaNumeroApolice()
    {
        if(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null && !SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).equals("N"))
        {
            dinheiro.setNumApolice(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).toString());
            return true;
        }
        else
        {
            if(dinheiro.getNumApolice() != null && !dinheiro.getNumApolice().equals(""))
                return true;
            else
            {
                Message.addWarningMsg("Informe o número de apolice", "dinhFormID", "dinhGrowl");  
                RequestContext.getCurrentInstance().execute("apoliceDinheiro()");
                RequestContext.getCurrentInstance().execute("moveTop()");
                return false;
            }
        }
    }
}
