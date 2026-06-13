# TrustCall Final Demonstration Checklist

Date: 13 June 2026

Project: CEF-612 TrustCall Intelligent Call Screening System

--------------------------------------------------
PRE-DEMO CHECKS
--------------------------------------------------

[ ] PostgreSQL running

[ ] TrustCall HTTP Server running

Command:

mvn exec:java -Dexec.mainClass="com.trustcall.TrustCallHttpServer"

[ ] OpenSIPS running

Command:

sudo opensips -F -dd

[ ] Linphone account 1001 registered

[ ] MicroSIP account 1002 registered

[ ] Internet/WSL networking functioning

--------------------------------------------------
DEMO 1: DATABASE VALIDATION
--------------------------------------------------

Objective:

Show reputation data stored in PostgreSQL.

Commands:

sudo -u postgres psql trustcall -c "SELECT * FROM reputation;"

Expected:

1001 -> Trusted

1002 -> Suspected Spam

Status:

[ ]

--------------------------------------------------
DEMO 2: TRUSTCALL DECISION API
--------------------------------------------------

Objective:

Show caller evaluation through HTTP API.

Commands:

curl "http://localhost:8080/decision?caller=1001"

curl "http://localhost:8080/decision?caller=1002"

Expected:

ALLOW

BLOCK

Status:

[ ]

--------------------------------------------------
DEMO 3: TRUSTED CALL
--------------------------------------------------

Call:

1001 -> 1002

Expected:

Call rings

Call connects

Audio established

Status:

[ ]

--------------------------------------------------
DEMO 4: BLOCKED CALL
--------------------------------------------------

Call:

1002 -> 1001

Expected:

Call rejected

Popup displays:

Call Blocked by TrustCall

Status:

[ ]

--------------------------------------------------
DEMO 5: OPENSIPS LOG VALIDATION
--------------------------------------------------

Expected OpenSIPS logs:

INVITE received

TrustCall decision for 1001: ALLOW

TrustCall decision for 1002: BLOCK

TrustCall blocked caller 1002

Status:

[ ]

--------------------------------------------------
DEMO 6: DATABASE EVIDENCE
--------------------------------------------------

Commands:

sudo -u postgres psql trustcall -c "SELECT * FROM fraud_reports;"

sudo -u postgres psql trustcall -c "SELECT * FROM wangiri_events;"

sudo -u postgres psql trustcall -c "SELECT * FROM simswap_events;"

sudo -u postgres psql trustcall -c "SELECT * FROM call_decisions;"

Status:

[ ]

--------------------------------------------------
PROJECT ACHIEVEMENTS
--------------------------------------------------

✓ SIP Registration

✓ SIP Call Routing

✓ PostgreSQL Integration

✓ Caller Reputation Evaluation

✓ Fraud Detection

✓ Wangiri Detection

✓ SIM Swap Risk Evaluation

✓ TrustCall HTTP Decision API

✓ OpenSIPS Integration

✓ Automatic Call Blocking

✓ JAIN SLEE Architecture Skeleton

--------------------------------------------------
END OF DEMO
--------------------------------------------------
