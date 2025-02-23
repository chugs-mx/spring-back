-- ADD COLUMN order_status TO THE order_product
alter table order_product
add column order_status VARCHAR(15) NOT NULL;

-- ADD COLUMN notified TO THE order_product
alter table order_product
add column notified BOOLEAN DEFAULT FALSE;

-- ADD COLUMN order_date TO THE ticket
alter table ticket
add column order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- ADD COLUMN tip TO THE payment
alter table payment
add column tip DECIMAL(12,2) NOT NULL DEFAULT 0.00;