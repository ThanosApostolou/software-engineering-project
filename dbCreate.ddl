CREATE TABLE prices (id int(11) NOT NULL, price double NOT NULL, `date` timestamp NOT NULL, product_id int(11) NOT NULL, shop_id int(11) NOT NULL, PRIMARY KEY (id, product_id, shop_id), INDEX (product_id), INDEX (shop_id));
CREATE TABLE product_tag_map (id int(11) NOT NULL, product_id int(11) NOT NULL, tag_id int(11) NOT NULL, PRIMARY KEY (id), INDEX (product_id), INDEX (tag_id));
CREATE TABLE products (id int(11) NOT NULL, name varchar(255) NOT NULL, description varchar(500), withdrawn int(1) DEFAULT 0 NOT NULL, category varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE shop_tag_map (id int(11) NOT NULL, shop_id int(11) NOT NULL, tag_id int(11) NOT NULL, PRIMARY KEY (id), INDEX (shop_id), INDEX (tag_id));
CREATE TABLE shops (id int(11) NOT NULL, name varchar(255) NOT NULL, address varchar(255) NOT NULL, withdrawn int(1) DEFAULT 0 NOT NULL, lat float, lng float, PRIMARY KEY (id));
CREATE TABLE tags (id int(11) NOT NULL, tag_name varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE users (id int(11) NOT NULL, username varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, password_hash varchar(255) NOT NULL UNIQUE, enabled int(1) DEFAULT 1 NOT NULL, admin int(1) DEFAULT 0 NOT NULL, PRIMARY KEY (id));
ALTER TABLE prices ADD CONSTRAINT FKprices743986 FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;
ALTER TABLE prices ADD CONSTRAINT FKprices505426 FOREIGN KEY (shop_id) REFERENCES shops (id) ON DELETE CASCADE;
ALTER TABLE product_tag_map ADD CONSTRAINT FKproduct_ta954681 FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;
ALTER TABLE product_tag_map ADD CONSTRAINT FKproduct_ta16777 FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE;
ALTER TABLE shop_tag_map ADD CONSTRAINT FKshop_tag_m721782 FOREIGN KEY (shop_id) REFERENCES shops (id) ON DELETE CASCADE;
ALTER TABLE shop_tag_map ADD CONSTRAINT FKshop_tag_m465535 FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE;

