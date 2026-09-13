package refactor.progress.application.port.out.persistance.query.personalprogress;

import refactor.common.domain.Id;
import refactor.progress.application.port.in.query.personalprogress.LessonProgressSummary;
import refactor.progress.application.port.in.query.personalprogress.TestProgressSummary;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

import java.util.List;
import java.util.Set;

public interface PersonalProgressQueryPort {
    List<LessonProgressSummary> findCompletedLessonsIn(Set<Id<Lesson>> lessonsIds, Id<User> userId);

    List<TestProgressSummary> findCompletedTestAttemptsIn(Set<Id<Test>> testsIds, Id<User> userId);
}
