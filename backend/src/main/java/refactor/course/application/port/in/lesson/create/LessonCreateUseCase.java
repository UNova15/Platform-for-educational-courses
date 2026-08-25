package refactor.course.application.port.in.lesson.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.internal.module.CourseModule;
import refactor.course.domain.external.User;

@Validated
public interface LessonCreateUseCase {
    LessonCreateResult createLesson(
            @Valid LessonCreateCommand createCommand, @NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);
}
