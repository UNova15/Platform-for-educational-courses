package kira.course.api;

import java.util.Optional;

public interface CourseProviderQuery {
    Optional<CourseInfo> getCourseInfo(long courseId);

    record CourseInfo(String title, long teacherId) {}
}
