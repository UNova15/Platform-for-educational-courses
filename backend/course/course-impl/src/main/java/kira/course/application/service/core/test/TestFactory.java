package kira.course.application.service.core.test;

import common.domain.Id;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kira.course.application.port.in.test.create.TestCreateCommand;
import kira.course.application.port.in.test.update.TestUpdateCommand;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.AnswerOption;
import kira.course.domain.test.Question;
import kira.course.domain.test.Test;
import kira.course.domain.test.valueobject.Option;
import kira.course.domain.test.valueobject.QuestionContent;
import org.springframework.stereotype.Component;

@Component
class TestFactory {
    public Test fromTestCreateCommand(TestCreateCommand command, Id<CourseModule> moduleId) {
        Set<Question> questions = command.questions().stream()
                .map(this::fromQuestionCreateCommand)
                .collect(Collectors.toSet());

        var title = Title.of(command.title());
        var description = Description.of(command.description());

        return Test.create(moduleId, title, description, questions, command.orderIndex());
    }

    private Question fromQuestionCreateCommand(TestCreateCommand.QuestionCommand command) {
        Set<AnswerOption> options = command.options().stream()
                .map(option -> AnswerOption.create(Option.of(option.option()), option.isCorrect()))
                .collect(Collectors.toSet());

        var title = QuestionContent.of(command.question());
        return Question.create(title, options, command.orderIndex());
    }

    public Set<Question> fromQuestionUpdateCommand(List<TestUpdateCommand.QuestionCommand> commands) {
        return commands.stream()
                .map(command -> Question.create(
                        QuestionContent.of(command.question()),
                        fromAnswerOptionUpdateCommand(command.options()),
                        command.orderIndex()))
                .collect(Collectors.toSet());
    }

    private Set<AnswerOption> fromAnswerOptionUpdateCommand(List<TestUpdateCommand.OptionCommand> commands) {
        return commands.stream()
                .map(commandOption ->
                        AnswerOption.create(Option.of(commandOption.option()), commandOption.isCorrect()))
                .collect(Collectors.toSet());
    }
}
