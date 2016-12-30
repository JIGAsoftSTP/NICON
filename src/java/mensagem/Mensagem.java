
package mensagem;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author Helcio
 */
public class Mensagem
{
    public static void addInfoMsg(String mensagem)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
        FacesContext  context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);  
    }
    public static void addErrorMsg(String mensagem)
    {
       FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
        FacesContext  context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);  
    }
    public static void addWarningMsg(String mensagem)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagem);
        FacesContext  context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);  
    }
}
