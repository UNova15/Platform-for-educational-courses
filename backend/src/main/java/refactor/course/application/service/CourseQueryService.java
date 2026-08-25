package refactor.course.application.service;

import lombok.RequiredArgsConstructor;
import refactor.common.domain.Id;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.domain.CourseNotFoundException;
import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.*;
import org.springframework.stereotype.Service;
import refactor.course.application.port.out.external.EnrollmentCheckPort;
import refactor.course.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.application.port.out.persistance.course.CourseLoadPort;
import refactor.course.application.port.out.persistance.course.CourseQueryPort;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;

@Service
@RequiredArgsConstructor
public class CourseQueryService implements CourseQueryUseCase {
    private final CourseAccessPort accessPort;
    private final CourseQueryPort queryPort;
    private final CourseLoadPort loadPort;
    private final EnrollmentCheckPort enrollmentCheckPort;

    @Override
    public OwnedCoursesListView findTeachersCourses(Id<User> teacherId) {
        return queryPort.findTeachersCourses(teacherId);
    }

    //TODO: вынести проверки в отдельный компонент
    @Override
    public TeacherCourseView findTeachersCourseById(Id<User> teacherId, Id<Course> courseId) {
        if (!loadPort.isExist(courseId)) {
            throw new CourseNotFoundException(courseId, teacherId);
        }

        if (!accessPort.isCourseOwner(teacherId, courseId)) {
            throw new CourseAccessException(courseId, teacherId);
        }

        return queryPort
                .findTeachersCourseById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));
    }

    @Override
    public CursorPageResponse<CourseCursorView> findCoursesByCursor(CursorCourseQuery query) {
        return queryPort.findCoursesByCursor(query);
    }

    @Override
    public StudentCourseView findStudentCourseById(Id<User> requesterId, Id<Course> courseId) {
        if(!loadPort.isExist(courseId)){
            throw new CourseNotFoundException(courseId,requesterId);
        }

        if(!enrollmentCheckPort.isUserEnrolledInCourse(requesterId,courseId)){
            throw new CourseAccessException(courseId,requesterId);
        }

        return queryPort.findStudentCourseById(courseId);
    }
}
