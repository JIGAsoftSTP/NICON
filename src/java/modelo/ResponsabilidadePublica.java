
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helio
 */
public class ResponsabilidadePublica implements Serializable
{
    private String numeroRegistro;
    private String numeroApolice;
    private String empregado;
    private String profissao;
    private String endereco;
    private String moeda;
    private String salarioDiretorColaborador;
    private String salarioSubempreiteiros;
    private String totalSegurado;
    private String totalSeguradoFormatado;
    private String premioBruto;
    private String premioBrutoMoeda;
    private String premioLiquido;
    private String premioLiquidoMoeda;
    private String cover;
    private String estadoEdificio ="61";
    public ResponsabilidadePublica()
    {
        
    }

    public ResponsabilidadePublica(String empregado, String profissao, String endereco) 
    {
        this.empregado = empregado;
        this.profissao = profissao;
        this.endereco = endereco;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSalarioDiretorColaborador() {
        return salarioDiretorColaborador;
    }

    public String getEstadoEdificio() {
        return estadoEdificio;
    }

    public void setEstadoEdificio(String estadoEdificio) {
        this.estadoEdificio = estadoEdificio;
    }

    public void setSalarioDiretorColaborador(String salarioDiretorColaborador) {
        this.salarioDiretorColaborador = salarioDiretorColaborador;
    }

    public String getSalarioSubempreiteiros() {
        return salarioSubempreiteiros;
    }

    public void setSalarioSubempreiteiros(String salarioSubempreiteiros) {
        this.salarioSubempreiteiros = salarioSubempreiteiros;
    }

    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getEmpregado() {
        return empregado;
    }

    public void setEmpregado(String empregado) {
        this.empregado = empregado;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getTotalSegurado() {
        return totalSegurado;
    }

    public void setTotalSegurado(String totalSegurado) {
        this.totalSegurado = totalSegurado;
    }

    public String getTotalSeguradoFormatado() {
        return totalSeguradoFormatado;
    }

    public void setTotalSeguradoFormatado(String totalSeguradoFormatado) {
        this.totalSeguradoFormatado = totalSeguradoFormatado;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public String getPremioBrutoMoeda() {
        return premioBrutoMoeda;
    }

    public void setPremioBrutoMoeda(String premioBrutoMoeda) {
        this.premioBrutoMoeda = premioBrutoMoeda;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public String getPremioLiquidoMoeda() {
        return premioLiquidoMoeda;
    }

    public void setPremioLiquidoMoeda(String premioLiquidoMoeda) {
        this.premioLiquidoMoeda = premioLiquidoMoeda;
    }

    public String getEmprego() {
        return empregado;
    }

    public void setEmprego(String empregado) {
        this.empregado = empregado;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "ResponsabilidadePublica{" + "numeroRegistro=" + numeroRegistro + ", numeroApolice=" + numeroApolice + ", empregado=" + empregado + ", profissao=" + profissao + ", endereco=" + endereco + ", moeda=" + moeda + ", salarioDiretorColaborador=" + salarioDiretorColaborador + ", salarioSubempreiteiros=" + salarioSubempreiteiros + ", totalSegurado=" + totalSegurado + ", totalSeguradoFormatado=" + totalSeguradoFormatado + ", premioBruto=" + premioBruto + ", premioBrutoMoeda=" + premioBrutoMoeda + ", premioLiquido=" + premioLiquido + ", premioLiquidoMoeda=" + premioLiquidoMoeda + ", estadoEdificio=" + estadoEdificio + '}';
    }
    
    
    
}
