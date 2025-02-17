CREATE DATABASE ingredient_test;

CREATE USER postgres WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE ingredient_test TO postgres;

CREATE DATABASE ingredient;

CREATE USER postgres WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

