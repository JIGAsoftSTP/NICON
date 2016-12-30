
package validacao;

import conexao.Conexao;
import dao.ClienteDao;
import dao.VeiculoDao;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import modelo.ComoBox;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Helio
 */
public class Validacao
{
    private int i = 2;
    /**
     * 
     * @param idFormulario identificação do formulário que está o componente
     * @param idComponente identificação do componente a ser atualizado
     */
    
    public static void AtualizarCompoente(String idFormulario, String idComponente)
    {
        RequestContext.getCurrentInstance().update(idFormulario+":"+idComponente);
    }
     
    public static double desformatarValor(String valor)
    {
         NumberFormat nf = new DecimalFormat (",###.##", new DecimalFormatSymbols (new Locale ("pt", "BR")));
        double valorFormtado = 0;
        try {
            valorFormtado = nf.parse (valor).doubleValue();
        } catch (ParseException ex) {
            System.out.println ("Valor com formatação inválida!");
        }
        return valorFormtado;
    }
    public static int anoAtual()
    {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         String ano = dateFormat.format(d);
        return Integer.valueOf(ano.substring(6, 10));
    }
    public static void atualizar(String idForm, Object... params)
    {   
        for (Object param : params)
        {
            RequestContext.getCurrentInstance().update(idForm+":" + param);
        }
    }
    public static void callBackParam(String nomeObjeto, String valor)
    {
        RequestContext.getCurrentInstance().addCallbackParam(nomeObjeto, valor);
    }
    
    public static void redirecionar(String url)
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Validacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Desformata o valor digitado no formulário
     * @param value
     * @return 
     */
    public static String unformat(String value)
    {
       value = value.replace(" ", "");
       value = value.replace(",", ".");
       return value;
    }
    public static String paginaAtual()
    {
        String pagina = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return pagina;
    }
    
    public static String Sigla(String idSigla)
    {
        PreparedStatement ps = null; 
        ResultSet rs = null;
        String resultado = null;
        Conexao conexao = new Conexao();
        if(conexao.getCon()==null)
            return "";
        String  sql = "SELECT * FROM VER_MOEDA where ID = ?";
        try {
            ps = conexao.getCon().prepareStatement(sql);
            ps.setString(1, idSigla);
            ps.execute();
            rs = ps.getResultSet();
            if(rs != null)
            {
                while(rs.next())
                {
                   resultado = rs.getString("SIGLA");
                }
                rs.close();
                conexao.destruir();
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    /**
     * 
     * @param campoRetorno O valor do campo a ser retornado
     * @param campoSql campo sql a comparar
     * @param view view que se efetuará a pesquisa
     * @return valor do campo campoRetorno
     */
    
    public String DevolverValor(String campoRetorno,String campoSql, String view, String valor)
    {
        String sql = null, resultado = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        sql = "SELECT "+campoRetorno+" FROM "+view+" WHERE "+campoSql+"=?";
           Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.setString(1, valor);
                ps.execute();
                rs = ps.getResultSet();
                if( rs!= null)
                {
                    while(rs.next())
                    {
                        resultado = rs.getString(campoRetorno);
                    }
                    rs.close();
                    conexao.destruir();
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public static String DevolverValorCampo(String campoRetorno,String campoSql, String view, String valor)
    {
        String sql;
        String resultado = null;
        PreparedStatement ps;
        ResultSet rs;
        sql = "SELECT "+campoRetorno+" FROM "+view+" WHERE "+campoSql+"=?";
           Conexao conexao = new Conexao();
        try
        {
            if(conexao.getCon()!=null)
            {
                ps = conexao.getCon().prepareStatement(sql);
                ps.setString(1, valor);
                ps.execute();
                rs = ps.getResultSet();
                if( rs!= null)
                {
                    while(rs.next())
                    {
                        resultado = rs.getString(campoRetorno);
                    }
                    rs.close();
                    conexao.destruir();
                }
            }
            System.out.println("resultado "+resultado);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public static String comboNome(List<ComoBox> list, String id)
    {
        for(ComoBox cb : list)
        {
            if(cb.getId().equals(id))
                return cb.getValue();
        }
        return null;
    }
   
    public static String comboId(List<ComoBox> list, String desc)
    {
        for(ComoBox cb : list)
        {
            if(cb.getValue().equals(desc))
                return cb.getId();
        }
        return null;
    }
   
    public static boolean isNumeric(String number) 
    {
        boolean valido = true;
        try {
            Double.valueOf(number);
        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }
    public static boolean isInteger(String value) 
    {
        boolean valido = true;
        try {
            Integer.valueOf(value);
        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }
    public static String valorSemVirgulaPonto(String valor)
    {
        String[] values = valor.split("[,.]");
        String result = null;
        for(String s : values)
        {
            result += s;
        }
        return result.substring(4, result.length()-2);
    }
    
    public static ArrayList<String> likeStart(List<String> list, String like)
    {
        if(like == null || list == null) return null;
        ArrayList<String> likeList = new ArrayList<>();
        if(like.length() == 0)
        {
            likeList.addAll(list);
            return likeList;
        }
        String aux;
        for(String s: list)
        {
            aux = s;
            if(s.length() >= like.length())
            {
                s=s.substring(0, like.length());
                if(s.toUpperCase().contains(like.toUpperCase()))
                {
                    likeList.add(aux);
                }
            }
            
        }
        
        return likeList;
    }
    
    public static double unformatValue(String valor)
    {
  
        char [] value = valor.toCharArray();
        valor ="";
        
        if(value[value.length-3] !=',')
            value[value.length-3] =',';

        for(int i = 0;i<value.length;i++)
        {
            if(value[i] ==' ')
            {
                value[i]='.'; 
            }
            valor += value[i];
        }
        return desformatarValor(valor);
    }
    
    public static boolean isLeapYear(int year)
    {
        return new GregorianCalendar().isLeapYear(year) != false;
    }
    
    public static String somarDiasData(String data)
    {
 
       String dia =data.substring(0, 2);
       int ano = Integer.valueOf(data.substring(6, 10));
        System.out.println("data "+data);
       ano++;
       dia = Integer.valueOf(dia)-1+"";
       if(Integer.valueOf(dia)<10)
       {
           dia = "0"+dia;
           return dia+"-"+data.substring(3, 5)+"-"+ano;
       }
       else
           return  dia+"-"+data.substring(3, 5)+"-"+ano;
    }
    
    

}
