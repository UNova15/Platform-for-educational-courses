package kira.course.application.service.core.test;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import java.util.Set;
import kira.course.application.port.in.test.update.TestUpdateCommand;
import kira.course.application.port.in.test.update.TestUpdateUseCase;
import kira.course.application.port.out.events.EventPublisherPort;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.query.CourseDetailsProviderPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.domain.markers.User;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.test.Question;
import kira.course.domain.test.Test;
import kira.course.events.ContentChangeEvent;
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
    private final CourseDetailsProviderPort checkStatusPort;
    private final CourseAccessPort accessPort;

    @Transactional
    @Override
    public void updateTest(TestUpdateCommand command, Id<Test> testId, Id<User> teacherId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.TEST, testId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.TEST, teacherId, testId);
        }

        CourseDetails details = checkStatusPort.findCourseDetailsByTestId(testId);
        details.verifyCanChangeContent();

        var title = Title.of(command.title());
        var description = Description.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);

        savePort.save(test);

        publisherPort.publish(new ContentChangeEvent(
                ContentChangeEvent.ContentChangeType.TEST,
                ContentChangeEvent.ChangeAction.UPDATE,
                test.title().value(),
                test.id().value(),
                test.isPublish()));
    }
}
