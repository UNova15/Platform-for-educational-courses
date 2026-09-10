package refactor.course.application.port.out.persistance.query;

import refactor.common.domain.Id;
import refactor.course.application.port.in.query.owned.*;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.markers.Account;

import java.util.List;
import java.util.Optional;

public interface OwnedCoursesQueryPort {
    List<OwnedCoursesView> findTeachersCourses(Id<Account> teacherId);

    Optional<TeacherCourseView> findTeachersCourseById(Id<Course> courseId);

    Optional<TeachersModuleView> findTeachersModuleById(Id<CourseModule> moduleId);

    Optional<TeachersLessonView> findTeachersLessonById(Id<Lesson> lessonId);

    Optional<TeachersTestView> findTeachersTestById(Id<Test> testId);
}
