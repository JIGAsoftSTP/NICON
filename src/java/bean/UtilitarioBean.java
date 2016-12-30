/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.UtilitarioDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.PrecoCobertura;
import modelo.Taxa;
import oracle.net.aso.o;
import org.primefaces.context.RequestContext;
import validacao.Validacao;

/**
 *
 * @author AhmedJorge
 */
@ManagedBean
@ViewScoped
public class UtilitarioBean implements Serializable
{
    private static final long SERIAL_VERSION =1L;
    HashMap<String, ArrayList<ComoBox>> mapDadosUtilitarios = new HashMap<>();
    ArrayList<ComoBox> listaMenu = new ArrayList<>();
    private ArrayList<ComoBox> listaLimiteResp = new ArrayList<>();
    private ArrayList<PrecoCobertura> listaPrecoCobertura = new ArrayList<>();
    private PrecoCobertura precoCoberturaSelected = new PrecoCobertura();
    private PrecoCobertura precoCobertura = new PrecoCobertura();
    ArrayList<ComoBox> lista = new ArrayList<>();
    private ArrayList[] listaPais = new ArrayList[2];
    private ArrayList[] listaMoeda = new ArrayList[2];
    UtilitarioDao ud = new UtilitarioDao();
    ComoBox valores;
    private ComoBox valorPais;
    private ComoBox valorMoeda;
    private ComoBox limiteRespo;
    private Taxa taxa;
    private Taxa taxaSeleted;
    private ArrayList<Taxa> taxaList;
    private ComoBox banco;
    private ComoBox bancoSeleted;
    private ArrayList<ComoBox> bancoList;
    Integer type;
    private ArrayList<ComoBox> moedaLista;
    private String inicio;
    private String fim;
    private String tipo;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public UtilitarioBean() 
    {
        System.out.println("entrou no construtor");
        carregarPaises();
        carregarMoedas();
        carregarLimiteResp();
        listaPrecoCobertura = ud.listaPrecoCobertura();
        listaMenu = ud.listaUtilitario();
        listaMenu.stream().forEach((comoBox) -> {
            if(comoBox.getCodigoNicon()!=null)
                mapDadosUtilitarios.put(comoBox.getId(), ud.listaUtilitarioID(comoBox.getId(),-1+""));
            else
                mapDadosUtilitarios.put(comoBox.getId(), ud.listaUtilitarioID(comoBox.getId(),comoBox.getCodigoNicon()));
        });
        moedaLista = ud.moedaName();
        bancoList = ud.bancoList();
    }

    public HashMap<String, ArrayList<ComoBox>> getMapDadosUtilitarios() {
        return mapDadosUtilitarios;
    }

    public void setMapDadosUtilitarios(HashMap<String, ArrayList<ComoBox>> mapDadosUtilitarios) {
        this.mapDadosUtilitarios = mapDadosUtilitarios;
    }

    public ArrayList<ComoBox> getListaMenu() {
        return (listaMenu==null)? listaMenu= new ArrayList<>():listaMenu;
    }

    public void setListaMenu(ArrayList<ComoBox> listaMenu) {
        this.listaMenu = listaMenu;
    }

    public ComoBox getValores() {
        return (valores == null) ? valores = new ComoBox() : valores;
    }
    
    public void setValores(ComoBox valores) {
        this.valores = valores;
    }

    public PrecoCobertura getPrecoCobertura() {
        return ((precoCobertura==null)? precoCobertura= new PrecoCobertura():precoCobertura) ;
    }

    public void setPrecoCobertura(PrecoCobertura precoCobertura) {
        this.precoCobertura = precoCobertura;
    }

    public ArrayList<ComoBox> getListaValoresMep(String id) {
        return mapDadosUtilitarios.get(id);
    }

    public ComoBox getValorPais() {
        return ((valorPais==null) ? valorPais = new ComoBox(): valorPais);
    }

    public void setValorPais(ComoBox valorPais) {
        this.valorPais = valorPais;
    }

    public ComoBox getValorMoeda() {
        return ((valorMoeda==null) ? valorMoeda = new ComoBox(): valorMoeda);
    }

    public void setValorMoeda(ComoBox valorMoeda) {
        this.valorMoeda = valorMoeda;
    }
    
