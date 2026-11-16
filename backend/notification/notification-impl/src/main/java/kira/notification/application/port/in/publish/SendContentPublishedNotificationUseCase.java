package kira.notification.application.port.in.publish;

public interface SendContentPublishedNotificationUseCase {

    void sendContentPublished(ContentPublishNotification notification);
}
