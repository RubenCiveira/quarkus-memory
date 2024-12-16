--liquibase formatted sql

--changeset auto.generated:1825492372-1
CREATE TABLE color (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT color_pkey PRIMARY KEY (uid));

--changeset auto.generated:1825492372-2
CREATE TABLE merchant (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT merchant_pkey PRIMARY KEY (uid));

--changeset auto.generated:1825492372-3
CREATE TABLE place (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT place_pkey PRIMARY KEY (uid));

