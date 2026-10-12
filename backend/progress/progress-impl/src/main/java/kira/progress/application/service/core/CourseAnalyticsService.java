package kira.progress.application.service.core;

import common.domain.Id;
import java.util.List;

import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import kira.progress.application.port.in.query.analytics.CourseAnalyticsUseCase;
import kira.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import kira.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.application.port.out.external.CheckCourseOwnerPort;
import kira.progress.application.port.out.persistance.query.CourseStatisticsQueryPort;
import kira.progress.application.port.out.persistance.query.TestAnswersViewQueryPort;
import kira.progress.application.exception.ProgressResourceName;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CourseAnalyticsService implements CourseAnalyticsUseCase {
    private final CheckCourseOwnerPort checkCourseOwnerPort;
    private final TestAnswersViewQueryPort testAnswersViewQueryPort;
    private final CourseStatisticsQueryPort statisticsQueryPort;

    @Override
    public List<EnrolledStudentsResult> findEnrolledStudents(Id<User> teacherId, Id<Course> courseId) {
        if (!checkCourseOwnerPort.isCourseOwner(teacherId, courseId)) {
            throw new ResourceAccessDeniedException(ProgressResourceName.COURSE, teacherId, courseId);
        }
        // В данный момент возвращаются только id записанных пользователей и время их записи. В будущем будет
        // возвращаться дополнительная информация о каждом записанном пользователе (Например ФИО, статистика по курсу и
        // тд)
        return statisticsQueryPort.findEnrolledUsersByCourseId(courseId);
    }

    @Override
    public List<StudentsLessonProgressResult> findWatchedLessonStudents(Id<User> teacherId, Id<Lesson> lessonId) {
        if (!checkCourseOwnerPort.isLessonOwner(teacherId, lessonId)) {
            throw new ResourceAccessDeniedException(ProgressResourceName.LESSON, teacherId, lessonId);
        }

        return statisticsQueryPort.findWatchedLessonStudentsByLessonId(lessonId);
    }

    @Override
    public List<CompletedTestStudentsResult> findStudentsTestResult(Id<User> teacherId, Id<Test> testId) {
        if (!checkCourseOwnerPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessDeniedException(ProgressResourceName.TEST, teacherId, testId);
        }

        return statisticsQueryPort.findCompletedTestStudentsByTestId(testId);
    }

    @Override
    public TestAnswersView findStudentTestAnswers(Id<User> teacherId, Id<User> studentId, Id<Test> testId) {
        if (!checkCourseOwnerPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessDeniedException(ProgressResourceName.TEST, teacherId, testId);
        }

        return testAnswersViewQueryPort
                .findTestAnswersViewByTestIdAndStudentId(studentId, testId)
                .orElseThrow(() -> new ResourceNotFoundException(ProgressResourceName.TEST, testId));
    }
}
