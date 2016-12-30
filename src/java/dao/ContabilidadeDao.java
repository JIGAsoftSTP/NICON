package dao;

import Export.GenericExcel;
import Export.GenericPDFs;
import bean.DataTableControl;
import conexao.Call;
import enumeracao.TipoPesquisa;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cheque;
import modelo.ComoBox;
import modelo.Conta;
import modelo.CreditoDebito;
import modelo.Funcionario;
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

    public Object registrarConta(Conta conta) 
    {
        String functionName = "FUNC_REG_CONTACONTABIL";
        String functionContaPagamento = "PACK_CONTA.func_reg_accountpayment";
        Object result = null;
        if (SessionUtil.obterValor(Funcionario.SESSION_NAME) != null) {
            int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
            if (conta.getTipoConta().equals("banco")) {
                result = Call.callSampleFunction(functionName, Types.VARCHAR,
                        idUser,
                        Integer.valueOf(conta.getBanco()),
                        3,
                        conta.getNumConta(),
                        Integer.valueOf(conta.getMoeda()),
                        conta.getConta(),
                        Integer.valueOf(conta.getTipoContaMovimento())
                );
            } else {
                result = Call.callSampleFunction(functionContaPagamento, Types.VARCHAR,
                        idUser,
                        Long.valueOf(conta.getNumContaPagamento()),
                        conta.getDesignacao()
                );
            }
        }
        return result;
    }

    public ResultSet accountReport() {
        String tipoConta = "TIPO CONTA";
        ResultSet rs = Call.selectFrom("VER_CONTA_CONTABIL WHERE \"" + tipoConta + "\"='Conta Banco'", "*");
        return rs;
    }

    public List<Conta> relatorioContas(String campoPesquisa, String valorPesquisa, String tipoContaS, boolean export, int type) {
        String sql;
        String campoConta = "COD";
        String descricao = "DESC";
        String tipoConta = "TIPO CONTA";
        System.out.println("tipo de conta " + tipoContaS + "\nCampo a pesquisar " + campoPesquisa);
        if ((valorPesquisa == null || valorPesquisa.equals("")) || (campoPesquisa == null || campoPesquisa.equals(""))) {
            if (tipoContaS.equals("Conta Pagamento")) {
                rs = Call.selectFrom("VER_CONTA_PAYMENT", "*");
                if (!export) {
                    dados("pagamento");
                }
            } else if (tipoContaS.equals("Conta Banco")) {
                rs = Call.selectFrom("VER_CONTA_CONTABIL WHERE \"" + tipoConta + "\"='Conta Banco'", "*");
                if (!export) {
                    dados("banco");
                }
            } else {
                rs = Call.selectFrom("VER_ALL_ACCOUNT", "*");
                if (!export) {
                    dados("todas");
                }
            }
        } else {
            if (tipoContaS.equals("Conta Pagamento")) {
                System.out.println("entrou conta pagamento");
                sql = "VER_CONTA_PAYMENT WHERE UPPER(\"" + campoConta + "\") LIKE UPPER('%" + valorPesquisa + "%') "
                        + "OR UPPER(\"" + descricao + "\") LIKE UPPER('%" + valorPesquisa + "%')";
                rs = Call.selectFrom(sql, "*");
                if (!export) {
                    dados("pagamento");
                }
            } else if (tipoContaS.equals("Conta Banco")) {
                rs = Call.selectFrom("VER_CONTA_CONTABIL WHERE UPPER(\"" + campoPesquisa + "\") LIKE UPPER('%" + valorPesquisa + "%') AND \"" + tipoConta + "\"='Conta Banco'", "*");
                if (!export) {
                    dados("banco");
                }
            } else {
                rs = Call.selectFrom("VER_ALL_ACCOUNT WHERE UPPER(\"" + campoPesquisa + "\") LIKE UPPER('%" + valorPesquisa + "%')", "*");
                if (!export) {
                    dados("todas");
                }
            }
            DataTableControl control;
            if (export && tipoContaS.equals("Conta Pagamento")) {
                control = new DataTableControl("id55", "clienteff.fjfjf");
                control.prepareModel(rs, DataTableControl.ShowMode.HIDE, "ID");
                control.renameColumn("COD", "Conta");
                control.renameColumn("DESC", "Descrição");
                if (export && type == 1) {
                    GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), tipoContaS, tipoContaS, control, GenericPDFs.OrientacaoPagina.VERTICAL, -1);
                } else if (export && type == 2) {
                    GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), tipoContaS, tipoContaS, control, -1);
                }
            } else if (export) {
                control = new DataTableControl("id55", "clienteff.fjfjf");
                control.prepareModel(rs, DataTableControl.ShowMode.HIDE, "ESTADO", "ID");
                control.renameColumn("DEBITO", "Valor Débito".toUpperCase());
                control.renameColumn("CREDITO", "Valor Crédito".toUpperCase());
                //            control.renameColumn("SALDO", "CONTACTO");
                control.renameColumn("NUMERO BANCARIO", "Número Bancário".toUpperCase());
                control.renameColumn("DESCRICAO", "CONTA");

                if (export && type == 1) {
                    GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), tipoContaS, tipoContaS, control, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1);
                } else if (export && type == 2) {
                    GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), tipoContaS, tipoContaS, control, -1);
                }
            }

            try {
                rs.close();
            } catch (Exception e) {
            }

        }

        return contas;
    }

    public void dados(String tipoConta) {
        if (tipoConta.equals("banco")) {
            try {
                if (rs != null) {
                    while (rs.next()) {
                        Conta conta = new Conta();
                        conta.setIdAccount(Integer.valueOf(rs.getString("ID")));
                        conta.setConta(rs.getString("DESCRICAO"));
                        conta.setBanco(rs.getString("BANCO"));
                        conta.setTipoContaMovimento(rs.getString("TIPO MOVIMENTO"));
                        conta.setMoeda(rs.getString("MOEDA"));
                        conta.setNumConta(rs.getString("NUMERO BANCARIO"));
                        conta.setSaldo(rs.getString("SALDO"));
                        conta.setCredito(rs.getString("CREDITO"));
                        conta.setDebito(rs.getString("DEBITO"));
                        conta.setEstado(rs.getString("ESTADO"));
                        conta.setRegistro(rs.getString("REGISTRO"));
                        contas.add(conta);
                    }
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (tipoConta.equals("pagamento")) {
            try {
                if (rs != null) {
                    while (rs.next()) {
                        Conta conta = new Conta();
                        conta.setIdAccount(Integer.valueOf(rs.getString("ID")));
                        conta.setConta(rs.getString("COD"));
                        conta.setDesignacao(rs.getString("DESC"));
                        conta.setCredito(rs.getString("CREDITO"));
                        contas.add(conta);
                    }
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                if (rs != null) {
                    while (rs.next()) {
                        Conta conta = new Conta();
                        conta.setIdAccount(Integer.valueOf(rs.getString("ID")));
                        conta.setConta(rs.getString("DESC"));
                        conta.setTipoConta(rs.getString("TIPO CONTA"));
                        conta.setDesignacao(rs.getString("DESC"));
                        conta.setTipoContaMovimento(rs.getString("TIPO CONTA BANCO"));
                        conta.setNumConta(rs.getString("COD"));
                        conta.setTipo(rs.getString("TP"));
                        contas.add(conta);
                    }
                    rs.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                    r.setDataApolice(rs.getObject("DTREG SF"));
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
                Integer.valueOf(prestacao.getConta())
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
        resultSet = Call.selectFrom("VER_CONTA_PAYMENT", "*");
        
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    accounts.add(resultSet.getString("COD"));
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
        rs = Call.selectFrom("VER_CONTA_PAYMENT WHERE COD=?", "ID", contaPagamento);
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
                pagamento.getTypeMoviment().getId()
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
                        pagamento.setContaBanco(rs.getString("CONTA BANCO"));
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
        rs = Call.selectFrom("VER_CONTA_PAYMENT WHERE COD=?", "*", codigo);
        if (rs != null) {
            try {
                while (rs.next()) {
                    descricao = rs.getString("DESC");
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
    
    public List<CreditoDebito> launchs(String search, int typeSearch)
    {
         ResultSet rs;
         List<CreditoDebito> listaLancamentos = new ArrayList<>();
         String numDoc = "MUMERO DOCUMENTO";
         System.out.println("pesquisa "+search);
         if(search != null && !search.equals("")) 
         {
             System.out.println("tipo S "+typeSearch);
                rs = Call.selectFrom("VER_OPERACOES WHERE UPPER(CODIGO) LIKE UPPER('%" + search + "%')", "*");  
         }
         else rs = Call.selectFrom("VER_OPERACOES", "*");
        
         if(rs != null)
        {
               try 
               {
                   while(rs.next())
                   {
                       CreditoDebito creditoDebito = new CreditoDebito();
                       creditoDebito.setCodigo(rs.getString("CODIGO"));
                       creditoDebito.setTypeOperationDesc(rs.getString("OPERACAO"));
                       creditoDebito.setValor(rs.getString("VALOR"));
                       creditoDebito.setColaborador(rs.getString("COLABORADOR"));
                       creditoDebito.setTipo(rs.getString("TIPO"));
                       creditoDebito.setDataRegistro(rs.getString("REGISTRO"));
                       
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
    
    public List<CreditoDebito> listCredits(String codigo, String tipo)
    {
         ResultSet rs;
         String field;
        if(tipo.contains("pagamento"))
        {
            field= "ID PAGAMENTO";
           rs = Call.selectFrom("VER_CONTA_MOVIMENTO_PAGAMENTO WHERE \"" + field + "\"=?", "*",codigo);
        }
        else
        {
             field= "ID LANCAMENTO";
            rs = Call.selectFrom("VER_CONTA_MOVIMENTO_LANCAMENTO WHERE \"" + field + "\"=?", "*",codigo);
        }
        
        List<CreditoDebito> creditoDebitos = new ArrayList<>();
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    CreditoDebito cd = new CreditoDebito();
                     cd.setConta(rs.getString("CONTA"));
                     if(tipo.contains("Lancamento"))
                     {
                        if(rs.getString("MOVIMENTACAO").equals("Credito"))  cd.setValorC(rs.getString("VALOR"));
                        else  cd.setValorD(rs.getString("VALOR"));
                     }
                     else
                     {
                        if(rs.getString("MOVIMENTO").equals("Credito"))  cd.setValorC(rs.getString("VALOR"));
                        else  cd.setValorD(rs.getString("VALOR"));
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

    public Object updateAccount(Conta conta) {
        String functionName = "PACK_CONTA.funcUpdateAccount";
        Object result = Call.callSampleFunction(functionName, Types.VARCHAR,
                conta.getIdAccount(),
                (conta.getDesignacao() == null || conta.getDesignacao().equals("")) ? 'B' : 'P',
                (conta.getDesignacao() == null || conta.getDesignacao().equals("")) ? conta.getNumConta() : conta.getDesignacao(),
                (conta.getTipoContaMovimento() == null || conta.getTipoContaMovimento().equals("")) ? null : Integer.valueOf(conta.getTipoContaMovimento()),
                (conta.getBanco() == null || conta.getBanco().equals("")) ? null : Integer.valueOf(conta.getBanco()));
        return result;
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
                Integer.valueOf(creditoDebito.getConta()),
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
    
    public List<ComoBox> bankAccounts()
    {
        ResultSet rs = Call.selectFrom("VER_ALL_ACCOUNT WHERE TP='B'", "*");
        List<ComoBox> list = new ArrayList<>();
        if(rs != null)
        {
            try {
                while(rs.next())
                {
                    ComoBox cb = new ComoBox(rs.getString("ID"), rs.getString("DESC"), rs.getDouble("SALDO SF"));
                    list.add(cb);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContabilidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list; 
    }
}
