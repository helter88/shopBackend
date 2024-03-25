--liquibase formatted sql
--changeset ah:21
create table user_data (
    id bigint not null auto_increment PRIMARY KEY,
    firstname varchar(64) not null,
    lastname varchar(64) not null,
    street varchar(80) not null,
    zipcode varchar(6) not null,
    city varchar(64) not null,
    email varchar(64) not null,
    phone varchar(16) not null,
    user_id bigint not null
);

--changeset ah:21.1
alter table user_data add constraint fk_user_data_user_id foreign key (user_id) references users(id);