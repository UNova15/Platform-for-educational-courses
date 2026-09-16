package kira.progress.application.port.in.query.analytics;

import common.domain.Id;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

import java.util.List;

public interface CourseAnalyticsUseCase {

    List<CompletedTestStudentsResult> findStudentsTestResult(Id<User> teacherId, Id<Test> testId);

    List<EnrolledStudentsResult> findEnrolledStudents(Id<User> teacherId, Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudents(Id<User> teacherId, Id<Lesson> lessonId);

    TestAnswersView findStudentTestAnswers(Id<User> teacherId, Id<User> studentId, Id<Test> testId);
}
