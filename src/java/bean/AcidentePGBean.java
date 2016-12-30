
package bean;

import dao.ContratoDao;
import dao.SeguroDao;
import dao.VeiculoDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import modelo.AcidentePG;
import modelo.ComoBox;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helio
 */
@ManagedBean(name = "acidente")
@ViewScoped
public class AcidentePGBean implements Serializable
{
    //Atributos
    private static final long serialVersionUID = 1L;
    private AcidentePG acidentePG = new AcidentePG();
    private AcidentePG acidente2 = new AcidentePG();
    private SeguroDao seguroDao = new SeguroDao();
    private List<AcidentePG>   info = new ArrayList<>();
    private List<AcidentePG>  listaPremio = new ArrayList<>();
    private boolean habilitarDesabilitar=true;
    private boolean obrigatorio = false;
    private String msg,msg2;
    private boolean obrigatorio2 = false;
    private List<ComoBox> moedas = new ArrayList<>();
    private VeiculoDao veiculoDao = new VeiculoDao();
    private boolean lastYears = true;
    private boolean habilitarRemover=true;
    @ManagedProperty(value = "#{listaRespostas}")
    private ListaRespostas listaRespostas;
    private Date maxDate;
    private Date minDate;
    private float  percentagemRetirar = 100-(5+0.6f);

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    

