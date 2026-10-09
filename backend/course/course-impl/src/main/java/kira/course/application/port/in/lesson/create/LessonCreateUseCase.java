package kira.course.application.port.in.lesson.create;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonCreateUseCase {
    enum CreateErrors {
        MODULE_NOT_FOUND,
        ACCESS_DENY,
        COURSE_ARCHIVED
    }

    Result<LessonCreateResponse, CreateErrors> createLesson(
            @Valid LessonCreateCommand createCommand, @NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);
}
