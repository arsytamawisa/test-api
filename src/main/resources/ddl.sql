CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    profile_image VARCHAR(500) NULL,
    balance BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_email (email),
    INDEX idx_user_created (created_at)
);


CREATE TABLE services (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    service_code VARCHAR(50) UNIQUE NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    service_icon VARCHAR(500) NOT NULL,
    service_tariff BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_service_code (service_code),
    INDEX idx_service_name (service_name)
);


CREATE TABLE banners (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    banner_name VARCHAR(255) NOT NULL,
    banner_image VARCHAR(500) NOT NULL,
    description TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_banner_created (created_at)
);


CREATE TABLE transactions (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    invoice_number VARCHAR(100) UNIQUE NOT NULL,
    transaction_type ENUM('PAYMENT', 'TOPUP') NOT NULL,
    description VARCHAR(500) NOT NULL,
    total_amount BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(36) NOT NULL,
    service_id VARCHAR(36) NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE SET NULL,
    INDEX idx_transaction_user (user_id),
    INDEX idx_transaction_invoice (invoice_number),
    INDEX idx_transaction_created (created_at),
    INDEX idx_transaction_type (transaction_type)
);


INSERT INTO users (id, email, password, first_name, last_name, balance) VALUES
('usr-001', 'user@example.com', '$2a$10$hashedpassword', 'John', 'Doe', 50000),
('usr-002', 'test2@example.com', '$2a$10$hashedpassword', 'Test2', 'User2', 100000);


INSERT INTO services (id, service_code, service_name, service_icon, service_tariff) VALUES
('srv-001', 'PAJAK', 'Pajak PBB', 'https://nutech-integrasi.app/dummy.jpg', 40000),
('srv-002', 'PLN', 'Listrik', 'https://nutech-integrasi.app/dummy.jpg', 10000),
('srv-003', 'PDAM', 'Air PDAM', 'https://nutech-integrasi.app/dummy.jpg', 40000),
('srv-004', 'PULSA', 'Pulsa', 'https://nutech-integrasi.app/dummy.jpg', 10000),
('srv-005', 'PGN', 'Gas PGN', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('srv-006', 'MUSIK', 'Musik', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('srv-007', 'TV', 'TV Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('srv-008', 'PAKET_DATA', 'Paket Data', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('srv-009', 'VOUCHER_GAME', 'Voucher Game', 'https://nutech-integrasi.app/dummy.jpg', 100000),
('srv-010', 'VOUCHER_MAKANAN', 'Voucher Makanan', 'https://nutech-integrasi.app/dummy.jpg', 100000),
('srv-011', 'QURBAN', 'Qurban', 'https://nutech-integrasi.app/dummy.jpg', 200000),
('srv-012', 'ZAKAT', 'Zakat', 'https://nutech-integrasi.app/dummy.jpg', 300000);


INSERT INTO banners (id, banner_name, banner_image, description) VALUES
('bnr-001', 'Banner 1', 'https://nutech-integrasi.app/banner1.jpg', 'Description for banner 1'),
('bnr-002', 'Banner 2', 'https://nutech-integrasi.app/banner2.jpg', 'Description for banner 2'),
('bnr-003', 'Banner 3', 'https://nutech-integrasi.app/banner3.jpg', 'Description for banner 3'),
('bnr-004', 'Banner 4', 'https://nutech-integrasi.app/banner4.jpg', 'Description for banner 4'),
('bnr-005', 'Banner 5', 'https://nutech-integrasi.app/banner5.jpg', 'Description for banner 5'),
('bnr-006', 'Banner 6', 'https://nutech-integrasi.app/banner6.jpg', 'Description for banner 6');


INSERT INTO transactions (id, invoice_number, transaction_type, description, total_amount, user_id, service_id) VALUES
('trx-001', 'INV-001', 'TOPUP', 'Top Up balance', 100000, 'usr-001', NULL),
('trx-002', 'INV-002', 'PAYMENT', 'Pembayaran Pajak PBB', 40000, 'usr-001', 'srv-001'),
('trx-003', 'INV-003', 'PAYMENT', 'Pembayaran Listrik', 10000, 'usr-002', 'srv-002');