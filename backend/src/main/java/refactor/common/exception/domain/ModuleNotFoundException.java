package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.internal.module.CourseModule;
import refactor.course.domain.external.User;

public class ModuleNotFoundException extends NotFoundException {

    public ModuleNotFoundException(Id<CourseModule> moduleId, Id<User> requesterId) {
        super("Module with ID: %d for user with ID %d not found".formatted(moduleId.value(), requesterId.value()));
    }
}
