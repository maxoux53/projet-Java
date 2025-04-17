-- ⚠️ Se connecter à 'store_management' ⚠️ --

/*CREATE SCHEMA store_management;
SET search_path TO store_management;*/

CREATE TABLE role (
    label VARCHAR(25) PRIMARY KEY
);

CREATE TABLE country (
    name VARCHAR(20) PRIMARY KEY
);

CREATE TABLE city (
    zip_code SMALLINT,
    name VARCHAR(20),
    country VARCHAR(20) NOT NULL REFERENCES country(name),
    CONSTRAINT pk_city PRIMARY KEY (zip_code, name)
);

CREATE TABLE employee (
    id SMALLSERIAL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    password BYTEA NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    street VARCHAR(30) NOT NULL,
    street_number SMALLINT NOT NULL,
    unit_number SMALLINT,
    role_label VARCHAR(25) NOT NULL,
    hire_date DATE NOT NULL,
    manager_id SMALLINT REFERENCES employee(id),
    city_zip_code SMALLINT NOT NULL,
    city_name VARCHAR(20) NOT NULL,
    CONSTRAINT fk_employee_city FOREIGN KEY (city_zip_code, city_name) REFERENCES city(zip_code, name)
);

CREATE TABLE vat (
    type CHAR(1) PRIMARY KEY,
    rate SMALLINT NOT NULL
);

CREATE TABLE category (
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE brand (
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE product (
    barcode BIGINT PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    description TEXT,
    amount SMALLINT NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    vat_type CHAR(1) NOT NULL REFERENCES vat(type),
    category_id SMALLINT NOT NULL REFERENCES category(id),
    brand_id SMALLINT NOT NULL REFERENCES brand(id),
    excl_vat_price MONEY NOT NULL,
    start_date DATE NOT NULL
);

CREATE TABLE customer (
    loyalty_card_number SERIAL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone INT,
    vat_number BIGINT,
    loyalty_points SMALLINT NOT NULL DEFAULT 0
);

CREATE TABLE purchase (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    employee_id SMALLINT REFERENCES employee(id),
    customer_card_number INT REFERENCES customer(loyalty_card_number)
);

CREATE TABLE order_line (
    quantity SMALLINT NOT NULL,
    product_barcode BIGINT REFERENCES product(barcode),
    purchase_id BIGINT NOT NULL REFERENCES purchase(id),
    CONSTRAINT pk_order_line PRIMARY KEY (product_barcode, purchase_id)
);
