package kira.course.adapter.out.persistence.course;

import common.domain.entity.Id;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CourseMapper {

    public Course toDomain(CourseEntity entity) {
        Id<Course> id = Id.of(entity.id());
        Id<User> teacherId = Id.of(entity.teacherId());
        Title title = Title.of(entity.title());
        Description description = Description.of(entity.description());

        return Course.restore(id, teacherId, title, description, entity.tag(), entity.createdAt(), entity.status());
    }

    public CourseEntity toEntity(Course course) {
        Long id = course.id() == null ? null : course.id().value();
        return new CourseEntity(
                id,
                course.teacherId().value(),
                course.title().value(),
                course.description().value(),
                course.tag(),
                course.createdAt(),
                course.status());
    }
}
