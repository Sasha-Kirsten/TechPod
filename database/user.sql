CREATE DATABASE IF NOT EXISTS users;

USE users;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NULL,
    role ENUM('CUSTOMER', 'ADMIN', 'DISPATCHER') NOT NULL,
    provider VARCHAR(50) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT IGNORE INTO users (first_name, last_name, email, password, role, provider, created_at) 
VALUES('Alice', 'Smith', 'alice.smith@techpod.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LjTsyXwT8Z6', 'CUSTOMER', 'USER', NOW()),
        ('Bob', 'Johnson', 'bob.johnson@techpod.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LjTsyXwT8Z6', 'CUSTOMER', 'USER', NOW());