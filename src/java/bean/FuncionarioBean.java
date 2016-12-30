/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import modelo.Funcionario;
import dao.ClienteDao;
import dao.FuncionarioDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mensagem.Mensagem;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Empresa;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author AhmedJorge
 */
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable
{
    private List<ComoBox> sexos;
    private List<ComoBox> estadoCivis;
    private List<ComoBox> categoriaFunc;
    private List<ComoBox> listaAcesso;
    private List<Funcionario> listaFunc;
    private List<ComoBox> listaBank;
    private List<ComoBox> listFuncNoUser;
    private List<ComoBox> listaDepartamentos;
    private ClienteDao clienteDao  = new ClienteDao();
    private FuncionarioDao fdao = new FuncionarioDao();
    private Funcionario funcionarioDados = new Funcionario();
    private Funcionario funcionarioConjuge = new Funcionario();
    private Funcionario funcionarioUilizador;
    private Funcionario funcionarioInfo;
    private Funcionario funcionarioV;
    private Funcionario conjugueV;
    private Empresa empresa;
    private ComoBox dados = new ComoBox();
    private String confimeSenha;
    private boolean validoEmail=true;
    private boolean validoNIF=true;
    private boolean validoNomeAcesso=true;
    private String pesquisa;
    private String tipoOperacao = "Registro";
    private String nivelAtual, novoNivel;
    private List<ComoBox> filtroNivel = new ArrayList<>();
    private Funcionario funcionarioSelecionado = new Funcionario();
    private String idepartamento;
    private boolean justUser;
    private Ferias feriasFuncionario;
    private String justSupersao;
    private final ArrayList<ComoBox> niveis;
    
    public FuncionarioBean() {
        sexos = new ArrayList<>();
        estadoCivis = new ArrayList<>(); 
        listaDepartamentos = new ArrayList<>();
        estadoCivis = clienteDao.listaEstados();
        sexos = clienteDao.listaSexos();
        categoriaFunc = fdao.ListFucionarioCategoria();
        listaFunc = fdao.ListFucionario(null, funcionarioDados.isState(), justUser);
        listFuncNoUser = fdao.ListFucionarioNoUser();
        listaDepartamentos = ComoBox.loadCombo("VER_DEPARTAMENTO", "ID", "DEPARTAMENTO");
        listaBank=fdao.BancoLista();
        listaAcesso = fdao.nivelAcesso();
        niveis = ComoBox.loadCombo("VER_LEVELCATEGORIA", "ID", "CATEGORIA");
    }

    public List<ComoBox> getSexos() {
        return sexos = ((sexos==null)? sexos= new ArrayList<>():sexos);
    }

    public void setSexos(List<ComoBox> sexos) {
        this.sexos = sexos;
    }

    public ArrayList<ComoBox> getNiveis() {
        return niveis;
    }

    public ComoBox getDados() {
        return dados;
    }

    public void setTipoOperacao(String tipoOperacao,Funcionario fS) {
        funcionarioSelecionado = fS;
        this.tipoOperacao = tipoOperacao;
        if(funcionarioSelecionado.getId() != null && !funcionarioSelecionado.getId().equals(""))
            if(this.tipoOperacao.contains("ativar"))
                RequestContext.getCurrentInstance().execute("modalAtivarDesativar()");
            else
               RequestContext.getCurrentInstance().execute("modalRedifinirSenha()"); 
    }
    
    private int idNivelAcesso(String nivel)
    {
        for(ComoBox cb: listaAcesso)
        {
            if(cb.getValue().equals(nivel))
                return Integer.valueOf(cb.getId());
        }
        return 0;
    }
    public void setDados(ComoBox dados) {
        this.dados = dados;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public List<ComoBox> getEstadoCivis() {
        return estadoCivis =((estadoCivis==null)? estadoCivis= new ArrayList<>():estadoCivis);
    }

    public Funcionario getFuncionarioSelecionado() {
        return (funcionarioSelecionado == null)?funcionarioSelecionado = new Funcionario() :funcionarioSelecionado;
    }

    public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
        this.funcionarioSelecionado = funcionarioSelecionado;
    }

    public String getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(String nivelAtual) {
        this.nivelAtual = nivelAtual;
    }

    public String getNovoNivel() {
        return novoNivel;
    }

    public void setNovoNivel(String novoNivel) {
        this.novoNivel = novoNivel;
    }
    
    
    public List<ComoBox> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setEstadoCivis(List<ComoBox> estadoCivis) {
        this.estadoCivis = estadoCivis;
    }

    public Funcionario getFuncionarioDados() {
        return  ((funcionarioDados==null)? funcionarioDados= new Funcionario():funcionarioDados);
    }

    public void setFuncionarioDados(Funcionario funcionarioDados) {
        this.funcionarioDados = funcionarioDados;
    }

    public Funcionario getFuncionarioConjuge() {
        return ((funcionarioConjuge==null)? funcionarioConjuge= new Funcionario():funcionarioConjuge);
    }

    public void setFuncionarioConjuge(Funcionario funcionarioConjuge) {
        this.funcionarioConjuge = funcionarioConjuge;
    }

    public Empresa getEmpresa() {
        return ((empresa==null)? empresa= new Empresa():empresa);
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<ComoBox> getCategoriaFunc() {
        return ((categoriaFunc==null)?categoriaFunc = new ArrayList<>():categoriaFunc);
    }

    public void setCategoriaFunc(List<ComoBox> categoriaFunc) {
        this.categoriaFunc = categoriaFunc;
    }

    public List<Funcionario> getListaFunc() {
        return ((listaFunc==null)? listaFunc = new ArrayList<>():listaFunc);
    }
    
    public void setListaFunc(List<Funcionario> listaFunc) {
        this.listaFunc = listaFunc;
    }

    public Funcionario getFuncionarioUilizador() {
        return (funcionarioUilizador == null) ? funcionarioUilizador = new Funcionario() : funcionarioUilizador;
    }

    public void setFuncionarioUilizador(Funcionario funcionarioUilizador) {
        this.funcionarioUilizador = funcionarioUilizador;
    }

    public String getPesquisa() {
        return pesquisa;
    }
    
    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public String getConfimeSenha() {
        return confimeSenha;
    }

    public void setConfimeSenha(String confimeSenha) {
        this.confimeSenha = confimeSenha;
    }

    public List<ComoBox> getListaBank() {
        return (listaBank==null)?listaBank = new ArrayList<>():listaBank;
    }

    public void setListaBank(List<ComoBox> listaBank) {
        this.listaBank = listaBank;
    }

    public boolean isValidoEmail() {
        return validoEmail;
    }

    public boolean isJustUser() {
        return justUser;
    }

    public void setJustUser(boolean justUser) {
        this.justUser = justUser;
    }
    
    public void setValidoEmail(boolean validoEmail) {
        this.validoEmail = validoEmail;
    }

    public boolean isValidoNIF() {
        return validoNIF;
    }

    public void setValidoNIF(boolean validoNIF) {
        this.validoNIF = validoNIF;
    }

    public boolean isValidoNomeAcesso() {
        return validoNomeAcesso;
    }

    public void setValidoNomeAcesso(boolean validoNomeAcesso) {
        this.validoNomeAcesso = validoNomeAcesso;
    }

    public List<ComoBox> getListFuncNoUser() {
        return ((listFuncNoUser==null)? listFuncNoUser = new ArrayList<>():listFuncNoUser );
    }

    public void setListFuncNoUser(List<ComoBox> listFuncNoUser) {
        this.listFuncNoUser = listFuncNoUser;
    }

    public List<ComoBox> getListaAcesso() {
        return ((listaAcesso==null)? new ArrayList<>():listaAcesso);
    }

    public void setListaAcesso(List<ComoBox> listaAcesso) {
        this.listaAcesso = listaAcesso;
    }

    public Ferias getFeriasFuncionario() {
        return (feriasFuncionario == null) ? feriasFuncionario = new Ferias() : feriasFuncionario;
    }

    public void setFeriasFuncionario(Ferias feriasFuncionario) {
        this.feriasFuncionario = feriasFuncionario;
    }

    public String getJustSupersao() {
        return justSupersao;
    }

    public void setJustSupersao(String justSupersao) {
        this.justSupersao = justSupersao;
    }

    public Funcionario getFuncionarioV() {
        return (funcionarioV == null) ? funcionarioV = new Funcionario() : funcionarioV;
    }

    public void setFuncionarioV(Funcionario funcionarioV) {
        this.funcionarioV = funcionarioV;
    }

    public Funcionario getConjugueV() {
        return (conjugueV == null) ? conjugueV = new Funcionario() : conjugueV;
    }

    public void setConjugueV(Funcionario conjugueV) {
        this.conjugueV = conjugueV;
    }
    
    public boolean validarCodigoNicon()
    {
        boolean valido = true;
        int result = fdao.verificarCodigoNicon(empresa.getCodigoNicon());
        if(result ==-1)
        {
            Mensagem.addErrorMsg("Código Nicon já existe!");
            RequestContext.getCurrentInstance().update("formFuncSima:funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("funcionariocodigoBorda()");
            valido = false;
        }
        return valido;
    }
    
    public void regFuncionarioPasso2()
    {
        System.out.println(funcionarioDados);
        System.out.println(funcionarioConjuge);
        System.out.println(empresa);
        if(!tipoOperacao.equals("Editar"))
        {
            if(validarCodigoNicon() == true)
            {
                 String reString= fdao.registrarFuncionario(funcionarioDados,(((Funcionario)SessionUtil.obterValor("utilizador")).getId()),empresa);
                if(reString.split(";")[0].equals("true"))
                {
                    String reString1 = fdao.registrarFuncionarioConta(empresa, (((Funcionario)SessionUtil.obterValor("utilizador")).getId()), reString.split(";")[1]);

                    if((funcionarioConjuge.getNome()!=null && !funcionarioConjuge.getNome().equals("")) &&
                       (funcionarioConjuge.getApelido() != null && !funcionarioConjuge.getApelido().equals(""))&&
                        (funcionarioConjuge.getDataNascimento()!=null)&& (funcionarioConjuge.getEstadoCivil() != null && !funcionarioConjuge.getEstadoCivil().equals("")))
                    {
                         fdao.registrarFuncionarioDependente(funcionarioConjuge, (((Funcionario)SessionUtil.obterValor("utilizador")).getId()), reString.split(";")[1]);
                    }
                    Mensagem.addInfoMsg("Novo funcionário registrado com êxito");
                    RequestContext.getCurrentInstance().update("formFuncSima:funcionarioGrowl");
                    RequestContext.getCurrentInstance().execute("PartefuncioLimpar()");
                    RequestContext.getCurrentInstance().execute("anterior()");
                    listaFunc= fdao.ListFucionario(null, false, false);
                    listFuncNoUser = fdao.ListFucionarioNoUser();
                    Validacao.AtualizarCompoente("formFuncTabela", "tabelaFuncionario");
                    Validacao.AtualizarCompoente("FunUtil", "FunUtilLis");     
                }   
            }  
        }
        else
        {
            System.out.println("entrou atualizar");
              if((funcionarioConjuge.getNome()!=null && !funcionarioConjuge.getNome().equals("")) &&
                   (funcionarioConjuge.getApelido() != null && !funcionarioConjuge.getApelido().equals(""))&&
                    (funcionarioConjuge.getDataNascimento()!=null)&& (funcionarioConjuge.getEstadoCivil() != null && !funcionarioConjuge.getEstadoCivil().equals("")))
                {
                    fdao.registrarFuncionarioConta(empresa, (((Funcionario)SessionUtil.obterValor("utilizador")).getId()),funcionarioSelecionado.getId().toString());
                    fdao.atualizarFuncionario(funcionarioDados,empresa,Integer.valueOf(funcionarioSelecionado.getId().toString()));
                    fdao.registrarFuncionarioDependente(funcionarioConjuge, SessionUtil.getUserlogado().getId().toString(), funcionarioSelecionado.getId().toString());
                    fdao.alterarNivel(SessionUtil.getUserlogado().getId().toString(), funcionarioSelecionado.getId().toString(),
                            idNivelAcesso(fdao.nivelAtual(funcionarioSelecionado.getId().toString())[0]), Integer.valueOf(empresa.getDepartamento()));
                    Mensagem.addInfoMsg("Funcionário atualizado com êxito!");
                    RequestContext.getCurrentInstance().update("formFuncSima:funcionarioGrowl");
                    RequestContext.getCurrentInstance().execute("PartefuncioLimpar()");
                    RequestContext.getCurrentInstance().execute("anterior()");
                    tipoOperacao = "Registro";
              }
              else
              {
                    fdao.registrarFuncionarioConta(empresa, (((Funcionario)SessionUtil.obterValor("utilizador")).getId()),funcionarioSelecionado.getId().toString());
                    fdao.atualizarFuncionario(funcionarioDados,empresa,Integer.valueOf(funcionarioSelecionado.getId().toString()));
                   fdao.alterarNivel(SessionUtil.getUserlogado().getId().toString(), funcionarioSelecionado.getId().toString(),
                            idNivelAcesso(fdao.nivelAtual(funcionarioSelecionado.getId().toString())[0]), Integer.valueOf(empresa.getDepartamento()));
                    Mensagem.addInfoMsg("Funcionário atualizado com êxito!");
                    RequestContext.getCurrentInstance().update("formFuncSima:funcionarioGrowl");
                    RequestContext.getCurrentInstance().execute("PartefuncioLimpar()");
                    RequestContext.getCurrentInstance().execute("anterior()");
                    tipoOperacao = "Registro";
              }
   
            
        }
       
    }
    
    public void regUilizador()
    {
        System.out.println(funcionarioUilizador);
        if (validarNomeAcesso())
        {
            String dd = fdao.registrarUtilizacao(funcionarioUilizador, (((Funcionario) SessionUtil.obterValor("utilizador")).getId()));
            String[] ar = dd.split(";");
            if(ar[0].equals("true"))
            {
                listFuncNoUser = fdao.ListFucionarioNoUser();
                listaFunc =fdao.ListFucionario(pesquisa, funcionarioDados.isState(),this.isJustUser());
                RequestContext.getCurrentInstance().update("FunUtil:FunUtilLis");
                RequestContext.getCurrentInstance().update("formFuncTabela:tabelaFuncionario");
                Mensagem.addInfoMsg("Novo Utilizador Adicionado");
                RequestContext.getCurrentInstance().execute("limpar()");
                RequestContext.getCurrentInstance().update("formFuncSima:funcionarioGrowl");
            }
            else
            {
                Mensagem.addInfoMsg(ar[1]);
                RequestContext.getCurrentInstance().update("formFuncSima:funcionarioGrowl");
            }
            
        }
    }
    
    public void validarEmail()
    {
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
 
        if(funcionarioDados.getEmail() != null && !funcionarioDados.getEmail().equals(""))
        {
             Matcher m = p.matcher(funcionarioDados.getEmail()); 
            if (!m.find())
            {
                validoEmail = false;
                Mensagem.addErrorMsg("Email inválido");
                Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
                RequestContext.getCurrentInstance().execute("soCor($('.fucP1Email'),'red')");
            }  
            else
            {
                validoEmail = true;
                Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
                RequestContext.getCurrentInstance().execute("soCor($('.fucP1Email'),'')");
            }
        }
        else
        {
            validoEmail = true;
        }
        Validacao.AtualizarCompoente("formFuncSima", "fucP1ValideEmail");
    }
    
    public void regFuncionarioPasso1()
    {
        validarEmail();
        validarNif();
        RequestContext.getCurrentInstance().execute("Parte1Proximo()");
    }
    
    public void validarNif()
    {
        System.out.println("tipo de operação "+funcionarioDados.getTypeOperation());
        fdao= new FuncionarioDao();
        if(fdao.exinteNifFucionario(funcionarioDados.getNif()) && !tipoOperacao.equals("Editar"))
        {
            Mensagem.addErrorMsg("NIF já existe!");
            Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("soCor($('.fucP1Nif'),'red')");
            RequestContext.getCurrentInstance().execute("funcionarioNifFoco()");
            validoNIF=false;
        }
        else
        {
            Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("soCor($('.fucP1Nif'),'')");
            validoNIF= true;
        }
        Validacao.AtualizarCompoente("formFuncSima", "fucP1ValideNIF");
    }   
    
    public boolean validarNomeAcesso()
    {
        if(fdao.exinteNomeAcessoFucionario(funcionarioUilizador.getNomeAcesso()) && !tipoOperacao.equals("Editar"))
        {
            Mensagem.addErrorMsg("Nome Usuario Já Foi usado!!");
            Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("soCor($('.FunUtilName'),'red')");
            validoNomeAcesso=false;
            Validacao.AtualizarCompoente("formFuncSima", "fucP1ValideNomeAcess");
            return false;
        }
        else
        {
            Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("soCor($('.FunUtilName'),'')");
            validoNomeAcesso=true;
            Validacao.AtualizarCompoente("formFuncSima", "fucP1ValideNomeAcess");
            return true;
        }
    }  
    
    public void testeNomeAcesso()
    {
        System.out.println(funcionarioUilizador.getNomeAcesso());
        validarNomeAcesso();
    }
    
    public void pesquisar(int p)
    {
        listaFunc =fdao.ListFucionario(pesquisa, funcionarioDados.isState(),this.isJustUser());
    }
    
    public void pegarCodigNicon()
    {
        if(getFuncionarioUilizador().getNome() != null
                && !getFuncionarioUilizador().getNome().equals(""))
        {   
            for(ComoBox cb: listFuncNoUser)
            {
                if(cb.getId().equals(getFuncionarioUilizador().getNome()))
                {
                    getFuncionarioUilizador().setNomeAcesso(cb.getCodigoNicon());
                    Validacao.AtualizarCompoente("FunUtil", "nomeAcesso");
                    break;
                }
            }
        }
        else{
            System.err.println("vasio!");
        }
    }
    
    public void redifinirSenha()
    {
        if(funcionarioSelecionado.getId() != null && !funcionarioSelecionado.getId().equals("") && SessionUtil.obterValor("utilizador") != null)
        {
            this.fdao.redefinirSenha(funcionarioSelecionado.getId().toString(),"F","U");
            Mensagem.addInfoMsg("Palavra-passe redifinida com sucesso!");
            Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("modalFecharOperacao()");  
            listaFunc =fdao.ListFucionario(pesquisa, funcionarioDados.isState(),this.isJustUser());
            RequestContext.getCurrentInstance().update("formFuncTabela:tabelaFuncionario");
        }
    }
    
    public void ativarDesativar()
    {
       if(funcionarioSelecionado.getId() != null && !funcionarioSelecionado.getId().equals("") && SessionUtil.obterValor("utilizador") != null)
       {
            this.fdao.redefinirSenha(funcionarioSelecionado.getId().toString(), ((!justUser) ? (!funcionarioDados.isState()) ? "D" : "A" : (!funcionarioDados.isState()) ? "D" : "F") , (justUser) ? "U" : "OP" );
            Mensagem.addInfoMsg("Estado do funcionário alterado com sucesso!");
            Validacao.AtualizarCompoente("formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("modalFecharOperacao()");
            listaFunc =fdao.ListFucionario(pesquisa, funcionarioDados.isState(),this.isJustUser());
            RequestContext.getCurrentInstance().update("formFuncTabela:tabelaFuncionario");
       }
    }
    
    public void editarFuncionario()
    {
     
        tipoOperacao = "Editar";
        this.fdao.editarFuncionario(funcionarioSelecionado,this.empresa, this.funcionarioConjuge);
    }
    public void sd()
    {
        funcionarioDados = new Funcionario();
    }
    public void cancelarEditar()
    {
        tipoOperacao = "Registro";
        RequestContext.getCurrentInstance().execute("cancelarEditar()");
    }
    
    public void operacao()
    {
       if(this.tipoOperacao.contains("ativar"))
           this.ativarDesativar();
       else
           this.redifinirSenha();
    }

    public List<ComoBox> getFiltroNivel() {
        return filtroNivel;
    }
    
    
    public void alterarNivel(Funcionario fS)
    {
        funcionarioSelecionado = fS;
        if(funcionarioSelecionado.getId() != null && !funcionarioSelecionado.getId().equals(""))
        {
             nivelAtual = fdao.nivelAtual(funcionarioSelecionado.getId().toString())[0];
             if(nivelAtual.contains("Sem"))
             {
                 Message.addInfoMsg("Este funcionário ainda não é um utilizador!", "formFuncSima", "funcionarioGrowl");
             }
             else
             {
                idepartamento = fdao.nivelAtual(funcionarioSelecionado.getId().toString())[1];
                filtroNivel = fdao.nivelAcessoAlterar(nivelAtual);
                Validacao.AtualizarCompoente("formNivel", "nivelAtualFunc");
                Validacao.AtualizarCompoente("formNivel", "novoNivel");
                RequestContext.getCurrentInstance().execute("mostrarModalNivel()");
             }
    
        }      
    }
    
    public void changeLevel()
    {
        System.out.println("novo nivel "+novoNivel);
        if(novoNivel!= null && !novoNivel.equals(""))
        {
            fdao.alterarNivel(SessionUtil.getUserlogado().getId().toString(),funcionarioSelecionado.getId().toString(),
                    Integer.valueOf(novoNivel), Integer.valueOf(idepartamento));
             Mensagem.addInfoMsg("Nível do utilizador alterado com sucesso!");
            RequestContext.getCurrentInstance().update("formFuncSima:funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("fecharModalNivel()");
        }
    }
    public void selectFuncion(int i, Funcionario f)
    {
        if(getFuncionarioSelecionado().getId() != null)
        {
            switch (i) {
                case 1:
                case 2:
                    RequestContext.getCurrentInstance().execute("$('.mp-suspender').show('fade');");
                    break;
                case 3:
                    {
                        RequestContext.getCurrentInstance().execute("$('.mp-ferias').show('fade');");
                        Object o = FuncionarioDao.diasRestantes(getFuncionarioSelecionado().getId());
                        if(o != null)
                        {
                            feriasFuncionario.setDiaRestante(o);
                            Validacao.atualizar("formFuncTabela", "diasRestantes");
                        }break;
                    }
                case 4:
                    {
                        funcionarioV = fdao.informations(fdao.getInfo("T_FUNCIONARIO", "FUNC_ID", "FUNC_NIF", f.getId().toString()));
                        conjugueV = fdao.dadosConjugue(f.getId().toString()) ;
                        RequestContext.getCurrentInstance().execute("$('.mp-more-info-sel').show('fade');");
                        break;
                    }
                default: break;
            }
        }
        else
        { Message.addWarningMsg("Por favor, selecione primeiro um funcionario!", "formFuncSima", "funcionarioGrowl"); }
    }
    
    public void sumpenderOuUltimar(int i)
    {   
        Object o = FuncionarioDao.ultimarSuspensao(getFuncionarioSelecionado().getId(),i, justSupersao);
        String[] ar = toString(o).split(";");
        if (ar[0].equals("true"))
        { 
            Message.addInfoMsg("Registro de "+((i==1) ? "Ultimato" : "Suspensão")+" foi efetuada com sucesso!", "formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("$('.justDesc').val('')");
        }
        else
        { Message.addWarningMsg(ar[1], "formFuncSima", "funcionarioGrowl"); }
    }
    
    
    public void darFerias()
    {
        Object o = FuncionarioDao.darFerias(getFuncionarioSelecionado().getId(),feriasFuncionario);
        String[] ar = toString(o).split(";");
        if( ar[0].equals("true") )
        { Message.addInfoMsg("Registro de ferias foi efetuada com sucesso!", "formFuncSima", "funcionarioGrowl");
            RequestContext.getCurrentInstance().execute("$('.funFerias').find('input:text').val('')");
        }
        else
        { Message.addWarningMsg(ar[1], "formFuncSima", "funcionarioGrowl"); }
    }
    
    
    public class Ferias
    {
        private Object diaRestante; 
        private Date dataInicio;
        private Date dataFim;

        public Ferias() {
        }

        public Object getDiaRestante() {
            return diaRestante;
        }

        public void setDiaRestante(Object diaRestante) {
            this.diaRestante = diaRestante;
        }

        public Date getDataInicio() {
            return dataInicio;
        }

        public void setDataInicio(Date dataInicio) {
            this.dataInicio = dataInicio;
        }

        public Date getDataFim() {
            return dataFim;
        }

        public void setDataFim(Date dataFim) {
            this.dataFim = dataFim;
        }

        @Override
        public String toString() {
            return "Ferias{" + "diaRestante=" + diaRestante + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + '}';
        }
        
    }
    
   public String toString(Object o)
   {
       return ((o != null) ? o.toString() : "");
   }
}
