CREATE TABLE test_answers_options
(
    option_id      INT REFERENCES question_options (id) ON DELETE CASCADE,
    test_answer_id INT REFERENCES test_answers (id) ON DELETE CASCADE,
    PRIMARY KEY (option_id, test_answer_id)
);