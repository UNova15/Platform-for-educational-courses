CREATE TABLE test_answers
(
    id                 SERIAL PRIMARY KEY,
    test_submission_id INT REFERENCES test_submissions (id) ON DELETE CASCADE,
    question_id        INT REFERENCES test_questions (id) ON DELETE CASCADE,
    answer_id          INT REFERENCES question_options(id) ON DELETE CASCADE
);