package refactor.progress.application.port.in.query.analytics;

import refactor.common.domain.Id;
import refactor.progress.application.port.in.query.TestAnswersView;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

import java.util.List;

public interface CourseAnalyticsUseCase {

    List<CompletedTestStudentsResult> findStudentsTestResult(Id<User> teacherId, Id<Test> testId);

    List<EnrolledStudentsResult> findEnrolledStudents(Id<User> teacherId, Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudents(Id<User> teacherId, Id<Lesson> lessonId);

    TestAnswersView findStudentTestAnswers(Id<User> teacherId, Id<User> studentId, Id<Test> testId);
}
