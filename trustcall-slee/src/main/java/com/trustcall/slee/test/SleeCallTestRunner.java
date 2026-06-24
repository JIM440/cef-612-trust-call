package com.trustcall.slee.test;

import com.trustcall.slee.event.IncomingCallEvent;
import com.trustcall.slee.sbb.CallControlSbb;

public class SleeCallTestRunner {
    public static void main(String[] args) {
        IncomingCallEvent event = new IncomingCallEvent("1002", "1001");
        CallControlSbb sbb = new CallControlSbb() {};
        sbb.onIncomingCallEvent(event, null);
    }
}
