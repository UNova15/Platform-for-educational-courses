package refactor.course.application.service;

import lombok.AllArgsConstructor;
import refactor.common.domain.Id;
import refactor.course.application.port.in.course.remove.CourseRemoveUseCase;
import refactor.course.application.port.in.course.update.CourseUpdateCommand;
import refactor.course.application.port.in.course.update.CourseUpdateUseCase;
import refactor.course.application.port.out.persistance.course.CourseLoadPort;
import refactor.course.application.port.out.persistance.course.CourseRemovePort;
import refactor.course.application.port.out.persistance.course.CourseSavePort;
import refactor.course.domain.common.Description;
import refactor.course.domain.common.Title;
import refactor.course.domain.course.Course;
import refactor.common.exception.domain.CourseNotFoundException;
import org.springframework.stereotype.Service;
import refactor.course.domain.markers.Account;

@Service
@AllArgsConstructor
public class CourseManageService implements CourseRemoveUseCase, CourseUpdateUseCase {
    private final CourseLoadPort courseLoadPort;
    private final CourseRemovePort courseRemovePort;
    private final CourseSavePort courseSavePort;

    @Override
    public void removeCourse(Id<Account> teacherId, Id<Course> courseId) {
        Course course =
                courseLoadPort.loadById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));

        course.verifyOwnership(teacherId);
        courseRemovePort.remove(course);
    }

    @Override
    public void updateCourse(CourseUpdateCommand updateCommand, Id<Course> courseId, Id<Account> userId) {
        Course course =
                courseLoadPort.loadById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId, userId));

        course.verifyOwnership(userId);

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());

        course.updateCourseInfo(title, description, updateCommand.tag());
        courseSavePort.save(course);
    }
}
