package kira.course.application.service.core.test;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.course.application.port.in.test.remove.TestRemoveUseCase;
import kira.course.application.port.out.events.EventPublisherPort;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.query.CourseDetailsProviderPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestRemovePort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import kira.course.events.ContentChangeEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class TestRemoveService implements TestRemoveUseCase {
    private final TestLoadPort loadPort;
    private final TestRemovePort removePort;

    private final CourseAccessPort accessPort;
    private final CourseDetailsProviderPort checkStatusPort;

    private final EventPublisherPort publisherPort;

    @Transactional
    @Override
    public void removeTest(Id<User> teacherId, Id<Test> testId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.TEST, testId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.TEST, teacherId, testId);
        }

        CourseDetails details = checkStatusPort.findCourseDetailsByTestId(testId);
        details.verifyCanChangeContent();

        removePort.removeTestById(testId);

        publisherPort.publish(new ContentChangeEvent(
                ContentChangeEvent.ContentChangeType.TEST,
                ContentChangeEvent.ChangeAction.REMOVE,
                test.title().value(),
                test.id().value(),
                test.isPublish()));
    }
}
