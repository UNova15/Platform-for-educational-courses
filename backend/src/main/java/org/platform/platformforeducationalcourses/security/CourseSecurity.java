package org.platform.platformforeducationalcourses.security;

import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.repository.EnrollmentRepository;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.platform.platformforeducationalcourses.repository.course.LessonRepository;
import org.platform.platformforeducationalcourses.repository.course.ModuleRepository;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseSecurity {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final TestRepository testRepository;

    public boolean canManagedCourse(long userId, long courseId) {
        return courseRepository.findByIdAndTeacherId(userId, courseId).isPresent();
    }

    public boolean canManagedModule(long userId, long courseId, long moduleId) {
        return moduleRepository.findModuleIfUserIsOwner(userId, courseId, moduleId)
                .isPresent();
    }

    public boolean canManagedLesson(long userId, long courseId, long moduleId, long lessonId) {
        return lessonRepository.findLessonIfUserIsOwner(lessonId, moduleId, courseId, userId)
                .isPresent();
    }

    public boolean canManagedTest(long userId, long courseId, long moduleId, long testId) {
        return testRepository.findTestIfUserIsOwner(userId, courseId, moduleId, testId)
                .isPresent();
    }

    public boolean canAccessCourse(long userId, long courseId) {
        return enrollmentRepository.findByCourseIdAndUserId(courseId, userId)
                .isPresent();
    }

    public boolean canAccessLesson(long userId, long courseId, long moduleId, long lessonId) {
        return enrollmentRepository.findEnrollmentForLessonIfUserIsStudying(userId, courseId, moduleId, lessonId)
                .isPresent();
    }

    public boolean canAccessTest(long userId, long courseId, long moduleId, long testId){
        return enrollmentRepository.findEnrollmentForTestIfUserIsStudying(userId,courseId,moduleId,testId)
                .isPresent();
    }
}
