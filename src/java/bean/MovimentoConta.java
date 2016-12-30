/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ContabilidadeDao;
import enumeracao.TipoPesquisa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.Cheque;
import modelo.ComoBox;
import modelo.CreditoDebito;
import modelo.Movimento;
import org.primefaces.context.RequestContext;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author AhmedJorge
 */
@ManagedBean(name = "admCont")
@ViewScoped
public class MovimentoConta implements Serializable {

    private Movimento movimento;
    private CreditoDebito creditoDebito;
    private Cheque cheque;
    private ArrayList<Cheque> chequeList;
//    private ArrayList<ComoBox> bankList;
    private ArrayList<ComoBox> contaList;
    private ArrayList<Cheque> cheques = new ArrayList<>();
    private String search;
    private ContabilidadeDao contabilidadeDao = new ContabilidadeDao();
    private List<CreditoDebito> listaCreditos = new ArrayList<>();
    private List<CreditoDebito> listaLancamentos = new ArrayList<>();
    private List<CreditoDebito> creditInfo = new ArrayList<>();
    private final ArrayList<ComoBox> listaMoedas;
    private final ArrayList<ComoBox> typeMoviment;
    
    public MovimentoConta() 
    {
        contaList = ComoBox.loadAllDados("VER_ALL_ACCOUNT WHERE TP IN ('B', 'P')", "ID", "DESC");
        RequestContext.getCurrentInstance().execute("$('.sequenceLaunchNum').val('"+contabilidadeDao.launchSeuquenceNumber()+"')");
        chequeList = contabilidadeDao.listaCheques(null);
        listaMoedas = ComoBox.loadCombo("VER_MOEDA", "ID", "SIGLA");
        typeMoviment = ComoBox.loadCombo("VER_TYPE_LANCAMENTO", "ID","LANCAMENTO");
        listaLancamentos = contabilidadeDao.launchs(null, 1);
    }

