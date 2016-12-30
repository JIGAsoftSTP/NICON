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
public class Sinistro implements Serializable
{
    private String id;
    private String contratoID;
    private String dataOcorrencia;
    private String dataInspecao;
    private String apolice;
    private String seguro;
    private String estado;
    private String numVeiculo;
    private String numChassi;
    private String cliente;
    private String dataRegistro;
    private Date data;
    private Date idata;
    private String hora;
    private String local;
    private String ilocal;
    private String numSinistro;
    private String enderecoPolicial;
    private String enderecoSinistro;
    private String narracaoSucedido;
    private String estimativaRecuperacao;
    private String medidasTomadas;
    private String testemunha = "false";
    private String hipoteca = "false";

    public String getApolice() {
        return apolice;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getSeguro() {
        return seguro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getDataInspecao() {
        return dataInspecao;
    }

    public void setDataInspecao(String dataInspecao) {
        this.dataInspecao = dataInspecao;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getNumVeiculo() {
        return numVeiculo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setNumVeiculo(String numVeiculo) {
        this.numVeiculo = numVeiculo;
    }

    public String getNumChassi() {
        return numChassi;
    }

    public void setNumChassi(String numChassi) {
        this.numChassi = numChassi;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEnderecoPolicial() {
        return enderecoPolicial;
    }

    public void setEnderecoPolicial(String enderecoPolicial) {
        this.enderecoPolicial = enderecoPolicial;
    }

    public String getEnderecoSinistro() {
        return enderecoSinistro;
    }

    public void setEnderecoSinistro(String enderecoSinistro) {
        this.enderecoSinistro = enderecoSinistro;
    }

    public String getNarracaoSucedido() {
        return narracaoSucedido;
    }

    public void setNarracaoSucedido(String narracaoSucedido) {
        this.narracaoSucedido = narracaoSucedido;
    }

    public String getEstimativaRecuperacao() {
        return estimativaRecuperacao;
    }

    public void setEstimativaRecuperacao(String estimativaRecuperacao) {
        this.estimativaRecuperacao = estimativaRecuperacao;
    }

    public Date getIdata() {
        return idata;
    }

    public void setIdata(Date idata) {
        this.idata = idata;
    }

    public String getIlocal() {
        return ilocal;
    }

    public void setIlocal(String ilocal) {
        this.ilocal = ilocal;
    }

    public String getMedidasTomadas() {
        return medidasTomadas;
    }

    public void setMedidasTomadas(String medidasTomadas) {
        this.medidasTomadas = medidasTomadas;
    }

    public String getTestemunha() {
        return testemunha;
    }

    public void setTestemunha(String testemunha) {
        this.testemunha = testemunha;
    }

    public String getHipoteca() {
        return hipoteca;
    }

    public void setHipoteca(String hipoteca) {
        this.hipoteca = hipoteca;
    }

    public String getContratoID() {
        return contratoID;
    }

    public void setContratoID(String contratoID) {
        this.contratoID = contratoID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumSinistro() {
        return numSinistro;
    }

    public void setNumSinistro(String numSinistro) {
        this.numSinistro = numSinistro;
    }
    
    public String getNameSeguro(String s)
    {
        switch (s) 
        {
            case "TIN": return "Seguro de Viagem";
            case "GPA": return "Seguro de A. Grupo";
            case "NHI": return "Seguro Maritimo";
            case "MV": return "Seguro de Automóvel"; 
            case "DI": return "Seguro de Roubo";
            case "FR": return "Seguro de Incêndio";
            case "MAC": return "Seguro C. Maritima";
            case "DH": return "Seguro de Dinheiro";
            case "RP": return "Seguro de R. Pública";
            case "MR": return "Seguro de Multiriscos";
            default: return "";
        }
    }

    @Override
    public String toString() {
        return "Sinistro{" + "id=" + id + ", contratoID=" + contratoID + ", dataOcorrencia=" + dataOcorrencia + ", dataInspecao=" + dataInspecao + ", apolice=" + apolice + ", seguro=" + seguro + ", estado=" + estado + ", numVeiculo=" + numVeiculo + ", numChassi=" + numChassi + ", cliente=" + cliente + ", dataRegistro=" + dataRegistro + ", data=" + data + ", idata=" + idata + ", hora=" + hora + ", local=" + local + ", ilocal=" + ilocal + ", numSinistro=" + numSinistro + ", enderecoPolicial=" + enderecoPolicial + ", enderecoSinistro=" + enderecoSinistro + ", narracaoSucedido=" + narracaoSucedido + ", estimativaRecuperacao=" + estimativaRecuperacao + ", medidasTomadas=" + medidasTomadas + ", testemunha=" + testemunha + ", hipoteca=" + hipoteca + '}';
    }
            
}
