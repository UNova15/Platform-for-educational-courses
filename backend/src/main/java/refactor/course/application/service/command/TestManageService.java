package refactor.course.application.service.command;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.access.TestAccessException;
import refactor.common.exception.domain.TestNotFoundException;
import refactor.course.application.port.in.test.create.TestCreateCommand;
import refactor.course.application.port.in.test.create.TestCreateResult;
import refactor.course.application.port.in.test.create.TestCreateUseCase;
import refactor.course.application.port.in.test.remove.TestRemoveUseCase;
import refactor.course.application.port.in.test.update.TestUpdateCommand;
import refactor.course.application.port.in.test.update.TestUpdateUseCase;
import refactor.course.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.application.port.out.persistance.test.TestLoadPort;
import refactor.course.application.port.out.persistance.test.TestRemovePort;
import refactor.course.application.port.out.persistance.test.TestSavePort;
import refactor.course.application.service.factory.TestFactory;
import refactor.course.domain.question.Question;
import refactor.course.domain.test.Test;
import org.springframework.stereotype.Service;
import refactor.course.domain.test.TestDescription;
import refactor.course.domain.test.TestTitle;

import java.util.Set;

@Service
@AllArgsConstructor
public class TestManageService implements TestCreateUseCase, TestRemoveUseCase, TestUpdateUseCase {
    private final CourseAccessPort accessPort;
    private final TestSavePort savePort;
    private final TestRemovePort removePort;
    private final TestLoadPort loadPort;

    private final TestFactory testFactory;

    @Override
    public TestCreateResult createTest(TestCreateCommand command, long teacherId, long moduleId) {
        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
        }
        Test test = testFactory.fromTestCreateCommand(command);

        Test savedTest = savePort.save(test);

        return new TestCreateResult(
                savedTest.id(), savedTest.moduleId(), savedTest.description().value(), savedTest.orderIndex());
    }

    @Override
    public void removeTest(long teacherId, long testId) {
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
    public void updateTest(TestUpdateCommand command, long testId, long teacherId) {
        Test test = loadPort.loadById(testId).orElseThrow(() -> new TestNotFoundException(testId));

        if (!accessPort.isTestOwner(teacherId, testId)) {
            throw new TestAccessException(testId, teacherId);
        }

        var title = TestTitle.of(command.title());
        var description = TestDescription.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);
        savePort.save(test);
    }
}
