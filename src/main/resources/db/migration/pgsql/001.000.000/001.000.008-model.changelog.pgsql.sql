--liquibase formatted sql

--changeset auto.generated:1825492372-1
CREATE TABLE area (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, name VARCHAR(255) NOT NULL, place VARCHAR(255) NOT NULL, CONSTRAINT area_pkey PRIMARY KEY (uid));

--changeset auto.generated:1825492372-2
CREATE INDEX fl_area_place ON area(place);

--changeset auto.generated:1825492372-3
CREATE UNIQUE INDEX uk_area_place_name ON area(place, name);

--changeset auto.generated:1825492372-4
ALTER TABLE area ADD CONSTRAINT fk_area_place FOREIGN KEY (place) REFERENCES place (uid) ON UPDATE NO ACTION ON DELETE NO ACTION;

