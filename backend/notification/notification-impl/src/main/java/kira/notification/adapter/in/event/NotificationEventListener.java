package kira.notification.adapter.in.event;

import kira.course.events.ContentPublishEvent;
import kira.notification.application.port.in.publish.SendContentPublishedNotificationUseCase;
import kira.notification.application.port.in.resetprogress.SendProgressResetNotificationUseCase;
import kira.progress.event.ResetProgressEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final SendContentPublishedNotificationUseCase sendContentChangeNotification;
    private final SendProgressResetNotificationUseCase sendProgressResetNotification;

    @EventListener
    public void onContentPublishEvent(ContentPublishEvent event) {
        var notificationRecord = NotificationEventMapper.mapToContentPublishNotification(event);

        sendContentChangeNotification.sendContentPublished(notificationRecord);
    }

    @EventListener
    public void onProgressResetEvent(ResetProgressEvent event) {
        if (event.reason() != ResetProgressEvent.ResetReason.UPDATE) {
            return;
        }
        var notificationRecord = NotificationEventMapper.mapToResetProgressNotification(event);

        sendProgressResetNotification.sendResetProgress(notificationRecord);
    }
}
