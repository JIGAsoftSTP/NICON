/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Essa interfacess processa tres argumento em uma função
 * -> Segi  BiConsumer mas esta orienta a trabalhar com tez argumentos
 * @author Servidor
 * @param <Argment1>
 * @param <Argment2>
 * @param <Argment3> 
 */
public interface ProcessThree <Argment1, Argment2, Argment3> 
{
    public void onProcess(Argment1 arg1, Argment2 arg2, Argment3 arg3);
}
