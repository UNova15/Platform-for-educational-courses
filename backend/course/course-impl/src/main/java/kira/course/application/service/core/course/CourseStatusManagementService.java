package kira.course.application.service.core.course;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.course.status.ArchiveUseCase;
import kira.course.application.port.in.course.status.PublishCourseUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.application.port.out.persistance.lesson.LessonChangeStatusPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModuleChangeStatusPort;
import kira.course.application.port.out.persistance.test.TestChangeStatusPort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.shared.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class CourseStatusManagementService implements PublishCourseUseCase, ArchiveUseCase {
    private final CourseLoadPort loadPort;
    private final CourseSavePort savePort;

    private final ModuleChangeStatusPort moduleChangeStatusPort;
    private final LessonChangeStatusPort lessonChangeStatusPort;
    private final TestChangeStatusPort testChangeStatusPort;
    private final ModuleLoadPort moduleLoadPort;

    @Override
    @Transactional
    public void publish(Id<User> requesterId, Id<Course> courseId) {
        Course course = loadPort.loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (!moduleLoadPort.isExistsModulesInCourse(courseId)) {
            throw new RuleConflictException("Cannot publish empty course", CourseResourceName.COURSE, courseId);
        }

        if (!course.isOwnedBy(requesterId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, requesterId, courseId);
        }

        if (!course.canPublish()) {
            throw new RuleConflictException(
                            "Cannot publish course in current status", CourseResourceName.COURSE, courseId)
                    .withContext("status", course.status());
        }
        course.publish();
        savePort.save(course);

        moduleChangeStatusPort.updateAllStatusesByCourseId(courseId, Status.PUBLISHED);
        lessonChangeStatusPort.updateAllStatusesByCourseId(courseId, Status.PUBLISHED);
        testChangeStatusPort.updateAllStatusesInCourse(courseId, Status.PUBLISHED);
    }

    @Override
    public void archive(Id<User> requesterId, Id<Course> courseId) {
        Course course = loadPort.loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (!course.isOwnedBy(requesterId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, requesterId, courseId);
        }

        if (!course.canArchive()) {
            throw new RuleConflictException(
                            "Cannot archive course in current status", CourseResourceName.COURSE, courseId)
                    .withContext("status", course.status());
        }
        course.archive();
        savePort.save(course);
    }
}
