
package modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Helio
 */
public class Roubo
{
    private String numeroApolice;
    private String numeroRegistro;
    private String quantidade;
    private String modelo;
    private String numeroRegistroAlternativo;
    private String descricao;
    private String valor;
    private String tipoEdificio = "63;";
    private String tipoEdificioEspecifique;
    private String enderecoEdificio;
    private String tempoOcupacao;
    private String datatInspecao;
    private String tipoAlarme;
    private String bens;
    private String d;
    private String sigla;
    private String mediasTomadas;
    private String valorCofre;
    private String marcaCofre;
    private Date dataAquisicao;
    private String limiteRsp;
    private String limiteRspFormatado;
    private String moeda;
    private Date minDate;
    private Date maxDate;
    private String premioBruto;
    private String premioLiquido;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
     
    public Roubo()
    {
        
    }

    public Roubo(String quantidade, String modelo, String descricao, String valor) {
        this.quantidade = quantidade;
        this.modelo = modelo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }
    
    
    public String getQuantidade() {
        return quantidade;
    }

    public String getNumeroRegistroAlternativo() {
        return numeroRegistroAlternativo;
    }

    public void setNumeroRegistroAlternativo(String numeroRegistroAlternativo) {
        this.numeroRegistroAlternativo = numeroRegistroAlternativo;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Date getMinDate() throws ParseException {
        String[] dd = sdf.format(new Date()).split("-");
        return minDate =(sdf.parse("01-01-"+(Integer.valueOf(dd[2])-190)));
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
          return maxDate = (new Date());
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
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

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoEdificio() {
        return tipoEdificio;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setTipoEdificio(String tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    public String getTipoEdificioEspecifique() {
        return tipoEdificioEspecifique;
    }

    public void setTipoEdificioEspecifique(String tipoEdificioEspecifique) {
        this.tipoEdificioEspecifique = tipoEdificioEspecifique;
    }

    public String getEnderecoEdificio() {
        return enderecoEdificio;
    }

    public void setEnderecoEdificio(String enderecoEdificio) {
        this.enderecoEdificio = enderecoEdificio;
    }

    public String getTempoOcupacao() {
        return tempoOcupacao;
    }

    public void setTempoOcupacao(String tempoOcupacao) {
        this.tempoOcupacao = tempoOcupacao;
    }

    public String getDatatInspecao() {
        return datatInspecao;
    }

    public void setDatatInspecao(String datatInspecao) {
        this.datatInspecao = datatInspecao;
    }


    public String getLimiteRsp() {
        return limiteRsp;
    }

    public void setLimiteRsp(String limiteRsp) {
        this.limiteRsp = limiteRsp;
    }

    public String getLimiteRspFormatado() {
        return limiteRspFormatado;
    }

    public void setLimiteRspFormatado(String limiteRspFormatado) {
        this.limiteRspFormatado = limiteRspFormatado;
    }


    public String getTipoAlarme() {
        return tipoAlarme;
    }

    public void setTipoAlarme(String tipoAlarme) {
        this.tipoAlarme = tipoAlarme;
    }

    public String getBens() {
        return bens;
    }

    public void setBens(String bens) {
        this.bens = bens;
    }

    public String getMediasTomadas() {
        return mediasTomadas;
    }

    public void setMediasTomadas(String mediasTomadas) {
        this.mediasTomadas = mediasTomadas;
    }

    public String getValorCofre() {
        return valorCofre;
    }

    public void setValorCofre(String valorCofre) {
        this.valorCofre = valorCofre;
    }

    public String getMarcaCofre() {
        return marcaCofre;
    }

    public void setMarcaCofre(String marcaCofre) {
        this.marcaCofre = marcaCofre;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    @Override
    public String toString() {
        return "Roubo{" + "numeroApolice=" + numeroApolice + ", numeroRegistro=" + numeroRegistro + ", quantidade=" + quantidade + ", modelo=" + modelo + ", numeroRegistroAlternativo=" + numeroRegistroAlternativo + ", descricao=" + descricao + ", valor=" + valor + ", tipoEdificio=" + tipoEdificio + ", tipoEdificioEspecifique=" + tipoEdificioEspecifique + ", enderecoEdificio=" + enderecoEdificio + ", tempoOcupacao=" + tempoOcupacao + ", datatInspecao=" + datatInspecao + ", tipoAlarme=" + tipoAlarme + ", bens=" + bens + ", d=" + d + ", sigla=" + sigla + ", mediasTomadas=" + mediasTomadas + ", valorCofre=" + valorCofre + ", marcaCofre=" + marcaCofre + ", dataAquisicao=" + dataAquisicao + ", limiteRsp=" + limiteRsp + ", limiteRspFormatado=" + limiteRspFormatado + ", moeda=" + moeda + ", minDate=" + minDate + ", maxDate=" + maxDate + ", sdf=" + sdf + '}';
    }

 

  

  
    
    
}
