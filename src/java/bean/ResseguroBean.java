/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Export.ConfigDoc;
import Export.DocNotaCredito;
import Export.DocNotaDebito;
import Export.DocOfReCoSeguro;
import Export.GenericExcel;
import Export.GenericPDFs;
import dao.ContratoDao;
import dao.SeguroDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lib.Moeda;
import mensagem.Message;
import modelo.ComoBox;
import modelo.Contrato;
import modelo.Funcionario;
import modelo.Resseguro;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;
import validacao.OperacaoData;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean(name = "resseguro")
@ViewScoped
public class ResseguroBean implements Serializable
{
    private static final long SERIAL_VERSION_UID = 1L;
    private Resseguro resseguro = new Resseguro();
    private  List<ComoBox> listaTipoResseguros;
    private List<ComoBox> listaMoedas;
    private List<ComoBox> listaEmpresasParceiras;
    private List<ComoBox> listaSeguros;
    private List<Resseguro> listResseguros = new ArrayList<>();
    private  List<Resseguro> listagemResseguros;
    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private String pesquisa;
    
    public ResseguroBean()
    {
        listaTipoResseguros = ComoBox.loadCombo("VER_TIPORESEGURO", "ID", "TIPO");
        listaMoedas = ComoBox.loadCombo("VER_MOEDA", "ID", "SIGLA");
        listaEmpresasParceiras = ComoBox.loadCombo("VER_EMPRESAPARCEIRAS", "ID", "EMPRESA");
        listaSeguros = ComoBox.loadCombo("VER_SEGURO", "ID", "NOME");
        listagemResseguros = ContratoDao.listagemResseguros(null);
    }

    public List<ComoBox> getListaTipoResseguros() {
        return listaTipoResseguros;
    }

    public List<Resseguro> getListagemResseguros() {
        return listagemResseguros;
    }

    public List<ComoBox> getListaEmpresasParceiras() {
        return listaEmpresasParceiras;
    }

    public List<ComoBox> getListaSeguros() {
        return listaSeguros;
    }

    public List<ComoBox> getListaMoedas() {
        return listaMoedas;
    }

