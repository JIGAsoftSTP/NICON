
package dao;

import modelo.Funcionario;
import conexao.Call;
import java.sql.Types;
import sessao.SessionUtil;

/**
 *
 * @author Helio
 */
public class SeguroDinheiroDAO 
{
     public String regInfoDinheiro(int idContrato, String distanciaBanco, String distanciaCorreio, String distanciaOutros, String transporteDinheiro
              , String precaucao, String tempoPermanencia, String pagamentoDinheiro, int cobertura, float limite, float valorPremio)
     {
         String functionName = "FUNC_CTTINF_DINHEIRO";
          char[] pagamento;
          char payment;
        if(pagamentoDinheiro == null || pagamentoDinheiro.equals(""))
        {
            pagamentoDinheiro = "N";
        }
        pagamento =  pagamentoDinheiro.toCharArray();
       payment = pagamento[0];
          String idUser = ((Funcionario) SessionUtil.obterValor(Funcionario.SESSION_NAME)).getId().toString();
           Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                        Integer.valueOf(idUser),
                        idContrato,
                        ((distanciaBanco == null || distanciaBanco.equals("")) ? null : distanciaBanco),
                        ((distanciaCorreio == null || distanciaCorreio.equals("")) ? null : distanciaCorreio),
                        ((distanciaOutros == null || distanciaOutros.equals("")) ? null : distanciaOutros),
                        ((transporteDinheiro == null || transporteDinheiro.equals("")) ? null : transporteDinheiro),
                        ((precaucao == null || precaucao.equals("")) ? null : precaucao),
                        ((tempoPermanencia == null || tempoPermanencia.equals("")) ? null : tempoPermanencia),
                          payment,
                          cobertura,
                          limite,
                          valorPremio
                        );
           return resp.toString();
     }
     
     public String regObjDinheiro(String idContrato, String nomeFabricante,String numeroFabricante, String tamanho, 
             String detentorChaves, String peso, String Construcao, String estrutura)
     {
         int idConstrucao = (Construcao.equals("Y")) ? 34 : 38;
         String functionName = "FUNC_REGOBJ_SEGURODINHEIRO";
                String idUser = ((Funcionario) SessionUtil.obterValor(Funcionario.SESSION_NAME)).getId().toString();
         Object resp = Call.callSampleFunction(functionName,Types.VARCHAR,
                     Integer.valueOf(idUser),
                     idContrato,
                     ((nomeFabricante == null || nomeFabricante.equals("")) ? null : nomeFabricante),
                     ((numeroFabricante == null || numeroFabricante.equals("")) ? null : numeroFabricante),
                     ((tamanho == null || tamanho.equals("")) ? null : tamanho),
                     ((detentorChaves == null || detentorChaves.equals("")) ? null : detentorChaves),
                     ((peso == null || peso.equals("")) ? null : Float.parseFloat(peso)),
                      idConstrucao,
                     estrutura
         );
         return resp.toString();
     }

}
