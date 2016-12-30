/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helio
 */
public class MultiRisco implements Serializable
{

    private String numeroApolice;
    private String numeroRegistro;
    private String sigla;
    private String moeda;
    private String limiteResponsabilidade;
    private String limiteResponsabilidadeFormatado;
    private String premioBruto;
    private String premioLiquido;


    public String getLimiteResponsabilidade() {
        return limiteResponsabilidade;
    }

    public void setLimiteResponsabilidade(String limiteResponsabilidade) {
        this.limiteResponsabilidade = limiteResponsabilidade;
    }

    public String getLimiteResponsabilidadeFormatado() {
        return limiteResponsabilidadeFormatado;
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

    public void setLimiteResponsabilidadeFormatado(String limiteResponsabilidadeFormatado) {
        this.limiteResponsabilidadeFormatado = limiteResponsabilidadeFormatado;
    }

    @Override
    public String toString() {
        return "MultiRisco{" + "numeroApolice=" + numeroApolice + ", numeroRegistro=" + numeroRegistro + ", sigla=" + sigla + ", moeda=" + moeda + ", limiteResponsabilidade=" + limiteResponsabilidade + ", limiteResponsabilidadeFormatado=" + limiteResponsabilidadeFormatado + ", premioBruto=" + premioBruto + ", premioLiquido=" + premioLiquido + '}';
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
    
    
}
