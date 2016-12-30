/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import conexao.Call;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author AhmedJorge
 */
public class ComoBox implements Serializable
{
    private String id;
    private String value;
    private String estado;
    private String codigoNicon;
    private String destalhes;
    private double saldo;
    private HashMap<String, Object> mapValues = new HashMap<>();;
    
    public ComoBox(String armnet)
    {
        String [] campos = armnet.split(";");
        this.id = campos[0];
        this.value = campos[1];
    }

    public ComoBox() {
        
    }
    
    public ComoBox(String id, String value) {
        this.id = id;
        this.value = value;
    }
    public ComoBox(String id, String value, double saldo) {
        this.id = id;
        this.value = value;
        this.saldo = saldo;
    }
    public ComoBox(String id, String value, String codigo) {
        this.id = id;
        this.value = value;
        this.codigoNicon = codigo;
    }

    public ComoBox(String id, String value, String codigoNicon, String destalhes) {
        this.id = id;
        this.value = value;
        this.codigoNicon = codigoNicon;
        this.destalhes = destalhes;
    }
    
    public String getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getCodigoNicon() {
        return codigoNicon;
    }

    public void setCodigoNicon(String codigoNicon) {
        this.codigoNicon = codigoNicon;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDestalhes() {
        return destalhes;
    }

    public void setDestalhes(String destalhes) {
        this.destalhes = destalhes;
    }
    
//    @Override
//    public boolean equals(Object obj)
//    {
//        if(obj != null && obj instanceof ComoBox)
//        {
//            ComoBox cb = (ComoBox) obj;
//            return cb.id.equals(this.id);
//        }
//        return false;
//    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.value);
        return hash;
    }
    
    
    public void putValue(String name, Object value)
    {
        if(this.mapValues.containsKey(name))
            this.mapValues.replace(name, value);
        else this.mapValues.put(name, value);
    }
    
    public Object getValue(String name)
    {
        return mapValues.get(name);
    }

    public double getSaldo() {
        return saldo;
    }
    
    
    /**
     * 
     * @param dbViewName
     * @param viewId
     * @param viewDesc
     * @return 
     */
    /**
     * Funcao para criar uma lista de ComoBox
     * Os dados para a lista vem de uma qualquer view de base de dados
     * @param dbViewName O nome da view que sera usada para buscar a como
     * @param viewId as identificacoes para combo
     * @param viewDesc as descrinco na view que serao utilizadas para a comoBox
     * @return 
     */
    public static ArrayList<ComoBox> loadCombo (String dbViewName, String viewId, String viewDesc)
    {
        //FUNCAL_COLLETAR_COMBOS -- Essa funcao esta na base de dados para
            //coletar os registro de uma tabela ou veiew num arary e retirnara esses array
        
        ArrayList<String> list = (ArrayList<String>) Call.callSampleFunction("FUNCAL_COLLETAR_COMBOS", Call.ARRAY, dbViewName, viewId, viewDesc);
        ArrayList<ComoBox> listaCombo = new ArrayList<>();
        if(list==null)
            return new ArrayList<>();
        list.stream().forEach((valuesCombo) -> {
            listaCombo.add(new ComoBox(valuesCombo));
        });
        return  listaCombo;
    }
 
    public boolean contains(String name)
    {
        return this.mapValues.containsKey(name);
    }
    
    public static ArrayList<ComoBox> loadAllDados(String dbViewName, String viewId, String viewDesc)
    {
       ArrayList<ComoBox> list = new ArrayList<>();
        ResultSet rs = Call.selectFrom(dbViewName, "*");
       Consumer <HashMap<String, Object>> act  = (HashMap<String, Object> map) -> 
       {list.add( new ComoBox(map.get(viewId)+"", map.get(viewDesc)+""));};
       Call.forEchaResultSet(act, rs);
      return list;
    }
}
