package refactor.progress.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.common.domain.Id;
import refactor.progress.application.exception.CourseAccessDenialException;
import refactor.progress.application.exception.TestAlreadyCompletedException;
import refactor.progress.application.exception.TestAttemptNotFoundException;
import refactor.progress.application.exception.TestNotFoundException;
import refactor.progress.application.port.in.testprogress.EndTestAttemptCommand;
import refactor.progress.application.port.in.testprogress.EndTestAttemptUseCase;
import refactor.progress.application.port.in.testprogress.StartTestAttemptUseCase;
import refactor.progress.application.port.out.external.TestAnswerKeyProviderPort;
import refactor.progress.application.port.out.external.TestProviderPort;
import refactor.progress.application.port.out.persistance.EnrollmentLoadPort;
import refactor.progress.application.port.out.persistance.TestAttemptLoadPort;
import refactor.progress.application.port.out.persistance.TestAttemptSavePort;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;
import refactor.progress.domain.testprogress.TestAttempt;
import refactor.progress.domain.testprogress.valueobject.AnswerKey;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestAttemptsService implements StartTestAttemptUseCase, EndTestAttemptUseCase {
    private final TestProviderPort testProviderPort;
    private final EnrollmentLoadPort enrollmentLoadPort;
    private final TestAttemptLoadPort loadPort;
    private final TestAttemptSavePort savePort;

    private final TestAnswerKeyProviderPort keyPort;

    @Override
    public void startTestAttempt(Id<User> userId, Id<Test> testId) {
        Id<Course> courseId =
                testProviderPort.findCourseIdByTestId(testId).orElseThrow(() -> new TestNotFoundException(testId));

        if (!enrollmentLoadPort.isEnrollmentExist(userId, courseId)) {
            throw new CourseAccessDenialException(courseId, userId);
        }

        // если попытка еще не закончена - игнорирование. Если попытка закончена - исключение
        Optional<TestAttempt> savedAttempt = loadPort.loadByUserIdAndTestId(userId, testId);

        if (savedAttempt.isPresent()) {
            if (savedAttempt.get().isCompleted()) {
                throw new TestAlreadyCompletedException(userId, testId);
            }
            return;
        }

        TestAttempt attempt = TestAttempt.createNew(userId, testId);
        savePort.save(attempt);
    }

    @Override
    public void endTestAttempt(EndTestAttemptCommand command) {
        TestAttempt attempt = loadPort.loadByUserIdAndTestId(command.userId(), command.testId())
                .orElseThrow(() -> new TestAttemptNotFoundException(command.userId(), command.testId()));

        if (attempt.completedAt() != null) {
            throw new TestAlreadyCompletedException(command.userId(), command.testId());
        }

        AnswerKey key = keyPort.findAnswerKeyByTestId(command.testId())
                .orElseThrow(() -> new TestNotFoundException(command.testId()));

        attempt.submitAnswers(command.answers(), key);
        savePort.save(attempt);
    }
}
