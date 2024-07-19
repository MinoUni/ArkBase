--liquibase formatted sql

--changeset minouni:1
ALTER TABLE skills
RENAME recovery_type TO charge_type;

ALTER TABLE skills
ALTER COLUMN description TYPE TEXT;