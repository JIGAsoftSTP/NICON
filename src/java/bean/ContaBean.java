package bean;

import dao.ContabilidadeDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Conta;
import org.primefaces.context.RequestContext;
import validacao.Validacao;


/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean
@ViewScoped
public class ContaBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<ComoBox> listaMovimentos = new ArrayList<>();
    private List<ComoBox> bancos = new ArrayList<>();
    private List<ComoBox> moedas = new ArrayList<>();
    private Conta conta = new Conta();
    private List<Conta> contas = new ArrayList<>();
    private List<ComoBox> listFieldSearch = new ArrayList<>();
    private final ContabilidadeDao contabilidadeDao = new ContabilidadeDao();
    private Conta contaSelecionada = new Conta();
    private String tipoConta = "Conta Banco";
    private String editarConta = "registrar";

    
    public ContaBean()
    {  
    }
    
    @PostConstruct
    public void init()
    {
       this.contas = contabilidadeDao.relatorioContas(null, null,conta.getTipo(), false, 0);  
        moedas = ComoBox.loadCombo("VER_MOEDA", "ID", "SIGLA");
        listaMovimentos = ComoBox.loadCombo("VER_TIPOCONTA", "ID", "TIPO");
        bancos = ComoBox.loadCombo("VER_BANK", "ID","SIGLA");
        listFieldSearch.add(new ComoBox("DESCRICAO", "Conta"));
        listFieldSearch.add(new ComoBox("BANCO", "Banco"));
        listFieldSearch.add(new ComoBox("TIPO MOVIMENTO", "Tipo Movimento"));
        listFieldSearch.add(new ComoBox("MOEDA", "Moeda"));
        listFieldSearch.add(new ComoBox("NUMERO BANCARIO", "Número Bancário"));
    }
    
    public List<ComoBox> getListaMovimentos() {
        return listaMovimentos;
    }

    public List<ComoBox> getListFieldSearch() {
        return listFieldSearch;
    }
    
    public Conta getContaSelecionada() {
        return contaSelecionada;
    }

    public void setContaSelecionada(Conta contaSelecionada) {
        this.contaSelecionada = contaSelecionada;
    }

    
    public List<ComoBox> getBancos() {
        return bancos;
    }

    public List<ComoBox> getMoedas() {
        return moedas;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public Conta getConta() {
        return (conta == null) ? conta = new Conta() : conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    public void operacao()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        editarConta = facesContext.getExternalContext().getRequestParameterMap().get("operation");
    }
    public void manageAccount()
    {
        System.out.println("editar conta "+editarConta);
        if(editarConta.equals("registrar"))
            regConta();
        else
            updateAccount();
    }
    
    private boolean validarAtualizacaoConta()
    {
        if(tipoConta.equals("Conta Banco"))
        {
            if((conta.getConta() != null && !conta.getConta().equals("")) &&
           (conta.getTipoContaMovimento() != null && !conta.getTipoContaMovimento().equals("")) &&
            (conta.getBanco() != null && !conta.getBanco().equals("")))
            {
                return true;
            }
        }
        else if(tipoConta.equals("Conta Pagamento"))
        {
            if(conta.getDesignacao() != null && !conta.getDesignacao().equals(""))
                return true;
        }
        return false;
    }
    private void updateAccount()
    {
        if(validarAtualizacaoConta() == true)
        {
             Object result = contabilidadeDao.updateAccount(conta);
            if(result != null)
            {
                if(result.toString().split(";")[0].equals("true"))
                {
                    this.contas.clear();
                    this.contas = contabilidadeDao.relatorioContas(null, null,tipoConta, false, 0);  
                    Validacao.AtualizarCompoente("accountTableForm", "accountTable");
                    Mensagem.addInfoMsg("Conta atualizado com sucesso");
                    RequestContext.getCurrentInstance().update("contaGrowl:growlConta");
                    RequestContext.getCurrentInstance().execute("registradoConta()");
                    RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                }
                else
                {
                    Message.addErrorMsg(result.toString().split(";")[1], "contaGrowl", "growlConta");
                }
                  
            }        
        }
    }
    private void regConta()
    {
        Object result = null;
        String [] info = null;
        if(conta.getTipoConta().equals("banco"))
        {  
            if((conta.getConta()!= null && !conta.getConta().equals("")) &&
              (conta.getTipoContaMovimento()!= null && !conta.getTipoContaMovimento().equals("")) &&
              (conta.getBanco()!= null && !conta.getBanco().equals("")) &&
              (conta.getNumConta()!= null && !conta.getNumConta().equals("")) &&
               (conta.getMoeda()!=null && !conta.getMoeda().equals(""))  )  
            {
                if(conta.getNumConta().length() == 11)
                {
                    result = contabilidadeDao.registrarConta(conta);
                    info = result.toString().split(";");
                }
                else
                    Message.addWarningMsg("Número de conta deve ter onze(11) digitos!", "contaGrowl", "growlConta");
            }
        }
        else
        {
            if((conta.getDesignacao()!=null && !conta.getDesignacao().equals("")) && (conta.getNumContaPagamento()!=null && !conta.getNumContaPagamento().equals("")))
            {
                result = contabilidadeDao.registrarConta(conta);
                info = result.toString().split(";");
            }
        }
        if(info != null)
        {
            if(info[0].equals("true"))
            {
                this.contas.clear();
                 this.contas = contabilidadeDao.relatorioContas(null, null,tipoConta, false, 0);  
                 Validacao.AtualizarCompoente("accountTableForm", "accountTable");
                Mensagem.addInfoMsg("Nova conta registrado com sucesso");
                RequestContext.getCurrentInstance().update("contaGrowl:growlConta");
                RequestContext.getCurrentInstance().execute("registradoConta()");
                RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
            }
            else
            {
                Mensagem.addErrorMsg(info[1]);
                RequestContext.getCurrentInstance().update("contaGrowl:growlConta");
            }
               
        }
    }

    public String getTipoConta() {
        return tipoConta;
    }
    
    public void searchAccount()
    {
        String valorPesquisa, campoPesquisa, tipoPesquisa;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        campoPesquisa = facesContext.getExternalContext().getRequestParameterMap().get("campoPesquisa");
        valorPesquisa = facesContext.getExternalContext().getRequestParameterMap().get("valorPesquisa");
        tipoConta = facesContext.getExternalContext().getRequestParameterMap().get("conta");
        tipoPesquisa = facesContext.getExternalContext().getRequestParameterMap().get("tipoPesquisa");
        String tipoContaS;
        getConta().setEstado(tipoConta);
       
        
        if(tipoPesquisa != null)
        {
            this.contas.clear();
            switch (tipoPesquisa) 
            {
                case "Por conta":
                    listFieldSearch.clear();
                    if(tipoConta.equals("Conta Banco"))
                    {
                        listFieldSearch.add(new ComoBox("DESCRICAO", "Conta"));
                        listFieldSearch.add(new ComoBox("BANCO", "Banco"));
                        listFieldSearch.add(new ComoBox("TIPO MOVIMENTO", "Tipo Movimento"));
                        listFieldSearch.add(new ComoBox("MOEDA", "Moeda"));
                        listFieldSearch.add(new ComoBox("NUMERO BANCARIO", "Número Bancário"));
                    }
                    else if(tipoConta.equals("Conta Pagamento"))
                    {
                        listFieldSearch.add(new ComoBox("COD", "Conta"));
                        listFieldSearch.add(new ComoBox("DESC", "Descrição"));
                    }
                    else
                    {   
                        listFieldSearch.add(new ComoBox("COD", "Número Conta"));
                        listFieldSearch.add(new ComoBox("DESC", "Descrição"));
                        listFieldSearch.add(new ComoBox("TIPO CONTA", "Tipo Conta"));
                    }
                    this.contas = contabilidadeDao.relatorioContas(null, null,tipoConta,false,0);
                    break;
                case "ExportPDF":
                    contabilidadeDao.relatorioContas(campoPesquisa, valorPesquisa, tipoConta,true,1);
                    break;
                case "ExportExel":
                    contabilidadeDao.relatorioContas(campoPesquisa, valorPesquisa, tipoConta,true,2);
                    break;
                default:
                    this.contas = contabilidadeDao.relatorioContas(campoPesquisa, valorPesquisa, tipoConta,false,0);
                    break;
            }
            if(!tipoPesquisa.equals("ExportPDF") && !tipoPesquisa.equals("ExportExel"))
                Validacao.atualizar("accountTableForm", "accountTable","campoFiltro"); 
        }
    }

 
    public void moreInfo(Conta contaS)
    {
        Conta c = new Conta(contaS);
        this.conta = c;
   
        RequestContext.getCurrentInstance().update("accountMoreInfo");
        RequestContext.getCurrentInstance().execute("accountInfo()");
    }
    
    public void editarConta(Conta conta)
    {
        this.conta = new Conta(conta);
        editarConta = "editar";
        if(tipoConta.equalsIgnoreCase("Conta Banco"))
            RequestContext.getCurrentInstance().execute("atualizarContaBanco('"+this.conta.getConta()+"','"+getId("tipo movimento", this.conta.getTipoContaMovimento())+"','"+getId("banco", this.conta.getBanco())+"','"+this.conta.getNumConta()+"','"+getId("moeda", this.conta.getMoeda())+"')");
        else 
             RequestContext.getCurrentInstance().execute("atualizarContaPagamento('"+this.conta.getConta()+"','"+this.conta.getDesignacao()+"')");
        
        RequestContext.getCurrentInstance().execute("$('.Modalframe').css('display','flex')");
    }
    
    private String getId(String campo, String valor)
    {
        switch (campo) {
            case "moeda":
                for(ComoBox comoBox : moedas)
                {
                    if(comoBox.getValue().equals(valor))
                        return comoBox.getId();
                }   break;
            case "tipo movimento":
                for(ComoBox comoBox : this.listaMovimentos)
                {
                    if(comoBox.getValue().equals(valor))
                        return comoBox.getId();
                }   break;
            default:
                for(ComoBox comoBox : this.bancos)
                {
                    if(comoBox.getValue().equals(valor))
                        return comoBox.getId();
                }   break;
        }
        return null;
    }
}
