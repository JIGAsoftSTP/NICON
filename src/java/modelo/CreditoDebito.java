/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import enumeracao.TipoPesquisa;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author AhmedJorge
 */
public class CreditoDebito implements Serializable
{
    private String tipo;
    private String numeroDucumento;
    private String conta;
    private String desc;
    private String accountDesc;
    private String movimentoDesc;
    private String typeOperationDesc;
    private String valorD;
    private String valorC;
    private String valor;
    private Date data;
    private Date dataDocumento;
    private int typeLaunch;
    private int moeda = -1;
    private int typeOperation = 1;
    private String sequenceNum;
    private String codigo;
    private String colaborador;
    private String dataRegistro;
    private TipoPesquisa tipoPesquisa;

    public CreditoDebito() {
    }

    public CreditoDebito(CreditoDebito creditoDebito)
    {
        this.numeroDucumento = creditoDebito.getNumeroDucumento();
        this.dataDocumento = creditoDebito.getDataDocumento();
        this.desc = creditoDebito.getDesc();
        this.typeOperation = creditoDebito.getTypeOperation();
        this.movimentoDesc = creditoDebito.getMovimentoDesc();
        this.moeda = creditoDebito.getMoeda();
        this.valor = creditoDebito.getValor();
        this.conta = creditoDebito.getConta();
        this.accountDesc = creditoDebito.getAccountDesc();
        if(creditoDebito.getTypeOperation() == 1) 
            this.typeOperationDesc = "Débito";
        else  this.typeOperationDesc = "Crédito";
            
        
   }
    public String getTipo() {
        return tipo;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public String getValorD() {
        return valorD;
    }

    public void setValorD(String valorD) {
        this.valorD = valorD;
    }

    public String getValorC() {
        return valorC;
    }

    public void setValorC(String valorC) {
        this.valorC = valorC;
    }

    public TipoPesquisa getTipoPesquisa() {
        return tipoPesquisa;
    }

    public void setTipoPesquisa(TipoPesquisa tipoPesquisa) {
        this.tipoPesquisa = tipoPesquisa;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public String getTypeOperationDesc() {
        return typeOperationDesc;
    }

    public void setTypeOperationDesc(String typeOperationDesc) {
        this.typeOperationDesc = typeOperationDesc;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getMoeda() {
        return moeda;
    }

    public String getAccountDesc() {
        return accountDesc;
    }

    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }

    public String getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(String sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public int getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(int typeOperation) {
        this.typeOperation = typeOperation;
    }

    public void setMoeda(int moeda) {
        this.moeda = moeda;
    }

    public int getTypeLaunch() {
        return typeLaunch;
    }

    public void setTypeLaunch(int typeLaunch) {
        this.typeLaunch = typeLaunch;
    }

    public String getMovimentoDesc() {
        return movimentoDesc;
    }

    public void setMovimentoDesc(String movimentoDesc) {
        this.movimentoDesc = movimentoDesc;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNumeroDucumento() {
        return numeroDucumento;
    }

    public void setNumeroDucumento(String numeroDucumento) {
        this.numeroDucumento = numeroDucumento;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "CreditoDebito{" + "tipo=" + tipo + ", numeroDucumento=" + numeroDucumento + ", conta=" + conta + ", desc=" + desc + ", accountDesc=" + accountDesc + ", movimentoDesc=" + movimentoDesc + ", valor=" + valor + ", data=" + data + ", dataDocumento=" + dataDocumento + ", typeLaunch=" + typeLaunch + ", moeda=" + moeda + ", typeOperation=" + typeOperation + ", sequenceNum=" + sequenceNum + '}';
    }

 
  
    
}
