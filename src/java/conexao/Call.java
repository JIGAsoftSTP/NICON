/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import validacao.OperacaoData;

/**
 * 
 * @author Daniel Costa
 */
public class Call
{
    public static int VARCHAR = Types.VARCHAR;
    public static int INTEGER = Types.INTEGER;
    public static int DOUBLE = Types.DOUBLE;
    public static int ARRAY = Types.ARRAY;  
    
    
    
    /**
     * Funcao para   invocara as funcaoe simples da base de dado
     * Serve para invocar as funcoes que se deve usar o {? = call ...}
     * @param functionName O nome da funcao em base de dados que se quer chamar
     * @param returnType O que sera retornado por ela
     * @param parans Os parametros de entrada na funcao {Atencao que a ordem dos parametros deve ser igual a sua ordem na base de dados}
     * @return O retorno sera um objecto do tipo definido
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static Object callSampleFunction (String functionName, int returnType, Object ... parans)
    {
        if(!EstadoConnexao.isValid) return null;
        try 
        {
            CallableStatement call;
            Object result;
            // Criar as interoganocoes necessaria para a invocacao da funcao ex (?, ?, ?, ...) || nada
            try (Connection con = new Conexao().getCon()) 
            {
                if(con==null)
                {
                    return null;
                }
                // Criar as interoganocoes necessaria para a invocacao da funcao ex (?, ?, ?, ...) || nada
                String interogations = (parans != null && parans.length>0)? createInterogation(parans.length): "";
                //Associa ao call e o nome da funcao
                String sql = "{? = call "+functionName+interogations+"}";
                //Mapear e setar os parametros no call
                call = mapParamsType(con, sql, 2, parans);
                if (returnType == Call.ARRAY) call.registerOutParameter(1, ARRAY, "TB_ARRAY_STRING");
                else if(returnType == Types.STRUCT) 
                    call.registerOutParameter(1,returnType, "TP_OBJECT");
                else 
                    call.registerOutParameter(1, returnType);
                call.execute();
                result = Call.treatReturn(call.getObject(1), returnType);
                
            }call.close();
            return  result;
                
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    
    /**
     * Converter um ArrayList em uma lista de map
     * @param rs o ResultSet com os valore
     * @return Lista Mapeada
     */
    public static ArrayList<HashMap<String, Object>> toListMap (ResultSet rs)
    {
        ArrayList<HashMap<String, Object>>  list = new ArrayList<>();
        forEchaResultSet((map)->
        {
            list.add(map);
        }, rs);
        return list;
    }
    
    
    
    
    
    /**
     * Essa funcoa serve para tratar os ResultSet Ela ira percorer todo o relutado
     * Envindo um HasMap como os nome dos campos de os seus valor para ser trabalho em outro lugar
     * Iso sera feito linhas por linhas
     * @param actinoExecut O camando que devera ser executado para cada linha do result set
     * @param result O resut set Retornado
     * No final ser fechado o resultSet
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void forEchaResultSet (Consumer<HashMap<String, Object>> actinoExecut, ResultSet result)
    {
        if (!EstadoConnexao.isValid) return;
        try 
        {
            HashMap<String, Object> map;
            String s;
            while (result.next())
            {
                map = new HashMap<>();
                for (int i =1 ; i<=result.getMetaData().getColumnCount(); i++)
                {
                    s = result.getMetaData().getColumnName(i);
                    map.put(s, result.getObject(s));
                }
                actinoExecut.accept(map);
            }
            result.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Essa funcao serve para invocar a funcao da base de dados que retorna uma tabela
     * @param functionName O nome da funcao a ser invocado
     * @param campos Os campos que se quer obter da funcao {NULL ou String valida = *}
     * @param param Os parametros que serao usados para invocar a funcao {Nao é obrigatorio ter os parametros}
     * @return 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static ResultSet callTableFunction  (String functionName, String campos, Object ... param)
    {
        if (!EstadoConnexao.isValid) return null;
        try 
        {
            Connection con = new Conexao().getCon();
            
            campos = (campos == null||campos.length() == 0)? "*":campos;
            String interogations = (param != null && param.length>0)? createInterogation(param.length): "";
            String sql = "SELECT "+campos+" FROM TABLE("+functionName+interogations+")";
            
            CallableStatement call  = mapParamsType(con, sql, 1, param);
            if(call==null)
                return null;
            call.execute();
            return call.getResultSet();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    /**
     * Invocar um procedimento de base dos
     * @param procedureName Nome do procedimento
     * @param param
     * @return 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static boolean callProcedure (String procedureName, Object ... param)
    {
        if(!EstadoConnexao.isValid) return false;
        try 
        {
            try (Connection con = new Conexao().getCon())
            {
                String interogations = (param != null && param.length>0)? createInterogation(param.length): "";
                String sql = "{call "+procedureName+interogations+"}";
                
                try (CallableStatement call = mapParamsType(con, sql, 1, param))
                {
                    call.execute();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * Selecionar em uma entidade ou view da base de dados
     * 
     * @param tableView A entidade ou view que se vai selecionar
     * @param colummns A coluna que ira ser selecionada | quando nula ou vazia sera foito um select *
     * @param paramns O parametros para subistituir as interogacoes
     * @return 
     */
    public static ResultSet selectFrom (String tableView, String colummns, Object ... paramns)
    {
        ResultSet rs = null;
        try {
            Conexao c = new Conexao();
            if (c.getCon() == null) { return null; }
            colummns = (colummns == null || colummns.length() == 0) ? "*" : colummns;
            rs = Call.executeQuere("SELECT " + colummns + " FROM " + tableView, paramns).resultSet;
        } catch (Exception e) {}
        return rs;
    }
    
