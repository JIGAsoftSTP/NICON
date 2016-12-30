/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import lib.Moeda;

/**
 *
 * @author AhmedJorge
 */
public class ModeloPagamento 
{
    private double valorPagameto;
    private String valorPagametoVeiw;
    private Date dataPagamentoLimite;
    private String dataPagamentoLimiteView;
    private Integer prazo;
    private Integer prestacao;
    private String tipoPagamento;
    private final SimpleDateFormat sdf  =new SimpleDateFormat("dd-MM-yyyy");

    public ModeloPagamento() {
    }
    
    
    public ModeloPagamento(ModeloPagamento mp) {
        this.valorPagameto = mp.valorPagameto;
        this.valorPagametoVeiw = Moeda.format(mp.valorPagameto);
        this.dataPagamentoLimite = mp.dataPagamentoLimite;
        this.dataPagamentoLimiteView = sdf.format(mp.dataPagamentoLimite);
    }

    public double getValorPagameto() {
        return valorPagameto;
    }

    public void setValorPagameto(double valorPagameto) {
        this.valorPagameto = valorPagameto;
    }

    public String getValorPagametoVeiw() {
        return valorPagametoVeiw;
    }

    public void setValorPagametoVeiw(String valorPagametoVeiw) {
        this.valorPagametoVeiw = valorPagametoVeiw;
    }

    public Date getDataPagamentoLimite() {
        return dataPagamentoLimite;
    }

    public void setDataPagamentoLimite(Date dataPagamentoLimite) {
        this.dataPagamentoLimite = dataPagamentoLimite;
    }

    public String getDataPagamentoLimiteView() {
        return dataPagamentoLimiteView;
    }

    public void setDataPagamentoLimiteView(String dataPagamentoLimiteView) {
        this.dataPagamentoLimiteView = dataPagamentoLimiteView;
    }

    public Integer getPrazo() {
        return prazo;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }

    public Integer getPrestacao() {
        return prestacao;
    }

    public void setPrestacao(Integer prestacao) {
        this.prestacao = prestacao;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
}
