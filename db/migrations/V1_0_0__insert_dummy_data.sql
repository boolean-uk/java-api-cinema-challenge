INSERT INTO customers (name, email, phone, created_at, updated_at)
VALUES
    ('Visitor 1', 'visitor1@example.com', '1234567890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Visitor 2', 'visitor2@example.com', '9876543210', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Visitor 3', 'visitor3@example.com', '5555555555', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Visitor 4', 'visitor4@example.com', '1111111111', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Visitor 5', 'visitor5@example.com', '9999999999', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies (title, rating, description, runtime_mins, created_at, updated_at)
VALUES
    ('The Grand Budapest Hotel', 'R', 'A renowned concierge and a young employee become friends amidst the backdrop of a changing Europe.', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Moonrise Kingdom', 'PG-13', 'Two young lovers run away from their New England town, causing a local search party to fan out to find them.', 94, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Royal Tenenbaums', 'R', 'An estranged family of former child prodigies reunites when their father announces he is terminally ill.', 110, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Fantastic Mr. Fox', 'PG', 'An urbane fox cannot resist returning to his farm raiding ways and then must help his community survive the farmers retaliation.', 87, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Rushmore', 'R', 'The eccentric student Max Fischer and his friend Rosemary Cross develop a mutual affection for each other.', 93, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO screenings (movie_id, screen_number, capacity, starts_at, created_at, updated_at)
VALUES
    (1, 1, 100, '2023-06-09 18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, 80, '2023-06-09 19:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, 120, '2023-06-09 20:45:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, 90, '2023-06-09 21:15:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, 150, '2023-06-09 22:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tickets (screening_id, customer_id, num_seats, created_at, updated_at)
VALUES
    (1, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);