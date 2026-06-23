package org.platform.platformforeducationalcourses.service;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.progress.Enrollment;
import org.platform.platformforeducationalcourses.dto.enrollment.CourseEnrolledFindResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.repository.EnrollmentRepository;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    //TODO добавить исключение
    public void enrollToCourse(long userId, long courseId) {
        enrollmentRepository.findByCourseIdAndUserId(courseId, userId)
                .ifPresent(enrollment -> {
                    throw new IllegalArgumentException();
                });

        Enrollment enrollment = Enrollment.createNew(userId, courseId);
        enrollmentRepository.save(enrollment);
    }

    public List<CourseEnrolledFindResponse> getCoursesForStudent(long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByUserId(userId);

        List<Long> courseIds = enrollments.stream()
                .map(Enrollment::getCourseId)
                .toList();

        List<Course> courses = courseRepository.findCoursesByIdIn(courseIds);
        return courseMapper.toCourseEnrolledFindResponse(courses);
    }

}
