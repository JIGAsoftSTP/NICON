package dao;

import modelo.Funcionario;
import conexao.Call;
import conexao.Conexao;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ComoBox;
import modelo.Viagem;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;

/**
 *
 * @author Helio
 */
public class ViagemDao {

    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;
    private String resultado = null;

    public List<ComoBox> listaPaises() {
        List<ComoBox> paises = new ArrayList<>();
        ComoBox comoBox = null;
        sql = "SELECT * FROM T_PAIS";
        Conexao conexao = new Conexao();
        try {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                if (rs != null) {
                    while (rs.next()) {
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
    public List<String> enderecos()
    {
        List<String> adress = new ArrayList<>();
        rs = Call.selectFrom("VER_RESIDENCIA","RESIDENCIA");
        if(rs != null)
        {
            try 
            {
                adress.add(0, " ");
                while(rs.next())
                {
                    adress.add(rs.getString("RESIDENCIA"));
                }
                   rs.close();
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return adress;
    }
    
    public List<String> listaObjetivoViagem()
    {
        List<String> listaObjetivo = new ArrayList<>();
        rs = Call.selectFrom("VER_PASSAGEIROS", "*");
        
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    listaObjetivo.add(rs.getString("OBJECTIVO VIAGEM"));
                }
                rs.close();
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(ViagemDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaObjetivo;
    }
    public List<String> listaZonaDestino()
    {
        List<String> list = new ArrayList<>();
        rs = Call.selectFrom("VER_PASSAGEIROS", "*");
        
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    list.add(rs.getString("ZONA DESTINO"));
                }
                rs.close();
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(ViagemDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    public List<String> listaCidadeDestino()
    {
        List<String> list = new ArrayList<>();
        rs = Call.selectFrom("VER_PASSAGEIROS", "*");
        
        if(rs != null)
        {
            try 
            {
                while(rs.next())
                {
                    list.add(rs.getString("CIDADE DESTINO"));
                }
                rs.close();
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(ViagemDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
   
    public String registrarObjectoViagem(String idUser,String idContrato,Viagem viagem) 
    {
        System.out.println(viagem.toString());
        Object resp = Call.callSampleFunction("FUNC_REGOBJ_VIAGEM", Types.VARCHAR,
                idUser,
                idContrato,
                viagem.getNomePessoaSegurada(),
                viagem.getTipoDocOther(),
                viagem.getNumDoc(),
                OperacaoData.toSQLDate(viagem.getDataNasc()),
                OperacaoData.toSQLDate(viagem.getDataEmissao()),
                viagem.getLocalEmissao(),
                viagem.getLocalNascimento(),
                viagem.getEndereco(),
                viagem.getSexoOther(),
                viagem.getTelefone(),
                ((viagem.getOutrasInformacoes() != null && !viagem.getOutrasInformacoes().equals("")) ? viagem.getOutrasInformacoes() : null),
                viagem.getNumApolice(),
                viagem.getZonaDestino(),
                viagem.getCidadeDestino(),
                viagem.getObjetivoViagem(),
                viagem.getPaisDestinoOther(),
                OperacaoData.toSQLDate(viagem.getDataInicio()),
               OperacaoData.toSQLDate(viagem.getDataFim()),
               viagem.getNacionalidadeOther(),
                viagem.getIdTaxa(),
               ((viagem.isTipoViagem()== true)? 2 : 1)
                
                
        );
        return resp.toString();
    }

    public String registrarViagem(Viagem vb, String idContradto, int tamahoLista) 
    {
        Funcionario utilizadorLogado = (Funcionario) SessionUtil.getSession().getAttribute("utilizador");
       Object resp = Call.callSampleFunction("FUNC_CTTINF_VIAGEM", Types.VARCHAR, 
              utilizadorLogado.getId(),
              idContradto,
              null,
              null,
              OperacaoData.toSQLDate(vb.getMenorData()),
              OperacaoData.toSQLDate(vb.getMaiorData()),
              tamahoLista,
              null
            );
        return resp.toString();
    }
    
    public List<ComoBox> listaDocumentos()
    {
           List<ComoBox> doumentos = new ArrayList<>();
        sql="SELECT REALID,DOCUMENTO FROM VER_TIPODOCUMENTO";
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
                    doumentos.add(new ComoBox(rs.getString("REALID"), rs.getString("DOCUMENTO")));
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
    
    public List<ComoBox>listaSexos()
    {
        List<ComoBox> sexos = new ArrayList<>();
        sql="SELECT GEN_ID,GEN_DESC FROM T_GENDER";
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
                    sexos.add(new ComoBox(rs.getString("GEN_ID"), rs.getString("GEN_DESC")));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return sexos;
    }
    
    public static ResultSet relatorioTravel(Date inicio, Date fim)
    {
        ResultSet rs = Call.callTableFunction("PACK_REPORT.reportProducaoTravel","*",((inicio == null ) ? null :OperacaoData.toSQLDate(inicio) ), ((fim == null) ?  null : OperacaoData.toSQLDate(fim)));
        return rs;
    }
    
    public List<String> listaNumeroDocumentos()
    {
        List<String> list = new ArrayList<>();
        try
        {
            ResultSet resultSearch = Call.callTableFunction("PACK_PESQUISA.functLoadAtributoSegurado", "*", 3,"numDoc");
            Consumer<HashMap<String, Object>> actionExecute = map ->
            {
                list.add(map.get("COLUMN_VALUE").toString());
            };
            Call.forEchaResultSet(actionExecute, resultSearch);
        }catch (Exception ex) 
        {
            System.out.println("erro a carregar número de documentos "+ex.getMessage());
        }
        return list;
    }
    
    public void carregarSegurado (String numeroDocumento)
    {    
        ResultSet resultSearch = Call.selectFrom("VER_PASSAGEIROS WHERE DOCUMENTO=? AND ROWNUM<=1", "*",numeroDocumento);
        Viagem viagem = new Viagem();
       
        if(resultSearch != null)
        { 
            try 
             {
                 while(resultSearch.next())
                 {
                    viagem.setTelefone((resultSearch.getString("TELEFONE") == null)? "": resultSearch.getString("TELEFONE"));
                    viagem.setSexo(resultSearch.getString("SEXO_ID"));
                    viagem.setEndereco(resultSearch.getString("ENDERECO"));
                    viagem.setLocalEmissao((resultSearch.getString("LOCAL EMISAO")== null)? "" : resultSearch.getString("LOCAL EMISAO")) ;
                    viagem.setLocalNascimento((resultSearch.getString("LOCAL NASCIMENTO") == null)? "" : resultSearch.getString("LOCAL NASCIMENTO"));
                    viagem.setDataEmissaoFormatada((resultSearch.getString("DATA EMISSAO") == null)? "" : resultSearch.getString("DATA EMISSAO"));
                    viagem.setDataNascFormatada((resultSearch.getString("DATA NASCIMENTO") == null)? "" : resultSearch.getString("DATA NASCIMENTO"));
                    viagem.setTipoDoc((resultSearch.getString("ID DOCUMENTO") == null) ? "" : resultSearch.getString("ID DOCUMENTO"));
                    viagem.setNomePessoaSegurada(resultSearch.getString("NOME"));
                    viagem.setNumApolice((resultSearch.getString("NUMERO APOLICE") == null)? "" : resultSearch.getString("NUMERO APOLICE"));
                    viagem.setNacionalidade((resultSearch.getString("ID NACIONALIDADE") == null)? "" : resultSearch.getString("ID NACIONALIDADE"));
                 }
                 resultSearch.close();         
                 if(viagem.getNomePessoaSegurada() != null && !viagem.getNomePessoaSegurada().equals(""))
                 {
                    String data = viagem.getDataNascFormatada().substring(viagem.getDataNascFormatada().length()-2, viagem.getDataNascFormatada().length())
                            +"-"+viagem.getDataNascFormatada().substring(viagem.getDataNascFormatada().length()-5, viagem.getDataNascFormatada().length()-3)+"-"+viagem.getDataNascFormatada().substring(0, viagem.getDataNascFormatada().length()-6);
                    viagem.setDataNascFormatada(data);

                    data = viagem.getDataEmissaoFormatada().substring(viagem.getDataEmissaoFormatada().length()-2, viagem.getDataEmissaoFormatada().length())
                            +"-"+viagem.getDataEmissaoFormatada().substring(viagem.getDataEmissaoFormatada().length()-5, viagem.getDataEmissaoFormatada().length()-3)+"-"+viagem.getDataEmissaoFormatada().substring(0, viagem.getDataEmissaoFormatada().length()-6);
                    viagem.setDataEmissaoFormatada(data);
                    RequestContext.getCurrentInstance().execute("carregarViagem('"+viagem.getTelefone()+"','"+viagem.getSexo()+"','"+viagem.getEndereco()+"','"+viagem.getLocalEmissao()+"','"+viagem.getLocalNascimento()+"','"+viagem.getDataEmissaoFormatada()+"','"+viagem.getDataNascFormatada()+"','"+viagem.getTipoDoc()+"','"+viagem.getNomePessoaSegurada()+"','"+viagem.getNacionalidade()+"')");
                 }
                 else
                     carregarCliente(numeroDocumento, viagem);
             }
             catch (SQLException ex) {
                 Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
   }
   
    private void carregarCliente(String numDoc, Viagem viagem)
    {
        String field = "NUMERO DOCUMENTO";
        ResultSet result = Call.selectFrom("VER_CLIENTECOMPLETO WHERE \"" + field + "\"=?", "*", numDoc);
        if(result != null)
        {
            try {
                while(result.next())
                {
                    viagem.setNomePessoaSegurada(result.getString("NOME"));
                    viagem.setDataNascFormatada(result.getString("DATA NASCIMENTO"));
                    viagem.setTelefone(result.getString("TELEFONE"));
                    viagem.setEndereco(result.getString("RESIDENCIA"));
                    viagem.setTipoDoc(result.getString("ID TIPO DOCUMENTO"));
                    viagem.setSexo(result.getString("SEXO ID"));
                    viagem.setNacionalidade(result.getString("ID PAIS"));
                }
                result.close();
              RequestContext.getCurrentInstance().execute("carregarViagem('"+viagem.getTelefone()+"','"+viagem.getSexo()+"','"+viagem.getEndereco()+"','"+viagem.getLocalEmissao()+"','"+viagem.getLocalNascimento()+"','"+viagem.getDataEmissaoFormatada()+"','"+viagem.getDataNascFormatada()+"','"+viagem.getTipoDoc()+"','"+viagem.getNomePessoaSegurada()+"','"+viagem.getNacionalidade()+"')");
            } catch (SQLException ex) {
                Logger.getLogger(ViagemDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
