CREATE TABLE course.question_options
(
    id          SERIAL PRIMARY KEY,
    question_id INT          NOT NULL REFERENCES course.test_questions (id),
    option      VARCHAR(100) NOT NULL,
    is_correct  BOOLEAN NOT NULL DEFAULT false
)