package refactor.course.application.service.command;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.access.TestAccessException;
import refactor.common.exception.domain.TestNotFoundException;
import refactor.course.application.port.in.test.command.create.TestCreateCommand;
import refactor.course.application.port.in.test.command.create.TestCreateResult;
import refactor.course.application.port.in.test.command.create.TestCreateUseCase;
import refactor.course.application.port.in.test.command.remove.RemoveTestCommand;
import refactor.course.application.port.in.test.command.remove.TestRemoveUseCase;
import refactor.course.application.port.in.test.command.update.TestUpdateCommand;
import refactor.course.application.port.in.test.command.update.TestUpdateUseCase;
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
    public TestCreateResult createTest(TestCreateCommand command) {
        if (!accessPort.canManageModule(command.teacherId(), command.moduleId())) {
            throw new ModuleAccessException(command.moduleId(), command.teacherId());
        }
        Test test = testFactory.fromTestCreateCommand(command);

        Test savedTest = savePort.save(test);

        return new TestCreateResult(
                savedTest.id(), savedTest.moduleId(), savedTest.description().value(), savedTest.orderIndex());
    }

    @Override
    public void removeTest(RemoveTestCommand command) {
        if (!loadPort.isExist(command.testId())) {
            throw new TestNotFoundException(command.testId());
        }

        if (!accessPort.canManageTest(command.requesterId(), command.testId())) {
            throw new TestAccessException(command.testId(), command.requesterId());
        }

        removePort.removeTestById(command.testId());
    }

    @Override
    @Transactional
    public void updateTest(TestUpdateCommand command) {
        Test test = loadPort.loadById(command.testId()).orElseThrow(() -> new TestNotFoundException(command.testId()));

        if (!accessPort.canManageTest(command.teacherId(), command.testId())) {
            throw new TestAccessException(command.testId(), command.teacherId());
        }

        var title = TestTitle.of(command.title());
        var description = TestDescription.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);
        savePort.save(test);
    }
}
