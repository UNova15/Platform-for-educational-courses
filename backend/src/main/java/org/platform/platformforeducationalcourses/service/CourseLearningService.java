package org.platform.platformforeducationalcourses.service;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.courseutil.ScoreCalculator;
import org.platform.platformforeducationalcourses.creator.assembler.TestAssembler;
import refactor.course.domain.test.Question;
import refactor.course.domain.test.Test;
import refactor.progress.domain.lessonprogress.LessonProgress;
import refactor.progress.domain.testprogress.TestAttempt;
import refactor.course.application.port.in.lesson.query.LessonQueryResult;
import refactor.course.application.port.in.test.query.TestQueryResult;
import org.platform.platformforeducationalcourses.dto.test.TestPostDto;
import org.platform.platformforeducationalcourses.dto.test.TestPostRequest;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataProgressRepository;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataSubmissionsRepository;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataTestRepository;
import refactor.course.application.service.LessonRemoveService;
import refactor.course.application.service.TestManageService;
import org.platform.platformforeducationalcourses.validator.SubmissionValidator;
import org.springframework.stereotype.Service;

/**
 * Сервис для взаимодействия студентов с курсом
 */
@Service
@AllArgsConstructor
public class CourseLearningService {
    private final LessonRemoveService lessonRemoveService;
    private final TestManageService testManageService;

    private final DataSubmissionsRepository submissionsRepository;
    private final DataProgressRepository progressRepository;
    private final DataTestRepository testRepository;

    private final TestMapper testMapper;
    private final TestAssembler testAssembler;

    private final SubmissionValidator validator;
    private final ScoreCalculator scoreCalculator;

    public LessonQueryResult getLesson(long userId, long lessonId) {
        if (!progressRepository.existsByUserIdAndLessonId(userId, lessonId)) {
            LessonProgress lessonProgress = LessonProgress.createNew(userId, lessonId);
            progressRepository.save(lessonProgress);
        }

        return lessonRemoveService.findLesson(lessonId);
    }

    // TODO создать unique индекс на бд на поля long userId, long testId
    public TestQueryResult startAttempt(long userId, long testId) {
        if (!submissionsRepository.existsByUserIdAndTestId(userId, testId)) {
            TestAttempt submission = TestAttempt.createNew(userId, testId);
            submissionsRepository.save(submission);
        }

        return testManageService.getTest(testId);
    }

    // TODO исключение
    public void endAttempt(TestPostRequest request, long userId, long testId) {
        TestPostDto testPostDto = testMapper.toTestPostDto(request);

        TestAttempt submission =
                submissionsRepository.findByUserIdAndTestId(userId, testId).orElseThrow();

        // попытка уже была завершена
        if (submission.getCompletedAt() != null) {
            throw new IllegalArgumentException();
        }

        Test test = testRepository.findById(testId).orElseThrow();

        Map<Long, Question> questionsOrderById =
                test.questions().stream().collect(Collectors.toMap(Question::getId, question -> question));

        submission.submitAnswers(testPostDto.answers(), questionsOrderById, scoreCalculator, validator);

        submissionsRepository.save(submission);
    }

    public TestReview getTestReview(long userId, long testId) {
        TestAttempt submission =
                submissionsRepository.findByUserIdAndTestId(userId, testId).orElseThrow();

        if (submission.getCompletedAt() == null) {
            throw new IllegalArgumentException();
        }
        Test test = testRepository.findById(testId).orElseThrow();

        return testAssembler.createTestAttempt(test, submission);
    }
}
