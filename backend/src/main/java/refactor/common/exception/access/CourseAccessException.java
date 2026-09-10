package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.markers.Account;

//TODO сделать базовые абстрактные исключения в common и наследоваться от них в исключениях конкретных модулей
public class CourseAccessException extends AccessException {

    public CourseAccessException(Id<Course> courseId, Id<Account> requesterId) {
        super(courseId.value(), requesterId.value());
    }
}
