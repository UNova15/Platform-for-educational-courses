package kira.course.application.port.in.test.update;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestUpdateUseCase {
    enum UpdateError {
        TEST_NOT_FOUND,
        ACCESS_DENY,
        COURSE_ARCHIVED
    }

    Result<Void, UpdateError> updateTest(
            @Valid TestUpdateCommand command, @NotNull Id<Test> testId, @NotNull Id<User> teacherId);
}
