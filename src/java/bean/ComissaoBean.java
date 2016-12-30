
package bean;

import dao.ContratoDao;
import dao.FuncionarioDao;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mensagem.Message;
import modelo.Comissao;
import modelo.ComoBox;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Helcio Guadalupe
 */
@ViewScoped
@ManagedBean
public class ComissaoBean
{
    private static final long SERIAL_VERSION_UID=1L;
    private final ArrayList<ComoBox> funcList;
    private Comissao comissao = new Comissao();
    private final List<ComoBox> listNotasDebito;
    
    
    
    
    public ComissaoBean()
    {
        funcList = FuncionarioDao.listFuc();
        listNotasDebito = ContratoDao.listaNotasDebito();
    }

    public ArrayList<ComoBox> getFuncList() {
        return funcList;
    }

    public List<ComoBox> getListNotasDebito() {
        return listNotasDebito;
    }

    
    public Comissao getComissao() {
        return (comissao == null)? comissao = new Comissao() : comissao;
    }

    public void setComissao(Comissao comissao) {
        this.comissao = comissao;
    }
    
    public void registrarComissao()
    {
        String result = ContratoDao.regComissao(comissao);
        if(result.split(";")[0].equals("true"))
        {
            Message.addInfoMsg("Comissão registrado com sucesso", "comissaoForm", "comissaoGrowl");
            RequestContext.getCurrentInstance().execute("$('.comissaoCampo').val('')");
        }
        else
            Message.addErrorMsg(result.split(";")[1], "comissaoForm", "comissaoGrowl");
    }
    
    
    
}
