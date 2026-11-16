package kira.notification.application.port.out.notification;

public interface NotificationSender {
    void send(String to, String subject, String text);
}
