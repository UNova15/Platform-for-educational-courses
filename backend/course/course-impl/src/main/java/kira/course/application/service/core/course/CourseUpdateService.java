package kira.course.application.service.core.course;

import common.domain.Id;
import common.domain.Result;
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

import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseUpdateService implements CourseUpdateUseCase {
    private final CourseSavePort savePort;
    private final CourseLoadPort loadPort;

    @Override
    public Result<Void, UpdateError> updateCourse(
            CourseUpdateCommand updateCommand, Id<Course> courseId, Id<User> userId) {
        Optional<Course> loaded = loadPort.loadById(courseId);

        if (loaded.isEmpty()) {
            return Result.failure(UpdateError.COURSE_NOT_FOUND);
        }
        var course = loaded.get();

        if (!course.isOwnedBy(userId)) {
            return Result.failure(UpdateError.ACCESS_DENIED);
        }

        if (course.isArchived()) {
            return Result.failure(UpdateError.COURSE_IS_ARCHIVED);
        }

        var title = Title.of(updateCommand.title());
        var description = Description.of(updateCommand.description());
        course.updateCourseInfo(title, description, updateCommand.tag());

        savePort.save(course);
        return Result.success(null);
    }
}
