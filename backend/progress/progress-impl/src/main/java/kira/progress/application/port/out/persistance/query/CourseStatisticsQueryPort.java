package kira.progress.application.port.out.persistance.query;


import common.domain.Id;
import kira.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import kira.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import kira.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

import java.util.List;

public interface CourseStatisticsQueryPort {

    List<EnrolledStudentsResult> findEnrolledUsersByCourseId(Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudentsByLessonId(Id<Lesson> lessonId);

    List<CompletedTestStudentsResult> findCompletedTestStudentsByTestId(Id<Test> testId);

    List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId);
}
