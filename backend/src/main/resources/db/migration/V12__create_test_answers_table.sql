CREATE TABLE test_answers
(
    id                 SERIAL PRIMARY KEY,
    test_submission_id INT REFERENCES test_submissions (id) ON DELETE CASCADE,
    question_id        INT REFERENCES test_questions (id) ON DELETE CASCADE
);