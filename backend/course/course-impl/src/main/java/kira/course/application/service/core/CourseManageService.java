package kira.course.application.service.core;

import common.domain.Id;
import common.exception.ResourceNotFoundException;
import kira.course.application.exceptions.CourseExceptionCode;
import kira.course.application.port.in.course.remove.CourseRemoveUseCase;
import kira.course.application.port.in.course.update.CourseUpdateCommand;
import kira.course.application.port.in.course.update.CourseUpdateUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseRemovePort;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseManageService implements CourseRemoveUseCase, CourseUpdateUseCase {
    private final CourseLoadPort courseLoadPort;
    private final CourseRemovePort courseRemovePort;
    private final CourseSavePort courseSavePort;

    @Override
    public void removeCourse(Id<User> teacherId, Id<Course> courseId) {
        Course course = courseLoadPort
                .loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.COURSE_NOT_FOUND_EXCEPTION, Course.class, courseId));

        course.verifyOwnership(teacherId);
        courseRemovePort.remove(course);
    }

    @Override
    public void updateCourse(CourseUpdateCommand updateCommand, Id<Course> courseId, Id<User> userId) {
        Course course = courseLoadPort
                .loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CourseExceptionCode.COURSE_NOT_FOUND_EXCEPTION, Course.class, courseId));

        course.verifyOwnership(userId);

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());

        course.updateCourseInfo(title, description, updateCommand.tag());
        courseSavePort.save(course);
    }
}
