package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.dto.course.*;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateRequest;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseCreateDto;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseModuleFindResponse;
import org.platform.platformforeducationalcourses.dto.enrollment.CourseEnrolledFindResponse;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;

@Mapper(uses = {ModuleMapper.class})
public interface CourseMapper {
    CourseCreateDto toCourseDto(CourseCreateRequest request);

    CourseUpdateDto toCourseUpdateDto(CourseUpdateRequest courseUpdateRequest, long userId, long courseId);

    CourseInfo toCourseGetResponse(Course course);

    List<CourseEnrolledFindResponse> toCourseEnrolledFindResponse(List<Course> courses);

    @Mapping(source = "modules", target = "modules")
    StudentCourseFindResponse toStudentCourseFindResponse(Course course, List<StudentModuleFindResponse> modules);

    @Mapping(source = "mappedModules", target = "modules")
    CourseFindResponse toCourseFindResponse(Course course, List<CourseModuleFindResponse> mappedModules);

    CoursePage toCoursePage(Course course);

    @Mapping(source = "modules", target = "modules")
    CourseCatalogResponse toCourseCatalogResponse(Course course, List<CourseModule> modules);
}
