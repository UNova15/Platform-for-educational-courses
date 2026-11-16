package kira.progress.application.port.out.persistance.query;

import common.domain.entity.Id;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;

import java.util.List;

public interface EnrollmentApiQueryPort {
    List<Id<User>> findEnrolledUsersByCourseId(Id<Course> courseId);

    List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId);

    boolean isEnrollmentExist(Id<User> studentId, Id<Course> courseId);
}
