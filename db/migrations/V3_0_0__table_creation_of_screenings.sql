CREATE TABLE IF NOT EXISTS screenings(
    screening_id SERIAL PRIMARY KEY,
    screen_number INTEGER,
    capacity INTEGER,
    starts_at TEXT,
	created_at TEXT,
	updated_at TEXT
);