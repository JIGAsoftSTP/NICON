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
public class Cheque implements Serializable
{
    
    private String banco;
    private String inicio;
    private String fim;
    private String total;
    private String numBancoConta;


    public Cheque() {
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNumBancoConta() {
        return numBancoConta;
    }

    public void setNumBancoConta(String numBancoConta) {
        this.numBancoConta = numBancoConta;
    }

    @Override
    public String toString() {
        return "Cheque{" + "banco=" + banco + ", inicio=" + inicio + ", fim=" + fim + ", total=" + total + "}";
    }
    
}
