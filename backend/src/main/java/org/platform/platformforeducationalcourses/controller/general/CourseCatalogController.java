package org.platform.platformforeducationalcourses.controller.general;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.common.PageResponse;
import org.platform.platformforeducationalcourses.dto.course.CoursePage;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.service.CourseStructureManagementService;
import org.platform.platformforeducationalcourses.service.CourseStructureQueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseCatalogController {
    private final CourseStructureManagementService courseService;
    private final CourseStructureQueryService courseQueryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CoursePage> getCourses(Pageable pageable, @RequestParam(required = false) Tag tag) {
        return courseService.findPageOfCourse(pageable, tag);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseCatalogResponse> getCourseDetails(@PathVariable long courseId) {
        CourseCatalogResponse response = courseQueryService.findCourseForCatalog(courseId);
        return ResponseEntity.ok(response);
    }
}
