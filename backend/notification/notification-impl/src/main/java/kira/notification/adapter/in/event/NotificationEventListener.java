package kira.notification.adapter.in.event;

import common.domain.entity.Id;
import kira.course.events.ContentPublishEvent;
import kira.notification.application.port.in.publish.ContentPublishedNotifyUseCase;
import kira.notification.application.port.in.resetprogress.ProgressResetNotifyUseCase;
import kira.notification.application.port.in.userenroll.UserEnrolledCourseNotifyUseCase;
import kira.notification.domain.Course;
import kira.notification.domain.User;
import kira.progress.event.ResetProgressEvent;
import kira.progress.event.UserEnrollEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final ContentPublishedNotifyUseCase contentPublishedNotify;
    private final ProgressResetNotifyUseCase progressResetNotify;
    private final UserEnrolledCourseNotifyUseCase userEnrolledCourseNotify;

    @EventListener
    public void onContentPublishEvent(ContentPublishEvent event) {
        var notificationRecord = NotificationEventMapper.mapToContentPublishNotification(event);

        contentPublishedNotify.sendContentPublishedNotification(notificationRecord);
    }

    @EventListener
    public void onProgressResetEvent(ResetProgressEvent event) {
        if (event.reason() != ResetProgressEvent.ResetReason.UPDATE) {
            return;
        }
        var notificationRecord = NotificationEventMapper.mapToResetProgressNotification(event);

        progressResetNotify.sendResetProgressNotification(notificationRecord);
    }

    @EventListener
    public void onUserEnrollEvent(UserEnrollEvent event) {
        Id<User> teacherId = Id.of(event.userId());
        Id<Course> courseId = Id.of(event.courseId());

        userEnrolledCourseNotify.sendTeacherNotification(teacherId, courseId);
    }
}
