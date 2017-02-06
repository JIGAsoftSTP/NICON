
package bean;

import Export.FuncPagamento;
import Export.ReciboPagamento;
import dao.ContabilidadeDao;
import dao.SinistroDao;
import enumeracao.TypeMoviment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Funcionario;
import modelo.Pagamento;
import modelo.Prestacao;
import modelo.Recebimento;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean(name = "contabilidade")
@ViewScoped
public class ContabilidadeBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Recebimento recebimento = new Recebimento();
    private List<Recebimento> contratoContabilidade = new ArrayList<>();
    private List<Prestacao> prestacoes = new ArrayList<>();
    private ContabilidadeDao cd = new ContabilidadeDao();
    private Recebimento recebimentoSelecionado = new Recebimento();
    private Prestacao prestacao = new Prestacao();
    private List<ComoBox> formaPagamento = new ArrayList<>();
    private List<ComoBox> contas = new ArrayList<>();
    private String pesquisar;
    private Pagamento pagamento = new Pagamento();
    private Pagamento pagamentoSelecionado = new Pagamento();
    private List<Pagamento> dadosPagamento = new ArrayList<>();
    private List<Pagamento> calculoValor = new ArrayList<>();
    private List<ComoBox> contasBanco = new ArrayList<>();
    private List<ComoBox> tipoPagamento = new ArrayList<>();
    private List<String> listaContas = new ArrayList<>();
    private List<Pagamento> listaPagamentos = new ArrayList<>();
    private  double valorTotal = 0;
    private String numeroCheque;
    private String infoAccount ="Account information here!";
    private String tamanhoLista, tamanhoListaPagamento;
    private String pesquisaPagamento, obs;
    private String pagamentoSolicitado;
    private String idRequisicaoPagamento;
    private List<Pagamento> pagamentoSolicitados;
    private int typeMov = 1;
 
  
    
    
    public ContabilidadeBean()
    {
        contratoContabilidade = cd.contratoClienteContabilidade("metade", null);
        formaPagamento = ComoBox.loadCombo("VER_TIPO_PAGAMENTO", "ID", "PAGAMENTO");
        contas = cd.bankAccounts();/**ComoBox.loadCombo("VER_CONTABOX", "ID", "CONTA");*/
        contasBanco = contas;
        tipoPagamento = ComoBox.loadCombo("T_MODPAYMENT", "MPAY_ID", "MPAY_DESC");
        listaContas = cd.contas();
        pagamento.setIdPagamento(cd.proximoCodigoPagamento(2));
        listaPagamentos = cd.listaPagamentos(null,"metade", null);
        tamanhoLista ="metade";
        
        pagamentoSolicitados = SinistroDao.listaPagamentosSolicitados();
        RequestContext.getCurrentInstance().execute("$('.totalNotif').html('"+((pagamentoSolicitados.isEmpty())? "": pagamentoSolicitados.size()) +"')");
//   
    }
    
    public Recebimento getRecebimento()
    {
        return (recebimento == null)? recebimento = new Recebimento() :recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public void moreInfoPayment()
    {
        Validacao.AtualizarCompoente("recebimentoForm", "payMoreInfoCodigo");
        Validacao.AtualizarCompoente("recebimentoForm", "payMoreInfoContaBanco");
        Validacao.AtualizarCompoente("recebimentoForm", "payMoreInfoValor");
        Validacao.AtualizarCompoente("recebimentoForm", "payMoreInfoPagamento");
        Validacao.AtualizarCompoente("recebimentoForm", "payMoreInfoModoPagamento");
        Validacao.AtualizarCompoente("recebimentoForm", "payMoreInfoDataRegistro");
        Validacao.AtualizarCompoente("recebimentoForm", "payMoreInfoEstado");

         RequestContext.getCurrentInstance().addCallbackParam("info", "pagamento");
    }
    
    public void infoPayment(Pagamento p)
    {
        RequestContext.getCurrentInstance().execute("$('.infoPagamentoCodigo').html('"+p.getCodigo()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoPagamentoContaBanco').html('"+p.getContaBanco()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoPagamentoValor').html('"+p.getValor()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoPagamentoPagamento').html('"+p.getPagamento()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoPagamentoTipoPagamento').html('"+p.getTipoPagamento()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoPagamentoDataRegistro').html('"+p.getDataRegistro()+"')");
        RequestContext.getCurrentInstance().execute("$('.modalInfoPayment').fadeIn()");
     
        
    }
    public List<ComoBox> getTipoPagamento() {
        return tipoPagamento;
    }

    public List<ComoBox> getContas() {
        return contas;
    }

    public List<Pagamento> getListaPagamentos() {
        return listaPagamentos;
    }

    public List<ComoBox> getContasBanco() {
        return contasBanco;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    
    public List<String> getListaContas() {
        return listaContas;
    }
    
    public String getPesquisar() {
        return pesquisar;
    }

    public void setPesquisar(String pesquisar) {
        this.pesquisar = pesquisar;
    }

    public Pagamento getPagamento() {
        return (pagamento == null) ? pagamento = new Pagamento() :pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    
    public List<ComoBox> getFormaPagamento() {
        return formaPagamento;
    }

    public List<Recebimento> getContratoContabilidade() {
        return contratoContabilidade;
    }

    public Recebimento getRecebimentoSelecionado() {
        return (recebimentoSelecionado==null)? recebimentoSelecionado = new Recebimento() :recebimentoSelecionado;
    }

    public void setRecebimentoSelecionado(Recebimento recebimentoSelecionado) {
        this.recebimentoSelecionado = recebimentoSelecionado;
    }
    
    public void moreInfo()
    {
        Validacao.atualizar("recebimentoFormTabela", "numeroInfo", "moreInoCliente", 
                "moreInfoApolice", "moreInfoSeguro", "moreInfoValorPagar", "moreInfoValorPago", "moreInfoMoeda", "moreInfoEstado");
//        RequestContext.getCurrentInstance().execute("more_info($(\".table-receivents\"))");
//        RequestContext.getCurrentInstance().execute("showInfo('recebimento')");
        RequestContext.getCurrentInstance().addCallbackParam("info", "recebimento");
    }
    
    public void moreInfoRecebimento(Recebimento r)
    {
        double valorPagar = Double.valueOf(r.getValorPagar());
        double valorPago =Double.valueOf(r.getValorPago());
        
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoNumero').html('"+r.getId()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoCliente').html('"+r.getCliente()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoApolice').html('"+r.getApolice()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoSeguro').html('"+r.getCodigoSeguro()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoValorPagar').html('"+Moeda.format(valorPagar)+"')");
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoValorPago').html('"+Moeda.format(valorPago)+"')");
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoMoeda').html('"+r.getMoeda()+"')");
        RequestContext.getCurrentInstance().execute("$('.infoRecebimentoEstado').html('"+r.getEstado()+"')");
        RequestContext.getCurrentInstance().execute("$('.modalInfoRecebimento').fadeIn()");

    }
    
    public void validarNumeroCheque()
    {   
        String sequenciaCheque = pagamento.getNumero();
       if(sequenciaCheque.startsWith(numeroCheque))
        {
            if((numeroCheque.length() - sequenciaCheque.length()) > 0)  sequenciaCheque = numeroCheque;
            
            if((numeroCheque.length() - sequenciaCheque.length()) < -3 )
                sequenciaCheque = sequenciaCheque.substring(0, (numeroCheque.length()+3));
        }
       else sequenciaCheque = numeroCheque;
    }
    
    public void carregarCheque()
    {
        String result;
        if(!SessionUtil.getUserlogado().getNivelAcesso().equals("0"))
        {
            if(((pagamento.getFormaPagamento() != null && !pagamento.getFormaPagamento().equals("")) && pagamento.getFormaPagamento().equals("2")))
            {
                result =cd.carregarNumCheque(Integer.valueOf(pagamento.getContaBanco()));
                if(result.split(";")[0].equals("true"))
                {
                    System.out.println("cheque carregado "+result);
                    numeroCheque = result.split(";")[1];
                    RequestContext.getCurrentInstance().execute("ativarDesativarCampoCheque('ativar')");
                }
                else
                {
                    Mensagem.addErrorMsg(result.split(";")[1]);
                    RequestContext.getCurrentInstance().execute("contaBancoFoco()");
                    RequestContext.getCurrentInstance().execute("ativarDesativarCampoCheque('desativar')");
                    Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
                }       
            }
            else RequestContext.getCurrentInstance().execute("ativarDesativarBotaoPagamento('ativar')");  
        }
           
 
    }
    public List<Pagamento> getDadosPagamento() {
        return dadosPagamento;
    }

    public Prestacao getPrestacao() {
        return (prestacao==null)? prestacao = new Prestacao():prestacao;
    }

    
    public List<Prestacao> getPrestacoes() {
        return prestacoes;
    }

    public void setPrestacao(Prestacao prestacao) {
        this.prestacao = prestacao;
    }
    
    public void carregarPrestacoes()
    {
        this.prestacoes = cd.carregarPrestacoes(Integer.valueOf(recebimentoSelecionado.getId()));
        RequestContext.getCurrentInstance().update("recebimentoFormTabela:prestacaoRecebimento");
    }
    
   public void novoRecebimento()
    {
        if(prestacao.getEstadoPrestacao().contains("Por"))
        {
            if(!recebimentoSelecionado.getMoeda().equals("STD"))
            {
                double cumpra = Moeda.valorCompra(recebimentoSelecionado.getIdMoeda(), recebimentoSelecionado.getDataApolice());
                double valor = (Double.valueOf(prestacao.getValorSF()) * cumpra);
                prestacao.setStdValor(valor);
                prestacao.setValorSTD(Moeda.format(valor)+" STD");
            }
            else
            {
                prestacao.setValorSTD("");
            }
            Validacao.atualizar("recebimentoFormTabela", "apolicePrestacao","clientePrestacao","seguroPrestacao","prestacaoValor","prestacaoReceb","valorSTD");
            RequestContext.getCurrentInstance().execute("mostrarFrameRecebimento()");
        }
        else
        {
             ReciboPagamento rp = new ReciboPagamento();
             String ret =rp.criarDoc(Integer.valueOf(prestacao.getId()), SessionUtil.getUserlogado().getNomeAcesso());
             RequestContext.getCurrentInstance().addCallbackParam("estado", "pago");
             RequestContext.getCurrentInstance().addCallbackParam("imprimir", ret);
        }
    }
   
   public void registrarAmortizacao()
   {
       Object[] result = null;
       if(SessionUtil.obterValor(Funcionario.SESSION_NAME) != null)
       {
           if(validarCamposAmortizacao() == true)
           {
               result = cd.regAmortizacao(prestacao).toString().split(";");
                if(result != null)
                {
                    if(result[0].equals("true"))
                    {
                        carregarPrestacoes();
                        Mensagem.addInfoMsg("Recebimento registrado com sucesso!");
                        RequestContext.getCurrentInstance().execute("receiveCloseFrame()");
                        Validacao.AtualizarCompoente("recebimentoFormTabela", "growlPrestacao");
                        RequestContext.getCurrentInstance().execute("registradoAmortizacao()");
                        
                        ReciboPagamento rp = new ReciboPagamento();
                        System.out.println("ID prestação "+prestacao.getId());
                        String ret =rp.criarDoc(Integer.valueOf(prestacao.getId()), SessionUtil.getUserlogado().getNomeAcesso());
                        RequestContext.getCurrentInstance().addCallbackParam("estado", "pago");
                        RequestContext.getCurrentInstance().addCallbackParam("imprimir", ret);
                        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                    }
                    else
                    {
                        Mensagem.addErrorMsg(result[1].toString());
                        Validacao.AtualizarCompoente("recebimentoFormTabela", "growlPrestacao");
                        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                    }
                }
           }
           else
                RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
       }
       else
       {
            RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
            Mensagem.addWarningMsg("Tem que estar logado para efetuar a operação!");
            Validacao.AtualizarCompoente("recebimentoFormTabela", "growlPrestacao");     
       }
   }
   
   /**
    * Verifica-se os campos de registro de amortização estão preenchidos
    * @return 
    */
   public boolean validarCamposAmortizacao()
   {
       boolean valido = false;
       if((prestacao.getFormaPagamento() != null && !prestacao.getFormaPagamento().equals("")) && (prestacao.getConta() != null && !prestacao.getConta().equals("")))
       {
           if(!prestacao.getFormaPagamento().equals("1"))
           {
               if(prestacao.getNumDoc()!= null && !prestacao.getNumDoc().equals(""))
                   valido = true;
           }
           else
               valido = true;
       }
       return valido;
   }
   
   public void pesquisarClienteRecebimento()
   {
       System.out.println("valor pesquisa "+pesquisar);
       this.contratoContabilidade = cd.pesquisar(pesquisar);
        Validacao.AtualizarCompoente("recebimentoFormTabela", "tabelaRecebimentoCliente");
//        Validacao.atualizar("recebimentoFormTabela", "tabelaRecebimentoCliente");
   }
   
   public void prestacaoAction()
   {
       if(prestacao.getEstadoPrestacao().contains("Por"))
            RequestContext.getCurrentInstance().execute("mostrarFrameRecebimento()");
   }

   public Pagamento getPagamentoSelecionado() {
        return (pagamentoSelecionado == null)? pagamentoSelecionado = new Pagamento() :pagamentoSelecionado;
    }

   public void setPagamentoSelecionado(Pagamento pagamentoSelecionado) {
        this.pagamentoSelecionado = pagamentoSelecionado;
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
   
   public void editarPagamento(Pagamento p)
   {
       dadosPagamento.remove(p);
       sumTotalDebitoCredito();
       RequestContext.getCurrentInstance().execute("$('.pagamentoNumDoc').val('"+p.getNumDoc()+"'),$('.pagamentoBeneficiario').val('"+p.getBeneficiario()+"')");
       RequestContext.getCurrentInstance().execute("$('.pagamentoValor').val('"+p.getValor()+"')");
       RequestContext.getCurrentInstance().execute("setHasRetensao("+p.isHasRetencion()+")");
       pagamento.setDescricaoPagamento(p.getDescricaoPagamento());  
       Validacao.atualizar("recebimentoForm", "paymentDesc", "pagamentoTabela");
   }
   public List<String>completarListaConta(String info)
   {    
       listaContas.set(0, info);
       return likeStart(this.listaContas, info);   
   }
    
   /**
    * Adiciona dados na tabela de pagamento
    */
   public void pagamentoAdicionarTabela()
   {
       FacesContext facesContext = FacesContext.getCurrentInstance();
       pagamento.setNumDoc(facesContext.getExternalContext().getRequestParameterMap().get("numDoc"));
       pagamento.setTipoPagamento(facesContext.getExternalContext().getRequestParameterMap().get("tipoPagamento"));
       pagamento.setBeneficiario(facesContext.getExternalContext().getRequestParameterMap().get("beneficiário"));
       pagamento.setContaPagamento(facesContext.getExternalContext().getRequestParameterMap().get("contaPagamento"));
       pagamento.setValor(facesContext.getExternalContext().getRequestParameterMap().get("pagamentoValor"));
       pagamento.setHasRetencion(Boolean.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("payHasRentFonte")));
       pagamento.setValor(Validacao.unformat(pagamento.getValor()));
       System.out.println("valor "+pagamento.getValor());
       pagamento.setDescricaoPagamento(facesContext.getExternalContext().getRequestParameterMap().get("pagamentoDesc"));
       pagamento.setContaPagamento(cd.validarContaPagamento(pagamento.getContaPagamento()));
       valorTotal = Double.valueOf(pagamento.getValor());
       
      if(typeMov == 1)
      {
          pagamento.setTypeMoviment(TypeMoviment.DEBITO);
          pagamento.setTyOperationDesc("Débito");
      }
      else
      {
          pagamento.setTypeMoviment(TypeMoviment.CREDITO);
          pagamento.setTyOperationDesc("Crédito");
      }
       
        for(Pagamento p: dadosPagamento)
        {
             valorTotal +=Double.valueOf(p.getValor());
        }
        if(dadosPagamento.isEmpty()) valorTotal = Double.valueOf(pagamento.getValor());
        
       if(pagamento.getContaPagamento() != null) // se aconta de pagamento for válida
       {
            if(pagamento.getTipoPagamento().equals("1"))
            {
                if(valorTotal>1000000)
                {
                    Mensagem.addWarningMsg("Valor total não pode ser superior a 1000000!");
                    Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
                    RequestContext.getCurrentInstance().execute("pagamentoValorSuperior()");
                }
                else
                {
                    this.dadosPagamento.add(new Pagamento(pagamento));
                    sumTotalDebitoCredito();
                    Message.addInfoMsg("Dados adicionados na tabela!", "recebimentoForm", "pagamentoGrowl");
                    Validacao.AtualizarCompoente("recebimentoForm", "pagamentoTabela");
                    Validacao.AtualizarCompoente("recebimentoForm", "pagamentoValorTotal");
                    RequestContext.getCurrentInstance().execute("limparAdicionar()");
                }
            }
            else
            {
                this.dadosPagamento.add(new Pagamento(pagamento));
                sumTotalDebitoCredito();
                Message.addInfoMsg("Dados adicionados na tabela.", "recebimentoForm", "pagamentoGrowl");
                Validacao.AtualizarCompoente("recebimentoForm", "pagamentoTabela");
                Validacao.AtualizarCompoente("recebimentoForm", "pagamentoValorTotal");
                RequestContext.getCurrentInstance().execute("limparAdicionar()");
            }           
       }
       else
       {
            Mensagem.addErrorMsg("Número de Conta de Pagamento não existe.");
            Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
            RequestContext.getCurrentInstance().execute("contaPagamentoInvalido()");
       }   
   }
   
   // Soma todos os valores de crédito e débito adicionados na tabela de registro de pagamento
   public void sumTotalDebitoCredito()
   {
       double sumDebito = 0, sumCredito = 0;
       for(Pagamento p: dadosPagamento)
       {
           if(p.getTypeMoviment() == TypeMoviment.DEBITO) sumDebito += Double.valueOf(p.getValor());
           else sumCredito += Double.valueOf(p.getValor());
       }
       pagamento.setTotalCredito(sumCredito);
       pagamento.setTotalDebito(sumDebito);
        if(pagamento.getTotalDebito() > pagamento.getTotalCredito())
        {
            valorTotal = pagamento.getTotalDebito() - pagamento.getTotalCredito();
            pagamento.setValorTotalFormatado(Moeda.format(valorTotal));
            pagamento.setValorTotal(String.valueOf(valorTotal));
            Validacao.atualizar("recebimentoForm", "pagamentoValorTotal", "pagamentoTabela");
        }
        else
        {
            pagamento.setValorTotalFormatado("");
            Validacao.atualizar("recebimentoForm", "pagamentoValorTotal", "pagamentoTabela");
        }
   }
   // remove o registro selecionado na tabela de registro de pagamento
   public void removerRegistroPagamento(Pagamento payment)
   {
        double sumDebito = 0, sumCredito = 0;
        dadosPagamento.remove(payment);
        valorTotal = 0;
        for(Pagamento p : dadosPagamento)
        {
            valorTotal +=Double.valueOf(p.getValor());
           if(p.getTypeMoviment() == TypeMoviment.DEBITO) sumDebito += Double.valueOf(p.getValor());
           else sumCredito += Double.valueOf(p.getValor());
        }
        pagamento.setTotalCredito(sumCredito);
        pagamento.setTotalDebito(sumDebito);    
        if(pagamento.getTotalDebito() > pagamento.getTotalCredito())
        {
            valorTotal = pagamento.getTotalDebito() - pagamento.getTotalCredito();
            pagamento.setValorTotalFormatado(Moeda.format(valorTotal));
            pagamento.setValorTotal(String.valueOf(valorTotal));
            Validacao.atualizar("recebimentoForm", "pagamentoValorTotal", "pagamentoTabela");
        }
        else
        {
            pagamento.setValorTotalFormatado("");
            Validacao.atualizar("recebimentoForm", "pagamentoValorTotal", "pagamentoTabela");
        }

   }
   
   public void removeAllDataPayment()
   {
       dadosPagamento.clear();
       pagamento.setValorTotal("");
       pagamento.setValorTotalFormatado("");
       valorTotal = 0;
       Validacao.atualizar("recebimentoForm", "pagamentoValorTotal", "pagamentoTabela");
   }
   
   public void paymentMoreInfo(Pagamento pagamento)
   {
       RequestContext.getCurrentInstance().execute("$('.numeroDocPagamento').html('"+pagamento.getNumDoc()+"')");
       RequestContext.getCurrentInstance().execute("$('.benefagamento').html('"+pagamento.getBeneficiario()+"')");
       RequestContext.getCurrentInstance().execute("$('.contaPayment').html('"+pagamento.getContaPagamento()+"')");
       RequestContext.getCurrentInstance().execute("$('.valorPagamento').html('"+pagamento.getValorFormatado()+"')");
       RequestContext.getCurrentInstance().execute("$('.descPagamento').html('"+pagamento.getDescricaoPagamento()+"')");   
       RequestContext.getCurrentInstance().execute("$('.retensaoView').html('"+((pagamento.isHasRetencion()) ? "Sim" : "Nao")+"')");   
   }
   
   public void proximoNumeroPagamento()
   {
       if(pagamento.getFormaPagamento() != null && !pagamento.getFormaPagamento().equals("") && pagamento.getFormaPagamento().equals("35"))
       {
            pagamento.setNumero(cd.proximoCodigoPagamento(Integer.valueOf(pagamento.getTipoPagamento())));
            RequestContext.getCurrentInstance().execute("nextPaymentCod('"+pagamento.getNumero()+"')");
       }
   }
   
   public void verificarSaldoContaBanco()
   {
       double saldoConta = 0;
       
       if(pagamento.getTotalDebito() < pagamento.getTotalCredito())
       {
            Message.addWarningMsg("Valor total de Dédito não pode ser inferior a Crédito.", "recebimentoForm", "pagamentoGrowl");
            RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
       }
       else
       {
            for(ComoBox cb : contasBanco)
            {
                if(cb.getId().equals(pagamento.getContaBanco()))
                {
                    System.out.println("id da conta "+cb.getId());
                    saldoConta = cb.getSaldo();
                    break;
                }
            }
            if(saldoConta > (pagamento.getTotalDebito() - pagamento.getTotalCredito()))
                dadosRegistroPagamento();
            else 
            {
                Message.addWarningMsg("Saldo da conta "+Validacao.comboNome(contasBanco, pagamento.getContaBanco())+" insuficiente.", "recebimentoForm", "pagamentoGrowl");
                RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
            }
       }    
   }
   
   public void dadosRegistroPagamento()
   {
       String result = null;
       String cheque ="";
       if(pagamento.getFormaPagamento().equals("2"))
       {
            cheque = SessionUtil.getUserlogado().getNivelAcesso().equals("0") ? pagamento.getNumero() : numeroCheque+pagamento.getNumero().substring(3, pagamento.getNumero().length());
            pagamento.setCheque(cheque);
            cheque = SessionUtil.getUserlogado().getNivelAcesso().equals("0") ? pagamento.getNumero() : numeroCheque+pagamento.getNumero();
            pagamento.setNumero(cheque);
       }
       else pagamento.setCheque(null);
        
        pagamento.setQuantidade(dadosPagamento.size());
        result = cd.registrarPagamento(pagamento, ((SessionUtil.getUserlogado().getNivelAcesso().equals("0") ? 0 : 1)), cheque).toString();    
        if(result.split(";")[0].equals("true"))
        {
            String id = result.split(";")[1];
            for(Pagamento p : dadosPagamento)
            {
                 result = cd.registrarItemPagamento(p,Integer.valueOf(id),pagamento.getQuantidade()).toString();
            }
            if(result.split(";")[0].equals("true"))
            {
                if(pagamentoSolicitado.equals("Y"))
                {
                    result = cd.regSolicitacaoPagamento(Integer.valueOf(idRequisicaoPagamento), Integer.valueOf(id));
                    if(result.split(";")[0].equals("true"))
                    {
                        result = cd.endPayment(Integer.valueOf(id));
                        if(result.split(";")[0].equals("true")) // last function payment
                            pagamentoSolicitadoRegistrado(id);
                        else
                        {
                            RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                            Mensagem.addErrorMsg(result.split(";")[1]);
                            Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
                        }
                    }
                    else
                    {
                        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                        Mensagem.addErrorMsg(result.split(";")[1]);
                        Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
                        System.out.println("same mistake");
                        System.out.println("same mistake");
                    }
                }
                else
                {
                    result = cd.endPayment(Integer.valueOf(id));
                    if(result.split(";")[0].equals("true")) // last function payment
                        pagamentoRegistrado(id);
                    else
                    {
                        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                        Mensagem.addErrorMsg(result.split(";")[1]);
                        Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
                    }
                }
              
            }
            else
            {
                RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                Mensagem.addErrorMsg(result.split(";")[1]);
                Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
            }
        }
        else
        {
            RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
             Mensagem.addErrorMsg(result.split(";")[1]);
             Validacao.AtualizarCompoente("recebimentoForm", "pagamentoGrowl");
        }       

   }
   
   public void pagamentoRegistrado(String id)
   {
         RequestContext.getCurrentInstance().execute("$('.totalPagamento').html(' ')");
        FuncPagamento fp = new FuncPagamento();
        String ret = fp.criarDoc(SessionUtil.getUserlogado().getNomeAcesso(), id,
        SessionUtil.getUserlogado().getNome()+" "+SessionUtil.getUserlogado().getApelido(), ((pagamento.getTipoPagamento().equals("2")) ? 2 : 1));        
        this.dadosPagamento.clear();
        pagamento.setValorTotal("");
        this.valorTotal = 0;
        pagamento.setIdPagamento(cd.proximoCodigoPagamento(Integer.valueOf(pagamento.getTipoPagamento())));
        Validacao.atualizar("recebimentoForm", "pagamentoTabela", "numeroPagamento", "pagamentoValorTotal");
        RequestContext.getCurrentInstance().execute("pagamentoEfetuado()");
//        contasBanco = ComoBox.loadCombo("VER_CONTABOX", "ID", "CONTA");
        pagamento.setContaBanco("");
        Validacao.AtualizarCompoente("recebimentoForm", "tipoContaBanco");
        pagamento.setTipoPagamento("2");
        Validacao.AtualizarCompoente("recebimentoForm", "elect-payment-type");
        this.listaPagamentos = cd.listaPagamentos(null,"total", pagamento.getFiltroPagamento());
        Validacao.AtualizarCompoente("recebimentoForm", "paymentTable");   
        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
        RequestContext.getCurrentInstance().execute("openAllDocument('"+ret+"')");
   }
   public void pagamentoSolicitadoRegistrado(String id)
   {
        pagamentoSolicitados = SinistroDao.listaPagamentosSolicitados();
        RequestContext.getCurrentInstance().execute("$('.totalNotif').html('"+((pagamentoSolicitados.isEmpty())? "": pagamentoSolicitados.size()) +"')");
        RequestContext.getCurrentInstance().update("formNotification");

        System.out.println("enviando nova notificação...");
        EventBus eventBus =EventBusFactory.getDefault().eventBus();
        eventBus.publish("/notify", "atualizar solicitação de pagamento");
        RequestContext.getCurrentInstance().execute("$('.totalPagamento').html(' ')");
        FuncPagamento fp = new FuncPagamento();
        String ret = fp.criarDoc(SessionUtil.getUserlogado().getNomeAcesso(), id,
        SessionUtil.getUserlogado().getNome()+" "+SessionUtil.getUserlogado().getApelido(), ((pagamento.getTipoPagamento().equals("2")) ? 2 : 1));        
        this.dadosPagamento.clear();
        pagamento.setValorTotal("");
        this.valorTotal = 0;
        pagamento.setIdPagamento(cd.proximoCodigoPagamento(Integer.valueOf(pagamento.getTipoPagamento())));
        Validacao.atualizar("recebimentoForm", "pagamentoTabela", "numeroPagamento", "pagamentoValorTotal");
        RequestContext.getCurrentInstance().execute("pagamentoEfetuado()");
//        contasBanco = ComoBox.loadCombo("VER_CONTABOX", "ID", "CONTA");
        pagamento.setContaBanco("");
        Validacao.AtualizarCompoente("recebimentoForm", "tipoContaBanco");
        pagamento.setTipoPagamento("2");
        Validacao.AtualizarCompoente("recebimentoForm", "elect-payment-type");
        this.listaPagamentos = cd.listaPagamentos(null,"total", pagamento.getFiltroPagamento());
        Validacao.AtualizarCompoente("recebimentoForm", "paymentTable");   
        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
        RequestContext.getCurrentInstance().execute("openAllDocument('"+ret+"')");
   }
   public void registrarPagamento()
   {
       String result;
       String data;
       String [] info;
  
       FacesContext facesContext = FacesContext.getCurrentInstance();
       pagamento.setTipoPagamento(facesContext.getExternalContext().getRequestParameterMap().get("tipoPagamento"));
       pagamento.setContaBanco(facesContext.getExternalContext().getRequestParameterMap().get("contaBanco"));
       pagamento.setFormaPagamento(facesContext.getExternalContext().getRequestParameterMap().get("formaPagamento"));
       pagamento.setNumero(facesContext.getExternalContext().getRequestParameterMap().get("numero"));
       data = facesContext.getExternalContext().getRequestParameterMap().get("dataPagamento");
       pagamentoSolicitado = facesContext.getExternalContext().getRequestParameterMap().get("pagamentoSolicitado");
       idRequisicaoPagamento = facesContext.getExternalContext().getRequestParameterMap().get("ID REQUISICAO PAGAMENTO");
       pagamento.setDataPagamento(OperacaoData.stringFormatToDate(data, "dd-MM-yyyy"));
       
       if(dadosPagamento.size()>0)
       {
           if(pagamento.getFormaPagamento().equals("2"))
           {
               if(pagamento.getNumero().length() == 10)
                    verificarSaldoContaBanco();
               else
                {
                     RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
                     Message.addErrorMsg("Número de cheque deve ter dez(digitos)!", "recebimentoForm", "pagamentoGrowl");
                }   
           }
           else verificarSaldoContaBanco();
       }
       else
       {
            RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
            Message.addWarningMsg("Informe dados de pagamento.","recebimentoForm", "pagamentoGrowl");
            RequestContext.getCurrentInstance().execute("pagamentoNumeroDocumentoFoco()");
       }
   }
   
   public void contaCarregar()
   {
//       if(pagamento.getTipoPagamento().equals("1"))
//            contasBanco = ComoBox.loadCombo("VER_CONTA_CAIXA","ID", "CONTA");
//       else
//       {
//           contasBanco = ComoBox.loadCombo("VER_CONTABOX", "ID", "CONTA");
//       }
       Validacao.AtualizarCompoente("recebimentoForm", "tipoContaBanco");
   }

   public String getInfoAccount() {
        return infoAccount;
    }
   /**
    * Apresenta a descricção da conta selecionada no registro de recebimento
    */
 
    public void descricaoConta()
   {
       if(prestacao.getConta() != null && !prestacao.getConta().equals(""))
       {
           for(ComoBox cb :this.contas)
           {
               if(cb.getId().equals(prestacao.getConta()))
               {
                   RequestContext.getCurrentInstance().execute("tituloConta('"+cb.getValue()+"')");
                   break;
               }
           }
       }
           
   }

   public void accountInfo()
   {
       String result = null;
       if(pagamento.getContaPagamento() != null && !pagamento.getContaPagamento().equals(""))
       {
           result = cd.infoConta(pagamento.getContaPagamento());
           if(result == null)
           {
               this.infoAccount ="Número de conta não existe!";
               Validacao.AtualizarCompoente("recebimentoForm", "infoAccount");
           }
           else
           {
               this.infoAccount =result;
               Validacao.AtualizarCompoente("recebimentoForm", "infoAccount");
           }
       }
       else
       {
           this.infoAccount ="Account information here!";
            Validacao.AtualizarCompoente("recebimentoForm", "infoAccount");
       }
   }
   
   public void pesquisarPagamento()
   {
       this.listaPagamentos = cd.listaPagamentos(pesquisaPagamento,"total", pagamento.getFiltroPagamento());
       Validacao.AtualizarCompoente("recebimentoForm", "paymentTable");
   }
   
   public void loadContractNoPay()
   {
       if(tamanhoLista !=null && tamanhoLista.equals("metade"))
       {
            if(pesquisar == null || pesquisar.equals(""))
            {
                this.contratoContabilidade = cd.contratoClienteContabilidade("total", recebimento.getFiltroRecebimento());
                tamanhoLista = "total";
                Validacao.AtualizarCompoente("recebimentoFormTabela","tabelaRecebimentoCliente");
            }
       }
   }

    public String getPesquisaPagamento() {
        return pesquisaPagamento;
    }

   public void setPesquisaPagamento(String pesquisaPagamento) {
        this.pesquisaPagamento = pesquisaPagamento;
    }
   
   public void loadAllPayments()
   {
       if(tamanhoListaPagamento !=null && tamanhoListaPagamento.equals("metade"))
       {
            if(pesquisaPagamento == null || pesquisaPagamento.equals(""))
            {
                this.listaPagamentos = cd.listaPagamentos(null, "total",pagamento.getFiltroPagamento());
                tamanhoListaPagamento = "total";
                Validacao.AtualizarCompoente("recebimentoForm","paymentTable");
            }
       }
   }
   
   public void impressaoPagamento(Pagamento p)
   {
        this.pagamento = p;
        FuncPagamento fp = new FuncPagamento();
        System.out.println(pagamento);
        String ret = fp.criarDoc(SessionUtil.getUserlogado().getNomeAcesso(), pagamento.getIdPagamento(),
        SessionUtil.getUserlogado().getNome()+" "+SessionUtil.getUserlogado().getApelido(), ((pagamento.getTipoPagamento().equalsIgnoreCase("folha de pagamento")) ? 2 :1 ));
        RequestContext.getCurrentInstance().addCallbackParam("estado", "pago");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir", ret);
   }
   
   public void pagamentoS(Pagamento p)
   {
       this.pagamento = p;
   }
   
   public void anularPagamento()
   {
       String result = cd.anularPagamento(Integer.valueOf(pagamento.getIdPagamento()), obs);
       if(result.split(";")[0].equals("true"))
       {
            this.listaPagamentos = cd.listaPagamentos(null,"total", pagamento.getFiltroPagamento());
            Validacao.AtualizarCompoente("recebimentoForm", "paymentTable");   
           RequestContext.getCurrentInstance().execute("$('.anularPagamento').fadeOut(),$('.obsAnular').val('')");
           Message.addInfoMsg("Pagamento anulado com sucesso", "formModalAnularPagamento", "growlAnularPagamento");
       }
       else
           Message.addErrorMsg(result.split(";")[1], "formModalAnularPagamento", "growlAnularPagamento");
   }
   
   public void filtrarPagamento(String filtro)
   {
        pagamento.setFiltroPagamento(filtro);
        this.listaPagamentos = cd.listaPagamentos(null,"total", pagamento.getFiltroPagamento());
        Validacao.AtualizarCompoente("recebimentoForm", "paymentTable");   
   }
   
   public void filtrarRecebimento(String filtro)
   {
        recebimento.setFiltroRecebimento(filtro);
        contratoContabilidade = cd.contratoClienteContabilidade("total", recebimento.getFiltroRecebimento());
        Validacao.AtualizarCompoente("recebimentoFormTabela", "tabelaRecebimentoCliente");
   }

    public List<Pagamento> getPagamentoSolicitados() {
        return pagamentoSolicitados;
    }
   
    public void solicitarPagamentoMaisInfo(Pagamento pagamento)
    {
        double valor = Double.valueOf(pagamento.getValor());
        pagamento.setDescricaoPagamento(pagamento.getDescricaoPagamento());
        Validacao.atualizar("recebimentoForm", "paymentDesc");
        String numSinistro = pagamento.getNumeroSinistro()!= null && !pagamento.getNumeroSinistro().equals("") ? pagamento.getNumeroSinistro() : "";
        RequestContext.getCurrentInstance().execute("$('.benef').html('"+pagamento.getBeneficiario()+"')");
        RequestContext.getCurrentInstance().execute("$('.idPagamento').html('"+pagamento.getIdPagamento()+"')");
        RequestContext.getCurrentInstance().execute("$('.numeroSinistro').html('"+numSinistro+"')");
        RequestContext.getCurrentInstance().execute("$('.pagamentoApolice').html('"+pagamento.getApolice()+"')");
        RequestContext.getCurrentInstance().execute("$('.pagamentoNotaDebito').html('"+pagamento.getNotaDebito()+"')");
        RequestContext.getCurrentInstance().execute("$('.solicitarPagamentoValor').html('"+Moeda.format(valor)+"')");  
        RequestContext.getCurrentInstance().execute("$('.pagamentoUtilizador').html('"+pagamento.getUtilizador()+"')");  
        RequestContext.getCurrentInstance().execute("$('.solicitarPagamentoDataPagamento').html('"+pagamento.getSolicitarPagamentoData()+"')");  
        RequestContext.getCurrentInstance().execute("$('.solicitarPagamentoDataRegistro').html('"+pagamento.getDataRegistro()+"')");  
        RequestContext.getCurrentInstance().execute("$('.maisInfoSolicitarPagamento').fadeIn()");      
    }
    
   public void solicitarPagamento(Pagamento p)
   {
        pagamento = p;
        pagamento.setDescricaoPagamento(pagamento.getDescricaoPagamento());
        Validacao.atualizar("recebimentoForm", "paymentDesc");
        RequestContext.getCurrentInstance().execute("solicitarPagamento('"+pagamento.getBeneficiario()+"','"+pagamento.getValor()+"','"+pagamento.getSolicitarPagamentoData()+"','"+pagamento.getIdPagamento()+"')"); 
   }
   
   public void notificationPayment()
    {  
        pagamentoSolicitados = SinistroDao.listaPagamentosSolicitados();       
       RequestContext.getCurrentInstance().execute("$('.totalNotif').html('"+((pagamentoSolicitados.isEmpty())? "": pagamentoSolicitados.size()) +"')");
    }
   
   public void setTypeOperationPayment(int type)// define o tipo de movimento a registrar pagamento
   {
       typeMov = type;
   }
   

}
