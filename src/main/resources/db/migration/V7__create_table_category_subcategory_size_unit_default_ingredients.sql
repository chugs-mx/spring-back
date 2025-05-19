create table category(
id bigint auto_increment primary key,
name varchar(50) not null unique,
description varchar(200)
);

create table subcategory(
id bigint auto_increment primary key,
fk_category_id bigint not null references category(id),
name varchar(50) not null,
description varchar(50)
);

create table unit(
id bigint auto_increment primary key,
name varchar(50) not null unique,
abbreviation varchar(50) not null unique
);

create table size(
id bigint auto_increment primary key,
fk_unit_id bigint not null references unit(id),
quantity decimal(10,2) not null
);

create table product_default_ingredients(
product_id bigint not null references product(product_id),
ingredient_id bigint not null references inventory(inventory_id),
primary key(product_id, ingredient_id)
);