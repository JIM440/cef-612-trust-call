package slee.reputation;

import slee.events.IncomingCallEvent;

public class ReputationSbb {

    public void onIncomingCall(IncomingCallEvent event) {

        System.out.println("CALL RECEIVED BY REPUTATION SBB");
        System.out.println("Caller  : " + event.getCaller());
        System.out.println("Receiver: " + event.getReceiver());
    }
}
