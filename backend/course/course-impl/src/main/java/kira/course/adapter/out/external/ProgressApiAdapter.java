package kira.course.adapter.out.external;

import common.domain.Id;
import kira.course.application.port.out.external.EnrollmentCheckPort;
import kira.course.application.port.out.external.TestCompletionCheckPort;
import kira.course.application.port.out.external.UserEnrollmentProviderPort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import kira.progress.api.EnrollmentProviderQuery;
import kira.progress.api.EnrollmentStatusQuery;
import kira.progress.api.TestStatusQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProgressApiAdapter implements EnrollmentCheckPort, UserEnrollmentProviderPort, TestCompletionCheckPort {
    private final EnrollmentStatusQuery enrollmentStatusQuery;
    private final EnrollmentProviderQuery enrollmentProviderQuery;
    private final TestStatusQuery testStatusQuery;

    @Override
    public boolean isUserEnrolledInCourse(Id<User> studentId, Id<Course> courseId) {
        return enrollmentStatusQuery.isUserEnrolledInCourse(studentId.value(), courseId.value());
    }

    @Override
    public List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId) {
        return enrollmentProviderQuery.findCoursesIdsThatUsersIsEnrolledIn(userId.value()).stream()
                .map(Id::<Course>of)
                .toList();
    }

    @Override
    public boolean isTestCompleted(Id<User> studentId, Id<Test> testId) {
        return testStatusQuery.hasCompletedTest(studentId.value(), testId.value());
    }

    @Override
    public boolean isStudentSolvingTest(Id<User> studentId, Id<Test> testId) {
        return testStatusQuery.isSolvingTest(studentId.value(), testId.value());
    }
}
