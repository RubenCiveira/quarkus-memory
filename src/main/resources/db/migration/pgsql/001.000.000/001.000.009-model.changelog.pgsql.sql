--liquibase formatted sql

--changeset auto.generated:1825492372-1
CREATE TABLE medal (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT medal_pkey PRIMARY KEY (uid));

--changeset auto.generated:1825492372-2
CREATE TABLE verify (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT verify_pkey PRIMARY KEY (uid));

--changeset auto.generated:1825492372-3
CREATE TABLE verify_medal (uid VARCHAR(255) NOT NULL, version INTEGER NOT NULL, medal VARCHAR(255) NOT NULL, verify VARCHAR(255) NOT NULL, CONSTRAINT verify_medal_pkey PRIMARY KEY (uid));

--changeset auto.generated:1825492372-4
CREATE INDEX fl_verify_medal_medal ON verify_medal(medal);

--changeset auto.generated:1825492372-5
CREATE INDEX fl_verify_medal_verify ON verify_medal(verify);

--changeset auto.generated:1825492372-6
CREATE UNIQUE INDEX uk_verify_medal_verify_medal ON verify_medal(verify, medal);

--changeset auto.generated:1825492372-7
ALTER TABLE verify_medal ADD CONSTRAINT fk_verify_medal_medal FOREIGN KEY (medal) REFERENCES medal (uid) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset auto.generated:1825492372-8
ALTER TABLE verify_medal ADD CONSTRAINT fk_verify_medal_verify FOREIGN KEY (verify) REFERENCES verify (uid) ON UPDATE NO ACTION ON DELETE NO ACTION;

