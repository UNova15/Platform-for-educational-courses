package org.platform.platformforeducationalcourses.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.creator.assembler.ModuleAssembler;
import org.platform.platformforeducationalcourses.domain.course.*;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.dto.CourseData;
import org.platform.platformforeducationalcourses.dto.course.StudentCourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.platform.platformforeducationalcourses.repository.course.LessonRepository;
import org.platform.platformforeducationalcourses.repository.course.ModuleRepository;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.platform.platformforeducationalcourses.service.domain.ProgressService;
import org.platform.platformforeducationalcourses.service.domain.TestSubmissionService;
import org.springframework.stereotype.Service;

/**
 * Сервис для операций получения данных из группы агрегатов курса
 */
@Service
@RequiredArgsConstructor
public class CourseQueryService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final TestRepository testRepository;

    private final CourseMapper courseMapper;
    private final ModuleAssembler moduleAssembler;

    private final ProgressService progressService;
    private final TestSubmissionService testSubmissionService;

    // TODO возможно стоит вынести в course assembler
    public CourseFindResponse getCourseForTeacher(long courseId) {
        CourseData courseData = getCourseStructure(courseId);

        List<CourseModuleFindResponse> mappedModules = moduleAssembler.createCourseModuleFindResponse(
                courseData.lessons(), courseData.tests(), courseData.modules());

        return courseMapper.toCourseFindResponse(courseData.course(), mappedModules);
    }

    public StudentCourseFindResponse getCourseForStudent(long userId, long courseId) {
        CourseData courseData = getCourseStructure(courseId);

        List<LessonProgress> lessonProgresses =
                progressService.findLessonProgressByLessonsIds(userId, courseData.lessons());
        List<TestSubmission> testSubmissions = testSubmissionService.findTestsSubmissions(userId, courseData.tests());

        List<StudentModuleFindResponse> mappedModules = moduleAssembler.createStudentModuleFindResponse(
                courseData.modules(), courseData.lessons(), lessonProgresses, courseData.tests(), testSubmissions);

        return courseMapper.toStudentCourseFindResponse(courseData.course(), mappedModules);
    }

    public CourseCatalogResponse getCourseForCatalog(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();

        List<CourseModule> modules = moduleRepository.findAllByCourseId(courseId);

        return courseMapper.toCourseCatalogResponse(course, modules);
    }

    private CourseData getCourseStructure(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();

        List<CourseModule> modules = moduleRepository.findAllByCourseId(courseId);
        List<Long> moduleIds = modules.stream().map(CourseModule::getId).toList();

        List<Lesson> lessons = lessonRepository.findAllByModuleIdIn(moduleIds);
        List<Test> tests = testRepository.findAllByModuleIdIn(moduleIds);

        return new CourseData(course, modules, lessons, tests);
    }
}
