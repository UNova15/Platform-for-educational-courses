package kira.course.application.port.out.persistance.query;

import common.domain.entity.Id;
import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.owned.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface OwnedCoursesQueryPort {
    List<OwnedCoursesView> findTeachersCourses(Id<User> teacherId);

    Optional<TeacherCourseView> findOwnedCourseById(Id<Course> courseId);

    Optional<OwnedModuleView> findOwnedModuleById(Id<CourseModule> moduleId);

    Optional<TeachersLessonView> findOwnedLessonById(Id<Lesson> lessonId);

    Optional<OwnedTestView> findOwnedTestById(Id<Test> testId);
}