    public Resseguro getResseguro() {
        return (resseguro == null) ? resseguro = new Resseguro() : resseguro;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public void setResseguro(Resseguro resseguro) {
        this.resseguro = resseguro;
    }

    public List<Resseguro> getListResseguros() {
        return listResseguros;
    }
    
    public void calculoValorTotal()
    {
        double valor;
        if(Validacao.paginaAtual().contains("GestSeg_Resseguros.xhtml"))
        {
            if((resseguro.getPremioGrosso() != null && !resseguro.getPremioGrosso().equals("")))
            {
                resseguro.setPremioGrosso(Validacao.unformat(resseguro.getPremioGrosso()));   
                if(Validacao.isNumeric(resseguro.getPremioGrosso()) == true)
                {

                    resseguro.setValorTotal(resseguro.getPremioGrosso());
                    resseguro.setValorTotalFormatado(Moeda.format(Double.valueOf(resseguro.getPremioGrosso())));
                    Validacao.atualizar("resseguroForm", "resseguroTotal");
                }
                else
                {
                    resseguro.setValorTotalFormatado("");
                    RequestContext.getCurrentInstance().execute("$('.resseguroTotal').val('')");
                    Validacao.atualizar("resseguroForm", "resseguroTotal"); 
                }
            }
            else
            {
                RequestContext.getCurrentInstance().execute("$('.resseguroTotal').val('')");
                resseguro.setValorTotalFormatado("");
                Validacao.atualizar("resseguroForm", "resseguroTotal");
            }
        }
        else
        {
            if((resseguro.getPremioGrosso() != null && !resseguro.getPremioGrosso().equals("")) &&
                (resseguro.getValorDeducao() != null && !resseguro.getValorDeducao().equals("")))
            {
                resseguro.setPremioGrosso(Validacao.unformat(resseguro.getPremioGrosso()));
                resseguro.setValorDeducao(Validacao.unformat(resseguro.getValorDeducao()));
                if(Double.valueOf(resseguro.getValorDeducao())<=100)
                {
                    valor = Double.valueOf(resseguro.getPremioGrosso())* (1-(Double.valueOf(resseguro.getValorDeducao())/100));
                    resseguro.setValorTotal(valor+"");
                    resseguro.setValorTotalFormatado(Moeda.format(valor));
                    Validacao.atualizar("resseguroForm", "resseguroTotal");
                }
                else
                {
                    resseguro.setValorTotalFormatado("");
                    RequestContext.getCurrentInstance().execute("$('.resseguroTotal').val('')");
                    Validacao.atualizar("resseguroForm", "resseguroTotal");
                }
            }
            else
            {
                resseguro.setValorTotalFormatado("");
                RequestContext.getCurrentInstance().execute("$('.resseguroTotal').val('')");
                Validacao.atualizar("resseguroForm", "resseguroTotal");
            }  
        }
    }

    public String getPesquisa() {
        return pesquisa;
    }
    
    public void dadosRegistro()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        resseguro.setIdTipoResseguro(Integer.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("tipo")));
        resseguro.setDataInicio(OperacaoData.stringFormatToDate(facesContext.getExternalContext().getRequestParameterMap().get("data inicio"), DATE_PATTERN));
        resseguro.setDataFim(OperacaoData.stringFormatToDate(facesContext.getExternalContext().getRequestParameterMap().get("data fim"), DATE_PATTERN));
        resseguro.setLimiteResp(facesContext.getExternalContext().getRequestParameterMap().get("limite de responsabilidade"));
        resseguro.setApolice(facesContext.getExternalContext().getRequestParameterMap().get("apolice"));
        resseguro.setTipoCobertura(facesContext.getExternalContext().getRequestParameterMap().get("cobertura"));
        resseguro.setDescricao(facesContext.getExternalContext().getRequestParameterMap().get("descrição"));
        resseguro.setPremioGrosso(facesContext.getExternalContext().getRequestParameterMap().get("premio"));
        resseguro.setNomeCliente(facesContext.getExternalContext().getRequestParameterMap().get("cliente"));
        resseguro.setIdSeguro(Integer.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("tipo seguro")));
        resseguro.setMoeda(facesContext.getExternalContext().getRequestParameterMap().get("moeda"));
        resseguro.setNotaDebito(facesContext.getExternalContext().getRequestParameterMap().get("nota debito"));
        
        registroResseguroParte1();
    }
    private void registroResseguroParte1()
    {
        System.out.println(resseguro);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(Validacao.paginaAtual().contains("GestSeg_Resseguros.xhtml"))
        {
            if((OperacaoData.compareDates(resseguro.getDataInicio(), resseguro.getDataFim()) != -1) 
                && (resseguro.getPremioGrosso() != null && !resseguro.getPremioGrosso().equals("")))
            {
                resseguro.setPremioGrosso(resseguro.getPremioGrosso().replace(" ", ""));
                resseguro.setPremioGrosso(resseguro.getPremioGrosso().replace(",", "."));
                RequestContext.getCurrentInstance().execute("resseguroNext(),$('.resseguroProximo').css('display','none')");
            }
            else if(OperacaoData.compareDates(resseguro.getDataInicio(), resseguro.getDataFim()) == -1)
            {
                Message.addErrorMsg("Data de Fim não pode ser inferior a data de Inicio", "resseguroForm", "resseguroGrowl");
                RequestContext.getCurrentInstance().execute("dataFimFoco()");
            }  
        }
        else RequestContext.getCurrentInstance().execute("resseguroNext(),$('.resseguroProximo').css('display','none')");
        
 
    }
    
   
    public void calculoValorPremioRisco()
    {
        double valorPremio, valorRisco;
        if(resseguro.getPercentagem() != null && !resseguro.getPercentagem().equals(""))
        {
            resseguro.setPercentagem(Validacao.unformat(resseguro.getPercentagem()));
            if(Double.valueOf(resseguro.getPercentagem())> 0 && Double.valueOf(resseguro.getPercentagem())<= 100)
            {
                if(Validacao.isNumeric(resseguro.getLimiteResp()) == true)
                {
                    valorPremio = Double.valueOf(resseguro.getValorTotal()) * (Double.valueOf(resseguro.getPercentagem())/100);
                    valorRisco = Double.valueOf(resseguro.getLimiteResp()) * (Double.valueOf(resseguro.getPercentagem())/100);
                    resseguro.setValorPremio(String.valueOf(valorPremio));
                    resseguro.setValorPremioFormatado(Moeda.format(valorPremio));
                    resseguro.setRisco(String.valueOf(valorRisco));
                    resseguro.setRiscoFormatado(Moeda.format(valorRisco));
                    Validacao.atualizar("resseguroForm", "resseguroValorPremio","resseguroRisco");   
                }
                else
                {
                    valorPremio = Double.valueOf(resseguro.getValorTotal()) * (Double.valueOf(resseguro.getPercentagem())/100);
                   resseguro.setValorPremio(String.valueOf(valorPremio));
                   resseguro.setValorPremioFormatado(Moeda.format(valorPremio));
                   resseguro.setRisco("Ilimitado");
                   resseguro.setRiscoFormatado("Ilimitado");
                   Validacao.atualizar("resseguroForm", "resseguroValorPremio","resseguroRisco");
                }
            }
            else
            {
                resseguro.setPercentagem("");
                resseguro.setValorPremio("");
                resseguro.setValorPremioFormatado("");
                resseguro.setRisco("");
                resseguro.setRiscoFormatado("");
                Validacao.atualizar("resseguroForm", "resseguroValorPremio","resseguroRisco");
            }
        }
        else
        {
            resseguro.setValorPremio("");
            resseguro.setValorPremioFormatado("");
            resseguro.setRisco("");
            resseguro.setRiscoFormatado("");
            Validacao.atualizar("resseguroForm", "resseguroValorPremio", "resseguroRisco");
        }
    }
    
   
    public void limparTabela()
    {
        listResseguros.clear();
        RequestContext.getCurrentInstance().execute("$('.btSaveRess').css('display', 'none')");
        Validacao.atualizar("resseguroForm", "tabelaResseguro");
    }
    public void removeFromTable(Resseguro r)
    {
        listResseguros.remove(r);
        Validacao.atualizar("resseguroForm", "tabelaResseguro");
        if(listResseguros.isEmpty())
            RequestContext.getCurrentInstance().execute("$('.btSaveRess').css('display', 'none')");

    }
   public void addTable()
   {
        if(Validacao.paginaAtual().contains("GestSeg_Resseguros.xhtml"))
        {
            if(listResseguros.size() == 1)
                Message.addWarningMsg("Só pode adicionar somente uma Empresa Parceira", "resseguroForm", "resseguroGrowl");
            else
            {
                listResseguros.add(new Resseguro(Validacao.comboNome(this.listaEmpresasParceiras, resseguro.getIdEmpresa()+""),resseguro.getIdEmpresa())); 
                Validacao.atualizar("resseguroForm", "tabelaResseguro");
                RequestContext.getCurrentInstance().execute("$('.resseguroParte2').val(''),$('.btSaveRess').css('display', '')");
            }
        }
        else
        {
            if(resseguro.getPercentagem() != null && !resseguro.getPercentagem().equals(""))
            {
                resseguro.setPercentagem(Validacao.unformat(resseguro.getPercentagem()));
                if(!listResseguros.isEmpty()) 
                {
                    if(checkPercentage(Double.valueOf(resseguro.getPercentagem())) == false) /// verificar se essa percentagem (100) e a empresa já foi adicionado a lista
                    {                    
                         listResseguros.add(new Resseguro(Validacao.comboNome(this.listaEmpresasParceiras, resseguro.getIdEmpresa()+""), resseguro.getValorPremio(),
                                             resseguro.getRisco(), resseguro.getPercentagem(),resseguro.getIdEmpresa()));   
                        Validacao.atualizar("resseguroForm", "tabelaResseguro");
                        RequestContext.getCurrentInstance().execute("$('.resseguroParte2').val(''),$('.btSaveRess').css('display', '')");
                    }
                }
                else
                {
                    listResseguros.add(new Resseguro(Validacao.comboNome(this.listaEmpresasParceiras, resseguro.getIdEmpresa()+""), resseguro.getValorPremio(),
                                        resseguro.getRisco(), resseguro.getPercentagem(),resseguro.getIdEmpresa()));    
                    Validacao.atualizar("resseguroForm", "tabelaResseguro");
                    RequestContext.getCurrentInstance().execute("$('.resseguroParte2').val(''),$('.btSaveRess').css('display', '')");
                }
            }
        }
   }
   
   
   private boolean checkPercentage(double percentage)
   {
       boolean existEnterprise = false;
       
       for(Resseguro r: listResseguros )
       {
            percentage +=Double.valueOf(r.getPercentagem());
            
            if(resseguro.getIdEmpresa() == r.getIdEmpresa())
               existEnterprise = true;
       }
       
       if(percentage>100)
       {
            Message.addWarningMsg("Não pode adicionar mais empresas.", "resseguroForm", "resseguroGrowl");
            return true;
       }
       if(existEnterprise == true)
       {
             Message.addWarningMsg("Essa empresa já foi adicionado na tabela.", "resseguroForm", "resseguroGrowl");
            return true;
       }
       return false;
   }
   public void regResseguro()
   {
       if(Validacao.paginaAtual().contains("GestSeg_Contratos.xhtml"))
       {
            resseguro.setPremioGrosso(Validacao.unformat(resseguro.getPremioGrosso()));
            resseguro.setValorDeducao(Validacao.unformat(resseguro.getValorDeducao()));
            resseguro.setTipoCobertura(null);
       }
      
       String result = ContratoDao.registrarResseguro(resseguro);
       int id;
       if(result.split(";")[0].equals("true"))
       {
           id = Integer.valueOf(result.split(";")[1]);
           resseguro.setIdTipoResseguro(id);
           for(Resseguro r : listResseguros)
           {
               result = ContratoDao.registrarResponsabilidade(r, id);
           }
           if(result.split(";")[0].equals("true"))
           {
               result = ContratoDao.endResseguro(id);
               if(result.split(";")[0].equals("true"))
               {
                   if(Validacao.paginaAtual().contains("GestSeg_Contratos.xhtml"))
                   {
                        Message.addInfoMsg("Resseguro registrado com sucesso", "resseguroForm", "resseguroGrowl");
                        RequestContext.getCurrentInstance().execute("resseguroRegistrado(),$('.pageModal').addClass('fecharModalV')");
                        listResseguros.clear();
                        listagemResseguros = ContratoDao.listagemResseguros(null);
                        Validacao.atualizar("resseguroForm", "tabelaResseguro");
                        Validacao.atualizar("resseguroFormTable", "resseguros");
                        DocOfReCoSeguro.docSeguros(Validacao.comboNome(listaSeguros, resseguro.getIdSeguro()+""),
                                SessionUtil.getUserlogado().getNomeAcesso(), ConfigDoc.Fontes.getDiretorio(), resseguro.getNotaDebito(), "Resseguro", id,true); 
                        resseguro.setId(id);
                        
                        new DocNotaCredito().Arquivo(ConfigDoc.Fontes.getDiretorio())
                        .IdResseguro(id)
                        .NomeSeguro(Validacao.comboNome(listaSeguros, resseguro.getIdSeguro()+""))
                        .InterCod(SessionUtil.getUserlogado().getNomeAcesso())
                        .TypeNotaCredito(DocNotaCredito.TypeNotaCredito.RESEGURO)
                        .User(SessionUtil.getUserlogado().getNomeCompleto())
                        .createDoc();
                   }
                   else
                   {
                        Message.addInfoMsg("Resseguro adicionado com sucesso", "resseguroForm", "resseguroGrowl");
                        RequestContext.getCurrentInstance().execute("resseguroRegistrado(),$('.pageModal').addClass('fecharModalV')");
                        DocOfReCoSeguro.docSeguros(Validacao.comboNome(listaSeguros, resseguro.getIdSeguro()+""),
                                SessionUtil.getUserlogado().getNomeAcesso(), ConfigDoc.Fontes.getDiretorio(), resseguro.getNotaDebito(), "Resseguro", id,false); 
                        resseguro.setId(id);  
                        notaDebitoImp(resseguro);
                   }
             
               }
               else
                    Message.addErrorMsg(result.split(";")[1], "resseguroForm", "resseguroGrowl");
           }
           else
                Message.addErrorMsg(result.split(";")[1], "resseguroForm", "resseguroGrowl");   
       }
       else
            Message.addErrorMsg(result.split(";")[1], "resseguroForm", "resseguroGrowl");
   }
   
   public void pesquisaResseguro()
   {
       listagemResseguros = ContratoDao.listagemResseguros(pesquisa);
       Validacao.atualizar("resseguroFormTable", "resseguros");
   }
   
   public void resseguroImpressao(Resseguro r)
   {
         DocOfReCoSeguro.docSeguros(Validacao.comboNome(listaSeguros, r.getId()+""),
                SessionUtil.getUserlogado().getNomeAcesso(), ConfigDoc.Fontes.getDiretorio(),r.getNotaDebito(), "Resseguro", r.getId(), false);
         
        notaDebitoImp(r);

   }
   
   public void anularResseguro(Resseguro r)
   {
       if(r != null)
          resseguro = r;
       else

       {
           SeguroDao seguroDao = new SeguroDao();
           String result = seguroDao.AnularContrato(resseguro.getId(), "-1", resseguro.getDescricao(), null, null);
           if(result.split(";")[0].equals("true"))
           {
               Message.addInfoMsg("Resseguro anulado com sucesso", "resseguroAnularForm", "resseguroGrowlAnular");
               RequestContext.getCurrentInstance().execute("$('.resseguroDescAnular').val(''),$('.anularResseguro').fadeOut()");
               listagemResseguros = ContratoDao.listagemResseguros(null);
               Validacao.atualizar("resseguroFormTable", "resseguros");
           }
           else Message.addErrorMsg(result.split(";")[1], "resseguroAnularForm", "resseguroGrowlAnular");
       }
   }
    private void notaDebitoImp( Resseguro  resseguro) throws NumberFormatException {
        ArrayList<String[]> al = new ArrayList();
        System.err.println(resseguro.getPremioGrosso());
        String[] dados = (1 + ";" + Validacao.comboNome(listaSeguros, resseguro.getIdSeguro() + "") + "\n" + resseguro.getDescricao() + ";" + Moeda.format(Double.valueOf(resseguro.getPremioGrosso())) + ";" + Moeda.format(Double.valueOf(resseguro.getPremioGrosso()))).split(";");
        al.add(dados);
        DocNotaDebito debito = new DocNotaDebito();
        Contrato c = new Contrato();
        
        c.setDataContrato(new Date());
        c.setDataInicio(resseguro.getDataInicio());
        c.setDataFim(resseguro.getDataFim());
//                   c.setDataRenovacao(rs.getDate("DATA RENOVACAO"));
        c.setDataRegistro(resseguro.getDataRegistro());
        c.setDataContrato(resseguro.getDataRegistro());
        
//                   c.setPremioAnual(rs.getString("PREMIO ANUAL"));
        c.setPremioBrutoMoeda(Moeda.format(Double.valueOf(resseguro.getPremioGrosso())));
        c.setPremioBruto(resseguro.getPremioGrosso());
        c.setPremioLiquidoMoeda(Moeda.format(Double.valueOf(resseguro.getPremioGrosso())));
        c.setPremioLiquido(resseguro.getPremioGrosso());
//             : rs.getString("VALOR TOTAL SEGURADO")))
        c.setTotalSeguradoMoeda(resseguro.getLimiteResp());
        c.setTotalSegurado(resseguro.getLimiteResp());
        c.setPrimeiroPremio(resseguro.getPremioGrosso());
        
//                   c.setValorPremio(rs.getString("PRIMEIRO PREMIO"));
//                   c.setExcesso(rs.getString("EXCESSO"));
//                   c.setFranquia(rs.getString("EXCESSO"));
        c.setObservacao(resseguro.getDescricao());
        c.setSigla(Validacao.Sigla(resseguro.getMoeda()));
        c.setMoeda(resseguro.getMoeda());
//                   c.setTaxa(rs.getString("TAXA ADICIONAR"));
        
        DocNotaDebito.segurado = resseguro.getNomeCliente();
        DocNotaDebito.isReseguro = true;
        String reString = debito.docSeguros(Validacao.comboNome(listaSeguros, resseguro.getIdSeguro() + ""), resseguro.getApolice(), SessionUtil.getUserlogado().getNomeAcesso() + "", resseguro.getId() + "", " ", al, c, (String) ((Funcionario) SessionUtil.obterValor("utilizador")).getNomeAcesso(), c.getSigla(), ConfigDoc.Fontes.getDiretorio(), resseguro.getNotaDebito());
        RequestContext.getCurrentInstance().execute("openAllDocument('" + reString + "')");
        
    }
    
    public void printDoc(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        int tipo = Integer.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("tipo"));
        DataTableControl clienteControl = new DataTableControl("id55", "reseguro.fjfjf");
        clienteControl.prepareModel(ContratoDao.listagemRessegurosPrint(pesquisa), DataTableControl.ShowMode.SHOW, "APOLICE", "CLIENTE", "MOEDA", "INICIO", "FIM", "TIPO COBERTURA");
        clienteControl.renameColumn("INICIO", "DATA INICIO");
        clienteControl.renameColumn("FIM", "DATA FIM");
        clienteControl.updFaces(FacesContext.getCurrentInstance());
        if (tipo == 1) {
            GenericPDFs.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Resseguros", "Relatório de Resseguros", clienteControl, GenericPDFs.OrientacaoPagina.HORIZONTAL, -1);
        } else {
            GenericExcel.createDoc(SessionUtil.getUserlogado().getNomeAcesso(), "Relatório de Resseguros", "Relatório de Resseguros", clienteControl, -1);
        }
        RequestContext.getCurrentInstance().execute("$('.modalProcess').hide()");
    }

}
