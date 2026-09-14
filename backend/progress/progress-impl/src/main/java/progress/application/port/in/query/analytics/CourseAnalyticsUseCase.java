package progress.application.port.in.query.analytics;

import refactor.common.domain.Id;
import progress.application.port.in.query.TestAnswersView;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

import java.util.List;

public interface CourseAnalyticsUseCase {

    List<CompletedTestStudentsResult> findStudentsTestResult(Id<User> teacherId, Id<Test> testId);

    List<EnrolledStudentsResult> findEnrolledStudents(Id<User> teacherId, Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudents(Id<User> teacherId, Id<Lesson> lessonId);

    TestAnswersView findStudentTestAnswers(Id<User> teacherId, Id<User> studentId, Id<Test> testId);
}
