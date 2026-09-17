package kira.progress.application.service.api;

import common.domain.Id;
import kira.progress.api.EnrollmentProviderQuery;
import kira.progress.api.EnrollmentStatusQuery;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentStatisticsPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentStatisticsQueryHandler implements EnrollmentProviderQuery, EnrollmentStatusQuery {
    private final EnrollmentLoadPort loadPort;
    private final EnrollmentStatisticsPort statisticsPort;

    @Override
    public List<Long> findCoursesIdsThatUsersIsEnrolledIn(long userId) {
        Id<User> studentId = Id.of(userId);

        return statisticsPort.findCoursesIdsThatUsersIsEnrolledIn(studentId).stream()
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
