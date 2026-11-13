package kira.course.application.port.out.persistance.course;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface CourseCheckStatusPort {

    CourseStatus findCourseStatusByModuleId(Id<CourseModule> moduleId);

    CourseStatus findCourseStatusByLessonId(Id<Lesson> lessonId);

    CourseStatus findCourseStatusByTestId(Id<Test> testId);

    Optional<CourseStatus> findCourseStatusById(Id<Course> courseId);
}
