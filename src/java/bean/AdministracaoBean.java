
package bean;

import dao.RecursosHumanosDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import mensagem.Message;
import modelo.Artigo;
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
public class AdministracaoBean implements Serializable
{
    private static final long SERIAL_VERSION_UID =1L;
    private Artigo artigo = new Artigo();
    private Artigo artigoSelecionado = new Artigo();
    private List<ComoBox> categories = new ArrayList<>();
    private List<Artigo> listArticles = new ArrayList<>();
    private List<String> listaCategorias = new ArrayList<>();
    private List<ComoBox> funcionarios = new ArrayList<>();
    private String codigo;
    private String stock;
    private final RecursosHumanosDao rhd = new RecursosHumanosDao();
    

    public AdministracaoBean()
    {
        listaCategorias = rhd.listaCategorias();
    }
    
    @PostConstruct
    public void init()
    {
        listArticles = this.rhd.listaArtigos();
        funcionarios = this.rhd.employeers();
    
        categories = this.rhd.categoryNames();
    }
    
    public Artigo getArtigo() {
        return (artigo == null)? artigo = new Artigo() : artigo;
    }

    public void setArtigo(Artigo artigo) {
        this.artigo = artigo;
    }

    public List<ComoBox> getFuncionarios() {
        return funcionarios;
    }

    public Artigo getArtigoSelecionado() {
        return (artigoSelecionado == null)? artigoSelecionado = new Artigo() : artigoSelecionado;
    }

    public void setArtigoSelecionado(Artigo artigoSelecionado) {
        this.artigoSelecionado = artigoSelecionado;
    }

    public List<ComoBox> getCategories() {
        return categories;
    }

    public List<Artigo> getListArticles() {
        return listArticles;
    }
     
    public void newArticle()
    {
        if(SessionUtil.obterValor(Funcionario.SESSION_NAME) != null)
        {
             artigo.setId(null);
            if(Integer.valueOf(artigo.getQuantidade())>0)
            {
                 String result = this.rhd.registarArtigo(artigo);
                if(result.split(";")[0].equals("true"))
                {
                    if(Integer.valueOf(artigo.getQuantidade())>0)
                    {
                        result = this.rhd.registrarItemArtigo(artigo, Integer.valueOf(result.split(";")[1]),1);
                        if(result.split(";")[0].equals("true"))
                        {
                            Message.addInfoMsg("Artigo "+artigo.getNomeArtigo()+" registrado com sucesso!","articleForm", "articleGrowl");
                            RequestContext.getCurrentInstance().execute("artigoRegistrado()");
                            listArticles = this.rhd.listaArtigos();
                            Validacao.atualizar("formArticleTable", "artigoTabela");
                        }
                        else
                        {
                           RequestContext.getCurrentInstance().execute("nomeArtigoExiste()");
                           Message.addErrorMsg(result.split(";")[1],"articleForm", "articleGrowl");
                        }
                    }
                    else
                    {
                        Message.addInfoMsg("Artigo "+artigo.getNomeArtigo()+" registrado com sucesso!","articleForm", "articleGrowl");
                        RequestContext.getCurrentInstance().execute("artigoRegistrado()");
                        listArticles = this.rhd.listaArtigos();
                        Validacao.atualizar("formArticleTable", "artigoTabela");
                    }  
                }
                else
                {
                    RequestContext.getCurrentInstance().execute("nomeArtigoExiste()");
                    Message.addErrorMsg(result.split(";")[1],"articleForm", "articleGrowl");
                }
            }
            else
            {
                Message.addWarningMsg("Quantidade de artigo deve ser superior a zero(0)!","articleForm", "articleGrowl");
                RequestContext.getCurrentInstance().execute(" $('.artigoQuantidade').focus()");
            }
           
        }
    }
    
    public void changeQuantity(String param, Artigo a)
    {
        stock = param;
        artigoSelecionado = a;
        artigo = artigoSelecionado;
        if(param.equals("Retirar Artigo do Stock") && Integer.valueOf(artigoSelecionado.getQuantidade())==0)
             Message.addWarningMsg("Nenhuma quantidade existente no stock!", "articleForm","articleGrowl"); 
        else if(param.equals("Editar Artigo do Stock"))
        {
            
            artigo.setNomeArtigo(artigoSelecionado.getConsumivel());
            artigo.setCategoria(artigoSelecionado.getCategoria());
            Validacao.atualizar("articleForm", "articleName","articleCategory");
            RequestContext.getCurrentInstance().execute("articleChangeQuantity('"+param+"','"+null+"')");
        }
        else
        {
            artigo.setNomeArtigo(artigoSelecionado.getConsumivel());
            artigo.setCategoria(artigoSelecionado.getCategoria());
            Validacao.atualizar("articleForm", "articleName","articleCategory");
            RequestContext.getCurrentInstance().execute("articleChangeQuantity('"+param+"','"+artigo.getQuantidade()+"')"); 
        }
    }
    
