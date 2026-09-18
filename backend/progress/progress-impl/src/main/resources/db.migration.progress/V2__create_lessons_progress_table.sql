CREATE TABLE progress.lessons_progress
(
    id           SERIAL PRIMARY KEY,
    user_id      INT,
    lesson_id    INT,
    completed_at TIMESTAMP
);