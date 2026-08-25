package refactor.course.application.port.out.persistance.access;

import refactor.common.domain.Id;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.internal.lesson.Lesson;
import refactor.course.domain.internal.module.CourseModule;
import refactor.course.domain.internal.test.Test;
import refactor.course.domain.external.User;

public interface CourseAccessPort {

    boolean isLessonOwner(Id<User> requesterId, Id<Lesson> lessonId);

    boolean isModuleOwner(Id<User> requesterId, Id<CourseModule> moduleId);

    boolean isCourseOwner(Id<User> requesterId, Id<Course> courseId);

    boolean isTestOwner(Id<User> requesterId, Id<Test> testId);
}
