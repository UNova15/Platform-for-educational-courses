package org.platform.platformforeducationalcourses.persistance.repository.provader;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import refactor.course.domain.course.Tag;
import org.platform.platformforeducationalcourses.persistance.entity.course.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DataCourseRepository extends CrudRepository<CourseEntity, Long> {
    List<CourseEntity> findAllByTeacherId(long userId);

    Optional<CourseEntity> findByIdAndTeacherId(long userId, long courseId);

    List<CourseEntity> findCoursesByIdIn(Collection<Long> ids);

    Page<CourseEntity> findAll(Pageable page);

    Page<CourseEntity> findAllByTag(Pageable pageable, Tag tag);
}
