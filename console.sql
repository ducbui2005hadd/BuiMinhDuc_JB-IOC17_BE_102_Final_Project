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
INSERT INTO product (name, brand, price, stock) VALUES
                                                    ('iPhone 15 Pro Max 256GB', 'Apple', 29990000, 50),
                                                    ('iPhone 15 128GB', 'Apple', 19990000, 100),
                                                    ('iPhone 14 Pro Max', 'Apple', 24500000, 30),
                                                    ('iPhone 13 128GB', 'Apple', 13990000, 45),
                                                    ('Samsung Galaxy S24 Ultra', 'Samsung', 28990000, 40),
                                                    ('Samsung Galaxy S24 Plus', 'Samsung', 21990000, 60),
                                                    ('Samsung Galaxy Z Fold5', 'Samsung', 34990000, 15),
                                                    ('Samsung Galaxy Z Flip5', 'Samsung', 18990000, 25),
                                                    ('Samsung Galaxy A54 5G', 'Samsung', 7990000, 120),
                                                    ('Samsung Galaxy A34 5G', 'Samsung', 5990000, 100),
                                                    ('Xiaomi 14 Ultra', 'Xiaomi', 24990000, 20),
                                                    ('Xiaomi 14', 'Xiaomi', 16990000, 35),
                                                    ('Redmi Note 13 Pro', 'Xiaomi', 6490000, 80),
                                                    ('Redmi Note 13', 'Xiaomi', 4290000, 150),
                                                    ('OPPO Find N3 Flip', 'OPPO', 22990000, 10),
                                                    ('OPPO Reno11 Pro', 'OPPO', 11990000, 50),
                                                    ('OPPO Reno11 F', 'OPPO', 8990000, 60),
                                                    ('OPPO A78', 'OPPO', 5490000, 90),
                                                    ('Realme 11 Pro Plus', 'Realme', 9490000, 40),
                                                    ('Vivo V30e 5G', 'Vivo', 8490000, 55);

INSERT INTO customer (name, phone, email, address) VALUES
                                                       ('Nguyen Van An', '0901234567', 'an.nguyen@gmail.com', '123 Le Loi, Quan 1, TP.HCM'),
                                                       ('Tran Thi Bich', '0912345678', 'bich.tran@hotmail.com', '456 Tran Hung Dao, Quan 5, TP.HCM'),
                                                       ('Le Van Cuong', '0987654321', 'cuong.le@yahoo.com', '789 Nguyen Trai, Ha Noi'),
                                                       ('Pham Thi Dung', '0933445566', 'dung.pham@outlook.com', '321 Cau Giay, Ha Noi'),
                                                       ('Hoang Van Em', '0977889900', 'em.hoang@gmail.com', '12 Nguyen Van Cu, Da Nang'),
                                                       ('Vo Thi Gai', '0966778899', 'gai.vo@gmail.com', '34 Le Duan, Da Nang'),
                                                       ('Dang Van Hung', '0955443322', 'hung.dang@gmail.com', '56 Ly Thuong Kiet, Hue'),
                                                       ('Bui Thi Hoa', '0944332211', 'hoa.bui@gmail.com', '78 Tran Phu, Nha Trang'),
                                                       ('Do Van Khanh', '0922113344', 'khanh.do@gmail.com', '90 Nguyen Thi Minh Khai, Can Tho'),
                                                       ('Ngo Thi Lan', '0999888777', 'lan.ngo@gmail.com', '11 Vo Van Kiet, Can Tho'),
                                                       ('Vu Van Minh', '0909090909', 'minh.vu@gmail.com', '22 Hai Ba Trung, Hai Phong'),
                                                       ('Duong Thi Ngoc', '0911223344', 'ngoc.duong@gmail.com', '33 Le Lai, Hai Phong'),
                                                       ('Ly Van Nam', '0988776655', 'nam.ly@gmail.com', '44 Hung Vuong, Quang Ninh'),
                                                       ('Mai Thi Oanh', '0976543210', 'oanh.mai@gmail.com', '55 Tran Hung Dao, Nam Dinh'),
                                                       ('Trinh Van Phuc', '0965432109', 'phuc.trinh@gmail.com', '66 Le Hong Phong, Vung Tau'),
                                                       ('Cao Thi Quyen', '0954321098', 'quyen.cao@gmail.com', '77 Nguyen Van Linh, Da Nang'),
                                                       ('Phan Van Son', '0943210987', 'son.phan@gmail.com', '88 Dien Bien Phu, TP.HCM'),
                                                       ('Lam Thi Tuyet', '0932109876', 'tuyet.lam@gmail.com', '99 Cach Mang Thang 8, TP.HCM'),
                                                       ('Ha Van Uy', '0921098765', 'uy.ha@gmail.com', '101 Nguyen Hue, TP.HCM'),
                                                       ('Ta Thi Van', '0910987654', 'van.ta@gmail.com', '202 Pasteur, TP.HCM');

INSERT INTO invoice (customer_id, created_at, total_amount) VALUES (1, NOW() - INTERVAL '2 days', 19990000);
INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES (1, 2, 1, 19990000);

INSERT INTO invoice (customer_id, created_at, total_amount) VALUES (2, NOW() - INTERVAL '1 day', 28990000);
INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES (2, 5, 1, 28990000);

INSERT INTO invoice (customer_id, created_at, total_amount) VALUES (3, NOW(), 8580000);
INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES (3, 14, 2, 4290000);