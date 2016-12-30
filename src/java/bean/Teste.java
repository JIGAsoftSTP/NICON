/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Helio
 */
@ManagedBean
@RequestScoped
public class Teste implements Serializable
{
    private String nome;
    private String apelido;
    
    public void salvar()
    {
        nome= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dados");
        System.out.println("INFORMAÇÕES "+nome+"\n"+apelido);
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }
    
    

}