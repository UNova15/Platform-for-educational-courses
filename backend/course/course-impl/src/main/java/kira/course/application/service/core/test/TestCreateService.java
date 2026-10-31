package kira.course.application.service.core.test;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.application.exception.RuleConflictException;
import common.domain.entity.Id;
import kira.course.application.port.in.test.create.TestCreateCommand;
import kira.course.application.port.in.test.create.TestCreateResponse;
import kira.course.application.port.in.test.create.TestCreateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseCheckStatusPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.domain.CourseAggregateType;
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

    private final CourseCheckStatusPort checkStatusPort;
    private final CourseAccessPort accessPort;
    private final ModuleLoadPort moduleLoadPort;

    private final TestFactory testFactory;

    @Override
    public TestCreateResponse createTest(TestCreateCommand command, Id<User> teacherId, Id<CourseModule> moduleId) {
        if (!moduleLoadPort.isExistsModule(moduleId)) {
            throw new ResourceNotFoundException(CourseAggregateType.MODULE, moduleId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.MODULE, teacherId, moduleId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByModuleId(moduleId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException(
                    "Cannot create test in archived course", CourseAggregateType.MODULE, moduleId);
        }
        Test test = testFactory.fromTestCreateCommand(command, moduleId);

        var savedTest = savePort.save(test);

        return new TestCreateResponse(
                savedTest.id().value(),
                savedTest.moduleId().value(),
                savedTest.description().value(),
                savedTest.orderIndex());
    }
}
