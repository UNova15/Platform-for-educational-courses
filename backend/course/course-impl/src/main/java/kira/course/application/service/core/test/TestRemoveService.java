package kira.course.application.service.core.test;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.test.remove.TestRemoveUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestRemovePort;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TestRemoveService implements TestRemoveUseCase {
    private final TestLoadPort loadPort;
    private final TestRemovePort removePort;

    private final CourseAccessPort accessPort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public Result<Void, RemoveError> removeTest(Id<User> teacherId, Id<Test> testId) {
        Optional<Test> loaded = loadPort.loadById(testId);

        if (loaded.isEmpty()) {
            return Result.failure(RemoveError.TEST_NOT_FOUND);
        }

        if (!accessPort.isTestOwner(teacherId, testId)) {
            return Result.failure(RemoveError.ACCESS_DENY);
        }

        if (checkStatusPort.isArchivedByTestId(testId)) {
            return Result.failure(RemoveError.COURSE_ARCHIVED);
        }

        var test = loaded.get();

        if (!test.isDraft()) {
            // TODO удаление прогресса через событие
        }

        removePort.removeTestById(testId);
        return Result.success(null);
    }
}
