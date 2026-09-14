package course.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refactor.common.domain.Id;
import refactor.course.application.port.in.query.owned.*;
import refactor.course.implementation.application.port.in.query.owned.*;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.test.Test;
import refactor.course.implementation.domain.markers.Account;
import refactor.infrastructure.accesstoken.TokenPayload;

import java.util.List;

@RestController
@RequestMapping("/owned")
@RequiredArgsConstructor
public class OwnedCoursesQueryController {
    private final OwnedCoursesQueryUseCase queryUseCase;

    // поиск всех курсов которыми владеет учитель
    @GetMapping("/courses")
    @PreAuthorize("hasRole('TEACHER')")
    public List<OwnedCoursesView> findTeachersCourses(@AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());

        return queryUseCase.findTeachersCourses(userId);
    }

    // поиск курса которым владеет учитель по id. Возвращает скелет курса(информация о курсе и краткая информация о
    // модулях)
    @GetMapping("/courses/{courseId}")
    @PreAuthorize("hasRole('TEACHER')")
    public TeacherCourseView findTeachersCourseById(
            @PathVariable("courseId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        return queryUseCase.findTeachersCourseById(userId, courseId);
    }

    // возвращает полную информацию о модуле и краткую информацию о уроках и тестах (их id и заголовки)
    @GetMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('TEACHER')")
    public TeachersModuleView findTeachersModuleById(
            @PathVariable("moduleId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return queryUseCase.findTeachersModuleById(userId, moduleId);
    }

    // возвращает полную информацию о тесте
    @GetMapping("/tests/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    public FullTestView findTeachersCoursesTestById(
            @PathVariable("testId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        return queryUseCase.findFullTestById(userId, testId);
    }

    // возвращает полную информацию о уроке
    @GetMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('TEACHER')")
    public TeachersLessonView findTeachersCoursesLessonById(
            @PathVariable("lessonId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        return queryUseCase.findTeachersLessonById(userId, lessonId);
    }
}
