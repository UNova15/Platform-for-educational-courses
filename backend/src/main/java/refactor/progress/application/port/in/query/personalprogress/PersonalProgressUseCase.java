package refactor.progress.application.port.in.query.personalprogress;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.CourseModule;
import refactor.progress.domain.markers.User;

public interface PersonalProgressUseCase {

    ProgressSummary findStudentsProgressInModule(Id<User> studentId, Id<CourseModule> moduleId);
}
