package jpcompany.smartwire2.common.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final String EMAIL_FROM = "smartwire";

    public void sendEmail(String emailTo, String title, String content) {
        try {
            MimeMessage emailForm = javaMailSender.createMimeMessage();
            emailForm.addRecipients(MimeMessage.RecipientType.TO, emailTo);
            emailForm.setSubject(title);
            emailForm.setFrom(EMAIL_FROM);
            emailForm.setText(content, "utf-8", "html");
            javaMailSender.send(emailForm);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}