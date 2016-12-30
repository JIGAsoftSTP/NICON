/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author Servidor
 */
public class OperacaoString
{
    /**
     * Funcao para comparar duas String e retornar entre elas quel e a maior ou menor
     * As comparacao serao feitas em relacao a base
     * Quando 1 Siguinifica que a outra string é mior
     * Quando -1 Siguinifica que a outra string é menor
     * Quando 0 Siguinifica que sao iguais
     * 
     * @param base A string base a ser comparada
     * @param other A outra string que sera comparada
     * @return {-1 Menor | 0 igual | 1 Maior}
     */
    public static byte compareString(String base, String other)
    {
        int compare = base.compareTo(other);
        return (byte) ((compare == 0)? 0
                :(compare<0)? 1
                : -1);
    }
    
    
    /**
     * Ordernar uma lista
     * @param collecao 
     * @param orderMode 
     */
    public static void orderCollection (List collecao, OrederBy orderMode)
    {
        Float f;
        Integer i;
        if (orderMode == OrederBy.ASC)
            Collections.sort(collecao);
        else
        {
            Comparator c = (Comparator) (Object o1, Object o2) -> 
            {
                try
                {
                    Comparable co1 = (Comparable) o1;
                    if (o1.getClass().equals(o2.getClass()))
                    {
                        return  co1.compareTo(o2) * -1;
                    }
                }catch(Exception ex) {}
                return 0;
            };
            Collections.sort(collecao, c); 
        }
    }
    
    /**
     * O modo da ordenacao
     */
    public static enum OrederBy
    {
        ASC,
        DESC;
    }
    
}
