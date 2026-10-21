package kira.course.application.port.out.persistance.test;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;

public interface TestCheckStatusPort {
    Optional<CourseContentStatus> findTestStatusById(Id<Test> testId);
}
