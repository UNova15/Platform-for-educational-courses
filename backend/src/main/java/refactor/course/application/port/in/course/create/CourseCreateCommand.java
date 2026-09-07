package refactor.course.application.port.in.course.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

import refactor.common.exception.domain.OrderException;
import refactor.common.util.OrderValidator;
import refactor.course.domain.common.Description;
import refactor.course.domain.common.Title;
import refactor.course.domain.course.Course;
import refactor.course.domain.course.Tag;
import refactor.course.domain.lesson.Content;
import refactor.course.domain.lesson.ContentType;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.test.Question;
import refactor.course.domain.test.Test;
import refactor.course.domain.test.valueobject.Option;
import refactor.course.domain.test.valueobject.QuestionContent;

public record CourseCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,
        @Size(max = Description.MAX_LENGTH) String description,
        Tag tag,

        @NotEmpty @Size(max = Course.MAX_MODULES_COUNT) @Valid
        List<@NotNull ModuleCommand> modules) {

    public CourseCreateCommand {
        if (!OrderValidator.isValidSequence(modules, ModuleCommand::orderIndex)) {
            throw new OrderException("Invalid order in modules");
        }
    }

    public record ModuleCommand(
            @NotBlank @Size(max = Title.MAX_LENGTH) String title,
            @Size(max = Description.MAX_LENGTH) String description,
            @Positive int orderIndex,

            @NotEmpty @Size(max = CourseModule.MAX_LESSONS_COUNT) @Valid
            List<@NotNull LessonCommand> lessons,

            @NotNull @Size(max = CourseModule.MAX_TESTS_COUNT) @Valid
            List<@NotNull TestCommand> tests) {

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
            @NotBlank @Size(max = Title.MAX_LENGTH) String title,
            @NotNull ContentType type,

            @NotBlank @Size(max = Content.MAX_CONTENT_LENGTH)
            String content,

            @PositiveOrZero int orderIndex,
            boolean mandatory) {}

    public record TestCommand(
            @Positive int orderIndex,
            @NotBlank @Size(max = Title.MAX_LENGTH) String title,
            @Size(max = Description.MAX_LENGTH) String description,

            @NotEmpty @Size(min = Test.MIN_QUESTION_COUNT, max = Test.MAX_QUESTION_COUNT) @Valid
            List<@NotNull QuestionCommand> questions) {

        public TestCommand {
            if (!OrderValidator.isValidSequence(questions, QuestionCommand::orderIndex)) {
                throw new OrderException("Invalid questions order");
            }
        }
    }

    public record QuestionCommand(
            @NotBlank @Size(max = QuestionContent.MAX_QUESTION_LENGTH)
            String question,

            @Positive int orderIndex,

            @NotEmpty @Size(min = Question.MIN_OPTION_COUNT, max = Question.MAX_OPTION_COUNT) @Valid
            List<@NotNull OptionCommand> options) {}

    public record OptionCommand(
            @NotBlank @Size(max = Option.MAX_LENGTH) String option, boolean isCorrect) {}
}
