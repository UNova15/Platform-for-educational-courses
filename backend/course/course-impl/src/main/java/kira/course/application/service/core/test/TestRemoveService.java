package kira.course.application.service.core.test;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.test.remove.TestRemoveUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestRemovePort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class TestRemoveService implements TestRemoveUseCase {
    private final TestLoadPort loadPort;
    private final TestRemovePort removePort;

    private final CourseAccessPort accessPort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public void removeTest(Id<User> teacherId, Id<Test> testId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.TEST, teacherId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.TEST, teacherId, teacherId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByTestId(testId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException(
                    "Cannot remove test in archived course", CourseResourceName.TEST, teacherId);
        }

        if (!test.isDraft()) {
            // TODO удаление прогресса через событие
        }

        removePort.removeTestById(testId);
    }
}
