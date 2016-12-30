
package bean;

import Export.ConfigDoc;
import modelo.Funcionario;
import dao.EmpresaDao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.*;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.servlet.http.Part;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mensagem.Mensagem;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.context.RequestContext;
import sessao.SessionUtil;


@ManagedBean
@ViewScoped
public class EmpresaBean implements Serializable
{
           
   EmpresaDao dao= new EmpresaDao();
   private String nomeEmpresa;
   private String nomeGerente;
   private String nifEmpresa;
   public int ii=0;

    public EmpresaDao getDao() {
        return dao;
    }

    public void setDao(EmpresaDao dao) {
        this.dao = dao;
    }
   
   
   private String razaoSocial;
   private String localizacao;
   private String telefoneEmpresa;
   private String emailEmpresa;
   private String capitalSocial;
   private String codigoPostal;
   private Date dataInaguracao;
   private String facebookEmpresa;
   private String twitterEmpresa;
   private String sobreEmpresa;
    
   private HtmlInputText idUserCriateH;
   private HtmlInputText nomeEmpresaH;
   private HtmlInputText nomeGerenteH;
   private HtmlInputText nifEmpresaH;
   private HtmlInputText razaoSocialH;
   private HtmlInputText localizacaoH;
   private HtmlInputText telefoneEmpresaH;
   private HtmlInputText emailEmpresaH;
   private HtmlInputText capitalSocialH;
   private HtmlInputText codigoPostalH;
   private Calendar dataInaguracaoH;
   private HtmlInputText facebookEmpresaH;
   private HtmlInputText twitterEmpresaH;
   private HtmlInputTextarea sobreEmpresaH;
   
   private Part logotipoEmpresa;
   private Part rodapeEmpresa;
   private Part cabecalhoEmpresa;
   
   private String logotipoFile;
   private String rodapeFile;
   private String cabecalhoFile;
   
   public EmpresaBean(){
       UpdateInformacao();
   }
   
    public EmpresaBean(
            String nomeEmpresa, 
            String nomeGerente, 
            String nifEmpresa, 
            String razaoSocial, 
            String localizacao, 
            String telefoneEmpresa, 
            String emailEmpresa, 
            String capitalSocial, 
            String codigoPostal, 
            Date dataInaguracao, 
            String facebookEmpresa, 
            String twitterEmpresa,
            String sobreEmpresa,
            String logotipoFile,
            String rodapeFile,
            String cabecalhoFile
        ) {
        this.nomeEmpresa = nomeEmpresa;
        this.nomeGerente = nomeGerente;
        this.nifEmpresa = nifEmpresa;
        this.razaoSocial = razaoSocial;
        this.localizacao = localizacao;
        this.telefoneEmpresa = telefoneEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.capitalSocial = capitalSocial;
        this.codigoPostal = codigoPostal;
        this.dataInaguracao = dataInaguracao;
        this.facebookEmpresa = facebookEmpresa;
        this.twitterEmpresa = twitterEmpresa;
        this.sobreEmpresa = sobreEmpresa;
        this.logotipoFile = logotipoFile;
        this.rodapeFile = rodapeFile;
        this.cabecalhoFile = cabecalhoFile;
    }

   

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getNomeGerente() {
        return nomeGerente;
    }

    public void setNomeGerente(String nomeGerente) {
        this.nomeGerente = nomeGerente;
    }

    public String getNifEmpresa() {
        return nifEmpresa;
    }

