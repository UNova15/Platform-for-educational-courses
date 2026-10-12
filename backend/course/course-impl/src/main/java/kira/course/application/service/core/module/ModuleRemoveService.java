package kira.course.application.service.core.module;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.module.remove.ModuleRemoveUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleRemovePort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.domain.course.CourseStatus;
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
    public void removeModule(Id<CourseModule> moduleId, Id<User> teacherId) {

        if (!loadPort.isExistsModule(moduleId)) {
            throw new ResourceNotFoundException(CourseResourceName.MODULE, moduleId);
        }
        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.MODULE, teacherId, moduleId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByModuleId(moduleId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException(
                    "Cannot remove module in archived course", CourseResourceName.MODULE, moduleId);
        }
        if (lessonLoadPort.isExistsInModule(moduleId) || testLoadPort.isExistsInModule(moduleId)) {
            throw new RuleConflictException("Cannot remove non empty module", CourseResourceName.MODULE, moduleId);
        }
        removePort.removeById(moduleId);
    }
}
