--liquibase formatted sql
--changeset ah:12
create table payment(
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(64) not null,
    type varchar(32) not null,
    default_payment boolean default false,
    note text
);

--changeset ah:12.1
insert into payment(id, name, type, default_payment, note)
values (1, 'Bank Transfer', 'BANK_TRANSFER', true, 'Please make payment on :\n30 1030 1739 5825 1518 9904 4499\n with title containing order number');