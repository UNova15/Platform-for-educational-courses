package org.platform.platformforeducationalcourses.repository.course;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findAllByTeacherId(long userId);

    Optional<Course> findByIdAndTeacherId(long userId, long courseId);

    List<Course> findCoursesByIdIn(Collection<Long> ids);

    Page<Course> findAll(Pageable page);

    Page<Course> findAllByTag(Pageable pageable, Tag tag);
}
