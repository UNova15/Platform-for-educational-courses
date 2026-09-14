package progress.application.port.out.persistance.query.personalprogress;

import refactor.common.domain.Id;
import refactor.progress.implementation.application.port.in.query.personalprogress.LessonProgressSummary;
import refactor.progress.implementation.application.port.in.query.personalprogress.TestProgressSummary;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

import java.util.List;
import java.util.Set;

public interface PersonalProgressQueryPort {
    List<LessonProgressSummary> findCompletedLessonsIn(Set<Id<Lesson>> lessonsIds, Id<User> userId);

    List<TestProgressSummary> findCompletedTestAttemptsIn(Set<Id<Test>> testsIds, Id<User> userId);
}
