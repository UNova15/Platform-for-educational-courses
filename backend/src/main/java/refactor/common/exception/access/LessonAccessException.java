package refactor.common.exception.access;

public class LessonAccessException extends AccessException {

    public LessonAccessException(long lessonId, long requesterId) {
        super(lessonId, requesterId);
    }
}
