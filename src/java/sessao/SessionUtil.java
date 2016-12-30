package sessao;

import modelo.Funcionario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Empresa;

/**
 * Classe responsavel por criar, recuperar e remover dados da sessão
 *
 * @author Helio
 */
@ManagedBean
public class SessionUtil implements Serializable {

    private static final long serialVersionUID = 31L;
    public static final String SESSION_AMOUNT_REGISTRO = "registro";
    public static final String APOLICE_CONTRATO_SEGURO = "apolice_contrato";
    private String nomeAcesso;
    private String ocultarMostrarMenuAdm;
    private Funcionario funcionario = new Funcionario();
    private Funcionario dadosUtilizador = new Funcionario();
    private Funcionario conjugue = new Funcionario();
    private Empresa empresa = new Empresa();

    public SessionUtil() {

    }

    public static HttpSession getSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            HttpSession hs = (HttpSession) facesContext.getExternalContext().getSession(true);
            hs.setMaxInactiveInterval(-1);
            return hs;
        }
        return null;
    }

    public static void definirParametro(String nomeParametro, Object valor) {
        if (getSession().getAttribute(nomeParametro) == null) {
            getSession().setAttribute(nomeParametro, valor);
        }
    }

    public String getOcultarMostrarMenuAdm() {
        return ocultarMostrarMenuAdm;
    }

    public void setOcultarMostrarMenuAdm(String ocultarMostrarMenuAdm) {
        this.ocultarMostrarMenuAdm = ocultarMostrarMenuAdm;
    }

    public Funcionario getConjugue() {
        return ((Funcionario) obterValor("dadosConjugue"));
    }

    public Funcionario getFuncionario() {
        return ((Funcionario) obterValor("dadosUtilizador"));
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public static Object obterValor(String nomeParametro) {
        return ((getSession() == null) ? null : getSession().getAttribute(nomeParametro));
    }

    public static void removerParametro(String nomeParametro) {
        getSession().removeAttribute(nomeParametro);
    }

    public static void invalidar() {
        getSession().invalidate();
    }

    public String getNomeAcesso() {
        if (SessionUtil.obterValor("nomeUtilizador") != null) {
            return (SessionUtil.obterValor("nomeUtilizador")).toString();
        } else {
            return "Sem Sessao";
        }
    }

    public static Funcionario getUserlogado() {
        return (obterValor("utilizador") == null) ? new Funcionario() : ((Funcionario) obterValor("utilizador"));
    }

    public Funcionario getDadosUtilizador() {
        return ((Funcionario) obterValor("utilizador"));
    }

}
