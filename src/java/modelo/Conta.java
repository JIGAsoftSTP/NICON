
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helcio Guadalupe
 */
public class Conta
{
    private String tipoConta = "banco";
    private String banco;
    private int idAccount;
    private String designacao;
    private String conta;
    private String numConta;
    private String numContaPagamento;
    private String moeda;
    private String tipoContaMovimento;
    private String saldo;
    private String registro;
    private String estado = "Ativo";
    private String credito;
    private String debito;
    private String tipo = "Conta Banco";
    private String fieldSearch = "BANCO";

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

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getDebito() {
        return debito;
    }

    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }

    public String getFieldSearch() {
        return fieldSearch;
    }

    public void setDebito(String debito) {
        this.debito = debito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
