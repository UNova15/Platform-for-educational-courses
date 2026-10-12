package kira.course.application.service.core.module;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.module.create.ModuleCreateCommand;
import kira.course.application.port.in.module.create.ModuleCreateResult;
import kira.course.application.port.in.module.create.ModuleCreateUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.module.ModuleSavePort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ModuleCreateService implements ModuleCreateUseCase {
    private final CourseLoadPort courseLoadPort;
    private final ModuleSavePort savePort;

    @Override
    public ModuleCreateResult createModule(ModuleCreateCommand createCommand, Id<Course> courseId, Id<User> teacherId) {
        Course course = courseLoadPort
                .loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (!course.isOwnedBy(teacherId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, teacherId, courseId);
        }

        if (course.isArchived()) {
            throw new RuleConflictException(
                    "Cannot create module in archived course", CourseResourceName.COURSE, courseId);
        }

        var title = Title.of(createCommand.title());
        var description = Description.of(createCommand.description());
        CourseModule module = CourseModule.create(courseId, title, description, createCommand.orderIndex());

        CourseModule savedModule = savePort.save(module);

        return new ModuleCreateResult(
                savedModule.id().value(), savedModule.title().value());
    }
}
