package jpcompany.smartwire2.unit.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import jpcompany.smartwire2.common.email.EmailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class EmailServiceTest {

//    @Autowired
    private EmailService emailService;
    private JavaMailSenderImpl javaMailSender;
    private GreenMail greenMail;

    public EmailServiceTest() {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("localhost");
        javaMailSender.setPort(ServerSetupTest.SMTP.getPort());
        emailService = new EmailService(javaMailSender);
    }

    @BeforeEach
    public void beforeEach() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @AfterEach
    public void afterEach() {
        greenMail.stop();
    }

//    @Test
    void sendEmail() throws MessagingException {
        // given
        String emailTo = "recipient@example.com";
        String title = "Test subject";
        String content = "Test message";

        // when
        emailService.sendEmail(emailTo, title, content);

        // then
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages[0].getSubject()).isEqualTo(title);
        assertThat(receivedMessages.length).isEqualTo(1);
        assertThat(GreenMailUtil.getBody(receivedMessages[0]).trim()).isEqualTo(content);


//        // JavaMailSender 구현체 생성
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("localhost");
//        mailSender.setPort(3025);
//
//        // 이메일 메시지 설정
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("sender@example.com");
//        message.setTo("recipient@example.com");
//        message.setSubject("Test Subject");
//        message.setText("Test message");
//
//        // 메일 전송
//        mailSender.send(message);
//
//        // GreenMail 서버에서 메일 수신 검증
//        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
//        assertEquals(1, receivedMessages.length);
//        assertEquals("Test Subject", receivedMessages[0].getSubject());
//        assertEquals("Test message", GreenMailUtil.getBody(receivedMessages[0]).trim());
    }
}