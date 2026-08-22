package refactor.course.application.port.in.course.command.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

import refactor.common.exception.domain.OrderException;
import refactor.common.util.OrderValidator;
import refactor.course.domain.course.Tag;
import refactor.course.domain.lesson.ContentType;

import static refactor.course.domain.course.Course.MAX_MODULES_COUNT;
import static refactor.course.domain.course.CourseDescription.MAX_COURSE_DESCRIPTION_SIZE;
import static refactor.course.domain.course.CourseTitle.MAX_COURSE_TITLE_SIZE;
import static refactor.course.domain.lesson.Content.MAX_CONTENT_LENGTH;
import static refactor.course.domain.lesson.LessonTitle.MAX_LESSON_TITLE_SIZE;
import static refactor.course.domain.module.CourseModule.MAX_LESSONS_COUNT;
import static refactor.course.domain.module.CourseModule.MAX_TESTS_COUNT;
import static refactor.course.domain.module.ModuleDescription.MAX_MODULE_DESCRIPTION_SIZE;
import static refactor.course.domain.module.ModuleTitle.MAX_MODULE_TITLE_SIZE;
import static refactor.course.domain.option.Option.MAX_OPTION_LENGTH;
import static refactor.course.domain.question.Question.MAX_OPTION_COUNT;
import static refactor.course.domain.question.Question.MIN_OPTION_COUNT;
import static refactor.course.domain.question.QuestionTitle.MAX_QUESTION_LENGTH;
import static refactor.course.domain.test.Test.MAX_QUESTION_COUNT;

public record CourseCreateCommand(
        @NotBlank @Size(max = MAX_COURSE_TITLE_SIZE) String title,
        @Size(max = MAX_COURSE_DESCRIPTION_SIZE) String description,
        Tag tag,
        @NotEmpty @Size(max = MAX_MODULES_COUNT) @Valid List<@NotNull ModuleCommand> modules) {

    public CourseCreateCommand {
        if (!OrderValidator.isValidSequence(modules, ModuleCommand::orderIndex)) {
            throw new OrderException("Invalid order in modules");
        }
    }

    public record ModuleCommand(
            @NotBlank @Size(max = MAX_MODULE_TITLE_SIZE) String title,
            @Size(max = MAX_MODULE_DESCRIPTION_SIZE) String description,
            @Positive int orderIndex,
            @NotEmpty @Size(max = MAX_LESSONS_COUNT) @Valid List<@NotNull LessonCommand> lessons,
            @NotNull @Size(max = MAX_TESTS_COUNT) @Valid List<@NotNull TestCommand> tests) {

        public ModuleCommand {
            if (!OrderValidator.isValidSequence(lessons, LessonCommand::orderIndex)) {
                throw new OrderException("Invalid order in lessons");
            }
            if (!OrderValidator.isValidSequence(tests, TestCommand::orderIndex)) {
                throw new OrderException("Invalid order in test");
            }
        }
    }

    public record LessonCommand(
            @NotBlank @Size(max = MAX_LESSON_TITLE_SIZE) String title,
            @NotNull ContentType type,
            @NotBlank @Size(max = MAX_CONTENT_LENGTH) String content,
            @PositiveOrZero int orderIndex,
            boolean mandatory) {}

    public record TestCommand(
            @Positive int orderIndex,
            @NotBlank String title,
            String description,
            @NotEmpty @Size(max = MAX_QUESTION_COUNT) @Valid List<@NotNull QuestionCommand> questions) {
        public TestCommand {
            if (!OrderValidator.isValidSequence(questions, QuestionCommand::orderIndex)) {
                throw new OrderException("Invalid questions order");
            }
        }
    }

    public record QuestionCommand(
            @NotBlank @Size(max = MAX_QUESTION_LENGTH) String question,
            @Positive int orderIndex,

            @NotEmpty @Size(min = MIN_OPTION_COUNT, max = MAX_OPTION_COUNT) @Valid
            List<@NotNull OptionCommand> options) {}

    public record OptionCommand(
            @NotBlank @Size(max = MAX_OPTION_LENGTH) String option, boolean isCorrect) {}
}
