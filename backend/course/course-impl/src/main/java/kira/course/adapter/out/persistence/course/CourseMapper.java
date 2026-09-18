package kira.course.adapter.out.persistence.course;

import common.domain.Id;
import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    public Course toDomain(CourseEntity entity) {
        Id<Course> id = Id.of(entity.id());
        Id<User> teacherId = Id.of(entity.teacherId());
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
