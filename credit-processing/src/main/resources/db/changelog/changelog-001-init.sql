-- ProductRegistry
CREATE TABLE product_registry (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    interest_rate NUMERIC(10,4),
    open_date DATE
);
-- PaymentRegistry
CREATE TABLE payment_registry (
    id BIGSERIAL PRIMARY KEY,
    product_registry_id BIGINT NOT NULL REFERENCES product_registry(id),
    paymend_date DATE,
    amount NUMERIC(18,2),
    interest_rate_amount NUMERIC(18,2),
    debt_amount NUMERIC(18,2),
    expired BOOLEAN,
    payment_expiration_date DATE
);
