--liquibase formatted sql

--changeset minouni:1
ALTER TABLE skills
ADD COLUMN sp_initial INTEGER NOT NULL;

ALTER TABLE skills
RENAME COLUMN description TO effect;
