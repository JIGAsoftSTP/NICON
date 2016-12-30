
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Helio
 */
public class CargaMaritima 
{
    private String numerApolice;
    private String numeroRegistro;
    private String paisOrigem;
    private String cover;
    private String paisDestino;
    private String portoCarga;
    private String portoDescarga;
    private String nomeNavio;
    private String moeda;
    private String valorLimiteIndeminizacao;
    private String TaxaValorLimiteIndeminizacao;
    private String premio;
    private String proposito;
    private String interese;
    private String modoEmbalagem;
    private String qualquerNavio;
    private String qualquerMercadoria;
    private String anualParaCadaMercadoria;
    private String formaEnvio="42;Marítimo";
    private String tempoNegocio;
    private String cobertura="142;Todos os riscos";
    
    private String epecifiqueCompanhina;
    private String mecioneNomeCompanhina;
    private String decricaoMecadoria;
    private String areaComercail;
    private boolean mercadoriaCigaro = false;
    private boolean mercadoriaVinho = false;
    private boolean mercadoriaMetal = false;
    private boolean mercadoriaExplosivo = false;
    private boolean jaEfectou= false;
    private boolean descolacaoComercia;
    private boolean possibilidadeTrancar;
    
    private ArrayList<String> allMercadorias;
    
    private String deNumeroRegisto;
    private String deVeiculoComercialRegitrado;
    private String deValorCarregamentoAnual;
    private String deMarca;
    private String deValorMaximoCadaCarregamento;
    private String deValorMaximoVeiculo;
    
    private String custoPorto;
    private String conservacaoNoite="N";
    private String objetivoSeguro;
    private String descricaoMercadorias;
    private String valorPremioReal;

    private String premioBruto;
    private String premioLiquido;
    private String premioBrutoMoeda;
    private String premioLiquidoMoeda;
    private String totalSegurado;
    private String totalSeguradoMoeda;
    
    public CargaMaritima(String interese, String modoEmbalagem) 
    {
        this.interese = interese;
        this.modoEmbalagem = modoEmbalagem;
    }

    public CargaMaritima(boolean descolacaoComercia, boolean possibilidadeTrancar, String deNumeroRegisto, String deVeiculoComercialRegitrado, String deValorCarregamentoAnual, String deMarca, String deValorMaximoCadaCarregamento, String deValorMaximoVeiculo, String conservacaoNoite) {
        this.descolacaoComercia = descolacaoComercia;
        this.possibilidadeTrancar = possibilidadeTrancar;
        this.deNumeroRegisto = deNumeroRegisto;
        this.deVeiculoComercialRegitrado = deVeiculoComercialRegitrado;
        this.deValorCarregamentoAnual = deValorCarregamentoAnual;
        this.deMarca = deMarca;
        this.deValorMaximoCadaCarregamento = deValorMaximoCadaCarregamento;
        this.deValorMaximoVeiculo = deValorMaximoVeiculo;
        this.conservacaoNoite = conservacaoNoite;
    }
    
    public CargaMaritima(CargaMaritima cm) {
        this.descolacaoComercia = cm.descolacaoComercia;
        this.possibilidadeTrancar = cm.possibilidadeTrancar;
        this.deNumeroRegisto = cm.deNumeroRegisto;
        this.deVeiculoComercialRegitrado = cm.deVeiculoComercialRegitrado;
        this.deValorCarregamentoAnual = cm.deValorCarregamentoAnual;
        this.deMarca = cm.deMarca;
        this.deValorMaximoCadaCarregamento = cm.deValorMaximoCadaCarregamento;
        this.deValorMaximoVeiculo = cm.deValorMaximoVeiculo;
        this.conservacaoNoite = cm.conservacaoNoite;
    }
    
    public CargaMaritima()
    {
    }
    
    public String getPaisOrigem() {
        return paisOrigem;
    }

    public String getDescricaoMercadorias() {
        return descricaoMercadorias;
    }

    public void setDescricaoMercadorias(String descricaoMercadorias) {
        this.descricaoMercadorias = descricaoMercadorias;
    }


    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public String getPortoCarga() {
        return portoCarga;
    }

    public void setPortoCarga(String portoCarga) {
        this.portoCarga = portoCarga;
    }

    public String getPortoDescarga() {
        return portoDescarga;
    }

    public void setPortoDescarga(String portoDescarga) {
        this.portoDescarga = portoDescarga;
    }

    public String getNomeNavio() {
        return nomeNavio;
    }

    public void setNomeNavio(String nomeNavio) {
        this.nomeNavio = nomeNavio;
    }

    public String getTempoNegocio() {
        return tempoNegocio;
    }

    public void setTempoNegocio(String tempoNegocio) {
        this.tempoNegocio = tempoNegocio;
    }

    public String getCustoPorto() {
        return custoPorto;
    }

    public void setCustoPorto(String custoPorto) {
        this.custoPorto = custoPorto;
    }

