package kira.progress.application.port.out.external;


import common.domain.Id;
import kira.progress.domain.markers.Test;
import kira.progress.domain.testprogress.valueobject.AnswerKey;

import java.util.Optional;

public interface TestAnswerKeyProviderPort {
    Optional<AnswerKey> findAnswerKeyByTestId(Id<Test> testId);
}
