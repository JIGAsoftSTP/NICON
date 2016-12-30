
package bean;

import dao.ContratoDao;
import dao.SeguroDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.MultiRisco;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author Helio
 */
@ManagedBean(name = "risco")
@ViewScoped
public class MultiRiscoBean implements Serializable
{
    private MultiRisco multiRisco = new MultiRisco();
    private SeguroDao sd = new SeguroDao();
    private RouboBean rouboBean;
    private DinheiroBean dinheiroBean;
    private IncendioBean incendioBean;
    private String estado;
    
    
    public MultiRiscoBean()
    {
        SessionUtil.removerParametro("FR2");
        SessionUtil.removerParametro("DH2");
        SessionUtil.removerParametro("DI2");
        RequestContext.getCurrentInstance().execute("riscos()");
        if(SessionUtil.obterValor("MR") != null)
        {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
            MultiRiscoBean multiRiscoBean = ((MultiRiscoBean) SessionUtil.obterValor("MR"));
            for (Field f : this.getClass().getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    f.set(this, f.get(multiRiscoBean));
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                }
            }
            RequestContext.getCurrentInstance().execute("backAndcontine()");
        }
        
    }

    public MultiRisco getMultiRisco() 
    {
        return (multiRisco == null) ? multiRisco = new MultiRisco() : multiRisco;
    }

    public RouboBean getRouboBean() {
        return rouboBean;
    }

    public DinheiroBean getDinheiroBean() {
        return dinheiroBean;
    }

    public IncendioBean getIncendioBean() {
        return incendioBean;
    }

    
    public void setMultiRisco(MultiRisco multiRisco) {
        this.multiRisco = multiRisco;
    }
    
    public boolean verificarNumeroRegistro()
    {
        boolean existe = true;
        if(multiRisco.getNumeroRegistro() != null && !multiRisco.getNumeroRegistro().equals(""))
        { 
            existe = ContratoDao.isNumRegistroVago(multiRisco.getNumeroRegistro());
            if(existe == false)
            {
                Message.addErrorMsg("Número de registro já existe", "multiCabecalho", "multiRiscoGrowl");
                RequestContext.getCurrentInstance().execute("moveTop()");
            }  
        }
        else
        {
            Message.addErrorMsg("Informe o número de registro", "multiCabecalho", "multiRiscoGrowl");
            RequestContext.getCurrentInstance().execute("moveTop()");
            existe = false;
        }
        return existe;
    }
     
    public void verificarSeguroSelecionado()
    {
        
    }
    public void validar()
    {
        if(verificarNumeroRegistro() == true && validaNumeroApolice() == true)
            dados();
    }
    
    public void segurosAdicionar()
    {
        estado =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado");
    }
    public void dados()
    {
        double limiteResp;
        SessionUtil.removerParametro("MR");
        if(SessionUtil.obterValor("FR2") != null && SessionUtil.obterValor("DI2") != null && SessionUtil.obterValor("DH2") != null)
        {
            System.out.println("todos");
            rouboBean = ((RouboBean) SessionUtil.obterValor("DI"));
            incendioBean = ((IncendioBean) SessionUtil.obterValor("FR"));
            dinheiroBean = ((DinheiroBean) SessionUtil.obterValor("DH"));
            limiteResp = Double.valueOf(rouboBean.getRoubo().getLimiteRsp())+ Double.valueOf(incendioBean.getIncendio().getTotalSegurado()) + Double.valueOf(dinheiroBean.getDinheiro().getTotalSegurado());
            multiRisco.setLimiteResponsabilidade(String.valueOf(limiteResp));
            multiRisco.setLimiteResponsabilidadeFormatado(Moeda.format(limiteResp));
            multiRisco.setPremioBruto(incendioBean.getIncendio().getPremioBruto());
            multiRisco.setPremioLiquido(incendioBean.getIncendio().getPremioLiquido());

            SessionUtil.definirParametro("MR", this);
            Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");

        }
        else if(SessionUtil.obterValor("FR2") != null && SessionUtil.obterValor("DI2") != null && SessionUtil.obterValor("DH2") == null)
        {
             System.out.println("incêndio e roubo");
            rouboBean = ((RouboBean) SessionUtil.obterValor("DI"));
            incendioBean = ((IncendioBean) SessionUtil.obterValor("FR"));
            limiteResp = Double.valueOf(rouboBean.getRoubo().getLimiteRsp())+ Double.valueOf(incendioBean.getIncendio().getTotalSegurado());
            multiRisco.setLimiteResponsabilidade(String.valueOf(limiteResp));
            multiRisco.setLimiteResponsabilidadeFormatado(Moeda.format(limiteResp));
            multiRisco.setPremioBruto(incendioBean.getIncendio().getPremioBruto());
            multiRisco.setPremioLiquido(incendioBean.getIncendio().getPremioLiquido());
            
            SessionUtil.definirParametro("MR", this);
            Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");
        }
        else if(SessionUtil.obterValor("FR2") != null && SessionUtil.obterValor("DH2") != null)
        {
             System.out.println("incêndio e dinheiro");
            incendioBean = ((IncendioBean) SessionUtil.obterValor("FR"));
            dinheiroBean = ((DinheiroBean) SessionUtil.obterValor("DH"));
            limiteResp = Double.valueOf(incendioBean.getIncendio().getTotalSegurado())+ Double.valueOf(dinheiroBean.getDinheiro().getTotalSegurado());
            multiRisco.setLimiteResponsabilidade(String.valueOf(limiteResp));
            multiRisco.setLimiteResponsabilidadeFormatado(Moeda.format(limiteResp));
            multiRisco.setPremioBruto(incendioBean.getIncendio().getPremioBruto());

            SessionUtil.definirParametro("MR", this);
            Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");
        }
        else if(SessionUtil.obterValor("DI2") != null && SessionUtil.obterValor("DH2") != null)
        {
             System.out.println("roubo e dinheiro entrouuuuuuuuuuu");
            rouboBean = ((RouboBean) SessionUtil.obterValor("DI"));
            dinheiroBean = ((DinheiroBean) SessionUtil.obterValor("DH"));
            limiteResp = Double.valueOf(rouboBean.getRoubo().getLimiteRsp())+ Double.valueOf(dinheiroBean.getDinheiro().getTotalSegurado());
            multiRisco.setLimiteResponsabilidade(String.valueOf(limiteResp));
            multiRisco.setLimiteResponsabilidadeFormatado(Moeda.format(limiteResp));
     
            SessionUtil.definirParametro("MR", this);
            Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");
        }
    }
    
    public boolean validaNumeroApolice()
    {
        if(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null && !SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).equals("N"))
        {
            multiRisco.setNumeroApolice(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).toString());
            return true;
        }
        else
        {
            if(multiRisco.getNumeroApolice() != null && !multiRisco.getNumeroApolice().equals(""))
                return true;
           else
            {
                Mensagem.addWarningMsg("Informe o número de apolice");
                Validacao.AtualizarCompoente("multiCabecalho", "multiRiscoGrowl");
                RequestContext.getCurrentInstance().execute("moveTop()");
                return false;
            }   
        }
    }
     
}
