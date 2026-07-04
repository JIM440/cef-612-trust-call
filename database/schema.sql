DROP TABLE IF EXISTS decision_history;
DROP TABLE IF EXISTS wangiri_events;
DROP TABLE IF EXISTS sim_flags;
DROP TABLE IF EXISTS call_logs;
DROP TABLE IF EXISTS fraud_reports;
DROP TABLE IF EXISTS reputation;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE fraud_reports (
    id SERIAL PRIMARY KEY,
    reported_number VARCHAR(20) NOT NULL,
    report_type VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wangiri_events (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL,
    event_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sim_flags (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL,
    risk_level VARCHAR(20) DEFAULT 'LOW',
    reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE decision_history (
    id SERIAL PRIMARY KEY,
    caller VARCHAR(20) NOT NULL,
    callee VARCHAR(20) NOT NULL,
    decision VARCHAR(20) NOT NULL,
    reason TEXT,
    final_score INT,
    source VARCHAR(50) DEFAULT 'OpenSIPS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
