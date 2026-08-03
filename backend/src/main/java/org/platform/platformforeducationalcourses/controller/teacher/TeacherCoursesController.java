package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.dto.course.*;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateRequest;
import org.platform.platformforeducationalcourses.dto.course.create.CourseCreateResponse;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import org.platform.platformforeducationalcourses.service.CourseStructureManagementService;
import org.platform.platformforeducationalcourses.service.CourseStructureQueryService;
import org.platform.platformforeducationalcourses.service.domain.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.user.adapter.out.security.model.SecurityUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("teacher/courses")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherCoursesController {
    private final CourseStructureQueryService courseQueryService;
    private final CourseStructureManagementService courseManagementService;
    private final CourseService courseService;

    // TODO перенести проверки авторизации в сервисный слой
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseCreateResponse createCourse(
            @AuthenticationPrincipal SecurityUser userPrincipal, @Valid @RequestBody CourseCreateRequest request) {

        return courseManagementService.createCourseWithContent(request, userPrincipal.getId());
    }

    @PutMapping("{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public void updateCourse(
            @PathVariable long courseId,
            @Valid @RequestBody CourseUpdateRequest request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        courseService.updateCourseInfo(request, userPrincipal.getId(), courseId);
    }

    @DeleteMapping("{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public void deleteCourse(@PathVariable long courseId, @AuthenticationPrincipal SecurityUser userPrincipal) {

        courseService.deleteCourse(userPrincipal.getId(), courseId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseInfo> getCourses(@AuthenticationPrincipal SecurityUser userPrincipal) {

        return courseService.findTeachersCoursesInfo(userPrincipal.getId());
    }

    @GetMapping("{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public CourseFindResponse getCourse(
            @PathVariable long courseId, @AuthenticationPrincipal SecurityUser userPrincipal) {

        return courseQueryService.findCourseForTeacher(courseId);
    }
}
