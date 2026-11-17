package kira.course.adapter.out.persistence.query.coursedetail;

import common.domain.entity.Id;
import java.sql.ResultSet;
import java.sql.SQLException;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.course.valueobject.CourseDetails;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper implements RowMapper<CourseDetails> {
    @Override
    public CourseDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        String status = rs.getString("status");
        long id = rs.getLong("id");

        CourseStatus courseStatus = CourseStatus.valueOf(status);
        Id<Course> courseId = Id.of(id);
        return CourseDetails.of(courseId, courseStatus);
    }
}
