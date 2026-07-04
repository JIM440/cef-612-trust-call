package com.trustcall.slee.buildingblock;

public class SleeDecisionEngine {

    public String analyze(
            String caller,
            int reputationScore,
            int fraudReports,
            int wangiriEvents,
            String simSwapRisk
    ) {
        int finalScore = reputationScore;

        finalScore -= fraudReports * 5;
        finalScore -= wangiriEvents * 10;

        if ("HIGH".equalsIgnoreCase(simSwapRisk)) {
            finalScore -= 20;
        } else if ("MEDIUM".equalsIgnoreCase(simSwapRisk)) {
            finalScore -= 10;
        }

        if (finalScore < 0) {
            finalScore = 0;
        }

        if (fraudReports > 10) {
            return "WARN";
        }

        if (fraudReports >= 7) {
            return "WARN";
        }

        if (wangiriEvents >= 3) {
            return "WARN";
        }

        if ("HIGH".equalsIgnoreCase(simSwapRisk)) {
            return "WARN";
        }

        if (finalScore >= 70) {
            return "ALLOW";
        }

        return "WARN";
    }
}
