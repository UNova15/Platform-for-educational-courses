package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.domain.user.SecurityUser;
import org.platform.platformforeducationalcourses.dto.course.*;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateRequest;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateResponse;
import org.platform.platformforeducationalcourses.dto.course.createdto.CourseCreateDto;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import org.platform.platformforeducationalcourses.mapper.CourseMapper;
import org.platform.platformforeducationalcourses.service.CourseManagementService;
import org.platform.platformforeducationalcourses.service.domain.CourseService;
import org.platform.platformforeducationalcourses.service.CourseQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("teacher/courses")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherCoursesController {
    private final CourseQueryService courseQueryService;
    private final CourseManagementService courseManagementService;
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    //TODO перенести проверки авторизации в сервисный слой
    @PostMapping
    public ResponseEntity<CourseCreateResponse> createCourse(@AuthenticationPrincipal SecurityUser userPrincipal,
                                                             @Valid @RequestBody CourseCreateRequest request) {
        CourseCreateDto courseDto = courseMapper.toCourseDto(request);
        CourseCreateResponse response = courseManagementService.createCourseWithContent(courseDto, userPrincipal.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{courseId}")
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public ResponseEntity<Void> updateCourse(@PathVariable long courseId,
                                             @Valid @RequestBody CourseUpdateRequest request,
                                             @AuthenticationPrincipal SecurityUser userPrincipal) {
        CourseUpdateDto courseUpdateDto = courseMapper.toCourseUpdateDto(request, userPrincipal.getId(), courseId);
        courseService.updateCourse(courseUpdateDto, userPrincipal.getId(), courseId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("{courseId}")
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public ResponseEntity<Void> deleteCourse(@PathVariable long courseId,
                                             @AuthenticationPrincipal SecurityUser userPrincipal) {

        courseService.deleteCourse(userPrincipal.getId(), courseId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<CourseInfo>> getCourses(@AuthenticationPrincipal SecurityUser userPrincipal) {
        List<CourseInfo> courses = courseService.findTeachersCoursesInfo(userPrincipal.getId());

        return ResponseEntity
                .ok(courses);
    }

    @GetMapping("{courseId}")
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public ResponseEntity<CourseFindResponse> getCourse(@PathVariable long courseId,
                                                        @AuthenticationPrincipal SecurityUser userPrincipal) {
        CourseFindResponse response = courseQueryService.getCourseForTeacher(courseId);

        return ResponseEntity.ok(response);
    }
}
