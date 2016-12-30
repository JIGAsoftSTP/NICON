
package modelo;

/**
 *
 * @author Helio
 */
public class MaritimoCobertura 
{
    
    private int cobreId;
    private String cobreValor;
    private String cobrePremio;
    private String cobreTaxa;
    private String cobreDetalhes;
    private String cobrePremioFormatado;
    private boolean  selcao;
    
    public MaritimoCobertura()
    {
    }
    public MaritimoCobertura (int id)
    {
        this.cobreId = id;
    }

    public int getCobreId() {
        return cobreId;
    }

    public void setCobreId(int cobreId) {
        this.cobreId = cobreId;
    }

    public String getCobreValor() {
        return cobreValor;
    }

    public String getCobrePremioFormatado() {
        return cobrePremioFormatado;
    }

    public void setCobrePremioFormatado(String cobrePremioFormatado) {
        this.cobrePremioFormatado = cobrePremioFormatado;
    }

    public boolean isSelcao() {
        return selcao;
    }

    public void setSelcao(boolean selcao) {
        this.selcao = selcao;
    }

    public void setCobreValor(String cobreValor) {
        this.cobreValor = cobreValor;
    }

    public String getCobrePremio() {
        return cobrePremio;
    }

    public void setCobrePremio(String cobrePremio) {
        this.cobrePremio = cobrePremio;
    }

    public String getCobreTaxa() {
        return cobreTaxa;
    }

    public void setCobreTaxa(String cobreTaxa) {
        this.cobreTaxa = cobreTaxa;
    }
    
    @Override
        public String toString()
        {
            return this.cobreId+";"
                    +this.cobreValor+";"
                    +this.cobreTaxa+";"
                    +this.cobrePremio;

        }
    
    
}
