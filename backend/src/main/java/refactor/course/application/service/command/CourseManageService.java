package refactor.course.application.service.command;

import lombok.AllArgsConstructor;
import refactor.course.application.port.in.course.command.remove.CourseRemoveUseCase;
import refactor.course.application.port.in.course.command.update.CourseUpdateCommand;
import refactor.course.application.port.in.course.command.update.CourseUpdateUseCase;
import refactor.course.application.port.out.persistance.course.CourseLoadPort;
import refactor.course.application.port.out.persistance.course.CourseRemovePort;
import refactor.course.application.port.out.persistance.course.CourseSavePort;
import refactor.course.domain.course.Course;
import refactor.common.exception.domain.CourseNotFoundException;
import org.springframework.stereotype.Service;
import refactor.course.domain.course.CourseDescription;
import refactor.course.domain.course.CourseTitle;

@Service
@AllArgsConstructor
public class CourseManageService implements CourseRemoveUseCase, CourseUpdateUseCase {
    private final CourseLoadPort courseLoadPort;
    private final CourseRemovePort courseRemovePort;
    private final CourseSavePort courseSavePort;

    @Override
    public void removeCourse(long teacherId, long courseId) {
        Course course =
                courseLoadPort.loadById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));

        course.verifyOwnership(teacherId);
        courseRemovePort.remove(course);
    }

    @Override
    public void updateCourse(CourseUpdateCommand updateCommand, long courseId,long userId) {
        Course course = courseLoadPort
                .loadById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId, userId));

        course.verifyOwnership(userId);

        var title = CourseTitle.of(updateCommand.title());
        var description = CourseDescription.of(updateCommand.description());

        course.updateCourseInfo(title, description, updateCommand.tag());
        courseSavePort.save(course);
    }
}
