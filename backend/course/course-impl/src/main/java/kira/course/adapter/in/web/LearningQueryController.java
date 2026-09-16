package kira.course.adapter.in.web;

import common.domain.Id;
import kira.course.application.port.in.query.learning.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/learning")
@RequiredArgsConstructor
public class LearningQueryController {
    private final LearningQueryUseCase queryUseCase;

    //получение списка курсов на которые записан пользователь
    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public List<UserCourseView> findEnrolledCourses(@AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());

        return queryUseCase.findCoursesThatUsersIsEnrolledIn(userId);
    }

    // поиск конкретного курса для студента/преподавателя по id. Возвращает скелет курса
    @GetMapping("/courses/{courseId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsCourseView findStudentsCourse(
            @PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        return queryUseCase.findStudentsCourse(userId, courseId);
    }

    @GetMapping("/modules/{moduleId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsModuleView findStudentsModule(
            @PathVariable("moduleId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return queryUseCase.findStudentsModule(userId, moduleId);
    }

    @GetMapping("/lessons/{lessonId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsLessonView findStudentsLesson(
            @PathVariable("lessonId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        return queryUseCase.findStudentsLesson(userId, lessonId);
    }

    @GetMapping("/tests/{testId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public StudentsTestView findStudentsTest(
            @PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        return queryUseCase.findStudentsTest(userId, testId);
    }
}
