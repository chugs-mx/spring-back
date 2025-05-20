-- Create User Table (Renamed to `app_user`)
CREATE TABLE app_user (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    birth_date    TIMESTAMP NOT NULL,
    user_type     VARCHAR(20) NOT NULL
);


-- Create Restaurant Table (Renamed)
CREATE TABLE restaurant_table (
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    seat_number  SMALLINT UNSIGNED NOT NULL CHECK (seat_number BETWEEN 0 AND 255),
    table_state  VARCHAR(20) NOT NULL
);


-- Create Order Table (Renamed)
CREATE TABLE order_table (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    total      DECIMAL(12,2) NOT NULL, -- More precise financial data
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id    BIGINT NOT NULL,
    table_id   BIGINT NOT NULL,
    party_size SMALLINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    FOREIGN KEY (table_id) REFERENCES restaurant_table(id)
);

-- Create Discount Table
CREATE TABLE discount (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    discount_type VARCHAR(20) NOT NULL,
    amount        DECIMAL(12,2) NOT NULL,
    start_date    TIMESTAMP NOT NULL,
    end_date      TIMESTAMP NOT NULL
);


-- Create Ticket Table
CREATE TABLE ticket (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    discount_id    BIGINT NULL,
    subtotal       DECIMAL(12,2) NOT NULL,
    total          DECIMAL(12,2) NOT NULL,
    tip            DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    order_id       BIGINT UNIQUE NOT NULL,
    ticket_status  VARCHAR(20) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES order_table(id),
    FOREIGN KEY (discount_id) REFERENCES discount(id)
);

-- Create Payment Table
CREATE TABLE payment (
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id      BIGINT NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    amount_paid    DECIMAL(12,2) NOT NULL,
    payment_date   TIMESTAMP NOT NULL,
    tip DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

-- Create Category Table
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200)
);

-- Create Subcategory Table
CREATE TABLE subcategory(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Create Unit Table
CREATE TABLE unit(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    abbreviation VARCHAR(50) NOT NULL UNIQUE
);

-- Create Size Table
CREATE TABLE size(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unit_id bigint NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (unit_id) REFERENCES unit(id)
);

-- Create Product Table
CREATE TABLE product(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT NOT NULL,
    subcategory_id bigint NOT NULL,
    size_id bigint NOT NULL,
    price decimal(10,2) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(id),
    FOREIGN KEY (size_id) REFERENCES size(id)
);

-- Create Inventory Table
CREATE TABLE inventory (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    category_id bigint NOT NULL,
    description    VARCHAR(500) NOT NULL,
    entry_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_date    TIMESTAMP NULL,
    size_id bigint NOT NULL,
    quantity       DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (size_id) REFERENCES size(id)
);

-- Create Product Default Ingredients Table
CREATE TABLE product_default_ingredients(
    product_id bigint NOT NULL,
    ingredient_id bigint NOT NULL,
    PRIMARY KEY(product_id, ingredient_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (ingredient_id) REFERENCES inventory(id)
);

-- Create Product Inventory Table
CREATE TABLE product_inventory (
    product_id    BIGINT NOT NULL,
    inventory_id  BIGINT NOT NULL,
    unit_measure  VARCHAR(10) NOT NULL,
    quantity      DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (product_id, inventory_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (inventory_id) REFERENCES inventory(id)
);

-- Create Order Product Table
CREATE TABLE order_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id         BIGINT NOT NULL,
    product_id       BIGINT NOT NULL,
    quantity         DECIMAL(12,2) NOT NULL,
    order_status VARCHAR(15) NOT NULL,
    notified BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (order_id) REFERENCES order_table(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Create Order Extra Inventory Table
CREATE TABLE order_extra_supply (
    order_product_id BIGINT NOT NULL,
    supply_id        BIGINT NOT NULL,
    quantity         DECIMAL(12,2) NOT NULL,
    unit_measure  VARCHAR(10) NOT NULL,
    PRIMARY KEY (order_product_id, supply_id),
    FOREIGN KEY (order_product_id) REFERENCES order_product(id),
    FOREIGN KEY (supply_id) REFERENCES inventory(id)
);

