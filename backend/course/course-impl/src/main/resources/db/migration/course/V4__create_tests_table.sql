CREATE TABLE course.tests
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL ,
    order_index INT CHECK ( order_index >= 0 ),
    module_id   INT REFERENCES course.modules (id),
    description VARCHAR(100),
    status      VARCHAR(50) NOT NULL
);