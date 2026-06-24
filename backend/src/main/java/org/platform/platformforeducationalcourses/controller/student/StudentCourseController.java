package org.platform.platformforeducationalcourses.controller.student;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.course.StudentCourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.enrollment.CourseEnrolledFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestPostRequest;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;
import org.platform.platformforeducationalcourses.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для взаимодействия пользователя со своими курсами
 */
@RestController
@RequestMapping("student/courses")
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor
public class StudentCourseController {
    private final CourseLearningService learningService;
    private final CourseQueryService courseQueryService;
    private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<CourseEnrolledFindResponse>> getCourses(
            @AuthenticationPrincipal SecurityUser securityUser) {
        List<CourseEnrolledFindResponse> courses = enrollmentService.getCoursesForStudent(securityUser.getId());
        return ResponseEntity.ok(courses);
    }

    @GetMapping("{courseId}")
    @PreAuthorize("@courseSecurity.canAccessCourse(#securityUser.id,#courseId)")
    public ResponseEntity<StudentCourseFindResponse> getCourse(
            @PathVariable long courseId, @AuthenticationPrincipal SecurityUser securityUser) {
        StudentCourseFindResponse response = courseQueryService.getCourseForStudent(securityUser.getId(), courseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{courseId}/modules/{moduleId}/lessons/{lessonId}")
    @PreAuthorize("@courseSecurity.canAccessLesson(#securityUser.id,#courseId,#moduleId,#lessonId)")
    public ResponseEntity<LessonFindResponse> getLesson(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long lessonId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        LessonFindResponse response = learningService.getLesson(securityUser.getId(), lessonId);
        return ResponseEntity.ok(response);
    }

    // получение ответов пользователя на тест
    @GetMapping("{courseId}/modules/{moduleId}/tests/{testId}/review")
    @PreAuthorize("@courseSecurity.canAccessTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<TestReview> getTestReview(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long testId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        TestReview response = learningService.getTestReview(testId, securityUser.getId());
        return ResponseEntity.ok(response);
    }

    // создание попытки теста и получение теста
    @PostMapping("{courseId}/modules/{moduleId}/tests/{testId}/attempts")
    @PreAuthorize("@courseSecurity.canAccessTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<TestFindResponse> startTestAttempt(
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @PathVariable long testId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        TestFindResponse response = learningService.startAttempt(testId, securityUser.getId());
        return ResponseEntity.ok(response);
    }

    // сохранение ответов
    @PostMapping("{courseId}/modules/{moduleId}/tests/{testId}/submit")
    @PreAuthorize("@courseSecurity.canAccessTest(#securityUser.id,#courseId,#moduleId,#testId)")
    public ResponseEntity<Void> postTest(
            @PathVariable long testId,
            @PathVariable long courseId,
            @PathVariable long moduleId,
            @RequestBody @Valid TestPostRequest request,
            @AuthenticationPrincipal SecurityUser securityUser) {
        learningService.endAttempt(request, securityUser.getId(), testId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
