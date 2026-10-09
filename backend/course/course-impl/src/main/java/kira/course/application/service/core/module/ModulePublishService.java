package kira.course.application.service.core.module;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.module.status.PublishModuleUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonPublishPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleSavePort;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestPublishPort;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModulePublishService implements PublishModuleUseCase {
    private final ModuleLoadPort loadPort;
    private final ModuleSavePort savePort;

    private final CourseAccessPort accessPort;
    private final LessonPublishPort lessonPublishPort;
    private final TestPublishPort testPublishPort;
    private final CourseStatusPort courseStatusPort;
    private final LessonLoadPort lessonLoadPort;
    private final TestLoadPort testLoadPort;

    @Override
    public void publish(Id<User> requesterId, Id<CourseModule> moduleId) {
        CourseModule module = loadPort.loadById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.MODULE, moduleId));

        if (courseStatusPort.isPublishedByModuleId(moduleId)) {
            throw new RuleConflictException(
                    "Cannot publish module in not published course", CourseResourceName.MODULE, moduleId);
        }

        if (!lessonLoadPort.isExistsInModule(moduleId) && !testLoadPort.isExistsInModule(moduleId)) {
            throw new RuleConflictException("Cannot publish empty module", CourseResourceName.MODULE, moduleId);
        }

        if (!accessPort.isModuleOwner(requesterId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.MODULE, requesterId, moduleId);
        }

        if (!module.canPublish()) {
            throw new RuleConflictException("Cannot publish module", CourseResourceName.MODULE, moduleId);
        }
        module.publish();
        savePort.save(module);

        lessonPublishPort.publishAllInModule(moduleId);
        testPublishPort.publishAllInModule(moduleId);
    }
}
