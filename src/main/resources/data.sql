INSERT INTO account (id, account_number, balance, recipient_name, pin_code) VALUES
(1, '1234567890', 1000.00, 'Ivan Ivanov', '1234'),
(2, '0987654321', 2000.00, 'Den Krylov', '5678');

INSERT INTO transaction (id, account_id, amount, type, timestamp) VALUES
(1, 1, 1000.00, 'DEPOSIT', NOW()),
(2, 2, 2000.00, 'DEPOSIT', NOW());