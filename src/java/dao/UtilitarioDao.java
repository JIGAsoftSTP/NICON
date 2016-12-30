/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.Call;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ComoBox;
import modelo.PrecoCobertura;
import modelo.Relatorio;
import modelo.Taxa;
import sessao.SessionUtil;
import validacao.OperacaoData;

/**
 *
 * @author AhmedJorge
 */
public class UtilitarioDao implements Serializable {

    public ArrayList<ComoBox> listaUtilitario() {
        ResultSet rs = Call.selectFrom("VER_TIPOOBJECTO", "*");
        ArrayList<ComoBox> list = new ArrayList<>();
        if (rs == null) {
            return list;
        }
        Consumer<HashMap<String, Object>> act = (map) -> list.add(new ComoBox(map.get("ID").toString(), map.get("ENTIDADE").toString(), (String) map.get("ID SUPER"), (String) map.get("SUPER")));
        Call.forEchaResultSet(act, rs);
        return list;
    }

    public ArrayList<ComoBox> listaUtilitarioID(String id, String valor) {
        ResultSet rs = Call.callTableFunction("FUNCT_LOAD_OBJTYPE", "*", id, valor);
        ArrayList<ComoBox> list = new ArrayList<>();
        Consumer<HashMap<String, Object>> act = (map) -> list.add(new ComoBox(map.get("ID").toString(), map.get("DESCRICAO").toString()));
        Call.forEchaResultSet(act, rs);
        return list;
    }

