
package bean;

import dao.FuncionarioDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mensagem.Mensagem;
import modelo.Funcionario;
import org.primefaces.context.RequestContext;
import validacao.Validacao;

/**
 *
 * @author Helio
 */
@ManagedBean
@ViewScoped
public class UtilizadorBean implements Serializable
{
    private static final long serialVersion = 1L;
    private Funcionario funcionario = new Funcionario();
    private FuncionarioDao funcionarioDao = new FuncionarioDao();
    private List<Funcionario> infoFuncionario;
    
    public UtilizadorBean()
    {
        infoFuncionario = new ArrayList<>();
    }

    public Funcionario getFuncionario()
    {
        return (funcionario == null) ? funcionario = new Funcionario() : funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    /**
     * Altera a palavra-passe do utilizador
     */
    public void alterarSenha()
    {
        Object resultado; 
        if(funcionarioDao.encrypt(funcionario.getSenha()).equals(funcionarioDao.senhaUtilizador()))
        {
           if(!funcionario.getNovaSenha().equals(funcionario.getConfirmarSenha()))
                 RequestContext.getCurrentInstance().execute("senhasErradas()");
            else
            {
                resultado = funcionarioDao.alterarSenha(funcionario);
                if(resultado != null && resultado.equals("true"))
                {
                    RequestContext.getCurrentInstance().execute("senhasCorretas()");
                    Mensagem.addInfoMsg("Palavra passe alterado com sucesso!");
                    Validacao.AtualizarCompoente("changePassword", "growlChangePassword");
                }
            }
        } 
        else
            RequestContext.getCurrentInstance().execute("bordaVermelha()");
    }
    
    public void validarSenhaAtual()
    {
        if(!funcionarioDao.encrypt(funcionario.getSenha()).equals(funcionarioDao.senhaUtilizador()))
            RequestContext.getCurrentInstance().execute("senhaAtualUtilizadorInvalida()");
        else
             RequestContext.getCurrentInstance().execute("senhaAtualUtilizadorValida()");
    }
 
    
 
}
