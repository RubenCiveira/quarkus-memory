--liquibase formatted sql

--changeset auto.generated:1825492372-1
ALTER TABLE color ADD merchant VARCHAR(255);

--changeset auto.generated:1825492372-2
ALTER TABLE place ADD merchant VARCHAR(255) NOT NULL;

--changeset auto.generated:1825492372-3
CREATE INDEX fl_color_merchant ON color(merchant);

--changeset auto.generated:1825492372-4
CREATE INDEX fl_place_merchant ON place(merchant);

--changeset auto.generated:1825492372-5
CREATE UNIQUE INDEX uk_place_merchant_name ON place(merchant, name);

--changeset auto.generated:1825492372-6
ALTER TABLE color ADD CONSTRAINT fk_color_merchant FOREIGN KEY (merchant) REFERENCES merchant (uid) ON UPDATE NO ACTION ON DELETE NO ACTION;

--changeset auto.generated:1825492372-7
ALTER TABLE place ADD CONSTRAINT fk_place_merchant FOREIGN KEY (merchant) REFERENCES merchant (uid) ON UPDATE NO ACTION ON DELETE NO ACTION;

