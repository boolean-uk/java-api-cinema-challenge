-- Generate dummy data for customers table
INSERT INTO customers (created_at, email, name, phone, updated_at)
VALUES
    (CURRENT_TIMESTAMP, 'john@example.com', 'John Doe', '1234567890', CURRENT_TIMESTAMP),
    (CURRENT_TIMESTAMP, 'jane@example.com', 'Jane Smith', '9876543210', CURRENT_TIMESTAMP),
    (CURRENT_TIMESTAMP, 'alex@example.com', 'Alex Johnson', '5555555555', CURRENT_TIMESTAMP);

-- Generate dummy data for movies table
INSERT INTO movies (created_at, description, rating, runtime_mins, title, updated_at)
VALUES
    (CURRENT_TIMESTAMP, 'An action-packed thriller', 'PG-13', 120, 'The Secret Agent', CURRENT_TIMESTAMP),
    (CURRENT_TIMESTAMP, 'A heartwarming drama', 'PG', 90, 'The Family Bond', CURRENT_TIMESTAMP),
    (CURRENT_TIMESTAMP, 'A hilarious comedy', 'R', 105, 'Laugh Out Loud', CURRENT_TIMESTAMP);

-- Generate dummy data for screenings table
INSERT INTO screenings (capacity, created_at, screen_number, starts_at, updated_at, movie_id)
VALUES
    (100, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    (150, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
    (200, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3);

-- Generate dummy data for tickets table
INSERT INTO tickets (created_at, num_seats, updated_at, customer_id, screening_id)
VALUES
    (CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 1, 1),
    (CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 2, 2),
    (CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 3, 3);
