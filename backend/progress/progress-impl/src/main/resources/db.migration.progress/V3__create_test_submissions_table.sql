CREATE TABLE progress.test_submissions
(
    id           SERIAL PRIMARY KEY,
    user_id      INT,
    test_id      INT,
    started_at   TIMESTAMP,
    completed_at TIMESTAMP,
    score        INT CHECK (score >= 0)
);
