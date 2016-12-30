
package modelo;

/**
 *
 * @author Helio
 */
public class Maritimo 
{
    private String bandeiraNavio;
    private String usoNavio;
    private String classeRenovacao;
    private String moeda;
    private String sigla;
    private String potenciaMotor;
    private String tipoCombustivel;
    private String peso;
    private String numMotor;
    private String marcaMotor;
    private String numMaximoTripulante;
    private String nomeNavio;
    private String marcaModelo;
    private String numeroChassi;
    private String cover;
    private String idadeNavio;
    private String tipoNavio;
    private String areaOperacao;
    private String especificacaoApoioNavegacao;
    private String tipoConstrucao;
    private String condicaoAtual;
    private String numeroApolice;
    private String apoioNavegacao = "N";
    private String numeroRegistro;
    private String estruturaRenovacao;
    private String experienciaRecalmacao = "N";
    private String premioBruto;
    private String premioBrutoMoeda;
    private String premioLiquido;
    private String premioLiquidoMoeda;
    private String totalSegurado;
    private String totalSeguradoMoeda;
    private String nc;
    private String valorPremio;
    private String impostoCincoPorCento ="0.05";
    private String impostoSeisPorCento = "0.006";
    private String impostoFga;
    
    public Maritimo()
    {
        
    }
    public Maritimo(String bandeiraNavio, String usoNavio, String classeRenovacao, String potenciaMotor, String tipoCombustivel, String peso, String numMotor, String marcaMotor, String numMaximoTripulante, String nomeNavio, String marcaModelo, String numeroChassi, String idadeNavio, String tipoNavio, String areaOperacao, String especificacaoApoioNavegacao, String tipoConstrucao, String condicaoAtual, String apoioNavegacao, String numeroRegistro, String estruturaRenovacao, String experienciaRecalmacao) {
        this.bandeiraNavio = bandeiraNavio;
        this.usoNavio = usoNavio;
        this.classeRenovacao = classeRenovacao;
        this.potenciaMotor = potenciaMotor;
        this.tipoCombustivel = tipoCombustivel;
        this.peso = peso;
        this.numMotor = numMotor;
        this.marcaMotor = marcaMotor;
        this.numMaximoTripulante = numMaximoTripulante;
        this.nomeNavio = nomeNavio;
        this.marcaModelo = marcaModelo;
        this.numeroChassi = numeroChassi;
        this.idadeNavio = idadeNavio;
        this.tipoNavio = tipoNavio;
        this.areaOperacao = areaOperacao;
        this.especificacaoApoioNavegacao = especificacaoApoioNavegacao;
        this.tipoConstrucao = tipoConstrucao;
        this.condicaoAtual = condicaoAtual;
        this.apoioNavegacao = apoioNavegacao;
        this.numeroRegistro = numeroRegistro;
        this.estruturaRenovacao = estruturaRenovacao;
        this.experienciaRecalmacao = experienciaRecalmacao;
    }
    public String getEstruturaRenovacao() 
    {
        return estruturaRenovacao;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getImpostoCincoPorCento() {
        return impostoCincoPorCento;
    }

    public void setImpostoCincoPorCento(String impostoCincoPorCento) {
        this.impostoCincoPorCento = impostoCincoPorCento;
    }

    public String getImpostoSeisPorCento() {
        return impostoSeisPorCento;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public void setImpostoSeisPorCento(String impostoSeisPorCento) {
        this.impostoSeisPorCento = impostoSeisPorCento;
    }

    public String getImpostoFga() {
        return impostoFga;
    }

    public void setImpostoFga(String impostoFga) {
        this.impostoFga = impostoFga;
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

    public String getTotalSegurado() {
        return totalSegurado;
    }

    public void setTotalSegurado(String totalSegurado) {
        this.totalSegurado = totalSegurado;
    }

    public String getTotalSeguradoMoeda() {
        return totalSeguradoMoeda;
    }

    public void setTotalSeguradoMoeda(String totalSeguradoMoeda) {
        this.totalSeguradoMoeda = totalSeguradoMoeda;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(String valorPremio) {
        this.valorPremio = valorPremio;
    }

    public String getExperienciaRecalmacao() {
        return experienciaRecalmacao;
    }

    public void setExperienciaRecalmacao(String experienciaRecalmacao) {
        this.experienciaRecalmacao = experienciaRecalmacao;
    }

    public void setEstruturaRenovacao(String estruturaRenovacao) {
        this.estruturaRenovacao = estruturaRenovacao;
    }

    public String getApoioNavegacao() {
        return apoioNavegacao;
    }

    public void setApoioNavegacao(String apoioNavegacao) {
        this.apoioNavegacao = apoioNavegacao;
//        if(this.apoioNavegacao.equals("N"))
//        {
//            especificacaoApoioNavegacao = null;
//        }
    }
    
   
    public String getBandeiraNavio() 
    {
        return bandeiraNavio;
    }

    public void setBandeiraNavio(String bandeiraNavio) {
        this.bandeiraNavio = bandeiraNavio;
    }

    public String getUsoNavio() {
        return usoNavio;
    }

    public void setUsoNavio(String usoNavio) {
        this.usoNavio = usoNavio;
    }

    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getClasseRenovacao() {
        return classeRenovacao;
    }

    public void setClasseRenovacao(String classeRenovacao) {
        this.classeRenovacao = classeRenovacao;
    }

    public String getPotenciaMotor() {
        return potenciaMotor;
    }

    public void setPotenciaMotor(String potenciaMotor) {
        this.potenciaMotor = potenciaMotor;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getNumMotor() {
        return numMotor;
    }

    public void setNumMotor(String numMotor) {
        this.numMotor = numMotor;
    }

    public String getMarcaMotor() {
        return marcaMotor;
    }

    public void setMarcaMotor(String marcaMotor) {
        this.marcaMotor = marcaMotor;
    }

    public String getNumMaximoTripulante() {
        return numMaximoTripulante;
    }

    public void setNumMaximoTripulante(String numMaximoTripulante) {
        this.numMaximoTripulante = numMaximoTripulante;
    }

    public String getNomeNavio() {
        return nomeNavio;
    }

    public void setNomeNavio(String nomeNavio) {
        this.nomeNavio = nomeNavio;
    }

    public String getMarcaModelo() {
        return marcaModelo;
    }

    public void setMarcaModelo(String marcaModelo) {
        this.marcaModelo = marcaModelo;
    }

    public String getNumeroChassi() {
        return numeroChassi;
    }

    public void setNumeroChassi(String numeroChassi) {
        this.numeroChassi = numeroChassi;
    }

    public String getIdadeNavio() {
        return idadeNavio;
    }

    public void setIdadeNavio(String idadeNavio) {
        this.idadeNavio = idadeNavio;
    }

    public String getTipoNavio() {
        return tipoNavio;
    }

    public void setTipoNavio(String tipoNavio) {
        this.tipoNavio = tipoNavio;
    }

    public String getAreaOperacao() {
        return areaOperacao;
    }

    public void setAreaOperacao(String areaOperacao) {
        this.areaOperacao = areaOperacao;
    }

    public String getEspecificacaoApoioNavegacao() {
        return especificacaoApoioNavegacao;
    }

    public void setEspecificacaoApoioNavegacao(String especificacaoApoioNavegacao) {
        this.especificacaoApoioNavegacao = especificacaoApoioNavegacao;
    }

    public String getTipoConstrucao() {
        return tipoConstrucao;
    }

    public void setTipoConstrucao(String tipoConstrucao) {
        this.tipoConstrucao = tipoConstrucao;
    }

    public String getCondicaoAtual() {
        return condicaoAtual;
    }

    public void setCondicaoAtual(String condicaoAtual) {
        this.condicaoAtual = condicaoAtual;
    }

    public String toString()
    {
        return "Maritimo{" + "bandeiraNavio=" + bandeiraNavio + ", usoNavio=" + usoNavio + ", classeRenovacao=" + classeRenovacao + ", potenciaMotor=" + potenciaMotor + ", tipoCombustivel=" + tipoCombustivel + ", peso=" + peso + ", numMotor=" + numMotor + ", marcaMotor=" + marcaMotor + ", numMaximoTripulante=" + numMaximoTripulante + ", nomeNavio=" + nomeNavio + ", marcaModelo=" + marcaModelo + ", numeroChassi=" + numeroChassi + ", idadeNavio=" + idadeNavio + ", tipoNavio=" + tipoNavio + ", areaOperacao=" + areaOperacao + ", especificacaoApoioNavegacao=" + especificacaoApoioNavegacao + ", tipoConstrucao=" + tipoConstrucao + ", condicaoAtual=" + condicaoAtual + ", numeroApolice=" + numeroApolice + ", apoioNavegacao=" + apoioNavegacao + ", numeroRegistro=" + numeroRegistro + ", estruturaRenovacao=" + estruturaRenovacao + ", experienciaRecalmacao=" + experienciaRecalmacao + '}';
    }
    
    
    
}
