package kira.course.application.service.core.course;

import common.domain.entity.Id;
import common.application.exception.RuleConflictException;
import common.application.exception.ResourceAccessDeniedException;
import common.application.exception.ResourceNotFoundException;
import kira.course.domain.CourseAggregateType;
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
import kira.course.domain.shared.CourseContentStatus;
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
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.COURSE, courseId));

        if (!moduleLoadPort.isExistsModulesInCourse(courseId)) {
            throw new RuleConflictException("Cannot publish empty course", CourseAggregateType.COURSE, courseId);
        }

        if (!course.isOwnedBy(requesterId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.COURSE, requesterId, courseId);
        }

        course.publish();
        savePort.save(course);

        moduleChangeStatusPort.updateAllStatusesByCourseId(courseId, CourseContentStatus.PUBLISHED);
        lessonChangeStatusPort.updateAllStatusesByCourseId(courseId, CourseContentStatus.PUBLISHED);
        testChangeStatusPort.updateAllStatusesInCourse(courseId, CourseContentStatus.PUBLISHED);
    }

    @Override
    public void archive(Id<User> requesterId, Id<Course> courseId) {
        Course course = loadPort.loadById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseAggregateType.COURSE, courseId));

        if (!course.isOwnedBy(requesterId)) {
            throw new ResourceAccessDeniedException(CourseAggregateType.COURSE, requesterId, courseId);
        }

        course.archive();
        savePort.save(course);
    }
}