    public String getId(String nome)
    {
        for(ComoBox cb:this.categories)
        {
            if(cb.getValue().equals(nome))
            {
               return cb.getId();
            }
        }
        return null;
    }
    
    public String getIdArtigo(String nomeArtigo)
    {
        String id = null;
        for(Artigo cb:this.listArticles)
        {
            if(cb.getConsumivel().equals(nomeArtigo))
            {
                id= cb.getCodigo();
                break;
            }
        }
        return id;
    }

    public void operation()
    {
        String type,articleName; 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        artigo.setNomeArtigo(facesContext.getExternalContext().getRequestParameterMap().get("nome"));
        artigo.setCategoria(facesContext.getExternalContext().getRequestParameterMap().get("categoria"));
        artigo.setQuantidade(facesContext.getExternalContext().getRequestParameterMap().get("quantidade"));
        artigo.setFuncionarioResponsavel(facesContext.getExternalContext().getRequestParameterMap().get("funcionario"));
        artigo.setFornecedor(facesContext.getExternalContext().getRequestParameterMap().get("fornecedor"));
        artigo.setDescricao(facesContext.getExternalContext().getRequestParameterMap().get("obs"));
        artigo.setAdicionarRemover(facesContext.getExternalContext().getRequestParameterMap().get("adicionarRemover"));
        artigo.setQuantidadeStock(Integer.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("stockQuantidade")));
        articleName = facesContext.getExternalContext().getRequestParameterMap().get("nomeArtigoAtualizar");
        type = facesContext.getExternalContext().getRequestParameterMap().get("typeOperation");
        
        switch (type) 
        {
            case "novo":
                this.newArticle();
                break;
            case "alterar quantidade":
                alterarQuantidade();
                break;
            default:
                alterarNomeCategoriaArtigo(articleName);
                break;
        }
    }
    
    public void atualizarTabelaArtigo()
    {
        listArticles = this.rhd.listaArtigos();
        Validacao.atualizar("formArticleTable", "artigoTabela");
    }
    
    private void alterarNomeCategoriaArtigo(String articleName)
    {
        artigo.setId(Integer.valueOf(getIdArtigo(articleName)));
        String result = rhd.registarArtigo(artigo);
        if(result.split(";")[0].equals("true"))
        {
            listArticles.clear();
            Message.addInfoMsg("Artigo atualizado com sucesso","articleForm", "articleGrowl");
            RequestContext.getCurrentInstance().execute("artigoRegistrado()");
            listArticles = this.rhd.listaArtigos();
            Validacao.atualizar("formArticleTable", "artigoTabela");
        }
        else
            Message.addErrorMsg(result.split(";")[1],"articleForm", "articleGrowl");
        
    }
    public void alterarQuantidade()
    {
        if(SessionUtil.obterValor(Funcionario.SESSION_NAME) != null)
        {
            if(Integer.valueOf(artigo.getQuantidade())>0)
            {
                if(!artigo.getAdicionarRemover().equals("Adicionar Artigo ao Stock") && (Integer.valueOf(artigo.getQuantidade())>artigo.getQuantidadeStock()))
                    Message.addErrorMsg("Quantidade de consumível superior ao existente do stock!","articleForm", "articleGrowl"); 
                else
                {
                    String result = this.rhd.registrarItemArtigo(artigo, Integer.valueOf(getIdArtigo(artigo.getNomeArtigo())),((artigo.getAdicionarRemover().equals("Adicionar Artigo ao Stock")? 1 : 2)));
                    if(result.split(";")[0].equals("true"))
                    {
                        Message.addInfoMsg("Quantidade do artigo "+artigo.getNomeArtigo()+" alterado no stock!","articleForm", "articleGrowl");
                        RequestContext.getCurrentInstance().execute("artigoRegistrado()");
                        listArticles = this.rhd.listaArtigos();
                        Validacao.atualizar("formArticleTable", "artigoTabela");
                    }
                }
            }
            else
            {
                RequestContext.getCurrentInstance().execute("artigoQuantidadeBorda()");
                 Message.addWarningMsg("Quantidade deve ser superior a zero(0)!","articleForm", "articleGrowl"); 
            }
                
        }
    }
    


    public List<String> getListaCategorias() {
        return listaCategorias;
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
    
   public List<String>completarListaCategoria(String info)
   {    
       System.out.println("info categoria "+info);
       listaCategorias.set(0, info);
       return likeStart(this.listaCategorias, info);
   }
   
   
  
}
