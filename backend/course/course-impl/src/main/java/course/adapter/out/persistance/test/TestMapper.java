package course.adapter.out.persistance.test;

import org.springframework.stereotype.Component;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.common.Description;
import refactor.course.implementation.domain.common.Title;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.test.AnswerOption;
import refactor.course.implementation.domain.test.Question;
import refactor.course.implementation.domain.test.Test;
import refactor.course.implementation.domain.test.valueobject.Option;
import refactor.course.implementation.domain.test.valueobject.QuestionContent;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class TestMapper {

    public Test toDomain(TestEntity entity) {
        Id<Test> id = Id.of(entity.id());
        Id<CourseModule> moduleId = Id.of(entity.moduleId());

        var title = Title.of(entity.title());
        var description = Description.of(entity.description());

        Set<Question> questions =
                entity.questions().stream().map(this::toDomainQuestion).collect(Collectors.toSet());

        return Test.restore(id, moduleId, title, description, entity.orderIndex(), questions);
    }

    private Question toDomainQuestion(QuestionEntity entity) {
        Set<AnswerOption> options =
                entity.answerOptions().stream().map(this::toDomainOption).collect(Collectors.toSet());
        return Question.restore(
                Id.of(entity.id()), QuestionContent.of(entity.question()), entity.orderIndex(), options);
    }

    private AnswerOption toDomainOption(AnswerOptionEntity entity) {
        return AnswerOption.restore(Id.of(entity.id()), Option.of(entity.option()), entity.isCorrect());
    }

    public List<Test> toDomain(Iterable<TestEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toDomain)
                .toList();
    }

    public TestEntity toEntity(Test test) {
        Set<QuestionEntity> questions =
                test.questions().stream().map(this::toQuestionEntity).collect(Collectors.toSet());

        return new TestEntity(
                test.id().value(),
                test.moduleId().value(),
                test.title().value(),
                test.description().value(),
                test.orderIndex(),
                questions);
    }

    private QuestionEntity toQuestionEntity(Question question) {
        Set<AnswerOptionEntity> options = question.answerOptions().stream()
                .map(this::toAnswerOptionEntity)
                .collect(Collectors.toSet());

        return new QuestionEntity(question.id().value(), question.content().value(), question.orderIndex(), options);
    }

    private AnswerOptionEntity toAnswerOptionEntity(AnswerOption option) {
        return new AnswerOptionEntity(option.id().value(), option.option().value(), option.isCorrect());
    }

    public List<TestEntity> toEntity(List<Test> tests) {
        return tests.stream().map(this::toEntity).toList();
    }
}
