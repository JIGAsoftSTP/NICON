
package bean;

import dao.ContratoDao;
import dao.SeguroDao;
import dao.ViagemDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Contrato;
import modelo.Viagem;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean
@ViewScoped
public class ViagemBean  implements  Serializable
{
    private static final long SERIAL_VERSIONUID =1L;
    private List<Viagem> infoPessoaSegurada = new ArrayList<>();
    private String msg;
    private List<ComoBox> paises = new ArrayList<>();
    private final SeguroDao sd;
    private List<ComoBox> listDocumentos = new ArrayList<>();
    private static final String PATTERN="dd/MM/yyyy";
    private String numPassaporte; //recebe o número de passaporte selecionado na tabela.
    private Viagem viagem = new Viagem();
    private Viagem viagemSelecionada = new Viagem();
    private Contrato contrato = new Contrato();
    @ManagedProperty( value = "#{clausulasBean}")
    private ClausulasBean clausula;
    private String numDias;
    private String dia;
    private  ViagemDao viagemDao = new ViagemDao();
    private List<ComoBox> listaSexo = new ArrayList<>();
    private ContratoDao contratoDao;
    private ArrayList<String> data =new ArrayList<>();
    private List<String> enderecos;
    private List<String> listaNumeroDocumentos = new ArrayList<>();
    private List<String> listaObjetivoViagem = new ArrayList<>();
    private List<String> listaCidadeDestino = new ArrayList<>();
    private List<String> listaZonaDestino = new ArrayList<>();
    private List<Viagem> listContractData = new ArrayList<>();
    private List<Viagem> listContractSum = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   
    
