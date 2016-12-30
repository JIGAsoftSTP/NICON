package bean;

import dao.FuncionarioDao;
import dao.RecursosHumanosDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mensagem.Message;
import modelo.Categoria;
import modelo.ComoBox;
import modelo.Funcionario;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Categoria categoria = new Categoria();
    private final Categoria categoriaSelecionada = new Categoria();
    private List<ComoBox> niveis = new ArrayList<>();
    private List<Categoria> listaCategorias = new ArrayList<>();
    private List<ComoBox> categoryNames = new ArrayList<ComoBox>();
    private final RecursosHumanosDao rhd = new RecursosHumanosDao();
    private final FuncionarioDao fdao = new FuncionarioDao();
    private String campoPesquisa;
    private String valorPesquisa;
    private List<ComoBox> categoriaFunc;
    private String operacao = "registro";


    public CategoriaBean() {

    }

    @PostConstruct
    public void init() {
        niveis = ComoBox.loadCombo("VER_LEVELCATEGORIA", "ID", "CATEGORIA");
        listaCategorias = this.rhd.listagemCategorias(null, null, false, 0);
        categoriaFunc = fdao.ListFucionarioCategoria();
    }

    public Categoria getCategoria() {
        return (categoria == null) ? categoria = new Categoria() : categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public List<ComoBox> getCategoriaFunc() {
        return categoriaFunc;
    }

    public List<ComoBox> getNiveis() {
        return niveis;
    }

    public String getCampoPesquisa() {
        return campoPesquisa;
    }

    public List<ComoBox> getCategoryNames() {
        return categoryNames;
    }

    
    public void setCampoPesquisa(String campoPesquisa) {
        this.campoPesquisa = campoPesquisa;
    }

    public String getValorPesquisa() {
        return valorPesquisa;
    }

    public void setValorPesquisa(String valorPesquisa) {
        this.valorPesquisa = valorPesquisa;

    }

    public void regCategoria() 
    {
        System.out.println(categoria);
        if (SessionUtil.obterValor(Funcionario.SESSION_NAME) != null
                && (categoria.getNome() != null && !categoria.getNome().equals(""))
                && (categoria.getNivel()!= null && !categoria.getNivel().equals(""))) {
            String result = rhd.registarCategoria(categoria,1);
            if (result.split(";")[0].equals("true")) 
            {
                Message.addInfoMsg("Estrutura de Salário registrado com sucesso!", "formContabilidadeSalario:formCategoria", "growlCategoria");
                listaCategorias.clear();
                listaCategorias = this.rhd.listagemCategorias(null, null, false, 0);
                Validacao.atualizar("formContabilidadeSalario:formTabelaCategoria", "categoriaTabela");
                RequestContext.getCurrentInstance().execute("clearFields()");
            } 
            else 
            {
                RequestContext.getCurrentInstance().execute("$('.processamento').hide()");
                Message.addErrorMsg(result.split(";")[1], "formContabilidadeSalario:formCategoria", "growlCategoria");
            }
        }
    }
    
    public String obterId(String combo, String texto)
    {
        switch(combo)
        {
            case "nome":
                for(ComoBox cb: categoriaFunc)
                {
                    if(cb.getValue().equals(texto))
                        return cb.getId();
                }
                break;
            default:
                 for(ComoBox cb: this.niveis)
                {
                    if(cb.getValue().equals(texto))
                        return cb.getId();
                }
                break;
        }
        return null;
    }
    
    public void typeOperacation()
    {
        if(operacao.equals("editar"))
            editarCategoria();
        else
            regCategoria();
    }
    private void editarCategoria()
    {
         if (SessionUtil.obterValor(Funcionario.SESSION_NAME) != null){
            categoria.setNome(obterId("nome", categoriaSelecionada.getNome()));
            categoria.setNivel(obterId("nivel", categoriaSelecionada.getNivel()));
            String result = rhd.registarCategoria(categoria,2);
            if (result.split(";")[0].equals("true")) 
            {
                Message.addInfoMsg("Estrutura de Salário "+categoriaSelecionada.getNome()+" autalizado com sucesso", "formContabilidadeSalario:formCategoria", "growlCategoria");
                listaCategorias.clear();
                listaCategorias = this.rhd.listagemCategorias(null, null, false, 0);
                Validacao.atualizar("formContabilidadeSalario:formTabelaCategoria", "categoriaTabela");
                RequestContext.getCurrentInstance().execute("clearFields()");
                operacao ="registro";
            } 
            else 
            {
                RequestContext.getCurrentInstance().execute("$('.processamento').hide()");
                Message.addErrorMsg(result.split(";")[1], "formContabilidadeSalario:formCategoria", "growlCategoria");
            }
        }
    }
       
    public void editarCategoria(Categoria c)
    {
        this.categoria =  new Categoria(c);
        categoriaSelecionada.setNome(categoria.getNome());
        categoriaSelecionada.setNivel(categoria.getNivel());
        operacao ="editar";
        
        RequestContext.getCurrentInstance().execute("editarCategoria('"+obterId("nome", categoria.getNome())+"','"+categoria.getNumDias()+"','"+obterId("nivel", categoria.getNivel())+"','"+Validacao.valorSemVirgulaPonto(categoria.getSalarioBase())+"','"+Validacao.valorSemVirgulaPonto(categoria.getSubsidioAlojamento())+"','"+Validacao.valorSemVirgulaPonto(categoria.getSubisidioTransporte())+"','"+Validacao.valorSemVirgulaPonto(categoria.getSubisidioAlmoco())+"','"+categoria.getValorTotal()+"','"+Validacao.valorSemVirgulaPonto(categoria.getAlmocoLivreImposto())+"')");
    }
    
    public void moreInfo(Categoria c)
    {
         this.categoria =  new Categoria(c);
         Validacao.atualizar("formContabilidadeSalario", "estruturaSalarioMaisInfo");
    }

    public void pesquisar() {
        if (campoPesquisa != null && !campoPesquisa.equals("")) {
            listaCategorias = this.rhd.listagemCategorias(campoPesquisa, valorPesquisa, false, 0);
            Validacao.atualizar("formTabelaCategoria", "catgoriaTabela");
        }
    }

    public void reportFunc(int i) {
        this.rhd.listagemCategorias(campoPesquisa, valorPesquisa, true, i);
    }

    private void teste()
    {
        int soma = 12;
        soma++;
        
    }

    
}
