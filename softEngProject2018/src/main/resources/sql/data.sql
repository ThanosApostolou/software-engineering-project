USE `softeng2018`;

INSERT INTO products (name, description, withdrawn, category) VALUES
('Riceland', 'Riceland American Jazmine Rice', 0, 'Food'),
('Caress', 'Caress Velvet Bliss Ultra Silkening Beauty Bar - 6 Ct', 0, 'Beauty Product'),
('Fage', 'Earths Best Organic Fruit Yogurt Smoothie Mixed Berry', 0 ,'Food'),
('Boar', 'Boars Head Sliced White American Cheese - 120 Ct', 0, 'Food'),
('SuperYogurt', 'Super delicious yogurt', 0 ,'Food'),
('BananaDream', 'Best banana juice in the world', 0 ,'Food'),
('AntiWrinkle', 'Gets rid of wrinkles', 0 ,'Beauty Product'),
('Clinex', 'Quick Shine', 0 ,'Cleaning Product'),
('Φέτα Ήπειρος', 'Φέτα Ήπειρος όχι απλά Ηπείρου', 0 ,'Φαγητό');

INSERT INTO product_tags (product_id, tags) VALUES
(1,'tag1'),(1,'tag2'),(2,'tag3'),(2,'tag4'),(3,'tag5'),
(4,'tag6'),(5,'tag6'),(6,'tag3'),(7,'tag2'),(7,'tag6'),(8,'tag4'),(9,'tag1');

INSERT into shops (name, address, withdrawn, coordinates) VALUES
('e-shop', 'Leof. Galatsiou 32', 0, ST_GEOMETRYFROMTEXT('POINT (38.015460 23.740750)',  4326 )),
('e-click', 'Leof. Galatsiou 61', 0, ST_GEOMETRYFROMTEXT('POINT (38.015499 23.743897)',  4326 ));

#INSERT into shops (name, address, withdrawn, coordinates) VALUES ('shop', 'Leof. Galatsiou 35', 0,
#                                                                  ST_GEOMETRYFROMTEXT('POINT (1 2)',0 ));

INSERT INTO `shop_tags` (shop_id, tags) VALUES
(1,'tag1'),(1,'tag2'),(2,'tag3'),(2,'tag4');

INSERT INTO users (username, email, password_hash, admin) VALUES
('thanos', 'thanos', '$2a$10$Z7a31jVmH1Y3rhGVcLF4Nu5PmzilazoHCqm9DdvC7gu/gM68.MHY6', 1),
('daphne', 'daphne', '$2a$10$9nu4sMOiz04BVzAhsjRYqOTyb1XylZj4A77TBmFZC5rpa2iwRFp6a', 1),
('mike', 'mike', '$2a$10$blstjQHisMSjZJIDFe2HSevwTRVzpvpM2fiGiUphCt1QAPjoOiG6S', 1),
('tsipras', 'tsipras', '$2a$10$tLfU50xot5N2HF3zkAAXjevMD4zOOLtiSRGFhiv/RwcvMOMj0JrI2', 0),
('paliatsos', 'paliatsos', '$2a$10$5B9MivD5d/lTQKBwVLBVy.pSTb9AvBMSX6PqKon4gDIXeDKqDAxju', 0);

INSERT INTO prices (price, date, product_id, shop_id) VALUES (25, '2015-04-11',1,1), (30, '2016-04-11',1,2),
                                                             (40, '2017-05-13',2,2), (50, '2018-04-11',2,1);
