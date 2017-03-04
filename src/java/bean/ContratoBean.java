package bean;

import modelo.Funcionario;
import Export.CertificadoCargaMaritima;
import Export.CertificadoMaritimo;
import Export.CertificadoViatura;
import Export.DocNotaDebito;
import Export.SeguroViagem;
import Export.ModalidadePagamentoPremio;
import Export.SeguroAPG;
import Export.SeguroCargaMaritima;
import Export.SeguroCoberturaViagem;
import Export.SeguroIncendio;
import Export.SeguroMaritimo;
import Export.SeguroRoubo;
import Export.SeguroViatura;
import Export.MarcaDAgua;
import conexao.Call;
import dao.AcdentePGDao;
import dao.CargaMaritimaDao;
import dao.ClienteDao;
import dao.ContratoDao;
import dao.IncendioDao;
import dao.RouboDao;
import dao.SeguroDao;
import dao.SeguroDinheiroDAO;
import dao.SeguroRespPublicaDao;
import dao.Seguro_MaritimoDao;
import dao.VeiculoDao;
import dao.ViagemDao;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import lib.Moeda;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.AcidentePG;
import modelo.ComoBox;
import modelo.Contrato;
import modelo.ModeloPagamento;
import modelo.Veiculo;
import modelo.Viagem;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helcio
 */
