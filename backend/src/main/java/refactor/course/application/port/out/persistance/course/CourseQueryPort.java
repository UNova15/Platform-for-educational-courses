package refactor.course.application.port.out.persistance.course;

import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.*;

import java.util.Optional;


public interface CourseQueryPort {
    OwnedCoursesListView findTeachersCourses(long teacherId);

    CursorPageResponse<CourseCursorView> findCoursesByCursor(CursorCourseQuery query);

    Optional<TeacherCourseView> findTeachersCourseById(long courseId);

    StudentCourseView findStudentCourseById(long courseId);
}
