package course.adapter.out.persistance.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.markers.Account;
import refactor.course.implementation.domain.common.Description;
import refactor.course.implementation.domain.common.Title;
import refactor.course.implementation.domain.course.Course;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    public Course toDomain(CourseEntity entity) {
        Id<Course> id = Id.of(entity.id());
        Id<Account> teacherId = Id.of(entity.teacherId());
        Title title = Title.of(entity.title());
        Description description = Description.of(entity.description());

        return Course.restore(id, teacherId, title, description, entity.tag(), entity.createdAt());
    }

    public CourseEntity toEntity(Course course) {
        return new CourseEntity(
                course.id().value(),
                course.teacherId().value(),
                course.title().value(),
                course.description().value(),
                course.tag(),
                course.createdAt());
    }
}
