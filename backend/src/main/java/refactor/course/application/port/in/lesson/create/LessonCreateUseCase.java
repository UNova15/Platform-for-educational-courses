package refactor.course.application.port.in.lesson.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.user.Account;

@Validated
public interface LessonCreateUseCase {
    LessonCreateResult createLesson(
            @Valid LessonCreateCommand createCommand, @NotNull Id<Account> teacherId, @NotNull Id<CourseModule> moduleId);
}
