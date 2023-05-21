CREATE TABLE IF NOT EXISTS product(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name text not null,
	stock_quantity INT not null,
	unit_price DECIMAL(10,2) not null,
	supplier_id int not null,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	foreign key (supplier_id) references supplier (id)
);