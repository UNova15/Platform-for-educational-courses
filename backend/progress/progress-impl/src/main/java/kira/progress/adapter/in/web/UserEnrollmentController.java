package kira.progress.adapter.in.web;

import common.domain.Id;
import kira.infrastructure.api.TokenPayload;
import kira.progress.application.port.in.enrollment.UserEnrollmentUseCase;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserEnrollmentController {
    private final UserEnrollmentUseCase userEnrollmentUseCase;

    @PostMapping("/courses/{courseId}/enrollments")
    @ResponseStatus(HttpStatus.CREATED)
    public void enrollToCourse(@PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Course> courseId = Id.of(resourceId);
        Id<User> userId = Id.of(token.userId());

        userEnrollmentUseCase.enrollToCourse(userId, courseId);
    }
}
