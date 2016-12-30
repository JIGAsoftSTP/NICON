
package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Helio
 */
public class AcidentePG implements Serializable
{
    private String numeroApolice;
    private String numeroRegistro;
    private String nome;
    private String categoria;
    private String profissao;
    private Date dataNascimento;
    private String dataNascimentoFormatada;
    private String valorMorte;
    private String incapacidadeTotalTemporaria;
    private String despesaMedica;
    private String incapacidadeTotal;
    private String custoRepatriamento;
    private String descricao;
    private String defeitosFisicos="N";
    private String acidenteUltimosTresAnos="N";
    private String dataNascAnoMesDia;
    private String defeitosFisicosCampo;
    private String acidenteUltimos;
    private String valorCobertura;
    private String valorProposto;
    private String moeda;
    private String sigla;
    private String d;
    private String taxaMorte;
    private String taxaIncapacidadePermanente;
    private String taxaIncapacidadeTemporaria;
    private String taxaDespesaMedica;
    private String taxaCustoRepatriamento;
    private String premioMorte;
    private String premioIncapacidadePermanente;
    private String premioIncapacidadeTemporaria;
    private String premioDespesaMedica;
    private String premioCustoRepatriamento;
    private String premioMorteMoeda;
    private String premioIncapacidadePermanenteMoeda;
    private String premioIncapacidadeTemporariaMoeda;
    private String premioDespesaMedicaMoeda;
    private String premioCustoRepatriamentoMoeda;
    private String totalPremioMoeda;
    private String totalMorte;
    private String totalDespesa;
    private String totalCusto;
    private String totalIncapTemp;
    private String totalIncaTotal;
    private String tipoCobertura = "2";
    private String limiteResponsabilidade;
    private String premioLiquido;
    private String premioLiquidoMoeda;
    private String totalSeguradoMoeda;
    private String premioBruto;
    private String premioBrutoMoeda;
    private String nc;
    private String impostoCinco = "0.05";
    private String impostoSeis = "0.006";
    private String impostoFGA;
    private String acidente;
    private String defeito;
    
    
    private SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
    

    public AcidentePG()
    {
        
    }



    public AcidentePG(String premioMorte, String premioIncapacidadePermanente, String premioIncapacidadeTemporaria, String premioDespesaMedica, String premioCustoRepatriamento) {
        this.premioMorte = premioMorte;
        this.premioIncapacidadePermanente = premioIncapacidadePermanente;
        this.premioIncapacidadeTemporaria = premioIncapacidadeTemporaria;
        this.premioDespesaMedica = premioDespesaMedica;
        this.premioCustoRepatriamento = premioCustoRepatriamento; 
    }

    public AcidentePG(String nome, String categoria, String profissao, String dataNascimentoFormatada,
            String valorMorte, String incapacidadeTotalTemporaria, String despesaMedica,
            String incapacidadeTotal, String custoRepatriamento, 
            String defeitosFisicosCampo, String acidenteUltimos, Date dataNasc,
            String taxaMorte, String taxaDespesaMedica, String taxaIncapTemp,
            String taxaIncapP, String taxaCustoR) {
        this.nome = nome;
        this.categoria = categoria;
        this.profissao = profissao;
        this.dataNascimento = dataNascimento;
        this.dataNascimentoFormatada = dataNascimentoFormatada;
        this.valorMorte = valorMorte;
        this.incapacidadeTotalTemporaria = incapacidadeTotalTemporaria;
        this.despesaMedica = despesaMedica;
        this.incapacidadeTotal = incapacidadeTotal;
        this.custoRepatriamento = custoRepatriamento;
        this.defeitosFisicosCampo = defeitosFisicosCampo;
        this.acidenteUltimos = acidenteUltimos;
        this.dataNascimento = dataNasc;
        this.taxaMorte = taxaMorte;
        this.taxaDespesaMedica = taxaDespesaMedica;
        this.taxaIncapacidadeTemporaria = taxaIncapTemp;
        this.taxaIncapacidadePermanente = taxaIncapP;
        this.taxaCustoRepatriamento = taxaCustoR;
        
        
    }

    
    
    public String getLimiteResponsabilidade() {
        return limiteResponsabilidade;
    }

    public String getPremioLiquido() {
        return premioLiquido;
    }

    public void setPremioLiquido(String premioLiquido) {
        this.premioLiquido = premioLiquido;
    }

    public String getPremioLiquidoMoeda() {
        return premioLiquidoMoeda;
    }

