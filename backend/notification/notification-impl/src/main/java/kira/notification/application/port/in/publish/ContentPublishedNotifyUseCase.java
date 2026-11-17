package kira.notification.application.port.in.publish;

public interface ContentPublishedNotifyUseCase {

    void sendContentPublishedNotification(ContentPublishNotification notification);
}
