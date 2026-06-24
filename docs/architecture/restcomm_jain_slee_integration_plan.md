# RestComm JAIN SLEE Integration Plan

Date: 13 June 2026

Project: CEF-612 TrustCall Intelligent Call Screening System

## Current Status

TrustCall currently has:

- Working OpenSIPS SIP routing
- Working TrustCall Core Java logic
- PostgreSQL-backed fraud and reputation data
- HTTP decision API
- OpenSIPS call blocking integration
- JAIN SLEE SBB skeletons

Full RestComm JAIN SLEE deployment has not yet been completed.

## Purpose of JAIN SLEE in TrustCall

RestComm JAIN SLEE will act as the telecom service execution environment. It will host Service Building Blocks that process telecom events such as incoming calls, fraud reports, Wangiri events, USSD requests, and SIM swap alerts.

## Planned SLEE Building Blocks

### Reputation SBB

Responsibilities:

- Receive incoming call events
- Query caller reputation
- Return GREEN, YELLOW, or RED trust level

### Fraud SBB

Responsibilities:

- Process fraud reports
- Detect repeated fraud complaints
- Trigger fraud alerts

### Wangiri SBB

Responsibilities:

- Detect repeated short-duration calls
- Identify possible one-ring scam behavior
- Raise WANGIRI DETECTED alerts

### SIM Swap SBB

Responsibilities:

- Track recent SIM changes
- Detect high-risk SIM swap activity
- Raise SIM ANOMALY DETECTED alerts

### USSD SBB

Responsibilities:

- Process USSD requests such as #123#number
- Return caller reputation and risk status

### Reporting SBB

Responsibilities:

- Accept subscriber reports
- Store fraud, harassment, and Wangiri reports
- Update caller risk score

## Planned Event Types

- IncomingCallEvent
- FraudEvent
- WangiriEvent
- SimSwapEvent
- UssdRequestEvent
- ReportSubmittedEvent

## Planned Integration Flow

Caller sends SIP INVITE

OpenSIPS receives INVITE

OpenSIPS forwards call event to JAIN SIP or HTTP bridge

JAIN SIP Resource Adaptor triggers IncomingCallEvent

RestComm JAIN SLEE receives event

Reputation SBB processes event

Fraud, Wangiri, and SIM Swap SBBs contribute risk signals

TrustCall Core calculates final decision

Decision returned:

- ALLOW
- WARN
- BLOCK

OpenSIPS routes or blocks the call

## Current Implementation Equivalent

At MVP level, the same decision flow is currently implemented as:

OpenSIPS

→ TrustCall HTTP Decision API

→ PostgreSQL

→ CallAnalysisService

→ ALLOW or BLOCK

This validates the core decision logic before full SLEE deployment.

## Remaining Work for Real RestComm Deployment

- Install RestComm/Mobicents JAIN SLEE
- Configure JAIN SIP Resource Adaptor
- Convert skeleton SBB classes into deployable SBB components
- Add real sbb-jar.xml descriptors
- Add service.xml descriptors
- Package SBB modules as deployable artifacts
- Deploy SBBs into the SLEE container
- Trigger IncomingCallEvent from SIP INVITE
- Validate event processing inside the SLEE container

## Conclusion

The TrustCall project has prepared the JAIN SLEE architecture and SBB skeletons. The MVP currently uses an HTTP decision API for practical OpenSIPS integration. Full RestComm JAIN SLEE deployment remains the next advanced integration step.
