package kira.course.application.service.core.module;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.module.update.ModuleUpdateCommand;
import kira.course.application.port.in.module.update.ModuleUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleSavePort;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleUpdateService implements ModuleUpdateUseCase {
    private final ModuleLoadPort loadPort;
    private final ModuleSavePort savePort;

    private final CourseAccessPort accessPort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public Result<Void, ModuleUpdateUseCase.UpdateError> updateModule(
            ModuleUpdateCommand updateCommand, Id<CourseModule> moduleId, Id<User> teacherId) {
        Optional<CourseModule> loaded = loadPort.loadById(moduleId);

        if (loaded.isEmpty()) {
            return Result.failure(ModuleUpdateUseCase.UpdateError.MODULE_NOT_FOUND);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            return Result.failure(ModuleUpdateUseCase.UpdateError.ACCESS_DENY);
        }

        if (checkStatusPort.isArchivedByModuleId(moduleId)) {
            return Result.failure(ModuleUpdateUseCase.UpdateError.COURSE_ARCHIVED);
        }

        var module = loaded.get();
        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());

        module.updateInfo(title, description, updateCommand.orderIndex());
        savePort.save(module);

        return Result.success(null);
    }
}
