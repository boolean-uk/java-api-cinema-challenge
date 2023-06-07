--Run all to Create Dummy data

-- Insert dummy data into the customers table
INSERT INTO customers (created_at, email, name, phone, updated_at)
VALUES
('2023-06-01 09:00:00', 'john@example.com', 'John Doe', '1234567890', '2023-06-01 09:00:00'),
('2023-06-02 14:30:00', 'jane@example.com', 'Jane Smith', '0987654321', '2023-06-02 14:30:00');

-- Insert dummy data into the movies table
INSERT INTO movies (created_at, description, rating, runtime_mins, title, updated_at)
VALUES
('2023-06-01 12:00:00', 'A thrilling action movie', 'PG-13', 120, 'The Avengers', '2023-06-01 12:00:00'),
('2023-06-02 18:30:00', 'A heartwarming romantic comedy', 'PG', 95, 'Love Actually', '2023-06-02 18:30:00');

-- Insert dummy data into the screenings table
INSERT INTO screenings (capacity, created_at, screen_number, starts_at, updated_at, movie_id)
VALUES
(100, '2023-06-05 16:00:00', 1, '2023-06-06 17:00:00', '2023-06-06 09:00:00', 1),
(80, '2023-06-05 17:30:00', 2, '2023-06-06 18:30:00', '2023-06-06 10:00:00', 2);

-- Insert dummy data into the tickets table
INSERT INTO tickets (created_at, num_seats, updated_at, customer_id, screening_id)
VALUES
('2023-06-06 09:30:00', 2, '2023-06-06 09:30:00', 1, 1),
('2023-06-06 10:30:00', 1, '2023-06-06 10:30:00', 2, 2);