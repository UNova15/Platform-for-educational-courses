package kira.notification.application.port.in.userenroll;

import common.domain.entity.Id;
import kira.notification.domain.Course;
import kira.notification.domain.User;

public interface UserEnrolledCourseNotifyUseCase {
    void sendTeacherNotification(Id<User> studentId, Id<Course> courseId);
}
