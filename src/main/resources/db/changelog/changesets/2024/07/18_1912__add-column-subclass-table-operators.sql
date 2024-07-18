--liquibase formatted sql

--changeset minouni:1
ALTER TABLE operators
ADD COLUMN subclass VARCHAR(25) NOT NULL;