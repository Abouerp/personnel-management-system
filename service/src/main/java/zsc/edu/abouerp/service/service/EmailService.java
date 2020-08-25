package zsc.edu.abouerp.service.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.service.config.EmailProperties;

/**
 * @author Abouerp
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private static String sender;

    public EmailService(JavaMailSender javaMailSender, EmailProperties emailProperties) {
        this.javaMailSender = javaMailSender;
        this.sender = emailProperties.getUsername();
    }

    @Async
    public  void sendEmail(String receiver, String subject, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        System.out.println("--------------------------"+sender+"--------------------------------");
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
}
