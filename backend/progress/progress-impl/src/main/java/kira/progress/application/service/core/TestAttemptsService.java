package kira.progress.application.service.core;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.entity.Id;
import java.util.Optional;
import kira.progress.application.exception.TestAlreadyCompletedException;
import kira.progress.application.port.in.testprogress.EndTestAttemptCommand;
import kira.progress.application.port.in.testprogress.EndTestAttemptUseCase;
import kira.progress.application.port.in.testprogress.StartTestAttemptUseCase;
import kira.progress.application.port.in.testprogress.TestResult;
import kira.progress.application.port.out.external.CheckCourseStatusPort;
import kira.progress.application.port.out.external.CourseStructurePort;
import kira.progress.application.port.out.external.TestAnswerKeyProviderPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptLoadPort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptSavePort;
import kira.progress.domain.ProgressAggregateType;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAttempt;
import kira.progress.domain.testprogress.valueobject.AnswerKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TestAttemptsService implements StartTestAttemptUseCase, EndTestAttemptUseCase {
    private final CourseStructurePort courseStructurePort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final TestAttemptLoadPort loadPort;
    private final TestAttemptSavePort savePort;

    private final TestAnswerKeyProviderPort keyPort;
    private final CheckCourseStatusPort courseStatusPort;

    @Override
    public void startTestAttempt(Id<User> userId, Id<Test> testId) {
        Id<Course> courseId = courseStructurePort
                .findCourseIdByTestId(testId)
                .orElseThrow(() -> new ResourceNotFoundException(ProgressAggregateType.TEST, testId));

        if (!enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new ResourceAccessDeniedException(ProgressAggregateType.COURSE, userId, courseId);
        }

        if (!courseStatusPort.isTestPublished(testId)) {
            throw new RuleConflictException(
                            "Cannot start test attempt in not published test", ProgressAggregateType.ATTEMPT)
                    .withContext("testId", testId.value())
                    .withContext("userId", userId.value());
        }

        // если попытка еще не закончена - игнорирование. Если попытка закончена - исключение
        Optional<TestAttempt> savedAttempt = loadPort.loadByUserIdAndTestId(userId, testId);

        savedAttempt.ifPresentOrElse(
                attempt -> {
                    if (attempt.isCompleted()) {
                        throw new TestAlreadyCompletedException(attempt.id(), testId, userId);
                    }
                },
                () -> {
                    TestAttempt attempt = TestAttempt.create(userId, testId);
                    savePort.save(attempt);
                });
    }

    @Override
    public TestResult endTestAttempt(EndTestAttemptCommand command) {
        TestAttempt attempt = loadPort.loadByUserIdAndTestId(command.userId(), command.testId())
                .orElseThrow(() -> new ResourceNotFoundException(ProgressAggregateType.TEST, command.testId()));

        AnswerKey key = keyPort.findAnswerKeyByTestId(command.testId())
                .orElseThrow(() -> new ResourceNotFoundException(ProgressAggregateType.TEST, command.testId()));

        attempt.submitAnswers(command.answers(), key);
        savePort.save(attempt);
        return new TestResult(attempt.score());
    }
}
