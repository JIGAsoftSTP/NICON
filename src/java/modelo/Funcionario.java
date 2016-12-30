
package modelo;

import java.io.Serializable;
import java.util.Date;
import org.primefaces.model.UploadedFile;

public class Funcionario implements Serializable
{
    public static String SESSION_NAME = "utilizador";
     private Object id;
     private String mensagem ;
     private String nomeAcesso;
     private String nomeCompleto;
     private String genero;
     private String nome;
     private String dataNasc;
     private String nif;
     private Date dataNascimento;
     private String dataEnt;
     private String banco;
     private String numConta;
     private String senha;
     private Date dataEntrada;
     private UploadedFile foto;
     private String residencia;
     private String categoria;
     private String numNivel;
     private String estadoCivil="0";
     private String movel;
     private String telefone;
     private String estado;
     private String nunFilhos;
     private String apelido;
     private String email;
     private String nivelAcesso;
     private String novaSenha;
     private String confirmarSenha;
     private String departamento;
     private String pwd;
     private String conjugueNome;
     private String nomeNivel;
     private String conjugueDataNasc;
     private String conjugueEstadoCivil;
     private String login;
     private String menuAdministrador;
     private String menuContabilidade;
     private String menuSinistro;
     private String menuRecHumanano;
     private String menuGeralDec;
     private String menuGestaoContrato;
     private boolean justRelatorio = false;
     private boolean operario;
     private String idDepartamento;
     private String codigoNicon;
     private boolean state = false;
     private int typeOperation = 1;
     private String nivel;
     private String menuAuditoria;
     private String menuConfiguracoes;
     
     
     public Funcionario()
     {
         
     }
    public Funcionario(Object id, String nomeCompleto, String nif) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.nif = nif;
    }

    public String getNivel() {
        return nivel;
    }

    public boolean isOperario() {
        return operario;
    }

    public void setOperario(boolean operario) {
        this.operario = operario;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
     
    public String getMensagem() 
    {
        return mensagem;
    }

    public String getNumNivel() {
        return numNivel;
    }

    public void setNumNivel(String numNivel) {
        this.numNivel = numNivel;
    }

    public String getMenuAuditoria() {
        return menuAuditoria;
    }

    public void setMenuAuditoria(String menuAuditoria) {
        this.menuAuditoria = menuAuditoria;
    }

    public String getMenuConfiguracoes() {
        return menuConfiguracoes;
    }

    public void setMenuConfiguracoes(String menuConfiguracoes) {
        this.menuConfiguracoes = menuConfiguracoes;
    }
    
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
      public Object getId() {
        return id;
    }

    public String getCodigoNicon() {
        return codigoNicon;
    }

    public int getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(int typeOperation) {
        this.typeOperation = typeOperation;
    }

    public void setCodigoNicon(String codigoNicon) {
        this.codigoNicon = codigoNicon;
    }
      

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMenuAdministrador() {
        return menuAdministrador;
    }

    public void setMenuAdministrador(String menuAdministrador) {
        this.menuAdministrador = menuAdministrador;
    }

    public String getConjugueNome() {
        return conjugueNome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setConjugueNome(String conjugueNome) {
        this.conjugueNome = conjugueNome;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNomeNivel() {
        return nomeNivel;
    }

    public void setNomeNivel(String nomeNivel) {
        this.nomeNivel = nomeNivel;
    }

    public String getConjugueDataNasc() {
        return conjugueDataNasc;
    }

    public void setConjugueDataNasc(String conjugueDataNasc) {
        this.conjugueDataNasc = conjugueDataNasc;
    }

    public String getConjugueEstadoCivil() {
        return conjugueEstadoCivil;
    }

    public void setConjugueEstadoCivil(String conjugueEstadoCivil) {
        this.conjugueEstadoCivil = conjugueEstadoCivil;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setId(Object id) {
        this.id = id;
    }
    public String getEstado() {
        return estado;
    }

    public String getDataEnt() {
        return dataEnt;
    }

    public void setDataEnt(String dataEnt) {
        this.dataEnt = dataEnt;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
     
    public String getNomeAcesso() {
        return nomeAcesso;
    }

    public void setNomeAcesso(String nomeAcesso) {
        this.nomeAcesso = nomeAcesso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

  

    public String getMovel() {
        return movel;
    }

    public void setMovel(String movel) {
        this.movel = movel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNunFilhos() {
        return nunFilhos;
    }

    public void setNunFilhos(String nunFilhos) {
        this.nunFilhos = nunFilhos;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getMenuContabilidade() {
        return menuContabilidade;
    }

    public void setMenuContabilidade(String menuContabilidade) {
        this.menuContabilidade = menuContabilidade;
    }

    public String getMenuGestaoContrato() {
        return menuGestaoContrato;
    }

    public void setMenuGestaoContrato(String menuGestaoContrato) {
        this.menuGestaoContrato = menuGestaoContrato;
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public boolean getJustRelatorio() {
        return justRelatorio;
    }

    public void setJustRelatorio(boolean justRelatorio) {
        this.justRelatorio = justRelatorio;
    }

    public String getMenuSinistro() {
        return menuSinistro;
    }

    public void setMenuSinistro(String menuSinistro) {
        this.menuSinistro = menuSinistro;
    }

    public String getMenuRecHumanano() {
        return menuRecHumanano;
    }

    public void setMenuRecHumanano(String menuRecHumanano) {
        this.menuRecHumanano = menuRecHumanano;
    }

    public String getMenuGeralDec() {
        return menuGeralDec;
    }

    public void setMenuGeralDec(String menuGeralDec) {
        this.menuGeralDec = menuGeralDec;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "id=" + id + ", mensagem=" + mensagem + ", nomeAcesso=" + nomeAcesso + ", nomeCompleto=" + nomeCompleto + ", genero=" + genero + ", nome=" + nome + ", dataNasc=" + dataNasc + ", nif=" + nif + ", dataNascimento=" + dataNascimento + ", dataEnt=" + dataEnt + ", banco=" + banco + ", numConta=" + numConta + ", senha=" + senha + ", dataEntrada=" + dataEntrada + ", foto=" + foto + ", residencia=" + residencia + ", categoria=" + categoria + ", estadoCivil=" + estadoCivil + ", movel=" + movel + ", telefone=" + telefone + ", estado=" + estado + ", nunFilhos=" + nunFilhos + ", apelido=" + apelido + ", email=" + email + ", nivelAcesso=" + nivelAcesso + ", novaSenha=" + novaSenha + ", confirmarSenha=" + confirmarSenha + ", departamento=" + departamento + ", pwd=" + pwd + ", conjugueNome=" + conjugueNome + ", nomeNivel=" + nomeNivel + ", conjugueDataNasc=" + conjugueDataNasc + ", conjugueEstadoCivil=" + conjugueEstadoCivil + ", login=" + login + ", menuAdministrador=" + menuAdministrador + ", menuContabilidade=" + menuContabilidade + ", menuSinistro=" + menuSinistro + ", menuRecHumanano=" + menuRecHumanano + ", menuGeralDec=" + menuGeralDec + ", menuGestaoContrato=" + menuGestaoContrato + ", justRelatorio=" + justRelatorio + ", idDepartamento=" + idDepartamento + ", codigoNicon=" + codigoNicon + ", state=" + state + ", typeOperation=" + typeOperation + '}';
    }
}
