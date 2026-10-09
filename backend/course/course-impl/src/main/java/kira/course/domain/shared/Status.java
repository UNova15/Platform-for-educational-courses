package kira.course.domain.shared;

public enum Status {
    DRAFT,
    PUBLISHED;

    // реализация конечного автомата для проверки валидности перехода между статусами контента
    public boolean canTransitionTo(Status targetStatus) {
        return switch (this) {
            case DRAFT -> targetStatus == PUBLISHED;
            case PUBLISHED -> false;
        };
    }
}
