package course.application.port.out.persistance.query;

import refactor.common.domain.Id;
import refactor.course.application.port.in.query.learning.*;
import refactor.course.implementation.application.port.in.query.learning.*;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.test.Test;

import java.util.List;
import java.util.Optional;

public interface LearningQueryPort {

    List<UserCourseView> findCoursesThatUsersIsEnrolledIn(List<Id<Course>> ids);

    Optional<StudentsCourseView> findStudentsCourseViewById(Id<Course> id);

    Optional<StudentsModuleView> findStudentsModuleViewById(Id<CourseModule> id);

    Optional<StudentsLessonView> findStudentsLessonViewById(Id<Lesson> id);

    Optional<StudentsTestView> findStudentsTestView(Id<Test> id);
}
