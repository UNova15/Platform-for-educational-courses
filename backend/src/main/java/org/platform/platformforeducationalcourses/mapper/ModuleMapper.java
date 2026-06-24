package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.dto.course.catalog.ModuleCatalogResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.StudentLessonFindResponse;
import org.platform.platformforeducationalcourses.dto.module.*;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.test.StudentTestFindResponse;

@Mapper(uses = {LessonMapper.class, TestMapper.class})
public interface ModuleMapper {

    ModuleCreateDto toCreateModuleDto(long courseId, ModuleCreateRequest request);

    @Mapping(source = "id", target = "moduleId")
    ModuleCreateResponse toCreateModuleResponse(CourseModule savedCourse);

    ModuleUpdateDto toModuleUpdateDto(long courseId, long moduleId, ModuleUpdateRequest request);

    ModuleFindResponse toModuleFindResponse(CourseModule courseModule);

    StudentModuleFindResponse toStudentModuleFindResponse(
            CourseModule module, List<StudentLessonFindResponse> lessons, List<StudentTestFindResponse> tests);

    CourseModuleFindResponse toCourseModuleFindResponse(CourseModule module, List<Lesson> lessons, List<Test> tests);

    List<ModuleCatalogResponse> toListModuleCatalogResponse(List<CourseModule> modules);
}
