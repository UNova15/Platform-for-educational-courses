package refactor.course.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.common.domain.Id;
import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.*;
import refactor.course.domain.external.User;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.internal.course.Tag;
import refactor.infrastructure.accesstoken.TokenPayload;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseQueryController {
    private final CourseQueryUseCase queryUseCase;

    // поиск всех курсов которыми владеет учитель
    @GetMapping("/owned")
    @PreAuthorize("hasRole('TEACHER')")
    public OwnedCoursesListView findTeachersCourses(@AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());

        return queryUseCase.findTeachersCourses(userId);
    }

    // поиск курса которым владеет учитель по id
    @GetMapping("/owned/{courseId}")
    @PreAuthorize("hasRole('TEACHER')")
    public TeacherCourseView findTeachersCourseById(
            @PathVariable("courseId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        return queryUseCase.findTeachersCourseById(userId, courseId);
    }

    // поиск всех доступных курсов для пользователя/преподавателя. Доступно всем ролям и не аунтефицированным
    // пользователям
    @GetMapping
    public CursorPageResponse<CourseCursorView> findCoursesByCursor(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) Tag tag) {
        CursorCourseQuery query = new CursorCourseQuery(cursorId, limit, tag);
        return queryUseCase.findCoursesByCursor(query);
    }

    // поиск конкретного курса для студента/преподавателя по id
    @GetMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentCourseView findCourseById(
            @PathVariable("courseId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        return queryUseCase.findStudentCourseById(userId, courseId);
    }
}
