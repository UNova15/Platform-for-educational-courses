CREATE TABLE test
(
    id          SERIAL PRIMARY KEY,
    order_index INT CHECK ( order_index >= 0 ),
    module_id   INT REFERENCES modules (id) ON DELETE CASCADE,
    description VARCHAR(100)
);