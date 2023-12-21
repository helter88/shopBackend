--liquibase formatted sql
--changeset ah:2
alter table product add image varchar(255) after currency;