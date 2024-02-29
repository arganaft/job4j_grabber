
CREATE TABLE company
(
    id integer NOT NULL,
    company_name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    person_name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company (id, company_name) VALUES
(1, 'Company A'),
(2, 'Company B'),
(3, 'Company C'),
(4, 'Company D'),
(5, 'Company E'),
(6, 'Company F'),
(7, 'Company G'),
(8, 'Company H'),
(9, 'Company I'),
(10, 'Company J');

-- Вставляем данные в таблицу person
INSERT INTO person (id, person_name, company_id) VALUES
(1, 'John', 1),
(2, 'Alice', 2),
(3, 'Bob', 3),
(4, 'Carol', 4),
(5, 'David', 5),
(6, 'Emma', 6),
(7, 'Frank', 7),
(8, 'Grace', 8),
(9, 'Henry', 9),
(10, 'Ivy', 10);

select person.person_name, company.company_name from person LEFT OUTER JOIN company on company.id = person.company_id where company.id != 5
;