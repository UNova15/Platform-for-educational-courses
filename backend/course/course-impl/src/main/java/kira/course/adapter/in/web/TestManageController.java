package kira.course.adapter.in.web;

import common.domain.entity.Id;
import jakarta.validation.Valid;
import kira.course.application.port.in.test.create.TestCreateCommand;
import kira.course.application.port.in.test.create.TestCreateResponse;
import kira.course.application.port.in.test.create.TestCreateUseCase;
import kira.course.application.port.in.test.remove.TestRemoveUseCase;
import kira.course.application.port.in.test.status.PublishTestUseCase;
import kira.course.application.port.in.test.update.TestUpdateCommand;
import kira.course.application.port.in.test.update.TestUpdateUseCase;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class TestManageController {
    private final TestCreateUseCase createUseCase;
    private final TestUpdateUseCase updateUseCase;
    private final TestRemoveUseCase removeUseCase;
    private final PublishTestUseCase publishUseCase;

    @PostMapping("/modules/{moduleId}/tests")
    @ResponseStatus(HttpStatus.CREATED)
    public TestCreateResponse createTest(
            @Valid @RequestBody TestCreateCommand command,
            @PathVariable("moduleId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return createUseCase.createTest(command, userId, moduleId);
    }

    @DeleteMapping("/tests/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTest(@PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        removeUseCase.removeTest(userId, testId);
    }

    @PutMapping("/tests/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTest(
            @Valid @RequestBody TestUpdateCommand command,
            @PathVariable("testId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        updateUseCase.updateTest(command, testId, userId);
    }

    @PostMapping("tests/{testId}/publication")
    public void publishTest(@PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        publishUseCase.publish(userId, testId);
    }
}
