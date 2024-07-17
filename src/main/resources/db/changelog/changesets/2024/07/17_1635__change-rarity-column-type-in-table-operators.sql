--liguibase formatted sql

--changeset minouni:1
ALTER TABLE operators
ALTER COLUMN rarity TYPE VARCHAR(2);
