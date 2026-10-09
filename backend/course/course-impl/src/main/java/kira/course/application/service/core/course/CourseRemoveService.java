package kira.course.application.service.core.course;

import common.domain.Id;
import common.domain.Result;
import kira.course.application.port.in.course.remove.CourseRemoveUseCase;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseRemovePort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseRemoveService implements CourseRemoveUseCase {
    private final CourseLoadPort courseLoadPort;
    private final CourseRemovePort courseRemovePort;

    @Override
    public Result<Void, RemoveError> removeCourse(Id<User> teacherId, Id<Course> courseId) {
        Optional<Course> loaded = courseLoadPort.loadById(courseId);

        if (loaded.isEmpty()) {
            return Result.failure(RemoveError.COURSE_NOT_FOUND);
        }
        var course = loaded.get();

        if (!course.isOwnedBy(teacherId)) {
            return Result.failure(RemoveError.ACCESS_DENIED);
        }

        if (!course.isDraft()) {
            return Result.failure(RemoveError.COURSE_IS_NOT_IN_DRAFT);
        }

        courseRemovePort.remove(course);
        return Result.success(null);
    }
}
