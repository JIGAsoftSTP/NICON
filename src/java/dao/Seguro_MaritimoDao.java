
package dao;

import bean.IncendioBean;
import modelo.Funcionario;
import conexao.Call;
import conexao.Conexao;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ComoBox;
import modelo.Maritimo;
import sessao.SessionUtil;

public class Seguro_MaritimoDao implements Serializable
{
     private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    public String resultado = "";
    /**
     * Funcao para registrar os objectos maritimos
     * @param maritimo
     * @param idContrato
     * @param listaCuberturaAceitada
     * @return 
     */
    public String regObjMaritimo (Maritimo maritimo, int idContrato, ArrayList<String> listaCuberturaAceitada)
    {
        String functionName = "FUNC_REGOBJ_MARITIMO";
        String apoio = (maritimo.getEspecificacaoApoioNavegacao() != null && maritimo.getEspecificacaoApoioNavegacao().equals("")) ?  maritimo.getEspecificacaoApoioNavegacao(): null;
        Object idUser = ((Funcionario) SessionUtil.obterValor(Funcionario.SESSION_NAME)).getId();
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, 
                                                idUser,
                                                idContrato,
                                                maritimo.getBandeiraNavio(),
                                                maritimo.getUsoNavio(),
                                                maritimo.getEstruturaRenovacao(),
                                                maritimo.getPotenciaMotor(),
                                                maritimo.getTipoCombustivel(),
                                                maritimo.getPeso(),
                                                maritimo.getNumMotor(),
                                                maritimo.getMarcaMotor(),
                                                maritimo.getNumMaximoTripulante(),
                                                maritimo.getNomeNavio(),
                                                maritimo.getMarcaModelo(),
                                                maritimo.getNumeroChassi(),
                                                maritimo.getIdadeNavio(),
                                                maritimo.getTipoNavio(),
                                                maritimo.getExperienciaRecalmacao(),
                                                apoio,
                                                maritimo.getAreaOperacao(),
                                                maritimo.getTipoCombustivel(),
                                                maritimo.getCondicaoAtual(),
                                                listaCuberturaAceitada
                                              );
        
        /*
            USER_ID NUMBER,
            ID_CONTRATO VARCHAR,
            BANDEIRA_DO_NAVIO NUMBER,
            USO_NAVIO VARCHAR2,
            CLASSE_ESTATUTO_RENOVACAO VARCHAR2,
            POTENCIA_MOTOR VARCHAR2,
            TIPO_COMBUSTIVEL VARCHAR2,
            QUANTIDADE_PESO BINARY_FLOAT,
            NUMERO_MOTOR VARCHAR2,
            MARCA_MOTOR VARCHAR2, 
            NUMERO_TRIPULANTE NUMBER,
            NOME_NAVIO VARCHAR2,
            MARCA_MODELO VARCHAR2,
            NUMERO_CHASSI VARCHAR2,
            IDADE_NAVIO DATE,
            TIPO_NAVIO VARCHAR2,
            EXPERIENCIA_RECLAMACAO NUMBER, 
            NAVEGACAO_INSTALADA VARCHAR2,
            AREA_OPERACAO  VARCHAR2,
            TIPO_CONSTRUCAO_NAVIO VARCHAR2,
            CONDICAO_NAVIO VARCHAR2,
            COBERTURAS TB_ARRAY_STRING
        */
        return resp.toString();
    }
    
    
    public ArrayList<ComoBox> getListaCombustivel ()
    {
        return ComoBox.loadCombo("VER_TIPO_COMBUSTIVEL", "REALID", "COMBUSTIVEL");
    }
    
    public ArrayList<ComoBox> getMoedas ()
    {
        return ComoBox.loadCombo("VER_MOEDA", "ID", "SIGLA");
    }
    
      public ArrayList<ComoBox> tipoCombustivel()
      {
           ArrayList<ComoBox> doumentos = new ArrayList<>();
          sql="SELECT ID,COMBUSTIVEL FROM VER_TIPO_COMBUSTIVEL";
        Conexao conexao = new Conexao();
        try 
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                while(rs.next())
                {
                    doumentos.add(new ComoBox(rs.getString("ID"), rs.getString("COMBUSTIVEL")));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doumentos;
    }
    public String listaCobertura(int idContrato, ArrayList<String> ir)
    {
        String functionName = "FUNC_REG_COBERTURAASEGURADO";
        for (int i = 0; i < ir.size(); i++) {
            Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                    ((Funcionario) SessionUtil.obterValor("utilizador")).getId(),
                    idContrato,
                    null,
                    ir.get(i).split(";")[0],
                    Float.valueOf(ir.get(i).split(";")[2]),
                    Float.valueOf(ir.get(i).split(";")[1]),
                    Float.valueOf(ir.get(i).split(";")[3]),
                    null
            );
            
        }
        return "";
    }
}
