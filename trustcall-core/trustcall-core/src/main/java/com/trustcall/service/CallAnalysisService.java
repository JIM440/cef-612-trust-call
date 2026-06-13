package com.trustcall.service;

import com.trustcall.model.CallDecision;

public class CallAnalysisService {

    public CallDecision analyzeCall(
            String caller,
            int reputationScore,
            int fraudReports,
            int wangiriEvents,
            String simSwapRisk
    ) {
        int finalScore = reputationScore;

        finalScore -= fraudReports * 10;
        finalScore -= wangiriEvents * 15;

        if ("HIGH".equals(simSwapRisk)) {
            finalScore -= 25;
        } else if ("MEDIUM".equals(simSwapRisk)) {
            finalScore -= 10;
        }

        if (finalScore < 0) {
            finalScore = 0;
        }

        String action;
        String reason;

        if (finalScore >= 70) {
            action = "ALLOW";
            reason = "Caller appears safe.";
        } else if (finalScore >= 40) {
            action = "WARN";
            reason = "Caller has moderate risk indicators.";
        } else {
            action = "BLOCK";
            reason = "Caller has high fraud risk.";
        }

        return new CallDecision(caller, finalScore, action, reason);
    }
}
