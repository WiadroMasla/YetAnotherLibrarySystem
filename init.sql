CREATE DATABASE library;
USE library;

CREATE TABLE user (
    id INT PRIMARY KEY NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO user (id, email) VALUES
(1, admin@gmail.com),
(2, person@wp.pl);