/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.SalarioBean;
import conexao.Call;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.AvancoSalarial;
import modelo.ProcessamentoSalario;
import modelo.SituacaoFamiliar;
import modelo.Taxa;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;

/**
 *
 * @author ahmedjorge
 */
public class TaxaImpostoDao implements Serializable
{
    public static final String SALARIO_RESULTADO ="SALARIO.RESULT", SALARIO_FINAL_MES ="SALARIO FINAL MES", 
            SALARIO_MESSAGE ="SALARIO.MESSAGE",ESTRUTRA_ID="ESTRUTURA.ID", ESTRUTURA_TYPE_ID="ESTRUTURA.TYPE_ID",
            ESTRUTURA_TYPE_DESC="ESTRUTURA.TYPE_DESC", ESTRUTURA_LEVEL_ID="ESTRUTURA.LEVEL_ID",
             ESTRUTURA_LEVEL_DESC="ESTRUTURA.LEVEL_DESC", ESTRUTURA_BASE="ESTRUTURA.BASE",
                    ESTRUTURA_ALOJAMENTO="ESTRUTURA.ALOJAMENTO", ESTRUTURA_LANCHE="ESTRUTURA.LANCHE",
                   ESTRUTURA_TRANSPORTE="ESTRUTURA.TRANSPORTE", ESTRUTURA_BONUS_ALMOCO="ESTRUTURA.BONUSALMOCO",
                    ESTRUTURA_SALARIO="ESTRUTURA.SALARIO", SALARIO_OUT_BONUS_ALMOCO="SALARIO OUT BONUS ALMOCO",
                    ID_SS_FUNCIONARIO="ID SS FUNCIONARIO", ID_SS_EMPRESA="ID SS EMPRESA", PERCENTAGEM_SS_FUNCIONARIO="% SS FUNCIONARIO",
                 PERCENTAGEM_SS_EMPRESA="% SS EMPRESA", SS_FUNCIONAIRO="SS FUNCIONARIO", SALARIO_OUT_SS_FUNCIONARIO="SALARIO OUT SS FUNCIONARIO",
                    ID_COMISSAO="ID COMISAO", VALOR_COMISSAO="VALOR COMISAO", SALARIO_COM_COMISSAO="SALARIO COM COMISAO",
                    CONTRATOS_COMISSAO="COTRATOS COMISAO", ID_IRS="ID IRS", PERCENTAGEM_IRS="% IRS",
                    IRS_PARCELA_BATER="IRS PARCELA BATER", IRS="IRS", IRS_APURADO="IRS APURADO",
                    ID_SITUACAO_FAMILIAR="IS SITUACAO FAMILIAR", NUMERO_FILHOS="NUMERO FILHOS",
                    VALOR_SITUACAO_FAMILIAR="VALOR SITUACAO FAMILIAR", ID_SALARIO_BASE_NACIONAL="ID SALARIO BASE NACIONAL",
                    VALOR_SALARIO_BASE_NACIONAL="VALOR SALARIO BASE NACIONAL", SITUACAO_FAMILIAR_TOTAL="SIUACAO FAMILIAR TOTAL",
                    IRS_LIQUIDO="IRS LIQUIDO", SALARIO_MES="SALARIO MES", ID_AVANCO_SALARIAL="IS AVANCO SALARIAL",
                    VALOR_AVANCO_SALARIAL="VALOR AVANCO SALARIAL",SS_EMPRESA="SS EMPRESA",SALARIO_IMPOSTO_EMPRESA="SALARIO IMPOSTO EMPRESA";
        
    
    public static Object regAvancoSalarial(AvancoSalarial as)
    { 
        return  Call.callSampleFunction("PACK_RH.funcRegAvancoSalarial", Types.VARCHAR, 
                SessionUtil.getUserlogado().getId(),
                as.getFuncionario(),
                as.getNumDoc(),
                (as.getOjs() == null || as.getOjs().equals("")? null : as.getOjs()),
                as.getValor(),
                OperacaoData.toSQLDate(as.getData())
        );
    }
    
