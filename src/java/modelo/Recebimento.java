
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helcio Guadalupe
 */
public class Recebimento implements Serializable
{
    private String numDeposito;
    private String codigoSeguro;
    private String cliente;
    private String valorTotal;
    private String nomeSeguro;
    private String data;
    private String moeda;
    private String estado;
    private String id;
    private String idPrestacao;
    private String apolice;
    private String valorPagar;
    private String valorPago;
    private String valorSF;
    private String valorPagoSF;
    private String valor;
    private String valorPagoPrestacao;
    private String estadoPrestacao;
    private String idMoeda;
    private Object dataApolice;
    private String filtroRecebimento = "todos";
    

    public String getNumDeposito() {
        return numDeposito;
    }

    public Recebimento(Recebimento r) {
        this.codigoSeguro = r.getCodigoSeguro();
        this.cliente = r.getCliente();
        this.moeda = r.getMoeda();
        this.estado = r.getEstado();
        this.id = r.getId();
        this.valorPagar = r.getValorPagar();
        this.valorPago = r.getValorPago();
        this.dataApolice = r.getDataApolice();
    }
    public Recebimento()
    {
        
    }
    public String getIdMoeda() {
        return idMoeda;
    }

    @Override
    public String toString() {
        return "Recebimento{" + "numDeposito=" + numDeposito + ", codigoSeguro=" + codigoSeguro + ", cliente=" + cliente + ", valorTotal=" + valorTotal + ", nomeSeguro=" + nomeSeguro + ", data=" + data + ", moeda=" + moeda + ", estado=" + estado + ", id=" + id + ", idPrestacao=" + idPrestacao + ", apolice=" + apolice + ", valorPagar=" + valorPagar + ", valorPago=" + valorPago + ", valorSF=" + valorSF + ", valorPagoSF=" + valorPagoSF + ", valor=" + valor + ", valorPagoPrestacao=" + valorPagoPrestacao + ", estadoPrestacao=" + estadoPrestacao + ", idMoeda=" + idMoeda + ", dataApolice=" + dataApolice + ", filtroRecebimento=" + filtroRecebimento + '}';
    }


    public void setIdMoeda(String idMoeda) {
        this.idMoeda = idMoeda;
    }

    public String getFiltroRecebimento() {
        return filtroRecebimento;
    }

    public void setFiltroRecebimento(String filtroRecebimento) {
        this.filtroRecebimento = filtroRecebimento;
    }

    public Object getDataApolice() {
        return dataApolice;
    }

    public void setDataApolice(Object dataApolice) {
        this.dataApolice = dataApolice;
    }


    
    public String getEstadoPrestacao() {
        return estadoPrestacao;
    }

    public void setEstadoPrestacao(String estadoPrestacao) {
        this.estadoPrestacao = estadoPrestacao;
    }

    public String getIdPrestacao() {
        return idPrestacao;
    }

    public String getValorPagoPrestacao() {
        return valorPagoPrestacao;
    }

    public void setValorPagoPrestacao(String valorPagoPrestacao) {
        this.valorPagoPrestacao = valorPagoPrestacao;
    }

    public void setIdPrestacao(String idPrestacao) {
        this.idPrestacao = idPrestacao;
    }

    public String getValorSF() {
        return valorSF;
    }

    public void setValorSF(String valorSF) {
        this.valorSF = valorSF;
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

    public void setNumDeposito(String numDeposito) {
        this.numDeposito = numDeposito;
    }

    public String getCodigoSeguro() {
        return codigoSeguro;
    }

    public void setCodigoSeguro(String codigoSeguro) {
        this.codigoSeguro = codigoSeguro;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApolice() {
        return apolice;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(String valorPagar) {
        this.valorPagar = valorPagar;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNomeSeguro() {
        return nomeSeguro;
    }

    public void setNomeSeguro(String nomeSeguro) {
        this.nomeSeguro = nomeSeguro;
    }

  

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

   
}
