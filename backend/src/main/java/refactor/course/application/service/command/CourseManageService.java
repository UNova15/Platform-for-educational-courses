package refactor.course.application.service.command;

import lombok.AllArgsConstructor;
import refactor.course.application.port.in.course.command.remove.CourseRemoveUseCase;
import refactor.course.application.port.in.course.command.remove.CourseRemoveCommand;
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
    public void removeCourse(CourseRemoveCommand removeCommand) {
        Course course = courseLoadPort
                .loadById(removeCommand.courseId())
                .orElseThrow(() -> new CourseNotFoundException(removeCommand.courseId(), removeCommand.teacherId()));

        course.verifyOwnership(removeCommand.teacherId());
        courseRemovePort.remove(course);
    }

    @Override
    public void updateCourse(CourseUpdateCommand updateCommand) {
        Course course = courseLoadPort
                .loadById(updateCommand.courseId())
                .orElseThrow(() -> new CourseNotFoundException(updateCommand.courseId(), updateCommand.teacherId()));

        course.verifyOwnership(updateCommand.teacherId());

        var title = CourseTitle.of(updateCommand.title());
        var description = CourseDescription.of(updateCommand.description());

        course.updateCourseInfo(title, description, updateCommand.tag());
        courseSavePort.save(course);
    }
}
