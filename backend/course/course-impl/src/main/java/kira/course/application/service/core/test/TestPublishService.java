package kira.course.application.service.core.test;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.test.status.PublishTestUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.module.ModuleStatusPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.markers.User;
import kira.course.domain.shared.Status;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class TestPublishService implements PublishTestUseCase {
    private final TestLoadPort loadPort;
    private final TestSavePort savePort;

    private final CourseAccessPort accessPort;
    private final ModuleStatusPort checkModuleStatusPort;
    private final CourseStatusPort checkCourseStatusPort;

    @Override
    public void publish(Id<User> requesterId, Id<Test> testId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.TEST, testId));

        if (!accessPort.isTestOwner(requesterId, testId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.TEST, requesterId, testId);
        }

        CourseStatus courseStatus = checkCourseStatusPort.findCourseStatusByTestId(testId);
        if (courseStatus != CourseStatus.PUBLISHED) {
            throw new RuleConflictException(
                    "Cannot publish test in not published course", CourseResourceName.TEST, testId);
        }


        Status moduleStatus = checkModuleStatusPort.findStatusByTestId(testId);
        if (moduleStatus != Status.PUBLISHED) {
            throw new RuleConflictException(
                    "Cannot publish test in not published module", CourseResourceName.TEST, testId);
        }

        if (!test.canPublish()) {
            throw new RuleConflictException("Cannot publish test in current status", CourseResourceName.TEST, testId)
                    .withContext("status", test.status());
        }

        test.publish();
        savePort.save(test);
    }
}
