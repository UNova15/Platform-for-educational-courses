package kira.course.application.service.core.test;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.test.create.TestCreateCommand;
import kira.course.application.port.in.test.create.TestCreateResponse;
import kira.course.application.port.in.test.create.TestCreateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class TestCreateService implements TestCreateUseCase {
    private final TestSavePort savePort;

    private final CourseStatusPort checkStatusPort;
    private final CourseAccessPort accessPort;
    private final ModuleLoadPort moduleLoadPort;

    private final TestFactory testFactory;

    @Override
    public TestCreateResponse createTest(TestCreateCommand command, Id<User> teacherId, Id<CourseModule> moduleId) {
        if (!moduleLoadPort.isExistsModule(moduleId)) {
            throw new ResourceNotFoundException(CourseResourceName.MODULE, moduleId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.MODULE, teacherId, moduleId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByModuleId(moduleId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException(
                    "Cannot create test in archived course", CourseResourceName.MODULE, moduleId);
        }
        Test test = testFactory.fromTestCreateCommand(command, moduleId);

        Test savedTest = savePort.save(test);

        return new TestCreateResponse(
                savedTest.id().value(),
                savedTest.moduleId().value(),
                savedTest.description().value(),
                savedTest.orderIndex());
    }
}
