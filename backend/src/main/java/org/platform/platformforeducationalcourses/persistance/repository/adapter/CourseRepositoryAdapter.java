package org.platform.platformforeducationalcourses.persistance.repository.adapter;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.course.Tag;
import org.platform.platformforeducationalcourses.domain.ports.persistance.CourseRepository;
import refactor.course.implementation.adapter.out.persistance.course.CourseEntity;
import org.platform.platformforeducationalcourses.persistance.repository.mapper.PersistCourseMapper;
import refactor.course.implementation.adapter.out.persistance.course.DataCourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CourseRepositoryAdapter implements CourseRepository {
    private final DataCourseRepository repository;
    private final PersistCourseMapper mapper;

    @Override
    public Course save(Course course) {
        CourseEntity entity = mapper.toEntity(course);
        CourseEntity savedCourse = repository.save(entity);
        return mapper.fromEntity(savedCourse);
    }

    @Override
    public Optional<Course> findById(long courseId) {
        Optional<CourseEntity> entity = repository.findById(courseId);
        return entity.map(mapper::fromEntity);
    }

    @Override
    public void delete(Course course) {
        CourseEntity entity = mapper.toEntity(course);
        repository.delete(entity);
    }

    @Override
    public List<Course> findAllByTeacherId(long teacherId) {
        List<CourseEntity> entity = repository.findAllByTeacherId(teacherId);
        return mapper.fromListEntity(entity);
    }

    @Override
    // TODO
    public Page<Course> findAll(Pageable pageable) {

        return null;
    }

    @Override
    public Page<Course> findAllByTag(Pageable pageable, Tag tag) {
        return null;
    }
}
