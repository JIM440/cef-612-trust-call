package com.trustcall;

import com.trustcall.model.CallerReputation;
import com.trustcall.model.CallDecision;
import com.trustcall.repository.ReputationRepository;
import com.trustcall.repository.FraudRepository;
import com.trustcall.repository.WangiriRepository;
import com.trustcall.repository.SimSwapRepository;
import com.trustcall.model.SimSwapEvent;
import com.trustcall.service.CallAnalysisService;

public class TrustCallCli {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("UNKNOWN");
            return;
        }

        String caller = args[0];

        ReputationRepository reputationRepository =
                new ReputationRepository();

        FraudRepository fraudRepository =
                new FraudRepository();

        WangiriRepository wangiriRepository =
                new WangiriRepository();

        SimSwapRepository simSwapRepository =
                new SimSwapRepository();

        CallAnalysisService analysisService =
                new CallAnalysisService();

        CallerReputation reputation =
                reputationRepository.findByNumber(caller);

        int reputationScore =
                reputation == null ? 50 : reputation.getScore();

        int fraudReports =
                fraudRepository.countReportsForNumber(caller);

        int wangiriEvents =
                wangiriRepository.countEventsForNumber(caller);

        SimSwapEvent simSwapEvent =
                simSwapRepository.findByNumber(caller);

        String simSwapRisk =
                simSwapEvent == null ? "NO SWAP" : "HIGH";

        CallDecision decision =
                analysisService.analyzeCall(
                        caller,
                        reputationScore,
                        fraudReports,
                        wangiriEvents,
                        simSwapRisk
                );

        System.out.println(decision.getAction());
    }
}
