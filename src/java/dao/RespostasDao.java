/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.ListaRespostas;
import conexao.Conexao;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AhmedJorge
 */
public class RespostasDao {
    private CallableStatement cs;
    private String sql;
    private String resultado;
    
    public void registarResposta(String idUser,String idContrato,String idPergunta,String respoata,String expecisicacao)
    {
        sql="{?=call FUNC_REG_RESPOSTA( ? , ? , ? , ? , ? )}";
        Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setString(2, idUser);
                cs.setString(3, idContrato);
                cs.setString(4, idPergunta);
                cs.setString(5, respoata);
                cs.setString(6, expecisicacao);
                cs.execute();
                resultado = cs.getString(1);
                System.out.println(RespostasDao.class.getName()+" --- "+respoata);
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(RespostasDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getTypePergunta (ListaRespostas.Resposta resp)
    {
        String sql = "SELECT * FROM TABLE(FUNCT_GET_TYPEPERGUNTA(?))";
        Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.setInt(1, resp.getIdPergunta());
                cs.execute();
                ResultSet rs = cs.getResultSet();
                if (rs.next())
                {
                    resp.setDescricao(rs.getString("DESCRICAO"));
                    resp.setTypePergunta(rs.getInt("ID"));
                    resp.setDescTypePergunta(rs.getString("PERGUNTA"));
                }
                conexao.destruir();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(RespostasDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
}
