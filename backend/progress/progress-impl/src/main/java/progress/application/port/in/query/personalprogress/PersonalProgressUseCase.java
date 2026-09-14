package progress.application.port.in.query.personalprogress;

import refactor.common.domain.Id;
import progress.application.port.in.query.TestAnswersView;
import refactor.progress.implementation.domain.markers.CourseModule;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

public interface PersonalProgressUseCase {

    ProgressSummary findStudentsProgressInModule(Id<User> studentId, Id<CourseModule> moduleId);

    TestAnswersView findStudentsTestAttempt(Id<User> userId, Id<Test> testId);
}
