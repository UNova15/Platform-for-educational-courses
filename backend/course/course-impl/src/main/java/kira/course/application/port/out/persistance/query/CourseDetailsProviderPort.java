package kira.course.application.port.out.persistance.query;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface CourseDetailsProviderPort {

    CourseDetails findCourseDetailsByModuleId(Id<CourseModule> moduleId);

    CourseDetails findCourseDetailsByLessonId(Id<Lesson> lessonId);

    CourseDetails findCourseDetailsByTestId(Id<Test> testId);

    Optional<CourseStatus> findCourseDetailsById(Id<Course> courseId);
}
