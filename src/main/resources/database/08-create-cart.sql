--liquibase formatted sql
--changeset ah:8
create table cart(
    id bigint not null auto_increment PRIMARY KEY,
    created datetime not null
);

--changeset ah:8.1
create table cart_item(
    id bigint not null auto_increment PRIMARY KEY,
    product_id bigint not null,
    quantity int,
    cart_id bigint not null,
    constraint fk_cart_item_product_id foreign key (product_id) references product(id),
    constraint fk_cart_item_cart_id foreign key (cart_id) references cart(id)
);