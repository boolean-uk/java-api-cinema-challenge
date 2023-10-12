CREATE TABLE IF NOT EXISTS movies(
movies_id SERIAL PRIMARY KEY,
title TEXT,
rating TEXT,
description TEXT,
runtimeMins INTEGER,
created_at TEXT,
updated_at TEXT
);