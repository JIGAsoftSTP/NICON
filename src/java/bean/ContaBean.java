package bean;

import dao.ContabilidadeDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    private static final long SERIAL_VERSIONUID = 1L;
    private Conta conta = new Conta();
    private int selectedAccount = -1;
    private final ContabilidadeDao contabilidadeDao = new ContabilidadeDao();
    private List<Conta> listAccounts = new ArrayList<>();
    private List<Conta> listAccountsMovimentavel = new ArrayList<>();
    private List<ComoBox> listTypeOperations = new ArrayList<>();
    private List<ComoBox> listOperationValue = new ArrayList<>();
    private List<ComoBox> listOperationDefinition = new ArrayList<>();
    private List<ComoBox> listTypeMov = new ArrayList<>();
    private List<String> listSelectedInsurances = new ArrayList<>();
    private List<Conta> listAccountsOriginal = new ArrayList<>();
    private List<Conta> listOperations = new ArrayList<>();
    
    private int operation = 1; // 1- registar conta e 2- registar desdobramento da conta 
    private int tamanhoConta;
    private String search, multiInsuranceField;

    
    public ContaBean()
    {  
        listAccounts = contabilidadeDao.listaContaRaiz(1, null, null);    
        listAccountsMovimentavel = contabilidadeDao.listaContaRaizMovimentavel();    
        listAccountsOriginal = listAccounts;  
        listTypeOperations = ComoBox.loadAllDados("T_OPERATIONGROUP", "OPRGROUP_COD", "OPRGROUP_DESC");
        listTypeMov = ComoBox.loadAllDados("T_TYPEMOVIMENTO", "TMOV_ID", "TMOV_OPERACTION");
//        listOperations = contabilidadeDao.listaOperacoes();
    }
    
    public List<Conta> getListAccounts() {
        return listAccounts;
    }

    public Conta getConta() {
        return (conta == null) ? conta = new Conta() : conta;
    }

    public List<ComoBox> getListTypeMov() {
        return listTypeMov;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public List<Conta> getListOperations() {
        return listOperations;
    }

    public String getSearch() {
        return search;
    }

    public List<ComoBox> getListOperationDefinition() {
        return listOperationDefinition;
    }

    public List<ComoBox> getListOperationValue() {
        return listOperationValue;
    }

    public List<ComoBox> getListTypeOperations() {
        return listTypeOperations;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Conta> getListAccountsMovimentavel() {
        return listAccountsMovimentavel;
    }

    public void regConta()
    {
        if(operation == 1){
           
            if(conta.getNumConta().length() == tamanhoConta){
                String result = contabilidadeDao.registarConta(conta);
                if(result.split(";")[0].equals("false")){
                    if(result.split(";")[1].equals("GIJA001001")){
                       conta.setNumConta(conta.getNumConta().substring(0, conta.getNumConta().length()-1));
                        RequestContext.getCurrentInstance().execute("$('.contaNum').val('"+conta.getNumConta()+"'), desdobrarConta()");
                        operation = 2;
                    }
                    else Message.addErrorMsg(result.split(";")[1], "GestConta", "accountGrowl");
                }
                else{
                    Message.addInfoMsg("Conta registada com sucesso!", "GestConta", "accountGrowl");
                    RequestContext.getCurrentInstance().execute("accountRegistered()");
                    operation = 1;
                    listAccounts = contabilidadeDao.listaContaRaiz(1, null, null);
                    validacao.Validacao.atualizar("accountTableForm", "accountTable");
                    validacao.Validacao.atualizar("GestConta", "contaR");
                            
                }   
            }
        }
        else{
            String result = contabilidadeDao.regDesdobrarConta(conta);
            if(result.split(";")[0].equals("true")){
               RequestContext.getCurrentInstance().execute("accountRegistered()");
                Message.addInfoMsg("Conta registada com sucesso!", "GestConta", "accountGrowl");
                operation =1;
                listAccounts = contabilidadeDao.listaContaRaiz(1, null, null);
                  validacao.Validacao.atualizar("accountTableForm", "accountTable");
                    validacao.Validacao.atualizar("GestConta", "contaR");
            }
            else Message.addErrorMsg(result.split(";")[1], "GestConta", "accountGrowl");
        }
    }
    
    public void determineClassTypeLevel(){
        char raiz;
        System.out.println("dddddd");
        if(conta.getIdAccount() != -1){
          
            for(Conta c : listAccounts){
                if(c.getIdAccount() == conta.getIdAccount()){
                    raiz = c.getNumRaiz().charAt(0);
                    conta.setNumClasse(raiz+"");
                    conta.setNivel(c.getNivel());
                    conta.setTipoConta(c.getTipoConta());
                    conta.setNumConta(c.getNumRaiz());
                    tamanhoConta = conta.getNumConta().length()+1;
                    RequestContext.getCurrentInstance().execute("$('.contaNum').val('"+conta.getNumConta()+"'),$('.contaClasse').html('"+conta.getNumClasse()+"'),"
                            + "$('.contaNivel').html('"+conta.getNivel()+"'),$('.contaTipo').html('"+conta.getTipoConta()+"'),activeNumberAccount()");
                    if(c.getEstado() == 1)
                        Message.addInfoMsg("Esta Conta é Movimentável. Se continuar terá que desdobrá-la!", "GestConta", "accountGrowl");
                    break;
                }
            }
            
        }
        else
            RequestContext.getCurrentInstance().execute("$('.accountField').val(''),$('.accountFieldLabel').html('')");
    }
    
    public void updateListOperacao(){
        listOperations = contabilidadeDao.listaOperacoes(this.conta.getIdAccount());
        validacao.Validacao.atualizar("GestConta", "operacao_table");
    }
    
    public void update(Conta c){
        selectedAccount = c.getIdAccount();
        char raiz;
        conta.setIdAccount(c.getIdAccount());
        raiz = c.getNumRaiz().charAt(0);
        conta.setNumClasse(raiz+"");
        conta.setNivel(c.getNivel());
        conta.setTipoConta(c.getTipoConta());
        conta.setNumConta(c.getNumRaiz());
        conta.setDesignacao(c.getDesignacao());
        validacao.Validacao.atualizar("GestConta", "contaDesignacao");
          RequestContext.getCurrentInstance().execute("$('.contaRaiz').val('"+conta.getIdAccount()+"'),$('.contaNum').val('"+conta.getNumConta()+"'),$('.contaClasse').html('"+conta.getNumClasse()+"'),"
                            + "$('.contaNivel').html('"+conta.getNivel()+"'),$('.contaTipo').html('"+conta.getTipoConta()+"'),updateAccount(1)");
    }
    
    public void accountOperation(){
        if(selectedAccount == -1)
            regConta();
        else
        {
            String result = contabilidadeDao.updateAccount(conta);
            if(result.split(";")[0].equals("true")){
                    Message.addInfoMsg("Conta atualizada com sucesso", "GestConta", "accountGrowl");
                    selectedAccount = -1;
                          RequestContext.getCurrentInstance().execute("accountRegistered()");
                listAccounts = contabilidadeDao.listaContaRaiz(1, null, null);
                validacao.Validacao.atualizar("accountTableForm", "accountTable");
            }
            else Message.addInfoMsg(result.split(";")[1], "GestConta", "accountGrowl");
        }
    }
    
    public void addNewAccount(){
        if(selectedAccount ==-1)
                RequestContext.getCurrentInstance().execute("accountRegistered()");
        else{
            selectedAccount = -1;
            RequestContext.getCurrentInstance().execute("updateAccount(2)");
        }     
    }
    
    public void searchAccountPrint(Integer typePrint){
        contabilidadeDao.listaContaRaiz(1, search, typePrint);
        RequestContext.getCurrentInstance().execute("$('.processamento').hide();");
    }
    
    public void searchAccount(int type){
       if(type == 1){
          listAccounts = contabilidadeDao.listaContaRaiz(2, search, null);
           Validacao.atualizar("accountTableForm", "accountTable"); 
       }
       else{
           if(search.equals("")){
               listAccounts = listAccountsOriginal;
               Validacao.atualizar("accountTableForm", "accountTable"); 
           }
       } 
    }
    
    public void determineOperationValue()
    {
        if(conta.getTypeOperation().equals("REG.SEG"))
              RequestContext.getCurrentInstance().execute("disableField('ativar')");
        else
        {
            RequestContext.getCurrentInstance().execute("disableField('desativar')");
        }

        listOperationValue = contabilidadeDao.listOperationValue(conta.getTypeOperation());
        listOperationDefinition = contabilidadeDao.listOperationDef(conta.getTypeOperation());
        Validacao.atualizar("GestConta", "accountOpValue", "accountInsurance");   
    }
    
    public void desativarOperacao(Conta c)
    {
 
        String result = contabilidadeDao.disableOperation(c.getIdAccount());
        
        if(result.split(";")[0].equals("true"))
        {
            Message.addInfoMsg("Operação desativada com sucesso!", "GestConta", "accountGrowl");
        }
        else
        {
              Message.addErrorMsg(result.split(";")[1], "GestConta", "accountGrowl");
        }
    }
    
    public void determineIns(String insurance)
    {
        multiInsuranceField = "";
        if(conta.getOperationInsurance().equals("true"))
        {
            if(insurance.equalsIgnoreCase("all"))
            {
                listSelectedInsurances.clear();
                conta.setOperationInsurance("true");
                listOperationDefinition.forEach((ComoBox cb) -> 
                {
                    if(!cb.getId().equalsIgnoreCase(insurance))
                        listSelectedInsurances.add(cb.getId());
                });
                Validacao.atualizar("GestConta", "accountInsurance");
            }
            else
                listSelectedInsurances.add(insurance);  
        }
        else
        {
           if(insurance.equalsIgnoreCase("all"))
            {
                listSelectedInsurances.clear();
                conta.setOperationInsurance("false");
                Validacao.atualizar("GestConta", "accountInsurance");
            }
           else
           {
                conta.setOperationInsurance("false");
                 Validacao.atualizar("GestConta", "accountInsuranceALL");
                for(String ins: listSelectedInsurances)
                {
                    if(insurance.equals(ins))
                    {
                        listSelectedInsurances.remove(ins);
                        break;
                    }
                }
           }
        }
        if(listSelectedInsurances.isEmpty())
            RequestContext.getCurrentInstance().execute("$('.MultipleSelectInput').val('')");
        else
        {
            listSelectedInsurances.forEach((value) -> {
              
                if(multiInsuranceField.equals(""))
                   multiInsuranceField =value;
               else
                   multiInsuranceField +=";"+value; 
            });
            
            RequestContext.getCurrentInstance().execute("$('.MultipleSelectInput').val('"+multiInsuranceField+"')");
        }
    }
    public void selectAccountOperation(Conta c)
    {
        selectedAccount = c.getIdAccount();
    }
    public void regOperation()
    {
         String result = "";
        if(!listOperationDefinition.isEmpty() && listSelectedInsurances.isEmpty())
            Message.addWarningMsg("Selecione Seguro(s)!", "GestConta", "accountGrowl");
        else
        {
//            conta.setIdAccount(selectedAccount);
            if(listSelectedInsurances.isEmpty())
                result = contabilidadeDao.regOperation(conta, "DEFAULT");
            else
            {
                for (String ins : listSelectedInsurances) 
                    result = contabilidadeDao.regOperation(conta, ins);
            }
            if(result.split(";")[0].equals("true"))
            {
                selectedAccount = -1;
                Message.addInfoMsg("Operação registrada com sucesso!", "GestConta", "accountGrowl");
                RequestContext.getCurrentInstance().execute("$('.MultipleSelectInput').val('')");
                RequestContext.getCurrentInstance().execute("accountOperationRegistered()");
                listAccounts = contabilidadeDao.listaContaRaiz(1, null, null);
                validacao.Validacao.atualizar("accountTableForm", "accountTable");
                listSelectedInsurances.clear();
                conta.setOperationInsurance("false");
                Validacao.atualizar("GestConta", "accountInsurance");
                this.updateListOperacao();
            }
            else
                Message.addErrorMsg(result.split(";")[1], "GestConta", "accountGrowl");
        }
    }
}
