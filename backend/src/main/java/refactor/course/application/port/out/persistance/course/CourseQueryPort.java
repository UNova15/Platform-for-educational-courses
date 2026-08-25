package refactor.course.application.port.out.persistance.course;

import refactor.common.domain.Id;
import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.*;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;

import java.util.Optional;

public interface CourseQueryPort {
    OwnedCoursesListView findTeachersCourses(Id<User> teacherId);

    CursorPageResponse<CourseCursorView> findCoursesByCursor(CursorCourseQuery query);

    Optional<TeacherCourseView> findTeachersCourseById(Id<Course> courseId);

    StudentCourseView findStudentCourseById(Id<Course> courseId);
}
