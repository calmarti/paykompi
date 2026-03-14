--DROP TABLE IF EXISTS transactions CASCADE;
--DROP TABLE IF EXISTS payments CASCADE;
--DROP TABLE IF EXISTS orders CASCADE;
--DROP TABLE IF EXISTS accounts CASCADE;
--DROP TABLE IF EXISTS users CASCADE;

---users
CREATE TABLE IF not exists users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    user_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
    );

---accounts

CREATE TABLE IF NOT EXISTS accounts (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    currency VARCHAR(3) NOT NULL,
    balance NUMERIC(19,2) NOT NULL,
    account_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_accounts_user_id_currency UNIQUE (user_id, currency),

    CONSTRAINT fk_accounts_users FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT ck_accounts_balance_non_negative CHECK (balance >= 0)
    );


---orders

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    merchant_id UUID NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    description VARCHAR(150),
    order_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_orders_users FOREIGN KEY (merchant_id) REFERENCES users(id),
    CONSTRAINT ck_orders_amount_positive CHECK (amount >= 0.01)
    );


---payments

CREATE TABLE IF NOT EXISTS payments (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    payer_account_id UUID NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    payment_currency VARCHAR(3) NOT NULL,
    payment_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_payments_orders FOREIGN KEY (order_id) REFERENCES orders(id),

    CONSTRAINT fk_payments_accounts FOREIGN KEY (payer_account_id) REFERENCES accounts(id),

    CONSTRAINT ck_payments_amount_non_negative CHECK (amount >= 0.01)
    );


---transactions

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    payment_id UUID NOT NULL,
    entry_type VARCHAR(50) NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    source VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_transactions_accounts FOREIGN KEY (account_id) REFERENCES accounts(id),

    CONSTRAINT fk_transactions_payments FOREIGN KEY (payment_id) REFERENCES payments(id),

    CONSTRAINT ck_transactions_amount_positive CHECK (amount >= 0.01)
    );

