DROP TABLE IF EXISTS call_decisions;
DROP TABLE IF EXISTS simswap_events;
DROP TABLE IF EXISTS wangiri_events;
DROP TABLE IF EXISTS fraud_reports;
DROP TABLE IF EXISTS reputation;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE reputation (
    number VARCHAR(20) PRIMARY KEY,
    score INT NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE fraud_reports (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wangiri_events (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL,
    call_duration_seconds INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE simswap_events (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL,
    days_since_swap INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE call_decisions (
    id SERIAL PRIMARY KEY,
    caller VARCHAR(20) NOT NULL,
    final_score INT NOT NULL,
    action VARCHAR(20) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
