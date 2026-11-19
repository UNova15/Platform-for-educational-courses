package kira.course.application.port.in.test.update;

import common.domain.entity.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestUpdateUseCase {

    void updateTest(@Valid TestUpdateCommand command, @NotNull Id<Test> testId, @NotNull Id<User> teacherId);
}
