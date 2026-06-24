package com.trustcall.slee;

/**
 * CallControlSbb coordinates all TrustCall Service Building Blocks.
 */
public class CallControlSbb {

    private final ReputationSbb reputationSbb =
            new ReputationSbb();

    private final FraudDetectionSbb fraudDetectionSbb =
            new FraudDetectionSbb();

    private final WangiriDetectionSbb wangiriDetectionSbb =
            new WangiriDetectionSbb();

    private final SimSwapSbb simSwapSbb =
            new SimSwapSbb();

    private final TrustCallSbb trustCallSbb =
            new TrustCallSbb();

    public void onCallReceived(String caller, String callee) {

        System.out.println("CallControlSbb: Call event received");

        reputationSbb.checkReputation(caller);
        fraudDetectionSbb.checkFraudReports(caller);
        wangiriDetectionSbb.checkWangiriEvents(caller);
        simSwapSbb.checkSimSwapRisk(caller);

        System.out.println("CallControlSbb: Forwarding call to TrustCallSbb");

        trustCallSbb.onIncomingCall(caller, callee);
    }
}
