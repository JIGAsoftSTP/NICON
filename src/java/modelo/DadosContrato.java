/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helcio Guadalupe
 */
public class DadosContrato implements Serializable
{
    private String id;
    private String idSeguro;
    private String apolice;
    private String seguro;
    private String cliente;
    private String dataInicio;
    private String dataFim;
    private String dataContrato;
    private String limiteResponsabilidade;
    private String premioBruto;
    private String premioLiquido;
    private String data;
    private String estado;
    private String notaDebito;
    private int estadoPagamento;

    public String getIdCliente() {
        return idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotaDebito() {
        return notaDebito;
    }

    public int getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(int estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public void setNotaDebito(String notaDebito) {
        this.notaDebito = notaDebito;
    }

    public String getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(String idSeguro) {
        this.idSeguro = idSeguro;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    private String idCliente;

    public String getData() {
        return data;
    }

    
    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApolice() {
        return apolice;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(String dataContrato) {
        this.dataContrato = dataContrato;
    }

    public String getLimiteResponsabilidade() {
        return limiteResponsabilidade;
    }

    public void setLimiteResponsabilidade(String limiteResponsabilidade) {
        this.limiteResponsabilidade = limiteResponsabilidade;
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
    
    
    
}
