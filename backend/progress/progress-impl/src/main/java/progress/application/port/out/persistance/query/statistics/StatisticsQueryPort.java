package progress.application.port.out.persistance.query.statistics;

import refactor.common.domain.Id;
import refactor.progress.implementation.application.port.in.query.analytics.CompletedTestStudentsResult;
import refactor.progress.implementation.application.port.in.query.analytics.EnrolledStudentsResult;
import refactor.progress.implementation.application.port.in.query.analytics.StudentsLessonProgressResult;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.Test;

import java.util.List;

public interface StatisticsQueryPort {

    List<EnrolledStudentsResult> findEnrolledUsersByCourseId(Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudentsByLessonId(Id<Lesson> lessonId);

    List<CompletedTestStudentsResult> findCompletedTestStudentsByTestId(Id<Test> testId);
}
