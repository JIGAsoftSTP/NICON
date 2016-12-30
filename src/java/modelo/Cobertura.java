
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helio
 */
public class Cobertura implements Serializable
{
    private int cobreId;
    private String cobreValor;
    private String cobrePremio;
    private String cobreTaxa;
    private String cobreDetalhes;
    private String cobrePremioFormatado;
    private boolean  selcao;
    private String [] coberturas;
    
    public Cobertura()
    {
    }
    public Cobertura (int id)
    {
        this.cobreId = id;
    }

    public String getCobreDetalhes() {
        return cobreDetalhes;
    }

    public void setCobreDetalhes(String cobreDetalhes) {
        this.cobreDetalhes = cobreDetalhes;
    }

    public int getCobreId() {
        return cobreId;
    }

    public String[] getCoberturas() {
        return coberturas;
    }

    public String getCobrePremioFormatado() {
        return cobrePremioFormatado;
    }

    public void setCobrePremioFormatado(String cobrePremioFormatado) {
        this.cobrePremioFormatado = cobrePremioFormatado;
    }

    public void setCoberturas(String[] coberturas) {
        this.coberturas = coberturas;
    }

    public void setCobreId(int cobreId) {
        this.cobreId = cobreId;
    }

    public String getCobreValor() {
        return cobreValor;
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
                    +this.cobrePremio+";"
                    +this.cobreDetalhes+""+";"
                    +this.selcao;
        }
    
}
