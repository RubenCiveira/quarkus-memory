--liquibase formatted sql

--changeset auto.generated:1825492372-1
CREATE TABLE fruit (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT fruit_pkey PRIMARY KEY (uid));

