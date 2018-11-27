package brozart.authorization.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTokenMail(final String subject, final String message, final String url, final String token,
                              final String recipient) {
        final String confirmationUrl = url + "?token=" + token;
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("info.brozartapps@gmail.com");
        mailSender.send(email);
    }
}
