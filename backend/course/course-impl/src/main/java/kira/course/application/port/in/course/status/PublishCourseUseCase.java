package kira.course.application.port.in.course.status;

import common.domain.entity.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;

public interface PublishCourseUseCase {
    void publish(Id<User> requesterId, Id<Course> courseId);
}
