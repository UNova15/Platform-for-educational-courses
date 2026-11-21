package kira.notification.application.service;

import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import java.util.List;
import kira.notification.application.port.in.CodeConfirmationNotifyUsaCase;
import kira.notification.application.port.in.CompletedTestNotifyUseCase;
import kira.notification.application.port.in.publish.ContentPublishNotification;
import kira.notification.application.port.in.publish.ContentPublishedNotifyUseCase;
import kira.notification.application.port.in.resetprogress.ProgressResetNotifyUseCase;
import kira.notification.application.port.in.resetprogress.ResetProgressNotification;
import kira.notification.application.port.in.userenroll.EnrolledCourseNotifyUseCase;
import kira.notification.application.port.out.internal.CourseProviderPort;
import kira.notification.application.port.out.internal.EnrollmentProviderPort;
import kira.notification.application.port.out.internal.UserProviderPort;
import kira.notification.application.port.out.notification.NotificationSender;
import kira.notification.application.port.out.render.MessageTemplatePort;
import kira.notification.domain.NotificationAggregateType;
import kira.notification.domain.marker.Course;
import kira.notification.domain.marker.Test;
import kira.notification.domain.marker.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

// Рефактор. Размер и кол-во портов и унификация методов
// Методы заменить общим методом обработки: render(Object ... args)
@Service
@RequiredArgsConstructor
public class NotificationService
        implements ContentPublishedNotifyUseCase,
                ProgressResetNotifyUseCase,
                EnrolledCourseNotifyUseCase,
                CompletedTestNotifyUseCase,
                CodeConfirmationNotifyUsaCase {

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

        var message = formatter.renderUserEnroll(details.courseTitle(), studentName);

        sendNotification(List.of(details.teacherId()), message);
    }

    @Async
    @Override
    public void sendCompletedTestNotification(Id<User> studentId, Id<Test> testId, long score) {
        CourseProviderPort.TestDetails details = courseProviderPort
                .getTestDetails(testId)
                .orElseThrow(() -> new ResourceNotFoundException(NotificationAggregateType.TEST, testId));

        String studentName = userProviderPort
                .getNameById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(NotificationAggregateType.USER, studentId));

        var message = formatter.renderUserCompletedTest(studentName, details.courseTitle(), details.testTitle(), score);
        sendNotification(List.of(details.teacherId()), message);
    }

    @Async
    @Override
    public void sendConfirmationCode(String email, String code) {
        var message = formatter.renderConfirmationCode(code);

        sender.send(email, message.subject(), message.body());
    }

    private void sendNotification(List<Id<User>> recipientIds, MessageTemplatePort.Notification notification) {
        if (recipientIds.isEmpty()) {
            return;
        }

        List<String> emails = userProviderPort.getEmailByIds(recipientIds);

        for (var email : emails) {
            sender.send(email, notification.subject(), notification.body());
        }
    }
}
