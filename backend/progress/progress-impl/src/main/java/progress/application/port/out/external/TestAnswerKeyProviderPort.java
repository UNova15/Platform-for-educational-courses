package progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.testprogress.valueobject.AnswerKey;

import java.util.Optional;

public interface TestAnswerKeyProviderPort {
    Optional<AnswerKey> findAnswerKeyByTestId(Id<Test> testId);
}
