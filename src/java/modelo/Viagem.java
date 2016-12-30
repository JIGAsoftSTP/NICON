
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Helio
 */
public class Viagem implements Serializable
{
    private static long serialVersionUID = 1L;
    private String dataInicioFormatada;
    private String numApolice;
    private String numeroRegistro;
    private String objetivoViagem;
    private String paisDestino;
    private Date dataInicio;
    private Date dataFim;
    private String Moeda;
    private String nomePessoaSegurada;
    private String passaporte;
    private String dataFimFormatada;
    private String paisDestinoOther;
    private Date dataNasc;
    private String dataNascFormatada;
    private String dataNascimentoFormatoAnoMesDia;
    private String numDoc;
    private String tipoDoc;
    private String tipoDocOther;
    private Date dataEmissao;
    private String localEmissao;
    private String dataEmissaoFormatada;
    private String localEmissaoFormatada;
    private String localNascimento;
    private String localResidencia;
    private String outrasInformacoes;
    private String telefone;
    private String premioBruto;
    private String premioLiquido;
    private String premioBrutoMoeda;
    private String premioLiquidoMoeda;
    private String totalSegurado;
    private String totalSeguradoMoeda;
    private float nc;
    private String dias;
    private String sexo;
    private String sexoOther;
    private String endereco;
    private String impostoSelo;
    private String impostoConsumo;
    private String niconComissao;
    private String apoliceSegurado;
    private String zonaDestino;
    private String cidadeDestino;
    private String nacionalidade;
    private String nacionalidadeOther;
    private Date menorData;
    private Date maiorData;
    private int idTaxa;
    private int numDias;
    private boolean tipoViagem; // 1-viagem normal e 2- multi-viagem
    private float valorImpostoCincoPorCento;
    private float valorImpostoSeisPorCento;
    private float valorNC;
    private float valorFGA;
    private float valorPremio;
    private float sumNc;
    private float sumSubTotal;
    private float sumTotal;
    
    public Viagem()
    {        
    }
    
    public Viagem(Viagem v)
    {
        this.nomePessoaSegurada = v.getNomePessoaSegurada();
        this.idTaxa = v.getIdTaxa();
        this.tipoViagem = v.isTipoViagem();
        this.dataNasc = v.getDataNasc();
        this.numDias = v.getNumDias();
        this.numDoc = v.getNumDoc();
        this.tipoDoc = v.getTipoDoc();
        this.tipoDocOther = v.getTipoDocOther();
        this.paisDestinoOther = v.getPaisDestinoOther();
        this.nacionalidadeOther = v.getNacionalidadeOther();
        this.nacionalidade = v.getNacionalidade();
        this.localEmissao = v.getLocalEmissao();
        this.dataEmissao = v.getDataEmissao();
        this.localNascimento = v.getLocalNascimento();
        this.outrasInformacoes = v.getOutrasInformacoes();
        this.telefone = v.getTelefone();
        this.sexo = v.getSexo();
        this.endereco = v.getEndereco();
        this.tipoDoc = v.getTipoDoc();
        this.sexoOther = v.getSexoOther();
        this.objetivoViagem = v.getObjetivoViagem();
        this.paisDestino = v.getPaisDestino();
        this.dataInicio = v.getDataInicio();
        this.dataFim = v.getDataFim();
        this.cidadeDestino = v.getCidadeDestino();
        this.zonaDestino = v.getZonaDestino();
        this.numApolice = v.getNumApolice();
        this.dataEmissaoFormatada = v.getDataEmissaoFormatada();
        this.dataNascFormatada = v.getDataNascFormatada();
        this.dataInicioFormatada = v.getDataInicioFormatada();
        this.dataFimFormatada = v.getDataFimFormatada();
        this.nc = v.getNc();
        this.premioBruto = v.getPremioBruto();
        this.premioLiquido = v.getPremioLiquido();
        this.sumNc = v.getSumNc();
        this.sumSubTotal = v.getSumSubTotal();
        this.sumTotal = v.getSumTotal();
        
    }


    public String getTipoDocOther() {
        return tipoDocOther;
    }

