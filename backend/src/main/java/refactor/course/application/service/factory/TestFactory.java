package refactor.course.application.service.factory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import refactor.course.application.port.in.test.command.create.AnswerOptionUpdateCommand;
import refactor.course.application.port.in.test.command.create.QuestionCreateCommand;
import refactor.course.application.port.in.test.command.create.QuestionUpdateCommand;
import refactor.course.application.port.in.test.create.TestCreateCommand;
import refactor.course.domain.option.AnswerOption;
import refactor.course.domain.option.Option;
import refactor.course.domain.question.Question;
import refactor.course.domain.question.QuestionTitle;
import refactor.course.domain.test.Test;
import refactor.course.application.port.in.course.command.create.CourseBulkQuestionCommand;
import refactor.course.application.port.in.course.command.create.CourseBulkTestCommand;
import org.springframework.stereotype.Component;
import refactor.course.domain.test.TestDescription;
import refactor.course.domain.test.TestTitle;

@Component
public class TestFactory {

    public Test fromBulkTestCreateCommand(CourseBulkTestCommand test, long moduleId) {
        Set<Question> questions = test.questions().stream()
                .map(this::fromBulkQuestionCreateCommand)
                .collect(Collectors.toSet());

        var description = TestDescription.of(test.description());
        var title = TestTitle.of(test.title());

        return Test.createNew(moduleId, title, description, questions, test.orderIndex());
    }

    public List<Test> fromBulkTestCreateCommand(List<CourseBulkTestCommand> tests, long moduleId) {
        return tests.stream()
                .map(test -> fromBulkTestCreateCommand(test, moduleId))
                .toList();
    }

    private Question fromBulkQuestionCreateCommand(CourseBulkQuestionCommand question) {
        Set<AnswerOption> options = question.options().stream()
                .map(option -> AnswerOption.createNew(Option.of(option.option()), option.isCorrect()))
                .collect(Collectors.toSet());

        return Question.createNew(QuestionTitle.of(question.question()), options, question.orderIndex());
    }

    public Test fromTestCreateCommand(TestCreateCommand command) {
        Set<Question> questions = command.questions().stream()
                .map(this::fromQuestionCreateCommand)
                .collect(Collectors.toSet());

        var title = TestTitle.of(command.title());
        var description = TestDescription.of(command.description());

        return Test.createNew(command.moduleId(), title, description, questions, command.orderIndex());
    }

    private Question fromQuestionCreateCommand(QuestionCreateCommand command) {
        Set<AnswerOption> options = command.options().stream()
                .map(option -> AnswerOption.createNew(Option.of(option.option()), option.isCorrect()))
                .collect(Collectors.toSet());

        var title = QuestionTitle.of(command.question());
        return Question.createNew(title, options, command.orderIndex());
    }

    public Set<Question> fromQuestionUpdateCommand(List<QuestionUpdateCommand> commands) {
        return commands.stream()
                .map(command -> Question.createNew(
                        QuestionTitle.of(command.question()),
                        fromAnswerOptionUpdateCommand(command.options()),
                        command.orderIndex()))
                .collect(Collectors.toSet());
    }

    private Set<AnswerOption> fromAnswerOptionUpdateCommand(List<AnswerOptionUpdateCommand> commands) {
        return commands.stream()
                .map(commandOption ->
                        AnswerOption.createNew(Option.of(commandOption.option()), commandOption.isCorrect()))
                .collect(Collectors.toSet());
    }
}
