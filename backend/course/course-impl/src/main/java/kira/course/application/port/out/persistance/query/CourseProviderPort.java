package kira.course.application.port.out.persistance.query;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.api.CourseProviderQuery;
import kira.course.domain.course.Course;
import kira.course.domain.test.Test;

public interface CourseProviderPort {
    Optional<CourseProviderQuery.CourseInfo> getCourseInfo(Id<Course> courseId);

    Optional<CourseProviderQuery.TestInfo> getTestInfo(Id<Test> testId);
}
