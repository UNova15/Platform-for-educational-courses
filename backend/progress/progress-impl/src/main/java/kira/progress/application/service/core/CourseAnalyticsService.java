package kira.progress.application.service.core;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.progress.application.exceptions.ProgressExceptionCode;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import kira.progress.application.port.in.query.analytics.CourseAnalyticsUseCase;
import kira.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import kira.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import kira.progress.application.port.out.external.CheckCourseOwnerPort;
import kira.progress.application.port.out.persistance.query.shared.TestAnswersViewQueryPort;
import kira.progress.application.port.out.persistance.query.statistics.StatisticsQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new ResourceAccessException(
                    ProgressExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, teacherId, courseId);
        }
        // В данный момент возвращаются только id записанных пользователей и время их записи. В будущем будет
        // возвращаться дополнительная информация о каждом записанном пользователе (Например ФИО, статистика по курсу и
        // тд)
        return statisticsQueryPort.findEnrolledUsersByCourseId(courseId);
    }

    @Override
    public List<StudentsLessonProgressResult> findWatchedLessonStudents(Id<User> teacherId, Id<Lesson> lessonId) {
        if (!checkCourseOwnerPort.isTeacherLessonOwner(teacherId, lessonId)) {
            throw new ResourceAccessException(
                    ProgressExceptionCode.LESSON_ACCESS_EXCEPTION, Lesson.class, teacherId, lessonId);
        }

        return statisticsQueryPort.findWatchedLessonStudentsByLessonId(lessonId);
    }

    @Override
    public List<CompletedTestStudentsResult> findStudentsTestResult(Id<User> teacherId, Id<Test> testId) {
        if (!checkCourseOwnerPort.isTeacherTestOwner(teacherId, testId)) {
            throw new ResourceAccessException(
                    ProgressExceptionCode.TEST_ACCESS_EXCEPTION, Test.class, teacherId, testId);
        }

        return statisticsQueryPort.findCompletedTestStudentsByTestId(testId);
    }

    @Override
    public TestAnswersView findStudentTestAnswers(Id<User> teacherId, Id<User> studentId, Id<Test> testId) {
        if (!checkCourseOwnerPort.isTeacherTestOwner(teacherId, testId)) {
            throw new ResourceAccessException(
                    ProgressExceptionCode.TEST_ACCESS_EXCEPTION, Test.class, teacherId, testId);
        }

        return testAnswersViewQueryPort
                .findTestAnswersViewByTestIdAndStudentId(studentId, testId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ProgressExceptionCode.TEST_ATTEMPT_NOT_FOUND_EXCEPTION, Test.class, testId));
    }
}
