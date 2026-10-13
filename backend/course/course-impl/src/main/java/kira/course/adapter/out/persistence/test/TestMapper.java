package kira.course.adapter.out.persistence.test;

import common.domain.entity.Id;
import java.util.Set;
import java.util.stream.Collectors;
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
class TestMapper {

    public Test toDomain(TestEntity entity) {
        Id<Test> id = Id.of(entity.id());
        Id<CourseModule> moduleId = Id.of(entity.moduleId());

        var title = Title.of(entity.title());
        var description = Description.of(entity.description());

        Set<Question> questions =
                entity.questions().stream().map(this::toDomainQuestion).collect(Collectors.toSet());

        return Test.restore(id, moduleId, title, description, entity.orderIndex(), questions, entity.status());
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

    public TestEntity toEntity(Test test) {
        Long id = test.id() == null ? null : test.id().value();
        Set<QuestionEntity> questions =
                test.questions().stream().map(this::toQuestionEntity).collect(Collectors.toSet());

        return new TestEntity(
                id,
                test.moduleId().value(),
                test.title().value(),
                test.description().value(),
                test.orderIndex(),
                test.status(),
                questions);
    }

    private QuestionEntity toQuestionEntity(Question question) {
        Long id = question.id() == null ? null : question.id().value();
        Set<AnswerOptionEntity> options = question.answerOptions().stream()
                .map(this::toAnswerOptionEntity)
                .collect(Collectors.toSet());

        return new QuestionEntity(id, question.content().value(), question.orderIndex(), options);
    }

    private AnswerOptionEntity toAnswerOptionEntity(AnswerOption option) {
        Long id = option.id() == null ? null : option.id().value();
        return new AnswerOptionEntity(id, option.option().value(), option.isCorrect());
    }
}