    public static Object cancelarAvancoSalarial(int idAvancoSalarial)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
        return Call.callSampleFunction("PACK_RH.funcCanselAvancoSalarial", Types.VARCHAR, idUser,idAvancoSalarial);
    }
    
    //funccancelavancosalarial
    
    public static List<AvancoSalarial> loadAvancoSalarial(String idFun, Date dataInicio, Date dataFim)
    {   
//        ID	DATA	VALOR	DOCUMENTO	OBSERVACAO	REGISTRO	ESTADO
       ResultSet rs = Call.callTableFunction("PACK_RH.loadAvancoSalarial", "*" , idFun, dataInicio, dataFim);
        
       List<AvancoSalarial> list = new ArrayList<>();
       Consumer <HashMap<String, Object>> act  = (HashMap<String, Object> map) -> 
       {
           AvancoSalarial ass = new AvancoSalarial();
           ass.setId(toString(map.get("ID")));
           ass.setDataView(toString(map.get("DATA")));
           ass.setFuncionario(toString(map.get("FUNCIONARIO")));
           ass.setNumDoc(toString(map.get("DOCUMENTO")));
           ass.setValor(toString(map.get("VALOR")));
           ass.setOjs(toString(map.get("OBSERVACAO")));
           ass.setEstado(toString(map.get("ESTADO")));
           ass.setRegistro(toString(map.get("REGISTRO")));
           list.add(ass);
       };
       Call.forEchaResultSet(act, rs);
      
        return list;
    }
    
    public static ArrayList<SituacaoFamiliar> loadSituacaoFamiliar()
    {   
//        NUMERO FILHO	PERCENTAGEM	VALOR
       ResultSet rs = Call.selectFrom("VER_SITUACAOFAMILIAR", "*");
       
       ArrayList<SituacaoFamiliar> list = new ArrayList<>();
       Consumer <HashMap<String, Object>> act  = (HashMap<String, Object> map) -> 
       {
           SituacaoFamiliar sf = new SituacaoFamiliar();
           sf.setPercentagem(Float.valueOf(toString(map.get("PERCENTAGEM"))));
           sf.setValor(Moeda.format(Double.valueOf(map.get("VALOR")+"")/100));
           sf.setNumFilho(Integer.valueOf(toString(map.get("NUMERO FILHO"))));
           list.add(sf);
       };
       Call.forEchaResultSet(act, rs);
       
        return list;
    }
    
    public static String toString(Object o){
        return ((o == null) ? "" : o.toString());
    }
    
    public static float getSaralioBase(){   
        Object o = Call.callSampleFunction("PACK_RH.loadSalarioBaseNacional", Types.FLOAT,SessionUtil.getUserlogado().getId());
        return Float.parseFloat(toString(o));
    }
    
    public static HashMap<String,Object> processamentoSalario (int idFuncionario, String tipo)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        ResultSet rs = Call.callTableFunction("PACK_RH.functPreviexProcessSalario", "*", idUser, idFuncionario);
        HashMap<String,Object> process = new HashMap<>();
        if(tipo.equals("avanço"))
        {
            if(rs != null)
            {
                try
                {
                    while(rs.next())
                    {
                        if(rs.getString(SALARIO_RESULTADO).equals("true"))
                        {
                            process.put(SALARIO_RESULTADO, "true");
                            process.put(SALARIO_FINAL_MES, rs.getString(SALARIO_FINAL_MES));
                        }
                        else
                        {
                            process.put(SALARIO_RESULTADO, "false");
                            process.put(SALARIO_MESSAGE, rs.getString(SALARIO_MESSAGE));
                        }
                    }
                    rs.close();
                }
                catch (SQLException ex) {
                    Logger.getLogger(TaxaImpostoDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            if(rs != null)
            {
                try {
                    while(rs.next())
                    {
                        if(rs.getString(SALARIO_RESULTADO).equals("true"))
                        {
                            process.put(SALARIO_IMPOSTO_EMPRESA, rs.getString(SALARIO_IMPOSTO_EMPRESA));
                            process.put(SS_EMPRESA, rs.getString(SS_EMPRESA));
                            process.put(SALARIO_FINAL_MES, rs.getString(SALARIO_FINAL_MES));
                            process.put(VALOR_AVANCO_SALARIAL, ((rs.getString(VALOR_AVANCO_SALARIAL)==null)? "":Moeda.format(Double.valueOf(rs.getString(VALOR_AVANCO_SALARIAL)+""))));
                            process.put(SALARIO_MES, rs.getString(SALARIO_MES));
                            process.put(IRS_LIQUIDO, rs.getString(IRS_LIQUIDO));
                            process.put(SITUACAO_FAMILIAR_TOTAL, ((rs.getString(SITUACAO_FAMILIAR_TOTAL)==null)? "":Moeda.format(Double.valueOf(rs.getString(SITUACAO_FAMILIAR_TOTAL)+""))));
                            process.put(ID_SALARIO_BASE_NACIONAL, rs.getString(ID_SALARIO_BASE_NACIONAL));
                            process.put(VALOR_SITUACAO_FAMILIAR, rs.getString(VALOR_SITUACAO_FAMILIAR));
                            process.put(IRS_APURADO, rs.getString(IRS_APURADO));
                            process.put(IRS,  rs.getString(IRS));
                            process.put(IRS_PARCELA_BATER, ((rs.getString(IRS_PARCELA_BATER)==null)? "":Moeda.format(Double.valueOf(rs.getString(IRS_PARCELA_BATER)+""))));
                            process.put(PERCENTAGEM_IRS, rs.getString(PERCENTAGEM_IRS));
                            process.put(ID_IRS, rs.getString(ID_IRS));
                            process.put(CONTRATOS_COMISSAO, rs.getString(CONTRATOS_COMISSAO));
                            process.put(SALARIO_COM_COMISSAO, rs.getString(SALARIO_COM_COMISSAO));
                            process.put(VALOR_COMISSAO,((rs.getString(VALOR_COMISSAO)==null)? "":Moeda.format(Double.valueOf(rs.getString(VALOR_COMISSAO)+""))));
                            process.put(SALARIO_OUT_SS_FUNCIONARIO, rs.getString(SALARIO_OUT_SS_FUNCIONARIO));
                            process.put(SS_FUNCIONAIRO, rs.getString(SS_FUNCIONAIRO));
                            process.put(SALARIO_OUT_BONUS_ALMOCO, rs.getString(SALARIO_OUT_BONUS_ALMOCO));
                            process.put(ESTRUTURA_SALARIO, ((rs.getString(ESTRUTURA_SALARIO)==null)? "":Moeda.format(Double.valueOf(rs.getString(ESTRUTURA_SALARIO)+""))));
                            process.put(ESTRUTURA_BONUS_ALMOCO, ((rs.getString(ESTRUTURA_BONUS_ALMOCO)==null)? "":Moeda.format(Double.valueOf(rs.getString(ESTRUTURA_BONUS_ALMOCO)+""))));
                            process.put(ESTRUTURA_TRANSPORTE, ((rs.getString(ESTRUTURA_TRANSPORTE)==null)? "":Moeda.format(Double.valueOf(rs.getString(ESTRUTURA_TRANSPORTE)+""))));
                            process.put(ESTRUTURA_LANCHE, ((rs.getString(ESTRUTURA_LANCHE)==null)? "":Moeda.format(Double.valueOf(rs.getString(ESTRUTURA_LANCHE)+""))));
                            process.put(ESTRUTURA_ALOJAMENTO,((rs.getString(ESTRUTURA_ALOJAMENTO)==null)? "":Moeda.format(Double.valueOf(rs.getString(ESTRUTURA_ALOJAMENTO)+""))));
                            process.put(ESTRUTURA_BASE, ((rs.getString(ESTRUTURA_BASE)==null)? "":Moeda.format(Double.valueOf(rs.getString(ESTRUTURA_BASE)+""))));
                            process.put(VALOR_SALARIO_BASE_NACIONAL, ((rs.getString(VALOR_SALARIO_BASE_NACIONAL) == null)? "" :Moeda.format(Double.valueOf(rs.getString(VALOR_SALARIO_BASE_NACIONAL)+""))));        
                        }       
                    }
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TaxaImpostoDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return process;
    }
    public static String alterarSalarioBase(Float salario){
        Object o = Call.callSampleFunction("PACK_RH.funcDefinirSalarioBaseNacional", Types.VARCHAR,salario, SessionUtil.getUserlogado().getId());
        return toString(o);
    }
    
    public static String regSituacaoFamiliar(SituacaoFamiliar sf){
        
        Object result = Call.callSampleFunction("PACK_RH.funcRegSituacaoFamiliar", Types.VARCHAR, SessionUtil.getUserlogado().getId(), sf.getNumFilho(), sf.getPercentagem());
        return result.toString();
    }
    
    public static String editarOutrasTaxas(Taxa taxa, Double valorMinimo, Double valorMaximo, Double parcela)
    {
        String functionName ="PACK_RH.funcRegImpostoxTaxas";
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, 
             taxa.getIdImposto(),
             Integer.valueOf(SessionUtil.getUserlogado().getId().toString()),
             Float.valueOf(taxa.getPercentagem()),
             valorMinimo,
             valorMaximo,
             parcela
             );
        return resp.toString();
    }
    
   public static List<Taxa> carregarTaxasImpostos(int type)
   {
       String functionName ="PACK_RH.funcLoadImpostosTaxa";
       List<Taxa> list = new ArrayList<>();
       ResultSet rs = Call.callTableFunction(functionName, "*", type);
       
       if(rs != null)
       {
           switch (type) {
               case 3:
                   try
                   {
                       while(rs.next())
                       {
                           Taxa taxa = new Taxa();
                           taxa.setNomeImposto(rs.getString("IMPOSTO"));
                           taxa.setIdImposto(rs.getString("ID IMPOSTO"));
                           taxa.setPercentagem(rs.getString("PERCENTAGEM"));
                           list.add(taxa);
                       }
                   } catch (SQLException ex) {
                       Logger.getLogger(SalarioBean.class.getName()).log(Level.SEVERE, null, ex);
                   }   break;
               case 2:
                   try
                   {
                       while(rs.next())
                       {
                           Taxa taxa = new Taxa();
                           taxa.setNome(rs.getString("NOME"));
                           taxa.setNomeImposto(rs.getString("IMPOSTO"));
                           taxa.setIdImposto(rs.getString("ID IMPOSTO"));
                           taxa.setPercentagem(rs.getString("PERCENTAGEM"));
                           list.add(taxa);
                       }
                       rs.close();
                   } catch (SQLException ex) {
                       Logger.getLogger(SalarioBean.class.getName()).log(Level.SEVERE, null, ex);
                   }   break;
               default:
                   try
                   {
                       while(rs.next())
                       {
                           Taxa taxa = new Taxa();
                           taxa.setValorMinimo(rs.getString("VALOR MINIMO"));
                           taxa.setValorMaximo(rs.getString("VALOR MAXIMO"));
                           taxa.setIdImposto(rs.getString("ID IMPOSTO"));
                           taxa.setPercentagem(rs.getString("PERCENTAGEM"));
                           taxa.setParcelaBater(rs.getString("PARCELA BATER"));
                           list.add(taxa);
                       }
                       rs.close();
                   } catch (SQLException ex) {
                       Logger.getLogger(SalarioBean.class.getName()).log(Level.SEVERE, null, ex);
                   }   break;
           }
         
               
       }
       return list;
   }
   
   public static Object registrarProcessamentoSalario(int idFuncionario, int idProcesso)
   {
       int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
       Object result = Call.callSampleFunction("PACK_RH.funcProcessSalario", Types.VARCHAR, idUser, idFuncionario, idProcesso);
       return result;   
   }
   
  
   public static List<ProcessamentoSalario> listagemProcessamentoSalario(String ano, String mes)
   {
       String functionName ="PACK_RH.functLoadProcessSalario";
       List<ProcessamentoSalario> list = new ArrayList<>();
       ResultSet rs = Call.callTableFunction(functionName, "*",null,null);
       
       if(rs != null)
       {
            try
            {
                while(rs.next())
                {
                    ProcessamentoSalario ps = new ProcessamentoSalario();
                    ps.setData(rs.getString("DATA"));
                    ps.setId(rs.getString("ID"));
                    ps.setCodigoProcesso(rs.getString("CODIGO PROCESSO"));
                    ps.setMontante(rs.getString("MONTANTE"));
                    ps.setEstado(rs.getString("ESTADO"));
                    list.add(ps);
                }
                rs.close();
                if(list.size()>0)
                    list.remove(list.size()-1);
            } catch (SQLException ex) {
                Logger.getLogger(SalarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
       }  
       return list;
   }
   public static List<ProcessamentoSalario> pesquisarProcessamentoSalario(String ano, String mes, String pesquisa)
   {
       String functionName ="PACK_RH.functLoadProcessSalario";
        ResultSet rs;
        List<ProcessamentoSalario> list = new ArrayList<>();
       if(pesquisa != null && !pesquisa.equals(""))
       {
            rs = Call.selectFrom("TABLE("+functionName+"(?,?)) WHERE UPPER(\"CODIGO PROCESSO\") LIKE UPPER(?) OR UPPER(ESTADO) LIKE UPPER(?)", "*",
                ((!ano.equals("-1") ? Integer.valueOf(ano) : null)),
               ((!mes.equals("-1") ?  Integer.valueOf(mes) : null)),
                "%"+pesquisa+"%",
                "%"+pesquisa+"%");
       }
       else
       {     
            rs = Call.callTableFunction(functionName, "*",
                    ((!ano.equals("-1")) ? Integer.valueOf(ano) : null),
                    ((!mes.equals("-1") )?  Integer.valueOf(mes) : null)
            );
       }

       if(rs != null)
       {
            try
            {
                while(rs.next())
                {
                    ProcessamentoSalario ps = new ProcessamentoSalario();
                    ps.setData(rs.getString("DATA"));
                    ps.setId(rs.getString("ID"));
                    ps.setCodigoProcesso(rs.getString("CODIGO PROCESSO"));
                    ps.setMontante(rs.getString("MONTANTE"));
                    ps.setEstado(rs.getString("ESTADO"));
                    list.add(ps);
                }
                rs.close();
                if(list.size()>0)
                    list.remove(list.size()-1);
            } catch (SQLException ex) {
                Logger.getLogger(SalarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       return list;
   }
   /**
    * Registra os processos do salário
    * @param ano
    * @param mes
    * @return 
    */
   public static String registrarProcessoSalario(int ano, int mes)
   {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
       Object result = Call.callSampleFunction("PACK_RH.regProcessSalary", Types.VARCHAR, idUser,ano, mes );
       return result.toString();
   }
   
   public static String endProcessSalary(int idProcess)
   {
        Object result = Call.callSampleFunction("PACK_RH.funcEndProcess", Types.VARCHAR, idProcess);
        return result.toString();
   }
   
   public static String anularProcessamentoSalario(int idProcesso)
   {
       int estado = -1; //1- anular, 0- aprovar
       String descricao = null;
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
        Object result = Call.callSampleFunction("PACK_RH.funcAlterSatateProcessSalario", Types.VARCHAR,
                idProcesso,
                idUser, 
               estado,
               descricao
        ); 
        return result.toString();
   }
   
   
   
   
   
}