    public String getObjetivoSeguro() {
        return objetivoSeguro;
    }

    public void setObjetivoSeguro(String objetivoSeguro) {
        this.objetivoSeguro = objetivoSeguro;
    }

    public String getInterese() {
        return interese;
    }

    public void setInterese(String interese) {
        this.interese = interese;
    }

    public String getModoEmbalagem() {
        return modoEmbalagem;
    }

    public void setModoEmbalagem(String modoEmbalagem) {
        this.modoEmbalagem = modoEmbalagem;
    }

    public String getNumerApolice() {
        return numerApolice;
    }

    public void setNumerApolice(String numerApolice) {
        this.numerApolice = numerApolice;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getValorLimiteIndeminizacao() {
        return valorLimiteIndeminizacao;
    }

    public void setValorLimiteIndeminizacao(String valorLimiteIndeminizacao) {
        this.valorLimiteIndeminizacao = valorLimiteIndeminizacao;
    }

    public String getTaxaValorLimiteIndeminizacao() {
        return TaxaValorLimiteIndeminizacao;
    }

    public void setTaxaValorLimiteIndeminizacao(String TaxaValorLimiteIndeminizacao) {
        this.TaxaValorLimiteIndeminizacao = TaxaValorLimiteIndeminizacao;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getQualquerNavio() {
        return qualquerNavio;
    }

    public void setQualquerNavio(String qualquerNavio) {
        this.qualquerNavio = qualquerNavio;
    }

    public String getQualquerMercadoria() {
        return qualquerMercadoria;
    }

    public void setQualquerMercadoria(String qualquerMercadoria) {
        this.qualquerMercadoria = qualquerMercadoria;
    }

    public String getAnualParaCadaMercadoria() {
        return anualParaCadaMercadoria;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public void setAnualParaCadaMercadoria(String anualParaCadaMercadoria) {
        this.anualParaCadaMercadoria = anualParaCadaMercadoria;
    }

    public String getFormaEnvio() {
        return formaEnvio;
    }

    public void setFormaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getEpecifiqueCompanhina() {
        return epecifiqueCompanhina;
    }

    public void setEpecifiqueCompanhina(String epecifiqueCompanhina) {
        this.epecifiqueCompanhina = epecifiqueCompanhina;
    }

    public String getDecricaoMecadoria() {
        return decricaoMecadoria;
    }

    public void setDecricaoMecadoria(String decricaoMecadoria) {
        this.decricaoMecadoria = decricaoMecadoria;
    }

    public String getAreaComercail() {
        return areaComercail;
    }

    public void setAreaComercail(String areaComercail) {
        this.areaComercail = areaComercail;
    }

    public boolean isMercadoriaCigaro() {
        return mercadoriaCigaro;
    }

    public void setMercadoriaCigaro(boolean mercadoriaCigaro) {
        this.mercadoriaCigaro = mercadoriaCigaro;
    }

    public boolean isMercadoriaVinho() {
        return mercadoriaVinho;
    }

    public void setMercadoriaVinho(boolean mercadoriaVinho) {
        this.mercadoriaVinho = mercadoriaVinho;
    }

    public boolean isMercadoriaMetal() {
        return mercadoriaMetal;
    }

    public void setMercadoriaMetal(boolean mercadoriaMetal) {
        this.mercadoriaMetal = mercadoriaMetal;
    }

    public boolean isMercadoriaExplosivo() {
        return mercadoriaExplosivo;
    }

    public void setMercadoriaExplosivo(boolean mercadoriaExplosivo) {
        this.mercadoriaExplosivo = mercadoriaExplosivo;
    }

    public boolean isDescolacaoComercia() {
        return descolacaoComercia;
    }

    public void setDescolacaoComercia(boolean descolacaoComercia) {
        this.descolacaoComercia = descolacaoComercia;
    }

    public boolean isPossibilidadeTrancar() {
        return possibilidadeTrancar;
    }

    public void setPossibilidadeTrancar(boolean possibilidadeTrancar) {
        this.possibilidadeTrancar = possibilidadeTrancar;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getMecioneNomeCompanhina() {
        return mecioneNomeCompanhina;
    }

    public void setMecioneNomeCompanhina(String mecioneNomeCompanhina) {
        this.mecioneNomeCompanhina = mecioneNomeCompanhina;
    }

    public String getDeNumeroRegisto() {
        return deNumeroRegisto;
    }

    public void setDeNumeroRegisto(String deNumeroRegisto) {
        this.deNumeroRegisto = deNumeroRegisto;
    }

    public String getDeVeiculoComercialRegitrado() {
        return deVeiculoComercialRegitrado;
    }

    public void setDeVeiculoComercialRegitrado(String deVeiculoComercialRegitrado) {
        this.deVeiculoComercialRegitrado = deVeiculoComercialRegitrado;
    }

    public String getDeValorCarregamentoAnual() {
        return deValorCarregamentoAnual;
    }

    public void setDeValorCarregamentoAnual(String deValorCarregamentoAnual) {
        this.deValorCarregamentoAnual = deValorCarregamentoAnual;
    }

    public String getDeMarca() {
        return deMarca;
    }

    public void setDeMarca(String deMarca) {
        this.deMarca = deMarca;
    }

    public String getDeValorMaximoCadaCarregamento() {
        return deValorMaximoCadaCarregamento;
    }

    public void setDeValorMaximoCadaCarregamento(String deValorMaximoCadaCarregamento) {
        this.deValorMaximoCadaCarregamento = deValorMaximoCadaCarregamento;
    }

    public String getDeValorMaximoVeiculo() {
        return deValorMaximoVeiculo;
    }

    public void setDeValorMaximoVeiculo(String deValorMaximoVeiculo) {
        this.deValorMaximoVeiculo = deValorMaximoVeiculo;
    }

    public String getConservacaoNoite() {
        return conservacaoNoite;
    }

    public void setConservacaoNoite(String conservacaoNoite) {
        this.conservacaoNoite = conservacaoNoite;
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

    public ArrayList<String> getAllMercadorias() {
        return (allMercadorias==null)? allMercadorias= new ArrayList<>():allMercadorias;
    }

    public void setAllMercadorias(ArrayList<String> allMercadorias) {
        this.allMercadorias = allMercadorias;
    }

    public String getTotalSegurado() {
        return totalSegurado;
    }

    public void setTotalSegurado(String totalSegurado) {
        this.totalSegurado = totalSegurado;
    }

    public String getTotalSeguradoMoeda() {
        return totalSeguradoMoeda;
    }

    public void setTotalSeguradoMoeda(String totalSeguradoMoeda) {
        this.totalSeguradoMoeda = totalSeguradoMoeda;
    }

    public String getValorPremioReal() {
        return valorPremioReal;
    }

    public void setValorPremioReal(String valorPremioReal) {
        this.valorPremioReal = valorPremioReal;
    }

    public boolean isJaEfectou() {
        return jaEfectou;
    }

    public void setJaEfectou(boolean jaEfectou) {
        this.jaEfectou = jaEfectou;
    }

    @Override
    public String toString() {
        return "CargaMaritima{" + "numerApolice=" + numerApolice + ", numeroRegistro=" + numeroRegistro + ", paisOrigem=" + paisOrigem + ", paisDestino=" + paisDestino + ", portoCarga=" + portoCarga + ", portoDescarga=" + portoDescarga + ", nomeNavio=" + nomeNavio + ", moeda=" + moeda + ", valorLimiteIndeminizacao=" + valorLimiteIndeminizacao + ", TaxaValorLimiteIndeminizacao=" + TaxaValorLimiteIndeminizacao + ", premio=" + premio + ", proposito=" + proposito + ", interese=" + interese + ", modoEmbalagem=" + modoEmbalagem + ", qualquerNavio=" + qualquerNavio + ", qualquerMercadoria=" + qualquerMercadoria + ", anualParaCadaMercadoria=" + anualParaCadaMercadoria + ", formaEnvio=" + formaEnvio + ", tempoNegocio=" + tempoNegocio + ", cobertura=" + cobertura + ", epecifiqueCompanhina=" + epecifiqueCompanhina + ", mecioneNomeCompanhina=" + mecioneNomeCompanhina + ", decricaoMecadoria=" + decricaoMecadoria + ", areaComercail=" + areaComercail + ", mercadoriaCigaro=" + mercadoriaCigaro + ", mercadoriaVinho=" + mercadoriaVinho + ", mercadoriaMetal=" + mercadoriaMetal + ", mercadoriaExplosivo=" + mercadoriaExplosivo + ", jaEfectou=" + jaEfectou + ", descolacaoComercia=" + descolacaoComercia + ", possibilidadeTrancar=" + possibilidadeTrancar + ", allMercadorias=" + allMercadorias + ", deNumeroRegisto=" + deNumeroRegisto + ", deVeiculoComercialRegitrado=" + deVeiculoComercialRegitrado + ", deValorCarregamentoAnual=" + deValorCarregamentoAnual + ", deMarca=" + deMarca + ", deValorMaximoCadaCarregamento=" + deValorMaximoCadaCarregamento + ", deValorMaximoVeiculo=" + deValorMaximoVeiculo + ", custoPorto=" + custoPorto + ", conservacaoNoite=" + conservacaoNoite + ", objetivoSeguro=" + objetivoSeguro + ", descricaoMercadorias=" + descricaoMercadorias + ", valorPremioReal=" + valorPremioReal + ", premioBruto=" + premioBruto + ", premioLiquido=" + premioLiquido + ", premioBrutoMoeda=" + premioBrutoMoeda + ", premioLiquidoMoeda=" + premioLiquidoMoeda + ", totalSegurado=" + totalSegurado + ", totalSeguradoMoeda=" + totalSeguradoMoeda + '}';
    }
    
}
