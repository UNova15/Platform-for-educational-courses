package kira.course.api;

import java.util.Optional;

public interface CourseProviderQuery {
    Optional<CourseInfo> getCourseInfo(long courseId);

    record CourseInfo(String title, long teacherId) {}

    Optional<TestInfo> getTestInfo(long testId);

    record TestInfo(String courseTitle, String testTitle, long teacherId) {}
}
