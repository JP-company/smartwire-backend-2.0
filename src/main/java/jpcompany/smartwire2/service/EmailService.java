package jpcompany.smartwire2.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jpcompany.smartwire2.common.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final JwtToken jwtToken;

    public void sendEmail(Long memberId, String loginEmail) {
        try {
            String emailAuthToken = jwtToken.createEmailAuthToken(memberId);
            MimeMessage emailForm = createEmailForm(loginEmail, emailAuthToken);
            javaMailSender.send(emailForm);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // 메일 양식 작성
    private MimeMessage createEmailForm(String loginEmail, String emailAuthToken) throws MessagingException {
        String setFrom = "smartwire98@no-repl.com"; // 보내는 사람
        String title = "스마트와이어 회원가입 인증 메일"; // 제목

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, loginEmail); // 보낼 이메일 주소 설정
        message.setSubject(title); // 제목 설정
        message.setFrom(setFrom); // 보내는 사람 설정
        message.setText(setContext(emailAuthToken), "utf-8", "html"); // 이메일 내용 설정
        return message;
    }

    // 타임리프를 이용한 context 설정, 보내는 내용
    private String setContext(String emailAuthToken) {
        Context context = new Context();
        context.setVariable("emailAuthToken", emailAuthToken);
        return templateEngine.process("email/verification", context); // mail.html
    }
}