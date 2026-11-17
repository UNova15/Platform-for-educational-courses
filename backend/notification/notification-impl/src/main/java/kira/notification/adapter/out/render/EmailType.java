package kira.notification.adapter.out.render;

public enum EmailType {
    CONTENT_PUBLISHED("Опубликован новый материал курса", "classpath:mail/course_changed.html"),
    PROGRESS_REMOVED("Ваш прогресс по курсу был сброшен", "classpath:mail/progress_removed.html"),
    USER_ENROLLED("На курс записан новый пользователь", "classpath:mail/user_enrolled.html"),
    USER_COMPLETED_TEST("Пользователь прошел тест", "classpath:mail/completed_test.html");

    private final String path;
    private final String subject;

    EmailType(String subject, String path) {
        this.path = path;
        this.subject = subject;
    }

    public String path() {
        return path;
    }

    public String subject() {
        return subject;
    }
}
