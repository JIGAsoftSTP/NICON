/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Export.GenericExcel;
import Export.GenericPDFs;
import dao.ContratoDao;
import dao.SinistroDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Message;
import modelo.AcidentePG;
import modelo.CargaMaritima;
import modelo.ComoBox;
import modelo.DataContrato;
import modelo.Dinheiro;
import modelo.Incendio;
import modelo.Maritimo;
import modelo.Pagamento;
import modelo.Relatorio;
import modelo.ResponsabilidadePublica;
import modelo.Roubo;
import modelo.Sinistro;
import modelo.Veiculo;
import modelo.Viagem;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;


import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author AhmedJorge
 */
@ManagedBean
@ViewScoped
public class SinistroBean implements Serializable
{
    private ArrayList<DataContrato> listContrato;
    private DataContrato selectedContrato;
    private String pesquisa;
    private Sinistro sinistro;
    private ComoBox testemunha;
    private ComoBox hipoteca;
    private ArrayList<ComoBox> listHipoteca;
    private ComoBox seletedHipoteca;
    private Relatorio relatorio = new Relatorio();
    private ResultSet rs;
    private DataTableControl sinistroControl, mapaPagamento;
    private Pagamento pagamento;
    private HashMap<String, Object> map;
    private String typeInfo = "Dados do Contrato";
    private String justificacao;
    private List<Viagem> dadosSeguradoViagem;
    private List<Veiculo> dadosSeguradoVeiculo;
    private List<AcidentePG> dadosSeguradoAcidentePG;
    private List<Roubo> dadosSeguradoRoubo;
    private List<Maritimo> dadosSeguradoMaritimo;
    private List<Incendio> dadosSeguradoIncendio;
    private List<CargaMaritima> dadosSeguradoCargamaritima;
    private List<Dinheiro> dadosSeguradoDinheiro;
    private List<ResponsabilidadePublica> dadosSeguradoResponsabilidadePublica;
    private Viagem coberturaViagem;
    private boolean anulado;

    
    @SuppressWarnings("LeakingThisInConstructor")
    public SinistroBean() 
    {
        if( SessionUtil.obterValor(SESSION_SINISTRO) != null && Validacao.paginaAtual().contains("Registro") )
        {
            SinistroBean sb = ((SinistroBean) SessionUtil.obterValor(SESSION_SINISTRO));
            for (Field f : this.getClass().getDeclaredFields())
            {
                try  { f.setAccessible(true); f.set(this, f.get(sb));  }
                catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) { }
                }
            SessionUtil.removerParametro(SESSION_SINISTRO);
            }
        else
        {
            listContrato = SinistroDao.listContrato(null);
            mapaPagamento = new  DataTableControl(pesquisa, "sinistroBean.mapaPagamento");
            if(Validacao.paginaAtual().contains("Relatorio"))
            {
                this.sinistroControl = new DataTableControl("sinistroTabelaRelatorio", "sinistroBean.sinistroControl");
                rs = SinistroDao.relatorioSinistro(relatorio);
                this.sinistroControl.updFaces(FacesContext.getCurrentInstance());
                this.sinistroControl.prepareModel(rs, DataTableControl.ShowMode.HIDE,"ID","ESTADO_ESTADO","SEGURO");
                this.sinistroControl.renameColumn("LOCAL OCORENCIA", "LOCAL DE OCOR.");
                this.sinistroControl.renameColumn("LOCAL INSPENSAO", "LOCAL DE INSPEÇÃO");
                this.sinistroControl.renameColumn("DATA INSPECAO", "DATA DE INSPEÇÃO");
                this.sinistroControl.renameColumn("DATA", "OCORRÊNCIA");
                this.sinistroControl.setFilter((mapf,i)-> 
                {   
                    if(mapf.get("ESTADO_ESTADO").equals("0") && anulado ) { return true; }
                    else if(!mapf.get("ESTADO_ESTADO").equals("0") && !anulado ) { return true; }
                    else { return false; }
                });
                    }
            else if(Validacao.paginaAtual().contains("MapaPgto"))
            {
                this.mapaPagamento = new DataTableControl("tabelaMapaPagamento", "sinistroBean.sinistroControl");
                rs = SinistroDao.relatorioMapaPagamento(relatorio);
                this.mapaPagamento.updFaces(FacesContext.getCurrentInstance());
                this.mapaPagamento.prepareModel(rs, DataTableControl.ShowMode.SHOWALL);
                RequestContext.getCurrentInstance().execute("$('.firstField').html('"+mapaPagamento.getRowMap(mapaPagamento.getListaValue().size() - 1).get("VALOR") +"')");
                mapaPagamento.getListaValue().remove(mapaPagamento.getListaValue().size()-1);
            }
        }
    }

    public ArrayList<DataContrato> getListContrato() {
        return (listContrato == null) ? listContrato = new ArrayList<>() : listContrato;
    }

    public void setListContrato(ArrayList<DataContrato> listContrato) {
        this.listContrato = listContrato;
    }

    public Relatorio getRelatorio() {
        return (relatorio == null) ? relatorio = new Relatorio() : relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public DataContrato getSelectedContrato() {
        return (selectedContrato == null) ? selectedContrato = new DataContrato() : selectedContrato;
    }

    public void setSelectedContrato(DataContrato selectedContrato) {
        this.selectedContrato = selectedContrato;
    }

    public void selecionarContrato(DataContrato contrato)
    {
        selectedContrato = new DataContrato(contrato);
        sinistro.setApolice(selectedContrato.getApolice());
        sinistro.setContratoID(selectedContrato.getId());
        sinistro.setSeguro(sinistro.getNameSeguro(selectedContrato.getSeguro()));
        if(!selectedContrato.getSeguro().equals("MV"))
        {
            sinistro.setNumChassi("");
            sinistro.setNumVeiculo("");
        }
        Validacao.atualizar("sinistro","sApolice","sSeguro","sNumChassi","sNumVeiculo");
        RequestContext.getCurrentInstance().execute("$('.modalFrameSin').fadeOut();");
    }

    public void selecionaContrato()
    {
        selecionarContrato(selectedContrato);
    }

    public Sinistro getSinistro() {
        return (sinistro == null ) ? sinistro = new Sinistro() : sinistro;
    }

    public void setSinistro(Sinistro sinistro) {
        this.sinistro = sinistro;
    }

    public ComoBox getTestemunha() {
        return (testemunha == null) ? testemunha = new ComoBox() : testemunha;
    }

    public void setTestemunha(ComoBox testemunha) {
        this.testemunha = testemunha;
    }

    public ComoBox getHipoteca() {
        return (hipoteca == null) ? hipoteca = new ComoBox() : hipoteca;
    }

    public void setHipoteca(ComoBox hipoteca) {
        this.hipoteca = hipoteca;
    }

    public ComoBox getSeletedHipoteca() {
        return (seletedHipoteca == null) ? seletedHipoteca = new ComoBox() : seletedHipoteca;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public Viagem getCoberturaViagem() {
        return coberturaViagem;
    }

    
    public void setSeletedHipoteca(ComoBox seletedHipoteca) {
        this.seletedHipoteca = seletedHipoteca;
    }

    public ArrayList<ComoBox> getListHipoteca() {
        return  (listHipoteca == null) ? listHipoteca = new ArrayList<>() : listHipoteca;
    }

    public void setListHipoteca(ArrayList<ComoBox> listHipoteca) {
        this.listHipoteca = listHipoteca;
    }

    public Pagamento getPagamento() {
        return (pagamento == null) ? pagamento = new Pagamento() : pagamento ;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public DataTableControl getSinistroControl() {
        return (sinistroControl == null ) ? sinistroControl = new DataTableControl("sinistroTabelaRelatorio", "sinistroBean.sinistroControl") : sinistroControl;
    }

    public void setSinistroControl(DataTableControl sinistroControl) {
        this.sinistroControl = sinistroControl;
    }

    public String getJustificacao() {
        return justificacao;
    }

    public void setJustificacao(String justificacao) {
        this.justificacao = justificacao;
    }

    public boolean isAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    public void pesquisarC(int t)
    {
        if(t == 0 && pesquisa.isEmpty())
        {
            listContrato = SinistroDao.listContrato(getPesquisa());
            RequestContext.getCurrentInstance().execute("$('.processamento').show()");
            Validacao.atualizar("table", "tableContrato");
        }
        else if(t == 1)
        {
            listContrato = SinistroDao.listContrato(getPesquisa());
            Validacao.atualizar("table", "tableContrato");
        }
    }
    int i = 0;
    public void addHipoteca()
    {
        i++;
        if( !hipoteca.getValue().isEmpty() && !hipoteca.getCodigoNicon().isEmpty())
        {
            getListHipoteca().add( new ComoBox( hipoteca.getId() , hipoteca.getValue(), hipoteca.getCodigoNicon()));
            hipoteca.setValue("");
            hipoteca.setCodigoNicon("");
            Validacao.atualizar("sinistro", "sHEndereco", "sHNomeIntere", "tableHipoteca");
        }
        else
        {  RequestContext.getCurrentInstance().execute("testeHipoteca()"); }
    }

    public void validacaoHipoteca()
    {
        if(sinistro.getHipoteca().equals("false"))
        {
            listHipoteca = new ArrayList<>();
            Validacao.atualizar("sinistro", "tableHipoteca");
        }
        RequestContext.getCurrentInstance().execute("validarHipoteca()");
    }
    public void validacaoTestemunha()
    {
        if(sinistro.getTestemunha().equals("false"))
        {
            System.err.println("false");
            testemunha.setValue("");
            testemunha.setCodigoNicon("");
            testemunha.setDestalhes("");
            Validacao.atualizar("sinistro", "sTesEndereco", "sTesTelefone","sTesNome");
        }
        RequestContext.getCurrentInstance().execute("validarTestemunha()");
    }

    public void removeAndEditeHipoteca(int i,ComoBox cb)
    {
        switch (i) {
            case 1:
            {
                hipoteca.setValue(cb.getValue());
                hipoteca.setCodigoNicon(cb.getCodigoNicon());
                hipoteca.setId(cb.getId());
                Validacao.atualizar("sinistro", "sHEndereco", "sHNomeIntere");
                listHipoteca.remove(cb);
                break;
            }
            case 2:{listHipoteca.remove(cb);break;}
            case 3:{listHipoteca.clear();break;}
            }
        Validacao.atualizar("sinistro", "tableHipoteca");
    }

    public void messagem(int f)
    {
        switch (f) 
        {
            case 1: Message.addWarningMsg( "Selecione um Contrato!", "sinistro", "mesagemSinistro"); break;
            case 2: Message.addWarningMsg( "Adicione dados de Hipoteca!", "sinistro", "mesagemSinistro"); break;
            case 3: Message.addInfoMsg(((sinistro.getId() == null || sinistro.getId().isEmpty()) ? "Sinistro registrado com sucessso!" : "Sinistro editado com sucessso" ), "sinistro", "mesagemSinistro");  break;
            default: break;
        }
    }

    boolean erro = false;
    public void regSinistro()
    {

        System.out.println(sinistro);
        Object o = SinistroDao.regSinistro(sinistro);
        String[] arr = SinistroDao.toString(o).split(";");
        if("true".equals(arr[0]))
        {
            if("true".equals(sinistro.getHipoteca())) 
            { 
                listHipoteca.stream().forEach((cb) -> 
                { 
                    Object regHipoteca = SinistroDao.regHipoteca(cb, arr[1]);
                    String[] hip = SinistroDao.toString(regHipoteca).split(";");
                    if(hip[0].equals("false"))
                    {
                        erro = true;
                        Message.addErrorMsg(hip[1], "sinistro", "mesagemSinistro");
                    }
                });

            }
            if("true".equals(sinistro.getTestemunha())) 
            {
                Object regTes = SinistroDao.regTestemunha(testemunha, arr[1]);
                String[] tes = SinistroDao.toString(regTes).split(";");
                if(tes[0].equals("false"))
                {
                    erro = true;
                    Message.addErrorMsg(tes[1], "sinistro", "mesagemSinistro");
                }
            }
            if(!erro) 
            {
                messagem(3);
                RequestContext.getCurrentInstance().execute("limparDadosSinistro()");
                removeAndEditeHipoteca(3, null);
            }
        } else {
            Message.addErrorMsg(arr[1], "sinistro", "mesagemSinistro");
        }
    }

    public void showMaisInfrorm(DataContrato contrato)
    {
        dadosSeguradoViagem = new ArrayList<>();
        selectedContrato = new DataContrato(contrato);
        sinistro.setSeguro(selectedContrato.getSeguro());
        sinistro.setContratoID(selectedContrato.getId());
        RequestContext.getCurrentInstance().execute("mostarInfoSinistro()");
        map = SinistroDao.getDadosContrato(Integer.valueOf(sinistro.getContratoID()));
        dados();
//        RequestContext.getCurrentInstance().execute("sinistroDadosContrato('"+map.get(SinistroDao.APOLICE_CONTRATO)+"','"+map.get(SinistroDao.DATA_INICIO)+"','"+map.get(SinistroDao.DATA_FIM)+"','"+map.get(SinistroDao.CLIENTE_CONTRATO)+"','"+map.get(SinistroDao.VALOR_SEGURADO)+"','"+map.get(SinistroDao.PREMIO_BRUTO)+"','"+map.get(SinistroDao.EXCESSO)+"','"+map.get(SinistroDao.ESTADO_PAGAMENTO)+"','"+map.get(SinistroDao.ESTADO_CONTRATO)+"','"+map.get(SinistroDao.MOEDA)+"')");

    }

    public void pesquisaSinistro()
    {
        System.out.println(relatorio.toString());
        rs = SinistroDao.relatorioSinistro(relatorio);
        this.sinistroControl.updFaces(FacesContext.getCurrentInstance());
        this.sinistroControl.prepareModel(rs, DataTableControl.ShowMode.HIDE,"ID","ESTADO_ESTADO");
        this.sinistroControl.renameColumn("LOCAL OCORENCIA", "LOCAL DE OCORRÊNCIA");
        this.sinistroControl.renameColumn("LOCAL INSPENSAO", "LOCAL DE INSPEÇÃO");
        this.sinistroControl.renameColumn("DATA INSPECAO", "DATA DE INSPEÇÃO");
        this.sinistroControl.renameColumn("CLIENTTE", "CLIENTE");
        this.sinistroControl.setFilter((mapf,i)-> 
        {   
            if(mapf.get("ESTADO_ESTADO").equals("0") && anulado ) { return true; }
            else return !mapf.get("ESTADO_ESTADO").equals("0") && !anulado;
        });
        Validacao.AtualizarCompoente("formRelatorioSinistro", "sinistroTabelaRelatorio");
    }

    public void reportSinistro(int i)
    {
//        rs = SinistroDao.relatorioSinistro(relatorio);
        if(i == 1)
        { GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Sinistro", "Relatório de Sinistro", sinistroControl, GenericPDFs.OrientacaoPagina.HORIZONTAL,-1); }
        else
        { GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Sinistro", "Relatório de Sinistro", sinistroControl,-1); }
        }

    public void regPagamento()
    {
//        System.err.println("fhfhfhfhfh Sinistro");
        pagamento.setValor(pagamento.getValor().replace(" ", ""));
        pagamento.setValor(pagamento.getValor().replace(",", "."));
        pagamento.setNumero(sinistroControl.getSelededRowMap().get("ID"));
        Object o = SinistroDao.regPagamento(pagamento);
        String[] rep = SinistroDao.toString(o).split(";");
        if(!rep[0].equals("true"))
        { Message.addErrorMsg(rep[1], "contaForm", "mesagemP"); }
        else
        { 
            Message.addInfoMsg("Solicitação de pagamento registrado com sucesso!", "contaForm", "mesagemP");
            RequestContext.getCurrentInstance().execute("$('.modalPagamentoS').fadeOut()");
            RequestContext.getCurrentInstance().execute("$('.processamento').hide()");
            RequestContext.getCurrentInstance().execute("$('.ContaPagF').find('input:text, textarea').val('')");
            pesquisaSinistro();
            envioNotificacaoPagamentoSolicitados();
        }
      
        
   
    }

    public void showModalPagamento()
    {
        if(sinistroControl.getSelededRowMap() != null)
            {  
                if(sinistroControl.getSelededRowMap().get("ESTADO_ESTADO").equals("1") 
                || sinistroControl.getSelededRowMap().get("ESTADO_ESTADO").equals("2"))
                { RequestContext.getCurrentInstance().execute("$('.modalPagamentoS').css('display','flex')"); }
                else
                { Message.addErrorMsg("selecione um sinistro que esteja pendente ou pago!", "contaForm", "mesagemP"); }
            }
            else
        { Message.addErrorMsg("Selecione um registro na tabela!", "contaForm", "mesagemP"); }
        }

    public void desativeModalPagamento(int i)
    {
        if(sinistroControl.getSelededRowMap() != null)
        {
            if(i==0)
            {
                Object o = SinistroDao.desatPagamento(sinistroControl.getSelededRowMap().get("ID"),justificacao);
                String[] rep = SinistroDao.toString(o).split(";");
                if(!rep[0].equals("true"))
                { Message.addErrorMsg(rep[1], "contaForm", "mesagemP"); }
                else
                { 
                    Message.addInfoMsg("O registro foi anulado com sucesso!", "contaForm", "mesagemP");
                    RequestContext.getCurrentInstance().execute("$('.anular').hide()");
                    RequestContext.getCurrentInstance().execute("$('.justText').val('')");
                    pesquisaSinistro();
                }
            }
            else
            { 
                
                if(sinistroControl.getSelededRowMap().get("ESTADO_ESTADO").equals("1"))
                { RequestContext.getCurrentInstance().execute("$('.anular').css('display','flex')"); }
                else
                { Message.addErrorMsg("selecione um sinistro que esteja pendente!", "contaForm", "mesagemP"); }
                }
            }
        else
        { Message.addErrorMsg("Selecione um registro na tabela!", "contaForm", "mesagemP"); }
        }

    public void pesquisaMapaPagamento()
    {
        rs = SinistroDao.relatorioMapaPagamento(relatorio);
        this.mapaPagamento.updFaces(FacesContext.getCurrentInstance());
        this.mapaPagamento.prepareModel(rs, DataTableControl.ShowMode.SHOWALL);
        Validacao.atualizar("formMapapagamento", "tabelaMapaPagamento");
        mapaPgamentoValorTotal();
    }

    private void mapaPgamentoValorTotal()
    {
        String currency ="", result;
        int finish = 0;
        double value = 0;
        if(mapaPagamento.getListaValue().size()>0)
        {
            if(mapaPagamento.getRowMap(mapaPagamento.getListaValue().size()-1).get("APOLICE").equalsIgnoreCase("total"))
            {
                finish = mapaPagamento.getListaValue().size()-1;
                mapaPagamento.getListaValue().remove(mapaPagamento.getListaValue().size()-1);
            }
            else
                finish = mapaPagamento.getListaValue().size();

            for (int index = 0;index<finish; index++) 
            {
                currency = mapaPagamento.getRowMap(index).get("VALOR").substring(mapaPagamento.getRowMap(index).get("VALOR").length()-3, mapaPagamento.getRowMap(index).get("VALOR").length());
                result = mapaPagamento.getRowMap(index).get("VALOR").substring(0, mapaPagamento.getRowMap(index).get("VALOR").length()-3);    
                value += Validacao.unformatValue(result);
            }
            result = Moeda.format(value)+" "+currency;
            RequestContext.getCurrentInstance().execute("$('.firstField').html('"+result+"')"); 
        }
        else
        {
            result = "";
            RequestContext.getCurrentInstance().execute("$('.firstField').html('"+result+"')"); 
        }


                     
    }

    public void exportMapaPagamento(int tipo)
    {
        if(tipo == 1)
            GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de mapa de pagamento", "Relatório de mapa de pagamento", mapaPagamento, GenericPDFs.OrientacaoPagina.HORIZONTAL,-1); 
        else
            GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de mapa de pagamento", "Relatório de mapa de pagamento", mapaPagamento,-1); 
        }

    @Override
    public String toString() {
        return "SinistroBean{" + "listContrato=" + listContrato + ", selectedContrato=" + selectedContrato + ", pesquisa=" + pesquisa + ", sinistro=" + sinistro + ", testemunha=" + testemunha + ", hipoteca=" + hipoteca + ", listHipoteca=" + listHipoteca + ", seletedHipoteca=" + seletedHipoteca + ", relatorio=" + relatorio + ", rs=" + rs + ", sinistroControl=" + sinistroControl + ", mapaPagamento=" + mapaPagamento + ", pagamento=" + pagamento + ", i=" + i + ", erro=" + erro + '}';
    }
    static String SESSION_SINISTRO = "SINISTRO";
    public void editarSinistro()
    {
        if(sinistroControl.getSelededRowMap() != null)
        {
            if(sinistroControl.getSelededRowMap().get("ESTADO_ESTADO").equals("1"))
            {
                SinistroBean sb = SinistroDao.getSinistro(sinistroControl.getSelededRowMap().get("ID"));
                SessionUtil.definirParametro(SESSION_SINISTRO, sb);
                Validacao.redirecionar("Registro.xhtml");
            }
            else 
                Message.addWarningMsg("Só pode editar dados de um registro com estado pendente!", "contaForm", "mesagemP"); 
        }
        else
           Message.addErrorMsg("Selecione um registro na tabela!", "contaForm", "mesagemP"); 
    }

    public List<Viagem> getDadosSeguradoViagem() {
        return dadosSeguradoViagem;
    }

    public List<Veiculo> getDadosSeguradoVeiculo() {
        return dadosSeguradoVeiculo;
    }

    public List<AcidentePG> getDadosSeguradoAcidentePG() {
        return dadosSeguradoAcidentePG;
    }

    public List<Roubo> getDadosSeguradoRoubo() {
        return dadosSeguradoRoubo;
    }

    public List<Maritimo> getDadosSeguradoMaritimo() {
        return dadosSeguradoMaritimo;
    }

    public List<Incendio> getDadosSeguradoIncendio() {
        return dadosSeguradoIncendio;
    }

    public List<CargaMaritima> getDadosSeguradoCargamaritima() {
        return dadosSeguradoCargamaritima;
    }

    public List<Dinheiro> getDadosSeguradoDinheiro() {
        return dadosSeguradoDinheiro;
    }

    public List<ResponsabilidadePublica> getDadosSeguradoResponsabilidadePublica() {
        return dadosSeguradoResponsabilidadePublica;
    }

    
    
    
    
    
    public void dados()
    {
        typeInfo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("typeInfo") == null) ? typeInfo : FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("typeInfo");
        switch(typeInfo)
        {
            case "Dados do Contrato":
                map = SinistroDao.getDadosContrato(Integer.valueOf(sinistro.getContratoID()));
                 RequestContext.getCurrentInstance().execute("sinistroDadosContrato('"+map.get(SinistroDao.APOLICE_CONTRATO)+"','"+map.get(SinistroDao.DATA_INICIO)+"','"+map.get(SinistroDao.DATA_FIM)+"','"+map.get(SinistroDao.CLIENTE_CONTRATO)+"','"+map.get(SinistroDao.VALOR_SEGURADO)+"','"+map.get(SinistroDao.PREMIO_BRUTO)+"','"+map.get(SinistroDao.EXCESSO)+"','"+map.get(SinistroDao.ESTADO_PAGAMENTO)+"','"+map.get(SinistroDao.ESTADO_CONTRATO)+"','"+map.get(SinistroDao.MOEDA)+"')");
                break;
            case "Dados do Segurado":
                infoSegurado();
                break;
        }
    }

    private void infoSegurado()
    {
        ContratoDao contratoDao = new ContratoDao();
        dadosSeguradoViagem  = new ArrayList<>();
        dadosSeguradoAcidentePG = new ArrayList<>();
        dadosSeguradoVeiculo  = new ArrayList<>();
        dadosSeguradoRoubo = new ArrayList<>();
        dadosSeguradoMaritimo = new ArrayList<>();
        dadosSeguradoIncendio = new ArrayList<>();
        dadosSeguradoCargamaritima =  new ArrayList<>();
        dadosSeguradoDinheiro = new ArrayList<>();
        dadosSeguradoResponsabilidadePublica = new ArrayList<>();

        switch(sinistro.getSeguro())
        {
            case "TIN":
                dadosSeguradoViagem = (List<Viagem>) contratoDao.loadDataContratoViagem(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro())[0];
                Validacao.atualizar("table", "SeguradoViagem");
                break;
            case "GPA":
                dadosSeguradoAcidentePG = (List<AcidentePG>) contratoDao.loadDataContratoAPG(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro());
                Validacao.atualizar("table", "SeguradoAcidentePG");
                break;
            case "MV":
                dadosSeguradoVeiculo = (List<Veiculo>) contratoDao.loadDataContratoVeiculo(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro());
                Validacao.atualizar("table", "SeguradoVeiculo");
                break;
            case "DI":
                dadosSeguradoRoubo = (List<Roubo>) contratoDao.loadDataContratoRoubo(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro())[0];
                Validacao.atualizar("table", "SeguradoRoubo");
                break;
            case "NHI":
                dadosSeguradoMaritimo = new ArrayList<>();
                dadosSeguradoMaritimo.add(contratoDao.loadDataContratoMaritimo(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro()));
                Validacao.atualizar("table", "SeguradoMaritimo");
                break;
            case "FR":
                dadosSeguradoIncendio.add(contratoDao.dadosSeguradoIncendio(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro()));
                Validacao.atualizar("table", "SeguradoIncendio");
                break;
            case "MAC":
                dadosSeguradoCargamaritima.add(contratoDao.loadDataContratoCargaMaritima(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro()));
                Validacao.atualizar("table", "SeguradoCargaMaritima");
                break;
            case "DH":
                dadosSeguradoDinheiro.add(contratoDao.dadosSeguradoDinheiro(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro()));
                Validacao.atualizar("table", "SeguradoDinheiro");
                break;
            case "RP":
                dadosSeguradoResponsabilidadePublica = contratoDao.dadosSeguradoResponsabilidadePublica(Integer.valueOf(sinistro.getContratoID()), sinistro.getSeguro());
                Validacao.atualizar("table", "SeguradoResponsabilidade");
                break;

        }
    }

    public void moreInfoReport()
    {
        testemunha.setCodigoNicon(null);
        SinistroBean sinistroBean = SinistroDao.dadosSinistro(sinistroControl.getSelededRowMap().get("ID"), this);
        testemunha = sinistroBean.getTestemunha();
        sinistro = sinistroBean.getSinistro();
        sinistro.setCliente(sinistroControl.getSelededRowMap().get("CLIENTTE"));
        sinistro.setEstado(sinistroControl.getSelededRowMap().get("ESTADO"));
        sinistro.setDataRegistro(sinistroControl.getSelededRowMap().get("REGISTRO"));
        sinistro.setDataOcorrencia(sinistroControl.getSelededRowMap().get("DATA"));
        sinistro.setDataInspecao(sinistroControl.getSelededRowMap().get("DATA INSPECAO"));
        RequestContext.getCurrentInstance().update("sinistroRelatorioMaisInfo");
        RequestContext.getCurrentInstance().execute("sinistroRelatorio('"+sinistro.getSeguro()+"','"+((testemunha.getCodigoNicon() == null)? "false" : "true")+"')");
        RequestContext.getCurrentInstance().execute("$('.mp-infoTable').fadeIn()");
    }

    private void envioNotificacaoPagamentoSolicitados()
    {
        System.out.println("===============enviando nova notificação============\n....Solicitação de pagamento....");
        EventBus eventBus =EventBusFactory.getDefault().eventBus();
        eventBus.publish("/notify", new FacesMessage("Notificação", "nova"));
      
       
    }
    
}
