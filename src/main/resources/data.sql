INSERT INTO PERSON (user_name, email, password, role)
VALUES ( 'john_doe', 'john@example.com', '$2a$10$pY6sFIFfYBVPsS.bJgnENu4w2kjZgWQDYnHxs8A2DBRN.28X1LvEq', 'USER'),
       ('sugoi', 'sugoi@example.com', '$2a$10$HyI6ZSgBwZRa.L.Q74iTmuTIzevjIWOHDelIoVQfUmEgOoEdRPYcW', 'ADMIN'),
       ('alex_smith', 'alex@example.com', '$2a$10$XPLwWkW5veEBRdJB1pm6uONTGogxvvNkxdnL75mmLeeS8UbZ8UKf6!', 'USER'),
       ('emily_white', 'emily@example.com', '$2a$10$tywP8y4QajhGG7jS1HCuJ.rtTzZR1bWAacToKaatgI7ZHHNiv.6ui', 'USER');

-- john = password123
-- sugoi = secret
-- alex = alexPass
-- emily = emily123

INSERT INTO MOVIE (movie_title, movie_description, genre )
VALUES
    ('Fighter’s Edge', 'A disgraced boxer gets one last shot at the championship title after training in a remote mountain monastery.', 'ACTION'),
    ('The Lost City of Raan', 'An adventurer and her rival race against time to uncover an ancient map leading to a mythical, sunken civilization.', 'ADVENTURE'),
    ('The Widget World', 'A colorful, hand-drawn film about a group of tiny creatures who must save their clockwork town from a giant cog.', 'ANIMATION'),
    ('The Worst Wedding Ever', 'Two best friends try to sabotage the wedding of their former flame, leading to catastrophic and embarrassing events.', 'COMEDY'),
    ('The Silk Road Job', 'A meticulous account of a high-stakes jewel robbery pulled off by a former police consultant in Istanbul.', 'CRIME'),
    ('Silent Tears of War', 'The gripping story of a nurse who illegally shelters wounded enemy soldiers during a brutal winter campaign.', 'DRAMA'),
    ('Amulet of the Seven Kings', 'A high school outcast discovers he is the last descendant of an old magical lineage and must defend the realm.', 'FANTASY'),
    ('The Iron Horse Promise', 'A sweeping period piece centered on the difficult and dangerous construction of the first transcontinental railway.', 'HISTORICAL'),
    ('The Shivering Truth', 'A group of college students breaks into an abandoned psychiatric hospital to prove a legend, only to find the horror is real.', 'HORROR'),
    ('Rhythm of the Rain', 'A vibrant tale of two dancers from rival schools who find common ground and love while preparing for a major competition.', 'MUSICAL');

INSERT INTO SHOW_TIME (start_time, stop_time, price, movie_id)
VALUES
    (1300, 1500, 200, 1),
    (1800, 2000, 250, 1),
    (1400, 1600, 220, 2),
    (1900, 2100, 300, 2),
    (1200, 1400, 180, 3),
    (1600, 1800, 230, 3),
    (1000, 1200, 150, 4),
    (1500, 1700, 200, 5),
    (1900, 2100, 280, 6),
    (2200, 0000, 250, 7);
