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
public class ProcessamentoSalario implements Serializable
{
    private String funcionario;
    private String data;
    private String banco;
    private String montante;
    private String estado;
    private String valorTotalSemImposto;
    private String valorTotalReceber;
    private String idFuncionario;
    private String valorTotalEmpresa;
    private String valorTotalSegSocialEmpresa;
    private String valorTotalSegSocialFuncionario;
    private String valorIRS;
    private String mes;
    private String ano;
    private String codigoProcesso;
    private String id;
    
    
    public ProcessamentoSalario()
    {
        
    }
    
    public ProcessamentoSalario(String idFuncionario,String funcionario, String salarioBase, String totalReceber,String valorTotalEmpresa, String valorTotalSegSocialEmpresa, String valorTotalSegSocialFuncionario, String irs)
    {
        this.idFuncionario = idFuncionario;
        this.funcionario = funcionario;
        this.valorTotalSemImposto = salarioBase;
        this.valorTotalReceber = totalReceber;
        this.valorTotalEmpresa = valorTotalEmpresa;
        this.valorTotalSegSocialEmpresa = valorTotalSegSocialEmpresa;
        this.valorTotalSegSocialFuncionario = valorTotalSegSocialFuncionario;
        this.valorIRS = irs;
    }
    
    public ProcessamentoSalario(ProcessamentoSalario ps)
    {
        this.id = ps.getId();
        this.codigoProcesso = ps.getCodigoProcesso();
        this.data = ps.getData();
    }
    public String getCodigoProcesso() {
        return codigoProcesso;
    }

    public void setCodigoProcesso(String codigoProcesso) {
        this.codigoProcesso = codigoProcesso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getFuncionario() {
        return funcionario;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getValorTotalEmpresa() {
        return valorTotalEmpresa;
    }

    public String getValorIRS() {
        return valorIRS;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setValorIRS(String valorIRS) {
        this.valorIRS = valorIRS;
    }

    public void setValorTotalEmpresa(String valorTotalEmpresa) {
        this.valorTotalEmpresa = valorTotalEmpresa;
    }

    public String getValorTotalSegSocialEmpresa() {
        return valorTotalSegSocialEmpresa;
    }

    public void setValorTotalSegSocialEmpresa(String valorTotalSegSocialEmpresa) {
        this.valorTotalSegSocialEmpresa = valorTotalSegSocialEmpresa;
    }

    public String getValorTotalSegSocialFuncionario() {
        return valorTotalSegSocialFuncionario;
    }

    public void setValorTotalSegSocialFuncionario(String valorTotalSegSocialFuncionario) {
        this.valorTotalSegSocialFuncionario = valorTotalSegSocialFuncionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getValorTotalSemImposto() {
        return valorTotalSemImposto;
    }

    public void setValorTotalSemImposto(String valorTotalSemImposto) {
        this.valorTotalSemImposto = valorTotalSemImposto;
    }

    public String getValorTotalReceber() {
        return valorTotalReceber;
    }

    public void setValorTotalReceber(String valorTotalReceber) {
        this.valorTotalReceber = valorTotalReceber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getMontante() {
        return montante;
    }

    public void setMontante(String montante) {
        this.montante = montante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
