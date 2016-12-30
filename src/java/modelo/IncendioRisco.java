
package modelo;

import java.io.Serializable;

/**
 *
 * @author Helio
 */
public class IncendioRisco implements Serializable
{
    private String riscoAviaoId = "false";
    private String aviaoValor;
    private String aviaoTaxa;
    private String aviaoPremio;
    private String aviaoPremioMoeda;
    
    private String colisaoId = "false";
    private String colisaoValor;
    private String colisaoTaxa;
    private String colisaoPremio;
    private String colisaoPremioMoeda;
    
    private String explosaoId = "false";
    private String explosaoValor;
    private String explosaoTaxa;
    private String explosaoPremio;
    private String explosaoPremioMoeda;
    
    private String terramotoId = "false";
    private String terramotoValor;
    private String terramotoTaxa;
    private String terramotoPremio;
    private String terramotoPremioMoeda;
    
    private String tempestadeId = "false";
    private String tempestadeValor;
    private String tempestadeTaxa;
    private String tempestadePremio;
    private String tempestadePremioMoeda;
    
    private String afundamentoId = "false";
    private String afundamentoValor;
    private String afundamentoTaxa;
    private String afundamentoPremio;
    private String afundamentoPremioMoeda;
    
    private String riscoPoliticoId = "false";
    private String riscoPoliticoValor;
    private String riscoPoliticoTaxa;
    private String riscoPoliticoPremio;
    private String riscoPoliticoPremioMoeda;
    
    private String danoId = "false";
    private String danoValor;
    private String danoTaxa;
    private String danoPremio;
    private String danoPremioMoeda;
    
    private String tumultoId = "false";
    private String tumultoValor;
    private String tumultoTaxa;
    private String tumultoPremio;
    private String tumultoPremioMoeda;
    
    private String incendioFId = "false";
    private String incendioFValor;
    private String incendioFTaxa;
    private String incendioFPremio;
    private String incendioFPremioMoeda;
    
    private String explosaoExId = "false";
    private String explosaoExValor;
    private String explosaoExTaxa;
    private String explosaoExPremio;
    private String explosaoExPremioMoeda;
    
    private String roturaId = "false";
    private String roturaValor;
    private String roturaTaxa;
    private String roturaPremio;
    private String roturaPremioMoeda;
    private String cover;

    public String getRiscoAviaoId() {
        return riscoAviaoId;
    }

    public void setRiscoAviaoId(String riscoAviaoId) {
        this.riscoAviaoId = riscoAviaoId;
    }

    public String getAviaoValor() {
        return aviaoValor;
    }

    public void setAviaoValor(String aviaoValor) {
        this.aviaoValor = aviaoValor;
    }

    public String getAviaoTaxa() {
        return aviaoTaxa;
    }

    public void setAviaoTaxa(String aviaoTaxa) {
        this.aviaoTaxa = aviaoTaxa;
    }

    public String getAviaoPremio() {
        return aviaoPremio;
    }
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public void setAviaoPremio(String aviaoPremio) {
        this.aviaoPremio = aviaoPremio;
    }

    public String getColisaoId() {
        return colisaoId;
    }

    public void setColisaoId(String colisaoId) {
        this.colisaoId = colisaoId;
    }

    public String getColisaoValor() {
        return colisaoValor;
    }

    public void setColisaoValor(String colisaoValor) {
        this.colisaoValor = colisaoValor;
    }

    public String getColisaoTaxa() {
        return colisaoTaxa;
    }

    public void setColisaoTaxa(String colisaoTaxa) {
        this.colisaoTaxa = colisaoTaxa;
    }

    public String getColisaoPremio() {
        return colisaoPremio;
    }

    public void setColisaoPremio(String colisaoPremio) {
        this.colisaoPremio = colisaoPremio;
    }

    public String getExplosaoId() {
        return explosaoId;
    }

    public void setExplosaoId(String explosaoId) {
        this.explosaoId = explosaoId;
    }

    public String getExplosaoValor() {
        return explosaoValor;
    }

    public void setExplosaoValor(String explosaoValor) {
        this.explosaoValor = explosaoValor;
    }

    public String getExplosaoTaxa() {
        return explosaoTaxa;
    }

    public void setExplosaoTaxa(String explosaoTaxa) {
        this.explosaoTaxa = explosaoTaxa;
    }

    public String getExplosaoPremio() {
        return explosaoPremio;
    }

