CREATE TABLE Admin (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE Product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         brand VARCHAR(50) NOT NULL,
                         price DECIMAL(12,2) NOT NULL,
                         stock INT NOT NULL
);

CREATE TABLE Customer (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          phone VARCHAR(20),
                          email VARCHAR(100) UNIQUE,
                          address VARCHAR(255)
);

CREATE TABLE Invoice (
                         id SERIAL PRIMARY KEY,
                         customer_id INT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         total_amount DECIMAL(12,2) NOT NULL,
                         CONSTRAINT fk_invoice_customer FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

CREATE TABLE Invoice_details (
                                 id SERIAL PRIMARY KEY,
                                 invoice_id INT,
                                 product_id INT,
                                 quantity INT NOT NULL,
                                 unit_price DECIMAL(12,2) NOT NULL,
                                 CONSTRAINT fk_detail_invoice FOREIGN KEY (invoice_id) REFERENCES Invoice(id),
                                 CONSTRAINT fk_detail_product FOREIGN KEY (product_id) REFERENCES Product(id)
);