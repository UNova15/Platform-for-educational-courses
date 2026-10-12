package kira.course.adapter.in.web;

import common.domain.Id;
import jakarta.validation.Valid;
import kira.course.application.port.in.course.create.CourseCreateCommand;
import kira.course.application.port.in.course.create.CourseCreateResponse;
import kira.course.application.port.in.course.create.CourseCreateUseCase;
import kira.course.application.port.in.course.remove.CourseRemoveUseCase;
import kira.course.application.port.in.course.status.ArchiveUseCase;
import kira.course.application.port.in.course.status.PublishCourseUseCase;
import kira.course.application.port.in.course.update.CourseUpdateCommand;
import kira.course.application.port.in.course.update.CourseUpdateUseCase;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
class CourseManageController {
    private final CourseCreateUseCase createUseCase;
    private final CourseRemoveUseCase removeUseCase;
    private final CourseUpdateUseCase updateUseCase;
    private final PublishCourseUseCase publishCourseUseCase;
    private final ArchiveUseCase archiveUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseCreateResponse createCourse(
            @Valid @RequestBody CourseCreateCommand command, @AuthenticationPrincipal TokenPayload token) {

        Id<User> userId = Id.of(token.userId());
        return createUseCase.createCourse(command, userId);
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCourse(@PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        removeUseCase.removeCourse(userId, courseId);
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourse(
            @Valid @RequestBody CourseUpdateCommand command,
            @PathVariable("courseId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        updateUseCase.updateCourse(command, courseId, userId);
    }

    @PostMapping("/{courseId}/status/publication")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void publishCourse(@PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        publishCourseUseCase.publish(userId, courseId);
    }

    @PostMapping("/{courseId}/status/archivation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archiveCourse(@PathVariable("courseId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Course> courseId = Id.of(resourceId);

        archiveUseCase.archive(userId, courseId);
    }
}
