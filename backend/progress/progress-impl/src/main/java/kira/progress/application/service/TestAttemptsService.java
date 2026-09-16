package kira.progress.application.service;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.progress.application.exceptions.ProgressExceptionCode;
import kira.progress.application.exceptions.TestAlreadyCompletedException;
import kira.progress.application.port.in.testprogress.EndTestAttemptCommand;
import kira.progress.application.port.in.testprogress.EndTestAttemptUseCase;
import kira.progress.application.port.in.testprogress.StartTestAttemptUseCase;
import kira.progress.application.port.out.external.CourseStructurePort;
import kira.progress.application.port.out.external.TestAnswerKeyProviderPort;
import kira.progress.application.port.out.persistance.enrollment.EnrollmentLoadPort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptLoadPort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptSavePort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAttempt;
import kira.progress.domain.testprogress.valueobject.AnswerKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestAttemptsService implements StartTestAttemptUseCase, EndTestAttemptUseCase {
    private final CourseStructurePort courseStructurePort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final TestAttemptLoadPort loadPort;
    private final TestAttemptSavePort savePort;

    private final TestAnswerKeyProviderPort keyPort;

    @Override
    public void startTestAttempt(Id<User> userId, Id<Test> testId) {
        Id<Course> courseId = courseStructurePort
                .findCourseIdByTestId(testId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ProgressExceptionCode.TEST_NOT_FOUND_EXCEPTION, Test.class, testId));

        if (!enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new ResourceAccessException(
                    ProgressExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, userId, courseId);
        }

        // если попытка еще не закончена - игнорирование. Если попытка закончена - исключение
        Optional<TestAttempt> savedAttempt = loadPort.loadByUserIdAndTestId(userId, testId);

        if (savedAttempt.isPresent()) {
            if (savedAttempt.get().isCompleted()) {
                throw new TestAlreadyCompletedException(testId, userId);
            }
            return;
        }

        TestAttempt attempt = TestAttempt.createNew(userId, testId);
        savePort.save(attempt);
    }

    @Override
    public void endTestAttempt(EndTestAttemptCommand command) {
        TestAttempt attempt = loadPort.loadByUserIdAndTestId(command.userId(), command.testId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ProgressExceptionCode.TEST_ATTEMPT_NOT_FOUND_EXCEPTION, Test.class, command.testId()));

        if (attempt.completedAt() != null) {
            throw new TestAlreadyCompletedException(command.testId(), command.userId());
        }

        AnswerKey key = keyPort.findAnswerKeyByTestId(command.testId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ProgressExceptionCode.TEST_NOT_FOUND_EXCEPTION, Test.class, command.testId()));

        attempt.submitAnswers(command.answers(), key);
        savePort.save(attempt);
    }
}
