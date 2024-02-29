
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
(5, 'Company E');

-- Вставляем данные в таблицу person
INSERT INTO person (id, person_name, company_id) VALUES
(1, 'John', 1),
(2, 'Alice', 2),
(3, 'Bob', 1),
(4, 'Carol', 2),
(5, 'David', 1),
(6, 'Emma', 2),
(7, 'Frank', 3),
(8, 'Grace', 4),
(9, 'Henry', 5),
(10, 'Ivy', 5);

SELECT company_name, COUNT(p.id) AS person_count
FROM company c
LEFT JOIN person p ON c.id = p.company_id
GROUP BY c.id, c.company_name
HAVING COUNT(p.id) = (SELECT MAX(sub_count)
                      FROM (SELECT COUNT(id) AS sub_count
                            FROM person
                            GROUP BY company_id) AS subquery);


