CREATE TABLE IF NOT EXISTS movie(
id SERIAL PRIMARY KEY,
title TEXT NOT NULL,
rating TEXT,
description TEXT,
runtime_mins INTEGER,
created_at TIMESTAMP,
updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS screening(
id SERIAL PRIMARY KEY,
screen_number INTEGER NOT NULL,
starts_at TIMESTAMP,
capacity INTEGER,
created_at TIMESTAMP,
updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customer(
id SERIAL PRIMARY KEY,
name TEXT NOT NULL,
email TEXT,
phone TEXT,
created_at TIMESTAMP,
updated_at TIMESTAMP
);