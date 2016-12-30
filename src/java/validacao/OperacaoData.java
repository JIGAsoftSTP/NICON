
package validacao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ildo
 */
public class OperacaoData
{
    
    /**
     * Converter uma data de java.util.Date em uma data de java.sql.Date
     * @param utilDate A entrada de data java
     * @return  Saida de data SQL
     */
    public static java.sql.Date toSQLDate (java.util.Date utilDate)
    {
        return new java.sql.Date(utilDate.getTime());
    }
    
    
    /**
     * Converter a java.util.Date em DD-MM-YYYY
     * @param dataCast objecto java.util.Date
     * @return 
     */
    public static String toStringDDMMYYY(Date dataCast)
    {
        return new SimpleDateFormat("dd-MM-yyyy").format(dataCast);
    }
    
    /**
     * Converter a java.util.Date em YYYY-MM-DD
     * @param dataCast objecto java.util.Date
     * @return 
     */
    public static String toStringYYYYMMDD (Date dataCast)
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(dataCast);
    }
    
    
    
    /**
     * Essa funcao compara a data actual com a data fornecida
     * @param otherDate A data que deve ser usada para efectura a compracao
     * @return 
     * 0 - Siginifica que a data fornecida e igula a data actual
     * 1 - Siginifica que a data fornecida e mairo que a data atual
     * -1 Siginifica que a data fornecida e menor que a data atual
     */
    public static int compareToSysDate (Date otherDate)
    {
        Date sysDate = new Date();
        return OperacaoData.compareDates(sysDate, otherDate);
    }
    
    
    /**
     * Essa funcao serva para compara duas datas e dezer se em relacao a dada base
     * A outra data e maior menor ou igual
     * @param baseDate A data que sera tomada como a data base
     * @param otherDate A data que sera comparada em relacao a ela
     * @return 
     *      0 - Singnifica que as duas datas sao iguais
     *      <br/> 1 - Siginifica que em relacao a data base a outra data e maior
     *      <br/> -1 - Significa que em ralacao a data base a outra data e menor
     */
    public static int compareDates (Date baseDate, Date otherDate)
    {
        return otherDate.compareTo(baseDate);
    }
    
    /**
     * Validar a data de nascimento
     * @param dataNascimento
     * @return 
     * true data de nascimento valido
     * flase data de nascimento invaldo
     */
    public static boolean isValidDateNascimento (Date dataNascimento)
    {
        return (compareToSysDate(dataNascimento) <= 0);
    }
    
    public static int DataNascimentoSuperior(Date dataNasc)
    {
        Date d = new Date();
        String dataNascimento = OperacaoData.toStringDDMMYYY(dataNasc);
        String dataNasc2 = dataNascimento.substring(6, 10);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ano = dateFormat.format(d);
        String anoAtual = ano.substring(6, 10);
        int idade = Integer.valueOf(anoAtual)-Integer.valueOf(dataNasc2);
        return idade;
       
    }
    
    /**
     * Essa funcoa converte uma String date em uma data do java.util.Date
     * @param dateYYYYMMDD
     * @return 
     */
    public static java.util.Date stringToJavaDate (String dateYYYYMMDD)
    {
        return stringFormatToDate(dateYYYYMMDD, "yyyy-MM-dd");
    }
    
    public static java.util.Date  stringFormatToDate(String dataValues, String formatDate)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
            Date date = formatter.parse(dataValues);
            return  date;
        } catch (ParseException ex) {
            System.err.println("Falha na conversao de string para dagetDataEmissaoFormatadade "+ex.getMessage());
            ex.printStackTrace();
            return null;
        }     
    }
    
    public static String formatoAnoMesDia(String data)
    {
        String [] campos = data.split("-");
        String valor = campos[2]+"-"+campos[1]+"-"+campos[0];
        return valor;
    }
}
