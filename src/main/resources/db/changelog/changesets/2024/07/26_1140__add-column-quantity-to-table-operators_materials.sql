--liquibase formatted sql

--changeset minouni:1
ALTER TABLE operators_materials
ADD COLUMN quantity INTEGER NOT NULL;