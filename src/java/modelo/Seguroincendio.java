
package modelo;

import bean.AutenticacaoBean;
import dao.Seguroincendiodao;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class Seguroincendio implements Serializable
{
    private String numeroApolice;
    private String numeroRegistro;
    private String endereco_Edificio;
    private String numero_Andares;
    private String condicao;
    private String ano;
    private String equi_Combate_Incendio;
    private String fonte_Agua_Disponivel;
    private String distancia_Com_Bombeiro;
    private String pavimento_Edificio;
    private String tetos;
    private String parede;
    private String processoFabricacao;
   
    private String TaxaAviao;
    private String TaxaColissao;
    private String TaxaExplusao;
    private String TaxaTerramorto;
    private String TaxaTempestade;
    private String TaxaAfundamento;
    private String TaxaRisccoPolitico;
    private String TaxaDarnosMalicioso;
    private String TaxaTumultoeGreve;
    private String TaxaIncendioFlorestal;
    private String TaxaExplosaoExpontanea;
    private String TaxaRopturadeCanalizacao;
    
    private String varimentoDiariamente="N";
    private String trenamentoInquilinos ="N";
    private String euipamentodecombateaincendio = "N";
    private String outrosInteressadopropriedade;
    private String fumar;
    private String perdaPorIncendio;

    public Seguroincendio(String numeroApolice, String numeroRegistro, String endereco_Edificio, String numero_Andares, String condicao, String ano, String equi_Combate_Incendio, String fonte_Agua_Disponivel, String distancia_Com_Bombeiro, String pavimento_Edificio, String tetos, String parede, String processoFabricacao, String TaxaAviao, String TaxaColissao, String TaxaExplusao, String TaxaTerramorto, String TaxaTempestade, String TaxaAfundamento, String TaxaRisccoPolitico, String TaxaDarnosMalicioso, String TaxaTumultoeGreve, String TaxaIncendioFlorestal, String TaxaExplosaoExpontanea, String TaxaRopturadeCanalizacao, String outrosInteressadopropriedade, String fumar, String perdaPorIncendio) {
        this.numeroApolice = numeroApolice;
        this.numeroRegistro = numeroRegistro;
        this.endereco_Edificio = endereco_Edificio;
        this.numero_Andares = numero_Andares;
        this.condicao = condicao;
        this.ano = ano;
        this.equi_Combate_Incendio = equi_Combate_Incendio;
        this.fonte_Agua_Disponivel = fonte_Agua_Disponivel;
        this.distancia_Com_Bombeiro = distancia_Com_Bombeiro;
        this.pavimento_Edificio = pavimento_Edificio;
        this.tetos = tetos;
        this.parede = parede;
        this.processoFabricacao = processoFabricacao;
        this.TaxaAviao = TaxaAviao;
        this.TaxaColissao = TaxaColissao;
        this.TaxaExplusao = TaxaExplusao;
        this.TaxaTerramorto = TaxaTerramorto;
        this.TaxaTempestade = TaxaTempestade;
        this.TaxaAfundamento = TaxaAfundamento;
        this.TaxaRisccoPolitico = TaxaRisccoPolitico;
        this.TaxaDarnosMalicioso = TaxaDarnosMalicioso;
        this.TaxaTumultoeGreve = TaxaTumultoeGreve;
        this.TaxaIncendioFlorestal = TaxaIncendioFlorestal;
        this.TaxaExplosaoExpontanea = TaxaExplosaoExpontanea;
        this.TaxaRopturadeCanalizacao = TaxaRopturadeCanalizacao;
        this.outrosInteressadopropriedade = outrosInteressadopropriedade;
        this.fumar = fumar;
        this.perdaPorIncendio = perdaPorIncendio;
    }
    
     public String getEuipamentodecombateaincendio() {
        return euipamentodecombateaincendio;
    }

    public void setEuipamentodecombateaincendio(String euipamentodecombateaincendio) {
        this.euipamentodecombateaincendio = euipamentodecombateaincendio;
    }
     public Seguroincendio()
        {

        }
    
      public String getVarimentoDiariamente() {
        return varimentoDiariamente;
    }

    public void setVarimentoDiariamente(String varimentoDiariamente) {
        this.varimentoDiariamente = varimentoDiariamente;
    }

    public String getTrenamentoInquilinos() {
        return trenamentoInquilinos;
    }

    public void setTrenamentoInquilinos(String trenamentoInquilinos) {
        this.trenamentoInquilinos = trenamentoInquilinos;
    }
     
    public String getOutrosInteressadopropriedade() {
        return outrosInteressadopropriedade;
    }

    public void setOutrosInteressadopropriedade(String outrosInteressadopropriedade) {
        this.outrosInteressadopropriedade = outrosInteressadopropriedade;
    }

    public String getFumar() {
        return fumar;
    }

    public void setFumar(String fumar) {
        this.fumar = fumar;
    }

    public String getPerdaPorIncendio() {
        return perdaPorIncendio;
    }

    public void setPerdaPorIncendio(String perdaPorIncendio) {
        this.perdaPorIncendio = perdaPorIncendio;
    }
    
    public String getTaxaAviao() {
        return TaxaAviao;
    }

    public void setTaxaAviao(String TaxaAviao) {
        this.TaxaAviao = TaxaAviao;
    }

    public String getTaxaColissao() {
        return TaxaColissao;
    }

    public void setTaxaColissao(String TaxaColissao) {
        this.TaxaColissao = TaxaColissao;
    }

    public String getTaxaExplusao() {
        return TaxaExplusao;
    }

    public void setTaxaExplusao(String TaxaExplusao) {
        this.TaxaExplusao = TaxaExplusao;
    }

    public String getTaxaTerramorto() {
        return TaxaTerramorto;
    }

    public void setTaxaTerramorto(String TaxaTerramorto) {
        this.TaxaTerramorto = TaxaTerramorto;
    }

    public String getTaxaTempestade() {
        return TaxaTempestade;
    }

    public void setTaxaTempestade(String TaxaTempestade) {
        this.TaxaTempestade = TaxaTempestade;
    }

    public String getTaxaAfundamento() {
        return TaxaAfundamento;
    }

    public void setTaxaAfundamento(String TaxaAfundamento) {
        this.TaxaAfundamento = TaxaAfundamento;
    }

    public String getTaxaRisccoPolitico() {
        return TaxaRisccoPolitico;
    }

    public void setTaxaRisccoPolitico(String TaxaRisccoPolitico) {
        this.TaxaRisccoPolitico = TaxaRisccoPolitico;
    }

    public String getTaxaDarnosMalicioso() {
        return TaxaDarnosMalicioso;
    }

    public void setTaxaDarnosMalicioso(String TaxaDarnosMalicioso) {
        this.TaxaDarnosMalicioso = TaxaDarnosMalicioso;
    }

    public String getTaxaTumultoeGreve() {
        return TaxaTumultoeGreve;
    }

    public void setTaxaTumultoeGreve(String TaxaTumultoeGreve) {
        this.TaxaTumultoeGreve = TaxaTumultoeGreve;
    }

    public String getTaxaIncendioFlorestal() {
        return TaxaIncendioFlorestal;
    }

    public void setTaxaIncendioFlorestal(String TaxaIncendioFlorestal) {
        this.TaxaIncendioFlorestal = TaxaIncendioFlorestal;
    }

    public String getTaxaExplosaoExpontanea() {
        return TaxaExplosaoExpontanea;
    }

    public void setTaxaExplosaoExpontanea(String TaxaExplosaoExpontanea) {
        this.TaxaExplosaoExpontanea = TaxaExplosaoExpontanea;
    }

    public String getTaxaRopturadeCanalizacao() {
        return TaxaRopturadeCanalizacao;
    }

    public void setTaxaRopturadeCanalizacao(String TaxaRopturadeCanalizacao) {
        this.TaxaRopturadeCanalizacao = TaxaRopturadeCanalizacao;
    }
    
    
    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }
    
    public String getProcessoFabricacao() {
        return processoFabricacao;
    }
     public void setProcessoFabricacao(String processoFabricacao) {
        this.processoFabricacao = processoFabricacao;
        this.processoFabricacao = (processoFabricacao.equals("N")) ? null : "ativa";
    }


   
    public String getEndereco_Edificio() {
        return endereco_Edificio;
    }

    public void setEndereco_Edificio(String endereco_Edificio) {
        this.endereco_Edificio = endereco_Edificio;
    }

    public String getNumero_Andares() {
        return numero_Andares;
    }

    public void setNumero_Andares(String numero_Andares) {
        this.numero_Andares = numero_Andares;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getEqui_Combate_Incendio() {
        return equi_Combate_Incendio;
    }

    public void setEqui_Combate_Incendio(String equi_Combate_Incendio) {
        this.equi_Combate_Incendio = equi_Combate_Incendio;
    }

    public String getFonte_Agua_Disponivel() {
        return fonte_Agua_Disponivel;
    }

    public void setFonte_Agua_Disponivel(String fonte_Agua_Disponivel) {
        this.fonte_Agua_Disponivel = fonte_Agua_Disponivel;
    }

    public String getDistancia_Com_Bombeiro() {
        return distancia_Com_Bombeiro;
    }

    public void setDistancia_Com_Bombeiro(String distancia_Com_Bombeiro) {
        this.distancia_Com_Bombeiro = distancia_Com_Bombeiro;
    }

    public String getPavimento_Edificio() {
        return pavimento_Edificio;
    }

    public void setPavimento_Edificio(String pavimento_Edificio) {
        this.pavimento_Edificio = pavimento_Edificio;
    }

    public String getTetos() {
        return tetos;
    }

    public void setTetos(String tetos) {
        this.tetos = tetos;
    }

    public String getParede() 
    {
        return parede;
    }

    public void setParede(String parede) {
        this.parede = parede;
    }  
//    public void registarSeguroincendio(Seguroincendio seguroincediodb)
//        {
//             Seguroincendiodao dao= new Seguroincendiodao();
//            dao.registarSeguroincendio(seguroincediodb);
//        } 
}
