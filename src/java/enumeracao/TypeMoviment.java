/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeracao;

 public enum TypeMoviment
{
    DEBITO(1),
    CREDITO(2);

    private int idTypeMoviment = 1;
    private TypeMoviment(int idType)
    {
        this.idTypeMoviment = idType;
    }

    public int getId()
    {
        return this.idTypeMoviment;
    }
}