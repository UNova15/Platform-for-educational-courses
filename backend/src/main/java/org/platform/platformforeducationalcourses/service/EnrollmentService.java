package org.platform.platformforeducationalcourses.service;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.course.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.progress.Enrollment;
import org.platform.platformforeducationalcourses.dto.enrollment.CourseEnrolledFindResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import refactor.course.adapter.out.persistance.course.DataCourseRepository;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataEnrollmentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnrollmentService {
    private final DataEnrollmentRepository enrollmentRepository;
    private final DataCourseRepository courseRepository;
    private final CourseMapper courseMapper;

    // TODO добавить исключение
    public void enrollToCourse(long userId, long courseId) {
        enrollmentRepository.findByCourseIdAndUserId(courseId, userId).ifPresent(enrollment -> {
            throw new IllegalArgumentException();
        });

        Enrollment enrollment = Enrollment.createNew(userId, courseId);
        enrollmentRepository.save(enrollment);
    }

    public List<CourseEnrolledFindResponse> getCoursesForStudent(long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByUserId(userId);

        List<Long> courseIds = enrollments.stream().map(Enrollment::getCourseId).toList();

        List<Course> courses = courseRepository.findCoursesByIdIn(courseIds);
        return courseMapper.toCourseEnrolledFindResponse(courses);
    }
}
