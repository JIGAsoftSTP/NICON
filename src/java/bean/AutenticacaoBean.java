
package bean;

import modelo.Funcionario;
import dao.FuncionarioDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;


/**
 * Classe que trata do acesso do funcionário a aplicação
 * @author ildo
 */
@ManagedBean(name = "login")
@ViewScoped
public class AutenticacaoBean implements Serializable
{
    private static final long serialVersionUID =1L;
    private Funcionario funcionarioLogado = new Funcionario();
    private Funcionario funcionario= new Funcionario();
    private Funcionario conjugue= new Funcionario();
    private Funcionario funcionarioData= new Funcionario();
    private FuncionarioDao fd= new FuncionarioDao();
    private String mensagem;
    private String nomeAcesso;
    
    public AutenticacaoBean()
    {
    }
    public Funcionario getFuncionarioLogado()
    {
        if(funcionarioLogado == null)
        {
            funcionarioLogado = new Funcionario();
        }
        return funcionarioLogado;
    }

    public String getMensagem()
    {
        FacesContext  facesContext = FacesContext.getCurrentInstance();
        if(facesContext.getExternalContext().getRequestServletPath().equals("index.xhtml"))
        {
            if(SessionUtil.obterValor("utilizador")!=null)
            {
                try 
                {
                    facesContext.getExternalContext().redirect("/GestaoContratos/GestaoContratos.xhtml");
                }
                catch (IOException ex) 
                {
                    
                }
            }
        }
        return mensagem;
    }

    public void setMensagem(String mensagem) 
    {
        this.mensagem = mensagem;
    }
    
    public void setFuncionarioLogado(Funcionario funcionarioLogado)
    {
        this.funcionarioLogado= funcionarioLogado;
    }
    
    public void acessarAplicacao() // método que permite o funcionário acessar a aplicação
    {
        funcionarioData = fd.logar(funcionarioLogado);
        nomeAcesso = funcionarioLogado.getNomeAcesso();
        if( funcionarioData == null)
            mensagem ="Nome de utilizador e/ou palavra-passe inválido(a) !";
        else
        {     
            funcionarioLogado = funcionarioData;
            funcionarioLogado.setNomeAcesso(nomeAcesso);
            if(funcionarioData.getEstado().equals("2"))
                RequestContext.getCurrentInstance().execute("modalAtivarUtilizador()"); 
            else
                dadosSessao();  
        }  
    }
    
    public void dadosSessao()
    {
        funcionario = fd.informations(fd.getInfo("T_FUNCIONARIO", "FUNC_ACCESSNAME", "FUNC_NIF", nomeAcesso));
        conjugue = fd.dadosConjugue(funcionarioLogado.getId().toString()) ;
        mensagem = null;
        SessionUtil.removerParametro("utilizador");
        SessionUtil.removerParametro("respostas");
        SessionUtil.removerParametro("dadosUtilizador");
        SessionUtil.removerParametro("dadosConjugue");
        SessionUtil.definirParametro("utilizador",funcionarioLogado);
        SessionUtil.definirParametro("dadosUtilizador",funcionario);
        SessionUtil.definirParametro("dadosConjugue",conjugue);        
        controloAcesso();
    }
    public String terminarSessao()
    {
        funcionarioLogado = null;
        SessionUtil.removerParametro("utilizador");
        SessionUtil.removerParametro("respostas");
        SessionUtil.removerParametro("dadosUtilizador");
        SessionUtil.removerParametro("dadosConjugue");
        SessionUtil.removerParametro("nomeUtilizador");
        System.err.println("remover login");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AutenticacaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    public void ativarUtilizador()
    {
        if((funcionario.getPwd() != null && !funcionario.getPwd().equals("")) && (funcionario.getConfirmarSenha() != null && !funcionario.getConfirmarSenha().equals("")))
        {
             if(!funcionario.getPwd().equals(funcionario.getConfirmarSenha()))
                RequestContext.getCurrentInstance().execute("eliminarCampo()");
            else
            {
                fd.ativarUtilizador(Integer.valueOf(funcionarioLogado.getId().toString()), funcionario.getPwd());
                RequestContext.getCurrentInstance().execute("ocultarMensagem()");
                dadosSessao();
            }
        }
    }
    
    public void controloAcesso()
    {
        /**
         * Niveis de utilizador
         * 3- Analista
         * 5-Operário
         */
        if(!funcionarioLogado.getNivelAcesso().equals("2")&&!funcionarioLogado.getNivelAcesso().equals("0"))
        {
            funcionarioLogado.setMenuAdministrador("ocultar");
        }
        switch (funcionarioLogado.getIdDepartamento()) 
        {
            case "2": // MENU CONTABILIDADE
                funcionarioLogado.setMenuGestaoContrato("ocultar");
                funcionarioLogado.setMenuSinistro("ocultar");
                funcionarioLogado.setMenuRecHumanano("ocultar");
                funcionarioLogado.setMenuAuditoria("ocultar");
                funcionarioLogado.setMenuAdministrador("ocultar");
                if(funcionarioLogado.getNivelAcesso().equals("3"))
                {
                    funcionarioLogado.setJustRelatorio(true);
                    Validacao.redirecionar("Contabilidade/Relatorio.xhtml");
                }
                else{Validacao.redirecionar("Contabilidade/Pgtos_Rcbtos.xhtml");}
                break;
            case "1": // MENU GESTÃO DE CONTRATOS
                funcionarioLogado.setMenuContabilidade("ocultar");
                funcionarioLogado.setMenuSinistro("ocultar");
                funcionarioLogado.setMenuRecHumanano("ocultar");
                funcionarioLogado.setMenuAuditoria("ocultar");
                funcionarioLogado.setMenuAdministrador("ocultar");
                if(funcionarioLogado.getNivelAcesso().equals("3"))
                {
                    funcionarioLogado.setJustRelatorio(true);
                    Validacao.redirecionar("GestaoContratos/GestSeg_Clientes.xhtml");
                }
                else{Validacao.redirecionar("GestaoContratos/GestaoContratos.xhtml");}
                break;
            case "3": // MENU RECURSOS HUMANOS
//                funcionarioLogado.setMenuContabilidade("ocultar");
                funcionarioLogado.setMenuGestaoContrato("ocultar");
                funcionarioLogado.setMenuSinistro("ocultar");
                funcionarioLogado.setMenuAuditoria("ocultar");
                funcionarioLogado.setMenuAdministrador("ocultar");
                if(funcionarioLogado.getNivelAcesso().equals("3"))
                {
                    funcionarioLogado.setJustRelatorio(true);
                    Validacao.redirecionar("RecursosHumanos/Relatorio.xhtml");
                }
                else{Validacao.redirecionar("RecursosHumanos/Funcionarios.xhtml");}
                break;
            case "4": //MENU SINISTRO
                funcionarioLogado.setMenuGestaoContrato("ocultar");
                funcionarioLogado.setMenuContabilidade("ocultar");
                funcionarioLogado.setMenuRecHumanano("ocultar");
                funcionarioLogado.setMenuAuditoria("ocultar");
                funcionarioLogado.setMenuAdministrador("ocultar");
                if(funcionarioLogado.getNivelAcesso().equals("3"))
                {
                    funcionarioLogado.setJustRelatorio(true);
                    Validacao.redirecionar("Sinistro/Relatorio.xhtml");
                }
                else{Validacao.redirecionar("Sinistro/Registro.xhtml");}
                break;
            case "5":
                Validacao.redirecionar("GestaoContratos/GestSeg_Clientes.xhtml");
                funcionarioLogado.setJustRelatorio(true);
                break;
        }
    }
}
