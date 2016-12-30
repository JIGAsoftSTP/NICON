/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import modelo.Funcionario;
import bean.RouboBean;
import conexao.Call;
import conexao.Conexao;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.AcidentePG;
import modelo.Roubo;
import sessao.SessionUtil;

/**
 *
 * @author AhmedJorge
 */
public class RouboDao
{
    private CallableStatement cs;
    private PreparedStatement ps;
    private String resultado;
    
    
    public String regRoubo(String idContrato,int quantidade, String modelo, float valor,String descricao )
    {
       Conexao conexao = new Conexao();
       String idUser = ((Funcionario) SessionUtil.obterValor("utilizador")).getId().toString();
       int user = Integer.valueOf(idUser);
       if(conexao.getCon() != null)
       {
           resultado = (String) Call.callSampleFunction("FUNC_REGOBJ_ROUBO",Types.VARCHAR,user,idContrato,quantidade,modelo,valor,descricao );
       }
       return resultado;
    }
    
    public String registrarInfoRoubo(String idContrato,String tipoEdificio, String enderecoEdificio, String tempoOcupacao,String dataOcupacao,
            float valorCofre, String marca, Date dataAquisicao)
    {
        Conexao conexao = new Conexao();
        String idUser =   ((Funcionario) SessionUtil.obterValor("utilizador")).getId().toString();
        if(conexao.getCon() != null)
        {
            resultado =(String) Call.callSampleFunction("FUNC_CTTINF_ROUBO", Types.VARCHAR, Integer.valueOf(idUser),Integer.valueOf(idContrato),tipoEdificio,enderecoEdificio,tempoOcupacao,dataOcupacao,valorCofre,marca,dataAquisicao);
        }
        return resultado;
    }
    
}
