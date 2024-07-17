--liquibase formatted sql

--changeset minouni:1
CREATE TABLE operators_skills (
  operator_id INTEGER NOT NULL,
  skill_id INTEGER NOT NULL,
  CONSTRAINT pk_operators_skills PRIMARY KEY (operator_id, skill_id)
);

ALTER TABLE operators_skills
ADD CONSTRAINT fk_opeski_on_operator FOREIGN KEY (operator_id) REFERENCES operators (id);

ALTER TABLE operators_skills
ADD CONSTRAINT fk_opeski_on_skill FOREIGN KEY (skill_id) REFERENCES skills (id);