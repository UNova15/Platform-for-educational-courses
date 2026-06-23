package org.platform.platformforeducationalcourses.service.domain;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.creator.assembler.CourseAssembler;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.PageResponse;
import org.platform.platformforeducationalcourses.dto.course.CourseInfo;
import org.platform.platformforeducationalcourses.dto.course.CoursePage;
import org.platform.platformforeducationalcourses.dto.course.CourseUpdateDto;
import org.platform.platformforeducationalcourses.exception.CourseNotFoundException;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseAssembler courseAssembler;
    private final CourseMapper courseMapper;

    public void updateCourse(CourseUpdateDto courseUpdateRequest, long teacherId, long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));

        course.updateCourse(courseUpdateRequest);

        courseRepository.save(course);
    }

    public void deleteCourse(long teacherId, long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));

        courseRepository.delete(course);
    }

    //TODO добавить id курса и его поддоменов
    public List<CourseInfo> findTeachersCoursesInfo(long teacherId) {
        List<Course> courses = courseRepository.findAllByTeacherId(teacherId);

        return courses.stream()
                .map(courseMapper::toCourseGetResponse)
                .toList();
    }

    public PageResponse<CoursePage> findPageOfCourse(Pageable pageable, Tag tag) {
        Page<Course> page = tag == null
                ? courseRepository.findAll(pageable)
                : courseRepository.findAllByTag(pageable, tag);

        return courseAssembler.createPageResponseWithCoursePage(page);
    }
}
