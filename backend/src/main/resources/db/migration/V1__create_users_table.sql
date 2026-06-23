CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(100)       NOT NULL,
    role     VARCHAR(20)        NOT NULL
);