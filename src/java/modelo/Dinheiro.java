
package modelo;

import java.io.Serializable;

public class Dinheiro implements Serializable
{
    private String numApolice;
    private String numeroRegistro;
    private String nome_Fabricante;
    private String numero_Fabricante;
    private String tamanho;
    private String moeda;
    private String cover;
    private String sigla;
    private String peso;
    private String detentor_Chave;
    private String construido_ou_Fixo = "Y";
    private String estrutura;
    
    public boolean inten1=false;
    public String inten1Valor;
    public String inten1Lresp;
    
    public boolean inten2=false;
    public String inten2Valor;
    public String inten2Lresp;
    
    public boolean inten3=false;
    public String inten3Valor;
    public String inten3Lresp;
    
    public boolean inten4=false;
    public String inten4Valor;
    public String inten4Lresp;
    
    public boolean inten5=false;
    public String inten5Valor;
    public String inten5Lresp;
    
    private String banco;
    private String correio;
    private String outros;
    private String transporteDinheiro;
    private String precupacao;
    private String tempoPermanenciaBanco;
    private String pagamento1item;
    
    private String totalSegurado;
    private String totalSeguradoFormatado;
    private String premioBruto;
    private String premioLiquido;
    private String premioBrutoMoeda;
    private String premioLiquidoMoeda;
 
    public Dinheiro()
    {
        
    }

    public Dinheiro(String nome_Fabricante, String numero_Fabricante, String tamanho, String peso, String detentor_Chave, String construido_ou_Fixo, String estrutura) {
        this.nome_Fabricante = nome_Fabricante;
        this.numero_Fabricante = numero_Fabricante;
        this.tamanho = tamanho;
        this.peso = peso;
        this.detentor_Chave = detentor_Chave;
        this.construido_ou_Fixo = construido_ou_Fixo;
        this.estrutura = estrutura;
    }
     public void limparInfo(String nome_Fabricante, String numero_Fabricante, String tamanho, String peso, String detentor_Chave, String construido_ou_Fixo, String estrutura) {
        this.nome_Fabricante = nome_Fabricante;
        this.numero_Fabricante = numero_Fabricante;
        this.tamanho = tamanho;
        this.peso = peso;
        this.detentor_Chave = detentor_Chave;
        this.construido_ou_Fixo = construido_ou_Fixo;
        this.estrutura = estrutura;
    }