    public void setExplosaoPremio(String explosaoPremio) {
        this.explosaoPremio = explosaoPremio;
    }

    public String getTerramotoId() {
        return terramotoId;
    }

    public void setTerramotoId(String terramotoId) {
        this.terramotoId = terramotoId;
    }

    public String getTerramotoValor() {
        return terramotoValor;
    }

    public void setTerramotoValor(String terramotoValor) {
        this.terramotoValor = terramotoValor;
    }

    public String getTerramotoTaxa() {
        return terramotoTaxa;
    }

    public void setTerramotoTaxa(String terramotoTaxa) {
        this.terramotoTaxa = terramotoTaxa;
    }

    public String getTerramotoPremio() {
        return terramotoPremio;
    }

    public void setTerramotoPremio(String terramotoPremio) {
        this.terramotoPremio = terramotoPremio;
    }

    public String getTempestadeId() {
        return tempestadeId;
    }

    public void setTempestadeId(String tempestadeId) {
        this.tempestadeId = tempestadeId;
    }

    public String getTempestadeValor() {
        return tempestadeValor;
    }

    public void setTempestadeValor(String tempestadeValor) {
        this.tempestadeValor = tempestadeValor;
    }

    public String getTempestadeTaxa() {
        return tempestadeTaxa;
    }

    public void setTempestadeTaxa(String tempestadeTaxa) {
        this.tempestadeTaxa = tempestadeTaxa;
    }

    public String getTempestadePremio() {
        return tempestadePremio;
    }

    public void setTempestadePremio(String tempestadePremio) {
        this.tempestadePremio = tempestadePremio;
    }

    public String getAfundamentoId() {
        return afundamentoId;
    }

    public void setAfundamentoId(String afundamentoId) {
        this.afundamentoId = afundamentoId;
    }

    public String getAfundamentoValor() {
        return afundamentoValor;
    }

    public void setAfundamentoValor(String afundamentoValor) {
        this.afundamentoValor = afundamentoValor;
    }

    public String getAfundamentoTaxa() {
        return afundamentoTaxa;
    }

    public void setAfundamentoTaxa(String afundamentoTaxa) {
        this.afundamentoTaxa = afundamentoTaxa;
    }

    public String getAfundamentoPremio() {
        return afundamentoPremio;
    }

    public void setAfundamentoPremio(String afundamentoPremio) {
        this.afundamentoPremio = afundamentoPremio;
    }

    public String getRiscoPoliticoId() {
        return riscoPoliticoId;
    }

    public void setRiscoPoliticoId(String riscoPoliticoId) {
        this.riscoPoliticoId = riscoPoliticoId;
    }

    public String getRiscoPoliticoValor() {
        return riscoPoliticoValor;
    }

    public void setRiscoPoliticoValor(String riscoPoliticoValor) {
        this.riscoPoliticoValor = riscoPoliticoValor;
    }

    public String getRiscoPoliticoTaxa() {
        return riscoPoliticoTaxa;
    }

    public void setRiscoPoliticoTaxa(String riscoPoliticoTaxa) {
        this.riscoPoliticoTaxa = riscoPoliticoTaxa;
    }

    public String getRiscoPoliticoPremio() {
        return riscoPoliticoPremio;
    }

    public void setRiscoPoliticoPremio(String riscoPoliticoPremio) {
        this.riscoPoliticoPremio = riscoPoliticoPremio;
    }

    public String getDanoId() {
        return danoId;
    }

    public void setDanoId(String danoId) {
        this.danoId = danoId;
    }

    public String getDanoValor() {
        return danoValor;
    }

    public void setDanoValor(String danoValor) {
        this.danoValor = danoValor;
    }

    public String getDanoTaxa() {
        return danoTaxa;
    }

    public void setDanoTaxa(String danoTaxa) {
        this.danoTaxa = danoTaxa;
    }

    public String getDanoPremio() {
        return danoPremio;
    }

    public void setDanoPremio(String danoPremio) {
        this.danoPremio = danoPremio;
    }

    public String getTumultoId() {
        return tumultoId;
    }

    public void setTumultoId(String tumultoId) {
        this.tumultoId = tumultoId;
    }

    public String getTumultoValor() {
        return tumultoValor;
    }

    public void setTumultoValor(String tumultoValor) {
        this.tumultoValor = tumultoValor;
    }

    public String getTumultoTaxa() {
        return tumultoTaxa;
    }

