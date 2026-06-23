package org.platform.platformforeducationalcourses.creator.factory;


import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.AnswerOption;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.dto.test.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Фабрика для создания доменных объектов Test из dto
 */

@Component
@AllArgsConstructor
public class TestFactory {

    public Test createTestFromCreateDto(TestCreateDto test, long moduleId) {
        Set<Question> questions = test.questions().stream()
                .map(this::createQuestionFromCreateDto)
                .collect(Collectors.toSet());
        return Test.createNew(moduleId, test.description(), test.orderIndex(), questions);
    }

    public Set<Question> createQuestionsFromUpdateDto(List<QuestionUpdateDto> rawQuestions, long testId) {
        return rawQuestions.stream()
                .map(question -> {
                    Set<AnswerOption> questions = question.options().stream()
                            .map(this::createAnswerOptionsFromUpdateDto)
                            .collect(Collectors.toSet());
                    return Question.createNew(question.question(), questions, question.orderIndex());
                })
                .collect(Collectors.toSet());
    }

    public List<Test> createTestsFromListCreateDto(List<TestCreateDto> tests, long moduleId) {
        return tests.stream()
                .map(test -> createTestFromCreateDto(test, moduleId))
                .toList();
    }

    private Question createQuestionFromCreateDto(TestQuestionCreateDto question) {
        Set<AnswerOption> options = question.options().stream()
                .map(option -> AnswerOption.createNew(
                        option.option(),
                        option.isCorrect()
                ))
                .collect(Collectors.toSet());
        return Question.createNew(question.question(), options, question.orderIndex());
    }

    private AnswerOption createAnswerOptionsFromUpdateDto(AnswerOptionUpdateDto answerOption) {
        return AnswerOption.createNew(answerOption.option(), answerOption.isCorrect());
    }
}
