CREATE TABLE IF NOT EXISTS movie(
id SERIAL PRIMARY KEY,
title TEXT NOT NULL,
rating TEXT,
description TEXT,
runtimeMins INTEGER,
createdAt DATE,
updatedAt DATE
);

CREATE TABLE IF NOT EXISTS screening(
id SERIAL PRIMARY KEY,
screenNumber INTEGER NOT NULL,
startsAt DATE,
capacity INTEGER,
createdAt DATE,
updatedAt DATE
);

CREATE TABLE IF NOT EXISTS customer(
id SERIAL PRIMARY KEY,
name TEXT NOT NULL,
email TEXT,
phone TEXT,
createdAt DATE,
updatedAt DATE
);