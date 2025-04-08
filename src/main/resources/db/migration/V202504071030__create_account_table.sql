CREATE TABLE public.accounts
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id    VARCHAR(50) UNIQUE NOT NULL,
    balance DOUBLE
);