-- ADD COLUMN size, types and default_ingredients
ALTER TABLE product ADD COLUMN size VARCHAR(255) NOT NULL;
ALTER TABLE product ADD COLUMN types TEXT NOT NULL;
ALTER TABLE product ADD COLUMN default_ingredients TEXT NOT NULL;
