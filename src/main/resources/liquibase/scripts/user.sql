-- liquibase formatted sql

-- changeset sergey_yakovlev:1
CREATE TABLE users
(
    id               INTEGER,
    first_name       VARCHAR,
    last_name        VARCHAR,
    email            VARCHAR,
    password         VARCHAR,
    phone            VARCHAR,
    reg_date         TIMESTAMPTZ,
    city             VARCHAR,
    image            VARCHAR,
    role             VARCHAR
);