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
    subcategory_id bigint NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (unit_id) REFERENCES unit(id),
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(id)
);

-- Create Product Table
CREATE TABLE product(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description  VARCHAR(500) NOT NULL,
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
    subcategory_id bigint NOT NULL,
    description    VARCHAR(500) NOT NULL,
    entry_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_date    TIMESTAMP NULL,
    size_id bigint NOT NULL,
    quantity       DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(id),
    FOREIGN KEY (size_id) REFERENCES size(id)
);

-- Create Product Default Ingredients Table
CREATE TABLE subcategory_default_ingredients(
    subcategory_id bigint NOT NULL,
    ingredient_id bigint NOT NULL,
    size_id bigint NOT NULL,
	quantity       DECIMAL(12,2) NOT NULL,
    PRIMARY KEY(subcategory_id, ingredient_id),
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(id),
    FOREIGN KEY (ingredient_id) REFERENCES inventory(id),
    FOREIGN KEY (size_id) REFERENCES size(id)
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



INSERT INTO category (id, name, description) VALUES
(1, 'Comida', 'Platillos principales'),
(2, 'Bebidas', 'Bebidas frías y calientes'),
(3, 'Insumos', 'Ingredientes');


INSERT INTO subcategory (id, category_id, name, description) VALUES
(1, 1, 'Hamburguesa de res', 'Carne de res'),
(2, 1, 'Hamburguesa vegana', 'A base de plantas'),
(3, 2, 'Malteadas', 'Batidos cremosos'),
(4, 3, 'Ingredientes hamburguesa', 'Ingredientes hamburguesa');


INSERT INTO unit (id, name, abbreviation) VALUES
(1, 'Gramos', 'g'),
(2, 'Mililitros', 'ml'),
(3, 'Pieza', 'pz'),
(4, 'Hoja', 'hoja');


INSERT INTO size (id, unit_id, subcategory_id, quantity) VALUES
(1, 3, 1, 2),   -- Pan
(2, 1, 1, 150), -- Carne
(3, 1, 1, 270), -- Carne
(4, 1, 1, 500), -- Carne
(5, 4, 4, 3), -- Lechuga
(11, 1, 3, 150), -- Helado
(12, 2, 3, 200); -- Leche


INSERT INTO inventory (id, category_id, subcategory_id, name, description, entry_date, size_id, quantity) VALUES
(1, 1, 1, 'Pan de hamburguesa', 'Pan artesanal', '2025-05-23 00:00:00', 1, 100),
(2, 1, 1, 'Carne de Res', 'Carne de res mediana', '2025-05-23 00:00:00', 3, 100),
(3, 1, 1, 'Carne de Res', 'Carne de res chica', '2025-05-23 00:00:00', 2, 100),
(4, 1, 1, 'Carne de Res', 'Carne de res grande', '2025-05-23 00:00:00', 4, 100),
(5, 1, 1, 'Lechuga', 'lechuga', '2025-05-23 00:00:00', 1, 100),
(6, 2, 3, 'Cerezas', 'En almíbar', '2025-05-23 00:00:00', 12, 100);

INSERT INTO product (name, description, category_id, subcategory_id, size_id, price) VALUES
('Hamburguesa mediana', 'Con carne de res mediana, lechuga y pan artesanal', 1, 1, 3, 89.00), -- Carne mediana (270g)
('Hamburguesa chica', 'Con carne de res chica, lechuga y pan artesanal', 1, 1, 2, 79.00),     -- Carne chica (150g)
('Hamburguesa grande', 'Con carne de res grande, lechuga y pan artesanal', 1, 1, 4, 109.00);  -- Carne grande (500g)s

INSERT INTO product (name, description, category_id, subcategory_id, size_id, price) VALUES
('Malteada chica', 'Malteada con 150g de helado y 200ml de leche', 2, 3, 11, 49.00), -- Helado
('Malteada mediana', 'Malteada con 200ml de leche y cerezas', 2, 3, 12, 59.00);     -- Leche

