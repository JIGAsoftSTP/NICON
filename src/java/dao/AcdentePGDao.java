
package dao;

import modelo.Funcionario;
import conexao.Call;
import conexao.Conexao;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.AcidentePG;
import sessao.SessionUtil;
import validacao.OperacaoData;

public class AcdentePGDao implements Serializable
{
    private CallableStatement cs;
    private String sql;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private String resultado;
    
    /**
     * Registrar o tipo de cubertura do contrato
     * @param acidentepg
     * @param idContrato Identificacao do contrato
     * @return 
     */
    public String registrarAcidentepg(AcidentePG acidentepg, String idContrato)
    {
        Object resp = Call.callSampleFunction("FUNC_REGOBJ_ACIDENTESGRUPO", Types.VARCHAR,
               ((Funcionario)SessionUtil.obterValor("utilizador")).getId(),
                 idContrato,
                acidentepg.getNome(),
                acidentepg.getCategoria(),
                acidentepg.getProfissao(),
                OperacaoData.toSQLDate(acidentepg.getDataNascimento()),
                acidentepg.getValorMorte(),
                acidentepg.getTaxaMorte(),
                acidentepg.getDespesaMedica(),
                acidentepg.getTaxaDespesaMedica(),
                acidentepg.getIncapacidadeTotalTemporaria(),
                acidentepg.getTaxaIncapacidadeTemporaria(),
                acidentepg.getIncapacidadeTotal(),
                acidentepg.getTaxaIncapacidadePermanente(),
                acidentepg.getDefeitosFisicosCampo(),
                acidentepg.getAcidenteUltimos(),
                acidentepg.getCustoRepatriamento(),
                acidentepg.getTaxaCustoRepatriamento()
        );        
        return resp.toString();
    }  
    
    public String registrarInfo(String idContrato, int tipoCobertura)
    {
       resultado = (String) Call.callSampleFunction("FUNC_CTTINF_ACIDENTEGRUPO",Types.VARCHAR, ((Funcionario)SessionUtil.obterValor("utilizador")).getId(),idContrato,tipoCobertura);
       return resultado;
    }
}
