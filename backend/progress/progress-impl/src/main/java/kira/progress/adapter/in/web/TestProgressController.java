package kira.progress.adapter.in.web;

import common.domain.Id;
import kira.infrastructure.api.TokenPayload;
import kira.progress.application.port.in.testprogress.EndTestAttemptCommand;
import kira.progress.application.port.in.testprogress.EndTestAttemptUseCase;
import kira.progress.application.port.in.testprogress.StartTestAttemptUseCase;
import kira.progress.application.port.in.testprogress.TestResult;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class TestProgressController {
    private final StartTestAttemptUseCase startTestAttemptUseCase;
    private final EndTestAttemptUseCase endTestAttemptUseCase;

    @PostMapping("/tests/{testId}/attempts")
    @ResponseStatus(HttpStatus.CREATED)
    public void startTest(@PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Test> testId = Id.of(resourceId);

        startTestAttemptUseCase.startTestAttempt(userId, testId);
    }

    @PostMapping("/tests/{testId}/attempts/submit")
    @ResponseStatus(HttpStatus.OK)
    public TestResult endTest(
            @RequestBody Set<EndTestRequest> endTestRequests,
            @PathVariable("testId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {

        EndTestAttemptCommand command =
                CommandFactory.toEndTestAttemptCommand(endTestRequests, resourceId, token.userId());
        return endTestAttemptUseCase.endTestAttempt(command);
    }
}
