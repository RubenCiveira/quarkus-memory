--liquibase formatted sql

--changeset auto.generated:1825492372-1
ALTER TABLE merchant ALTER COLUMN  enabled SET DEFAULT NULL;

