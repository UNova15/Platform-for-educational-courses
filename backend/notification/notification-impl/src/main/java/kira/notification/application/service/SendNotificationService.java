package kira.notification.application.service;

import common.domain.entity.Id;
import java.util.List;
import kira.notification.application.port.in.publish.ContentPublishNotification;
import kira.notification.application.port.in.publish.SendContentPublishedNotificationUseCase;
import kira.notification.application.port.in.resetprogress.ResetProgressNotification;
import kira.notification.application.port.in.resetprogress.SendProgressResetNotificationUseCase;
import kira.notification.application.port.out.external.EnrollmentProviderPort;
import kira.notification.application.port.out.external.UserProviderPort;
import kira.notification.application.port.out.notification.NotificationSender;
import kira.notification.application.port.out.render.MessageTemplatePort;
import kira.notification.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendNotificationService
        implements SendContentPublishedNotificationUseCase, SendProgressResetNotificationUseCase {
    private final NotificationSender sender;
    private final MessageTemplatePort formatter;

    private final EnrollmentProviderPort progressProviderPort;
    private final UserProviderPort userProviderPort;

    @Async
    @Override
    public void sendContentPublished(ContentPublishNotification notification) {
        // TODO
        IO.println("Вызов метода отправки сообщения о публикации");

        List<Id<User>> studentsIds = progressProviderPort.findEnrolledUsers(notification.courseId());

        var message = formatter.renderContentPublish(notification.type(), notification.name());

        sendNotification(studentsIds, message);
    }

    @Async
    @Override
    public void sendResetProgress(ResetProgressNotification notification) {
        // TODO
        IO.println("Вызов метода отправки сообщения о сбросе прогресса");
        var message = formatter.renderResetProgress(notification.resourceType(), notification.name());

        sendNotification(notification.studentIds(), message);
    }

    private void sendNotification(List<Id<User>> studentsIds, MessageTemplatePort.Notification notification) {
        List<String> emails = userProviderPort.getEmailByIds(studentsIds);

        for (var email : emails) {
            // TODO
            IO.println("Отправка сообщения:");
            IO.println(notification.body());
            sender.send(email, notification.subject(), notification.body());
        }
    }
}
