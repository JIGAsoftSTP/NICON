/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author AhmedJorge
 */
public class CoberturaLoad 
{
    private String tipoCobertura;
    private String premio;
    private String valorPremio;
    private String taxa;
    private String detalhes;

    public CoberturaLoad() {
    }
    
    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(String valorPremio) {
        this.valorPremio = valorPremio;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    @Override
    public String toString() {
        return "CoberturaLoad{" + "tipoCobertura=" + tipoCobertura + ", premio=" + premio + ", valorPremio=" + valorPremio + ", taxa=" + taxa + ", detalhes=" + detalhes + '}';
    }
    
    
}
