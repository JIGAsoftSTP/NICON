
package ControloNavegacao;

import Export.ConfigDoc;
import Export.MarcaDAgua;
import modelo.Funcionario;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 * Verifica-se existe algum usuário logado
 * @author ildo
 */
@SuppressWarnings("serial")
public class AutenticacaoPhaseListener implements PhaseListener
{

    @Override
    public void afterPhase(PhaseEvent event) 
    {
        FacesContext facesContext = event.getFacesContext();// saber em que pagina se encontra
        UIViewRoot uiViewRoot = facesContext.getViewRoot();// devolve a página atual
        String paginaAtual = uiViewRoot.getViewId();
        boolean paginaAutenticacao = paginaAtual.contains("index.xhtml");
        boolean paginaAcessoNegado = paginaAtual.contains("denied.xhtml");
        boolean paginaNoPagina = paginaAtual.contains("error-404.xhtml");
        boolean paginaNovoSeguroApolice = paginaAtual.contains("GestSeg_NovoSeguroApolice.xhtml");
        boolean paginaNovoSeguro = paginaAtual.contains("GestSeg_NovoSeguro.xhtml");
        boolean paginaSegContrato = paginaAtual.contains("GestSeg_Contratos.xhtml");
        
        if(SessionUtil.obterValor(Funcionario.SESSION_NAME) != null)
        {
            if(SessionUtil.getUserlogado().getNivelAcesso().equals("3") || SessionUtil.getUserlogado().getIdDepartamento().equals("5"))
                RequestContext.getCurrentInstance().execute("menuAnalista('"+SessionUtil.getUserlogado().getIdDepartamento()+"')");
            else if(SessionUtil.getUserlogado().getNivelAcesso().equals("2") || SessionUtil.getUserlogado().getNivelAcesso().equals("5") )
                RequestContext.getCurrentInstance().execute("admOperario()");
            else
                RequestContext.getCurrentInstance().execute("passado()");
        }
        
        if(!paginaSegContrato){ MarcaDAgua.isCanceled = false; }
       
       if(!paginaAutenticacao && SessionUtil.obterValor(Funcionario.SESSION_NAME) == null) 
            Validacao.redirecionar("../index.xhtml");
       if(!paginaNovoSeguro&&!paginaNovoSeguroApolice)
           sharchSessaoRemove();
    }

    @Override
    public void beforePhase(PhaseEvent event) 
    {    
       
    }
    @Override
    public PhaseId getPhaseId() 
    {
        return PhaseId.RESTORE_VIEW;
    }
    
    public void sharchSessaoRemove()
    {
        if (SessionUtil.obterValor("GPA") != null) {
            SessionUtil.removerParametro("GPA");
        }
        if (SessionUtil.obterValor("NHI") != null) {
            SessionUtil.removerParametro("NHI");
        }
        if (SessionUtil.obterValor("TIN") != null) {
            SessionUtil.removerParametro("TIN");
        }
        if (SessionUtil.obterValor("MAC") != null) {
            SessionUtil.removerParametro("MAC");
        }
        if (SessionUtil.obterValor("DI") != null) {
            SessionUtil.removerParametro("DI");
        }
        if (SessionUtil.obterValor("MV") != null) {
            SessionUtil.removerParametro("MV");
        }
        if (SessionUtil.obterValor("RP") != null) {
            SessionUtil.removerParametro("RP");
        }
        if (SessionUtil.obterValor("FR") != null) {
            SessionUtil.removerParametro("FR");
        }
        if (SessionUtil.obterValor("DH") != null) {
            SessionUtil.removerParametro("DH");
        }
        if (SessionUtil.obterValor("MR") != null) {
            SessionUtil.removerParametro("MR");
        }
        if (SessionUtil.obterValor("respostas") != null) {
            SessionUtil.removerParametro("respostas");
        } 
    }
}
