
package modelo;

public class EmpresaParceiramodelo 
{
    private String nome;
    private String endereco;
    private String pais;
    private String capitalSocial;
    private int idEmpresas;

    public EmpresaParceiramodelo()
        {

        }

    public int getIdEmpresas() 
    {
        return idEmpresas;
    }

    public void setIdEmpresas(int idEmpresas) 
    {
        this.idEmpresas = idEmpresas;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    @Override
    public String toString() {
        String saida = "-" +nome +" - " +endereco+ " - "+ pais+ " - "+capitalSocial+ "-"+idEmpresas;
        return saida;
    }

    public EmpresaParceiramodelo(String nome, String endereco, String pais, String capitalSocial, int idEmpresas) 
        {
            this.nome = nome;
            this.endereco = endereco;
            this.pais = pais;
            this.capitalSocial = capitalSocial;
            this.idEmpresas = idEmpresas;
        }
}
