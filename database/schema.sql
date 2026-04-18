-- TechPod Database — run this after Spring Boot creates the tables
CREATE DATABASE IF NOT EXISTS techpod;
USE techpod;

-- Seed: Admin (password = admin123)
INSERT IGNORE INTO users (first_name, last_name, email, password, role, created_at)
VALUES ('Admin', 'TechPod', 'admin@techpod.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LjTsyXwT8Z6', 'ADMIN', NOW());

-- Seed: Dispatcher (password = admin123)
INSERT IGNORE INTO users (first_name, last_name, email, password, role, created_at)
VALUES ('Dispatch', 'Manager', 'dispatch@techpod.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LjTsyXwT8Z6', 'DISPATCHER', NOW());

-- Seed: Sample laptops
INSERT IGNORE INTO laptops (brand, model, processor, ram_gb, storage, gpu, screen_size_inch, price, stock_quantity, image_url, description, created_at) VALUES
('Apple', 'MacBook Pro 14"', 'Apple M3 Pro', 18, '512GB SSD', 'M3 Pro 18-core GPU', 14.2, 1999.99, 25, 'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/mbp14-spacegray-select-202310', 'The most powerful MacBook Pro ever with M3 Pro chip.', NOW()),
('Apple', 'MacBook Air 15"', 'Apple M3', 8, '256GB SSD', 'M3 10-core GPU', 15.3, 1299.99, 40, 'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/mba15-midnight-select-202306', 'Supercharged by M3. Incredibly thin and light.', NOW()),
('Dell', 'XPS 15', 'Intel Core i7-13700H', 16, '512GB SSD', 'NVIDIA RTX 4060', 15.6, 1749.99, 15, 'https://i.dell.com/is/image/DellContent/content/dam/ss2/product-images/dell-client-products/notebooks/xps-notebooks/xps-15-9530/media-gallery/black/notebook-xps-15-9530-black-gallery-1.psd', 'Stunning OLED display meets powerful performance.', NOW()),
('Dell', 'XPS 13', 'Intel Core i5-1340P', 16, '512GB SSD', 'Intel Iris Xe', 13.4, 1199.99, 20, 'https://i.dell.com/is/image/DellContent/content/dam/ss2/product-images/dell-client-products/notebooks/xps-notebooks/xps-13-9340/media-gallery/gray/notebook-xps-13-9340-gray-gallery-1.psd', 'Iconic ultrabook design, now even more powerful.', NOW()),
('Lenovo', 'ThinkPad X1 Carbon', 'Intel Core i7-1365U', 16, '1TB SSD', 'Intel Iris Xe', 14.0, 1499.99, 30, 'https://p3-ofp.static.pub/fes/cms/2022/09/08/ofp.pictures.lenovo.com/p430-ideapad-5-14-intel-gallery-1.png', 'Business ultrabook with legendary ThinkPad reliability.', NOW()),
('Lenovo', 'Legion 5 Pro', 'AMD Ryzen 7 7745HX', 32, '1TB SSD', 'NVIDIA RTX 4070', 16.0, 1599.99, 18, 'https://p3-ofp.static.pub/fes/cms/2022/09/08/ofp.pictures.lenovo.com/p430-ideapad-5-14-intel-gallery-1.png', 'Dominate every game with RTX 4070 graphics.', NOW()),
('HP', 'Spectre x360 14"', 'Intel Core i7-1355U', 16, '1TB SSD', 'Intel Iris Xe', 14.0, 1399.99, 22, 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/knightley/c08233956.png', 'Premium 2-in-1 convertible with OLED display.', NOW()),
('ASUS', 'ROG Zephyrus G14', 'AMD Ryzen 9 7940HS', 32, '1TB SSD', 'NVIDIA RTX 4090', 14.0, 2499.99, 10, 'https://dlcdnwebimgs.asus.com/gain/a9cf2b34-a2fe-44dc-9dcf-29960a380caf/w717/h525', 'The ultimate compact gaming powerhouse.', NOW()),
('Microsoft', 'Surface Laptop 5', 'Intel Core i5-1245U', 8, '512GB SSD', 'Intel Iris Xe', 13.5, 1099.99, 35, 'https://img-prod-cms-rt-microsoft-com.akamaized.net/cms/api/am/imageFileData/RE4OXiR', 'Sleek and fast. Runs Windows 11 beautifully.', NOW());
