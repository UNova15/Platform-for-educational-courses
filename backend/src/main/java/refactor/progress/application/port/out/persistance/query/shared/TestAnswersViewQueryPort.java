package refactor.progress.application.port.out.persistance.query.shared;

import refactor.common.domain.Id;
import refactor.progress.application.port.in.query.TestAnswersView;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

import java.util.Optional;

public interface TestAnswersViewQueryPort {

    Optional<TestAnswersView> findTestAnswersViewByTestIdAndStudentId(Id<User> userId, Id<Test> testId);
}
