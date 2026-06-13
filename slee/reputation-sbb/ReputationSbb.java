package slee.reputation;

public class ReputationSbb {

    public void onIncomingCall(String caller) {
        System.out.println("Reputation SBB received call from: " + caller);
    }
}
