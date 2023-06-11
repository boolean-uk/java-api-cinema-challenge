-- Dummy data into the customer table
INSERT INTO customer (id, created_at, updated_at, email, name, phone)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'customer1@example.com', 'John Doe', '1234567890'),
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'customer2@example.com', 'Jane Smith', '9876543210');

-- Dummy data for the movie table
INSERT INTO movie (id, created_at, updated_at, description, rating, runtime_mins, title)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Action-packed thriller', 'PG-13', 120, 'The Matrix'),
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Romantic comedy', 'PG', 90, 'Notting Hill'),
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Sci-fi adventure', 'PG', 150, 'Star Wars: Episode IV - A New Hope'),
    (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Animated family film', 'G', 95, 'Toy Story');

-- Dummy data for the screening table
INSERT INTO screening (id, created_at, updated_at, capacity, screen_number, starts_at, movie_id)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 100, 1, '2023-06-10 18:00:00', 1),
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 80, 2, '2023-06-10 20:00:00', 2),
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 120, 3, '2023-06-11 14:00:00', 3),
    (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 90, 1, '2023-06-11 16:30:00', 4),
    (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 150, 2, '2023-06-11 19:00:00', 1),
    (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 100, 3, '2023-06-12 12:00:00', 2),
    (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 80, 1, '2023-06-12 15:30:00', 3),
    (8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 120, 2, '2023-06-12 18:00:00', 4),
    (9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 90, 3, '2023-06-13 10:00:00', 1),
    (10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 150, 1, '2023-06-13 13:30:00', 2),
    (11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 100, 2, '2023-06-13 16:00:00', 3),
    (12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 80, 3, '2023-06-14 10:30:00', 4),
    (13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 120, 1, '2023-06-14 13:00:00', 1),
    (14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 90, 2, '2023-06-14 15:30:00', 2),
    (15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 150, 3, '2023-06-15 14:00:00', 3);
