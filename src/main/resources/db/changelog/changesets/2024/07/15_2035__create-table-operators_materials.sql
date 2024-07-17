--liquibase formatted sql

--changeset minouni::1
CREATE TABLE operators_materials (
  material_id INTEGER NOT NULL,
  operator_id INTEGER NOT NULL,
  CONSTRAINT pk_operators_materials PRIMARY KEY (material_id, operator_id)
);

ALTER TABLE operators_materials
ADD CONSTRAINT fk_opmat_on_material FOREIGN KEY (material_id) REFERENCES materials (id);

ALTER TABLE operators_materials
ADD CONSTRAINT fk_opmat_on_operator FOREIGN KEY (operator_id) REFERENCES operators (id);