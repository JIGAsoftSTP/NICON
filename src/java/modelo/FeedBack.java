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
public class FeedBack implements Serializable
{
    private String nome;
    private String emailResposta;
    private String assunto;
    private String mensagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailResposta() {
        return emailResposta;
    }

    public void setEmailResposta(String emailResposta) {
        this.emailResposta = emailResposta;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "FeedBack{" + "nome=" + nome + ", emailResposta=" + emailResposta + ", assunto=" + assunto + ", mensagem=" + mensagem + '}';
    }
    
    
    
}
