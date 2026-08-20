package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import refactor.course.application.port.in.course.query.CourseCursorResult;
import refactor.course.application.port.in.course.query.CourseQueryResult;
import refactor.course.domain.course.Course;
import refactor.course.domain.module.CourseModule;
import org.platform.platformforeducationalcourses.dto.course.*;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.enrollment.CourseEnrolledFindResponse;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;

@Mapper(uses = {ModuleMapper.class})
public interface CourseMapper {

    List<CourseQueryResult> toCourseInfo(List<Course> course);

    List<CourseEnrolledFindResponse> toCourseEnrolledFindResponse(List<Course> courses);

    @Mapping(source = "modules", target = "modules")
    StudentCourseFindResponse toStudentCourseFindResponse(Course course, List<StudentModuleFindResponse> modules);

    @Mapping(source = "mappedModules", target = "modules")
    CourseFindResponse toCourseFindResponse(Course course, List<CourseModuleFindResponse> mappedModules);

    CourseCursorResult toCoursePage(Course course);

    @Mapping(source = "modules", target = "modules")
    CourseCatalogResponse toCourseCatalogResponse(Course course, List<CourseModule> modules);
}
