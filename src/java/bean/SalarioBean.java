/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Export.GenericExcel;
import Export.GenericPDFs;
import Export.GrossSarary;
import Export.TableOfSalary;
import conexao.Call;
import dao.FuncionarioDao;
import dao.TaxaImpostoDao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lib.Moeda;
import mensagem.Message;
import modelo.AvancoSalarial;
import modelo.ComoBox;
import modelo.ProcessamentoSalario;
import modelo.SituacaoFamiliar;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author ahmedjorge
 */
@ManagedBean
@ViewScoped
public class SalarioBean implements Serializable{
   private AvancoSalarial salarial;
   private SituacaoFamiliar situacao;
   private ProcessamentoSalario processamentoSalario = new ProcessamentoSalario();
   private int numFilhos = 0;
   private ArrayList<AvancoSalarial> salarialList;
   private List<AvancoSalarial> listaAvancoSalarial;
   private ArrayList<SituacaoFamiliar> situacaoList;
   private HashMap<String, Object> hashMap = new HashMap<>();
   private String processamentoSalarioTotal, totalIRS;
   private double totalEmpresa = 0, total = 0, totalSegSocialEmpresa = 0, totalSegSocialFuncionario = 0, valorTotalIRS = 0;
   private List<ProcessamentoSalario> listaProcessamento = new ArrayList<>();
   private List<ProcessamentoSalario> processamentoSalarios = new ArrayList<>();
   private List<ComoBox> meses = new ArrayList<>();
   private List<ComoBox> anos = new ArrayList<>();   
   private ArrayList<ComoBox> funcList;
   private String pesquisa;
   private Integer typeReport;
   private int tamanho;
  

    public SalarioBean() 
    {

    }
    
    @PostConstruct
    public void init()
    {
        typeReport = 1;
        funcList = FuncionarioDao.listFuc();
        situacaoList = TaxaImpostoDao.loadSituacaoFamiliar();
        getSituacao().setSalarioBase(TaxaImpostoDao.getSaralioBase());  
        listaAvancoSalarial = TaxaImpostoDao.loadAvancoSalarial(null, null, null);
        processamentoSalarios = TaxaImpostoDao.listagemProcessamentoSalario("", "");
        initial();
        if(listaAvancoSalarial.size()>0)
        {
             RequestContext.getCurrentInstance().execute("$('.firstField').html('"+listaAvancoSalarial.get((listaAvancoSalarial.size()-1)).getValor() +"')");
             listaAvancoSalarial.remove(listaAvancoSalarial.size()-1);
        }
        else
           RequestContext.getCurrentInstance().execute("$('.firstField').html('"+""+"')");
        
    }