    public void setNifEmpresa(String nifEmpresa) {
        this.nifEmpresa = nifEmpresa;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTelefoneEmpresa() {
        return telefoneEmpresa;
    }

    public void setTelefoneEmpresa(String telefoneEmpresa) {
        this.telefoneEmpresa = telefoneEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Date getDataInaguracao() {
        return dataInaguracao;
    }

    public void setDataInaguracao(Date dataInaguracao) {
        this.dataInaguracao = dataInaguracao;
    }

    public String getFacebookEmpresa() {
        return facebookEmpresa;
    }

    public void setFacebookEmpresa(String facebookEmpresa) {
        this.facebookEmpresa = facebookEmpresa;
    }

    public String getTwitterEmpresa() {
        return twitterEmpresa;
    }

    public void setTwitterEmpresa(String twitterEmpresa) {
        this.twitterEmpresa = twitterEmpresa;
    }

    public String getSobreEmpresa() {
        return sobreEmpresa;
    }

    public void setSobreEmpresa(String sobreEmpresa) {
        this.sobreEmpresa = sobreEmpresa;
    }

    public Part getLogotipoEmpresa() {
        return logotipoEmpresa;
    }

    public void setLogotipoEmpresa(Part logotipoEmpresa) {
        this.logotipoEmpresa = logotipoEmpresa;
    }

    public Part getRodapeEmpresa() {
        return rodapeEmpresa;
    }

    public void setRodapeEmpresa(Part rodapeEmpresa) {
        this.rodapeEmpresa = rodapeEmpresa;
    }

    public Part getCabecalhoEmpresa() {
        return cabecalhoEmpresa;
    }

    public void setCabecalhoEmpresa(Part cabecalhoEmpresa) {
        this.cabecalhoEmpresa = cabecalhoEmpresa;
    }

    public String getLogotipoFile() {
        return logotipoFile;
    }

    public void setLogotipoFile(String logotipoFile) {
        this.logotipoFile = logotipoFile;
    }

    public String getRodapeFile() {
        return rodapeFile;
    }

    public void setRodapeFile(String rodapeFile) {
        this.rodapeFile = rodapeFile;
    }

    public String getCabecalhoFile() {
        return cabecalhoFile;
    }

    public void setCabecalhoFile(String cabecalhoFile) {
        this.cabecalhoFile = cabecalhoFile;
    }
   
    public void registarEmpresa(){
//        System.out.println("1111 --  dddd");
        if(vaidar(this))
        {
//            System.out.println("2222 -- dddd");
            dao= new EmpresaDao();
            if(((Funcionario)SessionUtil.obterValor("utilizador"))!=null)
            {
//                System.out.println("3333 -- dddd");
                dao.registarEmpresa(this, ((Funcionario)SessionUtil.obterValor("utilizador")).getId().toString());
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Dados da empresa alterados com sucesso!") );
                UpdateInformacao();
            }
        }
    }

    private void UpdateInformacao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
       dao= new EmpresaDao();
       String[] dado=dao.listaEmpresa();
        if(dado!=null)
        {
//            System.out.println(dado.length);
            if(dado.length>10)
            {
                try {
                    this.nomeEmpresa=dado[0];
                    this.nomeGerente=dado[1];
                    this.nifEmpresa=dado[2];
                    this.razaoSocial=dado[3];
                    this.localizacao=dado[4];
                    this.telefoneEmpresa=dado[5];
                    this.emailEmpresa=dado[6];
                    this.capitalSocial=dado[7];
//                    System.out.println(dado[9]);
                    this.codigoPostal=dado[8];
                    this.dataInaguracao=sdf.parse(dado[9]);
                    this.facebookEmpresa=((dado[10] == null) ? "" : dado[10]);
                    this.twitterEmpresa=((dado[11] == null) ? "" : dado[11]);
                    this.sobreEmpresa=dado[12];
                    this.logotipoFile=dado[13];
                    this.rodapeFile=dado[14];
                    this.cabecalhoFile =dado[15];
                    
                    ConfigDoc.AlterDadosEmpresa();
                } catch (ParseException ex) 
                {Logger.getLogger(EmpresaBean.class.getName()).log(Level.SEVERE, null, ex);}
            }
        }
    }
    
    public void resetDados()
    {
        UpdateInformacao();
         FacesContext context = FacesContext.getCurrentInstance();
         context.addMessage(null, new FacesMessage("Alterações foram canceladas com êxito!") );
    }
    public HtmlInputText getIdUserCriateH() {
        return idUserCriateH;
    }

    public void setIdUserCriateH(HtmlInputText idUserCriateH) {
        this.idUserCriateH = idUserCriateH;
    }

    public HtmlInputText getNomeEmpresaH() {
        return nomeEmpresaH;
    }

    public void setNomeEmpresaH(HtmlInputText nomeEmpresaH) {
        this.nomeEmpresaH = nomeEmpresaH;
    }

    public HtmlInputText getNomeGerenteH() {
        return nomeGerenteH;
    }

    public void setNomeGerenteH(HtmlInputText nomeGerenteH) {
        this.nomeGerenteH = nomeGerenteH;
    }

    public HtmlInputText getNifEmpresaH() {
        return nifEmpresaH;
    }

    public void setNifEmpresaH(HtmlInputText nifEmpresaH) {
        this.nifEmpresaH = nifEmpresaH;
    }

    public HtmlInputText getRazaoSocialH() {
        return razaoSocialH;
    }

    public void setRazaoSocialH(HtmlInputText razaoSocialH) {
        this.razaoSocialH = razaoSocialH;
    }

    public HtmlInputText getLocalizacaoH() {
        return localizacaoH;
    }

    public void setLocalizacaoH(HtmlInputText localizacaoH) {
        this.localizacaoH = localizacaoH;
    }

    public HtmlInputText getTelefoneEmpresaH() {
        return telefoneEmpresaH;
    }

    public void setTelefoneEmpresaH(HtmlInputText telefoneEmpresaH) {
        this.telefoneEmpresaH = telefoneEmpresaH;
    }

    public HtmlInputText getEmailEmpresaH() {
        return emailEmpresaH;
    }

    public void setEmailEmpresaH(HtmlInputText emailEmpresaH) {
        this.emailEmpresaH = emailEmpresaH;
    }

    public HtmlInputText getCapitalSocialH() {
        return capitalSocialH;
    }

    public void setCapitalSocialH(HtmlInputText capitalSocialH) {
        this.capitalSocialH = capitalSocialH;
    }

    public HtmlInputText getCodigoPostalH() {
        return codigoPostalH;
    }

    public void setCodigoPostalH(HtmlInputText codigoPostalH) {
        this.codigoPostalH = codigoPostalH;
    }

    public Calendar getDataInaguracaoH() {
        return dataInaguracaoH;
    }

    public void setDataInaguracaoH(Calendar dataInaguracaoH) {
        this.dataInaguracaoH = dataInaguracaoH;
    }

    public HtmlInputText getFacebookEmpresaH() {
        return facebookEmpresaH;
    }

    public void setFacebookEmpresaH(HtmlInputText facebookEmpresaH) {
        this.facebookEmpresaH = facebookEmpresaH;
    }

    public HtmlInputText getTwitterEmpresaH() {
        return twitterEmpresaH;
    }

    public void setTwitterEmpresaH(HtmlInputText twitterEmpresaH) {
        this.twitterEmpresaH = twitterEmpresaH;
    }

    public HtmlInputTextarea getSobreEmpresaH() {
        return sobreEmpresaH;
    }

    public void setSobreEmpresaH(HtmlInputTextarea sobreEmpresaH) {
        this.sobreEmpresaH = sobreEmpresaH;
    }
    
    public boolean validarForm(HtmlInputText inputText,Object d){
        if(!d.toString().isEmpty())
        {
           inputText.setStyle("border:1px #ddd solid");
           return true;
        }
        else
        {
            inputText.setStyle("border-color:red");
            return false;
        }
    }
    public boolean validarCalendar(Calendar inputText,Object d){
        if(d != null && !d.toString().isEmpty())
        {
           inputText.setStyle("border:1px #ddd solid");
           return true;
        }
        else
        {
            inputText.setStyle("border-color:red");
            Mensagem.addWarningMsg("Por favor, digite a Data da Criação!");
            return false;
        }
    }
    public boolean validarFormText(HtmlInputTextarea inputText,Object d){
        if(!d.toString().isEmpty())
        {
//           System.out.println("Entrou");
           inputText.setStyle("border:1px #ddd solid");
           return true;
        }
        else
        {
            inputText.setStyle("border:1px red solid");
            return false;
        }
    }
   
    private boolean vaidar(EmpresaBean e) {
        int i=0;
//        System.out.println("entru Validação");
            if(!validarForm(e.capitalSocialH, e.capitalSocial))
                i++;
            if(!validarForm(e.codigoPostalH, e.codigoPostal))
                 i++;
            if(!validarCalendar(e.dataInaguracaoH, e.dataInaguracao))
                 i++;
            if(!validarForm(e.emailEmpresaH, e.emailEmpresa))
                 i++;
            if(!validarForm(e.localizacaoH, e.localizacao))
                 i++;
            if(!validarForm(e.nifEmpresaH, e.nifEmpresa))
                 i++;
            if(!validarForm(e.nomeEmpresaH, e.nomeEmpresa))
                 i++;
            if(!validarForm(e.nomeGerenteH, e.nomeGerente))
                 i++;
            if(!validarForm(e.razaoSocialH, e.razaoSocial))
                 i++;
            if(!validarFormText(e.sobreEmpresaH, e.sobreEmpresa))
                 i++;
            if(!validarForm(e.telefoneEmpresaH, e.telefoneEmpresa))
                 i++;
//            System.err.println(i==0);
       return (i==0);
    }
    
    public void handleFileUpload(String imagem) 
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try
        {       
         
//            System.err.println(typeImagem(imagem));
            byte[] bd = org.apache.poi.util.IOUtils.toByteArray(typeImagem(imagem).getInputStream());
           
            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
            String arquivo = scontext.getRealPath("/Imagem/");
//            System.err.println(arquivo);
            File f = new File(arquivo);
            for(File dd: f.listFiles())
                if(dd.getName().contains(SessionUtil.getUserlogado().getNomeAcesso()+imagem))
                    dd.delete();
            
            try (FileOutputStream fos = new FileOutputStream(arquivo+"/"+SessionUtil.getUserlogado().getNif()+imagem+ii+".jpg")) 
            {fos.write(bd);}
            
            alterarImagem(imagem,"/Imagem/"+SessionUtil.getUserlogado().getNif()+imagem+ii+".jpg");
            
            ii++;
            RequestContext.getCurrentInstance().update("empresaReg:"+imagem);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(UtilizadorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Part typeImagem(String imagem)
    {
        @SuppressWarnings("UnusedAssignment")
        Part image= null;
            
        if( null != imagem)switch (imagem) 
        {
           case "empLogo":image=this.getLogotipoEmpresa();break;
           case "empCabe":image=this.getCabecalhoEmpresa();break;
           default:image=this.getRodapeEmpresa();break;
       }
        return image;
    }
    
    public void alterarImagem(String imagem,String url)
    {
        if(null != imagem)switch (imagem) 
        {
            case "empLogo":this.setLogotipoFile(url);break;
            case "empCabe":this.setCabecalhoFile(url);break;
            default:this.setRodapeFile(url);break;
        }
    }

    @Override
    public String toString() {
        return "EmpresaBean{" + "dao=" + dao + ", nomeEmpresa=" + nomeEmpresa + ", nomeGerente=" + nomeGerente + ", nifEmpresa=" + nifEmpresa + ", ii=" + ii + ", razaoSocial=" + razaoSocial + ", localizacao=" + localizacao + ", telefoneEmpresa=" + telefoneEmpresa + ", emailEmpresa=" + emailEmpresa + ", capitalSocial=" + capitalSocial + ", codigoPostal=" + codigoPostal + ", dataInaguracao=" + dataInaguracao + ", facebookEmpresa=" + facebookEmpresa + ", twitterEmpresa=" + twitterEmpresa + ", sobreEmpresa=" + sobreEmpresa + ", idUserCriateH=" + idUserCriateH + ", nomeEmpresaH=" + nomeEmpresaH + ", nomeGerenteH=" + nomeGerenteH + ", nifEmpresaH=" + nifEmpresaH + ", razaoSocialH=" + razaoSocialH + ", localizacaoH=" + localizacaoH + ", telefoneEmpresaH=" + telefoneEmpresaH + ", emailEmpresaH=" + emailEmpresaH + ", capitalSocialH=" + capitalSocialH + ", codigoPostalH=" + codigoPostalH + ", dataInaguracaoH=" + dataInaguracaoH + ", facebookEmpresaH=" + facebookEmpresaH + ", twitterEmpresaH=" + twitterEmpresaH + ", sobreEmpresaH=" + sobreEmpresaH + ", logotipoEmpresa=" + logotipoEmpresa + ", rodapeEmpresa=" + rodapeEmpresa + ", cabecalhoEmpresa=" + cabecalhoEmpresa + ", logotipoFile=" + logotipoFile + ", rodapeFile=" + rodapeFile + ", cabecalhoFile=" + cabecalhoFile + '}';
    }
    
    
}