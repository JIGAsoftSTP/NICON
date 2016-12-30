
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Helcio Guadalupe
 */
public class Resseguro implements Serializable
{
    private int idTipoResseguro;
    private int id;
    private int idEmpresa;
    private String nomeEmpresa;
    private String idContrato;
    private int idSeguro;
    private Date dataInicio;
    private String dataI;
    private String dataF;
    private Date dataFim;
    private Date dataRegistro;
    private String descricao;
    private String percentagem;
    private String premioGrosso;
    private String risco;
    private String riscoFormatado;
    private String apolice;
    private String limiteResp;
    private String moeda;
    private String moedaSigla;
    private String tipoCobertura;
    private String nomeCliente;
    private String valorTotal;
    private String valorTotalFormatado;
    private String valorPremio;
    private String valorPremioFormatado;
    private String tipoResseguro;
    private String notaDebito;

    public Resseguro()
    {
        
    }
    public Resseguro(String empresa, String valorPremio, String valorRisco, String percentagem,int idEmpresa)
    {
        this.nomeEmpresa = empresa;
        this.valorPremio = valorPremio;
        this.risco =valorRisco;
        this.percentagem = percentagem;
        this.idEmpresa = idEmpresa;
    }
    
    public Resseguro(String empresa,int idEmpresa)
    {
        this.nomeEmpresa = empresa;
         this.idEmpresa = idEmpresa;
    }

    public String getRiscoFormatado() {
        return riscoFormatado;
    }

    public String getIdContrato() {
        return idContrato;
    }

    public String getMoedaSigla() {
        return moedaSigla;
    }

    public void setMoedaSigla(String moedaSigla) {
        this.moedaSigla = moedaSigla;
    }

    public void setIdContrato(String idContrato) {
        this.idContrato = idContrato;
    }

    public int getIdSeguro() {
        return idSeguro;
    }

    public String getNotaDebito() {
        return notaDebito;
    }

    public void setNotaDebito(String notaDebito) {
        this.notaDebito = notaDebito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataI() {
        return dataI;
    }

    public void setDataI(String dataI) {
        this.dataI = dataI;
    }

    public String getDataF() {
        return dataF;
    }

    public void setDataF(String dataF) {
        this.dataF = dataF;
    }

    public String getTipoResseguro() {
        return tipoResseguro;
    }

    public void setTipoResseguro(String tipoResseguro) {
        this.tipoResseguro = tipoResseguro;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public void setRiscoFormatado(String riscoFormatado) {
        this.riscoFormatado = riscoFormatado;
    }

    public String getValorPremioFormatado() {
        return valorPremioFormatado;
    }

    public void setValorPremioFormatado(String valorPremioFormatado) {
        this.valorPremioFormatado = valorPremioFormatado;
    }
    
    public int getIdTipoResseguro() {
        return idTipoResseguro;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getApolice() {
        return apolice;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValorTotalFormatado() {
        return valorTotalFormatado;
    }

    public void setValorTotalFormatado(String valorTotalFormatado) {
        this.valorTotalFormatado = valorTotalFormatado;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(String valorPremio) {
        this.valorPremio = valorPremio;
    }

    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getLimiteResp() {
        return limiteResp;
    }

    public void setLimiteResp(String limiteResp) {
        this.limiteResp = limiteResp;
    }

    public void setIdTipoResseguro(int idTipoResseguro) {
        this.idTipoResseguro = idTipoResseguro;
    }

    public String getPercentagem() {
        return percentagem;
    }

    public void setPercentagem(String percentagem) {
        this.percentagem = percentagem;
    }

    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getPremioGrosso() {
        return premioGrosso;
    }

    public void setPremioGrosso(String premioGrosso) {
        this.premioGrosso = premioGrosso;
    }



    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }


    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValorDeducao() {
        return valorDeducao;
    }

    public void setValorDeducao(String valorDeducao) {
        this.valorDeducao = valorDeducao;
    }
    private String valorDeducao;

    @Override
    public String toString() {
        return "Resseguro{" + "idTipoResseguro=" + idTipoResseguro + ", idEmpresa=" + idEmpresa + ", idContrato=" + idContrato + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", descricao=" + descricao + ", percentagem=" + percentagem + ", premio=" + premioGrosso + ", risco=" + risco + ", apolice=" + apolice + ", limiteResp=" + limiteResp + ", moeda=" + moeda + ", tipoCobertura=" + tipoCobertura + ", nomeCliente=" + nomeCliente + ", valorDeducao=" + valorDeducao + '}';
    }
    
    
    
}
