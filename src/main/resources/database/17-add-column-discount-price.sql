--liquibase formatted sql
--changeset ah:17
--preConditions onFail:MARK_RAN
--preCondition-sql-check expectedResult:0 SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'my_database_name' AND TABLE_NAME = 'product' AND COLUMN_NAME = 'discount_price'
alter table product add discount_price decimal(9, 2);