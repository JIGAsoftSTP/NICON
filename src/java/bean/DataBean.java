
package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import modelo.Funcionario;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;

/**
 *
 * @author Helio
 */
@ManagedBean
public class DataBean 
{
    private Date data;
    private Date toDay;
    private Date maxDate;
    private Date maxDateNasc;
    private Date minDateNasc;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private Date minDate;
    private Date minDataEmissao;
    private Date maxDataEmissao;
    
    public DataBean()
    {
        
    }

    public Date getToDay() {
        return  new Date();
    }

    public void setToDay(Date toDay) {
        this.toDay = toDay;
    }
    
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public void enviar()
    {
        FacesContext context = FacesContext.getCurrentInstance();
       
        RequestContext.getCurrentInstance().execute("PF('modal').show()");
    }

    public Date getMaxDataEmissao()
    {
        return (maxDataEmissao = new Date());
    }

    public Date getMinDataEmissao() {
        if(SessionUtil.obterValor(Funcionario.SESSION_NAME) != null)
            if(SessionUtil.getUserlogado().getNivelAcesso().equals("0") || SessionUtil.getUserlogado().getNivelAcesso().equals("2"))
                minDataEmissao = new Date(); 
            else
                minDataEmissao = null;
        return minDataEmissao;
    }
    
    public Date getMaxDate() {
        if(SessionUtil.obterValor(Funcionario.SESSION_NAME) != null)
            if(SessionUtil.getUserlogado().getNivelAcesso().equals("0") || SessionUtil.getUserlogado().getNivelAcesso().equals("2"))
                maxDate = null;
            else
                maxDate = null;
       return maxDate;
    }
    public Date getMaior18() throws ParseException {
       String[] dd = sdf.format(new Date()).split("-");
       return maxDate =(sdf.parse("31-12-"+(Integer.valueOf(dd[2])-18)));
    }
    
    public Date getMaior16(){
        try {
            String[] dd = sdf.format(new Date()).split("-");
            return (sdf.parse("31-12-"+(Integer.valueOf(dd[2])-16)));
        } catch (ParseException ex) {
            Logger.getLogger(DataBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    public Date getMaxDateNasc() throws ParseException 
    {
        String[] dd = sdf.format(new Date()).split("-");
       return (sdf.parse("31-12-"+(Integer.valueOf(dd[2])-18)));
    }

    public void setMaxDateNasc(Date maxDateNasc) {
        this.maxDateNasc = maxDateNasc;
    }

    public Date getMinDateNasc() {
        return null;
    }

    public void setMinDateNasc(Date minDateNasc) {
        this.minDateNasc = minDateNasc;
    }

    public Date getMinDate() throws ParseException {
        if(SessionUtil.obterValor(Funcionario.SESSION_NAME) != null)
            if(SessionUtil.getUserlogado().getNivelAcesso().equals("0") || SessionUtil.getUserlogado().getNivelAcesso().equals("2"))
                minDate = null;
            else
                minDate = new Date();
        return minDate;
    
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
    
    
}
