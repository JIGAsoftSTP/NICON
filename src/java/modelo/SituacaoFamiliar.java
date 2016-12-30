/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author ahmedjorge
 */
public class SituacaoFamiliar {
    private Float salarioBase;
    private Integer numFilho;
    private Float percentagem;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    private String estado;
    private String valor;

    public SituacaoFamiliar() {
    }

    
    public Float getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Float salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Integer getNumFilho() {
        return numFilho;
    }

    public void setNumFilho(Integer numFilho) {
        this.numFilho = numFilho;
    }

    public Float getPercentagem() {
        return percentagem;
    }

    public void setPercentagem(Float percentagem) {
        this.percentagem = percentagem;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "SituacaoFamiliar{" + "salarioBase=" + salarioBase + ", numFilho=" + numFilho + ", percentagem=" + percentagem + ", estado=" + estado + '}';
    }
    
}
