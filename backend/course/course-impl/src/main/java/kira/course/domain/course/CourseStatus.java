package kira.course.domain.course;

public enum CourseStatus {
    DRAFT,
    PUBLISHED,
    ARCHIVED;

    // реализация конечного автомата для проверки валидности перехода между статусами курса
    public boolean canTransitionTo(CourseStatus targetStatus) {
        return switch (this) {
            case DRAFT -> targetStatus == PUBLISHED;
            case PUBLISHED -> targetStatus == ARCHIVED;
            case ARCHIVED -> false;
        };
    }
}
