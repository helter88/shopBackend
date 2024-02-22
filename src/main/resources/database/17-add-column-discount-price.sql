--liquibase formatted sql
--changeset ah:17
alter table product add discount_price decimal(9, 2);