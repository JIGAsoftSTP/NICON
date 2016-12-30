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
public class Artigo implements Serializable
{
    private String nomeArtigo;
    private String codigo;
    private String funcionarioResponsavel;
    private String quantidade;
    private String categoria;
    private String fornecedor;
    private String descricao;
    private String custoEstimado;
    private String sourceDestination;
    private String observation;
    private String consumivel;
    private String registro;
    private String estado;
    private String tipoOperacao = "novo";
    private String adicionarRemover;
    private int quantidadeStock;
    private Integer id;
    
    public Artigo()
    {
    }

    public String getCodigo() {
        return codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getConsumivel() {
        return consumivel;
    }

    public void setConsumivel(String consumivel) {
        this.consumivel = consumivel;
    }

    public int getQuantidadeStock() {
        return quantidadeStock;
    }

    public void setQuantidadeStock(int quantidadeStock) {
        this.quantidadeStock = quantidadeStock;
    }

    
    public String getRegistro() {
        return registro;
    }

    public String getAdicionarRemover() {
        return adicionarRemover;
    }

    public void setAdicionarRemover(String adicionarRemover) {
        this.adicionarRemover = adicionarRemover;
    }

    
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }
    
    
    public String getNomeArtigo() {
        return nomeArtigo;
    }

    public void setNomeArtigo(String nomeArtigo) {
        this.nomeArtigo = nomeArtigo;
    }

    public String getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(String funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCustoEstimado() {
        return custoEstimado;
    }

    public void setCustoEstimado(String custoEstimado) {
        this.custoEstimado = custoEstimado;
    }

    public String getSourceDestination() {
        return sourceDestination;
    }

    public void setSourceDestination(String sourceDestination) {
        this.sourceDestination = sourceDestination;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "Artigo{" + "nomeArtigo=" + nomeArtigo + ", codigo=" + codigo + ", funcionarioResponsavel=" + funcionarioResponsavel + ", quantidade=" + quantidade + ", categoria=" + categoria + ", fornecedor=" + fornecedor + ", descricao=" + descricao + ", custoEstimado=" + custoEstimado + ", sourceDestination=" + sourceDestination + ", observation=" + observation + ", consumivel=" + consumivel + ", registro=" + registro + ", estado=" + estado + '}';
    }
    
    
    
  
    
    
    
}
