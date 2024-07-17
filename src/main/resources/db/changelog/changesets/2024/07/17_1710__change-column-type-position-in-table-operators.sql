--liquibase formatted sql

--changeset minouni:1
ALTER TABLE operators
ALTER COLUMN position TYPE VARCHAR(6);
