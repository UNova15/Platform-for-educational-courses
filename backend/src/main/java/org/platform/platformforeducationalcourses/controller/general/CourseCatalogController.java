package org.platform.platformforeducationalcourses.controller.general;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.PageResponse;
import org.platform.platformforeducationalcourses.dto.course.CoursePage;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.service.CourseQueryService;
import org.platform.platformforeducationalcourses.service.domain.CourseService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseCatalogController {
    private final CourseService courseService;
    private final CourseQueryService courseQueryService;

    @GetMapping
    public ResponseEntity<PageResponse<CoursePage>> getCourses(Pageable pageable, @RequestParam(required = false) Tag tag) {
        PageResponse<CoursePage> page = courseService.findPageOfCourse(pageable, tag);
        return ResponseEntity.ok(page);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseCatalogResponse> getCourseDetails(@PathVariable long courseId) {
        CourseCatalogResponse response = courseQueryService.getCourseForCatalog(courseId);
        return ResponseEntity.ok(response);
    }
}
