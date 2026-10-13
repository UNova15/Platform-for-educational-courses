package kira.course.application.port.in.course.status;

import common.domain.entity.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;

public interface ArchiveUseCase {
    void archive(Id<User> requesterId, Id<Course> courseId);
}
