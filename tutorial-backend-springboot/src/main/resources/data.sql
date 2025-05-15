INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES ('Juan Carlos');
INSERT INTO client(name) VALUES ('Fabian Montero');
INSERT INTO client(name) VALUES ('Pol Fernandez');
INSERT INTO client(name) VALUES ('Toni Pares');

INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (1, 2, '2025-04-04', '2025-04-10');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (4, 4, '2025-04-02', '2025-04-16');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (6, 1, '2025-03-29', '2025-04-04');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (2, 1, '2025-04-10', '2025-04-18');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (1, 3, '2025-04-11', '2025-04-20');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (3, 2, '2025-04-14', '2025-04-24');