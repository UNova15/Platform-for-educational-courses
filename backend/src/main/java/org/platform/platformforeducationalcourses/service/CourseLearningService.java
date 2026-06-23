package org.platform.platformforeducationalcourses.service;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.courseutil.ScoreCalculator;
import org.platform.platformforeducationalcourses.creator.assembler.TestAssembler;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestPostDto;
import org.platform.platformforeducationalcourses.dto.test.TestPostRequest;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestReview;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.platform.platformforeducationalcourses.repository.ProgressRepository;
import org.platform.platformforeducationalcourses.repository.SubmissionsRepository;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.platform.platformforeducationalcourses.service.domain.LessonService;
import org.platform.platformforeducationalcourses.service.domain.TestService;
import org.platform.platformforeducationalcourses.validator.SubmissionValidator;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для взаимодействия студентов с курсом
 */

@Service
@AllArgsConstructor
public class CourseLearningService {
    private final LessonService lessonService;
    private final TestService testService;

    private final SubmissionsRepository submissionsRepository;
    private final ProgressRepository progressRepository;
    private final TestRepository testRepository;

    private final TestMapper testMapper;
    private final TestAssembler testAssembler;

    private final SubmissionValidator validator;
    private final ScoreCalculator scoreCalculator;

    public LessonFindResponse getLesson(long userId, long lessonId) {
        if (!progressRepository.existsByUserIdAndLessonId(userId, lessonId)) {
            LessonProgress lessonProgress = LessonProgress.createNew(userId, lessonId);
            progressRepository.save(lessonProgress);
        }

        return lessonService.findLesson(lessonId);
    }

    //TODO создать unique индекс на бд на поля long userId, long testId
    public TestFindResponse startAttempt(long userId, long testId) {
        if (!submissionsRepository.existsByUserIdAndTestId(userId, testId)) {
            TestSubmission submission = TestSubmission.createNew(userId, testId);
            submissionsRepository.save(submission);
        }

        return testService.getTest(testId);
    }

    //TODO исключение
    public void endAttempt(TestPostRequest request, long userId, long testId) {
        TestPostDto testPostDto = testMapper.toTestPostDto(request);

        TestSubmission submission = submissionsRepository.findByUserIdAndTestId(userId, testId)
                .orElseThrow();

        //попытка уже была завершена
        if (submission.getCompletedAt() != null) {
            throw new IllegalArgumentException();
        }

        Test test = testRepository.findById(testId)
                .orElseThrow();

        Map<Long, Question> questionsOrderById = test.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, question -> question));

        submission.submitAnswers(testPostDto.answers(), questionsOrderById, scoreCalculator, validator);

        submissionsRepository.save(submission);
    }

    public TestReview getTestReview(long userId, long testId) {
        TestSubmission submission = submissionsRepository.findByUserIdAndTestId(userId,testId)
                .orElseThrow();

        if(submission.getCompletedAt() == null){
            throw new IllegalArgumentException();
        }
        Test test = testRepository.findById(testId)
                .orElseThrow();

        return testAssembler.createTestAttempt(test,submission);
    }
}
