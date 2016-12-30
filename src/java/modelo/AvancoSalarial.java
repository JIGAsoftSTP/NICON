/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author ahmedjorge
 */
public class AvancoSalarial implements Serializable{
    
    private String id;
    private String funcionario;
    private String valor;
    private Date data;
    private String dataView;
    private String ojs;
    private String numDoc;
    private String estado;
    private float salarioFinalMes;
   
    private String registro;

    public String getFuncionario() {
        return funcionario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public float getSalarioFinalMes() {
        return salarioFinalMes;
    }

    public void setSalarioFinalMes(float salarioFinalMes) {
        this.salarioFinalMes = salarioFinalMes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getOjs() {
        return ojs;
    }

    public void setOjs(String ojs) {
        this.ojs = ojs;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }
    
    public String toDate(Date d)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(d);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getDataView() {
        return dataView;
    }

    public void setDataView(String dataView) {
        this.dataView = dataView;
    }

    @Override
    public String toString() {
        return "AvancoSalarial{" + "funcionario=" + funcionario + ", valor=" + valor + ", data=" + data + ", dataView=" + dataView + ", ojs=" + ojs + ", numDoc=" + numDoc + ", estado=" + estado + ", registro=" + registro + '}';
    }
    
}
