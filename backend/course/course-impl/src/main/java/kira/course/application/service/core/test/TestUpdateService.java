package kira.course.application.service.core.test;

import common.domain.Id;

import java.util.Set;

import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.test.update.TestUpdateCommand;
import kira.course.application.port.in.test.update.TestUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.markers.User;
import kira.course.domain.test.Question;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class TestUpdateService implements TestUpdateUseCase {
    private final CourseAccessPort accessPort;
    private final TestSavePort savePort;
    private final TestLoadPort loadPort;
    private final CourseStatusPort checkStatusPort;

    private final TestFactory testFactory;

    @Override
    @Transactional
    public void updateTest(TestUpdateCommand command, Id<Test> testId, Id<User> teacherId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.TEST, testId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.TEST, testId, testId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByTestId(testId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException("Cannot update test in archive course", CourseResourceName.TEST, testId);
        }

        var title = Title.of(command.title());
        var description = Description.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);

        if (!test.isDraft()) {
            // TODO удаление прогресса через событие
        }

        savePort.save(test);
    }
}
