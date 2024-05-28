# --- !Ups
CREATE SCHEMA IF NOT EXISTS playwithscala;

CREATE TABLE playwithscala.users
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255),
    age  INT
);

# --- !Downs
DROP TABLE IF EXISTS playwithscala.users;
DROP SCHEMA IF EXISTS playwithscala;
