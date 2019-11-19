
CREATE TABLE user (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		name TEXT NOT NULL,
		surname TEXT NOT NULL,
		phone_number TEXT NOT NULL,
		email TEXT NOT NULL,
		registration NUMERIC NOT NULL DEFAULT (date('now')));


CREATE TABLE wishlist (
		user_id INTEGER references user(id) NOT NULL,
		item TEXT NOT NULL);


CREATE TABLE following (
		user_id INTEGER references user(id) NOT NULL,
		following_user_id INTEGER references user(id) NOT NULL);


CREATE TABLE important_date (
		user_id INTEGER references user(id) NOT NULL,
		event_id INTEGER references event(id) NOT NULL,
		date NUMERIC NOT NULL);

CREATE TABLE event (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		title TEXT NOT NULL);

INSERT INTO user (name, surname, phone_number, email) VALUES
	('Vladyslav', 'Kharchenko', '+380958752293', 'arkenstone@ex.ua');

SELECT name AS 'User name', date AS 'Date of registration' 
	FROM user JOIN event ON user.id = event.user_id;

UPDATE sqlite_sequence SET seq = 0 WHERE name = 'user';


INSERT INTO EVENT (title) VALUES ('Birthday');
INSERT INTO EVENT (title) VALUES ('Wedding anniversary');
INSERT INTO EVENT (title) VALUES ('Getting engaged');
INSERT INTO EVENT (title) VALUES ('Graduating from school');
INSERT INTO EVENT (title) VALUES ('Falling in love for the first time');
