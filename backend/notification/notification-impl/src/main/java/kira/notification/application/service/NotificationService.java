package kira.notification.application.service;

import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import java.util.List;
import kira.notification.application.port.in.publish.ContentPublishNotification;
import kira.notification.application.port.in.publish.ContentPublishedNotifyUseCase;
import kira.notification.application.port.in.resetprogress.ResetProgressNotification;
import kira.notification.application.port.in.resetprogress.ProgressResetNotifyUseCase;
import kira.notification.application.port.in.userenroll.UserEnrolledCourseNotifyUseCase;
import kira.notification.application.port.out.internal.CourseProviderPort;
import kira.notification.application.port.out.internal.EnrollmentProviderPort;
import kira.notification.application.port.out.internal.UserProviderPort;
import kira.notification.application.port.out.notification.NotificationSender;
import kira.notification.application.port.out.render.MessageTemplatePort;
import kira.notification.domain.Course;
import kira.notification.domain.NotificationAggregateType;
import kira.notification.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService
        implements ContentPublishedNotifyUseCase, ProgressResetNotifyUseCase, UserEnrolledCourseNotifyUseCase {
    private final NotificationSender sender;
    private final MessageTemplatePort formatter;

    private final EnrollmentProviderPort progressProviderPort;
    private final UserProviderPort userProviderPort;
    private final CourseProviderPort courseProviderPort;

    @Async
    @Override
    public void sendContentPublishedNotification(ContentPublishNotification notification) {
        List<Id<User>> studentsIds = progressProviderPort.findEnrolledUsers(notification.courseId());

        var message = formatter.renderContentPublish(notification.type(), notification.name());

        sendNotification(studentsIds, message);
    }

    @Async
    @Override
    public void sendResetProgressNotification(ResetProgressNotification notification) {
        var message = formatter.renderResetProgress(notification.resourceType(), notification.name());

        sendNotification(notification.studentIds(), message);
    }

    @Async
    @Override
    public void sendTeacherNotification(Id<User> studentId, Id<Course> courseId) {
        CourseProviderPort.CourseDetails details = courseProviderPort
                .getCourseDetails(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(NotificationAggregateType.COURSE, courseId));

        String studentName = userProviderPort
                .getNameById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(NotificationAggregateType.USER, studentId));

        var message = formatter.renderUserEnroll(details.name(), studentName);

        sendNotification(List.of(details.teacherId()), message);
    }

    private void sendNotification(List<Id<User>> recipientIds, MessageTemplatePort.Notification notification) {
        List<String> emails = userProviderPort.getEmailByIds(recipientIds);

        for (var email : emails) {
            sender.send(email, notification.subject(), notification.body());
        }
    }
}
