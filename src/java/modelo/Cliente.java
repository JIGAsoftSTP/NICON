
package modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Helio
 */
public class Cliente implements Serializable
{
    private String nomeS;
    private String quantidadeRegistro ="metade";
    private String nome;
    private String nif;
    private String contacto;
    private String endereco;
    private String apelido;
    private String nifS;
    private Date dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String morada;
    private String nacionalidade;
    private String profissao;
    private String localTrabalho;
    private String telefoneS;   
    private String telemovelS;
    private String emailS;
    private String nomeC;
    private String nifC;
    private String email;
    private String codigo;
    private String localizacao;
    private String site;
    private String alvara;
    private Date dataCriacao;
    private String razaoSocial;
    private String telefoneC;
    private String numLinhas;
    private String telemovelC;
    private String emailC;
    private String tipoCliente = "Singular";
    private String pais;
    private String função;
    private String responsavel;
    private String paisCliente;
    private String documento;
    private String numeroDocumento;
    private String type;
    private String tipoDocumento;
    private String numero;
    private Date minDate;
    private Date maxDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public String getNomeS() {
        return nomeS;
    }

    
    public void setNomeS(String nomeS) {
        this.nomeS = nomeS;
    }

    public String getNome() {
        return nome;
    }

    public String getQuantidadeRegistro() {
        return quantidadeRegistro;
    }

    public void setQuantidadeRegistro(String quantidadeRegistro) {
        this.quantidadeRegistro = quantidadeRegistro;
    }

    public String getFunção() {
        return função;
    }

    public void setFunção(String função) {
        this.função = função;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getPaisCliente() {
        return paisCliente;
    }

    public void setPaisCliente(String paisCliente) {
        this.paisCliente = paisCliente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public Date getMinDate() throws ParseException {
        String[] dd = sdf.format(new Date()).split("-");
        return minDate =(sdf.parse("01-01-"+(Integer.valueOf(dd[2])-190)));
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate = (new Date());
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNifS() {
        return nifS;
    }

    public void setNifS(String nifS) {
        this.nifS = nifS;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nomeS=" + nomeS + ", nome=" + nome + ", nif=" + nif + ", contacto=" + contacto + ", endereco=" + endereco + ", apelido=" + apelido + ", nifS=" + nifS + ", dataNascimento=" + dataNascimento + ", sexo=" + sexo + ", estadoCivil=" + estadoCivil + ", morada=" + morada + ", nacionalidade=" + nacionalidade + ", profissao=" + profissao + ", localTrabalho=" + localTrabalho + ", telefoneS=" + telefoneS + ", telemovelS=" + telemovelS + ", emailS=" + emailS + ", nomeC=" + nomeC + ", nifC=" + nifC + ", email=" + email + ", codigo=" + codigo + ", localizacao=" + localizacao + ", site=" + site + ", alvara=" + alvara + ", dataCriacao=" + dataCriacao + ", razaoSocial=" + razaoSocial + ", telefoneC=" + telefoneC + ", numLinhas=" + numLinhas + ", telemovelC=" + telemovelC + ", emailC=" + emailC + ", tipoCliente=" + tipoCliente + ", pais=" + pais + ", fun\u00e7\u00e3o=" + função + ", responsavel=" + responsavel + ", paisCliente=" + paisCliente + ", documento=" + documento + ", numeroDocumento=" + numeroDocumento + ", type=" + type + ", tipoDocumento=" + tipoDocumento + ", numero=" + numero + ", minDate=" + minDate + ", maxDate=" + maxDate + ", sdf=" + sdf + '}';
    }

 
  
 
    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public String getTelefoneS() {
        return telefoneS;
    }

    public void setTelefoneS(String telefoneS) {
        this.telefoneS = telefoneS;
    }

    public String getTelemovelS() {
        return telemovelS;
    }

    public void setTelemovelS(String telemovelS) {
        this.telemovelS = telemovelS;
    }

    public String getEmailS() {
        return emailS;
    }

    public void setEmailS(String emailS) {
        this.emailS = emailS;
    }

    public String getNomeC() {
        return nomeC;
    }

    public void setNomeC(String nomeC) {
        this.nomeC = nomeC;
    }

    public String getNifC() {
        return nifC;
    }

    public void setNifC(String nifC) {
        this.nifC = nifC;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAlvara() {
        return alvara;
    }

    public void setAlvara(String alvara) {
        this.alvara = alvara;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefoneC() {
        return telefoneC;
    }

    public void setTelefoneC(String telefoneC) {
        this.telefoneC = telefoneC;
    }

    public String getNumLinhas() {
        return numLinhas;
    }

    public void setNumLinhas(String numLinhas) {
        this.numLinhas = numLinhas;
    }

    public String getTelemovelC() {
        return telemovelC;
    }

    public void setTelemovelC(String telemovelC) {
        this.telemovelC = telemovelC;
    }

    public String getEmailC() {
        return emailC;
    }

    public void setEmailC(String emailC) {
        this.emailC = emailC;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
    
    
}
