
package bean;

import dao.CargaMaritimaDao;
import dao.ContratoDao;
import dao.SeguroDao;
import dao.VeiculoDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.CargaMaritima;
import modelo.ComoBox;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author Helio
 */
@ManagedBean(name = "carga")
@ViewScoped
public class CargaMaritimaBean implements Serializable
{
    //Atribtuos
    private static final long serialVersionUID = 1L;
    private CargaMaritima cargaMaritima = new CargaMaritima();
    private CargaMaritimaDao cargaMaritimaDao;
    private SeguroDao seguroDao;
    private String validarTable="false";
    private boolean validarForme=false;
//    private List<ComoBox> marcas;
    private List<String> listaMarcas;
    private String marcasSelectedMarca;
    private List<ComoBox> moedas = new ArrayList<>();
    private List<ComoBox> listaPaisesDestino = new ArrayList<>();
    private List<ComoBox> listaPaisesOrigem = new ArrayList<>();
    
    private ArrayList<CargaMaritima> listaModoEmbalagemEInterresse;
    private CargaMaritima selectedModoEmbalagemEInterresse;
    
    private ArrayList<CargaMaritima> listaDetahlesVeiculo;
    private CargaMaritima selectedDetahlesVeiculo;
    private List<ComoBox> moeda;
   private float  percentagemRetirar = 100-(5+0.6f);
    //Construtor
    @SuppressWarnings({"LeakingThisInConstructor", "LeakingThisInConstructor", "OverridableMethodCallInConstructor"})
    public CargaMaritimaBean()
    {
        VeiculoDao veiculoDao=  new VeiculoDao();
//        marcas = veiculoDao.marcas();
        listaMarcas = veiculoDao.listaMarcas();
        listaMarcas.add(0,"");
        moeda = veiculoDao.moedas();
        cargaMaritimaDao = new CargaMaritimaDao();
        listaPaisesDestino = cargaMaritimaDao.listaPaises(1);
        listaPaisesOrigem = cargaMaritimaDao.listaPaises(2);
        System.out.println("entrou con carga maritima");
        seguroDao = new SeguroDao();
        if (SessionUtil.obterValor("MAC") != null) {
            {
                RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
                CargaMaritimaBean cmb = ((CargaMaritimaBean) SessionUtil.obterValor("MAC"));
                for (Field f : this.getClass().getDeclaredFields()) {
                    try {
                        f.setAccessible(true);
                        f.set(this, f.get(cmb));
                    } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                    }
                }
                RequestContext.getCurrentInstance().execute("backAndcontine()");
            }
        }
    }
    
    public CargaMaritimaBean(String isnull){
        
    }

    public CargaMaritima getCargaMaritima() 
    {
        return (cargaMaritima == null) ? cargaMaritima = new CargaMaritima() : cargaMaritima;
    }

    public void setCargaMaritima(CargaMaritima cargaMaritima)
    {
        this.cargaMaritima = cargaMaritima;
    }

    public ArrayList<CargaMaritima> getListaModoEmbalagemEInterresse() {
        return ((listaModoEmbalagemEInterresse==null)? listaModoEmbalagemEInterresse = new ArrayList<>():listaModoEmbalagemEInterresse);
    }

    public void setListaModoEmbalagemEInterresse(ArrayList<CargaMaritima> listaModoEmbalagemEInterresse) {
        this.listaModoEmbalagemEInterresse = listaModoEmbalagemEInterresse;
    }

    public CargaMaritima getSelectedModoEmbalagemEInterresse() {
        return ((selectedModoEmbalagemEInterresse==null)?selectedModoEmbalagemEInterresse= new CargaMaritima():selectedModoEmbalagemEInterresse);
    }

    public void setSelectedModoEmbalagemEInterresse(CargaMaritima selectedModoEmbalagemEInterresse) {
        this.selectedModoEmbalagemEInterresse = selectedModoEmbalagemEInterresse;
    }

    public ArrayList<CargaMaritima> getListaDetahlesVeiculo() {
        return (listaDetahlesVeiculo==null)?listaDetahlesVeiculo=new ArrayList<>():listaDetahlesVeiculo;
    }

    public void setListaDetahlesVeiculo(ArrayList<CargaMaritima> listaDetahlesVeiculo) {
        this.listaDetahlesVeiculo = listaDetahlesVeiculo;
    }

    public CargaMaritima getSelectedDetahlesVeiculo() {
        return (selectedDetahlesVeiculo==null)?selectedDetahlesVeiculo= new CargaMaritima():selectedDetahlesVeiculo;
    }

    public void setSelectedDetahlesVeiculo(CargaMaritima selectedDetahlesVeiculo) {
        this.selectedDetahlesVeiculo = selectedDetahlesVeiculo;
    }

    public List<ComoBox> getListaPaisesDestino() {
        return (listaPaisesDestino==null)?listaPaisesDestino= new ArrayList<>():listaPaisesDestino;
    }

    public List<ComoBox> getListaPaisesOrigem() {
        return listaPaisesOrigem;
    }

    public String getValidarTable() {
        return validarTable;
    }

    public void setValidarTable(String validarTable) {
        this.validarTable = validarTable;
    }

    public boolean isValidarForme() {
        return validarForme;
    }

    public void setValidarForme(boolean validarForme) {
        this.validarForme = validarForme;
    }

    public List<ComoBox> getMoeda() {
        return moeda = ((moeda==null)?new ArrayList<>():moeda);
    }
    
    
   public void addInterreseEModoEbalagem()
   {
       if(!getCargaMaritima().getInterese().isEmpty()&&!getCargaMaritima().getModoEmbalagem().isEmpty())
       {
           getListaModoEmbalagemEInterresse().add( new CargaMaritima(getCargaMaritima().getInterese(), getCargaMaritima().getModoEmbalagem()));
//           getCargaMaritima().setInterese("");
//           getCargaMaritima().setModoEmbalagem("");
           RequestContext.getCurrentInstance().addCallbackParam("limparModoIterresse", "true");
           validarTable="true";
           Validacao.atualizar("cargaForm", "validarTableCargaID");
       }
   }
   
   public void deleteInterreseEModoEbalagem(CargaMaritima cm)
   {
        getListaModoEmbalagemEInterresse().remove(cm);
            validarTable=((getListaModoEmbalagemEInterresse().size()>0)?"true":"false");
       Validacao.atualizar("cargaForm", "validarTableCargaID");
   }
   
   public void removeAllInterreseEModoEbalagem()
   {
        setListaModoEmbalagemEInterresse(new ArrayList<>());
        validarTable="false";
        Validacao.atualizar("cargaForm", "validarTableCargaID");
   }

