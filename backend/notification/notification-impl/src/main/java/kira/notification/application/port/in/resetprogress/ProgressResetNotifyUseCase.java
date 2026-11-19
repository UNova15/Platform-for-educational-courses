package kira.notification.application.port.in.resetprogress;

public interface ProgressResetNotifyUseCase {
    void sendResetProgressNotification(ResetProgressNotification notification);
}
