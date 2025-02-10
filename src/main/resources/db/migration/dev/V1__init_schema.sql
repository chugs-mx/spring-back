-- Create User Table
CREATE TABLE User (
	    user_id        INT AUTO_INCREMENT PRIMARY KEY,
	    name          VARCHAR(100) NOT NULL,
	    email         VARCHAR(100) UNIQUE NOT NULL,
	    password_hash VARCHAR(255) NOT NULL,
	    user_type     ENUM('admin', 'staff', 'waiter') NOT NULL
);

-- Create Restaurant Table
CREATE TABLE RestaurantTable (
	    table_id     INT AUTO_INCREMENT PRIMARY KEY,
	    table_number TINYINT UNSIGNED NOT NULL CHECK (table_number BETWEEN 0 AND 255),
	    seat_number  TINYINT UNSIGNED NOT NULL CHECK (seat_number BETWEEN 0 AND 255),
	    table_state  ENUM('available', 'occupied', 'reserved') NOT NULL
);

-- Create Order Table
CREATE TABLE OrderTable (
	    order_id       INT AUTO_INCREMENT PRIMARY KEY,
	    total         DECIMAL(10,2) NOT NULL,
	    order_date    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	    user_id       INT NOT NULL,
	    table_id      INT NOT NULL,
	    party_size INT NOT NULL,
	    FOREIGN KEY (user_id) REFERENCES User(user_id),
	    FOREIGN KEY (table_id) REFERENCES RestaurantTable(table_id)
);

-- Create Discount Table
CREATE TABLE Discount (
	    discount_id  INT AUTO_INCREMENT PRIMARY KEY,
	    name        VARCHAR(50) NOT NULL,
	    discount_type ENUM('fixed', 'percentage') NOT NULL,
	    amount      DECIMAL(10,2) NOT NULL,
	    start_date  DATETIME NOT NULL,
	    end_date    DATETIME NOT NULL
);

-- Create Ticket Table
CREATE TABLE Ticket (
	    ticket_id         INT AUTO_INCREMENT PRIMARY KEY,
	    discount_id       INT NULL,
	    subtotal          DECIMAL(10,2) NOT NULL,
	    total            DECIMAL(10,2) NOT NULL,
	    tip              DECIMAL(10,2) NOT NULL DEFAULT 0.00,
	    order_id         INT UNIQUE NOT NULL,
	    ticket_status    ENUM('pending', 'paid', 'canceled') NOT NULL,
	    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id),
	    FOREIGN KEY (discount_id) REFERENCES Discount(discount_id)
);

-- Create Payment Table
CREATE TABLE Payment (
	    payment_id      INT AUTO_INCREMENT PRIMARY KEY,
	    ticket_id      INT NOT NULL,
	    payment_method ENUM('cash', 'credit_card', 'debit_card') NOT NULL,
	    amount_paid    DECIMAL(10,2) NOT NULL,
	    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id)
);

-- Create Product Table
CREATE TABLE Product (
	    product_id   INT AUTO_INCREMENT PRIMARY KEY,
	    name        VARCHAR(100) NOT NULL,
	    description TEXT NOT NULL,
	    price       DECIMAL(10,2) NOT NULL
);

-- Create Inventory (Supply) Table
CREATE TABLE Inventory (
	    inventory_id     INT AUTO_INCREMENT PRIMARY KEY,
	    name         VARCHAR(100) NOT NULL,
	    inventory_type  ENUM('ingredient', 'equipment', 'packaging') NOT NULL,
	    description  TEXT NOT NULL,
	    entry_date   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	    expiry_date  DATETIME NULL,
		unit_measure ENUM('kg', 'ltr', 'unit') NOT NULL,
		unit_price   DECIMAL(10,2) NOT NULL,
	    quantity     DECIMAL(10,2) NOT NULL
);

-- Create Product Inventory Table
CREATE TABLE ProductInventory (
	    product_id    INT NOT NULL,
	    unit_measure ENUM('kg', 'ltr', 'unit') NOT NULL,
	    inventory_id    INT NOT NULL,
	    quantity     DECIMAL(10,2) NOT NULL,
	    PRIMARY KEY (product_id, inventory_id),
	    FOREIGN KEY (product_id) REFERENCES Product(product_id),
	    FOREIGN KEY (inventory_id) REFERENCES Inventory(inventory_id)
);

-- Create Order Product Table
CREATE TABLE OrderProduct (
	    order_product_id INT AUTO_INCREMENT PRIMARY KEY,
	    order_id        INT NOT NULL,
	    product_id      INT NOT NULL,
	    quantity        DECIMAL(10,2) NOT NULL,
	    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id),
	    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

-- Create Order Extra Inventory Table
CREATE TABLE OrderExtraSupply (
	    order_product_id INT NOT NULL,
	    supply_id       INT NOT NULL,
	    quantity        DECIMAL(10,2) NOT NULL,
	    PRIMARY KEY (order_product_id, supply_id),
	    FOREIGN KEY (order_product_id) REFERENCES OrderProduct(order_product_id),
	    FOREIGN KEY (supply_id) REFERENCES Inventory(inventory_id)
);

