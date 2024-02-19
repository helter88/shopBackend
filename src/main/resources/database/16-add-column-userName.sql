--liquibase formatted sql
--changeset ah:16
alter table `order` add user_id bigint;

--changeset ah:16.1
alter table `order` add constraint fk_order_user_id foreign key (user_id) references users(id);