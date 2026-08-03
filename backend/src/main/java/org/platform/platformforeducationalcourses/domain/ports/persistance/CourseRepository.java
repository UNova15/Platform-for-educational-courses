package org.platform.platformforeducationalcourses.domain.ports.persistance;

import java.util.List;
import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepository {
    Course save(Course course);

    Optional<Course> findById(long courseId);

    void delete(Course course);

    List<Course> findAllByTeacherId(long teacherId);

    Page<Course> findAll(Pageable pageable);

    Page<Course> findAllByTag(Pageable pageable, Tag tag);
}
