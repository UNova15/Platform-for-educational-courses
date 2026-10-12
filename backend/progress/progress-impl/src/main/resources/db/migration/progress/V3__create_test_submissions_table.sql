CREATE TABLE progress.test_submissions
(
    id           SERIAL PRIMARY KEY,
    user_id      INT       NOT NULL,
    test_id      INT       NOT NULL,
    started_at   TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    score        INT CHECK (score >= 0)
);
