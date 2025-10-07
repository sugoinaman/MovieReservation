INSERT INTO PERSON (id, user_name, email, password, role)
VALUES (1, 'john_doe', 'john@example.com', '$2a$10$pY6sFIFfYBVPsS.bJgnENu4w2kjZgWQDYnHxs8A2DBRN.28X1LvEq', 'USER'),
       (2, 'sugoi', 'jane@example.com', '$2a$10$HyI6ZSgBwZRa.L.Q74iTmuTIzevjIWOHDelIoVQfUmEgOoEdRPYcW', 'ADMIN'),
       (3, 'alex_smith', 'alex@example.com', '$2a$10$XPLwWkW5veEBRdJB1pm6uONTGogxvvNkxdnL75mmLeeS8UbZ8UKf6!', 'USER'),
       (4, 'emily_white', 'emily@example.com', '$2a$10$tywP8y4QajhGG7jS1HCuJ.rtTzZR1bWAacToKaatgI7ZHHNiv.6ui', 'USER');

-- john = password123
-- sugoi = secret
-- alex = alexPass
-- emily = emily123