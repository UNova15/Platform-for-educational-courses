package kira.course.application.service.core.course;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.course.remove.CourseRemoveUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseRemovePort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class CourseRemoveService implements CourseRemoveUseCase {
    private final CourseLoadPort courseLoadPort;
    private final CourseRemovePort courseRemovePort;

    @Override
    public void removeCourse(Id<User> teacherId, Id<Course> courseId) {
        Course course = courseLoadPort
                .loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (!course.isOwnedBy(teacherId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, teacherId, courseId);
        }

        if (!course.isDraft()) {
            throw new RuleConflictException(
                    "Cannot remove course that status is not draft", CourseResourceName.COURSE, courseId);
        }

        courseRemovePort.remove(course);
    }
}
