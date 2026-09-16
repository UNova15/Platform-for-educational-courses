package kira.course.application.port.out.persistance.access;


import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface CourseAccessPort {

    boolean isLessonOwner(Id<User> requesterId, Id<Lesson> lessonId);

    boolean isModuleOwner(Id<User> requesterId, Id<CourseModule> moduleId);

    boolean isCourseOwner(Id<User> requesterId, Id<Course> courseId);

    boolean isTestOwner(Id<User> requesterId, Id<Test> testId);
}
