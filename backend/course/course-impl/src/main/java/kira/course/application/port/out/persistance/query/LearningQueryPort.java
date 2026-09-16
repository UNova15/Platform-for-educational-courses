package kira.course.application.port.out.persistance.query;


import common.domain.Id;
import kira.course.application.port.in.query.learning.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

import java.util.List;
import java.util.Optional;

public interface LearningQueryPort {

    List<UserCourseView> findCoursesThatUsersIsEnrolledIn(List<Id<Course>> ids);

    Optional<StudentsCourseView> findStudentsCourseViewById(Id<Course> id);

    Optional<StudentsModuleView> findStudentsModuleViewById(Id<CourseModule> id);

    Optional<StudentsLessonView> findStudentsLessonViewById(Id<Lesson> id);

    Optional<StudentsTestView> findStudentsTestView(Id<Test> id);
}
