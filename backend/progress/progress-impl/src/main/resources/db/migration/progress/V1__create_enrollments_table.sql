CREATE TABLE progress.enrollments
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT,
    course_id  BIGINT,
    created_at TIMESTAMP,
    UNIQUE (user_id, course_id)
);