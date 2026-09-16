package kira.course.application.port.in.test.remove;

import common.domain.Id;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestRemoveUseCase {
    void removeTest(@NotNull Id<User> requesterId, @NotNull Id<Test> testId);
}
