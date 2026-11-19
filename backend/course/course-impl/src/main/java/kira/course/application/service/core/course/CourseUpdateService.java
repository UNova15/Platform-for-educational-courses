package kira.course.application.service.core.course;

import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.course.application.port.in.course.update.CourseUpdateCommand;
import kira.course.application.port.in.course.update.CourseUpdateUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.domain.CourseAggregateType;
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
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.COURSE, courseId));

        if (!course.isOwnedBy(userId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.COURSE, userId, courseId);
        }

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());
        course.updateCourseInfo(title, description, updateCommand.tag());

        savePort.save(course);
    }
}
