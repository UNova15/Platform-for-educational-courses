package refactor.course.application.service.command;

import lombok.RequiredArgsConstructor;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.course.application.port.in.module.create.ModuleCreateCommand;
import refactor.course.application.port.in.module.create.ModuleCreateResult;
import refactor.course.application.port.in.module.create.ModuleCreateUseCase;
import refactor.course.application.port.in.module.remove.ModuleRemoveUseCase;
import refactor.course.application.port.in.module.update.ModuleUpdateCommand;
import refactor.course.application.port.in.module.update.ModuleUpdateUseCase;
import refactor.course.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.application.port.out.persistance.module.ModuleLoadPort;
import refactor.course.application.port.out.persistance.module.ModuleRemovePort;
import refactor.course.application.port.out.persistance.module.ModuleSavePort;
import refactor.course.domain.module.CourseModule;
import org.springframework.stereotype.Service;
import refactor.course.domain.module.ModuleDescription;
import refactor.course.domain.module.ModuleTitle;

@Service
@RequiredArgsConstructor
class ModuleManageService implements ModuleCreateUseCase, ModuleRemoveUseCase, ModuleUpdateUseCase {
    private final CourseAccessPort accessPort;
    private final ModuleSavePort savePort;
    private final ModuleRemovePort removePort;
    private final ModuleLoadPort loadPort;

    @Override
    public ModuleCreateResult createModule(ModuleCreateCommand createCommand, long courseId, long teacherId) {
        if (!accessPort.isCourseOwner(teacherId, courseId)) {
            throw new CourseAccessException(courseId, teacherId);
        }
        var title = ModuleTitle.of(createCommand.title());
        var description = ModuleDescription.of(createCommand.description());

        CourseModule module = CourseModule.createNew(courseId, title, description, createCommand.orderIndex());

        CourseModule savedModule = savePort.save(module);

        return new ModuleCreateResult(savedModule.id(), savedModule.title().value());
    }

    @Override
    public void updateModule(ModuleUpdateCommand updateCommand, long moduleId, long teacherId) {
        CourseModule module =
                loadPort.loadById(moduleId).orElseThrow(() -> new ModuleNotFoundException(moduleId, teacherId));

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
        }

        var title = ModuleTitle.of(updateCommand.title());
        var description = ModuleDescription.of(updateCommand.description());

        module.updateInfo(title, description, updateCommand.orderIndex());
        savePort.save(module);
    }

    @Override
    public void removeModule(long moduleId, long teacherId) {
        if (!loadPort.isExistModule(moduleId)) {
            throw new ModuleNotFoundException(moduleId, teacherId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
        }
        removePort.removeById(moduleId);
    }
}
