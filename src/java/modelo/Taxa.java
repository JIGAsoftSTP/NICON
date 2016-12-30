/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author AhmedJorge
 */
public class Taxa implements Serializable
{
    private String estado;
    private String moeda1;
    private String moeda2;
    private Float compraValue;
    private String compraValueView;
    private Float vendaValue;
    private String vendaValueView;
    private String categoria;
    private String valorTotal;
    private String valorLiqudo;
    private String fga;
    private String consumo;
    private String selo;
    private String idImposto;
    private String percentagem;
    private String valorMinimo;
    private String valorMaximo;
    private String parcelaBater;
    private String nomeImposto;
    private String taxa;
    private String nome;
    private String irs;
    private String segSocial;
    

    public Taxa() {
    }
    
    public Taxa(Taxa t) {
        this.estado = t.estado;
        this.moeda1 = t.moeda1;
        this.moeda2 = t.moeda2;
        this.compraValue = t.compraValue;
        this.compraValueView = t.compraValueView;
        this.vendaValue = t.vendaValue;
        this.vendaValueView = t.vendaValueView;
        this.nomeImposto = t.nomeImposto;
        this.percentagem = t.percentagem;
        this.idImposto = t.idImposto;
        this.nome = t.getNome();
        this.valorMinimo = t.getValorMinimo();
        this.valorMaximo = t.getValorMaximo();
        this.parcelaBater = t.getParcelaBater();
    }
   
    public String getIrs() {
        return irs;
    }

    public void setIrs(String irs) {
        this.irs = irs;
    }

    public String getSegSocial() {
        return segSocial;
    }

    public void setSegSocial(String segSocial) {
        this.segSocial = segSocial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getCompraValue() {
        return compraValue;
    }

    public void setCompraValue(Float compraValue) {
        this.compraValue = compraValue;
    }

    public Float getVendaValue() {
        return vendaValue;
    }

    public String getIdImposto() {
        return idImposto;
    }

    public String getNomeImposto() {
        return nomeImposto;
    }

    public void setNomeImposto(String nomeImposto) {
        this.nomeImposto = nomeImposto;
    }

    public void setIdImposto(String idImposto) {
        this.idImposto = idImposto;
    }

    public String getPercentagem() {
        return percentagem;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    
    public void setPercentagem(String percentagem) {
        this.percentagem = percentagem;
    }

    public String getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public String getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getParcelaBater() {
        return parcelaBater;
    }

    public void setParcelaBater(String parcelaBater) {
        this.parcelaBater = parcelaBater;
    }

    
    public void setVendaValue(Float vendaValue) {
        this.vendaValue = vendaValue;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValorLiqudo() {
        return valorLiqudo;
    }

    public void setValorLiqudo(String valorLiqudo) {
        this.valorLiqudo = valorLiqudo;
    }

    public String getFga() {
        return fga;
    }

    public void setFga(String fga) {
        this.fga = fga;
    }

    public String getConsumo() {
        return consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }

    public String getSelo() {
        return selo;
    }

    public void setSelo(String selo) {
        this.selo = selo;
    }

    public String getCompraValueView() {
        return compraValueView;
    }

    public void setCompraValueView(String compraValueView) {
        this.compraValueView = compraValueView;
    }

    public String getVendaValueView() {
        return vendaValueView;
    }

    public void setVendaValueView(String vendaValueView) {
        this.vendaValueView = vendaValueView;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMoeda1() {
        return moeda1;
    }

    public void setMoeda1(String moeda1) {
        this.moeda1 = moeda1;
    }

    public String getMoeda2() {
        return moeda2;
    }

    public void setMoeda2(String moeda2) {
        this.moeda2 = moeda2;
    }

    @Override
    public String toString() {
        return "Taxa{" + "estado=" + estado + ", moeda1=" + moeda1 + ", moeda2=" + moeda2 + ", compraValue=" + compraValue + ", compraValueView=" + compraValueView + ", vendaValue=" + vendaValue + ", vendaValueView=" + vendaValueView + ", categoria=" + categoria + ", valorTotal=" + valorTotal + ", valorLiqudo=" + valorLiqudo + ", fga=" + fga + ", consumo=" + consumo + ", selo=" + selo + ", idImposto=" + idImposto + ", percentagem=" + percentagem + ", valorMinimo=" + valorMinimo + ", valorMaximo=" + valorMaximo + ", parcelaBater=" + parcelaBater + ", nomeImposto=" + nomeImposto + ", taxa=" + taxa + ", nome=" + nome + ", irs=" + irs + ", segSocial=" + segSocial + '}';
    }
    
    
}
