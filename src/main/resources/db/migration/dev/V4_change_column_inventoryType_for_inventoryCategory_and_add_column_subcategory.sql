-- ADD COLUMN SUBCATEGORY
alter table inventory
add column subcategory VARCHAR(20) NOT NULL;

-- RENAME COLUMN INVENTORY_TYPE TO CATEGORY
alter table inventory
rename column inventory_type to inventory_category;