--liquibase formatted sql

--changeset auto.generated:1825492372-1
CREATE TABLE _long_tasks (code VARCHAR(250) NOT NULL, actor VARCHAR(250) NOT NULL, creation TIMESTAMP WITHOUT TIME ZONE NOT NULL, completion TIMESTAMP WITHOUT TIME ZONE, progress TEXT NOT NULL, CONSTRAINT pk_long_task PRIMARY KEY (code));

--changeset auto.generated:1825492372-2
CREATE INDEX fl_area_name ON area(name);

--changeset auto.generated:1825492372-3
CREATE INDEX fl_color_name ON color(name);

--changeset auto.generated:1825492372-4
CREATE INDEX fl_fruit_name ON fruit(name);

--changeset auto.generated:1825492372-5
CREATE INDEX idx_long_tasks_code_actor ON _long_tasks(code, actor);

--changeset auto.generated:1825492372-6
CREATE INDEX st_medal_name_desc ON medal(name);

--changeset auto.generated:1825492372-7
CREATE INDEX st_place_name_desc ON place(name);

--changeset auto.generated:1825492372-8
CREATE INDEX st_verify_name_asc ON verify(name);

