
package dao;

import modelo.Funcionario;
import bean.IncendioBean;
import conexao.Call;
import conexao.Conexao;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Incendio;
import modelo.IncendioRisco;
import sessao.SessionUtil;

/**
 *
 * @author Helio
 */
public class IncendioDao 
{
    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    private String resultado = null;
    
    public String IncedioInfo(int idContrato, IncendioBean ir)
    {
        String functionName = "FUNC_CTTINF_INCENDIO";
        ir.getIncendio().getPavimento().addAll(ir.getIncendio().getParede());
        ir.getIncendio().getPavimento().addAll(ir.getIncendio().getTecto());  
        
      String [] dd= "10;10;10;10;;10;10;10;10".split(";;");
      String [] ddss = "10;10;10".split(";");
        
       Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                        ((Funcionario) SessionUtil.obterValor("utilizador")).getId(),
                        idContrato,
                        ((ir.getIncendio().getCondicao()==null || ir.getIncendio().getCondicao().equals(""))? null : ir.getIncendio().getCondicao()),
                        ir.getIncendio().getFonte(),
                        ir.getIncendio().getEquipamento(),
                        ir.getIncendio().getEndereco(),
                        ir.getIncendio().getAndar(),
                        ir.getIncendio().getAno(),
                        ir.getIncendio().getDistancia(),
                        
                        ir.getIncendio().getUsoResidencial(),
                        ir.getIncendio().getUsoEdificioDescricao(),
                        
                        ir.getIncendio().getProcessoFabricacao(),
                        ir.getIncendio().getPavimento()
               );
        return resp.toString();
    }
    
//    public String IncedioListaDetalhesCasa(int idContrato, IncendioBean ir)
//    {
//        String functionName = "FUNC_CTTINF_INCENDIO";
//        ir.getIncendio().getPavimento().addAll(ir.getIncendio().getParede());
//        ir.getIncendio().getPavimento().addAll(ir.getIncendio().getTecto());  
//        
//        for (int i = 0; i < ir.getIncendio().getPavimento().size(); i++) {
//       Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
//                        ((Funcionario) SessionUtil.obterValor("utilizador")).getId(),
//                        idContrato,
//                        ir.getIncendio().getPavimento().get(i)
//               );
//       
//    }
//         return "";
//    }

//     idUser NUMBER,
//   idContrato NUMBER,
//   idAsegurado NUMBER,
//   idCobertura NUMBER,
//   taxa FLOAT,
//   valor FLOAT,
//   premio FLOAT,
//   detalhes FLOAT
    public String IncedioListaCobertura(int idContrato, IncendioBean ir)
    {
        String functionName = "FUNC_REG_COBERTURAASEGURADO";
        for (int i = 0; i < ir.getRiscosIncendio().size(); i++) {
            Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                    ((Funcionario) SessionUtil.obterValor("utilizador")).getId(),
                    idContrato,
                    null,
                    ir.getRiscosIncendio().get(i).split(";")[0],
                    Float.valueOf(ir.getRiscosIncendio().get(i).split(";")[2]),
                    Float.valueOf(ir.getRiscosIncendio().get(i).split(";")[1]),
                    Float.valueOf(ir.getRiscosIncendio().get(i).split(";")[3]),
                    null
            );
            
        }
        return "";
    }
}