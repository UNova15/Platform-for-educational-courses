package kira.course.application.port.out.persistance.query;

import common.domain.entity.Id;
import kira.course.api.CourseProviderQuery;
import kira.course.domain.course.Course;

import java.util.Optional;

public interface CourseProviderPort {
    Optional<CourseProviderQuery.CourseInfo> getCourseInfo(Id<Course> courseId);
}
