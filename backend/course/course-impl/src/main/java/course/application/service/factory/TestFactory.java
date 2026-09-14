package course.application.service.factory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import refactor.common.domain.Id;
import refactor.course.implementation.application.port.in.course.create.CourseCreateCommand;
import refactor.course.implementation.application.port.in.test.create.TestCreateCommand;
import refactor.course.implementation.application.port.in.test.update.TestUpdateCommand;
import refactor.course.implementation.domain.common.Description;
import refactor.course.implementation.domain.common.Title;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.test.AnswerOption;
import refactor.course.implementation.domain.test.Test;
import refactor.course.implementation.domain.test.valueobject.Option;
import refactor.course.implementation.domain.test.Question;
import refactor.course.implementation.domain.test.valueobject.QuestionContent;
import org.springframework.stereotype.Component;

@Component
public class TestFactory {

    public Test fromCourseCreateCommand(CourseCreateCommand.TestCommand test, Id<CourseModule> moduleId) {
        Set<Question> questions =
                test.questions().stream().map(this::fromQuestionCreateCommand).collect(Collectors.toSet());

        var description = Description.of(test.description());
        var title = Title.of(test.title());

        return Test.createNew(moduleId, title, description, questions, test.orderIndex());
    }

    public List<Test> fromCourseCreateCommand(List<CourseCreateCommand.TestCommand> tests, Id<CourseModule> moduleId) {
        return tests.stream()
                .map(test -> fromCourseCreateCommand(test, moduleId))
                .toList();
    }

    private Question fromQuestionCreateCommand(CourseCreateCommand.QuestionCommand question) {
        Set<AnswerOption> options = question.options().stream()
                .map(option -> AnswerOption.createNew(Option.of(option.option()), option.isCorrect()))
                .collect(Collectors.toSet());

        return Question.createNew(QuestionContent.of(question.question()), options, question.orderIndex());
    }

    public Test fromTestCreateCommand(TestCreateCommand command, Id<CourseModule> moduleId) {
        Set<Question> questions = command.questions().stream()
                .map(this::fromQuestionCreateCommand)
                .collect(Collectors.toSet());

        var title = Title.of(command.title());
        var description = Description.of(command.description());

        return Test.createNew(moduleId, title, description, questions, command.orderIndex());
    }

    private Question fromQuestionCreateCommand(TestCreateCommand.QuestionCommand command) {
        Set<AnswerOption> options = command.options().stream()
                .map(option -> AnswerOption.createNew(Option.of(option.option()), option.isCorrect()))
                .collect(Collectors.toSet());

        var title = QuestionContent.of(command.question());
        return Question.createNew(title, options, command.orderIndex());
    }

    public Set<Question> fromQuestionUpdateCommand(List<TestUpdateCommand.QuestionCommand> commands) {
        return commands.stream()
                .map(command -> Question.createNew(
                        QuestionContent.of(command.question()),
                        fromAnswerOptionUpdateCommand(command.options()),
                        command.orderIndex()))
                .collect(Collectors.toSet());
    }

    private Set<AnswerOption> fromAnswerOptionUpdateCommand(List<TestUpdateCommand.OptionCommand> commands) {
        return commands.stream()
                .map(commandOption ->
                        AnswerOption.createNew(Option.of(commandOption.option()), commandOption.isCorrect()))
                .collect(Collectors.toSet());
    }
}
