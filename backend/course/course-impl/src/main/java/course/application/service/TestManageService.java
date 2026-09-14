package course.application.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import refactor.common.domain.Id;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.access.TestAccessException;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.common.exception.domain.TestNotFoundException;
import refactor.course.implementation.application.port.in.test.create.TestCreateCommand;
import refactor.course.implementation.application.port.in.test.create.TestCreateResult;
import refactor.course.implementation.application.port.in.test.create.TestCreateUseCase;
import refactor.course.implementation.application.port.in.test.remove.TestRemoveUseCase;
import refactor.course.implementation.application.port.in.test.update.TestUpdateCommand;
import refactor.course.implementation.application.port.in.test.update.TestUpdateUseCase;
import refactor.course.implementation.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.implementation.application.port.out.persistance.module.ModuleLoadPort;
import refactor.course.implementation.application.port.out.persistance.test.TestLoadPort;
import refactor.course.implementation.application.port.out.persistance.test.TestRemovePort;
import refactor.course.implementation.application.port.out.persistance.test.TestSavePort;
import refactor.course.implementation.application.service.factory.TestFactory;
import refactor.course.implementation.domain.markers.Account;
import refactor.course.implementation.domain.common.Description;
import refactor.course.implementation.domain.common.Title;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.test.Question;
import refactor.course.implementation.domain.test.Test;
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
    public TestCreateResult createTest(TestCreateCommand command, Id<Account> teacherId, Id<CourseModule> moduleId) {
        if (!moduleLoadPort.isExistModule(moduleId)) {
            throw new ModuleNotFoundException(moduleId, teacherId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
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
    public void removeTest(Id<Account> teacherId, Id<Test> testId) {
        if (!loadPort.isExist(testId)) {
            throw new TestNotFoundException(testId);
        }

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new TestAccessException(testId, teacherId);
        }

        removePort.removeTestById(testId);
    }

    @Override
    @Transactional
    public void updateTest(TestUpdateCommand command, Id<Test> testId, Id<Account> teacherId) {
        Test test = loadPort.loadById(testId).orElseThrow(() -> new TestNotFoundException(testId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new TestAccessException(testId, teacherId);
        }

        var title = Title.of(command.title());
        var description = Description.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);
        savePort.save(test);
    }
}
