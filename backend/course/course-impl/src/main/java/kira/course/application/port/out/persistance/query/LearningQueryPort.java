package kira.course.application.port.out.persistance.query;

import common.domain.entity.Id;
import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.learning.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;

public interface LearningQueryPort {

    List<UserCourseView> findCoursesThatUsersIsEnrolledIn(List<Id<Course>> ids);

    Optional<StudentsCourseView> findCourseViewByIdAndStatus(Id<Course> id, CourseContentStatus status);

    Optional<StudentsModuleView> findModuleViewByIdAndStatus(Id<CourseModule> id, CourseContentStatus status);

    Optional<StudentsLessonView> findLessonViewByIdAndStatus(Id<Lesson> id, CourseContentStatus status);

    Optional<StudentsTestView> findTestViewByIdAndStatus(Id<Test> id, CourseContentStatus status);
}
