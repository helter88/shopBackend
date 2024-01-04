--liquibase formatted sql
--changeset ah:5
alter table product add category_id bigint after category;
--changeset ah:5.1
alter table product drop column category;
--changeset ah:5.2
alter table product add constraint fk_product_category_id foreign key (category_id) references category(id);