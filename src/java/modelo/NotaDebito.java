package modelo;

/**
 *
 * @author Helcio Guadalupe
 */
public class NotaDebito
{
    private int idContrato;
    private String periodo;
    private String segurado;
    private String seguro;
    private String beneficiario;
    private String apolice;
    private String numRegistro;
    private String enderecoEntrega;
    private String descricao;
    private String premioGrosso;
    private String valorDeducao;
    private double totalPagar;


    public String getPeriodo() {
        return periodo;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getValorDeducao() {
        return valorDeducao;
    }

    public void setValorDeducao(String valorDeducao) {
        this.valorDeducao = valorDeducao;
    }

    public String getSegurado() {
        return segurado;
    }

    public void setSegurado(String segurado) {
        this.segurado = segurado;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getApolice() {
        return apolice;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(String numRegistro) {
        this.numRegistro = numRegistro;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPremioGrosso() {
        return premioGrosso;
    }

    public void setPremioGrosso(String premioGrosso) {
        this.premioGrosso = premioGrosso;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    @Override
    public String toString() {
        return "NotaDebito{" + "idContrato=" + idContrato + ", periodo=" + periodo + ", segurado=" + segurado + ", seguro=" + seguro + ", beneficiario=" + beneficiario + ", apolice=" + apolice + ", numRegistro=" + numRegistro + ", enderecoEntrega=" + enderecoEntrega + ", descricao=" + descricao + ", premioGrosso=" + premioGrosso + ", valorDeducao=" + valorDeducao + ", totalPagar=" + totalPagar + '}';
    }


    
}
