CREATE DATABASE IF NOT EXISTS laptop_dispatched;
USE laptop_dispatched;

CREATE TABLE IF NOT EXISTS dispatched_laptops (
    id INT AUTO_INCREMENT PRIMARY KEY,
    laptop_id INT NOT NULL,
    user_id INT NOT NULL,
    dispatched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (laptop_id) REFERENCES laptops_store.laptops(id),
    FOREIGN KEY (user_id) REFERENCES users.users(id)
);

-- No seed data for dispatched laptops as this will be populated through application logic when laptops are dispatched to customers.

