package kira.course.application.service.core.test;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.entity.Id;
import kira.course.application.port.in.test.status.PublishTestUseCase;
import kira.course.application.port.out.events.EventPublisherPort;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.query.CourseDetailsProviderPort;
import kira.course.application.port.out.persistance.module.ModuleCheckStatusPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.domain.markers.User;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;
import kira.course.events.ContentPublishEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class TestPublishService implements PublishTestUseCase {
    private final TestLoadPort loadPort;
    private final TestSavePort savePort;

    private final CourseAccessPort accessPort;
    private final ModuleCheckStatusPort checkModuleStatusPort;
    private final CourseDetailsProviderPort checkCourseStatusPort;

    private final EventPublisherPort eventPublisherPort;

    @Override
    public void publish(Id<User> requesterId, Id<Test> testId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.TEST, testId));

        if (!accessPort.isTestOwner(requesterId, testId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.TEST, requesterId, testId);
        }

        CourseDetails details = checkCourseStatusPort.findCourseDetailsByTestId(testId);
        details.verifyCanPublishContent();
        /*if (details.status() != CourseStatus.PUBLISHED) {
            throw new RuleConflictException(
                    "Cannot publish test in not published course", CourseAggregateType.TEST, testId);
        }*/

        CourseContentStatus moduleStatus = checkModuleStatusPort.findStatusByTestId(testId);
        if (moduleStatus != CourseContentStatus.PUBLISHED) {
            throw new RuleConflictException(
                    "Cannot publish test in not published module", CourseAggregateType.TEST, testId);
        }

        test.publish();
        savePort.save(test);

        //TODO
        IO.println("Тест опубликован");
        eventPublisherPort.publish(new ContentPublishEvent(
                ContentPublishEvent.ContentPublishType.TEST,
                test.title().value(),
                details.courseId().value(),
                testId.value()));
    }
}
