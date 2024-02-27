CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    vacancy_name TEXT,
    vacancy_text TEXT,
    link TEXT UNIQUE,

    created timestamp
);