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
public class DataContrato implements Serializable
{
    private String id;	
    private String idCliente;
    private String apolice;
    private String data;
    private String seguro;
    private String cliente;
    private String premioBruto;
    private String dataInicio;
    private String dataFim;
    private String dataContrato;
    private String premioLiquido;	
    private String limiteResponsabilidade;
    private String premioLiquidoSF;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getApolice() {
        return apolice;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
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

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public String getLimiteResponsabilidade() {
        return limiteResponsabilidade;
    }

    public void setLimiteResponsabilidade(String limiteResponsabilidade) {
        this.limiteResponsabilidade = limiteResponsabilidade;
    }

    public String getPremioLiquidoSF() {
        return premioLiquidoSF;
    }

    public void setPremioLiquidoSF(String premioLiquidoSF) {
        this.premioLiquidoSF = premioLiquidoSF;
    }

    public DataContrato(DataContrato dc) {
        this.id = dc.id;
        this.idCliente = dc.idCliente;
        this.apolice = dc.apolice;
        this.data = dc.data;
        this.seguro = dc.seguro;
        this.cliente = dc.cliente;
        this.premioBruto = dc.premioBruto;
        this.dataInicio = dc.dataInicio;
        this.dataFim = dc.dataFim;
        this.dataContrato = dc.dataContrato;
        this.premioLiquido = dc.premioLiquido;
        this.limiteResponsabilidade = dc.limiteResponsabilidade;
        this.premioLiquidoSF = dc.premioLiquidoSF;
    }

    public DataContrato() {
    }

    @Override
    public String toString() {
        return "DataContrato{" + "id=" + id + ", idCliente=" + idCliente + ", apolice=" + apolice + ", data=" + data + ", seguro=" + seguro + ", cliente=" + cliente + ", premioBruto=" + premioBruto + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", dataContrato=" + dataContrato + ", premioLiquido=" + premioLiquido + ", limiteResponsabilidade=" + limiteResponsabilidade + ", premioLiquidoSF=" + premioLiquidoSF + '}';
    }
    
    
}
