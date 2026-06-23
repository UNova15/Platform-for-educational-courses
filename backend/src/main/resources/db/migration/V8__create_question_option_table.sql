CREATE TABLE question_options
(
    id          SERIAL PRIMARY KEY,
    question_id INT REFERENCES test_questions (id) ON DELETE CASCADE,
    option      VARCHAR(100) NOT NULL,
    is_correct  BOOLEAN
)