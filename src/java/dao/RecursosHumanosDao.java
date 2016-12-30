
package dao;

import Export.GenericExcel;
import Export.GenericPDFs;
import bean.DataTableControl;
import conexao.Call;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.Moeda;
import modelo.Artigo;
import modelo.Categoria;
import modelo.ComoBox;
import modelo.Relatorio;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
public class RecursosHumanosDao implements Serializable
{
    private String functionName;
    
    public String registarCategoria(Categoria categoria, int operacao)
    {
        functionName = "PACK_RH.funcRegCategori";
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                idUser,
                categoria.getNome(),
                Integer.valueOf(categoria.getNivel()),
                Integer.valueOf(categoria.getNumDias()),
                Float.valueOf(categoria.getSalarioBase()),
                Float.valueOf(categoria.getSubsidioAlojamento()),
                Float.valueOf(categoria.getSubisidioAlmoco()),
                Float.valueOf(categoria.getSubisidioTransporte()),
                Float.valueOf(categoria.getAlmocoLivreImposto()),
                operacao // 1- registro, 2- editar
                );
        return resp.toString();
    }
    
    public List<Categoria> listagemCategorias(String campoPesquisa, String valorPesquisa, boolean rel, int i)
    {
        ResultSet rs;
        List<Categoria> categorias = new ArrayList<>();
        if(valorPesquisa==null || valorPesquisa.equals(""))
            rs = Call.selectFrom("VER_CATEGORIA_FUNC","*");
        else
             rs = Call.selectFrom("VER_CATEGORIA_FUNC WHERE UPPER(\""+campoPesquisa+"\") LIKE UPPER('%"+valorPesquisa+"%') "  ,"*");
        
        if(rs != null && !rel)
        {
            try 
            {
                while(rs.next())
                {
                    Categoria c = new Categoria(rs.getString("NOME"), rs.getString("NIVEL"), rs.getString("DIAS"),
                            Moeda.format(Double.valueOf(rs.getString("SALARIO BASE"))), Moeda.format(Double.valueOf(rs.getString("SUBSIDIO ALAGAMENTO"))),
                            Moeda.format(Double.valueOf(rs.getString("SUBSIDIO LANCHE"))), Moeda.format(Double.valueOf(rs.getString("SUBSIDIO TRANSPORTE"))), 
                            rs.getString("ESTADO"), rs.getString("REGISTO"), Moeda.format(Double.valueOf(rs.getString("TOTAL"))), Moeda.format(Double.valueOf(rs.getString("BONUS ALMOCO"))));
                    categorias.add(c);
                }
                rs.close();
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(RecursosHumanosDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
//            Nome	Nível	Salário Base	Subsídio Alojamento	Subsídio Almoço	Subsídio Transporte	Dias	Estado
            DataTableControl control = new DataTableControl("id55", "clienteff.fjfjf");
            control.prepareModel(rs, DataTableControl.ShowMode.HIDE,"REGISTO");
            control.renameColumn("NIVEL", "Nível");
            control.renameColumn("SALARIO BASE", ("Salário Base".toUpperCase()));
            control.renameColumn("SUBSIDIO ALAGAMENTO", ("Subsídio Alojamento".toUpperCase()));
            control.renameColumn("SUBSIDIO LANCHE", ("Subsídio Almoço".toUpperCase()));
            control.renameColumn("SUBSIDIO TRANSPORTE", ("Subsídio Transporte".toUpperCase()));
            if(i == 1)
            { GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Salario", "Relatório de Salário", control, GenericPDFs.OrientacaoPagina.HORIZONTAL,-1); }
            else if(i == 2)
            { GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Salario", "Relatório de Salário", control, -1); }
        }
        
        return categorias;
    }
    
    public List<ComoBox> categoryNames()
    {
        ResultSet rs;
        List<ComoBox> list = new ArrayList<>();
        String campo = "ID ENTIDADE";
        rs = Call.selectFrom("VER_OBJECTYPE WHERE \""+campo+"\"=26", "*");
        Consumer <HashMap<String, Object>> act  = (map)-> list.add(new ComoBox(map.get("ID").toString(), map.get("DESCRICAO").toString()));
        Call.forEchaResultSet(act, rs);
        return list;
    }
    
    public List<String> listaCategorias()
    {
         ResultSet rs;
        List<String> categoria = new ArrayList<>();
        String campo = "ID ENTIDADE";
        rs = Call.selectFrom("VER_OBJECTYPE WHERE \""+campo+"\"=26", "*");
        Consumer <HashMap<String, Object>> act  = (map)-> categoria.add(map.get("DESCRICAO").toString());
        Call.forEchaResultSet(act, rs);
        return categoria;
    }
    
    public List<ComoBox> employeers()
    {
        ResultSet rs;
        List<ComoBox> list = new ArrayList<>();
        String campo = "ID ENTIDADE";
        rs = Call.selectFrom("VER_DATA_FUNCIONARIO", "*");
        Consumer <HashMap<String, Object>> act  = (map)-> list.add(new ComoBox(map.get("ID").toString(), map.get("NOME").toString()+" "+map.get("APELIDO").toString()));
        Call.forEchaResultSet(act, rs);
        return list;
    }
    
    /**
     * Registrar um novo artigo
     * @param artigo
     * @return 
     */
    public String registarArtigo(Artigo  artigo)
    {
        functionName = "PACK_RH.funcRegItem";
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR, 
                  idUser,
                  artigo.getCategoria(),
                  artigo.getNomeArtigo(),
                  ((artigo.getDescricao() == null || artigo.getDescricao().equals("")) ? null : artigo.getDescricao()),
                  (artigo.getId() == null) ? null : artigo.getId()/// QUANDO ALTERA O NOME DO CONSUMIVEL E CATEGORIA ENVIA O ID CASO CONTRÁRIO ENVIA NULL
        );
        return resp.toString();
    }
    
    public String registrarItemArtigo(Artigo artigo, int idArtigo,int tipoOperacao)
    {
        int idUser = Integer.valueOf(SessionUtil.getUserlogado().getId().toString());
        functionName = "PACK_RH.funcRegItemMovimentation";
        Object resp = Call.callSampleFunction(functionName, Types.VARCHAR,
                idUser,
                idArtigo,
                Integer.valueOf(artigo.getFuncionarioResponsavel()),
                tipoOperacao, // id do tipo de movimento 1 entrada e 2 saida
                Integer.valueOf(artigo.getQuantidade()),
                ((artigo.getCustoEstimado()==null || artigo.getCustoEstimado().equals(""))? null : Float.valueOf(artigo.getCustoEstimado())),
                artigo.getFornecedor(),
                artigo.getDescricao()
        );
        return resp.toString();
    }
    
    public List<Artigo> listaArtigos()
    {
        ResultSet rs;
        List<Artigo> lista = new ArrayList<>();
        rs = Call.selectFrom("VER_ITEM_CONSUMIVEL", "*");
        if(rs != null)
        {
            try
            {
                while(rs.next())
                {
                    Artigo artigo = new Artigo();
                    artigo.setCodigo(rs.getString("ID"));
                    artigo.setCategoria(rs.getString("CATEGORIA"));
                    artigo.setConsumivel(rs.getString("CONSUMIVEL"));
                    artigo.setQuantidade(rs.getString("QUANTIDADE"));
                    artigo.setRegistro(rs.getString("REGISTRO"));
                    artigo.setEstado(rs.getString("ESTADO"));
                    lista.add(artigo);
                }
                rs.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(RecursosHumanosDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return lista;
    }

    public ResultSet relatorioConsumivel(Relatorio relatorio)
    {
        ResultSet rs;
         functionName="PACK_REPORT2.reportMovimentacaoConsumivel";
        if(relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals(""))
        {
             String colunasSearch [] = {"CONSUMIVEL", "QUANTIDADE", "FUNCIONARIO"};
                rs = Call.selectFiltredFrom("TABLE("+functionName+"(?,?))", "*", relatorio.getValorPesquisa(), colunasSearch,
                ((relatorio.getDataInicio()==null)? null :OperacaoData.toSQLDate(relatorio.getDataInicio())),
                ((relatorio.getDataFim()==null) ? null :OperacaoData.toSQLDate(relatorio.getDataFim())));
        }
        else
        {
            rs = Call.callTableFunction(functionName, "*", 
               ((relatorio.getDataInicio()==null)? null :OperacaoData.toSQLDate(relatorio.getDataInicio())),
               (relatorio.getDataFim()==null) ? null :OperacaoData.toSQLDate(relatorio.getDataFim()));
        } 
        return rs;
    }
    
    public ResultSet relatorioFuncionario(Relatorio relatorio)
    {
        ResultSet rs;
         functionName="PACK_REPORT2.reportFuncionario";
        if(relatorio.getValorPesquisa() != null && !relatorio.getValorPesquisa().equals(""))
        {
             String colunasSearch [] = {"NOME","FUNCAO","DEPARTAMENTO", "CONTACTO","CONTA","BANCO"};
                rs = Call.selectFiltredFrom("TABLE("+functionName+"())", "*", relatorio.getValorPesquisa(), colunasSearch);
        }
        else
        {
            rs = Call.callTableFunction(functionName, "*");
        } 
        return rs;
    }
}
