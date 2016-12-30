
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Helio
 */
public class ContratoClausulas implements Serializable
{
    private int quantidadeDia;
    private String valorPremio;
    private String valorNC;
    private String valorImpostoCincoPorCento;
    private String valorImpostoSeisPorCento;
    private String premioBruto;
    private String premioLiquido="2399944";
    private String fga;
    private String totalSegurado;
    
    private String codigoSeguro;
    private Date dataInicio;
    private Date dataFim;
    private Date dataContrato;
    private Date dataRenovacao;
    private String observacao;
    private String primeiroPremio;
    private String menos;
    private String liquidoPagar;
    private String premioAnual;
    private String taxaAdicionar;
    private String excesso;
    private String franquia;
    private int numDias;
    public ContratoClausulas()
    {
        
    }
    public ContratoClausulas(String valorNC, String valorImpostoCincoPorCento, String valorImpostoSeisPorCento, String fga, String valorPremio,int numDias) 
    {
        this.valorNC = valorNC;
        this.valorImpostoCincoPorCento = valorImpostoCincoPorCento;
        this.valorImpostoSeisPorCento = valorImpostoSeisPorCento;
        this.fga = fga;
        this.valorPremio = valorPremio;
        this.numDias = numDias;
    }

    public int getQuantidadeDia() {
        return quantidadeDia;
    }

    public String getValorPremio() {
        return valorPremio;
    }

    public String getFranquia() {
        return franquia;
    }

    public void setFranquia(String franquia) {
        this.franquia = franquia;
    }

    public String getValorNC() {
        return valorNC;
    }

    public String getValorImpostoCincoPorCento() {
        return valorImpostoCincoPorCento;
    }

    public String getTotalSegurado() {
        return totalSegurado;
    }

    public int getNumDias() {
        return numDias;
    }

    public void setNumDias(int numDias) {
        this.numDias = numDias;
    }

    public void setTotalSegurado(String totalSegurado) {
        this.totalSegurado = totalSegurado;
    }

    public String getValorImpostoSeisPorCento() {
        return valorImpostoSeisPorCento;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public String getFga() {
        return fga;
    }

    public void setQuantidadeDia(int quantidadeDia) {
        this.quantidadeDia = quantidadeDia;
    }

    public void setValorPremio(String valorPremio) {
        this.valorPremio = valorPremio;
    }

    public void setValorNC(String valorNC) {
        this.valorNC = valorNC;
    }

    public void setValorImpostoCincoPorCento(String valorImpostoCincoPorCento) {
        this.valorImpostoCincoPorCento = valorImpostoCincoPorCento;
    }

    public void setValorImpostoSeisPorCento(String valorImpostoSeisPorCento) {
        this.valorImpostoSeisPorCento = valorImpostoSeisPorCento;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public void setFga(String fga) {
        this.fga = fga;
    }

    public String getCodigoSeguro() {
        return codigoSeguro;
    }

    public void setCodigoSeguro(String codigoSeguro) {
        this.codigoSeguro = codigoSeguro;
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

    public Date getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(Date dataContrato) {
        this.dataContrato = dataContrato;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    @Override
    public String toString() {
        return "ContratoClausulas{" + "quantidadeDia=" + quantidadeDia + ", valorPremio=" + valorPremio + ", valorNC=" + valorNC + ", valorImpostoCincoPorCento=" + valorImpostoCincoPorCento + ", valorImpostoSeisPorCento=" + valorImpostoSeisPorCento + ", premioBruto=" + premioBruto + ", premioLiquido=" + premioLiquido + ", fga=" + fga + ", totalSegurado=" + totalSegurado + ", codigoSeguro=" + codigoSeguro + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", dataContrato=" + dataContrato + ", dataRenovacao=" + dataRenovacao + ", observacao=" + observacao + ", primeiroPremio=" + primeiroPremio + ", menos=" + menos + ", liquidoPagar=" + liquidoPagar + ", premioAnual=" + premioAnual + ", taxaAdicionar=" + taxaAdicionar + ", excesso=" + excesso + ", franquia=" + franquia + '}';
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getPrimeiroPremio() {
        return primeiroPremio;
    }

    public void setPrimeiroPremio(String primeiroPremio) {
        this.primeiroPremio = primeiroPremio;
    }

    public String getMenos() {
        return menos;
    }

    public void setMenos(String menos) {
        this.menos = menos;
    }

    public String getLiquidoPagar() {
        return liquidoPagar;
    }

    public void setLiquidoPagar(String liquidoPagar) {
        this.liquidoPagar = liquidoPagar;
    }

    public String getPremioAnual() {
        return premioAnual;
    }

    public void setPremioAnual(String premioAnual) {
        this.premioAnual = premioAnual;
    }

    public String getTaxaAdicionar() {
        return taxaAdicionar;
    }

    public void setTaxaAdicionar(String taxaAdicionar) {
        this.taxaAdicionar = taxaAdicionar;
    }

    public String getExcesso() {
        return excesso;
    }

    public void setExcesso(String excesso) {
        this.excesso = excesso;
    }
    
    
    


 
            
            
}
