package com.trustcall.slee.sbb;

import com.trustcall.slee.buildingblock.ReputationSbbLogic;
import com.trustcall.slee.buildingblock.FraudSbbLogic;
import com.trustcall.slee.buildingblock.WangiriSbbLogic;
import com.trustcall.slee.buildingblock.SimSwapSbbLogic;
import com.trustcall.slee.buildingblock.SleeDecisionEngine;

import javax.sip.RequestEvent;
import javax.slee.ActivityContextInterface;
import javax.slee.CreateException;
import javax.slee.RolledBackContext;
import javax.slee.Sbb;
import javax.slee.SbbContext;

public abstract class CallControlSbb implements Sbb {

    private SbbContext sbbContext;

    public void onInviteEvent(RequestEvent event, ActivityContextInterface aci) {
        System.out.println("==================================================");
        System.out.println("CallControlSbb: SIP INVITE RECEIVED FROM SipRA");
        System.out.println("CallControlSbb: Processing inside RestComm JAIN SLEE");
        System.out.println("==================================================");

        String rawRequest = String.valueOf(event.getRequest());

        String caller = extractHeaderUser(rawRequest, "From:");
        String callee = extractHeaderUser(rawRequest, "To:");

        processCall(caller, callee);
    }

    private void processCall(String caller, String callee) {
        System.out.println("CallControlSbb: Caller = " + caller);
        System.out.println("CallControlSbb: Callee = " + callee);

        ReputationSbbLogic reputationSbb = new ReputationSbbLogic();
        FraudSbbLogic fraudSbb = new FraudSbbLogic();
        WangiriSbbLogic wangiriSbb = new WangiriSbbLogic();
        SimSwapSbbLogic simSwapSbb = new SimSwapSbbLogic();
        SleeDecisionEngine decisionEngine = new SleeDecisionEngine();

        int reputationScore = reputationSbb.getReputationScore(caller);
        String reputationStatus = reputationSbb.getReputationStatus(caller);
        int fraudReports = fraudSbb.countFraudReports(caller);
        int wangiriEvents = wangiriSbb.countWangiriEvents(caller);
        String simSwapRisk = simSwapSbb.getSimSwapRisk(caller);

        String decision = decisionEngine.analyze(
                caller,
                reputationScore,
                fraudReports,
                wangiriEvents,
                simSwapRisk
        );

        System.out.println("CallControlSbb: Reputation score = " + reputationScore);
        System.out.println("CallControlSbb: Reputation status = " + reputationStatus);
        System.out.println("CallControlSbb: Fraud reports = " + fraudReports);
        System.out.println("CallControlSbb: Wangiri events = " + wangiriEvents);
        System.out.println("CallControlSbb: SIM swap risk = " + simSwapRisk);
        System.out.println("CallControlSbb: TrustCall decision = " + decision);

        if ("WARN".equalsIgnoreCase(decision)) {
            System.out.println("CallControlSbb: ACTION = ALERT RECEIVER - PASSIVE SLEE OBSERVATION ONLY");
        } else {
            System.out.println("CallControlSbb: ACTION = ALLOW CALL - PASSIVE SLEE OBSERVATION ONLY");
        }
    }

    private String extractHeaderUser(String request, String headerName) {
        try {
            String[] lines = request.split("\\r?\\n");

            for (String line : lines) {
                if (line.startsWith(headerName)) {
                    int sipIndex = line.indexOf("sip:");
                    if (sipIndex < 0) {
                        return "UNKNOWN";
                    }

                    String value = line.substring(sipIndex + 4);
                    int atIndex = value.indexOf("@");

                    if (atIndex > 0) {
                        value = value.substring(0, atIndex);
                    }

                    return value.replace("<", "").replace(">", "").trim();
                }
            }
        } catch (Exception e) {
            System.out.println("CallControlSbb: Header parse error: " + e.getMessage());
        }

        return "UNKNOWN";
    }

    @Override
    public void setSbbContext(SbbContext context) { this.sbbContext = context; }

    @Override
    public void unsetSbbContext() { this.sbbContext = null; }

    @Override
    public void sbbCreate() throws CreateException {}

    @Override
    public void sbbPostCreate() throws CreateException {}

    @Override
    public void sbbActivate() {}

    @Override
    public void sbbPassivate() {}

    @Override
    public void sbbLoad() {}

    @Override
    public void sbbStore() {}

    @Override
    public void sbbRemove() {}

    @Override
    public void sbbExceptionThrown(Exception exception, Object event, ActivityContextInterface aci) {}

    @Override
    public void sbbRolledBack(RolledBackContext context) {}
}
