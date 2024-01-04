--liquibase formatted sql
--changeset ah:6
insert into category (id, name, description, slug) values(1, 'Other', 'desc1', 'other');
--changeset ah:6.1
update product set category_id=1;
--changeset ah:6.2
alter table product MODIFY category_id bigint not null;