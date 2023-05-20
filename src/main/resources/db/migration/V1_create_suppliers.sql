CREATE TABLE IF NOT EXISTS supplier(
	id INT PRIMARY KEY,
	name text,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT into supplier (id, name) values (1, 'Supplier 1');
INSERT into supplier (id, name) values (2, 'Supplier 2');
INSERT into supplier (id, name) values (3, 'Supplier 3');
INSERT into supplier (id, name) values (4, 'Supplier 4');
INSERT into supplier (id, name) values (5, 'Supplier 5');