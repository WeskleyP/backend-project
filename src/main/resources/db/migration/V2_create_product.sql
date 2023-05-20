CREATE TABLE IF NOT EXISTS product(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name text,
	stock_quantity INT,
	unit_price DECIMAL(10,2),
	supplier_id int,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	foreign key (supplier_id) references supplier (id)
);