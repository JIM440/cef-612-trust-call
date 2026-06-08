# Initial SIP Communication Test

Date: 2026-06-08

## Setup

- SIP Proxy: OpenSIPS 3.6.6
- Softphone 1: Linphone
- Softphone 2: MicroSIP
- User 1001 registered successfully
- User 1002 registered successfully

## Test

Call between:

- 1001
- 1002

## Result

- REGISTER messages received by OpenSIPS
- INVITE messages routed through OpenSIPS
- Call reached the destination endpoint
- Audio worked both ways

## Status

PASS
