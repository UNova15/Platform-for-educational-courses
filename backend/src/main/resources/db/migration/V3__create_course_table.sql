CREATE TABLE courses
(
    id          SERIAL PRIMARY KEY,
    teacher_id  INT REFERENCES users (id) ON DELETE CASCADE,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    tag         VARCHAR(50),
    created_at  TIMESTAMP    NOT NULL
)
--TODO сделать check на роль TEACHER и добавить enum для хранения ролей и прочих типов