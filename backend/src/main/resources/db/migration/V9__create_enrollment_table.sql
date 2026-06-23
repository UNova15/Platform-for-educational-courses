CREATE TABLE enrollments
(
    id         SERIAL PRIMARY KEY,
    user_id    INT REFERENCES users (id) ON DELETE CASCADE,
    course_id  INT REFERENCES courses (id) ON DELETE CASCADE,
    created_at TIMESTAMP
);