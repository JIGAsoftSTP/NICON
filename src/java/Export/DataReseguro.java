/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import conexao.Call;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 *
 * @author ahmedjorge
 */
public class DataReseguro {

    public static class DataResseguro {

        private String ID;
        private String IDCONTRATO;
        private String IDTIPORESEGURO;
        private String IDSEGURO;
        private String CLIENTE;
        private String APOLICE;
        private String MOEDA;
        private String DTINICO;
        private String DTFIM;
        private String INICIO;
        private String FIM;
        private String DMOEDA;
        private String LIMITE;
        private String PREMIOGROSSO;
        private String TOTAL;
        private String ESTADO;
        private String TIPOCOBERTURA;
        private String DEDUCAO;
        private String DESCRICAO;

        private final String ID_ = "ID";
        private final String IDCONTRATO_ = "ID CONTRATO";
        private final String IDTIPORESEGURO_ = "ID TIPO RESEGURO";
        private final String IDSEGURO_ = "SEGURO ID";
        private final String CLIENTE_ = "CLIENTE";
        private final String APOLICE_ = "APOLICE";
        private final String MOEDA_ = "MOEDA";
        private final String DTINICO_ = "DT INICO";
        private final String DTFIM_ = "DT FIM";
        private final String INICIO_ = "INICIO";
        private final String FIM_ = "FIM";
        private final String DMOEDA_ = "ID MOEDA";
        private final String LIMITE_ = "LIMITE";
        private final String PREMIOGROSSO_ = "PREMIO GROSSO";
        private final String TOTAL_ = "TOTAL";
        private final String ESTADO_ = "ESTADO";
        private final String TIPOCOBERTURA_ = "TIPO COBERTURA";
        private final String DEDUCAO_ = "DEDUCAO";
        private final String DESCRICAO_ = "DESCRICAO";

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getIDCONTRATO() {
            return IDCONTRATO;
        }

        public void setIDCONTRATO(String IDCONTRATO) {
            this.IDCONTRATO = IDCONTRATO;
        }

        public String getIDTIPORESEGURO() {
            return IDTIPORESEGURO;
        }

        public void setIDTIPORESEGURO(String IDTIPORESEGURO) {
            this.IDTIPORESEGURO = IDTIPORESEGURO;
        }

        public String getIDSEGURO() {
            return IDSEGURO;
        }

        public void setIDSEGURO(String IDSEGURO) {
            this.IDSEGURO = IDSEGURO;
        }

        public String getCLIENTE() {
            return CLIENTE;
        }

        public void setCLIENTE(String CLIENTE) {
            this.CLIENTE = CLIENTE;
        }

        public String getAPOLICE() {
            return APOLICE;
        }

        public void setAPOLICE(String APOLICE) {
            this.APOLICE = APOLICE;
        }

        public String getMOEDA() {
            return MOEDA;
        }

        public void setMOEDA(String MOEDA) {
            this.MOEDA = MOEDA;
        }

        public String getDTINICO() {
            return DTINICO;
        }

        public void setDTINICO(String DTINICO) {
            this.DTINICO = DTINICO;
        }

        public String getDTFIM() {
            return DTFIM;
        }

        public void setDTFIM(String DTFIM) {
            this.DTFIM = DTFIM;
        }

        public String getINICIO() {
            return INICIO;
        }

        public void setINICIO(String INICIO) {
            this.INICIO = INICIO;
        }

        public String getFIM() {
            return FIM;
        }

        public void setFIM(String FIM) {
            this.FIM = FIM;
        }

        public String getDMOEDA() {
            return DMOEDA;
        }

        public void setDMOEDA(String DMOEDA) {
            this.DMOEDA = DMOEDA;
        }

        public String getLIMITE() {
            return LIMITE;
        }

        public void setLIMITE(String LIMITE) {
            this.LIMITE = LIMITE;
        }

        public String getPREMIOGROSSO() {
            return PREMIOGROSSO;
        }

        public void setPREMIOGROSSO(String PREMIOGROSSO) {
            this.PREMIOGROSSO = PREMIOGROSSO;
        }

        public String getTOTAL() {
            return TOTAL;
        }

        public void setTOTAL(String TOTAL) {
            this.TOTAL = TOTAL;
        }

        public String getESTADO() {
            return ESTADO;
        }

        public void setESTADO(String ESTADO) {
            this.ESTADO = ESTADO;
        }

        public String getTIPOCOBERTURA() {
            return TIPOCOBERTURA;
        }

        public void setTIPOCOBERTURA(String TIPOCOBERTURA) {
            this.TIPOCOBERTURA = TIPOCOBERTURA;
        }

        public String getDEDUCAO() {
            return DEDUCAO;
        }

        public void setDEDUCAO(String DEDUCAO) {
            this.DEDUCAO = DEDUCAO;
        }

        public String getDESCRICAO() {
            return DESCRICAO;
        }

        public void setDESCRICAO(String DESCRICAO) {
            this.DESCRICAO = DESCRICAO;
        }

    }

    public static class DataEmpresa {

        private String ID;
        private String IDRESEGURO;
        private String DEMPPRESA;
        private String PERCENTAGEM;
        private String RISCO;
        private String STATE;
        private String EMPRESA;
        private String CAPITAL;
        private String ENDERECO;
        