    @SuppressWarnings({"LeakingThisInConstructor", "LeakingThisInConstructor"})
    public ViagemBean()
    {
        enderecos = new ArrayList<>();
        enderecos = viagemDao.enderecos();
        contratoDao = new ContratoDao();
        viagemDao = new ViagemDao();
        sd = new SeguroDao();
        listaSexo = viagemDao.listaSexos();
        listDocumentos = viagemDao.listaDocumentos();
        paises = viagemDao.listaPaises();
        listaNumeroDocumentos = viagemDao.listaNumeroDocumentos();
        listaObjetivoViagem = viagemDao.listaObjetivoViagem();
        listaCidadeDestino = viagemDao.listaCidadeDestino();
        listaZonaDestino = viagemDao.listaZonaDestino();
        if(((ViagemBean)SessionUtil.obterValor("TIN"))!=null)
        {
            ViagemBean vb = ((ViagemBean) SessionUtil.obterValor("TIN"));
             for (Field f : this.getClass().getDeclaredFields()) {
                 try {
                     f.setAccessible(true);
                     f.set(this, f.get(vb));
                 } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                 }
             }
             RequestContext.getCurrentInstance().execute("backAndcontine()");
        }
    }
    
    public ClausulasBean getClausula() {
        return clausula;
    }

    public void setClausula(ClausulasBean clausula) {
        this.clausula = clausula;
    }
    
    public List<Viagem> getInfoPessoaSegurada() 
    {
        return infoPessoaSegurada;
    }

    public List<ComoBox> getListaSexo() {
        return listaSexo;
    }

    public Viagem getViagemSelecionada() {
        return (viagemSelecionada == null) ? viagemSelecionada = new Viagem() : viagemSelecionada;
    }

    public void setViagemSelecionada(Viagem viagemSelecionada) {
        this.viagemSelecionada = viagemSelecionada;
    }

    public void setInfoPessoaSegurada(List<Viagem> infoPessoaSegurada) 
    {
        this.infoPessoaSegurada = infoPessoaSegurada;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
 
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
    
    public List<ComoBox> getPaises() {
        return paises;
    }
  
    public List<ComoBox> getListDocumentos() {
        return listDocumentos;
    }
    //método que devolve o número de dias
    public void devolverDias()
    { 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(viagem.isTipoViagem() == false)
        {
            if(viagem.getDataInicio()!=null && viagem.getDataFim()!=null)
            {
                 if(OperacaoData.compareDates(viagem.getDataInicio(), viagem.getDataFim()) == -1)
                     Message.addErrorMsg("Data de Fim não pode ser inferior a data Inicial.", "ViagemformularioGrowl", "viagemInfo");
                 else
                 {
                    listContractData = contratoDao.obterValoresSegurado(viagem.getDataInicio(), viagem.getDataFim(), ((viagem.isTipoViagem()== true)? 2 : 1));
                    
                    if (listContractData.isEmpty()) 
                    {
                        dia = null;
                        Mensagem.addErrorMsg("A Cobertura de Viagem para esse intervalo não foi definido.");
                        RequestContext.getCurrentInstance().update("ViagemformularioGrowl:viagemInfo");
                    } else if (listContractData.get(0).getNumDias()==1) {
                        dia = String.valueOf(listContractData.get(0).getNumDias())+" dia";
                        contrato.setDias(String.valueOf(listContractData.get(0).getNumDias()));
                    } else {
                        dia = String.valueOf(listContractData.get(0).getNumDias())+" dias";
                        contrato.setDias(String.valueOf(listContractData.get(0).getNumDias()));
                    }
                }
                Validacao.atualizar("Viagemformulario", "numDias");
            }
        }
        else
        {
            if(viagem.getDataInicio() != null)
            {
                viagem.setDataFimFormatada(Validacao.somarDiasData(OperacaoData.toStringDDMMYYY(viagem.getDataInicio())));
                viagem.setDataFim(OperacaoData.stringFormatToDate(viagem.getDataFimFormatada(), "dd-MM-yyyy"));
                Validacao.atualizar("Viagemformulario", "VdataF");
                 RequestContext.getCurrentInstance().execute("desativarDataFIm()");
                listContractData = contratoDao.obterValoresSegurado(viagem.getDataInicio(), viagem.getDataFim(), ((viagem.isTipoViagem()== true)? 2 : 1));
                if (listContractData.isEmpty()) 
                  {
                      dia = null;
                      Mensagem.addErrorMsg("A Cobertura de Viagem para esse intervalo não foi definido.");
                      RequestContext.getCurrentInstance().update("ViagemformularioGrowl:viagemInfo");
                  } else if (listContractData.get(0).getNumDias()==1) {
                      dia = String.valueOf(listContractData.get(0).getNumDias())+" dia";
                      contrato.setDias(String.valueOf(listContractData.get(0).getNumDias()));
                  } else {
                      dia = String.valueOf(listContractData.get(0).getNumDias())+" dias";
                      contrato.setDias(String.valueOf(listContractData.get(0).getNumDias()));
                  }
                 Validacao.atualizar("Viagemformulario", "numDias");
            }
        }
                    
      
    }
    public void setListDocumentos(List<ComoBox> listDocumentos) {
        this.listDocumentos = listDocumentos;
    }
  

    public void setPaises(List<ComoBox> paises) {
        this.paises = paises;
    }

    public Viagem getViagem()
    {
        return (viagem==null) ? viagem = new Viagem() : viagem;
    }

       public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public void addTable()
    {          
        if(existNumDoc() == false)
        {
            if(checkApolicy() == false)
            {
                if(dia == null)
                     Message.addErrorMsg("A Cobertura de Viagem para esse intervalo não foi definido.", "ViagemformularioGrowl", "viagemInfo");
                else
                {
                    viagem.setNumDias(listContractData.get(0).getNumDias()); // número de dias deste contrato
                    determineDates(); // determina a menor e maior data
                    calcular();
                    devolverDescricao("sexo");
                    devolverDescricao("tipo documento");
                    devolverDescricao("nacionalidade");
                    devolverDescricao("país");
                    viagem.setSumNc(listContractData.get(0).getNc());
                    viagem.setSumSubTotal(listContractData.get(0).getValorPremio());
                    viagem.setSumTotal(listContractData.get(0).getSumTotal());
                    infoPessoaSegurada.add(new Viagem(viagem));
                    RequestContext.getCurrentInstance().execute("viagemDadosAdicionados()");
                    Validacao.atualizar("ViagemformularioTable", "tabela1", "numeroDoc");
                }    
            }
        }
    }
    
    
    public void devolverDescricao(String tipo)
    {
        switch (tipo)
        {
            case "sexo":
                for(ComoBox cb : listaSexo)
                {
                    if(viagem.getSexoOther().equals(cb.getId()))
                    {
                        viagem.setSexo(cb.getValue());
                        break;
                    }
                } 
                break;
            case "tipo documento":
                for(ComoBox cb : this.listDocumentos)
                {
                    if(viagem.getTipoDocOther().equals(cb.getId()))
                    {
                        viagem.setTipoDoc(cb.getValue());
                        break;
                    }
                }  
                break;
            case "nacionalidade":
                for(ComoBox cb : this.paises)
                {
                    if(viagem.getNacionalidadeOther().equals(cb.getId()))
                    {
                        viagem.setNacionalidade(cb.getValue());
                        break;
                    }
                }  
                break;
            default:
                for(ComoBox cb : this.paises)
                {
                    if(viagem.getPaisDestinoOther().equals(cb.getId()))
                    {
                        viagem.setPaisDestino(cb.getValue());
                        break;
                    }
                }   
                break;
        }
       
    }

    
    @SuppressWarnings("unchecked")
    public void calcular()
    {
         @SuppressWarnings("UnusedAssignment")
        float calcularNC = 0, calcularPremioBruto = 0, valor = 0, calculcarPremioLiquido = 0, valorPremio = 0, impostoCincoPorCento = 0, impostoSeisPorCento = 0;
        if(viagem.getDataInicio() != null && viagem.getDataFim() != null)
        {
            if(OperacaoData.DataNascimentoSuperior(viagem.getDataNasc()) >75 )
            {
                System.out.println("entrou idade superior\nNC= "+listContractData.get(0).getNc());
                viagem.setTotalSegurado("Ilimitado");
                viagem.setTotalSeguradoMoeda("Ilimitado");
               // calcularNC = listContractData.get(0).getValorNC()* infoPessoaSegurada.size();// determina o valor de de NC (Nicon comission)
                calcularNC = listContractData.get(0).getNc();// determina o valor de de NC (Nicon comission)
                calcularNC = calcularNC *2;
                viagem.setNc(calcularNC);//valor de nc
                valorPremio =listContractData.get(0).getValorPremio()*2;
                viagem.setValorPremio(valorPremio);
                impostoCincoPorCento = listContractData.get(0).getValorImpostoCincoPorCento() *2;
                impostoSeisPorCento = listContractData.get(0).getValorImpostoSeisPorCento() *2;
                viagem.setValorImpostoCincoPorCento(impostoCincoPorCento);
                viagem.setValorImpostoSeisPorCento(impostoSeisPorCento);
             //   calcularPremioBruto = viagem.getValorPremio()* infoPessoaSegurada.size();// DETERMINA O PREMIO BRUTO DESTE SEGURO
                calcularPremioBruto = viagem.getValorPremio();// DETERMINA O PREMIO BRUTO DESTE SEGURO
                viagem.setPremioBruto(String.valueOf(calcularPremioBruto*2));//atribui o valor de premio bruto deste seguro
                viagem.setPremioBrutoMoeda(Moeda.format(calcularPremioBruto));
                valor = calcularNC*(1+viagem.getValorImpostoCincoPorCento()+viagem.getValorImpostoSeisPorCento());
                valor = (float) arrendodamentoNCalculado(Float.toString(valor));
                calculcarPremioLiquido = (float) (Moeda.arrendodamento(Double.toString(valor)) + calcularPremioBruto);
                viagem.setPremioLiquido(Double.toString(calculcarPremioLiquido));
                viagem.setPremioLiquidoMoeda(Moeda.format(Moeda.arrendodamento(Double.toString(calculcarPremioLiquido))));   
            }
            else
            {
                viagem.setTotalSegurado("Ilimitado");
                viagem.setTotalSeguradoMoeda("Ilimitado");
//                calcularNC = listContractData.get(0).getNc()* infoPessoaSegurada.size();// determina o valor de de NC (Nicon comission)
                calcularNC = listContractData.get(0).getNc();// determina o valor de de NC (Nicon comission)
                viagem.setNc(calcularNC);//valor de nc
//                calcularPremioBruto = listContractData.get(0).getValorPremio()* infoPessoaSegurada.size();// DETERMINA O PREMIO BRUTO DESTE SEGURO
                calcularPremioBruto = listContractData.get(0).getValorPremio();// DETERMINA O PREMIO BRUTO DESTE SEGURO
                viagem.setPremioBruto(String.valueOf(calcularPremioBruto));//atribui o valor de premio bruto deste seguro
                viagem.setPremioBrutoMoeda(Moeda.format(calcularPremioBruto));
                System.out.println("nc= "+listContractData.get(0).getNc()+"\n5% = "+listContractData.get(0).getValorImpostoCincoPorCento()+"\n6%= "+listContractData.get(0).getValorImpostoSeisPorCento());
                valor = calcularNC*(1+listContractData.get(0).getValorImpostoCincoPorCento()+ listContractData.get(0).getValorImpostoSeisPorCento());
       
                valor = (float) arrendodamentoNCalculado(Float.toString(valor));
                calculcarPremioLiquido = (float) (Moeda.arrendodamento(Float.toString(valor)) + calcularPremioBruto);
                viagem.setPremioLiquido(Double.toString(calculcarPremioLiquido));
                viagem.setPremioLiquidoMoeda(Moeda.format(Moeda.arrendodamento(Double.toString(calculcarPremioLiquido))));     
            }
            viagem.setNc((float) Moeda.arrendodamento(ViagemBean.calNCWhit(Float.valueOf(viagem.getPremioLiquido()), Float.valueOf(viagem.getPremioBruto()))));
            viagem.setIdTaxa(listContractData.get(0).getIdTaxa());
            System.out.println("========VALORES VIAGEM=======");
            System.out.println("NC = "+viagem.getNc());
            System.out.println("Premio bruto = "+viagem.getPremioBruto());
            System.out.println("Premio Liquido = "+viagem.getPremioLiquido());
         
        }
    }
    public void removeFromTable(Viagem viagem) // remove a linha selecionada na tabela do seguro de viagem
    {
        viagemSelecionada = viagem;
        infoPessoaSegurada.remove(viagemSelecionada);  
    }
    
   
    public boolean validarNumeroRegistro()
    {
        boolean valido;
        valido = ContratoDao.isNumRegistroVago(viagem.getNumeroRegistro());
        if(valido == false)
        {
            Mensagem.addErrorMsg("Número de registro já existe.");
            RequestContext.getCurrentInstance().update("ViagemformularioGrowl:viagemInfo");
            RequestContext.getCurrentInstance().execute("moveTop()");
        }
        return valido;
    }

    public void moreInformations(Viagem viagem)
    {
        viagemSelecionada = viagem;
        Validacao.atualizar("ViagemformularioGrowl", "nomeP", "enderecoP", 
                "documentoP", "numDocP", "dataEmissaoP", "localEmissaoP", "dataNascP", "localNascP", "sexoP", 
                "telefoneP", "outrasInfP", "apoliceS", "objetivoV", "cidadeDestinoV", "zonaDestinoV", "viagemPaisD", "viagemDataInicioF", "viagemDataFimF", "nascimentoP" );      
  
        RequestContext.getCurrentInstance().execute("mostrarModalInfo()");
    }
    
    public void editarInfo(Viagem v)
    {
        viagem = v;
        viagemSelecionada = v;
        viagem.setDataNasc(OperacaoData.stringFormatToDate(viagem.getDataNascFormatada(), PATTERN));
        viagem.setDataEmissao(OperacaoData.stringFormatToDate(viagem.getDataEmissaoFormatada(), PATTERN));
        viagem.setDataInicio(OperacaoData.stringFormatToDate(viagem.getDataInicioFormatada(), PATTERN));
        viagem.setDataFim(OperacaoData.stringFormatToDate(viagem.getDataFimFormatada(), PATTERN));
        viagem.setSexo(idInfo("sexo"));
        viagem.setTipoDoc(idInfo("documento"));
        viagem.setNacionalidade(idInfo("nacionalidade"));
        viagem.setPaisDestino(idInfo("país"));
        infoPessoaSegurada.remove(v);
        listContractData = contratoDao.obterValoresSegurado(v.getDataInicio(), v.getDataFim(), ((v.isTipoViagem()== true)? 2 : 1));
        Validacao.atualizar("Viagemformulario", "Vapolice", "Vobj", "Vpais", "VdataI", "VdataF", "viagemCidadeDestino", "viagemZonaDestino");
        Validacao.atualizar("ViagemformularioTable",
                "Vnome","Vadress","VtipoDoc","numeroDoc","dataEmissaoViagem", "viagemNac",
                "VlocalEmissao","VdataNasc","ViagemLocalNascimento","Vsexo","ViagemOutrasInf","contacto","tabela1");
        RequestContext.getCurrentInstance().execute("editar()");
        
    }
    
    /**
     * função que verifica-se o número de documento já existe na tabela
     * @return true se existe e false caso contrário
     */
    private boolean existNumDoc()
    {
        if(!infoPessoaSegurada.isEmpty())
        {
            for(Viagem viagem1 : infoPessoaSegurada)
            {
                if(viagem.getNumDoc().equals(viagem1.getNumDoc()) && viagem.getTipoDocOther().equals(viagem1.getTipoDocOther()))
                {
                    Mensagem.addErrorMsg("Número de documento já existe.");
                    RequestContext.getCurrentInstance().addCallbackParam("numeroDocumento", "existe");// retorna uma resposta
                    RequestContext.getCurrentInstance().update("ViagemformularioGrowl:viagemInfo");
                    return true;
                }
            }
        }
        return false;
    }
    
   
    public List<String>completarListaNumeroDocumentos(String info)
    {    
        listaNumeroDocumentos.set(0, info);
        return Validacao.likeStart(this.listaNumeroDocumentos, info);       
    }
    
    public void carregarSegurado()
    {
        viagemDao.carregarSegurado(viagem.getNumDoc());
    }
    
    private boolean checkApolicy()
    {
        if(!infoPessoaSegurada.isEmpty())
        {
            for(Viagem v: infoPessoaSegurada)
            {
                if(v.getNumApolice().equalsIgnoreCase(viagem.getNumApolice()))
                {
                     RequestContext.getCurrentInstance().execute("apoliceSeguradoVerificar()");
                      Message.addWarningMsg("Já existe um Segurado(a) com esse número de apólice.", "ViagemformularioGrowl", "viagemInfo");
                      return true;
                }
            }
        }
        return false;
    }
    
   private boolean verificarApolice()
   {
       if(infoPessoaSegurada.size()>1)
       {
           for(Viagem v: infoPessoaSegurada)
           {
               if(v.getNumApolice().equals(viagem.getNumApolice()))
               {
                    RequestContext.getCurrentInstance().execute("apoliceSeguradoVerificar()");
                    Message.addWarningMsg("Já existe um Segurado(a) com esse número de apólice.", "ViagemformularioGrowl", "viagemInfo");
                    return true;
               }
           }
       }
       return false;
   }
   public void proximo()
   {
        double premioBruto = 0, premioLiquido = 0;
        float nc = 0,sumNc =0, sumTotal=0, sumSubTotal=0;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        viagem.setNumApolice(facesContext.getExternalContext().getRequestParameterMap().get("apolice"));
        viagem.setNumeroRegistro(facesContext.getExternalContext().getRequestParameterMap().get("numeroR"));
        if(this.infoPessoaSegurada.isEmpty())
            Message.addWarningMsg("Adicione Pessoas Seguradas.", "ViagemformularioGrowl", "viagemInfo");
        else
        {
            if(validarNumeroRegistro() == true)
            {
                if(verificarApolice() == false)
                {
                    for(Viagem v : infoPessoaSegurada)
                    {
                        premioBruto += Double.valueOf(v.getPremioBruto());
                        premioLiquido += Double.valueOf(v.getPremioLiquido());
                        nc += v.getNc();
                        sumNc +=v.getSumNc();
                        sumSubTotal +=v.getSumSubTotal();
                        sumTotal +=v.getSumTotal();
                    }
                    viagem.setPremioLiquido(Double.toString(premioLiquido));
                    viagem.setPremioBruto(Double.toString(premioBruto));
                    viagem.setPremioBrutoMoeda(Moeda.format(premioBruto));
                    viagem.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
                    viagem.setNc(nc);
                    viagem.setSumNc(sumNc);
                    viagem.setSumSubTotal(sumSubTotal);
                    viagem.setSumTotal(sumTotal);
                    viagem.setDataInicio(viagem.getMenorData());
                    viagem.setDataFim(viagem.getMaiorData());
                    System.out.println("========VALORES VIAGEM=======\nNC total "+viagem.getNc()
                            +"\nPremio bruto total "+viagem.getPremioBruto()+"Premio liquido total "+viagem.getPremioLiquido());
                    if(SessionUtil.obterValor("TIN") == null)
                        SessionUtil.definirParametro("TIN", this);
                    else
                    {
                        SessionUtil.removerParametro("TIN");
                        SessionUtil.definirParametro("TIN", this);
                    }
                    Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");
                }        
            }
        }
   }
      
   public void viagemUpdate()
   {
       System.out.println(this.getViagem().getObjetivoViagem());
   }
   
   public void dadosEnviados()
   {
       String pattern ="dd-MM-yyyy";
       FacesContext facesContext = FacesContext.getCurrentInstance();
       viagem.setNomePessoaSegurada(facesContext.getExternalContext().getRequestParameterMap().get("nomePessoa"));
       viagem.setEndereco(facesContext.getExternalContext().getRequestParameterMap().get("address"));
       viagem.setCidadeDestino(facesContext.getExternalContext().getRequestParameterMap().get("cidade de destino"));
       viagem.setZonaDestino(facesContext.getExternalContext().getRequestParameterMap().get("zona de destino"));
       viagem.setTipoDocOther(facesContext.getExternalContext().getRequestParameterMap().get("documento"));
       viagem.setNacionalidadeOther(facesContext.getExternalContext().getRequestParameterMap().get("nacionalidade"));
       viagem.setNumDoc(facesContext.getExternalContext().getRequestParameterMap().get("numDoc"));
       viagem.setDataEmissao(OperacaoData.stringFormatToDate(facesContext.getExternalContext().getRequestParameterMap().get("dataEmissao"), pattern));
       viagem.setLocalEmissao(facesContext.getExternalContext().getRequestParameterMap().get("localEmissao"));
       viagem.setDataNasc(OperacaoData.stringFormatToDate(facesContext.getExternalContext().getRequestParameterMap().get("dataNasc"), pattern));
       viagem.setLocalNascimento(facesContext.getExternalContext().getRequestParameterMap().get("localNasc"));
       viagem.setSexoOther(facesContext.getExternalContext().getRequestParameterMap().get("sexo"));
       viagem.setTelefone(facesContext.getExternalContext().getRequestParameterMap().get("telefone"));
       viagem.setOutrasInformacoes(facesContext.getExternalContext().getRequestParameterMap().get("outrasInf"));
       viagem.setObjetivoViagem(facesContext.getExternalContext().getRequestParameterMap().get("objetivo da viagem"));
       viagem.setPaisDestinoOther(facesContext.getExternalContext().getRequestParameterMap().get("pais de destino"));
       viagem.setNumApolice(facesContext.getExternalContext().getRequestParameterMap().get("apolice segurado"));
       viagem.setDataInicio(OperacaoData.stringFormatToDate(facesContext.getExternalContext().getRequestParameterMap().get("data de inicio"), pattern));
       viagem.setDataFim(OperacaoData.stringFormatToDate(facesContext.getExternalContext().getRequestParameterMap().get("data de fim"), pattern));
       viagem.setDataEmissaoFormatada(sdf.format(viagem.getDataEmissao()));
       viagem.setDataNascFormatada(sdf.format(viagem.getDataNasc()));
       viagem.setDataInicioFormatada(sdf.format(viagem.getDataInicio()));
       viagem.setDataFimFormatada(sdf.format(viagem.getDataFim()));
      
       if(facesContext.getExternalContext().getRequestParameterMap().get("multiViagem").equals("normal"))
           viagem.setTipoViagem(false);
       else
       {viagem.setTipoViagem(true);}
        
        addTable();
   }
   
    private void determineDates()
    {
         if(viagem.getMenorData()!= null)
         {
             if(OperacaoData.compareDates(viagem.getMenorData(), viagem.getDataInicio()) == -1)
                 viagem.setMenorData(viagem.getDataInicio());
         }
        else
             viagem.setMenorData(viagem.getDataInicio());       
        if(viagem.getMaiorData()!= null)
        {
            if(OperacaoData.compareDates(viagem.getMaiorData(), viagem.getDataFim()) == 1)
                 viagem.setMaiorData(viagem.getDataFim());   
        }
        else
            viagem.setMaiorData(viagem.getDataFim());  
    }

   public void limparTudo()
   {
       infoPessoaSegurada.clear();
   }
   
   public void multiViagem()
   {
       if(viagem.isTipoViagem() == true)
           RequestContext.getCurrentInstance().execute("multiViagem('multi')");
       else
           RequestContext.getCurrentInstance().execute("multiViagem('N multi')");
   }
   
   public String idInfo(String info)
   {
       String id = null;
        switch (info) 
        {
            case "sexo":
                for(ComoBox cb : listaSexo)
                {
                    if(viagemSelecionada.getSexo().equals(cb.getValue()))
                    {
                        id = cb.getId();
                        break;
                    }
                }  
                break;
            case "documento":
                for(ComoBox cb : listDocumentos)
                {
                    if(viagemSelecionada.getTipoDoc().equals(cb.getValue()))
                    {
                        id = cb.getId();
                        break;
                    }
                }
                break;
            case "nacionalidade":
                for(ComoBox cb : paises)
                {
                    if(viagemSelecionada.getNacionalidade().equals(cb.getValue()))
                    {
                        id = cb.getId();
                        break;
                    }
                }
                break;
            default:
                for(ComoBox cb : paises)
                {
                    if(viagemSelecionada.getPaisDestino().equals(cb.getValue()))
                    {
                        id = cb.getId();
                        break;
                    }
                }
                break;
        }
        return id; 
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
            if(s != null)
            {
                 if(s.length() >= like.length())
                {
                    s=s.substring(0, like.length());
                    if(s.toUpperCase().contains(like.toUpperCase()))
                    {
                        likeList.add(aux);
                    }
                }  
            }
        }
        return likeList;
    }

    public List<String> getEnderecos() {
        return enderecos;
    }
    
   public List<String>completarEndereco(String info)
   {    
       enderecos.set(0, info);
       return likeStart(this.enderecos, info);       
   }
   
    public static double arrendodamentoNCalculado(String s) {
        double d=Double.valueOf(s.replace(',', '.'));
        DecimalFormat formato = new DecimalFormat("#.#");
        d = Double.valueOf((formato.format(d).replace(',', '.')));
        return d;
    }
    
    public static String calNCWhit(double total, double premio) 
    {
       return Double.toString((total - premio)/(1.056));
    }
    
    public List<String>completarListaObjetivoViagem(String info)
    {    
        listaObjetivoViagem.set(0, info);
        return likeStart(this.listaObjetivoViagem, info);       
    }
    public List<String>completarListaCidadeDestino(String info)
    {    
        listaCidadeDestino.set(0, info);
        return likeStart(this.listaCidadeDestino, info);       
    }
    public List<String>completarListaZonaDestino(String info)
    {    
        listaZonaDestino.set(0, info);
        return likeStart(this.listaZonaDestino, info);       
    }
     
}


