
package dao;

import modelo.Funcionario;
import conexao.Call;
import java.sql.Types;
import java.util.ArrayList;
import sessao.SessionUtil;

/**
 *
 * @author Helio
 */
public class SeguroRespPublicaDao 
{
    
    public String RegInfo(int idContrato, float salarioColaboradores, float salarioEmpreiteiro,int edificioEsatdoConservacao,ArrayList<String> cobertura )
    {
        for (String string : cobertura) {
            System.err.println(string);
        }
         listaCobertura(idContrato, cobertura);
         String idUser = ((Funcionario) SessionUtil.obterValor(Funcionario.SESSION_NAME)).getId().toString();
         String functionName = "FUNC_CTTINF_RESPONSAPUBLICA";
         Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                      Integer.valueOf(idUser),
                      idContrato,
                      salarioColaboradores,
                      salarioEmpreiteiro,
                      edificioEsatdoConservacao,
                      cobertura
         );
         return resp.toString();
    }
    
    public String RegObjResponsabilidadePublica(int idContrato, String empregado, String profissao, String enderecoEdificio)
    {
         String idUser = ((Funcionario) SessionUtil.obterValor(Funcionario.SESSION_NAME)).getId().toString();
          String functionName = "FUNC_REGOBJ_RESPONSAPUBLICA";
          Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, 
                        Integer.valueOf(idUser),
                        idContrato,
                        empregado,
                        profissao,
                        enderecoEdificio
          );
          return resp.toString();
    }
    
    public String RegObj(int idContrato, String empresa , String modoResponsabiladade, Object percentagem)
    {
         String idUser = ((Funcionario) SessionUtil.obterValor(Funcionario.SESSION_NAME)).getId().toString();
          String functionName = "FUNC_REG_RESPONSABILIDADE";
          Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, 
                        Integer.valueOf(idUser),
                        idContrato,
                        empresa,
                        modoResponsabiladade,
                        percentagem
          );
          return resp.toString();
    }
    
    public String listaCobertura(int idContrato, ArrayList<String> ir)
    {
        String functionName = "FUNC_REG_COBERTURAASEGURADO"; 
        for (int i = 0; i < ir.size(); i++) {
            
           String[] splite = (ir.get(i)+";a").split(";");
            Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                    ((Funcionario) SessionUtil.obterValor("utilizador")).getId(),
                    idContrato,
                    null,
                    splite[0],
                    ((splite[2].isEmpty()) ? null: Float.valueOf(splite[2])),
                    ((splite[1].isEmpty()) ? null: Float.valueOf(splite[1])),
                    ((splite[3].isEmpty()) ? null: Float.valueOf(splite[3])),
                    splite[4]
            );
            
        }
        return "";
    }
}
