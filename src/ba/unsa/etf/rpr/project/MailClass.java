package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.NotificationMessages;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailClass {
    public static void sendMail(Task t, User u) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "xxxxx";
        //Your gmail password
        String password = "xxxxx";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail,u.getMail(),t);

        session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");

    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, Task t) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(NotificationMessages.EMAILSUBJECT.toString());
            String htmlCode="<h2>"+NotificationMessages.H2MAILTEXT.toString()+t.getTaskName()+"</h2> <br/> <h3>"+NotificationMessages.REMINDERINFORMATION.toString()+t.getReminderDigit()+" "+t.getReminderPeriod()+"</h3>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(MailClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
