package kira.course.application.port.out.persistance.query;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.api.CourseAnswerKey;
import kira.course.domain.test.Test;

public interface TestAnswerKeyQueryPort {
    Optional<CourseAnswerKey> findAnswerKeyByTestId(Id<Test> testId);
}
