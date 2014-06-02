/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Carlos
 */
public class EnviarEmail {
     private final Properties properties = new Properties();
    private Session session;
    
    private void init() {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.mail.sender", "FMMs");
        properties.put("mail.smtp.password", "proyectos641");
        properties.put("mail.smtp.user", "proyectodb2s641@gmail.com");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);
        //fmms03310215   fullmotelms@gmail.com.
        //motelapp2@gmail.com    , motelappuniajc
    }
    
    public boolean send(String destino,String asunto, String mensaje) {
        init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            message.setSubject(asunto);
            message.setText(mensaje);
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
        } catch (MessagingException e) {
            System.err.println("NO SE PUDO ENVIAR");
            return false;
        }
    }
}
