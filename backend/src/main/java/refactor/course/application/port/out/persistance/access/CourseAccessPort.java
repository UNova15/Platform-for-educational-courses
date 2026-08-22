package refactor.course.application.port.out.persistance.access;

public interface CourseAccessPort {

    boolean isLessonOwner(long requesterId, long lessonId);

    boolean isModuleOwner(long requesterId, long moduleId);

    boolean isCourseOwner(long requesterId, long courseId);

    boolean isTestOwner(long requesterId, long testId);
}
