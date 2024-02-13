-- Insert dummy movies
INSERT INTO movies
	(title, rating, description, runtime_minutes, created_at, updated_at)
VALUES
  ('The Batman', 'PG-13', 'A new take on the Batman story, focusing on his early years as a vigilante in Gotham City.', 175, NOW(), NOW()),
  ('Spider-Man: No Way Home', 'PG-13', 'Spider-Man grapples with the consequences of revealing his identity to the world.', 148, NOW(), NOW()),
  ('Dune', 'PG-13', 'A young nobleman embarks on an epic journey to protect his planet from a malevolent force.', 155, NOW(), NOW()),
  ('Black Widow', 'PG-13', 'Natasha Romanoff confronts the darker parts of her ledger when a dangerous conspiracy arises.', 134, NOW(), NOW()),
  ('The Matrix Resurrections', 'R', 'Neo returns to the Matrix, where he learns the truth about his reality.', 148, NOW(), NOW());

-- Insert dummy customers
INSERT INTO customers
  (name, email, phone, created_at, updated_at)
VALUES
  ('John Doe', 'john.doe@example.com', '+1234567890', NOW(), NOW()),
  ('Jane Smith', 'jane.smith@example.com', '+1987654321', NOW(), NOW()),
  ('Michael Johnson', 'michael.johnson@example.com', '+1122334455', NOW(), NOW()),
  ('Emily Brown', 'emily.brown@example.com', '+1555666777', NOW(), NOW()),
  ('David Wilson', 'david.wilson@example.com', '+1444555666', NOW(), NOW());

-- Insert dummy screenings for "The Batman"
INSERT INTO screenings
	(movie_id, screen_number, starts_at, capacity, created_at, updated_at)
 VALUES
  (1, 1, '2024-02-15 18:00:00', 100, NOW(), NOW()), -- Screening for "The Batman" on screen 1
  (1, 2, '2024-02-15 20:30:00', 120, NOW(), NOW()), -- Screening for "The Batman" on screen 2
  (1, 3, '2024-02-16 19:00:00', 80, NOW(), NOW()),  -- Screening for "The Batman" on screen 3

-- Insert dummy screenings for "Spider-Man: No Way Home"
INSERT INTO screenings
	(movie_id, screen_number, starts_at, capacity, created_at, updated_at)
VALUES
  (2, 1, '2024-02-15 19:30:00', 100, NOW(), NOW()), -- Screening for "Spider-Man: No Way Home" on screen 1
  (2, 2, '2024-02-15 22:00:00', 120, NOW(), NOW()), -- Screening for "Spider-Man: No Way Home" on screen 2
  (2, 3, '2024-02-16 20:30:00', 80, NOW(), NOW());  -- Screening for "Spider-Man: No Way Home" on screen 3

-- Insert dummy screenings for "Dune"
INSERT INTO screenings
	(movie_id, screen_number, starts_at, capacity, created_at, updated_at)
VALUES
  (3, 1, '2024-02-16 17:00:00', 100, NOW(), NOW()), -- Screening for "Dune" on screen 1
  (3, 2, '2024-02-16 19:30:00', 120, NOW(), NOW()), -- Screening for "Dune" on screen 2
  (3, 3, '2024-02-17 18:00:00', 80, NOW(), NOW());  -- Screening for "Dune" on screen 3

-- Insert dummy screenings for "Black Widow"
INSERT INTO screenings
	(movie_id, screen_number, starts_at, capacity, created_at, updated_at)
VALUES
  (4, 1, '2024-02-16 20:00:00', 100, NOW(), NOW()), -- Screening for "Black Widow" on screen 1
  (4, 2, '2024-02-17 22:30:00', 120, NOW(), NOW()), -- Screening for "Black Widow" on screen 2
  (4, 3, '2024-02-18 19:30:00', 80, NOW(), NOW());  -- Screening for "Black Widow" on screen 3

-- Insert dummy screenings for "The Matrix Resurrections"
INSERT INTO screenings
	(movie_id, screen_number, starts_at, capacity, created_at, updated_at)
VALUES
(5, 1, '2024-02-17 18:30:00', 100, NOW(), NOW()), -- Screening for "The Matrix Resurrections" on screen 1
(5, 2, '2024-02-17 21:00:00', 120, NOW(), NOW()), -- Screening for "The Matrix Resurrections" on screen 2
(5, 3, '2024-02-18 20:30:00', 80, NOW(), NOW());  -- Screening for "The Matrix Resurrections" on screen 3

INSERT INTO tickets
	(customer_id, screen_id, num_seats, created_at, updated_at)
VALUES
  (1, 1, 2, NOW(), NOW()), -- Ticket for customer 1, screening 1 with 2 seats
  (2, 2, 3, NOW(), NOW()), -- Ticket for customer 2, screening 2 with 3 seats
  (3, 3, 1, NOW(), NOW()), -- Ticket for customer 3, screening 3 with 1 seat
  (1, 4, 4, NOW(), NOW()), -- Ticket for customer 1, screening 4 with 4 seats
  (2, 5, 2, NOW(), NOW()); -- Ticket for customer 2, screening 5 with 2 seats


