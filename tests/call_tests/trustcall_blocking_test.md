# TrustCall Blocking Integration Test

## Objective

Verify that OpenSIPS can call the TrustCall decision API before routing SIP calls.

## Components Used

- OpenSIPS 3.6.6
- TrustCall Java Core
- PostgreSQL
- Linphone
- MicroSIP

## Test Accounts

- 1001: Trusted caller
- 1002: Suspected spam caller

## Test 1: Trusted Caller

Call:

1001 -> 1002

Result:

- TrustCall returned ALLOW
- OpenSIPS routed the call
- Call reached the destination

Status: PASS

## Test 2: Blocked Caller

Call:

1002 -> 1001

Result:

- TrustCall returned BLOCK
- OpenSIPS rejected the call
- Softphone displayed: Call Blocked by TrustCall

Status: PASS

## Conclusion

The TrustCall integration test was successful. OpenSIPS now checks caller reputation through the TrustCall HTTP API before routing calls.
