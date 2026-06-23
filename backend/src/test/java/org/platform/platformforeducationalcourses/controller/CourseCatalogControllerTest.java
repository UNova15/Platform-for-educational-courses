package org.platform.platformforeducationalcourses.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.controller.general.CourseCatalogController;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.PageResponse;
import org.platform.platformforeducationalcourses.dto.course.CoursePage;
import org.platform.platformforeducationalcourses.dto.course.catalog.CourseCatalogResponse;
import org.platform.platformforeducationalcourses.service.CourseQueryService;
import org.platform.platformforeducationalcourses.service.domain.CourseService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseCatalogControllerTest {

    @Mock private CourseService courseService;
    @Mock private CourseQueryService courseQueryService;

    @InjectMocks private CourseCatalogController controller;

    @Test
    @SuppressWarnings("unchecked")
    void getCourses_ReturnsOk() {
        Pageable pageable = mock(Pageable.class);
        Tag tag = mock(Tag.class);

        PageResponse<CoursePage> mockPage = mock(PageResponse.class);
        when(courseService.findPageOfCourse(pageable, tag)).thenReturn(mockPage);

        ResponseEntity<PageResponse<CoursePage>> response = controller.getCourses(pageable, tag);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
    }

    @Test
    void getCourseDetails_ReturnsOk() {
        CourseCatalogResponse mockResponse = mock(CourseCatalogResponse.class);
        when(courseQueryService.getCourseForCatalog(1L)).thenReturn(mockResponse);

        ResponseEntity<CourseCatalogResponse> response = controller.getCourseDetails(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }
}