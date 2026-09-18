CREATE TABLE course.modules
(
    id          SERIAL PRIMARY KEY,
    course_id   INT REFERENCES course.courses (id),
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    order_index INT CHECK (order_index >= 0)
)