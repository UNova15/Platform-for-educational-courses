package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.user.Account;

public class ModuleNotFoundException extends EntityNotFoundException {

    public ModuleNotFoundException(Id<CourseModule> moduleId, Id<Account> requesterId) {
        super("Module with ID: %d for user with ID %d not found".formatted(moduleId.value(), requesterId.value()));
    }

    public ModuleNotFoundException(Id<CourseModule> moduleId) {
        super("Module with ID: %d for user not found".formatted(moduleId.value()));
    }
}