    public void Limpar(String inten1Valor, String inten1Lresp, String inten2Valor, String inten2Lresp, String inten3Valor, String inten3Lresp, String inten4Valor, String inten4Lresp, String inten5Valor, String inten5Lresp, String banco, String correio, String outros, String transporteDinheiro, String precupacao, String tempoPermanenciaBanco, String pagamento1item) {
        this.inten1Valor = inten1Valor;
        this.inten1Lresp = inten1Lresp;
        this.inten2Valor = inten2Valor;
        this.inten2Lresp = inten2Lresp;
        this.inten3Valor = inten3Valor;
        this.inten3Lresp = inten3Lresp;
        this.inten4Valor = inten4Valor;
        this.inten4Lresp = inten4Lresp;
        this.inten5Valor = inten5Valor;
        this.inten5Lresp = inten5Lresp;
        this.banco = banco;
        this.correio = correio;
        this.outros = outros;
        this.transporteDinheiro = transporteDinheiro;
        this.precupacao = precupacao;
        this.tempoPermanenciaBanco = tempoPermanenciaBanco;
        this.pagamento1item = pagamento1item;
    }
   
  
    public String getNumApolice() {
        return numApolice;
    }
    public void setNumApolice(String numApolice) {
        this.numApolice = numApolice;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getSigla() {
        return sigla;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
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
    
    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getNome_Fabricante() {
        return nome_Fabricante;
    }

    public void setNome_Fabricante(String nome_Fabricante) {
        this.nome_Fabricante = nome_Fabricante;
    }

    public String getNumero_Fabricante() {
        return numero_Fabricante;
    }

    public void setNumero_Fabricante(String numero_Fabricante) {
        this.numero_Fabricante = numero_Fabricante;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDetentor_Chave() {
        return detentor_Chave;
    }

    public void setDetentor_Chave(String detentor_Chave) {
        this.detentor_Chave = detentor_Chave;
    }

    public String getConstruido_ou_Fixo() {
        return construido_ou_Fixo;
    }

    public void setConstruido_ou_Fixo(String construido_ou_Fixo) {
        this.construido_ou_Fixo = construido_ou_Fixo;
    }

    public String getEstrutura() {
        return estrutura;
    }

    public void setEstrutura(String estrutura) {
        this.estrutura = estrutura;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCorreio() {
        return correio;
    }

    public void setCorreio(String correio) {
        this.correio = correio;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }

    public String getTransporteDinheiro() {
        return transporteDinheiro;
    }

    public void setTransporteDinheiro(String transporteDinheiro) {
        this.transporteDinheiro = transporteDinheiro;
    }

    public String getPrecupacao() {
        return precupacao;
    }

    public void setPrecupacao(String precupacao) {
        this.precupacao = precupacao;
    }

    public String getTempoPermanenciaBanco() {
        return tempoPermanenciaBanco;
    }

    public void setTempoPermanenciaBanco(String tempoPermanenciaBanco) {
        this.tempoPermanenciaBanco = tempoPermanenciaBanco;
    }

    public String getPagamento1item() {
        return pagamento1item;
    }

    public void setPagamento1item(String pagamento1item) {
        this.pagamento1item = pagamento1item;
    }

    public boolean isInten1() {
        return inten1;
    }

    public void setInten1(boolean inten1) {
        this.inten1 = inten1;
    }

    public String getInten1Valor() {
        return inten1Valor;
    }

    public void setInten1Valor(String inten1Valor) {
        this.inten1Valor = inten1Valor;
    }

    public String getInten1Lresp() {
        return inten1Lresp;
    }

    public void setInten1Lresp(String inten1Lresp) {
        this.inten1Lresp = inten1Lresp;
    }

    public boolean isInten2() {
        return inten2;
    }

    public void setInten2(boolean inten2) {
        this.inten2 = inten2;
    }

    public String getInten2Valor() {
        return inten2Valor;
    }

    public void setInten2Valor(String inten2Valor) {
        this.inten2Valor = inten2Valor;
    }

    public String getInten2Lresp() {
        return inten2Lresp;
    }

    public void setInten2Lresp(String inten2Lresp) {
        this.inten2Lresp = inten2Lresp;
    }

    public boolean isInten3() {
        return inten3;
    }

    public void setInten3(boolean inten3) {
        this.inten3 = inten3;
    }

    public String getInten3Valor() {
        return inten3Valor;
    }

    public void setInten3Valor(String inten3Valor) {
        this.inten3Valor = inten3Valor;
    }

    public String getInten3Lresp() {
        return inten3Lresp;
    }

    public void setInten3Lresp(String inten3Lresp) {
        this.inten3Lresp = inten3Lresp;
    }

    public boolean isInten4() {
        return inten4;
    }

    public void setInten4(boolean inten4) {
        this.inten4 = inten4;
    }

    public String getInten4Valor() {
        return inten4Valor;
    }

    public void setInten4Valor(String inten4Valor) {
        this.inten4Valor = inten4Valor;
    }

    public String getInten4Lresp() {
        return inten4Lresp;
    }

    public void setInten4Lresp(String inten4Lresp) {
        this.inten4Lresp = inten4Lresp;
    }

    public boolean isInten5() {
        return inten5;
    }

    public void setInten5(boolean inten5) {
        this.inten5 = inten5;
    }

    public String getInten5Valor() {
        return inten5Valor;
    }

    public void setInten5Valor(String inten5Valor) {
        this.inten5Valor = inten5Valor;
    }

    public String getInten5Lresp() {
        return inten5Lresp;
    }

    public void setInten5Lresp(String inten5Lresp) {
        this.inten5Lresp = inten5Lresp;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public String getPremioBrutoMoeda() {
        return premioBrutoMoeda;
    }

    public void setPremioBrutoMoeda(String premioBrutoMoeda) {
        this.premioBrutoMoeda = premioBrutoMoeda;
    }

    public String getPremioLiquidoMoeda() {
        return premioLiquidoMoeda;
    }

    public void setPremioLiquidoMoeda(String premioLiquidoMoeda) {
        this.premioLiquidoMoeda = premioLiquidoMoeda;
    }

    @Override
    public String toString() {
        return "Dinheiro{" + "numApolice=" + numApolice + ", numeroRegistro=" + numeroRegistro + ", nome_Fabricante=" + nome_Fabricante + ", numero_Fabricante=" + numero_Fabricante + ", tamanho=" + tamanho + ", moeda=" + moeda + ", sigla=" + sigla + ", peso=" + peso + ", detentor_Chave=" + detentor_Chave + ", construido_ou_Fixo=" + construido_ou_Fixo + ", estrutura=" + estrutura + ", inten1=" + inten1 + ", inten1Valor=" + inten1Valor + ", inten1Lresp=" + inten1Lresp + ", inten2=" + inten2 + ", inten2Valor=" + inten2Valor + ", inten2Lresp=" + inten2Lresp + ", inten3=" + inten3 + ", inten3Valor=" + inten3Valor + ", inten3Lresp=" + inten3Lresp + ", inten4=" + inten4 + ", inten4Valor=" + inten4Valor + ", inten4Lresp=" + inten4Lresp + ", inten5=" + inten5 + ", inten5Valor=" + inten5Valor + ", inten5Lresp=" + inten5Lresp + ", banco=" + banco + ", correio=" + correio + ", outros=" + outros + ", transporteDinheiro=" + transporteDinheiro + ", precupacao=" + precupacao + ", tempoPermanenciaBanco=" + tempoPermanenciaBanco + ", pagamento1item=" + pagamento1item + ", totalSegurado=" + totalSegurado + ", totalSeguradoFormatado=" + totalSeguradoFormatado + ", premioBruto=" + premioBruto + ", premioLiquido=" + premioLiquido + ", premioBrutoMoeda=" + premioBrutoMoeda + ", premioLiquidoMoeda=" + premioLiquidoMoeda + '}';
    }

   
}
