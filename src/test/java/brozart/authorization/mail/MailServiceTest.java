package brozart.authorization.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailService mailService;

    @Test
    public void testSendTokenMail() {
        final String subject = "subject";
        final String message = "message";
        final String url = "url";
        final String token = "token";
        final String recipient = "recipient";

        mailService.sendTokenMail(subject, message, url, token, recipient);

        // Verify
        final ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(mailCaptor.capture());

        final SimpleMailMessage mail = mailCaptor.getValue();
        assertNotNull(mail);
        assertEquals(subject, mail.getSubject());
        assertNotNull(mail.getText());
        assertTrue(mail.getText().contains(message));
        assertTrue(mail.getText().contains(url));
        assertTrue(mail.getText().contains(token));
        assertNotNull(mail.getTo());
        assertNotEquals(0, mail.getTo().length);
        assertEquals(recipient, mail.getTo()[0]);
        assertEquals("info.brozartapps@gmail.com", mail.getFrom());
    }
}
