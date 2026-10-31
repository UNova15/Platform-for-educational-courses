package kira.course.adapter.out.persistence.query.catalog;

import common.adapter.CursorFactory;
import common.adapter.CursorResponse;
import common.domain.entity.Id;
import java.util.List;
import java.util.Optional;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CourseInfo;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import kira.course.application.port.out.persistance.query.CourseCatalogQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.shared.CourseContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class CourseCatalogQueryAdapter implements CourseCatalogQueryPort {
    private final CourseDynamicQueryRepository dynamicQueryRepository;
    private final CourseCatalogQueryRepository repository;

    @Override
    public CursorResponse<CourseCursorView, Long> findCoursesByCursor(CursorCourseQuery query) {
        List<CourseCursorView> view = dynamicQueryRepository.findPublishedCoursesByCursor(query);
        // вынести сборку в application слой
        return CursorFactory.createFrom(view, CourseCursorView::id, query.limit());
    }

    @Override
    public Optional<CourseInfo> findCourseByIdAndStatus(
            Id<Course> courseId, CourseStatus courseStatus, CourseContentStatus moduleStatus) {
        Optional<CourseInfo> course = repository.findCourseByIdAndStatus(courseId.value(), courseStatus.name());

        course.ifPresent(value -> {
            List<CourseInfo.Module> modules =
                    repository.findModulesByCourseIdAndModuleStatus(courseId.value(), moduleStatus.name());
            value.setModules(modules);
        });
        return course;
    }
}
