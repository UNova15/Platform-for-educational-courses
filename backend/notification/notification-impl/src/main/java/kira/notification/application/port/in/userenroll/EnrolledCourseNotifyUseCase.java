package kira.notification.application.port.in.userenroll;

import common.domain.entity.Id;
import kira.notification.domain.marker.Course;
import kira.notification.domain.marker.User;

public interface EnrolledCourseNotifyUseCase {
    void sendTeacherNotification(Id<User> studentId, Id<Course> courseId);
}
