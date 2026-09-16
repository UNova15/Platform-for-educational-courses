package kira.progress.application.port.out.persistance.query.shared;

import common.domain.Id;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

import java.util.Optional;

public interface TestAnswersViewQueryPort {

    Optional<TestAnswersView> findTestAnswersViewByTestIdAndStudentId(Id<User> userId, Id<Test> testId);
}
