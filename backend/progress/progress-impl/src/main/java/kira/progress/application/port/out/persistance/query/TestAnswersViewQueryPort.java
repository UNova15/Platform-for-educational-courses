package kira.progress.application.port.out.persistance.query;

import common.domain.Id;
import java.util.Optional;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public interface TestAnswersViewQueryPort {

    Optional<TestAnswersView> findTestAnswersViewByTestIdAndStudentId(Id<User> userId, Id<Test> testId);
}
