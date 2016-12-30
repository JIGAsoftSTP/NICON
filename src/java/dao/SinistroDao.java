/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.SinistroBean;
import conexao.Call;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ComoBox;
import modelo.DataContrato;
import modelo.Pagamento;
import modelo.Relatorio;
import modelo.Sinistro;
import sessao.SessionUtil;
import validacao.OperacaoData;

/**
 *
 * @author AhmedJorge
 */
public class SinistroDao implements Serializable {

    public static final String APOLICE_CONTRATO = "APOLICE", DATA_INICIO = "DATA INICIO", DATA_FIM = "DATA FIM", CLIENTE_CONTRATO = "CLIENTE", CLIENTE_NOME = "NOME", CLIENTE_APELIDO = "APELIDO", VALOR_SEGURADO = "VALOR TOTAL SEGURADO",
            PREMIO_BRUTO = "PREMIO BRUTO", EXCESSO = "EXCESSO", ESTADO_PAGAMENTO = "ESTADO PAGAMENTO",
            ESTADO_CONTRATO = "ESTADO CONTRATO", MOEDA = "MOEDA NOME";

    static String ID = "ID", IDCLIENTE = "ID CLIENTE", APOLICE = "APOLICE", DATA = "DATA", SEGURO = "SEGURO", CLIENTE = "CLIENTE", PREMIOBRUTO = "PREMIO BRUTO",
            DATAINICIO = "DATA INICIO", DATAFIM = "DATA FIM", DATACONTRATO = "DATA CONTRATO", PREMIOLIQUIDO = "PREMIO LIQUIDO", LIMETERESPONSABILIDADE = "LIMETE RESPONSABILIDADE",
            LIQUIDOSF = "LIQUIDOSF";
    
    public static final String ID_PAGAMENTO = "ID PAGAMENTO", ID_OCORRENCIA ="ID OCORENCIA", APOLICE_PAGAMENTO ="APOLICE", NOTA_DEBITO ="NOTA DEBITO",
            VALOR_PAGAMENTO = "VALOR PAGAMENTO", UTILIZADOR ="USER", DATA_PAGAMENTO ="DATA PAGAMENTO", REGISTRO ="REGISTRO", OBS ="OBSERVACAO",
            OCORRENCIA ="OCORENCIA", NOME_CLIENTE ="NOME CLIENTE", APELIDO_CLIENTE="APELIDO";

    public static ArrayList<DataContrato> listContrato(String pes) {
        ResultSet rs;
        ArrayList<DataContrato> listMap = new ArrayList<>();
        if (pes == null) {
            rs = Call.callTableFunction("PACK_PESQUISA.pesqContrato", "*");
        } else {
            rs = Call.selectFiltredFrom("Table(PACK_PESQUISA.pesqContrato())", "*", pes, new String[]{"LIMETE RESPONSABILIDADE", "APOLICE", "SEGURO", "CLIENTE", "PREMIO BRUTO", "DATA CONTRATO"});
        }

        Consumer<HashMap<String, Object>> act = (map) -> {
            DataContrato dc = new DataContrato();
            dc.setId(toString(map.get(ID)));
            dc.setIdCliente(toString(map.get(IDCLIENTE)));
            dc.setApolice(toString(map.get(APOLICE)));
            dc.setData(toString(map.get(DATA)));
            dc.setSeguro(toString(map.get(SEGURO)));
            dc.setCliente(toString(map.get(CLIENTE)));
            dc.setPremioBruto(toString(map.get(PREMIOBRUTO)));
            dc.setDataInicio(toString(map.get(DATAINICIO)));
            dc.setDataFim(toString(map.get(DATAFIM)));
            dc.setDataContrato(toString(map.get(DATACONTRATO)));
            dc.setPremioLiquido(toString(map.get(PREMIOLIQUIDO)));
            dc.setLimiteResponsabilidade(toString(map.get(LIMETERESPONSABILIDADE)));
            dc.setPremioLiquidoSF(toString(map.get(LIQUIDOSF)));
            listMap.add(dc);
        };
        Call.forEchaResultSet(act, rs);

        return listMap;
    }

    public static String toString(Object o) {
        return (o == null) ? "" : o.toString();
    }

    public static Object regHipoteca(ComoBox cb, String id) {
        return Call.callSampleFunction("PACK_SINISTRO.FUNC_REG_HIPOTECA", Types.VARCHAR, SessionUtil.getUserlogado().getId(), id, cb.getCodigoNicon(), cb.getValue(), cb.getId());
    }