    /**
     * 
     * @param tableName
     * @param columns
     * @param valueQuere
     * @param whereColumns
     * @param parans
     * @return 
     */
    public static ResultSet selectFiltredFrom (String tableName, String columns, String valueQuere, String [] whereColumns, Object ... parans)
    {
        tableName = tableName + " WHERE";
        int count = 0;
        for(String colunWhere: whereColumns)
        {
            tableName = tableName + " UPPER(\""+colunWhere+"\") LIKE UPPER('%"+valueQuere+"%')";
            if(count< whereColumns.length - 1)
                tableName = tableName + " OR ";
            count ++;
        }
        
        return selectFrom(tableName, columns, parans);
    }
    
    
    
    
    /**
     * Essa funcoa serve para invocar uma funcao da base de dados que retorna um 
     *      Tipo costumisada e que esta mapeado com java
     * @param functionName
     * @param SQLType
     * @param javaClass
     * @param parans
     * @return 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static SQLData callMapFunction (String functionName, String SQLType, Class javaClass, Object ... parans)
    {
        if(!EstadoConnexao.isValid) return null;
        try 
        {
            Connection  con = new Conexao().getCon();
            Object result;
            
            // Criar as interoganocoes necessaria para a invocacao da funcao ex (?, ?, ?, ...) || nada
            String interogations = (parans != null && parans.length>0)? createInterogation(parans.length): "";
            
            //Associa ao call e o nome da funcao 
            String sql = "{? = call "+functionName+interogations+"}";
            
            con.getTypeMap().put(SQLType, javaClass);
            try (CallableStatement call = mapParamsType(con, sql, 2, parans)) 
            {
                call.registerOutParameter(1, Types.STRUCT, SQLType);
                call.execute();
                result = Call.treatReturn(call.getObject(1), VARCHAR);
            }
            return  (SQLData) result;
            
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        return null;
            
    }
    
    
    /**
     * Serve para executar um Stantment
     * @param quereSQL O sql de sera executado na base de dados
     * @param parans Os parametros que seram setados para as interogacoes
     * @return 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static ResultCall executeQuere (String quereSQL, Object ... parans)
    {
        if(!EstadoConnexao.isValid) return null;
        if (quereSQL != null)
        {
            try 
            {
                Connection con = new  Conexao().getCon();
                CallableStatement call = mapParamsType(con, quereSQL, 1, parans);
                
                call.execute();
                return new ResultCall(call, call.getResultSet());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    
    /**
     * Funcao para criar as interogacoes para o stantment
     * @param numInterogation
     * @return 
     */
    private static String createInterogation (int numInterogation)
    {
        String countInterogation = "(";
        for (int i=0; i<numInterogation; i++)
            countInterogation = countInterogation +"?" + ((i<numInterogation-1)? ", ":"");
        
        return countInterogation+")";
    }
    
    
    /**
     * Essta funcao serve para mapear um tipo de java com um tipo de base de dados
     *      Desde que o dipo de java seja herance de SQLData
     * @param connect A conexao que sera usada para mapear os tipos
     * @param parans Os parametros que poderam ser mapeados
     * @return A conexao mapeada
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private static CallableStatement mapParamsType (Connection connect, String sql, int initParam, Object ... parans )
    {
        try
        {
            CallableStatement call = connect.prepareCall(sql);
            System.out.println(sql);
            if (parans != null && parans.length >0)
            {
                for (Object obj : parans)
                {
                    if(obj != null) //As Somente quando o objecto nao for nulo e 
                    {//                  que serao feitas as verificacoes de mapeamento e do cast
                        if (obj  instanceof SQLData)
                        { //Quando o objecto for uma instancia de SQLData entao mapear o objecto com a base de dados
                            try 
                            {
                                SQLData sqlMap = (SQLData) obj ;
                                connect.getTypeMap().put(sqlMap.getSQLTypeName(), sqlMap.getClass());
                            } catch (Exception ex)
                            {
                                Logger.getLogger(Call.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else if (obj  instanceof Object []) 
                            obj  =  createSQLArrayVarchar((Object[]) obj, connect, null);
                        else if (obj  instanceof Collection)
                            obj = createSQLArrayVarchar(((Collection) obj).toArray(), connect, null);
                        else if (obj instanceof java.util.Date)
                            obj = OperacaoData.toSQLDate((java.util.Date) obj);
                        else if (obj instanceof Character)
                            obj = obj+"";
                    }
                    
                    System.out.println("Parametro "+initParam +" = "+obj);
                    call.setObject(initParam++, obj);
                }
            }
            return  call;
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    /** 
    * Essa funcao serve para converer um array em Um aray SQL
    * @param array O array a ser convertido para o array de base de dados
    * @param connect A conexao que sera usada
    * @param arrayTypeDataBase nome do aray na base de dados que ira receber o aray
    *       CASO for nulo sera mapeado para o TB_ARRAY_STRING
    * @return 
    */
    public static Array createSQLArrayVarchar (Object [] array, Connection connect, String arrayTypeDataBase)
    {
        try 
        {
            arrayTypeDataBase = (arrayTypeDataBase == null)? "TB_ARRAY_STRING": arrayTypeDataBase;
            ArrayDescriptor arryDesc = ArrayDescriptor.createDescriptor(arrayTypeDataBase, connect);
            Array oracleArray = new ARRAY(arryDesc, connect, array);
            OracleConnection oc = (OracleConnection) connect;
            return  oracleArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return null;
    }
    
    
    
    /**
     * Tratar o retorno vido do java
     * @param result
     * @param returnType
     * @return 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private static Object treatReturn (Object result, int returnType)
    {
        Object currectObject = result;
        try
        {
            if (returnType == ARRAY && result instanceof Array)
            {
                Array r = (Array) result;
                currectObject = new ArrayList<> (Arrays.asList((Object [])r.getArray()));
            }
            else if (Types.STRUCT == returnType)
            {
                System.out.println("dbdkj");
                currectObject = ((Struct) result).getAttributes();
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(currectObject != null)
            
            System.out.println("RETURN = class{"+currectObject.getClass().getName()+"} | toString = "+currectObject.toString()+"\n");
        return currectObject;
    }
    
    
    
    /**
     * Classe para armazenar os resultado do stantment
     * No resultado estara o CallableStamtment e o ResultSet
     */
    public static class ResultCall
    {
        private final CallableStatement callableStatement;
        private final ResultSet resultSet;

        
        /**
         * 
         * @param callableStatement
         * @param resultSet 
         */
        public ResultCall(CallableStatement callableStatement, ResultSet resultSet) {
            this.callableStatement = callableStatement;
            this.resultSet = resultSet;
        }
        
        
        /**
         * Obter o CallableStatement
         * @return 
         */
        public CallableStatement getCallableStatement() {
            return callableStatement;
        }

        
        /**
         * Obter o Result Set
         * @return 
         */
        public ResultSet getResultSet()
        {
            return resultSet;
        }
    }
}