    public void setTumultoTaxa(String tumultoTaxa) {
        this.tumultoTaxa = tumultoTaxa;
    }

    public String getTumultoPremio() {
        return tumultoPremio;
    }

    public void setTumultoPremio(String tumultoPremio) {
        this.tumultoPremio = tumultoPremio;
    }

    public String getIncendioFId() {
        return incendioFId;
    }

    public void setIncendioFId(String incendioFId) {
        this.incendioFId = incendioFId;
    }

    public String getIncendioFValor() {
        return incendioFValor;
    }

    public void setIncendioFValor(String incendioFValor) {
        this.incendioFValor = incendioFValor;
    }

    public String getIncendioFTaxa() {
        return incendioFTaxa;
    }

    public void setIncendioFTaxa(String incendioFTaxa) {
        this.incendioFTaxa = incendioFTaxa;
    }

    public String getIncendioFPremio() {
        return incendioFPremio;
    }

    public void setIncendioFPremio(String incendioFPremio) {
        this.incendioFPremio = incendioFPremio;
    }

    public String getExplosaoExId() {
        return explosaoExId;
    }

    public void setExplosaoExId(String explosaoExId) {
        this.explosaoExId = explosaoExId;
    }

    public String getExplosaoExValor() {
        return explosaoExValor;
    }

    public void setExplosaoExValor(String explosaoExValor) {
        this.explosaoExValor = explosaoExValor;
    }

    public String getExplosaoExTaxa() {
        return explosaoExTaxa;
    }

    public void setExplosaoExTaxa(String explosaoExTaxa) {
        this.explosaoExTaxa = explosaoExTaxa;
    }

    public String getExplosaoExPremio() {
        return explosaoExPremio;
    }

    public void setExplosaoExPremio(String explosaoExPremio) {
        this.explosaoExPremio = explosaoExPremio;
    }

    public String getRoturaId() {
        return roturaId;
    }

    public void setRoturaId(String roturaId) {
        this.roturaId = roturaId;
    }

    public String getRoturaValor() {
        return roturaValor;
    }

    public void setRoturaValor(String roturaValor) {
        this.roturaValor = roturaValor;
    }

    public String getRoturaTaxa() {
        return roturaTaxa;
    }

    public void setRoturaTaxa(String roturaTaxa) {
        this.roturaTaxa = roturaTaxa;
    }

    public String getRoturaPremio() {
        return roturaPremio;
    }

    public void setRoturaPremio(String roturaPremio) {
        this.roturaPremio = roturaPremio;
    }

    public String getAviaoPremioMoeda() {
        return aviaoPremioMoeda;
    }

    public void setAviaoPremioMoeda(String aviaoPremioMoeda) {
        this.aviaoPremioMoeda = aviaoPremioMoeda;
    }

    public String getColisaoPremioMoeda() {
        return colisaoPremioMoeda;
    }

    public void setColisaoPremioMoeda(String colisaoPremioMoeda) {
        this.colisaoPremioMoeda = colisaoPremioMoeda;
    }

    public String getExplosaoPremioMoeda() {
        return explosaoPremioMoeda;
    }

    public void setExplosaoPremioMoeda(String explosaoPremioMoeda) {
        this.explosaoPremioMoeda = explosaoPremioMoeda;
    }

    public String getTerramotoPremioMoeda() {
        return terramotoPremioMoeda;
    }

    public void setTerramotoPremioMoeda(String terramotoPremioMoeda) {
        this.terramotoPremioMoeda = terramotoPremioMoeda;
    }

    public String getTempestadePremioMoeda() {
        return tempestadePremioMoeda;
    }

    public void setTempestadePremioMoeda(String tempestadePremioMoeda) {
        this.tempestadePremioMoeda = tempestadePremioMoeda;
    }

    public String getAfundamentoPremioMoeda() {
        return afundamentoPremioMoeda;
    }

    public void setAfundamentoPremioMoeda(String afundamentoPremioMoeda) {
        this.afundamentoPremioMoeda = afundamentoPremioMoeda;
    }

    public String getRiscoPoliticoPremioMoeda() {
        return riscoPoliticoPremioMoeda;
    }

    public void setRiscoPoliticoPremioMoeda(String riscoPoliticoPremioMoeda) {
        this.riscoPoliticoPremioMoeda = riscoPoliticoPremioMoeda;
    }

    public String getDanoPremioMoeda() {
        return danoPremioMoeda;
    }

