package kira.course.application.port.in.test.remove;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestRemoveUseCase {
    enum RemoveError{
        TEST_NOT_FOUND,
        ACCESS_DENY,
        COURSE_ARCHIVED
    }

    Result<Void,RemoveError> removeTest(@NotNull Id<User> requesterId, @NotNull Id<Test> testId);
}
