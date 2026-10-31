package kira.course.domain.shared;

public enum CourseContentStatus {
    DRAFT,
    PUBLISHED;

    // реализация конечного автомата для проверки валидности перехода между статусами контента
    public boolean canTransitionTo(CourseContentStatus targetStatus) {
        return switch (this) {
            case DRAFT -> targetStatus == PUBLISHED;
            case PUBLISHED -> false;
        };
    }
}