    public void setDanoPremioMoeda(String danoPremioMoeda) {
        this.danoPremioMoeda = danoPremioMoeda;
    }

    public String getTumultoPremioMoeda() {
        return tumultoPremioMoeda;
    }

    public void setTumultoPremioMoeda(String tumultoPremioMoeda) {
        this.tumultoPremioMoeda = tumultoPremioMoeda;
    }

    public String getIncendioFPremioMoeda() {
        return incendioFPremioMoeda;
    }

    public void setIncendioFPremioMoeda(String incendioFPremioMoeda) {
        this.incendioFPremioMoeda = incendioFPremioMoeda;
    }

    public String getExplosaoExPremioMoeda() {
        return explosaoExPremioMoeda;
    }

    public void setExplosaoExPremioMoeda(String explosaoExPremioMoeda) {
        this.explosaoExPremioMoeda = explosaoExPremioMoeda;
    }

    public String getRoturaPremioMoeda() {
        return roturaPremioMoeda;
    }

    public void setRoturaPremioMoeda(String roturaPremioMoeda) {
        this.roturaPremioMoeda = roturaPremioMoeda;
    }

    @Override
    public String toString() {
        return "IncendioRisco{" + "riscoAviaoId=" + riscoAviaoId + ", aviaoValor=" + aviaoValor + ", aviaoTaxa=" + aviaoTaxa + ", aviaoPremio=" + aviaoPremio + ", aviaoPremioMoeda=" + aviaoPremioMoeda + ", colisaoId=" + colisaoId + ", colisaoValor=" + colisaoValor + ", colisaoTaxa=" + colisaoTaxa + ", colisaoPremio=" + colisaoPremio + ", colisaoPremioMoeda=" + colisaoPremioMoeda + ", explosaoId=" + explosaoId + ", explosaoValor=" + explosaoValor + ", explosaoTaxa=" + explosaoTaxa + ", explosaoPremio=" + explosaoPremio + ", explosaoPremioMoeda=" + explosaoPremioMoeda + ", terramotoId=" + terramotoId + ", terramotoValor=" + terramotoValor + ", terramotoTaxa=" + terramotoTaxa + ", terramotoPremio=" + terramotoPremio + ", terramotoPremioMoeda=" + terramotoPremioMoeda + ", tempestadeId=" + tempestadeId + ", tempestadeValor=" + tempestadeValor + ", tempestadeTaxa=" + tempestadeTaxa + ", tempestadePremio=" + tempestadePremio + ", tempestadePremioMoeda=" + tempestadePremioMoeda + ", afundamentoId=" + afundamentoId + ", afundamentoValor=" + afundamentoValor + ", afundamentoTaxa=" + afundamentoTaxa + ", afundamentoPremio=" + afundamentoPremio + ", afundamentoPremioMoeda=" + afundamentoPremioMoeda + ", riscoPoliticoId=" + riscoPoliticoId + ", riscoPoliticoValor=" + riscoPoliticoValor + ", riscoPoliticoTaxa=" + riscoPoliticoTaxa + ", riscoPoliticoPremio=" + riscoPoliticoPremio + ", riscoPoliticoPremioMoeda=" + riscoPoliticoPremioMoeda + ", danoId=" + danoId + ", danoValor=" + danoValor + ", danoTaxa=" + danoTaxa + ", danoPremio=" + danoPremio + ", danoPremioMoeda=" + danoPremioMoeda + ", tumultoId=" + tumultoId + ", tumultoValor=" + tumultoValor + ", tumultoTaxa=" + tumultoTaxa + ", tumultoPremio=" + tumultoPremio + ", tumultoPremioMoeda=" + tumultoPremioMoeda + ", incendioFId=" + incendioFId + ", incendioFValor=" + incendioFValor + ", incendioFTaxa=" + incendioFTaxa + ", incendioFPremio=" + incendioFPremio + ", incendioFPremioMoeda=" + incendioFPremioMoeda + ", explosaoExId=" + explosaoExId + ", explosaoExValor=" + explosaoExValor + ", explosaoExTaxa=" + explosaoExTaxa + ", explosaoExPremio=" + explosaoExPremio + ", explosaoExPremioMoeda=" + explosaoExPremioMoeda + ", roturaId=" + roturaId + ", roturaValor=" + roturaValor + ", roturaTaxa=" + roturaTaxa + ", roturaPremio=" + roturaPremio + ", roturaPremioMoeda=" + roturaPremioMoeda + '}';
    }
    
}
