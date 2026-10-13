package kira.progress.application.service.api;

import common.domain.entity.Id;
import java.util.List;
import kira.progress.api.EnrollmentProviderQuery;
import kira.progress.api.EnrollmentStatusQuery;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.query.PersonalProgressQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EnrollmentStatisticsQueryHandler implements EnrollmentProviderQuery, EnrollmentStatusQuery {
    private final EnrollmentLoadPort loadPort;
    private final PersonalProgressQueryPort progressPort;

    @Override
    public List<Long> findCoursesIdsThatUsersIsEnrolledIn(long userId) {
        Id<User> studentId = Id.of(userId);

        return progressPort.findCoursesIdsThatUsersIsEnrolledIn(studentId).stream()
                .map(Id::value)
                .toList();
    }

    @Override
    public boolean isUserEnrolledInCourse(long studentId, long courseId) {
        Id<User> userId = Id.of(studentId);
        Id<Course> mappedCourseId = Id.of(courseId);

        return loadPort.isEnrollmentExist(userId, mappedCourseId);
    }
}
