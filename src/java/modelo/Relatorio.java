
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Helio
 */
public class Relatorio implements Serializable
{
    private Date dataInicio;
    private Date dataFim;
    private int agrupamento;
    private String seguro;
    private String valorPesquisa;
    private String campoPesquisa;
    private String tipoRelatorio ="Clientes";
    private String tipoTaxa;
    private String nome;
    private String valor;

    public Relatorio()
    {
        
    }
    public Relatorio ( String nome, String valor)
    {
        this.nome = nome;
        this.valor = valor;
    }
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public String getTipoTaxa() {
        return tipoTaxa;
    }

    public void setTipoTaxa(String tipoTaxa) {
        this.tipoTaxa = tipoTaxa;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValorPesquisa() {
        return valorPesquisa;
    }

    public String getCampoPesquisa() {
        return campoPesquisa;
    }

    public void setCampoPesquisa(String campoPesquisa) {
        this.campoPesquisa = campoPesquisa;
    }

    
    public void setValorPesquisa(String valorPesquisa) {
        this.valorPesquisa = valorPesquisa;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public int getAgrupamento() {
        return agrupamento;
    }

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public void setAgrupamento(int agrupamento) {
        this.agrupamento = agrupamento;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    @Override
    public String toString() {
        return "Relatorio{" + "dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", agrupamento=" + agrupamento + ", seguro=" + seguro + ", valorPesquisa=" + valorPesquisa + ", campoPesquisa=" + campoPesquisa + ", tipoRelatorio=" + tipoRelatorio + '}';
    }
    
    
    
}
