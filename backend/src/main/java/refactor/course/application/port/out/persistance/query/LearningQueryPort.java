package refactor.course.application.port.out.persistance.query;

import refactor.common.domain.Id;
import refactor.course.application.port.in.query.learning.*;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;

import java.util.List;
import java.util.Optional;

public interface LearningQueryPort {

    List<EnrolledCourse> findEnrolledCoursesByIds(List<Id<Course>> ids);

    Optional<StudentsCourseView> findStudentsCourseViewById(Id<Course> id);

    Optional<StudentsModuleView> findStudentsModuleViewById(Id<CourseModule> id);

    Optional<StudentsLessonView> findStudentsLessonViewById(Id<Lesson> id);

    Optional<StudentsTestView> findStudentsTestView(Id<Test> id);
}
