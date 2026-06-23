CREATE TABLE lessons_progress
(
    id              SERIAL PRIMARY KEY,
    user_id         INT REFERENCES users (id) ON DELETE CASCADE,
    lesson_id       INT REFERENCES lessons (id) ON DELETE CASCADE,
    completed_at TIMESTAMP
);