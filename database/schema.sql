CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100)
);

CREATE TABLE reports (
    id SERIAL PRIMARY KEY,
    reported_number VARCHAR(20) NOT NULL,
    report_type VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reputation (
    number VARCHAR(20) PRIMARY KEY,
    score INT NOT NULL,
    status VARCHAR(20) NOT NULL
);