    public String getImpostoSelo() {
        return impostoSelo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getZonaDestino() {
        return zonaDestino;
    }

    public int getIdTaxa() {
        return idTaxa;
    }

    public int getNumDias() {
        return numDias;
    }

    public void setNumDias(int numDias) {
        this.numDias = numDias;
    }

    public void setIdTaxa(int idTaxa) {
        this.idTaxa = idTaxa;
    }

    public void setZonaDestino(String zonaDestino) {
        this.zonaDestino = zonaDestino;
    }

    public String getPaisDestinoOther() {
        return paisDestinoOther;
    }

    public void setPaisDestinoOther(String paisDestinoOther) {
        this.paisDestinoOther = paisDestinoOther;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public String getApoliceSegurado() {
        return apoliceSegurado;
    }

    public void setApoliceSegurado(String apoliceSegurado) {
        this.apoliceSegurado = apoliceSegurado;
    }

    public void setImpostoSelo(String impostoSelo) {
        this.impostoSelo = impostoSelo;
    }

    public String getImpostoConsumo() {
        return impostoConsumo;
    }

    public String getNacionalidadeOther() {
        return nacionalidadeOther;
    }

    public void setNacionalidadeOther(String nacionalidadeOther) {
        this.nacionalidadeOther = nacionalidadeOther;
    }

    public void setImpostoConsumo(String impostoConsumo) {
        this.impostoConsumo = impostoConsumo;
    }

    public String getNiconComissao() {
        return niconComissao;
    }

    public void setNiconComissao(String niconComissao) {
        this.niconComissao = niconComissao;
    }

    public void setTipoDocOther(String tipoDocOther) {
        this.tipoDocOther = tipoDocOther;
    }

    public String getSexoOther() {
        return sexoOther;
    }

    public void setSexoOther(String sexoOther) {
        this.sexoOther = sexoOther;
    }
    
    public String getNumApolice() {
        return numApolice;
    }

    public void setNumApolice(String numApolice) {
        this.numApolice = numApolice;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getDataNascimentoFormatoAnoMesDia() {
        return dataNascimentoFormatoAnoMesDia;
    }

    public void setDataNascimentoFormatoAnoMesDia(String dataNascimentoFormatoAnoMesDia) {
        this.dataNascimentoFormatoAnoMesDia = dataNascimentoFormatoAnoMesDia;
    }

    public String getObjetivoViagem() {
        return objetivoViagem;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

  
    public String getPremioLiquidoMoeda() {
        return premioLiquidoMoeda;
    }

    public void setPremioLiquidoMoeda(String premioLiquidoMoeda) {
        this.premioLiquidoMoeda = premioLiquidoMoeda;
    }


    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setObjetivoViagem(String objetivoViagem) {
        this.objetivoViagem = objetivoViagem;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;  
    }

    public String getDataNascFormatada() {
        return dataNascFormatada;
    }

    public String getMoeda() {
        return Moeda;
    }

    public void setMoeda(String Moeda) {
        this.Moeda = Moeda;
    }

    public void setDataNascFormatada(String dataNascFormatada) {
        this.dataNascFormatada = dataNascFormatada;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public Date getDataInicio() {
        return dataInicio = ((dataInicio==null) ? dataInicio = new Date():dataInicio);
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        Viagem.serialVersionUID = serialVersionUID;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getLocalNascimento() {
        return localNascimento;
    }

    public void setLocalNascimento(String localNascimento) {
        this.localNascimento = localNascimento;
    }

    public String getLocalResidencia() {
        return localResidencia;
    }

    public void setLocalResidencia(String localResidencia) {
        this.localResidencia = localResidencia;
    }

    public String getOutrasInformacoes() {
        return outrasInformacoes;
    }

    public void setOutrasInformacoes(String outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }

    public String getLocalEmissao() {
        return localEmissao;
    }

    public boolean isTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(boolean tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public void setLocalEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
    }

    public String getDataEmissaoFormatada() {
        return dataEmissaoFormatada;
    }

    public void setDataEmissaoFormatada(String dataEmissaoFormatada) {
        this.dataEmissaoFormatada = dataEmissaoFormatada;
    }

    public String getLocalEmissaoFormatada() {
        return localEmissaoFormatada;
    }

    public void setLocalEmissaoFormatada(String localEmissaoFormatada) {
        this.localEmissaoFormatada = localEmissaoFormatada;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getNomePessoaSegurada() {
        return nomePessoaSegurada;
    }

    public void setNomePessoaSegurada(String nomePessoaSegurada) {
        this.nomePessoaSegurada = nomePessoaSegurada;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }


    public String getDataFimFormatada() {
        return dataFimFormatada;
    }

    public void setDataFimFormatada(String dataFimFormatada) {
        this.dataFimFormatada = dataFimFormatada;
    }

    public String getDataInicioFormatada() {
        return dataInicioFormatada;
    }

    public void setDataInicioFormatada(String dataInicioFormatada) {
        this.dataInicioFormatada = dataInicioFormatada;
    }

    public Date getMenorData() {
        return menorData;
    }

    public void setMenorData(Date menorData) {
        this.menorData = menorData;
    }

    public Date getMaiorData() {
        return maiorData;
    }

    public void setMaiorData(Date maiorData) {
        this.maiorData = maiorData;
    }

    public float getNc() {
        return nc;
    }

    public void setNc(float nc) {
        this.nc = nc;
    }

    public float getValorImpostoCincoPorCento() {
        return valorImpostoCincoPorCento;
    }

    public void setValorImpostoCincoPorCento(float valorImpostoCincoPorCento) {
        this.valorImpostoCincoPorCento = valorImpostoCincoPorCento;
    }

    public float getValorImpostoSeisPorCento() {
        return valorImpostoSeisPorCento;
    }

    public void setValorImpostoSeisPorCento(float valorImpostoSeisPorCento) {
        this.valorImpostoSeisPorCento = valorImpostoSeisPorCento;
    }

    public float getValorNC() {
        return valorNC;
    }

    public void setValorNC(float valorNC) {
        this.valorNC = valorNC;
    }

    public float getValorFGA() {
        return valorFGA;
    }

    public void setValorFGA(float valorFGA) {
        this.valorFGA = valorFGA;
    }

    public float getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(float valorPremio) {
        this.valorPremio = valorPremio;
    }

    public float getSumNc() {
        return sumNc;
    }

    public void setSumNc(float sumNc) {
        this.sumNc = sumNc;
    }

    public float getSumSubTotal() {
        return sumSubTotal;
    }

    public void setSumSubTotal(float sumSubTotal) {
        this.sumSubTotal = sumSubTotal;
    }

    public float getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(float sumTotal) {
        this.sumTotal = sumTotal;
    }
    
    
    
            
}
