package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.internal.module.CourseModule;
import refactor.course.domain.external.User;

public class ModuleAccessException extends AccessException {

    public ModuleAccessException(Id<CourseModule> moduleId, Id<User> requesterId) {
        super(moduleId.value(), requesterId.value());
    }
}
