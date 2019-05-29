package website.psuti.fist.configuration;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.exception.service.remote.ServiceResponseException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.springframework.stereotype.Component;
import java.net.URI;

@Component
public class Sender {
    private final ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2); // change to whatever server you are running, though 2010_SP2 is the most recent version the Api supports
    private final String username = "robot.fist@psuti.ru";
    private final String password = "FIst_RePlY_NO_2019";

    public Sender() {
        try {
            service.setCredentials(new WebCredentials(username, password));
            service.setUrl(new URI("https://mail.psuti.ru/ews/exchange.asmx"));
        }
        catch (Exception e) { e.printStackTrace(); }
    }



    public boolean send(String subject, String htmlBody, String recipient) throws Exception {
        try {
            EmailMessage email = new EmailMessage(service);
            email.setSubject(subject);
            email.setBody(new MessageBody(BodyType.HTML, htmlBody));
            email.getToRecipients().add(recipient);
            email.sendAndSaveCopy();
            return true;
        } catch (ServiceResponseException ex) {
            System.out.println("Не удалось отправить на почту - " + recipient);
            return false;
        }
    }
}