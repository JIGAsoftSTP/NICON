
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helcio Guadalupe
 */
public class Conta implements Serializable
{
    private String tipoConta;
    private String banco;
    private int idAccount;
    private String numRaiz;
    private String nivel;
    private String numClasse;
    private String designacao;
    private String conta;
    private String numConta;
    private String numContaPagamento;
    private String moeda;
    private String tipoContaMovimento = "1";
    private String saldo;
    private String registro;
    private int estado;
    private String credito;
    private String debito;
    private String obs;
    private String typeOperation;
    private String operationInsurance;
    private String accountOperationValue;
    private String value;
   

    public Conta()
    {
        
    }
    

    public Conta(Conta conta) 
    {
        this.banco = conta.getBanco();
        this.designacao = conta.getDesignacao();
        this.conta = conta.getConta();
        this.numConta = conta.getNumConta();
        this.numContaPagamento = conta.getNumContaPagamento();
        this.moeda = conta.getMoeda();
        this.tipoContaMovimento = conta.getTipoContaMovimento();
        this.saldo = conta.getSaldo();
        this.registro = conta.getRegistro();
        this.credito = conta.getCredito();
        this.debito = conta.getDebito();
        this.idAccount = conta.getIdAccount();
    }

        public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getNumClasse() {
        return numClasse;
    }

    public String getAccountOperationValue() {
        return accountOperationValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setAccountOperationValue(String accountOperationValue) {
        this.accountOperationValue = accountOperationValue;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public String getOperationInsurance() {
        return operationInsurance;
    }

    public void setOperationInsurance(String operationInsurance) {
        this.operationInsurance = operationInsurance;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public void setNumClasse(String numClasse) {
        this.numClasse = numClasse;
    }

    public String getNumRaiz() {
        return numRaiz;
    }

    public void setNumRaiz(String numRaiz) {
        this.numRaiz = numRaiz;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }


    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCredito() {
        return credito;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getDebito() {
        return debito;
    }

    public void setDebito(String debito) {
        this.debito = debito;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
   


    
    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumContaPagamento() {
        return numContaPagamento;
    }

    public void setNumContaPagamento(String numContaPagamento) {
        this.numContaPagamento = numContaPagamento;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getTipoContaMovimento() {
        return tipoContaMovimento;
    }

    public void setTipoContaMovimento(String tipoContaMovimento) {
        this.tipoContaMovimento = tipoContaMovimento;
    }

    @Override
    public String toString() {
        return "Conta{" + "tipoConta=" + tipoConta + ", banco=" + banco + ", designacao=" + designacao + ", conta=" + conta + ", numConta=" + numConta + ", numContaPagamento=" + numContaPagamento + ", moeda=" + moeda + ", tipoContaMovimento=" + tipoContaMovimento + '}';
    }

  

    
    
}
