CREATE TABLE lessons
(
    id          SERIAL PRIMARY KEY,
    module_id   INT REFERENCES modules (id) ON DELETE CASCADE,
    title       VARCHAR(100) NOT NULL,
    type        VARCHAR(30) NOT NULL,
    content     TEXT         NOT NULL,
    order_index INT CHECK (order_index >= 0),
    mandatory   BOOLEAN
)