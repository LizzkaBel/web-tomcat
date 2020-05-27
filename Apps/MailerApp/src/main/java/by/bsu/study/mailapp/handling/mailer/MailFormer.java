package by.bsu.study.mailapp.handling.mailer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailFormer {
    private final String username;
    private final String password;
    private final Properties properties;

    public MailFormer(String username, String password) {
        this.username = username;
        this.password = password;
        properties = new Properties();
        setGmailSMTP();
    }

    // service configuration (gmail)
    public void setGmailSMTP(){
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587); //google port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public void sendMessage(String sender, String receiver
            , String theme, String messageContent) throws MessagingException {
        //authorization in gmail by given user and pass
        Session sessionAuth = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        //forming message using credentials given above
        Message message = new MimeMessage(sessionAuth);
        message.setFrom(new InternetAddress(sender)); //sender email
        message.setRecipients( Message.RecipientType.TO
                , InternetAddress.parse(receiver)); //receiver email (can be more than one)

        message.setSubject(theme); // message theme
        message.setText(messageContent + "\n\nMessage was send automatically. Mail-App test.");

        Transport.send(message); //message sending
    }
}
