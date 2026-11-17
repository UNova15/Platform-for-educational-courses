package kira.notification.adapter.in.event;

import common.domain.entity.Id;
import kira.course.events.ContentPublishEvent;
import kira.notification.application.port.in.CompletedTestNotifyUseCase;
import kira.notification.application.port.in.publish.ContentPublishedNotifyUseCase;
import kira.notification.application.port.in.resetprogress.ProgressResetNotifyUseCase;
import kira.notification.application.port.in.userenroll.EnrolledCourseNotifyUseCase;
import kira.notification.domain.marker.Course;
import kira.notification.domain.marker.Test;
import kira.notification.domain.marker.User;
import kira.progress.event.CompletedTestEvent;
import kira.progress.event.CourseEnrollEvent;
import kira.progress.event.ResetProgressEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final ContentPublishedNotifyUseCase contentPublishedNotify;
    private final ProgressResetNotifyUseCase progressResetNotify;
    private final EnrolledCourseNotifyUseCase enrolledCourseNotify;
    private final CompletedTestNotifyUseCase completedTestNotify;

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
    public void onCourseEnrollEvent(CourseEnrollEvent event) {
        Id<User> teacherId = Id.of(event.userId());
        Id<Course> courseId = Id.of(event.courseId());

        enrolledCourseNotify.sendTeacherNotification(teacherId, courseId);
    }

    @EventListener
    public void onCompletedTestEvent(CompletedTestEvent event) {
        Id<User> userId = Id.of(event.studentId());
        Id<Test> testId = Id.of(event.testId());

        completedTestNotify.sendCompletedTestNotification(userId, testId, event.score());
    }
}
