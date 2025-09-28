--CREATE TYPE client_product_status AS ENUM (
--    'ACTIVE',
--'CLOSED',
--        'BLOCKED',
--        'ARRESTED'
--);

--CREATE TYPE document_type AS ENUM (
--    'PASSPORT',
--        'INT_PASSPORT',
--        'BIRTH_CERT'
--);

--CREATE TYPE product_key AS ENUM (
--    'DC', 'CC', 'AC', 'IPO', 'PC', 'PENS', 'NS', 'INS', 'BS'
--);

-- blacklist_registry
CREATE TABLE blacklist_registry (
    id BIGSERIAL PRIMARY KEY,
    document_type VARCHAR(50),
    document_id VARCHAR(255),
    blacklisted_at DATE,
    reason VARCHAR(255),
    blacklist_expiration_date DATE
);


-- users
CREATE TABLE client_sequence (
    region_code VARCHAR(2) PRIMARY KEY,
    branch_code VARCHAR(2) NOT NULL,
    last_number BIGINT
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- clients
CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    client_id VARCHAR(20) NOT NULL UNIQUE,
    user_id BIGINT REFERENCES users(id),
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    document_type VARCHAR(50),
    document_id VARCHAR(255),
    document_prefix VARCHAR(50),
    document_suffix VARCHAR(50)
);

-- products
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    key VARCHAR(5),
    create_date TIMESTAMP,
    product_id VARCHAR(255) NOT NULL UNIQUE
);

-- client_products
CREATE TABLE client_products (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT REFERENCES clients(id),
    product_id BIGINT REFERENCES products(id),
    open_date TIMESTAMP,
    close_date TIMESTAMP,
    status VARCHAR(50)
);
