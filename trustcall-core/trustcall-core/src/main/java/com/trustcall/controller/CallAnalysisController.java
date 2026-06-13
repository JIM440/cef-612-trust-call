package com.trustcall.controller;

import com.trustcall.model.CallDecision;
import com.trustcall.repository.CallDecisionRepository;
import com.trustcall.service.CallAnalysisService;

public class CallAnalysisController {

    private final CallAnalysisService service =
            new CallAnalysisService();

    private final CallDecisionRepository repository =
            new CallDecisionRepository();

    public void displayDecision(
            String caller,
            int reputationScore,
            int fraudReports,
            int wangiriEvents,
            String simSwapRisk
    ) {
        CallDecision decision =
                service.analyzeCall(
                        caller,
                        reputationScore,
                        fraudReports,
                        wangiriEvents,
                        simSwapRisk
                );

        repository.save(decision);

        System.out.println("Caller       : " + decision.getCaller());
        System.out.println("Final Score  : " + decision.getFinalScore());
        System.out.println("Action       : " + decision.getAction());
        System.out.println("Reason       : " + decision.getReason());
    }
}
