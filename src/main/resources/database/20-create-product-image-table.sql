--liquibase formatted sql
--changeset ah:20
CREATE TABLE product_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    image_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (image_id) REFERENCES image(id)
);