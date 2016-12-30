/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import modelo.Funcionario;
import conexao.Call;
import dao.RespostasDao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneRadio;
import sessao.SessionUtil;

/**
 *
 * @author AhmedJorge
 */
@ManagedBean
@ViewScoped
public class ListaRespostas implements Serializable{
    private  HashMap<Integer, Resposta> listaRespostas = new HashMap<>();
    
    

    public HashMap<Integer, Resposta> getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(HashMap<Integer, Resposta> listaRespostas) {
        this.listaRespostas = listaRespostas;
    }
    
    /**
     * Essa funcao serve para reguistrar as resposta para as pergutanas na base de dado
     * @param idContrato Identificacao do contrato que deve receber as respostas
     */
    public void guardarRespostas (int idContrato)
    {
        System.out.println("ID DO CONTRATO "+idContrato);
        Funcionario f = (Funcionario) SessionUtil.getSession().getAttribute(Funcionario.SESSION_NAME);
        String idUser = f.getId().toString();
        
        listaRespostas.values().stream().forEach((r) -> {
            System.out.println(idContrato+"\n"+r.getIdPergunta()+"\n"+r.getResposta()+"\n"+r.getObj());
            Object resp = Call.callSampleFunction("FUNC_REG_RESPOSTA", Call.VARCHAR,
                    idUser,
                    idContrato,
                    r.getIdPergunta(),
                    ((r.getResposta().equals("true")) ? 1 : (r.getResposta().equals("false") ? 0 : r.getResposta())),
                    r.getObj());
        });
       
    }
    
    public void validarResposta(ListaRespostas lr){ 
        for (Map.Entry<Integer, ListaRespostas.Resposta> entrySet : lr.listaRespostas.entrySet()) {
//            Integer key = entrySet.getKey();
            String value = entrySet.getValue().getResposta();
//            System.out.println("value "+value.getResposta()+"   ------------   "+key+" key");  
            if(value==null){
                //entrySet.getValue().getObjH().setStyle(entrySet.getValue().getObjH().getStyle()+" border-color:red");
                System.err.println(entrySet.getValue().getObjH().getStyle());
            }else{
                //entrySet.getValue().getObjH().setStyle(entrySet.getValue().getObjH().getStyle()+" border-color:black");
                System.err.println(entrySet.getValue().getObjH().getStyle());
            }
        }
    }
    

    public Resposta getResposta(int numero){
        if(listaRespostas.containsKey(numero))
        {
            return listaRespostas.get(numero);
        }
        else
        {
            listaRespostas.put(numero, new Resposta(numero)); 
           return  listaRespostas.get(numero); 
        }
       
    }
    @ManagedBean
    @ViewScoped
    public static class Resposta implements Serializable
    {
        private final int idPergunta;
        private String resposta="N";
        private String respostaY="Y";
        private String obj;
        private HtmlSelectOneRadio objH;
        private boolean validar=true;
        
        private boolean testevalidar=false;//testevalidar para ser usado em formulario com chekbox quando a variavel validar for verdadeiro
                                            //o testevalidar faz o sentido inverso isto começa por falso...
        
        private String descTypePergunta; 
        private int typePergunta;
        
        private String descricao;
        
        public Resposta() {
            this.idPergunta = 0;
        }
        
        
        public int getIdPergunta() {
            return idPergunta;
        }

        public String getResposta() {
            if(idPergunta==16)
             resposta=respostaY;       
            return resposta;
        }
        
        public void setResposta(String resposta) {
            this.resposta = resposta;
        }

        public String getDescTypePergunta() {
            return descTypePergunta;
        }

        public void setDescTypePergunta(String typePergunta) {
            this.descTypePergunta = typePergunta;
        }

        public int getTypePergunta() {
            return typePergunta;
        }

        public void setTypePergunta(int typePergunta) {
            this.typePergunta = typePergunta;
        }

        public String getRespostaY() {
            return respostaY;
        }

        public void setRespostaY(String respostaY) {
            this.respostaY = respostaY;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
        
        

        public String getObj() {
            return obj;
        }

        public void setObj(String obj) {
            this.obj = obj;
        }

        public Resposta(int idPergunta) {
            this.idPergunta = idPergunta;
            RespostasDao resp = new RespostasDao();
            resp.getTypePergunta(this);
        }
        
        @Override
        public String toString(){
            return "ID PERGUNTA="+idPergunta+"; "
                    +"RESPOSTA="+resposta+"; "
                    +"OBSERVACAO="+obj+"; "
                    +"DESCRINCAO="+this.descricao+"; "
                    +"TIPO PERGUNTA="+this.typePergunta+"; "
                    +"DESCRINCAO TIPO="+this.descTypePergunta+"; ";
            
        }
        
        public void validarSelecaoCaso1()
        {
            this.validar= !getResposta().equals("Y");
        }
            ////////colocado 3
        public void validarSelecaoCaso3()
        {
            this.testevalidar= !getResposta().equals("Y");
        }
        ///////////
        
        public void validarSelecaoCaso2()
        {
            this.validar= getResposta().equals("Y");
        }
        
        ////////////colocado4
                    public void validarSelecaoCaso4()
                    {
                        this.testevalidar= !getResposta().equals("Y");
                    }
        ///////////////////////////////
                
        public void validarSelecaoCasoRodio()
        {
            setResposta (getResposta());
        }
        
                    public void validarSelecaoCasoRodio1 () 
                    {
                          setResposta (getResposta());  
                    }
        
        public boolean isValidar() {
            return validar;
        }
                        public boolean isTestevalidar() 
                            {
                                return testevalidar;
                            }

        public void setValidar(boolean validar) {
            this.validar = validar;
        }
                        public void setTestevalidar(boolean testevalidar) 
                                    {
                                        this.testevalidar = testevalidar;
                                    }

        public HtmlSelectOneRadio getObjH() {
            return objH;
        }

        public void setObjH(HtmlSelectOneRadio objH) {
            this.objH = objH;
        }
        
        public boolean isValidDescrition ()
        {
            return (this.descricao.equals("Y") && this.obj != null && this.obj.length()>0)
                        || (this.descricao.equals("N") && (this.obj == null || this.obj.length() == 0));
        }
    }
    
    
     public static void main(String [] args)
    {
        for (int i = 11; i<=26; i++)
        {
            Resposta r = new Resposta(i);
            r.setObj("hfjhjh");
            System.out.println(r.toString()+"   VALIDACAO="+r.isValidDescrition());
        }
    }
}
