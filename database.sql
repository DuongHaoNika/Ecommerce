CREATE DATABASE shopapp;
USE shopapp;

CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
)

ALTER TABLE users ADD COLUMN role_id INT;

CREATE TABLE roles(
    id INT PRIMARY KEY,
    name varchar(20) NOT NULL
)

ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id);

CREATE TABLE tokens(
    id int PRIMARY KEY AUTO_INCREMENT,
    token varchar(255) UNIQUE NOT NULL ,
    token_type varchar(50) NOT NULL ,
    expiration_date DATETIME,
    revoked tinyint(1) NOT NULL,
    expired tinyint(1) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
)

CREATE TABLE social_accounts(
    id int PRIMARY KEY AUTO_INCREMENT,
    provider varchar(20) NOT NULL COMMENT 'Name of social network',
    provider_id varchar(50) NOT NULL,
    email varchar(150) NOT NULL,
    name varchar(100) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
)

-- Danh muc san pham, vd: laptop
CREATE TABLE categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL DEFAULT ''
)

CREATE TABLE products(
     id int PRIMARY KEY AUTO_INCREMENT,
     name varchar(255),
     price FLOAT NOT NULL CHECK(price >= 0),
     url varchar(255) default '',
     description LONGTEXT,
     created_at DATETIME,
     updated_at DATETIME,
     category_id int,
     FOREIGN KEY (category_id) references categories(id)
)

CREATE TABLE product_images(
    id int PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_product_image_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
)

CREATE TABLE orders(
    id int PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname varchar(100) default '',
    email varchar(100) default '',
    phone_number varchar(20) NOT NULL,
    address varchar(200) NOT NULL ,
    note varchar(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status varchar(20),
    total_money float check(total_money >= 0)
)

ALTER TABLE orders ADD COLUMN `shipping_method` varchar(100);
ALTER TABLE orders ADD COLUMN `shipping_address` varchar(200);
ALTER TABLE orders ADD COLUMN `shipping_date` DATE;
ALTER TABLE orders ADD COLUMN `tracking_number` varchar(100);
ALTER TABLE orders ADD COLUMN `payment_method` varchar(100);
ALTER TABLE orders ADD COLUMN active TINYINT(1);
ALTER TABLE orders MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled');

CREATE TABLE order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    price FLOAT CHECK(price >= 0),
    quantity INT CHECK(quantity > 0),
    total_money float check(total_money >= 0),
    color varchar(20) DEFAULT ''
)





