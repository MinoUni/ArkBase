--liquibase formatted sql

--changeset minouni:1
ALTER TABLE operators
ALTER COLUMN code_name TYPE VARCHAR(50),
ALTER COLUMN archetype TYPE VARCHAR(10),
ALTER COLUMN attack_type TYPE VARCHAR(15);