package kira.course.application.service.core.course;

import common.domain.Id;
import kira.course.application.port.in.course.create.CourseCreateCommand;
import kira.course.application.port.in.course.create.CourseCreateResponse;
import kira.course.application.port.in.course.create.CourseCreateUseCase;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseCreateService implements CourseCreateUseCase {
    private final CourseSavePort savePort;

    @Override
    public CourseCreateResponse createCourse(CourseCreateCommand command, Id<User> userId) {
        var course =
                Course.create(userId, Title.of(command.title()), Description.of(command.description()), command.tag());
        var savedCourse = savePort.save(course);

        return new CourseCreateResponse(savedCourse.id().value(), command.title(), savedCourse.createdAt());
    }
}
