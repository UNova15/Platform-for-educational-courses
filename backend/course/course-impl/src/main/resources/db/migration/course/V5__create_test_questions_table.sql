CREATE TABLE course.test_questions
(
    id          SERIAL PRIMARY KEY,
    order_index INT          NOT NULL CHECK ( order_index >= 0 ) DEFAULT 0,
    test_id     INT          NOT NULL REFERENCES course.tests(id),
    question    VARCHAR(100) NOT NULL
)