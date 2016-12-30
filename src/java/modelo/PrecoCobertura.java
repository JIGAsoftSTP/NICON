/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;

/**
 *
 * @author AhmedJorge
 */
public class PrecoCobertura 
{
    private String id;
    private String incio;
    private String fim;
    private String premio;
    private String nc;
    private String total;
    private String acao;

    public PrecoCobertura() {
    }

    public PrecoCobertura(PrecoCobertura pc) 
    {
        this.id = pc.getId();
        this.incio = pc.getIncio();
        this.fim = pc.getFim();
        this.premio = pc.getPremio();
        this.total = pc.getTotal();
        this.nc = pc.getNc();
        this.acao = pc.getAcao();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncio() {
        return incio;
    }

    public void setIncio(String incio) {
        this.incio = incio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    public String formatStringToMoeda(String s)
    {
        return (s == null || s.isEmpty() ) ? "": Moeda.format(Float.valueOf(s));
    }
}
