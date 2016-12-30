/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author AhmedJorge
 */
public class Empresa implements Serializable
{
    private String codigoNicon;
    private String categoria;
    private Date dataEntrada;
    private String banco;
    private String numContaBancario;
    private String departamento;

    public Empresa() {
    }

    public String getCodigoNicon() {
        return codigoNicon;
    }

    public void setCodigoNicon(String codigoNicon) {
        this.codigoNicon = codigoNicon;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getNumContaBancario() {
        return numContaBancario;
    }

    public void setNumContaBancario(String numContaBancario) {
        this.numContaBancario = numContaBancario;
    }

    @Override
    public String toString() {
        return "Empresa{" + "codigoNicon=" + codigoNicon + ", categoria=" + categoria + ", dataEntrada=" + dataEntrada + ", banco=" + banco + ", numContaBancario=" + numContaBancario + '}';
    }
    
}