    public void updateArray(ComoBox box) {
        System.err.println("update "+box.getId() );
        mapDadosUtilitarios.replace(box.getId(), ud.listaUtilitarioID(box.getId(),(box.getCodigoNicon()==null) ? null : valores.getCodigoNicon())) ;
    }
    
    public ArrayList carregarLista(String id) 
    {   if(valores.getCodigoNicon()==null)  
            lista = ud.listaUtilitarioID(id,null);
        return lista;
    }

    public ArrayList<ComoBox> getListaLimiteResp() {
        return (listaLimiteResp==null) ? listaLimiteResp= new ArrayList<>():listaLimiteResp;
    }

    public void setListaLimiteResp(ArrayList<ComoBox> listaLimiteResp) {
        this.listaLimiteResp = listaLimiteResp;
    }

    public ComoBox getLimiteRespo() {
        return (limiteRespo == null) ? limiteRespo = new ComoBox() : limiteRespo;
    }

    public void setLimiteRespo(ComoBox limiteRespo) {
        this.limiteRespo = limiteRespo;
    }

    public ArrayList<PrecoCobertura> getListaPrecoCobertura() {
        return (listaPrecoCobertura==null) ? listaPrecoCobertura = new ArrayList<>(): listaPrecoCobertura;
    }

    public void setListaPrecoCobertura(ArrayList<PrecoCobertura> listaPrecoCobertura) {
        this.listaPrecoCobertura = listaPrecoCobertura;
    }

    public PrecoCobertura getPrecoCoberturaSelected() {
        return (precoCoberturaSelected==null)? new PrecoCobertura():precoCoberturaSelected;
    }

    public void setPrecoCoberturaSelected(PrecoCobertura precoCoberturaSelected) {
        this.precoCoberturaSelected = precoCoberturaSelected;
    }
      
    public void addObj(ComoBox box)
    {
        if(box.getCodigoNicon()!=null)
        {
            System.out.println(" SUPER PRIENCHIDO");
            if(valores.getCodigoNicon()!=null&&!valores.getCodigoNicon().equals("-1"))
            {
                reg(box);
            }
            else
            {
                Mensagem.addWarningMsg("Selecine um valor valido!");
                Validacao.AtualizarCompoente("forUtlGrowl", "utlGrowl");
            }
        }else{
            System.out.println(" SUPER NULL");
            reg(box);
        }
        
    }
    
    public ArrayList<ComoBox> getPaisesAtive (){
        return (ArrayList<ComoBox>)listaPais[0];
    }
    public ArrayList<ComoBox> getPaisesDevative (){
        return (ArrayList<ComoBox>)listaPais[1];
    }
    
    public ArrayList<ComoBox> getMoedasAtive (){
        return (ArrayList<ComoBox>)listaMoeda[0];
    }
    public ArrayList<ComoBox> getMoedasDevative (){
        return (ArrayList<ComoBox>)listaMoeda[1];
    }
    
    private void reg(ComoBox box) {
        ud.regOjecto(((box.getCodigoNicon() != null) ? valores.getCodigoNicon() : null), box.getId(), valores.getValue());
        updateArray(box);
        Mensagem.addInfoMsg("Ojecto (" + valores.getValue() + ") adcionado com sucesso!");
        Validacao.AtualizarCompoente("forUtlGrowl", "utlGrowl");
        valores.setValue("");
        Validacao.AtualizarCompoente("princ" + box.getId(), "valorUt" + box.getId());
    }
    
    public void desativeObj(ComoBox id ,ComoBox ouhterId) {
        ud.desaOjecto(id.getId());
//        Validacao.AtualizarCompoente("princ" + ouhterId.getId(), "como" + ouhterId);
        updateArray(ouhterId);
        Mensagem.addInfoMsg("Ojecto (" + id.getValue() + ") desativo com sucesso!");
        Validacao.AtualizarCompoente("forUtlGrowl", "utlGrowl");
    }
    
    public void carregarPaises()
    {
        listaPais = ud.listOther("VER_ADM_PAIS","ID", "NOME");
    }
    
    public void carregarMoedas()
    {
        listaMoeda = ud.listOther("VER_ADM_MOEDA","ID", "MOEDA");
    }
    
    public void actiarMoeda()
    {
        actiarDesativarMoeda(valorMoeda.getId());
    }
    
