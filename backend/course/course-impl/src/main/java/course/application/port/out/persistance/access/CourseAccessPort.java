package course.application.port.out.persistance.access;

import refactor.common.domain.Id;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.test.Test;
import refactor.course.implementation.domain.markers.Account;

public interface CourseAccessPort {

    boolean isLessonOwner(Id<Account> requesterId, Id<Lesson> lessonId);

    boolean isModuleOwner(Id<Account> requesterId, Id<CourseModule> moduleId);

    boolean isCourseOwner(Id<Account> requesterId, Id<Course> courseId);

    boolean isTestOwner(Id<Account> requesterId, Id<Test> testId);
}
