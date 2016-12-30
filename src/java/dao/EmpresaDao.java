package dao;

import bean.EmpresaBean;
import conexao.Conexao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import validacao.OperacaoData;

/**
 *
 * @author AhmedJorge
 */

//conexão
public class EmpresaDao implements Serializable{
    private String sql;
    private CallableStatement cs;
    private PreparedStatement ps;
    private ResultSet rs;

    public EmpresaDao(){
    }
    
    public void registarEmpresa(EmpresaBean e,String idUser){
        Conexao conexao = new Conexao();
        sql="{call PRC_REG_SEGURADORA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        System.out.println("");
        try 
        {
            if(conexao.getCon()!=null)
            {
                cs = conexao.getCon().prepareCall(sql);
                cs.setObject(1,idUser);
                cs.setString(2, e.getNomeEmpresa());
                cs.setString(3, e.getNomeGerente()); 
                cs.setString(4, e.getNifEmpresa());
                cs.setString(5, e.getRazaoSocial()); 
                cs.setString(6, e.getLocalizacao()); 
                cs.setString(7, e.getTelefoneEmpresa()); 
                cs.setString(8, e.getEmailEmpresa()); 
                cs.setString(9, e.getCapitalSocial()); 
                cs.setString(10, e.getCodigoPostal());
                cs.setDate(11, OperacaoData.toSQLDate(e.getDataInaguracao()));
                cs.setString(12, ((e.getFacebookEmpresa()==null || e.getFacebookEmpresa().equals("")) ? null : e.getFacebookEmpresa()));
                cs.setString(13, ((e.getTwitterEmpresa()==null || e.getTwitterEmpresa().equals(""))? null : e.getTwitterEmpresa()));
                cs.setString(14, e.getSobreEmpresa());
                cs.setBlob(15, testeInputStream(e.getLogotipoEmpresa(),"lo")); 
                cs.setBlob(16, testeInputStream(e.getRodapeEmpresa(),"ro"));
                cs.setBlob(17, testeInputStream(e.getCabecalhoEmpresa(),"ca"));
                cs.executeUpdate();
            }
//            System.out.println("Entou!!! Certo!!!");
        }
        catch (SQLException ex) 
        {
//            System.out.println("Entou!!! registro Empresa Erro!!   "+ex);
            Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[] listaEmpresa()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
        String arquivo = scontext.getRealPath("/Imagem/");
        Conexao conexao = new Conexao();
        sql="SELECT * FROM VER_SEGURADORA_ACTIVA";
        String[] lista=null;
        try 
        {
            if(conexao.getCon()!=null)
            {
                ps= conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
//                System.err.println(rs);
                while(rs.next())
                {
                    Blob lo=rs.getBlob("LOGOTIPO");
                    Blob ro=rs.getBlob("RODOPE");
                    Blob ca=rs.getBlob("CABECALHO");

                    if(lo!=null)
                    {
                        try (FileOutputStream foslo = new FileOutputStream(arquivo+"/logo.png")) 
                        {
                            foslo.write(lo.getBytes(1, (int) lo.length()));
                        }
    
                        try (FileOutputStream foslo = new FileOutputStream("logo.png")) 
                        {
                            foslo.write(lo.getBytes(1, (int) lo.length()));
                        }
                    }
                    if(ro!=null)
                    { 
//                        System.out.println(arquivo);
                        try (FileOutputStream fosro = new FileOutputStream(arquivo+"/rodape.png")) 
                        {
                            fosro.write(ro.getBytes(1, (int) ro.length()));
                        }
                        try (FileOutputStream fosro = new FileOutputStream("rodape.png")) 
                        {
                            fosro.write(ro.getBytes(1, (int) ro.length()));
                        }
                    }
                   if(ca!=null)
                    {
                        try (FileOutputStream fosca = new FileOutputStream(arquivo+"/cabecalho.png")) 
                        {
                            fosca.write(ca.getBytes(1, (int) ca.length()));
                        }
                        
                        try (FileOutputStream fosca = new FileOutputStream("cabecalho.png")) 
                        {
                            fosca.write(ca.getBytes(1, (int) ca.length()));
                        }
                    }

                    lista=
                        (rs.getString("NOME")+";-"+
                        rs.getString("GERENTE")+";-"+
                        rs.getString("NIF")+";-"+
                        rs.getString("RASASOCIAL")+";-"+
                        rs.getString("RESIDENCIA")+";-"+
                        rs.getString("TELEFONE")+";-"+
                        rs.getString("EMAIL")+";-"+
                        rs.getString("CAPITALSOCIAL")+";-"+
                        rs.getString("CP")+";-"+
                        rs.getString("DTINAGURACAO")+";-"+
                        ((rs.getString("FACEBOOK") == null ) ? "" : rs.getString("FACEBOOK") )+";-"+
                        ((rs.getString("TWITTER") == null ) ? "" : rs.getString("TWITTER") )+";-"+
                        rs.getString("SOBRE")+";-"+
                        "/Imagem/logo.png"+";-"+
                        "/Imagem/rodape.png"+";-"+
                        "/Imagem/cabecalho.png").split(";-");
                }
                rs.close();
                conexao.destruir();
            }
        }
        catch (SQLException ex)
        {
            conexao.destruir();
            Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return lista;
    }
    @SuppressWarnings("null")
   public InputStream testeInputStream(Part imagem,String tipo)
   {
       FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
        String arquivo = scontext.getRealPath("/Imagem/");
        switch (tipo) {
            case "ro":
                if(imagem==null) 
                {
                    try
                    {
//                        System.err.println("ddddd");
                        FileInputStream fis = new FileInputStream(new File("rodape.png"));
                        return fis;
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else 
                {
                    try
                    {
//                        System.out.println(imagem.getName());
                        return imagem.getInputStream();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            case "ca":
                if(imagem==null) 
                {
                    try
                    {
//                        System.err.println("ddddd");
                        FileInputStream fis = new FileInputStream(new File("cabecalho.png"));
                        return fis;
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else 
                {
                    try
                    {
//                        System.out.println(imagem.getName());
                        return imagem.getInputStream();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            default:
                if(imagem==null) 
                {
                    try
                    {
                        FileInputStream fis = new FileInputStream(new File("logo.png"));
//                        System.err.println("ddddd");
                        return fis;
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else 
                {
                    try
                    {
//                        System.out.println(imagem.getName());
                        return imagem.getInputStream(); 
                    }
                    catch (IOException ex) 
                {
                    Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
        
   }
    public static ResultSet getDadoEmpresa()
    {
        Conexao conexao = new Conexao();
        if(conexao.getCon() == null)
            return null;
        ResultSet rs=null;
        String sql="SELECT * FROM VER_SEGURADORA_ACTIVA";
        try 
        {
            if(conexao.getCon()!=null)
            {
                PreparedStatement ps= conexao.getCon().prepareStatement(sql);
                ps.execute();
                rs = ps.getResultSet();
                rs.next();
            }
        }
        catch (SQLException ex)
        {
            return null;
        }
       return rs;
    }
    public static void main(String[] args) {
//        System.err.println("100.000.55,55".);
    }
}