//    public List<ComoBox> getMarcas() {
//        return ((marcas==null)?marcas=new ArrayList<>():marcas);
//    }
//
//    public void setMarcas(List<ComoBox> marcas) {
//        this.marcas = marcas;
//    }

    public String getMarcasSelectedMarca(Object mObject) {
        for (String marca : listaMarcas ) {
            if (mObject.equals(marca)) {
                return marca;
            }
        }
        return marcasSelectedMarca;
    }

    public void setMarcasSelectedMarca(String marcasSelectedMarca) {
        this.marcasSelectedMarca = marcasSelectedMarca;
    }
    
   public void addDetalhesVeiculo()
   {
       
       if(!getCargaMaritima().getDeNumeroRegisto().isEmpty()&&!getCargaMaritima().getDeVeiculoComercialRegitrado().isEmpty()&&!getCargaMaritima().getDeValorCarregamentoAnual().isEmpty()
          &&!getCargaMaritima().getDeMarca().isEmpty()&&!getCargaMaritima().getDeMarca().equals("(Selecione Marca)")&&!getCargaMaritima().getDeValorMaximoCadaCarregamento().isEmpty()&&!getCargaMaritima().getDeValorMaximoVeiculo().isEmpty()&&!getCargaMaritima().getConservacaoNoite().isEmpty())
       {
           getListaDetahlesVeiculo().add( new CargaMaritima( getCargaMaritima().isDescolacaoComercia(), getCargaMaritima().isPossibilidadeTrancar()
                   , getCargaMaritima().getDeNumeroRegisto(), getCargaMaritima().getDeVeiculoComercialRegitrado(), getCargaMaritima().getDeValorCarregamentoAnual(),
                   getCargaMaritima().getDeMarca(), getCargaMaritima().getDeValorMaximoCadaCarregamento(), getCargaMaritima().getDeValorMaximoVeiculo(), getCargaMaritima().getConservacaoNoite()));
           RequestContext.getCurrentInstance().addCallbackParam("limparDetalheV", "true");
           validarForme=true;
           Validacao.atualizar("cargaForm", "cargaValidoFormeID");
        }
   }
   
   public void deleteDetalhesVeiculo(CargaMaritima cargaMaritima)
   {
        getListaDetahlesVeiculo().remove(cargaMaritima);
        if(getListaDetahlesVeiculo().size()<=0)
        {
             validarForme=false;
             Validacao.atualizar("cargaForm", "cargaValidoFormeID");
        }
   }
   
   public List<String>completarLista(String info)
   {      
        listaMarcas.set(0, info);
        ArrayList<String> lista = likeStart(this.listaMarcas, info);
        return lista;
   }
   
   
   public void removeAllDetalhesVeiculo()
   {
        setListaDetahlesVeiculo(new ArrayList<>());
        validarForme=false;
        Validacao.atualizar("cargaForm", "cargaValidoFormeID");
   }
   
   public void mensagen(int type)
   {
       if(validaNumeroApolice() == true && verificarNumeroRegistro() == true)
       {
           if(type==0)
            {
                if(getListaModoEmbalagemEInterresse().size()<=0)
                    Message.addInfoMsg("Tabela Carga Maritima  Vazia.\r\nPor favor, adicione infomações", "cargaForm", "cargaGrowl");
            }
            else
            {
                calculos();
                contruirArrayMercadoriaTransportada();
                if(getListaDetahlesVeiculo().size()<=0)
                    Message.addInfoMsg("Tabela Detalhes do(s) Veiculo(s) Vazia\r\n.Por favor, adicione infomações", "cargaForm", "cargaGrowl");
                else
                {
                    SessionUtil.definirParametro("MAC", this);
                    RequestContext.getCurrentInstance().execute("concluirCarga()");
                }
            }
        }
       
   }

    public List<String> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<String> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }
   
   public void contruirArrayMercadoriaTransportada()
   {
       ArrayList<String> arrayList= new ArrayList<>();
       if (getCargaMaritima().isMercadoriaCigaro())
       {
           arrayList.add("52");
       }
       if (getCargaMaritima().isMercadoriaVinho())
       {
           arrayList.add("53");
       }
       if (getCargaMaritima().isMercadoriaMetal()) 
       {
           arrayList.add("54");
       }
       if (getCargaMaritima().isMercadoriaExplosivo()) 
       {
           arrayList.add("55");
       }
       getCargaMaritima().setAllMercadorias(arrayList);
       System.err.println(Arrays.toString(getCargaMaritima().getAllMercadorias().toArray()));
   }
   
   public void calculosPremioBrupo()
   {
       if(!getCargaMaritima().getValorLimiteIndeminizacao().isEmpty()&&!getCargaMaritima().getTaxaValorLimiteIndeminizacao().isEmpty())
       {
           getCargaMaritima().setPremio(Moeda.format((Double.valueOf(getCargaMaritima().getValorLimiteIndeminizacao()))*((Double.valueOf(getCargaMaritima().getTaxaValorLimiteIndeminizacao())/100))));
           getCargaMaritima().setValorPremioReal(((Double.valueOf(getCargaMaritima().getValorLimiteIndeminizacao()))*((Double.valueOf(getCargaMaritima().getTaxaValorLimiteIndeminizacao())/100)))+"");
       }
   }
   
   public void calculos()
   {
       double premioBruto = 0;
       premioBruto = (percentagemRetirar * Double.valueOf(getCargaMaritima().getValorPremioReal()))/100;
       getCargaMaritima().setPremioBruto(String.valueOf(premioBruto));
       getCargaMaritima().setPremioBrutoMoeda(Moeda.format(premioBruto));
       
       getCargaMaritima().setTotalSegurado(Double.valueOf(getCargaMaritima().getTaxaValorLimiteIndeminizacao())+"");
       getCargaMaritima().setTotalSeguradoMoeda(Moeda.format(Double.valueOf(getCargaMaritima().getTaxaValorLimiteIndeminizacao())));
       
       getCargaMaritima().setPremioLiquido(getCargaMaritima().getValorPremioReal());
       getCargaMaritima().setPremioLiquidoMoeda(Moeda.format(Double.valueOf(getCargaMaritima().getValorPremioReal())));
   }
   
   public boolean verificarNumeroRegistro()
   {
       boolean existe = true;
       FacesContext context = FacesContext.getCurrentInstance();
       if(cargaMaritima.getNumeroRegistro() != null && cargaMaritima.getNumeroRegistro().length()>0)
       {
           existe = ContratoDao.isNumRegistroVago(cargaMaritima.getNumeroRegistro());
           if(existe == false)
           {
               Message.addErrorMsg("Número de registro já existe", "cargaForm", "cargaGrowl");
               RequestContext.getCurrentInstance().execute("moveTop()");
           }
       }
       return existe;
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
    
   public boolean validaNumeroApolice()
   {
        if(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null && !SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).equals("N"))
        {
             cargaMaritima.setNumerApolice(SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO).toString());
             return true;
        }
        else
            return cargaMaritima.getNumerApolice() != null && !cargaMaritima.getNumerApolice().equals("");
   }
   
   public void maisInfor(CargaMaritima cm)
   {
       selectedDetahlesVeiculo = new CargaMaritima(cm);
       Validacao.atualizar("cargaForm", "info1","info2","info3","info4","info5","info6","info7","info8","info9");
       RequestContext.getCurrentInstance().execute("mostarInfoCarga()");
   }
   
}
