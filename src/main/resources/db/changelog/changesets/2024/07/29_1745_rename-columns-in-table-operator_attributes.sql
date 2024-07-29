--liquibase formatted sql

--changeset minouni:1
ALTER TABLE operator_attributes
RENAME COLUMN deployment_cost TO deploy_cost;

ALTER TABLE operator_attributes
RENAME COLUMN redeployment_time TO redeploy_time;