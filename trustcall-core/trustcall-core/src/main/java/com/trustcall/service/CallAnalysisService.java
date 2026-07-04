package com.trustcall.service;

import com.trustcall.model.CallDecision;

public class CallAnalysisService {

    public CallDecision analyzeCall(
            String caller,
            int fraudReports,
            int wangiriEvents,
            String simSwapRisk
    ) {

        int finalScore = 100;

        finalScore -= fraudReports * 5;
        finalScore -= wangiriEvents * 10;

        if ("HIGH".equalsIgnoreCase(simSwapRisk)) {
            finalScore -= 20;
        } else if ("MEDIUM".equalsIgnoreCase(simSwapRisk)) {
            finalScore -= 10;
        }

        finalScore = Math.max(0, Math.min(100, finalScore));

        String action;
        String reason;

        if (finalScore >= 70) {

            action = "ALLOW";

            reason =
                    "Caller appears safe. " +
                    "Fraud reports: " + fraudReports +
                    ", Wangiri events: " + wangiriEvents +
                    ", SIM-swap risk: " + simSwapRisk + ".";

        } else if (finalScore >= 40) {

            action = "WARN";

            reason =
                    "Caller has moderate risk indicators. " +
                    "Fraud reports: " + fraudReports +
                    ", Wangiri events: " + wangiriEvents +
                    ", SIM-swap risk: " + simSwapRisk +
                    ". Receiver should answer with caution.";

        } else {

            action = "ALERT";

            reason =
                    "High-risk caller detected. " +
                    "Fraud reports: " + fraudReports +
                    ", Wangiri events: " + wangiriEvents +
                    ", SIM-swap risk: " + simSwapRisk +
                    ". Strong fraud warning required.";
        }

        return new CallDecision(
                caller,
                finalScore,
                action,
                reason
        );
    }
}
