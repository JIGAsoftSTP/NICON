
package lib;

import java.util.Calendar;

/**
 * Classe para trabalhar nas horas do systema
 * 
 * 
 * @author Daniel Costa || DCMOP
 */
public class Clock
{
    private static int fInt(String arg) { return Integer.parseInt(arg);}
    private static String format (String args) 
    { 
        return String.valueOf(String.format("%t"+args,Calendar.getInstance()));
    }

    
    /**
     * Obter a data do systema em formacto YYYY-MM-DD
     * @return 
     */
    public static final String getSysDate () {return format("F");}
    
    
    public static final String getSysTimeStamp () 
    {
        return Clock.getSysDate() + " "+Clock.getSysDate();
    }
    
   
    
    /**
     * Obter a actual HH:MM:SS em formato 24 horas 
     * @return 
     */
    public static final String get24HHMMSS () {return format("T");}
    
    
    /**
     * Obter a HH:MM:SS actual em formato de 12 horas
     * @return 
     */
    public static String get12HHMMSS () {return  format("r");}
    
    
    /**
     * O obter a hora atual como inteiro
     * @return 
     */
    public static final int getHourI () {return  fInt(getHora());}
    
    
    /**
     * Obter o minuto actual como inteiro
     * @return 
     */
    public static final int getMinutoI () {return  fInt(getMinuto());}
    
    /**
     * Obter o sgundo actual como inteiro
     * @return 
     */
    public static final int getSecondI () {return fInt(getSegundo());}
    
    
    /**
     * Obter  o ano actual como inteiro
     * @return 
     */
    public static final int getYearI() {return fInt(getAno());}
    
    /**
     * Obter a dia actul como inteiro
     * @return 
     */
    public static final int getDayI() {return fInt(getDia());}
    
    
    /**
     * Obter o mes actual como inteiro
     * @return 
     */
    public static final int getMonthI() {return fInt(getMes());}
    
    
    
    
    /**
     * Obter a hoara actual 
     * @return 
     */
    public static final String getHora () {return format("H");}
    
    /**
     * Obter o Minuto actual
     * @return 
     */
    public static final String getMinuto () {return format("M");}
    
    /**
     * Obter o segundo actual
     * @return 
     */
    public static final String getSegundo () {return format("S");}
    
    
    /**
     * Obter o ano actual
     * @return 
     */
    public static final String getAno () {return format("Y");}
    
    /**
     * Obter a dia actual
     * @return 
     */
    public static final String getDia () {return format("d");}
    
    /**
     * Obter o mes actula
     * @return 
     */
    public static final String getMes () {return format("m");}
    
    
    
    /**
     * Apresetar outras conversoes da data
     */
    public static void showOtherCovertion ()
    {
        for (int i = (int)'a'; i<(int) 'z'; i++ )
        {
            try
            {
                System.out.println((char)i+"    "+format(((char)i)+""));
            }catch (Exception e) {}
        }
        
        for (int i = (int)'A'; i<(int) 'Z'; i++ )
        {
            try
            {
                System.out.println((char)i+"    "+format(((char)i)+""));
            }catch (Exception e) {}
        }
    }
}