    public ArrayList<PrecoCobertura> listaPrecoCobertura() {
        ResultSet rs = Call.selectFrom("VER_PRECOS_SEGUROS", "*");
        ArrayList<PrecoCobertura> list = new ArrayList<>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    PrecoCobertura pc = new PrecoCobertura();
                    pc.setId(rs.getString("ID"));
                    pc.setIncio(rs.getString("INICIA"));
                    pc.setFim(rs.getString("FIM"));
                    pc.setNc(rs.getString("NC"));
                    pc.setTotal(rs.getString("TOTAL"));
                    pc.setPremio(rs.getString("PREMIO"));
                    pc.setAcao(rs.getString("TIPO"));
                    list.add(pc);
                }
                rs.close();
            } catch (SQLException ex) {
                System.out.println("erro a carregar coberturas " + ex.getMessage());
                Logger.getLogger(UtilitarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public ResultSet relatorioCrescente(Date dataInicio, Date dataFim, int tipo) {
        String functionName = "PACK_REPORT.reportCrescentTime";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)), tipo);
        return rs;
    }

    public ResultSet relatorioCrescenteSeguro(Date dataInicio, Date dataFim, int tipo, int idSeguro) {
        String functionName = "PACK_REPORT.reportCresentSeguro";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)), tipo, idSeguro);
        return rs;
    }

    public ResultSet relatorioCliente(String idSeguro, Date dataInicio, Date dataFim) {
        String functionName = "PACK_REPORT.reportClient";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                idSeguro,
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)));
        return rs;
    }

    public ResultSet relatorioPromocao(Date dataInicio, Date dataFim) {
        String functionName = "PACK_REPORT.reportproducao";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)));
        return rs;
    }

    public ResultSet relatorioPromocaoTipo(Date dataInicio, Date dataFim, int idSeguro, int tipo) {
        String functionName = "PACK_REPORT.reportproducaoTIPO";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)), idSeguro, tipo);
        return rs;
    }

    public ResultSet relatorioSeguro(Date dataInicio, Date dataFim) {
        String functionName = "PACK_REPORT.REPORTSEGURO";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)));
        return rs;
    }

    public ResultSet relatorioSeguroForImpresao(Date dataInicio, Date dataFim) {
        String functionName = "PACK_REPORT.REPORTSEGUROSCOMPRAS";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)));
        return rs;
    }

    public ResultSet relatorioCrescenteSeguro(Date dataInicio, Date dataFim) {
        String functionName = "PACK_REPORT.REPORTSEGURO";
        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((dataInicio == null) ? null : OperacaoData.toSQLDate(dataInicio)),
                ((dataFim == null) ? null : OperacaoData.toSQLDate(dataFim)));
        return rs;
    }

    //PARA TROCAR A VEIW
    public ArrayList<ComoBox> listaLimitesResp() {
        ResultSet rs = Call.selectFrom("VER_INFORMACAO_COBERTURA WHERE SEGURO = ?", "*", 3);
        ArrayList<ComoBox> list = new ArrayList<>();
        if (rs == null) {
            return list;
        }
        Consumer<HashMap<String, Object>> act = (map) -> list.add(new ComoBox(
                (map.get("ID COBERTURA") == null) ? null : map.get("ID COBERTURA").toString(),
                (map.get("INFORMACAO") == null) ? null : map.get("INFORMACAO").toString(),
                (map.get("ID INFORMACAO") == null) ? null : map.get("ID INFORMACAO").toString(),
                (map.get("COBERTURA") == null) ? null : map.get("COBERTURA").toString()));
        Call.forEchaResultSet(act, rs);
        return list;
    }

    public void regOjecto(String idSuperObj, String idObJ, String desObj) {
        Call.callProcedure("PACK_UTIL.PRC_REG_OBJECTYPE",
                SessionUtil.getUserlogado().getId(),
                idSuperObj,
                desObj,
                idObJ
        );
    }

    public void desaOjecto(String idObJ) {
        Call.callProcedure("PACK_UTIL.PRC_DISABLE_OBJECTYPE",
                SessionUtil.getUserlogado().getId(),
                idObJ
        );
    }

    public ArrayList[] listOther(String veiw, String id, String detalhes) {
        try {
            ArrayList[] als = new ArrayList[2];
            ArrayList<ComoBox> ativos = new ArrayList<>();
            ArrayList<ComoBox> desativos = new ArrayList<>();
            ResultSet rs = Call.selectFrom(veiw, "*");
            if (rs == null) {
                return new ArrayList[2];
            }
            while (rs.next()) {
                try {
                    if (rs.getInt("ESTADO") == 0) {
//                       System.err.println("atvio");
                        desativos.add(new ComoBox(rs.getObject(id).toString(), rs.getObject(detalhes).toString(), rs.getObject("ESTADO").toString()));
                    }
                    if (rs.getInt("ESTADO") == 1) {
                        ativos.add(new ComoBox(rs.getObject(id).toString(), rs.getObject(detalhes).toString(), rs.getObject("ESTADO").toString()));
//                       System.err.println("Desatvio");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UtilitarioDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            als[0] = ativos;
            als[1] = desativos;

            return als;
        } catch (SQLException ex) {
            Logger.getLogger(UtilitarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object ativaDesativarPais(String id) {
        Object o = Call.callSampleFunction("PACK_UTIL.FUNC_ACTIVADESATIVA_PAIS",
                Types.VARCHAR, SessionUtil.getUserlogado().getId(), id);
        return o;
    }

    public Object ativaDesativarMeoda(String id) {
        Object o = Call.callSampleFunction("PACK_UTIL.FUNC_ACTIVADESACTIVA_MOEDA",
                Types.VARCHAR, SessionUtil.getUserlogado().getId(), id);
        return o;
    }

    public static void main(String[] args) {
        new UtilitarioDao().listOther("VER_ADM_PAIS", "ID", "NOME");
        new UtilitarioDao().listOther("VER_ADM_MOEDA", "ID", "MOEDA");
    }

    public void editeResponsabilidade(ComoBox cb) {
        Call.callProcedure("PACK_UTIL.prc_update_informacao", cb.getCodigoNicon(), SessionUtil.getUserlogado().getId(), cb.getId(), cb.getValue());

    }

    public Object regPrecoCobertura(PrecoCobertura pc) {

        Object re = Call.callSampleFunction("PACK_UTIL.FUNC_ALTERAPRECARIO", Types.VARCHAR,
                3,
                SessionUtil.getUserlogado().getId(),
                pc.getIncio(),
                pc.getFim(),
                Float.valueOf(pc.getTotal()),
                Float.valueOf(pc.getPremio()));
        return re;
    }

    public Object regBankAndTaxa(Object o, int i) {
        Object re = "";
        if (i == 1) {
            ComoBox box = (ComoBox) o;
            re = Call.callSampleFunction("FUNC_REG_BANK", Types.VARCHAR, SessionUtil.getUserlogado().getId(), box.getValue(), box.getCodigoNicon());
        } else if (i == 2) {
            Taxa tax = (Taxa) o; //( moneyName VARCHAR2 , idUser NUMBER , valorCompra FLOAT , valorVenda FLOAT ) 
            re = Call.callSampleFunction("PACK_UTIL.FUNC_SET_TAXA", Types.VARCHAR,
                    tax.getMoeda1(), tax.getMoeda2(), SessionUtil.getUserlogado().getId(), tax.getCompraValue(), tax.getVendaValue());
        }
        return re;
    }

    public ArrayList<ComoBox> moedaName() {
        ResultSet rs = Call.selectFrom("VER_MONEY_NAME", "*");
        ArrayList<ComoBox> list = new ArrayList<>();
        if (rs == null) {
            return list;
        }
        Consumer<HashMap<String, Object>> act = (map) -> list.add(new ComoBox(map.get("MOE_NOME").toString(), map.get("MOE_NOME").toString()));
        Call.forEchaResultSet(act, rs);
        return list;
    }

    public ArrayList<ComoBox> bancoList() {
        ResultSet rs = Call.selectFrom("VER_BANK", "*");
        ArrayList<ComoBox> list = new ArrayList<>();
        if (rs == null) {
            return list;
        }
        Consumer<HashMap<String, Object>> act = (map) -> list.add(new ComoBox(map.get("ID").toString(), map.get("NOME").toString(), map.get("SIGLA").toString()));
        Call.forEchaResultSet(act, rs);
        return list;
    }

    public ArrayList<Taxa> taxaMoeda(String moeda) {
        ResultSet rs = Call.callTableFunction("PACK_REPORT.reportTaxas", "*", moeda);
        ArrayList<Taxa> list = new ArrayList<>();
        if (rs == null) {
            return list;
        }
        Consumer<HashMap<String, Object>> act = (map) -> {
            Taxa t = new Taxa();
            t.setEstado((String) map.get("ESTADO"));
            t.setCompraValueView((String) map.get("VALOR COMPRA"));
            t.setVendaValueView((String) map.get("VALOR VENDA"));
            t.setMoeda1((String) map.get("MOEDA"));
            list.add(new Taxa(t));
        };

        Call.forEchaResultSet(act, rs);
        return list;
    }

    public ResultSet relatorioMapaProvisao(Relatorio relatorio) {
        String functionName = "PACK_REPORT2.FUNCT_REPORT_PROVISAO";

        ResultSet rs = Call.callTableFunction(functionName, "*",
                ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                (relatorio.getSeguro() != null && relatorio.getSeguro().equals("0")) ? null : Integer.valueOf(relatorio.getSeguro()));
        return rs;
    }

}
