
package socket;

import javax.faces.application.FacesMessage;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

/**
 *
 * @author Helcio Guadalupe
 */
@PushEndpoint("/notify")
public class NotifyResource 
{
    @OnMessage( encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message)
    {
        return message;
    }
}
