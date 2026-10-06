package kira.course.application.port.in.lesson.create;

import common.domain.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonCreateUseCase {
    LessonCreateResult createLesson(
            @Valid LessonCreateCommand createCommand, @NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);
}
