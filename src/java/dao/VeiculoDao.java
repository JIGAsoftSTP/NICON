
package dao;

import modelo.Funcionario;
import conexao.Call;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexao.Conexao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import modelo.ComoBox;
import modelo.Veiculo;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;

public class VeiculoDao implements Serializable
{
    private CallableStatement cs;
    private String sql;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private String resultado;
    private HashMap<String, Veiculo> listVeiculos = new HashMap<>();

     
    public String registrarVeiculo(Veiculo veiculo,String idContradto)
    {
        System.out.println(veiculo.toString());
        String user =((Funcionario)SessionUtil.obterValor("utilizador")).getId().toString();
         String functionName = "FUNC_REGOBJ_VEICULO";
         Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, 
                                                Integer.valueOf(SessionUtil.getUserlogado().getId().toString()),
                                                idContradto,
                                                veiculo.getNumeroMatricula(),
                                                veiculo.getMarca(),
                                                veiculo.getModelo(),
                                                ((veiculo.getNumMotor()==null || veiculo.getNumMotor().equals(""))? null : veiculo.getNumMotor()),
                                                veiculo.getChassi(),
                                                ((veiculo.getAnoFabrico()==null || veiculo.getAnoFabrico().equals(""))? null: veiculo.getAnoFabrico()),
                                                ((veiculo.getAnoCompra()==null || veiculo.getAnoCompra().equals(""))? null:veiculo.getAnoCompra()),
                                                veiculo.getCapacidade(),
                                                ((veiculo.getValorCompra()==null || veiculo.getValorCompra().equals(""))? null: Float.valueOf(veiculo.getValorCompra())),
                                                ((veiculo.getValorAtual()==null || veiculo.getValorAtual().equals("")) ? null : Float.valueOf(veiculo.getValorAtual())),
                                                ((veiculo.getTipoCobertura()==null || veiculo.getTipoCobertura().equals(""))? null : Integer.valueOf(veiculo.getTipoCobertura())),
                                                ((veiculo.getValorPremio() == null || veiculo.getValorPremio().equals(""))? null : Float.valueOf(veiculo.getValorPremio())),
                                                ((veiculo.getLimiteResp().equals("Ilimitado") ? null : Float.parseFloat(veiculo.getLimiteResp()))),
                                                veiculo.getCertificado()
                                                        );
    return resp.toString();
    }
    
    public String testeFun()
    {
        Object resp = Call.callSampleFunction("FUNC_REGOBJ_VEICULO", Types.VARCHAR, "wwss");
        return resp.toString();
    }
    
    public List<String> MarcaModelo(String marca)
    {
        List<String> marcas = new ArrayList<>();
        rs = Call.callTableFunction("PACK_PESQUISA.loadModelo","*", marca);
        Consumer <HashMap<String, Object>> act  = (map)-> marcas.add(map.get("MODELO").toString());
        Call.forEchaResultSet(act, rs);
        return marcas;
    }
    
    public List<String> marcas()
    {
        sql ="SELECT * FROM VER_VEICULO_MARCA";
        List<String> marcas = new ArrayList<>();
        Conexao conexao = new Conexao();
         if(conexao.getCon()==null)
             return  marcas;
        try
        {
            ps = conexao.getCon().prepareStatement(sql);
            ps.execute();
            rs = ps.getResultSet();
            if(rs != null)
            {
                while(rs.next())
                {
                    marcas.add(rs.getString("MARCA"));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return marcas;
    }
    
    public List<String> listaMarcas()
    {
        sql ="SELECT * FROM VER_VEICULO_MARCA";
        List<String> marcas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()==null)
                return new ArrayList<>();
            ps = conexao.getCon().prepareStatement(sql);
            ps.execute();
            rs = ps.getResultSet();
            if(rs != null)
            {
                while(rs.next())
                {
                   marcas.add(rs.getString("MARCA"));
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return marcas;
    }
    
    public List<String> listaMatriculas()
    {
        List<String> matriculas = new ArrayList<>();
        try
        {
            ResultSet resultSearch = Call.callTableFunction("PACK_PESQUISA.functLoadAtributoSegurado", "*", 2,"numeroMatricula");
            Consumer<HashMap<String, Object>> actionExecute = map ->
            {
                matriculas.add(map.get("COLUMN_VALUE").toString());
            };
            Call.forEchaResultSet(actionExecute, resultSearch);
        }catch (Exception ex) 
        {
            System.out.println("erro a carregar matriculas "+ex.getMessage());
        }
        return matriculas;
    }
    
    public boolean matriculaIsValid(String matricula)
    {
        try
         {
             ResultSet resultSearch = Call.callTableFunction("PACK_PESQUISA.functLoadAtributoSegurado", "*", 2,"numeroMatricula");
             if(resultSearch != null)
             {
                 while(resultSearch.next())
                 {
                     if(matricula.equals(resultSearch.getString("COLUMN_VALUE")))
                     {
                         return false;
                     }
                 }
                 resultSearch.close();
             }
         }catch (Exception ex) 
         {
         }
        return true;
    }
    public List<ComoBox> moedas()
    {
        List<ComoBox> moeda = new ArrayList<>();
        Conexao conexao = new Conexao();
        sql = "SELECT * FROM VER_MOEDA";
        if(conexao.getCon() != null)
        {
             try {
            ps = conexao.getCon().prepareStatement(sql);
            ps.execute();
            rs = ps.getResultSet();
            if(rs != null)
            {
                while(rs.next())
                {
                    ComoBox box = new ComoBox(rs.getString("ID"), rs.getString("SIGLA"));
                    moeda.add(box);
                }
                rs.close();
                    conexao.destruir();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
       
        return moeda;
    }
    
    
    
    public List<ComoBox> Marca()
    {
        List<ComoBox> moeda = new ArrayList<>();
        Conexao conexao = new Conexao();
        sql = "SELECT * FROM VER_MOEDA";
        try {
            ps = conexao.getCon().prepareStatement(sql);
            ps.execute();
            rs = ps.getResultSet();
            if(rs != null)
            {
                while(rs.next())
                {
                    ComoBox box = new ComoBox(rs.getString("ID"), rs.getString("SIGLA"));
                    moeda.add(box);
                }
                rs.close();
                conexao.destruir();
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moeda;
    }
    
    public List<ComoBox> Modelo(int marca)
    {
        List<ComoBox> moeda = new ArrayList<>();
        Conexao conexao = new Conexao();
        ComoBox box = new ComoBox("(selecione)", "(selecione)");
        moeda.add(box);
        sql = "SELECT * FROM VER_VEICULO_MODELO where ID = ?";
        try {
            ps = conexao.getCon().prepareStatement(sql);
            ps.setInt(1, marca);
            ps.execute();
            rs = ps.getResultSet();
            if(rs != null)
            {
                while(rs.next())
                {
                    box = new ComoBox(rs.getString("ID"), rs.getString("SIGLA"));
                    moeda.add(box);
                }
                rs.close();
                conexao.destruir();
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moeda;
    }
      
    public String registrarCausulas(Object idUtilizadorLogado,String codContrato,String idPergunta,String resposta,String expecificacao)
    {
        Conexao conexao = new Conexao();
        sql="{?=FUNC_REG_RESPOSTA( ? , ? , ?, ? , ?)}";
        try 
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setObject(2, idUtilizadorLogado);
                cs.setObject(3, codContrato);
                cs.setObject(4, idPergunta);
                cs.setObject(5, resposta);
                cs.setObject(6, expecificacao);
                cs.execute();
                resultado = cs.getString(1);
            }
            
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro a registrar seguro de veiculo "+ex.getMessage());
        }
        
    return resultado;
    }
    
    private  Veiculo novoVeiculo(HashMap<String, Object> map)
    {
        Veiculo currentVeicu;
        String id = map.get("KEY").toString();
        if(!listVeiculos.containsKey(id))
        {
            currentVeicu = new Veiculo();
            listVeiculos.put(id, currentVeicu);
        }
        else currentVeicu = listVeiculos.get(id);
        return currentVeicu;
    }
    
    public void loadInfoVeiculo (String matricula)
    {    
        ResultSet resultSearch = Call.callTableFunction("PACK_PESQUISA.functLoadObjectByAttribute", "*", 2, "numeroMatricula",matricula);
          
        if(resultSearch != null)
        {
           Veiculo automovel = new Veiculo();
            try 
             {
                 while(resultSearch.next())
                 {
                     if(!resultSearch.getString("KEY").equals("OBJECT.ID"))
                     {
                         if(resultSearch.getString("KEY").equals("capacidade"))automovel.setCapacidade(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("marca"))automovel.setMarca(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("modelo"))automovel.setModelo(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("numMotor"))automovel.setNumMotor(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("chassi"))automovel.setChassi(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("anoFabrico"))automovel.setAnoFabrico(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("anoCompra"))automovel.setAnoCompra(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("valorCompra"))automovel.setValorCompra(resultSearch.getString("VALUE"));
                         if(resultSearch.getString("KEY").equals("valorAtual"))automovel.setValorAtual(resultSearch.getString("VALUE"));
                     }
                 }
                 resultSearch.close();
                 if(automovel.getCapacidade() != null && !automovel.getCapacidade().equals(""))
                     RequestContext.getCurrentInstance().execute("carregarVeiculo('"+automovel.getMarca()+"','"+automovel.getModelo()+"','"+automovel.getNumMotor()+"','"+automovel.getChassi()+"','"+automovel.getAnoFabrico()+"','"+automovel.getAnoCompra()+"','"+automovel.getCapacidade()+"','"+automovel.getValorCompra()+"','"+automovel.getValorAtual()+"')");
             }
             catch (SQLException ex) {
                 Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
   }
   
    public List<Veiculo> listagemVeiculos(String matricula)
    {
        List<Veiculo> list = new ArrayList<>();
        if(matricula == null) rs = Call.selectFrom("VER_VEICULO", "*");
        else rs = Call.selectFrom("VER_VEICULO WHERE UPPER(MATRICULA) like UPPER( '%"+matricula + "%') ", "*");
        
        try
        {
            while(rs.next())
            {
                Veiculo veiculo = new Veiculo();
                veiculo.setAnoCompra(rs.getString("ANO COMPRA"));
                veiculo.setCapacidade(rs.getString("CAPACIDADE"));
                veiculo.setAnoFabrico(rs.getString("ANO FABRICO"));
                veiculo.setChassi(rs.getString("CHASSI"));
                veiculo.setNumMotor(rs.getString("MOTOR"));
                veiculo.setModelo(rs.getString("MODELO"));
                veiculo.setMarca(rs.getString("MARCA"));
                veiculo.setNumeroMatricula(rs.getString("MATRICULA"));
                list.add(veiculo);  
            }
            rs.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
 
    
    
}
