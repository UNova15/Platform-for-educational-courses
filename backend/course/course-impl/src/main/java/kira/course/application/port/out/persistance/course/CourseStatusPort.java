package kira.course.application.port.out.persistance.course;

import common.domain.Id;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface CourseStatusPort {

    CourseStatus findCourseStatusByModuleId(Id<CourseModule> moduleId);

    CourseStatus findCourseStatusByLessonId(Id<Lesson> lessonId);

    CourseStatus findCourseStatusByTestId(Id<Test> testId);
}
