create database sistemaneurodivergente;
use sistemaneurodivergente;

CREATE TABLE acompanhamento (
    id VARCHAR(36) NOT NULL,
    data_inicio VARCHAR(10),
    data_fim VARCHAR(10),
    objetivo TEXT,
    status VARCHAR(50),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

