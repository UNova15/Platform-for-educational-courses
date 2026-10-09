package kira.course.application.port.out.persistance.course;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface CourseStatusPort {

    boolean isArchived(Id<Course> courseId);

    boolean isArchivedByModuleId(Id<CourseModule> moduleId);

    boolean isArchivedByLessonId(Id<Lesson> lessonId);

    boolean isArchivedByTestId(Id<Test> testId);

    boolean isPublishedByModuleId(Id<CourseModule> moduleId);
}
