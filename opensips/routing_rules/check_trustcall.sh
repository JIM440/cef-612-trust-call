#!/bin/bash

CALLER="$1"

cd ~/cef-612-trust-call/trustcall-core/trustcall-core || exit 1

mvn -q exec:java \
  -Dexec.mainClass="com.trustcall.TrustCallCli" \
  -Dexec.args="$CALLER" | tail -n 1
