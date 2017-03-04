/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import dao.ClienteDao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author AhmedJorge
 */
public class ClienteI implements Serializable {

    public String NUMCLIENTEID;

    public ClienteI(String id) {
        this.NUMCLIENTEID = id;
    }

    public String NOME_;
    public String ENDERECO_;
    public String NUNCLIENTE_;
    public String PROFISSAO_;
    public String LOCALTRABALHO_;
    public String TELEFONE_;
    public String CODPOSTAL_;

    public String NOMEL_;
    public String ENDERECOL_;
    public String NUNCLIENTEL_;
    public String PROFISSAOL_;
    public String LOCALTRABALHOL_;
    public String TELEFONEL_;
    public String CODPOSTALL_;

    public String getDados(String param) {
        try {
            ResultSet rss;
//            System.err.println(NUMCLIENTEID + " getDadosCliente");
            if (ClienteDao.getDadosCliente(NUMCLIENTEID) != null) {
                rss = ClienteDao.getDadosCliente(NUMCLIENTEID);
                return ((rss.getString(param) == null) ? " " : rss.getString(param));
            } else {
                return " ";
            }
        } catch (SQLException ex) {
            return " ";
        }
    }

    public String getNUMCLIENTEID() {
        return NUMCLIENTEID;
    }

    public String getNOME_() {
        return getDados("NOME").toUpperCase();
    }

    public String getENDERECO_() {
        return getDados("RESIDENCIA").toUpperCase();
    }

    public String getNUNCLIENTE_() {
        return getDados("ID").toUpperCase();
    }

    public String getPROFISSAO_() {
        return getDados("ACUPACAO").toUpperCase();
    }

    public String getLOCALTRABALHO_() {
        return getDados("LOCAL TRABALHO").toUpperCase();
    }

    public String getTELEFONE_() {
        return getDados("TELEFONE").toUpperCase();
    }

    public String getCODPOSTAL_() {
        return getDados("CAIXA POSTAL").toUpperCase();
    }

    public String getNOMEL_() {
        return "Nome: ".toUpperCase();
    }

    public String getENDERECOL_() {
        return "RESIDENCIA: ".toUpperCase();
    }

    public String getNUNCLIENTEL_() {
        return "Nº Cliente: ".toUpperCase();
    }

    public String getPROFISSAOL_() {
        return "PROFISSÃO/OCUPAÇÃO/ÁREA DE ATIVIDADE: ".toUpperCase();
    }

    public String getLOCALTRABALHOL_() {
        return "Local de Trabalho: ".toUpperCase();
    }

    public String getTELEFONEL_() {
        return "Telefone: ".toUpperCase();
    }

    public String getCODPOSTALL_() {
        return "CP NO.: ";
    }

}
