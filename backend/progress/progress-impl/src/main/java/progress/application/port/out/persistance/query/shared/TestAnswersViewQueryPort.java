package progress.application.port.out.persistance.query.shared;

import refactor.common.domain.Id;
import refactor.progress.implementation.application.port.in.query.TestAnswersView;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

import java.util.Optional;

public interface TestAnswersViewQueryPort {

    Optional<TestAnswersView> findTestAnswersViewByTestIdAndStudentId(Id<User> userId, Id<Test> testId);
}
