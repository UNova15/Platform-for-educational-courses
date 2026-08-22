package org.platform.platformforeducationalcourses.controller.general;

import lombok.AllArgsConstructor;
import refactor.course.domain.course.Tag;
import refactor.common.wrapper.CursorPageResponse;
import refactor.course.application.port.in.course.query.CourseCursorView;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import refactor.course.application.service.command.CourseCreateService;
import org.platform.platformforeducationalcourses.service.CourseStructureQueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseCatalogController {
    private final CourseCreateService courseService;
    private final CourseStructureQueryService courseQueryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CursorPageResponse<CourseCursorView> getCourses(Pageable pageable, @RequestParam(required = false) Tag tag) {
        return courseService.findPageOfCourse(pageable, tag);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseCatalogResponse> getCourseDetails(@PathVariable long courseId) {
        CourseCatalogResponse response = courseQueryService.findCourseForCatalog(courseId);
        return ResponseEntity.ok(response);
    }
}