    public void actiarDesativarMoeda(String id)
    {
        Object o =ud.ativaDesativarMeoda(id);
        carregarMoedas();
        Mensagem.addInfoMsg("Moeda foi "+o+" com sucesso!");
        Validacao.AtualizarCompoente("forUtlGrowl", "utlGrowl");
    }
    
    public void actiarDesativarPais(String id)
    {
        Object o = ud.ativaDesativarPais(id);
        carregarPaises();
        Mensagem.addInfoMsg("Pais foi "+o+" com sucesso!");
        Validacao.AtualizarCompoente("forUtlGrowl", "utlGrowl");
    }
    
    public void actiarPais()
    {
        actiarDesativarPais(valorPais.getId());
    }
    
    public void carregarLimiteResp()
    {
        listaLimiteResp = ud.listaLimitesResp();
    }
    
    public void editeLimeteResp()
    {
        System.err.println(limiteRespo.getId()+" "+limiteRespo.getValue()+" "+limiteRespo.getCodigoNicon());
        if(limiteRespo.getValue()==null||!limiteRespo.getValue().isEmpty())
        {
            ud.editeResponsabilidade(limiteRespo);
            carregarLimiteResp();
            RequestContext.getCurrentInstance().execute("closeModalLimiteResp()");
            Mensagem.addInfoMsg("Alteração efecuada com sucesso!");
            Validacao.AtualizarCompoente("forUtlGrowl", "utlGrowl");
        }
        else
        {
            RequestContext.getCurrentInstance().execute("testeVasioModal('red')");
            Mensagem.addWarningMsg("Por priencha o campo de valor antes de validar a alteração!");
            Validacao.AtualizarCompoente("forUtlGrowl", "utlGrowl");
        }
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    private boolean checkFields()
    {
        if((precoCobertura.getIncio() != null && !precoCobertura.getIncio().equals("")) &&
          (precoCobertura.getFim()!=null && !precoCobertura.getFim().equals("")) &&
            (precoCobertura.getNc() !=null && !precoCobertura.getNc().equals("")) &&
            (precoCobertura.getPremio()!= null && !precoCobertura.getPremio().equals("")))
        {
            return true;
        }
        else return false;
    }
    public void editeAndAddPrecoCoberturaViagem()
    {
        precoCobertura.setIncio(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("data inicio"));
        precoCobertura.setFim(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("data fim"));
        /**
         * Nc foi substutuido por total
         */
        precoCobertura.setTotal(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nc"));
        precoCobertura.setPremio(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("prémio"));
        tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");
     
        if(tipo != null)
        {
            if(tipo.equals("multi viagem"))
            {
                precoCobertura.setIncio("-1");
                precoCobertura.setFim("-1");
                Object o =ud.regPrecoCobertura(precoCobertura);
                if(o.toString().equals("true"))
                {
                    Message.addInfoMsg("Cobertura registrado ou atualizado com sucesso.", "forUtlGrowl", "utlGrowl");
     
                    RequestContext.getCurrentInstance().execute("limparCoberturaViagem()");
                    this.listaPrecoCobertura.clear();
                    listaPrecoCobertura = ud.listaPrecoCobertura();
                    Validacao.AtualizarCompoente("forUtlCober", "tableCoberturaViagem");
                }
                else
                {
                    Message.addErrorMsg(o.toString(), "forUtlGrowl", "utlGrowl");
                    RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                }
            }
            else
            {
                 if(Integer.valueOf(precoCobertura.getIncio())< Integer.valueOf(precoCobertura.getFim()))
                {
                    Object o =ud.regPrecoCobertura(precoCobertura);
                    if(o.toString().equals("true"))
                    {
                         Message.addInfoMsg("Cobertura registrado ou atualizado com sucesso.", "forUtlGrowl", "utlGrowl");
                        RequestContext.getCurrentInstance().execute("limparCoberturaViagem()");
                        this.listaPrecoCobertura.clear();
                        listaPrecoCobertura = ud.listaPrecoCobertura();
                        Validacao.AtualizarCompoente("forUtlCober", "tableCoberturaViagem");
                    }
                    else
                    {
                        Message.addErrorMsg(o.toString(), "forUtlGrowl", "utlGrowl");
                        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                    }
                }
                else
                {
                    RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                    Message.addErrorMsg("Valor de Inicio não pode ser superior a Fim!", "forUtlGrowl", "utlGrowl");
                } 
            }
        }
    }
    
    public void editarCoberturaViagem(PrecoCobertura precoCobertura)
    {
        precoCobertura = new PrecoCobertura(precoCobertura);
        inicio = precoCobertura.getIncio();
        fim = precoCobertura.getFim();
        RequestContext.getCurrentInstance().execute("coberturaViagemEditar('"+precoCobertura.getIncio()+"','"+precoCobertura.getFim()+"','"+precoCobertura.getTotal()+"','"+precoCobertura.getPremio()+"')"); 
    }
    public void passagem(PrecoCobertura pc)
    {
        precoCobertura = new PrecoCobertura(pc);
        Validacao.AtualizarCompoente("forUtlCober", "dataIncio");
        Validacao.AtualizarCompoente("forUtlCober", "dataFim");
        Validacao.AtualizarCompoente("forUtlCober", "NC");
        Validacao.AtualizarCompoente("forUtlCober", "preco");
        RequestContext.getCurrentInstance().execute("alterarCoberturaViagem()");
    }

    public Taxa getTaxa() {
        return (taxa==null)? taxa = new Taxa():taxa;
    }

    public void setTaxa(Taxa taxa) {
        this.taxa = taxa;
    }

    public Taxa getTaxaSeleted() {
        return (taxaSeleted==null) ? taxaSeleted = new Taxa():taxaSeleted;
    }

    public void setTaxaSeleted(Taxa taxaSeleted) {
        this.taxaSeleted = taxaSeleted;
    }

    public ArrayList<Taxa> getTaxaList() {
        return (taxaList == null )? taxaList= new ArrayList<>():taxaList;
    }

    public void setTaxaList(ArrayList<Taxa> taxaList) {
        this.taxaList = taxaList;
    }

    public ComoBox getBanco() {
        return  (banco==null)? banco = new ComoBox() : banco;
    }

    public void setBanco(ComoBox banco) {
        this.banco = banco;
    }

    public ComoBox getBancoSeleted() {
        return (bancoSeleted == null) ? bancoSeleted= new ComoBox() : bancoSeleted;
    }

    public void setBancoSeleted(ComoBox bancoSeleted) {
        this.bancoSeleted = bancoSeleted;
    }

    public ArrayList<ComoBox> getBancoList() {
        return (bancoList==null) ? bancoList = new ArrayList() : bancoList;
    }

    public void setBancoList(ArrayList<ComoBox> bancoList) {
        this.bancoList = bancoList;
    }

    public ArrayList<ComoBox> getMoedaLista() {
        return (moedaLista==null)?moedaLista= new ArrayList<>():moedaLista;
    }

    public void setMoedaLista(ArrayList<ComoBox> moedaLista) {
        this.moedaLista = moedaLista;
    }
    
    public void regBancoAndTaxa(Integer i)
    {
        Object re = ud.regBankAndTaxa((i==1) ? getBanco() : getTaxa() , i);
        if(i==1)
        {
            if(re!=null && "true".equals(re.toString()))
            {
                Mensagem.addInfoMsg("Novo Banco registado!");
                bancoList =  ud.bancoList();
                Validacao.AtualizarCompoente("forBanco", "tableBanco");
                RequestContext.getCurrentInstance().execute("limparcamposBanco()");
            }
            else if((re!=null && !"true".equals(re.toString())))
            {
                Mensagem.addErrorMsg(re.toString());
            }
            Validacao.AtualizarCompoente("forBanco", "growlBanco");   
        }
        else
        {
            if(re!=null && "true".equals(re.toString().substring(0, 4)))
            {
//                String[] r = re.toString().split(";");
                Mensagem.addInfoMsg("Nova Taxa registada!");
                RequestContext.getCurrentInstance().execute("limparcamposTaxa()");
                carregarListaTaxaMoeda();
            }
            else if(re!=null && !"true".equals(re.toString().substring(0, 4)))
            {
                Mensagem.addErrorMsg(re.toString());
            }
            Validacao.AtualizarCompoente("forTaxa", "growlTaxa");
        }
    }

    public void carregarListaTaxaMoeda() {
         taxaList =  ud.taxaMoeda(taxa.getMoeda1());
         Validacao.AtualizarCompoente("forTaxa", "tableTaxa");
    }
    
    public void desativeBancoAndTaxa(Integer i)
    {
        
    }
}
