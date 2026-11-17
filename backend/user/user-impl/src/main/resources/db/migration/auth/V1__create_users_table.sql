CREATE TABLE account.users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL,
    email    VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(100)       NOT NULL
);