package refactor.course.application.service.query;

import lombok.RequiredArgsConstructor;
import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.CourseQueryResult;
import refactor.course.application.port.in.course.query.CourseCursorResult;
import org.springframework.stereotype.Service;
import refactor.course.application.port.in.course.query.CourseQueryUseCase;
import refactor.course.application.port.in.course.query.CursorCourseQuery;
import refactor.course.application.port.out.persistance.course.CourseQueryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseQueryService implements CourseQueryUseCase {
    private final CourseQueryPort queryPort;

    @Override
    public List<CourseQueryResult> findTeachersCoursesInfo(long teacherId) {
        return queryPort.findTeachersCourses(teacherId);
    }

    @Override
    public CursorPageResponse<CourseCursorResult> findCoursesByCursor(CursorCourseQuery query) {
        return queryPort.findCoursesByCursor(query);
    }
}
