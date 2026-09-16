package kira.progress.adapter.in.web;

import common.domain.Id;
import kira.infrastructure.api.TokenPayload;
import kira.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import kira.progress.application.port.in.query.analytics.CourseAnalyticsUseCase;
import kira.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import kira.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owned")
@RequiredArgsConstructor
public class CourseAnalyticsController {
    private final CourseAnalyticsUseCase courseAnalyticsUseCase;

    @GetMapping("/tests/{testId}/results")
    @ResponseStatus(HttpStatus.OK)
    public List<CompletedTestStudentsResult> findStudentsTestResult(
            @PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        return courseAnalyticsUseCase.findStudentsTestResult(userId, testId);
    }

    @GetMapping("/courses/{courseId}/enrollments")
    @ResponseStatus(HttpStatus.OK)
    public List<EnrolledStudentsResult> findEnrolledStudents(
            @PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Course> courseId = Id.of(resourceId);
        Id<User> userId = Id.of(token.userId());

        return courseAnalyticsUseCase.findEnrolledStudents(userId, courseId);
    }

    @GetMapping("/lessons/{lessonId}/results")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentsLessonProgressResult> findWatchedLessonStudents(
            @PathVariable("lessonId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Lesson> lessonId = Id.of(resourceId);
        Id<User> userId = Id.of(token.userId());

        return courseAnalyticsUseCase.findWatchedLessonStudents(userId, lessonId);
    }

    @GetMapping("/tests/{testId}/completed/{studentId}/answers")
    @ResponseStatus(HttpStatus.OK)
    public TestAnswersView findStudentTestAnswers(
            @PathVariable("testId") Long resourceId,
            @PathVariable("studentId") Long userId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> studentId = Id.of(userId);
        Id<User> teacherId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        return courseAnalyticsUseCase.findStudentTestAnswers(teacherId, studentId, testId);
    }
}
