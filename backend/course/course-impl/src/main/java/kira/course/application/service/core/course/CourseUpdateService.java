package kira.course.application.service.core.course;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.course.update.CourseUpdateCommand;
import kira.course.application.port.in.course.update.CourseUpdateUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class CourseUpdateService implements CourseUpdateUseCase {
    private final CourseSavePort savePort;
    private final CourseLoadPort loadPort;

    @Override
    public void updateCourse(CourseUpdateCommand updateCommand, Id<Course> courseId, Id<User> userId) {
        Course course = loadPort.loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (!course.isOwnedBy(userId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, userId, courseId);
        }

        if (course.isArchived()) {
            throw new RuleConflictException("Cannot update archived course", CourseResourceName.COURSE, courseId);
        }

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());
        course.updateCourseInfo(title, description, updateCommand.tag());

        savePort.save(course);
    }
}
