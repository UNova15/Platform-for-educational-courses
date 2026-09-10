package refactor.progress.application.port.in.query.analytics;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

import java.util.List;

public interface CourseAnalyticsUseCase {

    List<CompletedTestStudentsResult> findCompletedTestStudents(Id<User> userId, Id<Test> testId);

    List<EnrolledStudentsResult> findEnrolledStudents(Id<User> teacherId, Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudents(Id<User> userId, Id<Lesson> lessonId);
}
