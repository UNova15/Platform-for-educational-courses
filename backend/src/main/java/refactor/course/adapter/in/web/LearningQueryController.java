package refactor.course.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refactor.common.domain.Id;
import refactor.course.application.port.in.query.learning.*;
import refactor.course.domain.course.Course;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Test;
import refactor.course.domain.markers.Account;
import refactor.infrastructure.accesstoken.TokenPayload;

import java.util.List;

@RestController
@RequestMapping("/learning")
@RequiredArgsConstructor
public class LearningQueryController {
    private final LearningQueryUseCase queryUseCase;

    //получение списка курсов на которые записан пользователь
    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public List<EnrolledCourse> findEnrolledCourses(@AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());

        return queryUseCase.findEnrolledCourses(userId);
    }

    // поиск конкретного курса для студента/преподавателя по id. Возвращает скелет курса
    @GetMapping("/courses/{courseId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsCourseView findStudentsCourse(
            @PathVariable("courseId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        return queryUseCase.findStudentsCourse(userId, courseId);
    }

    @GetMapping("/modules/{moduleId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsModuleView findStudentsModule(
            @PathVariable("moduleId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return queryUseCase.findStudentsModule(userId, moduleId);
    }

    @GetMapping("/lessons/{lessonId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsLessonView findStudentsLesson(
            @PathVariable("lessonId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        return queryUseCase.findStudentsLesson(userId, lessonId);
    }

    @GetMapping("/tests/{testId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsTestView findStudentsTest(
            @PathVariable("testId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        return queryUseCase.findStudentsTest(userId, testId);
    }
}
