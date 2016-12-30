package bean;

import dao.EmpresaParceriaDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mensagem.Mensagem;
import modelo.ComoBox;
import modelo.EmpresaParceiramodelo;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
//import modelo.EmpresaParceiramodelo;

@ManagedBean(name = "emp_Parceriabean")
@ViewScoped

public class EmpresaPardeiraBean implements Serializable 
{

    private EmpresaParceiramodelo empresaParceiramodelo = new EmpresaParceiramodelo();
    private List<ComoBox> todosPaises = new ArrayList<>();
    private EmpresaParceriaDao empresaParceiraDao;
    private List<ComoBox> mostrarNomeEmpresa = new ArrayList<>();
    
    /////////////////////////////////////////////////////////////
    private List<EmpresaParceiramodelo> listarEmpresaParceira =new ArrayList<>();

    public List<EmpresaParceiramodelo> getListarEmpresaParceira() 
    {
        return (listarEmpresaParceira == null) ? new ArrayList<>() : listarEmpresaParceira;
    }
    

    public void setListarEmpresaParceira(List<EmpresaParceiramodelo> listarEmpresaParceira) 
    {
        this.listarEmpresaParceira = listarEmpresaParceira;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    
    public EmpresaParceriaDao getEmpresaParceiraDao() 
    {
        return empresaParceiraDao;
    }

    public void setEmpresaParceiraDao(EmpresaParceriaDao empresaParceiraDao) {
        this.empresaParceiraDao = empresaParceiraDao;
    }

    public List<ComoBox> getMostrarNomeEmpresa() 
        {
            return mostrarNomeEmpresa;
        }

    public void setMostrarNomeEmpresa(List<ComoBox> mostrarNomeEmpresa) 
        {
            this.mostrarNomeEmpresa = mostrarNomeEmpresa;
        }

    public EmpresaPardeiraBean()//construtor para todos os paises e para mostrar todos dados na tabela
    {
        empresaParceiraDao = new EmpresaParceriaDao();
        todosPaises = empresaParceiraDao.todosPaises();
        listarEmpresaParceira = empresaParceiraDao.listarEmpresaParceira();
        //removerTabelaEmpresaParceira = empresaParceiraDao.removerTabelaEmpresaParceira();
    }

    public EmpresaParceiramodelo getEmpresaParceiramodelo() {
        return (empresaParceiramodelo == null) ? empresaParceiramodelo= new EmpresaParceiramodelo() : empresaParceiramodelo;
    }

    public void setEmpresaParceiramodelo(EmpresaParceiramodelo empresaParceiramodelo) {
        this.empresaParceiramodelo = empresaParceiramodelo;
    }


    public List<ComoBox> getTodosPaises() {
        return todosPaises = (todosPaises == null) ? new ArrayList<>() : todosPaises;
    }

    public void setTodosPaises(List<ComoBox> todosPaises) {
        this.todosPaises = todosPaises;
    }

    public void registarEmpresaParceiraBean() {
        try {
        if(empresaParceiraDao.mostrarNomeEmpresa() != empresaParceiraDao.mostrarNomeEmpresa()){
            EmpresaParceriaDao empresaParceriaDao1 = new EmpresaParceriaDao();
            Object re = empresaParceriaDao1.registarEmpresaParceira(empresaParceiramodelo);
            //apresentar messagem 
            if (re.equals("true")) {
                Mensagem.addInfoMsg("Nova empresa Registada");
                listarEmpresaParceira = empresaParceiraDao.listarEmpresaParceira();
                RequestContext.getCurrentInstance().execute("limparcampos()");
                validacao.Validacao.AtualizarCompoente("menssa:menssaForm", "parceiroGrowl"); //id do formulario e do growl
                 validacao.Validacao.AtualizarCompoente("menssa", "tableDEmPar"); 
            } else {
                Mensagem.addErrorMsg(re.toString());
                validacao.Validacao.AtualizarCompoente("menssa:menssaForm", "parceiroGrowl");//id do formulario e do growl
            }
        }
        } catch (Exception ex) {
            Logger.getLogger(EmpresaPardeiraBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void removerdadosTabela()
    {
        empresaParceiraDao.removerTabelaEmpresaParceira(Integer.parseInt(SessionUtil.getUserlogado().getId().toString()), empresaParceiramodelo.getIdEmpresas());
         listarEmpresaParceira = empresaParceiraDao.listarEmpresaParceira();
        validacao.Validacao.AtualizarCompoente("menssa", "tableDEmPar"); 
    }
}
