package kira.course.adapter.out.persistence.query.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import kira.course.application.port.in.query.catalog.CourseCursorView;
import kira.course.application.port.in.query.catalog.CursorCourseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CourseDynamicQueryRepository {
    private final JdbcClient jdbc;

    // в случае расширение количества условий изменить jdbc client на jooq
    public List<CourseCursorView> findCoursesByCursor(CursorCourseQuery query) {
        StringBuilder sql = new StringBuilder(
                "SELECT id,teacher_id,title,description,tag,createdAt FROM course.courses c WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();

        if (query.cursor() != null) {
            sql.append("AND :lastId < c.id ");
            params.put("lastId", query.cursor());
        }

        if (query.tag() != null) {
            sql.append("AND c.tag = :tag ");
            params.put("tag", query.tag().name());
        }

        // +1 - проверка на наличие следующих записей
        sql.append("ORDER BY c.id LIMIT :limit ");
        params.put("limit", query.limit() + 1);

        return jdbc.sql(sql.toString())
                .params(params)
                .query(CourseCursorView.class)
                .list();
    }
}
