CREATE TABLE test_questions
(
    id          SERIAL PRIMARY KEY,
    order_index INT CHECK ( order_index >= 0 ),
    test_id     INT REFERENCES test (id) ON DELETE CASCADE,
    question    VARCHAR(100) NOT NULL
)