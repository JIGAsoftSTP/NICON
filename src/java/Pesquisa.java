
import Export.CertificadoCargaMaritima;
import Export.CertificadoMaritimo;
import Export.CertificadoViatura;
import Export.ConfigDoc;
import Export.DocNotaCredito;
import Export.DocNotaDebito;
import Export.MarcaDAgua;
import Export.SeguroViagem;
import Export.SeguroAPG;
import Export.SeguroCargaMaritima;
import Export.SeguroCoberturaViagem;
import Export.SeguroIncendio;
import Export.SeguroMaritimo;
import Export.SeguroRoubo;
import Export.SeguroViatura;
import bean.AcidentePGBean;
import bean.CargaMaritimaBean;
import bean.DataTableControl;
import bean.IncendioBean;
import bean.MaritimoBean;
import bean.ResseguroBean;
import bean.RouboBean;
import bean.VeiculoBean;
import bean.ViagemBean;
import conexao.Call;
import java.io.Serializable;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import conexao.EstadoConnexao;
import dao.ClienteDao;
import dao.ContratoDao;
import dao.SeguroDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.AcidentePG;
import modelo.CargaMaritima;
import modelo.Cliente;
import modelo.ComoBox;
import modelo.Contrato;
import modelo.DadosContrato;
import modelo.Funcionario;
import modelo.Incendio;
import modelo.IncendioRisco;
import modelo.Maritimo;
import modelo.NotaDebito;
import modelo.Roubo;
import modelo.Veiculo;
import modelo.Viagem;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Servidor
 */
@ManagedBean
@ViewScoped
public class Pesquisa implements Serializable {

    private DataTableControl resultado;
    private String tipoPesquisa = "MV";
    private String chavePesquisa;
    public String[] colum;
    private String justificacao;
    private Cliente cliente = new Cliente();
    private SeguroDao seguroDao;
    private String idContrato;
    private String header;
    private String selectdColuna;
    private DataTableControl dadosClientes;
    private String keyQuere;
    private String seletedColuna;
    private String selectedeItemComb;
    private ResultSet rs;
    private List<Cliente> listaClientes;
    private ClienteDao clienteDao;
    private DocNotaDebito debito = new DocNotaDebito();
    private String ImpresaoForm;
    private String ImpresaoNoDeb;
    private String ImpresaoCert;
    private Date dataInicioSu;
    private Date dataFimSu;
    private int totalRegistro;
    private final ContratoDao contratoDao = new ContratoDao();
    private DadosContrato dadosContrato = new DadosContrato();
    private List<DadosContrato> listDadosContratos;
    private Date dataMinima;
    private NotaDebito notaDebito = new NotaDebito();
    
    @ManagedProperty(value = "#{resseguro}")
    private ResseguroBean resseguroBean;

