package refactor.course.application.port.out.persistance.access;

import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.markers.Account;

public interface CourseAccessPort {

    boolean isLessonOwner(Id<Account> requesterId, Id<Lesson> lessonId);

    boolean isModuleOwner(Id<Account> requesterId, Id<CourseModule> moduleId);

    boolean isCourseOwner(Id<Account> requesterId, Id<Course> courseId);

    boolean isTestOwner(Id<Account> requesterId, Id<Test> testId);
}
