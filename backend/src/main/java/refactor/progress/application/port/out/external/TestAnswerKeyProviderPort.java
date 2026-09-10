package refactor.progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.testprogress.valueobject.AnswerKey;

import java.util.Optional;

public interface TestAnswerKeyProviderPort {
    Optional<AnswerKey> findAnswerKeyByTestId(Id<Test> testId);
}
