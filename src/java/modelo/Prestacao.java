
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helcio Guadalupe
 */
public class Prestacao implements Serializable
{
    private String id;
    private String valorSF;
    private String valorPagoSF;
    private String valor;
    private Double stdValor;
    private String valorPagoPrestacao;
    private String estadoPrestacao;
    private String numDoc;
    private String formaPagamento;
    private String conta;
    private String valorSTD;
    private String dataRegistro;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    
    public String getValorSF() {
        return valorSF;
    }

    public void setValorSF(String valorSF) {
        this.valorSF = valorSF;
    }

    public String getValorSTD() {
        return valorSTD;
    }

    public void setValorSTD(String valorSTD) {
        this.valorSTD = valorSTD;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }
    
    

    public String getValorPagoSF() {
        return valorPagoSF;
    }

    public void setValorPagoSF(String valorPagoSF) {
        this.valorPagoSF = valorPagoSF;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorPagoPrestacao() {
        return valorPagoPrestacao;
    }

    public void setValorPagoPrestacao(String valorPagoPrestacao) {
        this.valorPagoPrestacao = valorPagoPrestacao;
    }

    public String getEstadoPrestacao() {
        return estadoPrestacao;
    }

    public void setEstadoPrestacao(String estadoPrestacao) {
        this.estadoPrestacao = estadoPrestacao;
    }

    public Double getStdValor() {
        return stdValor;
    }

    public void setStdValor(Double stdValor) {
        this.stdValor = stdValor;
    }

    @Override
    public String toString() {
        return "Prestacao{" + "id=" + id + ", valorSF=" + valorSF + ", valorPagoSF=" + valorPagoSF + ", valor=" + valor + ", valorPagoPrestacao=" + valorPagoPrestacao + ", estadoPrestacao=" + estadoPrestacao + ", numDoc=" + numDoc + ", formaPagamento=" + formaPagamento + ", conta=" + conta + ", valorSTD=" + valorSTD + ", dataRegistro=" + dataRegistro + '}';
    }
    
    
    
}
