--liquibase formatted sql

--changeset auto.generated:1825492372-1
ALTER TABLE merchant ADD key VARCHAR(255);

--changeset auto.generated:1825492372-2
ALTER TABLE place ADD photo VARCHAR(255);

