--liquibase formatted sql

--changeset auto.generated:1825492372-1
CREATE TABLE _filestorer (code VARCHAR(250) NOT NULL, temp SMALLINT DEFAULT 0 NOT NULL, name VARCHAR(250) NOT NULL, mime VARCHAR(250) NOT NULL, upload TIMESTAMP WITHOUT TIME ZONE NOT NULL, bytes BYTEA NOT NULL, CONSTRAINT pk_accion_coleccion PRIMARY KEY (code));

