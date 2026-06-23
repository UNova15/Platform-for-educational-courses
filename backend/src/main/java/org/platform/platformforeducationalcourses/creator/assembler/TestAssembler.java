package org.platform.platformforeducationalcourses.creator.assembler;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.domain.progress.TestAnswer;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.dto.test.StudentTestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.QuestionOption;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestQuestion;
import org.platform.platformforeducationalcourses.mapper.AnswerQuestionMapper;
import org.platform.platformforeducationalcourses.mapper.QuestionMapper;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TestAssembler {
    private final TestMapper testMapper;
    private final QuestionMapper questionMapper;
    private final AnswerQuestionMapper answerQuestionMapper;

    public List<StudentTestFindResponse> createStudentTestFindResponse(List<Test> tests, List<TestSubmission> testSubmissions) {
        Map<Long, TestSubmission> submissionOrderByTestId = testSubmissions.stream()
                .collect(Collectors.toMap(TestSubmission::getTestId, submission -> submission));

        List<StudentTestFindResponse> mappedTests = new ArrayList<>(tests.size());
        for (var test : tests) {
            long testId = test.getId();
            TestSubmission submission = submissionOrderByTestId.get(testId);

            StudentTestFindResponse testFindResponse;

            if (submission == null) {
                testFindResponse = testMapper.toStudentTestFindResponse(test, null, null, null);
            } else {
                testFindResponse = testMapper.toStudentTestFindResponse(test, submission.getCompletedAt(),
                        submission.getStartedAt(), submission.getScore());
            }
            mappedTests.add(testFindResponse);
        }
        return mappedTests;
    }

    public TestReview createTestAttempt(Test test, TestSubmission testSubmission) {
        Map<Long, TestAnswer> answerOrderByQuestionId = testSubmission.getAnswers().stream()
                .collect(Collectors.toMap(TestAnswer::getQuestionId, answer -> answer));

        List<TestQuestion> testQuestions = new ArrayList<>(test.getQuestions().size());
        for (var question : test.getQuestions()) {
            long selectedId = answerOrderByQuestionId.get(question.getId()).getAnswerId();

            List<QuestionOption> options = answerQuestionMapper.toQuestionOption(question.getAnswerOptions());
            TestQuestion testQuestion = questionMapper.toTestQuestion(question, options, selectedId);
            testQuestions.add(testQuestion);
        }
        return testMapper.toTestAttempt(test, testQuestions);
    }
}
