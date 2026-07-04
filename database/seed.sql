INSERT INTO users (phone_number, name) VALUES
('650100001', 'Trusted Subscriber'),
('650100002', 'Reported Subscriber'),
('650100003', 'High Risk Subscriber');

-- 650100002 has moderate fraud evidence: WARN
INSERT INTO fraud_reports (reported_number, report_type) VALUES
('650100002', 'Mobile Money fraud'),
('650100002', 'Suspicious caller'),
('650100002', 'Fake transaction request'),
('650100002', 'Harassment'),
('650100002', 'Scam attempt'),
('650100002', 'Mobile Money fraud'),
('650100002', 'Suspicious caller');

-- 650100003 has stronger evidence: ALERT
INSERT INTO fraud_reports (reported_number, report_type) VALUES
('650100003', 'Mobile Money fraud'),
('650100003', 'Mobile Money fraud'),
('650100003', 'Fake transaction request'),
('650100003', 'Scam attempt'),
('650100003', 'Scam attempt'),
('650100003', 'Fraudulent activity'),
('650100003', 'Fraudulent activity'),
('650100003', 'Suspicious caller'),
('650100003', 'Harassment'),
('650100003', 'Mobile Money fraud');

INSERT INTO wangiri_events (phone_number, event_count) VALUES
('650100003', 2);

INSERT INTO sim_flags (phone_number, risk_level, reason) VALUES
('650100003', 'HIGH', 'Recent suspicious SIM activity');
