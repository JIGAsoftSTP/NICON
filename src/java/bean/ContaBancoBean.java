/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ContaBancoDao;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mensagem.Message;
import modelo.ComoBox;
import modelo.ContaBanco;
import org.primefaces.context.RequestContext;
import validacao.Validacao;

/**
 *
 * @author ahmedjorge
 */
@ManagedBean
@ViewScoped
public class ContaBancoBean {
    private ContaBanco contaBanco;
    private ArrayList<ContaBanco> listContaBanco;
    private ArrayList<ComoBox> listTipoConta;
    private ArrayList<ComoBox> listContaContaBilistica;
    private ArrayList<ComoBox> listBanco;
    private ArrayList<ComoBox> listMoeda;
    private String pesquisaConta;

    public ContaBancoBean() {
        listMoeda = ComoBox.loadAllDados("VER_MOEDA", "ID", "MOEDA");
        listTipoConta = ComoBox.loadAllDados("VER_TYPEACCOUNTBANK", "ID", "TYPECONTA");
        listContaContaBilistica = ComoBox.loadAllDados("VER_ACCOUNT where STATE = 1", "ID", "NUMBER");
        listBanco = ComoBox.loadAllDados("VER_BANK", "ID", "NOME");
        listContaBanco = new ContaBancoDao().getListContaBanco(null);
    }

    public ContaBanco getContaBanco() {
        return (contaBanco == null) ? contaBanco = new ContaBanco() : contaBanco;
    }
    
    public void regContaBanco(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        contaBanco.setIdtipoConta(Int(facesContext.getExternalContext().getRequestParameterMap().get("tipoconta")));
        contaBanco.setIdtipoContaContabilistica(Int(facesContext.getExternalContext().getRequestParameterMap().get("contacontabalistica")));
        contaBanco.setIdbanco(Int(facesContext.getExternalContext().getRequestParameterMap().get("banco")));
        contaBanco.setIdTipoMoeda(Int(facesContext.getExternalContext().getRequestParameterMap().get("tipomoeda")));
        contaBanco.setNumConta(facesContext.getExternalContext().getRequestParameterMap().get("numconta"));
        String stra[] = new ContaBancoDao().regAccount(contaBanco);
        if(stra != null && "true".equals(stra[0])){
            RequestContext.getCurrentInstance().execute("limparCBForm()");
            listContaBanco = new ContaBancoDao().getListContaBanco(null);
            Validacao.atualizar("accountTableForm", "tableCantaBanco");
            Message.addInfoMsg("Nova Conta Banco registada com sucesso!","changePassword", "growlChangePassword");
        }else if(stra != null && "false".equals(stra[0]))
        { Message.addErrorMsg(stra[1],"changePassword", "growlChangePassword"); }
        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
    }
    
    public void printDocCB(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String typeDoc = facesContext.getExternalContext().getRequestParameterMap().get("docType");
        new ContaBancoDao().resultPesqu(pesquisaConta, true, typeDoc);
        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
    }
    
     public void pesquisaCB(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        pesquisaConta = facesContext.getExternalContext().getRequestParameterMap().get("pesq");
        listContaBanco = new ContaBancoDao().resultPesqu(pesquisaConta, false, null);
        Validacao.atualizar("accountTableForm", "tableCantaBanco");
        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
    }
    
    public Integer Int(String var){ return Integer.valueOf(var); }

    public void setContaBanco(ContaBanco contaBanco) {
        this.contaBanco = contaBanco;
    }

    public ArrayList<ContaBanco> getListContaBanco() {
        return (listContaBanco == null) ? listContaBanco = new ArrayList<>() : listContaBanco;
    }

    public void setListContaBanco(ArrayList<ContaBanco> listContaBanco) {
        this.listContaBanco = listContaBanco;
    }

    public ArrayList<ComoBox> getListTipoConta() {
        return (listTipoConta == null) ? listTipoConta = new ArrayList<>(): listTipoConta;
    }

    public void setListTipoConta(ArrayList<ComoBox> listTipoConta) {
        this.listTipoConta = listTipoConta;
    }

    public ArrayList<ComoBox> getListContaContaBilistica() {
        return (listContaContaBilistica == null) ? listContaContaBilistica = new ArrayList<>() : listContaContaBilistica ;
    }

    public void setListContaContaBilistica(ArrayList<ComoBox> listContaContaBilistica) {
        this.listContaContaBilistica = listContaContaBilistica;
    }

    public ArrayList<ComoBox> getListBanco() {
        return (listBanco == null) ? listBanco = new ArrayList<>(): listBanco;
    }

    public void setListBanco(ArrayList<ComoBox> listBanco) {
        this.listBanco = listBanco;
    }

    public ArrayList<ComoBox> getListMoeda() {
        return (listMoeda == null) ? listMoeda = new ArrayList<>(): listMoeda;
    }

    public void setListMoeda(ArrayList<ComoBox> listMoeda) {
        this.listMoeda = listMoeda;
    }

    public String getPesquisaConta() {
        return pesquisaConta;
    }

    public void setPesquisaConta(String pesquisaConta) {
        this.pesquisaConta = pesquisaConta;
    }
    
}
