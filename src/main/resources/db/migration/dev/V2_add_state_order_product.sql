-- ADD COLUMN order_status TO THE order_product
alter table order_product
add column order_status VARCHAR(15) NOT NULL;

-- ADD COLUMN notified TO THE order_product
alter table order_product
add column notified BOOLEAN DEFAULT FALSE;