        private final String ID_ = "ID";
        private final String IDRESEGURO_ = "ID RESEGURO";
        private final String IDEMPPRESA_ = "ID EMPPRESA";
        private final String PERCENTAGEM_ = "PERCENTAGEM";
        private final String RISCO_ = "RISCO";
        private final String STATE_ = "STATE";
        private final String EMPRESA_ = "EMPRESA";
        private final String CAPITAL_ = "CAPITAL";
        private final String ENDERECO_ = "ENDERECO";

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getDRESEGURO() {
            return IDRESEGURO;
        }

        public void setDRESEGURO(String DRESEGURO) {
            this.IDRESEGURO = DRESEGURO;
        }

        public String getDEMPPRESA() {
            return DEMPPRESA;
        }

        public void setDEMPPRESA(String DEMPPRESA) {
            this.DEMPPRESA = DEMPPRESA;
        }

        public String getPERCENTAGEM() {
            return PERCENTAGEM;
        }

        public void setPERCENTAGEM(String PERCENTAGEM) {
            this.PERCENTAGEM = PERCENTAGEM;
        }

        public String getRISCO() {
            return RISCO;
        }

        public void setRISCO(String RISCO) {
            this.RISCO = RISCO;
        }

        public String getSTATE() {
            return STATE;
        }

        public void setSTATE(String STATE) {
            this.STATE = STATE;
        }

        public String getEMPRESA() {
            return EMPRESA;
        }

        public void setEMPRESA(String EMPRESA) {
            this.EMPRESA = EMPRESA;
        }

        public String getCAPITAL() {
            return CAPITAL;
        }

        public void setCAPITAL(String CAPITAL) {
            this.CAPITAL = CAPITAL;
        }

        public String getENDERECO() {
            return ENDERECO;
        }

        public void setENDERECO(String ENDERECO) {
            this.ENDERECO = ENDERECO;
        }
        
    }

    public static ArrayList<DataEmpresa> getDadosEmpresa(int idRESEGURO) {
        ArrayList<DataEmpresa> listDataEmpresas = new ArrayList<>();
        ResultSet rs = Call.selectFrom("VER_RESEGUROEMPRESA where \"ID RESEGURO\" = ?", "*", idRESEGURO);
        Consumer<HashMap<String, Object>> act = (map) -> {
            DataEmpresa dEm = new DataEmpresa();
            dEm.ID = toString(map.get(dEm.ID_));
            dEm.CAPITAL = toString(map.get(dEm.CAPITAL_));
            dEm.DEMPPRESA = toString(map.get(dEm.IDEMPPRESA_));
            dEm.EMPRESA = toString(map.get(dEm.EMPRESA_));
            dEm.IDRESEGURO = toString(map.get(dEm.IDRESEGURO_));
            dEm.PERCENTAGEM = toString(map.get(dEm.PERCENTAGEM_));
            dEm.RISCO = toString(map.get(dEm.RISCO_));
            dEm.STATE = toString(map.get(dEm.STATE_));
            dEm.ENDERECO = toString(map.get(dEm.ENDERECO_));
            listDataEmpresas.add(dEm);
        };
        Call.forEchaResultSet(act, rs);
        return listDataEmpresas;
    }

    public static DataResseguro getDadosReseguro(int idRegistro) {
        DataResseguro dRe = new DataResseguro();
        ResultSet rs = Call.selectFrom("VER_RESEGUROS where \"ID\" = ?", "*", idRegistro);
        Consumer<HashMap<String, Object>> act = (map) -> {
            dRe.ID = toString(map.get(dRe.ID_));
            dRe.APOLICE = toString(map.get(dRe.APOLICE_));
            dRe.CLIENTE = toString(map.get(dRe.CLIENTE_));
            dRe.DEDUCAO = toString(map.get(dRe.DEDUCAO_));
            dRe.DESCRICAO = toString(map.get(dRe.DESCRICAO_));
            dRe.DMOEDA = toString(map.get(dRe.DMOEDA_));
            dRe.DTFIM = toString(map.get(dRe.DTFIM_));
            dRe.DTINICO = toString(map.get(dRe.DTINICO_));
            dRe.ESTADO = toString(map.get(dRe.ESTADO_));
            dRe.FIM = toString(map.get(dRe.FIM_));
            dRe.IDCONTRATO = toString(map.get(dRe.IDCONTRATO_));
            dRe.IDSEGURO = toString(map.get(dRe.IDSEGURO_));
            dRe.IDTIPORESEGURO = toString(map.get(dRe.IDTIPORESEGURO_));
            dRe.INICIO = toString(map.get(dRe.INICIO_));
            dRe.LIMITE = toString(map.get(dRe.LIMITE_));
            dRe.MOEDA = toString(map.get(dRe.MOEDA_));
            dRe.PREMIOGROSSO = toString(map.get(dRe.PREMIOGROSSO_));
            dRe.TIPOCOBERTURA = toString(map.get(dRe.TIPOCOBERTURA_));
            dRe.TOTAL = toString(map.get(dRe.TOTAL_));
        };
        try { Call.forEchaResultSet(act, rs); } catch (Exception e) { }
        return dRe;
    }

    private static String toString(Object o) {
        return ((o == null) ? "" : o.toString());
    }
}
