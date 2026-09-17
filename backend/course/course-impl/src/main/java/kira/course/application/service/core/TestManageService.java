package kira.course.application.service.core;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.course.application.exceptions.CourseExceptionCode;
import kira.course.application.port.in.test.create.TestCreateCommand;
import kira.course.application.port.in.test.create.TestCreateResult;
import kira.course.application.port.in.test.create.TestCreateUseCase;
import kira.course.application.port.in.test.remove.TestRemoveUseCase;
import kira.course.application.port.in.test.update.TestUpdateCommand;
import kira.course.application.port.in.test.update.TestUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestRemovePort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.application.service.factory.TestFactory;
import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Question;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class TestManageService implements TestCreateUseCase, TestRemoveUseCase, TestUpdateUseCase {
    private final CourseAccessPort accessPort;
    private final TestSavePort savePort;
    private final TestRemovePort removePort;
    private final TestLoadPort loadPort;
    private final ModuleLoadPort moduleLoadPort;

    private final TestFactory testFactory;

    @Override
    public TestCreateResult createTest(TestCreateCommand command, Id<User> teacherId, Id<CourseModule> moduleId) {
        if (!moduleLoadPort.isExistModule(moduleId)) {
            throw new ResourceNotFoundException(
                    CourseExceptionCode.MODULE_NOT_FOUND_EXCEPTION, CourseModule.class, moduleId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.MODULE_ACCESS_EXCEPTION, CourseModule.class, teacherId, moduleId);
        }
        Test test = testFactory.fromTestCreateCommand(command, moduleId);

        Test savedTest = savePort.save(test);

        return new TestCreateResult(
                savedTest.id().value(),
                savedTest.moduleId().value(),
                savedTest.description().value(),
                savedTest.orderIndex());
    }

    @Override
    public void removeTest(Id<User> teacherId, Id<Test> testId) {
        if (!loadPort.isExist(testId)) {
            throw new ResourceNotFoundException(CourseExceptionCode.TEST_NOT_FOUND_EXCEPTION, Test.class, testId);
        }

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessException(CourseExceptionCode.TEST_ACCESS_EXCEPTION, Test.class, teacherId, testId);
        }

        removePort.removeTestById(testId);
    }

    @Override
    @Transactional
    public void updateTest(TestUpdateCommand command, Id<Test> testId, Id<User> teacherId) {
        Test test = loadPort.loadById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.TEST_NOT_FOUND_EXCEPTION, Test.class, testId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new ResourceAccessException(CourseExceptionCode.TEST_ACCESS_EXCEPTION, Test.class, teacherId, testId);
        }

        var title = Title.of(command.title());
        var description = Description.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);
        savePort.save(test);
    }
}
