--liquibase formatted sql

--changeset minouni:1
CREATE TABLE materials (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL UNIQUE,
  rarity VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  CONSTRAINT pk_materials PRIMARY KEY (id)
);