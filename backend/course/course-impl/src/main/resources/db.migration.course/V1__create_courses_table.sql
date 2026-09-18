CREATE TABLE course.courses
(
    id          SERIAL PRIMARY KEY,
    teacher_id  INT,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    tag         VARCHAR(50),
    created_at  TIMESTAMP    NOT NULL
)