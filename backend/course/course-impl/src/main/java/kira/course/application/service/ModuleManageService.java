package kira.course.application.service;

import common.domain.Id;
import common.exception.ResourceAccessException;
import common.exception.ResourceNotFoundException;
import kira.course.application.exceptions.CourseExceptionCode;
import kira.course.application.port.in.module.create.ModuleCreateCommand;
import kira.course.application.port.in.module.create.ModuleCreateResult;
import kira.course.application.port.in.module.create.ModuleCreateUseCase;
import kira.course.application.port.in.module.remove.ModuleRemoveUseCase;
import kira.course.application.port.in.module.update.ModuleUpdateCommand;
import kira.course.application.port.in.module.update.ModuleUpdateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleRemovePort;
import kira.course.application.port.out.persistance.module.ModuleSavePort;
import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new ResourceNotFoundException(CourseExceptionCode.COURSE_NOT_FOUND_EXCEPTION, Course.class, courseId);
        }

        if (!accessPort.isCourseOwner(teacherId, courseId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.COURSE_ACCESS_EXCEPTION, Course.class, teacherId, courseId);
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
        CourseModule module = loadPort.loadById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.MODULE_NOT_FOUND_EXCEPTION, CourseModule.class, moduleId));

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.MODULE_ACCESS_EXCEPTION, CourseModule.class, teacherId, moduleId);
        }

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());

        module.updateInfo(title, description, updateCommand.orderIndex());
        savePort.save(module);
    }

    @Override
    public void removeModule(Id<CourseModule> moduleId, Id<User> teacherId) {
        if (!loadPort.isExistModule(moduleId)) {
            throw new ResourceNotFoundException(
                    CourseExceptionCode.MODULE_NOT_FOUND_EXCEPTION, CourseModule.class, moduleId);
        }

        if (!accessPort.isModuleOwner(teacherId, moduleId)) {
            throw new ResourceAccessException(
                    CourseExceptionCode.MODULE_ACCESS_EXCEPTION, CourseModule.class, teacherId, moduleId);
        }
        removePort.removeById(moduleId);
    }
}
