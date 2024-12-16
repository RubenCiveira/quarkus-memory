--liquibase formatted sql

--changeset auto.generated:1825492372-1
ALTER TABLE merchant ADD enabled BOOLEAN NOT NULL default false;

--changeset auto.generated:1825492372-2
CREATE INDEX fl_merchant_enabled ON merchant(enabled);

--changeset auto.generated:1825492372-3
CREATE INDEX st_merchant_name_asc ON merchant(name);

