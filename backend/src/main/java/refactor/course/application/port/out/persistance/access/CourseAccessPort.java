package refactor.course.application.port.out.persistance.access;

public interface CourseAccessPort {

    boolean canManageLesson(long requesterId, long lessonId);

    boolean canManageModule(long requesterId, long moduleId);

    boolean canManageCourse(long requesterId, long courseId);

    boolean canManageTest(long requesterId,long testId);
}
