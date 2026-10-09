package kira.course.application.service.core.test;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.test.create.TestCreateCommand;
import kira.course.application.port.in.test.create.TestCreateResult;
import kira.course.application.port.in.test.create.TestCreateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.application.service.factory.TestFactory;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestCreateService implements TestCreateUseCase {
    private final TestSavePort savePort;

    private final CourseStatusPort checkStatusPort;
    private final CourseAccessPort accessPort;
    private final ModuleLoadPort moduleLoadPort;

    private final TestFactory testFactory;

    @Override
    public Result<TestCreateResult, CreateError> createTest(
            TestCreateCommand command, Id<User> teacherId, Id<CourseModule> moduleId) {
        if (!moduleLoadPort.isExistsModule(moduleId)) {
            return Result.failure(CreateError.MODULE_NOT_FOUND);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            return Result.failure(CreateError.ACCESS_DENY);
        }

        if (checkStatusPort.isArchivedByModuleId(moduleId)) {
            return Result.failure(CreateError.COURSE_ARCHIVED);
        }

        Test test = testFactory.fromTestCreateCommand(command, moduleId);

        Test savedTest = savePort.save(test);

        var response = new TestCreateResult(
                savedTest.id().value(),
                savedTest.moduleId().value(),
                savedTest.description().value(),
                savedTest.orderIndex());

        return Result.success(response);
    }
}
