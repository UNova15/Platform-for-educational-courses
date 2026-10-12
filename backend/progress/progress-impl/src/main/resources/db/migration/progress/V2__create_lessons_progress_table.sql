CREATE TABLE progress.lessons_progress
(
    user_id      INT,
    lesson_id    INT,
    completed_at TIMESTAMP,
    PRIMARY KEY (user_id, lesson_id)
);