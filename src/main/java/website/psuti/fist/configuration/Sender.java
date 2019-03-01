package website.psuti.fist.configuration;

import org.springframework.stereotype.Component;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

@Component
public class Sender {

    private final String username = "help.warspear.gold@gmail.com";
    private final String password = "1q2w3e4r5tqwert";
    private Properties props;

    public Sender() {

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    public void send(String subject, String textClient, String toEmail, User user){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
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
            Reader s = new InputStreamReader(new FileInputStream(PathConstant.HTML_FILE_FOR_USER_ADD_CMS.getPath()) );
            char c[] = new char[(int)(new File(PathConstant.HTML_FILE_FOR_USER_ADD_CMS.getPath())).length()];
            s.read(c);
            String htmlBody = String.copyValueOf(c)
                    .replace("#headerTop", "Факультет информационных систем и технологий")
                    .replace("#logotipFIST", "http://109.124.219.82:8081/main/picture/"+ MainPageConstant.LOGOTIP_FIST.getId())
                    .replace("#logotipPSUTI", "http://109.124.219.82:8081/main/picture/"+ MainPageConstant.LOGOTIP_PSUTI.getId())
                    .replace("#logotipTwitter", "http://109.124.219.82:8081/main/picture/"+ 73)
                    .replace("#logotipInstagram", "http://109.124.219.82:8081/main/picture/"+ 72)
                    .replace("#logotipVK", "http://109.124.219.82:8081/main/picture/"+ 71)
                    .replace("#headerEmailHtml", "Добавлен пользователь в CMS")
                    .replace("#footer", "Вы получаете это письмо, потому что вас добавили в пользователи CMS сайта ФИСТ.")
                    .replace("#nameClient", "Здраствуйте, "+user.getFirstname()+",")
                    .replace("#textClient", textClient)
                    .replace("#buttonCheck", "Подтвердить почту")
                    .replace("#buttonHref", "http://109.124.219.82:8081/user/enable/email="+ user.getUsername());

            message.setContent(htmlBody , "text/html; charset=utf-8");
            s.close();

            //отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}