package kira.progress.application.port.out.persistance.enrollment;

import common.domain.Id;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;

import java.util.List;

public interface EnrollmentStatisticsPort {
    List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId);
}
