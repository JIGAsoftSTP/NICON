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
public class Categoria implements Serializable
{
    private String nome;
    private String nivel;
    private String numDias;
    private String salarioBase;
    private String subsidioAlojamento;
    private String subisidioAlmoco;
    private String subisidioTransporte;
    private String almocoLivreImposto;
    private String valorTotal;
    private String estado;
    private String registro;


    public Categoria(String nome, String nivel, String numDias, String salarioBase, String subsidioAlojamento, String subisidioAlmoco, String subisidioTransporte, String estado, String registro, String total, String almocoLivre) {
        this.nome = nome;
        this.nivel = nivel;
        this.numDias = numDias;
        this.salarioBase = salarioBase;
        this.subsidioAlojamento = subsidioAlojamento;
        this.subisidioAlmoco = subisidioAlmoco;
        this.subisidioTransporte = subisidioTransporte;
        this.estado = estado;
        this.valorTotal = total;
        this.almocoLivreImposto = almocoLivre;
        
    }
    public Categoria(Categoria c) {
        this.nome = c.getNome();
        this.nivel = c.getNivel();
        this.numDias = c.getNumDias();
        this.salarioBase = c.getSalarioBase();
        this.subsidioAlojamento = c.getSubsidioAlojamento();
        this.subisidioAlmoco = c.getSubisidioAlmoco();
        this.subisidioTransporte = c.getSubisidioTransporte();
        this.almocoLivreImposto= c.getAlmocoLivreImposto();
        this.valorTotal = c.getValorTotal();
        this.estado = c.getEstado();
        this.registro = c.getRegistro();
    }
    
   
    public Categoria()
    {
        
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivel() {
        return nivel;
    }

    public String getAlmocoLivreImposto() {
        return almocoLivreImposto;
    }

    public void setAlmocoLivreImposto(String almocoLivreImposto) {
        this.almocoLivreImposto = almocoLivreImposto;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNumDias() {
        return numDias;
    }

    public void setNumDias(String numDias) {
        this.numDias = numDias;
    }

    public String getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(String salarioBase) {
        this.salarioBase = salarioBase;
    }

    public String getSubsidioAlojamento() {
        return subsidioAlojamento;
    }

    public void setSubsidioAlojamento(String subsidioAlojamento) {
        this.subsidioAlojamento = subsidioAlojamento;
    }

    public String getSubisidioAlmoco() {
        return subisidioAlmoco;
    }

    public void setSubisidioAlmoco(String subisidioAlmoco) {
        this.subisidioAlmoco = subisidioAlmoco;
    }

    public String getSubisidioTransporte() {
        return subisidioTransporte;
    }

    public void setSubisidioTransporte(String subisidioTransporte) {
        this.subisidioTransporte = subisidioTransporte;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getEstado() {
        return estado;
    }

    public String getRegistro() {
        return registro;
    }

    @Override
    public String toString() {
        return "Categoria{" + "nome=" + nome + ", nivel=" + nivel + ", numDias=" + numDias + ", salarioBase=" + salarioBase + ", subsidioAlojamento=" + subsidioAlojamento + ", subisidioAlmoco=" + subisidioAlmoco + ", subisidioTransporte=" + subisidioTransporte + ", valorTotal=" + valorTotal + '}';
    }
    
    
    
}
