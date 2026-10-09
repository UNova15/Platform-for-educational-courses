package kira.course.application.service.core.module;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.module.remove.ModuleRemoveUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleRemovePort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ModuleRemoveService implements ModuleRemoveUseCase {
    private final CourseAccessPort accessPort;
    private final ModuleRemovePort removePort;
    private final ModuleLoadPort loadPort;

    private final CourseStatusPort checkStatusPort;
    private final LessonLoadPort lessonLoadPort;
    private final TestLoadPort testLoadPort;

    @Override
    public Result<Void, RemoveError> removeModule(Id<CourseModule> moduleId, Id<User> teacherId) {

        if (!loadPort.isExistsModule(moduleId)) {
            return Result.failure(RemoveError.MODULE_NOT_FOUND);
        }
        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            return Result.failure(RemoveError.ACCESS_DENY);
        }
        if (checkStatusPort.isArchivedByModuleId(moduleId)) {
            return Result.failure(RemoveError.COURSE_ARCHIVED);
        }
        if (lessonLoadPort.isExistsInModule(moduleId) || testLoadPort.isExistsInModule(moduleId)) {
            return Result.failure(RemoveError.MODULE_NOT_EMPTY);
        }

        removePort.removeById(moduleId);
        return Result.success(null);
    }
}
