package org.platform.platformforeducationalcourses.controller.teacher;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import refactor.course.application.port.in.course.command.create.CourseCreateCommand;
import refactor.course.application.port.in.course.command.create.CourseCreateResult;
import org.platform.platformforeducationalcourses.dto.course.find.CourseFindResponse;
import refactor.course.application.port.in.course.query.OwnedCoursesListView;
import refactor.course.application.port.in.course.command.update.CourseUpdateCommand;
import refactor.course.application.service.command.CourseCreateService;
import org.platform.platformforeducationalcourses.service.CourseStructureQueryService;
import refactor.course.application.service.command.CourseManageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("teacher/courses")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherCoursesController {
    private final CourseStructureQueryService courseQueryService;
    private final CourseCreateService courseManagementService;
    private final CourseManageService courseManageService;

    // TODO перенести проверки авторизации в сервисный слой
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseCreateResult createCourse(
            @AuthenticationPrincipal SecurityUser userPrincipal, @Valid @RequestBody CourseCreateCommand request) {

        return courseManagementService.createCourseWithContent(request, userPrincipal.getId());
    }

    @PutMapping("{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public void updateCourse(
            @PathVariable long courseId,
            @Valid @RequestBody CourseUpdateCommand request,
            @AuthenticationPrincipal SecurityUser userPrincipal) {

        courseManageService.updateCourseMetadata(request, userPrincipal.getId(), courseId);
    }

    @DeleteMapping("{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public void deleteCourse(@PathVariable long courseId, @AuthenticationPrincipal SecurityUser userPrincipal) {

        courseManageService.removeCourse(userPrincipal.getId(), courseId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OwnedCoursesListView> getCourses(@AuthenticationPrincipal SecurityUser userPrincipal) {

        return courseManageService.findTeachersCoursesInfo(userPrincipal.getId());
    }

    @GetMapping("{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@courseSecurity.canManagedCourse(#userPrincipal.id,#courseId)")
    public CourseFindResponse getCourse(
            @PathVariable long courseId, @AuthenticationPrincipal SecurityUser userPrincipal) {

        return courseQueryService.findCourseForTeacher(courseId);
    }
}
