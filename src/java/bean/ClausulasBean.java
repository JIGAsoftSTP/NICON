/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Contrato;

/**
 *
 * @author Helio
 */
@ManagedBean
@SessionScoped
public class ClausulasBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Contrato contrato = new Contrato();

    public Contrato getContrato() {
        return contrato;
    }  

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
    
    
    
}
