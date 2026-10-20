package kira.course.application.service.core.test;

import common.domain.entity.Id;

import java.util.Set;

import common.application.exception.RuleConflictException;
import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import kira.course.application.port.out.events.EventPublisherPort;
import kira.course.domain.CourseAggregateType;
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
import kira.course.events.TestUpdatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class TestUpdateService implements TestUpdateUseCase {
    private final TestSavePort savePort;
    private final TestLoadPort loadPort;

    private final TestFactory testFactory;

    private final EventPublisherPort publisherPort;
    private final CourseStatusPort checkStatusPort;
    private final CourseAccessPort accessPort;

    @Override
    @Transactional
    public void updateTest(TestUpdateCommand command, Id<Test> testId, Id<User> teacherId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.TEST, testId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.TEST, testId, testId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByTestId(testId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException("Cannot update test in archive course", CourseAggregateType.TEST, testId);
        }

        var title = Title.of(command.title());
        var description = Description.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);

        savePort.save(test);

        publisherPort.publish(new TestUpdatedEvent(test.id().value(), test.isPublish()));
    }
}
