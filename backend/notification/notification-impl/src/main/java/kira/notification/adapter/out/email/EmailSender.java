package kira.notification.adapter.out.email;

import kira.notification.application.port.out.notification.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailSender implements NotificationSender {
    private final JavaMailSender mailSender;
    private final String fromEMail;

    public EmailSender(JavaMailSender sender, @Value("${spring.mail.username}") String from) {
        this.mailSender = sender;
        this.fromEMail = from;
    }

    @Override
    public void send(String recipient, String subject, String text) {
        var message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        try {
            helper.setTo(recipient);
            helper.setFrom(fromEMail);
            helper.setText(text, true);
            helper.setSubject(subject);

            mailSender.send(message);
        } catch (Exception exception) {
            log.error("Failed to send email notification with subject '{}'", subject, exception);
        }
    }
}
