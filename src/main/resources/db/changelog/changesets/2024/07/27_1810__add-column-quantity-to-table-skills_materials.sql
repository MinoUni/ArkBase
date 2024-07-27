--liquibase formatted sql

--changeset minouni:1
ALTER TABLE skills_materials
ADD COLUMN quantity INTEGER NOT NULL;