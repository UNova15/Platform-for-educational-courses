package refactor.progress.application.port.out.persistance.query;

import refactor.common.domain.Id;
import refactor.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import refactor.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import refactor.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;

import java.util.List;

public interface StatisticsQueryPort {

    List<EnrolledStudentsResult> findEnrolledUsersByCourseId(Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudentsByLessonId(Id<Lesson> lessonId);

    List<CompletedTestStudentsResult> findCompletedTestStudentsByTestId(Id<Test> testId);
}
