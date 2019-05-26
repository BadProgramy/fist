package website.psuti.fist.configuration;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.exception.service.remote.ServiceResponseException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.property.complex.MimeContent;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class Sender {
    private final ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2); // change to whatever server you are running, though 2010_SP2 is the most recent version the Api supports
    private final String username = "asdsa";
    private final String password = "asdasd";

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

    /*public void send(String subject, String htmlBody, String toEmail) throws MessagingException {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(username));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //тема сообщения
            message.setSubject(subject);
            //текст
            //message.setText(text);
            //html body

            message.setContent(htmlBody , "text/html; charset=utf-8");

            //отправляем сообщение
            Transport.send(message);
    }*/
}