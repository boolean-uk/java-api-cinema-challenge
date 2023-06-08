CREATE TABLE IF NOT EXISTS tickets (
    id SERIAL PRIMARY KEY,
    num_seats INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    customer_id INT NOT NULL,
        CONSTRAINT fk_customer_id
            FOREIGN KEY (customer_id)
                REFERENCES customers(id),
    screening_id INT NOT NULL,
            CONSTRAINT fk_screening_id
                FOREIGN KEY (screening_id)
                    REFERENCES screenings(id)
);