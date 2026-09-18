CREATE TABLE progress.test_answers
(
    id                 SERIAL PRIMARY KEY,
    test_submission_id INT REFERENCES progress.test_submissions (id),
    question_id        INT
);