package kira.progress.application.port.out.persistance.query.statistics;


import common.domain.Id;
import kira.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import kira.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import kira.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;

import java.util.List;

public interface StatisticsQueryPort {

    List<EnrolledStudentsResult> findEnrolledUsersByCourseId(Id<Course> courseId);

    List<StudentsLessonProgressResult> findWatchedLessonStudentsByLessonId(Id<Lesson> lessonId);

    List<CompletedTestStudentsResult> findCompletedTestStudentsByTestId(Id<Test> testId);
}
