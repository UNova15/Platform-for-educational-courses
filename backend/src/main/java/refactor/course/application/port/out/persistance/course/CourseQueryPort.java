package refactor.course.application.port.out.persistance.course;

import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.CourseCursorResult;
import refactor.course.application.port.in.course.query.CourseQueryResult;
import refactor.course.application.port.in.course.query.CursorCourseQuery;

import java.util.List;

public interface CourseQueryPort {
    List<CourseQueryResult> findTeachersCourses(long teacherId);

    CursorPageResponse<CourseCursorResult> findCoursesByCursor(CursorCourseQuery query);
}
