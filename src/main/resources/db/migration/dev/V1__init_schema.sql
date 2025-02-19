-- Create User Table (Renamed to `app_user`)
CREATE TABLE app_user (
    user_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    birth_date    TIMESTAMP NOT NULL,
    user_type     VARCHAR(20) NOT NULL
);

-- Create Restaurant Table (Renamed)
CREATE TABLE restaurant_table (
    table_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    seat_number  SMALLINT UNSIGNED NOT NULL CHECK (seat_number BETWEEN 0 AND 255),
    table_state  VARCHAR(20) NOT NULL
);

-- Create Order Table (Renamed)
CREATE TABLE order_table (
    order_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    total      DECIMAL(12,2) NOT NULL, -- More precise financial data
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id    BIGINT NOT NULL,
    table_id   BIGINT NOT NULL,
    party_size SMALLINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user(user_id),
    FOREIGN KEY (table_id) REFERENCES restaurant_table(table_id)
);

-- Create Discount Table
CREATE TABLE discount (
    discount_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    discount_type VARCHAR(20) NOT NULL,
    amount        DECIMAL(12,2) NOT NULL,
    start_date    TIMESTAMP NOT NULL,
    end_date      TIMESTAMP NOT NULL
);

-- Create Ticket Table
CREATE TABLE ticket (
    ticket_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    discount_id    BIGINT NULL,
    subtotal       DECIMAL(12,2) NOT NULL,
    total          DECIMAL(12,2) NOT NULL,
    tip            DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    order_id       BIGINT UNIQUE NOT NULL,
    ticket_status  VARCHAR(20) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES order_table(order_id),
    FOREIGN KEY (discount_id) REFERENCES discount(discount_id)
);

-- Create Payment Table
CREATE TABLE payment (
    payment_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id      BIGINT NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    amount_paid    DECIMAL(12,2) NOT NULL,
    payment_date   TIMESTAMP NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id)
);

-- Create Product Table
CREATE TABLE product (
    product_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price       DECIMAL(12,2) NOT NULL
);

-- Create Inventory Table
CREATE TABLE inventory (
    inventory_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    inventory_type VARCHAR(20) NOT NULL,
    description    VARCHAR(500) NOT NULL,
    entry_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_date    TIMESTAMP NULL,
    unit_measure   VARCHAR(10) NOT NULL,
    unit_price     DECIMAL(12,2) NOT NULL,
    quantity       DECIMAL(12,2) NOT NULL
);

-- Create Product Inventory Table
CREATE TABLE product_inventory (
    product_id    BIGINT NOT NULL,
    inventory_id  BIGINT NOT NULL,
    unit_measure  VARCHAR(10) NOT NULL,
    quantity      DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (product_id, inventory_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);

-- Create Order Product Table
CREATE TABLE order_product (
    order_product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id         BIGINT NOT NULL,
    product_id       BIGINT NOT NULL,
    quantity         DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES order_table(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- Create Order Extra Inventory Table
CREATE TABLE order_extra_supply (
    order_product_id BIGINT NOT NULL,
    supply_id        BIGINT NOT NULL,
    quantity         DECIMAL(12,2) NOT NULL,
    unit_measure  VARCHAR(10) NOT NULL,
    PRIMARY KEY (order_product_id, supply_id),
    FOREIGN KEY (order_product_id) REFERENCES order_product(order_product_id),
    FOREIGN KEY (supply_id) REFERENCES inventory(inventory_id)
);
