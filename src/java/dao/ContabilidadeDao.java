package dao;

import Export.GenericExcel;
import Export.GenericPDFs;
import bean.DataTableControl;
import conexao.Call;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import lib.Moeda;
import modelo.Cheque;
import modelo.ComoBox;
import modelo.Conta;
import modelo.CreditoDebito;
import modelo.Pagamento;
import modelo.Prestacao;
import modelo.Recebimento;
import modelo.Relatorio;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
public class ContabilidadeDao implements Serializable {

    private ResultSet rs;
    private List<Conta> contas = new ArrayList<>();

    public String registarConta(Conta conta) 
    {      
        Object result = Call.callSampleFunction("FUNC_REG_ACCOUNT", Types.VARCHAR, 
                Integer.valueOf(SessionUtil.getUserlogado().getId()+""),
                Integer.valueOf(conta.getNumConta().substring(conta.getNumConta().length()-1, conta.getNumConta().length())),
                conta.getDesignacao(),
                Integer.valueOf(conta.getIdAccount()));
        return result.toString();
    }
    
    public String regDesdobrarConta(Conta conta){
        Object result = Call.callSampleFunction("FUNC_DESDOBRA_ACCOUNT", Types.VARCHAR, 
               Integer.valueOf(SessionUtil.getUserlogado().getId()+""),
                Integer.valueOf(conta.getIdAccount()),
                 Integer.valueOf(conta.getNumConta().substring(conta.getNumConta().length()-1, conta.getNumConta().length())),
                   conta.getDesignacao()
                );
        return result+"";
    }

    public ResultSet accountReport() {
        String tipoConta = "TIPO CONTA";
        ResultSet rs = Call.selectFrom("VER_CONTA_CONTABIL WHERE \"" + tipoConta + "\"='Conta Banco'", "*");
        return rs;
    }

