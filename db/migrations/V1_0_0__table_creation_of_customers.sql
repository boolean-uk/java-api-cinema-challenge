CREATE TABLE IF NOT EXISTS customers (
    customer_id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    phone TEXT,
    created_at TEXT,
    updated_at TEXT
);