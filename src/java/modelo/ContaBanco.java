/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author ahmedjorge
 */
public class ContaBanco {
    private Integer id;
    private Integer idtipoConta;
    private String numConta;
    private Integer idtipoContaContabilistica;
    private Integer idbanco;
    private Integer idTipoMoeda;
    private String nameConta;
    private String descContaContabilistica;
    private String numContaContabilistica;
    
    public Integer getIdtipoConta() {
        return idtipoConta;
    }

    public void setIdtipoConta(Integer idtipoConta) {
        this.idtipoConta = idtipoConta;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public Integer getIdtipoContaContabilistica() {
        return idtipoContaContabilistica;
    }

    public void setIdtipoContaContabilistica(Integer idtipoContaContabilistica) {
        this.idtipoContaContabilistica = idtipoContaContabilistica;
    }

    public Integer getIdbanco() {
        return idbanco;
    }

    public void setIdbanco(Integer idbanco) {
        this.idbanco = idbanco;
    }

    public Integer getIdTipoMoeda() {
        return idTipoMoeda;
    }

    public void setIdTipoMoeda(Integer idTipoMoeda) {
        this.idTipoMoeda = idTipoMoeda;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameConta() {
        return nameConta;
    }

    public void setNameConta(String nameConta) {
        this.nameConta = nameConta;
    }

    public String getDescContaContabilistica() {
        return descContaContabilistica;
    }

    public void setDescContaContabilistica(String descContaContabilistica) {
        this.descContaContabilistica = descContaContabilistica;
    }

    public String getNumContaContabilistica() {
        return numContaContabilistica;
    }

    public void setNumContaContabilistica(String numContaContabilistica) {
        this.numContaContabilistica = numContaContabilistica;
    }
    
}