    public static Object regTestemunha(ComoBox cb, String id) {
//        FUNC_REG_TESTEMUNHAS( ID_USER NUMBER , OCORRENCIA_TESTEMUNHO NUMBER , NOME_RESIDENCIA varchar2 , NOME_TESTEMUNHA VARCHAR2 , TELEFONE_TESTEMUNHA VARCHAR2 ) RETURN VARCHAR2 ; 
        return Call.callSampleFunction("PACK_SINISTRO.FUNC_REG_TESTEMUNHAS", Types.VARCHAR, SessionUtil.getUserlogado().getId(), id, cb.getValue(), cb.getCodigoNicon(), cb.getDestalhes(), cb.getId());
    }

    public static Object regSinistro(Sinistro s) {
//        FUNC_REG_OCORRENCIA( ID_USER NUMBER , ID_CTTOCORRENCIA NUMBER , residenciaPolicial VARCHAR2 , residenciaSinistrado VARCHAR2 , localInspecao VARCHAR2 ,
//        DTINSPECAO DATE , HORA_DATAOCORRENCIA TIMESTAMP , LOCALOCORRENCIA VARCHAR2 , ESTADO_SINISTRADO VARCHAR2 , NARRACAO_SUCEDIDO VARCHAR2 , ESTIMATIVA_RECUPERACAO VARCHAR2
//                , NUMERO_VEICULOTERCEIRO NUMBER , NUMERO_CHASSI NUMBER , DESCRICAO_OCORRENCIA VARCHAR2 ) RETURN VARCHAR2 ;
        return Call.callSampleFunction("PACK_SINISTRO.FUNC_REG_OCORRENCIA", Types.VARCHAR,
                SessionUtil.getUserlogado().getId(), s.getContratoID(), s.getEnderecoPolicial(), s.getEnderecoSinistro(), s.getIlocal(), s.getIdata(), toTimeStamp(s.getData(), s.getHora()), s.getLocal(),
                /* s.getMedidasTomadas(),*/ s.getNarracaoSucedido(), s.getEstimativaRecuperacao(), s.getNumVeiculo(), s.getNumChassi(), s.getMedidasTomadas(), s.getId(), s.getNumSinistro());
    }

    private static java.sql.Timestamp toTimeStamp(Date data, String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Object sqlData = Call.callSampleFunction("TO_TIMESTAMP", Types.TIMESTAMP, sdf.format(data) + " " + hora, "DD-MM-YYYY HH24:MI");
        return (Timestamp) sqlData;
    }

