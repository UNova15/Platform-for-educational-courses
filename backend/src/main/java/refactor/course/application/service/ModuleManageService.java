package refactor.course.application.service;

import lombok.RequiredArgsConstructor;
import refactor.common.domain.Id;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.access.ModuleAccessException;
import refactor.common.exception.domain.CourseNotFoundException;
import refactor.common.exception.domain.ModuleNotFoundException;
import refactor.course.application.port.in.module.create.ModuleCreateCommand;
import refactor.course.application.port.in.module.create.ModuleCreateResult;
import refactor.course.application.port.in.module.create.ModuleCreateUseCase;
import refactor.course.application.port.in.module.remove.ModuleRemoveUseCase;
import refactor.course.application.port.in.module.update.ModuleUpdateCommand;
import refactor.course.application.port.in.module.update.ModuleUpdateUseCase;
import refactor.course.application.port.out.persistance.access.CourseAccessPort;
import refactor.course.application.port.out.persistance.course.CourseLoadPort;
import refactor.course.application.port.out.persistance.module.ModuleLoadPort;
import refactor.course.application.port.out.persistance.module.ModuleRemovePort;
import refactor.course.application.port.out.persistance.module.ModuleSavePort;
import refactor.course.domain.internal.common.Description;
import refactor.course.domain.internal.common.Title;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.internal.module.CourseModule;
import org.springframework.stereotype.Service;
import refactor.course.domain.external.User;

@Service
@RequiredArgsConstructor
class ModuleManageService implements ModuleCreateUseCase, ModuleRemoveUseCase, ModuleUpdateUseCase {
    private final CourseAccessPort accessPort;
    private final ModuleSavePort savePort;
    private final ModuleRemovePort removePort;
    private final ModuleLoadPort loadPort;
    private final CourseLoadPort courseLoadPort;

    @Override
    public ModuleCreateResult createModule(ModuleCreateCommand createCommand, Id<Course> courseId, Id<User> teacherId) {
        if (!courseLoadPort.isExist(courseId)) {
            throw new CourseNotFoundException(courseId, teacherId);
        }

        if (!accessPort.isCourseOwner(teacherId, courseId)) {
            throw new CourseAccessException(courseId, teacherId);
        }
        var title = Title.of(createCommand.title());
        var description = Description.of(createCommand.description());

        CourseModule module = CourseModule.createNew(courseId, title, description, createCommand.orderIndex());

        CourseModule savedModule = savePort.save(module);

        return new ModuleCreateResult(
                savedModule.id().value(), savedModule.title().value());
    }

    @Override
    public void updateModule(ModuleUpdateCommand updateCommand, Id<CourseModule> moduleId, Id<User> teacherId) {
        CourseModule module =
                loadPort.loadById(moduleId).orElseThrow(() -> new ModuleNotFoundException(moduleId, teacherId));

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
        }

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());

        module.updateInfo(title, description, updateCommand.orderIndex());
        savePort.save(module);
    }

    @Override
    public void removeModule(Id<CourseModule> moduleId, Id<User> teacherId) {
        if (!loadPort.isExistModule(moduleId)) {
            throw new ModuleNotFoundException(moduleId, teacherId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ModuleAccessException(moduleId, teacherId);
        }
        removePort.removeById(moduleId);
    }
}
