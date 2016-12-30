
package dao;

import conexao.Call;
import conexao.Conexao;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CargaMaritima;
import modelo.ComoBox;

/**
 *
 * @author Helio
 */
public class CargaMaritimaDao 
{
    
    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    private String resultado = null;

    public List<ComoBox> listaPaises(int i) {
        List<ComoBox> paises = new ArrayList<>();
        ComoBox comoBox = null;
        sql = "SELECT * FROM T_PAIS";
        Conexao conexao = new Conexao();
        
        if(i==1)
        {
            comoBox = new ComoBox("(Pais Destino)","(Pais Destino)");
            paises.add(comoBox);
        }
        
        if(i==2)
        {
            comoBox = new ComoBox("(Pais Origem)","(Pais Origem)");
            paises.add(comoBox);
        }
        
        try {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                if (rs != null) 
                {
                    while (rs.next()) 
                    {
                        comoBox = new ComoBox(rs.getString("PAIS_ID"), rs.getString("PAIS_NOME"));
                        paises.add(comoBox);
                    }
                    rs.close();
                    conexao.destruir();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViagemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paises;
    }
    
    public String regInfoCargaMaritima(String idUser,String idContrato,CargaMaritima cm)
    {
        System.err.println(cm.toString());
        Conexao conexao = new Conexao();
        try
        {
            
            System.out.println(Arrays.toString(cm.getAllMercadorias().toArray()));
            if(conexao.getCon()!=null)
            {                
                Object o = Call.callSampleFunction("FUNC_CTTINF_CARGAMARTIMA", Call.VARCHAR, 
                 idUser,
                idContrato
                
                ,cm.getPaisOrigem()
                ,cm.getPaisDestino()
                ,cm.getPortoCarga()
                ,cm.getPortoDescarga()
                ,cm.getNomeNavio()
                ,Float.valueOf(cm.getValorLimiteIndeminizacao())
                
                , cm.getCobertura().split(";")[0]
                ,Float.valueOf(cm.getValorPremioReal())
                ,Float.valueOf(cm.getTaxaValorLimiteIndeminizacao())
                , cm.getProposito()
                
                , (cm.getQualquerNavio() == null || cm.getQualquerNavio().isEmpty()) ? null : Float.valueOf(cm.getQualquerNavio())
                , (cm.getQualquerMercadoria() == null || cm.getQualquerMercadoria().isEmpty()) ? null : Float.valueOf(cm.getQualquerMercadoria())
                , (cm.getAnualParaCadaMercadoria() == null || cm.getAnualParaCadaMercadoria().isEmpty()) ? null : Float.valueOf(cm.getAnualParaCadaMercadoria())
                
                , cm.getFormaEnvio().split(";")[0]
                
                , cm.getTempoNegocio()
                , cm.getCustoPorto()
                
                , cm.getDecricaoMecadoria()
                , cm.getAreaComercail()
                , cm.getEpecifiqueCompanhina()
                , cm.getMecioneNomeCompanhina()
                
                , cm.getAllMercadorias());
                
                
                resultado = ((o==null)?resultado:o.toString());
                conexao.destruir();
                
                
            }
        }catch(NumberFormatException ex)
        {
            System.err.println(ex);
            return null;
        }
        return resultado;
    }
    
    public void regMoodoEmbalagem(String idUser,String idContrato,CargaMaritima cm)
    {
        sql="{?=call FUNC_REGOBJ_CARGAMARITIMA( ? , ? , ? , ?)}";
        System.err.println(sql);
        Conexao conexao = new Conexao();
        try
        {
    
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setString(2, idUser);
                cs.setString(3, idContrato);
                cs.setString(4, cm.getInterese());
                cs.setString(5, cm.getModoEmbalagem());
                cs.execute();
                resultado = cs.getString(1);
                conexao.destruir();
            }
        }catch(SQLException | NumberFormatException ex)
        {
            System.err.println(ex);
        }
    }
    
    public void regDetalhesVeiculo(String idUser,String idContrato,CargaMaritima cm)
    {
        sql="{?=call FUNC_CTTINF_CARGAMARIT_VEICULO( ? , ? , ? , ? , ?,? , ? , ? , ? , ?, ?)}";
        Conexao conexao = new Conexao();
        try
        {
    
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setString(2, idUser);
                cs.setString(3, idContrato);
                cs.setString(4, cm.getConservacaoNoite());
                cs.setString(5, cm.getDeNumeroRegisto());
                cs.setString(6, cm.getDeVeiculoComercialRegitrado());
                cs.setFloat(7, Float.valueOf(cm.getDeValorCarregamentoAnual()));
                cs.setString(8, cm.getDeMarca());
                cs.setFloat(9, Float.valueOf(cm.getDeValorMaximoCadaCarregamento()));
                cs.setFloat(10, Float.valueOf(cm.getDeValorMaximoVeiculo()));
                cs.setInt(11, ((cm.isDescolacaoComercia())?1:0));
                cs.setInt(12, ((cm.isDescolacaoComercia())?1:0));
                
                cs.execute();
                resultado = cs.getString(1);
                conexao.destruir();
            }
        }catch(SQLException | NumberFormatException ex)
        {
            System.err.println(ex);
        }
    }
}
