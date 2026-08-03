package org.platform.platformforeducationalcourses.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.*;
import org.platform.platformforeducationalcourses.domain.ports.persistance.CourseRepository;
import org.platform.platformforeducationalcourses.domain.ports.persistance.LessonRepository;
import org.platform.platformforeducationalcourses.domain.ports.persistance.ModuleRepository;
import org.platform.platformforeducationalcourses.domain.ports.persistance.TestRepository;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateRequest;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateResponse;
import org.platform.platformforeducationalcourses.web.WebLessonMapper;
import org.platform.platformforeducationalcourses.web.WebModuleMapper;
import org.platform.platformforeducationalcourses.web.WebTestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseStructureManagementService {
    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final TestRepository testRepository;

    private final WebModuleMapper moduleMapper;
    private final WebLessonMapper lessonMapper;
    private final WebTestMapper testMapper;

    @Transactional
    public CourseCreateResponse createCourseWithContent(CourseCreateRequest request, long userId) {
        Course course = Course.createNew(userId, request.title(), request.description(), request.tag());
        Course savedCourse = courseRepository.save(course);

        if (request.modules() == null || request.modules().isEmpty()) {
            return new CourseCreateResponse(savedCourse.getId(), request.title(), savedCourse.getCreatedAt());
        }

        List<CourseModule> parsedModules = moduleMapper.fromRequest(request.modules(), savedCourse.getId());
        List<CourseModule> savedModules = moduleRepository.save(parsedModules);

        // TODO сделать util метод для такого маппинга
        Map<Integer, Long> orderedModules =
                savedModules.stream().collect(Collectors.toMap(CourseModule::getOrderIndex, CourseModule::getId));

        List<Lesson> lessonsToSave = request.modules().stream()
                .map(module ->
                        lessonMapper.fromListCreateRequest(module.lessons(), orderedModules.get(module.orderIndex())))
                .flatMap(Collection::stream)
                .toList();

        List<Test> testToSave = request.modules().stream()
                .map(module ->
                        testMapper.fromListCreateRequest(module.tests(), orderedModules.get(module.orderIndex())))
                .flatMap(Collection::stream)
                .toList();

        lessonRepository.saveAll(lessonsToSave);
        testRepository.save(testToSave);

        return new CourseCreateResponse(savedCourse.getId(), request.title(), savedCourse.getCreatedAt());
    }
}
