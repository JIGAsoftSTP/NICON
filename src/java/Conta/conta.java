/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conta;

/**
 *
 * @author ILDO
 */
public class conta {
    private int id;
    private String ContaId;
    private String ContaDescricao;
    private String SuperConta;
    private int nivel;
    private String SuperId;
    private int Classe;

    public conta(int id, String ContaId, String ContaDescricao, int nivel, String SuperId, int Classe, String SuperConta) {
        this.id = id;
        this.ContaId = ContaId;
        this.ContaDescricao = ContaDescricao;
        this.nivel = nivel;
        this.SuperId = SuperId;
        this.Classe = Classe;
        this.SuperConta = SuperConta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContaId(String ContaId) {
        this.ContaId = ContaId;
    }

    public void setContaDescricao(String ContaDescricao) {
        this.ContaDescricao = ContaDescricao;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setSuperId(String SuperId) {
        this.SuperId = SuperId;
    }

    public void setClasse(int Classe) {
        this.Classe = Classe;
    }

    public int getId() {
        return id;
    }

    public String getContaId() {
        return ContaId;
    }

    public String getContaDescricao() {
        return ContaDescricao;
    }

    public String getSuperConta() {
        return SuperConta;
    }

    public int getNivel() {
        return nivel;
    }

    public String getSuperId() {
        return SuperId;
    }

    public int getClasse() {
        return Classe;
    }

    public void setSuperConta(String SuperConta) {
        this.SuperConta = SuperConta;
    }
   
    
    
}
