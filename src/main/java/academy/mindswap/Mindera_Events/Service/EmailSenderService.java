package academy.mindswap.Mindera_Events.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                byte[] body

    ) throws MessagingException {

        InputStream inputStream= new ByteArrayInputStream(body);

        MimeMessage mimeMessage= mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
        message.setFrom("fromemail@gmail.com");
        message.setTo(toEmail);
        //message.addInline("qrcode.png",new InputStreamResource(inputStream),"image/png");
        message.addAttachment("qrcode.png",new ByteArrayResource(body,"image/png"));
        //message.setText("Mail QR <img src=\"cid:qrcode.png\"></img>",true);
        message.setText("Mail QR<br></br>",true);
        message.setSubject(subject);

        mailSender.send(mimeMessage);


        System.out.println("Mail Sent...");



    }
}
