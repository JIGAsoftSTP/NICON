
package bean;

import dao.TaxaImpostoDao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mensagem.Message;
import modelo.Taxa;
import org.primefaces.context.RequestContext;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ViewScoped
@ManagedBean
public class TaxaImpostoBean 
{
    private static final long SERIAL_VERSION_UID = 1L;
    private Taxa taxa = new Taxa();
    private List<Taxa> listOutrasTaxas;
    private List<Taxa> listSegSocial;
    private String empresaSegSocial;
    private String funcionarioSegSocial;
    private  List<Taxa> listIRS;
    
    
    public TaxaImpostoBean()
    {
         listOutrasTaxas = TaxaImpostoDao.carregarTaxasImpostos(3);
         listSegSocial = TaxaImpostoDao.carregarTaxasImpostos(2);
         listIRS = TaxaImpostoDao.carregarTaxasImpostos(1);
    }
    
    public Taxa getTaxa() {
        return (taxa == null) ? taxa = new Taxa() : taxa;
    }

    public List<Taxa> getListOutrasTaxas() {
        return listOutrasTaxas;
    }

    public List<Taxa> getListSegSocial() {
        return listSegSocial;
    }

    public String getEmpresaSegSocial() {
        return empresaSegSocial;
    }

    public List<Taxa> getListIRS() {
        return listIRS;
    }

    public void setEmpresaSegSocial(String empresaSegSocial) {
        this.empresaSegSocial = empresaSegSocial;
    }

    public String getFuncionarioSegSocial() {
        return funcionarioSegSocial;
    }

    public void setFuncionarioSegSocial(String funcionarioSegSocial) {
        this.funcionarioSegSocial = funcionarioSegSocial;
    }

    public void setTaxa(Taxa taxa) {
        this.taxa = taxa;
    }
    
    public void editarOutrasTaxas()
   {
       if(taxa.getPercentagem() != null && !taxa.getPercentagem().equals(""))
       {
           System.out.println("id do imposto "+taxa.getIdImposto()+"\nPercentagem "+taxa.getPercentagem());
            String result = TaxaImpostoDao.editarOutrasTaxas(taxa, null, null, null);
            if(result.split(";")[0].equals("true"))
            {
                Message.addInfoMsg("Valor do imposto "+taxa.getNomeImposto()+" atualizado com sucesso", "taxasImpostosForm", "outrasTaxasGrowl");
                listOutrasTaxas = TaxaImpostoDao.carregarTaxasImpostos(3);
                Validacao.atualizar("taxasImpostosForm", "outrasTaxasTabela");
                RequestContext.getCurrentInstance().execute("$('.outrasTaxasCampo').css('border','')");
                RequestContext.getCurrentInstance().execute("$('.outrasTaxasCampo').val('')");
                RequestContext.getCurrentInstance().execute("$('.botaoEditarOutrasTaxas').attr('disabled', true)");
            }
       }
   }
    
   public void outrasTaxasCarregarEditar(Taxa taxaSelecionada)
   {
       taxa = new Taxa(taxaSelecionada);
       RequestContext.getCurrentInstance().execute("outrasTaxasEditar('"+taxa.getNomeImposto()+"','"+((taxa.getPercentagem()==null || taxa.getPercentagem().equals(""))? "" :taxa.getPercentagem())+"')");
   }
   
   public void segurancaSocial()
   {
       if((empresaSegSocial != null && !empresaSegSocial.equals("")) || (funcionarioSegSocial != null && !funcionarioSegSocial.equals("")))
       {
            taxa.setPercentagem(((empresaSegSocial == null || empresaSegSocial.equals("")) ? funcionarioSegSocial : empresaSegSocial));
            String result = TaxaImpostoDao.editarOutrasTaxas(taxa, null, null, null);
            if(result.split(";")[0].equals("true"))
            {
                    Message.addInfoMsg("Valor atualizado com sucesso", "taxasImpostosForm", "outrasTaxasGrowl");
                    listSegSocial = TaxaImpostoDao.carregarTaxasImpostos(2);
                    Validacao.atualizar("taxasImpostosForm", "segSocialTabela");
                    RequestContext.getCurrentInstance().execute("$('.segSocialCampo').css('border','');$('.segSocialCampo').val('');$('.segSocialBotao').attr('disabled', true);");
            }
            else
                Message.addErrorMsg(result.split(";")[1], "taxasImpostosForm", "outrasTaxasGrowl");

        }
    }
      
    public void regIrs()
    {
        taxa.setValorMinimo(Validacao.unformat(taxa.getValorMinimo()));
        taxa.setValorMaximo(Validacao.unformat(taxa.getValorMaximo()));
        taxa.setParcelaBater(Validacao.unformat(taxa.getParcelaBater()));
        if(Double.valueOf(taxa.getValorMaximo())<Double.valueOf(taxa.getValorMinimo()))
            Message.addErrorMsg("Valor máximo não pode ser inferior ao valor mínimo", "taxasImpostosForm", "outrasTaxasGrowl");
        else
        {
            String result = TaxaImpostoDao.editarOutrasTaxas(taxa, Double.valueOf(taxa.getValorMinimo()), Double.valueOf(taxa.getValorMaximo()), Double.valueOf(taxa.getParcelaBater()));
            if(result.split(";")[0].equals("true"))
            {
                Message.addInfoMsg("Valor atualizado com sucesso", "taxasImpostosForm", "outrasTaxasGrowl");
                listIRS = TaxaImpostoDao.carregarTaxasImpostos(1);
                Validacao.atualizar("taxasImpostosForm", "irsTabela");
                RequestContext.getCurrentInstance().execute("$('.irsCampo').css('border','');$('.irsCampo').val('');$('.irsBotao').attr('disabled', true);");
            }
            else Message.addErrorMsg(result.split(";")[1], "taxasImpostosForm", "outrasTaxasGrowl");
        }
    }
   public void editarSegurancaSocial(Taxa taxaSelecionada)
   {
       taxa = new Taxa(taxaSelecionada);
       if(taxa.getNome().equalsIgnoreCase("Empresa")) RequestContext.getCurrentInstance().execute("segSocialEditar('"+taxa.getPercentagem()+"','"+null+"')");
       else RequestContext.getCurrentInstance().execute("segSocialEditar('"+null+"','"+taxa.getPercentagem()+"')");
   }
   
   public void editarIrs(Taxa taxaSelecionada)
   {
        taxa = new Taxa(taxaSelecionada);
        RequestContext.getCurrentInstance().execute("editarIRS('"+taxa.getValorMinimo()+"','"+taxa.getValorMaximo()+"','"+taxa.getPercentagem()+"','"+taxa.getParcelaBater()+"')");
   }
  
}
