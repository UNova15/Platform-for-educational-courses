package kira.course.application.port.out.persistance.query;


import common.domain.Id;
import kira.course.application.port.in.query.owned.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

import java.util.List;
import java.util.Optional;

public interface OwnedCoursesQueryPort {
    List<OwnedCoursesView> findTeachersCourses(Id<User> teacherId);

    Optional<TeacherCourseView> findTeachersCourseById(Id<Course> courseId);

    Optional<TeachersModuleView> findTeachersModuleById(Id<CourseModule> moduleId);

    Optional<TeachersLessonView> findTeachersLessonById(Id<Lesson> lessonId);

    Optional<FullTestView> findFullTestById(Id<Test> testId);
}
