package kira.course.application.service.core.module;

import common.domain.entity.Id;
import common.application.exception.RuleConflictException;
import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import kira.course.domain.CourseAggregateType;
import kira.course.application.port.in.module.update.ModuleUpdateCommand;
import kira.course.application.port.in.module.update.ModuleUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleSavePort;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ModuleUpdateService implements ModuleUpdateUseCase {
    private final ModuleLoadPort loadPort;
    private final ModuleSavePort savePort;

    private final CourseAccessPort accessPort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public void updateModule(ModuleUpdateCommand updateCommand, Id<CourseModule> moduleId, Id<User> teacherId) {
        CourseModule module = loadPort.loadById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.MODULE, moduleId));

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.MODULE, teacherId, moduleId);
        }

        CourseStatus status = checkStatusPort.findCourseStatusByModuleId(moduleId);
        if (status == CourseStatus.ARCHIVED) {
            throw new RuleConflictException(
                    "Cannot update module in archived course", CourseAggregateType.MODULE, moduleId);
        }

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());

        module.updateInfo(title, description, updateCommand.orderIndex());
        savePort.save(module);
    }
}
