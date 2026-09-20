package kira.progress.application.port.out.persistance.query;

import common.domain.Id;
import kira.progress.application.port.in.query.personalprogress.LessonProgressSummary;
import kira.progress.application.port.in.query.personalprogress.TestProgressSummary;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

import java.util.List;
import java.util.Set;

public interface PersonalProgressQueryPort {
    List<LessonProgressSummary> findCompletedLessonsIn(Set<Id<Lesson>> lessonsIds, Id<User> userId);

    List<TestProgressSummary> findCompletedTestAttemptsIn(Set<Id<Test>> testsIds, Id<User> userId);
}
