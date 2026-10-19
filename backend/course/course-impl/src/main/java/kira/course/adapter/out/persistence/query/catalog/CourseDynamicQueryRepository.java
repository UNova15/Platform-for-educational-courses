package kira.course.adapter.out.persistence.query.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CourseDynamicQueryRepository {
    private final JdbcClient dbClient;

    // в случае расширение количества условий изменить jdbc client на jooq/criteria api
    public List<CourseCursorView> findCoursesByCursor(CursorCourseQuery query) {
        StringBuilder sql = new StringBuilder("""
            SELECT id,teacher_id,title,description,tag,created_at
            FROM course.courses c
            WHERE c.status NOT IN ('DRAFT', 'ARCHIVED')
            """);

        Map<String, Object> params = new HashMap<>();

        if (query.cursor() != null) {
            sql.append(" AND :lastId < c.id");
            params.put("lastId", query.cursor());
        }

        if (query.tag() != null) {
            sql.append(" AND c.tag = :tag");
            params.put("tag", query.tag().name());
        }

        // +1 - проверка на наличие следующих записей
        sql.append(" ORDER BY c.id LIMIT :limit");
        params.put("limit", query.limit() + 1);

        return dbClient.sql(sql.toString())
                .params(params)
                .query(CourseCursorView.class)
                .list();
    }
}
