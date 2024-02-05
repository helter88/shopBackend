--liquibase formatted sql
--changeset ah:11
alter table order_row MODIFY product_id bigint;

--changeset ah:11.1
alter table order_row add shipment_id bigint;

--changeset ah:11.2
alter table order_row add constraint fk_order_row_shipment_id foreign key (shipment_id) references shipment(id);