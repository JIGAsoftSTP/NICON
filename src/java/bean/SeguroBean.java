
package bean;

import dao.SeguroDao;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Helio
 */
@ManagedBean(name = "seguroBean")
@RequestScoped
public class SeguroBean implements Serializable
{
    private String codigoSeguro;
    private String nomeSeguro;
    private  HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
    
    public SeguroBean()
    {
         if(session.getAttribute("S")!=null)
          {
              nomeSeguro =  session.getAttribute("S").toString().split(";")[0];
              switch(nomeSeguro)
              {
                  case "TIN":
                       nomeSeguro = "Seguro de Viagem";
                       break;
                   case "MV":
                      nomeSeguro = "Seguro de Automóvel";
                       break;
                   case "GPA":
                      nomeSeguro = "Seguro de Acidente para Grupo";
                       break;
                    case "MAC":
                      nomeSeguro = "Seguro de Carga Maritima";
                       break;
                    case "NHI":
                      nomeSeguro = "Seguro Maritimo";
                       break;
                    case "DI":
                      nomeSeguro = "Seguro de Roubo";
                       break;
                    case "FR":
                      nomeSeguro = "Seguro de Incêndio";
                       break;
                    case "DH":
                        nomeSeguro ="Seguro de Dinheiro";
                        break;
                    case "RP":
                        nomeSeguro = "Seguro de Responsabilidade Pública";
                        break;
                    case "MR":
                        nomeSeguro ="Seguro de Multiriscos";
                        break;
                       
              }
                   
          }
    }
    public String getCodigoSeguro()
    {
        return codigoSeguro;
    }

    public String getNomeSeguro() {
        return nomeSeguro;
    }

    public void setCodigoSeguro(String codigoSeguro) 
    {
        this.codigoSeguro = codigoSeguro;
    }
    
    public void newSeguros(String cod) throws IOException
    {
        SeguroDao dao= new SeguroDao();
        session.setAttribute("S", cod);
        session.setMaxInactiveInterval(-1);
        System.out.println(session.getAttribute("S"));
        //para redim
        FacesContext.getCurrentInstance().getExternalContext().redirect("GestSeg_NovoSeguro.xhtml");
    }

    public void redirecionar() throws IOException
    {
        String seguro = null;
        String pagina = null;
        if(session.getAttribute("S")!=null)
          {
               seguro =  session.getAttribute("S").toString().split(";")[0];
               switch(seguro)
               {
                   case "TIN":
                       pagina = "includes/Viagem.xhtml";
                       break;
                   case "MV":
                      pagina = "includes/seguro_Automovel.xhtml";
                       break;
                   case "GPA":
                      pagina = "includes/AcdentePG.xhtml";
                       break;
                    case "MAC":
                      pagina = "includes/Seg_Carga_Maritima.xhtml";
                       break;
                    case "DI":
                      pagina = "includes/seguro_Roubo.xhtml";
                       break;
                    case "NHI":
                      pagina = "includes/seguro_Maritimo.xhtml";
                      break;
                    case "DH":
                        pagina ="testeDinheiro.xhtml";
                        break;
                    case "RP":
                        pagina ="includes/Seg_ResPublica.xhtml";
                        break;
               }
          }
           FacesContext.getCurrentInstance().getExternalContext().redirect(pagina);
    }
    
   
}
