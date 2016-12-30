
package modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Helio
 */
public class Incendio implements Serializable 
{
    private String numeroApolice;
    private String numeroRegistro;
    private String condicao;
    private String fonte;
    private String ano;
    private String andar;
    private String distancia;
    private String equipamento;
    private String endereco;
    private String processoFabricacao;
    private String usoResidencial="33";
    private String usoEdificioDescricao;
    private String totalSegurado;
    private String totalSeguradoFormatado;
    private String premioBruto;
    private String premioBrutoFormatado;
    private String premioLiquido;
    private String premioLiquidoFormatado;
    private String costumeAtualizarLivro;
    private String costumeInventario;
    private String costumeGuardarRegistro;
    private String moeda;
    private String sigla;
    private String cover;
    
//    Baro		Mármore		Azuleijo		Cerramica		Betão	
    private boolean pavimentoBaro;
    private boolean pavimentoMarmore;
    private boolean pavimentoAzuleijo;
    private boolean pavimentoCerramica;
    private boolean pavimentoBetao;
    private ArrayList<Object> pavimento;
    
    //telha		chapa Zinco		chapa fibrocimento		Placa betão
    private boolean tectoTelha;
    private boolean tectoChapaZinco;
    private boolean tectoChapaFibrocimento;
    private boolean tectoPlacabetao;
    private ArrayList<Object> tecto;
    
    //Tijolo		Betão
    private boolean paredeTijolo;
    private boolean paredeBetao;
    private ArrayList<Object> parede;
    
