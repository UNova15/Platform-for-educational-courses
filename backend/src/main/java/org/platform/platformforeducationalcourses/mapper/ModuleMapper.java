package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import refactor.course.implementation.application.port.in.module.create.ModuleCreateCommand;
import refactor.course.implementation.application.port.in.module.create.ModuleCreateResult;
import refactor.course.application.port.in.module.query.ModuleQueryResult;
import refactor.course.implementation.application.port.in.module.update.ModuleUpdateCommand;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.test.Test;
import org.platform.platformforeducationalcourses.dto.course.catalog.ModuleCatalogResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.lesson.StudentLessonFindResponse;
import org.platform.platformforeducationalcourses.dto.module.*;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.test.StudentTestFindResponse;

@Mapper(uses = {LessonMapper.class, TestMapper.class})
public interface ModuleMapper {

    ModuleCreateCommand toCreateModuleDto(long courseId, ModuleCreateRequest request);

    @Mapping(source = "id", target = "moduleId")
    ModuleCreateResult toCreateModuleResponse(CourseModule savedCourse);

    ModuleUpdateCommand toModuleUpdateDto(long courseId, long moduleId, ModuleUpdateRequest request);

    ModuleQueryResult toModuleFindResponse(CourseModule courseModule);

    StudentModuleFindResponse toStudentModuleFindResponse(
            CourseModule module, List<StudentLessonFindResponse> lessons, List<StudentTestFindResponse> tests);

    CourseModuleFindResponse toCourseModuleFindResponse(CourseModule module, List<Lesson> lessons, List<Test> tests);

    List<ModuleCatalogResponse> toListModuleCatalogResponse(List<CourseModule> modules);
}
