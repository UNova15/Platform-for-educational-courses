CREATE TABLE progress.test_answers_options
(
    option_id      INT,
    test_answer_id INT REFERENCES progress.test_answers (id),
    PRIMARY KEY (option_id, test_answer_id)
);