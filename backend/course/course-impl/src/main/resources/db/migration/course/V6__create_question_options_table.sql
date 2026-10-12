CREATE TABLE course.question_options
(
    id          SERIAL PRIMARY KEY,
    question_id INT,
    option      VARCHAR(100) NOT NULL,
    is_correct  BOOLEAN
)