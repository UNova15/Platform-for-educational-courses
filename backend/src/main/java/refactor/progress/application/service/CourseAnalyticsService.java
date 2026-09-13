package refactor.progress.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.progress.application.exception.CourseAccessDenialException;
import refactor.progress.application.exception.LessonAccessDenyException;
import refactor.progress.application.exception.TestAccessDenyException;
import refactor.progress.application.exception.TestAttemptNotFoundException;
import refactor.progress.application.port.in.query.TestAnswersView;
import refactor.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import refactor.progress.application.port.in.query.analytics.CourseAnalyticsUseCase;
import refactor.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import refactor.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import refactor.progress.application.port.out.external.CheckCourseOwnerPort;
import refactor.progress.application.port.out.persistance.query.statistics.StatisticsQueryPort;
import refactor.progress.application.port.out.persistance.query.shared.TestAnswersViewQueryPort;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseAnalyticsService implements CourseAnalyticsUseCase {
    private final CheckCourseOwnerPort checkCourseOwnerPort;
    private final TestAnswersViewQueryPort testAnswersViewQueryPort;
    private final StatisticsQueryPort statisticsQueryPort;

    @Override
    public List<EnrolledStudentsResult> findEnrolledStudents(Id<User> teacherId, Id<Course> courseId) {
        if (!checkCourseOwnerPort.isTeacherCourseOwner(teacherId, courseId)) {
            throw new CourseAccessDenialException(courseId, teacherId);
        }
        // В данный момент возвращаются только id записанных пользователей и время их записи. В будущем будет
        // возвращаться дополнительная информация о каждом записанном пользователе (Например ФИО, статистика по курсу и
        // тд)
        return statisticsQueryPort.findEnrolledUsersByCourseId(courseId);
    }

    @Override
    public List<StudentsLessonProgressResult> findWatchedLessonStudents(Id<User> teacherId, Id<Lesson> lessonId) {
        if (!checkCourseOwnerPort.isTeacherLessonOwner(teacherId, lessonId)) {
            throw new LessonAccessDenyException(teacherId, lessonId);
        }

        return statisticsQueryPort.findWatchedLessonStudentsByLessonId(lessonId);
    }

    @Override
    public List<CompletedTestStudentsResult> findStudentsTestResult(Id<User> teacherId, Id<Test> testId) {
        if (!checkCourseOwnerPort.isTeacherTestOwner(teacherId, testId)) {
            throw new TestAccessDenyException(teacherId, testId);
        }

        return statisticsQueryPort.findCompletedTestStudentsByTestId(testId);
    }

    @Override
    public TestAnswersView findStudentTestAnswers(Id<User> teacherId, Id<User> studentId, Id<Test> testId) {
        if (!checkCourseOwnerPort.isTeacherTestOwner(teacherId, testId)) {
            throw new TestAccessDenyException(teacherId, testId);
        }

        return testAnswersViewQueryPort
                .findTestAnswersViewByTestIdAndStudentId(studentId, testId)
                .orElseThrow(() -> new TestAttemptNotFoundException(studentId, testId));
    }
}
