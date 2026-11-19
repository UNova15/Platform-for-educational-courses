package kira.progress.application.port.out.external;

import common.domain.entity.Id;
import java.util.Optional;
import kira.progress.domain.markers.Test;
import kira.progress.domain.testprogress.valueobject.AnswerKey;

public interface TestAnswerKeyProviderPort {
    Optional<AnswerKey> findAnswerKeyByTestId(Id<Test> testId);
}
