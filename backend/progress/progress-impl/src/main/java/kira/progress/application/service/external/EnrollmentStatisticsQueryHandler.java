package kira.progress.application.service.external;

import common.domain.entity.Id;
import java.util.List;
import kira.progress.api.EnrollmentProviderQuery;
import kira.progress.api.EnrollmentStatusQuery;
import kira.progress.application.port.out.persistance.query.EnrollmentApiQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EnrollmentStatisticsQueryHandler implements EnrollmentProviderQuery, EnrollmentStatusQuery {
    private final EnrollmentApiQueryPort enrollmentApiQueryPort;

    @Override
    public List<Long> findCoursesIdsThatUsersIsEnrolledIn(long userId) {
        Id<User> studentId = Id.of(userId);

        return enrollmentApiQueryPort.findCoursesIdsThatUsersIsEnrolledIn(studentId).stream()
                .map(Id::value)
                .toList();
    }

    @Override
    public List<Long> findEnrolledUsers(long courseId) {
        Id<Course> mappedId = Id.of(courseId);

        List<Id<User>> studentsIds = enrollmentApiQueryPort.findEnrolledUsersByCourseId(mappedId);
        return studentsIds.stream().map(Id::value).toList();
    }

    @Override
    public boolean isUserEnrolledInCourse(long studentId, long courseId) {
        Id<User> userId = Id.of(studentId);
        Id<Course> mappedCourseId = Id.of(courseId);

        return enrollmentApiQueryPort.isEnrollmentExist(userId, mappedCourseId);
    }
}
