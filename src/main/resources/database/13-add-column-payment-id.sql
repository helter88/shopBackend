--liquibase formatted sql
--changeset ah:13

alter table `order` add payment_id bigint;
update `order` set payment_id=1;
alter table `order` MODIFY payment_id bigint not null;