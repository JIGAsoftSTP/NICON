
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Helio
 */
public class Contrato implements Serializable
{
    private String nome;
    private String premioAnual;
    private String primeiroPremio;
    private String franquia;
    private Date dataContrato;
    private Date dataRegistro;
    private Date dataFim;
    private Date dataInicio;
    private Date dataRenovacao;
    private String observacao;
    private String desconto;
    private String valorPremio;
    private String valorNC;
  
    private int valorDesconto = 0;
    private String valorImpostoCincoPorCento="0";
    private String valorImpostoSeisPorCento="0";
    private String fga;
    private String totalSegurado;
    private String taxa;
    private String premioBruto;
    private String premioLiquido;
    private String premioBrutoMoeda;
    private String premioLiquidoMoeda;
    private String nc;
    private String dias;
    private String excesso;
    private String sigla;
    private String moeda;
    private String totalSeguradoMoeda;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPremioAnual() {
        return premioAnual;
    }

    public String getDias() {
        return dias;
    }

    public String getExcesso() {
        return excesso;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTotalSeguradoMoeda() {
        return totalSeguradoMoeda;
    }

    public void setTotalSeguradoMoeda(String totalSeguradoMoeda) {
        this.totalSeguradoMoeda = totalSeguradoMoeda;
    }

    public void setExcesso(String excesso) {
        this.excesso = excesso;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public void setPremioAnual(String premioAnual) {
        this.premioAnual = premioAnual;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public String getPrimeiroPremio() {
        return primeiroPremio;
    }

    public void setPrimeiroPremio(String primeiroPremio) {
        this.primeiroPremio = primeiroPremio;
    }

    public String getFranquia() {
        return franquia;
    }

    public void setFranquia(String franquia) {
        this.franquia = franquia;
    }

    public Date getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(Date dataContrato) {
        this.dataContrato = dataContrato;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public String getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(String valorPremio) {
        this.valorPremio = valorPremio;
    }

    public String getValorNC() {
        return valorNC;
    }

    public void setValorNC(String valorNC) {
        this.valorNC = valorNC;
    }

    public String getValorImpostoCincoPorCento() {
        return valorImpostoCincoPorCento;
    }

    public void setValorImpostoCincoPorCento(String valorImpostoCincoPorCento) {
        this.valorImpostoCincoPorCento = valorImpostoCincoPorCento;
    }

    public String getValorImpostoSeisPorCento() {
        return valorImpostoSeisPorCento;
    }

    public void setValorImpostoSeisPorCento(String valorImpostoSeisPorCento) {
        this.valorImpostoSeisPorCento = valorImpostoSeisPorCento;
    }

    public String getFga() {
        return fga;
    }

    public void setFga(String fga) {
        this.fga = fga;
    }

    public String getTotalSegurado() {
        return totalSegurado;
    }

    public void setTotalSegurado(String totalSegurado) {
        this.totalSegurado = totalSegurado;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public String getObservacao() {
        return observacao = ((observacao==null||observacao.isEmpty())? "":observacao);
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) 
    {
        this.desconto = desconto;
       
    }

    public int getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(int valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getPremioBrutoMoeda() {
        return premioBrutoMoeda;
    }

    public void setPremioBrutoMoeda(String premioBrutoMoeda) {
        this.premioBrutoMoeda = premioBrutoMoeda;
    }

    public String getPremioLiquidoMoeda() {
        return premioLiquidoMoeda;
    }

    public void setPremioLiquidoMoeda(String premioLiquidoMoeda) {
        this.premioLiquidoMoeda = premioLiquidoMoeda;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public Date getDataRegistro() {
        return ((dataRegistro == null) ? null : this.dataRegistro);
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    
    @Override
    public String toString() {
        return "Contrato{" + "nome=" + nome + ", premioAnual=" + premioAnual + ", primeiroPremio=" + primeiroPremio + ", franquia=" + franquia + ", dataContrato=" + dataContrato + ", dataFim=" + dataFim + ", dataInicio=" + dataInicio + ", dataRenovacao=" + dataRenovacao + ", observacao=" + observacao + ", desconto=" + desconto + ", valorPremio=" + valorPremio + ", valorNC=" + valorNC + ", valorDesconto=" + valorDesconto + ", valorImpostoCincoPorCento=" + valorImpostoCincoPorCento + ", valorImpostoSeisPorCento=" + valorImpostoSeisPorCento + ", fga=" + fga + ", totalSegurado=" + totalSegurado + ", taxa=" + taxa + ", premioBruto=" + premioBruto + ", premioLiquido=" + premioLiquido + ", premioBrutoMoeda=" + premioBrutoMoeda + ", premioLiquidoMoeda=" + premioLiquidoMoeda + ", nc=" + nc + ", dias=" + dias + ", excesso=" + excesso + ", totalSeguradoMoeda=" + totalSeguradoMoeda + '}';
    }

}
