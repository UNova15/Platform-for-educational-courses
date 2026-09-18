CREATE TABLE course.test_questions
(
    id          SERIAL PRIMARY KEY,
    order_index INT CHECK ( order_index >= 0 ),
    test_id     INT REFERENCES course.test (id),
    question    VARCHAR(100) NOT NULL
)