package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.module.CourseModule;
import refactor.course.domain.markers.Account;

public class ModuleAccessException extends AccessException {

    public ModuleAccessException(Id<CourseModule> moduleId, Id<Account> requesterId) {
        super(moduleId.value(), requesterId.value());
    }
}
