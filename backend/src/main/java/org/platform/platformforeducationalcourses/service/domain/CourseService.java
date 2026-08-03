package org.platform.platformforeducationalcourses.service.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.creator.assembler.CourseAssembler;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.domain.ports.persistance.CourseRepository;
import org.platform.platformforeducationalcourses.dto.common.PageResponse;
import org.platform.platformforeducationalcourses.dto.course.CourseInfo;
import org.platform.platformforeducationalcourses.dto.course.CoursePage;
import org.platform.platformforeducationalcourses.dto.course.CourseUpdateRequest;
import org.platform.platformforeducationalcourses.exception.CourseNotFoundException;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CourseAssembler courseAssembler;

    public void updateCourseInfo(CourseUpdateRequest courseUpdateRequest, long teacherId, long courseId) {
        Course course =
                courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));

        course.updateCourseInfo(
                courseUpdateRequest.title(), courseUpdateRequest.description(), courseUpdateRequest.tag());

        courseRepository.save(course);
    }

    public void deleteCourse(long teacherId, long courseId) {
        Course course =
                courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId, teacherId));
        courseRepository.delete(course);
    }

    public List<CourseInfo> findTeachersCoursesInfo(long teacherId) {
        List<Course> courses = courseRepository.findAllByTeacherId(teacherId);
        return courseMapper.toCourseInfo(courses);
    }

    // TODO нужно ли изолировать Pageble (протечка библиотеки) (скорей всего сделаю keyset пагинацию)
    public PageResponse<CoursePage> findPageOfCourse(Pageable pageable, Tag tag) {
        Page<Course> page =
                tag == null ? courseRepository.findAll(pageable) : courseRepository.findAllByTag(pageable, tag);

        return courseAssembler.createPageResponseWithCoursePage(page);
    }
}