    public Pesquisa() {
        clienteDao = new ClienteDao();
        listaClientes = new ArrayList<>();
        listaClientes = clienteDao.filtrarPesquisa(null, null, "metade", false, 0);
        seguroDao = new SeguroDao();
        this.resultado = new DataTableControl("GestaoSeguroContrato_ListaContratos", "pesquisa.resultado");
        listDadosContratos = contratoDao.pesquisarContrato("MV", null, null);
        RequestContext.getCurrentInstance().execute("$('.totalRegistro').html('" + listDadosContratos.size() + "')");
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public String getSelectdColuna() {
        return selectdColuna;
    }

    public void setSelectdColuna(String selectdColuna) {
        this.selectdColuna = selectdColuna;
    }

    public String getJustificacao() {
        return justificacao;
    }

    public void setJustificacao(String justificacao) {
        this.justificacao = justificacao;
    }

    public NotaDebito getNotaDebito() {
        return (notaDebito == null)?  notaDebito = new NotaDebito() : notaDebito;
    }

    public void setNotaDebito(NotaDebito notaDebito) {
        this.notaDebito = notaDebito;
    }

    public ResseguroBean getResseguroBean() {
        return resseguroBean;
    }

    public void setResseguroBean(ResseguroBean resseguroBean) {
        this.resseguroBean = resseguroBean;
    }

    public String getImpresaoForm() {
        return ImpresaoForm;
    }

    public void setImpresaoForm(String ImpresaoForm) {
        this.ImpresaoForm = ImpresaoForm;
    }

    public String getImpresaoNoDeb() {
        return ImpresaoNoDeb;
    }

    public List<DadosContrato> getListDadosContratos() {
        return listDadosContratos;
    }

    public int getTotalRegistro() {
        return totalRegistro;
    }

    public void setImpresaoNoDeb(String ImpresaoNoDeb) {
        this.ImpresaoNoDeb = ImpresaoNoDeb;
    }

    public String getImpresaoCert() {
        return ImpresaoCert;
    }

    public void setImpresaoCert(String ImpresaoCert) {
        this.ImpresaoCert = ImpresaoCert;
    }

    public String getChavePesquisa() {
        return chavePesquisa;
    }

    public DadosContrato getDadosContrato() {
        return (dadosContrato == null) ? dadosContrato = new DadosContrato() : dadosContrato;
    }

    public void setDadosContrato(DadosContrato dadosContrato) {
        this.dadosContrato = dadosContrato;
    }

    public void setChavePesquisa(String chavePesquisa) {
        this.chavePesquisa = chavePesquisa;
    }

    public String getIdContrato() {
        return idContrato;
    }

    public String getHeader() {
        return header;
    }

    public Date getDataInicioSu() {
        return dataInicioSu;
    }

    public void setDataInicioSu(Date dataInicioSu) {
        this.dataInicioSu = dataInicioSu;
    }

    public Date getDataFimSu() {
        return dataFimSu;
    }

    public void setDataFimSu(Date dataFimSu) {
        this.dataFimSu = dataFimSu;
    }

    public void operacao() {
        String resultado1 = null;
        switch (header) {
            case "Suspender Contrato": {
                if (dataInicioSu != null) {
                    RequestContext.getCurrentInstance().execute("dataSupenValide(-1)");
                    if (dataFimSu != null) {
                        RequestContext.getCurrentInstance().execute("dataSupenValide(-2)");
                        if (justificacao != null && justificacao.length() > 0) {
                            RequestContext.getCurrentInstance().execute("dataSupenValide(-3)");
                            resultado1 = seguroDao.AnularContrato(Integer.parseInt(dadosContrato.getId()),
                                    "2", justificacao, dataInicioSu, dataFimSu);
                            if (resultado1 != null && resultado1.equals("true")) {
                                Mensagem.addInfoMsg("Contrato suspenso com êxito!");
                                Validacao.AtualizarCompoente("resist", "GestaoContratoGrowl");
                                RequestContext.getCurrentInstance().execute("contrato('fechar')");
                                System.out.println("tipo de seguro " + tipoPesquisa);
                                listDadosContratos = contratoDao.pesquisarContrato(tipoPesquisa, null, null);
                                totalRegistro = listDadosContratos.size();
                                Validacao.atualizar("resist", "GestaoSeguroContrato_ListaContratos", "contratoTotalRegistro");
                                if(dadosContrato.getEstadoPagamento() == 0) // pagamento efetuado
                                {
                                    notaDebito.setIdContrato(Integer.valueOf(dadosContrato.getId()));
                                    RequestContext.getCurrentInstance().execute("$('.notaCreditoSeguro').html('"+typeInsurance(dadosContrato.getSeguro())+"'),"
                                            + "$('.notaCreditoSegurado').html('"+dadosContrato.getCliente()+"'),"
                                            + "$('.notaCreditoApolice').html('"+dadosContrato.getApolice()+"'), $('.notaCreditoPeriodo').html('"+dadosContrato.getDataContrato()+"')");
                                    RequestContext.getCurrentInstance().execute("$('.nota-credit').show('.nota-credit').slideDown(300)");
                                }
                                
                            } else {
                                Mensagem.addErrorMsg("Esse contrato já está suspenso!");
                                Validacao.AtualizarCompoente("resist", "GestaoContratoGrowl");
                            }
                        } else {
                            RequestContext.getCurrentInstance().execute("dataSupenValide(3)");
                        }
                    } else {
                        RequestContext.getCurrentInstance().execute("dataSupenValide(2)");
                    }
                } else {
                    RequestContext.getCurrentInstance().execute("dataSupenValide(1)");
                }
            }
            break;
            case "Anular Contrato": {
                if (justificacao != null && justificacao.length() > 0) {
                    RequestContext.getCurrentInstance().execute("dataSupenValide(-3)");
                    resultado1 = seguroDao.AnularContrato(Integer.parseInt(dadosContrato.getId()),
                            "-1", justificacao, null, null);
                    if (resultado1 != null && resultado1.equals("true")) {
                        Mensagem.addInfoMsg("Contrato anulado com êxito!");
                        Validacao.AtualizarCompoente("resist", "GestaoContratoGrowl");
                        RequestContext.getCurrentInstance().execute("contrato('fechar')");
                        listDadosContratos = contratoDao.pesquisarContrato(tipoPesquisa, null, null);
                        totalRegistro = listDadosContratos.size();
                        Validacao.atualizar("resist", "GestaoSeguroContrato_ListaContratos", "contratoTotalRegistro");
                        if(dadosContrato.getEstadoPagamento() == 0) // pagamento efetuado
                        {
                            notaDebito.setIdContrato(Integer.valueOf(dadosContrato.getId()));
                                RequestContext.getCurrentInstance().execute("$('.notaCreditoSeguro').html('"+typeInsurance(dadosContrato.getSeguro())+"'),"
                                        + "$('.notaCreditoSegurado').html('"+dadosContrato.getCliente()+"'),"
                                        + "$('.notaCreditoApolice').html('"+dadosContrato.getApolice()+"'), $('.notaCreditoPeriodo').html('"+dadosContrato.getDataContrato()+"')");
                                RequestContext.getCurrentInstance().execute("$('.nota-credit').show('.nota-credit').slideDown(300)");
                        }
                    } else {
                        Mensagem.addErrorMsg("Esse contrato já está anulado!");
                        Validacao.AtualizarCompoente("resist", "GestaoContratoGrowl");
                    }

                } else {
                    RequestContext.getCurrentInstance().execute("dataSupenValide(3)");
                }
            }
            break;
            case "Alterar Data Inicio Contrato":
                if (dataInicioSu != null && (!justificacao.equals("") && justificacao != null)) {
                    resultado1 = seguroDao.AnularContrato(Integer.parseInt(dadosContrato.getId()),
                            "3", justificacao, dataInicioSu, null);
                    if (resultado1 != null && resultado1.equals("true")) {
                        Mensagem.addInfoMsg("Data de inicio de contrato alterado com êxito!");
                        Validacao.AtualizarCompoente("resist", "GestaoContratoGrowl");
                        RequestContext.getCurrentInstance().execute("contrato('fechar'),ativarCampo()");
                        listDadosContratos = contratoDao.pesquisarContrato(tipoPesquisa, null, null);
                        totalRegistro = listDadosContratos.size();
                        Validacao.atualizar("resist", "GestaoSeguroContrato_ListaContratos", "contratoTotalRegistro");
                    } else {
                        Mensagem.addErrorMsg(resultado1);
                        Validacao.AtualizarCompoente("resist", "GestaoContratoGrowl");
                    }
                }
                break;
        }

    }

    public void atualizarDataFim() {
        if (header.contains("Suspender")) {
            Validacao.atualizar("resist", "dataFimSup");
        }
    }

    public Cliente getCliente() {
        return (cliente == null) ? cliente = new Cliente() : cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void alterarDataInicioContrato() {
        header = "Alterar Data Inicio Contrato";
        justificacao = "";
        Validacao.AtualizarCompoente("resist", "contratoJustificacao");
        if (dadosContrato.getId() == null || dadosContrato.getId().equals("")) {
            Message.addWarningMsg("Selecione um registro", "resist", "GestaoContratoGrowl");
        } else {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            try {
                dataMinima = df.parse(dadosContrato.getDataInicio());
            } catch (ParseException ex) {
                Logger.getLogger(Pesquisa.class.getName()).log(Level.SEVERE, null, ex);
            }
            justificacao = "";
            Validacao.atualizar("resist", "modalcontrato", "contratoJustificacao", "dataIniSup", "contratoId");
            idContrato = dadosContrato.getApolice();
            RequestContext.getCurrentInstance().execute("contrato('Alterar Data Inicio Contrato')");
        }
    }

    public void anularContrato() {
        header = "Anular Contrato";
        justificacao = "";
        Validacao.AtualizarCompoente("resist", "contratoJustificacao");
        if (dadosContrato.getId() == null || dadosContrato.getId().equals("")) {
            Message.addWarningMsg("Selecione um registro", "resist", "GestaoContratoGrowl");
        } else {
            justificacao = "";
            Validacao.AtualizarCompoente("resist", "modalcontrato");
            Validacao.AtualizarCompoente("resist", "contratoJustificacao");
            idContrato = dadosContrato.getApolice();
            Validacao.AtualizarCompoente("resist", "contratoId");
            RequestContext.getCurrentInstance().execute("contrato('Anular Contrato')");
        }
    }

    public void suspenderContrato() {
        header = "Suspender Contrato";
        justificacao = "";
        Validacao.AtualizarCompoente("resist", "contratoJustificacao");
        if (dadosContrato.getId() == null || dadosContrato.getId().equals("")) {
            Message.addWarningMsg("Selecione um registro", "resist", "GestaoContratoGrowl");
        } else {
            dataMinima = new Date();
            justificacao = "";
            dataFimSu = null;
            dataInicioSu = null;
            Validacao.atualizar("resist", "modalcontrato", "contratoJustificacao", "dataIniSup", "contratoId");
            idContrato = dadosContrato.getApolice();
            RequestContext.getCurrentInstance().execute("contrato('Suspender Contrato')");
        }
    }

    /**
     * Classe Dao
     *
     * @return
     */
    public String getTipoPesquisa() {
        return tipoPesquisa;
    }

    public Date getDataMinima() {
        return dataMinima;
    }

    public void setTipoPesquisa(String tipoPesquisa) {
        this.tipoPesquisa = tipoPesquisa;
        if (this.tipoPesquisa.equals("ANULADOS")) {
            RequestContext.getCurrentInstance().execute("$('.contratoAnuladoOcultarBotao').css('display','none')");
            MarcaDAgua.isCanceled = true;
        }
        else {
            RequestContext.getCurrentInstance().execute("$('.contratoAnuladoOcultarBotao').css('display','')");
            MarcaDAgua.isCanceled = false;
        }
        
        listDadosContratos = contratoDao.pesquisarContrato(this.tipoPesquisa, null, null);
        RequestContext.getCurrentInstance().execute("$('.totalRegistro').html('" + listDadosContratos.size() + "')");
    }

    public void loadOutros(String type) {

        Integer numRegistro = type.equalsIgnoreCase("GOLD") ? 10 : null;
        if (EstadoConnexao.isValid) {
            ResultSet rs = Call.callTableFunction("PACK_PESQUISA.pesqContratoGold", "*", numRegistro);
            this.geniriquiPesquisa(rs, "GOLD");
        }
    }

    public String[] getColum() {
        return colum;
    }

    public void pesquisarCampo() {
        listDadosContratos = contratoDao.pesquisarContrato(tipoPesquisa, selectdColuna, chavePesquisa);
        Validacao.AtualizarCompoente("resist", "GestaoSeguroContrato_ListaContratos");
        RequestContext.getCurrentInstance().execute("$('.totalRegistro').html('" + listDadosContratos.size() + "')");

    }

    private void geniriquiPesquisa(ResultSet rs, String tipo)//String funcao, String tipo, String campo, Object value)
    {
        this.resultado.updFaces(FacesContext.getCurrentInstance());

        if (tipo.equals("SEGURO")) {
            this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "ID CLIENTE",
                    "ID", "SEGURO", "LIQUIDOSF", "PREMIO LIQUIDO", "DATA FIM", "DATA INICIO", "DATA");
        } else {
            this.resultado.prepareModel(rs, DataTableControl.ShowMode.HIDE, "ID CLIENTE", "ID", "LIQUIDOSF", "PREMIO LIQUIDO", "DATA FIM", "DATA INICIO", "DATA");
        }

    }

    public void reDoDucument() {
        ContratoDao cd = new ContratoDao();
        Object[] c = cd.carregarUmContracto(Integer.valueOf(dadosContrato.getId()));

        System.err.println(c[3].toString() + " Codi Cliente");

        switch (dadosContrato.getSeguro()) {
            case "TIN":
                reDoDocViagem(c, cd);
                break;
            case "GPA":
                reDoDocAPg(c, cd);
                break;
            case "NHI":
                reDoDocMaritimo(c, cd);
                break;
            case "MV":
                reDoDocVeiculo(c, cd);
                break;
            case "DI":
                reDoDocRoubo(c, cd);
                break;
            case "FR":
                reDoDocIncendio(c, cd);
                break;
            case "MAC":
                reDoDocCargaM(c, cd);
                break;
            case "DH":

                break;
            case "RP":

                break;
        }

    }

    private void reDoDocVeiculo(Object[] cO, ContratoDao cd) {

        VeiculoBean veiculoBean = new VeiculoBean();
        Veiculo v = new Veiculo();

        v.setNumeroApolice(cO[1].toString());
        v.setNumeroRegistro(cO[2].toString());

        veiculoBean.setVeiculo(v);
        Contrato c = (Contrato) cO[0];

        veiculoBean.setInfo(cd.loadDataContratoVeiculo(Integer.valueOf(dadosContrato.getId()), "MV"));
        
        if(veiculoBean.getInfo().size()>0)
            veiculoBean.getVeiculo().setTipoCobertura(veiculoBean.getInfo().get(0).getTipoCobertura());

        SeguroViatura sv = new SeguroViatura();
        String dd = sv.criarDoc(veiculoBean.getVeiculo().getNumeroApolice(), cO[3].toString(),
                c, veiculoBean, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio());
        ImpresaoForm = dd;
//        RequestContext.getCurrentInstance().execute("openAllDocument('" + ImpresaoForm + "')");
        RequestContext.getCurrentInstance().addCallbackParam("seguro", "veiculo");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir1", ImpresaoForm);

        ArrayList<String[]> al = new ArrayList<>();

        //------------
        String[] dados = null;
        dados = (veiculoBean.getInfo().size() + ";" + "Seguro de Automovel\n" + c.getObservacao() + ";" + Moeda.format((Double.valueOf(((c.getPremioBruto() == null || c.getPremioBruto().isEmpty()) ? "0" : c.getPremioBruto())))) + ";" + c.getPremioBrutoMoeda()).split(";");
        al.add(dados);
        String reString = debito.docSeguros("Automovel", veiculoBean.getVeiculo().getNumeroApolice(), cO[4] + "", cO[3].toString(),
                "FGA", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio(), veiculoBean.getVeiculo().getNumeroRegistro());

        ImpresaoNoDeb = reString;
//        RequestContext.getCurrentInstance().execute("openAllDocument('" + ImpresaoNoDeb + "')");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir2", ImpresaoNoDeb);

        CertificadoViatura cv = new CertificadoViatura();
        ImpresaoCert = cv.criarDoc(veiculoBean.getVeiculo().getNumeroApolice(), cO[3].toString(), c, veiculoBean,
                (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio());

//        RequestContext.getCurrentInstance().execute("openAllDocument('" + ImpresaoCert + "')");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir3", ImpresaoCert);
    }

    public void reDoDocViagem(Object[] cO, ContratoDao cd)  {
        ViagemBean viagemBean = new ViagemBean();
        Object[] os = cd.loadDataContratoViagem(Integer.valueOf(dadosContrato.getId()), "TIN");
        
        Viagem v;

        v = (Viagem) os[1];
        v.setNumApolice(cO[1].toString());
        v.setNumeroRegistro(cO[2].toString());
        viagemBean.setDia(v.getDias());
        viagemBean.setInfoPessoaSegurada((List<Viagem>) os[0]);
        viagemBean.setViagem(v);

        Contrato c = (Contrato) cO[0];

        c.setValorNC(viagemBean.getViagem().getNiconComissao());
        
        ArrayList<String[]> al = new ArrayList<>();
        double total = 0;
        String[] dados = new String[4];
        
        for (int i = 0; i < viagemBean.getInfoPessoaSegurada().size(); i++) { 
           try {
               
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
                Viagem vd = cd.obterValoresSegurado(viagemBean.getInfoPessoaSegurada().get(i).getDataInicio(), viagemBean.getInfoPessoaSegurada().get(i).getDataFim(), (( viagemBean.getInfoPessoaSegurada().get(i).isTipoViagem()== true)? 2 : 1)).get(0);
                viagemBean.getInfoPessoaSegurada().get(i).setNumDias(vd.getNumDias());
                
                String nome = viagemBean.getInfoPessoaSegurada().get(i).getNomePessoaSegurada();
                String apolice = viagemBean.getInfoPessoaSegurada().get(i).getNumApolice();
                String valor;
                       
                if (OperacaoData.DataNascimentoSuperior(sdf.parse(viagemBean.getInfoPessoaSegurada().get(i).getDataNascFormatada())) >= 75 )
                { 
                    valor = Moeda.format(Moeda.arrendodamento(Double.toString(vd.getValorPremio())));
                    total += Moeda.arrendodamento(Double.toString(vd.getNc())) * 2;
                }
                else
                {   
                    valor = Moeda.format(Moeda.arrendodamento(Double.toString(vd.getValorPremio())));
                    total += Moeda.arrendodamento(Double.toString(vd.getNc())); 
                }
                
                dados[0] = " ";
                dados[1] = nome +" --- "+apolice;
                dados[2] = valor +" "+ c.getSigla();
                dados[3] = "";
                
                al.add(dados);
                dados = new String[4];
            } 
            catch (ParseException ex) {  Logger.getLogger(Pesquisa.class.getName()).log(Level.SEVERE, null, ex); }
        }
        
        SeguroViagem dw = new SeguroViagem();
        String dd = dw.criarDoc(viagemBean, cO[3].toString(), (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio(), c);
        ImpresaoForm = dd;
//        RequestContext.getCurrentInstance().execute("openAllDocument('" + ImpresaoForm + "')");
        RequestContext.getCurrentInstance().addCallbackParam("seguro", "viagem");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir1", ImpresaoForm);
        
        c.setValorNC(Double.toString(total));
        
        //------------
//        c.setValorNC(Double.toString(Moeda.arrendodamento(ViagemBean.calNCWhit(Double.valueOf(c.getPremioLiquido()), Double.valueOf(c.getPremioBruto())))));
 
        dados = (viagemBean.getInfoPessoaSegurada().size() + ";"+ "Seguro de Viagem\n"+c.getObservacao() + ";" +" "+ ";" + c.getPremioBrutoMoeda()).split(";");
        al.add(dados);
        String reString = debito.docSeguros("Viagem", viagemBean.getViagem().getNumApolice(), cO[4] + "", cO[3].toString(), "NC", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio(), viagemBean.getViagem().getNumeroRegistro());

        ImpresaoNoDeb = reString;
//        RequestContext.getCurrentInstance().execute("openAllDocument('" + ImpresaoNoDeb + "')");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir2", ImpresaoNoDeb);
        
        SeguroCoberturaViagem.criarDoc(viagemBean, cO[3].toString(), (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio(), c);
    }

    public void reDoDocAPg(Object[] cO, ContratoDao cd) {
        Double premioMorteMoeda = 0.0, premioDespesaMedicaMoeda = 0.0, premioIncapacidadeTemporariaMoeda = 0.0,
                premioIncapacidadePermanenteMoeda = 0.0, premioCustoRepatriamentoMoeda = 0.0;

        AcidentePGBean acidentePGBean = new AcidentePGBean();
        ArrayList<AcidentePG> os = cd.loadDataContratoAPG(Integer.valueOf(dadosContrato.getId()), "GPA");
        AcidentePG pG;

        pG = new AcidentePG();

        pG.setNumeroApolice(cO[1].toString());
        pG.setNumeroRegistro(cO[2].toString());

        acidentePGBean.setInfo(os);
        for (AcidentePG o : os) {
            
            premioMorteMoeda += Double.valueOf(o.getPremioMorteMoeda());
            premioDespesaMedicaMoeda += Double.valueOf(o.getPremioDespesaMedicaMoeda());
            premioIncapacidadeTemporariaMoeda += Double.valueOf(o.getPremioIncapacidadeTemporariaMoeda());
            premioIncapacidadePermanenteMoeda += Double.valueOf(o.getPremioIncapacidadePermanenteMoeda());
            premioCustoRepatriamentoMoeda += Double.valueOf(o.getPremioCustoRepatriamentoMoeda());
        }

        pG.setPremioMorteMoeda(Moeda.format(premioMorteMoeda));
        pG.setPremioDespesaMedicaMoeda(Moeda.format(premioDespesaMedicaMoeda));
        pG.setPremioIncapacidadeTemporariaMoeda(Moeda.format(premioIncapacidadeTemporariaMoeda));
        pG.setPremioIncapacidadePermanenteMoeda(Moeda.format(premioIncapacidadePermanenteMoeda));
        pG.setPremioCustoRepatriamentoMoeda(Moeda.format(premioCustoRepatriamentoMoeda));

        Contrato c = (Contrato) cO[0];
        ArrayList<String[]> al = new ArrayList<>();
        pG.setTotalSeguradoMoeda(Moeda.format(Double.valueOf((c.getTotalSegurado() == null) ? 0.0 + "" : c.getTotalSegurado())));

        acidentePGBean.setAcidentePG(pG);
        //------------
        String[] dados = null;
        dados = (acidentePGBean.getInfo().size() + ";" + "Seguro de Acidente Para Grupo\n" + c.getObservacao() + ";" + c.getPremioBrutoMoeda() + ";" + c.getPremioBrutoMoeda()).split(";");
        al.add(dados);
        
        String reString = debito.docSeguros("Acidente Para Grupo", acidentePGBean.getAcidentePG().getNumeroApolice(), cO[4] + "", cO[3].toString(), " ", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio(), acidentePGBean.getAcidentePG().getNumeroRegistro());
        System.err.println(reString + " FRe impresao");

        ImpresaoNoDeb = reString;
//        RequestContext.getCurrentInstance().execute("openAllDocument('" + ImpresaoNoDeb + "')");
        RequestContext.getCurrentInstance().addCallbackParam("seguro", "acidente para grupos");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir1", ImpresaoNoDeb);

        //para criar Docomento Do Seguro de Acidente para Grupo
        SeguroAPG aPG = new SeguroAPG();
        reString = aPG.criarDoc(acidentePGBean.getAcidentePG().getNumeroApolice(), cO[3].toString(), c, acidentePGBean, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio());

        ImpresaoForm = reString;
        RequestContext.getCurrentInstance().addCallbackParam("imprimir2", ImpresaoForm);
    }

    public void reDoDocIncendio(Object[] cO, ContratoDao cd) {
        IncendioBean incendioBean = new IncendioBean();
        IncendioRisco o = cd.loadDataContratoIncendio(Integer.valueOf(dadosContrato.getId()), "FR");
        Incendio i = new Incendio();

        i.setNumeroApolice(cO[1].toString());
        i.setNumeroRegistro(cO[2].toString());

        Contrato c = (Contrato) cO[0];

        incendioBean.setIncendioRisco(o);
        incendioBean.setIncendio(i);

        ArrayList<String[]> al = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        String[] dados = null;
        System.out.println("entrou IN");
        //para criar Docomento de Nota de Credito
        dados = (1 + ";" + "Seguro de Incendio\n" + c.getObservacao() + ";" + c.getPremioBrutoMoeda() + ";" + c.getPremioBrutoMoeda()).split(";");
        al.add(dados);

        String reString = debito.docSeguros("Incendio", incendioBean.getIncendio().getNumeroApolice(), cO[4] + "", cO[3].toString(), " ", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio(), incendioBean.getIncendio().getNumeroRegistro());
        System.err.println(reString + " FRe impresao");

        ImpresaoNoDeb = reString;
        RequestContext.getCurrentInstance().addCallbackParam("seguro", "incendio");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir1", ImpresaoNoDeb);

        //para criar Docomento Do Seguro de Acidente para Grupo
        SeguroIncendio incendio = new SeguroIncendio();
        reString = incendio.criarDoc(incendioBean.getIncendio().getNumeroApolice(), cO[3].toString(), c, incendioBean, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio());

        ImpresaoForm = reString;
        RequestContext.getCurrentInstance().addCallbackParam("imprimir2", ImpresaoForm);
    }

    public void reDoDocCargaM(Object[] cO, ContratoDao cd) {
        CargaMaritimaBean cargaMaritimaBean = new CargaMaritimaBean();
        CargaMaritima o = cd.loadDataContratoCargaMaritima(Integer.valueOf(dadosContrato.getId()), "MAC");
        CargaMaritima cm = o;

        cm.setNumerApolice(cO[1].toString());
        cm.setNumeroRegistro(cO[2].toString());

        Contrato c = (Contrato) cO[0];

        cargaMaritimaBean.setCargaMaritima(cm);

        //-----
        ArrayList<String[]> al = new ArrayList<>();
        String[] dados = (1 + ";" + "Seguro Carga Maritiman\n" + c.getObservacao() + ";" + c.getPremioBrutoMoeda() + ";" + c.getPremioBrutoMoeda()).split(";");
        al.add(dados);
        String reString = debito.docSeguros("Carga Maritima", cargaMaritimaBean.getCargaMaritima().getNumerApolice(), cO[4] + "", cO[3].toString(), " ", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio(), cargaMaritimaBean.getCargaMaritima().getNumeroRegistro());

        ImpresaoNoDeb = reString;

        RequestContext.getCurrentInstance().addCallbackParam("seguro", "carga maritima");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir1", ImpresaoNoDeb);
        //-------

        CertificadoCargaMaritima cargaMaritima = new CertificadoCargaMaritima();

        String reString1 = cargaMaritima.criarDoc(cargaMaritimaBean.getCargaMaritima().getNumerApolice(), cO[3].toString(), c, cargaMaritimaBean, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio());

        ImpresaoCert = reString1;
        RequestContext.getCurrentInstance().addCallbackParam("imprimir2", ImpresaoCert);
        //-------

        SeguroCargaMaritima seguroCargaMaritima = new SeguroCargaMaritima();
        String reString2 = seguroCargaMaritima.criarDoc(cargaMaritimaBean.getCargaMaritima().getNumerApolice(), cO[3].toString(), c, cargaMaritimaBean, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio());

        ImpresaoForm = reString2;
        RequestContext.getCurrentInstance().addCallbackParam("imprimir3", ImpresaoForm);
        //-------
    }

    public void reDoDocRoubo(Object[] cO, ContratoDao cd) {
        RouboBean rouboBean = new RouboBean();
        Object[] o = cd.loadDataContratoRoubo(Integer.valueOf(dadosContrato.getId()), "DI");
        Roubo r = ((Roubo) o[1]);

        r.setNumeroApolice(cO[1].toString());
        r.setNumeroRegistro(cO[2].toString());

        Contrato c = (Contrato) cO[0];

        rouboBean.setInfo((List<Roubo>) o[0]);
        rouboBean.setRoubo(r);

        @SuppressWarnings("UnusedAssignment")
        String[] dados = null;
        ArrayList<String[]> al = new ArrayList<>();
        //para criar Docomento de Nota de Credito
        dados = (rouboBean.getInfo().size() + ";" + "Seguro de Roubo\n" + c.getObservacao() + ";" + c.getPremioBrutoMoeda() + ";" + c.getPremioBrutoMoeda()).split(";");
        al.add(dados);

        String reString = debito.docSeguros("Roubo", rouboBean.getRoubo().getNumeroApolice(), cO[4] + "", cO[3].toString(), " ", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio(), rouboBean.getRoubo().getNumeroRegistro());
        System.err.println(reString + " FRe impresao");

        ImpresaoNoDeb = reString;

        RequestContext.getCurrentInstance().addCallbackParam("seguro", "roubo");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir1", ImpresaoNoDeb);

        SeguroRoubo roubo = new SeguroRoubo();
        ImpresaoForm = roubo.criarDoc(rouboBean.getRoubo().getNumeroApolice(), cO[3].toString(), rouboBean, c, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio());
        RequestContext.getCurrentInstance().addCallbackParam("imprimir2", ImpresaoForm);
        //-------
    }

    public void reDoDocMaritimo(Object[] cO, ContratoDao cd) {
        MaritimoBean maritimoBean = new MaritimoBean();
        Maritimo m = cd.loadDataContratoMaritimo(Integer.valueOf(dadosContrato.getId()), "NHI");

        m.setNumeroApolice(cO[1].toString());
        m.setNumeroRegistro(cO[2].toString());

        Contrato c = (Contrato) cO[0];

        maritimoBean.setMaritimo(m);

        ArrayList<String[]> al = new ArrayList<>();
        String[] dados = (1 + ";" + "Seguro Maritimo\n" + c.getObservacao() + ";" + c.getPremioBrutoMoeda() + ";" + c.getPremioBrutoMoeda()).split(";");
        al.add(dados);
        String reString = debito.docSeguros("Maritimo", maritimoBean.getMaritimo().getNumeroApolice(), cO[4] + "", cO[3].toString(), " ", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio(), maritimoBean.getMaritimo().getNumeroRegistro());

        ImpresaoNoDeb = reString;

        RequestContext.getCurrentInstance().addCallbackParam("seguro", "maritimo");
        RequestContext.getCurrentInstance().addCallbackParam("imprimir1", ImpresaoNoDeb);
        //-------

        CertificadoMaritimo certificadoMaritimo = new CertificadoMaritimo();

        String reString1 = certificadoMaritimo.criarDoc(maritimoBean.getMaritimo().getNumeroApolice(), cO[3].toString(), maritimoBean, c, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio());

        ImpresaoCert = reString1;
        RequestContext.getCurrentInstance().addCallbackParam("imprimir2", ImpresaoCert);
        //-------

        SeguroMaritimo seguroMaritimo = new SeguroMaritimo();
        String reString2 = seguroMaritimo.criarDoc(maritimoBean.getMaritimo().getNumeroApolice(), cO[3].toString(), maritimoBean, c, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), getDiretorio());

        ImpresaoForm = reString2;
        RequestContext.getCurrentInstance().addCallbackParam("imprimir3", ImpresaoForm);

    }

    public String getDiretorio() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
        String arquivo = scontext.getRealPath("/Documentos/");
        System.err.println(arquivo + "chdh hfgf");
        return arquivo;
    }

    public void moreInfo(DadosContrato dc) {
        this.dadosContrato = dc;
        Validacao.atualizar("resist", "contractMoreInfo");
        RequestContext.getCurrentInstance().execute("$('.mp-infoTable').fadeIn()");

    }

    public void adicionarResseguro()
    {
        if(dadosContrato.getId() == null || dadosContrato.getId().equals("")) 
            Message.addWarningMsg("Selecione um registro", "resist", "GestaoContratoGrowl");
        else
        {
            if(!dadosContrato.getSeguro().equals("TIN"))
            {
                resseguroBean.getResseguro().setIdContrato(dadosContrato.getId());
                resseguroBean.getResseguro().setPremioGrosso(dadosContrato.getPremioBruto().substring(0, dadosContrato.getPremioBruto().length()-3));
                RequestContext.getCurrentInstance().execute("$('.resseguroCliente').val('"+dadosContrato.getCliente()+"'),$('.valorDeducao').val(''),$('.resseguroTotal').val(''),$('.resseguroTipoFA').val('')");
                RequestContext.getCurrentInstance().execute("$('.resseguroApolice').val('"+dadosContrato.getApolice()+"')");
                RequestContext.getCurrentInstance().execute("$('.resseguroTipoSeguro').val('"+dadosContrato.getIdSeguro()+"')");
                RequestContext.getCurrentInstance().execute("$('.resseguroPremioGrosso').val('"+dadosContrato.getPremioBruto().substring(0, dadosContrato.getPremioBruto().length()-3)+"')");
                RequestContext.getCurrentInstance().execute("$('.resseguroLimiteResp').val('"+
                 (dadosContrato.getLimiteResponsabilidade().equalsIgnoreCase("ilimitado")? "ILIMITADO":dadosContrato.getLimiteResponsabilidade().substring(0, dadosContrato.getLimiteResponsabilidade().length()-3))+"')");
                RequestContext.getCurrentInstance().execute("$('.resseguroMoeda').val('"+Validacao.comboId(ComoBox.loadCombo("VER_MOEDA", "ID", "SIGLA"), dadosContrato.getPremioBruto().substring(dadosContrato.getPremioBruto().length()-3, dadosContrato.getPremioBruto().length()))+"')");
                RequestContext.getCurrentInstance().execute("$('.resseguroTipoSeguro').val('"+dadosContrato.getIdSeguro()+"')");
                RequestContext.getCurrentInstance().execute("$('.resseguroNotaDebito').val('"+dadosContrato.getNotaDebito()+"')");
                RequestContext.getCurrentInstance().execute("$('.resseguroTipoCobertura').val('"+insuranceTypeCover(dadosContrato.getSeguro(), Integer.valueOf(dadosContrato.getId()))+"')");
                RequestContext.getCurrentInstance().execute("adicionarResseguro('"+dadosContrato.getDataInicio()+"','"+dadosContrato.getDataFim()+"')");
            }
        }
    }
    
    public void calcularTotalNotaCredito()
    {
        if((notaDebito.getPremioGrosso() != null && !notaDebito.getPremioGrosso().equals("")) &&
          (notaDebito.getValorDeducao() != null && !notaDebito.getValorDeducao().equals("")))
        {
            System.out.println(notaDebito);
            notaDebito.setPremioGrosso(notaDebito.getPremioGrosso().replace(" ", ""));
            notaDebito.setPremioGrosso(notaDebito.getPremioGrosso().replace(",", "."));
            notaDebito.setValorDeducao(notaDebito.getValorDeducao().replace(" ", ""));
            notaDebito.setValorDeducao(notaDebito.getValorDeducao().replace(",", "."));
            if(Double.valueOf(notaDebito.getValorDeducao())<=100)
            {
                double valor = Double.valueOf(notaDebito.getPremioGrosso())* (1-(Double.valueOf(notaDebito.getValorDeducao())/100));
                notaDebito.setTotalPagar(valor);
                RequestContext.getCurrentInstance().execute("$('.notaDebitoTotal').html('"+Moeda.format(valor)+"')");
            }
            else RequestContext.getCurrentInstance().execute("$('.notaDebitoTotal').html('')");
       
        }
       else RequestContext.getCurrentInstance().execute("$('.notaDebitoTotal').html('')");

    }
    
    public static String typeInsurance(String seguro)
    {
        switch(seguro)
        {
            case "MV":
                return "Seguro de Automóvel";
            case "TIN":
                 return "Seguro de Viagem";
            case "FR":
                return "Seguro de Incêndio";
            case "RP":
                return "Seguro de Responsabilidade Pública";
            case "NHI":
                return "Seguro Marítimo";
            case "GPA":
                return "Seguro de Acidente Para Grupos";
            case "MAC":
                return "Seguro de Carga Marítima";
            case "DI":
                return "Seguro de Roubo";
            case "DH":
                return "Seguro de Dinheiro";
            case "MR":
                return "Seguro de Multi Riscos";
          }
        return null;
    }
    
    public void calcular()
    {
        
    }
    
    public void creditNoteReg()
    { 
        if((notaDebito.getPremioGrosso() != null && !notaDebito.getPremioGrosso().equals("")) &&
          (notaDebito.getValorDeducao() != null && !notaDebito.getValorDeducao().equals("")))
        {
            notaDebito.setPremioGrosso(Validacao.unformat(notaDebito.getPremioGrosso()));
            notaDebito.setValorDeducao(Validacao.unformat(notaDebito.getValorDeducao()));
            String result = ContratoDao.registrarNotaCredito(notaDebito);
            if(result.split(";")[0].equals("true"))
            {
                Message.addInfoMsg("Nota de Crédito registrado com sucesso", "resist", "GestaoContratoGrowl");
                new DocNotaCredito().Arquivo(ConfigDoc.Fontes.getDiretorio())
                        .IdNoTaCretido(Integer.valueOf(result.split(";")[1]))
                        .InterCod(SessionUtil.getUserlogado().getNomeAcesso())
                        .NomeSeguro(typeInsurance(dadosContrato.getSeguro()))
                        .TypeNotaCredito(DocNotaCredito.TypeNotaCredito.ANULACAO)
                        .User(SessionUtil.getUserlogado().getNomeCompleto())
                        .createDoc();
                        
                RequestContext.getCurrentInstance().execute("limparCamposNotaDebito()");
                RequestContext.getCurrentInstance().execute("$('.nota-credit').fadeOut()");
            }
            else  Message.addErrorMsg(result.split(";")[1], "resist", "GestaoContratoGrowl");
        }
    }
    
    public String insuranceTypeCover(String insurance, int idContrato)
    {
        switch(insurance)
        {
            case "MV":
                return contratoDao.loadDataContratoVeiculo(idContrato, "MV").get(0).getTipoCobertura();
            case "FR":
                return contratoDao.loadDataContratoIncendio(idContrato, "FR").getCover();
            case "RP":
                return contratoDao.dadosSeguradoResponsabilidadePublica(idContrato, "RP").get(0).getCover();
            case "NHI":
                return contratoDao.loadDataContratoMaritimo(idContrato, "NHI").getCover();
            case "GPA":
                return "Morte, Despesa Médica, Incapacidade total temporária, Incapacidade total permanente, Custo com repatriamento";
            case "MAC":
                return contratoDao.loadDataContratoCargaMaritima(idContrato, "MAC").getCover();
            case "DI":
                return "Seguro de Roubo";
            case "DH":
                return contratoDao.dadosSeguradoDinheiro(idContrato, "DH").getCover();
            case "MR":
                return "Multi Riscos";
        }
        return null;
    }
   
    
}
