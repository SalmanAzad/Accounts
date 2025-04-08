CREATE TABLE public.users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(50) NOT NULL,
    name        VARCHAR(100),
    surname     VARCHAR(100)
);