    public Movimento getMovimento() {
        return (movimento == null) ? movimento = new Movimento() : movimento;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    public ArrayList<ComoBox> getListaMoedas() {
        return listaMoedas;
    }

    public ArrayList<ComoBox> getTypeMoviment() {
        return typeMoviment;
    }

    public CreditoDebito getCreditoDebito() {
        return (creditoDebito == null) ? creditoDebito = new CreditoDebito() : creditoDebito;
    }

    public void setCreditoDebito(CreditoDebito creditoDebito) {
        this.creditoDebito = creditoDebito;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Cheque getCheque() {
        return (cheque == null) ? cheque = new Cheque() : cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public ArrayList<Cheque> getChequeList() {
        return (chequeList == null) ? chequeList = new ArrayList() : chequeList;
    }

    public void setChequeList(ArrayList<Cheque> chequeList) {
        this.chequeList = chequeList;
    }

    public List<CreditoDebito> getListaCreditos() {
        return listaCreditos;
    }


    public ArrayList<ComoBox> getContaList() {
        return (contaList == null) ? contaList = new ArrayList<>() : contaList;
    }

    public void setContaList(ArrayList<ComoBox> contaList) {
        this.contaList = contaList;
    }

    public void regMovDebCreChe(int i) 
    {
        ContabilidadeDao cd = new ContabilidadeDao();
        if (cheque.getFim().length() < 8)
        {
            if (Float.valueOf(cheque.getFim()) - Float.valueOf(cheque.getInicio()) <= 999f) 
            {
                Object o = cd.regMovimentoAndCheque(3, cheque);
                String[] re = resultDB(o);
                if (re != null & re[0].equals("true")) 
                {
                    Mensagem.addInfoMsg("Sequência de cheque registrado com sucesso.");
                    Validacao.AtualizarCompoente("movimentacao", "movimentacaoGrowl");
                    RequestContext.getCurrentInstance().execute("checkFields('registered');");
                    chequeList = contabilidadeDao.listaCheques(null);
                    Validacao.AtualizarCompoente("movimentacao", "checkTable");
                } else if (re != null & re[0].equals("false")) 
                {
                    Mensagem.addErrorMsg(re[1]);
                    Validacao.AtualizarCompoente("movimentacao", "movimentacaoGrowl");
                    RequestContext.getCurrentInstance().execute("checkFields('error');");
                }
            }
            else Message.addWarningMsg("O total de cheques não pode exceder 999.", "movimentacao", "movimentacaoGrowl");
        }
        else
        {
            Message.addWarningMsg("A sequência de cheque deve ter pelo menos (7) dígitos.", "movimentacao", "movimentacaoGrowl");
            Message.addWarningMsg("Tamanho atual cheque: " + cheque.getFim().length(), "movimentacao", "movimentacaoGrowl");
        }
    }

    public void loadListaChequeBank() {
        ContabilidadeDao cd = new ContabilidadeDao();
        if (cheque.getBanco() != null && !cheque.getBanco().equals("")) {
       //     this.chequeList = cd.chequesConta(Integer.valueOf(cheque.getBanco()), nomeConta());
            Object o = ContabilidadeDao.getNumContaBank(Integer.valueOf(cheque.getBanco()));
            this.cheque.setNumBancoConta((o == null) ? "" : o.toString());
        }
    }

    public String[] resultDB(Object o) {
        if (o != null) {
            return o.toString().split(";");
        } else {
            return null;
        }
    }

    public String nomeConta() {
        String nomeConta = null;
        for (ComoBox cb : this.contaList) {
            if (cheque.getBanco().equals(cb.getId())) {
                nomeConta = cb.getValue();
                break;
            }
        }
        return nomeConta;
    }
    
    public void searchCheck()
    {
        ContabilidadeDao cd = new ContabilidadeDao();
        chequeList = cd.listaCheques(search);
        Validacao.AtualizarCompoente("movimentacao", "checkTable");
    }
    
    public void launchAddTable()
    {
        if(creditoDebito.getMoeda() != -1)
        {
            creditoDebito.setAccountDesc(Validacao.comboNome(contaList, creditoDebito.getConta()));
            this.listaCreditos.add(new CreditoDebito(creditoDebito));
            RequestContext.getCurrentInstance().execute("creditAdded('add')");
            Validacao.atualizar("movimentacao", "launchTable");
            if(listaCreditos.size() == 1)
                RequestContext.getCurrentInstance().execute("moedaAtivarDesativar('desativar')"); 
        }
    }
    
    public void editCredit(CreditoDebito cd)
    {
        String desc;
        creditoDebito.setDataDocumento(cd.getDataDocumento());
        listaCreditos.remove(cd);   
        Validacao.atualizar("movimentacao", "launchTable");
        Validacao.atualizar("movimentacao", "dataEDocumento");
        if(cd.getDesc() != null && !cd.getDesc().equals("")) desc = cd.getDesc();
        else desc = "";
        
        RequestContext.getCurrentInstance().execute("$('.numDocLaunch').val('"+cd.getNumeroDucumento()+"'),$('.creditDesc').val('"+desc+"'),"
                + "$('.inConta').val('"+cd.getConta()+"'), $('.accountDesc').val('"+cd.getAccountDesc()+"'), $('.movimentLaunchDesc').val('"+cd.getMovimentoDesc()+"'),"
                + "$('.launchCoin').val('"+cd.getMoeda()+"'),$('.launchValue').val('"+cd.getValor()+"')");
        if(listaCreditos.isEmpty())
            RequestContext.getCurrentInstance().execute("moedaAtivarDesativar('ativar')");
    }
    private boolean verifyBothTypeOperation()
    {
        short debito = -1, credito = -1;
        if(!listaCreditos.isEmpty())
        {
            for(CreditoDebito cd: listaCreditos)
           {
               if(cd.getTypeOperation() == 1)
                   debito = 1;
               else
                   credito = 2;
           }
           if(credito != -1 && debito != -1)
               return true;
           else
           {
               if(credito != -1 && debito == -1)
               {
                   Message.addWarningMsg("Falta adicionar o Tipo de Operação Débito", "movimentacao", "movimentacaoGrowl");
                   return false;
               }
               else
               {
                   Message.addWarningMsg("Falta adicionar o Tipo de Operação Crédito", "movimentacao", "movimentacaoGrowl");
                   return false;
               }
           }
        }
        else
        {
            Message.addWarningMsg("Adicione informações do lançamento na tabela", "movimentacao", "movimentacaoGrowl");
            return false;
        }
       
    }
    
    private boolean validateSumTypeOperation()
    {
        double debitoSum = 0, creditSum = 0;
        for(CreditoDebito cd: listaCreditos)
        {
            if(cd.getTypeOperation() == 1)
                debitoSum += Double.valueOf(Validacao.unformat(cd.getValor()));
            else
                creditSum += Double.valueOf(Validacao.unformat(cd.getValor()));
        }
        if(debitoSum == creditSum)
            return true;
        else
        {
            Message.addErrorMsg("O somatório de Débito(s) e Crédito(s) não são iguais.", "movimentacao", "movimentacaoGrowl");
            return false;
        }
    }
    
    public void regLaunch()
    {
        int id;
        if(verifyBothTypeOperation() == true)
        {
            if(validateSumTypeOperation() == true)
            {
                String result = contabilidadeDao.regLaunch(creditoDebito,listaCreditos.size());
                if(result.split(";")[0].equals("true"))
                {
                    id = Integer.valueOf(result.split(";")[1]);
                    for(CreditoDebito cd:  listaCreditos)
                    {
                        result = contabilidadeDao.regLaunchAccount(id, cd);
                    }
                    if(result.split(";")[0].equals("true"))
                    {
                        result = contabilidadeDao.endLaunch(id);
                        if(result.split(";")[0].equals("true"))
                        {
                            Message.addInfoMsg("Lançamento registrado com sucesso", "movimentacao", "movimentacaoGrowl");
                            RequestContext.getCurrentInstance().execute("$('.sequenceLaunchNum').val('"+contabilidadeDao.launchSeuquenceNumber()+"')");
                            listaCreditos.clear();
                            listaLancamentos = contabilidadeDao.launchs(null, 1);
                            Validacao.atualizar("movimentacao", "launchTable", "tableLaunch");
                            RequestContext.getCurrentInstance().execute("creditAdded('register')");
                            creditoDebito.setMoeda(-1);
                            RequestContext.getCurrentInstance().execute("moedaAtivarDesativar('ativar')");
                        }
                        else  Message.addErrorMsg(result.split(";")[1], "movimentacao", "movimentacaoGrowl");        
                    }
                    else  Message.addErrorMsg(result.split(";")[1], "movimentacao", "movimentacaoGrowl");        
                }
                else Message.addErrorMsg(result.split(";")[1], "movimentacao", "movimentacaoGrowl");
            }
        }
    }
    
    public void setTypeSearch(int type)
    {
        switch (type) {
            case 1:
                creditoDebito.setTipoPesquisa(TipoPesquisa.TODOS);
                break;
            case 2:
                creditoDebito.setTipoPesquisa(TipoPesquisa.DOCUMENTO);
                break;
            default:
                creditoDebito.setTipoPesquisa(TipoPesquisa.LANCAMENTO);
                break;
        }
    }
    public void searchLaunch()
    {
        listaLancamentos = contabilidadeDao.launchs(search, creditoDebito.getTipoPesquisa().getType());
        Validacao.atualizar("movimentacao", "tableLaunch");
    }
    public void removeCredit(CreditoDebito cd)
    {
        listaCreditos.remove(cd);
        Validacao.atualizar("movimentacao", "launchTable");
        if(listaCreditos.isEmpty())
            RequestContext.getCurrentInstance().execute("moedaAtivarDesativar('ativar')");
    }

    public List<CreditoDebito> getListaLancamentos() {
        return listaLancamentos;
    }
    
    public void maisInf(CreditoDebito cd)
    {
         String desc;
         if(cd.getDesc() != null && !cd.getDesc().equals("")) desc = cd.getDesc();
        else desc = "";
         
        RequestContext.getCurrentInstance().execute("$('.lancamentoValor').val('"+cd.getValor()+"'),$('.lancamentoDescMov').val('"+cd.getMovimentoDesc()+"')");
        RequestContext.getCurrentInstance().execute("$('.lancamentoContaDesc').val('"+cd.getAccountDesc()+"'),$('.lancamentoTipoOp').val('"+cd.getTypeOperationDesc()+"')");
        RequestContext.getCurrentInstance().execute("$('.lancamentoDesc').val('"+desc+"'),$('.lancamentoNumDoc').val('"+cd.getNumeroDucumento()+"')");
        RequestContext.getCurrentInstance().execute("$('.lancamentoMoeda').val('"+Validacao.comboNome(listaMoedas, String.valueOf(cd.getMoeda()))+"'),$('.lancTipoLan').val('"+Validacao.comboNome(this.typeMoviment, String.valueOf(cd.getTypeLaunch()))+"')");
        RequestContext.getCurrentInstance().execute("$('.lancamentoDataC').val('"+OperacaoData.toStringDDMMYYY(cd.getData())+"'),$('.lancTipoLan').val('"+Validacao.comboNome(this.typeMoviment, String.valueOf(cd.getTypeLaunch()))+"')");
   
    }

    public List<CreditoDebito> getCreditInfo() {
        return creditInfo;
    }
    
    public void removeAllFromTable()
    {
        listaCreditos.clear();
        Validacao.atualizar("movimentacao", "launchTable");
        RequestContext.getCurrentInstance().execute("moedaAtivarDesativar('ativar')");
    }
    
    public void creditMoreInfo(CreditoDebito cd)
    {
       this.creditInfo = contabilidadeDao.listCredits(cd.getCodigo(), cd.getTipo());
        Validacao.atualizar("movimentacao", "maisInforL");
        RequestContext.getCurrentInstance().execute("$('.modalCreditInfo').fadeIn()");
    }
}
