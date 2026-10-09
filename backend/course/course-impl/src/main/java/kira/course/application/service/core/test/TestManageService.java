package kira.course.application.service.core.test;

import common.domain.Id;
import common.domain.Result;

import java.util.Optional;
import java.util.Set;
import kira.course.application.port.in.test.update.TestUpdateCommand;
import kira.course.application.port.in.test.update.TestUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.application.service.factory.TestFactory;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.markers.User;
import kira.course.domain.test.Question;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TestManageService implements TestUpdateUseCase {
    private final CourseAccessPort accessPort;
    private final TestSavePort savePort;
    private final TestLoadPort loadPort;
    private final CourseStatusPort checkStatusPort;

    private final TestFactory testFactory;

    @Override
    @Transactional
    public Result<Void, UpdateError> updateTest(TestUpdateCommand command, Id<Test> testId, Id<User> teacherId) {
        Optional<Test> loaded = loadPort.loadById(testId);

        if (loaded.isEmpty()) {
            return Result.failure(UpdateError.TEST_NOT_FOUND);
        }

        if (!accessPort.isTestOwner(teacherId, testId)) {
            return Result.failure(UpdateError.ACCESS_DENY);
        }

        if (checkStatusPort.isArchivedByTestId(testId)) {
            return Result.failure(UpdateError.COURSE_ARCHIVED);
        }

        var test = loaded.get();

        var title = Title.of(command.title());
        var description = Description.of(command.description());
        Set<Question> questions = testFactory.fromQuestionUpdateCommand(command.questions());

        test.update(title, description, command.orderIndex(), questions);

        if (!test.isDraft()) {
            // TODO удаление прогресса через событие
        }

        savePort.save(test);
        return Result.success(null);
    }
}
