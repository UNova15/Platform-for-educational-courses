package kira.course.application.port.out.persistance.query;

import common.domain.Id;
import kira.course.api.CourseAnswerKey;
import kira.course.domain.test.Test;

import java.util.Optional;

public interface TestAnswerKeyQueryPort {
    Optional<CourseAnswerKey> findAnswerKeyByTestId(Id<Test> testId);
}
