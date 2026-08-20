package refactor.course.application.service.command;

import lombok.RequiredArgsConstructor;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.course.application.port.in.module.command.create.ModuleCreateCommand;
import refactor.course.application.port.in.module.command.create.ModuleCreateResult;
import refactor.course.application.port.in.module.command.create.ModuleCreateUseCase;
import refactor.course.application.port.in.module.command.remove.ModuleRemoveCommand;
import refactor.course.application.port.in.module.command.remove.ModuleRemoveUseCase;
import refactor.course.application.port.in.module.command.update.ModuleUpdateCommand;
import refactor.course.application.port.in.module.command.update.ModuleUpdateUseCase;
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
    public ModuleCreateResult createModule(ModuleCreateCommand createCommand) {
        if (!accessPort.canManageCourse(createCommand.teacherId(), createCommand.courseId())) {
            throw new CourseAccessException(createCommand.courseId(), createCommand.teacherId());
        }
        var title = ModuleTitle.of(createCommand.title());
        var description = ModuleDescription.of(createCommand.description());

        CourseModule module =
                CourseModule.createNew(createCommand.courseId(), title, description, createCommand.orderIndex());

        CourseModule savedModule = savePort.save(module);

        return new ModuleCreateResult(savedModule.id(), savedModule.title().value());
    }

    @Override
    public void updateModule(ModuleUpdateCommand updateCommand) {
        CourseModule module = loadPort.loadById(updateCommand.moduleId())
                .orElseThrow(() -> new ModuleNotFoundException(updateCommand.moduleId(), updateCommand.teacherId()));

        if (!accessPort.canManageModule(updateCommand.teacherId(), updateCommand.moduleId())) {
            throw new ModuleAccessException(updateCommand.moduleId(), updateCommand.teacherId());
        }

        var title = ModuleTitle.of(updateCommand.title());
        var description = ModuleDescription.of(updateCommand.description());

        module.updateInfo(title, description, updateCommand.orderIndex());
        savePort.save(module);
    }

    @Override
    public void removeModule(ModuleRemoveCommand removeCommand) {
        if (!loadPort.isExistModule(removeCommand.moduleId())) {
            throw new ModuleNotFoundException(removeCommand.moduleId(), removeCommand.teacherId());
        }

        if (!accessPort.canManageModule(removeCommand.teacherId(), removeCommand.moduleId())) {
            throw new ModuleAccessException(removeCommand.moduleId(), removeCommand.teacherId());
        }
        removePort.removeById(removeCommand.moduleId());
    }
}