    @SuppressWarnings({"LeakingThisInConstructor", "LeakingThisInConstructor"})
    public AcidentePGBean()
    {
        moedas = veiculoDao.moedas();
        if(SessionUtil.obterValor("GPA") != null)
        {
            RequestContext.getCurrentInstance().execute("checkApolice('"+SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO)+"')");
            AcidentePGBean apgb = ((AcidentePGBean) SessionUtil.obterValor("GPA"));
              for (Field f : this.getClass().getDeclaredFields()) {
                  try {
                      f.setAccessible(true);
                      f.set(this, f.get(apgb));
                  } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                  }
              }
              RequestContext.getCurrentInstance().execute("backAndcontine()");
        }
    }

    public boolean isHabilitarRemover() {
        return habilitarRemover;
    }

    public AcidentePG getAcidentePG() {
        return (acidentePG == null) ? acidentePG = new  AcidentePG() : acidentePG;
     }

    public boolean isLastYears() {
        return lastYears;
    }

 
    public List<ComoBox> getMoedas() {
        return moedas;
    }

    public void setMoedas(List<ComoBox> moedas) {
        this.moedas = moedas;
    }

    public List<AcidentePG> getInfo() {
        return info;
    }

    public ListaRespostas getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(ListaRespostas listaRespostas) {
        this.listaRespostas = listaRespostas;
    }

    public void setInfo(List<AcidentePG> info) {
        this.info = info;
    }
    public SeguroDao getSeguroDao() {
        return seguroDao;
    }


    public boolean isHabilitarDesabilitar() {
   
        return habilitarDesabilitar;
    }

    public void setAcidentePG(AcidentePG acidentePG) {
        this.acidentePG = acidentePG;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public boolean isObrigatorio2() {
        return obrigatorio2;
    }

    public Date getMaxDate() {
        return maxDate = (new Date());
    }

    public Date getMinDate() throws ParseException {
        String[] dd = sdf.format(new Date()).split("-");
        return minDate =(sdf.parse("01-01-"+(Integer.valueOf(dd[2])-190)));
    }
    
    // metodo que permite ativar ou desativar os campos defeitos fisicos e acidentes nos últimos anos
    public void alterarEstadoCampo()
    {
        habilitarDesabilitar = (acidentePG.getDefeitosFisicos() == null);
        lastYears = (acidentePG.getAcidenteUltimosTresAnos()==null);
        habilitarRemover = (info.isEmpty());    
        if(habilitarDesabilitar == true)
        {
            msg = "campoDefeitoDesativado";
            obrigatorio = false;
            
        }
        else
        {
            acidentePG.setDefeitosFisicosCampo("");
            msg = "campoDefeitoAtivo";
            obrigatorio = true;
        }
        if(lastYears == true)
        {
            msg2 =  "campoAcidenteDesativado";
            obrigatorio2 = false;
        }
        else
        {
            acidentePG.setAcidenteUltimos("");
            msg2 = "campoAcidenteAtivo";
            obrigatorio2 = true;
        }
      
    }
    
    /**
     * Método que determina o premio de morte, incapacidade total, incapacidade temporária, despesa medica, custo repatriamento, prémio bruto,
     * limite de responsabilidade e prémio liquido
     */
    @SuppressWarnings("UnusedAssignment")
    public void calcular()
    { 
        double premioBruto = 0,premioLiquido = 0;
        double taxaMorte = Double.valueOf(acidentePG.getTaxaMorte())/100;
        double taxaIncapTemp = Double.valueOf(acidentePG.getTaxaIncapacidadeTemporaria())/100;
        double taxaDespesa = Double.valueOf(acidentePG.getTaxaDespesaMedica())/100;
        double taxaPermanente = Double.valueOf(acidentePG.getTaxaIncapacidadePermanente())/100;
   
        double taxaCustoRep = Double.valueOf(acidentePG.getTaxaCustoRepatriamento())/100;
        double premioMorte = (acidentePG.getPremioMorte()==null || acidentePG.getPremioMorte().equals("")) ? 0 : Double.valueOf(acidentePG.getPremioMorte());// atribui o valor de premio por morte
        double premioIncapacidadeTemporaria = (acidentePG.getPremioIncapacidadeTemporaria()==null || acidentePG.getPremioIncapacidadeTemporaria().equals(""))
                 ? 0 : Double.valueOf(acidentePG.getPremioIncapacidadeTemporaria());
        double premioDesepesaMedica = (acidentePG.getPremioDespesaMedica()==null || acidentePG.getPremioDespesaMedica().equals(""))
                 ? 0 : Double.valueOf(acidentePG.getPremioDespesaMedica());
        double premioIncapacidadePermanente = (acidentePG.getPremioIncapacidadePermanente()==null || acidentePG.getPremioIncapacidadePermanente().equals(""))
                 ? 0 : Double.valueOf(acidentePG.getPremioIncapacidadePermanente());
        double premioCustoRepatriamento = (acidentePG.getPremioCustoRepatriamento()==null || acidentePG.getPremioCustoRepatriamento().equals(""))
                 ? 0 : Double.valueOf(acidentePG.getPremioCustoRepatriamento());
               // Determina o valor do premio para morte
       premioMorte = premioMorte + (Double.valueOf(acidentePG.getValorMorte()) * taxaMorte);
       acidentePG.setPremioMorteMoeda(Moeda.format(premioMorte));
       acidentePG.setPremioMorte(String.valueOf(premioMorte));
       //determina o valor de premio para incapacidade temporaria
        premioIncapacidadeTemporaria = premioIncapacidadeTemporaria + (Double.valueOf(acidentePG.getIncapacidadeTotalTemporaria()) * taxaIncapTemp);
       acidentePG.setPremioIncapacidadeTemporariaMoeda(Moeda.format(premioIncapacidadeTemporaria));
       acidentePG.setPremioIncapacidadeTemporaria(String.valueOf(premioIncapacidadeTemporaria));
       premioDesepesaMedica = premioDesepesaMedica + (Double.valueOf(acidentePG.getDespesaMedica()) * taxaDespesa);
       acidentePG.setPremioDespesaMedicaMoeda(Moeda.format(premioDesepesaMedica));
       acidentePG.setPremioDespesaMedica(String.valueOf(premioDesepesaMedica));
       premioIncapacidadePermanente = premioIncapacidadePermanente + (Double.valueOf(acidentePG.getIncapacidadeTotal()) * taxaPermanente);
       acidentePG.setPremioIncapacidadePermanenteMoeda(Moeda.format(premioIncapacidadePermanente));
       acidentePG.setPremioIncapacidadePermanente(String.valueOf(premioIncapacidadePermanente));
       premioCustoRepatriamento = premioCustoRepatriamento +(Double.valueOf(acidentePG.getCustoRepatriamento()) * taxaCustoRep);
       acidentePG.setPremioCustoRepatriamentoMoeda(Moeda.format(premioCustoRepatriamento));
       acidentePG.setPremioCustoRepatriamento(String.valueOf(premioCustoRepatriamento));
       
       premioLiquido = Double.valueOf(acidentePG.getPremioMorte()) + Double.valueOf(acidentePG.getPremioIncapacidadePermanente()) +
              Double.valueOf(acidentePG.getPremioIncapacidadeTemporaria()) + Double.valueOf(acidentePG.getPremioDespesaMedica()) + Double.valueOf(acidentePG.getPremioCustoRepatriamento());
       premioBruto = (percentagemRetirar * premioLiquido)/100;
       acidentePG.setPremioLiquido(String.valueOf(premioLiquido));
       acidentePG.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
       acidentePG.setPremioBruto(String.valueOf(premioBruto));
       acidentePG.setPremioBrutoMoeda(Moeda.format(premioBruto));
       listaPremio.add(new AcidentePG(String.valueOf(premioMorte), String.valueOf(premioIncapacidadePermanente), String.valueOf(premioIncapacidadeTemporaria), String.valueOf(premioDesepesaMedica), String.valueOf(premioCustoRepatriamento)));
       SessionUtil.removerParametro("GPA");
       SessionUtil.removerParametro("respostas");
       SessionUtil.definirParametro("GPA", this);
       SessionUtil.definirParametro("respostas", listaRespostas);
       
    }

 
    
    public void avancar() 
    {
        if(info.size()>0)
          {
             
              Validacao.redirecionar("GestSeg_NovoSeguroApolice.xhtml");
          }
        else
        {
            mensagem.Mensagem.addWarningMsg("Adicione Informações na Tabela!");
            RequestContext.getCurrentInstance().update("acidenteF:acidenteGrowl");
        }
    }
    
    //remove todas as informações na tabela
    public void removeAllFromTable()
    {
       info.clear();
       RequestContext.getCurrentInstance().execute("limparValoresPremio()");
       Validacao.AtualizarCompoente("acidenteF", "Acidentetabela");
    }
    
    /**
     * Método que permite adicionar informações na tabela. Adiciona as informações do seguro de acidente para grupo.
     * Para os campos defeitos fisicos e acidentes se estiverem nulos será apresentado na tabela nenhum.
     * Determina também o total de todos os beneficios deste seguro
     */
    
    @SuppressWarnings("LocalVariableHidesMemberVariable")
    public void AdicionarTabela()
    {
        // variáveis que servirão para determinar o valor total de todos os beneficios deste seguro   
        @SuppressWarnings("UnusedAssignment")
        double totalMorte = 0, totalDespesa = 0,totalIncapTotal = 0, totalIncaTemp = 0,totalCusto = 0, limiteResp = 0;   
        habilitarRemover = false;// ativa o botão remover
        
       info.add(new AcidentePG(acidentePG.getNome(), acidentePG.getCategoria(), acidentePG.getProfissao(),
      acidentePG.getDataNascimentoFormatada(),
               acidentePG.getValorMorte(), acidentePG.getIncapacidadeTotalTemporaria(),
       acidentePG.getDespesaMedica(), acidentePG.getIncapacidadeTotal(), acidentePG.getCustoRepatriamento(),
      ((acidentePG.getDefeitosFisicosCampo() == null) ? "Nenhum" :acidentePG.getDefeitosFisicosCampo()), 
               ((acidentePG.getAcidenteUltimos() == null) ? "Nenhum" : acidentePG.getAcidenteUltimos()),acidentePG.getDataNascimento(), acidentePG.getTaxaMorte(),
               acidentePG.getTaxaDespesaMedica(), acidentePG.getTaxaIncapacidadeTemporaria(), acidentePG.getTaxaIncapacidadePermanente(), acidentePG.getTaxaCustoRepatriamento()));
 

        for (AcidentePG info1 : info) {
            // SOMA DE TODOS OS BENEFICIOS DESTE SEGURO
            totalMorte += Double.valueOf(info1.getValorMorte());
            totalDespesa += Double.valueOf(info1.getDespesaMedica());
            totalIncapTotal += Double.valueOf(info1.getIncapacidadeTotal());
            totalIncaTemp += Double.valueOf(info1.getIncapacidadeTotalTemporaria());
            totalCusto += Double.valueOf(info1.getCustoRepatriamento());
        }
        limiteResp = totalMorte + totalDespesa + totalIncapTotal + totalIncaTemp + totalCusto;
        acidentePG.setTotalMorte(Moeda.format(totalMorte));
        acidentePG.setTotalDespesa(Moeda.format(totalDespesa));
        acidentePG.setTotalIncaTotal(Moeda.format(totalIncapTotal));
        acidentePG.setTotalIncapTemp(Moeda.format(totalIncaTemp));
        acidentePG.setTotalCusto(Moeda.format(totalCusto));
        acidentePG.setLimiteResponsabilidade(String.valueOf(limiteResp));
        acidentePG.setTotalSeguradoMoeda(Moeda.format(limiteResp));
        calcular();
        acidentePG.setSigla(Validacao.Sigla(acidentePG.getMoeda()));
  
        RequestContext.getCurrentInstance().execute("acidenteLimparCampos()");
        update();
      
    }
    
    public void update()
    {
         Validacao.AtualizarCompoente("acidenteF", "Acidentetabela");
        Validacao.AtualizarCompoente("acidenteF", "limiteMorte");
        Validacao.AtualizarCompoente("acidenteF", "premioMorte");
        Validacao.AtualizarCompoente("acidenteF", "limiteIncaP");
        Validacao.AtualizarCompoente("acidenteF", "premioIncapacidadeTotal");
        Validacao.AtualizarCompoente("acidenteF", "limiteIncaT");
        Validacao.AtualizarCompoente("acidenteF", "premioIncapacidadeTemp");
        Validacao.AtualizarCompoente("acidenteF", "limiteDespesaM");
        Validacao.AtualizarCompoente("acidenteF", "premioDesepesa");
        Validacao.AtualizarCompoente("acidenteF", "limiteCustoR");
        Validacao.AtualizarCompoente("acidenteF", "premioCusto");
        Validacao.AtualizarCompoente("acidenteF", "totalS");
        Validacao.AtualizarCompoente("acidenteF", "AcPremioL");
        
    }
    
    // remove os dados da linha selecionada na tabela e atualiza a própria tabela e outros componentes
    public void removerDados()
    {
//        System.out.println(acidentePG.toString());
            // variáveis que servirão para determinar o valor total de todos os beneficios deste seguro   
        @SuppressWarnings({"UnusedAssignment", "LocalVariableHidesMemberVariable"})
        double totalMorte = 0,taxaMorte= 0, taxaDespesa= 0,taxaCustoRep=0, taxaIncaTemp=0, taxaIncaTotal=0, totalDespesa = 0,totalIncapTotal = 0, totalIncaTemp = 0,totalCusto = 0, limiteResp = 0,premioBruto = 0,premioLiquido = 0;  
        double premioMorte = 0, premioDespesa = 0, premioIncaTotal = 0, premioCustoRep = 0, premioIncapTemp = 0;
        if(info.size()==1)
        {
             info.remove(acidente2);
             listaPremio.remove(acidentePG);
             RequestContext.getCurrentInstance().execute("limparValoresPremio()");
        }
        else
        {
             info.remove(acidente2);
             listaPremio.remove(acidentePG);
             for (AcidentePG info1 : info) 
             {
                // SOMA DE TODOS OS BENEFICIOS DESTE SEGURO
                totalMorte += Double.valueOf(info1.getValorMorte());
                totalDespesa += Double.valueOf(info1.getDespesaMedica());
                totalIncapTotal += Double.valueOf(info1.getIncapacidadeTotal());
                totalIncaTemp += Double.valueOf(info1.getIncapacidadeTotalTemporaria());
                totalCusto += Double.valueOf(info1.getCustoRepatriamento());
                 limiteResp = totalMorte + totalDespesa + totalIncapTotal + totalIncaTemp + totalCusto;
                acidentePG.setTotalMorte(Moeda.format(totalMorte));
                acidentePG.setTotalDespesa(Moeda.format(totalDespesa));
                acidentePG.setTotalIncaTotal(Moeda.format(totalIncapTotal));
                acidentePG.setTotalIncapTemp(Moeda.format(totalIncaTemp));
                acidentePG.setTotalCusto(Moeda.format(totalCusto));
                acidentePG.setLimiteResponsabilidade(String.valueOf(limiteResp));
                acidentePG.setTotalSeguradoMoeda(Moeda.format(limiteResp));
            }
             taxaMorte = totalMorte/100;
             taxaDespesa = totalDespesa /100;
             taxaIncaTemp = totalIncaTemp/100;
             taxaIncaTotal = totalIncapTotal/100;
             taxaCustoRep = totalCusto/100;
            for (AcidentePG listaPremio1 : listaPremio) 
            {
                premioMorte +=Double.valueOf(listaPremio1.getPremioMorte())*taxaMorte;
                premioIncaTotal +=Double.valueOf(listaPremio1.getPremioIncapacidadePermanente())*taxaIncaTotal;
                premioIncapTemp +=Double.valueOf(listaPremio1.getPremioIncapacidadeTemporaria())*taxaIncaTemp;
                premioDespesa +=Double.valueOf(listaPremio1.getPremioDespesaMedica())*taxaDespesa;
                premioCustoRep +=Double.valueOf(listaPremio1.getPremioCustoRepatriamento())*taxaCustoRep;
                acidentePG.setPremioMorteMoeda(Moeda.format(premioMorte));
                acidentePG.setPremioMorte(String.valueOf(premioMorte));
                acidentePG.setPremioIncapacidadeTemporariaMoeda(Moeda.format(premioIncapTemp));
                acidentePG.setPremioIncapacidadeTemporaria(String.valueOf(premioIncapTemp));
                acidentePG.setPremioDespesaMedicaMoeda(Moeda.format(premioDespesa));
                acidentePG.setPremioDespesaMedica(String.valueOf(premioDespesa));
                acidentePG.setPremioIncapacidadePermanenteMoeda(Moeda.format(premioIncaTotal));
                acidentePG.setPremioIncapacidadePermanente(String.valueOf(premioIncaTotal));
                acidentePG.setPremioCustoRepatriamentoMoeda(Moeda.format(premioCustoRep));
                acidentePG.setPremioCustoRepatriamento(String.valueOf(premioCustoRep));
                premioLiquido = Double.valueOf(acidentePG.getPremioMorte()) + Double.valueOf(acidentePG.getPremioIncapacidadePermanente()) +
                Double.valueOf(acidentePG.getPremioIncapacidadeTemporaria()) + Double.valueOf(acidentePG.getPremioDespesaMedica()) + Double.valueOf(acidentePG.getPremioCustoRepatriamento());
                premioBruto = (percentagemRetirar * premioLiquido)/100;
            }
            acidentePG.setPremioLiquido(String.valueOf(premioLiquido));
            acidentePG.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
            acidentePG.setPremioBruto(String.valueOf(premioBruto));
            acidentePG.setPremioBrutoMoeda(Moeda.format(premioBruto)); 
            SessionUtil.removerParametro("respostas");
            SessionUtil.definirParametro("GPA", this);
            SessionUtil.definirParametro("respostas", listaRespostas);
            
        }
        update();
        
    }
    
  
    public void verificarDados()
    {
       String pattern="dd-MM-yyyy";
       String dataNasc;
       FacesContext context = FacesContext.getCurrentInstance();
       String estadoCampo = context.getExternalContext().getRequestParameterMap().get("campos");
       getAcidentePG().setNumeroApolice(context.getExternalContext().getRequestParameterMap().get("apolice"));
       getAcidentePG().setNumeroRegistro(context.getExternalContext().getRequestParameterMap().get("numeroRegistro"));
       getAcidentePG().setMoeda(context.getExternalContext().getRequestParameterMap().get("moeda"));
       getAcidentePG().setDefeitosFisicosCampo(context.getExternalContext().getRequestParameterMap().get("defeitosCampo"));
       getAcidentePG().setAcidenteUltimos(context.getExternalContext().getRequestParameterMap().get("acidente3AnosCampo"));
       getAcidentePG().setNome(context.getExternalContext().getRequestParameterMap().get("nome"));
       getAcidentePG().setProfissao(context.getExternalContext().getRequestParameterMap().get("profissão"));
       getAcidentePG().setCategoria(context.getExternalContext().getRequestParameterMap().get("categoria"));
       dataNasc = context.getExternalContext().getRequestParameterMap().get("dataNasc");
       getAcidentePG().setDataNascimento(OperacaoData.stringFormatToDate(dataNasc, pattern));
       getAcidentePG().setValorMorte(context.getExternalContext().getRequestParameterMap().get("valorMorte"));
       getAcidentePG().setTaxaMorte(context.getExternalContext().getRequestParameterMap().get("taxaMorte"));
       getAcidentePG().setIncapacidadeTotalTemporaria(context.getExternalContext().getRequestParameterMap().get("incapacidadeTemp"));
       getAcidentePG().setTaxaIncapacidadeTemporaria(context.getExternalContext().getRequestParameterMap().get("taxaIncapacidadeTemp"));
       getAcidentePG().setIncapacidadeTotal(context.getExternalContext().getRequestParameterMap().get("incapacidadePerm"));
       getAcidentePG().setTaxaIncapacidadePermanente(context.getExternalContext().getRequestParameterMap().get("taxaIncapPermanente"));
       getAcidentePG().setDespesaMedica(context.getExternalContext().getRequestParameterMap().get("despesaMedica"));
       getAcidentePG().setTaxaDespesaMedica(context.getExternalContext().getRequestParameterMap().get("taxaDespesaMedica"));
       getAcidentePG().setCustoRepatriamento(context.getExternalContext().getRequestParameterMap().get("custoRep"));
       getAcidentePG().setTaxaCustoRepatriamento(context.getExternalContext().getRequestParameterMap().get("taxaCustoRep"));
       
        System.out.println(acidentePG.toString());
       if(!estadoCampo.equals("vazio"))
            if(existNumeroRegistro() == true)
                AdicionarTabela();
    }

    public AcidentePG getAcidente2() {
        return acidente2;
    }

    public void setAcidente2(AcidentePG acidente2) {
        this.acidente2 = acidente2;
    }
    
   
    public boolean existNumeroRegistro()
    {
        boolean existe = false;
        existe = ContratoDao.isNumRegistroVago(acidentePG.getNumeroRegistro());
        if(existe == false)
        {
            RequestContext.getCurrentInstance().execute("numeroR('red')");
            Mensagem.addErrorMsg("Número de registro já existe");
            RequestContext.getCurrentInstance().update("acidenteF:acidenteGrowl");
        }
        else
        {
            RequestContext.getCurrentInstance().execute("numeroR('none')");
            RequestContext.getCurrentInstance().update("acidenteF:AcidenteRegistro");
        }
        return existe;
    }
    
    public void moreInfo()
    {
        Validacao.AtualizarCompoente("acidenteF", "moreInfoAcidente");
        RequestContext.getCurrentInstance().execute("moreInfoAPG()");
    }
    
}