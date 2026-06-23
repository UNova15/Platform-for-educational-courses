CREATE TABLE test_submissions
(
    id           SERIAL PRIMARY KEY,
    user_id      INT REFERENCES users (id) ON DELETE CASCADE,
    test_id      INT REFERENCES test (id) ON DELETE CASCADE,
    started_at   TIMESTAMP,
    completed_at TIMESTAMP,
    score        INT CHECK (score >= 0)
);