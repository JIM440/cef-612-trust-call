# TrustCall V2 Implementation Report

**Project:** TrustCall – Intelligent Call Protection System

**Course:** CEF-612 Intelligent Networks

**Version:** 2.0

**Date:** July 2026

---

# 1. Executive Summary

TrustCall is an intelligent call protection platform designed to detect and prevent fraudulent telephone calls before they reach the intended recipient.

The system combines SIP signaling, OpenSIPS, RestComm JAIN SLEE, REST-based decision services, and a PostgreSQL-backed reputation engine to analyze incoming calls in real time and determine whether a call should be:

- Allowed
- Warned
- Blocked

Unlike the initial prototype, Version 2 implements actual SIP signaling through OpenSIPS and RestComm JAIN SLEE rather than relying solely on simulated events.

---

# 2. Objectives

The primary objectives of TrustCall are:

- Prevent fraudulent calls
- Protect users from Wangiri attacks
- Detect reported spam callers
- Provide USSD-based reputation lookup
- Allow fraud reporting
- Demonstrate Intelligent Network concepts using SIP and JAIN SLEE

---

# 3. Overall Architecture

> Insert Updated System Architecture Diagram

Current implementation:

```
MicroSIP / Linphone
        │
        ▼
OpenSIPS (5060)
        │
        ├──────────────► TrustCall Core REST API (8081)
        │
        ▼
RestComm JAIN SLEE
        │
        ▼
SIP Resource Adaptor (5070)
        │
        ▼
CallControlSbb
        │
        ▼
Reputation Engine
        │
        ▼
PostgreSQL
```

---

# 4. Project Components

## 4.1 TrustCall Core

Implemented in Java.

Responsibilities:

- Reputation lookup
- Decision API
- Fraud reporting
- USSD processing
- Database access

REST Endpoints

```
GET /decision?caller=1001

GET /ussd?request=*123*1002#

GET /ussd?request=*55*1002*Fraud#
```

---

## 4.2 PostgreSQL Database

Current tables:

### Users

| Field | Type |
|---------|------|
| id | SERIAL |
| phone_number | VARCHAR |
| name | VARCHAR |

### Reputation

| Field | Type |
|---------|------|
| number | VARCHAR |
| score | INTEGER |
| status | VARCHAR |

### Reports

| Field | Type |
|---------|------|
| id | SERIAL |
| reported_number | VARCHAR |
| report_type | VARCHAR |
| timestamp | TIMESTAMP |

---

## 4.3 OpenSIPS

Responsibilities

- SIP Registration
- SIP Routing
- REST Integration
- Caller lookup
- BLOCK enforcement
- User location

Current listening port

```
5060
```

---

## 4.4 RestComm JAIN SLEE

Successfully installed and activated.

Components deployed:

- SIP Resource Adaptor
- CallControlSbb
- ReputationSbb
- FraudDetectionSbb
- WangiriDetectionSbb
- SimSwapSbb
- TrustCallService

---

# 5. Call Flow

## Registration

```
Phone

↓

REGISTER

↓

OpenSIPS

↓

Location Service
```

---

## Incoming Call

```
INVITE

↓

OpenSIPS

↓

TrustCall Core

↓

Decision

↓

ALLOW
WARN
BLOCK
```

---

## BLOCK Flow

```
Caller

↓

OpenSIPS

↓

TrustCall Core

↓

BLOCK

↓

603 Declined
```

---

## ALLOW Flow

```
Caller

↓

OpenSIPS

↓

Lookup Registered User

↓

Forward INVITE

↓

Destination Phone
```

---

# 6. USSD Services

Implemented commands

Lookup reputation

```
*123*1002#
```

Report fraud

```
*55*1002*Fraud#
```

Example responses

```
Number 1002

Score 15

Status Suspected Spam
```

---

# 7. Implementation Progress

## Completed

### TrustCall Core

- REST server
- Reputation lookup
- Decision API
- Fraud reports
- PostgreSQL integration
- USSD support

---

### OpenSIPS

- SIP registration
- SIP routing
- REST API integration
- Decision enforcement
- Logging
- User location

---

### RestComm JAIN SLEE

- Runtime installation
- SIP RA activation
- DU deployment
- Service activation
- SIP INVITE reception
- HTTP decision requests

---

# 8. Current Test Results

| Scenario | Expected | Result |
|-----------|----------|--------|
| 1001 → 1002 | ALLOW | PASS |
| 1002 → 1001 | BLOCK | PASS |
| REST Decision API | PASS | PASS |
| USSD Lookup | PASS | PASS |
| Fraud Reporting | PASS | PASS |
| SIP INVITE reaches SLEE | PASS | PASS |
| TrustCallService Active | PASS | PASS |

---

# 9. Sample Logs

## Service Deployment

```
Installed ServiceID[name=TrustCallService]
```

---

## SIP Event

```
CallControlSbb:

SIP INVITE RECEIVED
```

---

## Decision

```
Caller = 1002

TrustCall decision = BLOCK

ACTION = BLOCK CALL
```

---

## OpenSIPS

```
TrustCall BLOCK: rejecting call 1002 -> 1001
```

---

## Decision API

```
GET /decision?caller=1002

BLOCK
```

---

# 10. Current Status

Successfully implemented

- REST Decision Engine
- SIP Registration
- SIP Routing
- RestComm Runtime
- SIP Resource Adaptor
- CallControlSbb
- Reputation Queries
- Fraud Reporting
- USSD Services
- BLOCK enforcement
- Logging
- Deployment automation

Remaining work

- Finalize ALLOW call establishment between SIP clients.
- Implement WARN call flow.
- Capture final demonstration screenshots.
- Execute complete end-to-end validation.
- Package the final submission.

---

# 11. Repository Structure

```
trustcall-core/

trustcall-slee/

opensips/

database/

docs/

tests/
```

---

# 12. Git Milestones

Major completed milestones

- Database implementation
- TrustCall Core
- OpenSIPS integration
- USSD modernization
- RestComm installation
- SIP Resource Adaptor deployment
- TrustCall Service activation
- Runtime cleanup
- Repository cleanup
- Runtime artifact exclusion

---

# 13. Known Limitations

The TrustCall decision engine is fully operational.

OpenSIPS successfully queries the TrustCall Core before routing each INVITE.

Blocked callers are rejected using SIP 603 responses.

RestComm JAIN SLEE successfully receives SIP INVITE events through the SIP Resource Adaptor.

The remaining work focuses on completing end-to-end SIP session establishment for allowed calls and implementing the warning workflow.

---

# 14. Conclusion

TrustCall Version 2 represents a significant progression from the initial prototype into a functional intelligent network application.

The system demonstrates successful integration between:

- OpenSIPS
- RestComm JAIN SLEE
- SIP Resource Adaptor
- Java REST services
- PostgreSQL

The platform can now make real-time decisions on incoming calls using live SIP signaling while maintaining a modular architecture suitable for future extensions such as machine-learning-based fraud detection, distributed reputation services, and telecom operator deployment.