    public Incendio()
    {
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

    public String getTotalSegurado() {
        return totalSegurado;
    }

    public void setTotalSegurado(String totalSegurado) {
        this.totalSegurado = totalSegurado;
    }

    public String getTotalSeguradoFormatado() {
        return totalSeguradoFormatado;
    }

    public void setTotalSeguradoFormatado(String totalSeguradoFormatado) {
        this.totalSeguradoFormatado = totalSeguradoFormatado;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public String getPremioBrutoFormatado() {
        return premioBrutoFormatado;
    }

    public void setPremioBrutoFormatado(String premioBrutoFormatado) {
        this.premioBrutoFormatado = premioBrutoFormatado;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public String getPremioLiquidoFormatado() {
        return premioLiquidoFormatado;
    }

    public void setPremioLiquidoFormatado(String premioLiquidoFormatado) {
        this.premioLiquidoFormatado = premioLiquidoFormatado;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProcessoFabricacao() {
        return processoFabricacao;
    }

    public void setProcessoFabricacao(String processoFabricacao) {
        this.processoFabricacao = processoFabricacao;
    }

    public String getUsoResidencial() {
        return usoResidencial;
    }

    public void setUsoResidencial(String usoResidencial) {
        this.usoResidencial = usoResidencial;
    }

    public String getUsoEdificioDescricao() {
        return usoEdificioDescricao;
    }

    public void setUsoEdificioDescricao(String usoEdificioDescricao) {
        this.usoEdificioDescricao = usoEdificioDescricao;
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

    @Override
    public String toString() {
        return "Incendio{" + "numeroApolice=" + numeroApolice + ", numeroRegistro=" + numeroRegistro + ", condicao=" + condicao + ", fonte=" + fonte + ", ano=" + ano + ", andar=" + andar + ", distancia=" + distancia + ", equipamento=" + equipamento + ", endereco=" + endereco + ", processoFabricacao=" + processoFabricacao + ", usoResidencial=" + usoResidencial + ", usoEdificioDescricao=" + usoEdificioDescricao + ", totalSegurado=" + totalSegurado + ", totalSeguradoFormatado=" + totalSeguradoFormatado + ", premioBruto=" + premioBruto + ", premioBrutoFormatado=" + premioBrutoFormatado + ", premioLiquido=" + premioLiquido + ", premioLiquidoFormatado=" + premioLiquidoFormatado + ", costumeAtualizarLivro=" + costumeAtualizarLivro + ", costumeInventario=" + costumeInventario + ", costumeGuardarRegistro=" + costumeGuardarRegistro + ", moeda=" + moeda + ", sigla=" + sigla + ", pavimentoBaro=" + pavimentoBaro + ", pavimentoMarmore=" + pavimentoMarmore + ", pavimentoAzuleijo=" + pavimentoAzuleijo + ", pavimentoCerramica=" + pavimentoCerramica + ", pavimentoBetao=" + pavimentoBetao + ", pavimento=" + pavimento + ", tectoTelha=" + tectoTelha + ", tectoChapaZinco=" + tectoChapaZinco + ", tectoChapaFibrocimento=" + tectoChapaFibrocimento + ", tectoPlacabetao=" + tectoPlacabetao + ", tecto=" + tecto + ", paredeTijolo=" + paredeTijolo + ", paredeBetao=" + paredeBetao + ", parede=" + parede + '}';
    }

    public String getCostumeAtualizarLivro() {
        return costumeAtualizarLivro;
    }

    public void setCostumeAtualizarLivro(String costumeAtualizarLivro) {
        this.costumeAtualizarLivro = costumeAtualizarLivro;
    }

    public String getCostumeInventario() {
        return costumeInventario;
    }

    public void setCostumeInventario(String costumeInventario) {
        this.costumeInventario = costumeInventario;
    }

    public String getCostumeGuardarRegistro() {
        return costumeGuardarRegistro;
    }

    public void setCostumeGuardarRegistro(String costumeGuardarRegistro) {
        this.costumeGuardarRegistro = costumeGuardarRegistro;
    }

    public boolean isPavimentoBaro() {
        return pavimentoBaro;
    }

    public void setPavimentoBaro(boolean pavimentoBaro) {
        this.pavimentoBaro = pavimentoBaro;
    }

    public boolean isPavimentoMarmore() {
        return pavimentoMarmore;
    }

    public void setPavimentoMarmore(boolean pavimentoMarmore) {
        this.pavimentoMarmore = pavimentoMarmore;
    }

    public boolean isPavimentoAzuleijo() {
        return pavimentoAzuleijo;
    }

    public void setPavimentoAzuleijo(boolean pavimentoAzuleijo) {
        this.pavimentoAzuleijo = pavimentoAzuleijo;
    }

    public boolean isPavimentoCerramica() {
        return pavimentoCerramica;
    }

    public void setPavimentoCerramica(boolean pavimentoCerramica) {
        this.pavimentoCerramica = pavimentoCerramica;
    }

    public boolean isPavimentoBetao() {
        return pavimentoBetao;
    }

    public void setPavimentoBetao(boolean pavimentoBetao) {
        this.pavimentoBetao = pavimentoBetao;
    }

    public ArrayList<Object> getPavimento() {
        return pavimento = ((pavimento==null)? pavimento=new ArrayList<>():pavimento);
    }

    public void setPavimento(ArrayList<Object> pavimento) {
        this.pavimento = pavimento;
    }

    public boolean isTectoTelha() {
        return tectoTelha;
    }

    public void setTectoTelha(boolean tectoTelha) {
        this.tectoTelha = tectoTelha;
    }

    public boolean isTectoChapaZinco() {
        return tectoChapaZinco;
    }

    public void setTectoChapaZinco(boolean tectoChapaZinco) {
        this.tectoChapaZinco = tectoChapaZinco;
    }

    public boolean isTectoChapaFibrocimento() {
        return tectoChapaFibrocimento;
    }

    public void setTectoChapaFibrocimento(boolean tectoChapaFibrocimento) {
        this.tectoChapaFibrocimento = tectoChapaFibrocimento;
    }

    public boolean isTectoPlacabetao() {
        return tectoPlacabetao;
    }

    public void setTectoPlacabetao(boolean tectoPlacabetao) {
        this.tectoPlacabetao = tectoPlacabetao;
    }

    public ArrayList<Object> getTecto() {
        return tecto = ((tecto==null)? tecto=new ArrayList<>():tecto);
    }

    public void setTecto(ArrayList<Object> tecto) {
        this.tecto = tecto;
    }

    public boolean isParedeTijolo() {
        return paredeTijolo;
    }

    public void setParedeTijolo(boolean paredeTijolo) {
        this.paredeTijolo = paredeTijolo;
    }

    public boolean isParedeBetao() {
        return paredeBetao;
    }

    public void setParedeBetao(boolean paredeBetao) {
        this.paredeBetao = paredeBetao;
    }

    public ArrayList<Object> getParede() {
        return parede = ((parede==null)?parede=new ArrayList<>():parede);
    }

    public void setParede(ArrayList<Object> parede) {
        this.parede = parede;
    }

}
