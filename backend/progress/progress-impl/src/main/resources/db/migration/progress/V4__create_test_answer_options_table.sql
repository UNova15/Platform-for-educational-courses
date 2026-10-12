CREATE TABLE progress.test_answers
(
    test_submission_id INT NOT NULL REFERENCES progress.test_submissions (id),
    option_id          INT NOT NULL,
    question_id        INT NOT NULL,
    PRIMARY KEY (test_submission_id, option_id)
);

-- Целенаправленная денормализация question_id. Позволяет избежать cross-join запросов к другому модулю
-- (для получения questionId по optionId) и сохраняет историческую целостность при изменениях теста