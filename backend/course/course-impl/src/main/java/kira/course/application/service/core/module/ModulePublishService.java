package kira.course.application.service.core.module;

import common.domain.entity.Id;
import common.application.exception.RuleConflictException;
import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import kira.course.domain.CourseAggregateType;
import kira.course.application.port.in.module.status.PublishModuleUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonChangeStatusPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleSavePort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestChangeStatusPort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ModulePublishService implements PublishModuleUseCase {
    private final ModuleLoadPort loadPort;
    private final ModuleSavePort savePort;

    private final CourseAccessPort accessPort;
    private final LessonChangeStatusPort lessonChangeStatusPort;
    private final TestChangeStatusPort testChangeStatusPort;
    private final CourseStatusPort courseStatusPort;
    private final LessonLoadPort lessonLoadPort;
    private final TestLoadPort testLoadPort;

    @Override
    public void publish(Id<User> requesterId, Id<CourseModule> moduleId) {
        CourseModule module = loadPort.loadById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.MODULE, moduleId));

        CourseStatus status = courseStatusPort.findCourseStatusByModuleId(moduleId);
        if (status != CourseStatus.PUBLISHED) {
            throw new RuleConflictException(
                    "Cannot publish module in not published course", CourseAggregateType.MODULE, moduleId);
        }

        if (!lessonLoadPort.isExistsInModule(moduleId) && !testLoadPort.isExistsInModule(moduleId)) {
            throw new RuleConflictException("Cannot publish empty module", CourseAggregateType.MODULE, moduleId);
        }

        if (!accessPort.isModuleOwner(requesterId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.MODULE, requesterId, moduleId);
        }

        module.publish();
        savePort.save(module);

        lessonChangeStatusPort.updateAllStatusesByModuleId(moduleId, CourseContentStatus.PUBLISHED);
        testChangeStatusPort.updateAllStatusesInModule(moduleId, CourseContentStatus.PUBLISHED);
    }
}
