
package dao;

import bean.ContratoBean;
import modelo.Funcionario;
import conexao.Call;
import conexao.Conexao;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.AcidentePG;
import modelo.CargaMaritima;
import modelo.CoberturaLoad;
import modelo.Comissao;
import modelo.ComoBox;
import modelo.Contrato;
import modelo.DadosContrato;
import modelo.Dinheiro;
import modelo.Incendio;
import modelo.IncendioRisco;
import modelo.Maritimo;
import modelo.ModeloPagamento;
import modelo.NotaDebito;
import modelo.ResponsabilidadePublica;
import modelo.Resseguro;
import modelo.Roubo;
import modelo.Veiculo;
import modelo.Viagem;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;


public class ContratoDao 
{   
    private CallableStatement cs;
    private String sql;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private String resultado;
    static int i=0;
    static Viagem currentViagem = new Viagem();
    AcidentePG currentPessoas = new AcidentePG();
    static SimpleDateFormat sdfDat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfIng = new SimpleDateFormat("yyyy-MM-dd");
//    static SimpleDateFormat sdfPt = new SimpleDateFormat("dd-MM-yyyy");
    HashMap<String, Veiculo> listVeiculos = new HashMap<>();
    Roubo cR = new Roubo();
    
    public List<Viagem> obterValoresSegurado(Date inicial, Date dataFinal, int tipoViagem)
    {
        Contrato contrato = null;
        List<Viagem> list = new ArrayList<>();
        rs = Call.callTableFunction("FUNCT_GET_VALORESEGURADO", "*", 
               OperacaoData.toSQLDate(inicial),
                OperacaoData.toSQLDate(dataFinal),
                tipoViagem
        );
        try 
        {
            if(rs != null)
            {
                while(rs.next())
                {
                    Viagem v = new Viagem();
                    v.setNc((float) Moeda.arrendodamento(rs.getString("NC")));
                  //ID TAXA
                  v.setValorPremio(rs.getFloat("SUBTOTAL"));
                  v.setSumTotal(rs.getFloat("TOTAL"));
                  v.setValorImpostoCincoPorCento(rs.getFloat("CONSUMO"));
                  v.setValorImpostoSeisPorCento(rs.getFloat("SELO"));
                  v.setValorFGA(rs.getFloat("FGA"));
                  v.setNumDias(rs.getInt("DIAS"));
                  v.setIdTaxa(rs.getInt("ID"));
                  list.add(v);
                }
                rs.close();
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
        
    }
    
    public int IdCOntrato(String codigoSeguro)
    {
        int numero = 0;
        sql ="SELECT ID FROM VER_CONTRATO WHERE APOLICE =?";
        Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.setString(1, codigoSeguro);
                ps.execute();
                rs = ps.getResultSet();
                if(rs != null)
                {
                    while(rs.next())
                    {
                        numero = rs.getInt("ID");
                    }
                    rs.close();
                    conexao.destruir();
                }
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Erro a obter Id do seguro "+ex.getMessage());
        }
        return numero;
    }
    public String registarContrato(ContratoBean e,String CodCliente,String codiContrato,String numeroApolice,String numRegistro,String IdContrato, String premioLiquido, String premioBruto,int idMoeda, String idContratoMultiRisco)
    {
            String format ="MM/dd/yyyy";
           String totalSegurado = null;
           float total =0;
           Float valorSegurado = ((e.getContrato().getTotalSeguradoMoeda().equals("Ilimitado")) ? null : Float.parseFloat(e.getContrato().getTotalSegurado()));
           Object resp = Call.callSampleFunction("FUNC_REG_CONTRATO", Types.VARCHAR,
      
                ((Funcionario)SessionUtil.obterValor("utilizador")).getId(),
                 Integer.valueOf(CodCliente),
                IdContrato,
               ((numeroApolice != null && !numeroApolice.equals(""))? numeroApolice : null),
                codiContrato,
              ((e.getContrato().getDataInicio() == null) ? null : OperacaoData.toSQLDate(e.getContrato().getDataInicio())),
              ((e.getContrato().getDataFim() == null) ? null : OperacaoData.toSQLDate(e.getContrato().getDataFim())),
               ((e.getContrato().getDataContrato() == null) ? null : OperacaoData.toSQLDate(e.getContrato().getDataContrato())),
               ((e.getContrato().getDataRenovacao() == null) ? null : OperacaoData.toSQLDate(e.getContrato().getDataRenovacao())),
               Double.valueOf(premioBruto),
                ((e.getContrato().getPrimeiroPremio() == null) ? "0" : e.getContrato().getPrimeiroPremio()),
                 0,//falta valor de Nennos ------- /**/
                Double.valueOf(premioLiquido),
                valorSegurado,
                ((e.getContrato().getPremioAnual() == null) ? "0" : e.getContrato().getPremioAnual()),
               ((e.getContrato().getTaxa() == null || e.getContrato().getTaxa().equals("") ) ? "0" : e.getContrato().getTaxa()),
                ((e.getContrato().getFranquia() == null || e.getContrato().getFranquia().equals("")) ? null : e.getContrato().getFranquia()),
               ((e.getContrato().getObservacao() == null || e.getContrato().getObservacao().equals("")) ? null : e.getContrato().getObservacao()),
                numRegistro,
                idMoeda,
                idContratoMultiRisco
                );
        
        return resp.toString();
    }

    /**
     * Funcao serve para verificar se um numero do registro esta disponivel ou nao
     * @param numRegistro O nomero do registro a ser usado
     * @return  TRUE - Valido | FALSE - Invalido
     */
    public static boolean isNumRegistroVago (String numRegistro)
    {
        Conexao c = new Conexao();
        if(c.getCon()==null)
            return true;
        return (int) Call.callSampleFunction("FUNC_EXIST_REGISTRO", Types.INTEGER, numRegistro) ==0;
       
    }
    public String totalContrato(String id)
    {
        sql ="SELECT *FROM VER_STATUS_CLIENTE WHERE ID =?";
        Conexao conexao = new Conexao();
        if(conexao.getCon() != null)
        {
            try
            {
                ps= conexao.getCon().prepareStatement(sql);
                ps.setString(1, id);
                ps.execute();
                ps.getResultSet();
                rs = ps.getResultSet();
                if(rs != null)
                {
                    while(rs.next())
                    {
                        resultado = rs.getString("TOTAL CONTRATO");
                    }
                }
                rs.close();
                conexao.destruir();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            return "false";
        
        return resultado;
    } 
    public Object[] carregarUmContracto(int id)
    {
        Contrato c= new Contrato();
        Object[] oRe= new Object[5];
        try {
            
//            ID	APOLICE	FUNCIONARIO COD	FUNCIONARIO NOME	PREMIO BRUTO	PRIMEIRO PREMIO	MENOS	DATA INICIO	DATA FIM	DATA CONTRATO	DATA RENOVACAO	LIQUIDO PAGAR	VALOR TOTAL SEGURADO	PREMIO ANUAL	TAXA ADICIONAR	EXCESSO	OBSERVACAO	CODIGO EXTERNO	DATA FECHO	DATA REGISTRO	IMPOSTO CONSUMO	IMPOSTO FGA	IMPOSTO SELO	SEGURO CODIGO	SEGURO NOME	MOEDA NOME	MOEDA SIGLA	NOME	APELIDO
//40	2016FR0002/0002	""	Daniel Costa	17.28	15	0	2016-01-23 00:00:00.000	2016-01-31 00:00:00.000	2016-01-23 00:00:00.000	2016-01-31 00:00:00.000	14.598144	144	14	0	0	acessorioss	4784	""	2016-01-23 09:56:42.195	5	0	0.6	FR	Sguro de Incendio	euro	EUR	JJJJJ	""

            rs= Call.selectFrom("VER_CONTRATO_COMPLETO where ID = ?", "*", id);
            
            rs.next();
            c.setDataContrato(rs.getDate("DATA CONTRATO"));
            c.setDataInicio(rs.getDate("DATA INICIO"));
            c.setDataFim(rs.getDate("DATA FIM"));
            c.setDataRenovacao(rs.getDate("DATA RENOVACAO"));
            c.setDataRegistro(rs.getDate("DATA REGISTRO"));
            
            c.setPremioAnual(rs.getString("PREMIO ANUAL"));
            c.setPremioBrutoMoeda(Moeda.format(Double.valueOf(rs.getString("PREMIO BRUTO"))));
            c.setPremioBruto(rs.getString("PREMIO BRUTO"));
            c.setPremioLiquidoMoeda(Moeda.format(Double.valueOf(rs.getString("LIQUIDO PAGAR"))));
            c.setPremioLiquido(rs.getString("LIQUIDO PAGAR"));
//             : rs.getString("VALOR TOTAL SEGURADO")))
            c.setTotalSeguradoMoeda(((rs.getString("VALOR TOTAL SEGURADO") != null) ? Moeda.format(Double.valueOf(rs.getString("VALOR TOTAL SEGURADO"))) : "LIMITADO"));
            c.setTotalSegurado(rs.getString("VALOR TOTAL SEGURADO"));
            c.setPrimeiroPremio(rs.getString("PRIMEIRO PREMIO"));
            
            c.setValorPremio(rs.getString("PRIMEIRO PREMIO"));
            c.setExcesso(rs.getString("EXCESSO"));
            c.setFranquia(rs.getString("EXCESSO"));
            
            System.err.println(c.getFranquia());
            
//            c.setNc(rs.getString("MENOS"));
//            c.setFga(rs.getString("MENOS"));
//            c.setValorNC(rs.getString("MENOS"));
            
            c.setObservacao(rs.getString("OBSERVACAO"));
            c.setSigla(rs.getString("MOEDA SIGLA"));
            c.setTaxa(rs.getString("TAXA ADICIONAR"));
            
            try (ResultSet rss = Call.selectFrom("VER_MOEDA where SIGLA = ? ", "*", c.getSigla()))  
            {
                while (rss.next()) {  c.setMoeda(rss.getString("ID")); }
            }
            
            c.setValorNC(rs.getString("MENOS"));
            
            c.setValorImpostoCincoPorCento(rs.getString("IMPOSTO CONSUMO"));
            c.setValorImpostoSeisPorCento(rs.getString("IMPOSTO SELO"));
            
//            c.setFranquia(rs.getString("IMPOSTO FGA"));
            
            oRe[0]=c;
            oRe[1]=rs.getString("APOLICE");
            oRe[2]=rs.getString("CODIGO REGISTRADO");
            oRe[3]=rs.getString("ID CLIENTE");
            oRe[4]=rs.getString("USER");
//            oRe[5]=rs.getString("EXCESSO");
            
            System.err.println("este é o contrato -->-> <-<--\n"+c);
            return oRe;
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public ArrayList<Veiculo> loadDataContratoVeiculo(int idCtt, String codSeguro)
    {
        func_LoadContrato(codSeguro, idCtt);
   
        Call.forEchaResultSet((map)->
        {
            Veiculo currentVeicu;
            if(map.get("CATEGORIA").equals("VEICULO"))
            {
                Class classVeiculo = Veiculo.class;
                String attributeName = map.get("COD").toString();
                Object value = map.get("VALUE_1");
                currentVeicu = novoVeiculo(map);
                putFieledValue(classVeiculo, attributeName, currentVeicu, value);
                    
              
            }
            if(map.get("CATEGORIA").equals("COBERTURA"))
            {
                currentVeicu = novoVeiculo(map);
                if(map.get("COD").toString().equals("tipoCobertura"))currentVeicu.setTipoCobertura((String)map.get("VALUE_1"));
            }
        }, rs);
        
        ArrayList<Veiculo> lista = new ArrayList<>();
        listVeiculos.entrySet().stream().map((entry) -> entry.getValue()).forEach((value) -> {
            lista.add(value);
//            System.err.println(value);
        });
        
        return lista;    
    }
    
//    public static String insuranceCover(int idContrato, String codigoSeguro)
//    {
//            
//    }
    public void putFieledValue(Class classVeiculo, String attributeName, Object objectRecive, Object value)
    {
        try 
        {
            Field field = classVeiculo.getDeclaredField(attributeName);
            field.setAccessible(true);
            field.set(objectRecive, value);
            field.setAccessible(false);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Veiculo novoVeiculo(HashMap<String, Object> map)
    {
        Veiculo currentVeicu;
        String id = map.get("CATEGIRIA_COD").toString();
        if(!listVeiculos.containsKey(id))
        {
            currentVeicu = new Veiculo();
            listVeiculos.put(id, currentVeicu);
        }
        else currentVeicu = listVeiculos.get(id);
        return currentVeicu;
    }
    public Object[] loadDataContratoViagem(int idCtt, String codSeguro)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        func_LoadContrato(codSeguro, idCtt);
        HashMap<String, Viagem> listViagem = new HashMap<>();
        
        Call.forEchaResultSet((map)->
        {
            Viagem currentPassage;
            if(map.get("CATEGORIA").equals("PASSAGEIRO"))
            {
                try 
                {
                    String id = map.get("ID").toString();
                    if(!listViagem.containsKey(id))
                    {
                        currentPassage = new Viagem();
                        listViagem.put(id, currentPassage);
                    }
                    else currentPassage = listViagem.get(id);
                    if(map.get("COD").equals("nomePessoaSegurada"))currentPassage.setNomePessoaSegurada(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("tipoDoc"))currentPassage.setTipoDoc(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("numDoc"))currentPassage.setNumDoc(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("dataNasc"))currentPassage.setDataNascFormatada( sdf.format(sdfDat.parse(map.get("VALUE_1").toString())) );
                    if(map.get("COD").equals("localNascimento"))currentPassage.setLocalNascimento(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("dataEmissao"))currentPassage.setDataEmissaoFormatada(sdf.format(sdfDat.parse(map.get("VALUE_1").toString())));
                    if(map.get("COD").equals("localEmisao"))currentPassage.setLocalEmissao(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("endereco"))currentPassage.setEndereco(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("sexo"))currentPassage.setSexo(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("telefone"))currentPassage.setTelefone(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("outrasInformacao"))currentPassage.setOutrasInformacoes(((map.get("VALUE_1")==null)? "":map.get("VALUE_1").toString()));
                    if(map.get("COD").equals("numApolice"))currentPassage.setNumApolice(((map.get("VALUE_1")==null)? "":map.get("VALUE_1").toString()));
                    if(map.get("COD").equals("paisDestino"))currentPassage.setPaisDestino(((map.get("VALUE_1")==null)? "":map.get("VALUE_1").toString()));
                    try{
                        if(map.get("COD").equals("dataInicio"))currentPassage.setDataInicio(sdfIng.parse((String) map.get("VALUE_1")));
                        if(map.get("COD").equals("dataFim"))currentPassage.setDataFim(sdfIng.parse((String) map.get("VALUE_1")));
                    }catch(Exception e) { System.err.println(e); }
                    if(map.get("COD").equals("objetivoViagem"))currentPassage.setObjetivoViagem(((map.get("VALUE_1")==null)? "":map.get("VALUE_1").toString()));
               } catch (IllegalArgumentException | SecurityException | ParseException ex) {
                    Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
               }
                
            }
            else if(map.get("CATEGORIA").equals("VIAGEM"))
            {
                try {
                    if(i==0)
                    {
                        i++;
                        currentViagem = new Viagem();
                        
                    }
                    
                    if(map.get("COD").equals("objetivoViagem"))currentViagem.setObjetivoViagem((String)map.get("VALUE_1"));
                    if(map.get("COD").equals("dataInicio"))currentViagem.setDataInicio(sdfDat.parse((String)map.get("VALUE_1")));
                    if(map.get("COD").equals("dataFim"))currentViagem.setDataFim(sdfDat.parse((String) map.get("VALUE_1")));
                    if(map.get("COD").equals("numDias"))currentViagem.setDias((String)map.get("VALUE_1"));
                    if(map.get("COD").equals("idPaisDestino"))currentViagem.setPaisDestino((String)map.get("VALUE_1"));                
                    if(map.get("COD").equals("impostoConsumo"))currentViagem.setImpostoConsumo((map.get("VALUE_1").toString().startsWith(","))? "0"+map.get("VALUE_1").toString()
                            : map.get("VALUE_1").toString());
                    if(map.get("COD").equals("impostoSelo"))currentViagem.setImpostoSelo((map.get("VALUE_1").toString().startsWith(","))? "0"+map.get("VALUE_1").toString()
                            : map.get("VALUE_1").toString());
                    if(map.get("COD").equals("niconComisao"))currentViagem.setNiconComissao(map.get("VALUE_1").toString());
                } catch (ParseException ex) {
                    Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }, rs);
        
        ArrayList<Viagem> lista = new ArrayList<>();
        listViagem.entrySet().stream().map((entry) -> entry.getValue()).forEach((value) -> {
            lista.add(value);
        });
        Object[] os = new Object[2];
        os[0]=lista;
        os[1]=currentViagem;
        return os;
        
    }
    public ArrayList<AcidentePG> loadDataContratoAPG(int idCtt, String codSeguro)
    {
        func_LoadContrato(codSeguro, idCtt);
        HashMap<String, AcidentePG> listFuncio = new HashMap<>();
        HashMap<String, CoberturaLoad> listCobertura = new HashMap<>();
        
        Call.forEchaResultSet((map)->
        {
            CoberturaLoad objCoberturaLoad;
            if(map.get("CATEGORIA").equals("PROFISSIONAL"))
            {
                try 
                {
                    String id = map.get("ID").toString();
                    if(!listFuncio.containsKey(id))
                    {
                        currentPessoas = new AcidentePG();
                        listFuncio.put(id, currentPessoas);
                    }
                    else currentPessoas = listFuncio.get(id);
                    if(map.get("COD").equals("nome"))currentPessoas.setNome(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("categoria"))currentPessoas.setCategoria(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("profissao"))currentPessoas.setProfissao(map.get("VALUE_1").toString());
                    if(map.get("COD").equals("dataNascimento"))currentPessoas.setDataNascimentoFormatada(map.get("VALUE_1").toString());
//                    if(map.get("COD").equals("acidente3Anos"))currentPessoas.setAcidenteUltimosTresAnos(map.get("VALUE_1").toString());
               } catch (IllegalArgumentException | SecurityException ex) {
                   
                   
                    Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
               }
                
            }
            else if(map.get("CATEGORIA").equals("COBERTURA"))
            {
                if(listCobertura.containsKey(map.get("ID").toString()))
                {
//                    System.err.println("Carrear id"+ map.get("ID"));
                    objCoberturaLoad = listCobertura.get(map.get("ID").toString());
                }
                else 
                {
//                    System.err.println("Criando id"+ map.get("ID")+" é");
                    objCoberturaLoad = new CoberturaLoad();
                    listCobertura.put(map.get("ID").toString(), objCoberturaLoad);
                }
                
                Class classCobertura = CoberturaLoad.class;
                String attributeName = map.get("COD").toString();
                Object value = map.get("VALUE_1");
                
                putFieledValue(classCobertura, attributeName, objCoberturaLoad, value);
                
                passagemValorAPg(currentPessoas, objCoberturaLoad);
            }
            
        }, rs);
        
        ArrayList<AcidentePG> lista = new ArrayList<>();
        
        for (Map.Entry<String, AcidentePG> entry : listFuncio.entrySet()) {
            AcidentePG value = entry.getValue();
            lista.add(value);
        }
        
        return lista;
        
    }
    public void passagemValorAPg(AcidentePG g,CoberturaLoad cl)
    {
   
        switch (cl.getTipoCobertura()) {
            case "Morte":
                if(haveValor(cl) == true)
                    g.setValorMorte(cl.getValorPremio());
                System.out.println("valor morte "+cl.getValorPremio());
                if (prontoParaCalualr(cl)) {
                        System.out.println("premio morte "+Calculo(cl));
                    g.setPremioMorteMoeda(Calculo(cl));
           
                }
                break;
            case "Despesa Medica":
                if(haveValor(cl) == true)
                    g.setDespesaMedica(cl.getValorPremio());
                if (prontoParaCalualr(cl) == true) {
                    g.setPremioDespesaMedicaMoeda(Calculo(cl));
                }
                break;
            case "Incapacidade total temporaria":
                if(haveValor(cl) == true)
                    g.setIncapacidadeTotalTemporaria(cl.getValorPremio());
                if (prontoParaCalualr(cl) == true) {
                    g.setPremioIncapacidadeTemporariaMoeda(Calculo(cl));
                }
                break;
            case "Incapacidade total Permanete":
                if(haveValor(cl) == true)
                    g.setIncapacidadeTotal(cl.getValorPremio());
                if (prontoParaCalualr(cl) == true) {
                    g.setPremioIncapacidadePermanenteMoeda(Calculo(cl));
                }
                break;
            case "Custo com repatreamento":
                if(haveValor(cl) == true)
                    g.setCustoRepatriamento(cl.getValorPremio());
                if (prontoParaCalualr(cl) == true) {
                    g.setPremioCustoRepatriamentoMoeda(Calculo(cl));
                }
                break;
        }
    }
    public boolean prontoParaCalualr(CoberturaLoad cl)
    {
        return (cl.getTaxa()!=null && !cl.getTaxa().equals("")) && 
               (cl.getValorPremio()!=null && !cl.getValorPremio().equals(""));
    }
    public boolean haveValor(CoberturaLoad cl)
    {
        return cl.getValorPremio() != null && !cl.getValorPremio().equals("");
    }
    public String Calculo(CoberturaLoad cl)
    {
        Double valor =  (Double.valueOf(cl.getTaxa())/100)* Double.valueOf(cl.getValorPremio());
       
        return valor+"";
    }
    
    public Incendio dadosSeguradoIncendio(int idCtt, String codigoSeguro)
    {
        func_LoadContrato(codigoSeguro, idCtt);
        Incendio incendio = new Incendio();
                
        Call.forEchaResultSet((map)->
        {
           if((map.get("CATEGORIA")+"").equals("INCENDIO"))
           {
                if(map.get("COD").equals("processoFabricacao"))
                   incendio.setProcessoFabricacao(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("descricaoUsoEdificio"))
                   incendio.setUsoEdificioDescricao(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("distanciaComBombeiro"))
                   incendio.setDistancia(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("ano"))
                   incendio.setAno(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("numeroAndares"))
                   incendio.setAndar(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("equipaCombateIncendio"))
                   incendio.setEquipamento(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("fonteAguaDisponivel"))
                   incendio.setFonte(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("condicao"))
                   incendio.setCondicao(obPASSstr(map.get("VALUE_1")));
           }
        }, rs);
      return incendio;
    }
    
    public List<ResponsabilidadePublica> dadosSeguradoResponsabilidadePublica(int idCtt, String codSeguro)
    {
        func_LoadContrato(codSeguro, idCtt);
         List<ResponsabilidadePublica> list = new ArrayList<>();
        ResponsabilidadePublica rp = new ResponsabilidadePublica();
        rp.setCover("");
         Call.forEchaResultSet((map)->
        {
            if((map.get("CATEGORIA")+"").equals("TRABALHADOR"))
            {
             
                if(map.get("COD").equals("enderecoEdificio"))               
                    rp.setEndereco(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("profissao"))
                    rp.setProfissao(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("empregado"))
                    rp.setEmpregado(obPASSstr(map.get("VALUE_1")));
            }
            if((map.get("CATEGORIA")+"").equals("COBERTURA CTT"))
            {
                if(map.get("COD").equals("tipoCobertura"))               
                    rp.setCover(rp.getCover()+" "+obPASSstr(map.get("VALUE_1")));
            }
          
            list.add(rp);   
        }, rs);
        return list;
    }
    
  
    public IncendioRisco loadDataContratoIncendio(int idCtt, String codSeguro)
    {
        func_LoadContrato(codSeguro, idCtt);
        IncendioRisco ir = new IncendioRisco();
        Incendio incendio = new Incendio();
        Call.forEchaResultSet((map)->
        {
           if((map.get("CATEGORIA")+"").equals("COBERTURA CTT"))
           {
               if(map.get("VALUE_1").equals("Aviao"))
               {
                   ir.setRiscoAviaoId("true");
                   ir.setCover("Avião");
               }
               if(map.get("VALUE_1").equals("Colisão"))
               {
                   ir.setColisaoId("true");
                   ir.setCover(ir.getCover()+" "+"Colisão");
               }
               if(map.get("VALUE_1").equals("Explusão"))
               {
                   ir.setExplosaoId("true");
                   ir.setCover(ir.getCover()+" "+"Explosão");
               }
               if(map.get("VALUE_1").equals("Terramorto"))
               {
                   ir.setTerramotoId("true");
                   ir.setCover(ir.getCover()+" "+"Terramoto");
               }
               if(map.get("VALUE_1").equals("Tempestade"))
               {
                   ir.setTempestadeId("true");
                   ir.setCover(ir.getCover()+" "+"Tempestade");
               }
               if(map.get("VALUE_1").equals("Afundamento"))
               {
                   ir.setAfundamentoId("true");
                   ir.setCover(ir.getCover()+" "+"Afundamento");
               }
               if(map.get("VALUE_1").equals("Risco Politico"))
               {
                   ir.setRiscoPoliticoId("true");
                      ir.setCover(ir.getCover()+" "+"Risco Politico");
               }
               if(map.get("VALUE_1").equals("Danos maliciosos"))
               {
                   ir.setDanoId("true");
                    ir.setCover(ir.getCover()+" "+"Danos Maliciosos");
               }
               if(map.get("VALUE_1").equals("Tumultos e greves"))
               {
                   ir.setTumultoId("true");
                   ir.setCover(ir.getCover()+" "+"Tumultos e greves");
               }
               if(map.get("VALUE_1").equals("Incendio Florestal"))
               {
                   ir.setIncendioFId("true");
                   ir.setCover(ir.getCover()+" "+"Incêndio Florestal");
               }
               if(map.get("VALUE_1").equals("Expontánea"))
                   ir.setExplosaoExId("true");
               if(map.get("VALUE_1").equals("Roptura de canalização"))
               {
                   ir.setRoturaId("true");
                   ir.setCover(ir.getCover()+" "+"Rotura de canalização");
               }
           }   
        }, rs);
        
        return ir;
        
    }
    public Maritimo loadDataContratoMaritimo(int idCtt, String codSeguro)
    {
        func_LoadContrato(codSeguro, idCtt);
        Maritimo m = new Maritimo();
        m.setCover("");
        Call.forEchaResultSet((map)->
        {
           if((map.get("CATEGORIA")+"").equals("BARCO"))
           {
               if(map.get("COD").equals("condicaoNavio"))
                   m.setCondicaoAtual(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("arreaOperacao"))
                   m.setAreaOperacao(obPASSstr(map.get("VALUE_1")));
//               if(map.get("COD").equals("navegacaoInstalada"))
//                   m.set(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("experienciaRenovacao"))
                   m.setEstruturaRenovacao(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("tipoNavio"))
                   m.setTipoNavio(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("idadeNavio"))
                   m.setIdadeNavio(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("numeroChassi"))
                   m.setNumeroChassi(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("marcaModeloNavio"))
                   m.setMarcaModelo(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("nomeNavio"))
                   m.setNomeNavio(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("numeroTripulantes"))
                   m.setNumMaximoTripulante(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("marcaMotor"))
                   m.setMarcaMotor(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("numeroMotor"))
                   m.setNumMotor(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("quantidadePeso"))
                   m.setPeso(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("tipoCombustivel"))
                   m.setTipoCombustivel(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("potenciaMotor"))
                   m.setPotenciaMotor(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("classeEstruturaRenovacao"))
                   m.setClasseRenovacao(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("usoNavio"))
                   m.setUsoNavio(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("bandeiraNavio"))
                   m.setBandeiraNavio(obPASSstr(map.get("VALUE_1")));
           }  
           if((map.get("CATEGORIA")+"").contains("COBERTURA"))
           {
                if(map.get("COD").equals("tipoCobertura"))
                    m.setCover(m.getCover()+" "+obPASSstr(map.get("VALUE_1")));
           }
        }, rs);
        
        return m;
        
    }
    public Object[] loadDataContratoRoubo(int idCtt, String codSeguro)
    {
        Object[] os = new Object[2];
        func_LoadContrato(codSeguro, idCtt);
        ArrayList<Roubo> al = new ArrayList<>();
        HashMap<String, Roubo> listaRoubo = new HashMap<>();
    
        Call.forEchaResultSet((map)->
        {
           Roubo r;
           if((map.get("CATEGORIA")+"").equals("PROPIEDADE"))
           {
               String key = map.get("CATEGIRIA_COD").toString();
               if(!listaRoubo.containsKey(key))
               {
                   r = new Roubo();
                   listaRoubo.put(key, r);
               }
               else r = listaRoubo.get(key);
               
               if(obPASSstr(map.get("COD")).equals("descrincao")) {r.setDescricao(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("valor")) {r.setValor(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("modelo")) {r.setModelo(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("quantidade")) {r.setQuantidade(obPASSstr(map.get("VALUE_1")));}
           }  
           if((map.get("CATEGORIA")+"").equals("EDIFICIO"))
           {
               if(obPASSstr(map.get("COD")).equals("dataAquisicaoCrofe"))
               {
                   try {cR.setDataAquisicao(sdfDat.parse(obPASSstr(map.get("VALUE_1"))));} 
                   catch (ParseException ex) 
                   {Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);}
               }
               if(obPASSstr(map.get("COD")).equals("marcaCrofe")) {cR.setMarcaCofre(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("valorCrofe")) {cR.setValorCofre(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("dataOcupacao")) {cR.setDatatInspecao(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("tempoOcupacao")) {cR.setTempoOcupacao(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("enderecoEdificio")) {cR.setEnderecoEdificio(obPASSstr(map.get("VALUE_1")));}
//               if(obPASSstr(map.get("COD")).equals("descricaoTipoVeiculo")) {cR.setd(obPASSstr(map.get("VALUE_1")));}
               if(obPASSstr(map.get("COD")).equals("tipoEdificio")) {cR.setTipoEdificio(obPASSstr(map.get("VALUE_1")));}
           }  
        }, rs);
        
        for (Map.Entry<String, Roubo> entry : listaRoubo.entrySet()) {
            Roubo value = entry.getValue();
            al.add(value);
        }
        os[0]=al;
        os[1]=cR;
        return os;
        
    }
    
    public Dinheiro dadosSeguradoDinheiro(int idCtt, String codSeguro)
    {
        func_LoadContrato(codSeguro, idCtt);
        Dinheiro dinheiro = new Dinheiro();    
        dinheiro.setCover("");
        Call.forEchaResultSet((map)->
        {         
            if((map.get("CATEGORIA")+"").equals("DINHEIRO"))
            {
                if(map.get("COD").equals("dinheiroPagoRecepcao"))
                    dinheiro.setPagamento1item((map.get("VALUE_1").equals("Y")) ? "Sim" : "Não");
                if(map.get("COD").equals("preocupacao"))
                     dinheiro.setPrecupacao(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("transpDinheiro"))
                    dinheiro.setTransporteDinheiro(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("distanciaOutra"))
                    dinheiro.setOutros(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("distanciaCoreio"))
                    dinheiro.setCorreio(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("distanciaBanco"))
                    dinheiro.setBanco(obPASSstr(map.get("VALUE_1")));
            }
             if((map.get("CATEGORIA")+"").equals("COBERTURA CTT"))
            {
                if(map.get("COD").equals("tipoCobertura"))
                    dinheiro.setCover(dinheiro.getCover()+" "+obPASSstr(map.get("VALUE_1")));
            }
        }, rs); 
        return  dinheiro;
    }
      
    public CargaMaritima loadDataContratoCargaMaritima(int idCtt, String codSeguro)
    {
        func_LoadContrato(codSeguro, idCtt);
  
        CargaMaritima cm = new CargaMaritima();
        cm.setCover("");
        Call.forEchaResultSet((map)->
        {
           if((map.get("CATEGORIA")+"").equals("CARGA_MARITIMA"))
           {
                if(map.get("COD").equals("paisOrigem"))
                   cm.setPaisOrigem(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("paisDestino"))
                   cm.setPaisDestino(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("portoCarga"))
                   cm.setPortoCarga(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("portoDescarga"))
                   cm.setPortoDescarga(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("nomeNavio"))
                   cm.setNomeNavio(obPASSstr(map.get("VALUE_1")));
                if(map.get("COD").equals("propositoSeguro"))
                   cm.setProposito(obPASSstr(map.get("VALUE_1")));
           }  
           if((map.get("CATEGORIA")+"").equals("TRANSPORTE"))
           {
               if(map.get("COD").equals("paisDestino"))
                   cm.setPaisDestino(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("paisOrigem"))
                   cm.setPaisOrigem(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("portoCarga"))
                   cm.setPortoCarga(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("portoDescarga"))
                   cm.setPortoDescarga(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("nomeNavio"))
                   cm.setNomeNavio(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("propositoSeguro"))
                   cm.setProposito(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("qualquerNavio"))
                   cm.setQualquerNavio(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("qualquerLocalizacao"))
                   cm.setQualquerMercadoria(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("anualMercadoria"))
                   cm.setAnualParaCadaMercadoria(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("tempoNegocio"))
                   cm.setTempoNegocio(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("custoPorto"))
                   cm.setCustoPorto(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("descricaoMercadoria"))
                   cm.setDecricaoMecadoria(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("seguroEfectuadoCompanhia"))
                   cm.setEpecifiqueCompanhina(obPASSstr(map.get("VALUE_1")));
               if(map.get("COD").equals("areaActivid"))
                   cm.setAreaComercail(obPASSstr(map.get("VALUE_1")));
           }  
           if((map.get("CATEGORIA")+"").equals("COBERTURA CTT"))
           {
               if((map.get("COD")).equals("tipoCobertura"))
               {
                   cm.setCobertura(obPASSstr(map.get("VALUE_1")));
                   cm.setCover(cm.getCover()+" "+obPASSstr(map.get("VALUE_1")));
               }
           }  
           if((map.get("CATEGORIA")+"").equals("COBERTURA"))
           {
               if((map.get("COD")).equals("premio"))
                   cm.setValorLimiteIndeminizacao(obPASSstr(map.get("VALUE_1")));
               if((map.get("COD")).equals("valorPremio"))
                   cm.setValorPremioReal(obPASSstr(map.get("VALUE_1")));
               if((map.get("COD")).equals("taxa"))
                   cm.setTaxaValorLimiteIndeminizacao(obPASSstr(map.get("VALUE_1")));
           }  
        }, rs);
        
        return cm;
        
    }
    public void func_LoadContrato(String codSeguro, int idCtt)
    {
        Object idUser = SessionUtil.getUserlogado().getId();
        rs = Call.callTableFunction("FUNCT_LOAD_DATA_CONTRATO", "*", idUser, codSeguro, idCtt );
    }
    public String obPASSstr(Object o)
    {
        return ((o==null))? null : o.toString();
    }
    public ArrayList listaTipoPagamento()
    {
        try
        {
            ArrayList<ComoBox> list = new ArrayList<>();
            rs = Call.selectFrom("VER_TIPO_PAGAMENTO", "*");
            if(rs==null)
            {
                list.add(0, new  ComoBox("-1", "selecione"));
                return list;
            }
            while (rs.next()) 
            {list.add( new ComoBox(rs.getString("ID"), rs.getString("PAGAMENTO")));}
            return list;
        } 
        catch (SQLException ex) 
        {Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }
    
    public void regPestacao(String codContrato, ArrayList<ModeloPagamento> list)
    {
        for (ModeloPagamento mp : list) 
        {
            Call.callSampleFunction("FUNC_REG_PRESTACAO", Types.VARCHAR
            ,codContrato,SessionUtil.getUserlogado().getId(),mp.getValorPagameto()
            ,mp.getDataPagamentoLimite());
        }
        int num$d=12;
    }
    
    public List<DadosContrato> pesquisarContrato(String seguro, String campoPesquisa, String valorPesquisa)
    {
        List<DadosContrato> list = new ArrayList<>();
        String functionName;
        ResultSet  resultSet;
        if(seguro.equals("GOLD") || seguro.equals("MCARO") || seguro.equals("ATRASADO") || seguro.equals("TPAGOS"))
            functionName = "PACK_PESQUISA.pesqContratoType";
        else if(seguro.equals("ANULADOS"))
            functionName = "VER_CONTRATO_ANULADO";
        else
            functionName =  "PACK_PESQUISA.pesqContratoSeguro";
   
        
        if(valorPesquisa == null || valorPesquisa.equals(""))
        {
            if(functionName.contains("ANULADO"))
                resultSet = Call.selectFrom(functionName, "*");
            else
                resultSet = Call.callTableFunction(functionName+"", "*",seguro);
        }
        else
        {
            if(seguro.contains("ANULADO"))
            {
                  resultSet = Call.selectFrom(functionName+" WHERE UPPER(APOLICE) LIKE UPPER('%" + valorPesquisa + "%') OR"
                          + " UPPER(CLIENTE) LIKE UPPER('%" + valorPesquisa + "%') OR UPPER(SEGURO) LIKE UPPER('%" + valorPesquisa + "%')", "*");
            }
            else
            {
                resultSet = Call.selectFrom("TABLE("+functionName+"(?))"
                    + " WHERE UPPER(APOLICE) LIKE UPPER(?) OR UPPER(SEGURO) LIKE UPPER(?) OR UPPER(CLIENTE) LIKE UPPER(?)", "*",
                 seguro,
                "%"+valorPesquisa+"%",
                "%"+valorPesquisa+"%",
                "%"+valorPesquisa+"%"); 
            }    
        }
        if(resultSet != null)
        {
            try 
            {
                while(resultSet.next())
                {
                    if(!functionName.contains("ANULADO"))
                    {     
                        if(resultSet.getString("ESTADO").equals("Activo"))
                        {
                            DadosContrato dadosContrato = new DadosContrato();
                            dadosContrato.setNotaDebito(resultSet.getString("NOTA DEBITO"));
                            dadosContrato.setId(resultSet.getString("ID"));
                            dadosContrato.setIdSeguro(resultSet.getString("ID SEGURO"));
                            dadosContrato.setIdCliente(resultSet.getString("ID CLIENTE"));
                            dadosContrato.setApolice(resultSet.getString("APOLICE"));
                            dadosContrato.setData(resultSet.getString("DATA"));
                            dadosContrato.setSeguro(resultSet.getString("SEGURO"));
                            dadosContrato.setCliente(resultSet.getString("CLIENTE"));
                            dadosContrato.setPremioBruto(resultSet.getString("PREMIO BRUTO"));
                            dadosContrato.setDataInicio(resultSet.getString("DATA INICIO"));
                            dadosContrato.setDataFim(resultSet.getString("DATA FIM"));
                            dadosContrato.setDataContrato(resultSet.getString("DATA CONTRATO"));
                            dadosContrato.setPremioLiquido(resultSet.getString("PREMIO LIQUIDO"));
                            dadosContrato.setEstadoPagamento(resultSet.getInt("ESTADO PAGAMENTO"));
                            dadosContrato.setLimiteResponsabilidade(resultSet.getString("LIMETE RESPONSABILIDADE"));
                            dadosContrato.setEstado("Activo");
                            list.add(dadosContrato);
                        }
                    }
                    else
                    {
                        DadosContrato dadosContrato = new DadosContrato();
                        dadosContrato.setNotaDebito(resultSet.getString("NOTA DEBITO"));
                        dadosContrato.setId(resultSet.getString("ID"));
                        dadosContrato.setIdSeguro(resultSet.getString("ID SEGURO"));
                        dadosContrato.setIdCliente(resultSet.getString("ID CLIENTE"));
                        dadosContrato.setApolice(resultSet.getString("APOLICE"));
                        dadosContrato.setData(resultSet.getString("DATA"));
                        dadosContrato.setSeguro(resultSet.getString("SEGURO"));
                        dadosContrato.setCliente(resultSet.getString("CLIENTE"));
                        dadosContrato.setPremioBruto(resultSet.getString("PREMIO BRUTO"));
                        dadosContrato.setDataInicio(resultSet.getString("DATA INICIO"));
                        dadosContrato.setDataFim(resultSet.getString("DATA FIM"));
                        dadosContrato.setDataContrato(resultSet.getString("DATA CONTRATO"));
                        dadosContrato.setPremioLiquido(resultSet.getString("PREMIO LIQUIDO"));
                        dadosContrato.setLimiteResponsabilidade(resultSet.getString("LIMETE RESPONSABILIDADE"));
                        dadosContrato.setEstado("Anulado");
                        list.add(dadosContrato);
                    }
                }
                resultSet.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erro a pesquisar contrato "+ex.getMessage());
            }
        }
        return list;
    }
    
    /**
     * Carrega o número de apolice de um determinado contrato pertencente a um determinado cliente
     * @param idCliente
     * @param idSeguro
     * @return 
     */
    public static Object loadApolice(int idCliente, int idSeguro)
    {
        String functionName ="PACK_CONTRATOS.funcLoadNumeroApolice";
        Object result = Call.callSampleFunction(functionName, Types.VARCHAR, idCliente,idSeguro);
        return result;
    }
    
    public static List<ComoBox> listaNotasDebito()
    {
        ResultSet rs = Call.selectFrom("VER_CONTRATO_COMPLETO", "*");
        List<ComoBox> list = new ArrayList<>();
        if(rs != null)
        {
            try
            {
                while(rs.next())
                {
                    list.add(new ComoBox(rs.getString("ID"), rs.getString("CODIGO EXTERNO")));
                }
                rs.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public static String regComissao(Comissao comissao)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
        Object result = Call.callSampleFunction("PACK_CONTA.funcRegComisao", Types.VARCHAR , 
                idUser, 
                Integer.valueOf(comissao.getFuncionario()),
                Integer.valueOf(comissao.getContrato()),
                Float.valueOf(comissao.getPercentagem()),
                OperacaoData.toSQLDate(comissao.getData()));
        return result.toString();
    }
    
    public static String registrarResseguro(Resseguro resseguro)
    {
      //  (idUser NUMBER, idTipoReseguro NUMBER, idContrato NUMBER, inicion DATE, fim DATE, descrincao CHARACTER VARYING, deducao FLOAT, limite FLOAT, apolice CHARACTER VARYING, tipoCobertura CHARACTER VARYING, idMoeda NUMBER, premioGrosso FLOAT, nomeCliente CHARACTER VARYING, idSeguro NUMBER) RETURN CHARACTER VARYING;
           int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
        Object result = Call.callSampleFunction("pack_contratos.funcRegReseguro", Types.VARCHAR, 
                idUser,
                resseguro.getIdTipoResseguro(),
                 ((resseguro.getIdContrato() != null && !resseguro.getIdContrato().equals("")) ? resseguro.getIdContrato() : null),
                OperacaoData.toSQLDate(resseguro.getDataInicio()),
                OperacaoData.toSQLDate(resseguro.getDataFim()),
                resseguro.getDescricao(),
                ((resseguro.getValorDeducao() != null && !resseguro.getValorDeducao().equals("")) ? Double.valueOf(resseguro.getValorDeducao()) : null),
                ((Validacao.isNumeric(resseguro.getLimiteResp()) == true) ? Double.valueOf(resseguro.getLimiteResp()) : null),
                resseguro.getApolice(),
                ((resseguro.getTipoCobertura() != null && !resseguro.getTipoCobertura().equals("")) ? resseguro.getTipoCobertura() : null),
                Integer.valueOf(resseguro.getMoeda()),
                Double.valueOf(resseguro.getPremioGrosso()),
                resseguro.getNomeCliente(),
                resseguro.getIdSeguro()  ,
                resseguro.getNotaDebito()
                );
        return result.toString();
    }
    
    public static List<Resseguro> listagemResseguros(String pesquisa)
    {
        List<Resseguro> listaResseguros = new ArrayList<>();
        ResultSet resultSet;
        String sql = "VER_RESEGUROS";
        if(pesquisa  == null || pesquisa.equals(""))
             resultSet = Call.selectFrom(sql, "*");
        else
        {
            sql="VER_RESEGUROS WHERE UPPER(APOLICE) LIKE UPPER('%"+pesquisa+"%') OR UPPER(CLIENTE) LIKE UPPER('%"+pesquisa+"%') OR UPPER(MOEDA) LIKE UPPER('%"+pesquisa+"%')";
            resultSet = Call.selectFrom(sql, "*");
        }
        
        if(resultSet != null)
        {
            try 
            {
                while(resultSet.next())
                {
                    Resseguro r = new Resseguro();
                    r.setApolice(resultSet.getString("APOLICE"));
                    r.setNomeCliente(resultSet.getString("CLIENTE"));
                    r.setMoeda(resultSet.getString("ID MOEDA"));
                    r.setMoedaSigla(resultSet.getString("MOEDA"));
                    r.setLimiteResp(resultSet.getString("LIMITE"));
                    r.setDescricao(resultSet.getString("DESCRICAO"));
                    r.setPremioGrosso(resultSet.getString("PREMIO GROSSO"));
                    r.setPremioGrosso(r.getPremioGrosso().replace(',', '.'));
                    r.setTipoResseguro(resultSet.getString("TIPO RESEGURO"));
                    r.setDataInicio(sdfDat.parse(resultSet.getString("INICIO")));
                    r.setDataFim(sdfDat.parse(resultSet.getString("FIM")));
                    r.setDataRegistro(resultSet.getDate("DT REG"));
                    r.setDataI(resultSet.getString("INICIO"));
                    r.setNotaDebito(resultSet.getString("NUM DEBITO"));
                    r.setDataF(resultSet.getString("FIM"));
                    r.setLimiteResp(resultSet.getString("LIMITE"));
                    r.setTipoCobertura(resultSet.getString("TIPO COBERTURA"));
                    r.setId(resultSet.getInt("ID"));
                    r.setIdSeguro(resultSet.getInt("ID SEGURO"));
                    listaResseguros.add(r);
                }
                resultSet.close();
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaResseguros;
    }
    public static String registrarResponsabilidade(Resseguro resseguro, int id)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
        Object result = Call.callSampleFunction("pack_contratos.funcResponsabilidade", Types.VARCHAR, 
                idUser,
                resseguro.getIdEmpresa(),
                id,
                ((resseguro.getPercentagem() != null && !resseguro.getPercentagem().equals(""))? Double.valueOf(resseguro.getPercentagem()) : null),
                ((resseguro.getValorPremio() != null && !resseguro.getValorPremio().equals(""))? Double.valueOf(resseguro.getValorPremio()) : null),
                ((resseguro.getRisco() != null && !resseguro.getRisco().equals("") && !resseguro.getRisco().equals("Ilimitado"))? Double.valueOf(resseguro.getRisco()) : null)
                );
        return result.toString();
    }
    
    public static String anularResseguro(Resseguro r)
    {
          int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
          Object result = Call.callSampleFunction("FUNC_REG_RESEGUROSTATE", Types.VARCHAR,
                 idUser,
                 Integer.valueOf(r.getId()),
                 -1,
                 r.getDescricao()
            );
          return result.toString();
                  
    }
    
    public static String endResseguro(int idResseguro)
    {
        Object result = Call.callSampleFunction("pack_contratos.funcEndReseguro", Types.VARCHAR, idResseguro);
        return result.toString();
    }
    
    /**
     * 
     * @param idContrato codigo do contrato devolvido
     * @param idSeguro o número de identificação do seguro
     * @param numSegurado total de pessoas seguradas
     * @param numSubContratos o número de subcontratos
     * @param idSubContratos a identificação de subcontratos
     * @param result 1 se o contrato for registrado com sucesso e -1 caso contrário
     * @param viagem
     * @return 
     */
    public static void endRegInsurance(int idContrato, int idSeguro, int numSegurado, int numSubContratos, String idSubContratos, int result, Viagem viagem)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
        Object resp = Call.callSampleFunction("FUNC_ON_POS_REG_CONTRATO ", Types.VARCHAR, 
                idUser,
                idContrato,
                idSeguro,
                numSegurado,
                numSubContratos,
                idSubContratos,
                ((viagem != null) ? Float.valueOf(viagem.getSumNc()) : null),
                ((viagem != null) ? Float.valueOf(viagem.getSumSubTotal()) : null),
                ((viagem != null) ? Float.valueOf(viagem.getSumTotal()) : null),
                result
                 );
    }
    
    public static String registrarNotaCredito(NotaDebito notaDebito)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId()+"");
        Object resp = Call.callSampleFunction("PACK_CONTRATOS.FUNCREGNOTCREDITO ", Types.VARCHAR, 
                idUser,
                notaDebito.getIdContrato(),
                notaDebito.getNumRegistro(),
                notaDebito.getBeneficiario(),
                notaDebito.getEnderecoEntrega(),
                notaDebito.getDescricao(),
                Double.valueOf(notaDebito.getPremioGrosso()),
                Double.valueOf(notaDebito.getValorDeducao()),
                notaDebito.getTotalPagar()
                 );
        return resp.toString();
    }

    
 }
