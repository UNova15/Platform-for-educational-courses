package kira.progress.adapter.in.web;

import common.domain.Id;
import kira.infrastructure.api.TokenPayload;
import kira.progress.application.port.in.query.personalprogress.PersonalProgressUseCase;
import kira.progress.application.port.in.query.personalprogress.ProgressSummary;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.domain.markers.CourseModule;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class PersonalProgressController {
    private final PersonalProgressUseCase personalProgressUseCase;

    @GetMapping("/modules/{moduleId}/progress")
    @ResponseStatus(HttpStatus.OK)
    public ProgressSummary findStudentsProgressInModule(
            @PathVariable("moduleId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> studentId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return personalProgressUseCase.findStudentsProgressInModule(studentId, moduleId);
    }

    @GetMapping("/tests/{testId}/attempts")
    @ResponseStatus(HttpStatus.OK)
    public TestAnswersView findStudentsTestAttempt(
            @PathVariable("testId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Test> testId = Id.of(resourceId);
        Id<User> studentId = Id.of(token.userId());

        return personalProgressUseCase.findStudentsTestAttempt(studentId, testId);
    }
}
