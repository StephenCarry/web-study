package mail_app;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailClient {
    static String smtp = "smtp.qq.com";

    static int smtpPort = 587;

    static String username = "heyyaoo@qq.com";

    static String password = "crftgexmdxrfbigj";

    public static void main(String[] args) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host",smtp);
        props.put("mail.smtp.port",smtpPort);
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("a269190683@icloud.com"));
        message.setSubject("Java Hello");
        message.setText("Hi I'm from java");
        Transport.send(message);
    }
}
