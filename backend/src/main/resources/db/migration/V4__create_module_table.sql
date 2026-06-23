CREATE TABLE modules
(
    id          SERIAL PRIMARY KEY,
    course_id   INT REFERENCES courses (id) ON DELETE CASCADE,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    order_index INT CHECK (order_index >= 0)
)