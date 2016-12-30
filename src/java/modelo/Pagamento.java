
package modelo;

import enumeracao.TypeMoviment;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import lib.Moeda;

/**
 *
 * @author Helcio Guadalupe
 */
public class Pagamento implements Serializable 
{

    private  HashMap<String, Object> pagamentosSolicitados;
    
    private String numDoc;
    private String beneficiario;
    private String contaPagamento;
    private String valor;
    private String descricaoPagamento;
    private String contaBanco;
    private String formaPagamento;
    private String numero;
    private String tipoPagamento;
    private String idPagamento;
    private Date dataPagamento;
    private String valorTotal;
    private String valorTotalFormatado;
    private float quantidade;
    private String codigo;
    private String pagamento;
    private String dataRegistro;
    private String estado;
    private String cheque;
    private String valorFormatado;
    private String solicitarPagamentoData;
    private String filtroPagamento = "todos";
    private double totalDebito;
    private double totalCredito;
    
    private String numeroSinistro;
    private String tyOperationDesc;
    private String ocorrencia;
    private String idOcorrencia;
    private String notaDebito;
    private String utilizador;
    private String apolice;
    private TypeMoviment typeMoviment;
    
    
    
    public Pagamento(Pagamento pagamento)
    {
        this.numDoc = pagamento.getNumDoc();
        this.beneficiario = pagamento.getBeneficiario();
        this.contaPagamento = pagamento.getContaPagamento();
        this.valor = pagamento.getValor();
        this.valorFormatado = Moeda.format(Double.valueOf(pagamento.getValor()));
        this.descricaoPagamento = pagamento.getDescricaoPagamento();
        this.valorTotal = pagamento.getValorTotal();
        this.typeMoviment = pagamento.getTypeMoviment();
        this.totalCredito = pagamento.getTotalCredito();
        this.totalDebito = pagamento.getTotalDebito();
        this.tyOperationDesc = pagamento.getTyOperationDesc();
    }

    public Pagamento()
    {    
    }

    public String getValorFormatado() {
        return valorFormatado;
    }

    public void setValorFormatado(String valorFormatado) {
        this.valorFormatado = valorFormatado;
    }

    public String getTyOperationDesc() {
        return tyOperationDesc;
    }

    public void setTyOperationDesc(String tyOperationDesc) {
        this.tyOperationDesc = tyOperationDesc;
    }
    
    public String getNumDoc() {
        return numDoc;
    }

    public double getTotalDebito() {
        return totalDebito;
    }

    public void setTotalDebito(double totalDebito) {
        this.totalDebito = totalDebito;
    }

    public double getTotalCredito() {
        return totalCredito;
    }

    public void setTotalCredito(double totalCredito) {
        this.totalCredito = totalCredito;
    }

   
    public String getValorTotalFormatado() {
        return valorTotalFormatado;
    }

    public String getFiltroPagamento() {
        return filtroPagamento;
    }

    public void setFiltroPagamento(String filtroPagamento) {
        this.filtroPagamento = filtroPagamento;
    }

    public void setValorTotalFormatado(String valorTotalFormatado) {
        this.valorTotalFormatado = valorTotalFormatado;
    }

    public String getNumeroSinistro() {
        return numeroSinistro;
    }

    public void setNumeroSinistro(String numeroSinistro) {
        this.numeroSinistro = numeroSinistro;
    }

    public String getEstado() {
        return estado;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getApolice() {
        return apolice;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getPagamento() {
        return pagamento;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public String getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(String idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public String getSolicitarPagamentoData() {
        return solicitarPagamentoData;
    }

    public void setSolicitarPagamentoData(String solicitarPagamentoData) {
        this.solicitarPagamentoData = solicitarPagamentoData;
    }

    public String getNotaDebito() {
        return notaDebito;
    }

    public void setNotaDebito(String notaDebito) {
        this.notaDebito = notaDebito;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    
    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }
    
    
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    
    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getContaPagamento() {
        return contaPagamento;
    }

    public void setContaPagamento(String contaPagamento) {
        this.contaPagamento = contaPagamento;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricaoPagamento() {
        return descricaoPagamento;
    }

    public void setDescricaoPagamento(String descricaoPagamento) {
        this.descricaoPagamento = descricaoPagamento;
    }

    public String getContaBanco() {
        return contaBanco;
    }

    public void setContaBanco(String contaBanco) {
        this.contaBanco = contaBanco;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public TypeMoviment getTypeMoviment() {
        return typeMoviment;
    }

    public void setTypeMoviment(TypeMoviment typeMoviment) {
        this.typeMoviment = typeMoviment;
    }


    public void setIdPagamento(String idPagamento) {
        this.idPagamento = idPagamento;
    }
    
    public void pagamentoSolicitado(HashMap<String,Object> map)
    {
        pagamentosSolicitados = map;
    }

    public HashMap<String, Object> getPagamentosSolicitados() {
        return pagamentosSolicitados;
    }

    @Override
    public String toString() {
        return "Pagamento{" + "pagamentosSolicitados=" + pagamentosSolicitados + ", numDoc=" + numDoc + ", beneficiario=" + beneficiario + ", contaPagamento=" + contaPagamento + ", valor=" + valor + ", descricaoPagamento=" + descricaoPagamento + ", contaBanco=" + contaBanco + ", formaPagamento=" + formaPagamento + ", numero=" + numero + ", tipoPagamento=" + tipoPagamento + ", idPagamento=" + idPagamento + ", dataPagamento=" + dataPagamento + ", valorTotal=" + valorTotal + ", quantidade=" + quantidade + ", codigo=" + codigo + ", pagamento=" + pagamento + ", dataRegistro=" + dataRegistro + ", estado=" + estado + ", cheque=" + cheque + ", valorFormatado=" + valorFormatado + ", numeroSinistro=" + numeroSinistro + ", ocorrencia=" + ocorrencia + ", idOcorrencia=" + idOcorrencia + ", notaDebito=" + notaDebito + ", utilizador=" + utilizador + '}';
    }
    // tipo de movimento
   
  
  

    
}
