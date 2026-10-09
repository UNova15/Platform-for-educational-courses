package kira.course.application.service.core.course;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import common.exception.named.ResourceAccessDeniedException;
import common.exception.named.ResourceNotFoundException;
import kira.course.application.exceptions.CourseResourceName;
import kira.course.application.port.in.course.status.ArchiveCourseUseCase;
import kira.course.application.port.in.course.status.PublishCourseUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.application.port.out.persistance.lesson.LessonPublishPort;
import kira.course.application.port.out.persistance.module.ModuleLoadPort;
import kira.course.application.port.out.persistance.module.ModulePublishPort;
import kira.course.application.port.out.persistance.test.TestPublishPort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseStatusManagementService implements PublishCourseUseCase, ArchiveCourseUseCase {
    private final CourseLoadPort loadPort;
    private final CourseSavePort savePort;

    private final ModulePublishPort modulePublishPort;
    private final LessonPublishPort lessonPublishPort;
    private final TestPublishPort testPublishPort;
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

        modulePublishPort.publishAllByCourseId(courseId);
        lessonPublishPort.publishAllInCourse(courseId);
        testPublishPort.publishAllInCourse(courseId);
    }

    @Override
    public void archive(Id<User> requesterId, Id<Course> courseId) {
        Course course = loadPort.loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseResourceName.COURSE, courseId));

        if (!course.isOwnedBy(requesterId)) {
            throw new ResourceAccessDeniedException(CourseResourceName.COURSE, requesterId, courseId);
        }

        if (!course.canArchive()) {
            throw new RuleConflictException("Cannot archive course", CourseResourceName.COURSE, courseId);
        }
        course.archive();
        savePort.save(course);
    }
}
