CREATE TABLE IF NOT EXISTS call_decisions (
    id SERIAL PRIMARY KEY,
    caller VARCHAR(30) NOT NULL,
    callee VARCHAR(30) NOT NULL,
    decision VARCHAR(20) NOT NULL,
    reason TEXT,
    source VARCHAR(30) DEFAULT 'trustcall-core',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
