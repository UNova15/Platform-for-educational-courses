package kira.course.adapter.in.web;

import common.domain.Id;
import java.util.List;

import common.domain.Result;
import kira.course.application.port.in.query.learning.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning")
@RequiredArgsConstructor
public class LearningQueryController {
    private final LearningQueryUseCase queryUseCase;

    // получение списка курсов на которые записан пользователь
    @GetMapping("/courses")
    public List<UserCourseView> findEnrolledCourses(@AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());

        return queryUseCase.findCoursesThatUsersIsEnrolledIn(userId);
    }

    // поиск конкретного курса для студента/преподавателя по id. Возвращает скелет курса
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> findStudentsCourse(
            @PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        Result<StudentsCourseView, LearningQueryUseCase.CourseQueryError> result =
                queryUseCase.findStudentsCourse(userId, courseId);

        return result.fold(ResponseEntity::ok, error -> switch (error) {
            case COURSE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        });
    }

    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<?> findStudentsModule(
            @PathVariable("moduleId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        Result<StudentsModuleView, LearningQueryUseCase.ModuleQueryError> result =
                queryUseCase.findStudentsModule(userId, moduleId);

        return result.fold(ResponseEntity::ok, error -> switch (error) {
            case MODULE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        });
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<?> findStudentsLesson(
            @PathVariable("lessonId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        Result<StudentsLessonView, LearningQueryUseCase.LessonQueryError> result =
                queryUseCase.findStudentsLesson(userId, lessonId);

        return result.fold(ResponseEntity::ok, error -> switch (error) {
            case LESSON_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        });
    }

    @GetMapping("/tests/{testId}")
    public ResponseEntity<?> findStudentsTest(
            @PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        Result<StudentsTestView, LearningQueryUseCase.TestQueryError> result =
                queryUseCase.findStudentsTest(userId, testId);

        return result.fold(ResponseEntity::ok, error -> switch (error) {
            case TEST_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case TEST_NOT_STARTED -> ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }
}