    public static ResultSet relatorioSinistro(Relatorio relatorio) {
        String functionName = "PACK_REPORT2.FUNCT_REPORT_SINISTRO";
        ResultSet rs;
        if (relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals("")) {
            String colunasSearch[] = {"SEGURO", "CLIENTTE", "APOLICE", "LOCAL OCORENCIA", "LOCAL INSPENSAO"};
            rs = Call.selectFiltredFrom("TABLE(" + functionName + "(?,?))", "*", relatorio.getValorPesquisa(), colunasSearch,
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
        } else {
            rs = Call.callTableFunction(functionName, "*",
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    (relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim()));
        }
        return rs;
    }

    public static Object regPagamento(Pagamento p) {//  FUNC_REG_SINISTROPAYMENT( idUser NUMBER , idOcorencia NUMBER , valorOcorencia FLOAT , observacao CHARACTER VARYING )
        Object o = Call.callSampleFunction("PACK_SINISTRO.FUNC_REG_SINISTROPAYMENT", Types.VARCHAR, SessionUtil.getUserlogado().getId(), p.getNumero(), p.getValor(), p.getDescricaoPagamento(), p.getDataPagamento());
        return o;
    }

    public static Object desatPagamento(Object p, String just) {//  FUNC_REG_SINISTROPAYMENT( idUser NUMBER , idOcorencia NUMBER , valorOcorencia FLOAT , observacao CHARACTER VARYING )
        Object o = Call.callSampleFunction("PACK_SINISTRO.FUNC_DISABLE_SINISTRO", Types.VARCHAR, SessionUtil.getUserlogado().getId(), p, just);
        return o;
    }

    public static SinistroBean dadosSinistro(String id, SinistroBean sb) {
//        String idS = "OCOR_ID";
        String idS = "OCOR_OCOR_ID";
        String idContrato = "OCOR_CTT_ID";
//        OCOR_USER_ID
        String idendecoPolicial = "OCOR_OBJT_ENDERECOPOLICIAL";
        String idEnderecoSinistrado = "OCOR_OBJT_ENDERECOSINISTRADO";
        String idLocalInspensao = "OCOR_OBJT_LOCALINSPECAO";
        String dataInspensao = "OCOR_DTINSPECAO";
        String hora = "OCOR_HORA";
        String idLocal = "OCOR_OBJT_LOCAL";
        String narracaoSucedito = "OCOR_NARRACAOSUCEDIDO";
        String estemativaRecupercao = "OCOR_ESTIMATIVARECUPERACAO";
        String numVeiculo = "OCOR_NUMVEICULOTERCEIRO";
        String numChassi = "OCOR_NUMCHASSI";
        String descr = "OCOR_DESC";	//OCOR_DTREG	OCOR_STATE	OCOR_OCOR_ID
        String numSinistro = "OCOR_COD";	//OCOR_DTREG	OCOR_STATE	OCOR_OCOR_ID

        ResultSet rs = Call.callTableFunction("PACK_SINISTRO.FUNCT_LOAD_OCORRENCIA", "*", id);

        Consumer<HashMap<String, Object>> act = (map) -> {
            try {

                SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat horaF = new SimpleDateFormat("HH:mm");
                sb.getSinistro().setId(toString(map.get(idS)));
                sb.getSinistro().setNumSinistro(toString(map.get(numSinistro)));
                sb.getSinistro().setData(data.parse(toString(map.get(hora))));
                sb.getSinistro().setEnderecoPolicial(getDadosOjct(map.get(idendecoPolicial)));
                sb.getSinistro().setEnderecoSinistro(getDadosOjct(map.get(idEnderecoSinistrado)));
                sb.getSinistro().setEstimativaRecuperacao(toString(map.get(estemativaRecupercao)));
                sb.getSinistro().setHora(horaF.format(data.parse(toString(map.get(hora)))));
                sb.getSinistro().setIdata(data.parse(toString(map.get(dataInspensao))));
                sb.getSinistro().setIlocal(getDadosOjct(map.get(idLocalInspensao)));
                sb.getSinistro().setLocal(getDadosOjct(map.get(idLocal)));
                sb.getSinistro().setNarracaoSucedido(toString(map.get(narracaoSucedito)));

                sb.getSinistro().setMedidasTomadas(toString(map.get(descr)));
                sb.getSinistro().setNumChassi(toString(map.get(numChassi)));
                sb.getSinistro().setNumVeiculo(toString(map.get(numVeiculo)));

                getDadosHipoteca(id, sb);
                getDadosTestemuha(id, sb);
                getDadosApolice(toString(toString(map.get(idContrato))), sb);

                sb.setListContrato(listContrato(null));

            } catch (ParseException ex) {
                Logger.getLogger(SinistroDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        };
        Call.forEchaResultSet(act, rs);
        System.err.println(sb.getSelectedContrato());
        System.err.println(sb.getSinistro());
        return sb;

    }

    public static SinistroBean getSinistro(String idF) {
        String idS = "OCOR_OCOR_ID";
        String idContrato = "OCOR_CTT_ID";
//        OCOR_USER_ID
        String idendecoPolicial = "OCOR_OBJT_ENDERECOPOLICIAL";
        String idEnderecoSinistrado = "OCOR_OBJT_ENDERECOSINISTRADO";
        String idLocalInspensao = "OCOR_OBJT_LOCALINSPECAO";
        String dataInspensao = "OCOR_DTINSPECAO";
        String hora = "OCOR_HORA";
        String idLocal = "OCOR_OBJT_LOCAL";
        String narracaoSucedito = "OCOR_NARRACAOSUCEDIDO";
        String estemativaRecupercao = "OCOR_ESTIMATIVARECUPERACAO";
        String numVeiculo = "OCOR_NUMVEICULOTERCEIRO";
        String numChassi = "OCOR_NUMCHASSI";
        String descr = "OCOR_DESC";	//OCOR_DTREG	OCOR_STATE	OCOR_OCOR_ID
        String numSinistro = "OCOR_COD";
        
        SinistroBean sb = new SinistroBean();

        ResultSet rs = Call.callTableFunction("PACK_SINISTRO.FUNCT_LOAD_OCORRENCIA", "*", idF);

        Consumer<HashMap<String, Object>> act = (map) -> {
            try {

                SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat horaF = new SimpleDateFormat("HH:mm");
                sb.getSinistro().setId(toString(map.get(idS)));
                sb.getSinistro().setNumSinistro(toString(map.get(numSinistro)));
                sb.getSinistro().setData(data.parse(toString(map.get(hora))));
                sb.getSinistro().setEnderecoPolicial(getDadosOjct(map.get(idendecoPolicial)));
                sb.getSinistro().setEnderecoSinistro(getDadosOjct(map.get(idEnderecoSinistrado)));
                sb.getSinistro().setEstimativaRecuperacao(toString(map.get(estemativaRecupercao)));
                sb.getSinistro().setHora(horaF.format(data.parse(toString(map.get(hora)))));
                sb.getSinistro().setIdata(data.parse(toString(map.get(dataInspensao))));
                sb.getSinistro().setIlocal(getDadosOjct(map.get(idLocalInspensao)));
                sb.getSinistro().setLocal(getDadosOjct(map.get(idLocal)));
                sb.getSinistro().setNarracaoSucedido(toString(map.get(narracaoSucedito)));

                sb.getSinistro().setMedidasTomadas(toString(map.get(descr)));
                sb.getSinistro().setNumChassi(toString(map.get(numChassi)));
                sb.getSinistro().setNumVeiculo(toString(map.get(numVeiculo)));

                getDadosHipoteca(idF, sb);
                getDadosTestemuha(idF, sb);
                getDadosApolice(toString(toString(map.get(idContrato))), sb);

                sb.setListContrato(listContrato(null));

            } catch (ParseException ex) {
                Logger.getLogger(SinistroDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        };
        Call.forEchaResultSet(act, rs);
        System.err.println(sb.getSelectedContrato());
        System.err.println(sb.getSinistro());
        return sb;
    }

    public static ResultSet relatorioMapaPagamento(Relatorio relatorio) {
        String functionName = "PACK_SINISTRO.FUNCT_LOAD_MAPAPAYMENT";
        ResultSet rs;

        if (relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals("")) {
            String colunasSearch[] = {"APOLICE", "CLIENTE", "VALOR", "DATA", "OBSERVACAO"};
            rs = Call.selectFiltredFrom("TABLE(" + functionName + "(?,?))", "*", relatorio.getValorPesquisa(), colunasSearch,
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
        } else {
            rs = Call.callTableFunction(functionName, "*",
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
        }
        return rs;
    }

    public static void getDadosApolice(String idSeguro, SinistroBean sb) {
        ResultSet rsd = Call.selectFrom("Table(PACK_PESQUISA.pesqContrato) where ID = ? ", "*", idSeguro);

        Consumer<HashMap<String, Object>> act = (map) -> {
            sb.getSinistro().setContratoID(toString(map.get(ID)));
            sb.getSinistro().setApolice(toString(map.get(APOLICE)));
            sb.getSinistro().setSeguro(sb.getSinistro().getNameSeguro(toString(map.get(SEGURO))));
            sb.getSelectedContrato().setSeguro(toString(map.get(SEGURO)));
        };

        Call.forEchaResultSet(act, rsd);
    }

    public static void getDadosHipoteca(String idHipoteca, SinistroBean sb) {
        ResultSet srs = Call.callTableFunction("PACK_SINISTRO.FUNCT_LOAD_HIPOTECA", "*", idHipoteca);
        String id = "HIPO_ID";
        String endereco = "HIPO_OBJT_ENDERECO";
        String nome = "HIPO_NOMEINTERESSADO";

        Consumer<HashMap<String, Object>> actH = (mapH) -> {
            ComoBox box = new ComoBox();
            box.setId(toString(mapH.get(id)));
            box.setValue(getDadosOjct(mapH.get(endereco)));
            box.setCodigoNicon(toString(mapH.get(nome)));

            sb.getListHipoteca().add(box);
            sb.getHipoteca().setId(toString(mapH.get(id)));

        };

        Call.forEchaResultSet(actH, srs);

        sb.getSinistro().setHipoteca(((sb.getListHipoteca().size() > 0) ? "true" : "false"));
    }

    public static void getDadosTestemuha(String idTestemunha, SinistroBean sb) {
        String id = "TEST_ID";
        String endereco = "TEST_OBJT_ENDERECO";
        String nome = "TEST_NOME";
        String telefone = "TEST_TELEFONE";

        ResultSet rss = Call.callTableFunction("PACK_SINISTRO.FUNCT_LOAD_TESTEMNUNHA", "*", idTestemunha);

        Consumer<HashMap<String, Object>> actT = (mapT) -> {
            sb.getTestemunha().setCodigoNicon(toString(mapT.get(nome)));
            sb.getTestemunha().setDestalhes(toString(mapT.get(telefone)));
            sb.getTestemunha().setId(toString(mapT.get(id)));
            sb.getTestemunha().setValue(getDadosOjct(mapT.get(endereco)));
        };

        Call.forEchaResultSet(actT, rss);

        sb.getSinistro().setTestemunha(((sb.getTestemunha().getValue() == null || sb.getTestemunha().getValue().equals("")) ? "false" : "true"));
    }

    public static String getDadosOjct(Object idTestemunha) {
        return toString(Call.callSampleFunction("FUNC_GET_OBJECTTYPE_VALUE", Types.VARCHAR, toString(idTestemunha)));
    }

    public static HashMap<String, Object> getDadosContrato(int idContrato) {
        HashMap<String, Object> dadosContrato = new HashMap<>();
        ResultSet rs = Call.selectFrom("VER_CONTRATO_COMPLETO where ID = ?", "*", idContrato);

        Consumer<HashMap<String, Object>> act = (map) -> {
            dadosContrato.put(APOLICE_CONTRATO, map.get(APOLICE_CONTRATO));
            dadosContrato.put(DATA_INICIO, map.get(DATA_INICIO).toString().substring(0, 10));
            dadosContrato.put(DATA_FIM, map.get(DATA_FIM).toString().substring(0, 10));
            dadosContrato.put(CLIENTE_CONTRATO, map.get(CLIENTE_NOME) + " " + map.get(CLIENTE_APELIDO));
            dadosContrato.put(VALOR_SEGURADO, map.get(VALOR_SEGURADO));
            dadosContrato.put(PREMIO_BRUTO, map.get(PREMIO_BRUTO));
            dadosContrato.put(EXCESSO, map.get(EXCESSO));
            dadosContrato.put(ESTADO_PAGAMENTO, map.get(ESTADO_PAGAMENTO));
            dadosContrato.put(ESTADO_CONTRATO, map.get(ESTADO_CONTRATO));
            dadosContrato.put(MOEDA, map.get(MOEDA));
        };
        Call.forEchaResultSet(act, rs);
        return dadosContrato;
    }
    public static List<Pagamento> listaPagamentosSolicitados() 
    {
        List<Pagamento> list = new ArrayList<>();
        ResultSet rs = Call.selectFrom("VER_PAY_SINISTRO_PENDENTE", "*");
        
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    Pagamento p = new Pagamento();
                    p.setIdPagamento(rs.getString(ID_PAGAMENTO));
                    p.setIdOcorrencia(rs.getString(ID_OCORRENCIA));
                    p.setNumeroSinistro(rs.getString(OCORRENCIA));
                    p.setDataRegistro(dataFormatar(rs.getString(REGISTRO)));
                    p.setUtilizador(rs.getString(UTILIZADOR));
                    p.setDescricaoPagamento((rs.getString(OBS) == null || rs.getString(OBS).equals("") ? "" : rs.getString(OBS)));
                    p.setValor(rs.getString(VALOR_PAGAMENTO));
                    p.setApolice(rs.getString(APOLICE_PAGAMENTO));
                    p.setNotaDebito(rs.getString(NOTA_DEBITO));
                    if(rs.getString(APELIDO_CLIENTE) == null || rs.getString(APELIDO_CLIENTE).equals("") || rs.getString(APELIDO_CLIENTE).equals("null"))
                         p.setBeneficiario(rs.getString(NOME_CLIENTE));
                    else
                    {p.setBeneficiario(rs.getString(NOME_CLIENTE)+" "+rs.getString(APELIDO_CLIENTE));}
                    p.setSolicitarPagamentoData(dataFormatar(rs.getString(DATA_PAGAMENTO)));
                    list.add(p);
                }
                rs.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(SinistroDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    private static String dataFormatar(String data)
    {
        data = data.substring(0, 10);
        data = data.substring(data.length()-2, data.length())+"-"+data.substring(data.length()-5, data.length()-3)+"-"+data.subSequence(data.length()-10, data.length()-6);
        return data;
    }
  
  
 
}
