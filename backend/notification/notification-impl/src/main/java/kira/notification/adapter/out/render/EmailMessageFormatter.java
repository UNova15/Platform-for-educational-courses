package kira.notification.adapter.out.render;

import java.text.MessageFormat;
import kira.notification.application.port.out.render.MessageTemplatePort;
import kira.notification.domain.PublishType;
import kira.notification.domain.ResetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMessageFormatter implements MessageTemplatePort {
    private final LocalMessageTemplateLoader loader;

    @Override
    public Notification renderContentPublish(PublishType type, String name) {
        final var emailType = EmailType.CONTENT_PUBLISHED;
        String template = loader.getTemplate(emailType);

        String typeName =
                switch (type) {
                    case LESSON -> "урок";
                    case MODULE -> "модуль";
                    case TEST -> "тест";
                };

        var body = MessageFormat.format(template, typeName, name);
        return new Notification(emailType.subject(), body);
    }

    @Override
    public Notification renderResetProgress(ResetType type, String name) {
        final var emailType = EmailType.PROGRESS_REMOVED;
        String template = loader.getTemplate(emailType);

        String typeName =
                switch (type) {
                    case LESSON -> "урок";
                    case TEST -> "тест";
                };

        var body = MessageFormat.format(template, typeName, name);
        return new Notification(emailType.subject(), body);
    }

    @Override
    public Notification renderUserEnroll(String courseName, String userName) {
        final var emailType = EmailType.USER_ENROLLED;

        String template = loader.getTemplate(emailType);
        var body = MessageFormat.format(template, courseName, userName);
        return new Notification(emailType.subject(), body);
    }

    @Override
    public Notification renderUserCompletedTest(String studentName, String courseTitle, String testTile, long score) {
        final var emailType = EmailType.USER_COMPLETED_TEST;

        String template = loader.getTemplate(emailType);
        var body = MessageFormat.format(template, studentName, courseTitle, testTile, score);
        return new Notification(emailType.subject(), body);
    }

    @Override
    public Notification renderConfirmationCode(String code) {
        final var emailType = EmailType.CONFIRMATION_CODE;

        String template = loader.getTemplate(emailType);
        var body = MessageFormat.format(template,code);
        return new Notification(emailType.subject(),body);
    }
}
