package kira.notification.application.port.out.notification;

public interface NotificationSender {
    void send(String recipientName, String subject, String text);
}
