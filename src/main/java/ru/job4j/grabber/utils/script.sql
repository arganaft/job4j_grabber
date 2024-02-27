CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    vacancy_name VARCHAR(255),
    vacancy_text TEXT,
    link TEXT,
    created DATE
);