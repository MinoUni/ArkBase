--liquibase formatted sql

--changeset minouni:1
ALTER TABLE materials
ALTER COLUMN name TYPE VARCHAR(40),
ALTER COLUMN rarity TYPE VARCHAR(2),
ALTER COLUMN description TYPE TEXT;