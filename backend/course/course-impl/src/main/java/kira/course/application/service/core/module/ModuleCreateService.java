package kira.course.application.service.core.module;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.module.create.ModuleCreateCommand;
import kira.course.application.port.in.module.create.ModuleCreateResult;
import kira.course.application.port.in.module.create.ModuleCreateUseCase;
import kira.course.application.port.out.persistance.access.CourseAccessPort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
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
public class ModuleCreateService implements ModuleCreateUseCase {
    private final CourseLoadPort courseLoadPort;
    private final ModuleSavePort savePort;

    private final CourseAccessPort accessPort;
    private final CourseStatusPort checkStatusPort;

    @Override
    public Result<ModuleCreateResult, CreateError> createModule(
            ModuleCreateCommand createCommand, Id<Course> courseId, Id<User> teacherId) {

        if (!courseLoadPort.isExist(courseId)) {
            return Result.failure(CreateError.COURSE_NOT_FOUND);
        }

        if (!accessPort.isCourseOwner(teacherId, courseId)) {
            return Result.failure(CreateError.ACCESS_DENY);
        }

        if (checkStatusPort.isArchived(courseId)) {
            return Result.failure(CreateError.COURSE_ARCHIVED);
        }

        var title = Title.of(createCommand.title());
        var description = Description.of(createCommand.description());
        CourseModule module = CourseModule.create(courseId, title, description, createCommand.orderIndex());

        CourseModule savedModule = savePort.save(module);

        var response = new ModuleCreateResult(
                savedModule.id().value(), savedModule.title().value());

        return Result.success(response);
    }
}
