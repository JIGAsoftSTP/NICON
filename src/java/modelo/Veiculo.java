
package modelo;

import java.io.Serializable;

/**
 *
 * @author ildo
 */
public class Veiculo implements Serializable
{
    private String numeroApolice;
    private String numeroRegistro;
    private String numeroMatricula;
    private String modelo;
    private String marca;
    private String numMotor;
    private String chassi;
    private String anoFabrico;
    private String anoCompra;
    private String capacidade;
    private String valorCompra;
    private String valorAtual;
    private String limiteResp = "Ilimitado";
    private String totalSeguradoFormatado;
    private String valorPremio;
    private String premioBruto;
    private String premioBrutoMoeda;
    private String moeda;
    private String sigla;
    private String premioLiquido;
    private String premioLiquidoMoeda;
    private String valorNC;
    private String tipoCobertura = "41";
    private String certificado;
   
    public Veiculo()
    {
        
    }

    public Veiculo(String numeroMatricula, String modelo, String marca, String numMotor, String chassi, String anoFabrico, String anoCompra, String capacidade, String valorAtual, String valorPremio,String limite,String valorCompra,String certificado) {
        this.numeroMatricula = numeroMatricula;
        this.modelo = modelo;
        this.marca = marca;
        this.numMotor = numMotor;
        this.chassi = chassi;
        this.anoFabrico = anoFabrico;
        this.anoCompra = anoCompra;
        this.capacidade = capacidade;
        this.valorAtual = valorAtual;
        this.valorPremio = valorPremio;
        this.limiteResp = limite;
        this.valorCompra = valorCompra;
        this.certificado = certificado;
    }

    public void  limpar(String numeroMatricula, String modelo, String marca, String numMotor, String chassi, String anoFabrico, String anoCompra, String capacidade, String valorCompra, String valorAtual, String valorPremio) {
        this.numeroMatricula = numeroMatricula;
        this.modelo = modelo;
        this.marca = marca;
        this.numMotor = numMotor;
        this.chassi = chassi;
        this.anoFabrico = anoFabrico;
        this.anoCompra = anoCompra;
        this.capacidade = capacidade;
        this.valorCompra = valorCompra;
        this.valorAtual = valorAtual;
        this.valorPremio = valorPremio;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    
    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumMotor() {
        return numMotor;
    }

    public void setNumMotor(String numMotor) {
        this.numMotor = numMotor;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getAnoFabrico() {
        return anoFabrico;
    }

    public void setAnoFabrico(String anoFabrico) {
        this.anoFabrico = anoFabrico;
    }

    public String getAnoCompra() {
        return anoCompra;
    }

    public void setAnoCompra(String anoCompra) {
        this.anoCompra = anoCompra;
    }

    public String getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(String capacidade) {
        this.capacidade = capacidade;
    }

    public String getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(String valorCompra) {
        this.valorCompra = valorCompra;
    }

    public String getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(String valorAtual) {
        this.valorAtual = valorAtual;
    }

    public String getLimiteResp() {
        return limiteResp;
    }

    public void setLimiteResp(String limiteResp) {
        this.limiteResp = limiteResp;
    }

    public String getTotalSeguradoFormatado() {
        return totalSeguradoFormatado;
    }

    public void setTotalSeguradoFormatado(String totalSeguradoFormatado) {
        this.totalSeguradoFormatado = totalSeguradoFormatado;
    }

    public String getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(String valorPremio) {
        this.valorPremio = valorPremio;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public String getPremioBrutoMoeda() {
        return premioBrutoMoeda;
    }

    public void setPremioBrutoMoeda(String premioBrutoMoeda) {
        this.premioBrutoMoeda = premioBrutoMoeda;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public String getPremioLiquidoMoeda() {
        return premioLiquidoMoeda;
    }

    public void setPremioLiquidoMoeda(String premioLiquidoMoeda) {
        this.premioLiquidoMoeda = premioLiquidoMoeda;
    }

    public String getValorNC() {
        return valorNC;
    }

    public void setValorNC(String valorNC) {
        this.valorNC = valorNC;
    }

    @Override
    public String toString() {
        return "Veiculo{" + "numeroApolice=" + numeroApolice + ", numeroRegistro=" + numeroRegistro + ", numeroMatricula=" + numeroMatricula + ", modelo=" + modelo + ", marca=" + marca + ", numMotor=" + numMotor + ", chassi=" + chassi + ", anoFabrico=" + anoFabrico + ", anoCompra=" + anoCompra + ", capacidade=" + capacidade + ", valorCompra=" + valorCompra + ", valorAtual=" + valorAtual + ", limiteResp=" + limiteResp + ", totalSeguradoFormatado=" + totalSeguradoFormatado + ", valorPremio=" + valorPremio + ", premioBruto=" + premioBruto + ", premioBrutoMoeda=" + premioBrutoMoeda + ", moeda=" + moeda + ", sigla=" + sigla + ", premioLiquido=" + premioLiquido + ", premioLiquidoMoeda=" + premioLiquidoMoeda + ", valorNC=" + valorNC + ", tipoCobertura=" + tipoCobertura + '}';
    }

   
    
    
    
}
