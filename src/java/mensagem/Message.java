/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mensagem;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
public class Message 
{
   public static void addInfoMsg(String mensagem,String idFormulario, String idComponente)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
        FacesContext  context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm); 
         RequestContext.getCurrentInstance().update(idFormulario+":"+idComponente);
    }
    public static void addErrorMsg(String mensagem,String idFormulario, String idComponente)
    {
       FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
        FacesContext  context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);  
         RequestContext.getCurrentInstance().update(idFormulario+":"+idComponente);
    }
    public static void addWarningMsg(String mensagem,String idFormulario, String idComponente)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagem);
        FacesContext  context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);  
        RequestContext.getCurrentInstance().update(idFormulario+":"+idComponente);
    }
    
   

}