    public List<Recebimento> contratoClienteContabilidade(String quantidaRegistro, String filtro) 
    {
        ResultSet rs;
        String estado ="ESTADO PAGAMENT";
        List<Recebimento> lista = new ArrayList<>();
        if (quantidaRegistro.equals("metade")) 
            rs = Call.selectFrom("VER_CONTRATO_NOPAY WHERE ROWNUM<=15", "*");
        else
        {
            switch (filtro) 
            {
                case "todos":
                    rs = Call.selectFrom("VER_CONTRATO_NOPAY", "*");
                    break;
                case "não pagos":
                    rs = Call.selectFrom("VER_CONTRATO_NOPAY WHERE \"" + estado + "\"=2", "*");
                    break;
                default:
                    rs = Call.selectFrom("VER_CONTRATO_NOPAY WHERE \"" + estado + "\"=0", "*");
                    break;
            }
        }

        if (rs != null) {
            try {
                while (rs.next()) {
                    Recebimento r = new Recebimento();
                    r.setId(rs.getString("ID"));
                    r.setCliente(rs.getString("CLIENTE"));
                    r.setApolice(rs.getString("APOLICE"));
                    r.setCodigoSeguro(rs.getString("SEGURO"));
                    r.setValorPagar(rs.getString("VALOR PAGRA"));
                    r.setValorPago(rs.getString("PAGO"));
                    r.setMoeda(rs.getString("MOEDA"));
                    r.setEstado(rs.getString("PAGAMENTO"));
                    r.setIdMoeda(rs.getString("ID MOEDA"));
                    r.setDataApolice(rs.getObject("DATA_CONTRATO_SF"));
                    lista.add(r);               
                }
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("tamanho da lista 1 " + lista.size());
        return lista;
    }

    public List<Prestacao> carregarPrestacoes(int idContrato) {
        ResultSet rs;
        List<Prestacao> prestacoes = new ArrayList<>();
        rs = Call.callTableFunction("PACK_CONTA.loadPrestacaoContrato", "*", idContrato);
        if (rs != null) {
            try {
                while (rs.next()) {
                    Prestacao prestacao = new Prestacao();
                    prestacao.setId(rs.getString("ID"));
                    prestacao.setValor(rs.getString("VALOR"));
                    prestacao.setValorSF(rs.getString("VALOR_SF"));
                    prestacao.setValorPagoPrestacao(rs.getString("PAGO"));
                    prestacao.setValorPagoSF(rs.getString("PAGO_SF"));
                    prestacao.setEstadoPrestacao(rs.getString("ESTADO"));
                    prestacao.setDataRegistro(rs.getString("DATA"));
                    prestacoes.add(prestacao);

                }
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return prestacoes;
    }

    public String convertToSTD(float value, int moeda) {
        String functionName = "PACK_CONTA.toSTD";
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, value, moeda);
        return resp.toString();
    }

    public String convertFromSTD(float stdValue, int moeda) {
        String functionName = "PACK_CONTA.fromSTD";
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, stdValue, moeda);
        return resp.toString();
    }

    public Object regAmortizacao(Prestacao prestacao) {
        String functionName = "FUNC_REG_AMORTIZCAO";
        Object resp = null;
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                Integer.valueOf(prestacao.getId()),
                idUser,
                Float.valueOf(prestacao.getValorSF()),
                ((prestacao.getNumDoc() == null || prestacao.getNumDoc().equals("")) ? null : prestacao.getNumDoc()),
                Integer.valueOf(prestacao.getFormaPagamento()),
                Integer.valueOf(prestacao.getConta()),
                prestacao.getStdValor()
        );
        return resp;
    }

    public List<Recebimento> pesquisar(String value) {
        List<Recebimento> filtro = new ArrayList<>();
        ResultSet rs;
        String campoNumero = "ID";
        String campoCliente = "CLIENTE";
        String camposSeguro = "SEGURO";
        String campoApolice = "APOLICE";
        String campoValorPagar = "VALOR PAGRA";
        if (value != null && !value.equals("")) {
            String sql = "VER_CONTRATO_NOPAY WHERE UPPER(\"" + campoNumero + "\") LIKE UPPER('%" + value + "%') "
                    + "OR UPPER(\"" + campoCliente + "\") LIKE UPPER('%" + value + "%')"
                    + "OR UPPER(\"" + campoApolice + "\") LIKE UPPER('%" + value + "%')"
                    + "OR UPPER(\"" + camposSeguro + "\") LIKE UPPER('%" + value + "%')"
                    + "OR UPPER(\"" + campoValorPagar + "\") LIKE UPPER('%" + value + "%')";
            rs = Call.selectFrom(sql, "*");
            if (rs != null) {
                try {
                    while (rs.next()) {
                        Recebimento r = new Recebimento();
                        r.setId(rs.getString("ID"));
                        r.setCliente(rs.getString("CLIENTE"));
                        r.setApolice(rs.getString("APOLICE"));
                        r.setCodigoSeguro(rs.getString("SEGURO"));
                        r.setValorPagar(rs.getString("VALOR PAGRA"));
                        r.setValorPago(rs.getString("PAGO"));
                        r.setMoeda(rs.getString("MOEDA"));
                        r.setEstado(rs.getString("PAGAMENTO"));
                        r.setIdMoeda(rs.getString("ID MOEDA"));
                        r.setDataApolice(rs.getObject("DATA_CONTRATO_SF"));
                        filtro.add(r);
                    }
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            rs = Call.selectFrom("VER_CONTRATO_NOPAY", "*");
            if (rs != null) {
                try {
                    while (rs.next()) {
                        Recebimento r = new Recebimento();
                        r.setId(rs.getString("ID"));
                        r.setCliente(rs.getString("CLIENTE"));
                        r.setApolice(rs.getString("APOLICE"));
                        r.setCodigoSeguro(rs.getString("SEGURO"));
                        r.setValorPagar(rs.getString("VALOR PAGRA"));
                        r.setValorPago(rs.getString("PAGO"));
                        r.setMoeda(rs.getString("MOEDA"));
                        r.setEstado(rs.getString("PAGAMENTO"));
                        r.setIdMoeda(rs.getString("ID MOEDA"));
                        filtro.add(r);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return filtro;
    }

    public List<String> contas() {
        List<String> accounts = new ArrayList<>();
        ResultSet resultSet;
//        resultSet = Call.selectFrom("VER_CONTA_PAYMENT", "*");
        resultSet = Call.selectFrom("VER_ACCOUNT where STATE = 1", "*");
        
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    accounts.add(resultSet.getString("NUMBER"));
                }
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return accounts;
    }

    
    public String validarContaPagamento(String contaPagamento) {
        ResultSet rs;
        boolean valido = true;
        String id = null;
//        rs = Call.selectFrom("VER_CONTA_PAYMENT WHERE COD=?", "ID", contaPagamento);
        rs = Call.selectFrom("VER_ACCOUNT WHERE STATE = 1 and \"NUMBER\"=?", "ID", contaPagamento);
        if (rs != null) {
            try {
                while (rs.next()) {
                    id = rs.getString("ID");
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }
    public static String loadAccountId(String contaPagamento) {
        ResultSet rs;
  
        String id = null;
        String field = "ID ACCOUNT";
        rs = Call.selectFrom("VER_ACCOUNTBANK WHERE ID=?","*", contaPagamento);
        if (rs != null) {
            try {
                while (rs.next()) {
                    id = rs.getString(field);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    public String proximoCodigoPagamento(int idModoPagamento) {
        String functionName = "PACK_CONTA.nextPaymentCod";
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, idModoPagamento);
        return ((resp == null) ? "" : resp.toString());
    }

    public Object registrarPagamento(Pagamento p, int type, String cheque)
    {
        double valorTotal = p.getTotalDebito() - p.getTotalCredito();
        String functionName = "PACK_CONTA.func_reg_payemnt";
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                idUser,
                    Integer.valueOf(loadAccountId(p.getContaBanco())),
                     Integer.valueOf(p.getContaBanco()),
                OperacaoData.toSQLDate(p.getDataPagamento()),
                valorTotal,
                Integer.valueOf(p.getTipoPagamento()),
                Integer.valueOf(p.getFormaPagamento()),
               (( p.getNumero() == null || p.getNumero().equals(""))? null :p.getNumero()),
                  p.getIdPagamento(),
                  type,
                  ((p.getCheque() == null || p.getCheque().equals("")) ? null :p.getCheque()) // normal - envia cheque + os numeros digitados e passado ennvia null
                );
        return resp;
    }

    public Object registrarItemPagamento(Pagamento pagamento, int codigo, float quantidade) {
        String functionName = "PACK_CONTA.func_reg_itempayment";
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                idUser,
                codigo,
                Integer.valueOf(pagamento.getContaPagamento()),
                pagamento.getNumDoc(),
                ((pagamento.getBeneficiario() == null || pagamento.getBeneficiario().equals("")) ? null : pagamento.getBeneficiario()),
                quantidade,
                Double.valueOf(pagamento.getValor()),
                pagamento.getDescricaoPagamento(),
                pagamento.getTypeMoviment().getId(),
                ((!pagamento.isHasRetencion()) ? 0 : 1)
        );
        return resp;
    }
    
    public String endPayment(int idPayment)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        return Call.callSampleFunction("PACK_CONTA.func_end_payment", Types.VARCHAR, idPayment, idUser).toString();
    }

    public String carregarNumCheque(int idContaBanco) {
        String functionName = "PACK_CONTA.funcGetNumCheque";
        ResultSet rs;
        Object resp = null;
        resp = Call.callSampleFunction(functionName, Types.VARCHAR, idContaBanco);
        return resp.toString();

    }

    public String[] validarNumeroCheque(int idContaBanco, String numeroCheque) {
        String functionName = "PACK_CONTA.validateNumCheque";
        Object result;
        String[] valores;
        result = Call.callSampleFunction(functionName, Types.VARCHAR, idContaBanco, numeroCheque);
        valores = result.toString().split(";");
        return valores;
    }

    public List<Pagamento> listaPagamentos(String value, String quantidadeRegistro, String filtro) {
        ResultSet rs;
        String campoCodigo = "ID";
        String contaBanco = "CONTA BANCO";
        String pagamentoE = "PAGAMENTO";
        String registro = "REGISTRO";
        String modoPagamento = "MODO PAGAMENTO";
        List<Pagamento> payment = new ArrayList<>();
        if (value == null || value.equals("")) 
        {
            if (quantidadeRegistro.equals("metade")) 
                rs = Call.selectFrom("VER_PAYMENT WHERE ROWNUM<=20", "*");
            else 
            {
                if(filtro.equals("todos"))
                    rs = Call.selectFrom("VER_PAYMENT", "*");
                else
                    rs = Call.selectFrom("VER_PAYMENT WHERE ESTADO='Anulado'", "*"); 
            }
            if (rs != null) {
                try {
                    while (rs.next()) {
                        Pagamento pagamento = new Pagamento();
                        pagamento.setIdPagamento(rs.getString("ID"));
                        pagamento.setCodigo(rs.getString("CODIGO"));
                        pagamento.setContaBanco(rs.getString("CONTA"));
                        pagamento.setValor(rs.getString("VALOR"));
                        pagamento.setPagamento(rs.getString("PAGAMENTO"));
                        pagamento.setTipoPagamento(rs.getString("MODO PAGAMENTO"));
                        pagamento.setDataRegistro(rs.getString("REGISTRO"));
                        pagamento.setEstado(rs.getString("ESTADO"));
                        payment.add(pagamento);
                    }
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            String sql = "VER_PAYMENT WHERE UPPER(\"" + campoCodigo + "\") LIKE UPPER('%" + value + "%') "
                    + "OR UPPER(\"" + contaBanco + "\") LIKE UPPER('%" + value + "%')"
                    + "OR UPPER(\"" + pagamentoE + "\") LIKE UPPER('%" + value + "%')"
                    + "OR UPPER(\"" + modoPagamento + "\") LIKE UPPER('%" + value + "%')"
                    + "OR UPPER(\"" + registro + "\") LIKE UPPER('%" + value + "%')";
            rs = Call.selectFrom(sql, "*");
            if (rs != null) {
                try {
                    while (rs.next()) {
                        Pagamento pagamento = new Pagamento();
                        pagamento.setIdPagamento(rs.getString("ID"));
                        pagamento.setCodigo(rs.getString("CODIGO"));
                        pagamento.setContaBanco(rs.getString("CONTA BANCO"));
                        pagamento.setValor(rs.getString("VALOR"));
                        pagamento.setPagamento(rs.getString("PAGAMENTO"));
                        pagamento.setTipoPagamento(rs.getString("MODO PAGAMENTO"));
                        pagamento.setTipoPagamento(rs.getString("MODO PAGAMENTO"));
                        pagamento.setDataRegistro(rs.getString("REGISTRO"));
                        payment.add(pagamento);
                    }
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return payment;
    }

    public String infoConta(String codigo) {
        String descricao = null;
        ResultSet rs;
//        rs = Call.selectFrom("VER_CONTA_PAYMENT WHERE COD=?", "*", codigo);
        rs = Call.selectFrom("VER_ACCOUNT where STATE = 1 and \"NUMBER\"=?", "*", codigo);
        if (rs != null) {
            try {
                while (rs.next()) {
                    descricao = rs.getString("DESCRISION");
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return descricao;
    }

    public ResultSet relatorioPagamento(Relatorio relatorio) {
        ResultSet rs = null;
        String sql;

        String functionName = "PACK_REPORT.reportPagamento";
        if (relatorio.getValorPesquisa() == null || relatorio.getValorPesquisa().equals("")) {
            rs = Call.callTableFunction(functionName, "*",
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
        } else {
            if (relatorio.getCampoPesquisa().equals("BENEFICIARIO")) 
            {
                rs = Call.selectFrom("TABLE(" + functionName + "(?,?)) WHERE UPPER(BENEFICIARIO) LIKE UPPER(?)", "*",
                        ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                        ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                        "%" + relatorio.getValorPesquisa() + "%");
            }
            else if(relatorio.getCampoPesquisa().equals("BANCO")) 
            {
                rs = Call.selectFrom("TABLE(" + functionName + "(?,?)) WHERE UPPER(BANCO) LIKE UPPER(?)", "*",
                        ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                        ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                        "%" + relatorio.getValorPesquisa() + "%");
            }
            else
            {
                 rs = Call.selectFrom("TABLE(" + functionName + "(?,?)) WHERE UPPER(PAGAMENTO) LIKE UPPER(?)", "*",
                        ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                        ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                        "%" + relatorio.getValorPesquisa() + "%");
            }

        }

        return rs;
    }

    public ResultSet relatorioRecebimento(Relatorio relatorio) {
        ResultSet rs = null;
        String functionName = "PACK_REPORT.reportRecebimento";
        if (relatorio.getValorPesquisa() == null || relatorio.getValorPesquisa().equals("")) {
            rs = Call.callTableFunction(functionName, "*",
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
        } else {
            rs = Call.selectFrom("TABLE(" + functionName + "(?,?)) WHERE UPPER(\"" + relatorio.getCampoPesquisa() + "\") LIKE UPPER(?)", "*",
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                    "%" + relatorio.getValorPesquisa() + "%");
        }
        return rs;
    }

    public ResultSet relatorioMapaProducao(Relatorio relatorio) {
        ResultSet rs = null;
        return rs;
    }

    public Object regMovimentoAndCheque(int i, Object o) {
        Object re = "";
        Cheque c = (Cheque) o;
        System.out.println(c.getNumBancoConta());
        double total = (Double.valueOf(c.getFim()) - Double.valueOf(c.getInicio())) + 1;
//              func_reg_chequeconta( idUser NUMBER , idAccount NUMBER , sequenceInicio VARCHAR2 , sequenceFim VARCHAR2 , ttCheques NUMBER ) RETURN VARCHAR2 ; 
        re = Call.callSampleFunction("PACK_CONTA.func_reg_chequeconta", Types.VARCHAR, SessionUtil.getUserlogado().getId(), c.getBanco(), c.getNumBancoConta() + c.getInicio(), c.getNumBancoConta() + c.getFim(), ((long) total));
        return re;
        
    }

    public static Object getNumContaBank(int conta) {
        return Call.callSampleFunction("PACK_CONTA.getNumConta", Types.VARCHAR, conta);
    }

    public ArrayList<Cheque> listaCheques(String search) {
        
        ArrayList<Cheque> lista = new ArrayList<>();
        if(search == null) rs = Call.selectFrom("VER_CHEQUE", "*");
        else rs = Call.selectFrom("VER_CHEQUE WHERE UPPER(CONTA) LIKE UPPER('%" + search + "%')", "*");
        
        if (rs != null) 
        {
            try 
            {
                while (rs.next()) 
                {
                    Cheque cheque = new Cheque();
                    cheque.setBanco(rs.getString("CONTA"));
                    cheque.setNumBancoConta(rs.getString("NIB"));
                    cheque.setInicio(rs.getString("SEQUENCIA INICIO"));
                    cheque.setFim(rs.getString("SEQUENCIA FIM"));
                    cheque.setTotal(rs.getString("TOTAL CHEQUES"));
                    lista.add(cheque);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }

    /**
     * Relatório de produção de contabilidade
     *
     * @param relatorio
     * @return
     */
    public ResultSet relatorioMapaproducao(Relatorio relatorio) {
        String functionName;
        ResultSet rs = null;
        if (relatorio.getCampoPesquisa() != null && !relatorio.getCampoPesquisa().equals("")) {
            if (Validacao.isNumeric(relatorio.getCampoPesquisa()) == false) {
                functionName = "PACK_REPORT.reportProducaoContaNotTravel";
                rs = Call.callTableFunction(functionName, "*",
                        ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                        ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                        1); // i da 
            } else {
                if (!relatorio.getCampoPesquisa().equals("3")) {
                    functionName = "PACK_REPORT.reportProducaoContaNotTravel";
                    if (relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals("")) {
                        String colunasSearch[] = {"SEGURO", "BANCO", "APOLICE", "CLIENTE"};
                        rs = Call.selectFiltredFrom("TABLE(" + functionName + "(?,?,?))", "*", relatorio.getValorPesquisa(), colunasSearch,
                                ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                                ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                                Integer.valueOf(relatorio.getCampoPesquisa()));
                    } else {
                        rs = Call.callTableFunction(functionName, "*",
                                ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                                ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                                Integer.valueOf(relatorio.getCampoPesquisa())); // i da 
                    }
                } else {
                    functionName = "PACK_REPORT.reportProducaoTravel";
                    if (relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals("")) {
                        String colunasSearch[] = {"APOLICE", "CLIENTE"};
                        rs = Call.selectFiltredFrom("TABLE(" + functionName + "(?,?))", "*", relatorio.getValorPesquisa(), colunasSearch,
                                ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                                ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
                    } else {
                        rs = Call.callTableFunction(functionName, "*",
                                ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                                ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
                    }
                }
            }
        } else {
            functionName = "PACK_REPORT.reportProducaoContaNotTravel";
            rs = Call.callTableFunction(functionName, "*",
                    ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                    ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                    1); // i da 
        }
        return rs;
    }

    public float relatorioTaxaProducaoValorNicon(Relatorio relatorio) {
        String functionName = "PACK_REPORT.reportTaxaNicon";
        ResultSet rs = null;
        float valorNicon = 0;
        rs = Call.callTableFunction(functionName, "*",
                ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())));
        if (rs != null) {
            try {
                while (rs.next()) {
                    valorNicon += Float.valueOf(rs.getString("COLUMN_VALUE")); // SOMA TODOS OS VALORES DA COLUNA
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valorNicon;
    }

    public ResultSet relatoriotaxaProducao(Relatorio relatorio) {
        ResultSet rs = null;
        String functionName = "PACK_REPORT.reportTaxaProducao";
        rs = Call.callTableFunction(functionName, "*",
                ((relatorio.getDataInicio() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataInicio())),
                ((relatorio.getDataFim() == null) ? null : OperacaoData.toSQLDate(relatorio.getDataFim())),
                this.relatorioTaxaProducaoValorNicon(relatorio));
        return rs;
    }

    public void relatoriotaxa(Relatorio relatorio, List<Relatorio> listTaxaSalario, List<Relatorio> listTaxaSegurancaSocial) {
        ResultSet rs = null;

        Date data = null;
        if (relatorio.getDataInicio() != null) {
            data = OperacaoData.toSQLDate(relatorio.getDataInicio());
        }

        rs = Call.callTableFunction("PACK_REPORT2.reportSalarioTaxa", "*", data);

        if (rs != null) {
            try {
                while (rs.next()) {
                    if (rs.getString("TIPO TAXA").equals("IRS")) {
                        listTaxaSalario.add(new Relatorio(rs.getString("NOME"), rs.getString("VALOR")));
                    } else {
                        listTaxaSegurancaSocial.add(new Relatorio(rs.getString("NOME"), rs.getString("VALOR")));
                    }
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Validacao.atualizar("contabilidadeRelatorioForm", "contabilidadeTabelaSecurityTax", "contabilidadeTabelaTaxaSalario");

    }
    
    public List<CreditoDebito> launchs(String search)
    {
         ResultSet rs;
         List<CreditoDebito> listaLancamentos = new ArrayList<>();
         System.out.println("pesquisa "+search);
         if(search != null && !search.equals("")) 
               rs = Call.selectFrom("VER_OPERACOES WHERE UPPER(CODIGO) LIKE UPPER('%" + search + "%') OR UPPER(TIPO) LIKE UPPER('%" + search + "%')", "*");  
         else rs = Call.selectFrom("VER_OPERACOES", "*");
        
         if(rs != null)
        {
               try 
               {
                   while(rs.next())
                   {
                       CreditoDebito creditoDebito = new CreditoDebito();
                       creditoDebito.setCodigo(rs.getString("CODIGO"));
                       creditoDebito.setId(rs.getInt("ID"));
                       creditoDebito.setTypeOperationDesc(rs.getString("OPERACAO"));
                       creditoDebito.setValor(rs.getString("VALOR"));
                       creditoDebito.setColaborador(rs.getString("COLABORADOR"));
                       creditoDebito.setTipo(rs.getString("TIPO"));
                       creditoDebito.setDataRegistro(rs.getString("REGISTRO"));
                       creditoDebito.setOperation(rs.getString("OP"));
                       
                       listaLancamentos.add(creditoDebito);
                   }
                   rs.close();
               }
               catch (SQLException ex) {
                   Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
               }
        }
        return listaLancamentos;
    }
    
    public List<CreditoDebito> listCredits(int id, String tipo)
    {
         ResultSet rs = null;
         String field;
        if(tipo.equalsIgnoreCase("p"))
        {
            field= "ID PAGAMENTO";
           rs = Call.selectFrom("VER_CONTA_MOVIMENTO_PAGAMENTO WHERE \"" + field + "\"=?", "*",id);
        }
        else if(tipo.equalsIgnoreCase("l"))
        {
             field= "ID LANCAMENTO";
            rs = Call.selectFrom("VER_CONTA_MOVIMENTO_LANCAMENTO WHERE \"" + field + "\"=?", "*",id);
        }
        else
            rs = Call.selectFrom("VER_CONTA_MOVIMENTO_RECEBIMENT WHERE ID=?", "*",id);
        
        
        List<CreditoDebito> creditoDebitos = new ArrayList<>();
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    CreditoDebito cd = new CreditoDebito();
                     cd.setConta(rs.getString("CONTA"));
                     if(tipo.equalsIgnoreCase("l"))
                     {
                        if(rs.getString("MOVIMENTACAO").equals("Credito")) 
                            cd.setValorC(rs.getString("VALOR"));
                        else
                            cd.setValorD(rs.getString("VALOR"));
                     }
                     else
                     {
                        if(rs.getString("MOVIMENTO").equals("Credito"))
                            cd.setValorC(rs.getString("VALOR"));
                        else
                            cd.setValorD(rs.getString("VALOR"));
                     }
                    creditoDebitos.add(cd);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return creditoDebitos;
    }

    public ResultSet relatorioBalancete(Relatorio relatorio) {
        ResultSet rs = null;
        String functionName = "PACK_REPORT2.reportBalancete";
        if (relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals("")) {

            String colunasSearch[] = {"CONTA", "DESIGNACAO"};
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
    
     public ResultSet relatorioContas(Relatorio relatorio)
     {
        ResultSet rs = null;
        if (relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals("")) 
        {
            rs = Call.selectFrom("VER_CONTA_MOVIMENTO WHERE UPPER(CONTA) LIKE ('%" + relatorio.getValorPesquisa() + "%') AND DATA_SF BETWEEN ? AND ?", "*",
                OperacaoData.toSQLDate(relatorio.getDataInicio()),
                OperacaoData.toSQLDate(relatorio.getDataFim()) );
        } 
        else
        {
            rs = Call.selectFrom("VER_CONTA_MOVIMENTO WHERE DATA_SF BETWEEN ? AND ? ", "*", 
                    OperacaoData.toSQLDate(relatorio.getDataInicio()),
                    OperacaoData.toSQLDate(relatorio.getDataFim()) 
            );
            
        }
        return rs;
     }

    public String updateAccount(Conta conta) {
        String functionName = "PACK_CONTA.funcUpdateAccount";
        Object result = Call.callSampleFunction(functionName, Types.VARCHAR,
                conta.getIdAccount(),
                Integer.valueOf(SessionUtil.getUserlogado().getId()+""),
               conta.getDesignacao()
            );
        return result+"";
    }

    public String anularPagamento(int idPayment, String obs) {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        Object result = Call.callSampleFunction("PACK_CONTA.functAlterStatePayment", Types.VARCHAR,
                idUser,
                idPayment,
                -1,
                obs);
        return result.toString();
    }
    
    public String regSolicitacaoPagamento(int idRequisicaoPagamento, int idPagamento)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        Object result = Call.callSampleFunction("PACK_SINISTRO.FUNC_PAY_SINISTRO ", Types.VARCHAR,
                idUser,
                idRequisicaoPagamento,
                idPagamento
            );
       return result.toString();
    }
    
    public String launchSeuquenceNumber()
    {
        return Call.callSampleFunction("PACK_CONTA1.func_preview_lancamento_seq", Types.VARCHAR).toString();
    }
    
    public String regLaunch(CreditoDebito creditoDebito, int totalAccount)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        Object resp = Call.callSampleFunction("PACK_CONTA1.func_reg_lancamento", Types.VARCHAR,
                idUser,
                creditoDebito.getTypeLaunch(),
                totalAccount,
                OperacaoData.toSQLDate(creditoDebito.getData())
        );
        return resp.toString();  
    }
    
    public String regLaunchAccount(int idLaunch,CreditoDebito creditoDebito)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        Object resp = Call.callSampleFunction("PACK_CONTA1.func_reg_lancamentoaccount ", Types.VARCHAR, 
                idLaunch, 
                idUser,
                creditoDebito.getTypeOperation(),
                Integer.valueOf(getAccountInfo("id", creditoDebito.getConta())),
                creditoDebito.getMoeda(),
                creditoDebito.getNumeroDucumento(),
                ((creditoDebito.getDesc() != null && !creditoDebito.getDesc().equals("")) ? creditoDebito.getDesc() : null),
                OperacaoData.toSQLDate(creditoDebito.getDataDocumento()),
                creditoDebito.getMovimentoDesc(),
                Double.valueOf(Validacao.unformat(creditoDebito.getValor()))
                );
        return resp.toString();
    }
    
    public String endLaunch(int idLaunch)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        return Call.callSampleFunction("PACK_CONTA1.func_end_lancamento", Types.VARCHAR, idUser, idLaunch).toString();
    }
    
    public String disableOperation(int idOperation)
    {
         int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        return Call.callSampleFunction("FUNCT_CHANGE_OPERATION_DISABLE", Types.VARCHAR, idOperation, idUser).toString();
    }
    
    public List<ComoBox> bankAccounts()
    {
//        ResultSet rs = Call.selectFrom("VER_ALL_ACCOUNT WHERE TP='B'", "*");
        /**
         * por perguntar
         */
        rs = Call.selectFrom("VER_ACCOUNTBANK", "*");
        List<ComoBox> list = new ArrayList<>();
        if(rs != null)
        {
            try {
                while(rs.next())
                {
                    ComoBox cb = new ComoBox(rs.getString("ID"), rs.getString("NAME"), rs.getDouble("SALDO"));
                    list.add(cb);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return list; 
    }
    
    
    public List<Conta> listaContaRaizMovimentavel(){
        List<Conta> list = new ArrayList<>();
        ResultSet rs = Call.selectFrom("VER_CONTA_MOVIMENTAVEL", "*");
        try {
            while (rs.next()) {
//                ACCOUNT_ID	ACCOUNT_NUMBER	ACCOUNT_DESC
                Conta c = new Conta();
                c.setIdAccount(rs.getInt("ACCOUNT_ID"));
                c.setNumRaiz(rs.getString("ACCOUNT_NUMBER"));
                c.setDesignacao(rs.getString("ACCOUNT_DESC"));
                list.add(c);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Conta> listaContaRaiz(int typeOp, String value,Integer typePrint){
         List<Conta> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date();
        String ano = dateFormat.format(d);
        int anoAtual = Integer.valueOf(ano.substring(6, 10));
        ResultSet rs;
        String fieldNumber ="NUMBER", fieldDescription = "DESCRISION", fieldMov = "STATE OBS";
        
        if(typeOp == 1)
             rs = Call.selectFrom("VER_ACCOUNT", "*");
        else{
          if("".equals(value))
                  rs = Call.selectFrom("VER_ACCOUNT", "*");
          else{
               String sql = "VER_ACCOUNT WHERE UPPER(\"" + fieldNumber + "\") LIKE UPPER('%" + value + "%') "
                + "OR UPPER(\"" + fieldDescription + "\") LIKE UPPER('%" + value + "%') OR UPPER(\"" + fieldMov + "\") LIKE UPPER('%" + value + "%')"
                       ;
                rs = Call.selectFrom(sql, "*");
              
            }  
        }
        
         if(rs != null && typePrint == null){
             try {
                 while(rs.next()){
                    if(rs.getInt("YEAR") == anoAtual){
                        Conta c = new Conta();
                        c.setIdAccount(rs.getInt("ID"));
                        c.setNumRaiz(rs.getString("NUMBER"));
                        c.setDesignacao(rs.getString("DESCRISION"));
                        c.setTipoConta(rs.getString("TYPE"));
                        c.setObs(rs.getString("STATE OBS"));
                        c.setNivel(rs.getString("LEVEL"));
                        c.setCredito(rs.getString("CREDITO"));
                        c.setCredito(Moeda.format(Double.valueOf(c.getCredito())));
                        c.setDebito(rs.getString("DEBITO"));
                        c.setDebito(Moeda.format(Double.valueOf(c.getDebito())));
                        c.setSaldo(rs.getString("SALDO"));
                        c.setSaldo(Moeda.format(Double.valueOf(c.getSaldo())));
                        c.setEstado(rs.getInt("STATE"));
                        list.add(c);
                   }
                 }
                rs.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
             }
       
         }
         
        if (typePrint != null) {
            DataTableControl clienteControl = new DataTableControl("id55", "clienteff.fjfjf");
            clienteControl.prepareModel(rs, DataTableControl.ShowMode.SHOW, "NUMBER","DESCRISION", "TYPE", "CREDITO", "DEBITO", "SALDO");
            clienteControl.renameColumn("NUMBER", "Número");
            clienteControl.renameColumn("TYPE", "Tipo");
            clienteControl.renameColumn("CREDITO", "Crédito");
            clienteControl.renameColumn("DEBITO", "Débito");
            clienteControl.renameColumn("SALDO", "Saldo");
            clienteControl.updFaces(FacesContext.getCurrentInstance());
            if (typePrint == 88) {
                GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Conta", "Relatório de Conta", clienteControl, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1);
            } else {
                GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Conta", "Relatório de Conta", clienteControl, -1);
            }
        }
         
         return list;
    }
    
   public String getAccountInfo(String field, String account)
   {
       for(Conta c: listaContaRaiz(1, null, null) )
       {
           if(field.equals("desc"))
           {
                if(account.trim().equals(c.getNumRaiz()))
                    return c.getDesignacao();
           }
           else
           {
                if(account.trim().equals(c.getNumRaiz()))
                    return c.getIdAccount()+"";
           }
       }
       return null;
   }
    
   public List<ComoBox> listOperationValue(String operationCode)
   {
       List<ComoBox> list = new ArrayList<>();
        rs = Call.selectFrom("T_OPERATIONVALUE WHERE OPRVAL_OPR_COD=?", "*", operationCode);
       
       if(rs != null)
       {
           try
           {
               while(rs.next())
               {
                   ComoBox cb = new ComoBox(rs.getString("OPRVAL_COD"), rs.getString("OPRVAL_NAME"));
                   list.add(cb);
               }
               rs.close();
           }
           catch (SQLException ex) {
               Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return list;
   }
   public List<ComoBox> listOperationDef(String operationCode)
   {
       List<ComoBox> list = new ArrayList<>();
       rs = Call.selectFrom("T_OPERATIONDEFINICAO WHERE OPRDEF_GROUP_COD=?", "*", operationCode);
       
       if(rs != null)
       {
           try
           {
               while(rs.next())
               {
                   ComoBox cb = new ComoBox(rs.getString("OPRDEF_COD"), rs.getString("OPRDEF_DESC"));
                   list.add(cb);
               }
               rs.close();
           }
           catch (SQLException ex) {
               Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return list;
   }
   
   public String regOperation(Conta conta, String definitionCode)
   {
       int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
       Object result = Call.callSampleFunction("FUNC_REG_OPERATION_ACCOUNT", Types.VARCHAR,
               idUser,
               conta.getIdAccount(),
               conta.getAccountOperationValue(),
               Integer.valueOf(conta.getTipoContaMovimento()),
               definitionCode
            );
       return result+"";
   }
   
   public List<Conta> listaOperacoes(int acctount)
   {
       List<Conta> list = new ArrayList<>();
       rs = Call.selectFrom("VER_OPERATION_ACCOUNT where ACCOUNT_ID=?", "*", acctount);
       
       if(rs != null)
       {
           try 
           {
               while(rs.next())
               {
                   Conta c = new Conta();
                   c.setIdAccount(rs.getInt("ID"));
                   c.setTipoContaMovimento(rs.getString("TYPEMOVIMENT"));
                   c.setConta(rs.getString("ACCOUNT"));
                   c.setDesignacao(rs.getString("GROUP_NAME"));
                   c.setObs(rs.getString("AFETAVEL_DESC"));
                   c.setValue(rs.getString("VALUE_NAME"));
                   list.add(c);
               }
               rs.close();
           }
            catch (SQLException ex) {
               Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return list;
   }
   
   public List<Conta> listaOperacoes()
   {
       List<Conta> list = new ArrayList<>();
       rs = Call.selectFrom("VER_OPERATION_ACCOUNT", "*");
       
       if(rs != null)
       {
           try 
           {
               while(rs.next())
               {
                   Conta c = new Conta();
                   c.setIdAccount(rs.getInt("ID"));
                   c.setTipoContaMovimento(rs.getString("TYPEMOVIMENT"));
                   c.setConta(rs.getString("ACCOUNT"));
                   c.setDesignacao(rs.getString("GROUP_NAME"));
                   c.setObs(rs.getString("AFETAVEL_DESC"));
                   c.setValue(rs.getString("VALUE_NAME"));
                   list.add(c);
               }
               rs.close();
           }
            catch (SQLException ex) {
               Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return list;
   }
   
   public List<String> launchAccounts()
   {
        List<String> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date();
        String ano = dateFormat.format(d);
        int anoAtual = Integer.valueOf(ano.substring(6, 10));
       rs = Call.selectFrom("VER_ACCOUNT WHERE STATE=1", "*");
       
        if(rs != null){
             try {
                 while(rs.next()){
                    if(rs.getInt("YEAR") == anoAtual){
                        list.add(rs.getString("NUMBER"));
                   }
                 }
                rs.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
             }    
         }
         return list;  
   }
   public List<ComoBox> getReceptAccounts()
   {
        List<ComoBox> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date();
        String ano = dateFormat.format(d);
        int anoAtual = Integer.valueOf(ano.substring(6, 10));
       rs = Call.selectFrom("VER_ACCOUNT WHERE STATE=1", "*");
       
        if(rs != null){
             try {
                 while(rs.next()){
                    if(rs.getInt("YEAR") == anoAtual){
                        ComoBox cb = new ComoBox(rs.getString("ID"), rs.getString("DESCRISION"));
                        list.add(cb);
                   }
                 }
                rs.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
             }    
         }
         return list;  
   }
}
