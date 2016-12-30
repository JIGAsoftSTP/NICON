package bean;

import java.io.Serializable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mensagem.Mensagem;
import modelo.FeedBack;
import org.primefaces.context.RequestContext;
import validacao.Validacao;

/**
 *
 * @author Helcio Guadalupe
 */
@ManagedBean
@ViewScoped
public class FeedBackBean implements Serializable
{
    private static final long SERIAL_VERSION =1L;
    private FeedBack feedBack = new FeedBack();
    
    public FeedBack getFeedBack() {
        return (feedBack == null)? feedBack = new FeedBack() : feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }
        
    public boolean validarEmail()
    {
        boolean valido = true;
         Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
         Matcher m = p.matcher(feedBack.getEmailResposta()); 
        if (!m.find())
        {
            valido = false;
 
            Mensagem.addErrorMsg("Email inválido");
            Validacao.atualizar("formEmail", "growlFeedBack");
        }   
        return valido;
    }
    
    public void validData()
    {
        if(validarEmail() == true)
            sendMessage();
    }
    private void sendMessage()
    {
        try {
            String smtpHost="smtp.gmail.com";
            String smtpUsername ="jigasoft.stp2015@gmail.com";
            String smtpPassword ="OurAccountEnterprise2015";
            String from ="jigasoft.stp2015@gmail.com";
            String to="jigasoft_stp@hotmail.com";
            String info = "Mensagem enviada de NICON SEGUROS SA por "+feedBack.getNome()+"\n\n"+feedBack.getMensagem()+"\n\n"+feedBack.getEmailResposta();
      
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                props.put("mail.debug", "true");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
                        @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpUsername, smtpPassword);
			}
		  });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(feedBack.getAssunto());
            message.setText(info);
            Transport.send(message);
        
            Mensagem.addInfoMsg("Mensagem enviada com sucesso");
            Validacao.atualizar("formEmail", "growlFeedBack");
            RequestContext.getCurrentInstance().execute("messageSent()");
        } 
        catch (MessagingException ex)
        {  
            Mensagem.addErrorMsg("Erro a enviar mensagem\n"+ex.getMessage());
            Validacao.atualizar("formEmail", "growlFeedBack");
        }  
    }
    
    
    
    
    
}
