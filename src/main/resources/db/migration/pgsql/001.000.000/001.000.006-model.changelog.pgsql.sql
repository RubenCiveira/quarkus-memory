--liquibase formatted sql

--changeset auto.generated:1825492372-1
ALTER TABLE place ADD opening_date TIMESTAMP WITHOUT TIME ZONE;

