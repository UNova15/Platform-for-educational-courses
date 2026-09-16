package kira.progress.application.port.in.query.personalprogress;

import common.domain.Id;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.domain.markers.CourseModule;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public interface PersonalProgressUseCase {

    ProgressSummary findStudentsProgressInModule(Id<User> studentId, Id<CourseModule> moduleId);

    TestAnswersView findStudentsTestAttempt(Id<User> userId, Id<Test> testId);
}
