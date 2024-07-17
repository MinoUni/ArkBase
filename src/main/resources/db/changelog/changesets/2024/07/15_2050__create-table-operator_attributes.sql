--liquibase formatted sql

--changeset minouni:1
CREATE TABLE operator_attributes (
  operator_id INTEGER NOT NULL,
  hp INTEGER NOT NULL,
  atk INTEGER NOT NULL,
  def INTEGER NOT NULL,
  res INTEGER NOT NULL,
  block INTEGER NOT NULL,
  deployment_cost INTEGER NOT NULL,
  redeployment_time VARCHAR(255) NOT NULL,
  aspd VARCHAR(255) NOT NULL,
  CONSTRAINT pk_operator_attributes PRIMARY KEY (operator_id)
);

ALTER TABLE operator_attributes
ADD CONSTRAINT FK_OPERATOR_ATTRIBUTES_ON_OPERATOR FOREIGN KEY (operator_id) REFERENCES operators (id);