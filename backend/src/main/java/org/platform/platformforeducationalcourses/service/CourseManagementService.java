package org.platform.platformforeducationalcourses.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.creator.factory.LessonFactory;
import org.platform.platformforeducationalcourses.creator.factory.ModuleFactory;
import org.platform.platformforeducationalcourses.creator.factory.TestFactory;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateResponse;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseCreateDto;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseModuleCreateDto;
import org.platform.platformforeducationalcourses.repository.course.CourseRepository;
import org.platform.platformforeducationalcourses.repository.course.LessonRepository;
import org.platform.platformforeducationalcourses.repository.course.ModuleRepository;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для выполнения операций взаимодействия с общей структурой курса
 */
@Service
@AllArgsConstructor
public class CourseManagementService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final TestRepository testRepository;

    private final ModuleFactory moduleFactory;
    private final LessonFactory lessonFactory;
    private final TestFactory testFactory;

    @Transactional
    public CourseCreateResponse createCourseWithContent(CourseCreateDto request, long userId) {
        long courseId = saveCourse(request, userId);

        Iterable<CourseModule> savedModules = saveModules(request.modules(), courseId);

        Map<Integer, Long> modulesOrder = getModulesOrder(savedModules);

        saveContent(request, modulesOrder);

        return new CourseCreateResponse(courseId, request.title(), LocalDateTime.now());
    }

    private long saveCourse(CourseCreateDto request, long userId) {
        Course course = Course.createNew(userId, request.title(), request.description(), request.tag());
        Course savedCourse = courseRepository.save(course);
        return savedCourse.getId();
    }

    private Iterable<CourseModule> saveModules(List<CourseModuleCreateDto> modules, long courseId) {
        List<CourseModule> parsedModules = moduleFactory.createFromCreateDto(modules, courseId);
        return moduleRepository.saveAll(parsedModules);
    }

    private Map<Integer, Long> getModulesOrder(Iterable<CourseModule> savedModules) {
        return StreamSupport.stream(savedModules.spliterator(), false)
                .collect(Collectors.toMap(CourseModule::getOrderIndex, CourseModule::getId));
    }

    private void saveContent(CourseCreateDto request, Map<Integer, Long> modulesOrder) {
        List<Lesson> lessonsToSave = new ArrayList<>();
        List<Test> testToSave = new ArrayList<>();

        for (var requestModule : request.modules()) {
            long moduleId = modulesOrder.get(requestModule.orderIndex());

            lessonsToSave.addAll(lessonFactory.createLessonFromCreateDto(requestModule.lessons(), moduleId));
            testToSave.addAll(testFactory.createTestsFromListCreateDto(requestModule.tests(), moduleId));
        }

        lessonRepository.saveAll(lessonsToSave);
        testRepository.saveAll(testToSave);
    }
}
