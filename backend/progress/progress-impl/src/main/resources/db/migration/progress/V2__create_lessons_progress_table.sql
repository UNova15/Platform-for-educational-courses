CREATE TABLE progress.lessons_progress
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT    NOT NULL,
    lesson_id    BIGINT    NOT NULL,
    completed_at TIMESTAMP NOT NULL
);