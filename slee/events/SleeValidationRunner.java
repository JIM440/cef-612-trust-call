package slee.events;

import slee.reputation.ReputationSbb;

public class SleeValidationRunner {

    public static void main(String[] args) {

        IncomingCallEvent event =
                new IncomingCallEvent("1002", "1001");

        ReputationSbb reputationSbb =
                new ReputationSbb();

        reputationSbb.onIncomingCall(event);
    }
}
