package course.application.port.out.persistance.query;

import refactor.common.domain.Id;
import refactor.course.application.port.in.query.owned.*;
import refactor.course.implementation.application.port.in.query.owned.*;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.markers.Account;
import refactor.course.implementation.domain.test.Test;

import java.util.List;
import java.util.Optional;

public interface OwnedCoursesQueryPort {
    List<OwnedCoursesView> findTeachersCourses(Id<Account> teacherId);

    Optional<TeacherCourseView> findTeachersCourseById(Id<Course> courseId);

    Optional<TeachersModuleView> findTeachersModuleById(Id<CourseModule> moduleId);

    Optional<TeachersLessonView> findTeachersLessonById(Id<Lesson> lessonId);

    Optional<FullTestView> findFullTestById(Id<Test> testId);
}
