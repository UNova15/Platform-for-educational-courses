package refactor.common.exception.domain;

//TODO сделать общие исключения в модуле common и отедльные иерархии с нужными типами id в каждом модуле
public class CourseNotFoundException extends EntityNotFoundException {
    public CourseNotFoundException(long courseId, long requesterId) {
        super("Not found course with id: %s for user with id: %s".formatted(courseId, requesterId));
    }
}