    public void setPremioLiquidoMoeda(String premioLiquidoMoeda) {
        this.premioLiquidoMoeda = premioLiquidoMoeda;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getTotalSeguradoMoeda() {
        return totalSeguradoMoeda;
    }

    public void setTotalSeguradoMoeda(String totalSeguradoMoeda) {
        this.totalSeguradoMoeda = totalSeguradoMoeda;
    }

    public String getPremioBruto() {
        return premioBruto;
    }

    public void setPremioBruto(String premioBruto) {
        this.premioBruto = premioBruto;
    }

    public String getPremioBrutoMoeda() {
        return premioBrutoMoeda;
    }

    public void setPremioBrutoMoeda(String premioBrutoMoeda) {
        this.premioBrutoMoeda = premioBrutoMoeda;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getImpostoCinco() {
        return impostoCinco;
    }

    public void setImpostoCinco(String impostoCinco) {
        this.impostoCinco = impostoCinco;
    }

    public String getImpostoSeis() {
        return impostoSeis;
    }

    public void setImpostoSeis(String impostoSeis) {
        this.impostoSeis = impostoSeis;
    }

    public String getImpostoFGA() {
        return impostoFGA;
    }

    public void setImpostoFGA(String impostoFGA) {
        this.impostoFGA = impostoFGA;
    }

    public void setLimiteResponsabilidade(String limiteResponsabilidade) {
        this.limiteResponsabilidade = limiteResponsabilidade;
    }

    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public String getPremioMorteMoeda() {
        return premioMorteMoeda;
    }

    public void setPremioMorteMoeda(String premioMorteMoeda) {
        this.premioMorteMoeda = premioMorteMoeda;
    }

    public String getPremioIncapacidadePermanenteMoeda() {
        return premioIncapacidadePermanenteMoeda;
    }

    public void setPremioIncapacidadePermanenteMoeda(String premioIncapacidadePermanenteMoeda) {
        this.premioIncapacidadePermanenteMoeda = premioIncapacidadePermanenteMoeda;
    }

    public String getPremioIncapacidadeTemporariaMoeda() {
        return premioIncapacidadeTemporariaMoeda;
    }

    public void setPremioIncapacidadeTemporariaMoeda(String premioIncapacidadeTemporariaMoeda) {
        this.premioIncapacidadeTemporariaMoeda = premioIncapacidadeTemporariaMoeda;
    }

    public String getPremioDespesaMedicaMoeda() {
        return premioDespesaMedicaMoeda;
    }

    public void setPremioDespesaMedicaMoeda(String premioDespesaMedicaMoeda) {
        this.premioDespesaMedicaMoeda = premioDespesaMedicaMoeda;
    }

    public String getPremioCustoRepatriamentoMoeda() {
        return premioCustoRepatriamentoMoeda;
    }

    public void setPremioCustoRepatriamentoMoeda(String premioCustoRepatriamentoMoeda) {
        this.premioCustoRepatriamentoMoeda = premioCustoRepatriamentoMoeda;
    }

    public String getTotalPremioMoeda() {
        return totalPremioMoeda;
    }

    public void setTotalPremioMoeda(String totalPremioMoeda) {
        this.totalPremioMoeda = totalPremioMoeda;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public String getTotalMorte() {
        return totalMorte;
    }

    public void setTotalMorte(String totalMorte) {
        this.totalMorte = totalMorte;
    }

    public String getTotalDespesa() {
        return totalDespesa;
    }

    public void setTotalDespesa(String totalDespesa) {
        this.totalDespesa = totalDespesa;
    }

    public String getTotalCusto() {
        return totalCusto;
    }

    public void setTotalCusto(String totalCusto) {
        this.totalCusto = totalCusto;
    }

    public String getTotalIncapTemp() {
        return totalIncapTemp;
    }

    public void setTotalIncapTemp(String totalIncapTemp) {
        this.totalIncapTemp = totalIncapTemp;
    }

    public String getTotalIncaTotal() {
        return totalIncaTotal;
    }

    public void setTotalIncaTotal(String totalIncaTotal) {
        this.totalIncaTotal = totalIncaTotal;
    }


    public String getValorCobertura() {
        return valorCobertura;
    }

    public String getD() {
        return d;
    }

    public String getPremioMorte() {
        return premioMorte;
    }

    public void setPremioMorte(String premioMorte) {
        this.premioMorte = premioMorte;
    }

    public String getPremioIncapacidadePermanente() {
        return premioIncapacidadePermanente;
    }

    public void setPremioIncapacidadePermanente(String premioIncapacidadePermanente) {
        this.premioIncapacidadePermanente = premioIncapacidadePermanente;
    }

    public String getPremioIncapacidadeTemporaria() {
        return premioIncapacidadeTemporaria;
    }

    public void setPremioIncapacidadeTemporaria(String premioIncapacidadeTemporaria) {
        this.premioIncapacidadeTemporaria = premioIncapacidadeTemporaria;
    }

    public String getPremioDespesaMedica() {
        return premioDespesaMedica;
    }

    public void setPremioDespesaMedica(String premioDespesaMedica) {
        this.premioDespesaMedica = premioDespesaMedica;
    }

    public String etPremioCustoRepatriamento() {
        return premioCustoRepatriamento;
    }

    public void setPremioCustoRepatriamento(String premioCustoRepatriamento) {
        this.premioCustoRepatriamento = premioCustoRepatriamento;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getTaxaMorte() {
        return taxaMorte;
    }

    public void setTaxaMorte(String taxaMorte) {
        this.taxaMorte = taxaMorte;
    }

    public String getTaxaIncapacidadePermanente() {
        return taxaIncapacidadePermanente;
    }

    public void setTaxaIncapacidadePermanente(String taxaIncapacidadePermanente) {
        this.taxaIncapacidadePermanente = taxaIncapacidadePermanente;
    }

    public String getTaxaIncapacidadeTemporaria() {
        return taxaIncapacidadeTemporaria;
    }

    public void setTaxaIncapacidadeTemporaria(String taxaIncapacidadeTemporaria) {
        this.taxaIncapacidadeTemporaria = taxaIncapacidadeTemporaria;
    }

    public String getTaxaDespesaMedica() {
        return taxaDespesaMedica;
    }

    public void setTaxaDespesaMedica(String taxaDespesaMedica) {
        this.taxaDespesaMedica = taxaDespesaMedica;
    }

    public String getTaxaCustoRepatriamento() {
        return taxaCustoRepatriamento;
    }

    public void setTaxaCustoRepatriamento(String taxaCustoRepatriamento) {
        this.taxaCustoRepatriamento = taxaCustoRepatriamento;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public SimpleDateFormat getSdf1() {
        return sdf1;
    }

    public String getPremioCustoRepatriamento() {
        return premioCustoRepatriamento;
    }

    public void setSdf1(SimpleDateFormat sdf1) {
        this.sdf1 = sdf1;
    }

    public void setValorCobertura(String valorCobertura) {
        this.valorCobertura = valorCobertura;
    }

    public String getValorProposto() {
        return valorProposto;
    }

    public void setValorProposto(String valorProposto) {
        this.valorProposto = valorProposto;
    }
    
    public String getNumeroApolice() {
        return numeroApolice;
    }

    public String getDefeitosFisicos() {
        return defeitosFisicos;
    }

    public String getDefeitosFisicosCampo() {
        return defeitosFisicosCampo;
    }

    public String getAcidenteUltimos() {
        return acidenteUltimos;
    }

    public void setAcidenteUltimos(String acidenteUltimos) {
        this.acidenteUltimos = acidenteUltimos;
        this.acidenteUltimos = (acidenteUltimosTresAnos==null) ? acidente= null : acidenteUltimos;
    }

    public void setDefeitosFisicosCampo(String defeitosFisicosCampo) {
        this.defeitosFisicosCampo = defeitosFisicosCampo;
        System.out.println(this.defeitosFisicosCampo);
        this.defeitosFisicosCampo = (defeitosFisicos==null) ? null : defeitosFisicosCampo;
    }

    public String getDataNascAnoMesDia() {
        return dataNascAnoMesDia;
    }

    public void setDataNascAnoMesDia(String dataNascAnoMesDia) {
        this.dataNascAnoMesDia = dataNascAnoMesDia;
        
    }

    public void setDefeitosFisicos(String defeitosFisicos) {
        this.defeitosFisicos = defeitosFisicos;
        this.defeitosFisicos = (defeitosFisicos.equals("N")) ? null : "ativa";
    }

    public String getAcidenteUltimosTresAnos() {
        return acidenteUltimosTresAnos;
    }

    public void setAcidenteUltimosTresAnos(String acidenteUltimosTresAnos) {
        this.acidenteUltimosTresAnos = acidenteUltimosTresAnos;
        this.acidenteUltimosTresAnos = (acidenteUltimosTresAnos.equals("N")) ? null : "ativa";
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "AcidentePG{" + "numeroApolice=" + numeroApolice + ", numeroRegistro=" + numeroRegistro + ", nome=" + nome + ", categoria=" + categoria + ", profissao=" + profissao + ", dataNascimento=" + dataNascimento + ", dataNascimentoFormatada=" + dataNascimentoFormatada + ", valorMorte=" + valorMorte + ", incapacidadeTotalTemporaria=" + incapacidadeTotalTemporaria + ", despesaMedica=" + despesaMedica + ", incapacidadeTotal=" + incapacidadeTotal + ", custoRepatriamento=" + custoRepatriamento + ", descricao=" + descricao + ", defeitosFisicos=" + defeitosFisicos + ", acidenteUltimosTresAnos=" + acidenteUltimosTresAnos + ", dataNascAnoMesDia=" + dataNascAnoMesDia + ", defeitosFisicosCampo=" + defeitosFisicosCampo + ", acidenteUltimos=" + acidenteUltimos + ", valorCobertura=" + valorCobertura + ", valorProposto=" + valorProposto + ", moeda=" + moeda + ", sigla=" + sigla + ", d=" + d + ", taxaMorte=" + taxaMorte + ", taxaIncapacidadePermanente=" + taxaIncapacidadePermanente + ", taxaIncapacidadeTemporaria=" + taxaIncapacidadeTemporaria + ", taxaDespesaMedica=" + taxaDespesaMedica + ", taxaCustoRepatriamento=" + taxaCustoRepatriamento + ", premioMorte=" + premioMorte + ", premioIncapacidadePermanente=" + premioIncapacidadePermanente + ", premioIncapacidadeTemporaria=" + premioIncapacidadeTemporaria + ", premioDespesaMedica=" + premioDespesaMedica + ", premioCustoRepatriamento=" + premioCustoRepatriamento + ", premioMorteMoeda=" + premioMorteMoeda + ", premioIncapacidadePermanenteMoeda=" + premioIncapacidadePermanenteMoeda + ", premioIncapacidadeTemporariaMoeda=" + premioIncapacidadeTemporariaMoeda + ", premioDespesaMedicaMoeda=" + premioDespesaMedicaMoeda + ", premioCustoRepatriamentoMoeda=" + premioCustoRepatriamentoMoeda + ", totalPremioMoeda=" + totalPremioMoeda + ", totalMorte=" + totalMorte + ", totalDespesa=" + totalDespesa + ", totalCusto=" + totalCusto + ", totalIncapTemp=" + totalIncapTemp + ", totalIncaTotal=" + totalIncaTotal + ", tipoCobertura=" + tipoCobertura + ", limiteResponsabilidade=" + limiteResponsabilidade + ", premioLiquido=" + premioLiquido + ", premioLiquidoMoeda=" + premioLiquidoMoeda + ", totalSeguradoMoeda=" + totalSeguradoMoeda + ", premioBruto=" + premioBruto + ", premioBrutoMoeda=" + premioBrutoMoeda + ", nc=" + nc + ", impostoCinco=" + impostoCinco + ", impostoSeis=" + impostoSeis + ", impostoFGA=" + impostoFGA + ", acidente=" + acidente + ", defeito=" + defeito + ", sdf=" + sdf + ", sdf1=" + sdf1 + '}';
    }

  
    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
        this.dataNascAnoMesDia = (dataNascimento == null) ? null : sdf1.format(dataNascimento);
        this.dataNascimentoFormatada = (dataNascimento == null) ? null : sdf.format(dataNascimento);
    }

 
  
    public String getDataNascimentoFormatada() {
        return dataNascimentoFormatada;
    }

    public void setDataNascimentoFormatada(String dataNascimentoFormatada) {
        this.dataNascimentoFormatada = dataNascimentoFormatada;
    }

    public String getValorMorte() {
        return valorMorte;
    }

    public void setValorMorte(String valorMorte) {
        this.valorMorte = valorMorte;
    }

    public String getIncapacidadeTotalTemporaria() {
        return incapacidadeTotalTemporaria;
    }

    public void setIncapacidadeTotalTemporaria(String incapacidadeTotalTemporaria) {
        this.incapacidadeTotalTemporaria = incapacidadeTotalTemporaria;
    }

    public String getDespesaMedica() {
        return despesaMedica;
    }

    public void setDespesaMedica(String despesaMedica) {
        this.despesaMedica = despesaMedica;
    }

    public String getIncapacidadeTotal() {
        return incapacidadeTotal;
    }

    public void setIncapacidadeTotal(String incapacidadeTotal) {
        this.incapacidadeTotal = incapacidadeTotal;
    }

    public String getCustoRepatriamento() {
        return custoRepatriamento;
    }

    public void setCustoRepatriamento(String custoRepatriamento) {
        this.custoRepatriamento = custoRepatriamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
}