    public AvancoSalarial getSalarial() {
        return (salarial == null) ? salarial = new AvancoSalarial() :salarial;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    private void initial()
    {
        meses.add(new ComoBox("01", "Janeiro"));
        meses.add(new ComoBox("02", "Fevereiro"));
        meses.add(new ComoBox("03", "Março"));
        meses.add(new ComoBox("04", "Abril"));
        meses.add(new ComoBox("05", "Maio"));
        meses.add(new ComoBox("06", "Junho"));
        meses.add(new ComoBox("07", "Julho"));
        meses.add(new ComoBox("08", "Agosto"));
        meses.add(new ComoBox("09", "Setembro"));
        meses.add(new ComoBox("10", "Outubro"));
        meses.add(new ComoBox("11", "Novembro"));
        meses.add(new ComoBox("12", "Dezembro"));
        
        anos.add(new ComoBox(String.valueOf(Validacao.anoAtual()-1), String.valueOf(Validacao.anoAtual()-1)));
        anos.add(new ComoBox(String.valueOf(Validacao.anoAtual()), String.valueOf(Validacao.anoAtual())));        
    }

    public Integer getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(Integer typeReport) {
        this.typeReport = typeReport;
    }
   
    public List<ProcessamentoSalario> getProcessamentoSalarios() {
        return processamentoSalarios;
    }

    public List<ComoBox> getAnos() {
        return anos;
    }

    public List<ComoBox> getMeses() {
        return meses;
    }

    public void setSalarial(AvancoSalarial salarial) {
        this.salarial = salarial;
    }

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public ArrayList<AvancoSalarial> getSalarialList() {
        return (salarialList == null) ? salarialList = new ArrayList<>() : salarialList;
    }

    public void setSalarialList(ArrayList<AvancoSalarial> salarialList) {
        this.salarialList = salarialList;
    }

    public ArrayList<ComoBox> getFuncList() {
        return (funcList == null) ? funcList = new ArrayList<>() : funcList;
    }

    public void setFuncList(ArrayList<ComoBox> funcList) {
        this.funcList = funcList;
    }

    public SituacaoFamiliar getSituacao() {
        return ((situacao == null) ? situacao = new SituacaoFamiliar() : situacao);
    }

    public void setSituacao(SituacaoFamiliar situacao) {
        this.situacao = situacao;
    }

    public ArrayList<SituacaoFamiliar> getSituacaoList() {
        return ((situacaoList == null) ? situacaoList = new ArrayList<>() : situacaoList);
    }

    public List<AvancoSalarial> getListaAvancoSalarial() {
        return listaAvancoSalarial;
    }

    public void setSituacaoList(ArrayList<SituacaoFamiliar> situacaoList) {
        this.situacaoList = situacaoList;
    }
    
    public void avancoSalarialMoreInfo(AvancoSalarial as)
    {
        this.salarial = as;
        Validacao.atualizar("formContabilidadeSalario", "avancoSalarialMaisInfo");
        RequestContext.getCurrentInstance().execute("$('.avancoSalarialmodalMaisInfo').fadeIn(400)");
    }
    
    public void avancoSalarial(AvancoSalarial as)
    {
        this.salarial = as;
    }
            
    public void desativarAvancoSalarial()
    {
        Object result = TaxaImpostoDao.cancelarAvancoSalarial(Integer.valueOf(salarial.getId()));
        if(result != null)
        {
            if(result.toString().split(";")[0].equals("true"))
            {
                Message.addInfoMsg("Avanço Salarial desativado com sucesso", "formContabilidadeSalario:formAvancoSalarial", "avancoSalarialGrowl");
                listaAvancoSalarial.clear();
                listaAvancoSalarial = TaxaImpostoDao.loadAvancoSalarial(null, null, null);
                Validacao.atualizar("formContabilidadeSalario", "avancoTable");
            }
        }
    }
       
    public void regAvancoSalarial()
    {
        if(validarCamposAvancoSalarial() == true )
        {
            if(this.avancoSalarioFuncionario() == true)
            {
                if(avancoSalarialValidarNumDoc() == true)
                {
                      Object o = TaxaImpostoDao.regAvancoSalarial(salarial);
                      String[] ar = toString(o).split(";");
                      if( ar[0].equals("true") )
                      {
                          RequestContext.getCurrentInstance().execute("$('.avancoSalarialCampo').val(''),$('.avancoSalarialCampo').css('border',''),$('.mp-avanco').fadeOut(400)");
                          Message.addInfoMsg("Avanço Salarial registrado com sucesso!", "formContabilidadeSalario:formAvancoSalarial", "avancoSalarialGrowl");
                          listaAvancoSalarial = TaxaImpostoDao.loadAvancoSalarial(null, null, null);
                          Validacao.atualizar("formContabilidadeSalario", "avancoTable");
                      }
                      else
                          Message.addInfoMsg(ar[1],  "formContabilidadeSalario:formAvancoSalarial", "avancoSalarialGrowl");
                }        
            }
        }  
    }
    
   public String toString(Object o)
   {
       return ((o == null) ? "" : o.toString() );
   }
   
   public void alterarSalarioBase()
   {
        if(situacao.getSalarioBase() != null && situacao.getSalarioBase()>0)
        {
            situacao.setNumFilho(numFilhos);
            String result = TaxaImpostoDao.alterarSalarioBase(situacao.getSalarioBase());       
            if(result.split(";")[0].equals("true"))
            {
                situacaoList.clear();
                situacaoList = TaxaImpostoDao.loadSituacaoFamiliar();
                Validacao.atualizar("formContabilidadeSalario","situacaoTable");
                RequestContext.getCurrentInstance().execute("$('.situacaoFamiliarSalarioBase').val('"+situacao.getSalarioBase()+"')");
                RequestContext.getCurrentInstance().execute("$('.situacaoFamiliarAlterarSalarioBase').fadeOut(400)");
            }
        }
   }
     
   /**
    * Registro de situação familiar
    */
   public void regSituacaoFamiliar()
   {
       float valor;
       
       if(numFilhos >0)
       {
           if(situacao.getSalarioBase() != null && situacao.getSalarioBase()>0)
           {
                situacao.setNumFilho(numFilhos);
                String result = TaxaImpostoDao.alterarSalarioBase(situacao.getSalarioBase());       
                 if(result.split(";")[0].equals("true"))
                 {
                     result = TaxaImpostoDao.regSituacaoFamiliar(situacao);
                      if(result.split(";")[0].equals("true"))
                      {
                          RequestContext.getCurrentInstance().execute("$('.mp-situacao').fadeOut(400),$('.situacaoFamiliarCampo').val('');");
                          Message.addInfoMsg("Situação familiar atualizado com sucesso!", "formContabilidadeSalario:formSituacaoFamiliar", "situacaoFamiliarGrowl");
                          situacaoList.clear();
                          situacaoList = TaxaImpostoDao.loadSituacaoFamiliar();
                          Validacao.atualizar("formContabilidadeSalario","situacaoTable");
                          numFilhos = 0;
                      }
                      else
                          Message.addInfoMsg(result.split(";")[1], "formContabilidadeSalario:formSituacaoFamiliar", "situacaoFamiliarGrowl");
                 }
           }
       }
       else
       {
            if(existNumFilho() == false && (situacao.getSalarioBase() != null && situacao.getSalarioBase()>0))
            {
                 String result = TaxaImpostoDao.alterarSalarioBase(situacao.getSalarioBase());       
                if(result.split(";")[0].equals("true"))
                {
                    result = TaxaImpostoDao.regSituacaoFamiliar(situacao);
                     if(result.split(";")[0].equals("true"))
                     {
                         RequestContext.getCurrentInstance().execute("$('.mp-situacao').fadeOut(400),$('.situacaoFamiliarCampo').val('');");
                         Message.addInfoMsg("Novo registro de situação familiar efectuado com sucesso!", "formContabilidadeSalario:formSituacaoFamiliar", "situacaoFamiliarGrowl");
                         situacaoList.clear();
                         situacaoList = TaxaImpostoDao.loadSituacaoFamiliar();
                         Validacao.atualizar("formContabilidadeSalario","situacaoTable");
                     }
                     else
                         Message.addInfoMsg(result.split(";")[1], "formContabilidadeSalario:formSituacaoFamiliar", "situacaoFamiliarGrowl");
                }        
            }
       }
   }
   
   private boolean existNumFilho()
   {
       for(SituacaoFamiliar situacaoFamiliar : this.situacaoList)
       {
           if(situacaoFamiliar.getNumFilho().equals(situacao.getNumFilho()))
           {
               Message.addWarningMsg("Já existe registro de situação familiar com "+situacaoFamiliar.getNumFilho()+" filho(s)", 
                       "formContabilidadeSalario:formSituacaoFamiliar", "situacaoFamiliarGrowl");
                return true;
           }
       }
       return false;
   }
   
   public void editarSituacaoFamiliar(SituacaoFamiliar s)
   {
       numFilhos = s.getNumFilho();
       RequestContext.getCurrentInstance().execute("editarSituacaoFamiliar('"+s.getNumFilho()+"','"+s.getPercentagem()+"')");
   }
   
  

   private boolean validarCamposAvancoSalarial()
   {
       if((salarial.getFuncionario() != null && !salarial.getFuncionario().equals(""))
           && (salarial.getValor() != null && !salarial.getValor().equals("")))
        {
            return true;
        }
        else
           return false;
   }
   public boolean avancoSalarioFuncionario()
   {
       if(salarial.getFuncionario() != null && !salarial.getFuncionario().equals(""))
       {
            HashMap<String, Object> salarioProcessamento;
            float salarioFinalMes;
            salarioProcessamento = TaxaImpostoDao.processamentoSalario(Integer.valueOf(salarial.getFuncionario()), "avanço");
            
            if(salarioProcessamento.size() >0)
            {
                if(salarioProcessamento.get(TaxaImpostoDao.SALARIO_RESULTADO).equals("false"))
                    Message.addWarningMsg(salarioProcessamento.get(TaxaImpostoDao.SALARIO_MESSAGE)+"", "formContabilidadeSalario:formAvancoSalarial", "avancoSalarialGrowl");
                else
                {
                    salarioFinalMes = Float.valueOf(salarioProcessamento.get(TaxaImpostoDao.SALARIO_FINAL_MES)+"");
                    if(salarial.getValor() != null && !salarial.getValor().equals(""))
                    {
                       if((salarioFinalMes-Float.valueOf(salarial.getValor()))<0)
                       {
                           RequestContext.getCurrentInstance().execute("$('.avancoSalarialValor').css('border', '1px solid red')");
                           Message.addWarningMsg("Valor de Avanço Salarial é superior ao salário", "formContabilidadeSalario:formAvancoSalarial", "avancoSalarialGrowl");
                           return false;
                       }
                       else return true;
                           
                    } 
                }
            }
       }
       return false;
   }
   
   private boolean avancoSalarialValidarNumDoc()
   {
       for(AvancoSalarial as : this.listaAvancoSalarial)
       {
           if(as.getNumDoc().equals(salarial.getNumDoc()))
           {
               System.out.println("documento "+as.getNumDoc());
                RequestContext.getCurrentInstance().execute("$('.avancoSalarialNumDoc').css('border', '1px solid red')");
                Message.addWarningMsg("Número de documento já existe", "formContabilidadeSalario:formAvancoSalarial", "avancoSalarialGrowl");
                return false;
           }
       }
       return true;
   }

    public ProcessamentoSalario getProcessamentoSalario() {
        return (processamentoSalario == null) ? processamentoSalario = new ProcessamentoSalario() : processamentoSalario;
    }

    public void setProcessamentoSalario(ProcessamentoSalario processamentoSalario) {
        this.processamentoSalario = processamentoSalario;
    }

    public List<ProcessamentoSalario> getListaProcessamento() {
        return listaProcessamento;
    }

    public String getProcessamentoSalarioTotal() {
        return processamentoSalarioTotal;
    }

   
   public void processamentoSalarioFuncionario()
   { 
       this.hashMap = TaxaImpostoDao.processamentoSalario(Integer.valueOf(processamentoSalario.getFuncionario()), "processamento salário");
       if(hashMap.size() >0)
       {       
            processamentoSalario.setValorTotalReceber(hashMap.get(TaxaImpostoDao.SALARIO_FINAL_MES)+"");
            processamentoSalario.setValorIRS(hashMap.get(TaxaImpostoDao.IRS)+"");
            processamentoSalario.setValorTotalSemImposto(hashMap.get(TaxaImpostoDao.ESTRUTURA_SALARIO)+"");
            processamentoSalario.setValorTotalReceber(Moeda.format(Double.valueOf(processamentoSalario.getValorTotalReceber())));
            processamentoSalario.setValorIRS(Moeda.format(Double.valueOf(processamentoSalario.getValorIRS())));
            
            Validacao.atualizar("formContabilidadeSalario", "processamentoSalarioBase","processamentoSalarioValorSemImposto","processamentoSalarioAlmocoLivre",
                    "processamentoSalarioValorTransporte","processamentoSalarioAlmoco","processamentoSalarioAlojamento","processamentoSalarioValorComissao",
                    "processamentoSalarioValorAvanco","processamentoSalarioSituacaoFamiliar","processamentoSalarioSSFuncionario","processamentoSalarioIRS",
                    "processamentoSalarioParcelaBater","processamentoSalarioFinalMes");           
       }
   }
   
   private String processamentoSalarioNomeFuncionario(String funcionario)
   {
       for(ComoBox cb: this.funcList)
       {
           if(funcionario.equals(cb.getId()))
               return cb.getValue();
       }
       return null;
   }
   
   public void processamentoSalarioAdicionarTabela()
   {
       if(processamentoSalario.getFuncionario() != null && !processamentoSalario.getFuncionario().equals(""))
       {
            totalEmpresa +=Double.valueOf(hashMap.get(TaxaImpostoDao.SALARIO_FINAL_MES)+"");
            totalSegSocialEmpresa +=Double.valueOf(hashMap.get(TaxaImpostoDao.SS_EMPRESA)+"");
            totalSegSocialFuncionario +=Double.valueOf(hashMap.get(TaxaImpostoDao.SS_FUNCIONAIRO)+"");
            total = totalEmpresa + totalSegSocialEmpresa + totalSegSocialFuncionario;
            valorTotalIRS+=Double.valueOf(hashMap.get(TaxaImpostoDao.IRS)+"");
            processamentoSalario.setValorTotalEmpresa(Moeda.format(totalEmpresa));
            processamentoSalario.setValorTotalSegSocialEmpresa(Moeda.format(totalSegSocialEmpresa));
            processamentoSalario.setValorTotalSegSocialFuncionario(Moeda.format(totalSegSocialFuncionario));
            processamentoSalario.setValorIRS(Moeda.format(valorTotalIRS));
            processamentoSalarioTotal = Moeda.format(total);

            listaProcessamento.add(new ProcessamentoSalario(processamentoSalario.getFuncionario(),processamentoSalarioNomeFuncionario(processamentoSalario.getFuncionario()), processamentoSalario.getValorTotalSemImposto(), processamentoSalario.getValorTotalReceber(),
            processamentoSalario.getValorTotalEmpresa(), processamentoSalario.getValorTotalSegSocialEmpresa(),processamentoSalario.getValorTotalSegSocialFuncionario(),processamentoSalario.getValorIRS()));
            RequestContext.getCurrentInstance().execute("$('.processamentoSalarioLimparCamposTabela').html('')");
            for(int i=0;i<this.funcList.size();i++)
            {
                if(funcList.get(i).getId().equals(processamentoSalario.getFuncionario()))
                {
                    funcList.remove(i);
                    break;
                }
            }
            processamentoSalario.setFuncionario("");
             Validacao.atualizar("formContabilidadeSalario","processamentoSalarioTotalSegSocialEmpresa","processamentoSalarioTotalSegSocialFuncionario",
                    "processamentoSalarioValorTotalEmpresa","processamentoSalarioValorTotal","processamentoSalarioTabela","processamentoSalarioFuncionario","processamentoSalarioTotalIRS"); 
            RequestContext.getCurrentInstance().execute("$('.processamentoConcluirRegistro').css('visibility','visible')");
       }

   }
   
   public void processamentoSalarioRegistro()
   {
        totalEmpresa =0;
        totalSegSocialEmpresa =0;
        totalSegSocialFuncionario =0;
        total = 0;
        valorTotalIRS = 0;
        listaProcessamento.clear();
        funcList = FuncionarioDao.listFuc();
        RequestContext.getCurrentInstance().execute("$('.processamentoSalarioLimparCamposTabela').html('')");
        RequestContext.getCurrentInstance().execute("$('.processamentoConcluirRegistro').css('visibility','hidden')");
        RequestContext.getCurrentInstance().execute("$('.processamentoSalarioTotais').html('')");
        RequestContext.getCurrentInstance().execute("$('.processamentoSalarioMonth').val('')");
        RequestContext.getCurrentInstance().execute("$('.processamentoSalarioMonth').css('border','')");
        RequestContext.getCurrentInstance().execute("$('.processamentoSalarioYear').val('')");
        RequestContext.getCurrentInstance().execute("$('.processamentoSalarioYear').css('border','')");
        Validacao.atualizar("formContabilidadeSalario", "processamentoSalarioTabela","processamentoSalarioFuncionario");
   }
   public void processsamentoSalarioRemoverDadosTabela(ProcessamentoSalario ps)
   {
        totalEmpresa =0;
        totalSegSocialEmpresa =0;
        totalSegSocialFuncionario =0;
        total = 0;
        valorTotalIRS = 0;
        listaProcessamento.remove(ps);
       if(listaProcessamento.isEmpty())
       {
            funcList = FuncionarioDao.listFuc();
            RequestContext.getCurrentInstance().execute("$('.processamentoConcluirRegistro').css('visibility','hidden')");
            RequestContext.getCurrentInstance().execute("$('.processamentoSalarioTotais').html('')");
            Validacao.atualizar("formContabilidadeSalario", "processamentoSalarioTabela","processamentoSalarioFuncionario");
       }
       else
       {
           for(ProcessamentoSalario ps1 : listaProcessamento)
            {
                totalEmpresa +=Validacao.desformatarValor(ps1.getValorTotalEmpresa());
                totalSegSocialEmpresa +=Validacao.desformatarValor(ps1.getValorTotalSegSocialEmpresa());
                totalSegSocialFuncionario +=Validacao.desformatarValor(ps1.getValorTotalSegSocialFuncionario());
                this.valorTotalIRS +=Validacao.desformatarValor(ps1.getValorIRS());
            }
            total = totalEmpresa + totalSegSocialEmpresa + totalSegSocialFuncionario;
            processamentoSalario.setValorTotalEmpresa(Moeda.format(totalEmpresa));
            processamentoSalario.setValorTotalSegSocialEmpresa(Moeda.format(totalSegSocialEmpresa));
            processamentoSalario.setValorTotalSegSocialFuncionario(Moeda.format(totalSegSocialFuncionario));
            processamentoSalario.setValorIRS(Moeda.format(valorTotalIRS));
            processamentoSalarioTotal = Moeda.format(total);
            System.out.println("id "+ps.getIdFuncionario()+"\nnome "+ ps.getFuncionario());
            funcList.add(new ComoBox(ps.getIdFuncionario(), ps.getFuncionario()));
            Validacao.atualizar("formContabilidadeSalario","processamentoSalarioValorTotalEmpresa",
                "processamentoSalarioTotalSegSocialEmpresa","processamentoSalarioTotalSegSocialFuncionario",
               "processamentoSalarioTabela","processamentoSalarioValorTotal","processamentoSalarioTotalIRS","processamentoSalarioFuncionario");
       }
   }
   
   public void regProcessamentoSalario()
   {
       int idProcess = 0;
       String result = TaxaImpostoDao.registrarProcessoSalario(Integer.valueOf(processamentoSalario.getAno()), Integer.valueOf(processamentoSalario.getMes()));
       if(result.split(";")[0].equals("true"))
       {
            idProcess = Integer.valueOf(result.split(";")[1]);
            for(ProcessamentoSalario ps : listaProcessamento)
            {
                result = TaxaImpostoDao.registrarProcessamentoSalario(Integer.valueOf(ps.getIdFuncionario()), idProcess).toString();
            }
            if(result.split(";")[0].equals("true"))
            {
                result = TaxaImpostoDao.endProcessSalary(idProcess);
                if(result.split(";")[0].equals("true"))
                {
                     totalEmpresa =0;
                    totalSegSocialEmpresa =0;
                    totalSegSocialFuncionario =0;
                    total = 0;
                    valorTotalIRS = 0;
                    listaProcessamento.clear();
                    processamentoSalarios.clear();
                    processamentoSalarios = TaxaImpostoDao.listagemProcessamentoSalario("", "");
                    Validacao.atualizar("formContabilidadeSalario", "tabelaProcessamentoSalario");
                    RequestContext.getCurrentInstance().execute("$('.processamentoSalarioTotais').html('')");
                    RequestContext.getCurrentInstance().execute("$('.processamentoSalarioMonth').val('')");
                    RequestContext.getCurrentInstance().execute("$('.processamentoSalarioYear').val('')");
                    RequestContext.getCurrentInstance().execute("$('.mp-process').fadeOut(400)");
                    Message.addInfoMsg("Procesamento de salário registrado com sucesso!", "formContabilidadeSalario", "processamentoSalarioGrowl");
                }
                else
                    Message.addErrorMsg(result.split(";")[1], "formContabilidadeSalario", "processamentoSalarioGrowl");
            }
            else
                Message.addErrorMsg(result.split(";")[1], "formContabilidadeSalario", "processamentoSalarioGrowl");
       }
       else
           Message.addErrorMsg(result.split(";")[1], "formContabilidadeSalario", "processamentoSalarioGrowl"); 
   }
   
   public void processamentoSalarioTabela(ProcessamentoSalario ps, int operation)
   {
       processamentoSalario = new ProcessamentoSalario(ps);
       if(operation == 1)
           TableOfSalary.ciarDoc(SessionUtil.getUserlogado().getNomeAcesso(), ps.getId(), ps.getData());
   }
   
   public void anularProcessamentoSalario()
   {
       String result = TaxaImpostoDao.anularProcessamentoSalario(Integer.valueOf(processamentoSalario.getId()));
       if(result.split(";")[0].equals("true"))
       {
            Message.addInfoMsg("Procesamento de salário anulado com sucesso", "formContabilidadeSalario", "processamentoSalarioGrowl");
            processamentoSalarios.clear();
            processamentoSalarios = TaxaImpostoDao.listagemProcessamentoSalario("", "");
            Validacao.atualizar("formContabilidadeSalario", "tabelaProcessamentoSalario");
       }
       else
           Message.addInfoMsg(result.split(";")[1], "formContabilidadeSalario", "processamentoSalarioGrowl");
   }
   
   public void pesquisarProcessamentoSalario()
   {
        processamentoSalarios = TaxaImpostoDao.pesquisarProcessamentoSalario(processamentoSalario.getAno(), processamentoSalario.getMes(), pesquisa);
        Validacao.atualizar("formContabilidadeSalario", "tabelaProcessamentoSalario");
   }
  
   public void reportDoc(int i)
   {
        if (typeReport == 1) { //Estrutura de Salário
            if (i == 1) {
                GrossSarary.criaDoc(SessionUtil.getUserlogado().getNomeAcesso());
            } else {

            }
        } else if (typeReport == 2) { //Situação Familiar
            ResultSet rs = Call.selectFrom("VER_SITUACAOFAMILIAR", "*");
            DataTableControl report = new DataTableControl("id55", "clienteff.fjfjf");
            report.prepareModel(rs, DataTableControl.ShowMode.HIDE,"ID");
            
            if (i == 1) {
                GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Situação Familiar", "Situação Familia", report, GenericPDFs.OrientacaoPagina.VERTICAL, -1);
            } else {
                GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Situação Familiar", "Situação Familia", report, -1);
            }
        } else if (typeReport == 3) { //Avanço Salarial
            ResultSet rs = Call.callTableFunction("PACK_RH.loadAvancoSalarial", "*", null, null, null);
            DataTableControl report = new DataTableControl("id55", "clienteff.fjfjf");
            report.prepareModel(rs, DataTableControl.ShowMode.HIDE,"ID");
            if (i == 1) {
                GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Avanço Salarial", "Avanço Salarial", report, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1);
            } else {
                GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Avanço Salarial", "Avanço Salarial", report, -1);
            }
        }

    }
  
}
