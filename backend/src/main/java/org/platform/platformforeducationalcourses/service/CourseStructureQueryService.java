package org.platform.platformforeducationalcourses.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.creator.assembler.ModuleAssembler;
import org.platform.platformforeducationalcourses.domain.ports.persistance.CourseRepository;
import org.platform.platformforeducationalcourses.domain.ports.persistance.LessonRepository;
import org.platform.platformforeducationalcourses.domain.ports.persistance.ModuleRepository;
import org.platform.platformforeducationalcourses.domain.ports.persistance.TestRepository;
import refactor.progress.implementation.domain.lessonprogress.LessonProgress;
import refactor.progress.implementation.domain.testprogress.TestAttempt;
import org.platform.platformforeducationalcourses.dto.common.CourseData;
import org.platform.platformforeducationalcourses.dto.course.StudentCourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.service.domain.ProgressService;
import org.platform.platformforeducationalcourses.service.domain.TestSubmissionService;
import org.springframework.stereotype.Service;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.test.Test;

/**
 * Сервис для выполнения операций взаимодействия с общей структурой курса
 */
@Service
@AllArgsConstructor
public class CourseStructureQueryService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final TestRepository testRepository;
    private final ModuleAssembler moduleAssembler;

    private final ProgressService progressService;
    private final TestSubmissionService testSubmissionService;

    public CourseFindResponse findCourseForTeacher(long courseId) {
        CourseData courseData = findFullCourse(courseId);

        List<CourseModuleFindResponse> mappedModules = moduleAssembler.createCourseModuleFindResponse(
                courseData.lessons(), courseData.tests(), courseData.modules());

        return courseMapper.toCourseFindResponse(courseData.course(), mappedModules);
    }

    public StudentCourseFindResponse findCourseForStudent(long userId, long courseId) {
        CourseData courseData = findFullCourse(courseId);

        List<LessonProgress> lessonProgresses =
                progressService.findLessonProgressByLessonsIds(userId, courseData.lessons());
        List<TestAttempt> testAttempts = testSubmissionService.findTestsSubmissions(userId, courseData.tests());

        List<StudentModuleFindResponse> mappedModules = moduleAssembler.createStudentModuleFindResponse(
                courseData.modules(), courseData.lessons(), lessonProgresses, courseData.tests(), testAttempts);

        return courseMapper.toStudentCourseFindResponse(courseData.course(), mappedModules);
    }

    public CourseCatalogResponse findCourseForCatalog(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();

        List<CourseModule> modules = moduleRepository.findAllByCourseId(courseId);

        return courseMapper.toCourseCatalogResponse(course, modules);
    }

    // TODO exception
    private CourseData findFullCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();

        List<CourseModule> modules = moduleRepository.findAllByCourseId(courseId);
        List<Long> moduleIds = modules.stream().map(CourseModule::getId).toList();

        List<Lesson> lessons = lessonRepository.findAllByModuleIdIn(moduleIds);
        List<Test> tests = testRepository.findAllByModuleIdIn(moduleIds);

        return new CourseData(course, modules, lessons, tests);
    }
}
