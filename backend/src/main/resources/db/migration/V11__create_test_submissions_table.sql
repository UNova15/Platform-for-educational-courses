CREATE TABLE test_submissions
(
    id           SERIAL PRIMARY KEY,
    user_id      INT REFERENCES users (id) ON DELETE CASCADE,
    test_id      INT REFERENCES test (id) ON DELETE CASCADE,
    started_at   TIMESTAMP,
    completed_at TIMESTAMP,
    -- намеренная денормализация бд
    score        INT CHECK (score >= 0)
);
-- Данное поле позволит не делать огромное количество JOIN'ов и подобных операция для вычисления score при каждом обращении