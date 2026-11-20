package kira.notification.application.port.in;

public interface CodeConfirmationNotifyUsaCase {
    void sendConfirmationCode(String email, String code);
}
