package org.platform.platformforeducationalcourses.creator.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import refactor.course.domain.test.Test;
import refactor.progress.domain.testprogress.TestAnswer;
import refactor.progress.domain.testprogress.TestAttempt;
import org.platform.platformforeducationalcourses.dto.test.StudentTestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.QuestionOption;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestQuestion;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;
import org.platform.platformforeducationalcourses.mapper.AnswerQuestionMapper;
import org.platform.platformforeducationalcourses.mapper.QuestionMapper;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TestAssembler {
    private final TestMapper testMapper;
    private final QuestionMapper questionMapper;
    private final AnswerQuestionMapper answerQuestionMapper;

    public List<StudentTestFindResponse> createStudentTestFindResponse(
            List<Test> tests, List<TestAttempt> testAttempts) {
        Map<Long, TestAttempt> submissionOrderByTestId =
                testAttempts.stream().collect(Collectors.toMap(TestAttempt::getTestId, submission -> submission));

        List<StudentTestFindResponse> mappedTests = new ArrayList<>(tests.size());
        for (var test : tests) {
            long testId = test.getId();
            TestAttempt submission = submissionOrderByTestId.get(testId);

            StudentTestFindResponse testFindResponse;

            if (submission == null) {
                testFindResponse = testMapper.toStudentTestFindResponse(test, null, null, null);
            } else {
                testFindResponse = testMapper.toStudentTestFindResponse(
                        test, submission.getCompletedAt(), submission.getStartedAt(), submission.getScore());
            }
            mappedTests.add(testFindResponse);
        }
        return mappedTests;
    }

    public TestReview createTestAttempt(Test test, TestAttempt testAttempt) {
        Map<Long, TestAnswer> answerOrderByQuestionId = testAttempt.answers().stream()
                .collect(Collectors.toMap(TestAnswer::getQuestionId, answer -> answer));

        List<TestQuestion> testQuestions = new ArrayList<>(test.questions().size());
        for (var question : test.questions()) {
            List<Long> selectedIds =
                    answerOrderByQuestionId.get(question.getId()).getAnswerIds();

            List<QuestionOption> options = answerQuestionMapper.toQuestionOption(question.answerOptions());
            TestQuestion testQuestion = questionMapper.toTestQuestion(question, options, selectedIds);
            testQuestions.add(testQuestion);
        }
        return testMapper.toTestAttempt(test, testQuestions);
    }
}
