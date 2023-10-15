CREATE TABLE IF NOT EXISTS screenings (
    id SERIAL PRIMARY KEY,
    screen_number INT NOT NULL,
    capacity INT NOT NULL,
    starts_at TIMESTAMP NOT NULL,
    created_at TIMESTAMPZ NOT NULL,
    updated_at TIMESTAMPZ NOT NULL,
    movie_id INT NOT NULL,
    CONSTRAINT fk_movie_id
        FOREIGN KEY (movie_id)
            REFERENCES movies(id)
);