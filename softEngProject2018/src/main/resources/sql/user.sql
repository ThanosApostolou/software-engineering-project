USE `softeng2018`;
CREATE USER 'user' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON softeng2018.* TO 'user';
FLUSH PRIVILEGES;