@ManagedBean
@ViewScoped
public class ContratoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private ViagemBean viagemBean;
    private AcidentePGBean acidentePGBean;
    private CargaMaritimaBean cargaMaritimaBean;
    private AcdentePGDao apgd = new AcdentePGDao();
    private ListaRespostas lr;
    private MaritimoBean maritimoBean;
    private VeiculoBean veiculoBean;
    private MultiRiscoBean multiRiscoBean;
    private String mensagem;
    private String selectSeguros;
    private boolean ativarDesativarCampoFranquia = true, ativarDesativarDataInicio = false, ativarDesativarDataFim = false;
    private IncendioBean incendioBean;
    private ResponsabilidadePublicaBean responsabilidadePublicaBean;
    private Contrato contrato = new Contrato();
    private String[] nc;
    private String[] imposto;
    private String codigo = null;
    private boolean habilitarDesabilitar;
    private DinheiroBean dinheiroBean;
    private RouboBean rouboBean;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfPt = new SimpleDateFormat("dd-MM-yyyy");
    private Date data;
    private String nomeSeguro;
    private String ImpresaoForm;
    private String ImpresaoNoDeb;
    private ContratoDao contratoDao = new ContratoDao();
    private SeguroDao seguroDao = new SeguroDao();
    private String ImpresaoCert;
    private String codigoSeguro;
    private boolean data1 = false, data2 = true, data3 = true, data4 = true;
    private boolean taxaDesativado = false;
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private DocNotaDebito debito = new DocNotaDebito();
    ModeloPagamento modeloPagamento = new ModeloPagamento();
    ArrayList<ModeloPagamento> listaModeloPagamento = new ArrayList<>();
    ArrayList<ComoBox> listaTipoPagamento = new ArrayList<>();
    private String yearRange;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ContratoBean() {
        listaTipoPagamento = contratoDao.listaTipoPagamento();
        this.limitarData();
        SessionUtil.removerParametro("PREMIO");
        switch (getSelectSeguros()[0]) {
            case "TIN": // se o seguro selecionado for viagem, seleciona imposto de cinco por cento, seis por cento, nc e desativa o campo franquia.  
                dadosViagem();
                nomeSeguro = "Seguro de Viagem";
                break;
            case "GPA":// se o seguro selecionado for acidente para grupo, seleciona imposto de cinco por cento, seis por cento, nc, desativa o campo franquia e desativa nc.
                DadosAcidentePG();
                nomeSeguro = "Seguro de Acidente para Grupo";
                break;
            case "NHI":// se o seguro selecionado for acidente para grupo, seleciona imposto de cinco por cento, seis por cento, nc, desativa o campo franquia e desativa nc.
                DadosMaritimo();
                nomeSeguro = "Seguro Maritimo";
                break;
            case "MV":
                DadosVeiculo();
                nomeSeguro = "Seguro de Automóvel";
                break;
            case "DI":
                DadosRoubo();
                nomeSeguro = "Seguro de Roubo";
                break;
            case "FR":
                DadosIncendio();
                nomeSeguro = "Seguro de Incêndio";
                break;
            case "MAC":
                DadosCargaMaritima();
                nomeSeguro = "Seguro de Carga Maritima";
                break;
            case "DH":
                DadosDinheiro();
                nomeSeguro = "Seguro de Dinheiro";
                break;
            case "RP":
                DadosResponsabilidadePublica();
                nomeSeguro = "Seguro de Responsabilidade Pública";
                break;
            case "MR":
                dadosMultiRisco();
                nomeSeguro = "Seguro de Multiriscos";
                break;
        }

    }

    public boolean isData1() {
        return data1;
    }

    public String getNomeSeguro() {
        return nomeSeguro;
    }

    public void limitarData() {
        data2 = true;
        if (contrato.getDataInicio() != null) {
            data2 = false;
        }
        if (contrato.getDataFim() != null) {
            data3 = false;
            data4 = false;
        }

    }

    public boolean isAtivarDesativarCampoFranquia() {
        return ativarDesativarCampoFranquia;
    }

    public String getYearRange() {
        return yearRange;
    }

    public VeiculoBean getVeiculoBean() {
        return veiculoBean;
    }

    public RouboBean getRouboBean() {
        return rouboBean;
    }

    public boolean isData2() {
        return data2;
    }

    public boolean isData3() {
        return data3;
    }

    public boolean isData4() {
        return data4;
    }

    public void setVeiculoBean(VeiculoBean veiculoBean) {
        this.veiculoBean = veiculoBean;
    }

    public void setImposto(String[] imposto) {
        this.imposto = imposto;
    }

    public String[] getNc() {
        return nc;
    }

    public String getMensagem() {
        return mensagem;
    }

    public MultiRiscoBean getMultiRiscoBean() {
        return multiRiscoBean;
    }

    public MaritimoBean getMaritimoBean() {
        return maritimoBean;
    }

    public DinheiroBean getDinheiroBean() {
        return dinheiroBean;
    }

    public void setDinheiroBean(DinheiroBean dinheiroBean) {
        this.dinheiroBean = dinheiroBean;
    }

    public void setMaritimoBean(MaritimoBean maritimoBean) {
        this.maritimoBean = maritimoBean;
    }

    public void setNc(String[] nc) {
        this.nc = nc;
    }

    public ListaRespostas getLr() {
        return lr;
    }

    public void setLr(ListaRespostas lr) {
        this.lr = lr;
    }

    public AcidentePGBean getAcidentePGBean() {
        return acidentePGBean;
    }

    public boolean verificarInfo() {
        boolean valido = true;
        try {
            Integer.parseInt(contrato.getDesconto());
        } catch (NumberFormatException formatException) {
            valido = false;
        }
        return valido;
    }

    public void setAcidentePGBean(AcidentePGBean acidentePGBean) {
        this.acidentePGBean = acidentePGBean;
    }

    public String getCodigoSeguro() {
        return codigoSeguro;
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

    public void setImpresaoNoDeb(String ImpresaoNoDeb) {
        this.ImpresaoNoDeb = ImpresaoNoDeb;
    }

    public String getImpresaoCert() {
        return ImpresaoCert;
    }

    public void setImpresaoCert(String ImpresaoCert) {
        this.ImpresaoCert = ImpresaoCert;
    }

    public ModeloPagamento getModeloPagamento() {
        return (modeloPagamento == null) ? new ModeloPagamento() : modeloPagamento;
    }

    public void setModeloPagamento(ModeloPagamento modeloPagamento) {
        this.modeloPagamento = modeloPagamento;
    }

    public ArrayList<ModeloPagamento> getListaModeloPagamento() {
        return (listaModeloPagamento == null) ? listaModeloPagamento = new ArrayList<>() : listaModeloPagamento;
    }

    public void setListaModeloPagamento(ArrayList<ModeloPagamento> listaModeloPagamento) {
        this.listaModeloPagamento = listaModeloPagamento;
    }

    public ArrayList<ComoBox> getListaTipoPagamento() {
        return listaTipoPagamento;
    }

    public void setListaTipoPagamento(ArrayList<ComoBox> listaTipoPagamento) {
        this.listaTipoPagamento = listaTipoPagamento;
    }

    @SuppressWarnings("UnusedAssignment")
    public void calcularPremioLiquido() {
        double desconto = 0, premioLiquidoReal = 0, novoPremioLiquido = 0, premioliquidoCerto = 0;
        if (SessionUtil.obterValor("PREMIO") == null && contrato.getPremioLiquido() != null && contrato.getPremioLiquido().length() > 0) {
            SessionUtil.definirParametro("PREMIO", contrato.getPremioLiquido());
            System.out.println("entrou");
        }

        if (contrato.getPremioLiquido() != null && contrato.getPremioLiquido().length() > 0) {
            if ((contrato.getDesconto() != null && contrato.getDesconto().length() > 0) && (SoNumero(contrato.getDesconto()) == true)) {
                if (SessionUtil.obterValor("PREMIO") != null) {
                    premioLiquidoReal = Double.valueOf(SessionUtil.obterValor("PREMIO").toString());
                    System.out.println("premio liquido real " + premioLiquidoReal);
                }
                if (Double.valueOf(contrato.getDesconto()) <= 100) {
                    desconto = Double.valueOf(contrato.getDesconto()) / 100;
                    novoPremioLiquido = (premioLiquidoReal * (1 - desconto));
                    contrato.setPremioLiquido(String.valueOf(novoPremioLiquido));
                    contrato.setPremioLiquidoMoeda(Moeda.format(novoPremioLiquido));
                    System.out.println("novo premio " + novoPremioLiquido);
                    RequestContext.getCurrentInstance().update("contrato:premioLiquido");

                } else if (SessionUtil.obterValor("PREMIO") != null) {
                    premioLiquidoReal = Double.valueOf(SessionUtil.obterValor("PREMIO").toString());
                    contrato.setPremioLiquido(String.valueOf(premioLiquidoReal));
                    contrato.setPremioLiquidoMoeda(Moeda.format(premioLiquidoReal));
                    contrato.setDesconto("");
                    RequestContext.getCurrentInstance().update("contrato:contratoDesconto");
                    RequestContext.getCurrentInstance().update("contrato:premioLiquido");
                }
            } else if (SessionUtil.obterValor("PREMIO") != null) {
                premioLiquidoReal = Double.valueOf(SessionUtil.obterValor("PREMIO").toString());
                contrato.setPremioLiquido(String.valueOf(premioLiquidoReal));
                contrato.setPremioLiquidoMoeda(Moeda.format(premioLiquidoReal));
                contrato.setDesconto("");
                RequestContext.getCurrentInstance().update("contrato:contratoDesconto");
                RequestContext.getCurrentInstance().update("contrato:premioLiquido");
            }

        }

    }

    public ViagemBean getViagemBean() {
        return viagemBean;
    }

    public void setViagemBean(ViagemBean viagemBean) {
        this.viagemBean = viagemBean;
    }

    public Contrato getContrato() {
        return (contrato == null) ? contrato = new Contrato() : contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Date getData() {
        return data;
    }

    public String[] getImposto() {
        return imposto;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void enviar() {
        SeguroDao sd = new SeguroDao();
        sd.s(data);
    }
    // devolve os dados do seguro selecionado

    public String[] getSelectSeguros() {
        if (session.getAttribute("S") != null) {
            return session.getAttribute("S").toString().split(";");
        }
        return null;
    }

    private void regRoubo(String idContrato) {
        String resultado = "";
        String tipoEdificio = null;
        
        if (!MarcaDAgua.isSimulation) {
            RouboDao rouboDao = new RouboDao();
            for (int i = 0; i < rouboBean.getInfo().size(); i++) {
                resultado = rouboDao.regRoubo(idContrato, Integer.valueOf(rouboBean.getInfo().get(i).getQuantidade()), rouboBean.getInfo().get(i).getModelo(),
                        Float.valueOf(rouboBean.getInfo().get(i).getValor()), rouboBean.getInfo().get(i).getDescricao());
            }
            if (resultado != null && resultado.equals("true")) {
                tipoEdificio = (rouboBean.getRoubo().getTipoEdificio().equals("66;")) ? "66;" + rouboBean.getRoubo().getTipoEdificioEspecifique() : rouboBean.getRoubo().getTipoEdificio();
                System.out.println("tipo de edificio " + tipoEdificio);
                resultado = rouboDao.registrarInfoRoubo(idContrato, tipoEdificio, rouboBean.getRoubo().getEnderecoEdificio(), rouboBean.getRoubo().getTempoOcupacao(), rouboBean.getRoubo().getDatatInspecao(), Float.valueOf(rouboBean.getRoubo().getValorCofre()),
                        rouboBean.getRoubo().getMarcaCofre(), OperacaoData.toSQLDate(rouboBean.getRoubo().getDataAquisicao()));
            }
        }
            if (resultado.equals("true") || MarcaDAgua.isSimulation) {
                String[] dados = null;
                ArrayList<String[]> al = new ArrayList<>();
                //para criar Docomento de Nota de Credito
                dados = (rouboBean.getInfo().size() + ";" + "Seguro de Roubo\n" + contrato.getObservacao() + ";" + contrato.getPremioBrutoMoeda() + ";" + contrato.getPremioBrutoMoeda()).split(";");
                al.add(dados);
                RequestContext.getCurrentInstance().addCallbackParam("seguro", "roubo");
                String reString = debito.docSeguros("Roubo", rouboBean.getRoubo().getNumeroApolice(), SessionUtil.getUserlogado().getNomeAcesso(), session.getAttribute("CodigoCliente").toString(), " ", al, contrato, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio(), rouboBean.getRoubo().getNumeroRegistro());
                System.err.println(reString + " FRe impresao");

                ImpresaoNoDeb = reString;
                RequestContext.getCurrentInstance().update("contrato:docNoDeb");

                SeguroRoubo roubo = new SeguroRoubo();
                ImpresaoForm = roubo.criarDoc(rouboBean.getRoubo().getNumeroApolice(), session.getAttribute("CodigoCliente").toString(), rouboBean, contrato, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio());

                RequestContext.getCurrentInstance().update("contrato:docForm");

                String retur = createDocPagamento();
                RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);

                if (!MarcaDAgua.isSimulation) {
                    lr.guardarRespostas(Integer.valueOf(idContrato));

                    if (codigoSeguro.equals("DI")) {
                        mensagem = "Apolice " + rouboBean.getRoubo().getNumeroApolice() + " registrado com sucesso";
//                        rouboBean = null;

                        RequestContext.getCurrentInstance().update("contratoCon:info");
                        RequestContext.getCurrentInstance().execute("abrirModal()");
                    }
                }
        }
    }

    @SuppressWarnings("null")
    private void regAcidenteGrupo(String idContrato) throws IOException {

        System.out.println(acidentePGBean.toString());
        @SuppressWarnings("UnusedAssignment")
        String result = "";
        String result2 = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        if(!MarcaDAgua.isSimulation ) 
        {
            System.err.println("GPA COVERS "+acidentePGBean.getAcidentePG().getTipoCobertura());
            result = apgd.registrarInfo(idContrato.split(";")[1], Integer.valueOf(acidentePGBean.getAcidentePG().getTipoCobertura()));
            if(result.equals("true"))
            {
                for (AcidentePG info : acidentePGBean.getInfo())
                {
                    result = apgd.registrarAcidentepg(info, idContrato.split(";")[1]);
                }
                if(!result.split(";")[0].equals("true"))
                {
                    seguroDao.AnularContrato(Integer.valueOf(idContrato.split(";")[1]), "-1","Erro a registrar Seguro de Acidente de Trabalho", null, null);
                    Message.addErrorMsg("Houve um erro a registrar o contrato!", "contrato", "apoliceInfo");
                }
                else lr.guardarRespostas(Integer.valueOf(idContrato.split(";")[1]));   
            }
            else
            {
                seguroDao.AnularContrato(Integer.valueOf(idContrato.split(";")[1]), "-1","Erro a registrar Seguro de Acidente de Trabalho", null, null);
                Message.addErrorMsg("Houve um erro a registrar o contrato!", "contrato", "apoliceInfo");
            }
        }

        if(result.split(";")[0].equals("true") || MarcaDAgua.isSimulation) 
        {
            ArrayList<String[]> al = new ArrayList<>();
            @SuppressWarnings("UnusedAssignment")
            String[] dados = null;
              RequestContext.getCurrentInstance().addCallbackParam("seguro", "acidente para grupo");
            //para criar Docomento de Nota de Credito
            dados = (acidentePGBean.getInfo().size() + ";" + "Seguro de Acidente Para Grupo\n" + contrato.getObservacao() + ";" + contrato.getPremioBrutoMoeda() + ";" + contrato.getPremioBrutoMoeda()).split(";");
            al.add(dados);

            String reString = debito.docSeguros("Acidente Para Grupo",
            acidentePGBean.getAcidentePG().getNumeroApolice(),
            SessionUtil.getUserlogado().getNomeAcesso(),
            session.getAttribute("CodigoCliente").toString(), 
            " ", 
            al, 
            contrato, 
            (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(),
            contrato.getSigla(), 
            getDiretorio(), 
            acidentePGBean.getAcidentePG().getNumeroRegistro());

            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");

            //para criar Docomento Do Seguro de Acidente para Grupo
            SeguroAPG aPG = new SeguroAPG();
            reString = aPG.criarDoc(acidentePGBean.getAcidentePG().getNumeroApolice(), session.getAttribute("CodigoCliente").toString(), contrato, acidentePGBean, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio());

            ImpresaoForm = reString;
            RequestContext.getCurrentInstance().update("contrato:docForm");

            String retur = createDocPagamento();
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);
            
            if(!MarcaDAgua.isSimulation ) 
            {
                if(result.split(";")[0].equals("true"))
                {
                    mensagem = "Apolice " + acidentePGBean.getAcidentePG().getNumeroApolice() + " registrado com sucesso";
                    RequestContext.getCurrentInstance().update("contratoCon:info");
                    RequestContext.getCurrentInstance().execute("abrirModal()");
                    ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), acidentePGBean.getInfo().size(), 0, null, 1, null);
                }
                else ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), acidentePGBean.getInfo().size(), 0, null, -1, null); 
            }
        }

    }

    @SuppressWarnings("null")
    private void regIncensdio(String idContrato) throws IOException {
        String result = "";

        IncendioDao id = new IncendioDao();
        if(!MarcaDAgua.isSimulation)
        {
            result = id.IncedioInfo(Integer.valueOf(idContrato), incendioBean);
            id.IncedioListaCobertura(Integer.valueOf(idContrato), incendioBean);
            lr.guardarRespostas(Integer.valueOf(codigo));
        }

        if (result.equals("true") || MarcaDAgua.isSimulation) {
            ArrayList<String[]> al = new ArrayList<>();
            @SuppressWarnings("UnusedAssignment")
            String[] dados = null;

            //para criar Docomento de Nota de Credito
            dados = (1 + ";" + "Seguro de Incendio\n" + contrato.getObservacao() + ";" + contrato.getPremioBrutoMoeda() + ";" + contrato.getPremioBrutoMoeda()).split(";");
            al.add(dados);

            String reString = debito.docSeguros("Incendio", incendioBean.getIncendio().getNumeroApolice(), SessionUtil.getUserlogado().getNivelAcesso(), session.getAttribute("CodigoCliente").toString(), " ", al, contrato, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio(), incendioBean.getIncendio().getNumeroRegistro());
            System.err.println(reString + " FRe impresao");
                  RequestContext.getCurrentInstance().addCallbackParam("seguro", "incendio");
            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");

            //para criar Docomento Do Seguro de Acidente para Grupo
            SeguroIncendio incendio = new SeguroIncendio();
            reString = incendio.criarDoc(incendioBean.getIncendio().getNumeroApolice(), session.getAttribute("CodigoCliente").toString(), contrato, incendioBean, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio());

            ImpresaoForm = reString;
            RequestContext.getCurrentInstance().update("contrato:docForm");

            String retur = createDocPagamento();
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);
               
            if (codigoSeguro.equals("FR") && !MarcaDAgua.isSimulation) {
                mensagem = "Apolice " + incendioBean.getIncendio().getNumeroApolice() + " registrado com sucesso";
//                incendioBean = null;
                RequestContext.getCurrentInstance().update("contratoCon:info");
                RequestContext.getCurrentInstance().execute("abrirModal()");
//                SessionUtil.removerParametro("FR");
            }

        }

    }

    @SuppressWarnings("null")
    public void regVeiculo(String idContrato) {
        String result = "";
        
        if(!MarcaDAgua.isSimulation)
        {
            VeiculoDao veiculoDao = new VeiculoDao();
            for (Veiculo vi : veiculoBean.getInfo()) {
                vi.setTipoCobertura(veiculoBean.getVeiculo().getTipoCobertura());
                result = veiculoDao.registrarVeiculo(vi, idContrato.split(";")[1]);
            }
            if(!result.split(";")[0].equals("true"))
            {
                seguroDao.AnularContrato(Integer.valueOf(idContrato.split(";")[1]), "-1","Erro a registrar Seguro de Automóvel", null, null);
                Message.addErrorMsg("Houve um erro a registrar o contrato!", "contrato", "apoliceInfo");
            }
        }
        if (result.split(";")[0].equals("true") || MarcaDAgua.isSimulation ) 
        {
            SeguroViatura sv = new SeguroViatura();
            String dd = sv.criarDoc(veiculoBean.getVeiculo().getNumeroApolice(), session.getAttribute("CodigoCliente").toString(),
                    contrato, veiculoBean, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio());
            ImpresaoForm = dd;
            RequestContext.getCurrentInstance().update("contrato:docForm");
//            RequestContext.getCurrentInstance().execute("openForm()");
            RequestContext.getCurrentInstance().addCallbackParam("seguro", "veiculo");

            ArrayList<String[]> al = new ArrayList<>();

            //------------
            String[] dados = null;
            dados = (veiculoBean.getInfo().size() + ";" + "Seguro de Automovel\n" + contrato.getObservacao() + ";" + Moeda.format((Double.valueOf((contrato.getPremioBruto() == null || contrato.getPremioBrutoMoeda().isEmpty()) ? "0" : contrato.getPremioBruto())) / veiculoBean.getInfo().size()) + ";" + contrato.getPremioBrutoMoeda()).split(";");
            al.add(dados);

            String reString = debito.docSeguros("Automovel",
                    veiculoBean.getVeiculo().getNumeroApolice(), 
                    SessionUtil.getUserlogado().getNomeAcesso(), 
                    session.getAttribute("CodigoCliente").toString(),
                    "FGA", 
                    al, 
                    contrato,
                    (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(),
                    contrato.getSigla(),
                    getDiretorio(),
                    veiculoBean.getVeiculo().getNumeroRegistro());

            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");
//            RequestContext.getCurrentInstance().execute("openDebito()");
            CertificadoViatura cv = new CertificadoViatura();

            ImpresaoCert = cv.criarDoc(veiculoBean.getVeiculo().getNumeroApolice(), session.getAttribute("CodigoCliente").toString(), contrato, veiculoBean,
                    (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio());

            RequestContext.getCurrentInstance().update("contrato:docCer");

            String retur = createDocPagamento();
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);

            if(!MarcaDAgua.isSimulation)
            {
                lr.guardarRespostas(Integer.valueOf(idContrato.split(";")[1]));
            }
        }

        if(!MarcaDAgua.isSimulation )
        {
            if(result.split(";")[0].equals("true"))
            {
                mensagem = "Apolice " + veiculoBean.getVeiculo().getNumeroApolice() + " registrado com sucesso";
                RequestContext.getCurrentInstance().update("contratoCon:info");
                RequestContext.getCurrentInstance().execute("abrirModal()");
                ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), veiculoBean.getInfo().size(),
                        0, null, 1, null);
            }
            else
                ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), veiculoBean.getInfo().size(),
                 0, null, -1, null);  
        }
      
    }

    // MÉTODO QUE REGISTRA AS INFORMAÇÕES DO SEGURO DE VIAGEM
    @SuppressWarnings("UnusedAssignment")
    private void regViagem(String idContrato) throws IOException {
        String result = "";
        
        if(!MarcaDAgua.isSimulation)
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ViagemDao vd = new ViagemDao();
       
            result = vd.registrarViagem(viagemBean.getViagem(), idContrato.split(";")[1], viagemBean.getInfoPessoaSegurada().size());
            if(result.equals("true"))
            {
                for (Viagem oVb : viagemBean.getInfoPessoaSegurada())
                {
                    result = vd.registrarObjectoViagem((String) ((Funcionario) SessionUtil.obterValor("utilizador")).getId(), idContrato.split(";")[1], oVb);
                }
                if(!result.split(";")[0].equals("true"))
                {
                    seguroDao.AnularContrato(Integer.valueOf(idContrato.split(";")[1]), "-1","Erro a registrar Seguro de Viagem", null, null);
                    Message.addErrorMsg("Houve um erro a registrar o contrato!", "contrato", "apoliceInfo");
                }
            }
            else
            {
                seguroDao.AnularContrato(Integer.valueOf(idContrato.split(";")[1]), "-1","Erro a registrar Seguro de Viagem", null, null);
                Message.addErrorMsg("Houve um erro a registrar o contrato!", "contrato", "apoliceInfo");
            }
                
        }
        if(result.split(";")[0].equals("true") || MarcaDAgua.isSimulation) 
        {
            //------------
            SeguroViagem dw = new SeguroViagem();
            String dd = dw.criarDoc(viagemBean, session.getAttribute("CodigoCliente").toString(), (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio(), contrato);
            ImpresaoForm = dd;
            RequestContext.getCurrentInstance().update("contrato:docForm");
//            RequestContext.getCurrentInstance().execute("openForm()");
            RequestContext.getCurrentInstance().addCallbackParam("seguro", "viagem");

            String retur = createDocPagamento();
//            RequestContext.getCurrentInstance().execute("openAll('" + retur + "')");
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);
            
            SeguroCoberturaViagem.criarDoc(viagemBean, session.getAttribute("CodigoCliente").toString(), (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio(), contrato);

            ArrayList<String[]> al = new ArrayList<>();
            
            String[] dados = new String[4];
            for (int i = 0; i < viagemBean.getInfoPessoaSegurada().size(); i++) { 
                
                String nome = viagemBean.getInfoPessoaSegurada().get(i).getNomePessoaSegurada();
                String apolice = viagemBean.getInfoPessoaSegurada().get(i).getNumApolice();
                String valor = Moeda.format(Double.valueOf(viagemBean.getInfoPessoaSegurada().get(i).getPremioBruto()));
                
                dados[0] = " ";
                dados[1] = nome +" --- "+apolice;
                dados[2] = valor +" "+ contrato.getSigla();
                dados[3] = "";
                
                al.add(dados);
                dados = new String[4];
            } 

            //------------
            dados = new String[4];
            
            dados = (viagemBean.getInfoPessoaSegurada().size() + ";" + "Seguro de Viagem\n" + contrato.getObservacao() + ";" +" "+ ";" + getContrato().getPremioBrutoMoeda()).split(";");
            al.add(dados);
            String reString = debito.docSeguros("Viagem", viagemBean.getViagem().getNumApolice(), SessionUtil.getUserlogado().getNomeAcesso(), session.getAttribute("CodigoCliente").toString(), "NC", al, contrato, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio(), viagemBean.getViagem().getNumeroRegistro());

            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");
//            RequestContext.getCurrentInstance().execute("openDebito()");
            if(!MarcaDAgua.isSimulation)
            {
                if(result.split(";")[0].equals("true"))
                {
                    mensagem = "Apolice " + viagemBean.getViagem().getNumApolice() + " registrado com sucesso";
                    RequestContext.getCurrentInstance().update("contratoCon:info");
                    RequestContext.getCurrentInstance().execute("abrirModal()");
                    ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), viagemBean.getInfoPessoaSegurada().size(),
                        0, null, 1, viagemBean.getViagem());
                }
                else
                    ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), viagemBean.getInfoPessoaSegurada().size(),0, null, -1, viagemBean.getViagem()); 
            }
            
        }

    }

    @SuppressWarnings("UnusedAssignment")
    public void regSeguroMaritimo(String idContrato) {
        String resultado = null;
        
        if (!MarcaDAgua.isSimulation) {
            Seguro_MaritimoDao maritimoDao = new Seguro_MaritimoDao();
            System.out.println(Arrays.toString(maritimoBean.getCoberturas().toArray()));
            resultado = maritimoDao.regObjMaritimo(maritimoBean.getMaritimo(), Integer.parseInt(idContrato), maritimoBean.getCoberturas());
            maritimoDao.listaCobertura(Integer.valueOf(idContrato), maritimoBean.getCoberturas());
        }
        
        System.out.println("resultado da funcção " + resultado);
        if (resultado != null || MarcaDAgua.isSimulation) {
            ArrayList<String[]> al = new ArrayList<>();
            String[] dados = (1 + ";" + "Seguro Maritimo\n" + contrato.getObservacao() + ";" + contrato.getPremioBrutoMoeda() + ";" + contrato.getPremioBrutoMoeda()).split(";");
            al.add(dados);
            String reString = debito.docSeguros("Maritimo", maritimoBean.getMaritimo().getNumeroApolice(), SessionUtil.getUserlogado().getNomeAcesso(), session.getAttribute("CodigoCliente").toString(), " ", al, contrato, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio(), maritimoBean.getMaritimo().getNumeroRegistro());

            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().addCallbackParam("seguro", "maritimo");
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");
            //-------

            CertificadoMaritimo certificadoMaritimo = new CertificadoMaritimo();

            String reString1 = certificadoMaritimo.criarDoc(maritimoBean.getMaritimo().getNumeroApolice(), session.getAttribute("CodigoCliente").toString(), maritimoBean, contrato, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio());

            ImpresaoCert = reString1;
            RequestContext.getCurrentInstance().update("contrato:docCer");
            //-------

            SeguroMaritimo seguroMaritimo = new SeguroMaritimo();
            String reString2 = seguroMaritimo.criarDoc(maritimoBean.getMaritimo().getNumeroApolice(), session.getAttribute("CodigoCliente").toString(), maritimoBean, contrato, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio());

            ImpresaoForm = reString2;
            RequestContext.getCurrentInstance().update("contrato:docForm");

            String retur = createDocPagamento();
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);

            mensagem = "Apolice " + maritimoBean.getMaritimo().getNumeroApolice() + " registrado com sucesso";
//            maritimoBean = null;
//            SessionUtil.removerParametro("NHI");
            if(!MarcaDAgua.isSimulation)
            {
                RequestContext.getCurrentInstance().update("contratoCon:info");
                RequestContext.getCurrentInstance().execute("abrirModal()");
            }

        }

    }

    public void RegSeguroDinheiro(String idContrato) {
        String resultado = "", resultado2 = "";
        System.out.println(dinheiroBean.getDinheiro().toString());
        SeguroDinheiroDAO dinheiroDAO = new SeguroDinheiroDAO();
        
        if(MarcaDAgua.isSimulation)
        {
            resultado = dinheiroDAO.regInfoDinheiro(Integer.valueOf(idContrato), dinheiroBean.getDinheiro().getBanco(),
                    dinheiroBean.getDinheiro().getCorreio(), dinheiroBean.getDinheiro().getOutros(), dinheiroBean.getDinheiro().getTransporteDinheiro(),
                    dinheiroBean.getDinheiro().getPrecupacao(), dinheiroBean.getDinheiro().getTempoPermanenciaBanco(), dinheiroBean.getDinheiro().getPagamento1item(),
                    Integer.valueOf(dinheiroBean.getCobertura()), Float.valueOf(dinheiroBean.getLimite()), Float.valueOf(dinheiroBean.getPremio()));
        }
        
        if (resultado != null && resultado.equals("true") || MarcaDAgua.isSimulation) {

            ArrayList<String[]> al = new ArrayList<>();
            @SuppressWarnings("UnusedAssignment")
            String[] dados = null;

            //para criar Docomento de Nota de Credito
            dados = (1 + ";" + "Seguro Dinheiro\n" + contrato.getObservacao() + ";" + contrato.getPremioBrutoMoeda() + ";" + contrato.getPremioBrutoMoeda()).split(";");
            al.add(dados);
            RequestContext.getCurrentInstance().addCallbackParam("seguro", "dinheiro");
            String reString = debito.docSeguros("Dinheiro", dinheiroBean.getDinheiro().getNumApolice(), SessionUtil.getUserlogado().getNivelAcesso(), session.getAttribute("CodigoCliente").toString(), " ", al, contrato, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio(), dinheiroBean.getDinheiro().getNumeroRegistro());
            System.err.println(reString + " FRe impresao");

            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");

            String retur = createDocPagamento();
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);
            
            if( !MarcaDAgua.isSimulation )
            if (dinheiroBean.getInfoCofre().size() > 0) {
                for (int i = 0; i < dinheiroBean.getInfoCofre().size(); i++) {
                    resultado2 = dinheiroDAO.regObjDinheiro(idContrato, dinheiroBean.getInfoCofre().get(i).getNome_Fabricante(),
                            dinheiroBean.getInfoCofre().get(i).getNumero_Fabricante(), dinheiroBean.getInfoCofre().get(i).getTamanho(),
                            dinheiroBean.getInfoCofre().get(i).getDetentor_Chave(), dinheiroBean.getInfoCofre().get(i).getPeso(),
                            dinheiroBean.getInfoCofre().get(i).getConstruido_ou_Fixo(), dinheiroBean.getInfoCofre().get(i).getEstrutura());
                }
                if (resultado2 != null && resultado2.equals("true") && codigoSeguro.equals("DH")) {
                    mensagem = "Apolice " + dinheiroBean.getDinheiro().getNumApolice() + " registrado com sucesso";
//                    dinheiroBean = null;
//                    SessionUtil.removerParametro("DH");
                    RequestContext.getCurrentInstance().update("contratoCon:info");
                    RequestContext.getCurrentInstance().execute("abrirModal()");
                }
            } else {
                if (codigoSeguro.equals("DH")) {
                    mensagem = "Apolice " + dinheiroBean.getDinheiro().getNumApolice() + " registrado com sucesso";
//                    dinheiroBean = null;
//                    SessionUtil.removerParametro("DH");
                    RequestContext.getCurrentInstance().update("contratoCon:info");
                    RequestContext.getCurrentInstance().execute("abrirModal()");
                }

            }

        }
    }
    // metodo que permite registrar um novo seguro

    @SuppressWarnings("UnusedAssignment")
    public void registrarSeguro() throws IOException {
        String idContrato = null;
        ViagemDao vd = new ViagemDao();
        String result = null;
        if (SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null) {
            SessionUtil.removerParametro(SessionUtil.APOLICE_CONTRATO_SEGURO);
        }

        if (session.getAttribute("CodigoCliente") != null) 
        {
            if (session.getAttribute("S") != null) 
            {
                ContratoDao cdd = new ContratoDao();
                switch (session.getAttribute("S").toString().split(";")[0]) 
                {
                    case "TIN":// se o codigo do contrato for MV
                    {
                        System.out.println(viagemBean.getViagem().toString());
                        if(codigo == null || !codigo.split(";")[0].equals("true"))
                        { 
                            idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "TIN",
                                    viagemBean.getViagem().getNumApolice(),
                                    viagemBean.getViagem().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), 10, null);
                            if (idContrato == null) {
                                break;
                            }
                            codigo = idContrato;
                            if(codigo.split(";")[0].equals("true"))
                                regViagem(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                        else
                        {
                            if(codigo.split(";")[0].equals("true"))
                                regViagem(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                        break;
                    }
                    case "MAC": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                                 idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "MAC",
                                    cargaMaritimaBean.getCargaMaritima().getNumerApolice(),
                                    cargaMaritimaBean.getCargaMaritima().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(cargaMaritimaBean.getCargaMaritima().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                             if(codigo.split(";")[0].equals("true"))
                                 regSeguroCargaMaritima(codigo);
                            else
                             {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                             }
                        } else {
                             if(codigo.split(";")[0].equals("true"))
                                 regSeguroCargaMaritima(codigo);
                            else
                             {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                             }
                        }
                        break;
                    }
                    case "NHI": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                                idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "NHI",
                                    maritimoBean.getMaritimo().getNumeroApolice(),
                                    maritimoBean.getMaritimo().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(maritimoBean.getMaritimo().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                              if(codigo.split(";")[0].equals("true"))
                                  regSeguroMaritimo(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        } else {
                              if(codigo.split(";")[0].equals("true"))
                                  regSeguroMaritimo(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                    }
                    break;
                    case "GPA": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                                idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "GPA",
                                    acidentePGBean.getAcidentePG().getNumeroApolice(),
                                    acidentePGBean.getAcidentePG().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(acidentePGBean.getAcidentePG().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                             if(codigo.split(";")[0].equals("true"))
                                  regAcidenteGrupo(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        } else {
                            if(codigo.split(";")[0].equals("true"))
                                regAcidenteGrupo(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                        break;
                    }
                    case "DI": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                                idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "DI",
                                    rouboBean.getRoubo().getNumeroApolice(),
                                    rouboBean.getRoubo().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(rouboBean.getRoubo().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                             if(codigo.split(";")[0].equals("true"))
                                regRoubo(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        } else {
                             if(codigo.split(";")[0].equals("true"))
                                regRoubo(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                    }
                    break;
                    case "MV": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                                 idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "MV",
                                    veiculoBean.getVeiculo().getNumeroApolice(),
                                    veiculoBean.getVeiculo().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(veiculoBean.getVeiculo().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                             if(codigo.split(";")[0].equals("true"))
                                regVeiculo(codigo);
                             else
                             {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                             }
                        } else {
                            if(codigo.split(";")[0].equals("true"))
                                regVeiculo(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                    }
                    break;
                    case "MR": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")){
                                 idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "MR",
                                    multiRiscoBean.getMultiRisco().getNumeroApolice(),
                                    multiRiscoBean.getMultiRisco().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(multiRiscoBean.getMultiRisco().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                            if(codigo.split(";")[0].equals("true"))
                                  regMultiRisco(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        } else {
                            if(codigo.split(";")[0].equals("true"))
                                  regMultiRisco(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                    }
                    break;
                    case "FR": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                                idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "FR",
                                    incendioBean.getIncendio().getNumeroApolice(),
                                    incendioBean.getIncendio().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(incendioBean.getIncendio().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                               if(codigo.split(";")[0].equals("true"))
                                   regIncensdio(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        } else {
                            if(codigo.split(";")[0].equals("true"))
                                   regIncensdio(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                    }
                    break;
                    case "DH": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                                 idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "DH",
                                    dinheiroBean.getDinheiro().getNumApolice(),
                                    dinheiroBean.getDinheiro().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(), Integer.valueOf(dinheiroBean.getDinheiro().getMoeda()), null);
                            codigo = idContrato;
                            if (idContrato == null) {
                                break;
                            }
                            if(codigo.split(";")[0].equals("true"))
                                RegSeguroDinheiro(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        } else {
                              if(codigo.split(";")[0].equals("true"))
                                RegSeguroDinheiro(codigo);
                            else
                            {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                            }
                        }
                    }
                    break;
                    case "RP": {
                            if(codigo == null || !codigo.split(";")[0].equals("true")) {
                             System.out.println(responsabilidadePublicaBean.getRp().toString());
                             idContrato = cdd.registarContrato(this,
                                    session.getAttribute("CodigoCliente").toString(),
                                    "RP",
                                    responsabilidadePublicaBean.getRp().getNumeroApolice(),
                                    responsabilidadePublicaBean.getRp().getNumeroRegistro(),
                                    session.getAttribute("S").toString().split(";")[1],
                                    contrato.getPremioLiquido(),
                                    contrato.getPremioBruto(),
                                    Integer.valueOf(responsabilidadePublicaBean.getRp().getMoeda()), null);
                            codigo = idContrato; 
                            if (idContrato == null) {
                                break;
                            }
                              if(codigo.split(";")[0].equals("true"))
                                  regResponsabilidade(codigo);
                              else
                              {
                                    mensagem = codigo.split(";")[1];
                                    RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                    RequestContext.getCurrentInstance().execute("modalInfoError()");
                              }
                        } else {
                          if(codigo.split(";")[0].equals("true"))
                                regResponsabilidade(codigo);
                          else
                          {
                                mensagem = codigo.split(";")[1];
                                RequestContext.getCurrentInstance().update("contrato:infoApolice");
                                RequestContext.getCurrentInstance().execute("modalInfoError()");
                          }
                        }
                    }
                    break;

                }

            }
        }
    }

    @SuppressWarnings("IncompatibleEquals")
    public void RegContrato(String param) {
        System.out.println("entrou");
        try {
            registrarSeguro();
        } catch (IOException ex) {
            Logger.getLogger(ContratoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isTaxaDesativado() {
        return taxaDesativado;
    }

    public void dadosViagem() {
        codigoSeguro = "TIN";
        taxaDesativado = true;
        data1 = true;
        data2 = true;
        data3 = false;
        data4 = false;
        imposto = "true1;true2".split(";");
        nc = "true".split(";");
        ativarDesativarCampoFranquia = true;
        if (SessionUtil.obterValor("TIN") != null) {
            viagemBean = ((ViagemBean) SessionUtil.obterValor("TIN"));
            contrato.setTotalSegurado(viagemBean.getViagem().getTotalSegurado());
            contrato.setPremioBruto(viagemBean.getViagem().getPremioBruto());
            contrato.setPremioBrutoMoeda(viagemBean.getViagem().getPremioBrutoMoeda());
            contrato.setPremioLiquido(viagemBean.getViagem().getPremioLiquido());
            contrato.setPremioLiquidoMoeda(viagemBean.getViagem().getPremioLiquidoMoeda());
            contrato.setTotalSeguradoMoeda(viagemBean.getViagem().getTotalSeguradoMoeda());
            contrato.setValorNC(viagemBean.getViagem().getNc()+"");
            contrato.setSigla("EUR");
            contrato.setDataInicio(viagemBean.getViagem().getDataInicio());
            contrato.setDataFim(viagemBean.getViagem().getDataFim());
            ResultSet o = Call.selectFrom("VER_MOEDA where SIGLA = ?", "*", "EUR");
            if(o != null) {
                try {
                    o.next();
                    contrato.setMoeda(o.getString("ID"));
                } catch (SQLException ex)
                { Logger.getLogger(ContratoBean.class.getName()).log(Level.SEVERE, null, ex); }
            }
        }
    }

    public void DadosAcidentePG() {
       double premioLiquido, premioBruto;
        codigoSeguro = "GPA";
        imposto = "true1;true2".split(";");
        ativarDesativarCampoFranquia = true;
        taxaDesativado = true;
        if (SessionUtil.obterValor("GPA") != null) {
            acidentePGBean = ((AcidentePGBean) SessionUtil.obterValor("GPA"));
            premioBruto = Double.valueOf(acidentePGBean.getAcidentePG().getPremioLiquido());
            acidentePGBean.getAcidentePG().setPremioBruto(premioBruto+"");
            acidentePGBean.getAcidentePG().setPremioBrutoMoeda(Moeda.format(premioBruto));
            
            premioLiquido = Double.valueOf(acidentePGBean.getAcidentePG().getPremioLiquido()) * (1+ 0.056);
            acidentePGBean.getAcidentePG().setPremioLiquido(premioLiquido+"");
            acidentePGBean.getAcidentePG().setPremioLiquidoMoeda(Moeda.format(premioLiquido));
            
            lr = ((ListaRespostas) SessionUtil.obterValor("respostas"));
            contrato.setTotalSegurado(acidentePGBean.getAcidentePG().getLimiteResponsabilidade());
            contrato.setPremioBruto(acidentePGBean.getAcidentePG().getPremioBruto());
            contrato.setPremioBrutoMoeda(acidentePGBean.getAcidentePG().getPremioBrutoMoeda());
            contrato.setPremioLiquido(acidentePGBean.getAcidentePG().getPremioLiquido());
            contrato.setPremioLiquidoMoeda(acidentePGBean.getAcidentePG().getPremioLiquidoMoeda());
            contrato.setTotalSeguradoMoeda(acidentePGBean.getAcidentePG().getTotalSeguradoMoeda());
            contrato.setValorNC(acidentePGBean.getAcidentePG().getNc());
            contrato.setSigla(acidentePGBean.getAcidentePG().getSigla());
            contrato.setMoeda(acidentePGBean.getAcidentePG().getMoeda());
        }
    }

    public void dadosMultiRisco() {
        codigoSeguro = "MR";
        taxaDesativado = false;
        imposto = "true1;true2;".split(";");
        if (SessionUtil.obterValor("MR") != null) {
            multiRiscoBean = ((MultiRiscoBean) SessionUtil.obterValor("MR"));
            contrato.setTotalSeguradoMoeda(multiRiscoBean.getMultiRisco().getLimiteResponsabilidadeFormatado());
            contrato.setTotalSegurado(multiRiscoBean.getMultiRisco().getLimiteResponsabilidade());
            contrato.setSigla(Validacao.Sigla(multiRiscoBean.getMultiRisco().getMoeda()));
            contrato.setMoeda(multiRiscoBean.getMultiRisco().getMoeda());

            if (SessionUtil.obterValor("DI") != null) {
                rouboBean = ((RouboBean) SessionUtil.obterValor("DI"));
            }
            if (SessionUtil.obterValor("FR") != null) {
                incendioBean = ((IncendioBean) SessionUtil.obterValor("FR"));
            }
            if (SessionUtil.obterValor("DH") != null) {
                dinheiroBean = ((DinheiroBean) SessionUtil.obterValor("DH"));
            }
            if (SessionUtil.obterValor("respostas") != null) {
                lr = ((ListaRespostas) SessionUtil.obterValor("respostas"));
            }
        }
    }

    public void regMultiRisco(String idContrato) {
        String numeroContrato = null;
        if (incendioBean != null) {
            numeroContrato = contratoDao.registarContrato(this,
                    session.getAttribute("CodigoCliente").toString(),
                    "FR",
                    null,
                    null,
                    session.getAttribute("S").toString().split(";")[1],
                    incendioBean.getIncendio().getPremioLiquido(),
                    incendioBean.getIncendio().getPremioBruto(),
                    Integer.valueOf(multiRiscoBean.getMultiRisco().getMoeda()),
                    idContrato);
            try {
                regIncensdio(numeroContrato);
            } catch (IOException ex) {
                Logger.getLogger(ContratoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rouboBean != null) {
            numeroContrato = contratoDao.registarContrato(this,
                    session.getAttribute("CodigoCliente").toString(),
                    "DI",
                    null,
                    null,
                    session.getAttribute("S").toString().split(";")[1],
                    rouboBean.getRoubo().getPremioLiquido(),
                    rouboBean.getRoubo().getPremioBruto(),
                    Integer.valueOf(multiRiscoBean.getMultiRisco().getMoeda()),
                    ((incendioBean == null) ? idContrato : numeroContrato));
            regRoubo(numeroContrato);
        }
        if (dinheiroBean != null) {

            numeroContrato = contratoDao.registarContrato(this,
                    session.getAttribute("CodigoCliente").toString(),
                    "DH",
                    null,
                    null,
                    session.getAttribute("S").toString().split(";")[1],
                    dinheiroBean.getDinheiro().getPremioLiquido(),
                    dinheiroBean.getDinheiro().getPremioBruto(),
                    Integer.valueOf(multiRiscoBean.getMultiRisco().getMoeda()),
                    numeroContrato);
            RegSeguroDinheiro(numeroContrato);
        }
        if (numeroContrato != null) {
            mensagem = "Apolice " + multiRiscoBean.getMultiRisco().getNumeroApolice() + " registrado com sucesso";
//            multiRiscoBean = null;
            SessionUtil.removerParametro("MR");
            RequestContext.getCurrentInstance().update("contratoCon:info");
            RequestContext.getCurrentInstance().execute("abrirModal()");
        }
    }

    public void DadosRoubo() {
        codigoSeguro = "DI";
        taxaDesativado = false;
        imposto = "true1;true2;".split(";");
        ativarDesativarCampoFranquia = false;
        if (SessionUtil.obterValor("DI") != null) {
            rouboBean = ((RouboBean) SessionUtil.obterValor("DI"));
            lr = ((ListaRespostas) SessionUtil.obterValor("respostas"));
            contrato.setTotalSeguradoMoeda(rouboBean.getRoubo().getLimiteRspFormatado());
            contrato.setTotalSegurado(rouboBean.getRoubo().getLimiteRsp());
            contrato.setSigla(rouboBean.getRoubo().getSigla());
            contrato.setMoeda(rouboBean.getRoubo().getMoeda());
        }
    }

    public void regResponsabilidade(String idContrato) {
        String result = "";
        if(!MarcaDAgua.isSimulation)
        {
            SeguroRespPublicaDao publicaDao = new SeguroRespPublicaDao();
            lr.guardarRespostas(Integer.valueOf(idContrato.split(";")[1]));
            System.out.println(responsabilidadePublicaBean.getRp().toString());
            result = publicaDao.RegInfo(Integer.valueOf(idContrato.split(";")[1]), Float.parseFloat(responsabilidadePublicaBean.getRp().getSalarioDiretorColaborador()),
                    Float.parseFloat(responsabilidadePublicaBean.getRp().getSalarioSubempreiteiros()), Integer.valueOf(responsabilidadePublicaBean.getRp().getEstadoEdificio()),
                    responsabilidadePublicaBean.getCoberturas());
   
            if(result.equals("true")) 
            {
                for (int i = 0; i < responsabilidadePublicaBean.getInfo().size(); i++) {
                    result = publicaDao.RegObjResponsabilidadePublica(Integer.valueOf(idContrato.split(";")[1]), responsabilidadePublicaBean.getInfo().get(i).getEmpregado(),
                            responsabilidadePublicaBean.getInfo().get(i).getProfissao(), responsabilidadePublicaBean.getInfo().get(i).getEndereco());
                }
                if(!result.equals("true"))
                {
                    seguroDao.AnularContrato(Integer.valueOf(idContrato.split(";")[1]), "-1","Erro a registrar Seguro de Responsabilidade Pública", null, null);
                    Message.addErrorMsg("Houve um erro a registrar o contrato!", "contrato", "apoliceInfo");
                }
            }
            else
            {
                seguroDao.AnularContrato(Integer.valueOf(idContrato.split(";")[1]), "-1","Erro a registrar Seguro de Responsabilidade Pública", null, null);
                Message.addErrorMsg("Houve um erro a registrar o contrato!", "contrato", "apoliceInfo");
            }
        }
        if(result.equals("true") || MarcaDAgua.isSimulation) 
        {
            ArrayList<String[]> al = new ArrayList<>();
            @SuppressWarnings("UnusedAssignment")
            String[] dados = null;

            //para criar Docomento de Nota de Credito
            dados = (1 + ";" + "Seguro de Responsabilidade Publica\n" + contrato.getObservacao() + ";" + contrato.getPremioBrutoMoeda() + ";" + contrato.getPremioBrutoMoeda()).split(";");
            al.add(dados);
            RequestContext.getCurrentInstance().addCallbackParam("seguro", "rp");
            String reString = debito.docSeguros("Responsabilidade Publica", responsabilidadePublicaBean.getRp().getNumeroApolice(), SessionUtil.getUserlogado().getNomeAcesso(), session.getAttribute("CodigoCliente").toString(), " ", al, contrato, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio(), responsabilidadePublicaBean.getRp().getNumeroRegistro());
            System.err.println(reString + " FRe impresao");

            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");

            String retur = createDocPagamento();
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);
            if (!MarcaDAgua.isSimulation)
            {
                mensagem = "Apolice " + responsabilidadePublicaBean.getRp().getNumeroApolice() + " registrado com sucesso";
                RequestContext.getCurrentInstance().update("contratoCon:info");
                RequestContext.getCurrentInstance().execute("abrirModal()");
                ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), responsabilidadePublicaBean.getInfo().size(), 0, null, 1, null);
            }
            else
                ContratoDao.endRegInsurance(Integer.valueOf(idContrato.split(";")[1]), Integer.valueOf(getSelectSeguros()[1]), responsabilidadePublicaBean.getInfo().size(), 0, null, -1, null);
        }
    }

    public void DadosVeiculo() {
        codigoSeguro = "MV";
        imposto = "true1;true2;true3".split(";");
        ativarDesativarCampoFranquia = false;
        taxaDesativado = true;
        if (SessionUtil.obterValor("MV") != null) {
            veiculoBean = ((VeiculoBean) SessionUtil.obterValor("MV"));
            lr = ((ListaRespostas) SessionUtil.obterValor("respostas"));
            contrato.setTotalSegurado(veiculoBean.getVeiculo().getLimiteResp());
            contrato.setTotalSeguradoMoeda(veiculoBean.getVeiculo().getTotalSeguradoFormatado());
            contrato.setPremioBruto(veiculoBean.getVeiculo().getPremioBruto());
            contrato.setPremioBrutoMoeda(veiculoBean.getVeiculo().getPremioBrutoMoeda());
            contrato.setPremioLiquido(veiculoBean.getVeiculo().getPremioLiquido());
            contrato.setPremioLiquidoMoeda(veiculoBean.getVeiculo().getPremioLiquidoMoeda());
            contrato.setSigla(veiculoBean.getVeiculo().getSigla());
            contrato.setMoeda(veiculoBean.getVeiculo().getMoeda());
//            System.out.println("Contrato sigla " + veiculoBean.getVeiculo().getSigla());
        }
    }

    public void DadosMaritimo() {
        codigoSeguro = "NHI";
        imposto = "true1;true2;true3".split(";");
        taxaDesativado = true;
        ativarDesativarCampoFranquia = false;

        if (SessionUtil.obterValor("NHI") != null) {
            System.out.println("entrou maritimo");
            maritimoBean = ((MaritimoBean) SessionUtil.obterValor("NHI"));
            contrato.setTotalSegurado(maritimoBean.getMaritimo().getTotalSegurado());
            contrato.setPremioBruto(maritimoBean.getMaritimo().getPremioBruto());
            contrato.setPremioBrutoMoeda(maritimoBean.getMaritimo().getPremioBrutoMoeda());
            contrato.setPremioLiquido(maritimoBean.getMaritimo().getPremioLiquido());
            contrato.setPremioLiquidoMoeda(maritimoBean.getMaritimo().getPremioLiquidoMoeda());
            contrato.setTotalSeguradoMoeda(maritimoBean.getMaritimo().getTotalSeguradoMoeda());
            contrato.setSigla(maritimoBean.getMaritimo().getSigla());
            contrato.setMoeda(maritimoBean.getMaritimo().getMoeda());
            System.out.println("premio bruto " + maritimoBean.getMaritimo().getPremioBruto() + "\nPremio liquido " + maritimoBean.getMaritimo().getPremioLiquido());
        }
    }

    public boolean ValidarDataApolice() {
        boolean valido = true;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        System.out.println("data inicial " + contrato.getDataContrato());
        if (OperacaoData.compareDates(contrato.getDataInicio(), contrato.getDataFim()) == -1) {
            requestContext.execute("dataFinalInvalido()");
            valido = false;
        } else {
            requestContext.execute("dataFinalValido()");
        }
        if (OperacaoData.compareDates(contrato.getDataContrato(), contrato.getDataInicio()) == -1) {
            valido = false;
            requestContext.execute("dataContratoInvalido()");
        } else {
            requestContext.execute("dataContratoValido()");
        }
        return valido;
    }

    public boolean validarDataViagem() {
        boolean valido = true;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (OperacaoData.compareDates(contrato.getDataContrato(), contrato.getDataInicio()) == -1) {
            valido = false;
            requestContext.execute("dataContratoInvalido()");
        } else {
            requestContext.execute("dataContratoValido()");
        }
        return valido;
    }

    public boolean isAtivarDesativarDataInicio() {
        return ativarDesativarDataInicio;
    }

    public boolean isAtivarDesativarDataFim() {
        return ativarDesativarDataFim;
    }

    /**
     * Returna true se for válido e false caso contrário
     *
     * @param param
     * @return
     */
    public boolean SoNumero(String param) {
        boolean valido = true;
        try {
            Double.valueOf(param);
        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }

    public void PremioRoubo() {
        double valor = 0, totatSegurado = 0, premioLiqudio = 0;
        if ((contrato.getTaxa() != null && contrato.getTaxa().length() > 0) && (SoNumero(contrato.getTaxa()) == true) && (Double.valueOf(contrato.getTaxa()) <= 100)) {
            valor = Double.valueOf(contrato.getTaxa()) / 100;
            totatSegurado = Double.valueOf(contrato.getTotalSegurado());
            valor = totatSegurado * valor;
            contrato.setPremioBruto(String.valueOf(valor));
            contrato.setPremioBrutoMoeda(Moeda.format(valor));

        }
    }

    private void DadosCargaMaritima() {
        imposto = "true1;true2".split(";");
        ativarDesativarCampoFranquia = true;
        taxaDesativado = true;
        codigoSeguro = "MAC";
        if (SessionUtil.obterValor("MAC") != null) {
            cargaMaritimaBean = ((CargaMaritimaBean) SessionUtil.obterValor("MAC"));
            contrato.setTotalSegurado(cargaMaritimaBean.getCargaMaritima().getTotalSegurado());
            contrato.setPremioBruto(cargaMaritimaBean.getCargaMaritima().getPremioBruto());
            contrato.setPremioBrutoMoeda(cargaMaritimaBean.getCargaMaritima().getPremioBrutoMoeda());
            contrato.setPremioLiquido(cargaMaritimaBean.getCargaMaritima().getPremioLiquido());
            contrato.setPremioLiquidoMoeda(cargaMaritimaBean.getCargaMaritima().getPremioLiquidoMoeda());
            contrato.setTotalSeguradoMoeda(cargaMaritimaBean.getCargaMaritima().getTotalSeguradoMoeda());
            contrato.setSigla(Validacao.Sigla(cargaMaritimaBean.getCargaMaritima().getMoeda()));
            contrato.setMoeda(cargaMaritimaBean.getCargaMaritima().getMoeda());
            //contrato.setValorNC(cargaMaritimaBean.getCargaMaritima().getNc());
        }
    }

    private void regSeguroCargaMaritima(String codigo) {
        CargaMaritimaDao cmd = new CargaMaritimaDao();
        
        String resut = "";
        if(!MarcaDAgua.isSimulation)
        {
             resut = cmd.regInfoCargaMaritima(((Funcionario) SessionUtil.obterValor("utilizador")).getId().toString(), codigo, cargaMaritimaBean.getCargaMaritima());

            for (int i = 0; i < cargaMaritimaBean.getListaDetahlesVeiculo().size(); i++) {
                cmd.regDetalhesVeiculo(((Funcionario) SessionUtil.obterValor("utilizador")).getId().toString(), codigo, cargaMaritimaBean.getListaDetahlesVeiculo().get(i));
            }

            for (int i = 0; i < cargaMaritimaBean.getListaModoEmbalagemEInterresse().size(); i++) {
                cmd.regMoodoEmbalagem(((Funcionario) SessionUtil.obterValor("utilizador")).getId().toString(), codigo, cargaMaritimaBean.getListaModoEmbalagemEInterresse().get(i));
            }
        }
        
        if (resut.equals("true") || MarcaDAgua.isSimulation) {
            
            System.err.println("Carga Maritima Finalidada");
            mensagem = "Apolice " + cargaMaritimaBean.getCargaMaritima().getNumerApolice() + " registrado com sucesso";
            RequestContext.getCurrentInstance().addCallbackParam("seguro", "carga maritima");
//            SessionUtil.removerParametro("MAC");
            
            if( !MarcaDAgua.isSimulation)
            {
                RequestContext.getCurrentInstance().update("contratoCon:info");
                RequestContext.getCurrentInstance().execute("abrirModal()");
            }
            //-----
            ArrayList<String[]> al = new ArrayList<>();
            String[] dados = (1 + ";" + "Seguro Carga Maritiman\n" + contrato.getObservacao() + ";" + contrato.getPremioBrutoMoeda() + ";" + contrato.getPremioBrutoMoeda()).split(";");
            al.add(dados);
            String reString = debito.docSeguros("Carga Maritima", cargaMaritimaBean.getCargaMaritima().getNumerApolice(), SessionUtil.getUserlogado().getNomeAcesso(), session.getAttribute("CodigoCliente").toString(), " ", al, contrato, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio(), cargaMaritimaBean.getCargaMaritima().getNumeroRegistro());

            ImpresaoNoDeb = reString;
            RequestContext.getCurrentInstance().update("contrato:docNoDeb");
            //-------

            CertificadoCargaMaritima cargaMaritima = new CertificadoCargaMaritima();

            String reString1 = cargaMaritima.criarDoc(cargaMaritimaBean.getCargaMaritima().getNumerApolice(), session.getAttribute("CodigoCliente").toString(), contrato, cargaMaritimaBean, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), contrato.getSigla(), getDiretorio());

            ImpresaoCert = reString1;
            RequestContext.getCurrentInstance().update("contrato:docCer");
            //-------

            SeguroCargaMaritima seguroCargaMaritima = new SeguroCargaMaritima();
            String reString2 = seguroCargaMaritima.criarDoc(cargaMaritimaBean.getCargaMaritima().getNumerApolice(), session.getAttribute("CodigoCliente").toString(), contrato, cargaMaritimaBean, ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), getDiretorio());

            ImpresaoForm = reString2;
            RequestContext.getCurrentInstance().update("contrato:docForm");
            //-------

            String retur = createDocPagamento();
            RequestContext.getCurrentInstance().addCallbackParam("imprimir1", retur);

        }

    }

    public void adicionarContrato() {
        int i = 0;
        if (validarData() == true) {
            if (contrato.getDataContrato() == null) {
                i = 1;
            } else {
                if (OperacaoData.compareDates(contrato.getDataInicio(), contrato.getDataContrato()) > 0) {
                    i = 1;
                    mensagem = "Data inicial tem que ser inferior ou igual a data registro de contrato";
                    RequestContext.getCurrentInstance().update("contrato:infoApolice");
                    RequestContext.getCurrentInstance().execute("modalInfoError()");
                }
                if (OperacaoData.compareDates(contrato.getDataContrato(), contrato.getDataFim()) < 0) {
                    i = 1;
                    mensagem = "Data de contrato tem que ser inferior a data final";
                    RequestContext.getCurrentInstance().update("contrato:infoApolice");
                    RequestContext.getCurrentInstance().execute("modalInfoError()");
                }
            }
            if (contrato.getDataRenovacao() != null) {
                if (OperacaoData.compareDates(contrato.getDataFim(), contrato.getDataRenovacao()) == -1) {
                    i = 1;
                    mensagem = "Data de renovação não pode ser inferior a data de fim do contrato";
                    RequestContext.getCurrentInstance().update("contrato:infoApolice");
                    RequestContext.getCurrentInstance().execute("modalInfoError()");
                }

            }
            if (contrato.getDataFim() != null) {
                if (OperacaoData.compareDates(contrato.getDataInicio(), contrato.getDataFim()) == -1) {
                    i = 1;
                    mensagem = "Data de fim do contrato tem que ser superior ou igual a data final";
                    RequestContext.getCurrentInstance().update("contrato:infoApolice");
                    RequestContext.getCurrentInstance().execute("modalInfoError()");
                }
            }
            if (contrato.getDataRenovacao() != null) {
                if (OperacaoData.compareDates(contrato.getDataFim(), contrato.getDataRenovacao()) == -1) {
                    i = 1;
                    mensagem = "Data de renovação tem que ser superior a data final";
                    RequestContext.getCurrentInstance().update("contrato:infoApolice");
                    RequestContext.getCurrentInstance().execute("modalInfoError()");
                }
            }
            if (i == 0) {
                if (codigoSeguro.equals("DI") || codigoSeguro.equals("DH") || codigoSeguro.equals("MR")) {
                    RequestContext.getCurrentInstance().execute("verificarTaxa()");
                    if (contrato.getTaxa() != null && !contrato.getTaxa().equals("")) {
                        if (contrato.getPremioBruto() != null && contrato.getPremioLiquido() != null) {
                            RequestContext.getCurrentInstance().execute("showModalPagemento()");
                        } else {
                            Message.addWarningMsg("Por favor, verifique os dos Premios!", "contrato", "apoliceInfo");
                        }
                    }
                } else {
                    SessionUtil.removerParametro("PREMIO");
                    RequestContext.getCurrentInstance().execute("showModalPagemento()");
                }
            }
        }
    }

    public boolean validarTaxa() {
        boolean valido = true;
        if ((contrato.getTaxa() != null && contrato.getTaxa().length() > 0) && SoNumero(contrato.getTaxa()) == true && (Double.valueOf(contrato.getTaxa()) <= 100)) {
            contrato.setTaxa("");
            valido = false;
            RequestContext.getCurrentInstance().update("contrato:contratoTaxa");
        }
        return valido;

    }

    public void premioRouboDinheiro() {
        @SuppressWarnings("UnusedAssignment")
        double taxa = 0, premioBruto = 0, premioLiquido = 0, premioBrutoRoubo = 0, premioLiquidoRoubo = 0, premioBrutoDinheiro = 0, premioLiquidoDinheiro = 0;
        float percentagemRetirar = 100 - (5 + 0.6f);
        if ((codigoSeguro != null && codigoSeguro.length() > 0) && (codigoSeguro.equals("DI") || codigoSeguro.equals("DH") || codigoSeguro.equals("MR"))) {
            if ((contrato.getTotalSegurado() != null && contrato.getTotalSegurado().length() > 0) && (contrato.getTaxa() != null && contrato.getTaxa().length() > 0) && (SoNumero(contrato.getTaxa()) == true)) {
                if (Double.valueOf(contrato.getTaxa()) <= 100) {
                    taxa = Double.valueOf(contrato.getTaxa()) / 100;
                    if (codigoSeguro.equalsIgnoreCase("MR")) {
                        if (rouboBean != null) {
                            premioLiquido = Double.valueOf(rouboBean.getRoubo().getLimiteRsp()) * taxa; // premio liquido de roubo e multrisco
                            premioLiquidoRoubo = Double.valueOf(rouboBean.getRoubo().getLimiteRsp()) * taxa;
                            premioBrutoRoubo = (percentagemRetirar * premioLiquidoRoubo) / 100;
                            premioBruto = (percentagemRetirar * premioLiquidoRoubo) / 100;
                            rouboBean.getRoubo().setPremioBruto(String.valueOf(premioBrutoRoubo));
                            rouboBean.getRoubo().setPremioLiquido(String.valueOf(premioLiquidoRoubo));
                        }
                        if (dinheiroBean != null) {
                            premioLiquido += Double.valueOf(dinheiroBean.getDinheiro().getTotalSegurado()) * taxa;
                            premioLiquidoDinheiro = Double.valueOf(dinheiroBean.getDinheiro().getTotalSegurado()) * taxa;
                            premioBrutoDinheiro = (percentagemRetirar * premioLiquidoDinheiro) / 100;
                            premioBruto += (percentagemRetirar * premioLiquidoDinheiro) / 100;
                            dinheiroBean.getDinheiro().setPremioBruto(String.valueOf(premioBrutoDinheiro));
                            dinheiroBean.getDinheiro().setPremioLiquido(String.valueOf(premioLiquidoDinheiro));
                        }

                        premioBruto += ((multiRiscoBean.getMultiRisco().getPremioBruto() == null || multiRiscoBean.getMultiRisco().getPremioBruto().equals("")) ? 0 : Double.valueOf(multiRiscoBean.getMultiRisco().getPremioBruto()));
                        premioLiquido += ((multiRiscoBean.getMultiRisco().getPremioLiquido() == null || multiRiscoBean.getMultiRisco().getPremioLiquido().equals("")) ? 0 : Double.valueOf(multiRiscoBean.getMultiRisco().getPremioLiquido()));
                        contrato.setPremioBruto(String.valueOf(premioBruto));
                        contrato.setPremioLiquido(String.valueOf(premioLiquido));
                        contrato.setPremioBrutoMoeda(Moeda.format(premioBruto));
                        contrato.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
                    } else {
                        premioLiquido = Double.valueOf(contrato.getTotalSegurado()) * taxa;
                        premioBruto = (percentagemRetirar * premioLiquido) / 100;
                        contrato.setPremioBruto(String.valueOf(premioBruto));
                        contrato.setPremioLiquido(String.valueOf(premioLiquido));
                        contrato.setPremioBrutoMoeda(Moeda.format(premioBruto));
                        contrato.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
                    }
                    RequestContext.getCurrentInstance().update("contrato:premioBruto");
                    RequestContext.getCurrentInstance().update("contrato:premioLiquido");
                } else {
                    contrato.setTaxa("");
                    contrato.setPremioBrutoMoeda("");
                    contrato.setPremioLiquidoMoeda("");
                    RequestContext.getCurrentInstance().update("contrato:premioBruto");
                    RequestContext.getCurrentInstance().update("contrato:premioLiquido");
                    RequestContext.getCurrentInstance().update("contrato:contratoTaxa");
                }
            } else {
                contrato.setTaxa("");
                contrato.setPremioBrutoMoeda("");
                contrato.setPremioLiquidoMoeda("");
                RequestContext.getCurrentInstance().update("contrato:premioBruto");
                RequestContext.getCurrentInstance().update("contrato:premioLiquido");
                RequestContext.getCurrentInstance().update("contrato:contratoTaxa");
            }
        }
    }

    public void premioMultiRisco() {
        double premioBruto, premioLiquido;
        if (codigoSeguro != null && !codigoSeguro.equals("") && codigoSeguro.equals("MR")) {
            premioBruto = ((multiRiscoBean.getMultiRisco().getPremioBruto() == null || multiRiscoBean.getMultiRisco().getPremioBruto().equals("")) ? 0 : Double.valueOf(multiRiscoBean.getMultiRisco().getPremioBruto()));
            premioLiquido = ((multiRiscoBean.getMultiRisco().getPremioLiquido() == null || multiRiscoBean.getMultiRisco().getPremioLiquido().equals("")) ? 0 : Double.valueOf(multiRiscoBean.getMultiRisco().getPremioLiquido()));
            premioBruto = Double.valueOf(contrato.getPremioBruto()) + premioBruto;
            premioLiquido = Double.valueOf(contrato.getPremioLiquido()) + premioLiquido;
            contrato.setPremioBruto(String.valueOf(premioBruto));
            contrato.setPremioLiquido(String.valueOf(premioLiquido));
            contrato.setPremioBrutoMoeda(Moeda.format(premioBruto));
            contrato.setPremioLiquidoMoeda(Moeda.format(premioLiquido));
        }
    }

    public void DadosIncendio() {
        codigoSeguro = "FR";
        imposto = "true1;true2;".split(";");
        taxaDesativado = true;
        if (SessionUtil.obterValor("FR") != null) {
            lr = ((ListaRespostas) SessionUtil.obterValor("respostas"));
            incendioBean = ((IncendioBean) SessionUtil.obterValor("FR"));
            contrato.setTotalSegurado(incendioBean.getIncendio().getTotalSegurado());
            contrato.setTotalSeguradoMoeda(incendioBean.getIncendio().getTotalSeguradoFormatado());
            contrato.setPremioBruto(incendioBean.getIncendio().getPremioBruto());
            contrato.setPremioBrutoMoeda(incendioBean.getIncendio().getPremioBrutoFormatado());
            contrato.setPremioLiquido(incendioBean.getIncendio().getPremioLiquido());
            contrato.setPremioLiquidoMoeda(incendioBean.getIncendio().getPremioLiquidoFormatado());
            contrato.setSigla(Validacao.Sigla(incendioBean.getIncendio().getMoeda()));
            contrato.setMoeda(incendioBean.getIncendio().getMoeda());
        }
    }

    public String redirect() {
        SessionUtil.removerParametro("CodigoCliente");
        if (SessionUtil.obterValor("GPA") != null) {
            SessionUtil.removerParametro("GPA");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor("NHI") != null) {
            SessionUtil.removerParametro("NHI");
        }
        if (SessionUtil.obterValor("TIN") != null) {
            SessionUtil.removerParametro("TIN");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor("MAC") != null) {
            SessionUtil.removerParametro("MAC");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor("DI") != null) {
            SessionUtil.removerParametro("DI");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor("MV") != null) {
            SessionUtil.removerParametro("MV");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor("RP") != null) {
            SessionUtil.removerParametro("RP");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor("FR") != null) {
            SessionUtil.removerParametro("FR");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor("DH") != null) {
            SessionUtil.removerParametro("DH");
            SessionUtil.removerParametro("respostas");
        }
        if (SessionUtil.obterValor(SessionUtil.APOLICE_CONTRATO_SEGURO) != null) {
            SessionUtil.removerParametro(SessionUtil.APOLICE_CONTRATO_SEGURO);
        }

        return "GestaoContratos.xhtml?faces-redirect=true";
    }

    public String voltal() {
        return "GestSeg_NovoSeguro.xhtml?faces-redirect=true";
    }

    public boolean validarData() {
        boolean valido = true;
        int i = 0;
        if (contrato.getDataInicio() == null) {
            i = 1;
            valido = false;
        }
        if (i == 0) {
            if (contrato.getDataFim() == null) {
                valido = false;
            }
        }
        return valido;
    }

    public void DadosDinheiro() {
        codigoSeguro = "DH";
        taxaDesativado = false;
        if (SessionUtil.obterValor("DH") != null) {
            imposto = "true1;true;".split(";");
            ativarDesativarCampoFranquia = false;
            dinheiroBean = ((DinheiroBean) SessionUtil.obterValor("DH"));
            contrato.setTotalSegurado(dinheiroBean.getDinheiro().getTotalSegurado());
            contrato.setTotalSeguradoMoeda(dinheiroBean.getDinheiro().getTotalSeguradoFormatado());
            contrato.setSigla(dinheiroBean.getDinheiro().getSigla());
            contrato.setMoeda(dinheiroBean.getDinheiro().getMoeda());
        }
    }

    public ResponsabilidadePublicaBean getResponsabilidadePublicaBean() {
        return responsabilidadePublicaBean;
    }

    public void setResponsabilidadePublicaBean(ResponsabilidadePublicaBean responsabilidadePublicaBean) {
        this.responsabilidadePublicaBean = responsabilidadePublicaBean;
    }

    public void DadosResponsabilidadePublica() {
        codigoSeguro = "RP";
        if (SessionUtil.obterValor("respostas") != null) {
            lr = ((ListaRespostas) SessionUtil.obterValor("respostas"));
        }
        if (SessionUtil.obterValor("RP") != null) {
            imposto = "true1;true2;".split(";");
            taxaDesativado = true;
            ativarDesativarCampoFranquia = false;
            responsabilidadePublicaBean = ((ResponsabilidadePublicaBean) SessionUtil.obterValor("RP"));
            contrato.setSigla(Validacao.Sigla(responsabilidadePublicaBean.getRp().getMoeda()));
            contrato.setTotalSegurado(responsabilidadePublicaBean.getRp().getTotalSegurado());
            contrato.setTotalSeguradoMoeda(responsabilidadePublicaBean.getRp().getTotalSeguradoFormatado());
            contrato.setPremioBruto(responsabilidadePublicaBean.getRp().getPremioBruto());
            contrato.setPremioBrutoMoeda(responsabilidadePublicaBean.getRp().getPremioBrutoMoeda());
            contrato.setPremioLiquido(responsabilidadePublicaBean.getRp().getPremioLiquido());
            contrato.setPremioLiquidoMoeda(responsabilidadePublicaBean.getRp().getPremioLiquidoMoeda());
            contrato.setSigla(Validacao.Sigla(responsabilidadePublicaBean.getRp().getMoeda()));
            contrato.setMoeda(responsabilidadePublicaBean.getRp().getMoeda());

        }
    }

    public String getDiretorio() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
        String arquivo = scontext.getRealPath("/Documentos/");
        return arquivo;
    }

    public String[] dadosForPagamento(int s) {

        if (SessionUtil.obterValor("MR") != null) {
            return constroArray(multiRiscoBean.getMultiRisco().getNumeroApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("GPA") != null) {
            return constroArray(acidentePGBean.getAcidentePG().getNumeroApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("NHI") != null) {
            return constroArray(maritimoBean.getMaritimo().getNumeroApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("TIN") != null) {
            return constroArray(viagemBean.getViagem().getNumApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("MAC") != null) {
            return constroArray(cargaMaritimaBean.getCargaMaritima().getNumerApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("DI") != null) {
            return constroArray(rouboBean.getRoubo().getNumeroApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("MV") != null) {
            return constroArray(veiculoBean.getVeiculo().getNumeroApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("RP") != null) {
            return constroArray(responsabilidadePublicaBean.getRp().getNumeroApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("FR") != null) {
            return constroArray(incendioBean.getIncendio().getNumeroApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        if (SessionUtil.obterValor("DH") != null) {
            return constroArray(dinheiroBean.getDinheiro().getNumApolice(), SessionUtil.obterValor("CodigoCliente"));
        }
        return constroArray("Sem Numero de Apolice", "43");
    }

    public String[] constroArray(String numApolice, Object numCliente) {
        return (numApolice + ";:;" + ClienteDao.getDadosCliente(numCliente + "", "NOME")).split(";:;");
    }

    public void constroListaPagamento() {
        listaModeloPagamento = new ArrayList<>();
        ModeloPagamento mp = new ModeloPagamento();
        if (modeloPagamento.getPrestacao() > 0 && (modeloPagamento.getPrestacao() <= modeloPagamento.getPrazo())) {
            Double valor = (Double.valueOf(contrato.getPremioLiquido()) / modeloPagamento.getPrestacao());
            mp.setValorPagameto(valor);
            mp.setDataPagamentoLimite(contrato.getDataInicio());
            listaModeloPagamento.add(new ModeloPagamento(mp));

            for (int i = 0; i < (modeloPagamento.getPrestacao() - 1); i++) {
                mp.setValorPagameto(valor);
                mp.setDataPagamentoLimite(nextDate(listaModeloPagamento.get(i).getDataPagamentoLimite()));
                listaModeloPagamento.add(new ModeloPagamento(mp));
                if (OperacaoData.compareDates(contrato.getDataFim(), listaModeloPagamento.get(listaModeloPagamento.size() - 1).getDataPagamentoLimite()) > 0) {
                    listaModeloPagamento = new ArrayList<>();
                    Mensagem.addWarningMsg("O prazo inserido faz com que data pagamento ultrapasse a data Final do seguro (" + sdfPt.format(contrato.getDataFim()) + ")!");
                    Validacao.AtualizarCompoente("contrato", "apoliceInfo");
                    break;
                }

            }
        } else {
            Mensagem.addWarningMsg("Por favor, digite um prazo maior que a pestação!");
            Validacao.AtualizarCompoente("contrato", "apoliceInfo");
        }

        Validacao.AtualizarCompoente("pagamentoForm", "pagamentoTable");
    }

    public Date nextDate(Date dateAtual) {
        try {
            String A = "", M = "", D = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String[] ff = format.format(dateAtual).split("-");
            int a, m, d;
            {
                a = Integer.valueOf(ff[0]);
                m = Integer.valueOf(ff[1]);
                d = Integer.valueOf(ff[2]);
            }
            int i;
            for (i = 0; i < ((modeloPagamento.getPrazo() / modeloPagamento.getPrestacao())); i++) {
                d = d + 1;
                switch (m) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        if (d > 31) {
                            d = 1;
                            m++;
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if (d > 30) {
                            d = 1;
                            m++;
                        }
                        break;
                    default:
                        if (a % 400 == 0 && a % 4 == 0 && a % 100 != 0) {
                            if (d > 28) {
                                d = 1;
                                m++;
                            }
                        } else {
                            if (d > 29) {
                                d = 1;
                                m++;
                            }
                        }
                        break;
                }
                if (m > 12) {
                    m = 1;
                    a++;
                    d = 1;
                }

                A = a + "";
                M = m + "";
                D = d + "";
                if (M.length() < 2) {
                    M = "0" + m;
                }
                if (D.length() < 2) {
                    D = "0" + d;
                }
            }
            return format.parse(A + "-" + M + "-" + D);
        } catch (ParseException ex) {
            Logger.getLogger(ContratoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String createDocPagamento() {
        if(!MarcaDAgua.isSimulation) { contratoDao.regPestacao(codigo.split(";")[1], listaModeloPagamento); }
        ModalidadePagamentoPremio mpp = new ModalidadePagamentoPremio();
        String re = mpp.criarDoc(dadosForPagamento(1)[0], SessionUtil.obterValor("CodigoCliente").toString(),
                SessionUtil.getUserlogado().getNomeAcesso(), getDiretorio(), nomeSeguro, listaModeloPagamento, contrato.getSigla());
        return re;
    }

    public void beforeRegContrato() {
        if (listaModeloPagamento != null && listaModeloPagamento.size() > 0) {
            try {
                registrarSeguro();
            } catch (IOException ex) {
                Logger.getLogger(ContratoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Mensagem.addWarningMsg("Por favor, defina a modalidade de pagamento!");
            Validacao.AtualizarCompoente("contrato", "apoliceInfo");
        }
    }
    public void simulate()
    {
        MarcaDAgua.isSimulation = true;
         if (listaModeloPagamento != null && listaModeloPagamento.size() > 0) 
         {
             try
             {
                  switch (session.getAttribute("S").toString().split(";")[0]) 
                  {
                      case "TIN":// se o codigo do contrato for MV
                         regViagem(codigo); 
                       break; 
                      case "MAC":  { regSeguroCargaMaritima(codigo); break; }
                      case "NHI": {  regSeguroMaritimo(codigo); break; }
                      case "GPA": { regAcidenteGrupo(codigo); break; }
                      case "DI": { regRoubo(codigo); break; }
                      case "MV": { regVeiculo(codigo); break; }
                      case "MR": { regMultiRisco(codigo); break; }
                      case "FR": {  regIncensdio(codigo); break; }
                      case "DH": { RegSeguroDinheiro(codigo); break; }
                      case "RP": { regResponsabilidade(codigo); break; }
                  }
              } 
             catch (Exception e) { System.err.println(e); }
        } 
        else 
             Message.addWarningMsg("Por favor, defina a modalidade de pagamento.", "contrato", "apoliceInfo");
  
        MarcaDAgua.isSimulation = false;
    }
    

}
