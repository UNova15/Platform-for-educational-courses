CREATE TABLE course.test
(
    id          SERIAL PRIMARY KEY,
    order_index INT CHECK ( order_index >= 0 ),
    module_id   INT REFERENCES course.modules (id),
    description VARCHAR(100)
);